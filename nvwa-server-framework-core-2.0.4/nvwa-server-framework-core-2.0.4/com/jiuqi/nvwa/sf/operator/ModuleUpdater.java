/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.AbstractSQLOperator;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import com.jiuqi.nvwa.sf.operator.SQLOperator;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleUpdater {
    private List<AbstractSQLOperator.SQLExecutionCallBack> callbacks = new ArrayList<AbstractSQLOperator.SQLExecutionCallBack>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Connection conn;
    private Framework framework;

    public ModuleUpdater(Connection conn, Framework framework) {
        this.conn = conn;
        this.framework = framework;
        this.callbacks.add(new AbstractSQLOperator.SQLExecutionCallBack(){

            @Override
            public void sqlFailed(String sql, String moduleVersionGuid, Throwable cause, int type) {
                ModuleUpdater.this.logger.error("  \u6267\u884cSQL\u5931\u8d25\uff1a \n{}", (Object)sql, (Object)cause);
            }

            @Override
            public void sqlExecuted(String sql, String moduleVersionGuid) {
                ModuleUpdater.this.logger.info("  \u6b63\u5728\u6267\u884cSQL: \n{}", (Object)sql);
            }

            @Override
            public void endSQLFile(SQLFile file, String moduleVersionGuid) {
                ModuleUpdater.this.logger.info("  \u811a\u672c\u6587\u4ef6[{}]\u6267\u884c\u5b8c\u6bd5", (Object)file);
            }

            @Override
            public void endModule(ModuleDescriptor module, String moduleVersionGuid) {
                ModuleUpdater.this.logger.info("[{}]\u6a21\u5757\u811a\u672c\u6267\u884c\u5b8c\u6bd5", (Object)module);
            }

            @Override
            public void beginSQLFile(SQLFile file, String moduleVersionGuid) {
                ModuleUpdater.this.logger.info("  \u5f00\u59cb\u6267\u884c\u811a\u672c\u6587\u4ef6[{}]", (Object)file);
            }

            @Override
            public void beginTask() {
            }

            @Override
            public void endTask() {
            }

            @Override
            public void beginModule(ModuleDescriptor module, String moduleVersionGuid) {
                ModuleUpdater.this.logger.info("\u5f00\u59cb\u6267\u884c[{}]\u6a21\u5757\u7684\u811a\u672c", (Object)module);
            }
        });
    }

    public void addCallback(AbstractSQLOperator.SQLExecutionCallBack callback) {
        this.callbacks.add(callback);
    }

    public void executeAll() throws Exception {
        ModuleUpgradeLockOperator.LockInfo lock = ModuleUpgradeLockOperator.acquireLock("ALL");
        if (lock.isLock()) {
            try {
                SQLOperator.executeAllSQL(this.conn, this.framework, this.framework.getCurrentDatabase(), this.callbacks.toArray(new AbstractSQLOperator.SQLExecutionCallBack[0]));
            }
            catch (Throwable e) {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, e.getMessage());
                ModuleUpgradeLockOperator.releaseLock();
                throw e;
            }
            finally {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, "EOF");
                ModuleUpgradeLockOperator.releaseLock();
            }
        } else {
            throw new Exception("\u670d\u52a1\u6b63\u5728\u6267\u884c\u5347\u7ea7\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        this.framework.updateServerStatus();
    }

    public void executeSingle(ModuleWrapper wrapper) throws Exception {
        if (wrapper.getStatus().equalsIgnoreCase("unmatch")) {
            throw new Exception("\u6a21\u5757\u7248\u672c\u65e0\u6cd5\u5339\u914d\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u5347\u7ea7\u64cd\u4f5c");
        }
        ModuleUpgradeLockOperator.LockInfo lock = ModuleUpgradeLockOperator.acquireLock(wrapper.getModule().getId());
        if (lock.isLock()) {
            try {
                SQLOperator.executeSQL(this.conn, this.framework, wrapper, this.framework.getCurrentDatabase(), this.callbacks.toArray(new AbstractSQLOperator.SQLExecutionCallBack[0]));
            }
            catch (Throwable e) {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, e.getMessage());
                ModuleUpgradeLockOperator.releaseLock();
                throw e;
            }
            finally {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, "EOF");
                ModuleUpgradeLockOperator.releaseLock();
            }
        } else {
            throw new Exception("\u670d\u52a1\u6b63\u5728\u6267\u884c\u5347\u7ea7\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        this.framework.updateServerStatus();
    }
}

