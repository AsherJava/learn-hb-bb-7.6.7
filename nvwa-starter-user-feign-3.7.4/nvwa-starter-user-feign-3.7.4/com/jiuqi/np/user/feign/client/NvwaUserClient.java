/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.AttrEncryptType
 *  com.jiuqi.np.user.attr.UserAttributeTypeValues
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.user.feign.client;

import com.jiuqi.np.user.AttrEncryptType;
import com.jiuqi.np.user.attr.UserAttributeTypeValues;
import com.jiuqi.np.user.dto.AttributeTypeDTO;
import com.jiuqi.np.user.dto.BatchUpdateDTO;
import com.jiuqi.np.user.dto.BatchUserAttributeDTO;
import com.jiuqi.np.user.dto.Result;
import com.jiuqi.np.user.dto.ResultDTO;
import com.jiuqi.np.user.dto.UserAttributeTypeValuesDTO;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.dto.UserExtendedAttributeDTO;
import com.jiuqi.np.user.dto.UserLockPolicyDTO;
import com.jiuqi.np.user.dto.UserStateDTO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="nvwaUserClient", name="${feignClient.nvwaUserClient.name}", path="${feignClient.nvwaUserClient.path}", url="${feignClient.nvwaUserClient.url}")
public interface NvwaUserClient {
    @PostMapping(value={"/nvwa-user/v1/user/batch/update"})
    public ResultDTO batchUpdateState(@RequestBody BatchUpdateDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/add"})
    public String create(@RequestBody UserDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/batchAdd"})
    public ResultDTO batchAdd(@RequestBody List<UserDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/user/batchUpdate"})
    public ResultDTO batchUpdate(@RequestBody List<UserDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/user/update"})
    public ResultDTO update(@RequestBody UserDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/pwdLocked"})
    public ResultDTO pwdLocked(@RequestBody UserStateDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/del/{id}"})
    public ResultDTO delete(@PathVariable(value="id") String var1);

    @PostMapping(value={"/nvwa-user/v1/user/batchDel"})
    public ResultDTO batchDel(@RequestBody List<String> var1);

    @GetMapping(value={"/nvwa-user/v1/user/name/{name}"})
    public UserDTO getByName(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/list/param"})
    public Result getByParam(@RequestParam(name="offset") int var1, @RequestParam(name="maxResult") int var2);

    @PostMapping(value={"/nvwa-user/v1/user/locked"})
    public ResultDTO locked(@RequestBody UserStateDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/locked/policy"})
    public ResultDTO autoUnlock(@RequestBody UserLockPolicyDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/enable"})
    public ResultDTO enable(@RequestBody UserStateDTO var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/name/{name}"})
    public UserDTO findByUsername(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/name/simple/{name}/{simple}"})
    public UserDTO findByUsername(@PathVariable(value="name") String var1, @PathVariable(value="simple") boolean var2);

    @GetMapping(value={"/nvwa-user/v1/user/list/name/{name}"})
    public List<String> fuzzyFindByUsername(String var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/name/ignore/{name}"})
    public UserDTO findByUsernameIgnoreCase(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/phone/{phoneNumber}"})
    public UserDTO findByPhoneNumber(@PathVariable(value="phoneNumber") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/certificate/{type}/{certificateNumber}"})
    public UserDTO findByCertificateNumber(@PathVariable(value="type") int var1, @PathVariable(value="certificateNumber") String var2);

    @PostMapping(value={"/nvwa-user/v1/user/get/phoneNumbers"})
    public List<UserDTO> findByPhoneNumbers(@RequestBody List<String> var1);

    @PostMapping(value={"/nvwa-user/v1/user/get/certificateNumbers/{type}"})
    public List<UserDTO> findByCertificateNumbers(@PathVariable(value="type") int var1, @RequestBody List<String> var2);

    @GetMapping(value={"/nvwa-user/v1/user/optional/wechat/{wechat}"})
    public UserDTO findByWeChat(@PathVariable(value="wechat") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/optional/id/{id}"})
    public UserDTO find(@PathVariable(value="id") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/ignore/name/{name}"})
    public UserDTO getByUsernameIgnoreCase(@PathVariable(value="name") String var1);

    @PostMapping(value={"/nvwa-user/v1/user/get/names"})
    public List<UserDTO> getByUsernames(@RequestBody List<String> var1);

    @GetMapping(value={"/nvwa-user/v1/user/id/{id}"})
    public UserDTO get(@PathVariable(value="id") String var1);

    @PostMapping(value={"/nvwa-user/v1/user/ids"})
    public List<UserDTO> get(@RequestBody List<String> var1);

    @GetMapping(value={"/nvwa-user/v1/user/all"})
    public List<UserDTO> getAllUsers();

    @GetMapping(value={"/nvwa-user/v1/user/all/page"})
    public List<UserDTO> getAllUsersByPage(@RequestParam(name="offset") int var1, @RequestParam(name="maxResult") int var2);

    @GetMapping(value={"/nvwa-user/v1/user/all/{encryptType}"})
    public List<UserDTO> getAllUsers(@PathVariable(value="encryptType") AttrEncryptType var1);

    @PostMapping(value={"/nvwa-user/v1/user/del/name/{name}"})
    public ResultDTO deleteByName(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/exists/{name}"})
    public boolean exists(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/ignore/exists/{name}"})
    public boolean existsIgnoreCase(@PathVariable(value="name") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/list/all"})
    public List<UserDTO> getUsers();

    @GetMapping(value={"/nvwa-user/v1/user/list/all/count"})
    public long count();

    @GetMapping(value={"/nvwa-user/v1/user/list/page"})
    public List<UserDTO> getUsers(@RequestParam(name="offset") int var1, @RequestParam(name="maxResult") int var2);

    @GetMapping(value={"/nvwa-user/v1/user/list/page/{offset}/{maxResult}"})
    public List<UserDTO> getUsers(@RequestBody UserDTO var1, @PathVariable(value="offset") int var2, @PathVariable(value="maxResult") int var3);

    @GetMapping(value={"/nvwa-user/v1/user/list/keywords/{keywords}"})
    public List<String> getUserIdsFuzzyByName(@PathVariable(value="keywords") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/orgCode/{orgCode}"})
    public List<UserDTO> getByOrgCode(@PathVariable(value="orgCode") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/orgCodes"})
    public List<UserDTO> getByOrgCode(@RequestBody List<String> var1);

    @GetMapping(value={"/nvwa-user/v1/user/attr/{userId}/{attributeName}"})
    public UserExtendedAttributeDTO findExtendedAttribute(@PathVariable(value="userId") String var1, @PathVariable(value="attributeName") String var2);

    @GetMapping(value={"/nvwa-user/v1/user/attr/{userId}"})
    public List<UserExtendedAttributeDTO> getExtendedAttribute(@PathVariable(value="userId") String var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr"})
    public List<UserExtendedAttributeDTO> getExtendedAttribute(@RequestBody UserDTO var1);

    @GetMapping(value={"/nvwa-user/v1/user/attr/batch/{userIds}"})
    public Map<String, List<UserExtendedAttributeDTO>> batchGetExtendedAttribute(@PathVariable(value="userIds") List<String> var1);

    @GetMapping(value={"/nvwa-user/v1/user/attr/all/{userId}"})
    public List<UserExtendedAttributeDTO> getAllExtendedAttribute(@PathVariable(value="userId") String var1);

    @GetMapping(value={"/nvwa-user/v1/user/attr/all/{userId}/{encryptType}"})
    public List<UserExtendedAttributeDTO> getAllExtendedAttribute(@PathVariable(value="userId") String var1, @PathVariable(value="encryptType") AttrEncryptType var2);

    @PostMapping(value={"/nvwa-user/v1/user/attr/add"})
    public String addExtendedAttribute(@RequestBody UserExtendedAttributeDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/batch/add"})
    public ResultDTO batchAddAttribute(@RequestBody BatchUserAttributeDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/batch/adds"})
    public ResultDTO batchAddAttributes(@RequestBody List<BatchUserAttributeDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/update"})
    public ResultDTO updateExtendedAttribute(@RequestBody UserExtendedAttributeDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/batch/update"})
    public ResultDTO batchUpdateAttribute(@RequestBody BatchUserAttributeDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/batch/updates"})
    public ResultDTO batchUpdateAttributes(@RequestBody List<BatchUserAttributeDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/delete"})
    public ResultDTO deleteExtendedAttribute(@RequestParam(name="userId") String var1, @RequestParam(name="attribute") String var2);

    @GetMapping(value={"/nvwa-user/v1/user/attr/types"})
    public List<AttributeTypeDTO> getUserAttributeExtendTypes();

    @GetMapping(value={"/nvwa-user/v1/user/attr/values"})
    public UserAttributeTypeValues getAttributeValues(@RequestParam(name="defineKey") String var1, @RequestParam(name="parentId", required=false) String var2);

    @PostMapping(value={"/nvwa-user/v1/user/attr/values"})
    public UserAttributeTypeValues getAttributeValues(@RequestBody UserAttributeTypeValuesDTO var1);

    @GetMapping(value={"/nvwa-user/v1/user/attr/path"})
    public String[] getAttributePath(@RequestParam(name="defineKey") String var1, @RequestParam(name="key") String var2);

    @PostMapping(value={"/nvwa-user/v1/user/attr/path"})
    public String[] getAttributePath(UserAttributeTypeValuesDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/attr/by/value"})
    public List<String> getUsersByAttrValue(@RequestParam(value="attributeName") String var1, @RequestParam(value="value") String var2);

    @PostMapping(value={"/nvwa-user/v1/user/update/encrypt"})
    public ResultDTO updateEncryptFields(@RequestBody List<UserDTO> var1);

    @PostMapping(value={"/nvwa-user/v1/user/decrypt"})
    public UserDTO decrypt(@RequestBody UserDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/login/update"})
    public ResultDTO updateLoginTime(@RequestBody UserDTO var1);

    @PostMapping(value={"/nvwa-user/v1/user/login/batch-update"})
    public ResultDTO batchUpdateLoginTime(@RequestBody List<UserDTO> var1);
}

