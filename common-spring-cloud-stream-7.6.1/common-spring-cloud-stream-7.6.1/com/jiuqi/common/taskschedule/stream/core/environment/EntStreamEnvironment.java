/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.taskschedule.stream.core.environment;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.taskschedule.stream.core.entconsumer.EntStreamConsumer;
import java.util.StringJoiner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@Order(value=-2147483648)
public class EntStreamEnvironment
implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        String defaultBinder = environment.getProperty("spring.cloud.stream.default-binder", "");
        if (ObjectUtils.isEmpty(defaultBinder)) {
            return;
        }
        String[] beanNamesForType = applicationContext.getBeanNamesForType(EntStreamConsumer.class);
        StringJoiner joiner = new StringJoiner(";");
        for (String beanName : beanNamesForType) {
            joiner.add(beanName);
        }
        String name = environment.getProperty("spring.cloud.function.definition");
        name = StringUtils.isEmpty((String)name) ? joiner.toString() : name + ";" + joiner;
        ((StandardEnvironment)environment).getSystemProperties().put("spring.cloud.function.definition", name);
    }
}

