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
@Order(value=16)
public class GcAdjustButton
implements GcOffsetItemButton {
    public String code() {
        return "gcAdjust";
    }

    public String title() {
        return "\u5408\u5e76\u8c03\u6574";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        return null;
    }

    public boolean isVisible(QueryParamsVO queryParamsVO) {
        return false;
    }

    public boolean isEnable(QueryParamsVO queryParamsVO) {
        return true;
    }
}

