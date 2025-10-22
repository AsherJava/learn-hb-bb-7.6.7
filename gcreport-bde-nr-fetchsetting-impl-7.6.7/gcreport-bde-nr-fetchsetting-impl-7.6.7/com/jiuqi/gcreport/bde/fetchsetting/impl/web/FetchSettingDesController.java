/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.FetchSettingDesApi
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.web;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.FetchSettingDesApi;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesFormDataService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesStopService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingLogHelperUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchSettingDesController
implements FetchSettingDesApi {
    @Autowired
    private FetchSettingDesService fetchSettingDesService;
    @Autowired
    private FetchSettingDesFormDataService fetchSettingDesFormDataService;
    @Autowired
    private FetchFloatSettingDesService fetchFloatSettingDesService;
    @Autowired
    private FetchSettingDesBaseDataService fetchSettingDesBaseDataService;
    @Autowired
    private FetchSettingDesStopService fetchSettingDesStopService;

    public BusinessResponseEntity<String> queryFormData(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok((Object)this.fetchSettingDesFormDataService.queryFormData(fetchSettingCond));
    }

    public BusinessResponseEntity<String> queryFormTitle(String formId) {
        return BusinessResponseEntity.ok((Object)this.fetchSettingDesService.queryFormTitle(formId));
    }

    public BusinessResponseEntity<List<Map<String, Object>>> listFixedSettingDesByForm(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok(this.fetchSettingDesService.listFixedSettingDesByForm(fetchSettingCond));
    }

    public BusinessResponseEntity<EncryptRequestDTO<FixedFieldDefineSettingDTO>> listDataLinkFixedSettingDesRowRecords(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getRegionId(), "\u533a\u57dfkey\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getDataLinkId(), "\u94fe\u63a5key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        FixedFieldDefineSettingDTO fixedFieldDefineSettingDTO = this.fetchSettingDesService.listDataLinkFixedSettingDesRowRecords(fetchSettingCond);
        if (Objects.isNull(fixedFieldDefineSettingDTO)) {
            return BusinessResponseEntity.ok((Object)new EncryptRequestDTO());
        }
        return BusinessResponseEntity.ok((Object)new EncryptRequestDTO((Object)fixedFieldDefineSettingDTO));
    }

    public BusinessResponseEntity<EncryptRequestDTO<List<FixedFieldDefineSettingDTO>>> listDataLinkFixedSettingDes(FetchSettingListLinkCond fetchSettingListLinkCond) {
        Objects.requireNonNull(fetchSettingListLinkCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingListLinkCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingListLinkCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingListLinkCond.getRegionId(), "\u533a\u57dfkey\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingListLinkCond.getDataLinkIdList(), "\u94fe\u63a5key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok((Object)new EncryptRequestDTO(this.fetchSettingDesService.listDataLinkFixedSettingDes(fetchSettingListLinkCond)));
    }

    public BusinessResponseEntity<List<FixedFieldDefineSettingVO>> listFixedFieldDefineSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        return BusinessResponseEntity.ok(this.fetchSettingDesService.listFixedFieldDefineSettingDesByFormId(fetchSettingCond));
    }

    public BusinessResponseEntity<FetchFixedAndFloatSettingVO> queryFetchFixedAndFloatSettingVOByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        FetchFixedAndFloatSettingVO fixedAndFloatSettingVO = new FetchFixedAndFloatSettingVO();
        List<FetchSettingVO> fetchSettingVOS = this.fetchSettingDesService.listFetchSettingDesByFetchSchemeId(fetchSettingCond);
        List<FloatRegionConfigVO> fetchFloatSettingVOS = this.fetchFloatSettingDesService.listFetchFloatSettingDesByFetchScheme(fetchSettingCond);
        fixedAndFloatSettingVO.setFetchSettingList(fetchSettingVOS);
        fixedAndFloatSettingVO.setFloatRegionConfigList(fetchFloatSettingVOS);
        return BusinessResponseEntity.ok((Object)fixedAndFloatSettingVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> saveFetchSettingDes(EncryptRequestDTO<FetchSettingSaveDataVO> encryptRequestDTO) {
        String floatMassageInfo;
        FetchSettingSaveDataVO fetchSettingSaveData = (FetchSettingSaveDataVO)encryptRequestDTO.parseParam(FetchSettingSaveDataVO.class);
        Objects.requireNonNull(fetchSettingSaveData.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingSaveData.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingSaveData.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        StringBuilder massageInfo = new StringBuilder();
        FetchSettingLogHelperUtil.logFetchSettingSave(fetchSettingSaveData);
        String fixedMassageInfo = this.fetchSettingDesService.saveFetchFixedSettingDataHandle(fetchSettingSaveData);
        if (!StringUtils.isEmpty((String)fixedMassageInfo)) {
            massageInfo.append("\u56fa\u5b9a\u8868\u4fdd\u5b58\uff1a").append(fixedMassageInfo);
        }
        if (!StringUtils.isEmpty((String)(floatMassageInfo = this.fetchFloatSettingDesService.saveFetchFloatSettingDataHandle(fetchSettingSaveData)))) {
            massageInfo.append("\u6d6e\u52a8\u533a\u57df\u4fdd\u5b58\uff1a").append(floatMassageInfo);
        }
        if (massageInfo.length() > 0) {
            throw new BdeRuntimeException(massageInfo.toString());
        }
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<Object> listDataLinkByRegionId(String regionId) {
        return BusinessResponseEntity.ok(this.fetchSettingDesService.listDataLinkByRegionId(regionId));
    }

    public BusinessResponseEntity<String> cleanFloatSetting(FetchSettingCond fetchSettingCond) {
        FetchSettingLogHelperUtil.logCleanFloatSetting(fetchSettingCond);
        this.fetchFloatSettingDesService.cleanFloatSetting(fetchSettingCond);
        return BusinessResponseEntity.ok((Object)"\u6e05\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<Map<String, List<BaseDataVO>>> queryBaseData(Map<String, BaseDataParam> dimBaseDataParamMap) {
        return BusinessResponseEntity.ok((Object)this.fetchSettingDesBaseDataService.getBaseDataByTableAndCode(dimBaseDataParamMap));
    }

    public BusinessResponseEntity<Object> disableFetchSetting(FetchSettingDesStopDTO disableDTO) {
        this.fetchSettingDesStopService.disableFetchSetting(disableDTO);
        return BusinessResponseEntity.ok((Object)"\u505c\u7528\u6210\u529f");
    }

    public BusinessResponseEntity<Object> enableFetchSetting(FetchSettingDesStopDTO enableDTO) {
        this.fetchSettingDesStopService.enableFetchSetting(enableDTO);
        return BusinessResponseEntity.ok((Object)"\u542f\u7528\u6210\u529f");
    }
}

