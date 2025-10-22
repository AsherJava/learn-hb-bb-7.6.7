/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FC_ABNORMALDATA", title="\u5173\u8054\u4ea4\u6613\u5f02\u5e38\u5206\u5f55\u8868", dataSource="jiuqi.gcreport.mdd.datasource")
public class FcAbnormalDataEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FC_ABNORMALDATA";
    @DBColumn(nameInDB="SRCITEMID", title="\u6765\u6e90\u5206\u5f55\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String srcItemId;
    @DBColumn(nameInDB="INFO", title="\u5f02\u5e38\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=500)
    private String info;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unitId;

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}

