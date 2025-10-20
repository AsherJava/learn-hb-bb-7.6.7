/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

public final class EncodingUtil {
    private static final String UTF8 = "UTF-8";

    private EncodingUtil() {
    }

    public static byte[] convertToBytes(char[] chars, String encoding) throws CharacterCodingException {
        Charset cc = Charset.forName(encoding);
        CharsetEncoder ce = cc.newEncoder();
        ce.onUnmappableCharacter(CodingErrorAction.IGNORE);
        ByteBuffer bb = ce.encode(CharBuffer.wrap(chars));
        ((Buffer)bb).rewind();
        int lim = bb.limit();
        int offset = UTF8.equals(encoding) ? 3 : 0;
        byte[] br = new byte[lim + offset];
        if (UTF8.equals(encoding)) {
            br[0] = -17;
            br[1] = -69;
            br[2] = -65;
        }
        bb.get(br, offset, lim);
        return br;
    }

    public static String convertToString(byte[] bytes, String encoding) throws UnsupportedEncodingException {
        if (bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65 && encoding.equals(UTF8)) {
            return new String(bytes, 3, bytes.length - 3, UTF8);
        }
        return new String(bytes, encoding);
    }
}

