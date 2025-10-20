/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.bde.bizmodel.client;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.bizmodel.client.AssistExtendDimClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface AssistExtendDimClient {
    public static final String ASSIST_EXTEND_API_PREFIX = "/api/bde/v1/assistextend";

    @GetMapping(value={"/api/bde/v1/assistextend/getAssistExtendDimByCode/{code}"})
    public BusinessResponseEntity<AssistExtendDimVO> getAssistExtendDimByCode(@PathVariable(value="code") String var1);

    @PostMapping(value={"/api/bde/v1/assistextend/save"})
    public BusinessResponseEntity<String> save(@RequestBody AssistExtendDimVO var1);

    @PostMapping(value={"/api/bde/v1/assistextend/update"})
    public BusinessResponseEntity<String> update(@RequestBody AssistExtendDimVO var1);

    @GetMapping(value={"/api/bde/v1/assistextend/get"})
    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllAssistExtendDim();

    @GetMapping(value={"/api/bde/v1/assistextend/getAllStart"})
    public BusinessResponseEntity<List<AssistExtendDimVO>> getAllStartAssistExtendDim();

    @GetMapping(value={"/api/bde/v1/assistextend/stop/{id}"})
    public BusinessResponseEntity<String> stopAssistExtendDimById(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/bde/v1/assistextend/start/{id}"})
    public BusinessResponseEntity<String> startAssistExtendDimById(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/bde/v1/assistextend/getBaseDataColumn/{name}"})
    public BusinessResponseEntity<List<DataModelColumn>> getBaseDataColumns(@PathVariable(value="name") String var1);
}

