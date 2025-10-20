/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.SQLOperator;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSQLOperator {
    private static Logger logger = LoggerFactory.getLogger(SQLOperator.class);

    static void logModuleBegin(ModuleDescriptor module, String moduleVersionGuid, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.beginModule(module, moduleVersionGuid);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static void logModuleEnd(ModuleDescriptor module, String moduleVersionGuid, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.endModule(module, moduleVersionGuid);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static void logSQLFileBegin(SQLFile file, String moduleVersionGuid, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.beginSQLFile(file, moduleVersionGuid);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static void logSQLFileEnd(SQLFile file, String moduleVersionGuid, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.endSQLFile(file, moduleVersionGuid);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static void logSQLExecuted(String sql, String moduleVersionGuid, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.sqlExecuted(sql + ";", moduleVersionGuid);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static void logSQLFailed(String sql, String moduleVersionGuid, Throwable t, int type, SQLExecutionCallBack ... callbacks) {
        for (SQLExecutionCallBack callback : callbacks) {
            try {
                callback.sqlFailed(sql, moduleVersionGuid, t, type);
            }
            catch (Exception e) {
                logger.warn("\u6a21\u5757\u6267\u884c\u811a\u672c\u56de\u8c03\u63a5\u53e3\u6267\u884c\u9519\u8bef", e);
            }
        }
    }

    static String loadSQL(URL url) throws Exception {
        StringBuffer buffer = new StringBuffer();
        try (InputStream is = url.openStream();
             InputStreamReader temp = new InputStreamReader(is, "UTF-8");
             BufferedReader reader = new BufferedReader(temp);){
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        }
        return buffer.toString();
    }

    public static void printSQL(StringBuffer buffer, ModuleWrapper wrapper, IDatabase db) throws Exception {
        buffer.append("---------   generate to ").append(db.getName()).append("   --------\n\n");
        List<LegacyModule> list = wrapper.getLegacies();
        for (LegacyModule lm : list) {
            List<SQLFile> files = lm.getSqlFiles();
            for (SQLFile file : files) {
                URL url = file.getUrl();
                buffer.append("---------   begin sql file ").append(file.getFilename()).append("   ---------\n\n");
                StringBuffer sqlFile = new StringBuffer();
                AbstractSQLOperator.printSQL(sqlFile, url, db);
                String string = sqlFile.toString();
                file.setSql(string);
                buffer.append(string);
                buffer.append("---------   end sql file   ").append(file.getFilename()).append("   ---------\n\n");
            }
        }
        for (SQLFile file : wrapper.getSqlFiles()) {
            URL url = file.getUrl();
            buffer.append("---------   begin sql file ").append(file.getFilename()).append("   ---------\n\n");
            StringBuffer sqlFile = new StringBuffer();
            AbstractSQLOperator.printSQL(sqlFile, url, db);
            String string = sqlFile.toString();
            file.setSql(string);
            buffer.append(string);
            buffer.append("---------   end sql file   ").append(file.getFilename()).append("   ---------\n\n");
        }
    }

    public static void printSQL(StringBuffer buffer, URL url, IDatabase db) throws Exception {
        String sqls = AbstractSQLOperator.loadSQL(url);
        SQLCommandParser parser = new SQLCommandParser();
        List commands = parser.parse(db, sqls);
        for (SQLCommand command : commands) {
            buffer.append(command.toString(db.getName()));
            buffer.append("\n");
        }
    }

    public static interface SQLExecutionCallBack {
        public void beginTask();

        public void endTask();

        public void beginModule(ModuleDescriptor var1, String var2);

        public void endModule(ModuleDescriptor var1, String var2);

        public void beginSQLFile(SQLFile var1, String var2);

        public void sqlExecuted(String var1, String var2);

        public void sqlFailed(String var1, String var2, Throwable var3, int var4);

        public void endSQLFile(SQLFile var1, String var2);
    }
}

