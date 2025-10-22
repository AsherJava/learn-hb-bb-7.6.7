/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.link.provider.ILinkResourceProvider
 *  com.jiuqi.nvwa.link.provider.ResourceAppConfig
 *  com.jiuqi.nvwa.link.provider.ResourceAppInfo
 *  com.jiuqi.nvwa.link.provider.ResourceNode
 *  com.jiuqi.nvwa.link.provider.SearchItem
 *  org.json.JSONObject
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceAppInfo;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.SearchItem;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class EFDCResourceProvider
implements ILinkResourceProvider {
    private static final String TYPE = "com.jiuqi.nvwa.efdcpierce";
    private static final String TITLE = "efdc\u7a7f\u900f";

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return TITLE;
    }

    public double getOrder() {
        return 0.0;
    }

    public String getIcon() {
        return null;
    }

    public boolean isVisible() {
        return false;
    }

    public ResourceAppInfo getAppInfo(String resourceId, String extData) {
        ResourceAppInfo resourceAppInfo = new ResourceAppInfo("@nr", "efdcPierceApp");
        resourceAppInfo.setType("efdcPierce");
        return resourceAppInfo;
    }

    public List<ResourceNode> getChildNodes(String resourceId, String extData) {
        return null;
    }

    public List<String> getPaths(String resourceId, String extData) {
        return null;
    }

    public ResourceAppConfig buildAppConfig(String resourceId, String extData, String linkMsg) {
        ResourceAppConfig resourceAppConfig = new ResourceAppConfig();
        JSONObject configJson = new JSONObject();
        configJson.put("EFDCPierceParam", (Object)linkMsg);
        resourceAppConfig.setConfig(configJson.toString());
        return resourceAppConfig;
    }

    public List<SearchItem> search(String key) {
        return null;
    }

    public ResourceNode getResource(String resourceId, String extData) {
        return new ResourceNode();
    }
}

