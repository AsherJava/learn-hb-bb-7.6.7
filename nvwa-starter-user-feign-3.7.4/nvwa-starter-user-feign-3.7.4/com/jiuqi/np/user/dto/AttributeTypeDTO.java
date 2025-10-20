/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.attr.UserAttributeExtendDisplay
 *  com.jiuqi.np.user.attr.UserAttributeType
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.attr.UserAttributeExtendDisplay;
import com.jiuqi.np.user.attr.UserAttributeType;

public class AttributeTypeDTO
implements UserAttributeType {
    private static final long serialVersionUID = 1L;
    private String key;
    private int value;
    private String desc;
    private UserAttributeExtendDisplay type;

    public AttributeTypeDTO() {
    }

    public AttributeTypeDTO(String key, int value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
        this.type = UserAttributeExtendDisplay.BASE;
    }

    public AttributeTypeDTO(String key, int value, String desc, UserAttributeExtendDisplay type) {
        this.key = key;
        this.value = value;
        this.desc = desc;
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserAttributeExtendDisplay getType() {
        return this.type;
    }

    public void setType(UserAttributeExtendDisplay type) {
        this.type = type;
    }
}

