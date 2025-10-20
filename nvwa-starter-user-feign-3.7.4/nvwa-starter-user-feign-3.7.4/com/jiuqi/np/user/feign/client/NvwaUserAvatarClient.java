/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.dto.ResultDTO;
import com.jiuqi.np.user.dto.UserAvatarDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(primary=false, contextId="nvwaUserAvatarClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaUserAvatarClient {
    @PostMapping(value={"/nvwa-user/v1/user-avatar/add-update/{userId}"})
    public String addOrUpdate(@RequestParam(name="file", required=false) MultipartFile var1, @PathVariable(value="userId") String var2);

    @PostMapping(value={"/nvwa-user/v1/user-avatar/del/{userId}"})
    public ResultDTO delete(@PathVariable(value="userId") String var1);

    @GetMapping(value={"/nvwa-user/v1/user-avatar/img/{userId}"})
    public void httpGet(@PathVariable(value="userId") String var1);

    @GetMapping(value={"/nvwa-user/v1/user-avatar/{userId}"})
    public UserAvatarDTO get(@PathVariable(value="userId") String var1);

    @GetMapping(value={"/nvwa-user/v1/user-avatar/exist/{userId}"})
    public boolean exist(@PathVariable(value="userId") String var1);
}

