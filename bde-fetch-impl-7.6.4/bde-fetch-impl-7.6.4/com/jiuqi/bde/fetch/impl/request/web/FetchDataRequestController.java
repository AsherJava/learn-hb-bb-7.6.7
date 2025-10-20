/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.bde.log.fetch.service.FetchTaskLogService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.impl.request.web;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataRequestService;
import com.jiuqi.bde.log.fetch.service.FetchTaskLogService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchDataRequestController
implements FetchDataRequestClient {
    @Autowired
    private FetchDataRequestService requestService;
    @Autowired
    private FetchTaskLogService fetchTaskLogService;

    @PostMapping(value={"/api/bde/v1/fetch/init"})
    public BusinessResponseEntity<Boolean> doInit(FetchInitTaskDTO fetchInitTaskDTO) {
        return BusinessResponseEntity.ok((Object)this.requestService.doInit(fetchInitTaskDTO));
    }

    @PostMapping(value={"/api/bde/v1/fetch/execute"})
    public BusinessResponseEntity<FetchResultDTO> executeFetch(@RequestBody FetchRequestDTO fetchRequestDTO) {
        return BusinessResponseEntity.ok((Object)this.requestService.doFetch(fetchRequestDTO));
    }

    @PostMapping(value={"/api/bde/v1/penetrate/data"})
    public BusinessResponseEntity<List<Map<String, Object>>> penetrateTable(@RequestBody FetchRequestDTO fetchRequestDTO) {
        return BusinessResponseEntity.ok(this.requestService.penetrateTable(fetchRequestDTO));
    }

    @PostMapping(value={"/api/bde/v1/fetch/task/query"})
    public BusinessResponseEntity<FetchTaskInfoDTO> queryTaskInfo(@RequestBody FetchTaskInfoQueryDTO taskInfoQueryDTO) {
        return BusinessResponseEntity.ok((Object)this.fetchTaskLogService.queryTaskInfo(taskInfoQueryDTO));
    }
}

