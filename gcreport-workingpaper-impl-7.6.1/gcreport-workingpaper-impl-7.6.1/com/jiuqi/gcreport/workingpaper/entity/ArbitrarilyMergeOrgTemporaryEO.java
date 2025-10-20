/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ORGTEMPORARY_AM", title="\u4efb\u610f\u5408\u5e76\u5355\u4f4d\u4e34\u65f6\u8868")
public class ArbitrarilyMergeOrgTemporaryEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ORGTEMPORARY_AM";
    @DBColumn(title="\u6279\u91cfID", dbType=DBColumn.DBType.NVarchar)
    private String batchId;
    @DBColumn(title="\u5355\u4f4d\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=60)
    private String code;
    @DBColumn(title="\u7236\u8def\u5f84", dbType=DBColumn.DBType.NVarchar, length=610)
    private String parents;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}

