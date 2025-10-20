/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.nvwa.definition.common.event.DataModelDefinitionChangeListener
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableParams
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nvwa.definition.common.event.DataModelDefinitionChangeListener;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableParams;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"GcEntTableSqlDeclaratorRefreshSubscriber"})
public class GcEntTableSqlDeclaratorRefreshSubscriber
implements MessageSubscriber {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String CHANNEL = "GcEntTableSqlDeclaratorRefreshSubscriber";
    @Autowired(required=false)
    private MessagePublisher messagePublisher;
    @Autowired(required=false)
    private List<DataModelDefinitionChangeListener> dataModelListeners;
    @Autowired
    private EntDaoCacheManager entDaoCacheManager;
    @Autowired
    private DataModelService dataModelService;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        List tableDefines;
        if (message == null) {
            return;
        }
        DeployTableParams deployTableParams = (DeployTableParams)JsonUtils.readValue((String)message.toString(), DeployTableParams.class);
        if (this.dataModelListeners != null) {
            for (DataModelDefinitionChangeListener listener : this.dataModelListeners) {
                try {
                    listener.onDeploy(deployTableParams);
                }
                catch (Exception e) {
                    this.logger.error("\u54cd\u5e94\u53c2\u6570\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u53c2\u6570\u53d1\u5e03\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), e);
                }
            }
        }
        try {
            tableDefines = this.dataModelService.getTableModelDefinesByIds((Collection)deployTableParams.getTable().getRunTimeKeys());
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38\u3002", e);
            return;
        }
        Set<String> changedTableNameSet = tableDefines.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        this.entDaoCacheManager.refreshEntTableDefines(changedTableNameSet);
    }

    public void sendMessage(DeployTableParams deployTableParams) {
        if (this.messagePublisher == null) {
            this.logger.error("\u6d88\u606f\u53d1\u9001\u5668\u4e0d\u5b58\u5728\uff0c\u8df3\u8fc7\u53d1\u9001\u5408\u5e76dao\u5c42sql\u6784\u9020\u5668\u5237\u65b0\u6d88\u606f\uff0c\u6d88\u606f\u5185\u5bb9:" + JsonUtils.writeValueAsString((Object)deployTableParams));
            return;
        }
        this.messagePublisher.publishMessage(CHANNEL, (Object)JsonUtils.writeValueAsString((Object)deployTableParams));
    }
}

