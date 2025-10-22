/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.nr.io.sb.service.BaseTableCreateDao;
import com.jiuqi.nr.io.tz.TzConstants;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class SbIdTableCreateDao
extends BaseTableCreateDao {
    @Override
    protected LogicTable buildTmpTable() {
        LogicTable tmpTable = new LogicTable();
        tmpTable.setName(TzConstants.createName());
        return tmpTable;
    }

    @Override
    protected Set<TempIndex> getIndexFields() {
        return null;
    }

    @Override
    protected List<String> pkFieldName() {
        return Collections.singletonList("SBID");
    }

    @Override
    protected List<LogicField> buildTmpFields() {
        LogicField sbId = new LogicField();
        sbId.setFieldName("SBID");
        sbId.setSize(50);
        sbId.setDataType(6);
        return Collections.singletonList(sbId);
    }
}

