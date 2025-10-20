/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.AbstractFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TuplesFilter
extends AbstractFilter
implements ISQLFilter {
    private List<ISQLField> fields = new ArrayList<ISQLField>();
    private List<Integer> fieldTypes = new ArrayList<Integer>();
    private List<String[]> values = new ArrayList<String[]>();

    public void addField(ISQLField field) {
        this.addField(field, 6);
    }

    public void addField(ISQLField field, int dataType) {
        this.fields.add(field);
        this.fieldTypes.add(dataType);
    }

    public int fieldSize() {
        return this.fields.size();
    }

    public ISQLField getField(int index) {
        return this.fields.get(index);
    }

    public int getFieldType(int index) {
        return this.fieldTypes.get(index);
    }

    public void clear() {
        this.fields.clear();
        this.fieldTypes.clear();
        this.values.clear();
    }

    public List<String[]> getValues() {
        return this.values;
    }

    public void addValue(String ... tuple) {
        this.values.add((String[])tuple.clone());
    }

    public void addValue(Collection<String> tuple) {
        String[] value = tuple.toArray(new String[this.fieldSize()]);
        this.values.add(value);
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        int i;
        if (this.fieldSize() == 0) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u4efb\u4f55\u9650\u5b9a\u6761\u4ef6");
        }
        if (this.fieldSize() == 1) {
            ArrayList<String> items = new ArrayList<String>(this.values.size());
            for (String[] tuple : this.values) {
                items.add(tuple[0]);
            }
            SQLHelper.in(database, this.owner, this.getField(0), items, buffer, this.getFieldType(0));
            return;
        }
        buffer.append('(');
        for (i = 0; i < this.fieldSize(); ++i) {
            if (i > 0) {
                buffer.append(',');
            }
            this.printField(buffer, this.getField(i), database);
        }
        buffer.append(") IN (");
        for (i = 0; i < this.values.size(); ++i) {
            if (i > 0) {
                buffer.append(',');
            }
            String[] tuple = this.values.get(i);
            buffer.append('(');
            for (int j = 0; j < tuple.length; ++j) {
                if (j > 0) {
                    buffer.append(',');
                }
                SQLHelper.printValue(buffer, tuple[j], this.getFieldType(j));
            }
            buffer.append(')');
        }
        buffer.append(')');
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(');
        for (int i = 0; i < this.fieldSize(); ++i) {
            if (i > 0) {
                buffer.append(',');
            }
            buffer.append(this.getField(i).fieldName());
        }
        buffer.append(") IN ");
        buffer.append(this.values);
        return buffer.toString();
    }
}

