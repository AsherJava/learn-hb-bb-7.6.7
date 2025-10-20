/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterRegistrationBean
 *  com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.nvwa.sf.adapter.spring.MDCFilter;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterRegistrationBean;
import com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MDCFilterConfig {
    @Bean
    public NvwaFilterRegistrationBean<MDCFilter> logMDCFilter() {
        NvwaFilterRegistrationBean registrationBean = new NvwaFilterRegistrationBean();
        MDCFilter restMDCFilter = new MDCFilter();
        registrationBean.setOrder(50);
        registrationBean.setFilter((NvwaGenericFilterBean)restMDCFilter);
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}

