/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffSetItemAdjustExecutorImpl
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.button;

import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffSetItemAdjustExecutorImpl;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=15)
public class RefreshButton
implements GcOffsetItemButton {
    @Autowired
    private GcOffSetItemAdjustExecutorImpl gcOffSetItemAdjustExecutor;

    public String code() {
        return "refresh";
    }

    public String title() {
        return "\u5237\u65b0";
    }

    public String icon() {
        return super.icon();
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        gcOffsetExecutorVO.setActionCode("query");
        return this.gcOffSetItemAdjustExecutor.getActionForCode(gcOffsetExecutorVO).execute(gcOffsetExecutorVO);
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return true;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return true;
    }
}

