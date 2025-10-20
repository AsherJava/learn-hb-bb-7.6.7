/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionManageService
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.jiuqi.bde.bizmodel.impl.model.service.BizModelBaseDataConfigService;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizModelBaseDataConfigServiceImpl
implements BizModelBaseDataConfigService {
    @Autowired
    private INvwaSystemOptionService optionService;
    @Autowired
    private DimensionManageService dimensionService;

    @Override
    public Map<String, String> getBaseDataInputConfig() {
        HashMap<String, String> dimCodeBasaDataTableNameMap = new HashMap<String, String>();
        if ("0".equals(this.optionService.findValueById("BDE_FETCH_SETTING_BASE_DATA_INPUT_SWITCH"))) {
            return dimCodeBasaDataTableNameMap;
        }
        List dimensionQueryVOS = this.dimensionService.listDimensions();
        dimCodeBasaDataTableNameMap.putAll(this.buildDimCodeBasaDataTableNameMap(dimensionQueryVOS));
        return dimCodeBasaDataTableNameMap;
    }

    private Map<String, String> buildDimCodeBasaDataTableNameMap(List<DimensionQueryVO> dimensionQueryVOS) {
        HashMap<String, String> dimCodeBasaDataTableNameMap = new HashMap<String, String>();
        Map<String, String> dimRefTableMap = dimensionQueryVOS.stream().filter(dim -> StringUtils.isNotEmpty((String)dim.getReferField())).collect(Collectors.toMap(DimensionQueryVO::getCode, vo -> vo.getReferField()));
        dimCodeBasaDataTableNameMap.put(FetchFixedFieldEnum.SUBJECTCODE.getCode(), "MD_ACCTSUBJECT");
        dimCodeBasaDataTableNameMap.put(FetchFixedFieldEnum.EXCLUDESUBJECTCODE.getCode(), "MD_ACCTSUBJECT");
        dimCodeBasaDataTableNameMap.put(FetchFixedFieldEnum.CASHCODE.getCode(), "MD_CFITEM");
        dimCodeBasaDataTableNameMap.put(OptionItemEnum.CURRENCYCODE.getCode(), "MD_CURRENCY");
        for (String code : dimRefTableMap.keySet()) {
            dimCodeBasaDataTableNameMap.put(code, dimRefTableMap.get(code));
        }
        return dimCodeBasaDataTableNameMap;
    }
}

