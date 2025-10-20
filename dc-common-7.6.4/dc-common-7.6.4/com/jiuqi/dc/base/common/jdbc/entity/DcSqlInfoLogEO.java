/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.base.common.jdbc.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="GC_LOG_SQLINFO", title="SQL\u4fe1\u606f\u65e5\u5fd7\u8868", kind=TableModelKind.SYSTEM_EXTEND, dataSource="jiuqi.gcreport.mdd.datasource", ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class DcSqlInfoLogEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -8454577616915676997L;
    @DBColumn(nameInDB="SQLFULLTEXT", title="SQLFULLTEXT", dbType=DBColumn.DBType.Text, isRequired=true, order=1)
    private String sqlFullText;

    public String getSqlFullText() {
        return this.sqlFullText;
    }

    public void setSqlFullText(String sqlFullText) {
        this.sqlFullText = sqlFullText;
    }
}

