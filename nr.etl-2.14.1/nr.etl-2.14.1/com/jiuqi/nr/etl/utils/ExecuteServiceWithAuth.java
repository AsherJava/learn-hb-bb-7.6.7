/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.ws.Service
 *  javax.xml.ws.WebEndpoint
 *  javax.xml.ws.WebServiceClient
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.ExecuteServiceWithAuthPortType;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServiceClient(name="ExecuteServiceWithAuth", wsdlLocation="http://10.2.4.169:18011/newetl/services/ExecuteServiceWithAuth?wsdl", targetNamespace="http://service.etl.jiuqi.com")
public class ExecuteServiceWithAuth
extends Service {
    public static final QName SERVICE = new QName("http://service.etl.jiuqi.com", "ExecuteServiceWithAuth");
    public static final QName ExecuteServiceWithAuthHttpPort = new QName("http://service.etl.jiuqi.com", "ExecuteServiceWithAuthHttpPort");
    private static final Logger logger = LoggerFactory.getLogger(ExecuteServiceWithAuth.class);

    public ExecuteServiceWithAuth(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
        logger.info("guowenhui:com.jiuqi.report.utils.etl.impl.ExecuteServiceWithAuth");
    }

    public ExecuteServiceWithAuth(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    @WebEndpoint(name="ExecuteServiceWithAuthHttpPort")
    public ExecuteServiceWithAuthPortType getExecuteServiceWithAuthHttpPort() {
        ExecuteServiceWithAuthPortType porttype = (ExecuteServiceWithAuthPortType)super.getPort(ExecuteServiceWithAuthHttpPort, ExecuteServiceWithAuthPortType.class);
        return porttype;
    }

    public void close() {
    }
}

