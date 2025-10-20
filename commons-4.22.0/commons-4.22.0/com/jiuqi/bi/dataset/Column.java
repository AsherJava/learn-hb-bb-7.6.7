/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import com.jiuqi.bi.io.FilteredObjectInputStream;
import com.jiuqi.bi.io.IBIFFOutput;
import com.jiuqi.bi.io.IBIFFSerializer;
import com.jiuqi.bi.types.DataTypes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Column<T>
implements Cloneable {
    private String name;
    private int dataType;
    private String title;
    private T info;
    private int index = -1;
    private static final int BIFF_FIRST = 10;
    private static final byte BIFF_NAME = 11;
    private static final byte BIFF_TYPE = 12;
    private static final byte BIFF_TITLE = 13;
    private static final byte BIFF_INFO = 14;
    private static final byte BIFF_INFOCLASS = 15;
    private static final byte BIFF_INFODATA = 16;

    Column() {
    }

    public Column(String name, int dataType) {
        this(name, dataType, null, null);
    }

    public Column(String name, int dataType, T info) {
        this(name, dataType, null, info);
    }

    public Column(String name, int dataType, String title, T info) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("\u5b57\u6bb5\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        this.name = name;
        this.dataType = dataType;
        this.title = title;
        this.info = info;
    }

    public String getName() {
        return this.name;
    }

    public int getDataType() {
        return this.dataType;
    }

    public String getTitle() {
        return this.title;
    }

    public T getInfo() {
        return this.info;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public int getIndex() {
        return this.index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    public Column<T> clone() {
        try {
            Column result = (Column)super.clone();
            result.index = -1;
            return result;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    void save(BIFFWriter writer) throws IOException {
        writer.write((byte)11, this.name);
        writer.write((byte)12, this.dataType);
        writer.write((byte)13, this.title);
        if (this.info instanceof IBIFFSerializer) {
            writer.write((byte)15, this.info.getClass().getName());
            try (IBIFFOutput out = writer.add((byte)16);){
                ((IBIFFSerializer)this.info).save(out.toWriter());
            }
        } else if (this.info instanceof Serializable) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(buffer);
            output.writeObject(this.info);
            writer.write((byte)14, buffer.toByteArray());
        } else {
            writer.write((byte)14);
        }
    }

    void load(BIFFReader reader) throws IOException {
        this.load(reader, null);
    }

    /*
     * Unable to fully structure code
     */
    void load(BIFFReader reader, Class<?> infoClass) throws IOException {
        infoClassName = null;
        block22: while (reader.hasNext()) {
            in = reader.next();
            switch (in.sign()) {
                case 11: {
                    this.name = in.readString();
                    break;
                }
                case 12: {
                    this.dataType = in.readInt();
                    break;
                }
                case 13: {
                    this.title = in.readString();
                    break;
                }
                case 14: {
                    if (in.size() == 0L) ** GOTO lbl52
                    data = in.readBytes();
                    try {
                        input = new FilteredObjectInputStream(new ByteArrayInputStream(data));
                        var7_10 = null;
                        if (infoClass != null) {
                            input.filterClass(infoClass);
                        }
                        this.info = input.readObject();
                        if (input == null) continue block22;
                        if (var7_10 == null) ** GOTO lbl32
                        try {
                            input.close();
                        }
                        catch (Throwable var8_11) {
                            var7_10.addSuppressed(var8_11);
                        }
                        continue block22;
lbl32:
                        // 1 sources

                        input.close();
                        ** break;
                        catch (Throwable var8_12) {
                            try {
                                var7_10 = var8_12;
                                throw var8_12;
                            }
                            catch (Throwable var9_13) {
                                if (input != null) {
                                    if (var7_10 != null) {
                                        try {
                                            input.close();
                                        }
                                        catch (Throwable var10_14) {
                                            var7_10.addSuppressed(var10_14);
                                        }
                                    } else {
                                        input.close();
                                    }
                                }
                                throw var9_13;
lbl49:
                                // 1 sources

                                break;
                            }
                        }
                    }
                    catch (ClassNotFoundException e) {
                        throw new IOException(e);
                    }
lbl52:
                    // 1 sources

                    this.info = null;
                    break;
                }
                case 15: {
                    infoClassName = in.readString();
                    break;
                }
                case 16: {
                    if (infoClassName == null) {
                        throw new IOException("\u5143\u6570\u636e\u5217\u4fe1\u606f\u5b58\u50a8\u683c\u5f0f\u9519\u8bef\uff0c\u672a\u5b58\u50a8\u9644\u52a0\u5c5e\u6027\u7c7b\u4fe1\u606f");
                    }
                    if (infoClass != null && infoClassName.equals(infoClass.getName())) {
                        klass = infoClass;
                    } else {
                        try {
                            klass = Class.forName(infoClassName);
                        }
                        catch (ClassNotFoundException e) {
                            throw new IOException(e);
                        }
                    }
                    try {
                        this.info = klass.newInstance();
                    }
                    catch (IllegalAccessException | InstantiationException e) {
                        throw new IOException(e);
                    }
                    ((IBIFFSerializer)this.info).load(in.toReader());
                }
            }
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(').append(this.name).append(',').append(DataTypes.dataTypeToString((int)this.dataType)).append(',').append(this.title).append(',').append(this.info).append(')');
        return buffer.toString();
    }
}

