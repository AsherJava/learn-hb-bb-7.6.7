/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatArgsMappingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 *  com.jiuqi.gcreport.sdk.rest.FloatRegionConfigRequest
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.handler;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatArgsMappingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.sdk.rest.FloatRegionConfigRequest;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaQueryFloatConfigExcelHandler
implements FloatConfigExcelHandle {
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;
    @Autowired
    private FloatRegionConfigRequest floatRegionConfigRequest;

    @Override
    public String getCode() {
        return FloatResultQueryTypeEnum.VA_QUERY.getCode();
    }

    @Override
    public FloatRegionConfigVO getQueryFieldByConfig(FloatRegionConfigData floatRegionConfigData, FetchSettingExcelContext fetchSettingExcelContext) {
        ArrayList<Object> configArgs;
        FloatRegionConfigVO fetchFloatSettingVO = new FloatRegionConfigVO();
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        FloatResultQueryTypeEnum floatResultQueryTypeEnum = FloatResultQueryTypeEnum.getEnumByName((String)floatRegionConfigData.getFloatType());
        if (floatResultQueryTypeEnum == null) {
            throw new BdeRuntimeException("\u6682\u672a\u652f\u6301" + floatRegionConfigData.getFloatType() + "\u7c7b\u578b\u6d6e\u52a8\u884c\u8bbe\u7f6e");
        }
        String floatCode = floatResultQueryTypeEnum.getCode();
        fetchFloatSettingVO.setQueryType(floatCode);
        String templateCode = "";
        String argConfig = "";
        VaQueryPluginDataVO vaQueryPluginDataVO = new VaQueryPluginDataVO();
        try {
            templateCode = floatRegionConfigData.getFloatConfig().substring(0, floatRegionConfigData.getFloatConfig().indexOf("{"));
            argConfig = floatRegionConfigData.getFloatConfig().substring(floatRegionConfigData.getFloatConfig().indexOf("{") + 1, floatRegionConfigData.getFloatConfig().indexOf("}"));
        }
        catch (Exception e) {
            throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u8868\u4e2d\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9\u683c\u5f0f\u9519\u8bef", floatRegionConfigData.getSheetName()), (Throwable)e);
        }
        vaQueryPluginDataVO.setQueryDefineCode(templateCode);
        List fetchQueryFiledVOS = this.floatRegionConfigRequest.getQueryParams(templateCode);
        ArrayList<FloatArgsMappingVO> argsMappingVOS = new ArrayList<FloatArgsMappingVO>();
        if (StringUtils.isEmpty((String)argConfig)) {
            configArgs = new ArrayList();
        } else {
            String[] args = argConfig.split(";");
            configArgs = new ArrayList<String>(Arrays.asList(args));
        }
        Map<String, String> titleAndArgMap = configArgs.stream().collect(Collectors.toMap(a -> a.split("\\|")[0], arg -> arg, (k1, k2) -> k1));
        for (FetchQueryFiledVO fetchQueryFiledVO : fetchQueryFiledVOS) {
            FloatArgsMappingVO argsMappingVO = new FloatArgsMappingVO();
            BeanUtils.copyProperties(fetchQueryFiledVO, argsMappingVO);
            if (titleAndArgMap.get(fetchQueryFiledVO.getTitle()) != null) {
                String argType = titleAndArgMap.get(fetchQueryFiledVO.getTitle()).split("\\|")[1];
                String argValue = titleAndArgMap.get(fetchQueryFiledVO.getTitle()).split("\\|")[2];
                if ("\u9884\u5236".equals(argType)) {
                    argsMappingVO.setArgType("DROPDOWN");
                    argsMappingVO.setArgValue(ArgumentValueEnum.getArgumentValueEnumByTitle((String)argValue).getCode());
                } else if ("\u81ea\u5b9a\u4e49".equals(argType)) {
                    argsMappingVO.setArgType("INPUTBOX");
                    argsMappingVO.setArgValue(argValue);
                }
            }
            argsMappingVOS.add(argsMappingVO);
        }
        vaQueryPluginDataVO.setArgsMapping(argsMappingVOS);
        queryConfigInfo.setPluginData(JSONUtil.toJSONString((Object)vaQueryPluginDataVO));
        fetchFloatSettingVO.setQueryConfigInfo(queryConfigInfo);
        try {
            queryConfigInfo.setQueryFields(FetchSettingNrUtil.convertFetchQueryFiledVO((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.floatRowAnalysisClient.parseFloatRowFields(new EncryptRequestDTO((Object)fetchFloatSettingVO)))));
        }
        catch (Exception e) {
            throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u8868\u4e2d%2$s\u67e5\u8be2\u6807\u8bc6\u4e0d\u5b58\u5728", floatRegionConfigData.getSheetName(), templateCode), (Throwable)e);
        }
        return fetchFloatSettingVO;
    }

    @Override
    public String initFloatConfig(FloatRegionConfigVO fetchFloatSettingVO, FetchSettingExportContext fetchSettingExcelContext) {
        String separator = "|";
        VaQueryPluginDataVO vaQueryPluginDataVO = (VaQueryPluginDataVO)JSONUtil.parseObject((String)fetchFloatSettingVO.getQueryConfigInfo().getPluginData(), VaQueryPluginDataVO.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vaQueryPluginDataVO.getQueryDefineCode()).append("{");
        ArrayList<String> argList = new ArrayList<String>(vaQueryPluginDataVO.getArgsMapping().size());
        block8: for (FloatArgsMappingVO argsMappingVO : vaQueryPluginDataVO.getArgsMapping()) {
            if (StringUtils.isEmpty((String)argsMappingVO.getArgType()) || StringUtils.isEmpty((String)argsMappingVO.getArgValue())) continue;
            StringBuilder argInfo = new StringBuilder();
            argInfo.append(argsMappingVO.getTitle()).append(separator);
            switch (argsMappingVO.getArgType()) {
                case "DROPDOWN": {
                    ArgumentValueEnum argumentValueEnumByCode = ArgumentValueEnum.getArgumentValueEnumByCode((String)argsMappingVO.getArgValue());
                    if (argumentValueEnumByCode == null) continue block8;
                    argInfo.append("\u9884\u5236").append(separator).append(argumentValueEnumByCode.getTitle());
                    break;
                }
                case "INPUTBOX": {
                    argInfo.append("\u81ea\u5b9a\u4e49").append(separator).append(argsMappingVO.getArgValue());
                    break;
                }
                default: {
                    argInfo.append(separator).append(argsMappingVO.getArgValue());
                }
            }
            argList.add(argInfo.toString());
        }
        stringBuilder.append(String.join((CharSequence)";", argList)).append("}");
        return stringBuilder.toString();
    }
}

