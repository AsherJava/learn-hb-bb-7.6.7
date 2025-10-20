/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DBException
 *  com.jiuqi.bi.sql.SQLPretreatment
 *  com.jiuqi.bi.util.StringUtils
 *  org.antlr.runtime.ANTLRStringStream
 *  org.antlr.runtime.CharStream
 *  org.antlr.runtime.CommonTokenStream
 *  org.antlr.runtime.TokenSource
 *  org.antlr.runtime.TokenStream
 */
package com.jiuqi.bi.database.sql.parser;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.sql.parser.antlr.ANTLRLexer;
import com.jiuqi.bi.database.sql.parser.antlr.ANTLRParser;
import com.jiuqi.bi.database.sql.parser.antlr.TokenInfo;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.BatchInsertStatment;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.CustomClassStatment;
import com.jiuqi.bi.database.statement.SpecificStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.sql.SQLPretreatment;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

public final class SQLParser {
    private static final Set<String> KEYWORD_DDL = new HashSet<String>();
    private static final Set<String> KEYWORD_DML = new HashSet<String>();
    private static final Set<String> KEYWORD_BLOCK = new HashSet<String>();
    private static final Set<String> KEYWORD_CLASS = new HashSet<String>();

    public List<SqlStatement> parse(String sql) throws SQLParserException {
        sql = this.tryRemoveUtf8BOM(sql);
        String lineSeparator = StringUtils.LINE_SEPARATOR;
        String[] lines = sql.split("\r?\n");
        ArrayList<String> sqls = new ArrayList<String>();
        ArrayList<Integer> sqlStartRow = new ArrayList<Integer>();
        boolean isSqlBegin = false;
        boolean isBlock = false;
        StringBuffer sqlBuf = null;
        for (int i = 0; i < lines.length; ++i) {
            String lineStr = lines[i].trim();
            if (lineStr.startsWith("--")) continue;
            if (isSqlBegin) {
                if (sqlBuf == null) {
                    throw new RuntimeException();
                }
                sqlBuf.append(lineStr).append(lineSeparator);
                if (!this.isSQLEnd(isBlock, lineStr)) continue;
                isSqlBegin = false;
                String newsql = sqlBuf.toString().trim();
                if (newsql.charAt(newsql.length() - 1) == ';') {
                    newsql = newsql.substring(0, newsql.length() - 1);
                }
                sqls.add(newsql);
                continue;
            }
            if (this.isSQLStart(lineStr)) {
                isSqlBegin = true;
                sqlBuf = new StringBuffer();
                String lineVal = lineStr.toLowerCase().trim();
                isBlock = lineVal.startsWith("begin");
                sqlStartRow.add(i + 1);
                if (isBlock) {
                    if (lineStr.endsWith("end")) {
                        sqlBuf.append(lineStr).append(lineSeparator);
                        sqls.add(lineStr);
                        isSqlBegin = false;
                        continue;
                    }
                    sqlBuf.append(lineStr).append(lineSeparator);
                    continue;
                }
                if (lineStr.endsWith(";")) {
                    String s = lineStr.substring(0, lineStr.length() - 1);
                    sqlBuf.append(s).append(lineSeparator);
                    sqls.add(s);
                    isSqlBegin = false;
                    continue;
                }
                sqlBuf.append(lineStr).append(lineSeparator);
                continue;
            }
            if (lineStr.length() == 0) continue;
            throw new SQLParserException("\u884c" + (i + 1) + "\u5904\u9047\u5230\u672a\u8bc6\u522b\u7684\u8bed\u6cd5:" + lineStr);
        }
        if (sqlStartRow.size() > sqls.size()) {
            throw new SQLParserException("SQL\u8bed\u53e5\u6709\u8bef\uff0c\u884c" + lines.length + "\u5904\u7f3a\u5c11SQL\u7ed3\u675f\u6807\u8bc6");
        }
        List<SqlStatement> sqlStatements = this.parseSqls(sqls, sqlStartRow);
        return sqlStatements;
    }

    private String tryRemoveUtf8BOM(String str) {
        byte[] bytes = str.getBytes();
        if (bytes.length >= 3 && bytes[0] + 256 == 239 && bytes[1] + 256 == 187 && bytes[2] + 256 == 191) {
            byte[] nb = new byte[bytes.length - 3];
            System.arraycopy(bytes, 3, nb, 0, nb.length);
            return new String(nb);
        }
        return str;
    }

    private boolean isSQLStart(String sql) {
        String word = this.getFirstWord(sql).toLowerCase();
        return KEYWORD_DDL.contains(word) || KEYWORD_DML.contains(word) || KEYWORD_BLOCK.contains(word) || KEYWORD_CLASS.contains(word);
    }

    private String getFirstWord(String sql) {
        int i;
        char[] chars = sql.toCharArray();
        for (i = 0; i < chars.length && chars[i] != ' ' && chars[i] != '\t' && chars[i] != '\n'; ++i) {
        }
        return sql.substring(0, i);
    }

    private boolean isSQLEnd(boolean isBlock, String sql) {
        if (sql.length() == 0) {
            return false;
        }
        if (isBlock) {
            return sql.toUpperCase().endsWith("END");
        }
        return sql.charAt(sql.length() - 1) == ';';
    }

    private List<SqlStatement> parseSqls(List<String> sqls, List<Integer> sqlStartRow) throws SQLParserException {
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>();
        HashMap<String, AlterPrimarykeyStatement> pkMap = new HashMap<String, AlterPrimarykeyStatement>();
        for (int i = 0; i < sqls.size(); ++i) {
            AlterPrimarykeyStatement apks;
            String sql = sqls.get(i);
            String word = this.getFirstWord(sql).toLowerCase().trim();
            SqlStatement s = null;
            if (KEYWORD_DDL.contains(word)) {
                s = this.parseAsDDLStatement(sqlStartRow.get(i), sql);
                if (s.getErrorMessage() != null) {
                    s.setLine(sqlStartRow.get(i));
                    throw new SQLParserException("SQL\u8bed\u53e5\u5728" + (s.getLine() + s.getErrorLine()) + "\u884c\u6709\u8bef\uff0c" + s.getErrorMessage());
                }
            } else if (KEYWORD_DML.contains(word)) {
                s = this.parseAsDMLStatement(sql);
            } else if (KEYWORD_BLOCK.contains(word)) {
                s = this.parseBlockSql(sqlStartRow.get(i), sql);
            } else if (KEYWORD_CLASS.contains(word)) {
                s = this.parseAsClassStatement(sql);
            }
            if (s == null) continue;
            s.setLine(sqlStartRow.get(i));
            statements.add(s);
            if (!(s instanceof AlterPrimarykeyStatement) || (apks = (AlterPrimarykeyStatement)s).getAlterType() != AlterType.ADD) continue;
            pkMap.put(apks.getTableName(), apks);
        }
        ArrayList<AlterPrimarykeyStatement> removed = new ArrayList<AlterPrimarykeyStatement>();
        for (SqlStatement ss : statements) {
            CreateTableStatement cts;
            AlterPrimarykeyStatement pks;
            if (!(ss instanceof CreateTableStatement) || (pks = (AlterPrimarykeyStatement)pkMap.get((cts = (CreateTableStatement)ss).getTableName())) == null) continue;
            cts.setPkName(pks.getPrimarykeyName());
            cts.getPrimaryKeys().addAll(pks.getPrimaryKeys());
            removed.add(pks);
        }
        statements.removeAll(removed);
        return statements;
    }

    private SqlStatement parseAsDDLStatement(int startRow, String sql) throws SQLParserException {
        CommonTokenStream stream = new CommonTokenStream();
        ANTLRLexer lexer = new ANTLRLexer();
        lexer.setCharStream((CharStream)new ANTLRStringStream(sql));
        stream.setTokenSource((TokenSource)lexer);
        ANTLRParser parser = new ANTLRParser((TokenStream)stream);
        SqlStatement sqlStatement = parser.parse();
        ArrayList<TokenInfo> errors = new ArrayList<TokenInfo>();
        errors.addAll(lexer.getErrorTokens());
        errors.addAll(parser.getErrorTokens());
        if (errors.size() > 0) {
            StringBuffer buf = new StringBuffer();
            int i = 0;
            Iterator iterator = errors.iterator();
            if (iterator.hasNext()) {
                TokenInfo info = (TokenInfo)iterator.next();
                if (i != 0) {
                    buf.append("\r\n");
                }
                int line = 1;
                if (info.token != null) {
                    line = info.token.getLine();
                }
                buf.append("\u884c").append(startRow + line).append(":").append(info.msg);
                ++i;
            }
            if (sqlStatement != null) {
                if (((TokenInfo)errors.get((int)0)).token != null) {
                    int line = ((TokenInfo)errors.get((int)0)).token.getLine();
                    sqlStatement.setErrorLine(line);
                }
                sqlStatement.setErrorMessage(buf.toString());
            } else {
                throw new SQLParserException("sql\u89e3\u6790\u51fa\u9519:[" + sql + "]" + buf.toString());
            }
        }
        return sqlStatement;
    }

    private SqlStatement parseAsDMLStatement(String sql) throws SQLParserException {
        SQLPretreatment sp = new SQLPretreatment(sql);
        sp.setToUpperCase(true);
        try {
            sp.execute();
        }
        catch (DBException e) {
            throw new SQLParserException(e.getMessage(), e);
        }
        if (sql.substring(0, 6).equalsIgnoreCase("INSERT")) {
            return this.parseInsertSqlStatment(sp.getSQL());
        }
        return new SpecificStatement(sp.getSQL(), false);
    }

    private BatchInsertStatment parseInsertSqlStatment(String sql) {
        int valPos = sql.toUpperCase().indexOf("VALUES");
        String prefixstr = sql.substring(0, valPos + 6);
        String valstr = sql.substring(valPos + 6);
        char[] chars = valstr.toCharArray();
        BatchInsertStatment statement = new BatchInsertStatment();
        int start = 0;
        boolean inQuote = false;
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\\' && chars[i + 1] == '\'') {
                ++i;
                continue;
            }
            if (chars[i] == '\'') {
                inQuote = !inQuote;
                continue;
            }
            if (!inQuote && chars[i] == '(') {
                start = i;
                continue;
            }
            if (inQuote || chars[i] != ')') continue;
            statement.addInsertSql(prefixstr + valstr.substring(start, i + 1));
        }
        return statement;
    }

    private SqlStatement parseAsClassStatement(String sql) throws SQLParserException {
        int idx = sql.toLowerCase().indexOf("class");
        String value = sql.substring(idx + 5).trim();
        CustomClassStatment statment = new CustomClassStatment(value);
        return statment;
    }

    private SpecificStatement parseBlockSql(int line, String sql) throws SQLParserException {
        String[] lines = sql.split("\r?\n");
        if (lines.length > 2 && lines[0].trim().equalsIgnoreCase("begin") && lines[lines.length - 1].trim().equalsIgnoreCase("end")) {
            SpecificStatement statment = new SpecificStatement(sql, true);
            String[] dbNames = null;
            StringBuffer sqlBuf = null;
            for (int i = 1; i < lines.length - 1; ++i) {
                String curLineStr = lines[i].trim();
                if (curLineStr.startsWith("@")) {
                    this.appendSqlToSpecificStatement(dbNames, sqlBuf, statment);
                    dbNames = curLineStr.substring(1).split(",");
                    sqlBuf = new StringBuffer();
                    continue;
                }
                if (sqlBuf == null) {
                    throw new SQLParserException("\u884c" + line + "\u5904\u6709\u8bef\uff0c\u672a\u6307\u660e\u6267\u884c\u7684SQL\u8bed\u53e5\u9002\u7528\u7684\u6570\u636e\u5e93\u540d\u79f0");
                }
                sqlBuf.append(System.getProperty("line.separator")).append(curLineStr);
            }
            this.appendSqlToSpecificStatement(dbNames, sqlBuf, statment);
            return statment;
        }
        throw new SQLParserException("SQL\u8bed\u6cd5\u5757\u5b58\u5728\u5f02\u5e38\uff0c\u8bed\u6cd5\u5757\u5fc5\u987b\u4ee5begin\u5f00\u5934\uff0c\u4ee5end\u7ed3\u675f");
    }

    private void appendSqlToSpecificStatement(String[] dbNames, StringBuffer sqlBuf, SpecificStatement statment) throws SQLParserException {
        String[] sqls;
        if (dbNames == null || sqlBuf == null) {
            return;
        }
        for (String sql : sqls = sqlBuf.toString().split(";")) {
            SQLPretreatment sp = new SQLPretreatment(sql);
            sp.setToUpperCase(true);
            try {
                sp.execute();
            }
            catch (DBException e) {
                throw new SQLParserException(e.getMessage(), e);
            }
            sql = sp.getSQL();
            for (String dbName : dbNames) {
                statment.addSpecificSql(dbName, sql);
            }
        }
    }

    public static void main(String[] args) {
        String pathname = "C:/wumingxing/a.sqlx";
        try {
            String sql = SQLParser.getSqlFromFile(pathname);
            SQLParser parser = new SQLParser();
            List<SqlStatement> statements = parser.parse(sql);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByName("oracle");
            for (SqlStatement s : statements) {
                List<String> interpretSqls = s.interpret(database, null);
                if (interpretSqls == null) continue;
                for (String interpretsql : interpretSqls) {
                    System.out.println(interpretsql + ";");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Exception decompiling
     */
    private static String getSqlFromFile(String pathname) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static {
        KEYWORD_DML.add("insert");
        KEYWORD_DML.add("update");
        KEYWORD_DML.add("delete");
        KEYWORD_DML.add("select");
        KEYWORD_DDL.add("create");
        KEYWORD_DDL.add("alter");
        KEYWORD_DDL.add("drop");
        KEYWORD_BLOCK.add("begin");
        KEYWORD_BLOCK.add("end");
        KEYWORD_CLASS.add("call");
        KEYWORD_CLASS.add("class");
    }
}

