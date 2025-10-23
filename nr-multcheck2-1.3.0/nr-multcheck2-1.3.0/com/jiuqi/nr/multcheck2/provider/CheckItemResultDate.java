/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import java.util.Date;

public class CheckItemResultDate
extends CheckItemResult {
    private Date begin;
    private Date end;

    public Date getBegin() {
        return this.begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

