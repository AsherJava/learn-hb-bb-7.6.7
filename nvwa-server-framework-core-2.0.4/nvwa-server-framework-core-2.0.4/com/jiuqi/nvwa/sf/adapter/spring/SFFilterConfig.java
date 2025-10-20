/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterRegistrationBean
 *  com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.nvwa.sf.adapter.spring.SFFilter;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterRegistrationBean;
import com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SFFilterConfig {
    @Bean
    public NvwaFilterRegistrationBean<SFFilter> filter(SFFilter sfFilter) {
        NvwaFilterRegistrationBean registrationBean = new NvwaFilterRegistrationBean();
        registrationBean.setFilter((NvwaGenericFilterBean)sfFilter);
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}

