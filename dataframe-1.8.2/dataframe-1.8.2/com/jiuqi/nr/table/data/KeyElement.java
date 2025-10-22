/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.DataType;
import java.text.Format;

public class KeyElement {
    private String name;
    private DataType type;
    private AbstractColumnParser<?> parser;
    private Format format;

    public KeyElement(String name) {
        this(name, null, null);
    }

    public KeyElement(String name, DataType type, AbstractColumnParser<?> parser) {
        if (null == name) {
            throw new IllegalArgumentException("name \u4e0d\u53ef\u4ee5\u4e3a\u7a7a\u3002");
        }
        this.name = name;
        this.type = type;
        this.parser = parser;
    }

    public static KeyElement create(String name) {
        return new KeyElement(name);
    }

    public String name() {
        return this.name;
    }

    public DataType type() {
        return this.type;
    }

    public AbstractColumnParser<?> getParser() {
        return this.parser;
    }

    public Format getFormat() {
        return this.format;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        return this.name.equals(obj);
    }
}

