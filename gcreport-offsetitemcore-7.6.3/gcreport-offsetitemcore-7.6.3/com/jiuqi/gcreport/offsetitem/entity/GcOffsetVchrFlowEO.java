/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.offsetitem.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_OFFSETVCHR_FLOW", title="\u62b5\u9500\u5206\u5f55\u6d41\u6c34\u53f7", inStorage=true)
public class GcOffsetVchrFlowEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_OFFSETVCHR_FLOW";
    @DBColumn(nameInDB="DIMENSIONS", title="\u7f16\u53f7\u7ef4\u5ea6", dbType=DBColumn.DBType.Varchar, length=100, isRequired=true)
    protected String dimensions;
    @DBColumn(nameInDB="FLOWNUMBER", title="\u6d41\u6c34\u53f7", dbType=DBColumn.DBType.Long, length=19)
    protected Long flowNumber;

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Long getFlowNumber() {
        return this.flowNumber;
    }

    public void setFlowNumber(Long flowNumber) {
        this.flowNumber = flowNumber;
    }
}

