/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.api.GcBasedataFeignClient
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  org.thymeleaf.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.GcBasedataFeignClient;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

@Service
public class FetchSettingDesBaseDataServiceImpl
implements FetchSettingDesBaseDataService {
    @Autowired
    private GcBasedataFeignClient basedataFeignClient;
    private static final String BASE_DATA_TABLE_CODE = "tableCode";
    private static final String BASE_DATA_CODES = "codes";

    @Override
    public Map<String, List<BaseDataVO>> getBaseDataByTableAndCode(Map<String, BaseDataParam> dimBaseDataParamMap) {
        HashMap<String, List<BaseDataVO>> dimBaseDataMap = new HashMap<String, List<BaseDataVO>>();
        for (String dimCode : dimBaseDataParamMap.keySet()) {
            BaseDataParam baseDataParam = dimBaseDataParamMap.get(dimCode);
            if (ObjectUtils.isEmpty(baseDataParam) || StringUtils.isEmpty((String)baseDataParam.getTableName()) || StringUtils.isEmpty((String)baseDataParam.getCode())) {
                dimBaseDataMap.put(dimCode, new ArrayList());
                continue;
            }
            List<BaseDataVO> baseDataList = this.getBaseDataByTableAndCodeSingle(baseDataParam.getTableName(), baseDataParam.getCode());
            dimBaseDataMap.put(dimCode, baseDataList);
        }
        return dimBaseDataMap;
    }

    @Override
    public List<BaseDataVO> getBaseDataByTableAndCodeSingle(String tableName, String dimValue) {
        if (StringUtils.isEmpty((String)tableName) || StringUtils.isEmpty((String)dimValue)) {
            return new ArrayList<BaseDataVO>();
        }
        if (dimValue.contains(":") || dimValue.contains("\uff1a")) {
            return new ArrayList<BaseDataVO>();
        }
        List<String> dimValueList = Arrays.stream(dimValue.split(",")).collect(Collectors.toList());
        return this.queryBaseData(tableName, dimValueList);
    }

    private List<BaseDataVO> queryBaseData(String tableName, List<String> codes) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(BASE_DATA_TABLE_CODE, tableName);
        params.put(BASE_DATA_CODES, codes);
        List baseDataVOList = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.basedataFeignClient.getBaseDataByCodes(params));
        List noNullbaseDataList = baseDataVOList.stream().filter(item -> Objects.nonNull(item)).collect(Collectors.toList());
        if (noNullbaseDataList.size() != codes.size()) {
            return new ArrayList<BaseDataVO>();
        }
        return baseDataVOList;
    }
}

