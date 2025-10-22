/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.extend.OrgDataMgrUiMenuExtend
 */
package com.jiuqi.nr.entity.ext.dwdm;

import com.jiuqi.nr.entity.ext.dwdm.AbstractDWDMMenu;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.extend.OrgDataMgrUiMenuExtend;
import java.util.HashMap;

public class DWDMGeneratorMenuExtend
extends AbstractDWDMMenu
implements OrgDataMgrUiMenuExtend {
    public String getName() {
        return "DWDMGenerator";
    }

    public String getTitle() {
        return "\u5355\u4f4d\u4ee3\u7801\u751f\u6210\u5668";
    }

    public String getParam() {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("prodLine", "@nr");
        config.put("appName", "dwdmGeneratorPlugin");
        config.put("appTitle", "\u5355\u4f4d\u4ee3\u7801\u751f\u6210\u5668");
        config.put("openWay", "MODELWINDOW");
        HashMap appConfig = new HashMap(1);
        config.put("appConfig", JSONUtil.toJSONString(appConfig));
        HashMap<String, String> winConfig = new HashMap<String, String>(2);
        winConfig.put("width", "800px");
        winConfig.put("height", "150px");
        config.put("modelWinStyle", JSONUtil.toJSONString(winConfig));
        return JSONUtil.toJSONString(config);
    }

    public int getOrderNum() {
        return 0;
    }
}

