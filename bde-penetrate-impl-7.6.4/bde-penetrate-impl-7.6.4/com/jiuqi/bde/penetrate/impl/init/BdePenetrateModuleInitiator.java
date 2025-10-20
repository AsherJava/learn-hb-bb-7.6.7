/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.init.IBdeModuleItemInitiator
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.nvwa.authority.service.AuthorityService
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthorityState
 *  com.jiuqi.nvwa.authority.vo.AuthorityPrivilegeSaveReq
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.login.domain.NvwaContext
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletContext
 *  org.apache.shiro.SecurityUtils
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.penetrate.impl.init;

import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.nvwa.authority.service.AuthorityService;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.AuthorityPrivilegeSaveReq;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BdePenetrateModuleInitiator
implements IBdeModuleItemInitiator {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AuthorityService authorityService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String SELECT_ROUTE_SQL = "SELECT MIN(ID) AS ID, APP_NAME, MIN(R_TITLE) AS TITLE FROM NVWA_ROUTE WHERE PRODUCT_LINE ='@bde' AND APP_NAME IN ('bde-logmanager/LogResultDetail','bde-penetrate/FetchPerspective')  AND DESIGN_ID IS NULL  GROUP BY APP_NAME";
    @Value(value="${jiuqi.np.user.system[0].name:}")
    private String userName;

    public void initDefaultRoute() throws Exception {
        List routes = this.jdbcTemplate.query(SELECT_ROUTE_SQL, (RowMapper)new BeanPropertyRowMapper(Route.class));
        if (CollectionUtils.isEmpty((Collection)routes)) {
            this.LOGGER.info("\u6ca1\u6709\u9700\u8981\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355");
            return;
        }
        AuthorityPrivilegeSaveReq privilegeSaveReq = new AuthorityPrivilegeSaveReq();
        GranteeInfo granteeInfo = new GranteeInfo();
        granteeInfo.setEveryone(Boolean.valueOf(true));
        granteeInfo.setOwnerId("ffffffff-ffff-ffff-bbbb-ffffffffffff");
        granteeInfo.setIsRole(Boolean.valueOf(true));
        privilegeSaveReq.setGranteeInfo(granteeInfo);
        HashMap authority = new HashMap();
        for (Object route : routes) {
            HashMap<String, AuthorityConst.AuthorityState> authInfo = new HashMap<String, AuthorityConst.AuthorityState>();
            authInfo.put("22222222-1111-1111-1111-222222222222", AuthorityConst.AuthorityState.UNKNOW);
            authInfo.put("route_privilege_read", AuthorityConst.AuthorityState.ALLOW);
            authInfo.put("22222222-3333-3333-3333-222222222222", AuthorityConst.AuthorityState.UNKNOW);
            authority.put(route.getId(), authInfo);
        }
        privilegeSaveReq.setAuthority(authority);
        privilegeSaveReq.setResCategoryId("RouteResourceCategory-c03c30d323d9");
        HashMap<String, String> resourceTitles = new HashMap<String, String>();
        for (Route route : routes) {
            resourceTitles.put(route.getId(), route.getTitle());
        }
        NvwaLoginService loginService = (NvwaLoginService)ApplicationContextRegister.getBean(NvwaLoginService.class);
        NvwaLoginUserDTO nvwaLoginUser = new NvwaLoginUserDTO();
        nvwaLoginUser.setCheckPwd(false);
        nvwaLoginUser.setUsername(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName);
        R tryLogin = loginService.tryLogin(nvwaLoginUser, true);
        this.LOGGER.debug("\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355\u6a21\u62df\u767b\u9646\u5b8c\u6210", (Object)tryLogin);
        Object object = null;
        try {
            object = SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (object != null) {
            NvwaContext nvWaContext = (NvwaContext)JSONUtil.parseObject((String)((String)object), NvwaContext.class);
            NpContextImpl npContext = new NpContextImpl();
            npContext.setTenant(nvWaContext.getTenantName());
            npContext.setLoginDate(nvWaContext.getLoginDate());
            npContext.setUser((ContextUser)nvWaContext.getConetxtUser());
            npContext.setOrganization((ContextOrganization)nvWaContext.getContextOrg());
            npContext.setIdentity((ContextIdentity)nvWaContext.getContextIdentity());
            npContext.setLocale(LocaleContextHolder.getLocale());
            NpContextHolder.setContext((NpContext)npContext);
            this.LOGGER.debug("\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355\u5199\u5165\u4e0a\u4e0b\u6587\u5b8c\u6210", (Object)npContext);
        }
        privilegeSaveReq.setResourceTitles(resourceTitles);
        this.authorityService.privilegeSave(privilegeSaveReq);
    }

    public String getName() {
        return "\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355";
    }

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.LOGGER.info("\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355\u6267\u884c\u5f00\u59cb");
        try {
            this.initDefaultRoute();
        }
        catch (Exception e) {
            this.LOGGER.info("\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355\u51fa\u73b0\u9519\u8bef", e);
        }
        this.LOGGER.info("\u56fa\u5316BDE\u900f\u89c6\u9ed8\u8ba4\u83dc\u5355\u6267\u884c\u7ed3\u675f");
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

