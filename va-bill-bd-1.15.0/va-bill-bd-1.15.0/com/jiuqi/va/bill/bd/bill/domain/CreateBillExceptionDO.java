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
package com.jiuqi.va.bill.bd.bill.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.Order;

@Table(name="CREATEBILL_EXCEPTION")
public class CreateBillExceptionDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private BigDecimal ver;
    @Order(value="asc", priority=1)
    private BigDecimal ordinal;
    private Integer createtype;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String configname;
    private String srcdefinename;
    private String srcdefinecode;
    private String srcbillcode;
    private String srcmasterid;
    private String srcdetailbillid;
    private String definename;
    private String definecode;
    private String billcode;
    private String masterid;
    private Integer createbillstate;
    private Integer cachesyncdisable = 0;
    private String memo;

    public Integer getCachesyncdisable() {
        return this.cachesyncdisable;
    }

    public void setCachesyncdisable(Integer cachesyncdisable) {
        this.cachesyncdisable = cachesyncdisable;
    }

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

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getCreatetype() {
        return this.createtype;
    }

    public void setCreatetype(Integer createtype) {
        this.createtype = createtype;
    }

    public String getSrcdefinecode() {
        return this.srcdefinecode;
    }

    public void setSrcdefinecode(String srcdefinecode) {
        this.srcdefinecode = srcdefinecode;
    }

    public String getSrcdefinename() {
        return this.srcdefinename;
    }

    public void setSrcdefinename(String srcdefinename) {
        this.srcdefinename = srcdefinename;
    }

    public String getSrcbillcode() {
        return this.srcbillcode;
    }

    public void setSrcbillcode(String srcbillcode) {
        this.srcbillcode = srcbillcode;
    }

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    public Date getCreatetime() {
        return this.createtime;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getConfigname() {
        return this.configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSrcmasterid() {
        return this.srcmasterid;
    }

    public void setSrcmasterid(String srcmasterid) {
        this.srcmasterid = srcmasterid;
    }

    public String getSrcdetailbillid() {
        return this.srcdetailbillid;
    }

    public void setSrcdetailbillid(String srcdetailbillid) {
        this.srcdetailbillid = srcdetailbillid;
    }

    public String getDefinename() {
        return this.definename;
    }

    public void setDefinename(String definename) {
        this.definename = definename;
    }

    public String getDefinecode() {
        return this.definecode;
    }

    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getMasterid() {
        return this.masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public Integer getCreatebillstate() {
        return this.createbillstate;
    }

    public void setCreatebillstate(Integer createbillstate) {
        this.createbillstate = createbillstate;
    }
}

