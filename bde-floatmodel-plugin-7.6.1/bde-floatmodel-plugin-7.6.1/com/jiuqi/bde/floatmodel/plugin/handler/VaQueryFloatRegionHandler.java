/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.FloatArgsMappingVO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.GcOrgUtils
 *  com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO
 *  com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.fetch.vo.FetchQueryResultVO
 *  com.jiuqi.va.query.fetch.web.FetchQueryClient
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  com.jiuqi.va.query.template.dto.ScopeDefaultDTO
 *  com.jiuqi.va.query.template.service.TemplateContentService
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.web.QueryTemplateContentClient
 */
package com.jiuqi.bde.floatmodel.plugin.handler;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.FloatArgsMappingVO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.GcOrgUtils;
import com.jiuqi.bde.floatmodel.client.vo.VaQueryPluginDataVO;
import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import com.jiuqi.va.query.fetch.web.FetchQueryClient;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.template.dto.ScopeDefaultDTO;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.web.QueryTemplateContentClient;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

public class VaQueryFloatRegionHandler
implements FloatConfigHandler {
    private static final Logger logger = LoggerFactory.getLogger(VaQueryFloatRegionHandler.class);
    @Autowired
    @Lazy
    private FetchQueryClient fetchQueryClient;
    @Autowired
    @Lazy
    private QueryTemplateContentClient queryTemplateContentClient;
    @Autowired
    @Lazy
    private TemplateContentService templateContentService;
    @Value(value="${jiuqi.np.user.system[0].name:}")
    private String username;

    public String getCode() {
        return "VA_QUERY";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u67e5\u8be2";
    }

    public String getProdLine() {
        return "@bde";
    }

    public String getAppName() {
        return "bde-floatmodel";
    }

    public Integer getOrder() {
        return 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FetchFloatRowResult queryFloatRowDatas(FetchTaskContext fetchTaskContext, QueryConfigInfo queryConfigInfo) {
        if (queryConfigInfo == null) {
            return null;
        }
        VaQueryPluginDataVO vaQueryPluginDataVO = this.getVaQueryPluginDataVO(queryConfigInfo.getPluginData());
        if (vaQueryPluginDataVO == null || queryConfigInfo == null) {
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u67e5\u8be2\u5b9a\u4e49\u7ed3\u679c\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        String queryDefineCode = vaQueryPluginDataVO.getQueryDefineCode();
        if (StringUtils.isEmpty((String)queryDefineCode)) {
            throw new BusinessRuntimeException("\u672a\u914d\u7f6e\u6d6e\u52a8\u884c\u53d6\u6570\u67e5\u8be2\u5b9a\u4e49,\u53d6\u6570\u5931\u8d25\uff01");
        }
        try {
            BdeCommonUtil.initNpUser((String)"admin");
            List argMappings = vaQueryPluginDataVO.getArgsMapping();
            TemplateContentVO templateContentVO = (TemplateContentVO)this.queryTemplateContentClient.getTemplateContentByCode(queryDefineCode).getData();
            if (templateContentVO == null) {
                throw new BusinessRuntimeException("\u6839\u636e\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u3010" + queryDefineCode + "\u3011\u672a\u627e\u5230\u67e5\u8be2\u5b9a\u4e49,\u53d6\u6570\u5931\u8d25\uff01");
            }
            Map<String, Object> params = this.parseParam(fetchTaskContext, vaQueryPluginDataVO, templateContentVO);
            BdeLogUtil.recordLog((String)fetchTaskContext.getRequestTaskId(), (String)"\u6d6e\u52a8\u884c\u89e3\u6790-\u81ea\u5b9a\u4e49\u67e5\u8be2", (Object)new Object[]{params}, (String)queryDefineCode);
            FetchQueryResultVO floatData = (FetchQueryResultVO)this.fetchQueryClient.execSql(queryDefineCode, params).getData();
            FetchFloatRowResult fetchFloatRowResult = new FetchFloatRowResult();
            fetchFloatRowResult.setRowDatas(floatData.getRowDatas());
            LinkedHashMap floatColumns = new LinkedHashMap(floatData.getFloatColumns().size());
            for (Map.Entry floatColumn : floatData.getFloatColumns().entrySet()) {
                floatColumns.put(((String)floatColumn.getKey()).toUpperCase(), floatColumn.getValue());
            }
            fetchFloatRowResult.setFloatColumns(floatColumns);
            if (floatData == null || floatData.getFloatColumns() == null || floatData.getFloatColumns().size() == 0) {
                FetchFloatRowResult fetchFloatRowResult2 = fetchFloatRowResult;
                return fetchFloatRowResult2;
            }
            Map TemplateFieldSettingVOMap = templateContentVO.getFields().stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, Function.identity(), (K1, K2) -> K1));
            HashMap<String, ColumnTypeEnum> floatColumnsType = new HashMap<String, ColumnTypeEnum>();
            for (String name : floatData.getFloatColumns().keySet()) {
                ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getEnumByName((String)((TemplateFieldSettingVO)TemplateFieldSettingVOMap.get(name)).getDataType()) == null ? ColumnTypeEnum.STRING : ColumnTypeEnum.getEnumByName((String)((TemplateFieldSettingVO)TemplateFieldSettingVOMap.get(name)).getDataType());
                floatColumnsType.put(name.toUpperCase(), columnTypeEnum);
            }
            fetchFloatRowResult.setFloatColumnsType(floatColumnsType);
            FetchFloatRowResult fetchFloatRowResult3 = fetchFloatRowResult;
            return fetchFloatRowResult3;
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    public List<FetchQueryFiledVO> parseFloatRowFields(QueryConfigInfo queryConfigInfo) {
        VaQueryPluginDataVO vaQueryPluginDataVO = this.getVaQueryPluginDataVO(queryConfigInfo.getPluginData());
        if (vaQueryPluginDataVO == null) {
            return Collections.emptyList();
        }
        Assert.isNotEmpty((String)vaQueryPluginDataVO.getQueryDefineCode(), (String)"\u67e5\u8be2\u5b9a\u4e49\u65b9\u5f0f\u89e3\u6790\uff0c\u67e5\u8be2\u5b9a\u4e49code\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        if (this.templateContentService.getTemplateContentByCode(vaQueryPluginDataVO.getQueryDefineCode()) == null) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u3010%1$s\u3011\u672a\u627e\u5230\u67e5\u8be2\u5b9a\u4e49\u3002", vaQueryPluginDataVO.getQueryDefineCode()));
        }
        List simpleTemplateFields = this.templateContentService.getSimpleTemplateFields(vaQueryPluginDataVO.getQueryDefineCode());
        if (CollectionUtils.isEmpty((Collection)simpleTemplateFields)) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u67e5\u8be2\u5217", vaQueryPluginDataVO.getQueryDefineCode()));
        }
        simpleTemplateFields.stream().forEach(item -> {
            item.setName(item.getName().toUpperCase());
            item.setTitle(item.getTitle().toUpperCase());
        });
        return simpleTemplateFields;
    }

    public Map<String, Object> parseParam(FetchTaskContext fetchTaskContext, VaQueryPluginDataVO vaQueryPluginDataVO, TemplateContentVO templateContentVO) {
        List argMappings = vaQueryPluginDataVO.getArgsMapping();
        Map<String, Object> params = new HashMap<String, Object>(8);
        if (argMappings != null && argMappings.size() > 0) {
            List templateParamsVOList = templateContentVO.getParams();
            params = this.initQueryDefineCodeParams(templateParamsVOList);
            Map templateParamsVOMap = templateParamsVOList.stream().collect(Collectors.toMap(TemplateParamsVO::getName, Function.identity()));
            for (FloatArgsMappingVO argMappingVO : argMappings) {
                String argType;
                String argValue = argMappingVO.getArgValue();
                if (StringUtils.isEmpty((String)argValue) || StringUtils.isEmpty((String)(argType = argMappingVO.getArgType()))) continue;
                if ("DROPDOWN".equals(argType)) {
                    this.addPrecastParam(params, argMappingVO, fetchTaskContext, vaQueryPluginDataVO);
                    continue;
                }
                this.addCustomParam(params, argMappingVO, vaQueryPluginDataVO, fetchTaskContext, (TemplateParamsVO)templateParamsVOMap.get(argMappingVO.getName()));
            }
        }
        return params;
    }

    private Map<String, Object> initQueryDefineCodeParams(List<TemplateParamsVO> templateParamsVOList) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>(8);
        if (CollectionUtils.isEmpty(templateParamsVOList)) {
            return paramMap;
        }
        for (TemplateParamsVO templateParamsVO : templateParamsVOList) {
            if (StringUtils.isEmpty((String)templateParamsVO.getDefaultValue())) continue;
            String mode = templateParamsVO.getMode();
            if (QueryModeEnum.singleData.getModeSign().equals(mode)) {
                paramMap.put(templateParamsVO.getName(), templateParamsVO.getDefaultValue());
                continue;
            }
            if (QueryModeEnum.mutileData.getModeSign().equals(mode)) {
                paramMap.put(templateParamsVO.getName(), templateParamsVO.getDefaultValue().split(","));
                continue;
            }
            if (!QueryModeEnum.scope.getModeSign().equals(mode)) continue;
            paramMap.put(templateParamsVO.getName(), JSONUtil.parseObject((String)templateParamsVO.getDefaultValue(), ScopeDefaultDTO.class));
        }
        return paramMap;
    }

    private void addCustomParam(Map<String, Object> params, FloatArgsMappingVO argMappingVO, VaQueryPluginDataVO vaQueryPluginDataVO, FetchTaskContext fetchTaskContext, TemplateParamsVO templateParamsVOList) {
        if (templateParamsVOList == null) {
            logger.info("\u53d6\u6570\u516c\u5f0f\u65b9\u6848\u3010{}\u3011\u62a5\u8868\u65b9\u6848\u3010{}\u3011,\u62a5\u8868\u8868\u5355\u3010{}\u3011,\u83b7\u53d6\u6d6e\u52a8\u884c\u6570\u636e\u8c03\u7528\u67e5\u8be2\u5b9a\u4e49\u3010{}\u3011\u65f6\uff0c\u67e5\u8be2\u53c2\u6570\u3010{}\u3011\u5728\u67e5\u8be2\u5b9a\u4e49\u4e2d\u6ca1\u627e\u5230\u8be5\u67e5\u8be2\u6761\u4ef6\uff0c\u8df3\u8fc7\u4e0d\u5904\u7406", fetchTaskContext.getFetchSchemeId(), fetchTaskContext.getFormSchemeId(), fetchTaskContext.getFormId(), vaQueryPluginDataVO.getQueryDefineCode(), argMappingVO.getTitle());
            return;
        }
        String mode = templateParamsVOList.getMode();
        if (QueryModeEnum.singleData.getModeSign().equals(mode)) {
            String argValue = argMappingVO.getArgValue();
            if (fetchTaskContext.getExtParam() != null && fetchTaskContext.getExtParam().containsKey(argMappingVO.getName())) {
                argValue = (String)fetchTaskContext.getExtParam().get(argMappingVO.getName());
            }
            params.put(argMappingVO.getName(), argValue);
            return;
        }
        if (QueryModeEnum.mutileData.getModeSign().equals(mode)) {
            params.put(argMappingVO.getName(), argMappingVO.getArgValue().split(","));
            return;
        }
        if (QueryModeEnum.scope.getModeSign().equals(mode)) {
            String[] split = argMappingVO.getArgValue().split(":");
            if (split.length != 2) {
                logger.info("\u53d6\u6570\u516c\u5f0f\u65b9\u6848\u3010{}\u3011\u62a5\u8868\u65b9\u6848\u3010{}\u3011,\u62a5\u8868\u8868\u5355\u3010{}\u3011,\u83b7\u53d6\u6d6e\u52a8\u884c\u6570\u636e\u8c03\u7528\u67e5\u8be2\u5b9a\u4e49\u3010{}\u3011\u65f6\uff0c\u67e5\u8be2\u53c2\u6570\u3010{}\u3011\u8bbe\u7f6e\u7684\u67e5\u8be2\u6a21\u5f0f\u4e3a\u8303\u56f4\uff0c\u67e5\u8be2\u503c\u3010{}\u3011\u4e0d\u7b26\u5408\u8303\u56f4\u503c\u89c4\u8303\uff0c\u5e94\u8be5\u4ee5:\u5206\u5272\u8d77\u6b62\u6570\u636e\uff0c\u8df3\u8fc7\u4e0d\u5904\u7406", fetchTaskContext.getFetchSchemeId(), fetchTaskContext.getFormSchemeId(), fetchTaskContext.getFormId(), vaQueryPluginDataVO.getQueryDefineCode(), argMappingVO.getTitle(), argMappingVO.getArgValue());
                return;
            }
            ScopeDefaultDTO scopeDefaultDTO = new ScopeDefaultDTO();
            scopeDefaultDTO.setStart(split[0]);
            scopeDefaultDTO.setEnd(split[1]);
            params.put(argMappingVO.getName(), scopeDefaultDTO);
        }
    }

    private void addPrecastParam(Map<String, Object> params, FloatArgsMappingVO argMappingVO, FetchTaskContext fetchTaskContext, VaQueryPluginDataVO vaQueryPluginDataVO) {
        ArgumentValueEnum argumentValueEnumByCode = ArgumentValueEnum.getArgumentValueEnumByCode((String)argMappingVO.getArgValue());
        if (argumentValueEnumByCode == null) {
            logger.info("\u53d6\u6570\u516c\u5f0f\u65b9\u6848\u3010{}\u3011\u62a5\u8868\u65b9\u6848\u3010{}\u3011,\u62a5\u8868\u8868\u5355\u3010{}\u3011,\u83b7\u53d6\u6d6e\u52a8\u884c\u6570\u636e\u8c03\u7528\u67e5\u8be2\u5b9a\u4e49\u3010{}\u3011\u65f6\uff0c\u67e5\u8be2\u53c2\u6570\u3010{}\u3011\u7684\u53c2\u6570\u7c7b\u578b\u4e3a\u9884\u5236\u53c2\u6570\u3010{}\u3011\uff0c\u8be5\u53c2\u6570\u5c1a\u672a\u652f\u6301\uff0c\u8df3\u8fc7\u4e0d\u5904\u7406", fetchTaskContext.getFetchSchemeId(), fetchTaskContext.getFormSchemeId(), fetchTaskContext.getFormId(), vaQueryPluginDataVO.getQueryDefineCode(), argMappingVO.getTitle(), argMappingVO.getArgValue());
            return;
        }
        String year = "";
        String period = "";
        if (!StringUtils.isEmpty((String)fetchTaskContext.getEndDateStr())) {
            Date endDate = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
            year = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)1));
            period = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)2));
        }
        List orgMappingItems = fetchTaskContext.getOrgMapping().getOrgMappingItems();
        switch (argumentValueEnumByCode) {
            case UNITCODE: {
                if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
                    params.put(argMappingVO.getName(), fetchTaskContext.getOrgMapping().getAcctOrgCode());
                    break;
                }
                HashSet<String> orgCodeSet = new HashSet<String>();
                for (OrgMappingItem orgMappingItem : orgMappingItems) {
                    if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
                    orgCodeSet.add(orgMappingItem.getAcctOrgCode());
                }
                if (CollectionUtils.isEmpty(orgCodeSet)) {
                    params.put(argMappingVO.getName(), fetchTaskContext.getOrgMapping().getAcctOrgCode());
                    break;
                }
                params.put(argMappingVO.getName(), String.join((CharSequence)",", orgCodeSet));
                break;
            }
            case RPUNITCODE: {
                params.put(argMappingVO.getName(), fetchTaskContext.getOrgMapping().getReportOrgCode());
                break;
            }
            case YEAR: {
                params.put(argMappingVO.getName(), year);
                break;
            }
            case FULLPERIOD: {
                params.put(argMappingVO.getName(), CommonUtil.lpad((String)period, (String)"0", (int)2));
                break;
            }
            case PERIOD: {
                params.put(argMappingVO.getName(), period);
                break;
            }
            case YEARPERIOD: {
                params.put(argMappingVO.getName(), String.format("%1$s-%2$s", year, CommonUtil.lpad((String)period, (String)"0", (int)2)));
                break;
            }
            case BOOKCODE: {
                if (CollectionUtils.isEmpty((Collection)orgMappingItems) && StringUtils.isEmpty((String)fetchTaskContext.getOrgMapping().getAcctBookCode())) break;
                if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
                    params.put(argMappingVO.getName(), fetchTaskContext.getOrgMapping().getAcctBookCode().replace("'", ""));
                    break;
                }
                HashSet<String> assistBookSet = new HashSet<String>();
                for (OrgMappingItem orgMappingItem : orgMappingItems) {
                    if (StringUtils.isEmpty((String)orgMappingItem.getAcctBookCode())) continue;
                    assistBookSet.add(orgMappingItem.getAcctBookCode());
                }
                if (!CollectionUtils.isEmpty(assistBookSet)) {
                    params.put(argMappingVO.getName(), String.join((CharSequence)",", assistBookSet));
                    break;
                }
                params.put(argMappingVO.getName(), fetchTaskContext.getOrgMapping().getAcctBookCode().replace("'", ""));
                break;
            }
            case STARTDATE: {
                params.put(argMappingVO.getName(), BdeCommonUtil.formatDateNoDash((String)fetchTaskContext.getStartDateStr()));
                break;
            }
            case ENDDATE: {
                params.put(argMappingVO.getName(), BdeCommonUtil.formatDateNoDash((String)fetchTaskContext.getEndDateStr()));
                break;
            }
            case TASKID: {
                params.put(argMappingVO.getName(), fetchTaskContext.getTaskId());
                break;
            }
            case INCLUDEUNCHARGED: {
                params.put(argMappingVO.getName(), fetchTaskContext.getIncludeUncharged());
                break;
            }
            case MD_GCADJTYPE: {
                if (fetchTaskContext.getOtherEntity() == null) break;
                params.put(argMappingVO.getName(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCADJTYPE.getCode()));
                break;
            }
            case MD_CURRENCY: {
                if (fetchTaskContext.getOtherEntity() == null) break;
                params.put(argMappingVO.getName(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_CURRENCY.getCode()));
                break;
            }
            case MD_GCORGTYPE: {
                if (fetchTaskContext.getOtherEntity() == null) break;
                params.put(argMappingVO.getName(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCORGTYPE.getCode()));
                break;
            }
            case PERIODSCHEME: {
                params.put(argMappingVO.getName(), fetchTaskContext.getPeriodScheme());
                break;
            }
            case SELFANDCHILDUNIT: {
                params.put(argMappingVO.getName(), GcOrgUtils.unitToStringCommaSeparated((FetchTaskContext)fetchTaskContext));
                break;
            }
        }
    }

    public VaQueryPluginDataVO getVaQueryPluginDataVO(String plugDataStr) {
        if (StringUtils.isEmpty((String)plugDataStr)) {
            return null;
        }
        return (VaQueryPluginDataVO)JSONUtil.parseObject((String)plugDataStr, VaQueryPluginDataVO.class);
    }
}

