/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.SystemUserDTO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.SystemUserDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaSystemUserClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaSystemUserClient {
    @GetMapping(value={"/nvwa-user/v1/sys-user/name/{name}"})
    public SystemUserDTO getByName(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/optional/name/{name}"})
    public SystemUserDTO findByUsername(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/optional/name/ignore/{name}"})
    public SystemUserDTO findByUsernameIgnoreCase(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/optional/id/{id}"})
    public SystemUserDTO find(@PathVariable(value="id") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/ignore/name/{name}"})
    public SystemUserDTO getByUsernameIgnoreCase(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/id/{id}"})
    public SystemUserDTO get(@PathVariable(value="id") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/exists/{name}"})
    public boolean exists(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/ignore/exists/{name}"})
    public boolean existsIgnoreCase(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/list/all"})
    public List<SystemUserDTO> getUsers();

    @GetMapping(value={"/nvwa-user/v1/sys-user/list/all/count"})
    public long count();

    @GetMapping(value={"/nvwa-user/v1/sys-user/list/page"})
    public List<SystemUserDTO> getUsers(@RequestParam(name="offset") int var1, @RequestParam(name="maxResult") int var2);

    @GetMapping(value={"/nvwa-user/v1/sys-user/orgCode/{orgCode}"})
    public List<SystemUserDTO> getByOrgCode(@PathVariable(value="orgCode") String var1);

    @GetMapping(value={"/nvwa-user/v1/sys-user/orgCodes"})
    public List<SystemUserDTO> getByOrgCode(@RequestBody List<String> var1);
}

