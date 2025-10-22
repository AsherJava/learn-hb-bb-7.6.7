/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.investcarryover.api.InvestBillCarryOverSettingClient
 *  com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.invest.investbillcarryover.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverSettingService;
import com.jiuqi.gcreport.investcarryover.api.InvestBillCarryOverSettingClient;
import com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestBillCarryOverSettingController
implements InvestBillCarryOverSettingClient {
    @Autowired
    private InvestBillCarryOverSettingService carryOverSettingService;

    public BusinessResponseEntity<List<InvestBillCarryOverSettingVO>> listSettings(String carryOverSchemeId) {
        return BusinessResponseEntity.ok(this.carryOverSettingService.listSettings(carryOverSchemeId));
    }

    public BusinessResponseEntity<String> saveSetting(InvestBillCarryOverSettingVO carryOverSettingVO) {
        this.carryOverSettingService.saveSetting(carryOverSettingVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> updateSetting(InvestBillCarryOverSettingVO investBillCarryOverSettingVO) {
        this.carryOverSettingService.updateSetting(investBillCarryOverSettingVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> deleteSetting(String id) {
        this.carryOverSettingService.deleteSetting(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Map<String, Object>> listCarryOverColums() {
        return BusinessResponseEntity.ok(this.carryOverSettingService.listCarryOverColums());
    }

    public BusinessResponseEntity<Object> exchangeSort(String currId, String exchangeId) {
        this.carryOverSettingService.exchangeSort(currId, exchangeId);
        return BusinessResponseEntity.ok();
    }
}

