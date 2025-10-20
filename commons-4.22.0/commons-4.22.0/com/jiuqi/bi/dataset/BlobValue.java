/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

public final class BlobValue
implements Cloneable,
Comparable<BlobValue>,
Serializable {
    private static final long serialVersionUID = 6072550246040204998L;
    private byte[] value;
    private int hash = -1;
    public static final BlobValue NULL = new BlobValue();
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public BlobValue() {
        this(null);
    }

    public BlobValue(byte[] value) {
        this.value = value;
    }

    public BlobValue(String value, String format) {
        if (value == null) {
            this.value = null;
        } else if ("HEX".equalsIgnoreCase(format)) {
            this.value = BlobValue.hexStringToBytes(value);
        } else if ("BASE64".equalsIgnoreCase(format)) {
            this.value = Base64.getDecoder().decode(value);
        } else {
            throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u5b57\u7b26\u4e32\u7f16\u7801\u683c\u5f0f\uff1a" + format);
        }
    }

    public boolean isNull() {
        return this.value == null || this.value.length == 0;
    }

    public int length() {
        return this.value == null ? 0 : this.value.length;
    }

    public byte byteAt(int index) {
        if (index < 0 || index >= this.length()) {
            throw new IndexOutOfBoundsException();
        }
        return this.value[index];
    }

    public byte[] toBytes() {
        return (byte[])this.value.clone();
    }

    public byte[] _getBytes() {
        return this.value;
    }

    public String toString() {
        return BlobValue.bytesToHexString(this.value);
    }

    public String toBase64() {
        if (this.value == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(this.value);
    }

    @Override
    public int compareTo(BlobValue o) {
        if (this.value == o.value) {
            return 0;
        }
        if (this.value == null) {
            return o.isNull() ? 0 : -1;
        }
        if (o.value == null) {
            return this.isNull() ? 0 : 1;
        }
        int c = this.value.length - o.value.length;
        if (c != 0) {
            return c;
        }
        for (int i = 0; i < this.value.length; ++i) {
            c = this.value[i] - o.value[i];
            if (c == 0) continue;
            return c;
        }
        return 0;
    }

    public Object clone() {
        try {
            BlobValue result = (BlobValue)super.clone();
            result.hash = -1;
            result.value = (byte[])this.value.clone();
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public int hashCode() {
        int h = this.hash;
        if (h == -1) {
            this.hash = h = Arrays.hashCode(this.value);
        }
        return h;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return this.isNull();
        }
        if (!(obj instanceof BlobValue)) {
            return false;
        }
        return Arrays.equals(this.value, ((BlobValue)obj).value);
    }

    public static String bytesToHexString(byte[] value) {
        if (value == null) {
            return null;
        }
        if (value.length == 0) {
            return "";
        }
        char[] buffer = new char[value.length * 2];
        for (int i = 0; i < value.length; ++i) {
            buffer[i * 2] = HEX_CHARS[value[i] >> 4 & 0xF];
            buffer[i * 2 + 1] = HEX_CHARS[value[i] & 0xF];
        }
        return new String(buffer);
    }

    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        if (s.length() % 2 != 0) {
            throw new IllegalArgumentException("\u89e3\u7801\u5b57\u7b26\u4e32\u957f\u5ea6\u9519\u8bef\uff1a" + s.length());
        }
        byte[] data = new byte[s.length() / 2];
        for (int i = 0; i < s.length() / 2; ++i) {
            int h = BlobValue.hexValueOf(s.charAt(i * 2));
            int l = BlobValue.hexValueOf(s.charAt(i * 2 + 1));
            data[i] = (byte)(h << 4 | l);
        }
        return data;
    }

    private static int hexValueOf(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - 48;
        }
        if (ch >= 'A' && ch <= 'F') {
            return ch - 65 + 10;
        }
        if (ch >= 'a' && ch <= 'f') {
            return ch - 97 + 10;
        }
        throw new IllegalArgumentException("\u65e0\u6cd5\u8bc6\u522b\u7684\u7f16\u7801\u5b57\u7b26\uff1a" + ch);
    }

    public static BlobValue merge(BlobValue ... values) {
        if (values == null || values.length == 0) {
            return NULL;
        }
        if (values.length == 1) {
            return values[0];
        }
        int len = 0;
        for (BlobValue v : values) {
            if (v == null) continue;
            len += v.length();
        }
        if (len == 0) {
            return NULL;
        }
        byte[] data = new byte[len];
        int cur = 0;
        for (BlobValue v : values) {
            if (v == null || v.isNull()) continue;
            byte[] src = v._getBytes();
            System.arraycopy(src, 0, data, cur, src.length);
            cur += src.length;
        }
        return new BlobValue(data);
    }
}

