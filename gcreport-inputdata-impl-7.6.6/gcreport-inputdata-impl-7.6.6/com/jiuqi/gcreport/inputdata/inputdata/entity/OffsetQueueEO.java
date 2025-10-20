/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.inputdata.inputdata.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_OFFSETQUEUE", title="\u62a5\u8868\u62b5\u9500\u961f\u5217", inStorage=true, indexs={@DBIndex(name="IDX_GC_OFFSETQUEUE_COMB1", columnsFields={"UNITCOMB", "NRPERIOD", "TASKID", "CURRENCYCODE", "UNIONRULEID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class OffsetQueueEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_OFFSETQUEUE";
    @DBColumn(nameInDB="UNITCOMB", title="\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true, description="\u5408\u5e76\u8ba1\u7b97\u65f6\u4e3a\u5408\u5e76\u5355\u4f4d\uff0c\u5176\u5b83\u4e3a\u672c\u5bf9\u65b9\u5355\u4f4d\u7ec4\u5408")
    private String unitComb;
    @DBColumn(nameInDB="UNIONRULEID", title="\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unionRuleId;
    @DBColumn(nameInDB="NRPERIOD", title="\u65f6\u671f", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String nrPeriod;
    @DBColumn(nameInDB="TASKID", title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u79cd\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String currencyCode;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0, isRequired=true)
    private Long createTime;

    public String getUnitComb() {
        return this.unitComb;
    }

    public void setUnitComb(String unitComb) {
        this.unitComb = unitComb;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getNrPeriod() {
        return this.nrPeriod;
    }

    public void setNrPeriod(String nrPeriod) {
        this.nrPeriod = nrPeriod;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}

