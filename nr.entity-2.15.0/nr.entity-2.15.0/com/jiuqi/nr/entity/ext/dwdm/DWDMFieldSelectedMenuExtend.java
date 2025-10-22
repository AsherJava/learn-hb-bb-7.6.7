/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend
 */
package com.jiuqi.nr.entity.ext.dwdm;

import com.jiuqi.nr.entity.ext.dwdm.AbstractDWDMMenu;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend;
import java.util.HashMap;

public class DWDMFieldSelectedMenuExtend
extends AbstractDWDMMenu
implements OrgCategoryMgrUiMenuExtend {
    public String getName() {
        return "DWDMFieldSelected";
    }

    public String getTitle() {
        return "\u542f\u7528IDC\u6821\u9a8c";
    }

    public String getParam() {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("prodLine", "@nr");
        config.put("appName", "dwdmSelectedPlugin");
        config.put("appTitle", "\u542f\u7528IDC\u6821\u9a8c");
        config.put("openWay", "MODELWINDOW");
        HashMap appConfig = new HashMap(1);
        config.put("appConfig", JSONUtil.toJSONString(appConfig));
        HashMap<String, String> winConfig = new HashMap<String, String>();
        winConfig.put("width", "450px");
        winConfig.put("height", "150px");
        config.put("modelWinStyle", JSONUtil.toJSONString(winConfig));
        return JSONUtil.toJSONString(config);
    }

    public int getOrderNum() {
        return 0;
    }
}

