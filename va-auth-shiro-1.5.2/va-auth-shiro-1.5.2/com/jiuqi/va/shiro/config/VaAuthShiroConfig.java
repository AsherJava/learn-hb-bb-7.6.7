/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.MD5Util
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.SessionListener
 *  org.apache.shiro.session.mgt.SessionManager
 *  org.apache.shiro.session.mgt.eis.MemorySessionDAO
 *  org.apache.shiro.session.mgt.eis.SessionDAO
 *  org.apache.shiro.spring.LifecycleBeanPostProcessor
 *  org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
 *  org.apache.shiro.spring.web.ShiroFilterFactoryBean
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.MD5Util;
import com.jiuqi.va.shiro.config.MySessionManager;
import com.jiuqi.va.shiro.config.MyWebSecurityManage;
import com.jiuqi.va.shiro.config.MyWebSecurityProvider;
import com.jiuqi.va.shiro.config.optimize.MyShiroFilterFactoryBean;
import com.jiuqi.va.shiro.config.redis.MyRedisManager;
import com.jiuqi.va.shiro.config.redis.MyRedisProperties;
import com.jiuqi.va.shiro.config.redis.MyRedisSessionDAO;
import com.jiuqi.va.shiro.config.redis.MyReidsClientConfig;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

@Configuration
@Lazy(value=false)
@EnableConfigurationProperties(value={MyRedisProperties.class})
@ComponentScan(basePackages={"com.jiuqi.va.shiro.config"})
@PropertySource(value={"classpath:va-auth-shiro.properties"})
@ConfigurationProperties(prefix="va-auth-shiro")
public class VaAuthShiroConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(VaAuthShiroConfig.class);
    private static String trustCode = null;
    @Autowired
    private MyWebSecurityProvider myWebSecurityProvider;
    @Autowired(required=false)
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired(required=false)
    private List<SessionListener> sessionListeners;
    @Autowired(required=false)
    private DataSourceProperties dataSourceProperties;
    @Value(value="${va-auth-shiro.session-delay-update-seconds:30}")
    private int sessionDelayUpdateSeconds;
    @Value(value="${va-auth-shiro.sessionid-request-parameter:nv_t}")
    private String sessionidRequestParameter;
    @Value(value="${va-auth-shiro.cookie.enable:false}")
    private boolean cookieEnable = false;
    @Autowired(required=false)
    private MyReidsClientConfig aloneReidsConnConfig;
    private Map<String, List<String>> anon = new HashMap<String, List<String>>();
    private Environment environment;

    public Map<String, List<String>> getAnon() {
        return this.anon;
    }

    public void setAnon(Map<String, List<String>> anon) {
        this.anon = anon;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        EnvConfig.setEnvironment((Environment)environment);
    }

    @Value(value="${va-auth-shiro.trust-code:}")
    public void setTrustCode(String code) {
        if (trustCode != null) {
            return;
        }
        if (StringUtils.hasText(code)) {
            trustCode = code.trim();
            return;
        }
        try {
            if (this.dataSourceProperties != null) {
                String dspCode = this.dataSourceProperties.getUrl().trim() + this.dataSourceProperties.getUsername().trim() + this.dataSourceProperties.getPassword().trim();
                trustCode = MD5Util.encrypt((String)dspCode);
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        trustCode = "f820d5f89057430b8e5f4cbea000b3a4";
    }

    public static String getTrustCode() {
        return trustCode;
    }

    @Bean
    static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(value={"vaShiroSecurityManager"})
    SecurityManager securityManager() {
        MyWebSecurityManage securityManager = this.myWebSecurityProvider.getWebSecurityManager();
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(this.sessionDAO());
        sessionManager.setGlobalSessionTimeout((long)this.getSessionTimeout() * 1000L);
        sessionManager.setDelayUpdateTime(this.sessionDelayUpdateSeconds * 1000);
        sessionManager.setSessionidRequestParameter(this.sessionidRequestParameter);
        sessionManager.setCookieEnabled(this.cookieEnable);
        if (null != this.sessionListeners && !this.sessionListeners.isEmpty()) {
            sessionManager.setSessionListeners(this.sessionListeners);
        }
        String keyPrefix = "va-auth-shiro.anon.";
        String key = null;
        String value = null;
        for (Map.Entry<String, List<String>> entry : this.anon.entrySet()) {
            key = entry.getKey();
            value = this.environment.getProperty(keyPrefix + key);
            StringBuilder sb = new StringBuilder("\u533f\u540d\u8bbf\u95ee\u914d\u7f6e\uff1a");
            sb.append(keyPrefix);
            sb.append(key);
            sb.append("=");
            sb.append(value);
            LOGGER.info(sb.toString());
            if ("openApi".equals(key) && !"/anon/**,/**/anon/**".equals(value)) {
                throw new RuntimeException("\u975e\u6cd5\u7684\u533f\u540d\u8bbf\u95ee\u914d\u7f6e\uff0c \u4e0d\u53ef\u8986\u76d6\u5185\u7f6e\u7684openApi\u89c4\u5219\u914d\u7f6e\u3002");
            }
            if (!"ssoLogin".equals(key) || "/sso/**".equals(value)) continue;
            throw new RuntimeException("\u975e\u6cd5\u7684\u533f\u540d\u8bbf\u95ee\u914d\u7f6e\uff0c \u4e0d\u53ef\u8986\u76d6\u5185\u7f6e\u7684ssoLogin\u89c4\u5219\u914d\u7f6e\u3002");
        }
        this.anon.values().forEach(data -> data.forEach(path -> {
            if (!path.contains("/authc/")) {
                sessionManager.addAnonPathRule((String)path);
            }
        }));
        int anonCnt = sessionManager.getAnonPathRule().size();
        if (anonCnt > 20) {
            LOGGER.warn("*************************************");
            LOGGER.warn("\u300b\u300b\u300b \u533f\u540d\u8bbf\u95ee\u914d\u7f6e\u4e2a\u6570\u5df2\u8fbe\u3010" + anonCnt + "\u3011\u4e2a");
            LOGGER.warn("\u300b\u300b\u300b \u8fc7\u591a\u7684\u914d\u7f6e\uff08\u5927\u4e8e20\u4e2a\uff09\u4f1a\u5bfc\u81f4\u5927\u91cf\u5e76\u53d1\u8bbf\u95ee\u65f6\u51fa\u73b0\u7f13\u6162");
            LOGGER.warn("\u300b\u300b\u300b \u8bf7\u5408\u7406\u4f7f\u7528\u5df2\u6709\u89c4\u5219");
            LOGGER.warn("*************************************");
        }
        securityManager.setSessionManager((SessionManager)sessionManager);
        securityManager.setRememberMeManager(null);
        securityManager.setCacheManager(null);
        return securityManager;
    }

    @Bean(value={"vaShiroSessionDAO"})
    SessionDAO sessionDAO() {
        if (EnvConfig.getRedisEnable()) {
            MyRedisSessionDAO redisSessionDAO = new MyRedisSessionDAO();
            redisSessionDAO.setRedisManager(this.redisManager());
            return redisSessionDAO;
        }
        return new MemorySessionDAO();
    }

    private MyRedisManager redisManager() {
        MyRedisManager myRedisManager = new MyRedisManager();
        RedisTemplate redisTemplate = new RedisTemplate();
        if (this.aloneReidsConnConfig != null) {
            redisTemplate.setConnectionFactory(this.aloneReidsConnConfig.getConnectionFactory());
        } else {
            redisTemplate.setConnectionFactory(this.redisConnectionFactory);
        }
        redisTemplate.afterPropertiesSet();
        redisTemplate.setKeySerializer(null);
        redisTemplate.setValueSerializer(null);
        myRedisManager.setRedisTemplate((RedisTemplate<byte[], byte[]>)redisTemplate);
        myRedisManager.setExpire(this.getSessionTimeout());
        return myRedisManager;
    }

    private int getSessionTimeout() {
        return this.environment.getProperty("server.servlet.session.timeout", Integer.class, 1800);
    }

    @Bean(value={"vaAuthorizationAttributeSourceAdvisor"})
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(value={"vaShiroFilterFactoryBean"})
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        MyShiroFilterFactoryBean shiroFilterFactoryBean = new MyShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/anon/**", "anon");
        filterChainDefinitionMap.put("/**/anon/**", "anon");
        filterChainDefinitionMap.put("/sso/**", "anon");
        this.anon.values().forEach(data -> data.forEach(path -> {
            if (!path.contains("/authc/")) {
                filterChainDefinitionMap.put((String)path, "anon");
            }
        }));
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", this.myWebSecurityProvider.getFormAuthenticationFilter());
        return shiroFilterFactoryBean;
    }
}

