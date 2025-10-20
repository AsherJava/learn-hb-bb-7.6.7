/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateCollectCondi
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePluginDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.penetrate.impl.controller;

import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateCollectCondi;
import com.jiuqi.bde.penetrate.client.dto.PenetratePluginDTO;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateMainService;
import com.jiuqi.bde.penetrate.impl.service.PenetratePluginCollectService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdePenetrateMainController {
    public static final String API_PATH = "/api/bde/v1/penetrate/main";
    public static final String FN_REQUEST_TASKID = "requestTaskId";
    @Autowired
    private BdePenetrateMainService service;
    @Autowired
    PenetratePluginCollectService penetratePluginCollectService;

    @PostMapping(value={"/api/bde/v1/penetrate/main/init/{bizModel}/{penetrateType}"})
    public BusinessResponseEntity<PenetrateInitDTO> init(@PathVariable String bizModel, @PathVariable String penetrateType, @RequestBody Map<String, Object> condi) {
        if (condi.get(FN_REQUEST_TASKID) == null || StringUtils.isEmpty((String)((String)condi.get(FN_REQUEST_TASKID)))) {
            condi.put(FN_REQUEST_TASKID, UUIDUtils.newHalfGUIDStr());
        }
        return BusinessResponseEntity.ok((Object)this.service.init(bizModel, penetrateType, condi));
    }

    @PostMapping(value={"/api/bde/v1/penetrate/main/query/{bizModel}/{penetrateType}"})
    public BusinessResponseEntity<Object> doQuery(@PathVariable String bizModel, @PathVariable String penetrateType, @RequestBody Map<String, Object> condi) {
        if (condi.get(FN_REQUEST_TASKID) == null || StringUtils.isEmpty((String)((String)condi.get(FN_REQUEST_TASKID)))) {
            condi.put(FN_REQUEST_TASKID, UUIDUtils.newHalfGUIDStr());
        }
        return BusinessResponseEntity.ok((Object)this.service.doQuery(bizModel, penetrateType, condi));
    }

    @PostMapping(value={"/api/bde/v1/penetrate/main/customFetch/detailLedger/{bizModel}"})
    public BusinessResponseEntity<Object> customFetchDetailLedger(@PathVariable String bizModel, @RequestBody Map<String, Object> condi) {
        if (condi.get(FN_REQUEST_TASKID) == null || StringUtils.isEmpty((String)((String)condi.get(FN_REQUEST_TASKID)))) {
            condi.put(FN_REQUEST_TASKID, UUIDUtils.newHalfGUIDStr());
        }
        return BusinessResponseEntity.ok((Object)this.service.customFetchDetailLedger(bizModel, condi));
    }

    @PostMapping(value={"/api/bde/v1/penetrate/main/getPluginByCondition"})
    public BusinessResponseEntity<PenetratePluginDTO> getPluginByCondition(@RequestBody PenetrateCollectCondi condi) {
        return BusinessResponseEntity.ok((Object)this.penetratePluginCollectService.getPluginByCondition(condi));
    }
}

