/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.AbstractSQLOperator;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import com.jiuqi.nvwa.sf.operator.SQLPreOperator;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModulePreUpdater {
    private List<AbstractSQLOperator.SQLExecutionCallBack> callbacks = new ArrayList<AbstractSQLOperator.SQLExecutionCallBack>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Connection conn;
    private Framework framework;

    public ModulePreUpdater(Connection conn, Framework framework) {
        this.conn = conn;
        this.framework = framework;
        this.callbacks.add(new AbstractSQLOperator.SQLExecutionCallBack(){

            @Override
            public void sqlFailed(String sql, String moduleVersionGuid, Throwable cause, int type) {
                ModulePreUpdater.this.logger.error("  SQL\u5f02\u5e38\uff1a \n{}", (Object)sql, (Object)cause);
            }

            @Override
            public void sqlExecuted(String sql, String moduleVersionGuid) {
                ModulePreUpdater.this.logger.info("  SQL: \n{}", (Object)sql);
            }

            @Override
            public void endSQLFile(SQLFile file, String moduleVersionGuid) {
                ModulePreUpdater.this.logger.info("  \u811a\u672c\u6587\u4ef6[{}]\u5bfc\u51fa\u5b8c\u6bd5", (Object)file);
            }

            @Override
            public void endModule(ModuleDescriptor module, String moduleVersionGuid) {
                ModulePreUpdater.this.logger.info("[{}]\u6a21\u5757\u811a\u672c\u5bfc\u51fa\u5b8c\u6bd5", (Object)module);
            }

            @Override
            public void beginSQLFile(SQLFile file, String moduleVersionGuid) {
                ModulePreUpdater.this.logger.info("  \u5f00\u59cb\u5bfc\u51fa\u811a\u672c\u6587\u4ef6[{}]", (Object)file);
            }

            @Override
            public void beginTask() {
            }

            @Override
            public void endTask() {
            }

            @Override
            public void beginModule(ModuleDescriptor module, String moduleVersionGuid) {
                ModulePreUpdater.this.logger.info("\u5f00\u59cb\u5bfc\u51fa[{}]\u6a21\u5757\u7684\u811a\u672c", (Object)module);
            }
        });
    }

    public void addCallback(AbstractSQLOperator.SQLExecutionCallBack callback) {
        this.callbacks.add(callback);
    }

    public void exportAll() throws Exception {
        ModuleUpgradeLockOperator.LockInfo lock = ModuleUpgradeLockOperator.acquireLock("ALL");
        if (lock.isLock()) {
            try {
                SQLPreOperator.exportAllSQL(this.conn, this.framework, this.framework.getCurrentDatabase(), this.callbacks.toArray(new AbstractSQLOperator.SQLExecutionCallBack[0]));
            }
            catch (Throwable e) {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, e.getMessage());
                throw e;
            }
            finally {
                ModuleUpgradeLockOperator.releaseLock();
            }
        } else {
            throw new Exception("\u5b58\u5728\u6b63\u5728\u6267\u884c\u5347\u7ea7\u7684\u4efb\u52a1\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
    }

    public void executeForward() throws Exception {
        ModuleUpgradeLockOperator.LockInfo lock = ModuleUpgradeLockOperator.acquireLock("ALL");
        if (lock.isLock()) {
            try {
                SQLPreOperator.executeForward(this.conn, this.framework, this.framework.getCurrentDatabase(), this.callbacks.toArray(new AbstractSQLOperator.SQLExecutionCallBack[0]));
                this.framework.updateServerStatus();
            }
            catch (Throwable e) {
                ModuleUpgradeLockOperator.doLog(this.conn, 4, e.getMessage());
                throw e;
            }
            finally {
                ModuleUpgradeLockOperator.releaseLock();
            }
        } else {
            throw new Exception("\u5b58\u5728\u6b63\u5728\u6267\u884c\u5347\u7ea7\u7684\u4efb\u52a1\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
    }
}

