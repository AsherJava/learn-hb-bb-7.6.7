/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie.dat;

import com.jiuqi.bi.util.trie.dat.ByteUtil;
import com.jiuqi.bi.util.trie.dat.IOUtil;
import com.jiuqi.bi.util.trie.dat.Services;

class ByteArray {
    byte[] bytes;
    int offset;

    public ByteArray(byte[] bytes) {
        this.bytes = bytes;
    }

    public static ByteArray createByteArray(String path) {
        byte[] bytes = IOUtil.readBytes(path);
        if (bytes == null) {
            return null;
        }
        return new ByteArray(bytes);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int nextInt() {
        int result = ByteUtil.bytesHighFirstToInt(this.bytes, this.offset);
        this.offset += 4;
        return result;
    }

    public double nextDouble() {
        double result = ByteUtil.bytesHighFirstToDouble(this.bytes, this.offset);
        this.offset += 8;
        return result;
    }

    public char nextChar() {
        char result = ByteUtil.bytesHighFirstToChar(this.bytes, this.offset);
        this.offset += 2;
        return result;
    }

    public byte nextByte() {
        return this.bytes[this.offset++];
    }

    public boolean hasMore() {
        return this.offset < this.bytes.length;
    }

    public String nextString() {
        char[] buffer = new char[this.nextInt()];
        for (int i = 0; i < buffer.length; ++i) {
            buffer[i] = this.nextChar();
        }
        return new String(buffer);
    }

    public float nextFloat() {
        float result = ByteUtil.bytesHighFirstToFloat(this.bytes, this.offset);
        this.offset += 4;
        return result;
    }

    public int nextUnsignedShort() {
        byte a = this.nextByte();
        byte b = this.nextByte();
        return (a & 0xFF) << 8 | b & 0xFF;
    }

    public String nextUTF() {
        int c;
        int utflen = this.nextUnsignedShort();
        byte[] bytearr = null;
        char[] chararr = null;
        bytearr = new byte[utflen];
        chararr = new char[utflen];
        int count = 0;
        int chararr_count = 0;
        for (int i = 0; i < utflen; ++i) {
            bytearr[i] = this.nextByte();
        }
        while (count < utflen && (c = bytearr[count] & 0xFF) <= 127) {
            ++count;
            chararr[chararr_count++] = (char)c;
        }
        block7: while (count < utflen) {
            c = bytearr[count] & 0xFF;
            switch (c >> 4) {
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    ++count;
                    chararr[chararr_count++] = (char)c;
                    continue block7;
                }
                case 12: 
                case 13: {
                    byte char2;
                    if ((count += 2) > utflen) {
                        Services.logger.severe("malformed input: partial character at end");
                    }
                    if (((char2 = bytearr[count - 1]) & 0xC0) != 128) {
                        Services.logger.severe("malformed input around byte " + count);
                    }
                    chararr[chararr_count++] = (char)((c & 0x1F) << 6 | char2 & 0x3F);
                    continue block7;
                }
                case 14: {
                    if ((count += 3) > utflen) {
                        Services.logger.severe("malformed input: partial character at end");
                    }
                    byte char2 = bytearr[count - 2];
                    byte char3 = bytearr[count - 1];
                    if ((char2 & 0xC0) != 128 || (char3 & 0xC0) != 128) {
                        Services.logger.severe("malformed input around byte " + (count - 1));
                    }
                    chararr[chararr_count++] = (char)((c & 0xF) << 12 | (char2 & 0x3F) << 6 | (char3 & 0x3F) << 0);
                    continue block7;
                }
            }
            Services.logger.severe("malformed input around byte " + count);
        }
        return new String(chararr, 0, chararr_count);
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.bytes.length;
    }

    public void close() {
        this.bytes = null;
    }
}

