/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 */
package com.jiuqi.va.bill.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;

public class BillDefineImpl
extends ModelDefineImpl
implements BillDefine {
    @JsonIgnore
    private transient DataDefineImpl data;
    @JsonIgnore
    private transient RulerDefineImpl ruler;
    @JsonIgnore
    private transient ViewDefineImpl view;

    public DataDefineImpl getData() {
        if (this.data == null) {
            this.data = (DataDefineImpl)this.getPlugins().get(DataDefineImpl.class);
        }
        return this.data;
    }

    public RulerDefineImpl getRuler() {
        if (this.ruler == null) {
            this.ruler = (RulerDefineImpl)this.getPlugins().get(RulerDefineImpl.class);
        }
        return this.ruler;
    }

    public ViewDefineImpl getView() {
        if (this.view == null) {
            this.view = (ViewDefineImpl)this.getPlugins().get(ViewDefineImpl.class);
        }
        return this.view;
    }
}

