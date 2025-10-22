/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperSettingClient
 *  com.jiuqi.gcreport.investworkpaper.vo.Column
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investworkpaper.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperSettingService;
import com.jiuqi.gcreport.investworkpaper.api.InvestWorkPaperSettingClient;
import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestWorkPaperSettingController
implements InvestWorkPaperSettingClient {
    @Autowired
    private InvestWorkPaperSettingService investWorkPaperSettingService;

    public BusinessResponseEntity<String> save(InvestWorkPaperSettingVo investWorkPaperSettingDataVo) {
        this.investWorkPaperSettingService.save(investWorkPaperSettingDataVo);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<InvestWorkPaperSettingVo> getSettingData(String taskId, String systemId, String orgType) {
        return BusinessResponseEntity.ok((Object)this.investWorkPaperSettingService.getSettingData(taskId, systemId, orgType));
    }

    public BusinessResponseEntity<List<Column>> listInvestAmtFields() {
        return BusinessResponseEntity.ok(this.investWorkPaperSettingService.listInvestAmtFields());
    }
}

