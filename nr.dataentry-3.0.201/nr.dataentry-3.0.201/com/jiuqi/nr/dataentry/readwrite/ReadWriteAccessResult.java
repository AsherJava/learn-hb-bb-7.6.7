/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.nr.dataentry.util.Consts;
import java.util.Map;

public class ReadWriteAccessResult {
    private Map<String, Object> status;
    private Boolean readable;
    private Boolean writeable;
    private Consts.ReadWriteAccessLevel type;
    private String name;
    private String unreadableDesc;
    private String unwriteableDesc;

    public Map<String, Object> getStatus() {
        return this.status;
    }

    public void setStatus(Map<String, Object> status) {
        this.status = status;
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

    public Consts.ReadWriteAccessLevel getType() {
        return this.type;
    }

    public void setType(Consts.ReadWriteAccessLevel type) {
        this.type = type;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

