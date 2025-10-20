/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.antlr.runtime.EarlyExitException
 *  org.antlr.runtime.FailedPredicateException
 *  org.antlr.runtime.MismatchedNotSetException
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.MismatchedTokenException
 *  org.antlr.runtime.MismatchedTreeNodeException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.Parser
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 *  org.antlr.runtime.Token
 *  org.antlr.runtime.TokenStream
 */
package com.jiuqi.bi.database.sql.parser.antlr;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.sql.parser.antlr.TokenInfo;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

class DDLParser
extends Parser {
    private SqlStatement sqlStatement;
    public static final String COL_NAME = "COLNAME";
    public static final String ORG_COL_NAME = "ORG_COL_NAME";
    public static final String COL_ALTERTYPE = "ALTERTYPE";
    public static final String COL_TYPE = "COLTYPE";
    public static final String COL_NULL = "COLNULL";
    public static final String COL_LEN = "COLLEN";
    public static final String COL_DECIMAL = "COLDECIMAL";
    public static final String COL_DEFAULT = "COLDEFAULT";
    public static final String COL_TITLE = "comment";
    private static final Map<String, String> tNames = new HashMap<String, String>(25);
    private Map<String, String> columnInfo = new HashMap<String, String>();
    private StringBuilder tablePropStringbuilder = new StringBuilder();
    private List<TokenInfo> errorTokens = new ArrayList<TokenInfo>();

    public DDLParser(TokenStream input) {
        super(input);
    }

    public DDLParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public SqlStatement parse() throws SQLParserException {
        try {
            this.evaluate();
            return this.sqlStatement;
        }
        catch (RecognitionException e) {
            throw new SQLParserException(e);
        }
    }

    protected Object evaluate() throws RecognitionException {
        return null;
    }

    protected void newCreateTableStatement(String tableName, boolean isJudgeExist) {
        this.sqlStatement = new CreateTableStatement(this.input.toString(), DDLParser.upper(tableName));
        ((CreateTableStatement)this.sqlStatement).setJudgeExists(isJudgeExist);
    }

    protected void newCreateIndexStatement(String indexName, boolean isUnique, boolean isJudgeExists) {
        this.sqlStatement = new CreateIndexStatement(this.input.toString(), indexName, isUnique);
        ((CreateIndexStatement)this.sqlStatement).setJudgeExists(isJudgeExists);
    }

    protected void newDropTableStatement(String tableName, boolean isJudgeExists) {
        this.sqlStatement = new AlterTableStatement(this.input.toString(), DDLParser.upper(tableName), AlterType.DROP);
        ((AlterTableStatement)this.sqlStatement).setJudgeExists(isJudgeExists);
    }

    protected void newDropIndexStatement(String indexName, boolean isJudgeExists) {
        this.sqlStatement = new AlterIndexStatement(this.input.toString(), indexName, AlterType.DROP);
        ((AlterIndexStatement)this.sqlStatement).setJudgeExists(isJudgeExists);
    }

    protected void newAlterColumnStatement(String tableName) {
        this.sqlStatement = new AlterColumnStatement(this.input.toString(), DDLParser.upper(tableName), null);
    }

    protected void newAlterPrimarykeyStatement(String tableName, String alterType) {
        this.sqlStatement = new AlterPrimarykeyStatement(this.input.toString(), DDLParser.upper(tableName));
        AlterType type = AlterType.typeOf(alterType);
        ((AlterPrimarykeyStatement)this.sqlStatement).setAlterType(type);
    }

    protected void newRenameTableStatement(String tableName) {
        this.sqlStatement = new AlterTableStatement(this.input.toString(), DDLParser.upper(tableName), AlterType.RENAME);
    }

    protected void newRenameIndexStatement(String indexName) {
        this.sqlStatement = new AlterIndexStatement(this.input.toString(), indexName);
    }

    protected void newRenameConstraintStatement(String tableName) {
        this.sqlStatement = new AlterPrimarykeyStatement(this.input.toString(), DDLParser.upper(tableName), AlterType.RENAME);
    }

    protected void recordColumnInfo(String prop, String value) throws RecognitionException {
        this.columnInfo.put(prop, value);
        if (prop.equals(COL_TYPE)) {
            this.checkColType(value);
        }
    }

    protected void addTableProperty(String key, String value) throws RecognitionException {
        ((CreateTableStatement)this.sqlStatement).setProperty(key, value);
    }

    private void checkColType(String value) throws RecognitionException {
    }

    protected void addColumnForCreateTable() {
        if (this.sqlStatement instanceof CreateTableStatement) {
            LogicField column = this.createColumnFromInfo();
            ((CreateTableStatement)this.sqlStatement).addColumn(column);
            this.columnInfo.clear();
        }
    }

    protected void addCommentForCreateTable(String comment) {
        char[] chars;
        if (comment != null && comment.length() >= 2 && (chars = comment.toCharArray())[0] == '\'' && chars[chars.length - 1] == '\'') {
            comment = comment.substring(1, comment.length() - 1);
        }
        if (this.sqlStatement instanceof CreateTableStatement) {
            ((CreateTableStatement)this.sqlStatement).setComment(comment);
        }
    }

    protected void addPkNameForCreateTable(String pkName) {
        if (this.sqlStatement instanceof CreateTableStatement) {
            ((CreateTableStatement)this.sqlStatement).setPkName(pkName);
        }
    }

    protected void addPrimaryKeyForCreateTable(String colName) {
        if (this.sqlStatement instanceof CreateTableStatement) {
            ((CreateTableStatement)this.sqlStatement).getPrimaryKeys().add(DDLParser.upper(colName));
        }
    }

    protected void addAlterColumnInfo() {
        AlterColumnStatement acs = (AlterColumnStatement)this.sqlStatement;
        String alterType = this.columnInfo.get(COL_ALTERTYPE);
        AlterType type = AlterType.typeOf(alterType);
        acs.setAlterType(type);
        String columnName = this.columnInfo.get(COL_NAME);
        acs.setColumnName(DDLParser.upper(columnName));
        if (type == AlterType.ADD || type == AlterType.MODIFY) {
            acs.setNewColumn(this.createColumnFromInfo());
        } else if (type == AlterType.RENAME) {
            acs.setColumnName(DDLParser.upper(this.columnInfo.get(ORG_COL_NAME)));
            acs.setReColumnName(DDLParser.upper(this.columnInfo.get(COL_NAME)));
            acs.setNewColumn(this.createColumnFromInfo());
        }
        this.columnInfo.clear();
    }

    protected void setPrimaryKeyName(String keyName) {
        if (this.sqlStatement instanceof AlterPrimarykeyStatement) {
            ((AlterPrimarykeyStatement)this.sqlStatement).setPrimarykeyName(keyName);
        }
    }

    protected void addPrimaryColumn(String columnName) {
        if (this.sqlStatement instanceof AlterPrimarykeyStatement) {
            ((AlterPrimarykeyStatement)this.sqlStatement).addPrimaryKey(DDLParser.upper(columnName));
        }
    }

    protected void setTableNameForIndex(String tableName) {
        if (this.sqlStatement instanceof CreateIndexStatement) {
            ((CreateIndexStatement)this.sqlStatement).setTableName(DDLParser.upper(tableName));
        } else if (this.sqlStatement instanceof AlterIndexStatement) {
            ((AlterIndexStatement)this.sqlStatement).setTableName(DDLParser.upper(tableName));
        }
    }

    protected void setRenameForTable(String tableName) {
        if (this.sqlStatement instanceof AlterTableStatement) {
            ((AlterTableStatement)this.sqlStatement).setRename(DDLParser.upper(tableName));
        } else if (this.sqlStatement instanceof AlterPrimarykeyStatement) {
            ((AlterPrimarykeyStatement)this.sqlStatement).setRename(DDLParser.upper(tableName));
        }
    }

    protected void setRenameForIndex(String indexName) {
        if (this.sqlStatement instanceof AlterIndexStatement) {
            ((AlterIndexStatement)this.sqlStatement).setNewIndexName(indexName);
        }
    }

    protected void setConstraintNameForRenameConstraint(String constraintName) {
        if (this.sqlStatement instanceof AlterPrimarykeyStatement) {
            ((AlterPrimarykeyStatement)this.sqlStatement).setPrimarykeyName(constraintName);
        }
    }

    protected void addIndexColumn(String columnName) {
        if (this.sqlStatement instanceof CreateIndexStatement) {
            ((CreateIndexStatement)this.sqlStatement).addIndexColumn(DDLParser.upper(columnName));
        }
    }

    private LogicField createColumnFromInfo() {
        int decimal;
        char[] chars;
        String name = this.columnInfo.get(COL_NAME);
        String type = this.columnInfo.get(COL_TYPE);
        String nullstr = this.columnInfo.get(COL_NULL);
        String title = this.columnInfo.get(COL_TITLE);
        if (title != null && title.length() >= 2 && (chars = title.toCharArray())[0] == '\'' && chars[chars.length - 1] == '\'') {
            title = title.substring(1, title.length() - 1);
        }
        boolean nullable = nullstr == null || Boolean.parseBoolean(nullstr);
        String lenstr = this.columnInfo.get(COL_LEN);
        int len = lenstr == null ? -1 : Integer.valueOf(lenstr);
        String decimalstr = this.columnInfo.get(COL_DECIMAL);
        int n = decimal = decimalstr == null ? 0 : Integer.valueOf(decimalstr);
        if (type.equalsIgnoreCase("number")) {
            type = decimal == 0 && len <= 11 ? "integer" : "decimal";
        }
        int dataType = DDLParser.typeOf(type);
        LogicField field = new LogicField();
        field.setFieldName(DDLParser.upper(name));
        field.setFieldTitle(title);
        field.setNullable(nullable);
        if (nullstr == null) {
            field.setAlterOptions(4);
        }
        field.setDataType(dataType);
        field.setRawType(0);
        field.setDataTypeName(type);
        field.setSize(len);
        field.setPrecision(len);
        field.setScale(decimal);
        if (type.equalsIgnoreCase("nvarchar")) {
            field.setRawType(-9);
            field.setSupportI18n(true);
        }
        String dfVal = this.columnInfo.get(COL_DEFAULT);
        field.setDefaultValue(dfVal);
        if (StringUtils.isEmpty((String)dfVal)) {
            field.setIgnoreDefaultValue(true);
        }
        return field;
    }

    private static String upper(String str) {
        return str == null ? null : str.toUpperCase();
    }

    private static int typeOf(String name) {
        if (name.equalsIgnoreCase("varchar") || name.equalsIgnoreCase("nvarchar")) {
            return 6;
        }
        if (name.equalsIgnoreCase("decimal")) {
            return 3;
        }
        if (name.equalsIgnoreCase("integer")) {
            return 5;
        }
        if (name.equalsIgnoreCase("date") || name.equalsIgnoreCase("timestamp") || name.equalsIgnoreCase("datetime")) {
            return 2;
        }
        if (name.equalsIgnoreCase("blob")) {
            return 9;
        }
        if (name.equalsIgnoreCase("clob")) {
            return 12;
        }
        if (name.equalsIgnoreCase("NCLOB")) {
            return 2011;
        }
        if (name.equalsIgnoreCase("raw")) {
            return 13;
        }
        return -1;
    }

    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String msg = this.getErrorMessage(e, tokenNames);
        if (e.token != null) {
            this.errorTokens.add(new TokenInfo(e.token, msg));
        }
    }

    public List<TokenInfo> getErrorTokens() {
        return this.errorTokens;
    }

    public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        StringBuilder buf = new StringBuilder();
        if (e instanceof MismatchedTokenException) {
            MismatchedTokenException mte = (MismatchedTokenException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            if (mte.expecting == -1) {
                buf.append("\u5904\u9700\u8981\u4e00\u4e2a\u7ed3\u675f\u6807\u8bc6");
            } else {
                buf.append("\u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
                String expecting = tNames.get(tokenNames[mte.expecting]);
                if (expecting == null) {
                    expecting = tokenNames[mte.expecting];
                }
                if (!expecting.equals("'!'")) {
                    buf.append(", \u53ef\u80fd\u9700\u8981").append(expecting);
                }
            }
        } else if (e instanceof MismatchedTreeNodeException) {
            MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
            buf.append(mtne.node);
            if (mtne.expecting == -1) {
                buf.append("\u5904\u9700\u8981\u4e00\u4e2a\u7ed3\u675f\u6807\u8bc6");
            } else {
                String expecting = tNames.get(tokenNames[mtne.expecting]);
                if (expecting == null) {
                    expecting = tokenNames[mtne.expecting];
                }
                buf.append("\u5904\u9700\u8981").append(expecting);
            }
        } else if (e instanceof NoViableAltException) {
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
        } else if (e instanceof EarlyExitException) {
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
        } else if (e instanceof MismatchedSetException) {
            MismatchedSetException mse = (MismatchedSetException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            if (mse.expecting != null) {
                buf.append(" \u5904\u9700\u8981").append(mse.expecting);
            } else {
                buf.append(" \u5904\u8bed\u6cd5\u9519\u8bef");
            }
        } else if (e instanceof MismatchedNotSetException) {
            MismatchedNotSetException mse = (MismatchedNotSetException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u5904\u9700\u8981").append(mse.expecting);
        } else if (e instanceof FailedPredicateException) {
            FailedPredicateException fpe = (FailedPredicateException)e;
            buf.append(fpe.ruleName).append("\u65ad\u8a00\u5931\u8d25\uff1a{").append(fpe.predicateText).append("}?");
        } else if (e instanceof GrammarException) {
            buf.append(e.getMessage());
        } else {
            buf.append(e.getMessage());
        }
        return buf.toString();
    }

    public String getTokenErrorDisplay(Token t) {
        String s = t.getText();
        if (s == null) {
            s = t.getType() == -1 ? "\u8868\u8fbe\u5f0f\u672b\u5c3e" : "<" + t.getType() + ">";
        }
        s = s.replaceAll("\n", "\\\\n");
        s = s.replaceAll("\r", "\\\\r");
        if ((s = s.replaceAll("\t", "\\\\t")).trim().equals("'")) {
            return "\u5355\u5f15\u53f7";
        }
        return "'" + s + "'";
    }

    static {
        tNames.put("EQ", "=");
        tNames.put("GT", ">");
        tNames.put("GE", ">=");
        tNames.put("LT", "<");
        tNames.put("LE", "<=");
        tNames.put("NE", "!=");
        tNames.put("PLUS", "+");
        tNames.put("MINUS", "-");
        tNames.put("LINK", "&");
        tNames.put("MULTI", "\u00d7");
        tNames.put("DIVIDE", "\u00f7");
        tNames.put("POWER", "\u4e58\u65b9");
        tNames.put("LBRACKET", "[");
        tNames.put("RBRACKET", "]");
        tNames.put("COMMA", "\u9017\u53f7");
        tNames.put("COLON", "\u5192\u53f7");
        tNames.put("POINT", "\u70b9");
        tNames.put("LBRACE", "{");
        tNames.put("RBRACE", "}");
        tNames.put("SEMICOLON", ";");
        tNames.put("LPAREN", "(");
        tNames.put("RPAREN", ")");
        tNames.put("WS", "\u7a7a\u683c");
        tNames.put("QUOTE", "\u5355\u5f15\u53f7");
        tNames.put("DOUBLEQUOTE", "\u53cc\u5f15\u53f7");
        tNames.put("'<EOF>'", "\u8868\u8fbe\u5f0f\u672b\u5c3e");
    }

    private class GrammarException
    extends RecognitionException {
        private static final long serialVersionUID = -2576355109533400641L;

        private GrammarException() {
        }
    }
}

