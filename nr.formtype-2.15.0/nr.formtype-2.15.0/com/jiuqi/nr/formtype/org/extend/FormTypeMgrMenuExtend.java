/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTypeMgrMenuExtend
implements OrgCategoryMgrUiMenuExtend {
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    public String getName() {
        return "formtype-manager-window";
    }

    public String getTitle() {
        return "\u5173\u8054\u62a5\u8868\u7c7b\u578b";
    }

    public String getParam() {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("prodLine", "@nr");
        config.put("appName", "reportTypeExtends");
        config.put("appConfig", "{}");
        config.put("appTitle", "\u5173\u8054\u62a5\u8868\u7c7b\u578b");
        config.put("openWay", "MODELWINDOW");
        HashMap<String, String> style = new HashMap<String, String>();
        style.put("height", "260px");
        style.put("width", "480px");
        style.put("overflow", "hidden");
        config.put("modelWinStyle", JSONUtil.toJSONString(style));
        return JSONUtil.toJSONString(config);
    }

    public int getOrderNum() {
        return 0;
    }

    public Set<String> getApplyTo() {
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return null;
        }
        HashSet<String> orgCodeSet = new HashSet<String>();
        OrgCategoryDO param = new OrgCategoryDO();
        PageVO pageVO = this.orgCategoryClient.list(param);
        if (pageVO != null && pageVO.getTotal() > 0) {
            List list = pageVO.getRows();
            for (OrgCategoryDO orgCategoryDO : list) {
                if (this.iFormTypeApplyService.isMdOrg(orgCategoryDO.getName())) continue;
                orgCodeSet.add(orgCategoryDO.getName());
            }
        }
        return orgCodeSet;
    }
}

