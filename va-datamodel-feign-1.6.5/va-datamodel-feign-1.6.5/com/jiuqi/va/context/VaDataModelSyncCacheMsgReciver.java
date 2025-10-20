/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.context;

import com.jiuqi.va.context.DataModelSyncCacheChannel;
import com.jiuqi.va.context.DataModelSyncCacheDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaDataModelSyncCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaDataModelSyncCacheMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaDataModelSyncCacheMsgReciver.class);
    @Autowired(required=false)
    private List<DataModelSyncCacheChannel> dataModelCacheChannelList;

    public void onMessage(Message message, byte[] pattern) {
        if (this.dataModelCacheChannelList == null) {
            return;
        }
        String msg = new String(message.getBody());
        DataModelSyncCacheDTO dmsc = (DataModelSyncCacheDTO)((Object)JSONUtil.parseObject((String)msg, DataModelSyncCacheDTO.class));
        if (dmsc == null) {
            logger.error("\u7f13\u5b58\u6d88\u606f\u53cd\u5e8f\u5217\u5316\u7ed3\u679c\u4e3anull");
            return;
        }
        for (DataModelSyncCacheChannel dataModelSyncCacheChannel : this.dataModelCacheChannelList) {
            try {
                dataModelSyncCacheChannel.execute(dmsc);
            }
            catch (Throwable e) {
                logger.error("\u7f13\u5b58\u6269\u5c55\u6267\u884c\u5931\u8d25", e);
            }
        }
    }
}

