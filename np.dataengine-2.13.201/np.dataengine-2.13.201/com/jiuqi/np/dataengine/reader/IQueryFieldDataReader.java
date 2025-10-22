/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;

public interface IQueryFieldDataReader {
    public Object readData(QueryField var1) throws Exception;

    public Object readData(int var1) throws Exception;

    public int findIndex(QueryField var1);

    public QueryFieldInfo putIndex(DataModelDefinitionsCache var1, QueryField var2, int var3);

    public QueryFieldInfo findQueryFieldInfo(QueryField var1);

    public void setDataSet(Object var1);

    public boolean next();

    public void reset();
}

