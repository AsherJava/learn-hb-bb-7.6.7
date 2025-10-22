/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StreamIniBuffer
extends IniBuffer {
    private static final Logger logger = LoggerFactory.getLogger(StreamIniBuffer.class);
    private Stream stream;
    private int cursor;

    public StreamIniBuffer(Stream aStream) {
        this.stream = aStream;
    }

    @Override
    public int getEntry() {
        return this.cursor;
    }

    @Override
    public void gotoCursor() {
        try {
            this.stream.setPosition(this.cursor);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeEntry() {
        try {
            this.stream.setPosition(this.stream.getSize());
            this.stream.writeInt(this.cursor);
            this.stream.writeString("BINI");
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean gotoEntry(boolean isRead) {
        boolean ret = false;
        try {
            if (isRead) {
                this.cursor = 0;
                if (this.stream.getSize() < 8L) {
                    return ret;
                }
                this.stream.setPosition(this.stream.getSize() - 4L);
                String s = this.stream.readString(4);
                if (s.equals("BINI")) {
                    this.stream.setPosition(this.stream.getSize() - 8L);
                    this.cursor = this.stream.readInt();
                    this.stream.setPosition(this.cursor);
                    return true;
                }
            } else {
                this.stream.setPosition(this.cursor);
                ret = true;
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public void writeInfoBoolean(boolean value) {
        try {
            this.stream.writeBool(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeInfoDouble(double value) {
        try {
            this.stream.writeDouble(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeInfoInteger(int value) {
        try {
            this.stream.writeInt(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeInfoLong(long value) {
        try {
            this.stream.writeLong(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeInfoString(String value) {
        try {
            if (value == null) {
                this.stream.write((byte)0);
                return;
            }
            byte[] b = this.stream.encodeString(value);
            byte sl = (byte)b.length;
            this.stream.write(sl);
            this.stream.write(b, 0, b.length);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void writeInfoByte(byte value) {
        try {
            this.stream.write(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean readInfoBoolean() {
        boolean ret = false;
        try {
            ret = this.stream.readBool();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public double readInfoDouble() {
        double ret = 0.0;
        try {
            ret = this.stream.readDouble();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int readInfoInteger() {
        int ret = 0;
        try {
            ret = this.stream.readInt();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public long readInfoLong() {
        long ret = 0L;
        try {
            ret = this.stream.readLong();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public String readInfoString() {
        String ret = null;
        try {
            byte sl = this.stream.read();
            ret = this.stream.readString(sl);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public byte readInfoByte() {
        byte ret = 0;
        try {
            ret = this.stream.read();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeBoolean(boolean value) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.writeBool(value);
            ++this.cursor;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeDouble(double value) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.writeDouble(value);
            this.cursor = (int)this.stream.getPosition();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeInteger(int value) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.writeInt(value);
            this.cursor = (int)this.stream.getPosition();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeLong(long value) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.writeLong(value);
            this.cursor = (int)this.stream.getPosition();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeString(String value) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.writeStringWithSize(value);
            this.cursor = (int)this.stream.getPosition();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public boolean readBoolean(int position) {
        boolean ret = false;
        try {
            this.stream.setPosition(position);
            ret = this.stream.readBool();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public double readDouble(int position) {
        double ret = 0.0;
        try {
            this.stream.setPosition(position);
            ret = this.stream.readDouble();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int readInteger(int position) {
        int ret = 0;
        try {
            this.stream.setPosition(position);
            ret = this.stream.readInt();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public long readLong(int position) {
        long ret = 0L;
        try {
            this.stream.setPosition(position);
            ret = this.stream.readLong();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public String readString(int position) {
        String ret = null;
        try {
            this.stream.setPosition(position);
            int sl = this.stream.readInt();
            ret = this.stream.readString(sl);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public int writeStream(Stream aStream, int size) {
        int ret = this.cursor;
        try {
            this.stream.setPosition(this.cursor);
            this.stream.copyFrom(aStream, (long)size);
            this.cursor = (int)this.stream.getPosition();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return ret;
    }

    @Override
    public void readStream(Stream aStream, int position, int size) {
        try {
            this.stream.setPosition(position);
            aStream.copyFrom(this.stream, (long)size);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void loadFromStream(Stream aStream) {
        try {
            this.stream.setSize(aStream.getSize());
            this.stream.setPosition(0L);
            this.stream.copyFrom(aStream, 0L);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void replaceStream(Stream aStream) {
        this.stream = aStream;
    }

    @Override
    public void saveToStream(Stream aStream) {
        try {
            aStream.setSize(this.stream.getSize());
            aStream.setPosition(0L);
            aStream.copyFrom(this.stream, 0L);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean verify(Stream stream) {
        boolean bl;
        long origin = stream.getPosition();
        try {
            StreamIniBuffer buffer = new StreamIniBuffer(stream);
            bl = buffer.verify();
        }
        catch (Throwable throwable) {
            try {
                stream.setPosition(origin);
                throw throwable;
            }
            catch (StreamException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        stream.setPosition(origin);
        return bl;
    }
}

