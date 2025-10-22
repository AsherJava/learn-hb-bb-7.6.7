/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import nr.single.map.configurations.file.ini.StreamException;

public abstract class Stream {
    private boolean encode = true;
    private String charset = "GBK";
    private static final int BUFF_SIZE = 1024;
    public static final int SEEK_ORIGIN_START = 0;
    public static final int SEEK_ORIGIN_CURRENT = 1;
    public static final int SEEK_ORIGIN_END = 2;

    public void setUseEncode(boolean useEncode) {
        this.encode = useEncode;
    }

    public boolean getUseEncode() {
        return this.encode;
    }

    public void setCharset(String encodeCharset) {
        if (encodeCharset != null && encodeCharset.length() != 0) {
            this.charset = encodeCharset;
        }
    }

    public String getCharset() {
        return this.charset;
    }

    public abstract void setSize(long var1) throws StreamException;

    public abstract int read(byte[] var1, int var2, int var3) throws StreamException;

    public abstract byte read() throws StreamException;

    public abstract int write(byte[] var1, int var2, int var3) throws StreamException;

    public abstract void write(byte var1) throws StreamException;

    public abstract long seek(long var1, int var3) throws StreamException;

    public void readBuffer(byte[] buffer, int pos, int count) throws StreamException {
        if (count != 0 && this.read(buffer, pos, count) != count) {
            throw new StreamException("Stream read error");
        }
    }

    public void writeBuffer(byte[] buffer, int pos, int count) throws StreamException {
        if (count != 0 && this.write(buffer, pos, count) != count) {
            throw new StreamException("Stream write error");
        }
    }

    public long copyFrom(Stream source, long count) throws StreamException {
        if (source == null) {
            return 0L;
        }
        if (count == 0L) {
            source.setPosition(0L);
            count = source.getSize();
        }
        int bufSize = count > 1024L ? 1024 : (int)count;
        byte[] buffer = new byte[1024];
        while (count != 0L) {
            int n = count > (long)bufSize ? bufSize : (int)count;
            source.readBuffer(buffer, 0, n);
            this.writeBuffer(buffer, 0, n);
            count -= (long)n;
        }
        return 0L;
    }

    public long copyFrom(InputStream stream, long size) throws StreamException, IOException {
        if (stream == null) {
            return 0L;
        }
        long readcount = 0L;
        if (size == 0L) {
            byte[] buf = new byte[1024];
            int b = 0;
            do {
                if ((b = stream.read(buf)) <= 0) continue;
                this.write(buf, 0, b);
                readcount += (long)b;
            } while (b > 0);
            while (b > 0) {
                b = stream.read(buf);
                if (b <= 0) continue;
                this.write(buf, 0, b);
            }
        } else {
            int readSize;
            byte[] buffer = new byte[1024];
            do {
                int blockSize = size > 1024L ? 1024 : (int)size;
                readSize = stream.read(buffer, 0, blockSize);
                this.write(buffer, 0, readSize);
                readcount += (long)readSize;
            } while (readSize > 0 && (size -= (long)readSize) > 0L);
        }
        return readcount;
    }

    public long copyTo(OutputStream stream, long size) throws StreamException, IOException {
        long writecount = 0L;
        byte[] buffer = new byte[1024];
        int writesize = this.read(buffer, 0, 1024);
        while (writesize > 0 && size > 0L) {
            stream.write(buffer, 0, writesize);
            writesize = this.read(buffer, 0, 1024);
            writecount += (long)writesize;
            size -= (long)writesize;
        }
        return writecount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] getBytes() throws StreamException {
        long oldPos = this.getPosition();
        try {
            byte[] rt = new byte[(int)this.getSize()];
            this.setPosition(0L);
            this.read(rt, 0, rt.length);
            byte[] byArray = rt;
            return byArray;
        }
        finally {
            this.setPosition(oldPos);
        }
    }

    public long getSize() throws StreamException {
        long pos = this.seek(0L, 1);
        long r = this.seek(0L, 2);
        this.seek(pos, 0);
        return r;
    }

    public long getPosition() throws StreamException {
        return this.seek(0L, 1);
    }

    public void setPosition(long pos) throws StreamException {
        this.seek(pos, 0);
    }

    public short readShort() throws StreamException {
        int b1 = this.read() & 0xFF;
        int b2 = this.read() & 0xFF;
        return (short)((b2 << 8) + (b1 << 0));
    }

    public int readWord() throws StreamException {
        int b1 = this.read() & 0xFF;
        int b2 = this.read() & 0xFF;
        return (b2 << 8) + (b1 << 0);
    }

    public int readInt() throws StreamException {
        int b1 = this.read() & 0xFF;
        int b2 = this.read() & 0xFF;
        int b3 = this.read() & 0xFF;
        int b4 = this.read() & 0xFF;
        return (b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0);
    }

    public float readFloat() throws StreamException {
        return Float.intBitsToFloat(this.readInt());
    }

    public double readDouble() throws StreamException {
        return Double.longBitsToDouble(this.readLong());
    }

    public String readString(int length) throws StreamException {
        byte[] str = new byte[length];
        this.read(str, 0, length);
        return this.decodeString(str);
    }

    public String decodeString(byte[] data) {
        if (this.encode) {
            try {
                return new String(data, this.charset);
            }
            catch (UnsupportedEncodingException ex) {
                return new String(data);
            }
        }
        return new String(data);
    }

    public String readStringBySize() throws StreamException {
        int len = this.readInt();
        if (len == 0) {
            return null;
        }
        return this.readString(len);
    }

    public boolean readBool() throws StreamException {
        return this.read() > 0;
    }

    public long readLong() throws StreamException {
        long b1 = this.read() & 0xFF;
        long b2 = this.read() & 0xFF;
        long b3 = this.read() & 0xFF;
        long b4 = this.read() & 0xFF;
        long b5 = this.read() & 0xFF;
        long b6 = this.read() & 0xFF;
        long b7 = this.read() & 0xFF;
        long b8 = this.read();
        return (b8 << 56) + (b7 << 48) + (b6 << 40) + (b5 << 32) + (b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0);
    }

    public void writeShort(short value) throws StreamException {
        this.write((byte)(value >>> 0 & 0xFF));
        this.write((byte)(value >>> 8 & 0xFF));
    }

    public void writeWord(int value) throws StreamException {
        this.write((byte)(value >>> 0 & 0xFF));
        this.write((byte)(value >>> 8 & 0xFF));
    }

    public void writeInt(int value) throws StreamException {
        this.write((byte)(value >>> 0 & 0xFF));
        this.write((byte)(value >>> 8 & 0xFF));
        this.write((byte)(value >>> 16 & 0xFF));
        this.write((byte)(value >>> 24 & 0xFF));
    }

    public void writeLong(long value) throws StreamException {
        this.write((byte)(value >>> 0 & 0xFFL));
        this.write((byte)(value >>> 8 & 0xFFL));
        this.write((byte)(value >>> 16 & 0xFFL));
        this.write((byte)(value >>> 24 & 0xFFL));
        this.write((byte)(value >>> 32 & 0xFFL));
        this.write((byte)(value >>> 40 & 0xFFL));
        this.write((byte)(value >>> 48 & 0xFFL));
        this.write((byte)(value >>> 56 & 0xFFL));
    }

    public void writeFloat(float value) throws StreamException {
        this.writeInt(Float.floatToIntBits(value));
    }

    public void writeDouble(double value) throws StreamException {
        this.writeLong(Double.doubleToLongBits(value));
    }

    public void writeBool(boolean value) throws StreamException {
        byte b = value ? (byte)1 : 0;
        this.write(b);
    }

    public void writeString(String value) throws StreamException {
        byte[] b = this.encodeString(value);
        this.write(b, 0, b.length);
    }

    public byte[] encodeString(String value) {
        if (value == null) {
            return null;
        }
        if (this.encode) {
            try {
                return value.getBytes(this.charset);
            }
            catch (UnsupportedEncodingException e) {
                return value.getBytes();
            }
        }
        return value.getBytes();
    }

    public void writeStringWithSize(String value) throws StreamException {
        if (value == null) {
            this.writeInt(0);
            return;
        }
        byte[] b = this.encodeString(value);
        this.writeInt(b.length);
        this.write(b, 0, b.length);
    }

    public void loadFromFile(String fileName) throws StreamException, IOException {
        try (FileInputStream source = new FileInputStream(fileName);){
            this.loadFromStream(source);
        }
    }

    public void loadFromStream(InputStream inStream) throws StreamException, IOException {
        this.copyFrom(inStream, 0L);
    }

    public void saveToFile(String fileName) throws StreamException, IOException {
        try (FileOutputStream dest = new FileOutputStream(fileName);){
            this.saveToStream(dest);
        }
    }

    public void saveToStream(OutputStream outStream) throws StreamException, IOException {
        this.setPosition(0L);
        this.copyTo(outStream, this.getSize());
    }
}

