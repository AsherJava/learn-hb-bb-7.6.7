/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.IBIFFOutput;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;

public class BIFFWriter {
    private DataOutput output;
    private BIFFOutput biff;

    public BIFFWriter(OutputStream outStream) {
        this.output = new DataOutputStream(outStream);
        this.biff = new BIFFOutput();
    }

    public IBIFFOutput add(byte sign) {
        this.biff.reset(sign);
        return this.biff;
    }

    public void write(byte sign) throws IOException {
        this.biff.reset(sign);
        this.biff.close();
    }

    public void write(byte sign, byte[] value) throws IOException {
        this.biff.reset(sign);
        if (value != null && value.length != 0) {
            this.biff.data().write(value);
        }
        this.biff.close();
    }

    public void write(byte sign, byte[] value, int off, int len) throws IOException {
        this.biff.reset(sign);
        this.biff.data().write(value, off, len);
        this.biff.close();
    }

    public void write(byte sign, int value) throws IOException {
        this.biff.reset(sign);
        this.biff.data().writeInt(value);
        this.biff.close();
    }

    public void write(byte sign, long value) throws IOException {
        this.biff.reset(sign);
        this.biff.data().writeLong(value);
        this.biff.close();
    }

    public void write(byte sign, boolean value) throws IOException {
        this.biff.reset(sign);
        this.biff.data().writeBoolean(value);
        this.biff.close();
    }

    public void write(byte sign, double value) throws IOException {
        this.biff.reset(sign);
        this.biff.data().writeDouble(value);
        this.biff.close();
    }

    public void write(byte sign, String value) throws IOException {
        this.biff.reset(sign);
        if (value != null) {
            this.biff.data().write(value.getBytes("UTF-8"));
        }
        this.biff.close();
    }

    public void write(byte sign, BigDecimal value) throws IOException {
        this.write(sign, value == null ? null : value.toString());
    }

    public void write(byte sign, Calendar value) throws IOException {
        if (value == null) {
            this.write(sign);
        } else {
            this.write(sign, value.getTimeInMillis());
        }
    }

    public <T extends Enum<T>> void write(byte sign, T value) throws IOException {
        if (value == null) {
            this.write(sign);
        } else {
            this.write(sign, value.toString());
        }
    }

    public void writeError(String message) throws IOException {
        this.write((byte)-2, message);
    }

    public void writeError(Throwable error) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();){
            try (ObjectOutputStream objOut = new ObjectOutputStream(output);){
                objOut.writeObject(error);
            }
            this.write((byte)-3, output.toByteArray());
        }
        this.writeError(error.getMessage());
    }

    public void close() throws IOException {
        this.write((byte)0);
    }

    public static void writeSize(DataOutput output, long size) throws IOException {
        if (size > -32768L && size < 32767L) {
            output.writeShort((short)size);
        } else {
            output.writeShort(Short.MAX_VALUE);
            if (size > Integer.MIN_VALUE && size < Integer.MAX_VALUE) {
                output.writeInt((int)size);
            } else {
                output.writeInt(Integer.MAX_VALUE);
                output.writeLong(size);
            }
        }
    }

    public static void writeSize(DataOutput output, int size) throws IOException {
        if (size > Short.MIN_VALUE && size < Short.MAX_VALUE) {
            output.writeShort((short)size);
        } else {
            output.writeShort(Short.MAX_VALUE);
            if (size == Integer.MAX_VALUE || size == Integer.MIN_VALUE) {
                output.writeInt(Integer.MAX_VALUE);
                output.writeLong(size);
            } else {
                output.writeInt(size);
            }
        }
    }

    public static void writeString(DataOutput output, String value) throws IOException {
        if (value == null || value.length() == 0) {
            BIFFWriter.writeSize(output, 0);
            return;
        }
        byte[] data = value.getBytes("UTF-8");
        BIFFWriter.writeSize(output, data.length);
        output.write(data);
    }

    public static void writeNullEndingString(DataOutput output, String value) throws IOException {
        if (value != null && value.length() > 0) {
            byte[] data = value.getBytes("UTF-8");
            output.write(data);
        }
        output.writeByte(0);
    }

    public static void writeCalendar(DataOutput output, Calendar value) throws IOException {
        if (value == null) {
            output.writeLong(-1L);
            return;
        }
        output.writeLong(value.getTimeInMillis());
    }

    public static void writeBigDecimal(DataOutput output, BigDecimal value) throws IOException {
        BIFFWriter.writeString(output, value == null ? null : value.toString());
    }

    private static class SubWriter
    extends BIFFWriter {
        private BIFFOutput owner;

        public SubWriter(OutputStream buffer, BIFFOutput owner) {
            super(buffer);
            this.owner = owner;
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.owner.subWriter = null;
        }
    }

    private final class BIFFOutput
    implements IBIFFOutput {
        private byte sign;
        private DataOutput writer;
        private SubWriter subWriter;
        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        public BIFFOutput() {
            this.writer = new DataOutputStream(this.buffer);
        }

        @Override
        public void close() throws IOException {
            SubWriter w = this.subWriter;
            if (w != null) {
                w.close();
            }
            BIFFWriter.this.output.writeByte(this.sign);
            BIFFWriter.writeSize(BIFFWriter.this.output, this.buffer.size());
            if (this.buffer.size() > 0) {
                BIFFWriter.this.output.write(this.buffer.toByteArray());
            }
        }

        @Override
        public DataOutput data() {
            if (this.subWriter != null) {
                throw new IllegalAccessError("BIFF\u5d4c\u5957\u6a21\u5f0f\u4e0b\u7981\u6b62\u76f4\u63a5\u64cd\u4f5c\u6570\u636e\uff01");
            }
            return this.writer;
        }

        public void reset(byte sign) {
            this.sign = sign;
            this.subWriter = null;
            this.buffer.reset();
        }

        @Override
        public BIFFWriter toWriter() throws IOException {
            if (this.subWriter == null) {
                this.subWriter = new SubWriter(this.buffer, this);
            }
            return this.subWriter;
        }
    }
}

