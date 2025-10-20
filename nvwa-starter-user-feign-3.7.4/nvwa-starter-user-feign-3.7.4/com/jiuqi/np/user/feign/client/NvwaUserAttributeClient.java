/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.dto.AttributeDefineDTO;
import com.jiuqi.np.user.dto.ResultDTO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaUserAttributeClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaUserAttributeClient {
    @GetMapping(value={"/nvwa-user/v1/attr/query"})
    public List<AttributeDefineDTO> query();

    @GetMapping(value={"/nvwa-user/v1/attr/query/name-entity-map"})
    public Map<String, AttributeDefineDTO> queryNameAndEntityMap();

    @GetMapping(value={"/nvwa-user/v1/attr/query/{id}"})
    public AttributeDefineDTO query(@PathVariable(value="id") String var1);

    @GetMapping(value={"/nvwa-user/v1/attr/find/{name}"})
    public AttributeDefineDTO queryByName(@PathVariable(value="name") String var1);

    @PostMapping(value={"/nvwa-user/v1/attr/add"})
    public ResultDTO add(@RequestBody AttributeDefineDTO var1);

    @PostMapping(value={"/nvwa-user/v1/attr/batch/add"})
    public ResultDTO add(@RequestBody List<AttributeDefineDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/attr/update"})
    public ResultDTO update(@RequestBody AttributeDefineDTO var1);

    @PostMapping(value={"/nvwa-user/v1/attr/batch/update"})
    public ResultDTO update(@RequestBody List<AttributeDefineDTO> var1);

    @GetMapping(value={"/nvwa-user/v1/attr/delete"})
    public ResultDTO delete(@RequestParam(name="id") String var1);
}

