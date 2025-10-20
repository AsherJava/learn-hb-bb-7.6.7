/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import java.io.Serializable;

public class BIFF2
implements Serializable {
    public short ident;
    public int size;
    private Stream2 biffData = new MemStream2();

    public void reset() throws StreamException2 {
        this.biffData.setPosition(0L);
        this.size = 0;
        this.ident = (short)127;
    }

    public Stream2 data() {
        return this.biffData;
    }

    public static void writeBIFF(int biffVer, BIFF2 b, Stream2 stream) throws StreamException2 {
        b.size = (int)b.data().getPosition();
        stream.write((byte)b.ident);
        stream.writeInt(b.size);
        stream.writeInt(0);
        stream.writeInt(0);
        stream.writeInt(0);
        stream.writeInt(0);
        if (b.size > 0) {
            b.data().setPosition(0L);
            stream.copyFrom(b.data(), (long)b.size);
        }
    }

    public static void readBIFF(int ver, BIFF2 b, Stream2 stream) throws StreamException2 {
        b.ident = stream.read();
        if (b.ident == 20) {
            b.ident = (short)20;
        }
        b.size = stream.readInt();
        stream.readInt();
        stream.readInt();
        stream.readInt();
        stream.readInt();
        if (b.size > 0) {
            b.data().setSize(b.size);
            b.data().setPosition(0L);
            b.data().copyFrom(stream, (long)b.size);
            b.data().setPosition(0L);
        }
    }
}

