/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

import com.jiuqi.nvwa.link.provider.CheckResult;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceAppInfo;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.ResourceParam;
import com.jiuqi.nvwa.link.provider.SearchItem;
import com.jiuqi.nvwa.link.web.vo.AppInfoVO;
import java.util.Collections;
import java.util.List;

public interface ILinkResourceProvider {
    default public String getGroup() {
        return null;
    }

    public String getType();

    public String getTitle();

    public double getOrder();

    public String getIcon();

    default public boolean linkResource() {
        return false;
    }

    default public boolean isVisible() {
        return true;
    }

    default public boolean checkHidden() {
        return false;
    }

    public ResourceAppInfo getAppInfo(String var1, String var2);

    default public AppInfoVO getAppInfoVO(String resourceId, String extData) {
        return new AppInfoVO(this.getAppInfo(resourceId, extData));
    }

    public List<ResourceNode> getChildNodes(String var1, String var2);

    public List<String> getPaths(String var1, String var2);

    public ResourceAppConfig buildAppConfig(String var1, String var2, String var3);

    public List<SearchItem> search(String var1);

    public ResourceNode getResource(String var1, String var2);

    default public CheckResult checkResource(String resourceId, String extData, String linkMsg) {
        return null;
    }

    default public List<ResourceParam> getParamsByResource(ResourceNode resource) {
        return Collections.emptyList();
    }
}

