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

public enum GroupType implements Externalizable
{
    unit("unit", "\u5355\u4f4d"),
    UNIT_FORM("unit_form", "\u5355\u4f4d_\u62a5\u8868"),
    form_formula("form_formula", "\u62a5\u8868_\u516c\u5f0f"),
    formula("formula", "\u516c\u5f0f"),
    checktype_unit("checktype_unit", "\u5ba1\u6838\u7c7b\u578b_\u5355\u4f4d"),
    UNIT_CHECKTYPE("unit_checktype", "\u5355\u4f4d_\u5ba1\u6838\u7c7b\u578b"),
    checktype_form("checktype_form", "\u5ba1\u6838\u7c7b\u578b_\u62a5\u8868");

    private String key;
    private String value;

    private GroupType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static GroupType getByKey(String key) {
        if (StringUtils.isEmpty((String)key)) {
            return unit;
        }
        Optional<GroupType> any = Arrays.stream(GroupType.class.getEnumConstants()).filter(e -> e.getKey().equals(key)).findAny();
        return any.orElse(unit);
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

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}

