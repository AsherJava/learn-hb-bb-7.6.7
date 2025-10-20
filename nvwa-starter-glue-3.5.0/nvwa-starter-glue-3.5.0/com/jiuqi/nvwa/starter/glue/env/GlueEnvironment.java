/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.starter.glue.env;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ObjectUtils;

@Configuration
public class GlueEnvironment
implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String defaultBinder = environment.getProperty("spring.cloud.stream.defaultBinder", "");
        if (ObjectUtils.isEmpty(defaultBinder)) {
            return;
        }
        String functionNames = environment.getProperty("spring.cloud.function.definition", "");
        if (ObjectUtils.isEmpty(functionNames)) {
            functionNames = "glueConsumer";
        } else if (!functionNames.contains("glueConsumer")) {
            functionNames = functionNames + ";glueConsumer";
        }
        HashMap<String, Object> map = new HashMap<String, Object>(1);
        map.put("spring.cloud.stream.bindings.glueConsumer-in-0.group", "default");
        map.put("spring.cloud.function.definition", functionNames);
        String glueEnv = "glueEnv";
        MapPropertySource source = new MapPropertySource(glueEnv, (Map<String, Object>)map);
        PropertySource<?> propertySource = environment.getPropertySources().get(glueEnv);
        if (null != propertySource) {
            environment.getPropertySources().remove(glueEnv);
        }
        environment.getPropertySources().addFirst(source);
    }
}

