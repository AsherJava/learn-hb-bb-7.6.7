/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.service.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.service.impl.GcEntTableSqlDeclaratorRefreshSubscriber;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class NvwaDeployListener
implements ApplicationListener<DeployTableFinishedEvent> {
    private Logger logger = LoggerFactory.getLogger(NvwaDeployListener.class);
    @Autowired
    private EntDaoCacheManager entDaoCacheManager;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private GcEntTableSqlDeclaratorRefreshSubscriber subscriber;

    @Override
    public void onApplicationEvent(DeployTableFinishedEvent event) {
        List tableDefines;
        try {
            tableDefines = this.dataModelService.getTableModelDefinesByIds((Collection)event.getTableParams().getTable().getRunTimeKeys());
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38\u3002", e);
            return;
        }
        final Set<String> changedTableNameSet = tableDefines.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        if (!this.entDaoCacheManager.hasGcTable(changedTableNameSet)) {
            return;
        }
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCommit() {
                    NvwaDeployListener.this.entDaoCacheManager.refreshEntTableDefines(changedTableNameSet);
                }
            });
        } else {
            this.entDaoCacheManager.refreshEntTableDefines(changedTableNameSet);
        }
        this.subscriber.sendMessage(event.getTableParams());
    }
}

