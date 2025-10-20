/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.ws.Endpoint
 *  org.apache.cxf.Bus
 *  org.apache.cxf.bus.spring.SpringBus
 *  org.apache.cxf.jaxws.EndpointImpl
 *  org.apache.cxf.transport.servlet.CXFServlet
 */
package com.jiuqi.gcreport.webserviceclient.config;

import com.jiuqi.gcreport.webserviceclient.service.WebserviceServerService;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.webserviceclient"})
public class WebserviceClientConfiguration {
    @Autowired
    private WebserviceServerService webserviceServerService;

    @Bean
    @ConditionalOnMissingBean(name={"cxfServletRegistration"})
    public ServletRegistrationBean<CXFServlet> cxfServletRegistration() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/services/*");
    }

    @Bean(name={"cxf"})
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    @Lazy(value=false)
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl((Bus)this.springBus(), (Object)this.webserviceServerService);
        endpoint.publish("/gcreport/wsServer");
        return endpoint;
    }
}

