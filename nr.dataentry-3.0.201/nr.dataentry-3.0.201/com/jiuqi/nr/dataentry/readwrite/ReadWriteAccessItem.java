/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.nr.dataentry.util.Consts;

public class ReadWriteAccessItem {
    private String name;
    private Boolean readable;
    private Boolean writeable;
    private Consts.ReadWriteAccessLevel type;
    private Object params;
    private Boolean IsReal;
    private String unreadableDesc;
    private String unwriteableDesc;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Consts.ReadWriteAccessLevel getType() {
        return this.type;
    }

    public void setType(Consts.ReadWriteAccessLevel type) {
        this.type = type;
    }

    public Object getParams() {
        return this.params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Boolean getReadable() {
        return this.readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public Boolean getWriteable() {
        return this.writeable;
    }

    public void setWriteable(Boolean writeable) {
        this.writeable = writeable;
    }

    public Boolean getIsReal() {
        return this.IsReal;
    }

    public void setIsReal(Boolean isReal) {
        this.IsReal = isReal;
    }

    public String getUnreadableDesc() {
        return this.unreadableDesc;
    }

    public void setUnreadableDesc(String unreadableDesc) {
        this.unreadableDesc = unreadableDesc;
    }

    public String getUnwriteableDesc() {
        return this.unwriteableDesc;
    }

    public void setUnwriteableDesc(String unwriteableDesc) {
        this.unwriteableDesc = unwriteableDesc;
    }
}

