/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SingleFileDataCrypt {
    private static final int DEFAULT_BUFFERSIZE = 2048;

    public static void decodeStream(FileInputStream soureStream, FileOutputStream destStream) throws IOException {
        SingleFileDataCrypt.decodeStream(soureStream, destStream, 1);
    }

    public static void decodeStream(FileInputStream soureStream, FileOutputStream destStream, int version) throws IOException {
        int bufferSize = 0;
        while (soureStream.available() > 0) {
            bufferSize = soureStream.available() < 2048 ? soureStream.available() : 2048;
            byte[] buffer = new byte[bufferSize];
            soureStream.read(buffer, 0, bufferSize);
            SingleFileDataCrypt.doDecodeBuffer(buffer, 0, bufferSize);
            if (version == 2 && bufferSize >= 3) {
                byte a = buffer[0];
                byte b = buffer[1];
                byte c = buffer[2];
                buffer[0] = b;
                buffer[1] = c;
                buffer[2] = a;
            }
            destStream.write(buffer, 0, buffer.length);
        }
    }

    public static byte[] decodeBuffer(byte[] buffer, int startPos) {
        int bufferSize = 0;
        for (int tmpPos = startPos; tmpPos < buffer.length; tmpPos += bufferSize) {
            bufferSize = buffer.length - tmpPos < 2048 ? buffer.length - tmpPos : 2048;
            SingleFileDataCrypt.doDecodeBuffer(buffer, tmpPos, bufferSize);
        }
        return buffer;
    }

    public static void doDecodeBuffer(byte[] buffer, int startPos, int bufferSize) {
        if (bufferSize >= 4) {
            int ebx = bufferSize ^ 0x715C0D4;
            int edx = 59313618;
            for (int i = startPos; i < startPos + bufferSize; i += 4) {
                int oldValue = SingleFileDataCrypt.getInt(buffer, i);
                int newValue = oldValue - ebx ^ edx;
                edx += oldValue;
                ebx = Integer.rotateLeft(ebx, 1);
                int temp = buffer.length - 1;
                if (temp - i < 3) continue;
                buffer[i] = (byte)(newValue & 0xFF);
                buffer[i + 1] = (byte)(newValue >> 8 & 0xFF);
                buffer[i + 2] = (byte)(newValue >> 16 & 0xFF);
                buffer[i + 3] = (byte)(newValue >> 24 & 0xFF);
            }
        }
    }

    public static void encodeStream(FileInputStream soureStream, FileOutputStream destStream) throws IOException {
        int bufferSize = 0;
        while (soureStream.available() > 0) {
            bufferSize = soureStream.available() < 2048 ? soureStream.available() : 2048;
            byte[] buffer = new byte[bufferSize];
            soureStream.read(buffer, 0, bufferSize);
            SingleFileDataCrypt.doEncodeBuffer(buffer, 0, bufferSize);
            destStream.write(buffer, 0, buffer.length);
        }
    }

    public static byte[] encodeBuffer(byte[] buffer, int startPos) {
        int bufferSize = 0;
        for (int tmpPos = startPos; tmpPos < buffer.length; tmpPos += bufferSize) {
            bufferSize = buffer.length - tmpPos < 2048 ? buffer.length - tmpPos : 2048;
            SingleFileDataCrypt.doEncodeBuffer(buffer, tmpPos, bufferSize);
        }
        return buffer;
    }

    public static void doEncodeBuffer(byte[] buffer, int startPos, int bufferSize) {
        if (bufferSize >= 4) {
            int ebx = bufferSize ^ 0x715C0D4;
            int edx = 59313618;
            for (int i = startPos; i < startPos + bufferSize; i += 4) {
                int oldValue = SingleFileDataCrypt.getInt(buffer, i);
                int newValue = (oldValue ^ edx) + ebx;
                edx += newValue;
                ebx = Integer.rotateLeft(ebx, 1);
                int temp = buffer.length - 1;
                if (temp - i < 3) continue;
                buffer[i] = (byte)(newValue & 0xFF);
                buffer[i + 1] = (byte)(newValue >> 8 & 0xFF);
                buffer[i + 2] = (byte)(newValue >> 16 & 0xFF);
                buffer[i + 3] = (byte)(newValue >> 24 & 0xFF);
            }
        }
    }

    private static int CircularShift(int number, int bits) {
        if (bits >= 0) {
            return number << bits | number >> 32 - bits;
        }
        return number >> -bits | number << 32 + bits;
    }

    public static byte rotateLeft(byte sourceByte, int n) {
        int temp = sourceByte & 0xFF;
        return (byte)(temp << n | temp >> 8 - n);
    }

    public static byte rotateRight(byte sourceByte, int n) {
        int temp = sourceByte & 0xFF;
        return (byte)(temp >> n | temp << 8 - n);
    }

    public static byte[] rotateLeft(byte[] sourceBytes, int n) {
        byte[] resultBytes = new byte[sourceBytes.length];
        for (int i = 0; i < sourceBytes.length; ++i) {
            resultBytes[i] = SingleFileDataCrypt.rotateLeft(sourceBytes[i], n);
        }
        return resultBytes;
    }

    public static byte[] rotateRight(byte[] sourceBytes, int n) {
        byte[] resultBytes = new byte[sourceBytes.length];
        for (int i = 0; i < sourceBytes.length; ++i) {
            resultBytes[i] = SingleFileDataCrypt.rotateRight(sourceBytes[i], n);
        }
        return resultBytes;
    }

    private static int getInt(byte[] buffer, int startPos) {
        int result = 0;
        int temp = buffer.length - 1;
        if (temp - startPos < 3) {
            for (int i = buffer.length - 1; i >= startPos; --i) {
                result <<= 8;
                result += buffer[i] & 0xFF;
            }
        } else {
            for (int i = startPos + 3; i >= startPos; --i) {
                result <<= 8;
                result += buffer[i] & 0xFF;
            }
        }
        return result;
    }

    public static int getDefaultBufferSize() {
        return 2048;
    }
}

