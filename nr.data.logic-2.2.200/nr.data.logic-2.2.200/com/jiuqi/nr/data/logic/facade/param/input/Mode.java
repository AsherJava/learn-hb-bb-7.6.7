/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.bi.util.StringUtils;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Optional;

public enum Mode implements Externalizable
{
    FORM("form", "\u62a5\u8868"),
    FORMULA("formula", "\u516c\u5f0f");

    private String key;
    private String value;

    public static Mode getByKey(String key) {
        if (StringUtils.isEmpty((String)key)) {
            return FORM;
        }
        Optional<Mode> any = Arrays.stream(Mode.class.getEnumConstants()).filter(e -> e.getKey().equals(key)).findAny();
        return any.orElse(FORM);
    }

    private Mode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.key);
        out.writeObject(this.value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.key = (String)in.readObject();
        this.value = (String)in.readObject();
    }
}

