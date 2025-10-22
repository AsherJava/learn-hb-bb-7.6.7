/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.nr.api.RestoreActionClient
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nr.impl.web.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.nr.api.RestoreActionClient;
import com.jiuqi.gcreport.nr.impl.service.ProcessInstancesService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessInstancesController
implements RestoreActionClient {
    @Autowired
    private ProcessInstancesService processInstancesService;
    private static Logger logger = LoggerFactory.getLogger(ProcessInstancesController.class);

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Map<String, Object>> executeAction(String actionParamJson) {
        Map actionParam = (Map)JsonUtils.readValue((String)actionParamJson, Map.class);
        String sourceTaskKey = (String)actionParam.get("sourceTaskKey");
        String targetTaskKey = (String)actionParam.get("targetTaskKey");
        String periodStr = (String)actionParam.get("periodStr");
        HashMap<String, String> map = new HashMap<String, String>();
        logger.info("\u5f00\u59cb\u4fee\u590d");
        this.processInstancesService.restoreProcessInstances(sourceTaskKey, targetTaskKey, periodStr);
        logger.info("\u4fee\u590d\u5b8c\u6210");
        map.put("state", "200");
        map.put("info", "success");
        return BusinessResponseEntity.ok(map);
    }
}

