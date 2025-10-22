/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.extend.OrgAuthExtendProvider
 *  com.jiuqi.va.extend.OrgAuthExtendProvider$Method
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.entity.ext.auth.org;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.org.auth.OrgAuthTypeExtendImpl;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.extend.OrgAuthExtendProvider;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class IndependentOrgAuthProvider
implements OrgAuthExtendProvider {
    private static final String INDEPENDENT_ORG_SUFFIX = "0";
    private static final String MODEL_ORG_SUFFIX = "1";
    private static final String DEFAULT_BBLX_FIELD_CODE = "bblx";
    private static final String DEFAULT_BBLX_VALUE = "0";
    private static final String BOOLEAN_TRUE = "1";
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private AuthUserClient userClient;

    public Set<String> listAuthOrg(UserDO user, OrgDTO param) {
        OrgDataOption.AuthType authType = param.getAuthType();
        if (this.isSpecialAuth(authType)) {
            return Collections.emptySet();
        }
        HashSet<String> orgCodes = new HashSet<String>();
        orgCodes.add(this.getUserUnitCode(user));
        return orgCodes;
    }

    public boolean existDataAuth(UserDO userDO, OrgDTO param) {
        return !this.isSpecialAuth(param.getAuthType());
    }

    public boolean isEnabled(UserDO userDO, OrgDTO param, OrgAuthExtendProvider.Method method) {
        String enable = this.systemOptionService.get("ORG_AUTH_EXT", "INDEPENDENT_ORG_EXT_ENABLE");
        if (!"1".equalsIgnoreCase(enable)) {
            return false;
        }
        String unitCode = this.getUserUnitCode(userDO);
        if (OrgAuthExtendProvider.Method.LIST_AUTH_ORG.equals((Object)method)) {
            return this.isIndependentUnit(unitCode, param.getCategoryname());
        }
        if (!StringUtils.hasText(unitCode)) {
            return false;
        }
        if (!unitCode.equals(param.getCode())) {
            return false;
        }
        return this.isIndependentUnit(unitCode, param.getCategoryname());
    }

    private boolean isIndependentUnit(String unitCode, String category) {
        if (!StringUtils.hasText(unitCode) || !StringUtils.hasText(category)) {
            return false;
        }
        String model = this.systemOptionService.get("ORG_AUTH_EXT", "INDEPENDENT_ORG_BASIS");
        if ("1".equals(model)) {
            return unitCode.endsWith("0");
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(category);
        orgDTO.setCode(unitCode);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setCacheSyncDisable(Boolean.valueOf(false));
        OrgDO orgDO = this.orgDataClient.get(orgDTO);
        if (orgDO == null || orgDO.get((Object)DEFAULT_BBLX_FIELD_CODE) == null) {
            return false;
        }
        String bblxCode = this.systemOptionService.get("ORG_AUTH_EXT", "DEFAULT_BBLX_CODE");
        if (!StringUtils.hasText(bblxCode)) {
            bblxCode = "0";
        }
        return orgDO.get((Object)DEFAULT_BBLX_FIELD_CODE).equals(bblxCode);
    }

    private boolean isSpecialAuth(OrgDataOption.AuthType authType) {
        return OrgDataOption.AuthType.APPROVAL.equals(authType) || OrgDataOption.AuthType.valueOf((String)OrgAuthTypeExtendImpl.AuthTypeExtend.READ_UN_PUBLISH.name()).equals(authType);
    }

    private String getUserUnitCode(UserDO userDO) {
        String unitCode = userDO.getUnitcode();
        if (StringUtils.hasText(unitCode)) {
            return unitCode;
        }
        ContextUser currentUser = NpContextHolder.getContext().getUser();
        unitCode = userDO.getUsername().equals(currentUser.getName()) ? currentUser.getOrgCode() : this.queryUserUnitCode(userDO);
        return unitCode;
    }

    private String queryUserUnitCode(UserDO userDO) {
        UserDTO userDto = new UserDTO();
        userDto.setId(userDO.getId());
        userDto.setUsername(userDO.getUsername());
        UserDO queryUserDo = this.userClient.get(userDto);
        return queryUserDo.getUnitcode();
    }
}

