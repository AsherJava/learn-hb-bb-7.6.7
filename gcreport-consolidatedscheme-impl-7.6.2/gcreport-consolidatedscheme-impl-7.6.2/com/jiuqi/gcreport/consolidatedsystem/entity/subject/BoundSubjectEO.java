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
package com.jiuqi.gcreport.consolidatedsystem.entity.subject;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_BOUNDSUBJECT", title="\u5408\u5e76\u79d1\u76ee\u5173\u8054\u96c6\u56e2\u79d1\u76ee")
@DBIndex(name="INDEX_BOUNDSUBJECT_1", columnsFields={"CODE", "SYSTEMID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)
public class BoundSubjectEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_BOUNDSUBJECT";
    @DBColumn(nameInDB="CODE", title="\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=32)
    private String code;
    @DBColumn(nameInDB="ORDINAL", title="\u6392\u5e8f", dbType=DBColumn.DBType.Numeric, precision=19, scale=6)
    private Double ordinal;
    @DBColumn(nameInDB="SYSTEMID", title="\u5408\u5e76\u4f53\u7cfb", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String systemId;
    @DBColumn(nameInDB="CONSSUBJECTCODE", title="\u5408\u5e76\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=32)
    private String consSubjectCode;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getConsSubjectCode() {
        return this.consSubjectCode;
    }

    public void setConsSubjectCode(String consSubjectCode) {
        this.consSubjectCode = consSubjectCode;
    }
}

