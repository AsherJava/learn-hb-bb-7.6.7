/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FloatDimensionSettingApi {
    @GetMapping(value={"/api/gcreport/v1/fetch/getDimensionSetByRegionId/{regionId}"})
    public BusinessResponseEntity<List<DimensionSetting>> getDimensionSetByRegionId(@PathVariable(value="regionId") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/saveFloatDimensionSetting"})
    public BusinessResponseEntity<Object> saveFloatDimensionSetting(@RequestBody FloatDimensionSettingDTO var1);
}

