import heapq
import os
import pickle
import struct
class HuffmanNode:
    def __init__(self, char=None, freq=0):
        self.char = char
        self.freq = freq
        self.left = None
        self.right = None
    def __lt__(self, other):
        return self.freq < other.freq
class DirectoryZipper:
    def __init__(self):
        self.codes = {}
    def _make_frequency_dict(self, data):
        frequency = {}
        for byte in data:
            frequency[byte] = frequency.get(byte, 0) + 1
        return frequency
    def _build_heap(self, frequency):
        heap = []
        for key in frequency:
            node = HuffmanNode(key, frequency[key])
            heapq.heappush(heap, node)
        return heap
    def _merge_nodes(self, heap):
        while len(heap) > 1:
            node1 = heapq.heappop(heap)
            node2 = heapq.heappop(heap)
            merged = HuffmanNode(None, node1.freq + node2.freq)
            merged.left = node1
            merged.right = node2
            heapq.heappush(heap, merged)
    def _make_codes_helper(self, root, current_code):
        if root is None:
            return
        if root.char is not None:
            self.codes[root.char] = current_code
            return
        self._make_codes_helper(root.left, current_code + "0")
        self._make_codes_helper(root.right, current_code + "1")
    def _make_codes(self, heap):
        root = heapq.heappop(heap)
        self._make_codes_helper(root, "")
    def _compress_bytes(self, data):
        """Compresses a single byte stream and returns (metadata, bitstream_bytes)."""
        if not data:
            return pickle.dumps({}), b""
        self.codes = {}
        frequency = self._make_frequency_dict(data)
        heap = self._build_heap(frequency)
        self._merge_nodes(heap)
        self._make_codes(heap)
        encoded_text = "".join([self.codes[byte] for byte in data])
        extra_padding = 8 - (len(encoded_text) % 8)
        encoded_text += "0" * extra_padding
        padded_info = "{0:08b}".format(extra_padding)
        full_bitstream = padded_info + encoded_text
        compressed_bytes = bytearray()
        for i in range(0, len(full_bitstream), 8):
            compressed_bytes.append(int(full_bitstream[i:i+8], 2))
        return pickle.dumps(frequency), bytes(compressed_bytes)
    def _decompress_bytes(self, metadata_bytes, compressed_bytes):
        """Decompresses a single compressed payload using its serialized tree metadata."""
        frequency = pickle.loads(metadata_bytes)
        if not frequency:
            return b""
        bit_string = "".join([bin(b)[2:].zfill(8) for b in compressed_bytes])
        extra_padding = int(bit_string[:8], 2)
        encoded_text = bit_string[8:-extra_padding] if extra_padding > 0 else bit_string[8:]
        heap = self._build_heap(frequency)
        self._merge_nodes(heap)
        root = heapq.heappop(heap)
        decoded_bytes = bytearray()
        current_node = root
        for bit in encoded_text:
            current_node = current_node.left if bit == '0' else current_node.right
            if current_node.char is not None:
                decoded_bytes.append(current_node.char)
                current_node = root
        return bytes(decoded_bytes)
    def compress_dir(self, source_dir, output_archive_path):
        """Walks through a directory tree, compresses each file, and writes an archive."""
        if not os.path.isdir(source_dir):
            print(f"Error: '{source_dir}' is not a valid directory.")
            return
        print(f"Archiving and compressing directory: '{source_dir}'...")
        # Ensure path format is clean
        source_dir = os.path.normpath(source_dir)
        base_name = os.path.basename(source_dir)
        with open(output_archive_path, 'wb') as archive:
            for root, _, files in os.walk(source_dir):
                for file in files:
                    full_path = os.path.join(root, file)
                    # Create a relative path inside the zip (e.g., "my_folder/sub/file.txt")
                    rel_path = os.path.join(base_name, os.path.relpath(full_path, source_dir))
                    rel_path_encoded = rel_path.encode('utf-8')
                    with open(full_path, 'rb') as f:
                        raw_data = f.read()
                    # Compress the individual file data
                    meta, comp_data = self._compress_bytes(raw_data)
                    # Write dynamic packet header: [Path Length][Meta Length][Data Length][Path][Meta][Data]
                    header = struct.pack('!III', len(rel_path_encoded), len(meta), len(comp_data))
                    archive.write(header)
                    archive.write(rel_path_encoded)
                    archive.write(meta)
                    archive.write(comp_data)
        print(f"Success! Saved archive to '{output_archive_path}'")
    def decompress_dir(self, archive_path, extract_to_dir="."):
        """Reads custom archive pack and reconstructs original file directory tree."""
        if not os.path.exists(archive_path):
            print(f"Error: Archive '{archive_path}' not found.")
            return
        print(f"Extracting '{archive_path}' to '{extract_to_dir}'...")
        with open(archive_path, 'rb') as archive:
            while True:
                header_bytes = archive.read(12)  # Read 3 standard unsigned integers (4 bytes each)
                if not header_bytes:
                    break  # End of file reached safely
                path_len, meta_len, data_len = struct.unpack('!III', header_bytes)
                # Fetch matching segments from stream
                rel_path = archive.read(path_len).decode('utf-8')
                meta = archive.read(meta_len)
                comp_data = archive.read(data_len)
                # Reconstruct original content
                raw_data = self._decompress_bytes(meta, comp_data)
                # Handle output folder structuring safely
                final_output_path = os.path.join(extract_to_dir, rel_path)
                os.makedirs(os.path.dirname(final_output_path), exist_ok=True)
                with open(final_output_path, 'wb') as f:
                    f.write(raw_data)
        print("Extraction completed successfully!")
if __name__ == "__main__":
    import sys
    def show_usage():
        print("\n--- Recursive Directory Zipper ---")
        print("Zip folder:        python zipper.py -c <my_folder> <archive.pzip>")
        print("Unzip archive:     python zipper.py -d <archive.pzip> <extraction_target_dir>")
        print("----------------------------------\n")
    if len(sys.argv) < 4:
        show_usage()
    else:
        mode = sys.argv[1]
        source = sys.argv[2]
        dest = sys.argv[3]
        zipper = DirectoryZipper()
        if mode == "-c":
            zipper.compress_dir(source, dest)
        elif mode == "-d":
            zipper.decompress_dir(source, dest)
        else:
            print("Invalid execution mode flag.")
            show_usage()
