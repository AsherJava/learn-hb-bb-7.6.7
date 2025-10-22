/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.ini;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.IOException;

public class EncodingType {
    public static String GetType(String FILE_NAME) throws StreamException, IOException {
        MemStream fs = new MemStream();
        fs.loadFromFile(FILE_NAME);
        fs.seek(0L, 0);
        String r = EncodingType.GetType((Stream)fs);
        return r;
    }

    public static String GetType(Stream fs) throws StreamException {
        String reVal = "GB2312";
        int size = (int)fs.getSize();
        byte[] ss = new byte[size];
        fs.read(ss, 0, size);
        reVal = EncodingType.GetType(ss);
        return reVal;
    }

    public static String GetType(byte[] ss) {
        String reVal = "GB2312";
        if (EncodingType.IsUTF8Bytes(ss) || (0xFF & ss[0]) == 239 && (0xFF & ss[1]) == 187 && (0xFF & ss[2]) == 191) {
            reVal = "UTF8";
        } else if ((0xFF & ss[0]) == 254 && (0xFF & ss[1]) == 255 && (0xFF & ss[2]) == 0) {
            reVal = "BigEndianUnicode";
        } else if ((0xFF & ss[0]) == 255 && (0xFF & ss[1]) == 254 && (0xFF & ss[2]) == 65) {
            reVal = "Unicode";
        }
        return reVal;
    }

    private static boolean IsUTF8Bytes(byte[] data) {
        int charByteCounter = 1;
        for (int i = 0; i < data.length; ++i) {
            int curByte = data[i];
            if (charByteCounter == 1) {
                if (curByte < 0) {
                    curByte = 256 + curByte;
                }
                if (curByte < 128) continue;
                while (((curByte <<= 1) & 0x80) != 0) {
                    ++charByteCounter;
                }
                if (charByteCounter != 1 && charByteCounter <= 6) continue;
                return false;
            }
            if ((curByte & 0xC0) != 128) {
                return false;
            }
            --charByteCounter;
        }
        if (charByteCounter > 1) {
            throw new RuntimeException("\u975e\u9884\u671f\u7684byte\u683c\u5f0f");
        }
        return true;
    }
}

