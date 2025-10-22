/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend
 */
package com.jiuqi.nr.entity.ext.currency;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend;
import java.util.HashMap;

public class OrgCurrencyAttributeMenuExtend
implements OrgCategoryMgrUiMenuExtend {
    public String getName() {
        return "CURRENCY_ATTRIBUTE";
    }

    public String getTitle() {
        return "\u5173\u8054\u5e01\u79cd";
    }

    public String getParam() {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("prodLine", "@nr");
        config.put("appName", "currencyAttributePlugin");
        config.put("appTitle", "\u5173\u8054\u5e01\u79cd");
        config.put("openWay", "MODELWINDOW");
        HashMap appConfig = new HashMap(1);
        config.put("appConfig", JSONUtil.toJSONString(appConfig));
        HashMap<String, String> winConfig = new HashMap<String, String>();
        winConfig.put("width", "450px");
        winConfig.put("height", "180px");
        config.put("modelWinStyle", JSONUtil.toJSONString(winConfig));
        return JSONUtil.toJSONString(config);
    }

    public int getOrderNum() {
        return 1;
    }
}

