/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.handler;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleDataComposeFloatConfigExcelHandler
implements FloatConfigExcelHandle {
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;
    @Autowired
    private BizModelClient bizModelClient;
    private final Pattern includePattern = Pattern.compile("\u5305\u542b\\(([^}]*?)\\)");
    private final Pattern excludePattern = Pattern.compile("\u6392\u9664\\(([^}]*?)\\)");

    @Override
    public String getCode() {
        return FloatResultQueryTypeEnum.SIMPLE_FETCHSOURCE.getCode();
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
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = new SimpleCustomComposePluginDataVO();
        ArrayList<String> dimensions = new ArrayList<String>();
        ArrayList<Dimension> dimensionList = new ArrayList<Dimension>();
        try {
            fetchSourceName = floatRegionConfigData.getFloatConfig().substring(0, floatRegionConfigData.getFloatConfig().indexOf("{"));
            String dimConfig = floatRegionConfigData.getFloatConfig().substring(floatRegionConfigData.getFloatConfig().indexOf("{") + 1, floatRegionConfigData.getFloatConfig().indexOf("}"));
            if (!StringUtils.isEmpty((String)dimConfig)) {
                dimensions.addAll(new ArrayList<String>(Arrays.asList(dimConfig.split(";"))));
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u8868\u4e2d\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9\u683c\u5f0f\u9519\u8bef", floatRegionConfigData.getSheetName()), (Throwable)e);
        }
        BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByName(fetchSourceName.substring(0, fetchSourceName.indexOf("|")));
        if (bizModelDTO == null) {
            throw new BdeRuntimeException(String.format("\u6839\u636e\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6a21\u578b\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u4e1a\u52a1\u6a21\u578b\u914d\u7f6e", fetchSourceName.substring(0, fetchSourceName.indexOf("|"))));
        }
        String code = bizModelDTO.getCode();
        simpleCustomComposePluginDataVO.setFetchSourceCode(code);
        fetchFloatSettingVO.setQueryConfigInfo(queryConfigInfo);
        simpleCustomComposePluginDataVO.setDimensionMapping(dimensionList);
        queryConfigInfo.setPluginData(JSONUtil.toJSONString((Object)simpleCustomComposePluginDataVO));
        List<FloatQueryFieldVO> queryFields = FetchSettingNrUtil.convertFetchQueryFiledVO((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.floatRowAnalysisClient.parseFloatRowFields(new EncryptRequestDTO((Object)fetchFloatSettingVO))));
        Map queryFieldVOMap = queryFields.stream().collect(Collectors.toMap(FloatQueryFieldVO::getTitle, Function.identity(), (K1, K2) -> K1));
        Boolean cleanZeroRecords = fetchSourceName.substring(fetchSourceName.indexOf("|") + 1, fetchSourceName.length()).equals("Y");
        simpleCustomComposePluginDataVO.setCleanZeroRecords(cleanZeroRecords.booleanValue());
        queryConfigInfo.setQueryFields(queryFields);
        for (String dimensionStr : dimensions) {
            Dimension dimension = new Dimension();
            FloatQueryFieldVO floatQueryFieldVO = (FloatQueryFieldVO)queryFieldVOMap.get(dimensionStr.substring(0, dimensionStr.indexOf(",")));
            if (floatQueryFieldVO == null) {
                throw new BdeRuntimeException(String.format("\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9\u5b58\u5728\u65e0\u6cd5\u89e3\u6790\u7684\u5b57\u6bb5\u3010%1$s\u3011", dimensionStr.substring(0, dimensionStr.indexOf(","))));
            }
            dimension.setDimCode(floatQueryFieldVO.getName());
            Matcher includePatcher = this.includePattern.matcher(dimensionStr);
            Matcher excludePatcher = this.excludePattern.matcher(dimensionStr);
            if (includePatcher.find()) {
                dimension.setDimValue(includePatcher.group(1));
            }
            if (excludePatcher.find()) {
                dimension.setExcludeValue(excludePatcher.group(1));
            }
            dimensionList.add(dimension);
        }
        queryConfigInfo.setPluginData(JSONUtil.toJSONString((Object)simpleCustomComposePluginDataVO));
        return fetchFloatSettingVO;
    }

    @Override
    public String initFloatConfig(FloatRegionConfigVO fetchFloatSettingVO, FetchSettingExportContext fetchSettingExcelContext) {
        String separator = ",";
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = (SimpleCustomComposePluginDataVO)JSONUtil.parseObject((String)fetchFloatSettingVO.getQueryConfigInfo().getPluginData(), SimpleCustomComposePluginDataVO.class);
        StringBuilder floatConfig = new StringBuilder();
        BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByCode(simpleCustomComposePluginDataVO.getFetchSourceCode());
        if (bizModelDTO == null) {
            return null;
        }
        floatConfig.append(bizModelDTO.getName()).append("|").append(simpleCustomComposePluginDataVO.isCleanZeroRecords() ? "Y" : "N").append("{");
        List queryFieldVOS = fetchFloatSettingVO.getQueryConfigInfo().getQueryFields();
        Map queryFieldVOMap = queryFieldVOS.stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, Function.identity(), (K1, K2) -> K1));
        ArrayList<String> dimensions = new ArrayList<String>();
        for (Dimension dimension : simpleCustomComposePluginDataVO.getDimensionMapping()) {
            if (StringUtils.isEmpty((String)dimension.getDimCode())) continue;
            FloatQueryFieldVO queryFieldVO = (FloatQueryFieldVO)queryFieldVOMap.get(dimension.getDimCode());
            StringBuilder title = new StringBuilder(queryFieldVO.getTitle());
            if (!StringUtils.isEmpty((String)dimension.getDimValue())) {
                title.append(separator).append(String.format("\u5305\u542b(%1$s)", dimension.getDimValue()));
            }
            if (!StringUtils.isEmpty((String)dimension.getExcludeValue())) {
                title.append(separator).append(String.format("\u6392\u9664(%1$s)", dimension.getExcludeValue()));
            }
            dimensions.add(title.toString());
        }
        floatConfig.append(String.join((CharSequence)";", dimensions)).append("}");
        return floatConfig.toString();
    }
}

