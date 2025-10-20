/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.ExternalClassCommand
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.util.type.GUID
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.ExternalClassCommand;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.adapter.spring.SpringAdapter;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.AbstractSQLOperator;
import com.jiuqi.nvwa.sf.operator.CustomSqlCommandContext;
import com.jiuqi.nvwa.sf.operator.VersionOperator;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLPreOperator
extends AbstractSQLOperator {
    private static final Logger logger = LoggerFactory.getLogger(SQLPreOperator.class);

    public static void exportAllSQL(Connection conn, Framework framework, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack[] callbacks) throws Exception {
        for (ModuleDescriptor module : framework.getModules()) {
            ModuleWrapper mw = framework.getModuleWrappers().get(module.getId());
            if (mw.getStatus().equalsIgnoreCase("unmatch")) continue;
            SQLPreOperator.exportSQL(conn, framework, mw, db, callbacks);
        }
    }

    private static void exportSQL(Connection conn, Framework framework, ModuleWrapper wrapper, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack[] callbacks) throws Exception {
        if (!wrapper.getStatus().equalsIgnoreCase("tobeupdate")) {
            logger.info("\u6a21\u5757[{}]\u5df2\u7ecf\u6210\u529f\u5347\u7ea7\uff0c\u65e0\u9700\u91cd\u590d\u6267\u884c", (Object)wrapper.getModule());
            return;
        }
        String moduleVersionGuid = GUID.newGUID();
        String insertModuleVersionSql = String.format("insert into %s values ('%s','%s',%s,'%s','%s',%s)", "SF_VERSION", wrapper.getModule().getId(), wrapper.getModuleVersion().toString(), new Date().getTime(), wrapper.getDbVersion().toString(), moduleVersionGuid, 0);
        SQLPreOperator.logSQLExecuted(insertModuleVersionSql, moduleVersionGuid, callbacks);
        for (LegacyModule lm : wrapper.getLegacies()) {
            for (SQLFile file : lm.getSqlFiles()) {
                SQLPreOperator.logSQLFileBegin(file, moduleVersionGuid, callbacks);
                SQLPreOperator.exportSQLFile(conn, moduleVersionGuid, file, db, callbacks);
                SQLPreOperator.logSQLExecuted(String.format("delete from %s where %s = '%s'", "NP_DB_VERSION", "MODULEID", lm.getId()), moduleVersionGuid, callbacks);
                SQLPreOperator.logSQLExecuted(String.format("insert into %s values ('%s', '%s')", "NP_DB_VERSION", lm.getId(), lm.getVersion()), moduleVersionGuid, callbacks);
                SQLPreOperator.logSQLFileEnd(file, moduleVersionGuid, callbacks);
            }
        }
        for (SQLFile file : wrapper.getSqlFiles()) {
            SQLPreOperator.logSQLFileBegin(file, moduleVersionGuid, callbacks);
            SQLPreOperator.exportSQLFile(conn, moduleVersionGuid, file, db, callbacks);
            SQLPreOperator.logSQLFileEnd(file, moduleVersionGuid, callbacks);
        }
        SQLPreOperator.logModuleEnd(wrapper.getModule(), moduleVersionGuid, callbacks);
        SQLPreOperator.logSQLExecuted(String.format("update %s set %s = 2 where %s = '%s'", "SF_VERSION", "MODULETAG", "GUID", moduleVersionGuid), moduleVersionGuid, callbacks);
    }

    public static void executeForward(Connection conn, Framework framework, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack[] callbacks) throws Exception {
        for (ModuleDescriptor module : framework.getModules()) {
            ModuleWrapper mw = framework.getModuleWrappers().get(module.getId());
            if (mw.getStatus().equalsIgnoreCase("unmatch")) continue;
            SQLPreOperator.forwardSQL(conn, framework, mw, db, callbacks);
        }
    }

    private static void exportSQLFile(Connection conn, String moduleVersionGuid, SQLFile file, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack ... callbacks) throws Exception {
        String sqls = SQLPreOperator.loadSQL(file.getUrl());
        SQLCommandParser parser = new SQLCommandParser();
        List commands = null;
        try {
            commands = parser.parse(db, sqls);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            SQLPreOperator.logSQLFailed("", moduleVersionGuid, e, 1, callbacks);
        }
        for (SQLCommand command : commands) {
            try {
                if (command instanceof ExternalClassCommand) {
                    SQLPreOperator.logSQLExecuted(" -- " + command, moduleVersionGuid, callbacks);
                    ExternalClassCommand ecc = (ExternalClassCommand)command;
                    String className = ecc.getClassName();
                    Class<?> clazz = Class.forName(className);
                    Method preExecuteSql = null;
                    try {
                        preExecuteSql = clazz.getMethod("preExecuteSql", DataSource.class, CustomSqlCommandContext.class);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                    if (preExecuteSql == null) continue;
                    SQLPreOperator.logSQLExecuted(new SpringAdapter().preExecuteSql(preExecuteSql, clazz.newInstance()), moduleVersionGuid, callbacks);
                    continue;
                }
                SQLPreOperator.logSQLExecuted(command.toString(db.getName()), moduleVersionGuid, callbacks);
            }
            catch (Exception e) {
                SQLPreOperator.logSQLFailed(command.toString(db.getName()), moduleVersionGuid, e, 0, callbacks);
            }
        }
    }

    private static void forwardSQL(Connection conn, Framework framework, ModuleWrapper wrapper, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack[] callbacks) throws Exception {
        if (!wrapper.getStatus().equalsIgnoreCase("preupdate")) {
            logger.info("\u6a21\u5757[{}]\u5df2\u7ecf\u6210\u529f\u5347\u7ea7\uff0c\u65e0\u9700\u91cd\u590d\u6267\u884c", (Object)wrapper.getModule());
            return;
        }
        for (LegacyModule lm : wrapper.getLegacies()) {
            for (SQLFile file : lm.getSqlFiles()) {
                SQLPreOperator.forwardClassFile(conn, file, db, callbacks);
            }
        }
        for (SQLFile file : wrapper.getSqlFiles()) {
            SQLPreOperator.forwardClassFile(conn, file, db, callbacks);
        }
        VersionOperator.markVersionById(conn, wrapper.getModule().getId());
        wrapper.setStatus("updated");
    }

    private static void forwardClassFile(Connection conn, SQLFile file, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack[] callbacks) throws Exception {
        String sqls = SQLPreOperator.loadSQL(file.getUrl());
        SQLCommandParser parser = new SQLCommandParser();
        List commands = null;
        try {
            commands = parser.parse(db, sqls);
        }
        catch (Exception e) {
            SQLPreOperator.logSQLFailed(sqls, "", e, 1, callbacks);
        }
        for (SQLCommand command : commands) {
            try {
                CustomSqlCommandContext customSqlCommandContext2;
                if (!(command instanceof ExternalClassCommand)) continue;
                ExternalClassCommand ecc = (ExternalClassCommand)command;
                String className = ecc.getClassName();
                Class<?> clazz = Class.forName(className);
                Method method = null;
                try {
                    method = clazz.getMethod("execute", DataSource.class, CustomSqlCommandContext.class);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
                if (method != null) {
                    customSqlCommandContext2 = new CustomSqlCommandContext();
                    customSqlCommandContext2.setPreExecuteMode(true);
                    new SpringAdapter().execSQLUpdateExecutor(method, clazz.newInstance(), customSqlCommandContext2);
                } else {
                    try {
                        method = clazz.getMethod("execute", DataSource.class);
                    }
                    catch (NoSuchMethodException customSqlCommandContext2) {
                        // empty catch block
                    }
                    if (method != null) {
                        customSqlCommandContext2 = new CustomSqlCommandContext();
                        customSqlCommandContext2.setPreExecuteMode(true);
                        new SpringAdapter().execSQLUpdateExecutor(method, clazz.newInstance(), customSqlCommandContext2);
                    } else {
                        logger.warn("\u5347\u7ea7\u811a\u672c\u6267\u884c\u7c7b\u672a\u627e\u5230\u53ef\u4ee5\u6267\u884c\u7684\u65b9\u6cd5[{}]", (Object)className);
                    }
                }
                SQLPreOperator.logSQLExecuted("\u6587\u4ef6\uff1a" + className + " \u6267\u884c\u5b8c\u6bd5", "", callbacks);
            }
            catch (Exception e) {
                SQLPreOperator.logSQLFailed(command.toString(db.getName()), "", e, 0, callbacks);
            }
        }
    }
}

