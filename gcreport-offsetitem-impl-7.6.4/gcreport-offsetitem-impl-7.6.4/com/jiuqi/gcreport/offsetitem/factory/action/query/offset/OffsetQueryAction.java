/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.factory.action.query.offset;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OffsetQueryAction
implements GcOffSetItemAction {
    @Autowired
    private GcOffSetAppOffsetService gcOffSetItemAdjustService;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        queryParamsVO.setFilterDisableItem(false);
        return this.gcOffSetItemAdjustService.listOffsetEntrys(queryParamsVO);
    }
}

