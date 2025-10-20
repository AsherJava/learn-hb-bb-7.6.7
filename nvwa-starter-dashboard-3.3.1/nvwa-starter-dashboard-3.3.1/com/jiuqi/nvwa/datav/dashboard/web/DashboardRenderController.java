/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.SimpleExportor
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.datav.chart.ChartException
 *  com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam
 *  com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils
 *  com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException
 *  com.jiuqi.nvwa.datav.dashboard.adapter.IDashboardTreeNodeManager
 *  com.jiuqi.nvwa.datav.dashboard.adapter.chart.IDashboardScreenshotSdk
 *  com.jiuqi.nvwa.datav.dashboard.controller.R
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardItem
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager
 *  com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException
 *  com.jiuqi.nvwa.datav.dashboard.theme.manager.DashboardThemeManager
 *  com.jiuqi.nvwa.datav.dashboard.theme.model.Theme
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.datav.dashboard.web;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.SimpleExportor;
import com.jiuqi.bi.util.JqLib;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.datav.chart.ChartException;
import com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam;
import com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils;
import com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException;
import com.jiuqi.nvwa.datav.dashboard.adapter.IDashboardTreeNodeManager;
import com.jiuqi.nvwa.datav.dashboard.adapter.chart.IDashboardScreenshotSdk;
import com.jiuqi.nvwa.datav.dashboard.controller.R;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardItem;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardRenderCacheManager;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDashboardRenderProvider;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager;
import com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException;
import com.jiuqi.nvwa.datav.dashboard.theme.manager.DashboardThemeManager;
import com.jiuqi.nvwa.datav.dashboard.theme.model.Theme;
import com.jiuqi.nvwa.datav.dashboard.web.DashboardRenderDTO;
import com.jiuqi.nvwa.datav.dashboard.web.WidgetRenderDTO;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/nvwa/datav/dashboard/v1"})
public class DashboardRenderController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardRenderController.class);
    @Autowired
    private DashboardManager dashboardManager;
    @Autowired
    private DashboardThemeManager dashboardThemeManager;
    @Autowired
    private IDashboardTreeNodeManager dashboardTreeNodeManager;
    @Autowired
    private IDashboardScreenshotSdk screenshotSdk;
    @Value(value="${jiuqi.nvwa.dashboard.link.send-ext:false}")
    private String sendExt;

    @PostMapping(value={"/query-dashboard"})
    public String queryDashboard(@RequestBody DashboardRenderDTO dashboardRenderDTO) {
        try {
            String dashboardModelStr = dashboardRenderDTO.getDashboardModel();
            DashboardModel dashboardModel = null;
            JSONObject result = new JSONObject();
            if (StringUtils.isNotEmpty((String)dashboardModelStr)) {
                dashboardModel = new DashboardModel();
                dashboardModel.fromJSON(new JSONObject(JqLib.decodePassword((String)dashboardModelStr)));
            } else if (StringUtils.isNotEmpty((String)dashboardRenderDTO.getDashboardGuid())) {
                String dashboardGuid = dashboardRenderDTO.getDashboardGuid();
                String errorMsg = this.checkAuth(dashboardGuid);
                if (StringUtils.isNotEmpty((String)errorMsg)) {
                    return R.error((int)401, (String)errorMsg).jsonString();
                }
                DashboardItem dashboardItem = this.dashboardManager.getDashboardItem(dashboardGuid);
                if (dashboardItem == null) {
                    return R.error((int)404, (String)"\u4eea\u8868\u76d8\u5df2\u88ab\u5220\u9664").jsonString();
                }
                dashboardModel = this.dashboardManager.getDashboardModel(dashboardGuid, false);
                result.put("dashboardItem", (Object)dashboardItem.save());
            }
            if (dashboardModel == null) {
                return R.error((int)404, (String)"\u4eea\u8868\u76d8\u5df2\u88ab\u5220\u9664").jsonString();
            }
            boolean preview = dashboardRenderDTO.isPreview();
            String sessionId = dashboardRenderDTO.getSessionId();
            JSONObject msg = StringUtils.isNotEmpty((String)dashboardRenderDTO.getLinkMsg()) ? new JSONObject(dashboardRenderDTO.getLinkMsg()) : new JSONObject();
            if (StringUtils.isNotEmpty((String)dashboardRenderDTO.getWidgetIds())) {
                String[] widgetIds = dashboardRenderDTO.getWidgetIds().split(";");
                HashSet<String> widgetIdSet = new HashSet<String>(Arrays.asList(widgetIds));
                dashboardModel.getWidgets().removeIf(widget -> !widgetIdSet.contains(widget.getId()));
            }
            Theme currentTheme = this.dashboardThemeManager.getThemeById(dashboardModel.getConfig() == null ? null : dashboardModel.getConfig().getThemeId(), true);
            result.put("currentTheme", (Object)currentTheme.generateKeyValue());
            if (!preview) {
                JSONArray themeArray = new JSONArray();
                List themes = this.dashboardThemeManager.getAllThemes();
                if (currentTheme.getItem().isDeprecated()) {
                    themes.stream().filter(c -> !c.isDeprecated() || c.getGuid().equals(currentTheme.getItem().getGuid())).forEach(c -> themeArray.put((Object)c.toJSON()));
                } else {
                    themes.stream().filter(c -> !c.isDeprecated()).forEach(c -> themeArray.put((Object)c.toJSON()));
                }
                result.put("allThemes", (Object)themeArray);
            }
            JSONObject modelJson = dashboardModel.toJSON(false);
            modelJson.put("__isSysAdmin", this.dashboardTreeNodeManager.isSysAdmin(NpContextHolder.getContext().getUserId()));
            modelJson.put("__linkSendExt", StringUtils.isNotEmpty((String)this.sendExt) && this.sendExt.equals("true"));
            modelJson.put("__screenShotServerAddress", (Object)this.screenshotSdk.getScreenShotServerAddress());
            result.put("dashboardModel", (Object)modelJson);
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, true);
            LinkMsg linkMsg = this.parseLinkMsg(msg);
            DashboardRenderContext context = new DashboardRenderContext(currentTheme.getItem().getGuid(), NpContextHolder.getContext().getUserId(), NpContextHolder.getContext().getLocale().toLanguageTag(), sessionId);
            cacheProvider.init(modelJson.toString(), linkMsg, context);
            return R.ok((JSONObject)result).jsonString();
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u4eea\u8868\u76d8\u4fe1\u606f\u5931\u8d25", e);
            return R.error((int)500, (String)"\u83b7\u53d6\u4eea\u8868\u76d8\u4fe1\u606f\u5931\u8d25\u3002").jsonString();
        }
    }

    @PostMapping(value={"/widget-render"})
    public String widgetRender(@RequestBody WidgetRenderDTO widgetRenderDTO) {
        String widgetId = widgetRenderDTO.getWidgetId();
        try {
            String sessionId = widgetRenderDTO.getSessionId();
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
            if (cacheProvider == null) {
                return R.error((String)"\u7f13\u5b58\u8fc7\u671f\uff0c\u8bf7\u5237\u65b0\u9875\u9762").jsonString();
            }
            WidgetRenderContext widgetRenderContext = new WidgetRenderContext();
            widgetRenderContext.setRenderParam(widgetRenderDTO.isRenderParams());
            widgetRenderContext.setForceUpdateDataset(widgetRenderDTO.isForceUpdateDataset());
            if (StringUtils.isNotEmpty((String)widgetRenderDTO.getWidgetModel())) {
                if (widgetRenderDTO.isUpdateCache()) {
                    cacheProvider.removeWidget(widgetId);
                }
                cacheProvider.updateWidget(JqLib.decodePassword((String)widgetRenderDTO.getWidgetModel()));
            } else {
                widgetRenderContext.setDrill(widgetRenderDTO.isDrillMode() && !widgetRenderDTO.isUpdateCache());
            }
            JSONObject msg = StringUtils.isNotEmpty((String)widgetRenderDTO.getLinkMsg()) ? new JSONObject(widgetRenderDTO.getLinkMsg()) : new JSONObject();
            LinkMsg linkMsg = this.parseLinkMsg(msg);
            String result = cacheProvider.getQueryResult(widgetId, linkMsg, widgetRenderContext);
            if (StringUtils.isEmpty((String)result)) {
                logger.error("\u7ec4\u4ef6[" + widgetId + "]\u4e0d\u5b58\u5728\uff0c\u5f53\u524d\u6267\u884c\u8282\u70b9\uff1a" + DistributionManager.getInstance().self().getMachineName());
                return R.error((String)("\u7ec4\u4ef6[" + widgetId + "]\u4e0d\u5b58\u5728")).jsonString();
            }
            return R.ok((JSONObject)new JSONObject(result)).jsonString();
        }
        catch (Exception e) {
            logger.error("\u7ec4\u4ef6[" + widgetId + "]\u6e32\u67d3\u5931\u8d25", e);
            if (e instanceof ChartException || e instanceof TextBlockException) {
                return R.error((String)e.getMessage()).jsonString();
            }
            return R.error((String)"\u7ec4\u4ef6\u6e32\u67d3\u5931\u8d25").jsonString();
        }
    }

    @PostMapping(value={"/theme-change/{themeId}/{sId}"})
    public String themeChange(@PathVariable(value="themeId") String themeId, @PathVariable(value="sId") String sessionId) {
        try {
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
            if (cacheProvider == null) {
                return R.error((String)"\u7f13\u5b58\u8fc7\u671f\uff0c\u8bf7\u5237\u65b0\u9875\u9762").jsonString();
            }
            cacheProvider.updateTheme(themeId);
            Theme theme = this.dashboardThemeManager.getThemeById(themeId, true);
            return R.ok((JSONObject)theme.generateKeyValue()).jsonString();
        }
        catch (Exception e) {
            logger.error("\u5207\u6362\u4e3b\u9898\u5931\u8d25", e);
            return R.error((String)"\u5207\u6362\u4e3b\u9898\u5931\u8d25").jsonString();
        }
    }

    @PostMapping(value={"/process-drill"})
    public String processDrill(@RequestBody WidgetRenderDTO widgetRenderDTO) {
        String widgetId = widgetRenderDTO.getWidgetId();
        try {
            String sessionId = widgetRenderDTO.getSessionId();
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
            if (cacheProvider == null) {
                return R.error((String)"\u7f13\u5b58\u8fc7\u671f\uff0c\u8bf7\u5237\u65b0\u9875\u9762").jsonString();
            }
            if (StringUtils.isEmpty((String)widgetRenderDTO.getDrillInfo())) {
                return R.error((String)"\u4e0b\u94bb\u4fe1\u606f\u4e3a\u7a7a").jsonString();
            }
            DrillInfoParam drillInfoParam = this.parseDrillInfoParam(new JSONObject(widgetRenderDTO.getDrillInfo()));
            String result = cacheProvider.processDrill(widgetId, drillInfoParam);
            return R.ok((String)result).jsonString();
        }
        catch (Exception e) {
            logger.error("\u9884\u5904\u7406\u4e0b\u94bb\u4fe1\u606f\u51fa\u9519", e);
            return R.error((String)"\u9884\u5904\u7406\u4e0b\u94bb\u4fe1\u606f\u51fa\u9519").jsonString();
        }
    }

    @PostMapping(value={"/remove-widget-cache"})
    public String removeWidgetCache(@RequestBody WidgetRenderDTO widgetRenderDTO) {
        String widgetId = widgetRenderDTO.getWidgetId();
        try {
            if (StringUtils.isEmpty((String)widgetId)) {
                return R.ok().jsonString();
            }
            String sessionId = widgetRenderDTO.getSessionId();
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
            if (cacheProvider == null) {
                return R.ok().jsonString();
            }
            cacheProvider.removeWidget(widgetId);
            return R.ok().jsonString();
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7f13\u5b58\u5931\u8d25", e);
            return R.error((String)"\u5220\u9664\u7f13\u5b58\u5931\u8d25").jsonString();
        }
    }

    @GetMapping(value={"/remove-cache/{sessionId}"})
    public void removeCache(@PathVariable String sessionId) {
        try {
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
            if (cacheProvider != null) {
                cacheProvider.destory();
            }
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7f13\u5b58\u5931\u8d25", e);
        }
        try {
            DashboardRenderCacheManager.getInstance().removeDashboardRenderCache(sessionId);
        }
        catch (TaskException e) {
            logger.error("\u5220\u9664\u7f13\u5b58\u5931\u8d25", e);
        }
    }

    @PostMapping(value={"/exportTableData"})
    public void exportTableData(@RequestBody WidgetRenderDTO widgetRenderDTO, HttpServletResponse response) {
        try (ByteArrayOutputStream byteArrOutStream = new ByteArrayOutputStream();
             ServletOutputStream os = response.getOutputStream();){
            ICacheDashboardRenderProvider cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(widgetRenderDTO.getSessionId(), false);
            if (cacheProvider == null) {
                throw new ChartException("\u7f13\u5b58\u8fc7\u671f\uff0c\u8bf7\u5237\u65b0\u9875\u9762");
            }
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
            JSONObject msg = StringUtils.isNotEmpty((String)widgetRenderDTO.getLinkMsg()) ? new JSONObject(widgetRenderDTO.getLinkMsg()) : new JSONObject();
            LinkMsg linkMsg = this.parseLinkMsg(msg);
            GridData gridData = cacheProvider.getGridData(widgetRenderDTO.getWidgetId(), linkMsg);
            if (gridData == null) {
                throw new ChartException("\u751f\u6210\u8868\u683c\u5931\u8d25");
            }
            SimpleExportor exporter = new SimpleExportor(gridData, true);
            exporter.export((OutputStream)byteArrOutStream);
            byteArrOutStream.writeTo((OutputStream)os);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u56fe\u8868\u6570\u636e\u51fa\u9519\uff1a" + widgetRenderDTO.getWidgetId(), e);
        }
    }

    private String checkAuth(String dashboardGuid) throws DashboardException {
        try {
            return this.dashboardTreeNodeManager.canAccess(dashboardGuid, NpContextHolder.getContext().getUserId());
        }
        catch (DashboardAdapterException e) {
            throw new DashboardException("\u83b7\u53d6\u4eea\u8868\u76d8\u6743\u9650\u4fe1\u606f\u5931\u8d25", (Throwable)e);
        }
    }

    private LinkMsg parseLinkMsg(JSONObject msg) {
        HashMap linkMsgMap = new HashMap();
        DashboardChartUtils.buildLinkMsg(linkMsgMap, (JSONObject)msg);
        LinkMsg linkMsg = new LinkMsg();
        linkMsg.setMessages(linkMsgMap);
        return linkMsg;
    }

    private DrillInfoParam parseDrillInfoParam(JSONObject drillInfoJson) {
        DrillInfoParam drillInfoParam = new DrillInfoParam();
        drillInfoParam.setDrillUp(drillInfoJson.optBoolean("isDrillUp"));
        drillInfoParam.setDimValue(drillInfoJson.optString("dimValue", ""));
        drillInfoParam.setMapType(drillInfoJson.optString("mapType", ""));
        return drillInfoParam;
    }
}

