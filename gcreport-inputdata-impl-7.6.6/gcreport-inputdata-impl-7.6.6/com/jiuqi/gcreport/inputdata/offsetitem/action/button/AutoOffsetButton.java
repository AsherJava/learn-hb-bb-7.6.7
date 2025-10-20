/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.button;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
public class AutoOffsetButton
implements GcOffsetItemButton {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;

    public String code() {
        return "autoOffset";
    }

    public String title() {
        return "\u81ea\u52a8\u62b5\u9500";
    }

    public String icon() {
        return super.icon();
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)((String)gcOffsetExecutorVO.getParamObject()), QueryParamsVO.class);
        if (queryParamsVO != null) {
            GcOrgTypeUtils.setContextEntityId((String)queryParamsVO.getOrgType());
        }
        this.gcOffsetAppInputDataService.calc(queryParamsVO);
        return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.dataentryquery.inputdataservice.calcsuccessmsg");
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return true;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return true;
    }
}

