/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import nr.single.map.configurations.file.ini.BufferIni;

public class Ident {
    public static final byte IDENT_TYPE_BOOLEAN = 1;
    public static final byte IDENT_TYPE_INTEGER = 2;
    public static final byte IDENT_TYPE_DOUBLE = 3;
    public static final byte IDENT_TYPE_LONG = 4;
    public static final byte IDENT_TYPE_STRING = 5;
    public static final byte IDENT_TYPE_STREAM = 6;
    public static final byte IDENT_TYPE_INI = 7;
    private String name;
    private byte type;
    private int position;
    private BufferIni child;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BufferIni getChild() {
        return this.child;
    }

    public void setChild(BufferIni child) {
        this.child = child;
    }
}

