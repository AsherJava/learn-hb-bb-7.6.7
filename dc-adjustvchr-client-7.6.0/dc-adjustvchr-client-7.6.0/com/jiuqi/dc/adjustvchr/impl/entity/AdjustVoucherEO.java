/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 */
package com.jiuqi.dc.adjustvchr.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.util.Date;
import javax.persistence.Column;

@DBTable(name="DC_ADJUSTVOUCHER", title="\u8c03\u6574\u51ed\u8bc1\u4e3b\u8868", indexs={@DBIndex(name="IDX_ADJUSTVOUCHER_U_Y", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE", "ACCTYEAR"}), @DBIndex(name="IDX_ADJUSTVOUCHER_PERIOD", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"AFFECTPERIODSTART", "AFFECTPERIODEND"})}, dataSource="jiuqi.gcreport.mdd.datasource", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class AdjustVoucherEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -6958523123019275404L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, isRequired=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, order=2)
    private String unitCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, isRequired=true, order=3)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, length=5, isRequired=true, order=4)
    private Integer acctPeriod;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=12, isRequired=true, order=5)
    private String vchrNum;
    @DBColumn(nameInDB="CREATETIME", title="\u5236\u5355\u65e5\u671f", dbType=DBColumn.DBType.DateTime, isRequired=true, order=6)
    private Date createTime;
    @DBColumn(nameInDB="CREATOR", title="\u5236\u5355\u4eba", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, order=7)
    private String creator;
    @DBColumn(nameInDB="MODIFYTIME", title="\u4fee\u6539\u65e5\u671f", dbType=DBColumn.DBType.DateTime, order=8)
    private Date modifyTime;
    @DBColumn(nameInDB="MODIFYUSER", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.NVarchar, length=50, order=9)
    private String modifyUser;
    @DBColumn(nameInDB="ADJUSTTYPECODE", title="\u8c03\u6574\u7c7b\u578b\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=10)
    private String adjustTypeCode;
    @DBColumn(nameInDB="AFFECTPERIODSTART", title="\u5f71\u54cd\u671f\u95f4\u8d77\u59cb", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=11)
    private String affectPeriodStart;
    @DBColumn(nameInDB="AFFECTPERIODEND", title="\u5f71\u54cd\u671f\u95f4\u622a\u6b62", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=12)
    private String affectPeriodEnd;
    @DBColumn(nameInDB="VCHRSRCTYPE", title="\u51ed\u8bc1\u6765\u6e90\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=60, order=13)
    private String vchrSrcType;
    @DBColumn(nameInDB="PERIODTYPE", title="\u671f\u95f4\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=14)
    private String periodType;
    @DBColumn(nameInDB="GROUPID", title="\u5206\u7ec4ID", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=15)
    private String groupId;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
    }

    public String getAffectPeriodStart() {
        return this.affectPeriodStart;
    }

    public void setAffectPeriodStart(String affectPeriodStart) {
        this.affectPeriodStart = affectPeriodStart;
    }

    public String getAffectPeriodEnd() {
        return this.affectPeriodEnd;
    }

    public void setAffectPeriodEnd(String affectPeriodEnd) {
        this.affectPeriodEnd = affectPeriodEnd;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

