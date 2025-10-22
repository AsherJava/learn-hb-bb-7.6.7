/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Optional;

public enum DesCheckState implements Externalizable
{
    DEFAULT(0, "\u9ed8\u8ba4"),
    PASS(1, "\u68c0\u67e5\u901a\u8fc7"),
    FAIL(2, "\u68c0\u67e5\u4e0d\u901a\u8fc7");

    private int code;
    private String des;

    private DesCheckState(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public static DesCheckState getByCode(int code) {
        Optional<DesCheckState> any = Arrays.stream(DesCheckState.class.getEnumConstants()).filter(e -> code == e.getCode()).findAny();
        return any.orElse(DEFAULT);
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

