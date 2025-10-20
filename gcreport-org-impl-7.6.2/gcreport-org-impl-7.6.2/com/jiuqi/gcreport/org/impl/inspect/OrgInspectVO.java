/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;

public class OrgInspectVO
extends HashMap<String, Object> {
    private Date validtime;
    private Date invalidtime;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    public Date getValidtime() {
        return this.validtime;
    }

    public void setValidtime(Date validtime) {
        this.validtime = validtime;
    }

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    public Date getInvalidtime() {
        return this.invalidtime;
    }

    public void setInvalidtime(Date invalidtime) {
        this.invalidtime = invalidtime;
    }
}

