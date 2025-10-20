/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class BIFF
implements Serializable {
    private static final long serialVersionUID = 6164516453681731914L;
    public short ident;
    public int size;
    private Stream biffData = new MemStream();

    private void writeInfo(OutputStream out) throws IOException {
        out.write(this.ident >> 0);
        out.write(this.ident >> 8);
        out.write(this.size >> 0);
        out.write(this.size >> 8);
        out.write(this.size >> 16);
        out.write(this.size >> 24);
    }

    public void readInfo(InputStream in) throws IOException {
        byte b1 = (byte)in.read();
        byte b2 = (byte)in.read();
        this.ident = (short)((b2 << 8) + (b1 << 0));
        b1 = (byte)in.read();
        b2 = (byte)in.read();
        byte b3 = (byte)in.read();
        byte b4 = (byte)in.read();
        this.size = (b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0);
    }

    public void reset() throws StreamException {
        this.biffData.setPosition(0L);
        this.size = 0;
        this.ident = (short)127;
    }

    public Stream data() {
        return this.biffData;
    }

    public void write(OutputStream out) throws IOException, StreamException {
        this.writeInfo(out);
        this.biffData.setPosition(0L);
        this.biffData.copyTo(out, (long)this.size);
    }

    public void read(InputStream in) throws IOException, StreamException {
        this.readInfo(in);
        this.biffData.setPosition(0L);
        this.biffData.copyFrom(in, (long)this.size);
    }

    public void write(Stream out) throws StreamException {
        out.writeShort(this.ident);
        out.writeInt(this.size);
        this.biffData.setPosition(0L);
        out.copyFrom(this.biffData, (long)this.size);
    }

    public void read(Stream in) throws StreamException {
        this.ident = in.readShort();
        this.size = in.readInt();
        this.biffData.setPosition(0L);
        this.biffData.copyFrom(in, (long)this.size);
    }

    public static void writeBIFF(int biffVer, BIFF b, Stream stream) throws StreamException {
        b.size = (int)b.data().getPosition();
        stream.write((byte)b.ident);
        if (biffVer == 1) {
            stream.writeWord(b.size);
        } else {
            stream.writeInt(b.size);
        }
        if (b.size > 0) {
            b.data().setPosition(0L);
            stream.copyFrom(b.data(), (long)b.size);
        }
    }

    public static void readBIFF(int ver, BIFF b, Stream stream) throws StreamException {
        b.ident = stream.read();
        if (b.ident == 20) {
            b.ident = (short)20;
        }
        b.size = ver == 1 ? stream.readWord() : stream.readInt();
        if (b.size > 0) {
            b.data().setSize((long)b.size);
            b.data().setPosition(0L);
            b.data().copyFrom(stream, (long)b.size);
            b.data().setPosition(0L);
        }
    }
}

