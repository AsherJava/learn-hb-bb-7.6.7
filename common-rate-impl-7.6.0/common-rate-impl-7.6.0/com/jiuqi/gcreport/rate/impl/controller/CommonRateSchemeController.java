/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.rate.client.api.CommonRateSchemeClient
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.rate.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.rate.client.api.CommonRateSchemeClient;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonRateSchemeController
implements CommonRateSchemeClient {
    @Autowired
    private CommonRateSchemeService commonRateSchemeService;

    public BusinessResponseEntity<CommonRateSchemeVO> queryRateScheme(String code) {
        return BusinessResponseEntity.ok((Object)this.commonRateSchemeService.queryRateScheme(code));
    }

    public BusinessResponseEntity<List<CommonRateSchemeVO>> listAllRateScheme() {
        return BusinessResponseEntity.ok(this.commonRateSchemeService.listAllRateScheme());
    }

    public BusinessResponseEntity<Boolean> saveRateScheme(CommonRateSchemeVO rateSchemeVO) {
        return BusinessResponseEntity.ok((Object)this.commonRateSchemeService.saveRateScheme(rateSchemeVO));
    }

    public BusinessResponseEntity<Boolean> deleteRateScheme(String code) {
        return BusinessResponseEntity.ok((Object)this.commonRateSchemeService.deleteRateScheme(code));
    }
}

