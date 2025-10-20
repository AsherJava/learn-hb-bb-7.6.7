/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 */
package com.jiuqi.dc.base.impl.assistdim.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;

public class AssistDimItemDO
extends TenantDO {
    public static final String TABLENAME = "MD_ASSISTDIMITEM";
    private static final long serialVersionUID = 4946490917861087572L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="CODE")
    private String code;
    @Column(name="EFFECTTABLE")
    private String effectTable;

    public AssistDimItemDO() {
    }

    public AssistDimItemDO(String code) {
        this.code = code;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffectTable() {
        return this.effectTable;
    }

    public void setEffectTable(String effectTable) {
        this.effectTable = effectTable;
    }

    public String toString() {
        return "AssistDimItemDO [id=" + this.id + ", code=" + this.code + ", effectTable=" + this.effectTable + "]";
    }
}

