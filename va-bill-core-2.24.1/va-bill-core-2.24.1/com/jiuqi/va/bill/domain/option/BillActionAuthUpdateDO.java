/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.option;

import com.jiuqi.va.bill.domain.BillActionAuthDO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class BillActionAuthUpdateDO
extends TenantDO {
    private Integer authtype;
    private String definename;
    private Integer biztype;
    private String bizname;
    private List<BillActionAuthDO> data;

    public Integer getAuthtype() {
        return this.authtype;
    }

    public void setAuthtype(Integer authtype) {
        this.authtype = authtype;
    }

    public String getDefinename() {
        return this.definename;
    }

    public void setDefinename(String definename) {
        this.definename = definename;
    }

    public Integer getBiztype() {
        return this.biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public String getBizname() {
        return this.bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public List<BillActionAuthDO> getData() {
        return this.data;
    }

    public void setData(List<BillActionAuthDO> data) {
        this.data = data;
    }
}

