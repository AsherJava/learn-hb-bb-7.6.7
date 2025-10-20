/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.common.util.GcOrgUtils
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.penetrate.client.dto.CustomFetchDetailLedgerDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.va.query.template.service.TemplateDesignService
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.common.util.GcOrgUtils;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.penetrate.client.dto.CustomFetchDetailLedgerDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateModelGather;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateMainService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BdePenetrateMainServiceImpl
implements BdePenetrateMainService {
    private static final String DIM_MAP_DATATIME = "DATATIME";
    private static final String VAL_VAR_STR = "value";
    private static final Logger log = LoggerFactory.getLogger(BdePenetrateMainServiceImpl.class);
    @Autowired
    private IPenetrateModelGather gather;
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private BizModelServiceGather bizModelServiceGather;
    @Autowired
    private TemplateDesignService templateDesignService;

    @Override
    public PenetrateInitDTO init(String bizModelStr, String penetrateTypeStr, Map<String, Object> param) {
        ComputationModelEnum bizModel = this.convert2BizModel(bizModelStr);
        PenetrateTypeEnum penetrateType = PenetrateTypeEnum.fromCode(penetrateTypeStr);
        Object condi = this.gather.getModel(penetrateType).getCondi(param);
        return this.gather.getModel(penetrateType).init(bizModel.getCode(), condi);
    }

    @Override
    public Object doQuery(String bizModelStr, String penetrateTypeStr, Map<String, Object> param) {
        ComputationModelEnum bizModel = this.convert2BizModel(bizModelStr);
        PenetrateTypeEnum penetrateType = PenetrateTypeEnum.fromCode(penetrateTypeStr);
        Object condi = this.gather.getModel(penetrateType).getCondi(param);
        return this.gather.getModel(penetrateType).doQuery(bizModel.getCode(), condi);
    }

    @Override
    public Object customFetchDetailLedger(String bizModelStr, Map<String, Object> condi) {
        PenetrateBaseDTO orgnPenetrateBaseDTO = (PenetrateBaseDTO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(condi), PenetrateBaseDTO.class);
        Assert.isNotNull((Object)orgnPenetrateBaseDTO);
        Assert.isNotEmpty((String)orgnPenetrateBaseDTO.getUnitCode());
        Assert.isNotNull((Object)orgnPenetrateBaseDTO.getAcctYear());
        Object penetrateBaseDTO = this.gather.getModel(PenetrateTypeEnum.DETAILLEDGER).initCondi(this.gather.getModel(PenetrateTypeEnum.DETAILLEDGER).getCondi(condi));
        penetrateBaseDTO.setCurrencyCode(orgnPenetrateBaseDTO.getCurrencyCode());
        penetrateBaseDTO.setFetchType(orgnPenetrateBaseDTO.getFetchType());
        penetrateBaseDTO.setUnitType(orgnPenetrateBaseDTO.getUnitType());
        penetrateBaseDTO.setUnitVer(orgnPenetrateBaseDTO.getUnitVer());
        String fetchSourceCode = penetrateBaseDTO.getFetchSourceCode();
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode()).getByCode(fetchSourceCode);
        String queryTemplateId = customBizModelDTO.getQueryTemplateId();
        Assert.isNotEmpty((String)queryTemplateId, (String)"\u67e5\u8be2\u6a21\u677fId\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u7684\u7a7f\u900f\u9875\u9762\u662f\u5426\u6b63\u786e\u914d\u7f6e", (Object[])new Object[0]);
        QueryTemplate queryTemplate = this.templateDesignService.getTemplate(queryTemplateId);
        List params = queryTemplate.getDataSourceSet().getParams();
        Set tpVoNames = params.stream().map(TemplateParamsVO::getName).collect(Collectors.toSet());
        String oriFixedCondition = customBizModelDTO.getFixedCondition();
        String fetchTable = customBizModelDTO.getFetchTable();
        Pattern CTX_PARAM_PATTERN = Pattern.compile("#[a-zA-Z0-9_]+#");
        Matcher matcher = CTX_PARAM_PATTERN.matcher(oriFixedCondition + fetchTable);
        HashSet<String> oriFieldsSet = new HashSet<String>();
        String ctxParam = "";
        while (matcher.find()) {
            ctxParam = matcher.group();
            oriFieldsSet.add(ctxParam.toUpperCase().replace("#", ""));
        }
        Set fieldsSet = oriFieldsSet.stream().filter(field -> field.equalsIgnoreCase(ArgumentValueEnum.RPUNITCODE.getCode()) || field.equalsIgnoreCase(ArgumentValueEnum.UNITCODE.getCode()) || field.equalsIgnoreCase(ArgumentValueEnum.ASSISTCODE.getCode()) || field.equalsIgnoreCase(ArgumentValueEnum.BOOKCODE.getCode())).map(String::toUpperCase).collect(Collectors.toSet());
        if (!tpVoNames.containsAll(fieldsSet)) {
            throw new BusinessRuntimeException("\u7f3a\u5c11\u67e5\u8be2\u6761\u4ef6\u201c\u5b57\u6bb5\u6807\u8bc6\u201d\uff0c\u8bf7\u5148\u5728\u67e5\u8be2\u5b9a\u4e49\u4e2d\u914d\u7f6e\u3002");
        }
        Map queryCondi = (Map)condi.get("row");
        OrgMappingDTO orgMappingDTO = penetrateBaseDTO.getOrgMapping();
        List orgMappingItems = orgMappingDTO.getOrgMappingItems();
        String unitCode = penetrateBaseDTO.getUnitCode();
        String unitCodeStr = ArgumentValueEnum.UNITCODE.getCode();
        if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
            queryCondi.put(unitCodeStr, unitCode);
        } else {
            Set<String> itemOrgCodeSet = orgMappingItems.stream().filter(item -> item != null && !StringUtils.isEmpty((String)item.getAcctOrgCode())).map(OrgMappingItem::getAcctOrgCode).collect(Collectors.toSet());
            this.putUnitOrBookCodeVal(itemOrgCodeSet, queryCondi, unitCodeStr, unitCode);
        }
        queryCondi.put(ArgumentValueEnum.RPUNITCODE.getCode(), orgMappingDTO.getReportOrgCode());
        Integer year = penetrateBaseDTO.getAcctYear();
        queryCondi.put(ArgumentValueEnum.YEAR.getCode(), year);
        queryCondi.put(ArgumentValueEnum.YEARPERIOD.getCode(), this.buildPeriod(year, penetrateBaseDTO.getEndPeriod()));
        Integer endPeriod = penetrateBaseDTO.getEndPeriod();
        queryCondi.put(ArgumentValueEnum.PERIOD.getCode(), endPeriod);
        queryCondi.put(ArgumentValueEnum.FULLPERIOD.getCode(), String.format("%1$s-%2$s", year, CommonUtil.lpad((String)String.valueOf(endPeriod), (String)"0", (int)2)));
        String dtoAcctBookCode = orgMappingDTO.getAcctBookCode();
        String bookCodeStr = ArgumentValueEnum.BOOKCODE.getCode();
        if (!StringUtils.isEmpty((String)dtoAcctBookCode)) {
            if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
                queryCondi.put(bookCodeStr, dtoAcctBookCode.replace("'", ""));
            } else {
                Set<String> assistBookSet = orgMappingItems.stream().filter(item -> item != null && !StringUtils.isEmpty((String)item.getAcctBookCode())).map(OrgMappingItem::getAcctBookCode).collect(Collectors.toSet());
                this.putUnitOrBookCodeVal(assistBookSet, queryCondi, bookCodeStr, dtoAcctBookCode);
            }
        } else {
            queryCondi.put(bookCodeStr, "");
        }
        queryCondi.put(ArgumentValueEnum.TASKID.getCode(), penetrateBaseDTO.getRequestTaskId());
        queryCondi.put(ArgumentValueEnum.INCLUDEUNCHARGED.getCode(), penetrateBaseDTO.getIncludeUncharged());
        Map dimensionSet = penetrateBaseDTO.getDimensionSet();
        if (MapUtils.isNotEmpty((Map)dimensionSet)) {
            String mdCurrencyCode = ArgumentValueEnum.MD_CURRENCY.getCode();
            if (dimensionSet.containsKey(mdCurrencyCode)) {
                queryCondi.put(mdCurrencyCode, ((Map)dimensionSet.get(mdCurrencyCode)).get(VAL_VAR_STR));
            } else {
                queryCondi.put(mdCurrencyCode, "");
            }
            String mdGcorgtypeCode = ArgumentValueEnum.MD_GCORGTYPE.getCode();
            if (dimensionSet.containsKey(mdGcorgtypeCode)) {
                queryCondi.put(mdGcorgtypeCode, ((Map)dimensionSet.get(mdGcorgtypeCode)).get(VAL_VAR_STR));
            } else {
                queryCondi.put(mdGcorgtypeCode, "");
            }
            String mdGcadjtypeCode = ArgumentValueEnum.MD_GCADJTYPE.getCode();
            if (dimensionSet.containsKey(mdGcadjtypeCode)) {
                queryCondi.put(mdGcadjtypeCode, ((Map)dimensionSet.get(mdGcadjtypeCode)).get(VAL_VAR_STR));
            } else {
                queryCondi.put(mdGcadjtypeCode, "");
            }
            String periodschemeCode = ArgumentValueEnum.PERIODSCHEME.getCode();
            if (dimensionSet.containsKey(DIM_MAP_DATATIME)) {
                queryCondi.put(periodschemeCode, ((Map)dimensionSet.get(DIM_MAP_DATATIME)).get(VAL_VAR_STR));
            } else {
                queryCondi.put(periodschemeCode, "");
            }
        }
        queryCondi.put(ArgumentValueEnum.STARTDATE.getCode(), penetrateBaseDTO.getStartDate());
        queryCondi.put(ArgumentValueEnum.ENDDATE.getCode(), penetrateBaseDTO.getEndDate());
        queryCondi.put(ArgumentValueEnum.SELFANDCHILDUNIT.getCode(), this.getSelfAndChildUnitVal(orgMappingDTO, (PenetrateBaseDTO)penetrateBaseDTO, year, endPeriod));
        String assistCode = orgMappingDTO.getAssistCode();
        String assistCodeStr = ArgumentValueEnum.ASSISTCODE.getCode();
        if (CollectionUtils.isEmpty((Collection)orgMappingItems) && StringUtils.isEmpty((String)assistCode)) {
            queryCondi.put(ContextVariableParseUtil.getVariable((String)assistCodeStr), "");
        } else if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
            queryCondi.put(ContextVariableParseUtil.getVariable((String)assistCodeStr), assistCode);
        } else {
            Set assistCodeSet = orgMappingItems.stream().filter(item -> item != null && !StringUtils.isEmpty((String)item.getAssistCode())).map(OrgMappingItem::getAssistCode).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(assistCodeSet)) {
                queryCondi.put(ContextVariableParseUtil.getVariable((String)assistCodeStr), String.join((CharSequence)"','", assistCodeSet));
            } else {
                queryCondi.put(ContextVariableParseUtil.getVariable((String)assistCodeStr), "");
            }
        }
        CustomFetchDetailLedgerDTO customFetchDetailLedgerDTO = new CustomFetchDetailLedgerDTO();
        customFetchDetailLedgerDTO.setQueryId(queryTemplate.getId());
        customFetchDetailLedgerDTO.setQueryCode(queryTemplate.getCode());
        customFetchDetailLedgerDTO.setQueryTitle(queryTemplate.getBaseInfo().getBaseInfo().getTitle());
        customFetchDetailLedgerDTO.setQueryCondi(queryCondi);
        return customFetchDetailLedgerDTO;
    }

    private String getSelfAndChildUnitVal(OrgMappingDTO orgMappingDTO, PenetrateBaseDTO penetrateBaseDTO, Integer year, Integer endPeriod) {
        String val;
        OrgDataParam orgDataParam = new OrgDataParam();
        orgDataParam.setOrgParentCode(orgMappingDTO.getReportOrgCode());
        orgDataParam.setAuthType("NONE");
        orgDataParam.setOrgType(penetrateBaseDTO.getUnitType());
        try {
            HashMap dimensionValueMap = MapUtils.isEmpty((Map)penetrateBaseDTO.getDimensionSet()) ? new HashMap() : (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)penetrateBaseDTO.getDimensionSet()), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
            orgDataParam.setOrgVerCode(dimensionValueMap.get(DIM_MAP_DATATIME) != null ? ((DimensionValue)dimensionValueMap.get(DIM_MAP_DATATIME)).getValue() : year + "Y" + String.format("00%02d", endPeriod));
            val = "'" + String.join((CharSequence)"','", GcOrgUtils.getChildAndSelfUnitCodes((OrgDataParam)orgDataParam)) + "'";
        }
        catch (Exception e) {
            log.error("getSelfAndChildUnitVal() \u5f02\u5e38", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return val;
    }

    private void putUnitOrBookCodeVal(Set<String> itemsValSet, Map<String, Object> queryCondi, String argValueCodeKey, String dtoArgValue) {
        if (CollectionUtils.isEmpty(itemsValSet)) {
            if (ArgumentValueEnum.UNITCODE.getCode().equals(argValueCodeKey)) {
                queryCondi.put(argValueCodeKey, dtoArgValue);
            } else {
                queryCondi.put(argValueCodeKey, dtoArgValue.replace("'", ""));
            }
        } else {
            queryCondi.put(argValueCodeKey, String.join((CharSequence)",", itemsValSet));
        }
    }

    private Pair<Date, Date> handleDateOffset(PenetrateBaseDTO condi, Map<String, Object> optimizeMap) {
        Date startDate = condi.getStartPeriod().compareTo(condi.getEndPeriod()) != 0 ? DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getStartPeriod(), (int)1) : DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getEndPeriod(), (int)1);
        Date endDate = DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getEndPeriod(), (int)1);
        Pair offsetDateByYearRange = BdeCommonUtil.handleOptimizeDateOffset((Pair)Pair.of((Object)startDate, (Object)endDate), (String)((String)optimizeMap.get("acctYear")), (int)1);
        Pair offsetDateByPeriodRange = BdeCommonUtil.handleOptimizeDateOffset((Pair)offsetDateByYearRange, (String)((String)optimizeMap.get("acctPeriod")), (int)2);
        return offsetDateByPeriodRange;
    }

    protected DataSchemeDTO getDataScheme(String dataSchemeCode) {
        return this.dataSchemeService.getByCode(dataSchemeCode);
    }

    public String buildPeriod(int year, int period) {
        if (period == 0) {
            period = 1;
        }
        return String.format("%1$d-%02$d", year, period);
    }

    private ComputationModelEnum convert2BizModel(String bizModelStr) {
        Assert.isNotEmpty((String)bizModelStr);
        ComputationModelEnum bizModel = ComputationModelEnum.getEnumByCode((String)bizModelStr);
        return bizModel;
    }
}

