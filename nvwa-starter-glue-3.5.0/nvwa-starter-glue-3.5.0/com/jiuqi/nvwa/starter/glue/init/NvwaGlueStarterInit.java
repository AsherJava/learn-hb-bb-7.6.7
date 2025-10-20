/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.glue.common.GlueEnums$ConfigModeType
 *  com.jiuqi.nvwa.glue.common.GlueRestTemplateRetryCache
 *  com.jiuqi.nvwa.glue.manage.cache.GlueManageCache
 *  com.jiuqi.nvwa.glue.manage.po.ConfigModePO
 *  com.jiuqi.nvwa.glue.manage.service.ConfigModeService
 *  com.jiuqi.nvwa.glue.mq.GlueBindingService
 *  com.jiuqi.nvwa.glue.properties.NvwaGlueProperties
 */
package com.jiuqi.nvwa.starter.glue.init;

import com.jiuqi.nvwa.glue.common.GlueEnums;
import com.jiuqi.nvwa.glue.common.GlueRestTemplateRetryCache;
import com.jiuqi.nvwa.glue.manage.cache.GlueManageCache;
import com.jiuqi.nvwa.glue.manage.po.ConfigModePO;
import com.jiuqi.nvwa.glue.manage.service.ConfigModeService;
import com.jiuqi.nvwa.glue.mq.GlueBindingService;
import com.jiuqi.nvwa.glue.properties.NvwaGlueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class NvwaGlueStarterInit {
    Logger logger = LoggerFactory.getLogger(NvwaGlueStarterInit.class);
    @Autowired
    Environment env;
    @Autowired
    private GlueBindingService glueBindingService;
    @Autowired
    private GlueManageCache glueManageCache;
    @Autowired
    private ConfigModeService configModeService;
    @Autowired
    private GlueRestTemplateRetryCache glueRestTemplateRetryCache;
    @Autowired
    private NvwaGlueProperties nvwaGlueProperties;

    public void init() throws Exception {
        this.bindingConsumer();
        this.glueRestTemplateRetryCache.clear();
    }

    private void bindingConsumer() throws Exception {
        boolean hostOpenSubscribe;
        ConfigModePO glueConfigMode = this.glueManageCache.getMode();
        if (glueConfigMode != null && glueConfigMode.getMode() == GlueEnums.ConfigModeType.GUEST.getValue() && this.glueBindingService.checkMqConfig()) {
            try {
                this.glueBindingService.dynamicBindingConsumer();
            }
            catch (Exception e) {
                this.logger.error("\u52a8\u6001\u7ed1\u5b9a\u80f6\u6c34\u5c42\u6d88\u8d39\u8005\u901a\u9053\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        } else if (glueConfigMode != null && glueConfigMode.getMode() == GlueEnums.ConfigModeType.HOST.getValue() && glueConfigMode.getEnableRealtimeSync() == 1 && !this.glueBindingService.checkMqConfig()) {
            glueConfigMode.setEnableRealtimeSync(0);
            this.configModeService.saveOrUpdate(glueConfigMode);
            this.logger.warn("\u53d6\u6d88\u80f6\u6c34\u5c42\u5b9e\u65f6\u540c\u6b65\u590d\u9009\u6846");
        }
        if ((hostOpenSubscribe = this.nvwaGlueProperties.getSync().isHostOpenSubscribe()) && glueConfigMode != null && glueConfigMode.getMode() == GlueEnums.ConfigModeType.HOST.getValue() && glueConfigMode.getEnableRealtimeSync() == 1 && this.glueBindingService.checkMqConfig()) {
            try {
                this.glueBindingService.dynamicBindingConsumer();
                this.logger.info("\u670d\u52a1\u7aef\u4f5c\u4e3a\u63a5\u5165\u670d\u52a1\u8ba2\u9605\u4e3b\u6570\u636e\u53d8\u52a8\u6210\u529f\uff01");
            }
            catch (Exception e) {
                this.logger.error("\u52a8\u6001\u7ed1\u5b9a\u80f6\u6c34\u5c42\u6d88\u8d39\u8005\u901a\u9053\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
    }

    public void initWhenStarted() throws Exception {
    }
}

