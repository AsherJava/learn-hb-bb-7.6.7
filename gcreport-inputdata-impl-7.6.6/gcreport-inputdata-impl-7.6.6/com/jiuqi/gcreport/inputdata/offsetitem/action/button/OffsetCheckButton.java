/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.button;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(value=2)
public class OffsetCheckButton
implements GcOffsetItemButton {
    @Autowired
    private InputDataService inputDataService;

    public String code() {
        return "offsetCheck";
    }

    public String title() {
        return "\u62b5\u9500\u68c0\u67e5";
    }

    public String icon() {
        return super.icon();
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        Map params = (Map)JsonUtils.readValue((String)((String)gcOffsetExecutorVO.getParamObject()), Map.class);
        List inputItemIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(params.get("inputItemIds")), (TypeReference)new TypeReference<List<String>>(){});
        boolean realTimeOffset = (Boolean)JsonUtils.readValue((String)JsonUtils.writeValueAsString(params.get("realTimeOffset")), Boolean.class);
        String orgType = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(params.get("orgType")), String.class);
        String taskId = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString(params.get("taskId")), String.class);
        if (StringUtils.hasText(orgType)) {
            GcOrgTypeUtils.setContextEntityId((String)orgType);
        }
        return this.inputDataService.canOffset(inputItemIds, realTimeOffset, orgType, taskId);
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return true;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return true;
    }
}

