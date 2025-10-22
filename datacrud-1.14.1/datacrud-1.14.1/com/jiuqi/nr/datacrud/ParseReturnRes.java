/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.ReturnRes;

public class ParseReturnRes
extends ReturnRes {
    private AbstractData abstractData;

    public AbstractData getAbstractData() {
        return this.abstractData;
    }

    public void setAbstractData(AbstractData abstractData) {
        this.abstractData = abstractData;
    }

    public ParseReturnRes() {
    }

    public ParseReturnRes(ReturnRes res) {
        this.code = res.code;
        this.message = res.message;
        this.data = res.data;
        this.messages = res.messages;
    }

    public static ParseReturnRes build(int code) {
        ParseReturnRes res = new ParseReturnRes();
        res.setCode(code);
        return res;
    }
}

