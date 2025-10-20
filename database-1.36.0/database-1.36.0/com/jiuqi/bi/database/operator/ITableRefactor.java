/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.operator;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.operator.ITableOperation;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import java.sql.Connection;
import java.util.List;

public interface ITableRefactor {
    public void setConnection(Connection var1);

    public List<ITableOperation> restructurePreview(LogicTable var1, List<LogicField> var2, LogicPrimaryKey var3, List<LogicIndex> var4, List<LoadFieldMap> var5) throws Exception;

    public List<ITableOperation> restructure(LogicTable var1, List<LogicField> var2, LogicPrimaryKey var3, List<LogicIndex> var4, List<LoadFieldMap> var5) throws Exception;
}

