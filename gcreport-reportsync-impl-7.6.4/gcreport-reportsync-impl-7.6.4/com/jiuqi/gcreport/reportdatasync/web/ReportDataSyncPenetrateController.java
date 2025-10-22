/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.itextpdf.xmp.impl.Base64
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncPenetrateClient
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl
 *  com.jiuqi.nr.dataentry.service.ITemplateConfigService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.framework.nros.bean.dto.NvwaAppInitCheckDTO
 *  com.jiuqi.nvwa.framework.nros.bean.vo.ResultObject
 *  com.jiuqi.nvwa.framework.nros.web.RouteController
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.itextpdf.xmp.impl.Base64;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncPenetrateClient;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.framework.nros.bean.dto.NvwaAppInitCheckDTO;
import com.jiuqi.nvwa.framework.nros.bean.vo.ResultObject;
import com.jiuqi.nvwa.framework.nros.web.RouteController;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ReportDataSyncPenetrateController
implements ReportDataSyncPenetrateClient {
    @Autowired
    private INvwaAppRequestManage nvwaAppRequestManage;
    @Autowired
    private ITemplateConfigService templateConfigService;
    private Logger logger = LoggerFactory.getLogger(ReportDataSyncPenetrateController.class);
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private RouteController routeController;

    public void multiLevelPentrateRedirect(HttpServletRequest request, HttpServletResponse response, String params) throws Exception {
        String parasmJson = Base64.decode((String)params);
        Map paramMap = (Map)JsonUtils.readValue((String)parasmJson, (TypeReference)new TypeReference<Map<String, String>>(){});
        String url = (String)paramMap.get("url");
        response.sendRedirect(url + "multiLevel/multiLevelPentrate/?params=" + params);
    }

    public void multiLevelPentrate(HttpServletRequest request, HttpServletResponse response, String params) throws Exception {
        NvwaSsoBuildDTO nvwaSsoBuildDTO = new NvwaSsoBuildDTO();
        String parasmJson = Base64.decode((String)params);
        Map paramMap = (Map)JsonUtils.readValue((String)parasmJson, (TypeReference)new TypeReference<Map<String, String>>(){});
        String userName = (String)paramMap.get("userName");
        nvwaSsoBuildDTO.setUserName(userName);
        User user = this.getUserByUserName(userName);
        if (user == null || user.getId() == null) {
            throw new BusinessRuntimeException("\u7a7f\u900f\u7528\u6237\u540d[" + userName + "]\u5728\u4f01\u4e1a\u7aef\u4e0d\u5b58\u5728\u3002");
        }
        if (!ObjectUtils.isEmpty(userName)) {
            NpContextImpl npContext = this.buildContext(userName);
            NpContextHolder.setContext((NpContext)npContext);
        }
        nvwaSsoBuildDTO.setJumpType("app");
        nvwaSsoBuildDTO.setScope("@nr");
        nvwaSsoBuildDTO.setAppName("dataentry");
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        HashMap<String, String> locate = new HashMap<String, String>();
        String orgCode = (String)paramMap.get("orgCode");
        PageVO<OrgDO> baseOrgs = ReportDataSyncDataUtils.listBaseOrgs(null);
        Map<String, String> gzCode2OrgCode = baseOrgs.getRows().stream().filter(orgDO -> !StringUtils.isEmpty((String)((String)orgDO.get((Object)"gzcode")))).collect(Collectors.toMap(orgDo -> (String)orgDo.get((Object)"gzcode"), OrgDO::getCode, (o1, o2) -> o2));
        if (gzCode2OrgCode.containsKey(orgCode)) {
            orgCode = gzCode2OrgCode.get(orgCode);
        }
        locate.put("unitId", orgCode);
        locate.put("formId", (String)paramMap.get("formId"));
        locate.put("regionId", (String)paramMap.get("regionId"));
        locate.put("dataLinkKey", (String)paramMap.get("dataLinkKey"));
        String bizkeyorder = (String)paramMap.get("bizkeyorder");
        if (!StringUtils.isEmpty((String)bizkeyorder)) {
            locate.put("bizkeyorder", bizkeyorder);
        }
        locate.put("dataLinkKey", (String)paramMap.get("dataLinkKey"));
        String taskId = (String)paramMap.get("taskId");
        paramsMap.put("locate", locate);
        paramsMap.put("taskId", paramMap.get("taskId"));
        String formSchemeId = (String)paramMap.get("formSchemeId");
        paramsMap.put("formSchemeId", paramMap.get("formSchemeId"));
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formSchemeDefines = iRunTimeViewController.getFormScheme(formSchemeId);
        if (formSchemeDefines == null) {
            throw new BusinessRuntimeException("\u8be5\u62a5\u8868\u5728\u4f01\u4e1a\u7aef\u4e0d\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5");
        }
        String adjustCode = (String)paramMap.get("adjustCode");
        if (null != adjustCode && !adjustCode.equals("")) {
            if (!DimensionUtils.isExistAdjust((String)taskId)) {
                throw new RuntimeException("\u672a\u627e\u5230\u5f53\u524d\u5355\u4f4d\u5bf9\u5e94\u7684\u65f6\u671f\u6570\u636e");
            }
            paramsMap.put("adjust", paramMap.get("adjustCode"));
        }
        paramsMap.put("period", paramMap.get("period"));
        List allTemplateConfig = this.templateConfigService.getAllTemplateConfig();
        String dataentryDefineCode = ((TemplateConfigImpl)allTemplateConfig.get(0)).getCode();
        paramsMap.put("dataentryDefineCode", dataentryDefineCode);
        HashMap<String, HashMap<String, Object>> appConfigMap = new HashMap<String, HashMap<String, Object>>();
        appConfigMap.put("appConfig", paramsMap);
        String openConfig = JsonUtils.writeValueAsString(appConfigMap);
        nvwaSsoBuildDTO.setAppConfig(openConfig);
        String ssoLocation = this.nvwaAppRequestManage.buildCurSsoLocation(nvwaSsoBuildDTO);
        this.checkAuthority(nvwaSsoBuildDTO);
        response.sendRedirect("/#" + ssoLocation);
    }

    private void checkAuthority(NvwaSsoBuildDTO nvwaSsoBuildDTO) {
        NvwaAppInitCheckDTO nvwaAppInitCheckDTO = new NvwaAppInitCheckDTO();
        nvwaAppInitCheckDTO.setScope(nvwaSsoBuildDTO.getScope());
        nvwaAppInitCheckDTO.setName(nvwaSsoBuildDTO.getAppName());
        ResultObject resultObject = this.routeController.appInitCheck(nvwaAppInitCheckDTO);
        if (!resultObject.isSuccess()) {
            throw new RuntimeException(resultObject.getMessage());
        }
    }

    public User getUserByUserName(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }

    public NpContextImpl buildContext(String userName) {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    protected NpContextUser buildUserContext(String userName) {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u4e0d\u5230\u7528\u6237\u4fe1\u606f[" + userName + "]\u3002");
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }
}

