/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.bd.bill.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class MultiValueDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String groupid;
    private String masterid;
    private String bindingid;
    private String bindingvalue;
    private String ordernum;
    private String srcTablename;
    private String tableName;

    public String getSrcTablename() {
        return this.srcTablename;
    }

    public void setSrcTablename(String srcTablename) {
        this.srcTablename = srcTablename;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupid() {
        return this.groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMasterid() {
        return this.masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getBindingid() {
        return this.bindingid;
    }

    public void setBindingid(String bindingid) {
        this.bindingid = bindingid;
    }

    public String getBindingvalue() {
        return this.bindingvalue;
    }

    public void setBindingvalue(String bindingvalue) {
        this.bindingvalue = bindingvalue;
    }

    public String getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.bindingvalue == null ? 0 : this.bindingvalue.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        MultiValueDTO other = (MultiValueDTO)((Object)obj);
        return !(this.bindingvalue == null ? other.bindingvalue != null : !this.bindingvalue.equals(other.bindingvalue));
    }
}

