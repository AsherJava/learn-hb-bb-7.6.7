/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.FloatDimensionSettingApi
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.FloatDimensionSettingApi;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FloatDimensionSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloatDimensionSettingController
implements FloatDimensionSettingApi {
    @Autowired
    private FloatDimensionSettingService floatDimensionSettingService;

    public BusinessResponseEntity<List<DimensionSetting>> getDimensionSetByRegionId(String regionId) {
        return BusinessResponseEntity.ok(this.floatDimensionSettingService.getDimensionSetByRegionId(regionId));
    }

    public BusinessResponseEntity<Object> saveFloatDimensionSetting(FloatDimensionSettingDTO floatDimensionSettingDTO) {
        this.floatDimensionSettingService.saveFloatDimensionSetting(floatDimensionSettingDTO);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }
}

