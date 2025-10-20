/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.mysql;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamTable
implements ISQLTable,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> params;
    private String alias;
    private static final List<ISQLField> EMPTY_FIELDS = Arrays.asList(new ISQLField[0]);
    private static final List<ISQLFilter> EMPTY_FILTERS = Arrays.asList(new ISQLFilter[0]);
    private static final List<SortField> EMPTY_ORDERS = Arrays.asList(new SortField[0]);

    public ParamTable() {
        this(null);
    }

    public ParamTable(String alias) {
        this.alias = alias;
        this.params = new HashMap<String, Object>();
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String alias() {
        return this.alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Map<String, Object> params() {
        return this.params;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        boolean needAlias;
        if (!"MYSQL".equalsIgnoreCase(database.getName()) && !"TDSQL".equalsIgnoreCase(database.getName())) {
            throw new SQLModelException("\u53c2\u6570\u521d\u59cb\u5316\u8868\u53ea\u80fd\u5728MySQL\u6570\u636e\u5e93\u4e2d\u4f7f\u7528\u3002");
        }
        if (StringUtils.isEmpty((String)this.alias())) {
            throw new SQLModelException("MySQL\u53c2\u6570\u8868\u5fc5\u987b\u6307\u5b9a\u522b\u540d\u3002");
        }
        if (this.params.isEmpty()) {
            throw new SQLModelException("MySQL\u53c2\u6570\u8868\u672a\u6307\u5b9a\u4efb\u4f55\u53c2\u6570\u3002");
        }
        boolean bl = needAlias = (options & 1) == 0;
        if (needAlias) {
            buffer.append("(SELECT ");
        } else {
            buffer.append("SELECT ");
        }
        boolean started = false;
        for (Map.Entry<String, Object> e : this.params.entrySet()) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            this.printParam(buffer, e.getKey(), e.getValue());
        }
        if (needAlias) {
            buffer.append(") ").append(this.alias);
        }
    }

    private void printParam(StringBuilder buffer, String paramName, Object paramValue) {
        buffer.append('@').append(paramName).append(" := ");
        if (paramValue == null) {
            buffer.append("NULL");
        } else {
            buffer.append(paramValue);
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    @Override
    public List<ISQLField> fields() {
        return EMPTY_FIELDS;
    }

    @Override
    public Collection<ISQLFilter> whereFilters() {
        return EMPTY_FILTERS;
    }

    @Override
    public Collection<ISQLFilter> havingFilters() {
        return EMPTY_FILTERS;
    }

    @Override
    public List<SortField> sortFields() {
        return EMPTY_ORDERS;
    }

    @Override
    public boolean isSimpleMode() {
        return false;
    }

    @Override
    public boolean isGroupMode() {
        return false;
    }

    @Override
    public ISQLField findField(String name) {
        return null;
    }

    @Override
    public ISQLField findByFieldName(String fieldName) {
        return null;
    }

    @Override
    public ISQLField createField(String name, String alias) {
        throw new UnsupportedOperationException("MySQL\u53c2\u6570\u521d\u59cb\u5316\u8868\u4e0d\u652f\u6301\u521b\u5efa\u5b57\u6bb5\u64cd\u4f5c\u3002");
    }

    @Override
    public ISQLField createField(String name) {
        return this.createField(name, null);
    }

    @Override
    public String tableName() {
        return this.alias();
    }

    @Override
    public boolean containsParameter() {
        return false;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        return null;
    }

    public Object clone() {
        ParamTable result;
        try {
            result = (ParamTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        result.params = new HashMap<String, Object>(this.params);
        return result;
    }
}

