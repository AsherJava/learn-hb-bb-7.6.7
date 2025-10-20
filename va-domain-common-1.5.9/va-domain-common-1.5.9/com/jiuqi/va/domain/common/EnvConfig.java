/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.data.redis.serializer.RedisSerializer
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

@Configuration
@Lazy(value=false)
public class EnvConfig
implements ApplicationRunner {
    private static Boolean redisEnable = null;
    private static Boolean webFluxSupport = null;
    private static Environment environment = null;
    private static StringRedisTemplate stringRedisTemplate = null;
    private static String currNodeId = null;
    private static Set<String> allNodeIds = new HashSet<String>();
    private static long nodePeriod = 90000L;
    public static final String SERVER_NODE_KEY = "#SERVER_NODE_REDIS_KEY#";
    public static final String REDIS_ENABLED_EXPRESSION = "${spring.redis.enabled:true}";
    private static Locale defaultLocale = null;

    @Autowired
    public void setEnv(Environment environment) {
        EnvConfig.environment = environment;
    }

    public static void setEnvironment(Environment environment) {
        EnvConfig.environment = environment;
    }

    private static Environment getEnvironment() {
        if (environment == null) {
            environment = (Environment)ApplicationContextRegister.getBean(Environment.class);
        }
        if (environment == null) {
            throw new RuntimeException("\u670d\u52a1\u5c1a\u672a\u51c6\u5907\u597d\uff0c\u53ef\u5148\u8c03\u7528setEnvironment()\u65b9\u6cd5\u521d\u59cb\u9700\u8981\u7684\u73af\u5883\u3002");
        }
        return environment;
    }

    @Deprecated
    public static boolean getRedisEnable(Environment environment) {
        EnvConfig.environment = environment;
        return EnvConfig.getRedisEnable();
    }

    public static boolean getRedisEnable() {
        if (redisEnable != null) {
            return redisEnable;
        }
        redisEnable = EnvConfig.getEnvironment().getProperty("spring.redis.enabled", Boolean.class, true);
        return redisEnable;
    }

    public static boolean isWebFluxSupport() {
        if (webFluxSupport != null) {
            return webFluxSupport;
        }
        webFluxSupport = EnvConfig.getEnvironment().getProperty("spring.webflux.support", Boolean.class, true);
        return webFluxSupport;
    }

    @Value(value="${spring.application.instance-id:}")
    private void setCurrNodeId(String instanceId) {
        if (StringUtils.hasText(instanceId)) {
            if (!instanceId.matches("^[A-Za-z0-9_-]{1,35}$")) {
                throw new RuntimeException("\u542f\u52a8\u53c2\u6570spring.application.instance-id=" + instanceId + "\u4e0d\u7b26\u5408\u89c4\u5219: ^[A-Za-z0-9_-]{1,35}$");
            }
        } else {
            instanceId = UUID.randomUUID().toString().replace("-", "");
        }
        currNodeId = instanceId.toUpperCase().replace("-", "_");
    }

    public static String getCurrNodeId() {
        return currNodeId;
    }

    public static Set<String> getAllNodeIds() {
        if (allNodeIds.isEmpty()) {
            EnvConfig.reloadNodeInfo();
        }
        return allNodeIds;
    }

    private static StringRedisTemplate getStringRedisTemplate() {
        if (stringRedisTemplate == null) {
            stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        }
        return stringRedisTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!EnvConfig.getRedisEnable()) {
            return;
        }
        try {
            TimerTask timerTask = new TimerTask(){

                @Override
                public void run() {
                    EnvConfig.reloadNodeInfo();
                }
            };
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0L, nodePeriod);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static void reloadNodeInfo() {
        if (!EnvConfig.getRedisEnable()) {
            allNodeIds.add(currNodeId);
            return;
        }
        long systime = System.currentTimeMillis();
        EnvConfig.getStringRedisTemplate().opsForHash().put((Object)SERVER_NODE_KEY, (Object)currNodeId, (Object)("" + systime));
        HashSet<String> nodes = new HashSet<String>();
        Map infos = EnvConfig.getStringRedisTemplate().opsForHash().entries((Object)SERVER_NODE_KEY);
        for (Map.Entry entry : infos.entrySet()) {
            long time = Long.parseLong(entry.getValue().toString());
            if (time + nodePeriod < systime) {
                EnvConfig.getStringRedisTemplate().opsForHash().delete((Object)SERVER_NODE_KEY, new Object[]{entry.getKey()});
                continue;
            }
            String nodeid = entry.getKey().toString();
            nodes.add(nodeid);
            allNodeIds.add(nodeid);
        }
        allNodeIds.removeIf(key -> !nodes.contains(key));
    }

    public static Locale getDefaultLocale() {
        if (defaultLocale != null) {
            return defaultLocale;
        }
        String value = EnvConfig.getEnvironment().getProperty("jiuqi.nvwa.i18n.default", "zh_CN");
        String[] strs = value.split("_");
        defaultLocale = new Locale(strs[0], strs[1]);
        return defaultLocale;
    }

    public static void sendRedisMsg(String channel, String message) {
        StringRedisTemplate template = EnvConfig.getStringRedisTemplate();
        if (template == null) {
            throw new RuntimeException("\u672a\u627e\u5230StringRedisTemplate");
        }
        byte[] rawChannel = RedisSerializer.string().serialize((Object)channel);
        byte[] rawMessage = RedisSerializer.string().serialize((Object)message);
        template.execute(connection -> connection.publish(rawChannel, rawMessage), true);
    }
}

