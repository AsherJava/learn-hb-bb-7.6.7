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
package com.jiuqi.gcreport.inputdata.formsetting.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_OFFSETDIMSETTING", title="\u62b5\u9500\u7ef4\u5ea6\u8bbe\u7f6e", indexs={@DBIndex(name="IDX_ODIMSETTING_FORMID", columnsFields={"FORMID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class OffsetDimSettingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_OFFSETDIMSETTING";
    @DBColumn(title="\u8868\u5355ID", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String formId;
    @DBColumn(title="\u62b5\u9500\u7ef4\u5ea6", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String offsetDims;
    @DBColumn(title="\u64cd\u4f5c\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date operateTime;
    @DBColumn(title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String dimOperator;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getOffsetDims() {
        return this.offsetDims;
    }

    public void setOffsetDims(String offsetDims) {
        this.offsetDims = offsetDims;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getDimOperator() {
        return this.dimOperator;
    }

    public void setDimOperator(String dimOperator) {
        this.dimOperator = dimOperator;
    }

    public String toString() {
        return "OffsetDimSettingEO{formId='" + this.formId + '\'' + ", offsetDims='" + this.offsetDims + '\'' + ", operateTime=" + this.operateTime + ", dimOperator='" + this.dimOperator + '\'' + '}';
    }
}

