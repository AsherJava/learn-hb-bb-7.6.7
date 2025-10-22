/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.reader.convert.BigDecimaConverter;
import com.jiuqi.np.dataengine.reader.convert.BinaryConverter;
import com.jiuqi.np.dataengine.reader.convert.BooleanConverter;
import com.jiuqi.np.dataengine.reader.convert.DefaultConverter;
import com.jiuqi.np.dataengine.reader.convert.FloatConverter;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.np.dataengine.reader.convert.IntConverter;
import com.jiuqi.np.dataengine.reader.convert.StringConverter;
import com.jiuqi.np.dataengine.reader.convert.UUIDConverter;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class QueryFieldInfo
implements Comparable<QueryFieldInfo> {
    public QueryField queryField;
    public ColumnModelDefine fieldDefine;
    public int index;
    public int memoryIndex;
    public String dimensionName;
    public IFieldDataConverter dataConverter;
    public DimensionTable dimTable;

    public QueryFieldInfo(QueryField queryField, ColumnModelDefine fieldDefine, int index) {
        this.queryField = queryField;
        this.fieldDefine = fieldDefine;
        this.index = index;
        this.dataConverter = this.createDataConverter();
    }

    @Override
    public int compareTo(QueryFieldInfo o) {
        this.memoryIndex = o.memoryIndex;
        return this.memoryIndex;
    }

    public String toString() {
        return this.queryField.toString();
    }

    private IFieldDataConverter createDataConverter() {
        if (this.fieldDefine == null) {
            return new DefaultConverter(this.fieldDefine);
        }
        switch (this.fieldDefine.getColumnType()) {
            case BIGDECIMAL: {
                return new BigDecimaConverter(this.fieldDefine);
            }
            case DOUBLE: {
                if (this.fieldDefine.getPrecision() > 16) {
                    return new BigDecimaConverter(this.fieldDefine);
                }
                return new FloatConverter(this.fieldDefine);
            }
            case INTEGER: {
                return new IntConverter(this.fieldDefine);
            }
            case STRING: {
                return new StringConverter(this.fieldDefine);
            }
            case UUID: {
                return new UUIDConverter(this.fieldDefine);
            }
            case BLOB: {
                return new BinaryConverter(this.fieldDefine);
            }
            case BOOLEAN: {
                return new BooleanConverter(this.fieldDefine);
            }
        }
        return new DefaultConverter(this.fieldDefine);
    }
}

