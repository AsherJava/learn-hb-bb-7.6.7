/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.webserviceclient.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.webserviceclient.service.WebserviceClientService;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebserviceClientController {
    static final String API_PATH = "/api/gcreport/v1/webserviceClient";
    @Autowired
    private WebserviceClientService webserviceClientService;

    @ResponseBody
    @PostMapping(value={"/api/gcreport/v1/webserviceClient/executeWebservicClient"})
    BusinessResponseEntity<Object> executeWebservicClient(@RequestBody WebserviceClientParam webserviceClientParam) {
        return BusinessResponseEntity.ok((Object)this.webserviceClientService.publishWebservicClientTask(webserviceClientParam));
    }

    @ResponseBody
    @PostMapping(value={"/api/gcreport/v1/webserviceClient/getWsClientBaseData/{tableName}"})
    BusinessResponseEntity<TableModelDefine> getWsClientBaseData(@PathVariable(value="tableName") String tableName) {
        return BusinessResponseEntity.ok((Object)this.webserviceClientService.getWsClientBaseData(tableName));
    }
}

