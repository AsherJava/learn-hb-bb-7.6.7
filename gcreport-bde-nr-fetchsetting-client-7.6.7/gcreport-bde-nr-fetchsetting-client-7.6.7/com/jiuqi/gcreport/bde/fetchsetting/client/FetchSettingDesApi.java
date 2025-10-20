/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FetchSettingDesApi {
    @PostMapping(value={"/api/gcreport/v1/fetch/queryFormData"})
    public BusinessResponseEntity<String> queryFormData(@RequestBody FetchSettingCond var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/queryFormTitle/{formId}"})
    public BusinessResponseEntity<String> queryFormTitle(@PathVariable(value="formId") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/listFixedSettingDesByForm"})
    public BusinessResponseEntity<List<Map<String, Object>>> listFixedSettingDesByForm(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/listDataLinkFixedSettingDesRowRecords"})
    public BusinessResponseEntity<EncryptRequestDTO<FixedFieldDefineSettingDTO>> listDataLinkFixedSettingDesRowRecords(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/listDataLinkFixedSettingDes"})
    public BusinessResponseEntity<EncryptRequestDTO<List<FixedFieldDefineSettingDTO>>> listDataLinkFixedSettingDes(@RequestBody FetchSettingListLinkCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/listFixedFieldDefineSettingDesByFormId"})
    public BusinessResponseEntity<List<FixedFieldDefineSettingVO>> listFixedFieldDefineSettingDesByFormId(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/queryFetchFixedAndFloatSettingVOByFetchSchemeId"})
    public BusinessResponseEntity<FetchFixedAndFloatSettingVO> queryFetchFixedAndFloatSettingVOByFetchSchemeId(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/saveFetchSettingDes"})
    public BusinessResponseEntity<Object> saveFetchSettingDes(@RequestBody EncryptRequestDTO<FetchSettingSaveDataVO> var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/listDataLinkByRegionId/{regionId}"})
    public BusinessResponseEntity<Object> listDataLinkByRegionId(@PathVariable(value="regionId") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/cleanFloatSetting"})
    public BusinessResponseEntity<String> cleanFloatSetting(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/queryBaseData"})
    public BusinessResponseEntity<Map<String, List<BaseDataVO>>> queryBaseData(@RequestBody Map<String, BaseDataParam> var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/disableFetchSetting"})
    public BusinessResponseEntity<Object> disableFetchSetting(@RequestBody FetchSettingDesStopDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/enableFetchSetting"})
    public BusinessResponseEntity<Object> enableFetchSetting(@RequestBody FetchSettingDesStopDTO var1);
}

