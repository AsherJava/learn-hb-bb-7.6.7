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
import com.jiuqi.bi.database.sql.command.CSVLoadCommand;
import com.jiuqi.bi.database.sql.command.CombinedSQLCommand;
import com.jiuqi.bi.database.sql.command.ExternalClassCommand;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import com.jiuqi.bi.database.sql.command.SimpleSQLCommand;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.command.TableExistCommand;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.sql.parser.StringReader;
import com.jiuqi.bi.database.sql.parser.Term;
import com.jiuqi.bi.database.sql.parser.TextUtils;
import com.jiuqi.bi.database.sql.parser.antlr.ANTLRLexer;
import com.jiuqi.bi.database.sql.parser.antlr.ANTLRParser;
import com.jiuqi.bi.database.sql.parser.antlr.TokenInfo;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.sql.SQLPretreatment;
import com.jiuqi.bi.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

public class SQLCommandParser {
    public List<SQLCommand> parse(IDatabase database, File file) throws IOException, SQLParserException {
        String sql = SQLCommandParser.getSqlFromFile(file);
        return this.parse(database, sql);
    }

    public List<SQLCommand> parse(IDatabase database, String sql) throws SQLParserException {
        Term term;
        sql = TextUtils.tryRemoveUtf8BOM(sql);
        List<SQLCommand> cmds = new ArrayList<SQLCommand>();
        ArrayList<Term> terms = new ArrayList<Term>();
        for (int i = 0; i < sql.length() && (term = TextUtils.getTerm(sql, i)) != null; ++i) {
            if (term.text().equalsIgnoreCase("begin")) {
                i = this.parseBlockRegion(database, sql, term.pos(), cmds) - 1;
                continue;
            }
            if (term.isSQLEndSign()) {
                this.parseTerms(database, sql, terms, cmds);
                i = term.end();
                continue;
            }
            terms.add(term);
            i = term.end();
        }
        if (!terms.isEmpty()) {
            this.parseTerms(database, sql, terms, cmds);
        }
        cmds = this.mergeTablePrimarykeyCommand(cmds);
        return cmds;
    }

    private List<SQLCommand> mergeTablePrimarykeyCommand(List<SQLCommand> cmds) {
        HashMap<String, CreateTableStatement> createTableMap = new HashMap<String, CreateTableStatement>();
        for (SQLCommand cmd : cmds) {
            if (cmd.getType() == SQLCommandType.COMBINED) {
                CombinedSQLCommand c = (CombinedSQLCommand)cmd;
                for (int i = 0; i < c.size(); ++i) {
                    SQLCommand tmp = c.getCommand(i);
                    if (tmp.getType() != SQLCommandType.CREATE_TABLE) continue;
                    cmd = tmp;
                    break;
                }
            }
            if (cmd.getType() != SQLCommandType.CREATE_TABLE || !(cmd instanceof StatementCommand)) continue;
            SqlStatement s = ((StatementCommand)cmd).getStatement();
            String tname = ((CreateTableStatement)s).getTableName();
            createTableMap.put(tname.toUpperCase(), (CreateTableStatement)s);
        }
        if (createTableMap.isEmpty()) {
            return cmds;
        }
        ArrayList<SQLCommand> rs = new ArrayList<SQLCommand>();
        for (SQLCommand cmd : cmds) {
            AlterPrimarykeyStatement apk;
            CreateTableStatement createStat;
            SqlStatement s;
            if (cmd.getType() == SQLCommandType.ALTER_TABLE && cmd instanceof StatementCommand && (s = ((StatementCommand)cmd).getStatement()) instanceof AlterPrimarykeyStatement && (createStat = (CreateTableStatement)createTableMap.get((apk = (AlterPrimarykeyStatement)s).getTableName().toUpperCase())) != null) {
                createStat.setPkName(apk.getPrimarykeyName());
                createStat.getPrimaryKeys().addAll(apk.getPrimaryKeys());
                continue;
            }
            rs.add(cmd);
        }
        return rs;
    }

    private void parseTerms(IDatabase database, String sql, List<Term> terms, List<SQLCommand> cmds) throws SQLParserException {
        int start = terms.get(0).pos();
        int end = terms.get(terms.size() - 1).end();
        String currSql = sql.substring(start, end + 1).trim();
        SQLCommandType type = SQLCommandType.exploreType(terms);
        int startRow = TextUtils.getRownumByPosition(sql, terms.get(0).pos());
        if (this.isDDL(type)) {
            this.parseDDLRegion(currSql, startRow, database, terms, cmds);
        } else if (this.isDML(type)) {
            this.parseDMLRegion(currSql, startRow, database, terms, cmds);
        } else if (type == SQLCommandType.EXTERNAL_CLASS) {
            this.checkExternalClassSyntax(currSql, startRow, terms);
            cmds.add(new ExternalClassCommand(terms.get(2).text()));
        } else if (type == SQLCommandType.LOAD) {
            this.checkLoadCSVSyntax(currSql, startRow, terms);
            this.parseLoadCSVRegion(currSql, database, terms, cmds);
        } else {
            int rownum = TextUtils.getRownumByPosition(sql, terms.get(0).pos());
            throw new SQLParserException("\u884c" + rownum + "\u5904\u51fa\u73b0\u672a\u77e5\u7684SQL\u8bed\u6cd5\uff1a" + currSql);
        }
        terms.clear();
    }

    private void parseLoadCSVRegion(String sql, IDatabase database, List<Term> terms, List<SQLCommand> cmds) throws SQLParserException {
        String tableName = terms.get(5).text();
        CSVLoadCommand cmd = new CSVLoadCommand(tableName);
        StringReader reader = new StringReader(sql);
        reader.readLine();
        String line = reader.readLine();
        cmd.addRecord(this.splitRowData(line));
        while (line != null) {
            line = reader.readLine();
            cmd.addRecord(this.splitRowData(line));
        }
        cmds.add(cmd);
    }

    private List<String> splitRowData(String line) throws SQLParserException {
        Term prev = null;
        ArrayList<String> rowData = new ArrayList<String>();
        for (int i = 0; i < line.length(); ++i) {
            Term term = TextUtils.getTerm(line, i);
            if (term.isChar() && term.toChar() == ',') {
                if (prev != null) {
                    if (prev.isChar() && prev.toChar() == ',') {
                        rowData.add("");
                    }
                } else {
                    rowData.add("");
                }
            } else {
                rowData.add(term.text());
            }
            prev = term;
            i = term.end();
        }
        if (line.charAt(line.length() - 1) == ',') {
            rowData.add("");
        }
        return rowData;
    }

    private void checkLoadCSVSyntax(String sql, int startRow, List<Term> terms) throws SQLParserException {
        if (terms.size() < 7) {
            throw new SQLParserException("\u884c" + startRow + "\u5904load\u8bed\u6cd5\u6709\u8bef\uff1a" + sql);
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            b.append(terms.get(i).text().toLowerCase());
        }
        if (!b.toString().toLowerCase().equals("loadfromcsvinsertinto")) {
            throw new SQLParserException("\u884c" + startRow + "\u5904load\u8bed\u6cd5\u6709\u8bef\uff1a" + sql);
        }
    }

    private void checkExternalClassSyntax(String sql, int startRow, List<Term> terms) throws SQLParserException {
        if (terms.size() != 3) {
            throw new SQLParserException("\u884c" + startRow + "\u5904SQL\u8bed\u6cd5\u6709\u8bef:" + sql);
        }
        if (!terms.get(1).text().equalsIgnoreCase("class")) {
            throw new SQLParserException("\u884c" + startRow + "\u5904SQL\u8bed\u6cd5\u6709\u8bef\uff1a" + sql);
        }
    }

    private void parseDDLRegion(String sql, int startRow, IDatabase db, List<Term> terms, List<SQLCommand> dst) throws SQLParserException {
        SQLCommandType type = SQLCommandType.exploreType(terms);
        if (type == SQLCommandType.DROP_TABLE && !db.getDescriptor().supportDropIfExists() && this.isDropExistsCommand(terms)) {
            String tableName = terms.get(4).text().toUpperCase();
            TableExistCommand cmd1 = new TableExistCommand(tableName);
            AlterTableStatement ats = new AlterTableStatement(tableName, AlterType.DROP);
            StatementCommand cmd2 = new StatementCommand(ats, "DROP TABLE " + tableName);
            CombinedSQLCommand cmd = new CombinedSQLCommand(sql).addCommand(cmd1).addCommand(cmd2);
            dst.add(cmd);
        } else if (type == SQLCommandType.CREATE_TABLE && !db.getDescriptor().supportCreateIfNotExists() && this.isCreateTableExistsCommand(terms)) {
            TableExistCommand cmd1 = new TableExistCommand(terms.get(5).text());
            SqlStatement statement = this.parseAsDDLStatement(startRow, sql);
            StatementCommand cmd2 = new StatementCommand(statement);
            CombinedSQLCommand cmd = new CombinedSQLCommand(sql).addCommand(cmd1, false).addCommand(cmd2);
            dst.add(cmd);
        } else {
            SqlStatement statement = this.parseAsDDLStatement(startRow, sql);
            dst.add(new StatementCommand(statement));
        }
    }

    private void parseDMLRegion(String sql, int startRow, IDatabase db, List<Term> terms, List<SQLCommand> dst) throws SQLParserException {
        SQLCommandType type = SQLCommandType.exploreType(terms);
        if (type == SQLCommandType.INSERT) {
            List<SimpleSQLCommand> cmds = this.parseAsBatchInsertSqlStatment(sql);
            if (cmds.size() == 1) {
                dst.add(cmds.get(0));
            } else {
                CombinedSQLCommand cmd = new CombinedSQLCommand(sql);
                for (SimpleSQLCommand c : cmds) {
                    cmd.addCommand(c, false);
                }
                dst.add(cmd);
            }
        } else {
            dst.add(new SimpleSQLCommand(this.upper(sql), type));
        }
    }

    private List<SimpleSQLCommand> parseAsBatchInsertSqlStatment(String sql) throws SQLParserException {
        ArrayList<SimpleSQLCommand> cmds = new ArrayList<SimpleSQLCommand>();
        int valPos = sql.toUpperCase().indexOf("VALUES");
        if (valPos == -1) {
            SimpleSQLCommand cmd = new SimpleSQLCommand(this.upper(sql), SQLCommandType.INSERT);
            cmds.add(cmd);
        } else {
            String prefixstr = sql.substring(0, valPos + 6);
            String valstr = sql.substring(valPos + 6);
            String s = this.upper(prefixstr) + valstr;
            SimpleSQLCommand cmd = new SimpleSQLCommand(s, SQLCommandType.INSERT);
            cmds.add(cmd);
        }
        return cmds;
    }

    private boolean isDropExistsCommand(List<Term> terms) {
        return terms.get(0).text().equalsIgnoreCase("drop") && terms.get(2).text().equalsIgnoreCase("if") && terms.get(3).text().equalsIgnoreCase("exists");
    }

    private boolean isCreateTableExistsCommand(List<Term> terms) {
        return terms.get(0).text().equalsIgnoreCase("create") && terms.get(2).text().equalsIgnoreCase("if") && terms.get(3).text().equalsIgnoreCase("not") && terms.get(4).text().equalsIgnoreCase("exists");
    }

    private int parseBlockRegion(IDatabase database, String sql, int start, List<SQLCommand> dst) throws SQLParserException {
        StringReader reader = new StringReader(sql);
        reader.skip(start);
        String line = reader.readLine();
        if (!line.trim().equalsIgnoreCase("begin")) {
            throw new SQLParserException("SQL\u5757\u8bed\u6cd5\u63cf\u8ff0\u6709\u8bef\uff1a" + line);
        }
        String dbName = database.getName().toUpperCase();
        boolean matched = false;
        boolean others = false;
        boolean matchSucc = false;
        boolean hasEnd = false;
        while ((line = reader.readLine()) != null && !hasEnd) {
            if ((line = line.trim()).isEmpty()) continue;
            if (line.equalsIgnoreCase("end")) break;
            char c = line.charAt(0);
            if (c == '@') {
                if (matchSucc) {
                    while (line != null) {
                        if (line.trim().equalsIgnoreCase("end")) {
                            hasEnd = true;
                            break;
                        }
                        line = reader.readLine();
                    }
                    if (!hasEnd) {
                        throw new SQLParserException("SQL\u8bed\u6cd5\u6709\u8bef\uff0c\u5757\u8bed\u6cd5\u672a\u627e\u5230END\u63cf\u8ff0\u7b26");
                    }
                }
                if (matched || line == null) continue;
                List<String> dbList = Arrays.asList((line = line.toUpperCase()).substring(1).split(","));
                if (dbList.contains(dbName) || dbList.contains("DEFAULT")) {
                    matched = true;
                    continue;
                }
                if (dbList.contains("OTHER")) {
                    matched = true;
                    others = true;
                    continue;
                }
                matched = false;
                continue;
            }
            if (!matched) continue;
            String currSql = this.readIntactsql(reader, line);
            matchSucc = true;
            if (currSql.isEmpty()) continue;
            if (others) {
                List<SQLCommand> rs = this.parse(database, currSql);
                dst.addAll(rs);
                continue;
            }
            SimpleSQLCommand cmd = new SimpleSQLCommand(currSql, SQLCommandParser.exploreSQLCommandType(currSql));
            dst.add(cmd);
        }
        return reader.getPos();
    }

    private String readIntactsql(StringReader reader, String first) {
        String line = first;
        StringBuilder b = new StringBuilder();
        int pos = reader.getPos();
        while (line != null) {
            if ((line = line.trim()).isEmpty()) {
                pos = reader.getPos();
                line = reader.readLine();
                continue;
            }
            if (line.charAt(line.length() - 1) == ';') {
                b.append(line.substring(0, line.length() - 1)).append("\n");
                break;
            }
            if (line.charAt(0) == '@' || line.equalsIgnoreCase("end")) {
                reader.skip(pos - reader.getPos());
                break;
            }
            b.append(line).append("\n");
            pos = reader.getPos();
            line = reader.readLine();
        }
        if (b.length() > 0) {
            b.deleteCharAt(b.length() - 1);
        }
        return b.toString();
    }

    public static SQLCommandType exploreSQLCommandType(String sql) throws SQLParserException {
        Term term;
        ArrayList<Term> terms = new ArrayList<Term>();
        for (int i = 0; i < sql.length() && (term = TextUtils.getTerm(sql, i)) != null; ++i) {
            i = term.end() + 1;
            terms.add(term);
            if (terms.size() == 2) break;
        }
        return SQLCommandType.exploreType(terms);
    }

    private String upper(String sql) throws SQLParserException {
        SQLPretreatment sp = new SQLPretreatment(sql);
        sp.setToUpperCase(true);
        try {
            sp.execute();
        }
        catch (DBException e) {
            throw new SQLParserException(e.getMessage(), e);
        }
        return sp.getSQL();
    }

    private boolean isDDL(SQLCommandType type) {
        switch (type) {
            case CREATE_TABLE: 
            case CREATE_INDEX: 
            case CREATE_VIEW: 
            case ALTER_TABLE: 
            case ALTER_INDEX: 
            case DROP_INDEX: 
            case DROP_TABLE: 
            case DROP_VIEW: {
                return true;
            }
        }
        return false;
    }

    private boolean isDML(SQLCommandType type) {
        switch (type) {
            case INSERT: 
            case SELECT: 
            case UPDATE: 
            case DELETE: {
                return true;
            }
        }
        return false;
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
            for (TokenInfo info : errors) {
                if (i != 0) {
                    buf.append(StringUtils.LINE_SEPARATOR);
                }
                int line = 1;
                if (info.token != null) {
                    line = info.token.getLine();
                }
                buf.append("\u884c").append(startRow + line).append(":").append(info.msg);
                ++i;
            }
            throw new SQLParserException("sql\u89e3\u6790\u51fa\u9519:[" + sql + "]" + buf.toString());
        }
        return sqlStatement;
    }

    public static void main(String[] args) throws Exception {
        String pathname = "C:/wumingxing/a.sqlx";
        SQLCommandParser.parseSQLFile(new File(pathname));
    }

    private static void parseSQLFile(File file) throws Exception {
        String sql = SQLCommandParser.getSqlFromFile(file);
        SQLCommandParser parser = new SQLCommandParser();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByName("mysql");
        List<SQLCommand> cmds = parser.parse(database, sql);
        for (SQLCommand cmd : cmds) {
            String desc = cmd.toString("mysql");
            System.out.println(desc);
        }
    }

    /*
     * Exception decompiling
     */
    private static String getSqlFromFile(File file) throws IOException {
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
}

