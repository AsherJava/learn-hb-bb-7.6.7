/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.ws.Endpoint
 *  org.apache.cxf.Bus
 *  org.apache.cxf.bus.spring.SpringBus
 *  org.apache.cxf.jaxws.EndpointImpl
 *  org.apache.cxf.transport.servlet.CXFServlet
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.intermediatelibrary.config;

import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingWebServiceService;
import com.jiuqi.gcreport.intermediatelibrary.service.impl.DataDockingWebServiceServiceImpl;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.jiuqi.gcreport.intermediatelibrary"})
@PropertySource(value={"classpath:datadocking.properties"})
public class IntermediateLibraryRunConfiguration {
    @Autowired
    @Qualifier(value="cxf")
    SpringBus springBus;

    @Bean
    public ServletRegistrationBean<CXFServlet> disServlet() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/services/*");
    }

    @Bean
    public DataDockingWebServiceService dataDockingWebServiceService() {
        return new DataDockingWebServiceServiceImpl();
    }

    @Bean
    public Endpoint dataDockingEndpoint() {
        EndpointImpl endpoint = new EndpointImpl((Bus)this.springBus, (Object)this.dataDockingWebServiceService());
        endpoint.publish("/dataDocking");
        return endpoint;
    }
}

