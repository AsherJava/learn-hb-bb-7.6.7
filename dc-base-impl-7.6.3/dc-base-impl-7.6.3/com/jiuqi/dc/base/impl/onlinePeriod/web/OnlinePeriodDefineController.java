/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.onlinePeriod.OnlinePeriodDefineClient
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.onlinePeriod.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.onlinePeriod.OnlinePeriodDefineClient;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodDefineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlinePeriodDefineController
implements OnlinePeriodDefineClient {
    @Autowired
    private OnlinePeriodDefineService onlinePeriodDefineService;

    public BusinessResponseEntity<List<OnlinePeriodDefineVO>> getAllTableData(@RequestBody OnlinePeriodCondiVO onlinePeriodCondiVO) {
        return BusinessResponseEntity.ok(this.onlinePeriodDefineService.getAllTableData(onlinePeriodCondiVO));
    }

    public BusinessResponseEntity<Object> insertPeriod(@RequestBody OnlinePeriodDefineVO onlinePeriodDefineVO) {
        this.onlinePeriodDefineService.insertPeriod(onlinePeriodDefineVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> updatePeriod(@RequestBody OnlinePeriodDefineVO onlinePeriodDefineVO) {
        this.onlinePeriodDefineService.updatePeriod(onlinePeriodDefineVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> deletePeriod(String id) {
        this.onlinePeriodDefineService.deletePeriod(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<OnlinePeriodDefineVO> getPeriodDataById(@RequestBody String id) {
        return BusinessResponseEntity.ok((Object)this.onlinePeriodDefineService.getPeriodDataById(id));
    }
}

