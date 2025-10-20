/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.bde.fetch.client;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.fetch.client.FetchDataRequestClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface FetchDataRequestClient {
    @PostMapping(value={"/api/bde/v1/fetch/init"})
    public BusinessResponseEntity<Boolean> doInit(@RequestBody FetchInitTaskDTO var1);

    @PostMapping(value={"/api/bde/v1/fetch/execute"})
    public BusinessResponseEntity<FetchResultDTO> executeFetch(@RequestBody FetchRequestDTO var1);

    @PostMapping(value={"/api/bde/v1/penetrate/data"})
    public BusinessResponseEntity<List<Map<String, Object>>> penetrateTable(@RequestBody FetchRequestDTO var1);

    @PostMapping(value={"/api/bde/v1/fetch/task/query"})
    public BusinessResponseEntity<FetchTaskInfoDTO> queryTaskInfo(@RequestBody FetchTaskInfoQueryDTO var1);
}

