/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.util.FloatOrderGenerator
 *  com.jiuqi.nr.mapping2.web.vo.SelectOptionVO
 *  com.jiuqi.nr.task.service.IFormSchemeService
 *  com.jiuqi.nr.task.web.vo.FormSchemeVO
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.xlib.runtime.Assert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.formulaschemeconfig.dao.FormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import com.jiuqi.gcreport.formulaschemeconfig.gather.IFormulaSchemeColumnProviderGather;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.NrFormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO;
import com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.xlib.runtime.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormulaSchemeConfigServiceImpl
implements FormulaSchemeConfigService,
INrExtractSchemeConfigService {
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    private FormulaSchemeConfigDao formulaSchemeConfigDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IFormulaSchemeColumnProviderGather iFormulaSchemeColumnProviderGather;
    @Autowired
    OrgDataService orgDataService;
    @Autowired
    @Lazy
    private IDesignTimeViewController iDesignTimeViewController;
    private final NedisCache formulaSchemeConfigCache;
    private static final String CACHE_KEY_TMPL = "%1$s|%2$s";
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeConfigServiceImpl.class);

    public FormulaSchemeConfigServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("FORMULA_SCHEME_CONFIG_MANAGE");
        this.formulaSchemeConfigCache = cacheManager.getCache("FORMULA_SCHEME_CONFIG");
    }

    private String buildCacheKey(String schemeId, String entityId) {
        return String.format(CACHE_KEY_TMPL, schemeId, entityId);
    }

    private void formulaSchemeConfigCacheEvict(String cacheKey) {
        this.formulaSchemeConfigCache.evict(cacheKey);
    }

    private List<FormulaSchemeConfigEO> getFormulaSchemeConfig(String schemeId, String entityId) {
        String cacheKey = this.buildCacheKey(schemeId, entityId);
        return (List)this.formulaSchemeConfigCache.get(cacheKey, () -> this.formulaSchemeConfigDao.getAllFormulaSchemeConfigs(schemeId, entityId));
    }

    @Override
    public List<FormulaSchemeColumn> createQueryColumns(String tableCategory, String taskId) {
        boolean isExistCurrency;
        ArrayList<FormulaSchemeColumn> columns = new ArrayList<FormulaSchemeColumn>();
        if ("mainTable".equals(tableCategory)) {
            FormulaSchemeColumn orgTitle = new FormulaSchemeColumn("\u7ec4\u7ec7\u673a\u6784", "orgTitle", Integer.valueOf(250));
            columns.add(orgTitle);
        } else {
            FormulaSchemeColumn orgUnit = new FormulaSchemeColumn("\u5355\u4f4d\u8303\u56f4", "orgUnit", Integer.valueOf(250));
            columns.add(orgUnit);
        }
        if (!"mainTable".equals(tableCategory)) {
            FormulaSchemeColumn reportType = new FormulaSchemeColumn("\u62a5\u8868\u7c7b\u578b", "reportType", Integer.valueOf(150));
            columns.add(reportType);
        }
        if (isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId)) {
            if ("mainTable".equals(tableCategory)) {
                FormulaSchemeColumn currencyTitle = new FormulaSchemeColumn("\u5e01\u79cd", "currencyTitle", Integer.valueOf(80));
                columns.add(currencyTitle);
            } else {
                FormulaSchemeColumn currency = new FormulaSchemeColumn("\u5e01\u79cd", "currency", Integer.valueOf(150));
                columns.add(currency);
            }
        }
        FormulaSchemeColumn fetchScheme = new FormulaSchemeColumn("\u53d6\u6570\u65b9\u6848", "fetchScheme", Integer.valueOf(150));
        columns.add(fetchScheme);
        FormulaSchemeColumn fetchAfterScheme = new FormulaSchemeColumn("\u53d6\u6570\u540e\u8fd0\u7b97", "fetchAfterScheme", Integer.valueOf(150));
        columns.add(fetchAfterScheme);
        FormulaSchemeColumn completeMerge = new FormulaSchemeColumn("\u5b8c\u6210\u5408\u5e76\u540e\u8fd0\u7b97", "completeMerge", Integer.valueOf(150));
        columns.add(completeMerge);
        List<String> providerNames = this.iFormulaSchemeColumnProviderGather.list();
        if (!providerNames.isEmpty()) {
            if (providerNames.contains("CONVERSION")) {
                FormulaSchemeColumn convertSystemScheme = new FormulaSchemeColumn("\u5141\u8bb8\u5916\u5e01\u6298\u7b97", "convertSystemScheme", Integer.valueOf(150));
                columns.add(convertSystemScheme);
                FormulaSchemeColumn convertAfterScheme = new FormulaSchemeColumn("\u6298\u7b97\u4e3a\u5f53\u524d\u5e01\u79cd\u540e\u8fd0\u7b97", "convertAfterScheme", Integer.valueOf(200));
                columns.add(convertAfterScheme);
            }
            if (providerNames.contains("SAMECONTROL")) {
                FormulaSchemeColumn unSaCtDeExtLaYeNumSaPer = new FormulaSchemeColumn("\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570", "unSaCtDeExtLaYeNumSaPer", Integer.valueOf(200));
                columns.add(unSaCtDeExtLaYeNumSaPer);
                FormulaSchemeColumn sameCtrlExtAfterScheme = new FormulaSchemeColumn("\u540c\u63a7\u3001\u975e\u540c\u63a7\u63d0\u53d6\u540e\u8fd0\u7b97", "sameCtrlExtAfterScheme", Integer.valueOf(200));
                columns.add(sameCtrlExtAfterScheme);
            }
            if (providerNames.contains("POST")) {
                FormulaSchemeColumn postingScheme = new FormulaSchemeColumn("\u8fc7\u8d26\u540e\u8fd0\u7b97", "postingScheme", Integer.valueOf(150));
                columns.add(postingScheme);
            }
        }
        if ("mainTable".equals(tableCategory)) {
            FormulaSchemeColumn collocationMethod = new FormulaSchemeColumn("\u914d\u7f6e\u65b9\u5f0f", "collocationMethod", Integer.valueOf(80));
            columns.add(collocationMethod);
            FormulaSchemeColumn strategyUnit = new FormulaSchemeColumn("\u7b56\u7565\u7ee7\u627f\u5355\u4f4d", "strategyUnit", Integer.valueOf(120));
            columns.add(strategyUnit);
            FormulaSchemeColumn action = new FormulaSchemeColumn("\u64cd\u4f5c", "action", Integer.valueOf(130));
            columns.add(action);
        }
        return columns;
    }

    @Override
    public NrFormulaSchemeConfigQueryResultDTO getShowTableByOrgId(NrFormulaSchemeConfigCondition formulaSchemeConfigCondition) {
        String schemeId = formulaSchemeConfigCondition.getSchemeId();
        String entityId = formulaSchemeConfigCondition.getEntityId();
        String orgId = formulaSchemeConfigCondition.getOrgId();
        List currencys = formulaSchemeConfigCondition.getCurrencys();
        List fetchSchemes = formulaSchemeConfigCondition.getFetchSchemes();
        List formulaSchemes = formulaSchemeConfigCondition.getFormulaSchemes();
        List collocationMethods = formulaSchemeConfigCondition.getCollocationMethods();
        Boolean showBlankRow = formulaSchemeConfigCondition.getShowBlankRow();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(formSchemeDefine.getTaskKey());
        Map<String, Object> formulaSchemeData = this.getFormulaSchemesBySchemeId(schemeId, entityId);
        String orgType = formulaSchemeData.get("orgType").toString();
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId);
        String parents = (String)((OrgDO)orgDOList.get(0)).get((Object)"parents");
        List<String> parentList = Arrays.stream(parents.split("/")).filter(id -> !orgId.equals(id)).collect(Collectors.toList());
        List<String> childWithSelfList = orgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        List<Object> currentOrgTree = this.getCurrentOrgTree(orgDOList, schemeId);
        List<FormulaSchemeConfigEO> allFormulaSchemeConfig = this.getFormulaSchemeConfig(schemeId, entityId);
        Map<Boolean, List<FormulaSchemeConfigEO>> partitioned = allFormulaSchemeConfig.stream().collect(Collectors.partitioningBy(eo -> "-".equals(eo.getBblx())));
        List<FormulaSchemeConfigEO> unitFormulaSchemeConfig = partitioned.get(true);
        List<FormulaSchemeConfigEO> strategyFormulaSchemeConfig = partitioned.get(false);
        if (!strategyFormulaSchemeConfig.isEmpty()) {
            Collections.reverse(strategyFormulaSchemeConfig);
            Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap = strategyFormulaSchemeConfig.stream().collect(Collectors.groupingBy(FormulaSchemeConfigEO::getOrgId));
            this.processParentStrategies(parentList, codeToObjectsMap, currentOrgTree, formulaSchemeData, isExistCurrency);
            this.processChildStrategies(childWithSelfList, codeToObjectsMap, currentOrgTree, formulaSchemeData, isExistCurrency, orgId, orgType);
        }
        if (!unitFormulaSchemeConfig.isEmpty()) {
            this.processUnitConfigurations(unitFormulaSchemeConfig, currentOrgTree, formulaSchemeData, isExistCurrency);
        }
        if (!currencys.isEmpty() && !currencys.contains("ALL")) {
            boolean containsBWB = currencys.contains("BWB");
            currentOrgTree = currentOrgTree.stream().filter(item -> containsBWB && item.getIsBaseCurrency() != false || currencys.contains(item.getCurrencyCode())).collect(Collectors.toList());
        }
        if (!fetchSchemes.isEmpty()) {
            currentOrgTree = currentOrgTree.stream().filter(item -> fetchSchemes.contains(item.getFetchSchemeId())).collect(Collectors.toList());
        }
        if (!formulaSchemes.isEmpty()) {
            currentOrgTree = currentOrgTree.stream().filter(vo -> {
                if (vo.getPostingSchemeId() != null && formulaSchemes.contains(vo.getPostingSchemeId())) {
                    return true;
                }
                return Stream.of(vo.getFetchAfterSchemeId(), vo.getCompleteMergeId(), vo.getConvertAfterSchemeId(), vo.getUnSaCtDeExtLaYeNumSaPerId(), vo.getSameCtrlExtAfterSchemeId()).filter(Objects::nonNull).filter(list -> !list.isEmpty()).anyMatch(list -> list.stream().anyMatch(formulaSchemes::contains));
            }).collect(Collectors.toList());
        }
        if (!collocationMethods.isEmpty()) {
            currentOrgTree = currentOrgTree.stream().filter(item -> collocationMethods.contains(item.getCollocationMethodId())).collect(Collectors.toList());
        }
        if (showBlankRow != null && !showBlankRow.booleanValue()) {
            currentOrgTree = currentOrgTree.stream().filter(vo -> {
                boolean hasStringValue = !StringUtils.isEmpty((String)vo.getPostingSchemeId()) || !StringUtils.isEmpty((String)vo.getFetchSchemeId()) || !StringUtils.isEmpty((String)vo.getConvertSystemSchemeId()) || !StringUtils.isEmpty((String)vo.getCollocationMethodId()) || !StringUtils.isEmpty((String)vo.getStrategyUnit());
                boolean hasListValue = Stream.of(vo.getFetchAfterSchemeId(), vo.getCompleteMergeId(), vo.getConvertAfterSchemeId(), vo.getUnSaCtDeExtLaYeNumSaPerId(), vo.getSameCtrlExtAfterSchemeId()).anyMatch(list -> list != null && !list.isEmpty());
                return hasStringValue || hasListValue;
            }).collect(Collectors.toList());
        }
        int total = currentOrgTree.size();
        currentOrgTree = currentOrgTree.stream().skip((long)(formulaSchemeConfigCondition.getPage() - 1) * (long)formulaSchemeConfigCondition.getPageSize().intValue()).limit(formulaSchemeConfigCondition.getPageSize().intValue()).collect(Collectors.toList());
        return new NrFormulaSchemeConfigQueryResultDTO(currentOrgTree, Integer.valueOf(total));
    }

    private void processParentStrategies(List<String> parentList, Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap, List<NrFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData, boolean isExistCurrency) {
        List parentOrderedList = parentList.stream().filter(codeToObjectsMap::containsKey).flatMap(key -> ((List)codeToObjectsMap.get(key)).stream()).collect(Collectors.toList());
        for (FormulaSchemeConfigEO config : parentOrderedList) {
            this.setTableVO(currentOrgTree, config, formulaSchemeData, isExistCurrency);
        }
    }

    private void processChildStrategies(List<String> childWithSelfList, Map<String, List<FormulaSchemeConfigEO>> codeToObjectsMap, List<NrFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData, boolean isExistCurrency, String orgId, String orgType) {
        List childOrderedList = childWithSelfList.stream().filter(codeToObjectsMap::containsKey).flatMap(key -> ((List)codeToObjectsMap.get(key)).stream()).collect(Collectors.toList());
        if (!childOrderedList.isEmpty()) {
            for (FormulaSchemeConfigEO formulaSchemeConfig : childOrderedList) {
                List<Object> finalCurrentOrgTree;
                if (!orgId.equals(formulaSchemeConfig.getOrgId())) {
                    List affectedOrgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfig.getOrgId());
                    List affectedOrgCodeList = affectedOrgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
                    finalCurrentOrgTree = currentOrgTree.stream().filter(vo -> affectedOrgCodeList.contains(vo.getOrgId())).collect(Collectors.toList());
                } else {
                    finalCurrentOrgTree = currentOrgTree;
                }
                this.setTableVO(finalCurrentOrgTree, formulaSchemeConfig, formulaSchemeData, isExistCurrency);
            }
        }
    }

    private void processUnitConfigurations(List<FormulaSchemeConfigEO> unitFormulaSchemeConfig, List<NrFormulaSchemeConfigTableVO> currentOrgTree, Map<String, Object> formulaSchemeData, boolean isExistCurrency) {
        Map<String, List<NrFormulaSchemeConfigTableVO>> orgIdToTableVOMap = currentOrgTree.stream().collect(Collectors.groupingBy(FormulaSchemeConfigTableVO::getOrgId));
        for (FormulaSchemeConfigEO config : unitFormulaSchemeConfig) {
            List<NrFormulaSchemeConfigTableVO> affectedList = orgIdToTableVOMap.get(config.getOrgId());
            if (affectedList == null || affectedList.isEmpty()) continue;
            if (isExistCurrency) {
                String currencyCode = config.getAssistDim().split("MD_CURRENCY@")[1];
                affectedList.stream().filter(tableVO -> currencyCode.equals(tableVO.getCurrencyCode())).forEach(tableVO -> this.setFormulaScheme((NrFormulaSchemeConfigTableVO)tableVO, config, formulaSchemeData, "unitSetting"));
                continue;
            }
            affectedList.forEach(tableVO -> this.setFormulaScheme((NrFormulaSchemeConfigTableVO)tableVO, config, formulaSchemeData, "unitSetting"));
        }
    }

    private void setTableVO(List<NrFormulaSchemeConfigTableVO> currentOrgTree, FormulaSchemeConfigEO formulaSchemeConfig, Map<String, Object> formulaSchemeData, boolean isExistCurrency) {
        List filterTableVOList;
        if (currentOrgTree.isEmpty()) {
            return;
        }
        String orgType = formulaSchemeData.get("orgType").toString();
        List orgDOList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfig.getOrgId());
        Predicate<NrFormulaSchemeConfigTableVO> baseFilter = tableVO -> formulaSchemeConfig.getBblx().equals(tableVO.getBblx());
        if (isExistCurrency) {
            String assistDim = formulaSchemeConfig.getAssistDim();
            if (assistDim.contains("MD_CURRENCY@ALL")) {
                filterTableVOList = currentOrgTree.stream().filter(baseFilter).collect(Collectors.toList());
            } else if (!assistDim.contains("MD_CURRENCY")) {
                filterTableVOList = currentOrgTree.stream().filter(baseFilter).filter(tableVO -> Boolean.TRUE.equals(tableVO.getIsBaseCurrency())).collect(Collectors.toList());
            } else {
                String currencyCode = assistDim.split("MD_CURRENCY@")[1];
                filterTableVOList = currentOrgTree.stream().filter(baseFilter).filter(tableVO -> currencyCode.equals(tableVO.getCurrencyCode())).collect(Collectors.toList());
            }
        } else {
            filterTableVOList = currentOrgTree.stream().filter(baseFilter).collect(Collectors.toList());
        }
        if (!filterTableVOList.isEmpty()) {
            for (NrFormulaSchemeConfigTableVO tableVO2 : filterTableVOList) {
                this.setFormulaScheme(tableVO2, formulaSchemeConfig, formulaSchemeData, "strategySetting");
                tableVO2.setStrategyUnit(((OrgDO)orgDOList.get(0)).getName());
            }
        }
    }

    private void setFormulaScheme(NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO, FormulaSchemeConfigEO formulaSchemeConfigEO, Map<String, Object> formulaSchemeMap, String collocationMethod) {
        if ("-".equals(formulaSchemeConfigEO.getBblx())) {
            formulaSchemeConfigTableVO.setId(formulaSchemeConfigEO.getId());
            formulaSchemeConfigTableVO.setBblx("-");
        }
        formulaSchemeConfigTableVO.setFetchSchemeId("");
        formulaSchemeConfigTableVO.setFetchScheme("");
        formulaSchemeConfigTableVO.setFetchAfterSchemeId(null);
        formulaSchemeConfigTableVO.setFetchAfterScheme("");
        formulaSchemeConfigTableVO.setConvertAfterSchemeId(null);
        formulaSchemeConfigTableVO.setConvertAfterScheme("");
        formulaSchemeConfigTableVO.setConvertSystemSchemeId(null);
        formulaSchemeConfigTableVO.setConvertSystemScheme("");
        formulaSchemeConfigTableVO.setPostingSchemeId("");
        formulaSchemeConfigTableVO.setPostingScheme("");
        formulaSchemeConfigTableVO.setCompleteMergeId(null);
        formulaSchemeConfigTableVO.setCompleteMerge("");
        formulaSchemeConfigTableVO.setSameCtrlExtAfterSchemeId(null);
        formulaSchemeConfigTableVO.setSameCtrlExtAfterScheme("");
        formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPerId(null);
        formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPer("");
        Map fetchAndFormulaSchemeMap = (Map)formulaSchemeMap.get("formulaSchemes");
        List convertSystemSchemes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(fetchAndFormulaSchemeMap.get("convertSystemSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        List fetchSchemes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(fetchAndFormulaSchemeMap.get("fetchSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        List formulaSchemes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(fetchAndFormulaSchemeMap.get("formulaSchemes")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        List collocationMethods = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formulaSchemeMap.get("collocationMethods")), (TypeReference)new TypeReference<List<SelectOptionVO>>(){});
        fetchSchemes.forEach(fetchScheme -> {
            if (formulaSchemeConfigEO.getFetchSchemeId() != null && fetchScheme.getValue().equals(formulaSchemeConfigEO.getFetchSchemeId())) {
                formulaSchemeConfigTableVO.setFetchSchemeId(fetchScheme.getValue().toString());
                formulaSchemeConfigTableVO.setFetchScheme(fetchScheme.getLabel());
            }
        });
        convertSystemSchemes.forEach(convertSystemScheme -> {
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertSystemSchemeId())) {
                formulaSchemeConfigTableVO.setConvertSystemSchemeId(formulaSchemeConfigEO.getConvertSystemSchemeId());
                if (formulaSchemeConfigEO.getConvertSystemSchemeId().equals(convertSystemScheme.getValue())) {
                    formulaSchemeConfigTableVO.setConvertSystemScheme(convertSystemScheme.getLabel());
                }
            }
        });
        collocationMethods.forEach(method -> {
            if (collocationMethod.equals(method.getValue().toString())) {
                formulaSchemeConfigTableVO.setCollocationMethodId(method.getValue().toString());
                formulaSchemeConfigTableVO.setCollocationMethod(method.getLabel());
            }
        });
        Map<Object, String> formulaSchemeGroupIdAndLabel = formulaSchemes.stream().collect(Collectors.toMap(SelectOptionVO::getValue, SelectOptionVO::getLabel));
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getFetchAfterSchemeId())) {
            formulaSchemeConfigTableVO.setFetchAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getFetchAfterSchemeId().split(";")));
            formulaSchemeConfigTableVO.setFetchAfterScheme(this.getFormulaSchemeLabelById(formulaSchemeConfigTableVO.getFetchAfterSchemeId(), formulaSchemeGroupIdAndLabel));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertAfterSchemeId())) {
            formulaSchemeConfigTableVO.setConvertAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getConvertAfterSchemeId().split(";")));
            formulaSchemeConfigTableVO.setConvertAfterScheme(this.getFormulaSchemeLabelById(formulaSchemeConfigTableVO.getConvertAfterSchemeId(), formulaSchemeGroupIdAndLabel));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId())) {
            formulaSchemeConfigTableVO.setSameCtrlExtAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId().split(";")));
            formulaSchemeConfigTableVO.setSameCtrlExtAfterScheme(this.getFormulaSchemeLabelById(formulaSchemeConfigTableVO.getSameCtrlExtAfterSchemeId(), formulaSchemeGroupIdAndLabel));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId())) {
            formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPerId(Arrays.asList(formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId().split(";")));
            formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPer(this.getFormulaSchemeLabelById(formulaSchemeConfigTableVO.getUnSaCtDeExtLaYeNumSaPerId(), formulaSchemeGroupIdAndLabel));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getPostingSchemeId())) {
            formulaSchemeConfigTableVO.setPostingSchemeId(formulaSchemeConfigEO.getPostingSchemeId());
            formulaSchemeConfigTableVO.setPostingScheme(formulaSchemeGroupIdAndLabel.get(formulaSchemeConfigEO.getPostingSchemeId()));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getCompleteMergeId())) {
            formulaSchemeConfigTableVO.setCompleteMergeId(Arrays.asList(formulaSchemeConfigEO.getCompleteMergeId().split(";")));
            formulaSchemeConfigTableVO.setCompleteMerge(this.getFormulaSchemeLabelById(formulaSchemeConfigTableVO.getCompleteMergeId(), formulaSchemeGroupIdAndLabel));
        }
        formulaSchemeConfigTableVO.setCreator(formulaSchemeConfigEO.getCreator());
        formulaSchemeConfigTableVO.setCreateTime(formulaSchemeConfigEO.getCreateTime());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveStrategyFormulaSchemeConfig(List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String taskId = formulaSchemeConfigTableVOs.get(0).getTaskId();
        String schemeId = formulaSchemeConfigTableVOs.get(0).getSchemeId();
        String entityId = formulaSchemeConfigTableVOs.get(0).getEntityId();
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId);
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            for (int j = i + 1; j < formulaSchemeConfigTableVOs.size(); ++j) {
                if (isExistCurrency) {
                    if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx()) || !formulaSchemeConfigTableVOs.get(i).getAssistDim().equals(formulaSchemeConfigTableVOs.get(j).getAssistDim())) continue;
                    throw new BusinessRuntimeException("\u7b2c" + (i + 1) + "\u884c\u4e0e\u7b2c" + (j + 1) + "\u884c\u5408\u5e76\u5355\u4f4d\u3001\u62a5\u8868\u7c7b\u578b\u548c\u5e01\u522b\u91cd\u590d\uff01");
                }
                if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx())) continue;
                throw new BusinessRuntimeException("\u7b2c" + (i + 1) + "\u884c\u4e0e\u7b2c" + (j + 1) + "\u884c\u5408\u5e76\u5355\u4f4d\u3001\u62a5\u8868\u7c7b\u578b\u91cd\u590d\uff01");
            }
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            this.schemeConvertVOToEo(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO, floatOrderGenerator, userName, time);
            String assistDimStr = this.getAssistDimStr(formulaSchemeConfigEO.getAssistDim(), formulaSchemeConfigEO.getSchemeId());
            formulaSchemeConfigEO.setAssistDim(assistDimStr);
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        this.formulaSchemeConfigDao.deleteStrategySchemeConfig(schemeId, entityId);
        formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList);
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList)) {
            this.formulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper("batchStrategy", formulaSchemeConfigList, "save");
        }
        String cacheKey = this.buildCacheKey(schemeId, entityId);
        this.formulaSchemeConfigCacheEvict(cacheKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveUnitFormulaSchemeConfig(List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String schemeId = formulaSchemeConfigTableVOs.get(0).getSchemeId();
        String entityId = formulaSchemeConfigTableVOs.get(0).getEntityId();
        List<String> deleteFormulaSchemeConfigIds = formulaSchemeConfigTableVOs.stream().filter(vo -> vo.getCollocationMethodId() != null && "unitSetting".equals(vo.getCollocationMethodId())).map(FormulaSchemeConfigTableVO::getId).collect(Collectors.toList());
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            this.schemeConvertVOToEo(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO, floatOrderGenerator, userName, time);
            String assistDimStr = this.getAssistDimStr(formulaSchemeConfigEO.getAssistDim(), formulaSchemeConfigEO.getSchemeId());
            formulaSchemeConfigEO.setAssistDim(assistDimStr);
            formulaSchemeConfigEO.setBblx("-");
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        if (!CollectionUtils.isEmpty(deleteFormulaSchemeConfigIds)) {
            this.formulaSchemeConfigDao.deleteSelectSchemeConfig(deleteFormulaSchemeConfigIds);
        }
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList))) {
            this.formulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper("batchUnit", formulaSchemeConfigList, "save");
        }
        String cacheKey = this.buildCacheKey(schemeId, entityId);
        this.formulaSchemeConfigCacheEvict(cacheKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void importFormulaSchemeConfig(String selectTab, List<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigTableVOs)) {
            return;
        }
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        String taskId = formulaSchemeConfigTableVOs.get(0).getTaskId();
        String schemeId = formulaSchemeConfigTableVOs.get(0).getSchemeId();
        String entityId = formulaSchemeConfigTableVOs.get(0).getEntityId();
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId);
        List<FormulaSchemeConfigEO> formulaSchemeConfigList = new ArrayList<FormulaSchemeConfigEO>();
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        for (int i = 0; i < formulaSchemeConfigTableVOs.size(); ++i) {
            FormulaSchemeConfigEO formulaSchemeConfigEO = new FormulaSchemeConfigEO();
            for (int j = i + 1; j < formulaSchemeConfigTableVOs.size(); ++j) {
                if ("batchStrategy".equals(selectTab)) {
                    if (isExistCurrency) {
                        if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx()) || !formulaSchemeConfigTableVOs.get(i).getAssistDim().equals(formulaSchemeConfigTableVOs.get(j).getAssistDim())) continue;
                        throw new BusinessRuntimeException("\u7b2c" + formulaSchemeConfigTableVOs.get(i).getIndex() + "\u884c\u4e0e\u7b2c" + formulaSchemeConfigTableVOs.get(j).getIndex() + "\u884c\u5408\u5e76\u5355\u4f4d\u3001\u62a5\u8868\u7c7b\u578b\u548c\u5e01\u522b\u91cd\u590d\uff01");
                    }
                    if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx())) continue;
                    throw new BusinessRuntimeException("\u7b2c" + formulaSchemeConfigTableVOs.get(i).getIndex() + "\u884c\u4e0e\u7b2c" + formulaSchemeConfigTableVOs.get(j).getIndex() + "\u884c\u5408\u5e76\u5355\u4f4d\u3001\u62a5\u8868\u7c7b\u578b\u91cd\u590d\uff01");
                }
                if (isExistCurrency) {
                    if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx()) || !formulaSchemeConfigTableVOs.get(i).getAssistDim().equals(formulaSchemeConfigTableVOs.get(j).getAssistDim())) continue;
                    throw new BusinessRuntimeException("\u7b2c" + formulaSchemeConfigTableVOs.get(i).getIndex() + "\u884c\u4e0e\u7b2c" + formulaSchemeConfigTableVOs.get(j).getIndex() + "\u884c\u5355\u4f4d\u548c\u5e01\u522b\u91cd\u590d\uff01");
                }
                if (!formulaSchemeConfigTableVOs.get(i).getOrgId().equals(formulaSchemeConfigTableVOs.get(j).getOrgId()) || !formulaSchemeConfigTableVOs.get(i).getBblx().equals(formulaSchemeConfigTableVOs.get(j).getBblx())) continue;
                throw new BusinessRuntimeException("\u7b2c" + formulaSchemeConfigTableVOs.get(i).getIndex() + "\u884c\u4e0e\u7b2c" + formulaSchemeConfigTableVOs.get(j).getIndex() + "\u884c\u5355\u4f4d\u91cd\u590d\uff01");
            }
            BeanUtils.copyProperties(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO);
            this.schemeConvertVOToEo(formulaSchemeConfigTableVOs.get(i), formulaSchemeConfigEO, floatOrderGenerator, userName, time);
            String assistDimStr = this.getAssistDimStr(formulaSchemeConfigEO.getAssistDim(), formulaSchemeConfigEO.getSchemeId());
            formulaSchemeConfigEO.setAssistDim(assistDimStr);
            formulaSchemeConfigList.add(formulaSchemeConfigEO);
        }
        List<String> deleteFormulaSchemeConfigIds = this.getImportData(formulaSchemeConfigList);
        if (!CollectionUtils.isEmpty(deleteFormulaSchemeConfigIds)) {
            this.formulaSchemeConfigDao.deleteSelectSchemeConfig(deleteFormulaSchemeConfigIds);
        }
        if (!CollectionUtils.isEmpty(formulaSchemeConfigList = this.filterBlankRow(formulaSchemeConfigList))) {
            this.formulaSchemeConfigDao.addBatch(formulaSchemeConfigList);
            this.writeBatchSaveLogHelper(selectTab, formulaSchemeConfigList, "import");
        }
        String cacheKey = this.buildCacheKey(schemeId, entityId);
        this.formulaSchemeConfigCacheEvict(cacheKey);
    }

    private List<FormulaSchemeConfigEO> filterBlankRow(List<FormulaSchemeConfigEO> formulaSchemeConfigList) {
        return formulaSchemeConfigList.stream().filter(vo -> {
            boolean hasStringValue = !StringUtils.isEmpty((String)vo.getPostingSchemeId()) || !StringUtils.isEmpty((String)vo.getFetchSchemeId()) || !StringUtils.isEmpty((String)vo.getConvertSystemSchemeId());
            boolean hasListValue = Stream.of(vo.getFetchAfterSchemeId(), vo.getCompleteMergeId(), vo.getConvertAfterSchemeId(), vo.getUnSaCtDeExtLaYeNumSaPerId(), vo.getSameCtrlExtAfterSchemeId()).anyMatch(list -> list != null && !list.isEmpty());
            return hasStringValue || hasListValue;
        }).collect(Collectors.toList());
    }

    @Override
    public void recoverDefaultStrategy(NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO) {
        String schemeId = formulaSchemeConfigTableVO.getSchemeId();
        String entityId = formulaSchemeConfigTableVO.getEntityId();
        ArrayList<String> deletedList = new ArrayList<String>();
        deletedList.add(formulaSchemeConfigTableVO.getId());
        this.formulaSchemeConfigDao.deleteSelectSchemeConfig(deletedList);
        this.writeBatchDeleteLogHelper(deletedList);
        String cacheKey = this.buildCacheKey(schemeId, entityId);
        this.formulaSchemeConfigCacheEvict(cacheKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSelectSchemeConfig(String schemeId, String entityId, List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            this.formulaSchemeConfigDao.deleteSelectSchemeConfig(ids);
            this.writeBatchDeleteLogHelper(ids);
            String cacheKey = this.buildCacheKey(schemeId, entityId);
            this.formulaSchemeConfigCacheEvict(cacheKey);
        }
    }

    private void schemeConvertVOToEo(NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO, FormulaSchemeConfigEO formulaSchemeConfigEO, FloatOrderGenerator floatOrderGenerator, String userName, Date time) {
        formulaSchemeConfigEO.setFetchAfterSchemeId(this.schemeConvertListToStr(formulaSchemeConfigTableVO.getFetchAfterSchemeId()));
        formulaSchemeConfigEO.setConvertAfterSchemeId(this.schemeConvertListToStr(formulaSchemeConfigTableVO.getConvertAfterSchemeId()));
        formulaSchemeConfigEO.setCompleteMergeId(this.schemeConvertListToStr(formulaSchemeConfigTableVO.getCompleteMergeId()));
        formulaSchemeConfigEO.setUnSaCtDeExtLaYeNumSaPerId(this.schemeConvertListToStr(formulaSchemeConfigTableVO.getUnSaCtDeExtLaYeNumSaPerId()));
        formulaSchemeConfigEO.setSameCtrlExtAfterSchemeId(this.schemeConvertListToStr(formulaSchemeConfigTableVO.getSameCtrlExtAfterSchemeId()));
        formulaSchemeConfigEO.setSortOrder(Double.valueOf(floatOrderGenerator.next()));
        if (StringUtils.isEmpty((String)formulaSchemeConfigEO.getId())) {
            formulaSchemeConfigEO.setId(UUIDUtils.newUUIDStr());
            formulaSchemeConfigEO.setCreateTime(time);
            formulaSchemeConfigEO.setCreator(userName);
        } else {
            formulaSchemeConfigEO.setUpdateTime(time);
            formulaSchemeConfigEO.setUpdator(userName);
        }
    }

    private String schemeConvertListToStr(List<String> schemaIds) {
        String schemaStr = "";
        if (!CollectionUtils.isEmpty(schemaIds)) {
            for (String schemaId : schemaIds) {
                schemaStr = schemaStr + schemaId + ";";
            }
            schemaStr = schemaStr.substring(0, schemaStr.length() - 1);
        }
        return schemaStr;
    }

    @Override
    public List<NrFormulaSchemeConfigTableVO> getStrategyTabSchemeConfig(String schemeId, String entityId) {
        ArrayList<NrFormulaSchemeConfigTableVO> formulaSchemeConfigTableVOs = new ArrayList<NrFormulaSchemeConfigTableVO>();
        Map<String, Object> formulaSchemeData = this.getFormulaSchemesBySchemeId(schemeId, entityId);
        String orgType = formulaSchemeData.get("orgType").toString();
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        HashMap orgDOMap = new HashMap();
        List<Object> allFormulaSchemeConfig = this.getFormulaSchemeConfig(schemeId, entityId);
        if (!allFormulaSchemeConfig.isEmpty()) {
            allFormulaSchemeConfig = allFormulaSchemeConfig.stream().filter(eo -> !"-".equals(eo.getBblx())).collect(Collectors.toList());
        }
        if (allFormulaSchemeConfig.isEmpty()) {
            return formulaSchemeConfigTableVOs;
        }
        allFormulaSchemeConfig.forEach(formulaSchemeConfigEO -> {
            NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new NrFormulaSchemeConfigTableVO();
            BeanUtils.copyProperties(formulaSchemeConfigEO, formulaSchemeConfigTableVO);
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getFetchAfterSchemeId())) {
                formulaSchemeConfigTableVO.setFetchAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getFetchAfterSchemeId().split(";")));
            }
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertAfterSchemeId())) {
                formulaSchemeConfigTableVO.setConvertAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getConvertAfterSchemeId().split(";")));
            }
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertSystemSchemeId())) {
                formulaSchemeConfigTableVO.setConvertSystemSchemeId(formulaSchemeConfigEO.getConvertSystemSchemeId());
            }
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getCompleteMergeId())) {
                formulaSchemeConfigTableVO.setCompleteMergeId(Arrays.asList(formulaSchemeConfigEO.getCompleteMergeId().split(";")));
            }
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId())) {
                formulaSchemeConfigTableVO.setUnSaCtDeExtLaYeNumSaPerId(Arrays.asList(formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId().split(";")));
            }
            if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId())) {
                formulaSchemeConfigTableVO.setSameCtrlExtAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId().split(";")));
            }
            OrgDO orgDO = null;
            if (orgDOMap.containsKey(formulaSchemeConfigEO.getOrgId())) {
                orgDO = (OrgDO)orgDOMap.get(formulaSchemeConfigEO.getOrgId());
            } else {
                orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfigEO.getOrgId()).get(0);
                orgDOMap.put(formulaSchemeConfigEO.getOrgId(), orgDO);
            }
            if (orgDO != null) {
                formulaSchemeConfigTableVO.setOrgUnit(orgDO);
                if (!StringUtils.isEmpty((String)formulaSchemeConfigEO.getAssistDim())) {
                    String[] assistDimArr = formulaSchemeConfigEO.getAssistDim().split("\\|");
                    String currency = "";
                    for (int i = 0; i < assistDimArr.length; ++i) {
                        if (!assistDimArr[i].startsWith("MD_CURRENCY")) continue;
                        currency = assistDimArr[i].split("@")[1];
                    }
                    if (!StringUtils.isEmpty((String)currency) && !"ALL".equals(currency)) {
                        BaseDataVO baseData = baseDataCenterTool.queryBaseDataVoByCode("MD_CURRENCY", currency);
                        formulaSchemeConfigTableVO.setCurrency(baseData);
                    }
                } else if (StringUtils.isEmpty((String)formulaSchemeConfigEO.getAssistDim())) {
                    formulaSchemeConfigTableVO.setAssistDim("");
                }
                formulaSchemeConfigTableVOs.add(formulaSchemeConfigTableVO);
            }
        });
        return formulaSchemeConfigTableVOs;
    }

    @Override
    public List<String> getCurrencyByOrgId(String schemeId, String orgId, String entityId) {
        ArrayList<String> currencyList = new ArrayList<String>();
        Map<String, Object> formulaSchemeData = this.getFormulaSchemesBySchemeId(schemeId, entityId);
        String orgType = formulaSchemeData.get("orgType").toString();
        if (!StringUtils.isEmpty((String)orgType)) {
            OrgDO orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId).get(0);
            return (List)orgDO.get((Object)"currencyids");
        }
        return currencyList;
    }

    private Map<String, Object> getFormulaSchemeBySchemeId(String schemeId, String entityId) {
        String referTableID;
        TableModelDefine tableModelDefineById;
        HashMap<String, Object> formulaSchemeMap = new HashMap<String, Object>(16);
        ArrayList fetchSchemes = new ArrayList();
        ArrayList formulaSchemes = new ArrayList();
        List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(schemeId);
        formulaSchemeDefines.forEach(formulaSchemeDefine -> {
            if (FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.equals((Object)formulaSchemeDefine.getFormulaSchemeType())) {
                formulaSchemes.add(new SelectOptionVO(formulaSchemeDefine.getKey(), formulaSchemeDefine.getTitle()));
            } else {
                fetchSchemes.add(new SelectOptionVO(formulaSchemeDefine.getKey(), formulaSchemeDefine.getTitle()));
            }
        });
        List reportTypes = new ArrayList();
        String dw = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndEntityId(schemeId, entityId);
        TableModelDefine tableDefineDw = this.entityMetaService.getTableModel(dw);
        Assert.isNotNull((Object)String.format("\u6839\u636e\u53e3\u5f84\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", dw));
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableDefineDw.getID(), "BBLX");
        if (Objects.nonNull(columnModelDefine) && Objects.nonNull(tableModelDefineById = this.dataModelService.getTableModelDefineById(referTableID = columnModelDefine.getReferTableID()))) {
            reportTypes = GcBaseDataCenterTool.getInstance().queryBasedataTree(tableModelDefineById.getName());
        }
        ArrayList<SelectOptionVO> convertSystemSchemes = new ArrayList<SelectOptionVO>(2);
        convertSystemSchemes.add(new SelectOptionVO("true", "\u662f"));
        convertSystemSchemes.add(new SelectOptionVO("false", "\u5426"));
        formulaSchemeMap.put("fetchSchemes", fetchSchemes);
        formulaSchemeMap.put("formulaSchemes", formulaSchemes);
        formulaSchemeMap.put("reportTypes", reportTypes);
        formulaSchemeMap.put("convertSystemSchemes", convertSystemSchemes);
        return formulaSchemeMap;
    }

    @Override
    public Map<String, Object> getFormulaSchemesBySchemeId(String schemeId) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        return this.getFormulaSchemesBySchemeId(schemeId, formSchemeDefine.getDw());
    }

    @Override
    public Map<String, Object> getFormulaSchemesBySchemeId(String schemeId, String srcEntityId) {
        FormSchemeDefine formSchemeDefine;
        String dims;
        HashMap<String, Object> formulaSchemeData = new HashMap<String, Object>(16);
        HashMap iEntityRowMaps = new HashMap();
        ArrayList<SelectOptionVO> tableDefineList = new ArrayList<SelectOptionVO>();
        Map<String, Object> formulaSchemeMap = this.getFormulaSchemeBySchemeId(schemeId, srcEntityId);
        ArrayList<SelectOptionVO> collocationMethods = new ArrayList<SelectOptionVO>();
        TableModelDefine tableDefineDw = null;
        String dw = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndEntityId(schemeId, srcEntityId);
        tableDefineDw = this.entityMetaService.getTableModel(dw);
        if (tableDefineDw != null) {
            formulaSchemeData.put("orgType", tableDefineDw.getName());
        }
        if (StringUtils.isNotEmpty((String)(dims = (formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId)).getDims()))) {
            String[] idArray;
            for (String entityId : idArray = dims.split(";")) {
                TableModelDefine tableDefine = this.entityMetaService.getTableModel(entityId);
                if (tableDefine == null || !"MD_CURRENCY".equals(tableDefine.getCode())) continue;
                SelectOptionVO tableDefineSelectOptionVO = new SelectOptionVO();
                tableDefineSelectOptionVO.setValue(tableDefine.getCode());
                tableDefineSelectOptionVO.setLabel(tableDefine.getTitle());
                tableDefineList.add(tableDefineSelectOptionVO);
                List baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO(tableDefine.getName());
                List iEntityRowSelectOptionList = baseDatas.stream().map(entityData -> {
                    SelectOptionVO iEntityRowSelectOptionVO = new SelectOptionVO();
                    iEntityRowSelectOptionVO.setValue(entityData.getCode());
                    iEntityRowSelectOptionVO.setLabel(entityData.getTitle());
                    return iEntityRowSelectOptionVO;
                }).collect(Collectors.toList());
                iEntityRowMaps.put(tableDefine.getCode(), iEntityRowSelectOptionList);
            }
        }
        String orgVer = "";
        if (!Objects.isNull(formulaSchemeData.get("orgType"))) {
            orgVer = DateUtils.format((Date)FormulaSchemeConfigUtils.getOrgVersion((String)formulaSchemeData.get("orgType").toString()), (String)"yyyyMMdd");
        }
        collocationMethods.add(new SelectOptionVO("strategySetting", "\u7b56\u7565\u8bbe\u7f6e"));
        collocationMethods.add(new SelectOptionVO("unitSetting", "\u5355\u4f4d\u8bbe\u7f6e"));
        formulaSchemeData.put("orgVer", orgVer);
        formulaSchemeData.put("formulaSchemes", formulaSchemeMap);
        formulaSchemeData.put("tableDefines", tableDefineList);
        formulaSchemeData.put("iEntityRows", iEntityRowMaps);
        formulaSchemeData.put("collocationMethods", collocationMethods);
        return formulaSchemeData;
    }

    private List<NrFormulaSchemeConfigTableVO> getCurrentOrgTree(List<OrgDO> list, String schemeId) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(formSchemeDefine.getTaskKey());
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        List<Object> tree = new ArrayList();
        tree = isExistCurrency ? list.stream().flatMap(orgDO -> {
            List currencies = (List)orgDO.get((Object)"currencyids");
            String baseCurrency = (String)orgDO.get((Object)"currencyid");
            String bblx = (String)orgDO.get((Object)"bblx");
            return currencies.stream().distinct().map(currency -> {
                BaseDataVO baseData = baseDataCenterTool.queryBaseDataVoByCode("MD_CURRENCY", currency);
                if (baseData == null) {
                    this.logger.error("\u5355\u4f4d\u3010{}\u3011\u6839\u636e\u5e01\u79cd\u6807\u8bc6\u3010{}\u3011\u83b7\u53d6\u62a5\u8868\u5e01\u79cd\u5931\u8d25", (Object)orgDO.getCode(), currency);
                    return null;
                }
                return new NrFormulaSchemeConfigTableVO(orgDO, baseData, Boolean.valueOf(baseCurrency.equals(currency)), bblx);
            }).filter(Objects::nonNull);
        }).collect(Collectors.toList()) : list.stream().map(orgDO -> new NrFormulaSchemeConfigTableVO(orgDO, null, null, (String)orgDO.get((Object)"bblx"))).collect(Collectors.toList());
        return tree;
    }

    public NrSchemeConfigDTO getSchemeByOrgId(String schemeId, String entityId, String orgId, Map<String, String> assistDim) {
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = this.getFormulaSchemeConfig(schemeId, entityId, orgId, assistDim, false);
        if (formulaSchemeConfigDTO.getTaskId() == null) {
            return new NrSchemeConfigDTO();
        }
        NrSchemeConfigDTO nrScheme = new NrSchemeConfigDTO();
        BeanUtils.copyProperties(formulaSchemeConfigDTO, nrScheme);
        return nrScheme;
    }

    @Override
    public FormulaSchemeConfigDTO getSchemeConfigByOrgAndAssistDim(String schemeId, String orgId, Map<String, String> assistDim) {
        return this.getSchemeConfigByOrgAndAssistDimWithCache(schemeId, orgId, assistDim);
    }

    @Override
    public FormulaSchemeConfigDTO getSchemeConfigByOrgAndAssistDimWithCache(String schemeId, String orgId, Map<String, String> assistDim) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", schemeId));
        String entityId = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndCtx(schemeId);
        List<Object> allSchemes = this.getFormulaSchemeConfig(schemeId, entityId);
        if (NrFormulaSchemeConfigUtils.enableTaskMultiOrg(schemeId)) {
            allSchemes = allSchemes.stream().filter(item -> item.getEntityId().equals(entityId)).collect(Collectors.toList());
        }
        Map<String, String> assistDimMapping = this.getAssistDim(schemeId, entityId, assistDim);
        String assistDimStr = assistDimMapping.get("assistDim");
        String orgType = assistDimMapping.get("orgType");
        OrgDO orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId).get(0);
        List unitTabSchemes = allSchemes.stream().filter(item -> item.getBblx().equals("-") && item.getOrgId().equals(orgId) && item.getAssistDim().equals(assistDimStr)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(unitTabSchemes)) {
            return this.getFormulaSchemeConfigSetting((FormulaSchemeConfigEO)unitTabSchemes.get(0));
        }
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = new FormulaSchemeConfigDTO();
        final String parents = (String)orgDO.get((Object)"parents");
        List parentList = Arrays.stream(parents.split("/")).collect(Collectors.toList());
        HashSet parentIds = new HashSet(parentList);
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(formSchemeDefine.getTaskKey());
        String gcAdjType = null;
        if (NrFormulaSchemeConfigUtils.isExisAdjType(formSchemeDefine.getTaskKey())) {
            gcAdjType = assistDimStr.split("\\|")[0];
        }
        String finalGcAdjType = gcAdjType;
        List stageTabSchemes = allSchemes.stream().filter(item -> parentIds.contains(item.getOrgId()) && !item.getBblx().equals("-") && (StringUtils.isEmpty((String)finalGcAdjType) || item.getAssistDim().contains(finalGcAdjType))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(stageTabSchemes)) {
            if (stageTabSchemes.size() > 1) {
                stageTabSchemes.sort(new Comparator<FormulaSchemeConfigEO>(){

                    @Override
                    public int compare(FormulaSchemeConfigEO o1, FormulaSchemeConfigEO o2) {
                        return parents.indexOf(o1.getOrgId()) > parents.indexOf(o2.getOrgId()) ? 1 : -1;
                    }
                });
            }
            for (int i = stageTabSchemes.size() - 1; i >= 0; --i) {
                String bblx = (String)orgDO.get((Object)"bblx");
                if (bblx.equals(((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getBblx())) {
                    if (StringUtils.isNotEmpty((String)((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim()) && ((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim().contains("MD_CURRENCY") && assistDimStr.equals(((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim())) {
                        formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting((FormulaSchemeConfigEO)stageTabSchemes.get(i));
                        break;
                    }
                    if (isExistCurrency) {
                        if (StringUtils.isNotEmpty((String)((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim()) && ((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim().contains("MD_CURRENCY@ALL")) {
                            formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting((FormulaSchemeConfigEO)stageTabSchemes.get(i));
                        } else {
                            String baseCurrency = (String)orgDO.get((Object)"currencyid");
                            formulaSchemeConfigDTO = this.getFormulaSchemeConfigByCurrencyId(baseCurrency, assistDim, (FormulaSchemeConfigEO)stageTabSchemes.get(i));
                        }
                    } else if (assistDimStr.equals(((FormulaSchemeConfigEO)stageTabSchemes.get(i)).getAssistDim())) {
                        formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting((FormulaSchemeConfigEO)stageTabSchemes.get(i));
                    }
                }
                if (formulaSchemeConfigDTO.getTaskId() != null) break;
            }
        }
        return formulaSchemeConfigDTO;
    }

    @Override
    public FormulaSchemeConfigDTO getConvertSchemeConfig(String schemeId, String orgId, Map<String, String> assistDim) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", schemeId));
        String entityId = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndCtx(schemeId);
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = this.getFormulaSchemeConfig(schemeId, entityId, orgId, assistDim, true);
        if (Objects.isNull(formulaSchemeConfigDTO) || StringUtils.isEmpty((String)formulaSchemeConfigDTO.getSchemeId())) {
            throw new BusinessRuntimeException("\u672a\u53d6\u5230\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u3002");
        }
        return formulaSchemeConfigDTO;
    }

    private FormulaSchemeConfigDTO getFormulaSchemeConfig(String schemeId, String entityId, String orgId, Map<String, String> assistDim, boolean isConvertConfig) {
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = new FormulaSchemeConfigDTO();
        Map<String, String> assistDimMapping = this.getAssistDim(schemeId, entityId, assistDim);
        String assistDimStr = assistDimMapping.get("assistDim");
        String orgType = assistDimMapping.get("orgType");
        OrgDO orgDO = (OrgDO)FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)orgId).get(0);
        FormulaSchemeConfigEO formulaSchemeConfigEO = this.formulaSchemeConfigDao.getShowTableByOrgIdOrBblx(orgId, entityId, assistDimStr, schemeId, "-");
        formulaSchemeConfigDTO = !Objects.isNull(formulaSchemeConfigEO) ? this.getFormulaSchemeConfigSetting(formulaSchemeConfigEO) : this.getFormulaSchemeConfigByParentIds(schemeId, entityId, assistDim, assistDimStr, orgDO, isConvertConfig);
        return formulaSchemeConfigDTO;
    }

    private FormulaSchemeConfigDTO getFormulaSchemeConfigByParentIds(String schemeId, String entityId, Map<String, String> assistDim, String assistDimStr, OrgDO orgDO, boolean isConvertConfig) {
        List<FormulaSchemeConfigEO> formulaSchemeConfigEOs;
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = new FormulaSchemeConfigDTO();
        final String parents = (String)orgDO.get((Object)"parents");
        List<String> parentIds = Arrays.stream(parents.split("/")).collect(Collectors.toList());
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(formSchemeDefine.getTaskKey());
        String gcAdjType = null;
        if (NrFormulaSchemeConfigUtils.isExisAdjType(formSchemeDefine.getTaskKey())) {
            gcAdjType = assistDimStr.split("\\|")[0];
        }
        if (!CollectionUtils.isEmpty(formulaSchemeConfigEOs = this.formulaSchemeConfigDao.getFormulaSchemeConfigsByParents(parentIds, gcAdjType, schemeId, entityId))) {
            if (formulaSchemeConfigEOs.size() > 1) {
                formulaSchemeConfigEOs.sort(new Comparator<FormulaSchemeConfigEO>(){

                    @Override
                    public int compare(FormulaSchemeConfigEO o1, FormulaSchemeConfigEO o2) {
                        return parents.indexOf(o1.getOrgId()) > parents.indexOf(o2.getOrgId()) ? 1 : -1;
                    }
                });
            }
            for (int i = formulaSchemeConfigEOs.size() - 1; i >= 0; --i) {
                String bblx = (String)orgDO.get((Object)"bblx");
                if (bblx.equals(formulaSchemeConfigEOs.get(i).getBblx())) {
                    if (StringUtils.isNotEmpty((String)formulaSchemeConfigEOs.get(i).getAssistDim()) && formulaSchemeConfigEOs.get(i).getAssistDim().indexOf("MD_CURRENCY") > -1 && assistDimStr.equals(formulaSchemeConfigEOs.get(i).getAssistDim())) {
                        formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting(formulaSchemeConfigEOs.get(i));
                        break;
                    }
                    if (isExistCurrency) {
                        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEOs.get(i).getAssistDim()) && formulaSchemeConfigEOs.get(i).getAssistDim().contains("MD_CURRENCY@ALL")) {
                            formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting(formulaSchemeConfigEOs.get(i));
                        } else {
                            String baseCurrency = (String)orgDO.get((Object)"currencyid");
                            formulaSchemeConfigDTO = this.getFormulaSchemeConfigByCurrencyId(baseCurrency, assistDim, formulaSchemeConfigEOs.get(i));
                        }
                    } else if (assistDimStr.equals(formulaSchemeConfigEOs.get(i).getAssistDim())) {
                        formulaSchemeConfigDTO = this.getFormulaSchemeConfigSetting(formulaSchemeConfigEOs.get(i));
                    }
                }
                if (formulaSchemeConfigDTO.getTaskId() != null) break;
            }
        }
        return formulaSchemeConfigDTO;
    }

    private FormulaSchemeConfigDTO getFormulaSchemeConfigByCurrencyId(Object currencyId, Map<String, String> assistDim, FormulaSchemeConfigEO formulaSchemeConfigEO) {
        if (currencyId != null && currencyId.equals(assistDim.get("MD_CURRENCY")) && (StringUtils.isEmpty((String)formulaSchemeConfigEO.getAssistDim()) || !formulaSchemeConfigEO.getAssistDim().contains("MD_CURRENCY"))) {
            return this.getFormulaSchemeConfigSetting(formulaSchemeConfigEO);
        }
        return new FormulaSchemeConfigDTO();
    }

    private FormulaSchemeConfigDTO getFormulaSchemeConfigSetting(FormulaSchemeConfigEO formulaSchemeConfigEO) {
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = new FormulaSchemeConfigDTO();
        BeanUtils.copyProperties(formulaSchemeConfigEO, formulaSchemeConfigDTO);
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getFetchAfterSchemeId())) {
            formulaSchemeConfigDTO.setFetchAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getFetchAfterSchemeId().split(";")));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertAfterSchemeId())) {
            formulaSchemeConfigDTO.setConvertAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getConvertAfterSchemeId().split(";")));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getConvertSystemSchemeId())) {
            formulaSchemeConfigDTO.setConvertSystemSchemeId(formulaSchemeConfigEO.getConvertSystemSchemeId());
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getCompleteMergeId())) {
            formulaSchemeConfigDTO.setCompleteMergeId(Arrays.asList(formulaSchemeConfigEO.getCompleteMergeId().split(";")));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId())) {
            formulaSchemeConfigDTO.setUnSaCtDeExtLaYeNumSaPerId(Arrays.asList(formulaSchemeConfigEO.getUnSaCtDeExtLaYeNumSaPerId().split(";")));
        }
        if (StringUtils.isNotEmpty((String)formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId())) {
            formulaSchemeConfigDTO.setSameCtrlExtAfterSchemeId(Arrays.asList(formulaSchemeConfigEO.getSameCtrlExtAfterSchemeId().split(";")));
        }
        return formulaSchemeConfigDTO;
    }

    private Map<String, String> getAssistDim(String schemeId, String entityId, Map<String, String> assistDim) {
        boolean isExistCurrency;
        String orgType = "";
        StringBuilder assistDimStr = new StringBuilder();
        String dw = NrFormulaSchemeConfigUtils.getEntityIdBySchemeIdAndEntityId(schemeId, entityId);
        TableModelDefine tableDefineDw = this.entityMetaService.getTableModel(dw);
        if (tableDefineDw != null) {
            orgType = tableDefineDw.getName();
        }
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeId);
        HashMap<String, String> adjTypeMap = new HashMap<String, String>();
        NrFormulaSchemeConfigUtils.dimensionMapAdjTypeValue(formSchemeDefine.getTaskKey(), adjTypeMap);
        if (!adjTypeMap.isEmpty()) {
            for (Map.Entry entry : adjTypeMap.entrySet()) {
                assistDimStr.append((String)entry.getKey()).append("@").append((String)entry.getValue());
            }
        }
        if (isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(formSchemeDefine.getTaskKey())) {
            if (!adjTypeMap.isEmpty()) {
                assistDimStr.append("|");
            }
            assistDimStr.append("MD_CURRENCY").append("@").append(assistDim.get("MD_CURRENCY"));
        }
        HashMap<String, String> assistDimMapping = new HashMap<String, String>(16);
        if (assistDimStr.length() <= 0) {
            assistDimMapping.put("assistDim", "-");
        } else {
            assistDimMapping.put("assistDim", assistDimStr.toString());
        }
        assistDimMapping.put("orgType", orgType);
        return assistDimMapping;
    }

    @Override
    public Map<String, List<NrFormulaSchemeConfigTableVO>> queryTabSelectOrgIds(String schemeId, String entityId, List<String> orgIds, Boolean isSystemResource) {
        HashMap<String, List<NrFormulaSchemeConfigTableVO>> batchTableSelect = new HashMap<String, List<NrFormulaSchemeConfigTableVO>>();
        ArrayList<NrFormulaSchemeConfigTableVO> formulaSchemeConfigBatchStrategyList = new ArrayList<NrFormulaSchemeConfigTableVO>();
        ArrayList<NrFormulaSchemeConfigTableVO> formulaSchemeConfigBatchUnitList = new ArrayList<NrFormulaSchemeConfigTableVO>();
        Map<String, Object> formulaSchemeData = this.getFormulaSchemesBySchemeId(schemeId, entityId);
        Map formulaSchemeMap = (Map)formulaSchemeData.get("formulaSchemes");
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        HashMap<String, OrgDO> orgV0Map = new HashMap<String, OrgDO>();
        String orgType = formulaSchemeData.get("orgType").toString();
        List<Object> formulaSchemeConfigEOList = new ArrayList();
        formulaSchemeConfigEOList = isSystemResource != false ? this.formulaSchemeConfigDao.getAllFormulaSchemeConfigs(schemeId, entityId) : this.formulaSchemeConfigDao.getFormulaSchemeConfigsByOrgIds(orgIds, schemeId, entityId);
        List reportTypes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formulaSchemeMap.get("reportTypes")), (TypeReference)new TypeReference<List<BaseDataVO>>(){});
        if (!CollectionUtils.isEmpty(formulaSchemeConfigEOList)) {
            for (FormulaSchemeConfigEO formulaSchemeConfigEO : formulaSchemeConfigEOList) {
                OrgDO orgDO = null;
                if (orgV0Map.containsKey(formulaSchemeConfigEO.getOrgId())) {
                    orgDO = (OrgDO)orgV0Map.get(formulaSchemeConfigEO.getOrgId());
                } else {
                    List orgList = FormulaSchemeConfigUtils.queryOrgDO((String)orgType, (String)formulaSchemeConfigEO.getOrgId());
                    if (CollectionUtils.isEmpty((Collection)orgList)) continue;
                    orgDO = (OrgDO)orgList.get(0);
                    orgV0Map.put(formulaSchemeConfigEO.getOrgId(), orgDO);
                }
                NrFormulaSchemeConfigTableVO formulaSchemeConfigTableVO = new NrFormulaSchemeConfigTableVO();
                formulaSchemeConfigTableVO.setOrgUnit(orgDO);
                BeanUtils.copyProperties(formulaSchemeConfigEO, formulaSchemeConfigTableVO);
                formulaSchemeConfigTableVO.setId(null);
                if (!StringUtils.isEmpty((String)formulaSchemeConfigEO.getAssistDim())) {
                    String[] assistDimArr = formulaSchemeConfigEO.getAssistDim().split("\\|");
                    String currency = "";
                    for (int i = 0; i < assistDimArr.length; ++i) {
                        if (!assistDimArr[i].startsWith("MD_CURRENCY")) continue;
                        currency = assistDimArr[i].split("@")[1];
                    }
                    BaseDataVO baseData = null;
                    if (!StringUtils.isEmpty((String)currency) && !"ALL".equals(currency)) {
                        baseData = baseDataCenterTool.queryBaseDataVoByCode("MD_CURRENCY", currency);
                        formulaSchemeConfigTableVO.setCurrency(baseData);
                    }
                }
                this.setFormulaScheme(formulaSchemeConfigTableVO, formulaSchemeConfigEO, formulaSchemeData, "");
                if (isSystemResource.booleanValue()) {
                    formulaSchemeConfigTableVO.setBblx(formulaSchemeConfigEO.getBblx());
                } else {
                    reportTypes.forEach(reportType -> {
                        if (orgFormulaSchemeConfigEO.getBblx() != null && reportType.getCode().equals(orgFormulaSchemeConfigEO.getBblx())) {
                            formulaSchemeConfigTableVO.setBblx(reportType.getTitle());
                        }
                    });
                }
                formulaSchemeConfigTableVO.setEntityId(entityId);
                if (!"-".equals(formulaSchemeConfigEO.getBblx())) {
                    formulaSchemeConfigBatchStrategyList.add(formulaSchemeConfigTableVO);
                    continue;
                }
                formulaSchemeConfigBatchUnitList.add(formulaSchemeConfigTableVO);
            }
        }
        batchTableSelect.put("batchStrategy", formulaSchemeConfigBatchStrategyList);
        batchTableSelect.put("batchUnit", formulaSchemeConfigBatchUnitList);
        return batchTableSelect;
    }

    @Override
    public int getFormulaSchemeConfigByFetchSchemeId(String fetchSchemeId) {
        return this.formulaSchemeConfigDao.getFormulaSchemeConfigByFetchSchemeId(fetchSchemeId);
    }

    @Override
    public List<FormulaSchemeConfigEO> getByTaskId(String taskId) {
        return this.formulaSchemeConfigDao.queryAllByTaskId(taskId);
    }

    private List<String> getImportData(List<FormulaSchemeConfigEO> formulaSchemeConfigList) {
        FormulaSchemeConfigEO schemeConfig;
        String userName = NpContextHolder.getContext().getUser().getName();
        Date time = new Date();
        ArrayList<String> deleteImportIds = new ArrayList<String>();
        HashMap<String, FormulaSchemeConfigEO> importDeleteMap = new HashMap<String, FormulaSchemeConfigEO>();
        List<String> orgIds = formulaSchemeConfigList.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toList());
        List<FormulaSchemeConfigEO> schemeConfigList = this.formulaSchemeConfigDao.getFormulaSchemeConfigsByOrgIds(orgIds, (schemeConfig = formulaSchemeConfigList.get(0)).getSchemeId(), schemeConfig.getEntityId());
        if (!CollectionUtils.isEmpty(schemeConfigList)) {
            for (FormulaSchemeConfigEO formulaSchemeConfig : schemeConfigList) {
                importDeleteMap.put(formulaSchemeConfig.getAssistDim() + formulaSchemeConfig.getOrgId() + formulaSchemeConfig.getSchemeId() + formulaSchemeConfig.getBblx(), formulaSchemeConfig);
            }
        }
        for (FormulaSchemeConfigEO schemeConfigItem : formulaSchemeConfigList) {
            String uniqueKey = schemeConfigItem.getAssistDim() + schemeConfigItem.getOrgId() + schemeConfigItem.getSchemeId() + schemeConfigItem.getBblx();
            FormulaSchemeConfigEO item = (FormulaSchemeConfigEO)importDeleteMap.get(uniqueKey);
            if (item == null || !StringUtils.isNotEmpty((String)item.getId())) continue;
            schemeConfigItem.setCreateTime(item.getCreateTime());
            schemeConfigItem.setCreator(item.getCreator());
            schemeConfigItem.setUpdateTime(time);
            schemeConfigItem.setUpdator(userName);
            schemeConfigItem.setId(item.getId());
            deleteImportIds.add(item.getId());
        }
        return deleteImportIds;
    }

    private String getFormulaSchemeLabelById(List<String> fromualSchemeIds, Map<Object, String> formulaSchemeGroupIdAndLabel) {
        if (CollectionUtils.isEmpty(fromualSchemeIds)) {
            return null;
        }
        String formulaSchemeLabel = "";
        for (String formulaId : fromualSchemeIds) {
            String label = formulaSchemeGroupIdAndLabel.get(formulaId);
            if (StringUtils.isEmpty((String)label)) continue;
            formulaSchemeLabel = formulaSchemeLabel + label + ";";
        }
        if (StringUtils.isEmpty((String)formulaSchemeLabel)) {
            return null;
        }
        return formulaSchemeLabel.substring(0, formulaSchemeLabel.length() - 1);
    }

    private String getAssistDimStr(String currencyStr, String schemeId) {
        HashMap<String, String> adjTypeMap = new HashMap<String, String>();
        if (StringUtils.isEmpty((String)schemeId)) {
            return currencyStr;
        }
        FormSchemeVO formScheme = ((IFormSchemeService)ApplicationContextRegister.getBean(IFormSchemeService.class)).getFormScheme(schemeId);
        StringBuilder assistDimStr = new StringBuilder();
        NrFormulaSchemeConfigUtils.dimensionMapAdjTypeValue(formScheme.getTaskKey(), adjTypeMap);
        if (!adjTypeMap.isEmpty()) {
            for (Map.Entry entry : adjTypeMap.entrySet()) {
                assistDimStr.append((String)entry.getKey()).append("@").append((String)entry.getValue());
            }
        }
        if (StringUtils.isNotEmpty((String)currencyStr) && !"-".equals(currencyStr)) {
            if (assistDimStr.length() > 0) {
                assistDimStr.append("|");
            }
            assistDimStr.append(currencyStr);
        }
        if (assistDimStr.length() <= 0) {
            return "-";
        }
        return assistDimStr.toString();
    }

    private String getAssistDimAdjType(String schemeId) {
        HashMap<String, String> adjTypeMap = new HashMap<String, String>();
        if (StringUtils.isEmpty((String)schemeId)) {
            return null;
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        StringBuilder assistDimStr = new StringBuilder();
        NrFormulaSchemeConfigUtils.dimensionMapAdjTypeValue(formScheme.getTaskKey(), adjTypeMap);
        if (!adjTypeMap.isEmpty()) {
            for (Map.Entry entry : adjTypeMap.entrySet()) {
                assistDimStr.append((String)entry.getKey()).append("@").append((String)entry.getValue());
            }
        }
        if (assistDimStr.length() <= 0) {
            return null;
        }
        return assistDimStr.toString();
    }

    private void writeBatchSaveLogHelper(String selectTab, List<FormulaSchemeConfigEO> formulaSchemeConfigs, String sourceInfo) {
        if (CollectionUtils.isEmpty(formulaSchemeConfigs)) {
            return;
        }
        String taskId = formulaSchemeConfigs.get(0).getTaskId();
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.getTask(taskId);
        String unitTab = "batchUnit";
        Set codes = formulaSchemeConfigs.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        String title = "import".equals(sourceInfo) ? "\u5bfc\u5165-\u4efb\u52a1" : "\u65b0\u589e-\u4efb\u52a1";
        String unitTabTitle = unitTab.equals(selectTab) ? "-\u5355\u4f4d\u516c\u5f0f\u65b9\u6848" : "-\u7b56\u7565\u516c\u5f0f\u65b9\u6848";
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)(title + taskDefine.getTitle() + unitTabTitle), (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + codes.toString()));
    }

    private void writeBatchDeleteLogHelper(List<String> ids) {
        List<FormulaSchemeConfigEO> formulaSchemeConfigs = this.formulaSchemeConfigDao.listFormulaSchemeConfigById(ids);
        if (CollectionUtils.isEmpty(formulaSchemeConfigs)) {
            return;
        }
        FormulaSchemeConfigEO formulaSchemeConfig = formulaSchemeConfigs.get(0);
        String taskId = formulaSchemeConfig.getTaskId();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        Set codes = formulaSchemeConfigs.stream().map(FormulaSchemeConfigEO::getOrgId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        String unitTabTitle = StringUtils.isNotEmpty((String)formulaSchemeConfig.getBblx()) && !"-".equals(formulaSchemeConfig.getBblx()) ? "-\u7b56\u7565\u516c\u5f0f\u65b9\u6848" : "-\u5355\u4f4d\u516c\u5f0f\u65b9\u6848";
        LogHelper.info((String)"\u5408\u5e76-\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848", (String)("\u5220\u9664-\u4efb\u52a1" + taskDefine.getTitle() + unitTabTitle), (String)("\u5355\u4f4d\u4fe1\u606f\uff1a" + codes.toString()));
    }
}

