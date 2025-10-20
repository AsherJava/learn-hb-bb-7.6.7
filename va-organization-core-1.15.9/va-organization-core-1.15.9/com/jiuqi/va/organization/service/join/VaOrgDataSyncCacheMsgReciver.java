/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.organization.service.join;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaOrgDataSyncCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaOrgDataSyncCacheMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaOrgDataSyncCacheMsgReciver.class);
    @Autowired
    private OrgDataCacheService orgDataCacheService;

    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        OrgDataSyncCacheDTO odscd = (OrgDataSyncCacheDTO)((Object)JSONUtil.parseObject((String)msg, OrgDataSyncCacheDTO.class));
        if (odscd == null || odscd.getOrgDTO() == null) {
            logger.error("\u673a\u6784\u6570\u636e\u6d88\u606f\u53cd\u5e8f\u5217\u5316\u7ed3\u679c\u4e0d\u6b63\u786e");
            return;
        }
        this.orgDataCacheService.handleSyncCacheMsg(odscd);
    }
}

