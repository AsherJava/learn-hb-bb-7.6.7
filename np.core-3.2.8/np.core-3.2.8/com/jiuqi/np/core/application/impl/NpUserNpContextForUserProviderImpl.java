/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 */
package com.jiuqi.np.core.application.impl;

import com.jiuqi.np.common.exception.NvwaUserNotFoundException;
import com.jiuqi.np.core.application.NpContextForUserProvider;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextBuilder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextOrganization;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class NpUserNpContextForUserProviderImpl
implements NpContextForUserProvider {
    @Value(value="${jiuqi.nvwa.login.ignoreCase:true}")
    private boolean ignoreCase;
    @Autowired
    private NvwaSystemUserClient systemUserService;
    @Autowired
    private NvwaUserClient userService;

    @Override
    public NpContext getTempContext(String userName) throws NvwaUserNotFoundException {
        SystemUserDTO npUser;
        SystemUserDTO systemUserDTO = npUser = this.ignoreCase ? this.systemUserService.findByUsernameIgnoreCase(userName) : this.systemUserService.findByUsername(userName);
        if (npUser == null) {
            UserDTO userDTO = npUser = this.ignoreCase ? this.userService.findByUsernameIgnoreCase(userName) : this.userService.findByUsername(userName);
        }
        if (null == npUser) {
            throw new NvwaUserNotFoundException(userName + " not found");
        }
        NpContextBuilder npContextBuilder = new NpContextBuilder();
        npContextBuilder.date(new Date());
        npContextBuilder.local(LocaleContextHolder.getLocale());
        NpContextUser npContextUser = new NpContextUser();
        npContextUser.setId(npUser.getId());
        npContextUser.setName(npUser.getName());
        npContextUser.setNickname(StringUtils.hasLength(npUser.getFullname()) ? npUser.getFullname() : npUser.getNickname());
        npContextUser.setDescription(npUser.getDescription());
        npContextUser.setOrgCode(npUser.getOrgCode());
        npContextUser.setType(npUser.getUserType());
        npContextUser.setSecuritylevel(npUser.getSecurityLevel());
        npContextUser.setAvatar(null != npUser.getAvatar() && npUser.getAvatar().length > 0);
        npContextBuilder.user(npContextUser);
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(npUser.getId());
        identity.setOrgCode(npUser.getOrgCode());
        identity.setTitle(npContextUser.getNickname());
        npContextBuilder.identity(identity);
        if (null != npUser.getOrgCode() && npUser.getOrgCode().length() > 0) {
            NpContextOrganization organization = new NpContextOrganization();
            organization.setCode(npUser.getOrgCode());
            npContextBuilder.organization(organization);
        }
        return npContextBuilder.build();
    }
}

