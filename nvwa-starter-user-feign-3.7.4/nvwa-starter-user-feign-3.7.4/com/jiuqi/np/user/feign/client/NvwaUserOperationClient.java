/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.DelUser
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.DelUser;
import com.jiuqi.np.user.dto.SyncUserDTO;
import com.jiuqi.np.user.dto.UserDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaUserOperationClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaUserOperationClient {
    @GetMapping(value={"/nvwa-user/v1/user-operation/update-del"})
    public List<SyncUserDTO> getAllUpdateDel(@RequestParam(value="startTime") Date var1, @RequestParam(value="endTime") Date var2, @RequestParam(value="offset") int var3, @RequestParam(value="limit") int var4);

    @Deprecated
    @GetMapping(value={"/nvwa-user/v1/user-operation/update"})
    public List<UserDTO> getUpdate(@RequestParam(value="startTime") Date var1);

    @GetMapping(value={"/nvwa-user/v1/user-operation/all-update"})
    public List<UserDTO> getAllUpdate(@RequestParam(value="startTime") Date var1);

    @Deprecated
    @GetMapping(value={"/nvwa-user/v1/user-operation/update/section"})
    public List<UserDTO> getUpdate(@RequestParam(value="startTime") Date var1, @RequestParam(value="endTime") Date var2);

    @GetMapping(value={"/nvwa-user/v1/user-operation/all-update/section"})
    public List<UserDTO> getAllUpdate(@RequestParam(value="startTime") Date var1, @RequestParam(value="endTime") Date var2);

    @GetMapping(value={"/nvwa-user/v1/user-operation/delete"})
    public List<DelUser> getDelete(@RequestParam(value="startTime") Date var1);

    @GetMapping(value={"/nvwa-user/v1/user-operation/delete/section"})
    public List<DelUser> getDelete(@RequestParam(value="startTime") Date var1, @RequestParam(value="endTime") Date var2);

    @PostMapping(value={"/nvwa-user/v1/user-operation/ids"})
    public Map<String, String> getNamesByIds(@RequestBody List<String> var1);
}

