/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.adjustvchr.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class AdjustVchrItemVO {
    private String id;
    private Long ver;
    private String vchrId;
    private String vchrNum;
    @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a") String unitCode;
    private String unitName;
    @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a") Integer acctYear;
    private Integer acctPeriod;
    private Integer itemOrder;
    private String digest;
    @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a") String subjectCode;
    private String subjectName;
    @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u5e01\u522b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u5e01\u522b\u4e0d\u80fd\u4e3a\u7a7a") String currencyCode;
    private String cfItemCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date bizDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date expireDate;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal orgnD;
    private BigDecimal orgnC;
    private BigDecimal exchrate;
    private Map<String, Object> assistDatas;
    private String periodType;
    private String adjustTypeCode;
    private String adjustTypeName;
    private Map<String, BigDecimal> convertAmount;
    private String vchrSrcType;
    private List<DimensionVO> assistDims;
    private String remark;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Integer getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
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

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public Date getBizDate() {
        return this.bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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

    public BigDecimal getOrgnD() {
        return this.orgnD;
    }

    public void setOrgnD(BigDecimal orgnD) {
        this.orgnD = orgnD;
    }

    public BigDecimal getOrgnC() {
        return this.orgnC;
    }

    public void setOrgnC(BigDecimal orgnC) {
        this.orgnC = orgnC;
    }

    public BigDecimal getExchrate() {
        return this.exchrate;
    }

    public void setExchrate(BigDecimal exchrate) {
        this.exchrate = exchrate;
    }

    public Map<String, Object> getAssistDatas() {
        return this.assistDatas;
    }

    public void setAssistDatas(Map<String, Object> assistDatas) {
        this.assistDatas = assistDatas;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
    }

    public String getAdjustTypeName() {
        return this.adjustTypeName;
    }

    public void setAdjustTypeName(String adjustTypeName) {
        this.adjustTypeName = adjustTypeName;
    }

    public Map<String, BigDecimal> getConvertAmount() {
        return this.convertAmount;
    }

    public void setConvertAmount(Map<String, BigDecimal> convertAmount) {
        this.convertAmount = convertAmount;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }

    public List<DimensionVO> getAssistDims() {
        return this.assistDims;
    }

    public void setAssistDims(List<DimensionVO> assistDims) {
        this.assistDims = assistDims;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAmountDefaultValue() {
        if (this.debit == null) {
            this.debit = BigDecimal.ZERO;
        }
        if (this.credit == null) {
            this.credit = BigDecimal.ZERO;
        }
        if (this.orgnD == null) {
            this.orgnD = BigDecimal.ZERO;
        }
        if (this.orgnC == null) {
            this.orgnC = BigDecimal.ZERO;
        }
    }

    public void setConvertAmountDefaultValue(List<String> columns) {
        if (this.convertAmount == null) {
            this.convertAmount = new HashMap<String, BigDecimal>();
        }
        for (String col : columns) {
            if (this.convertAmount.containsKey(col) && this.convertAmount.get(col) != null) continue;
            this.convertAmount.put(col, BigDecimal.ZERO);
        }
    }
}

