/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.framework.nros.bean.RouteParam
 *  com.jiuqi.nvwa.framework.nros.bean.UiScheme
 *  com.jiuqi.nvwa.framework.nros.service.IRouteParamService
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 *  com.jiuqi.nvwa.framework.nros.service.IUiSchemeService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.framework.nros.bean.RouteParam;
import com.jiuqi.nvwa.framework.nros.bean.UiScheme;
import com.jiuqi.nvwa.framework.nros.service.IRouteParamService;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import com.jiuqi.nvwa.framework.nros.service.IUiSchemeService;
import com.jiuqi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class DataentryInnerTemplateExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataentryInnerTemplateExecutor.class);
    private static final String DATAENTRY_TEMPLATE_INNER_CODE = "inner_template";

    public void execute(DataSource dataSource) throws Exception {
        ClassPathResource templateResource = new ClassPathResource("template/templateConfig_inner.json");
        String defaultConfig = "";
        try (InputStream input = templateResource.getInputStream();){
            defaultConfig = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        JSONObject defaultConfigJson = new JSONObject(defaultConfig);
        JSONObject configJson = (JSONObject)defaultConfigJson.get("config");
        String templateId = UUID.randomUUID().toString();
        configJson.put("templateId", (Object)templateId);
        configJson.put("code", (Object)DATAENTRY_TEMPLATE_INNER_CODE);
        configJson.put("title", (Object)"\u5185\u7f6e\u6a21\u677f");
        defaultConfigJson.put("config", (Object)configJson);
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        templateConfig.setCode(DATAENTRY_TEMPLATE_INNER_CODE);
        templateConfig.setTitle("\u5185\u7f6e\u6a21\u677f");
        templateConfig.setTemplate("standardTemplate");
        templateConfig.setTemplateId(templateId);
        templateConfig.setTemplateConfig(JSONObject.valueToString((Object)defaultConfigJson));
        ITemplateConfigService templateConfigService = (ITemplateConfigService)BeanUtil.getBean(ITemplateConfigService.class);
        templateConfigService.addTemplate_old(templateConfig);
        IUiSchemeService uiSchemeService = (IUiSchemeService)BeanUtil.getBean(IUiSchemeService.class);
        IRouteService routeService = (IRouteService)BeanUtil.getBean(IRouteService.class);
        IRouteParamService routeParamService = (IRouteParamService)BeanUtil.getBean(IRouteParamService.class);
        List uiSchemes = uiSchemeService.list();
        for (UiScheme u : uiSchemes) {
            boolean publish = false;
            List designRoutes = routeService.getDesignRoutes(u.getId());
            ArrayList<RouteParam> params = new ArrayList<RouteParam>();
            for (Route route : designRoutes) {
                JSONObject jsonParam;
                if (!"dataentry".equals(route.getAppName())) continue;
                RouteParam routeParam = routeParamService.searchById(route.getId());
                if (routeParam != null) {
                    jsonParam = new JSONObject(routeParam.getConfigJson());
                    if (jsonParam.has("dataentryDefineCode") && !StringUtils.isEmpty((String)jsonParam.getString("dataentryDefineCode"))) continue;
                    publish = true;
                    jsonParam.put("dataentryDefineCode", (Object)DATAENTRY_TEMPLATE_INNER_CODE);
                    routeParam.setConfigJson(jsonParam.toString());
                    params.add(routeParam);
                    continue;
                }
                publish = true;
                jsonParam = new JSONObject();
                jsonParam.put("dataentryDefineCode", (Object)DATAENTRY_TEMPLATE_INNER_CODE);
                routeParam = new RouteParam();
                routeParam.setRouteId(route.getId());
                routeParam.setDesignId(route.getDesignId());
                routeParam.setConfigJson(jsonParam.toString());
                params.add(routeParam);
            }
            routeParamService.batchSave(params, true);
            if (!publish) continue;
            routeParamService.publish(u.getId());
        }
    }
}

