/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 */
package com.jiuqi.dc.adjustvchr.impl.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;

@DBTable(name="DC_ADJUSTVCHRITEM", title="\u8c03\u6574\u51ed\u8bc1\u6a21\u578b", indexs={@DBIndex(name="IDX_ADJUSTVCHRITEM_VCHRID", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"VCHRID"}), @DBIndex(name="IDX_ADJUSTVCHRITEM_UNITCODE", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE"})}, convertToBudModel=true, dataSource="jiuqi.gcreport.mdd.datasource", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class AdjustVchrItemEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = 18241350962560511L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, isRequired=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="VCHRID", title="\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=2)
    private String vchrId;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=12, isRequired=true, order=3)
    private String vchrNum;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, order=4)
    private String unitCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, isRequired=true, order=5)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, length=5, isRequired=true, order=6)
    private Integer acctPeriod;
    @DBColumn(nameInDB="ITEMORDER", title="\u5206\u5f55\u7f16\u53f7", dbType=DBColumn.DBType.Numeric, precision=10, scale=0, isRequired=true, order=7)
    private Integer itemOrder;
    @DBColumn(nameInDB="DIGEST", title="\u6458\u8981", dbType=DBColumn.DBType.NVarchar, length=300, order=8)
    private String digest;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=9)
    private String subjectCode;
    @DBColumn(nameInDB="SUBJECTNAME", title="\u79d1\u76ee\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=100, order=10)
    private String subjectName;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=11)
    private String currencyCode;
    @DBColumn(nameInDB="DEBIT", title="\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=12)
    private BigDecimal debit;
    @DBColumn(nameInDB="CREDIT", title="\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=13)
    private BigDecimal credit;
    @DBColumn(nameInDB="EXCHRATE", title="\u6c47\u7387", dbType=DBColumn.DBType.Numeric, precision=19, scale=10, defaultValue="0.00", order=14)
    private BigDecimal exchrate;
    @DBColumn(nameInDB="VCHRSRCTYPE", title="\u51ed\u8bc1\u6765\u6e90\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=60, order=15)
    private String vchrSrcType;
    @DBColumn(nameInDB="ADJUSTTYPECODE", title="\u8c03\u6574\u7c7b\u578b\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=16)
    private String adjustTypeCode;
    @DBColumn(nameInDB="BIZDATE", title="\u4e1a\u52a1\u65e5\u671f", dbType=DBColumn.DBType.DateTime, order=17)
    private Date bizDate;
    @DBColumn(nameInDB="EXPIREDATE", title="\u5230\u671f\u65e5", dbType=DBColumn.DBType.DateTime, order=18)
    private Date expireDate;
    @DBColumn(nameInDB="CFITEMCODE", title="\u73b0\u91d1\u6d41\u91cf\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, order=19)
    private String cfItemCode;
    @DBColumn(nameInDB="FINCURR", title="\u672c\u4f4d\u5e01", dbType=DBColumn.DBType.NVarchar, length=60, order=20)
    private String fincurr;
    @DBColumn(nameInDB="ORGND", title="\u501f\u65b9\u539f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=21)
    private BigDecimal orgnD;
    @DBColumn(nameInDB="ORGNC", title="\u8d37\u65b9\u539f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=22)
    private BigDecimal orgnC;
    @DBColumn(nameInDB="PERIODTYPE", title="\u671f\u95f4\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=23)
    private String periodType;
    @DBColumn(nameInDB="CNYDEBIT", title="\u4eba\u6c11\u5e01\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=24)
    private BigDecimal cnyDebit;
    @DBColumn(nameInDB="CNYCREDIT", title="\u4eba\u6c11\u5e01\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=25)
    private BigDecimal cnyCredit;
    @DBColumn(nameInDB="HKDDEBIT", title="\u6e2f\u5e01\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=26)
    private BigDecimal hkdDebit;
    @DBColumn(nameInDB="HKDCREDIT", title="\u6e2f\u5e01\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=27)
    private BigDecimal hkdCredit;
    @DBColumn(nameInDB="USDDEBIT", title="\u7f8e\u5143\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=28)
    private BigDecimal usdDebit;
    @DBColumn(nameInDB="USDCREDIT", title="\u7f8e\u5143\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=29)
    private BigDecimal usdCredit;
    @DBColumn(nameInDB="REMARK", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=300, order=30)
    private String remark;
    private Map<String, Object> assistDatas;
    private Map<String, BigDecimal> convertAmount;
    private List<DimensionVO> assistDims;

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

    public BigDecimal getExchrate() {
        return this.exchrate;
    }

    public void setExchrate(BigDecimal exchrate) {
        this.exchrate = exchrate;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
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

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public String getFincurr() {
        return this.fincurr;
    }

    public void setFincurr(String fincurr) {
        this.fincurr = fincurr;
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

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public BigDecimal getCnyDebit() {
        return this.cnyDebit;
    }

    public void setCnyDebit(BigDecimal cnyDebit) {
        this.cnyDebit = cnyDebit;
    }

    public BigDecimal getCnyCredit() {
        return this.cnyCredit;
    }

    public void setCnyCredit(BigDecimal cnyCredit) {
        this.cnyCredit = cnyCredit;
    }

    public BigDecimal getHkdDebit() {
        return this.hkdDebit;
    }

    public void setHkdDebit(BigDecimal hkdDebit) {
        this.hkdDebit = hkdDebit;
    }

    public BigDecimal getHkdCredit() {
        return this.hkdCredit;
    }

    public void setHkdCredit(BigDecimal hkdCredit) {
        this.hkdCredit = hkdCredit;
    }

    public BigDecimal getUsdDebit() {
        return this.usdDebit;
    }

    public void setUsdDebit(BigDecimal usdDebit) {
        this.usdDebit = usdDebit;
    }

    public BigDecimal getUsdCredit() {
        return this.usdCredit;
    }

    public void setUsdCredit(BigDecimal usdCredit) {
        this.usdCredit = usdCredit;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getAssistDatas() {
        return this.assistDatas;
    }

    public void setAssistDatas(Map<String, Object> assistDatas) {
        this.assistDatas = assistDatas;
    }

    public Map<String, BigDecimal> getConvertAmount() {
        return this.convertAmount;
    }

    public void setConvertAmount(Map<String, BigDecimal> convertAmount) {
        this.convertAmount = convertAmount;
    }

    public List<DimensionVO> getAssistDims() {
        return this.assistDims;
    }

    public void setAssistDims(List<DimensionVO> assistDims) {
        this.assistDims = assistDims;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        ArrayList fields = CollectionUtils.newArrayList();
        List fieldVList = DimensionManagerUtil.getExtendColumn((String)"DC_ADJUSTVCHRITEM");
        double order = 30.0;
        for (DefinitionFieldV fieldV : fieldVList) {
            fieldV.setOrder(order);
            order += 1.0;
        }
        fields.addAll(fieldVList);
        return fields;
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

