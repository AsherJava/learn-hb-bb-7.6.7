/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.login.domain.NvwaContext
 *  com.jiuqi.nvwa.login.domain.NvwaContextIdentity
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.nvwa.login.domain.NvwaContextUser
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.shiro.MyNvwaExtendProvider
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.apache.shiro.codec.Base64
 */
package com.jiuqi.budget.components;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.shiro.MyNvwaExtendProvider;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="budLoginUtil")
public class LoginUtil {
    @Autowired
    private NvwaSystemUserClient systemUserService;
    @Autowired
    private NvwaUserClient userService;
    @Autowired
    private MyNvwaExtendProvider myNvwaExtendProvider;

    public void doInnerLogin(NvwaLoginUserDTO userDTO, Locale locale) {
        userDTO.setCheckPwd(true);
        User npUser = this.validate(userDTO);
        NvwaContext context = this.getContext(userDTO, npUser);
        this.initNpContext(context, locale);
    }

    public void doInnerLogOut() {
        NpContextHolder.clearContext();
        LocaleContextHolder.setLocale(null);
        String token = ShiroUtil.getToken();
        if (StringUtils.hasLength(token)) {
            ShiroUtil.unbindToken((String)token);
        }
        ShiroUtil.unbindUser();
        ShiroUtil.unbindSubject();
        ShiroUtil.unbindTenantName();
        ShiroUtil.cleanContext();
    }

    private void initNpContext(NvwaContext context, Locale locale) {
        NpContextImpl npContext = new NpContextImpl();
        npContext.setTenant(context.getTenantName());
        npContext.setLoginDate(context.getLoginDate());
        npContext.setOrganization((ContextOrganization)context.getContextOrg());
        npContext.setUser((ContextUser)context.getConetxtUser());
        npContext.setLocale(locale);
        NpContextIdentity identity = this.buildIdentityContext(context.getContextIdentity());
        npContext.setIdentity((ContextIdentity)identity);
        Map extInfo = context.getExtInfo();
        if (extInfo != null) {
            ContextExtension ctxExt = npContext.getDefaultExtension();
            extInfo.forEach((key, value) -> ctxExt.put(key, (Serializable)((Object)value.toString())));
        }
        NpContextHolder.setContext((NpContext)npContext);
        LocaleContextHolder.setLocale(locale);
    }

    private NpContextIdentity buildIdentityContext(NvwaContextIdentity nvwaContextIdentity) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(nvwaContextIdentity.getId());
        identity.setTitle(nvwaContextIdentity.getTitle());
        identity.setOrgCode(nvwaContextIdentity.getOrgCode());
        return identity;
    }

    private NvwaContext getContext(NvwaLoginUserDTO userDTO, User npUser) {
        NvwaContext nvwaCtx = new NvwaContext();
        NvwaContextUser user = new NvwaContextUser();
        user.setId(npUser.getId());
        user.setName(npUser.getName());
        user.setFullname(npUser.getFullname());
        user.setDescription(npUser.getDescription());
        user.setOrgCode(npUser.getOrgCode());
        user.setType(npUser.getUserType());
        user.setSecuritylevel(npUser.getSecurityLevel());
        nvwaCtx.setConetxtUser(user);
        if (userDTO.getLoginDate() != null) {
            nvwaCtx.setLoginDate(userDTO.getLoginDate());
        } else {
            nvwaCtx.setLoginDate(new Date());
        }
        String loginUnit = userDTO.getLoginUnit();
        if (loginUnit == null) {
            loginUnit = npUser.getOrgCode();
        }
        if (loginUnit != null) {
            nvwaCtx.setLoginUnit(loginUnit);
            NvwaContextOrg ctxOrg = new NvwaContextOrg();
            ctxOrg.setCode(loginUnit);
            ctxOrg.setName(this.myNvwaExtendProvider.getOrgTitle(loginUnit));
            nvwaCtx.setContextOrg(ctxOrg);
        }
        NvwaContextIdentity contextIdentity = this.myNvwaExtendProvider.buildContextIdentity((ContextUser)user, nvwaCtx.getExtInfo());
        nvwaCtx.setContextIdentity(contextIdentity);
        nvwaCtx.setTenantName(userDTO.getTenant());
        nvwaCtx.setId(npUser.getId());
        nvwaCtx.setUsername(npUser.getName());
        nvwaCtx.setName(npUser.getFullname());
        if (npUser instanceof SystemUser) {
            nvwaCtx.setMgrFlag("super");
        }
        nvwaCtx.setExtInfo(userDTO.getExtInfo());
        Set permissions = this.myNvwaExtendProvider.getPermissions(nvwaCtx);
        nvwaCtx.setPerms(permissions);
        return nvwaCtx;
    }

    private User validate(NvwaLoginUserDTO userDTO) {
        SystemUserDTO npUser;
        String username = userDTO.getUsername();
        if (userDTO.isEncrypted() && this.isBase64(username)) {
            username = Base64.decodeToString((String)Base64.decodeToString((String)username));
        }
        if ((npUser = this.systemUserService.findByUsername(username)) == null) {
            npUser = this.userService.findByUsername(username);
        }
        if (npUser.isLocked()) {
            throw new BudgetException("\u7528\u6237\u5df2\u9501\u5b9a");
        }
        if (!npUser.isEnabled()) {
            throw new BudgetException("\u7528\u6237\u5df2\u505c\u7528");
        }
        if (null != npUser.getMarkDelete() && npUser.getMarkDelete().booleanValue()) {
            throw new BudgetException("\u8d26\u6237\u5b58\u5728\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458");
        }
        return npUser;
    }

    private boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }
}

