/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.para.util.WriteUtil;
import java.io.IOException;

public class FontDataClass {
    private String privateName;
    private int privateColor;
    private byte privateCharSet;
    private int privateHeight;
    private byte privateStyles;

    public final String getName() {
        return this.privateName;
    }

    public final void setName(String value) {
        this.privateName = value;
    }

    public final int getColor() {
        return this.privateColor;
    }

    public final void setColor(int value) {
        this.privateColor = value;
    }

    public final byte getCharSet() {
        return this.privateCharSet;
    }

    public final void setCharSet(byte value) {
        this.privateCharSet = value;
    }

    public final int getHeight() {
        return this.privateHeight;
    }

    public final void setHeight(int value) {
        this.privateHeight = value;
    }

    public final byte getStyles() {
        return this.privateStyles;
    }

    public final void setStyles(byte value) {
        this.privateStyles = value;
    }

    public final void loadFromStrea(Stream stream) throws IOException, StreamException {
        this.setName(ReadUtil.readStreams(stream));
        this.setCharSet(ReadUtil.readByteValue(stream));
        this.setHeight(ReadUtil.readIntValue(stream));
        this.setStyles(ReadUtil.readByteValue(stream));
        this.setColor(ReadUtil.readIntValue(stream));
    }

    public final void writeToStream(Stream stream) throws IOException, StreamException {
        WriteUtil.writeStreams(stream, this.getName());
        WriteUtil.writeByteValue(stream, this.getCharSet());
        WriteUtil.writeIntValue(stream, this.getHeight());
        WriteUtil.writeByteValue(stream, this.getStyles());
        WriteUtil.writeIntValue(stream, this.getColor());
    }
}

