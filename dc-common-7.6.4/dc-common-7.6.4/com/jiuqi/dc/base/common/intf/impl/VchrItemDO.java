/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Transient
 *  tk.mybatis.mapper.entity.IDynamicTableName
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import tk.mybatis.mapper.entity.IDynamicTableName;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class VchrItemDO
extends TenantDO
implements IDynamicTableName {
    private static final long serialVersionUID = 4087935600314819742L;
    @Transient
    private String dynamicTableName;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="UNITCODE")
    private String unitCode;
    @Column(name="ACCTYEAR")
    private Integer acctYear;
    @Column(name="ACCTPERIOD")
    private Integer acctPeriod;
    @Column(name="VCHRNUM")
    private String vchrNum;
    @Column(name="ITEMORDER")
    private String itemOrder;
    @Column(name="DIGEST")
    private String digest;
    @Column(name="SUBJECTCODE")
    private String subjectCode;
    @Column(name="CURRENCYCODE")
    private String currencyCode;
    @Column(name="DEBIT")
    private BigDecimal debit;
    @Column(name="CREDIT")
    private BigDecimal credit;
    @Column(name="ORGND")
    private BigDecimal orgnd;
    @Column(name="ORGNC")
    private BigDecimal orgnc;
    @Column(name="SN")
    private Integer sn;
    @Column(name="BIZDATE")
    private Date bizDate;
    @Column(name="EXPIREDATE")
    private String expireDate;
    @Column(name="CREATEDATE")
    private Date createDate;
    @Column(name="RECLASSIFYSUBJCODE")
    private String reclassifySubjCode;
    @Column(name="RECLASSIFYSCHEMEID")
    private String reclassifySchemeId;
    @Column(name="RULECHANGEHANDLEFLAG")
    private Integer ruleChangeHandleFlag;
    @Column(name="SRCITEMASSID")
    private String srcItemAssId;

    public VchrItemDO() {
    }

    public VchrItemDO(String unitCode, Integer acctYear, Integer acctPeriod) {
        this.unitCode = unitCode;
        this.acctYear = acctYear;
        this.acctPeriod = acctPeriod;
        this.setDynamicTableName(acctYear);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDebit() {
        return this.debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getOrgnd() {
        return this.orgnd;
    }

    public void setOrgnd(BigDecimal orgnd) {
        this.orgnd = orgnd;
    }

    public BigDecimal getOrgnc() {
        return this.orgnc;
    }

    public void setOrgnc(BigDecimal orgnc) {
        this.orgnc = orgnc;
    }

    public Integer getSn() {
        return this.sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Date getBizDate() {
        return this.bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getReclassifySubjCode() {
        return this.reclassifySubjCode;
    }

    public void setReclassifySubjCode(String reclassifySubjCode) {
        this.reclassifySubjCode = reclassifySubjCode;
    }

    public String getReclassifySchemeId() {
        return this.reclassifySchemeId;
    }

    public void setReclassifySchemeId(String reclassifySchemeId) {
        this.reclassifySchemeId = reclassifySchemeId;
    }

    public Integer getRuleChangeHandleFlag() {
        return this.ruleChangeHandleFlag;
    }

    public void setRuleChangeHandleFlag(Integer ruleChangeHandleFlag) {
        this.ruleChangeHandleFlag = ruleChangeHandleFlag;
    }

    public void setDynamicTableName(String dynamicTableName) {
        this.dynamicTableName = dynamicTableName;
    }

    public String getDynamicTableName() {
        return this.dynamicTableName;
    }

    public void setDynamicTableName(int acctYear) {
        this.dynamicTableName = CommonUtil.getVoucherItemAssTableName(acctYear);
    }

    public String getSrcItemAssId() {
        return this.srcItemAssId;
    }

    public void setSrcItemAssId(String srcItemAssId) {
        this.srcItemAssId = srcItemAssId;
    }

    public String toString() {
        return "VchrItemDO [id=" + this.id + ", reclassifySubjCode=" + this.reclassifySubjCode + ", reclassifySchemeId=" + this.reclassifySchemeId + ", unitCode=" + this.unitCode + ", acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", vchrNum=" + this.vchrNum + ", itemOrder=" + this.itemOrder + ", subjectCode=" + this.subjectCode + ", debit=" + this.debit + ", credit=" + this.credit + ", currencyCode=" + this.currencyCode + ", orgnd=" + this.orgnd + ", orgnc=" + this.orgnc + ", digest=" + this.digest + ", bizDate=" + this.bizDate + ", expireDate=" + this.expireDate + ", createDate=" + this.createDate + ", sn=" + this.sn + ", ruleChangeHandleFlag=" + this.ruleChangeHandleFlag + "]";
    }
}

