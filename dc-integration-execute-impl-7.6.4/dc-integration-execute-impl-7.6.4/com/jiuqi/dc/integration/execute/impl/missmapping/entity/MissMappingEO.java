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
 */
package com.jiuqi.dc.integration.execute.impl.missmapping.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="DC_LOG_MISSMAPPING", title="\u7f3a\u6620\u5c04\u8bb0\u5f55\u8868", indexs={@DBIndex(name="IDX_MISSMAPPING_COMB", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"RUNNERID", "DATASCHEMECODE", "UNITCODE", "ACCTYEAR", "ACCTPERIOD"})}, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class MissMappingEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -552023121011207391L;
    @DBColumn(nameInDB="RUNNERID", title="\u8fd0\u884cID", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=1)
    private String runnerId;
    @DBColumn(nameInDB="TASKTYPE", title="\u4efb\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=2)
    private String taskType;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, isRequired=true, order=3)
    private String acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u4f1a\u8ba1\u671f\u95f4", dbType=DBColumn.DBType.Int, length=2, isRequired=true, order=4)
    private String acctPeriod;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=5)
    private String unitCode;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true, order=6)
    private String dataSchemeCode;
    @DBColumn(nameInDB="VCHRID", title="\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=36, order=7)
    private String vchrId;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=60, order=8)
    private String vchrNum;
    @DBColumn(nameInDB="DIMCODE", title="\u7ef4\u5ea6\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=9)
    private String dimCode;
    @DBColumn(nameInDB="DIMVALUE", title="\u7ef4\u5ea6\u503c", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=10)
    private String dimValue;

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
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

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }
}

