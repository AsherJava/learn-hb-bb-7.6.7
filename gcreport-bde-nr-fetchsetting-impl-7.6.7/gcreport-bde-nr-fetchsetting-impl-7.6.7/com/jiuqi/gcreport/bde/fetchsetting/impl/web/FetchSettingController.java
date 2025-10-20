/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.FetchSettingApi
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.FetchSettingApi;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingLogHelperUtil;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchSettingController
implements FetchSettingApi {
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;

    public BusinessResponseEntity<FetchFixedAndFloatSettingVO> listFetchFixedAndFloatSetting(FetchSettingCond fetchSettingCond) {
        FetchFixedAndFloatSettingVO fetchFixedAndFloatSetting = new FetchFixedAndFloatSettingVO();
        fetchFixedAndFloatSetting.setFetchSettingList(this.fetchSettingService.listFetchSettingByFormId(fetchSettingCond));
        fetchFixedAndFloatSetting.setFloatRegionConfigList(this.fetchFloatSettingService.listFetchFloatSettingByFormId(fetchSettingCond));
        return BusinessResponseEntity.ok((Object)fetchFixedAndFloatSetting);
    }

    public BusinessResponseEntity<Object> fetchSettingPublish(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            this.fetchSettingService.fetchSettingPublishByFormId(fetchSettingCond);
        } else {
            this.fetchSettingService.fetchSettingPublishByFetchSchemeId(fetchSettingCond);
        }
        FetchSettingLogHelperUtil.logFetchSettingPublish(fetchSettingCond);
        return BusinessResponseEntity.ok((Object)"\u53d1\u5e03\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<List<FixedFieldDefineSettingDTO>> listDataLinkFixedSettingRowRecords(FetchSettingCond fetchSettingCond) throws Exception {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getRegionId(), "\u533a\u57dfkey\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getDataLinkId(), "\u94fe\u63a5key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok(this.fetchSettingService.listDataLinkFixedSettingRowRecords(fetchSettingCond));
    }

    public BusinessResponseEntity<Map<String, Double>> getBDEQueryDataMapping(BDEQueryDataCond bdeQueryDataCond) {
        return BusinessResponseEntity.ok(this.fetchSettingService.getBDEQueryDataMapping(bdeQueryDataCond));
    }

    public BusinessResponseEntity<List<Map<String, Object>>> getBDEFloatPenetrateTableData(BDEQueryDataCond bdeQueryDataCond) {
        return BusinessResponseEntity.ok(this.fetchSettingService.getBDEFloatPenetrateTableData(bdeQueryDataCond));
    }

    @NRContextBuild
    public BusinessResponseEntity<GcFetchRequestDTO> getBDEPenetrateParam(BDEQueryDataCond bdeQueryDataCond) {
        return BusinessResponseEntity.ok((Object)this.fetchSettingService.getBDEPenetrateParam(bdeQueryDataCond));
    }
}

