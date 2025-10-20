/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zhuozhengsoft.pageoffice.poserver.Server
 */
package com.jiuqi.common.pageoffice.autoconfigure;

import com.zhuozhengsoft.pageoffice.poserver.Server;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(value={"com.jiuqi.common.pageoffice"})
@PropertySource(value={"classpath:onlineoffice-pageoffice.properties"})
public class PageOfficeAutoConfiguration {
    private final Logger LOGGER = LoggerFactory.getLogger(PageOfficeAutoConfiguration.class);
    public static final String PAGEOFFICE_LICENSE_LIC_LOCATION = System.getProperty("java.io.tmpdir") + File.separator + "AppData" + File.separator + "jiuqi" + File.separator + "pageoffice" + File.separator + "license";

    @Bean
    public ServletRegistrationBean pageofficeRegistrationBean() {
        Server poserver = new Server();
        File poSysPathFile = new File(PAGEOFFICE_LICENSE_LIC_LOCATION);
        if (!poSysPathFile.exists()) {
            poSysPathFile.mkdirs();
        }
        this.LOGGER.info("PageOffice\u6ce8\u518c\u6210\u529f\u540e,license.lic\u6587\u4ef6\u5b58\u653e\u7684\u76ee\u5f55\u4e3a\uff1a{}", (Object)PAGEOFFICE_LICENSE_LIC_LOCATION);
        poserver.setSysPath(PAGEOFFICE_LICENSE_LIC_LOCATION);
        ServletRegistrationBean<Server> srb = new ServletRegistrationBean<Server>(poserver, new String[0]);
        srb.addUrlMappings("/poserver.zz");
        srb.addUrlMappings("/posetup.exe");
        srb.addUrlMappings("/pageoffice.js");
        srb.addUrlMappings("/jquery.min.js");
        srb.addUrlMappings("/pobstyle.css");
        srb.addUrlMappings("/sealsetup.exe");
        srb.addUrlMappings("/adminseal.zz");
        srb.addUrlMappings("/sealimage.zz");
        srb.addUrlMappings("/loginseal.zz");
        return srb;
    }
}

