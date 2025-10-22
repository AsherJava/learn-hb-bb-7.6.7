/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.va.query.fetch.web.FetchQueryClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetch.impl.web;

import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.va.query.fetch.web.FetchQueryClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcFetchController {
    @Autowired
    private FetchQueryClient fetchQueryClient;
    @Autowired
    private FetchDataRequestClient fetchRequestClient;
    @Autowired
    private GcFetchService fetchService;
    private static final String FETCH_API_PREFIX = "/api/gcreport/v1/fetch";

    @PostMapping(value={"/api/gcreport/v1/fetchData"})
    @NRContextBuild
    public AsyncTaskInfo fetchData(@RequestBody EfdcInfo efdcInfo) {
        return this.fetchService.fetchData(efdcInfo);
    }

    @PostMapping(value={"/api/gcreport/v1/batchFetchData"})
    @NRContextBuild
    public AsyncTaskInfo batchFetchData(@RequestBody EfdcInfo efdcInfo) {
        return this.fetchService.batchFetchData(efdcInfo);
    }

    @GetMapping(value={"/api/gcreport/v1/fetchData/query"})
    public AsyncTaskInfo queryFetchTaskInfo(AsyncTaskQueryInfo asyncTaskQueryInfo) {
        return this.fetchService.queryFetchTaskInfo(asyncTaskQueryInfo);
    }

    @GetMapping(value={"/api/gcreport/v1/batchFetchData/query"})
    public AsyncTaskInfo queryBatchFetchTaskInfo(AsyncTaskQueryInfo asyncTaskQueryInfo) {
        return this.fetchService.queryBatchFetchTaskInfo(asyncTaskQueryInfo);
    }

    @PostMapping(value={"/api/gcreport/v1/vaquery/execute/{templateCode}"})
    public BusinessResponseEntity<Object> vaQueryExecute(@PathVariable(value="templateCode") String templateCode, @RequestBody Map<String, Object> params) {
        return BusinessResponseEntity.ok((Object)this.fetchQueryClient.execSql(templateCode, params).getData());
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/task/query"})
    public BusinessResponseEntity<FetchTaskInfoDTO> queryTaskInfo(@RequestBody FetchTaskInfoQueryDTO taskInfoQueryDTO) {
        FetchTaskInfoDTO fetchTaskInfoDTO = (FetchTaskInfoDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.fetchRequestClient.queryTaskInfo(taskInfoQueryDTO));
        fetchTaskInfoDTO.setStandaloneServer(BdeCommonUtil.isStandaloneServer());
        fetchTaskInfoDTO.setAppName(BdeRequestCertifyConfig.getAppName().toUpperCase());
        return BusinessResponseEntity.ok((Object)fetchTaskInfoDTO);
    }
}

