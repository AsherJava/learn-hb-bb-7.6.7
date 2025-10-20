/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.monthcalcscheme.api.MonthCalcZbMappingClient
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.invest.monthcalcscheme.service.MonthCalcZbMappingService;
import com.jiuqi.gcreport.monthcalcscheme.api.MonthCalcZbMappingClient;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonthCalcZbMappingController
implements MonthCalcZbMappingClient {
    @Autowired
    private MonthCalcZbMappingService monthCalcZbMappingService;

    public BusinessResponseEntity<PageInfo<MonthCalcZbMappingVO>> listZbMappings(MonthCalcZbMappingCond cond) {
        return BusinessResponseEntity.ok(this.monthCalcZbMappingService.listZbMappings(cond));
    }

    public BusinessResponseEntity<String> saveZbMappings(String monthCalcSchemeId, List<MonthCalcZbMappingVO> monthCalcZbMappingVOs) {
        this.monthCalcZbMappingService.saveZbMappings(monthCalcSchemeId, monthCalcZbMappingVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<MonthCalcZbMappingVO>> deleteZbMappings(List<String> monthCalcZbMappingIds) {
        this.monthCalcZbMappingService.deleteZbMappings(monthCalcZbMappingIds);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> exchangeSort(String opNodeId, int step) {
        this.monthCalcZbMappingService.exchangeSort(opNodeId, step);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }
}

