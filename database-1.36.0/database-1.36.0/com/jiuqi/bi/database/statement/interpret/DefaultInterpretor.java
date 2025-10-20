/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.statement.interpret;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.util.SQLPrinter;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterCommentStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.AlterViewStatement;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.CreateViewStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultInterpretor
implements ISQLInterpretor {
    protected IDatabase database;

    public DefaultInterpretor(IDatabase db) {
        this.database = db;
    }

    @Override
    public List<String> alterColumn(AlterColumnStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        buf.append("ALTER TABLE ").append(statement.getTableName()).append(" ");
        switch (statement.getAlterType()) {
            case ADD: {
                buf.append(" ADD ");
                this.appendColumnClause(buf, statement.getNewColumn());
                break;
            }
            case DROP: {
                buf.append(" DROP COLUMN ").append(statement.getColumnName());
                break;
            }
            case MODIFY: {
                buf.append(" MODIFY ");
                this.appendColumnClause(buf, statement.getNewColumn());
                break;
            }
            case RENAME: {
                buf.append(" RENAME COLUMN ").append(statement.getColumnName());
                buf.append(" TO ").append(statement.getReColumnName());
                break;
            }
            default: {
                throw new SQLInterpretException("\u672a\u652f\u6301\u7684\u64cd\u4f5c\u7c7b\u578b");
            }
        }
        return this.asList(buf.toString());
    }

    @Override
    public List<String> alterIndex(AlterIndexStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        switch (statement.getAlterType()) {
            case DROP: {
                buf.append("DROP INDEX ").append(statement.getIndexName());
                buf.append(" ON ").append(statement.getTableName());
                break;
            }
            case RENAME: {
                buf.append("ALTER INDEX ").append(statement.getIndexName());
                buf.append(" RENAME TO ").append(statement.getNewIndexName());
                break;
            }
            default: {
                throw new SQLInterpretException("\u6570\u636e\u5e93\u53ea\u652f\u6301\u5220\u9664\u548c\u91cd\u547d\u540d\u7d22\u5f15");
            }
        }
        return this.asList(buf.toString());
    }

    @Override
    public List<String> alterPrimarykey(AlterPrimarykeyStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        buf.append("ALTER TABLE ").append(statement.getTableName()).append(" ");
        switch (statement.getAlterType()) {
            case ADD: {
                buf.append(" ADD CONSTRAINT ").append(statement.getPrimarykeyName());
                buf.append(" PRIMARY KEY ").append("(");
                List<String> keys = statement.getPrimaryKeys();
                SQLPrinter.printName(this.database, keys.get(0), buf);
                for (int i = 1; i < keys.size(); ++i) {
                    buf.append(",");
                    SQLPrinter.printName(this.database, keys.get(i), buf);
                }
                buf.append(")");
                break;
            }
            case DROP: {
                buf.append(" DROP CONSTRAINT ").append(statement.getPrimarykeyName());
                break;
            }
            case RENAME: {
                buf.append(" RENAME CONSTRAINT ").append(statement.getPrimarykeyName()).append(" TO ").append(statement.getRename());
                break;
            }
            case MODIFY: {
                ArrayList<String> list = new ArrayList<String>();
                StringBuilder b = new StringBuilder();
                b.append((CharSequence)buf).append(" DROP CONSTRAINT ").append(statement.getPrimarykeyName());
                list.add(b.toString());
                b.delete(0, b.length());
                b.append((CharSequence)buf).append(" ADD CONSTRAINT ").append(statement.getPrimarykeyName());
                b.append(" PRIMARY KEY ").append("(");
                b.append(StringUtils.join(statement.getPrimaryKeys().iterator(), (String)","));
                b.append(")");
                list.add(b.toString());
                return list;
            }
        }
        return this.asList(buf.toString());
    }

    @Override
    public List<String> alterTable(AlterTableStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        switch (statement.getAlterType()) {
            case DROP: {
                buf.append("DROP TABLE ").append(statement.getTableName());
                break;
            }
            case RENAME: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" ALTER TABLE ");
                stringBuilder.append(statement.getTableName());
                stringBuilder.append(" RENAME TO ");
                stringBuilder.append(statement.getRename());
                return this.asList(stringBuilder.toString());
            }
            default: {
                throw new SQLInterpretException("\u6570\u636e\u5e93\u53ea\u652f\u6301\u5220\u9664\u548c\u91cd\u547d\u540d\u8868");
            }
        }
        return this.asList(buf.toString());
    }

    @Override
    public List<String> createIndex(CreateIndexStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        buf.append("CREATE ");
        if (statement.isUniqueIndex()) {
            buf.append(" UNIQUE ");
        }
        buf.append(" INDEX ").append(statement.getIndexName());
        buf.append(" ON ").append(statement.getTableName()).append("(");
        List<String> names = statement.getColumnNames();
        SQLPrinter.printName(this.database, names.get(0), buf);
        for (int i = 1; i < names.size(); ++i) {
            buf.append(",");
            SQLPrinter.printName(this.database, names.get(i), buf);
        }
        buf.append(")");
        return this.asList(buf.toString());
    }

    @Override
    public List<String> createTable(CreateTableStatement statement) throws SQLInterpretException {
        String comment;
        List<String> sqls;
        AlterCommentStatement alterComment;
        StringBuilder buf = new StringBuilder();
        buf.append("CREATE TABLE ").append(statement.getTableName());
        buf.append("\r\n ( \r\n");
        List<LogicField> cols = statement.getColumns();
        ArrayList<String> columnCommentCreateSqls = new ArrayList<String>();
        for (int i = 0; i < cols.size(); ++i) {
            if (i != 0) {
                buf.append(", \r\n");
            }
            LogicField f = cols.get(i);
            this.appendColumnClause(buf, f);
            if (f.getFieldTitle() == null || f.getFieldTitle().length() <= 0) continue;
            alterComment = new AlterCommentStatement(statement.getTableName(), f, f.getFieldTitle(), AlterType.ADD);
            sqls = this.alterComment(alterComment);
            columnCommentCreateSqls.addAll(sqls);
        }
        buf.append("\r\n )");
        List<String> list = this.asList(buf.toString());
        if (statement.hasPrimaryKey()) {
            AlterPrimarykeyStatement apks = new AlterPrimarykeyStatement(null, statement.getTableName(), AlterType.ADD);
            apks.setPrimarykeyName(StringUtils.isEmpty((String)statement.getPkName()) ? "pk_" + statement.getTableName() : statement.getPkName());
            apks.getPrimaryKeys().addAll(statement.getPrimaryKeys());
            List<String> pks = this.alterPrimarykey(apks);
            list.addAll(pks);
        }
        if ((comment = statement.getComment()) != null && comment.length() > 0) {
            alterComment = new AlterCommentStatement(statement.getTableName(), comment, AlterType.ADD);
            sqls = this.alterComment(alterComment);
            list.addAll(sqls);
        }
        if (columnCommentCreateSqls.size() > 0) {
            list.addAll(columnCommentCreateSqls);
        }
        return list;
    }

    protected List<String> asList(String value) {
        ArrayList<String> list = new ArrayList<String>(1);
        list.add(value);
        return list;
    }

    protected String getStringTypeName(LogicField col) {
        if (StringUtils.isNotEmpty((String)col.getDataTypeName())) {
            return col.getDataTypeName();
        }
        int raw = col.getRawType();
        if (raw == 12) {
            return "varchar";
        }
        if (raw == -9) {
            return "nvarchar";
        }
        return "varchar";
    }

    protected void buildStringTypeSql(StringBuilder buf, LogicField col) {
        buf.append(this.getStringTypeName(col)).append("(");
        buf.append(this.getColumnSizeString(col)).append(")");
    }

    public String getColumnSizeString(LogicField col) {
        return col.getSize() == -100 ? "max" : String.valueOf(col.getSize());
    }

    @Override
    public String getDataTypeSQL(LogicField col) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        switch (col.getDataType()) {
            case 6: {
                this.buildStringTypeSql(buf, col);
                break;
            }
            case 3: 
            case 5: 
            case 8: {
                if (col.getPrecision() <= 0) {
                    buf.append("number ");
                    break;
                }
                buf.append("number(");
                buf.append(col.getPrecision());
                if (col.getScale() > 0) {
                    buf.append(", ").append(col.getScale());
                }
                buf.append(")");
                break;
            }
            case 1: {
                buf.append("number(1)");
                break;
            }
            case 2: {
                String tname = col.getDataTypeName();
                if (tname == null) {
                    buf.append("DATE");
                    break;
                }
                if (tname.equalsIgnoreCase("timestamp") || tname.equalsIgnoreCase("datetime")) {
                    buf.append(tname);
                    break;
                }
                buf.append("DATE");
                break;
            }
            case 9: {
                buf.append("BLOB");
                break;
            }
            case 12: {
                buf.append("CLOB");
                break;
            }
            case 2011: {
                buf.append("NCLOB");
                break;
            }
            case 13: {
                buf.append("RAW(").append(col.getPrecision()).append(")");
                break;
            }
            default: {
                throw new SQLInterpretException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + col.getRawType() + " dataTypeName=" + col.getDataTypeName());
            }
        }
        return buf.toString();
    }

    private void appendColumnClause(StringBuilder buf, LogicField col) throws SQLInterpretException {
        SQLPrinter.printName(this.database, col.getFieldName(), buf);
        buf.append(" ");
        buf.append(this.getDataTypeSQL(col));
        if (!col.isIgnoreDefaultValue() && StringUtils.isNotEmpty((String)col.getDefaultValue())) {
            buf.append(" default ");
            if (col.getDataType() == 6) {
                String v = col.getDefaultValue();
                if (v.length() >= 2 && v.charAt(0) == '\'') {
                    buf.append(v);
                } else {
                    buf.append("'").append(v).append("'");
                }
            } else {
                buf.append(col.getDefaultValue());
            }
        }
        if (!col.isIgnoreNullable()) {
            if (!col.isNullable()) {
                buf.append(" not null ");
            } else {
                buf.append(" null ");
            }
        }
    }

    @Override
    public List<String> createView(CreateViewStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        buf.append("CREATE VIEW ").append(statement.getViewName());
        List<String> columns = statement.getColumns();
        if (columns.size() > 0) {
            buf.append(" (");
            for (int i = 0; i < columns.size(); ++i) {
                buf.append(columns.get(i)).append(",");
            }
            buf.delete(buf.length() - 1, buf.length());
            buf.append(") ");
        }
        buf.append("\r\nAS \r\n").append(statement.getAsSQL());
        return this.asList(buf.toString());
    }

    @Override
    public List<String> alterView(AlterViewStatement statement) throws SQLInterpretException {
        StringBuilder buf = new StringBuilder();
        switch (statement.getAlterType()) {
            case DROP: {
                buf.append("DROP VIEW ").append(statement.getViewName());
                break;
            }
            default: {
                throw new SQLInterpretException("\u6570\u636e\u5e93\u53ea\u652f\u6301\u5220\u9664\u89c6\u56fe");
            }
        }
        return this.asList(buf.toString());
    }

    @Override
    public String formatSQLDate(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return "'" + fmt.format(date) + "'";
    }

    @Override
    public List<String> alterComment(AlterCommentStatement statement) throws SQLInterpretException {
        return new ArrayList<String>();
    }

    @Override
    public String analyzeTableEstimate(String tableName) throws SQLInterpretException {
        return null;
    }

    @Override
    public String analyzeTableFull(String tableName) throws SQLInterpretException {
        return null;
    }

    @Override
    public List<String> createBakTable(String srcTablename, String bakTableName) throws SQLInterpretException {
        StringBuilder sbf = new StringBuilder();
        sbf.append("CREATE TABLE ").append(bakTableName).append("  AS ( SELECT * FROM ").append(srcTablename).append(")");
        return this.asList(sbf.toString());
    }

    @Override
    public String formatSQLTimestamp(String dateTime_ms_Str) {
        return "'" + dateTime_ms_Str + "'";
    }

    @Override
    public final List<String> renameTable(String tableName, String newTableName) throws SQLInterpretException {
        AlterTableStatement statement = new AlterTableStatement(tableName, AlterType.RENAME);
        statement.setRename(newTableName);
        return this.alterTable(statement);
    }
}

