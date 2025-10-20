/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.BIFFErrorException;
import com.jiuqi.bi.io.FilteredObjectInputStream;
import com.jiuqi.bi.io.GuardedInputStream;
import com.jiuqi.bi.io.IBIFFInput;
import com.jiuqi.bi.logging.LogManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;

public final class BIFFReader {
    private DataInput input;
    private BIFFInput biff;
    private GuardedInputStream in;

    public BIFFReader(InputStream inStream) throws IOException {
        this.in = new GuardedInputStream(inStream);
        this.input = new DataInputStream(this.in);
        this.biff = new BIFFInput(-1);
    }

    public boolean hasNext() {
        return this.biff.sign() != 0;
    }

    public IBIFFInput next() throws IOException, BIFFErrorException {
        long delta = this.in.popGuard();
        if (delta > 0L) {
            this.input.skipBytes((int)delta);
        }
        this.biff.setSign(this.input.readByte());
        long biffSize = BIFFReader.readSize(this.input);
        this.in.pushGuard(biffSize);
        this.biff.setSize(biffSize);
        switch (this.biff.sign()) {
            case -2: {
                String msg;
                try {
                    msg = this.biff.readString();
                }
                catch (IOException e) {
                    msg = null;
                }
                if (msg == null || msg.length() == 0) {
                    msg = "\u6d41\u6570\u636e\u52a0\u8f7d\u9519\u8bef";
                }
                throw new BIFFErrorException(msg);
            }
            case -3: {
                Throwable cause;
                byte[] data = this.biff.readBytes();
                try (FilteredObjectInputStream reader = new FilteredObjectInputStream(new ByteArrayInputStream(data));){
                    cause = reader.readObject(Throwable.class);
                }
                catch (Exception e) {
                    LogManager.getLogger(this.getClass()).error("", e);
                    cause = null;
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                throw cause == null ? new BIFFErrorException("\u6d41\u6570\u636e\u52a0\u8f7d\u9519\u8bef") : new BIFFErrorException(cause.getMessage(), cause);
            }
        }
        return this.biff;
    }

    public static long readSize(DataInput input) throws IOException {
        short shortSize = input.readShort();
        if (shortSize != Short.MAX_VALUE) {
            return shortSize;
        }
        int intSize = input.readInt();
        if (intSize != Integer.MAX_VALUE) {
            return intSize;
        }
        return input.readLong();
    }

    public static int readIntSize(DataInput input) throws IOException {
        short shortSize = input.readShort();
        if (shortSize != Short.MAX_VALUE) {
            return shortSize;
        }
        int intSize = input.readInt();
        if (intSize != Integer.MAX_VALUE) {
            return intSize;
        }
        long longSize = input.readLong();
        if (longSize == Integer.MAX_VALUE || longSize == Integer.MIN_VALUE) {
            return (int)longSize;
        }
        throw new IOException("\u8bfb\u53d6\u957f\u5ea6\u503c\u8d8a\u754c\uff1a" + longSize);
    }

    public static String readString(DataInput input) throws IOException {
        long len = BIFFReader.readSize(input);
        return BIFFReader.readString(input, (int)len);
    }

    public static String readString(DataInput input, int len) throws IOException {
        if (len == 0) {
            return null;
        }
        byte[] data = new byte[len];
        input.readFully(data);
        return new String(data, "UTF-8");
    }

    public static String readNullEndingString(DataInput input) throws IOException {
        byte v;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while ((v = input.readByte()) != 0) {
            buffer.write(v);
        }
        return buffer.size() == 0 ? null : new String(buffer.toByteArray(), "UTF-8");
    }

    public static Calendar readCalendar(DataInput input) throws IOException {
        long value = input.readLong();
        if (value == -1L) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(value);
        return date;
    }

    public static BigDecimal readBigDecimal(DataInput input) throws IOException {
        String value = BIFFReader.readString(input);
        return value == null ? null : new BigDecimal(value);
    }

    private final class BIFFInput
    implements IBIFFInput {
        private byte sign;
        private long size;

        public BIFFInput(byte sign) {
            this.sign = sign;
        }

        @Override
        public DataInput data() {
            return BIFFReader.this.input;
        }

        @Override
        public BIFFReader toReader() throws IOException {
            return new BIFFReader(BIFFReader.this.in);
        }

        @Override
        public byte sign() {
            return this.sign;
        }

        @Override
        public long size() {
            return this.size;
        }

        public void setSign(byte sign) {
            this.sign = sign;
        }

        public void setSize(long size) {
            this.size = size;
        }

        @Override
        public boolean readBoolean() throws IOException {
            return BIFFReader.this.input.readBoolean();
        }

        @Override
        public byte[] readBytes() throws IOException {
            if (this.size == 0L) {
                return null;
            }
            byte[] data = new byte[(int)this.size];
            BIFFReader.this.input.readFully(data);
            return data;
        }

        @Override
        public void readBytes(byte[] value, int off, int len) throws IOException {
            BIFFReader.this.input.readFully(value, off, len);
        }

        @Override
        public double readDouble() throws IOException {
            return BIFFReader.this.input.readDouble();
        }

        @Override
        public int readInt() throws IOException {
            return BIFFReader.this.input.readInt();
        }

        @Override
        public long readLong() throws IOException {
            return BIFFReader.this.input.readLong();
        }

        @Override
        public String readString() throws IOException {
            byte[] data = this.readBytes();
            return data == null ? null : new String(data, "UTF-8");
        }

        @Override
        public BigDecimal readBigDecimal() throws IOException {
            String val = this.readString();
            return val == null || val.length() == 0 ? null : new BigDecimal(val);
        }

        @Override
        public Calendar readDateTime() throws IOException {
            if (this.size == 0L) {
                return null;
            }
            long val = this.readLong();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(val);
            return cal;
        }

        @Override
        public <T extends Enum<T>> T readEnum(Class<T> enumType) throws IOException {
            String val = this.readString();
            if (val == null || val.isEmpty()) {
                return null;
            }
            return Enum.valueOf(enumType, val);
        }
    }
}

