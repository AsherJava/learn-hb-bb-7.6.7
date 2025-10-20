/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.ExternalClassCommand
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.ExternalClassCommand;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.adapter.spring.SpringAdapter;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.AbstractSQLOperator;
import com.jiuqi.nvwa.sf.operator.CustomSqlCommandContext;
import com.jiuqi.nvwa.sf.operator.VersionOperator;
import com.jiuqi.sf.module.ModuleUpdateExecutor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLOperator
extends AbstractSQLOperator {
    private static Logger logger = LoggerFactory.getLogger(SQLOperator.class);

    public static void executeAllSQL(Connection conn, Framework framework, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack ... callbacks) throws Exception {
        try {
            LicenceManager manager = framework.getLicenceManager();
            manager.outOfService(framework.getProductId());
        }
        catch (Exception e) {
            SQLOperator.logSQLFailed("\u65e0\u6cd5\u6267\u884c\u6a21\u5757\u5347\u7ea7", "", e, 1, callbacks);
            logger.error("\u65e0\u6cd5\u6267\u884c\u6a21\u5757\u5347\u7ea7", e);
            return;
        }
        for (ModuleDescriptor module : framework.getModules()) {
            ModuleWrapper mw = framework.getModuleWrappers().get(module.getId());
            if (mw.getStatus().equalsIgnoreCase("unmatch")) continue;
            SQLOperator.executeSQL(conn, framework, mw, db, callbacks);
        }
    }

    public static void executeSQL(Connection conn, Framework framework, ModuleWrapper wrapper, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack ... callbacks) throws Exception {
        if (!wrapper.getStatus().equalsIgnoreCase("tobeupdate")) {
            logger.info("\u6a21\u5757[{}]\u5df2\u7ecf\u6210\u529f\u5347\u7ea7\uff0c\u65e0\u9700\u91cd\u590d\u6267\u884c", (Object)wrapper.getModule());
            return;
        }
        try {
            LicenceManager manager = framework.getLicenceManager();
            manager.outOfService(framework.getProductId());
        }
        catch (Exception e) {
            SQLOperator.logSQLFailed("\u65e0\u6cd5\u6267\u884c\u6a21\u5757\u5347\u7ea7", "", e, 1, callbacks);
            logger.error("\u65e0\u6cd5\u6267\u884c\u6a21\u5757\u5347\u7ea7", e);
            return;
        }
        String moduleVersionGuid = VersionOperator.insertModuleVersion(conn, wrapper.getModule().getId(), wrapper.getModuleVersion().toString(), wrapper.getDbVersion().toString());
        SQLOperator.logModuleBegin(wrapper.getModule(), moduleVersionGuid, callbacks);
        for (LegacyModule lm : wrapper.getLegacies()) {
            for (SQLFile file : lm.getSqlFiles()) {
                SQLOperator.logSQLFileBegin(file, moduleVersionGuid, callbacks);
                SQLOperator.executeSQLFile(conn, moduleVersionGuid, file, db, callbacks);
                if (VersionOperator.existLegacyVersion(conn, lm.getId())) {
                    VersionOperator.updateLegacyVersion(conn, lm.getId(), file.getVersion().toString());
                } else {
                    VersionOperator.insertLegacyVersion(conn, lm.getId(), file.getVersion().toString());
                }
                SQLOperator.logSQLFileEnd(file, moduleVersionGuid, callbacks);
            }
        }
        for (SQLFile file : wrapper.getSqlFiles()) {
            SQLOperator.logSQLFileBegin(file, moduleVersionGuid, callbacks);
            SQLOperator.executeSQLFile(conn, moduleVersionGuid, file, db, callbacks);
            SQLOperator.logSQLFileEnd(file, moduleVersionGuid, callbacks);
        }
        SQLOperator.logModuleEnd(wrapper.getModule(), moduleVersionGuid, callbacks);
        VersionOperator.markVersion(conn, moduleVersionGuid);
        wrapper.setStatus("updated");
    }

    private static void executeSQLFile(Connection conn, String moduleVersionGuid, SQLFile file, IDatabase db, AbstractSQLOperator.SQLExecutionCallBack ... callbacks) throws Exception {
        String sqls = SQLOperator.loadSQL(file.getUrl());
        SQLCommandParser parser = new SQLCommandParser();
        List commands = null;
        try {
            commands = parser.parse(db, sqls);
        }
        catch (Exception e) {
            SQLOperator.logSQLFailed("", moduleVersionGuid, e, 1, callbacks);
        }
        for (SQLCommand command : commands) {
            try {
                if (command instanceof ExternalClassCommand) {
                    ExternalClassCommand ecc = (ExternalClassCommand)command;
                    String className = ecc.getClassName();
                    Class<?> clazz = Class.forName(className);
                    if (ModuleUpdateExecutor.class.isAssignableFrom(clazz)) {
                        ModuleUpdateExecutor executor = (ModuleUpdateExecutor)clazz.newInstance();
                        new SpringAdapter().execUpdateExecutorCompatible(executor);
                    } else {
                        Method[] methods = clazz.getMethods();
                        boolean hasFoundMethod = false;
                        for (Method method : methods) {
                            if (!method.getName().equals("execute")) continue;
                            CustomSqlCommandContext customSqlCommandContext = new CustomSqlCommandContext();
                            customSqlCommandContext.setPreExecuteMode(false);
                            if (!new SpringAdapter().execSQLUpdateExecutor(method, clazz.newInstance(), customSqlCommandContext)) continue;
                            hasFoundMethod = true;
                            break;
                        }
                        if (!hasFoundMethod) {
                            logger.warn("\u5347\u7ea7\u811a\u672c\u6267\u884c\u7c7b\u672a\u627e\u5230\u53ef\u4ee5\u6267\u884c\u7684\u65b9\u6cd5[{}]", (Object)className);
                        }
                    }
                } else {
                    command.execute(conn);
                }
                SQLOperator.logSQLExecuted(command.toString(db.getName()), moduleVersionGuid, callbacks);
            }
            catch (Exception e) {
                SQLOperator.logSQLFailed(command.toString(db.getName()), moduleVersionGuid, e, 0, callbacks);
            }
        }
    }
}

