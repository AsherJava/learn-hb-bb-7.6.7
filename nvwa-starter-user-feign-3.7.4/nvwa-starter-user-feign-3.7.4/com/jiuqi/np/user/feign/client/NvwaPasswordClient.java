/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.Password$ModifyByType
 *  com.jiuqi.np.user.Password$ValidateResult
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.Password;
import com.jiuqi.np.user.dto.BatchPasswordResetDTO;
import com.jiuqi.np.user.dto.PasswordDTO;
import com.jiuqi.np.user.dto.PasswordHistoryDTO;
import com.jiuqi.np.user.dto.ResultDTO;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaUserPassword", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaPasswordClient {
    @PostMapping(value={"/nvwa-user/v1/password/batch/change"})
    public ResultDTO batchReset(@RequestBody BatchPasswordResetDTO var1);

    @PostMapping(value={"/nvwa-user/v1/password/create/{userId}"})
    public ResultDTO create(@PathVariable(value="userId") String var1, @RequestParam(name="password") String var2);

    @GetMapping(value={"/nvwa-user/v1/password/get/{userId}"})
    public String get(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/update/{userId}"})
    public ResultDTO update(@PathVariable(value="userId") String var1, @RequestParam(name="password") String var2, @RequestParam(name="modifyByType") Password.ModifyByType var3);

    @PostMapping(value={"/nvwa-user/v1/password/change/{userId}"})
    public ResultDTO changePassword(@PathVariable(value="userId") String var1, @RequestParam(name="password") String var2);

    @PostMapping(value={"/nvwa-user/v1/password/delete/{userId}"})
    public ResultDTO delete(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/batchDelete"})
    public ResultDTO batchDelete(@RequestBody List<String> var1);

    @PostMapping(value={"/nvwa-user/v1/password/encode"})
    public String encode(@RequestParam(name="password") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/validate/{userId}"})
    public boolean validate(@PathVariable(value="userId") String var1, @RequestParam(name="password") String var2);

    @Deprecated
    @PostMapping(value={"/nvwa-user/v1/password/validateSamePwd"})
    public boolean validateSamePwd(@RequestParam(name="beforeEncode") String var1, @RequestParam(name="password") String var2);

    @PostMapping(value={"/nvwa-user/v1/password/validateSamePwdUserId"})
    public boolean validateSamePwd(@RequestParam(name="userId") String var1, @RequestParam(name="beforeEncode") String var2, @RequestParam(name="password") String var3);

    @PostMapping(value={"/nvwa-user/v1/password/validateSamePwdExpire"})
    public Password.ValidateResult validateSamePwdExpire(@RequestParam(name="userId") String var1, @RequestParam(name="beforeEncode") String var2, @RequestParam(name="password") String var3);

    @PostMapping(value={"/nvwa-user/v1/password/get/obj/{userId}"})
    public PasswordDTO getPassword(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/history/create"})
    public ResultDTO historyCreate(@RequestBody PasswordHistoryDTO var1);

    @PostMapping(value={"/nvwa-user/v1/password/history/delete/{userId}"})
    public ResultDTO historyDeleteByUserId(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/history/find/{userId}"})
    public List<PasswordHistoryDTO> historyFindByUser(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/password/history/find/{userId}/{historyTime}"})
    public List<PasswordHistoryDTO> historyFindByUser(@PathVariable(value="userId") String var1, @PathVariable(value="historyTime") Timestamp var2);
}

