/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.common.datasync.feign;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaBaseDataDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaEntityIdentityDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaOrganizationDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleUserRelationDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaUserDTO;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;
import java.net.URI;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.common.datasync.feign.DataSyncFeignClient", name="datasync-service", url="url-placeholder")
@RefreshScope
public interface CommonDataSyncNvwaFeignClient {
    @PostMapping(value={"/nvwa/login"})
    public R nvwaLogin(URI var1, @RequestBody NvwaLoginUserDTO var2);

    @PostMapping(value={"api/v1/commondatasync/role"})
    public BusinessResponseEntity<List<DataSyncNvwaRoleDTO>> getNvwaRoleDTOs(URI var1, @RequestBody NvwaLoginUserDTO var2);

    @PostMapping(value={"api/v1/commondatasync/role-user-relation"})
    public BusinessResponseEntity<List<DataSyncNvwaRoleUserRelationDTO>> getNvwaRoleUserRelationDTOs(URI var1, @RequestBody NvwaLoginUserDTO var2);

    @PostMapping(value={"api/v1/commondatasync/user"})
    public BusinessResponseEntity<List<DataSyncNvwaUserDTO>> getNvwaUserDTOs(URI var1, @RequestBody NvwaLoginUserDTO var2);

    @PostMapping(value={"api/v1/commondatasync/orgnization"})
    public BusinessResponseEntity<List<DataSyncNvwaOrganizationDTO>> getNvwaOrgnizationDTOs(URI var1, @RequestBody NvwaLoginUserDTO var2, @RequestParam(value="orgType") String var3);

    @PostMapping(value={"api/v1/commondatasync/basedata"})
    public BusinessResponseEntity<List<DataSyncNvwaBaseDataDTO>> getNvwaBaseDataDTOs(URI var1, @RequestParam(value="type", required=false) String var2, @RequestBody NvwaLoginUserDTO var3);

    @PostMapping(value={"api/v1/commondatasync/nvwa-entity-identitys"})
    public BusinessResponseEntity<List<DataSyncNvwaEntityIdentityDTO>> getNvwaEntityIdentityDTOs(URI var1, @RequestBody NvwaLoginUserDTO var2);
}

