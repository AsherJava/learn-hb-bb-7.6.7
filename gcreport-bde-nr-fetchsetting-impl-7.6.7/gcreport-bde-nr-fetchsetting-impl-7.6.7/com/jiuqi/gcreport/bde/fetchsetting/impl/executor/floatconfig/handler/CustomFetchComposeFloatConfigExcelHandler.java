/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 *  com.jiuqi.va.domain.common.JSONUtil
 *  io.jsonwebtoken.lang.Objects
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.handler;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.CustomFetchPluginDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.CustomFetchPluginDimension;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import io.jsonwebtoken.lang.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchComposeFloatConfigExcelHandler
implements FloatConfigExcelHandle {
    private static final Logger log = LoggerFactory.getLogger(CustomFetchComposeFloatConfigExcelHandler.class);
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;
    @Autowired
    private BizModelClient bizModelClient;
    private final Pattern includePattern = Pattern.compile("\u5305\u542b\\(([^}]*?)\\)");
    private final Pattern excludePattern = Pattern.compile("\u6392\u9664\\(([^}]*?)\\)");
    private final String SEPARATOR = ",";

    @Override
    public String getCode() {
        return FloatResultQueryTypeEnum.CUSTOM_FETCHSOURCE.getCode();
    }

    @Override
    public FloatRegionConfigVO getQueryFieldByConfig(FloatRegionConfigData floatRegionConfigData, FetchSettingExcelContext fetchSettingExcelContext) {
        String fetchSourceName;
        FloatRegionConfigVO fetchFloatSettingVO = new FloatRegionConfigVO();
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        FloatResultQueryTypeEnum floatResultQueryTypeEnum = FloatResultQueryTypeEnum.getEnumByName((String)floatRegionConfigData.getFloatType());
        if (floatResultQueryTypeEnum == null) {
            throw new BdeRuntimeException("\u6682\u672a\u652f\u6301" + floatRegionConfigData.getFloatType() + "\u7c7b\u578b\u6d6e\u52a8\u884c\u8bbe\u7f6e");
        }
        String floatCode = floatResultQueryTypeEnum.getCode();
        fetchFloatSettingVO.setQueryType(floatCode);
        CustomFetchPluginDataVO customFetchPluginDataVO = new CustomFetchPluginDataVO();
        try {
            fetchSourceName = floatRegionConfigData.getFloatConfig().substring(0, floatRegionConfigData.getFloatConfig().indexOf("{"));
        }
        catch (Exception e) {
            throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u8868\u4e2d\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9\u683c\u5f0f\u9519\u8bef", floatRegionConfigData.getSheetName()), (Throwable)e);
        }
        BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByName(fetchSourceName.substring(0, fetchSourceName.indexOf("|")));
        if (bizModelDTO == null) {
            throw new BdeRuntimeException(String.format("\u6839\u636e\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6a21\u578b\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u4e1a\u52a1\u6a21\u578b\u914d\u7f6e", fetchSourceName.substring(0, fetchSourceName.indexOf("|"))));
        }
        String bizModelDTOCode = bizModelDTO.getCode();
        customFetchPluginDataVO.setFetchSourceCode(bizModelDTOCode);
        boolean cleanZeroRecords = fetchSourceName.substring(fetchSourceName.indexOf("|") + 1).equals("Y");
        customFetchPluginDataVO.setCleanZeroRecords(cleanZeroRecords);
        List<CustomFetchPluginDimension> customFetchPluginDimensions = this.analysisCustomFetchPluginDimensions(floatRegionConfigData, fetchSettingExcelContext, bizModelDTOCode);
        customFetchPluginDataVO.setDimensionMapping(customFetchPluginDimensions);
        queryConfigInfo.setPluginData(JSONUtil.toJSONString((Object)customFetchPluginDataVO));
        fetchFloatSettingVO.setQueryConfigInfo(queryConfigInfo);
        List<FloatQueryFieldVO> queryFields = FetchSettingNrUtil.convertFetchQueryFiledVO((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.floatRowAnalysisClient.parseFloatRowFields(new EncryptRequestDTO((Object)fetchFloatSettingVO))));
        queryConfigInfo.setQueryFields(queryFields);
        return fetchFloatSettingVO;
    }

    private List<CustomFetchPluginDimension> analysisCustomFetchPluginDimensions(FloatRegionConfigData floatRegionConfigData, FetchSettingExcelContext fetchSettingExcelContext, String fetchSourceCode) {
        String floatConfig = floatRegionConfigData.getFloatConfig();
        CustomBizModelDTO bizModelDTO = (CustomBizModelDTO)fetchSettingExcelContext.getBizModelByCode(fetchSourceCode);
        ArrayList<CustomFetchPluginDimension> customBizModelDTOList = new ArrayList<CustomFetchPluginDimension>();
        List customConditions = bizModelDTO.getCustomConditions();
        Map<String, CustomCondition> nameConditionMap = customConditions.stream().collect(Collectors.toMap(CustomCondition::getParamsName, item -> item));
        ArrayList<Object> dimensions = new ArrayList();
        try {
            String dimConfig = floatConfig.substring(floatConfig.indexOf("{") + 1, floatConfig.indexOf("}"));
            if (!StringUtils.isEmpty((String)dimConfig)) {
                dimensions = new ArrayList<String>(new ArrayList<String>(Arrays.asList(dimConfig.split(";"))));
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u8868\u4e2d\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9\u683c\u5f0f\u9519\u8bef", floatRegionConfigData.getSheetName()), (Throwable)e);
        }
        for (String dimensionStr : dimensions) {
            CustomCondition customCondition;
            CustomFetchPluginDimension customDimension = new CustomFetchPluginDimension();
            String dimName = dimensionStr.substring(0, dimensionStr.indexOf(","));
            customDimension.setDimName(dimName);
            Matcher includePatcher = this.includePattern.matcher(dimensionStr);
            if (includePatcher.find()) {
                customDimension.setDimValue(includePatcher.group(1));
            }
            if (Objects.isEmpty((Object)(customCondition = nameConditionMap.get(dimName)))) continue;
            customDimension.setRequired(customCondition.getRequired());
            customDimension.setDimCode(customCondition.getParamsCode());
            customDimension.setRuleCode(customCondition.getRuleCode());
            customDimension.setRuleShow(MatchingRuleEnum.getEnumByCode((String)customCondition.getRuleCode()).getRuleShow());
            customBizModelDTOList.add(customDimension);
        }
        return customBizModelDTOList;
    }

    @Override
    public String initFloatConfig(FloatRegionConfigVO fetchFloatSettingVO, FetchSettingExportContext fetchSettingExcelContext) {
        CustomFetchPluginDataVO customFetchPluginDataVO = (CustomFetchPluginDataVO)JSONUtil.parseObject((String)fetchFloatSettingVO.getQueryConfigInfo().getPluginData(), CustomFetchPluginDataVO.class);
        StringBuilder floatConfig = new StringBuilder();
        BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByCode(customFetchPluginDataVO.getFetchSourceCode());
        if (bizModelDTO == null) {
            return null;
        }
        floatConfig.append(bizModelDTO.getName()).append("|").append(customFetchPluginDataVO.isCleanZeroRecords() ? "Y" : "N").append("{");
        List queryFieldVOS = fetchFloatSettingVO.getQueryConfigInfo().getQueryFields();
        Map queryFieldVOMap = queryFieldVOS.stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, Function.identity(), (K1, K2) -> K1));
        ArrayList<String> dimensionMsg = new ArrayList<String>();
        for (CustomFetchPluginDimension dimension : customFetchPluginDataVO.getDimensionMapping()) {
            if (StringUtils.isEmpty((String)dimension.getDimCode())) continue;
            StringBuilder title = new StringBuilder(dimension.getDimName());
            if (!StringUtils.isEmpty((String)dimension.getDimValue())) {
                title.append(",").append(String.format("\u5305\u542b(%1$s)", dimension.getDimValue()));
            }
            dimensionMsg.add(title.toString());
        }
        floatConfig.append(String.join((CharSequence)";", dimensionMsg)).append("}");
        return floatConfig.toString();
    }
}

