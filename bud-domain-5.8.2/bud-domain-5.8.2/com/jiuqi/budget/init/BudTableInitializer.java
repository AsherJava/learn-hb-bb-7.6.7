/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.LogUtil
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.LogUtil;
import com.jiuqi.budget.init.BaseStorage;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class BudTableInitializer
implements ApplicationInitialization {
    private static final Logger logger = LoggerFactory.getLogger(BudTableInitializer.class);
    private final List<BaseStorage> storages;
    @Qualifier(value="budAsyncBizExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public BudTableInitializer(List<BaseStorage> storages) {
        this.storages = storages;
    }

    public void init(boolean isSysTenant) {
        long time = System.currentTimeMillis();
        LogUtil.logIfInfo((Logger)logger, (String)"\u5f00\u59cbBUD\u540c\u6b65\u8868\u7ed3\u6784", (Object[])new Object[0]);
        JDialectUtil jDialect = JDialectUtil.getInstance();
        int size = this.storages.size();
        CountDownLatch tableInitLatch = new CountDownLatch(size);
        int i = 0;
        while (i < size) {
            BaseStorage storage = this.storages.get(i);
            int finalI = i++;
            this.threadPoolTaskExecutor.submit(() -> {
                block6: {
                    try {
                        if (!storage.beforeCreate()) break block6;
                        JTableModel table = storage.createTable();
                        LogUtil.logIfInfo((Logger)logger, (String)"\u8fdb\u5ea6[{}/{}]:\u5f00\u59cb\u540c\u6b65\u8868[{}]\uff0c\u79df\u6237[{}]", (Object[])new Object[]{finalI + 1, size, table.getTableName(), table.getTenantName()});
                        try {
                            if (!jDialect.hasTable(table)) {
                                jDialect.createTable(table);
                                break block6;
                            }
                            jDialect.updateTable(table);
                        }
                        catch (Exception e) {
                            logger.error("\u8868[{}]\u540c\u6b65\u5931\u8d25", (Object)table.getTableName());
                            logger.error(e.getMessage(), e);
                        }
                    }
                    finally {
                        tableInitLatch.countDown();
                    }
                }
            });
        }
        try {
            tableInitLatch.await();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BudgetException((Throwable)e);
        }
        LogUtil.logIfInfo((Logger)logger, (String)"BUD\u540c\u6b65\u8868\u7ed3\u6784\u7ed3\u675f\uff0c\u8017\u65f6[{}]\u6beb\u79d2", (Object[])new Object[]{System.currentTimeMillis() - time});
        time = System.currentTimeMillis();
        LogUtil.logIfInfo((Logger)logger, (String)"\u5f00\u59cb\u5185\u7f6eBUD\u9ed8\u8ba4\u6570\u636e", (Object[])new Object[0]);
        this.storages.stream().filter(BaseStorage::beforeCreate).forEach(baseStorage -> {
            try {
                baseStorage.afterCreate();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        LogUtil.logIfInfo((Logger)logger, (String)"\u5185\u7f6e\u6570\u636e\u5b8c\u6bd5\uff0c\u8017\u65f6[{}]\u6beb\u79d2", (Object[])new Object[]{System.currentTimeMillis() - time});
    }
}

