/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.FetchFloatSettingDesApi
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.web;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.FetchFloatSettingDesApi;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchFloatSettingDesController
implements FetchFloatSettingDesApi {
    @Autowired
    private FetchFloatSettingDesService fetchFloatSettingDesService;

    public BusinessResponseEntity<FloatRegionConfigVO> getFloatRegionSettingDes(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u8bf7\u914d\u7f6e\u53d6\u6570\u65b9\u6848\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getRegionId(), "\u533a\u57dfkey\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok((Object)this.fetchFloatSettingDesService.getFetchFloatSettingDes(fetchSettingCond));
    }

    public BusinessResponseEntity<EncryptRequestDTO<Map<String, FloatRegionConfigVO>>> getFloatRegionSettingDesByForm(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u8bf7\u914d\u7f6e\u53d6\u6570\u65b9\u6848\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok((Object)new EncryptRequestDTO(this.fetchFloatSettingDesService.getFloatRegionSettingDesByForm(fetchSettingCond)));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> listOtherEntity(String formSchemeId) {
        return BusinessResponseEntity.ok(this.fetchFloatSettingDesService.listOtherEntity(formSchemeId));
    }
}

