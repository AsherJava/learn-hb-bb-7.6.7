/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.BIFFReader;
import java.io.DataInput;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

public interface IBIFFInput {
    public byte sign();

    public long size();

    public DataInput data();

    public BIFFReader toReader() throws IOException;

    public int readInt() throws IOException;

    public long readLong() throws IOException;

    public boolean readBoolean() throws IOException;

    public double readDouble() throws IOException;

    public String readString() throws IOException;

    public byte[] readBytes() throws IOException;

    public Calendar readDateTime() throws IOException;

    public BigDecimal readBigDecimal() throws IOException;

    public <T extends Enum<T>> T readEnum(Class<T> var1) throws IOException;

    public void readBytes(byte[] var1, int var2, int var3) throws IOException;
}

