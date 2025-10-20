/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.AbstractFilter;
import com.jiuqi.bi.database.sql.model.filters.IsNullFilter;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public final class ValuesFilter
extends AbstractFilter
implements ISQLFilter {
    private ISQLField field;
    private int dataType = 6;
    private List<String> values;
    private static final int MAX_IN_SIZE = 1000;

    public ValuesFilter() {
        this.values = new ArrayList<String>();
    }

    public ValuesFilter(ISQLField field) {
        this();
        this.field = field;
    }

    public ValuesFilter(ISQLField field, Collection<String> values) {
        this.field = field;
        this.values = new ArrayList<String>(values);
    }

    public ISQLField getField() {
        return this.field;
    }

    public void setField(ISQLField field) {
        this.field = field;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List<String> getValues() {
        return this.values;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.values.isEmpty()) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u4efb\u4f55\u9650\u5b9a\u6761\u4ef6");
        }
        if (this.values.size() == 1) {
            if (this.isNull(this.values.get(0), database)) {
                IsNullFilter.toSQL(buffer, this.owner, this.field, database, this.dataType == 6);
            } else {
                this.printField(buffer, this.field, database);
                buffer.append('=');
                this.printValue(buffer, this.values.get(0), database);
            }
        } else {
            boolean hasMulti;
            boolean hasNull = this.testNull(database);
            boolean bl = hasMulti = this.values.size() >= 1000;
            if (hasNull || hasMulti) {
                buffer.append('(');
            }
            boolean started = false;
            int index = 0;
            for (String value : this.values) {
                if (this.isNull(value, database)) continue;
                if (index == 0) {
                    if (started) {
                        buffer.append(" OR ");
                    } else {
                        started = true;
                    }
                    this.printField(buffer, this.field, database);
                    buffer.append(" IN (");
                } else {
                    buffer.append(',');
                }
                this.printValue(buffer, value, database);
                if (++index < 1000) continue;
                buffer.append(')');
                index = 0;
            }
            if (index > 0) {
                buffer.append(')');
            }
            if (hasNull) {
                if (started) {
                    buffer.append(" OR ");
                }
                IsNullFilter.toSQL(buffer, this.owner, this.field, database, this.dataType == 6);
            }
            if (hasNull || hasMulti) {
                buffer.append(')');
            }
        }
    }

    private boolean testNull(IDatabase database) {
        for (String value : this.values) {
            if (!this.isNull(value, database)) continue;
            return true;
        }
        return false;
    }

    private boolean isNull(String value, IDatabase dbType) {
        if (value == null) {
            return true;
        }
        return this.dataType == 6 && value.length() == 0;
    }

    private void printValue(StringBuilder buffer, String value, IDatabase database) throws SQLModelException {
        if (value.indexOf(13) >= 0 || value.indexOf(10) >= 0) {
            throw new SQLModelException("\u8fc7\u6ee4\u503c\u4e2d\u4e0d\u5141\u8bb8\u51fa\u73b0\u6362\u884c\u7b26\uff1a" + value);
        }
        if (this.dataType == 6) {
            buffer.append(StringUtils.quote((String)value, (char)'\''));
        } else if (this.dataType == 1) {
            Boolean v = DataTypes.parseBoolean((String)value);
            buffer.append(v != null && v != false ? "1" : "0");
        } else if (this.dataType == 2) {
            ISQLInterpretor interpretor;
            Date date;
            SimpleDateFormat fmt = new SimpleDateFormat(value.indexOf(45) >= 0 ? "yyyy-MM-dd" : "yyyyMMdd");
            try {
                date = fmt.parse(value);
            }
            catch (ParseException e) {
                throw new SQLModelException("\u65e5\u671f\u578b\u5b57\u6bb5\u9650\u5b9a\u503c\u683c\u5f0f\u9519\u8bef\uff1a" + value, e);
            }
            try {
                interpretor = database.createSQLInterpretor(null);
            }
            catch (SQLInterpretException e) {
                throw new SQLModelException(e);
            }
            String dateExpr = interpretor.formatSQLDate(date);
            buffer.append(dateExpr);
        } else {
            if (value.indexOf("--") >= 0) {
                throw new SQLModelException("\u8fc7\u6ee4\u503c\u4e2d\u542b\u6709\u975e\u6cd5\u7684\u5b57\u7b26\u4e32\uff1a" + value);
            }
            buffer.append(value);
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.field).append(" in ").append(this.values);
        return buffer.toString();
    }
}

