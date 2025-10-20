/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 */
package com.jiuqi.bde.penetrate.impl.core.model;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateColumnBuilderGather;
import com.jiuqi.bde.penetrate.impl.gather.impl.PenetrateContentProviderGather;
import com.jiuqi.bde.penetrate.impl.i18n.BdeBizModelTitleI18NResource;
import com.jiuqi.bde.penetrate.impl.model.IBdePenetrateModel;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBdePenetrateModel<C extends PenetrateBaseDTO, E>
implements IBdePenetrateModel<C, E> {
    @Autowired
    private IPenetrateColumnBuilderGather columnProviderGather;
    @Autowired
    private PenetrateContentProviderGather contentProviderGather;
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private BdeI18nHelper bdeI18nHelper;
    @Autowired
    private BizModelServiceGather bizModelServiceGather;
    @Autowired
    protected IBizModelGather modelGather;

    @Override
    public PenetrateInitDTO init(String bizModelKey, C orgnCondi) {
        String datasourceCode;
        this.basicCondiCheck((PenetrateBaseDTO)orgnCondi);
        this.externalInitCheck((PenetrateBaseDTO)orgnCondi);
        PenetrateBaseDTO condi = (PenetrateBaseDTO)this.cloneObj(orgnCondi, orgnCondi.getClass());
        condi.setBizModelKey(bizModelKey);
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            condi.setAssTypeList(condi.getAssTypeList().stream().filter(item -> !StringUtils.isEmpty((String)item.getDimValue())).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty((Collection)condi.getDimType())) {
            HashSet dimSet = CollectionUtils.isEmpty((Collection)condi.getAssTypeList()) ? new HashSet() : condi.getAssTypeList().stream().map(Dimension::getDimCode).collect(Collectors.toSet());
            for (String dimType : condi.getDimType()) {
                if ("SUBJECTCODE".equals(dimType) || dimSet.contains(dimType)) continue;
                condi.getAssTypeList().add(new Dimension(dimType));
            }
        }
        PenetrateInitDTO penetrateInit = new PenetrateInitDTO();
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)orgnCondi.getOptimizeRuleGroup());
        if (!optimizeMap.isEmpty()) {
            Pair<Date, Date> offsetDateByPeriodRange = this.handleDateOffset(condi, optimizeMap);
            penetrateInit.setStartAcctYear(Integer.valueOf(DateUtils.getYearOfDate((Date)((Date)offsetDateByPeriodRange.getFirst()))));
            penetrateInit.setStartPeriod(Integer.valueOf(DateUtils.getDateFieldValue((Date)((Date)offsetDateByPeriodRange.getFirst()), (int)2)));
            penetrateInit.setEndAcctYear(Integer.valueOf(DateUtils.getYearOfDate((Date)((Date)offsetDateByPeriodRange.getSecond()))));
            penetrateInit.setEndPeriod(Integer.valueOf(DateUtils.getDateFieldValue((Date)((Date)offsetDateByPeriodRange.getSecond()), (int)2)));
        } else {
            penetrateInit.setStartAcctYear(orgnCondi.getAcctYear());
            penetrateInit.setStartPeriod(orgnCondi.getStartPeriod());
            penetrateInit.setEndAcctYear(orgnCondi.getAcctYear());
            penetrateInit.setEndPeriod(orgnCondi.getEndPeriod());
        }
        String orgCode = (String)optimizeMap.get("orgCode");
        OrgMappingDTO orgMapping = !StringUtils.isEmpty((String)orgCode) ? this.orgMappingProvider.getByCode(condi.getBblx()).getOrgMapping(orgCode) : this.orgMappingProvider.getByCode(condi.getBblx()).getOrgMapping(condi.getUnitCode());
        String string = datasourceCode = optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()) == null ? null : optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()).toString();
        if (!StringUtils.isEmpty(datasourceCode)) {
            orgMapping.setDataSourceCode(datasourceCode);
        }
        condi.setOrgMapping(orgMapping);
        if (ComputationModelEnum.CUSTOMFETCH.getCode().equals(bizModelKey)) {
            CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode()).getByCode(condi.getFetchSourceCode());
            penetrateInit.setTitle(customBizModelDTO.getName());
        } else {
            String resourceKey = BdeBizModelTitleI18NResource.getResourceKey(bizModelKey, this.getPenetrateType());
            String defaultMessage = BdeBizModelTitleI18NResource.getResourceName(bizModelKey, this.getPenetrateType());
            penetrateInit.setTitle(this.bdeI18nHelper.getMessage(resourceKey, defaultMessage));
        }
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            penetrateInit.setUnitCode(orgMapping.getAcctOrgCode());
            penetrateInit.setUnitName(orgMapping.getAcctOrgName());
        } else if (!StringUtils.isEmpty((String)condi.getAssistCode()) && !StringUtils.isEmpty((String)condi.getAssistName())) {
            penetrateInit.setUnitCode(condi.getAssistCode());
            penetrateInit.setUnitName(StringUtils.isEmpty((String)condi.getAcctOrgName()) ? condi.getAssistName() : condi.getAcctOrgName() + "|" + condi.getAssistName());
        } else if (!StringUtils.isEmpty((String)condi.getAcctOrgCode()) && !StringUtils.isEmpty((String)condi.getAcctOrgName())) {
            penetrateInit.setUnitCode(condi.getAcctOrgCode());
            penetrateInit.setUnitName(condi.getAcctOrgName());
        } else {
            penetrateInit.setUnitCode(CollectionUtil.join(orgMapping.getOrgMappingItems().stream().map(OrgMappingItem::getAcctOrgCode).distinct().collect(Collectors.toList()), (String)","));
            penetrateInit.setUnitName(CollectionUtil.join(orgMapping.getOrgMappingItems().stream().map(OrgMappingItem::getAcctOrgName).distinct().collect(Collectors.toList()), (String)","));
        }
        condi.setFetchType(orgnCondi.getFetchType());
        penetrateInit.setColumns(this.columnProviderGather.getProvider(bizModelKey, this.getPenetrateType()).createQueryColumns(condi));
        return penetrateInit;
    }

    @Override
    public E doQuery(String bizModelKey, C orgnCondi) {
        C condi = this.initCondi(orgnCondi);
        condi.setBizModelKey(bizModelKey);
        return (E)this.contentProviderGather.getProvider(condi.getDataScheme().getPluginType(), bizModelKey, this.getPenetrateType()).doQuery(condi);
    }

    @Override
    public C initCondi(C orgnCondi) {
        String datasourceCode;
        Map optimizeMap;
        this.basicCondiCheck((PenetrateBaseDTO)orgnCondi);
        this.externalQueryCheck((PenetrateBaseDTO)orgnCondi);
        PenetrateBaseDTO condi = (PenetrateBaseDTO)this.cloneObj(orgnCondi, orgnCondi.getClass());
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            condi.setAssTypeList(condi.getAssTypeList().stream().filter(item -> !StringUtils.isEmpty((String)item.getDimValue())).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty((Collection)condi.getDimType())) {
            HashSet dimSet = CollectionUtils.isEmpty((Collection)condi.getAssTypeList()) ? new HashSet() : condi.getAssTypeList().stream().map(Dimension::getDimCode).collect(Collectors.toSet());
            for (String dimType : condi.getDimType()) {
                if ("SUBJECTCODE".equals(dimType) || dimSet.contains(dimType)) continue;
                condi.getAssTypeList().add(new Dimension(dimType));
            }
        }
        if (!(optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)orgnCondi.getOptimizeRuleGroup())).isEmpty()) {
            Pair<Date, Date> offsetDateByPeriodRange = this.handleDateOffset(condi, optimizeMap);
            condi.setAcctYear(Integer.valueOf(DateUtils.getYearOfDate((Date)((Date)offsetDateByPeriodRange.getFirst()))));
            condi.setStartPeriod(Integer.valueOf(DateUtils.getDateFieldValue((Date)((Date)offsetDateByPeriodRange.getFirst()), (int)2)));
            condi.setEndPeriod(Integer.valueOf(DateUtils.getDateFieldValue((Date)((Date)offsetDateByPeriodRange.getSecond()), (int)2)));
        } else {
            condi.setAcctYear(orgnCondi.getAcctYear());
            condi.setStartPeriod(orgnCondi.getStartPeriod());
            condi.setEndPeriod(orgnCondi.getEndPeriod());
        }
        String orgCode = (String)optimizeMap.get("orgCode");
        OrgMappingDTO orgMapping = !StringUtils.isEmpty((String)orgCode) ? this.orgMappingProvider.getByCode(condi.getBblx()).getOrgMapping(orgCode) : this.orgMappingProvider.getByCode(condi.getBblx()).getOrgMapping(condi.getUnitCode());
        String string = datasourceCode = optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()) == null ? null : optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()).toString();
        if (!StringUtils.isEmpty(datasourceCode)) {
            orgMapping.setDataSourceCode(datasourceCode);
        }
        condi.setOrgMapping(orgMapping);
        condi.setUnitCode(orgMapping.getAcctOrgCode());
        DataSchemeDTO dataSchemeDto = this.getDataScheme(orgMapping.getDataSchemeCode());
        condi.setDataScheme(dataSchemeDto);
        condi.setCurrencyCode(orgnCondi.getCurrencyCode());
        condi.setFetchType(orgnCondi.getFetchType());
        condi.setUnitType(orgnCondi.getUnitType());
        condi.setUnitVer(orgnCondi.getUnitVer());
        return (C)condi;
    }

    protected DataSchemeDTO getDataScheme(String dataSchemeCode) {
        return this.dataSchemeService.getByCode(dataSchemeCode);
    }

    private Pair<Date, Date> handleDateOffset(C condi, Map<String, Object> optimizeMap) {
        Date startDate = condi.getStartPeriod().compareTo(condi.getEndPeriod()) != 0 ? DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getStartPeriod(), (int)1) : DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getEndPeriod(), (int)1);
        Date endDate = DateUtils.dateOf((int)condi.getAcctYear(), (int)condi.getEndPeriod(), (int)1);
        Pair offsetDateByYearRange = BdeCommonUtil.handleOptimizeDateOffset((Pair)Pair.of((Object)startDate, (Object)endDate), (String)((String)optimizeMap.get("acctYear")), (int)1);
        Pair offsetDateByPeriodRange = BdeCommonUtil.handleOptimizeDateOffset((Pair)offsetDateByYearRange, (String)((String)optimizeMap.get("acctPeriod")), (int)2);
        return offsetDateByPeriodRange;
    }

    protected void basicCondiCheck(PenetrateBaseDTO condi) {
        Assert.isNotNull((Object)condi);
        Assert.isNotEmpty((String)condi.getUnitCode());
        Assert.isNotNull((Object)condi.getAcctYear());
    }

    protected void externalInitCheck(PenetrateBaseDTO condi) {
    }

    protected void externalQueryCheck(PenetrateBaseDTO condi) {
    }

    protected <T> T cloneObj(Object source, Class<T> clazz) {
        return (T)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)source), clazz);
    }
}

