/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public enum AutoCalStrategy implements Externalizable
{
    FIND_FML("\u6839\u636e\u5f53\u524d\u8868\u7684\u6307\u6807\u641c\u7d22\u76f8\u5173\u8054\u7684\u516c\u5f0f"),
    BJ_FORM("\u8868\u95f4\u5206\u7ec4\u52a0\u5f53\u524d\u8868\u7684\u516c\u5f0f"),
    FORM("\u5f53\u524d\u8868\u7684\u516c\u5f0f"),
    ALL("\u5f53\u524d\u516c\u5f0f\u65b9\u6848\u4e0b\u6240\u6709\u516c\u5f0f"),
    EXCEPTION("\u629b\u51fa\u5f02\u5e38");

    private String desc;

    private AutoCalStrategy(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.desc);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.desc = (String)in.readObject();
    }
}

