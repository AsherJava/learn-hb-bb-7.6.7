/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.tool.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/serverconfig"})
@ConditionalOnBean(value={ServerConfigProperties.class})
public class ServerConfigController {
    @Autowired(required=false)
    private ServerConfigProperties serverConfigProperties;

    @GetMapping(value={"alladdress"})
    public BusinessResponseEntity<Map<String, String>> getServerUrls() {
        if (this.serverConfigProperties == null) {
            return BusinessResponseEntity.ok();
        }
        HashMap serviceKeyMap = new HashMap();
        Map<String, String> serviceNameMap = this.serverConfigProperties.getServiceName();
        Map<String, String> serviceUrlMap = this.serverConfigProperties.getServiceUrl();
        serviceNameMap.forEach((serviceKey, serviceName) -> {
            String serviceUrl = serviceUrlMap == null || serviceUrlMap.size() == 0 || serviceUrlMap.get(serviceKey) == null ? "/" + (String)serviceNameMap.get(serviceKey) : (String)serviceUrlMap.get(serviceKey);
            serviceKeyMap.put(serviceKey, serviceUrl);
        });
        return BusinessResponseEntity.ok(serviceKeyMap);
    }

    @GetMapping(value={"allwebsocketaddress"})
    public BusinessResponseEntity<Map<String, String>> getWebsocketServerUrls() {
        if (this.serverConfigProperties == null) {
            return BusinessResponseEntity.ok();
        }
        Map<String, String> serviceWebsocketUrlMap = this.serverConfigProperties.getServiceWebsocketUrl();
        return BusinessResponseEntity.ok(serviceWebsocketUrlMap);
    }

    @Component
    @RefreshScope
    @ConfigurationProperties(prefix="custom")
    public static class ServerConfigProperties {
        private Map<String, String> serviceUrl = new HashMap<String, String>();
        private Map<String, String> serviceName = new HashMap<String, String>();
        private Map<String, String> serviceWebsocketUrl = new HashMap<String, String>();

        public Map<String, String> getServiceUrl() {
            return this.serviceUrl;
        }

        public void setServiceUrl(Map<String, String> serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public Map<String, String> getServiceName() {
            return this.serviceName;
        }

        public void setServiceName(Map<String, String> serviceName) {
            this.serviceName = serviceName;
        }

        public Map<String, String> getServiceWebsocketUrl() {
            return this.serviceWebsocketUrl;
        }

        public void setServiceWebsocketUrl(Map<String, String> serviceWebsocketUrl) {
            this.serviceWebsocketUrl = serviceWebsocketUrl;
        }
    }
}

