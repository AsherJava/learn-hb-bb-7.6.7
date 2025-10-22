/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.common.vo.CustomPluginDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.handler;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.common.vo.CustomPluginDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinedFloatConfigExcelHandler
implements FloatConfigExcelHandle {
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;

    @Override
    public String getCode() {
        return FloatResultQueryTypeEnum.CUSTOM_SQL.getCode();
    }

    @Override
    public FloatRegionConfigVO getQueryFieldByConfig(FloatRegionConfigData floatRegionConfigData, FetchSettingExcelContext fetchSettingExcelContext) {
        FloatRegionConfigVO fetchFloatSettingVO = new FloatRegionConfigVO();
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        FloatResultQueryTypeEnum floatResultQueryTypeEnum = FloatResultQueryTypeEnum.getEnumByName((String)floatRegionConfigData.getFloatType());
        if (floatResultQueryTypeEnum == null) {
            throw new BdeRuntimeException("\u6682\u672a\u652f\u6301" + floatRegionConfigData.getFloatType() + "\u7c7b\u578b\u6d6e\u52a8\u884c\u8bbe\u7f6e");
        }
        String floatCode = floatResultQueryTypeEnum.getCode();
        fetchFloatSettingVO.setQueryType(floatCode);
        CustomPluginDataVO customPluginDataVO = new CustomPluginDataVO();
        customPluginDataVO.setDefinedSql(floatRegionConfigData.getFloatConfig());
        queryConfigInfo.setPluginData(JSONUtil.toJSONString((Object)customPluginDataVO));
        fetchFloatSettingVO.setQueryConfigInfo(queryConfigInfo);
        queryConfigInfo.setQueryFields(FetchSettingNrUtil.convertFetchQueryFiledVO((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.floatRowAnalysisClient.parseFloatRowFields(new EncryptRequestDTO((Object)fetchFloatSettingVO)))));
        return fetchFloatSettingVO;
    }

    @Override
    public String initFloatConfig(FloatRegionConfigVO fetchFloatSettingVO, FetchSettingExportContext fetchSettingExcelContext) {
        CustomPluginDataVO customPluginDataVO = (CustomPluginDataVO)JSONUtil.parseObject((String)fetchFloatSettingVO.getQueryConfigInfo().getPluginData(), CustomPluginDataVO.class);
        return customPluginDataVO.getDefinedSql();
    }
}

