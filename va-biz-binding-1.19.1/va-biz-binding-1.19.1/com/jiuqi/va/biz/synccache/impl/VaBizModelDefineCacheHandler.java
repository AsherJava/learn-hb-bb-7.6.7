/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.synccache.impl;

import com.jiuqi.va.biz.impl.model.ModelDefineCacheServiceImpl;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.synccache.VaBizBindCacheHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VaBizModelDefineCacheHandler
implements VaBizBindCacheHandler {
    private static final Logger logger = LoggerFactory.getLogger(VaBizModelDefineCacheHandler.class);
    @Autowired
    ModelDefineCacheServiceImpl modelDefineCacheService;
    @Autowired
    private ModelManager modelManager;

    @Override
    public boolean enableWhileIsCurrNode() {
        return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void handle(Map<String, Object> messageObj, String tenantName) {
        String msg = (String)messageObj.get("MODELDEFINE_CACHE");
        if (msg == null) {
            return;
        }
        String[] split = msg.split("#");
        if (split.length < 6) {
            return;
        }
        this.modelManager.getModelList("bill").stream().filter(item -> {
            String modelName = item.getName();
            return StringUtils.hasText(modelName) && modelName.equals(split[5]);
        }).findFirst().ifPresent(item -> {
            try {
                this.modelDefineCacheService.handleCache(tenantName, split[0], Long.valueOf(split[1]), true);
            }
            catch (Exception e) {
                this.modelDefineCacheService.handleStatusCache(split[0], Long.valueOf(split[1]), 0, true);
                logger.error("\u66f4\u65b0\u6a21\u578b\u5b9a\u4e49\u7f13\u5b58\u5931\u8d25\uff08{}\uff09\uff0c{}", msg, e.getMessage(), e);
            }
        });
    }
}

