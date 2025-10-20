/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.Filter
 *  javax.servlet.Filter
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package com.jiuqi.va.biz.encrypt;

import com.jiuqi.va.biz.encrypt.boot2.CustomForDecryptFilter;
import jakarta.servlet.Filter;
import java.lang.reflect.Method;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DecryptWebConfig
implements WebMvcConfigurer {
    private Boolean versionTwoFlag;

    private boolean isVersionTwo() {
        if (this.versionTwoFlag == null) {
            String springBootVersion = SpringBootVersion.getVersion();
            this.versionTwoFlag = springBootVersion.startsWith("2.");
        }
        return this.versionTwoFlag;
    }

    @Bean
    FilterRegistrationBean<?> customFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("decryptFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(Integer.MAX_VALUE);
        try {
            if (this.isVersionTwo()) {
                CustomForDecryptFilter decryptFilter = new CustomForDecryptFilter();
                Method method = bean.getClass().getMethod("setFilter", javax.servlet.Filter.class);
                method.invoke(bean, decryptFilter);
            } else {
                com.jiuqi.va.biz.encrypt.boot3.CustomForDecryptFilter decryptFilter = new com.jiuqi.va.biz.encrypt.boot3.CustomForDecryptFilter();
                Method method = bean.getClass().getMethod("setFilter", Filter.class);
                method.invoke(bean, decryptFilter);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}

