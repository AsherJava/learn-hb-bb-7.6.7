/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.BaseDataIsolationExtend
 */
package com.jiuqi.gcreport.consolidatedsystem.isolation;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.BaseDataIsolationExtend;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class GcSubjectIsolation
implements BaseDataIsolationExtend {
    public DataModelColumn getIsolationColumn() {
        DataModelColumn column = new DataModelColumn();
        column.columnName("SYSTEMID").columnTitle("\u4f53\u7cfb").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60});
        return column;
    }

    public Set<String> getApplyTo() {
        HashSet<String> set = new HashSet<String>();
        set.add("MD_GCSUBJECT");
        return set;
    }
}

