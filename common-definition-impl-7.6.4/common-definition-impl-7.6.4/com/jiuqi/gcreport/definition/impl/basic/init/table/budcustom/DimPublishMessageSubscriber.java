/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.DimPublishMessage;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.GcCustomModelPublisher;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.service.DimPublishManageService;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"SyncDimPublishChannel"})
public class DimPublishMessageSubscriber
implements MessageSubscriber {
    public static final String PUBLISH_CHANNELS = "SyncDimPublishChannel";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required=false)
    private MessagePublisher messagePublisher;
    @Autowired
    private GcCustomModelPublisher gcCustomModelPublisher;
    @Autowired
    private DimPublishManageService dimPublishManageService;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        int i;
        if (Boolean.TRUE.equals(fromThisInstance)) {
            return;
        }
        DimPublishMessage msg = (DimPublishMessage)JsonUtils.readValue((String)message.toString(), DimPublishMessage.class);
        if (msg == null || msg.getShowModelDTO() == null) {
            this.logger.error("\u6d88\u606f\u4f53\u683c\u5f0f\u9519\u8bef\u5bfc\u81f4\u6a21\u578b\u53d1\u5e03\u5931\u8d25\uff0c\u6d88\u606f\u4f53\uff1a{}", message);
            return;
        }
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List<DefinitionTableV> definitionTableList = entityTableCollector.getModelTableMap().get(msg.getShowModelDTO().getCode());
        if (!CollectionUtils.isEmpty(definitionTableList) && (i = this.dimPublishManageService.updatePublishInfo(msg.getId())) == 1) {
            this.gcCustomModelPublisher.doDimPublish(msg.getShowModelDTO(), entityTableCollector, definitionTableList);
            this.logger.info("\u3010{}\u3011\u6a21\u578b\u53d1\u5e03\u6210\u529f", (Object)msg.getShowModelDTO().getCode());
        }
    }

    public void sendMessage(String message) {
        if (null != this.messagePublisher) {
            this.messagePublisher.publishMessage(PUBLISH_CHANNELS, (Object)message);
        }
    }
}

