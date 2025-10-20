/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.Order;

@Table(name="BILLCHANGE_RECORD")
public class BillChangeRecordDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Order(value="asc", priority=1)
    private BigDecimal ver;
    private String changefiledname;
    private String changefiledtitle;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String changebefore;
    private String changeafter;
    private String changeuser;
    private String srcbillcode;
    private String billcode;
    private String changereason;
    private String srcbilldefine;
    private String billdefine;
    private Integer changetype;
    private String billtable;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String getChangefiledname() {
        return this.changefiledname;
    }

    public void setChangefiledname(String changefiledname) {
        this.changefiledname = changefiledname;
    }

    public String getChangefiledtitle() {
        return this.changefiledtitle;
    }

    public void setChangefiledtitle(String changefiledtitle) {
        this.changefiledtitle = changefiledtitle;
    }

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public Date getCreatetime() {
        return this.createtime;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getChangebefore() {
        return this.changebefore;
    }

    public void setChangebefore(String changebefore) {
        this.changebefore = changebefore;
    }

    public String getChangeafter() {
        return this.changeafter;
    }

    public void setChangeafter(String changeafter) {
        this.changeafter = changeafter;
    }

    public String getChangeuser() {
        return this.changeuser;
    }

    public void setChangeuser(String changeuser) {
        this.changeuser = changeuser;
    }

    public String getSrcbillcode() {
        return this.srcbillcode;
    }

    public void setSrcbillcode(String srcbillcode) {
        this.srcbillcode = srcbillcode;
    }

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getChangereason() {
        return this.changereason;
    }

    public void setChangereason(String changereason) {
        this.changereason = changereason;
    }

    public String getSrcbilldefine() {
        return this.srcbilldefine;
    }

    public void setSrcbilldefine(String srcbilldefine) {
        this.srcbilldefine = srcbilldefine;
    }

    public String getBilldefine() {
        return this.billdefine;
    }

    public void setBilldefine(String billdefine) {
        this.billdefine = billdefine;
    }

    public Integer getChangetype() {
        return this.changetype;
    }

    public void setChangetype(Integer changetype) {
        this.changetype = changetype;
    }

    public String getBilltable() {
        return this.billtable;
    }

    public void setBilltable(String billtable) {
        this.billtable = billtable;
    }
}

