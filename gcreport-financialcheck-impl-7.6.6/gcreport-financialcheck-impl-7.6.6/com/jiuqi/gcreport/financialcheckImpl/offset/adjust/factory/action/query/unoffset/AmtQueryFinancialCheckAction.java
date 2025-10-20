/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.factory.action.query.unoffset;

import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.GcOffsetInputAdjustEntryService;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmtQueryFinancialCheckAction
implements GcOffSetItemAction {
    @Autowired
    private FinancialCheckOffsetService financialCheckOffsetService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffsetInputAdjustEntryService gcOffsetInputAdjustEntryService;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        Pagination<Map<String, Object>> pagination = this.financialCheckOffsetService.listFinancialCheckOffset(queryParamsVO, false);
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetInputAdjustEntryService.setTitles(pagination, queryParamsVO, systemId);
        return pagination;
    }
}

