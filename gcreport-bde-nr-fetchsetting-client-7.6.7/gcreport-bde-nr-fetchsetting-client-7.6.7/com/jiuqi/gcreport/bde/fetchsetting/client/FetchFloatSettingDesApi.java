/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FetchFloatSettingDesApi {
    @PostMapping(value={"/api/gcreport/v1/fetch/getFloatRegionSettingDes"})
    public BusinessResponseEntity<FloatRegionConfigVO> getFloatRegionSettingDes(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/getFloatRegionSettingDesByForm"})
    public BusinessResponseEntity<EncryptRequestDTO<Map<String, FloatRegionConfigVO>>> getFloatRegionSettingDesByForm(@RequestBody FetchSettingCond var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/listOtherEntity/{formSchemeId}"})
    public BusinessResponseEntity<List<SelectOptionVO>> listOtherEntity(@PathVariable(value="formSchemeId") String var1);
}

