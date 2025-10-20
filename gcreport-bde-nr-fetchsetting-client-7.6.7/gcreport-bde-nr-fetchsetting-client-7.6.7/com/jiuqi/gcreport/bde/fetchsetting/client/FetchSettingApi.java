/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchFixedAndFloatSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FetchSettingApi {
    @PostMapping(value={"/api/gcreport/v1/fetch/listFetchFixedAndFloatSetting"})
    public BusinessResponseEntity<FetchFixedAndFloatSettingVO> listFetchFixedAndFloatSetting(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/fetchSettingPublish"})
    public BusinessResponseEntity<Object> fetchSettingPublish(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/listDataLinkFixedSettingRowRecords"})
    public BusinessResponseEntity<List<FixedFieldDefineSettingDTO>> listDataLinkFixedSettingRowRecords(@RequestBody FetchSettingCond var1) throws Exception;

    @Deprecated
    @PostMapping(value={"/api/gcreport/v1/fetch/getBDEQueryDataMapping"})
    public BusinessResponseEntity<Map<String, Double>> getBDEQueryDataMapping(@RequestBody BDEQueryDataCond var1);

    @Deprecated
    @PostMapping(value={"/api/gcreport/v1/fetch/penetrate/tableData"})
    public BusinessResponseEntity<List<Map<String, Object>>> getBDEFloatPenetrateTableData(@RequestBody BDEQueryDataCond var1);

    @PostMapping(value={"/api/gcreport/v1/penetrate/getParam"})
    public BusinessResponseEntity<GcFetchRequestDTO> getBDEPenetrateParam(@RequestBody BDEQueryDataCond var1);
}

