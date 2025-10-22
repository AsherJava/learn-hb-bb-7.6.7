/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamException;

public abstract class IniBuffer {
    private int writeCount = 0;
    private int readCount = 0;

    public abstract int getEntry();

    public abstract void writeEntry();

    public abstract boolean gotoEntry(boolean var1);

    public abstract void gotoCursor();

    public abstract void writeInfoBoolean(boolean var1);

    public abstract void writeInfoDouble(double var1);

    public abstract void writeInfoInteger(int var1);

    public abstract void writeInfoLong(long var1);

    public abstract void writeInfoString(String var1);

    public abstract void writeInfoByte(byte var1);

    public abstract boolean readInfoBoolean();

    public abstract double readInfoDouble();

    public abstract int readInfoInteger();

    public abstract long readInfoLong();

    public abstract String readInfoString();

    public abstract byte readInfoByte();

    public abstract int writeBoolean(boolean var1);

    public abstract int writeDouble(double var1);

    public abstract int writeInteger(int var1);

    public abstract int writeLong(long var1);

    public abstract int writeString(String var1);

    public abstract boolean readBoolean(int var1);

    public abstract double readDouble(int var1);

    public abstract int readInteger(int var1);

    public abstract long readLong(int var1);

    public abstract String readString(int var1);

    public abstract int writeStream(Stream var1, int var2);

    public abstract void readStream(Stream var1, int var2, int var3);

    public abstract void loadFromStream(Stream var1);

    public abstract void replaceStream(Stream var1);

    public abstract void saveToStream(Stream var1);

    public final boolean verify() {
        return this.gotoEntry(true);
    }

    public void beginWrite() {
        if (this.writeCount == 0) {
            this.gotoCursor();
        }
        ++this.writeCount;
    }

    public boolean writeing() {
        return this.writeCount > 0;
    }

    public void endWrite() {
        --this.writeCount;
    }

    public void beginRead() {
        ++this.readCount;
    }

    public boolean reading() {
        return this.readCount > 0;
    }

    public void endRead() {
        --this.readCount;
    }

    public void loadFromFile(String fileName) throws StreamException, IOException {
        MemStream stream = new MemStream();
        stream.setPosition(0L);
        stream.loadFromFile(fileName);
        this.loadFromStream(stream);
    }

    public void saveToFile(String fileName) throws StreamException, IOException {
        MemStream stream = new MemStream();
        this.saveToStream(stream);
        stream.saveToFile(fileName);
    }

    public void loadFromStream(InputStream inStream) throws StreamException, IOException {
        MemStream stream = new MemStream();
        stream.setPosition(0L);
        stream.loadFromStream(inStream);
        this.loadFromStream(stream);
    }

    public void saveToStream(OutputStream outStream) throws StreamException, IOException {
        MemStream stream = new MemStream();
        this.saveToStream(stream);
        stream.saveToStream(outStream);
    }
}

