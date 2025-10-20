/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 */
package com.jiuqi.va.join.api.extend;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.config.JoinApiConfig;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.join.api.extend.JoinTemplateExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Primary
@Order(value=-2147483648)
@Component(value="vaJoinPrimaryTemplate")
public class JoinPrimaryTemplate
implements JoinTemplate,
ApplicationListener<StorageSyncFinishedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(JoinPrimaryTemplate.class);
    private static boolean inited = false;
    private static JoinTemplateExtend template = null;
    private static String error = "****************** \u7f3a\u5c11VA-JOIN\u9002\u914d\u5305\u6216\u4e0d\u5b58\u5728\u6709\u6548MQ\u914d\u7f6e *****************";

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        this.init();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        if (inited) {
            return;
        }
        try {
            Map listenerMap;
            Map jtMap = ApplicationContextRegister.getBeansOfType(JoinTemplateExtend.class);
            if (jtMap == null || jtMap.size() == 0) {
                logger.error(error);
                inited = true;
                return;
            }
            String primaryTemplate = JoinApiConfig.getPrimaryTemplate();
            if (StringUtils.hasText(primaryTemplate)) {
                for (Object extend : jtMap.values()) {
                    if (!primaryTemplate.equalsIgnoreCase(extend.getName()) || !extend.isEnabled()) continue;
                    template = extend;
                    break;
                }
            } else if (jtMap.size() == 1) {
                for (Object extend : jtMap.values()) {
                    if (extend == null || !extend.isEnabled()) continue;
                    template = extend;
                }
            } else {
                JoinTemplateExtend extend = (JoinTemplateExtend)jtMap.get("vaJoinRabbitTemplate");
                if (extend != null && extend.isEnabled()) {
                    template = extend;
                }
                if (template == null && (extend = (JoinTemplateExtend)jtMap.get("vaJoinRocketTemplate")) != null && extend.isEnabled()) {
                    template = extend;
                }
                if (template == null && (extend = (JoinTemplateExtend)jtMap.get("vaJoinActiveTemplate")) != null && extend.isEnabled()) {
                    template = extend;
                }
                if (template == null && (extend = (JoinTemplateExtend)jtMap.get("vaJoinIBMTemplate")) != null && extend.isEnabled()) {
                    template = extend;
                }
                if (template == null) {
                    for (JoinTemplateExtend jtextend : jtMap.values()) {
                        if ("rabbitmq".equalsIgnoreCase(jtextend.getName()) || "rocketmq".equalsIgnoreCase(jtextend.getName()) || "activemq".equalsIgnoreCase(jtextend.getName()) || "ibmmq".equalsIgnoreCase(jtextend.getName()) || !jtextend.isEnabled()) continue;
                        template = jtextend;
                        break;
                    }
                }
            }
            if (template == null) {
                logger.error(error);
                inited = true;
                return;
            }
            Map declareMap = ApplicationContextRegister.getBeansOfType(JoinDeclare.class);
            if (declareMap != null && !declareMap.isEmpty()) {
                template.batchAddDeclare(declareMap.values());
            }
            if ((listenerMap = ApplicationContextRegister.getBeansOfType(JoinListener.class)) != null && !listenerMap.isEmpty()) {
                template.batchAddListener(listenerMap.values());
            }
            if (declareMap != null && !declareMap.isEmpty()) {
                template.handleFormerNames(declareMap.values());
            }
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            inited = true;
        }
    }

    private JoinTemplateExtend getTemplate() {
        this.init();
        if (template == null) {
            throw new RuntimeException(error);
        }
        return template;
    }

    @Override
    public boolean isRunning() {
        this.init();
        return template != null;
    }

    @Override
    public R send(String joinName, String message) {
        if (message == null) {
            throw new RuntimeException("Message body is null.");
        }
        if (message.length() > 20000000) {
            throw new RuntimeException("Message body is too large.");
        }
        return this.getTemplate().send(joinName, message);
    }

    @Override
    public ReplyTo sendAndReceive(String joinName, String message) {
        return this.getTemplate().sendAndReceive(joinName, message);
    }

    @Override
    public R addDeclare(JoinDeclare declare) {
        return this.getTemplate().addDeclare(declare);
    }

    @Override
    public R addListener(JoinListener listener) {
        return this.getTemplate().addListener(listener);
    }

    @Override
    public R startListener(String joinName) {
        return this.getTemplate().startListener(joinName);
    }

    @Override
    public R stopListener(String joinName) {
        return this.getTemplate().stopListener(joinName);
    }
}

