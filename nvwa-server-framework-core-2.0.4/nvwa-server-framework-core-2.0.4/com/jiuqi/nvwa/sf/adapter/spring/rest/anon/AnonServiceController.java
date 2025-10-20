/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.SFService;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonServiceController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SFRemoteResourceManage remoteResourceManage;

    @GetMapping(value={"/service"})
    public Response services(String serviceName) {
        for (IServiceManager serviceManagerResource : this.remoteResourceManage.getServiceManagerResources()) {
            SFService service = null;
            try {
                service = serviceManagerResource.getService();
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                continue;
            }
            if (!service.getServiceName().equals(serviceName)) continue;
            return Response.okWithObj(service);
        }
        return Response.error("\u672a\u627e\u5230\u670d\u52a1\uff1a" + serviceName);
    }

    @GetMapping(value={"/services"})
    public Response services(@RequestParam(required=false, name="serviceUrl") boolean serviceUrl) {
        ArrayList<SFService> list = new ArrayList<SFService>();
        for (IServiceManager serviceManagerResource : this.remoteResourceManage.getServiceManagerResources()) {
            SFService service;
            block3: {
                service = null;
                try {
                    service = serviceManagerResource.getService();
                    if (!serviceUrl) break block3;
                    service.setServiceUrl(serviceManagerResource.getServiceUrl());
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                    continue;
                }
            }
            list.add(service);
        }
        return Response.okWithObj(list);
    }
}

