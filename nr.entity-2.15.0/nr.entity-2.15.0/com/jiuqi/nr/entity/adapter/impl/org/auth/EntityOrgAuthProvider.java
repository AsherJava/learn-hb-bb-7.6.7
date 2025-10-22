/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.common.OrgAuthRuleExtProvider
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth;

import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.impl.org.auth.AbstractOrgAuthProvider;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.OrgAuthClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.common.OrgAuthRuleExtProvider;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.StringUtils;

public class EntityOrgAuthProvider
extends AbstractOrgAuthProvider
implements IEntityAuthProvider {
    private final OrgAuthClient orgAuthClient;
    private final OrgDataClient orgDataClient;

    public EntityOrgAuthProvider(UserService<User> userService, OrgIdentityService orgIdentityService, SystemIdentityService systemIdentityService, OrgDataClient orgDataClient, AuthQueryDao authQueryDao, RoleService roleService, List<OrgAuthRuleExtProvider> orgAuthRuleExtProvider, OrgAuthClient orgAuthClient) {
        super(userService, orgIdentityService, systemIdentityService, authQueryDao, roleService, orgAuthRuleExtProvider);
        this.orgAuthClient = orgAuthClient;
        this.orgDataClient = orgDataClient;
    }

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String entityKeyData, Date queryVersionDate) {
        String userName = NpContextHolder.getContext().getUserName();
        if (!StringUtils.hasText(userName)) {
            return false;
        }
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(entityId.toUpperCase());
        orgDataDTO.setVersionDate(queryVersionDate);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
        orgDataDTO.setCode(entityKeyData);
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        orgAuthFindDTO.setOrgDTO(orgDataDTO);
        R r = this.orgAuthClient.existDataAuth(orgAuthFindDTO);
        Boolean exist = (Boolean)r.get((Object)"exist");
        return exist != null && exist != false;
    }

    @Override
    public Map<String, Boolean> canAuthorityEntityKey(String authType, String entityId, Set<String> entityKeyDatas, Date queryVersionDate) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>(entityKeyDatas.size());
        for (String entityKeyData : entityKeyDatas) {
            result.put(entityKeyData, Boolean.FALSE);
        }
        String userName = NpContextHolder.getContext().getUserName();
        if (!StringUtils.hasText(userName)) {
            return result;
        }
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(entityId.toUpperCase());
        orgDataDTO.setVersionDate(queryVersionDate);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
        UserDO userDO = new UserDO();
        userDO.setUsername(userName);
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        orgAuthFindDTO.setOrgDTO(orgDataDTO);
        orgAuthFindDTO.setUserDO(userDO);
        Set authKeys = this.orgAuthClient.findAuth(orgAuthFindDTO);
        for (String auth : authKeys) {
            if (!entityKeyDatas.contains(auth)) continue;
            result.put(auth, Boolean.TRUE);
        }
        return result;
    }

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String identityId, String entityKeyData, Date queryVersionDate) {
        if (!StringUtils.hasText(identityId)) {
            throw new IllegalArgumentException("'identityId' must not be null");
        }
        UserDTO user = new UserDTO();
        user.setId(identityId);
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(entityId.toUpperCase());
        orgDataDTO.setVersionDate(queryVersionDate);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
        orgDataDTO.setCode(entityKeyData);
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        orgAuthFindDTO.setOrgDTO(orgDataDTO);
        orgAuthFindDTO.setUserDO((UserDO)user);
        R r = this.orgAuthClient.existDataAuth(orgAuthFindDTO);
        Boolean exist = (Boolean)r.get((Object)"exist");
        return exist != null && exist != false;
    }

    @Override
    public Set<String> getCanOperateEntityKeys(String authType, String orgTable, Date versionEndDate) {
        NpContext context = NpContextHolder.getContext();
        if (context == null || context.getUser() == null || context.getUser().getName() == null) {
            throw new RuntimeException("\u4e0a\u4e0b\u6587\u4e2d\u65e0\u6cd5\u83b7\u5f97\u7528\u6237\u767b\u5f55\u540d\u4fe1\u606f");
        }
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(orgTable.toUpperCase());
        orgDataDTO.setVersionDate(versionEndDate);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        UserDO userDO = new UserDO();
        userDO.setUsername(context.getUser().getName());
        orgAuthFindDTO.setUserDO(userDO);
        orgAuthFindDTO.setOrgDTO(orgDataDTO);
        return this.orgAuthClient.findAuth(orgAuthFindDTO);
    }

    @Override
    protected OrgDO getOrgData(String code, String table, Date versionEndDate) {
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(table);
        orgDataDTO.setCode(code);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDataDTO.setVersionDate(versionEndDate);
        orgDataDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        return this.orgDataClient.get(orgDataDTO);
    }
}

