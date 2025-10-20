/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.Password$ValidateResult
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.TenantInfoClient
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.shiro.SecurityUtils
 *  org.apache.shiro.authc.AuthenticationToken
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.session.mgt.eis.SessionDAO
 *  org.apache.shiro.subject.PrincipalCollection
 *  org.apache.shiro.subject.SimplePrincipalCollection
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.subject.support.DefaultSubjectContext
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nvwa.login.service.impl;

import com.jiuqi.np.user.Password;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.login.config.CookieProperties;
import com.jiuqi.nvwa.login.config.NvwaLoginProperties;
import com.jiuqi.nvwa.login.constant.LoginConsts;
import com.jiuqi.nvwa.login.constant.LoginState;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.encrypt.NvwaLoginAes128EncryptProvider;
import com.jiuqi.nvwa.login.exception.ChangeLoginContextException;
import com.jiuqi.nvwa.login.log.NvwaLoginLogHelper;
import com.jiuqi.nvwa.login.provider.NvwaContextWrapperProvider;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import com.jiuqi.nvwa.login.provider.NvwaLoginProvider;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.nvwa.login.shiro.MyAuthenticationToken;
import com.jiuqi.nvwa.login.shiro.MyNvwaExtendProvider;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class NvwaLoginServiceImpl
implements NvwaLoginService {
    public static final Logger LOGGER = LoggerFactory.getLogger(NvwaLoginServiceImpl.class);
    @Autowired
    private NvwaSystemUserClient systemUserService;
    @Autowired
    private NvwaUserClient userService;
    @Autowired
    private NvwaPasswordClient pwdService;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired(required=false)
    private List<NvwaLoginProvider> loginProviders;
    @Autowired
    private SecurityManager securityManager;
    @Autowired
    private MyNvwaExtendProvider myNvwaExtendProvider;
    @Autowired
    private NvwaLoginLogHelper nvwaLoginLogHelper;
    @Autowired(required=false)
    private List<NvwaLoginEncryptProvider> encryptProviders;
    @Autowired
    private NvwaLoginAes128EncryptProvider aes128EncryptProvider;
    @Autowired
    private NvwaLoginProperties nvwaLoginProperties;
    @Autowired(required=false)
    private List<NvwaContextWrapperProvider> nvwaContextWrapperProviders;
    @Autowired
    private TenantInfoClient tenantInfoClient;
    @Value(value="${va-auth-shiro.sessionid-url-parameter:JTOKENID}")
    private String urlParameter;
    @Value(value="${server.servlet.context-path:/}")
    private String contextPath;
    @Value(value="${va-auth-shiro.cookie.enable:false}")
    private boolean cookieEnable;

    @Override
    public R tryLogin(NvwaLoginUserDTO userDTO, boolean isInnerLogin) {
        String tenantName = null;
        tenantName = TenantUtil.isMultiTenant() ? userDTO.getTenant() : "__default_tenant__";
        String loginUsername = userDTO.getUsername();
        if (loginUsername == null || tenantName == null) {
            this.nvwaLoginLogHelper.failLog(userDTO, null, "\u7f3a\u5c11\u767b\u5f55\u5fc5\u987b\u53c2\u6570");
            return R.error((int)LoginState.FAIL.getCode(), (String)"\u7f3a\u5c11\u767b\u5f55\u5fc5\u987b\u53c2\u6570");
        }
        if (!isInnerLogin) {
            userDTO.setCheckPwd(true);
        }
        String loginPwd = userDTO.getPwd();
        boolean encrypted = userDTO.isEncrypted();
        if (encrypted) {
            String encryptType = userDTO.getEncryptType();
            try {
                NvwaLoginEncryptProvider encryptProvider = null;
                if (StringUtils.hasLength(encryptType)) {
                    Optional<NvwaLoginEncryptProvider> findFirst = this.encryptProviders.stream().filter(e -> e.getType().equalsIgnoreCase(encryptType) || e.getAlias().equalsIgnoreCase(encryptType)).findFirst();
                    if (findFirst.isPresent()) {
                        encryptProvider = findFirst.get();
                    }
                } else {
                    encryptProvider = this.aes128EncryptProvider;
                }
                if (null == encryptProvider) {
                    throw new RuntimeException("\u672a\u77e5\u7684\u52a0\u5bc6\u7c7b\u578b\uff1a" + encryptType);
                }
                userDTO.setUsername(encryptProvider.decrypt(loginUsername));
                userDTO.setPwd(encryptProvider.decrypt(loginPwd));
                userDTO.setEncrypted(false);
            }
            catch (Exception e2) {
                LOGGER.error("\u767b\u5f55\u53d1\u751f\u5f02\u5e38", e2);
                this.nvwaLoginLogHelper.failLog(userDTO, null, "\u767b\u5f55\u53d1\u751f\u5f02\u5e38:" + e2.getMessage());
                return R.error((int)LoginState.FAIL.getCode(), (String)"\u767b\u5f55\u53d1\u751f\u5f02\u5e38");
            }
        } else if (!isInnerLogin && this.nvwaLoginProperties.isEncrypt()) {
            LOGGER.error("\u767b\u5f55\u5f3a\u5236\u8981\u6c42\u5bc6\u6587\uff0c\u975e\u5bc6\u6587\u767b\u5f55\u8bf7\u6c42\u4e0d\u5904\u7406\uff01");
            return R.error((int)LoginState.FAIL.getCode(), (String)"\u7f3a\u5c11\u767b\u5f55\u5fc5\u987b\u53c2\u6570");
        }
        if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
            try {
                R loginRs = null;
                for (NvwaLoginProvider verifyProvider : this.loginProviders) {
                    loginRs = verifyProvider.loginBefore(userDTO);
                    if (loginRs.getCode() == LoginState.NEED_PIN.getCode()) {
                        if (encrypted) {
                            userDTO.setUsername(loginUsername);
                            userDTO.setPwd(loginPwd);
                            userDTO.setEncrypted(true);
                        }
                        return loginRs;
                    }
                    if (loginRs.getCode() == LoginState.SUCCESS.getCode() || loginRs.getCode() == LoginState.OK.getCode()) continue;
                    this.nvwaLoginLogHelper.failLog(userDTO, null, loginRs.getMsg());
                    LOGGER.error("\u767b\u5f55\u5931\u8d25\uff01" + verifyProvider.getClass().getName() + ";\u767b\u5f55\u6821\u9a8c\u5668\u6821\u9a8c\u5931\u8d25\uff01");
                    return loginRs;
                }
            }
            catch (Exception e3) {
                LOGGER.error("\u767b\u5f55\u53d1\u751f\u5f02\u5e38", e3);
                this.nvwaLoginLogHelper.failLog(userDTO, null, "\u767b\u5f55\u53d1\u751f\u5f02\u5e38:" + e3.getMessage());
                return R.error((int)LoginState.FAIL.getCode(), (String)"\u767b\u5f55\u53d1\u751f\u5f02\u5e38");
            }
        }
        if (!isInnerLogin) {
            userDTO.setCheckPwd(true);
        } else if (null == userDTO.getExtInfo("loginType")) {
            userDTO.addExtInfo("loginType", "sso");
        }
        if (this.nvwaLoginProperties.isLogStackTrace()) {
            StackTraceElement[] stackTrace;
            LOGGER.info((isInnerLogin ? "\u5185\u90e8" : "\u5916\u90e8") + "\u767b\u5f55\uff1a" + userDTO.getUsername() + " \u8c03\u7528\u6808\u5982\u4e0b\uff1a");
            for (StackTraceElement stack : stackTrace = Thread.currentThread().getStackTrace()) {
                LOGGER.info("\u7c7b\u8def\u5f84\uff1a" + stack.getClassName() + " \u65b9\u6cd5\u540d\uff1a " + stack.getMethodName() + " \u8c03\u7528\u884c\u53f7\uff1a" + stack.getLineNumber());
            }
        }
        R rs = R.ok();
        User npUser = null;
        try {
            npUser = this.validate(userDTO, rs);
        }
        catch (Exception e4) {
            LOGGER.error("\u767b\u5f55\u53d1\u751f\u5f02\u5e38", e4);
            this.nvwaLoginLogHelper.failLog(userDTO, npUser, "\u767b\u5f55\u53d1\u751f\u5f02\u5e38:" + e4.getMessage());
            return R.error((int)LoginState.FAIL.getCode(), (String)"\u767b\u5f55\u53d1\u751f\u5f02\u5e38");
        }
        if (rs.getCode() != LoginState.SUCCESS.getCode() && rs.getCode() != LoginState.OK.getCode()) {
            this.nvwaLoginLogHelper.failLog(userDTO, npUser, rs.getMsg());
            return rs;
        }
        NvwaContext context = this.getContext(userDTO, npUser);
        String pwd = userDTO.getPwd();
        if (!StringUtils.hasText(pwd)) {
            pwd = "-";
        }
        MyAuthenticationToken token = new MyAuthenticationToken(context, this.pwdService.encode(pwd));
        SecurityManager sManager = ThreadContext.getSecurityManager();
        if (sManager == null) {
            ThreadContext.bind((SecurityManager)this.securityManager);
        }
        ThreadContext.unbindSubject();
        Subject subject = SecurityUtils.getSubject();
        try {
            ServletRequestAttributes servletRequestAttributes;
            subject.login((AuthenticationToken)token);
            String sessionid = subject.getSession().getId().toString();
            rs.put("token", (Object)sessionid);
            rs.put("urlAuthKey", (Object)this.urlParameter);
            if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
                for (NvwaLoginProvider verifyProvider : this.loginProviders) {
                    verifyProvider.loginAfter(userDTO, npUser, rs);
                }
            }
            if (this.cookieEnable && null != (servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes())) {
                rs.put("cookie", (Object)this.cookieEnable);
                CookieProperties cookieProperties = this.nvwaLoginProperties.getCookie();
                String domain = "";
                String path = this.contextPath;
                if (StringUtils.hasLength(cookieProperties.getPath())) {
                    path = cookieProperties.getPath();
                }
                if (StringUtils.hasLength(cookieProperties.getDomain())) {
                    domain = cookieProperties.getDomain();
                }
                RequestContextUtil.setCookie((String)this.urlParameter, (String)sessionid, (String)domain, (String)path, (int)cookieProperties.getMaxAge(), (boolean)cookieProperties.isSecure());
            }
            this.nvwaLoginLogHelper.successLog(context, "\u767b\u5f55\u6210\u529f");
            return rs;
        }
        catch (Exception e5) {
            LOGGER.error("\u767b\u5f55\u53d1\u751f\u5f02\u5e38", e5);
            this.nvwaLoginLogHelper.failLog(userDTO, npUser, "\u767b\u5f55\u53d1\u751f\u5f02\u5e38:" + e5.getMessage());
            return R.error((int)LoginState.FAIL.getCode(), (String)"\u767b\u5f55\u53d1\u751f\u5f02\u5e38");
        }
    }

    private NvwaContext getContext(NvwaLoginUserDTO userDTO, User npUser) {
        NvwaContext nvwaCtx = new NvwaContext();
        NvwaContextUser user = new NvwaContextUser();
        user.setId(npUser.getId());
        user.setName(npUser.getName());
        user.setFullname(StringUtils.hasLength(npUser.getFullname()) ? npUser.getFullname() : npUser.getNickname());
        user.setDescription(npUser.getDescription());
        user.setOrgCode(npUser.getOrgCode());
        user.setType(npUser.getUserType());
        user.setSecuritylevel(npUser.getSecurityLevel());
        user.setAvatar(null != npUser.getAvatar() && npUser.getAvatar().length > 0);
        nvwaCtx.setConetxtUser(user);
        nvwaCtx.setExtInfo(userDTO.getExtInfo());
        this.convertLoginDate(nvwaCtx, userDTO);
        NvwaContextIdentity contextIdentity = this.myNvwaExtendProvider.buildContextIdentity(user, nvwaCtx.getExtInfo());
        nvwaCtx.setContextIdentity(contextIdentity);
        String loginUnit = userDTO.getLoginUnit();
        if (loginUnit == null) {
            loginUnit = npUser.getOrgCode();
        }
        if (StringUtils.hasLength(loginUnit)) {
            boolean existOrgAuth = this.myNvwaExtendProvider.existOrgAuth(loginUnit, user, contextIdentity);
            if (!existOrgAuth) {
                if (contextIdentity != null) {
                    LOGGER.error("\u767b\u5f55\u5f02\u5e38\uff01\u7528\u6237\uff1a[" + user.getName() + "]\u8eab\u4efd\uff1a[" + contextIdentity.getId() + " ]\u5bf9\u673a\u6784\uff1a[" + loginUnit + "]\u65e0\u8bbf\u95ee\u6743\u9650\uff01\u65e0\u6cd5\u767b\u5f55\u5230\u8be5\u673a\u6784,\u56de\u843d\u5230\u8eab\u4efd\u6240\u5c5e\u673a\u6784\uff1a[" + contextIdentity.getOrgCode() + "]");
                    loginUnit = contextIdentity.getOrgCode();
                } else {
                    loginUnit = npUser.getOrgCode();
                }
            }
            if (StringUtils.hasLength(loginUnit)) {
                nvwaCtx.setLoginUnit(loginUnit);
                NvwaContextOrg ctxOrg = new NvwaContextOrg();
                ctxOrg.setCode(loginUnit);
                ctxOrg.setName(this.myNvwaExtendProvider.getOrgTitle(loginUnit));
                nvwaCtx.setContextOrg(ctxOrg);
            }
        }
        nvwaCtx.setTenantName(userDTO.getTenant());
        nvwaCtx.setId(npUser.getId());
        nvwaCtx.setUsername(npUser.getName());
        nvwaCtx.setName(npUser.getFullname());
        if (npUser instanceof SystemUser) {
            nvwaCtx.setMgrFlag("super");
        }
        if (contextIdentity != null) {
            nvwaCtx.addExtInfo("contextIdentity", contextIdentity.getId());
        }
        Set<String> perms = this.myNvwaExtendProvider.getPermissions(nvwaCtx);
        nvwaCtx.setPerms(perms);
        if (null != this.nvwaContextWrapperProviders) {
            for (NvwaContextWrapperProvider nvwaContextWrapperProvider : this.nvwaContextWrapperProviders) {
                Object value;
                if (nvwaContextWrapperProvider.getWrapperType() != LoginConsts.WrapperType.EDIT || null != (value = nvwaCtx.getExtInfo(nvwaContextWrapperProvider.getId()))) continue;
                nvwaCtx.addExtInfo(nvwaContextWrapperProvider.getId(), nvwaContextWrapperProvider.getUnWrappedObject(nvwaCtx));
            }
        }
        return nvwaCtx;
    }

    private void convertLoginDate(NvwaContext nvwaCtx, NvwaLoginUserDTO userDTO) {
        TimeZone defaultZone = TimeZone.getDefault();
        Calendar ca = Calendar.getInstance(defaultZone);
        if (userDTO.getLoginUTCDate() != null) {
            ca.setTimeInMillis(userDTO.getLoginUTCDate());
        }
        ca.set(12, 0);
        ca.set(13, 0);
        ca.set(14, 0);
        nvwaCtx.setLoginDate(ca.getTime());
    }

    private void noticePasswrodError(NvwaLoginUserDTO userDTO, User user, R r) {
        for (NvwaLoginProvider verifyProvider : this.loginProviders) {
            try {
                verifyProvider.loginPasswordError(userDTO, user, r);
            }
            catch (Exception e) {
                LOGGER.error("\u767b\u5f55\u5f02\u5e38\uff01" + verifyProvider.getClass().getName() + ";\u767b\u5f55\u6821\u9a8c\u5668\u5931\u8d25\uff01", e);
            }
        }
    }

    @Override
    public User validate(NvwaLoginUserDTO userDTO, R r) throws Exception {
        SystemUserDTO npUser;
        String username = userDTO.getUsername();
        SystemUserDTO systemUserDTO = npUser = this.nvwaLoginProperties.isIgnoreCase() ? this.systemUserService.findByUsernameIgnoreCase(username) : this.systemUserService.findByUsername(username);
        if (npUser == null) {
            UserDTO userDTO2 = npUser = this.nvwaLoginProperties.isIgnoreCase() ? this.userService.findByUsernameIgnoreCase(username) : this.userService.findByUsername(username);
            if (npUser == null) {
                this.noticePasswrodError(userDTO, (User)npUser, r);
                if (r.getCode() == LoginState.SUCCESS.getCode() || r.getCode() == LoginState.OK.getCode()) {
                    r.setMsg(LoginState.EXCEPTION_USER_PWD.getCode(), LoginState.EXCEPTION_USER_PWD.getDescribe());
                }
                return null;
            }
        }
        if (userDTO.isCheckPwd()) {
            String pwd = userDTO.getPwd();
            Password.ValidateResult validateSamePwdExpire = this.pwdService.validateSamePwdExpire(npUser.getId(), pwd, null);
            if (validateSamePwdExpire == Password.ValidateResult.ERROR) {
                this.noticePasswrodError(userDTO, (User)npUser, r);
                if (r.getCode() == LoginState.SUCCESS.getCode() || r.getCode() == LoginState.OK.getCode()) {
                    r.setMsg(LoginState.EXCEPTION_USER_PWD.getCode(), LoginState.EXCEPTION_USER_PWD.getDescribe());
                }
                return null;
            }
            if (validateSamePwdExpire == Password.ValidateResult.DEPRECATED) {
                r.setMsg(LoginState.INIT_PASSWORD_EXPIRED.getCode(), LoginState.INIT_PASSWORD_EXPIRED.getDescribe());
                return null;
            }
        }
        if (npUser.isLocked()) {
            r.setMsg(LoginState.USER_LOCKED.getCode(), LoginState.USER_LOCKED.getDescribe());
            return null;
        }
        if (!npUser.isEnabled()) {
            r.setMsg(LoginState.USER_ENABLED.getCode(), LoginState.USER_ENABLED.getDescribe());
            return null;
        }
        if (null != npUser.getMarkDelete() && npUser.getMarkDelete().booleanValue()) {
            r.setMsg(LoginState.USER_MARK_DELETE.getCode(), LoginState.USER_MARK_DELETE.getDescribe());
            return null;
        }
        if (null != npUser.getExpiryTime() && Instant.now().isAfter(npUser.getExpiryTime().plus(Duration.ofDays(1L)))) {
            r.setMsg(LoginState.USER_EXPIRED.getCode(), LoginState.USER_EXPIRED.getDescribe());
            return null;
        }
        return npUser;
    }

    private NvwaContext getNvwaContext() {
        NvwaContext nvwaCtx = null;
        Object object = null;
        try {
            object = SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (object != null) {
            nvwaCtx = (NvwaContext)JSONUtil.parseObject((String)((String)object), NvwaContext.class);
        }
        return nvwaCtx;
    }

    @Override
    public R getLoginContext() {
        try {
            NvwaContext nvwaContext = this.getNvwaContext();
            if (nvwaContext == null) {
                return R.error();
            }
            nvwaContext.setMgrFlag(null);
            nvwaContext.setPerms(null);
            R r = R.ok();
            if (null != this.nvwaContextWrapperProviders) {
                Map<String, Object> extInfo = nvwaContext.getExtInfo();
                for (NvwaContextWrapperProvider nvwaContextWrapperProvider : this.nvwaContextWrapperProviders) {
                    try {
                        if (nvwaContextWrapperProvider.getWrapperType() == LoginConsts.WrapperType.ADD || nvwaContextWrapperProvider.getWrapperType() == LoginConsts.WrapperType.EDIT) {
                            nvwaContext.addExtInfo(nvwaContextWrapperProvider.getId(), nvwaContextWrapperProvider.getWrappedObject(nvwaContext));
                            continue;
                        }
                        extInfo.remove(nvwaContextWrapperProvider.getId());
                    }
                    catch (Exception e) {
                        LOGGER.error(nvwaContextWrapperProvider.getId() + " \u5305\u88c5\u4e0a\u4e0b\u6587\u62a5\u9519\uff01", e);
                    }
                }
            }
            TimeZone defaultZone = TimeZone.getDefault();
            TimeZone timeZone = LocaleContextHolder.getTimeZone();
            if (!defaultZone.getDisplayName().equals(timeZone.getDisplayName())) {
                Calendar ca = Calendar.getInstance(defaultZone);
                ca.setTime(nvwaContext.getLoginDate());
                ca.add(14, timeZone.getRawOffset() - defaultZone.getRawOffset());
                ca.set(11, 0);
                nvwaContext.setLoginDate(ca.getTime());
            }
            r.put("context", (Object)nvwaContext);
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u4e0a\u4e0b\u6587\u5f02\u5e38", e);
            return R.error();
        }
    }

    @Override
    public R refreshLoginContext() {
        NvwaContext nvwaContext = this.getNvwaContext();
        if (nvwaContext == null) {
            return R.error();
        }
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        User npUser = null;
        try {
            R r = R.ok();
            userDTO.setUsername(nvwaContext.getUsername());
            userDTO.setCheckPwd(false);
            userDTO.setLoginUTCDate(nvwaContext.getLoginDate().getTime());
            userDTO.setLoginUnit(nvwaContext.getLoginUnit());
            userDTO.setExtInfo(nvwaContext.getExtInfo());
            userDTO.setTenant(nvwaContext.getTenantName());
            npUser = this.validate(userDTO, r);
            if (r.getCode() != LoginState.SUCCESS.getCode() && r.getCode() != LoginState.OK.getCode()) {
                this.nvwaLoginLogHelper.failLog(userDTO, npUser, "\u5237\u65b0\u7528\u6237\u5f53\u524d\u4e0a\u4e0b\u6587\u5931\u8d25\uff01" + r.getMsg());
                return r;
            }
            NvwaContext context = this.getContext(userDTO, npUser);
            String realmName = (String)ShiroUtil.getSubjct().getPrincipals().getRealmNames().iterator().next();
            SimplePrincipalCollection spc = new SimplePrincipalCollection((Object)JSONUtil.toJSONString((Object)context, (String)"##long"), realmName);
            SecurityUtils.getSubject().runAs((PrincipalCollection)spc);
            return R.ok();
        }
        catch (Exception e) {
            LOGGER.error("\u5237\u65b0\u5f53\u524d\u7528\u6237\u4e0a\u4e0b\u6587\u53d1\u751f\u5f02\u5e38", e);
            this.nvwaLoginLogHelper.failLog(userDTO, npUser, "\u5237\u65b0\u7528\u6237\u5f53\u524d\u4e0a\u4e0b\u6587\u53d1\u751f\u5f02\u5e38:" + e.getMessage());
            return R.error((int)LoginState.FAIL.getCode(), (String)"\u5237\u65b0\u7528\u6237\u5f53\u524d\u4e0a\u4e0b\u6587\u53d1\u751f\u5f02\u5e38");
        }
    }

    @Override
    public NvwaLoginUserDTO checkChangeLoginContext(Map<String, Object> params) {
        String id;
        Object contextOrg;
        Object contextIdentity;
        Object conetxtUser;
        NvwaContext nvwaContext = this.getNvwaContext();
        if (nvwaContext == null) {
            throw new ChangeLoginContextException("\u7528\u6237\u4fe1\u606f\u4e3a\u7a7a\uff01");
        }
        if (params.containsKey("conetxtUser") && null != (conetxtUser = params.get("conetxtUser"))) {
            NvwaContextUser fromWebUser = (NvwaContextUser)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)conetxtUser), NvwaContextUser.class);
            if (!nvwaContext.getConetxtUser().equals(fromWebUser)) {
                LOGGER.error("\u7528\u6237\u4fe1\u606f\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)JSONUtil.toJSONString((Object)conetxtUser), (Object)JSONUtil.toJSONString((Object)nvwaContext.getConetxtUser()));
                throw new ChangeLoginContextException("\u7528\u6237\u4fe1\u606f\u88ab\u7be1\u6539\uff01");
            }
        }
        if (params.containsKey("contextIdentity") && null != (contextIdentity = params.get("contextIdentity"))) {
            NvwaContextIdentity fromWebIdentity = (NvwaContextIdentity)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)contextIdentity), NvwaContextIdentity.class);
            if (!nvwaContext.getContextIdentity().equals(fromWebIdentity)) {
                LOGGER.error("\u8eab\u4efd\u4fe1\u606f\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)JSONUtil.toJSONString((Object)contextIdentity), (Object)JSONUtil.toJSONString((Object)nvwaContext.getContextIdentity()));
                throw new ChangeLoginContextException("\u8eab\u4efd\u4fe1\u606f\u88ab\u7be1\u6539\uff01");
            }
        }
        if (params.containsKey("contextOrg") && null != (contextOrg = params.get("contextOrg"))) {
            NvwaContextOrg fromWebOrg = (NvwaContextOrg)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)contextOrg), NvwaContextOrg.class);
            if (!nvwaContext.getContextOrg().equals(fromWebOrg)) {
                LOGGER.error("\u673a\u6784\u4fe1\u606f\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)JSONUtil.toJSONString((Object)contextOrg), (Object)JSONUtil.toJSONString((Object)nvwaContext.getContextOrg()));
                throw new ChangeLoginContextException("\u673a\u6784\u4fe1\u606f\u88ab\u7be1\u6539\uff01");
            }
        }
        if (StringUtils.hasLength(id = (String)params.get("id")) && !id.equals(nvwaContext.getId())) {
            LOGGER.error("\u7528\u6237id\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)id, (Object)nvwaContext.getId());
            throw new ChangeLoginContextException("\u7528\u6237id\u88ab\u7be1\u6539\uff01");
        }
        String username = (String)params.get("username");
        if (StringUtils.hasLength(username) && !username.equals(nvwaContext.getUsername())) {
            LOGGER.error("\u7528\u6237\u767b\u5f55\u540d\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)username, (Object)nvwaContext.getUsername());
            throw new ChangeLoginContextException("\u7528\u6237\u767b\u5f55\u540d\u88ab\u7be1\u6539\uff01");
        }
        String tenantName = (String)(params.containsKey("tenantName") ? params.get("tenantName") : params.get("tenant"));
        if (StringUtils.hasLength(tenantName) && !tenantName.equals(nvwaContext.getTenantName())) {
            if (TenantUtil.isMultiTenant()) {
                List list = this.tenantInfoClient.nameList();
                if (list != null && !list.contains(tenantName)) {
                    LOGGER.error("\u7528\u6237\u79df\u6237\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)tenantName, (Object)nvwaContext.getTenantName());
                    throw new ChangeLoginContextException("\u7528\u6237\u79df\u6237\u88ab\u7be1\u6539\uff01");
                }
            } else {
                LOGGER.error("\u7528\u6237\u79df\u6237\u88ab\u7be1\u6539\uff01web:{},session:{}", (Object)tenantName, (Object)nvwaContext.getTenantName());
                throw new ChangeLoginContextException("\u7528\u6237\u79df\u6237\u88ab\u7be1\u6539\uff01");
            }
        }
        Map extInfoFromWeb = (Map)params.get("extInfo");
        Map<String, Object> extInfo = nvwaContext.getExtInfo();
        if (null != extInfoFromWeb) {
            Set entrySet = extInfoFromWeb.entrySet();
            for (Map.Entry entry : entrySet) {
                Optional<NvwaContextWrapperProvider> any;
                boolean white = false;
                if (null != extInfo && extInfo.containsKey(entry.getKey())) {
                    white = true;
                }
                if (!white && null != this.nvwaContextWrapperProviders && (any = this.nvwaContextWrapperProviders.stream().filter(e -> e.getId().equals(entry.getKey())).findAny()).isPresent()) {
                    white = true;
                }
                if (white) continue;
                LOGGER.error("\u7528\u6237\u672a\u77e5\u7684\u4e0a\u4e0b\u6587\u6269\u5c55\u4fe1\u606f\uff01web:{}", entry.getKey());
                throw new ChangeLoginContextException("\u7528\u6237\u672a\u77e5\u7684\u4e0a\u4e0b\u6587\u6269\u5c55\u4fe1\u606f\uff01");
            }
        }
        NvwaLoginUserDTO userInfoDTO = new NvwaLoginUserDTO();
        userInfoDTO.setExtInfo(extInfoFromWeb);
        userInfoDTO.setTenant(tenantName);
        userInfoDTO.setLoginUnit((String)params.get("loginUnit"));
        userInfoDTO.setLoginDate((String)params.get("loginDate"));
        return userInfoDTO;
    }

    @Override
    public R changeLoginContext(NvwaLoginUserDTO userDTO) {
        String loginUnit;
        NvwaContext nvwaContext = this.getNvwaContext();
        if (nvwaContext == null) {
            return R.error();
        }
        if (userDTO.getLoginUTCDate() != null) {
            this.convertLoginDate(nvwaContext, userDTO);
        }
        NvwaContextIdentity conetxtIdentity = this.myNvwaExtendProvider.buildContextIdentity(nvwaContext.getConetxtUser(), userDTO.getExtInfo());
        nvwaContext.setContextIdentity(conetxtIdentity);
        if (conetxtIdentity != null) {
            nvwaContext.addExtInfo("contextIdentity", conetxtIdentity.getId());
        }
        if ((loginUnit = userDTO.getLoginUnit()) == null || "".equals(loginUnit)) {
            loginUnit = nvwaContext.getLoginUnit();
        }
        if (StringUtils.hasLength(loginUnit)) {
            NvwaContextUser conetxtUser = nvwaContext.getConetxtUser();
            boolean existOrgAuth = this.myNvwaExtendProvider.existOrgAuth(loginUnit, conetxtUser, conetxtIdentity);
            if (!existOrgAuth) {
                LOGGER.error("\u5207\u6362\u4e0a\u4e0b\u6587\u5f02\u5e38\uff01\u7528\u6237\uff1a[" + conetxtUser.getName() + "]\u8eab\u4efd\uff1a[" + (conetxtIdentity != null ? conetxtIdentity.getId() : "") + " ]\u5bf9\u673a\u6784\uff1a[" + loginUnit + "]\u65e0\u8bbf\u95ee\u6743\u9650\uff01\u65e0\u6cd5\u767b\u5f55\u5230\u8be5\u673a\u6784,\u56de\u843d\u5230\u8eab\u4efd\u6240\u5c5e\u673a\u6784\uff1a[" + conetxtIdentity.getOrgCode() + "]");
                loginUnit = conetxtIdentity.getOrgCode();
            }
            if (StringUtils.hasLength(loginUnit)) {
                nvwaContext.setLoginUnit(loginUnit);
                NvwaContextOrg ctxOrg = new NvwaContextOrg();
                ctxOrg.setCode(loginUnit);
                ctxOrg.setName(this.myNvwaExtendProvider.getOrgTitle(loginUnit));
                nvwaContext.setContextOrg(ctxOrg);
            }
        }
        if (userDTO.getExtInfo() != null) {
            userDTO.getExtInfo().entrySet().stream().forEach(o -> nvwaContext.addExtInfo((String)o.getKey(), o.getValue()));
            if (null != this.nvwaContextWrapperProviders) {
                Map<String, Object> extInfo = nvwaContext.getExtInfo();
                for (NvwaContextWrapperProvider nvwaContextWrapperProvider : this.nvwaContextWrapperProviders) {
                    if (nvwaContextWrapperProvider.getWrapperType() == LoginConsts.WrapperType.ADD) {
                        extInfo.remove(nvwaContextWrapperProvider.getId());
                        continue;
                    }
                    if (nvwaContextWrapperProvider.getWrapperType() != LoginConsts.WrapperType.EDIT) continue;
                    nvwaContext.addExtInfo(nvwaContextWrapperProvider.getId(), nvwaContextWrapperProvider.getUnWrappedObject(nvwaContext));
                }
            }
        }
        if (StringUtils.hasLength(userDTO.getTenant())) {
            nvwaContext.setTenantName(userDTO.getTenant());
        }
        Set<String> perms = this.myNvwaExtendProvider.getPermissions(nvwaContext);
        nvwaContext.setPerms(perms);
        if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
            for (NvwaLoginProvider verifyProvider : this.loginProviders) {
                try {
                    verifyProvider.loginContextChangeBefore(nvwaContext);
                }
                catch (Exception e) {
                    LOGGER.error("\u5207\u6362\u4e0a\u4e0b\u6587\u5f02\u5e38\uff01" + verifyProvider.getClass().getName() + "\uff01", e);
                }
            }
        }
        String realmName = (String)ShiroUtil.getSubjct().getPrincipals().getRealmNames().iterator().next();
        SimplePrincipalCollection spc = new SimplePrincipalCollection((Object)JSONUtil.toJSONString((Object)nvwaContext, (String)"##long"), realmName);
        SecurityUtils.getSubject().runAs((PrincipalCollection)spc);
        return R.ok();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R logout() {
        R rs = R.ok();
        String tokenId = ShiroUtil.getToken();
        if (tokenId == null) {
            return rs;
        }
        try {
            if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
                for (NvwaLoginProvider loginProvider : this.loginProviders) {
                    loginProvider.logout(rs);
                    if (rs.getCode() == 0) continue;
                    R r = rs;
                    return r;
                }
            }
            this.nvwaLoginLogHelper.logout();
            SecurityUtils.getSubject().logout();
        }
        finally {
            try {
                Session session = this.sessionDAO.readSession((Serializable)((Object)tokenId));
                if (session != null) {
                    this.sessionDAO.delete(session);
                }
            }
            catch (Exception exception) {}
        }
        return rs;
    }

    @Override
    public long getLoginSessionCount(String tentant) {
        int cnt = 0;
        try {
            Collection sessions = this.sessionDAO.getActiveSessions();
            if (tentant == null) {
                return sessions.size();
            }
            String ctxJson = null;
            TenantDO tenantDO = null;
            for (Session session : sessions) {
                Object attribute = session.getAttribute((Object)DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (null == attribute) continue;
                try {
                    ctxJson = attribute.toString();
                    tenantDO = (TenantDO)JSONUtil.parseObject((String)ctxJson, TenantDO.class);
                    if (!tenantDO.getTenantName().equalsIgnoreCase(tentant)) continue;
                    ++cnt;
                }
                catch (Exception e) {
                    LOGGER.error("\u7edf\u8ba1\u79df\u6237\u4e0b\u767b\u5f55\u6570\u91cf\u5f02\u5e38(session\u5185\u4fe1\u606f\u8f6c\u6362\u5bf9\u8c61\u5f02\u5e38)\uff1atentant\uff1a" + tentant, e);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("\u7edf\u8ba1\u79df\u6237\u4e0b\u767b\u5f55\u6570\u91cf\u5f02\u5e38\uff1atentant\uff1a" + tentant, e);
        }
        return cnt;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R forceLogout(String tenant, String username) {
        try {
            Collection sessions = this.sessionDAO.getActiveSessions();
            String ctxJson = null;
            NvwaContext nvwaContext = null;
            for (Session session : sessions) {
                Object attribute = session.getAttribute((Object)DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (null == attribute || !(nvwaContext = (NvwaContext)JSONUtil.parseObject((String)(ctxJson = attribute.toString()), NvwaContext.class)).getTenantName().equalsIgnoreCase(tenant) || !nvwaContext.getUsername().equalsIgnoreCase(username)) continue;
                try {
                    R rs = R.ok();
                    if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
                        for (NvwaLoginProvider loginProvider : this.loginProviders) {
                            loginProvider.logout(rs);
                        }
                    }
                    this.nvwaLoginLogHelper.forceLogout(nvwaContext);
                }
                finally {
                    try {
                        session.setTimeout(0L);
                        session.stop();
                        this.sessionDAO.delete(session);
                    }
                    catch (Exception exception) {}
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5f3a\u5236\u8e22\u51fa\u7528\u6237\u767b\u5f55\u5f02\u5e38\uff1atentant\uff1a" + tenant + ";username:" + username, e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R forceLogout(String tokenId) {
        block11: {
            try {
                Session session = this.sessionDAO.readSession((Serializable)((Object)tokenId));
                if (null == session) break block11;
                Object attribute = session.getAttribute((Object)DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                String ctxJson = null;
                NvwaContext nvwaContext = null;
                if (null == attribute) break block11;
                ctxJson = attribute.toString();
                nvwaContext = (NvwaContext)JSONUtil.parseObject((String)ctxJson, NvwaContext.class);
                try {
                    R rs = R.ok();
                    if (this.loginProviders != null && !this.loginProviders.isEmpty()) {
                        for (NvwaLoginProvider loginProvider : this.loginProviders) {
                            loginProvider.logout(rs);
                        }
                    }
                    this.nvwaLoginLogHelper.forceLogout(nvwaContext);
                }
                finally {
                    try {
                        session.setTimeout(0L);
                        session.stop();
                        this.sessionDAO.delete(session);
                    }
                    catch (Exception exception) {}
                }
            }
            catch (Exception e) {
                LOGGER.error("\u5f3a\u5236\u8e22\u51fa\u7528\u6237\u767b\u5f55\u5f02\u5e38\uff1atokenId\uff1a" + tokenId, e);
                return R.error((String)e.getMessage());
            }
        }
        return R.ok();
    }
}

