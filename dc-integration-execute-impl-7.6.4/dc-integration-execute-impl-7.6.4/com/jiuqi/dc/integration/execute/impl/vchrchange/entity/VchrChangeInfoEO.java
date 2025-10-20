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
package com.jiuqi.dc.integration.execute.impl.vchrchange.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_VCHRCHANGEINFO", indexs={@DBIndex(name="INDEX_DC_VCHRCHANGEINFO_ALL", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE", "ACCTYEAR", "DATASCHEMECODE", "OFFSETGROUPID"})}, title="\u51ed\u8bc1\u53d8\u66f4\u8bb0\u5f55\u8868\u56fa\u5316", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class VchrChangeInfoEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 6814567586648194428L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, isRequired=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784CODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String unitCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.NVarchar, length=4, order=3)
    private String acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=5, order=4)
    private String acctPeriod;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=5)
    private String dataSchemeCode;
    @DBColumn(nameInDB="SRCVCHRID", title="\u6765\u6e90\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=60, order=6)
    private String srcVchrId;
    @DBColumn(nameInDB="OFFSETGROUPID", title="\u62b5\u9500\u5206\u7ec4\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=60, order=7)
    private String offsetGroupId;
    @DBColumn(nameInDB="CREATEOFFSETVCHR", title="\u662f\u5426\u751f\u6210\u62b5\u9500\u51ed\u8bc1", dbType=DBColumn.DBType.Int, length=1, defaultValue="0", order=8)
    private String createOffsetVchr;
    @DBColumn(nameInDB="VCHRCLEANFLAG", title="\u51ed\u8bc1\u6570\u636e\u6e05\u9664\u6807\u8bc6", dbType=DBColumn.DBType.Int, length=1, defaultValue="0", order=9)
    private String vchrCleanFlag;

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

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getSrcVchrId() {
        return this.srcVchrId;
    }

    public void setSrcVchrId(String srcVchrId) {
        this.srcVchrId = srcVchrId;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public String getCreateOffsetVchr() {
        return this.createOffsetVchr;
    }

    public void setCreateOffsetVchr(String createOffsetVchr) {
        this.createOffsetVchr = createOffsetVchr;
    }

    public String getVchrCleanFlag() {
        return this.vchrCleanFlag;
    }

    public void setVchrCleanFlag(String vchrCleanFlag) {
        this.vchrCleanFlag = vchrCleanFlag;
    }
}

