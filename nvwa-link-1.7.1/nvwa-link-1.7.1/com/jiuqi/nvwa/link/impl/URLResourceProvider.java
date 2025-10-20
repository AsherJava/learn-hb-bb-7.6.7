/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.framework.nros.bean.RouteParam
 *  com.jiuqi.nvwa.framework.nros.service.IRouteParamService
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.link.impl;

import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.framework.nros.bean.RouteParam;
import com.jiuqi.nvwa.framework.nros.service.IRouteParamService;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceAppInfo;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.SearchItem;
import com.jiuqi.nvwa.link.web.vo.AppInfoVO;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class URLResourceProvider
implements ILinkResourceProvider {
    private static Logger logger = LoggerFactory.getLogger(URLResourceProvider.class);
    private static final String URL_PARA_OSJUMP_TAG = "os-jump";
    private static final String PARA_APPCONFIG = "appConfig";
    private static final String[] URL_NVWA_TAG = new String[]{"/app?", "/go?", "/sso?"};
    private static final String URL_PARA_ROUTER_TAG = "router";
    @Autowired
    private IRouteService routeService;
    @Autowired
    private IRouteParamService routeParamService;
    private static final String PARA_LINKMSG = "linkMsgParam";
    public static final String URL_TYPE = "TYPE_URLRESOURCE";
    public static final String URL_TITLE = "\u81ea\u5b9a\u4e49URL";
    public static final String URL_ICON = "#icon16_DH_A_NW_xitonglianjie";

    @Override
    public String getType() {
        return URL_TYPE;
    }

    @Override
    public String getTitle() {
        return URL_TITLE;
    }

    @Override
    public double getOrder() {
        return 9999.0;
    }

    @Override
    public String getIcon() {
        return URL_ICON;
    }

    @Override
    public ResourceAppInfo getAppInfo(String resourceId, String extData) {
        return new ResourceAppInfo("@nvwa", "url-app");
    }

    @Override
    public AppInfoVO getAppInfoVO(String resourceId, String extData) {
        Route route = this.getRouteByUrl(extData);
        if (route != null) {
            int slashIndex = route.getAppName().indexOf(47);
            if (slashIndex != -1) {
                return new AppInfoVO(new ResourceAppInfo(route.getProdLine(), route.getAppName().substring(0, slashIndex), route.getAppName().substring(slashIndex + 1)), route.getId());
            }
            return new AppInfoVO(new ResourceAppInfo(route.getProdLine(), route.getAppName()), route.getId());
        }
        return new AppInfoVO(new ResourceAppInfo("@nvwa", "url-app"));
    }

    private Route getRouteByUrl(String url) {
        String router = this.getParamValue(url, URL_PARA_ROUTER_TAG);
        if (StringUtils.hasText(router)) {
            return this.routeService.selectById(router);
        }
        return null;
    }

    private JSONObject getOsJumpConfig(String url) {
        try {
            String os_jump_value = this.getParamValue(url, URL_PARA_OSJUMP_TAG);
            if (!StringUtils.hasText(os_jump_value)) {
                return null;
            }
            JSONObject object = this.parseFuncPara(os_jump_value);
            if (object == null) {
                return null;
            }
            return object.getJSONObject(PARA_APPCONFIG);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u94fe\u63a5URL\u7c7b\u578b\u62a5\u9519\uff1a" + url);
            logger.error("\u94fe\u63a5URL\u7c7b\u578b\u62a5\u9519\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private String getParamValue(String url, String paramName) {
        String value = null;
        String regEx = paramName + "=[\\w]+[^&]*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String funcParaStr = matcher.group();
            value = funcParaStr.replace(paramName + "=", "");
        }
        return value;
    }

    @Override
    public List<ResourceNode> getChildNodes(String resourceId, String extData) {
        return null;
    }

    @Override
    public List<String> getPaths(String resourceId, String extData) {
        return null;
    }

    @Override
    public ResourceAppConfig buildAppConfig(String resourceId, String extData, String linkMsg) {
        ResourceAppConfig appConfig = new ResourceAppConfig();
        Route route = this.getRouteByUrl(extData);
        if (route != null) {
            JSONObject appConfigObj = this.getOsJumpConfig(extData);
            if (appConfigObj == null) {
                RouteParam routeParam = this.routeParamService.searchById(route.getId());
                appConfigObj = routeParam != null && StringUtils.hasText(routeParam.getConfigJson()) ? new JSONObject(routeParam.getConfigJson()) : new JSONObject();
            }
            if (StringUtils.hasText(linkMsg)) {
                appConfigObj.put(PARA_LINKMSG, (Object)linkMsg);
            }
            appConfig.setConfig(appConfigObj.toString());
            return appConfig;
        }
        JSONObject config = new JSONObject();
        String url = this.buildURL(extData, linkMsg);
        if (StringUtils.hasText(url)) {
            config.put("urlTarget", (Object)url);
            appConfig.setConfig(config.toString());
        } else {
            appConfig.setMsg("URL\u94fe\u63a5\u683c\u5f0f\u9519\u8bef\uff1a" + extData);
        }
        return appConfig;
    }

    private String buildURL(String urlPath, String linkMsg) {
        String urlStr = urlPath;
        if (StringUtils.hasText(urlPath) && StringUtils.hasText(linkMsg)) {
            try {
                String regEx = "os-jump=[\\w]+[^&]*";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(urlPath);
                if (matcher.find()) {
                    String funcParaStr = matcher.group();
                    String funcParaVal = funcParaStr.replace("os-jump=", "");
                    JSONObject object = this.parseFuncPara(funcParaVal);
                    if (object == null) {
                        return null;
                    }
                    JSONObject appConfigObj = object.getJSONObject(PARA_APPCONFIG);
                    appConfigObj.put(PARA_LINKMSG, (Object)linkMsg);
                    String base64Params = Base64.getEncoder().encodeToString(object.toString().getBytes(StandardCharsets.UTF_8));
                    base64Params = "os-jump=" + URLEncoder.encode(base64Params, "UTF-8");
                    urlStr = urlPath.replace(funcParaStr, base64Params);
                } else if (this.isNvwaURL(urlPath)) {
                    JSONObject object = new JSONObject();
                    JSONObject appConfigObj = new JSONObject();
                    appConfigObj.put(PARA_LINKMSG, (Object)linkMsg);
                    object.put(PARA_APPCONFIG, (Object)appConfigObj);
                    String base64Params = Base64.getEncoder().encodeToString(object.toString().getBytes(StandardCharsets.UTF_8));
                    urlStr = urlStr + "&os-jump=" + URLEncoder.encode(base64Params, "UTF-8");
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error("\u94fe\u63a5URL\u7c7b\u578b\u62a5\u9519\uff1a" + urlStr);
                logger.error("\u94fe\u63a5URL\u7c7b\u578b\u62a5\u9519\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return urlStr;
    }

    private boolean isNvwaURL(String url) {
        for (int i = 0; i < URL_NVWA_TAG.length; ++i) {
            if (!url.contains(URL_NVWA_TAG[i])) continue;
            return true;
        }
        return false;
    }

    private JSONObject parseFuncPara(String funcParaVal) throws UnsupportedEncodingException {
        JSONObject object = null;
        funcParaVal = URLDecoder.decode(funcParaVal, "UTF-8");
        try {
            object = new JSONObject(new String(Base64.getDecoder().decode(funcParaVal), StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            logger.error("\u94fe\u63a5URL\u7c7b\u578bdecode1\u62a5\u9519\uff1a" + e.getMessage(), e);
            funcParaVal = URLDecoder.decode(funcParaVal, "UTF-8");
            try {
                object = new JSONObject(new String(Base64.getDecoder().decode(funcParaVal), StandardCharsets.UTF_8));
            }
            catch (Exception ee) {
                logger.error("\u94fe\u63a5URL\u7c7b\u578bdecode2\u62a5\u9519\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return object;
    }

    @Override
    public List<SearchItem> search(String key) {
        return null;
    }

    @Override
    public ResourceNode getResource(String resourceId, String extData) {
        return new ResourceNode();
    }
}

