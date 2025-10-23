/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp
 *  com.jiuqi.nvwa.resourceview.plugin.GlobalCondition
 */
package com.jiuqi.nr.multcheck2.view;

import com.jiuqi.nr.multcheck2.common.GlobalType;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp;
import com.jiuqi.nvwa.resourceview.plugin.GlobalCondition;
import org.springframework.stereotype.Component;

@Component
public class MultcheckApp
implements IResourceCategoryApp {
    public String getId() {
        return "com.jiuqi.nr.multcheck2";
    }

    public String getTitle() {
        return "\u5ba1\u6838\u65b9\u6848";
    }

    public String getIcon() {
        return "icon nvwa-iconfont icon16_DH_A_NW_dengluyepeizhi";
    }

    public double getOrder() {
        return 0.0;
    }

    public GlobalCondition getGlobalCondition() {
        GlobalCondition global = new GlobalCondition("@nr", "nr-multcheck2-plugin", "nr-multcheck2-plugin", "Global");
        global.setDefaultValue(GlobalType.EXIST.value());
        return global;
    }
}

