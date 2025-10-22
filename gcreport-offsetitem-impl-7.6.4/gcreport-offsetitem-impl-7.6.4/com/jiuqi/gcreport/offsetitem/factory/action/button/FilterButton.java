/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.factory.action.button;

import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=12)
public class FilterButton
implements GcOffsetItemButton {
    public String code() {
        return "filter";
    }

    public String title() {
        return "\u7b5b\u9009";
    }

    public String icon() {
        return super.icon();
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        return null;
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return true;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return true;
    }
}

