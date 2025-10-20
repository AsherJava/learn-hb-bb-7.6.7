/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.dto.Result;
import com.jiuqi.np.user.dto.UserDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaUserByTypeClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaUserByTypeClient {
    @GetMapping(value={"/nvwa-user/v1/user-type/list/param"})
    public Result getByParam(@RequestParam(name="userType") int var1, @RequestParam(name="offset") int var2, @RequestParam(name="maxResult") int var3);

    @GetMapping(value={"/nvwa-user/v1/user-type/list/all"})
    public List<UserDTO> getUsers(@RequestParam(name="userType") int var1);

    @GetMapping(value={"/nvwa-user/v1/user-type/list/all/count"})
    public long count(@RequestParam(name="userType") int var1);

    @GetMapping(value={"/nvwa-user/v1/user-type/list/page"})
    public List<UserDTO> getUsers(@RequestParam(name="userType") int var1, @RequestParam(name="offset") int var2, @RequestParam(name="maxResult") int var3);

    @GetMapping(value={"/nvwa-user/v1/user-type/list/page/{offset}/{maxResult}"})
    public List<UserDTO> getUsers(@RequestParam(name="userType") int var1, @RequestBody UserDTO var2, @PathVariable(value="offset") int var3, @PathVariable(value="maxResult") int var4);

    @GetMapping(value={"/nvwa-user/v1/user-type/list/keywords/{keywords}"})
    public List<String> getUserIdsFuzzyByName(@RequestParam(name="userType") int var1, @PathVariable(value="keywords") String var2);

    @GetMapping(value={"/nvwa-user/v1/user-type/orgCode/{orgCode}"})
    public List<UserDTO> getByOrgCode(@RequestParam(name="userType") int var1, @PathVariable(value="orgCode") String var2);

    @GetMapping(value={"/nvwa-user/v1/user-type/orgCodes"})
    public List<UserDTO> getByOrgCode(@RequestParam(name="userType") int var1, @RequestBody List<String> var2);

    @GetMapping(value={"/nvwa-user/v1/user-type/open/threeMember"})
    public boolean isEnableThreeMember();

    @GetMapping(value={"/nvwa-user/v1/user-type/top/user"})
    public boolean isTopUser(@RequestParam(name="userType") int var1, @RequestParam(name="userId", required=false) String var2);
}

