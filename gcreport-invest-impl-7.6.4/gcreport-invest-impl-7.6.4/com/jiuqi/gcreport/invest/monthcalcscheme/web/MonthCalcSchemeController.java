/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.monthcalcscheme.api.MonthCalcSchemeClient
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO
 *  com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.monthcalcscheme.service.MonthCalcSchemeService;
import com.jiuqi.gcreport.monthcalcscheme.api.MonthCalcSchemeClient;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO;
import com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonthCalcSchemeController
implements MonthCalcSchemeClient {
    @Autowired
    private MonthCalcSchemeService monthCalcSchemeService;

    public BusinessResponseEntity<List<MonthCalcSchemeVO>> listMonthCalcSchemes() {
        return BusinessResponseEntity.ok(this.monthCalcSchemeService.listMonthCalcSchemes());
    }

    public BusinessResponseEntity<Object> addMonthCalcScheme(MonthCalcSchemeVO monthCalcSchemeVO) {
        this.monthCalcSchemeService.saveMonthCalcScheme(monthCalcSchemeVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<MonthCalcSchemeVO> getMonthCalcScheme(String monthCalcSchemeId) {
        return BusinessResponseEntity.ok((Object)this.monthCalcSchemeService.getMonthCalcScheme(monthCalcSchemeId));
    }

    public BusinessResponseEntity<Object> deleteMonthCalcScheme(String schemeId) {
        this.monthCalcSchemeService.deleteMonthCalcScheme(schemeId);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Map<String, List<TaskVO>>> getTasksOfType() {
        return BusinessResponseEntity.ok(this.monthCalcSchemeService.getTasksOfType());
    }
}

