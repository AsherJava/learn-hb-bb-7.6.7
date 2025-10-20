/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.role.RoleDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.feign.client;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.role.RoleDTO;
import com.jiuqi.va.domain.user.UserDO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="vaAuthRoleClient", name="${feignClient.authMgr.name}", path="${feignClient.authMgr.path}", url="${feignClient.authMgr.url}")
public interface AuthRoleClient {
    @PostMapping(value={"/role/get"})
    public RoleDO get(@RequestBody RoleDTO var1);

    @PostMapping(value={"/role/list"})
    public PageVO<RoleDO> list(@RequestBody RoleDTO var1);

    @PostMapping(value={"/role/list/byUser"})
    public List<RoleDO> listByUser(@RequestBody UserDO var1);

    @PostMapping(value={"/role/listName/byUser"})
    public List<String> listNameByUser(@RequestBody UserDO var1);
}

