/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 *  com.jiuqi.nvwa.homepage.bean.HomePage
 *  com.jiuqi.nvwa.homepage.service.IHomePageService
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.common.systemparam.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.systemparam.consts.EntSystemParamInitConst;
import com.jiuqi.common.systemparam.util.EntLocalFileParamImportUtil;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import com.jiuqi.nvwa.homepage.bean.HomePage;
import com.jiuqi.nvwa.homepage.service.IHomePageService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntHomePagesInitExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INIT_HOMEPAGE_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initHomePage.nvdata";

    public void execute(DataSource dataSource) throws Exception {
        EntLocalFileParamImportUtil.importParam(this.INIT_HOMEPAGE_FILE_PATH, progressLogConsumer -> this.logger.info("\u9884\u7f6e\u9996\u9875\u521d\u59cb\u5316\uff1a" + progressLogConsumer.getMsg()));
        IHomePageService homePageService = (IHomePageService)SpringContextUtils.getBean(IHomePageService.class);
        List designHomePageList = homePageService.getDesignHomePageList(false);
        if (CollectionUtils.isEmpty((Collection)designHomePageList)) {
            this.logger.info("\u672a\u627e\u5230\u8bbe\u8ba1\u671f\u7684\u9996\u9875\u914d\u7f6e\uff0c\u8df3\u8fc7\u53d1\u5e03\u9636\u6bb5");
            return;
        }
        HomePage homePage = (HomePage)designHomePageList.stream().filter(item -> !StringUtils.isEmpty((String)item.getTemplate())).collect(Collectors.toList()).get(0);
        this.handlerFunctionList(homePageService, homePage);
        homePageService.publish(homePage.getHomeId(), "zh-CN");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handlerFunctionList(IHomePageService homePageService, HomePage homePage) {
        NpContext context = NpContextHolder.getContext();
        try {
            if (context == null || StringUtils.isEmpty((String)context.getIdentityId())) {
                EntHomePagesInitExecutor.initUserInfo();
            }
            IRouteService routeService = (IRouteService)SpringContextUtils.getBean(IRouteService.class);
            Map<String, String> routeNameToIdMap = routeService.getRuntimeRoutes().stream().collect(Collectors.toMap(Route::getTitle, Route::getId, (o1, o2) -> o1));
            JSONObject defaultTemplate = new JSONObject(homePage.getTemplate());
            JSONArray modulesConfig = defaultTemplate.getJSONArray("modulesConfig");
            JSONObject funJson = null;
            for (int i = 0; i < modulesConfig.length(); ++i) {
                JSONObject jsonObject = modulesConfig.getJSONObject(i);
                if (!"CommonFun-properties".equals(jsonObject.get("propertiesType"))) continue;
                funJson = jsonObject;
                break;
            }
            if (funJson != null) {
                JSONArray funList = funJson.getJSONObject("properties").getJSONArray("funList");
                for (int i = 0; i < funList.length(); ++i) {
                    JSONObject jsonObject = funList.getJSONObject(i);
                    String appTitle = jsonObject.getString("title");
                    jsonObject.put("appId", (Object)routeNameToIdMap.get(appTitle));
                }
            }
            homePage.setTemplate(defaultTemplate.toString());
            homePageService.modify(homePage);
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u5e38\u7528\u529f\u80fdappid\u5931\u8d25\uff0c\u8df3\u8fc7\u5904\u7406\u5e38\u7528\u529f\u80fd\uff1a" + e.getMessage(), e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private static void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = EntHomePagesInitExecutor.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)EntHomePagesInitExecutor.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private static NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private static NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }
}

