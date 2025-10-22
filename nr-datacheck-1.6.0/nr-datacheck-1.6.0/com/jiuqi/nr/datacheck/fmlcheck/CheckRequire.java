/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.fmlcheck;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Optional;

public enum CheckRequire implements Externalizable
{
    IGNORE(1, "\u53ef\u5ffd\u7565"),
    WRITE_DES(2, "\u9700\u586b\u5199\u8bf4\u660e"),
    MUST_CHECK_PASS(3, "\u5fc5\u987b\u5ba1\u6838\u901a\u8fc7");

    private int code;
    private String des;

    private CheckRequire(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public static CheckRequire getByCode(int code) {
        Optional<CheckRequire> any = Arrays.stream(CheckRequire.class.getEnumConstants()).filter(e -> code == e.getCode()).findAny();
        return any.orElse(null);
    }

    public int getCode() {
        return this.code;
    }

    public String getDes() {
        return this.des;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.code);
        out.writeObject(this.des);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.code = in.readInt();
        this.des = (String)in.readObject();
    }
}

