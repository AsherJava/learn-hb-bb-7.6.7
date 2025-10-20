/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchDataRequestFeign
implements FetchDataRequestClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<Boolean> doInit(FetchInitTaskDTO fetchInitTaskDTO) {
        FetchDataRequestClient dynamicClient = (FetchDataRequestClient)this.requestCertifyService.getFeignClient(FetchDataRequestClient.class);
        return dynamicClient.doInit(fetchInitTaskDTO);
    }

    public BusinessResponseEntity<FetchResultDTO> executeFetch(@RequestBody FetchRequestDTO fetchRequestDTO) {
        FetchDataRequestClient dynamicClient = (FetchDataRequestClient)this.requestCertifyService.getFeignClient(FetchDataRequestClient.class);
        return dynamicClient.executeFetch(fetchRequestDTO);
    }

    public BusinessResponseEntity<List<Map<String, Object>>> penetrateTable(@RequestBody FetchRequestDTO fetchRequestDTO) {
        FetchDataRequestClient dynamicClient = (FetchDataRequestClient)this.requestCertifyService.getFeignClient(FetchDataRequestClient.class);
        return dynamicClient.penetrateTable(fetchRequestDTO);
    }

    public BusinessResponseEntity<FetchTaskInfoDTO> queryTaskInfo(@RequestBody FetchTaskInfoQueryDTO taskInfoQueryDTO) {
        FetchDataRequestClient dynamicClient = (FetchDataRequestClient)this.requestCertifyService.getFeignClient(FetchDataRequestClient.class);
        return dynamicClient.queryTaskInfo(taskInfoQueryDTO);
    }
}

