/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.common.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaBaseDataSyncCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaBaseDataSyncCacheMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaBaseDataSyncCacheMsgReciver.class);
    @Autowired
    private BaseDataCacheService baseDataCacheService;

    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        BaseDataSyncCacheDTO dmsc = (BaseDataSyncCacheDTO)((Object)JSONUtil.parseObject((String)msg, BaseDataSyncCacheDTO.class));
        if (dmsc == null || dmsc.getBaseDataDTO() == null) {
            logger.error("\u57fa\u7840\u6570\u636e\u6d88\u606f\u53cd\u5e8f\u5217\u5316\u7ed3\u679c\u4e0d\u6b63\u786e");
            return;
        }
        this.baseDataCacheService.handleSyncCacheMsg(dmsc);
    }
}

