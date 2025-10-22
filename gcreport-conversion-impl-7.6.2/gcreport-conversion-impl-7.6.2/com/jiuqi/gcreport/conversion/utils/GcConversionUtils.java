/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperCurrencyEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.json.JSONArray
 */
package com.jiuqi.gcreport.conversion.utils;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperCurrencyEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv;
import com.jiuqi.gcreport.conversion.conversionrate.service.impl.ConversionRateServiceImpl;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.service.ConversionFormulaEvalService;
import com.jiuqi.gcreport.conversion.utils.RateTypeUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class GcConversionUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ConversionRateServiceImpl.class);
    private List<GcConversionWorkPaperCurrencyEnv> gcConversionWorkPaperCurrencyEnvList;
    private GcConversionOrgAndFormContextEnv orgAndFormEnv;
    private List<String> dimFieldNames;
    private TableModelRunInfo tableInfo;
    private JSONArray conversionDatas;
    private FieldDefine matchFieldDefine;
    private GcConversionWorkPaperEnv conversionWorkPaperEnv;
    private List<ConversionSystemItemEO> systemItems;

    public static final BigDecimal getConversionZbRateValue(IDataRow dataRow, GcConversionOrgAndFormContextEnv conversionContextEnv, GcConversionIndexRateInfo indexRateInfo) {
        BigDecimal rate;
        int lastIndex = indexRateInfo.getRateTypeCode().lastIndexOf(95);
        String rateTypeCode = lastIndex != -1 ? indexRateInfo.getRateTypeCode().substring(0, lastIndex) : indexRateInfo.getRateTypeCode();
        GcBaseData rateTypeEnum = RateTypeUtils.findByCode(rateTypeCode);
        if (rateTypeEnum == null) {
            return BigDecimal.ONE;
        }
        String rateFormula = indexRateInfo.getRateFormula();
        if ((RateTypeEnum.FORMULA.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_FORMULA_BN.getCode().equals(rateTypeCode) || RateTypeEnum.SEGMENT_FORMULA_LJ.getCode().equals(rateTypeCode)) && !StringUtils.isEmpty((String)rateFormula)) {
            Map dimensionSet = conversionContextEnv.getDimensionSet();
            DimensionValueSet dimSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
            ConversionFormulaEvalService formulaEvalService = (ConversionFormulaEvalService)SpringContextUtils.getBean(ConversionFormulaEvalService.class);
            Double rateDouble = formulaEvalService.evaluateConversionRate(dimSet, rateFormula, conversionContextEnv, dataRow);
            BigDecimal rate2 = new BigDecimal(Double.toString(rateDouble));
            return rate2;
        }
        String rateTypeFormula = RateTypeUtils.getRateFormula(rateTypeEnum);
        if (!ObjectUtils.isEmpty(rateTypeFormula)) {
            Map dimensionSet = conversionContextEnv.getDimensionSet();
            DimensionValueSet dimSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
            ConversionFormulaEvalService formulaEvalService = (ConversionFormulaEvalService)SpringContextUtils.getBean(ConversionFormulaEvalService.class);
            Double rateDouble = formulaEvalService.evaluateConversionRate(dimSet, rateTypeFormula, conversionContextEnv, dataRow);
            BigDecimal rate3 = new BigDecimal(Double.toString(rateDouble));
            return rate3;
        }
        BigDecimal rateValue = indexRateInfo.getRateValue();
        if (rateValue == null) {
            LOGGER.warn("\u6298\u7b97\u6c47\u7387\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u53c2\u6570\u8be6\u60c5\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)indexRateInfo));
            rate = BigDecimal.ONE;
        } else {
            rate = rateValue;
        }
        return rate;
    }

    public static final GcConversionIndexRateInfo getRateByFieldKey(List<GcConversionIndexRateInfo> indexRateInfos, String fieldKey) {
        if (CollectionUtils.isEmpty(indexRateInfos)) {
            return null;
        }
        Optional<GcConversionIndexRateInfo> matchRateVO = indexRateInfos.stream().filter(indexRateInfo -> {
            boolean feildKeyEquals = fieldKey.equals(indexRateInfo.getIndexId());
            return feildKeyEquals;
        }).findFirst();
        GcConversionIndexRateInfo indexRateInfo2 = matchRateVO.orElse(null);
        return indexRateInfo2;
    }

    public static final GcConversionIndexRateInfo getRateByFormKeyAndFieldKey(List<GcConversionIndexRateInfo> indexRateInfos, String formKey, String fieldKey) {
        if (CollectionUtils.isEmpty(indexRateInfos)) {
            return null;
        }
        Optional<GcConversionIndexRateInfo> matchRateVO = indexRateInfos.stream().filter(indexRateInfo -> {
            boolean feildKeyEquals = fieldKey.equals(indexRateInfo.getIndexId());
            boolean formKeyEquals = formKey.equals(indexRateInfo.getFormId());
            return feildKeyEquals && formKeyEquals;
        }).findFirst();
        GcConversionIndexRateInfo indexRateInfo2 = matchRateVO.orElse(null);
        return indexRateInfo2;
    }

    public static IDataTable getQueryDataTable(GcConversionOrgAndFormContextEnv env, TableModelRunInfo definitionsCache, Collection<FieldDefine> matchFeildDefines, boolean isBeforeConversion) throws Exception {
        String schemeId = env.getSchemeId();
        String regionKey = env.getDataRegionDefine().getKey();
        String formKey = env.getFormDefine().getKey();
        DimensionValueSet dimensionValueSet = GcConversionUtils.buildDimensionValueSet(env, definitionsCache, isBeforeConversion);
        IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable(env, schemeId, regionKey, formKey, dimensionValueSet, matchFeildDefines);
        return beforeDataTable;
    }

    public static IDataTable getQueryDataTable(GcConversionOrgAndFormContextEnv env, String schemeId, String regionKey, String formKey, DimensionValueSet dimensionValueSet, Collection<FieldDefine> matchFeildDefines) throws Exception {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(runTimeViewController, dataDefinitionRuntimeController, entityViewController, schemeId);
        context.setEnv((IFmlExecEnvironment)environment);
        InputDataChangeMonitorEnvVo monitorEnvVo = new InputDataChangeMonitorEnvVo(false, env.isAfterConversionRealTimeOffset());
        environment.getVariableManager().add(new Variable("INPUTDATA_CHANGEMONITOR_ENV_VO", "\u5916\u5e01\u6298\u7b97\u8bbe\u7f6e\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u53d8\u5316\u73af\u5883", 0, (Object)monitorEnvVo));
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(schemeId);
        queryEnvironment.setRegionKey(regionKey);
        queryEnvironment.setFormKey(formKey);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        dataAccessProvider.newDataAssist(context);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        DataRegionDefine regionDefine = runTimeViewController.queryDataRegionDefine(regionKey);
        dataQuery.setRowFilter(regionDefine.getFilterCondition());
        matchFeildDefines.forEach(fieldDefine -> dataQuery.addColumn(fieldDefine));
        dataQuery.setMasterKeys(dimensionValueSet);
        IDataTable beforeDataTable = dataQuery.executeQuery(context);
        return beforeDataTable;
    }

    public static DimensionValueSet buildDimensionValueSet(GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableInfo, boolean isBeforeConversion) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        ENameSet eNameSet = tableInfo.getDimensions().getDimensions();
        block12: for (int i = 0; i < eNameSet.size(); ++i) {
            String dimensionFieldName;
            ColumnModelDefine dimensionField;
            String eName = eNameSet.get(i);
            if (StringUtils.isEmpty((String)eName) || (dimensionField = tableInfo.getDimensionField(eName)) == null) continue;
            switch (dimensionFieldName = dimensionField.getName()) {
                case "DATATIME": {
                    dimensionValueSet.setValue(eName, (Object)env.getPeriodStr());
                    continue block12;
                }
                case "MDCODE": {
                    dimensionValueSet.setValue(eName, (Object)env.getOrgId());
                    continue block12;
                }
                case "MD_GCORGTYPE": {
                    dimensionValueSet.setValue(eName, (Object)env.getOrgTypeId());
                    continue block12;
                }
                case "MD_CURRENCY": {
                    dimensionValueSet.setValue(eName, (Object)(isBeforeConversion ? env.getBeforeCurrencyCode() : env.getAfterCurrencyCode()));
                    continue block12;
                }
                default: {
                    DimensionValue dimensionValue;
                    Map dimensionSet = env.getDimensionSet();
                    if (dimensionSet == null || (dimensionValue = (DimensionValue)dimensionSet.get(dimensionFieldName)) == null) continue block12;
                    dimensionValueSet.setValue(eName, (Object)dimensionValue.getValue());
                }
            }
        }
        return dimensionValueSet;
    }

    public static IDataTable getPriorQueryDataTable(GcConversionOrgAndFormContextEnv env, Collection<FieldDefine> matchFeildDefines, boolean isBeforeConversion) throws Exception {
        String priorPeriodStr = GcConversionUtils.getPriorPeriod(env.getPeriodStr());
        String orgId = env.getOrgId();
        String orgTypeId = env.getOrgTypeId();
        String beforeCurrencyCode = env.getBeforeCurrencyCode();
        String afterCurrencyCode = env.getAfterCurrencyCode();
        String schemeId = env.getSchemeId();
        String regionKey = env.getDataRegionDefine().getKey();
        String formKey = env.getFormDefine().getKey();
        String currencyId = isBeforeConversion ? beforeCurrencyCode : afterCurrencyCode;
        Map beforeDimensionSetMap = DimensionUtils.buildDimensionMap((String)env.getTaskId(), (String)currencyId, (String)priorPeriodStr, (String)orgTypeId, (String)orgId, (String)env.getSelectAdjustCode());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)beforeDimensionSetMap);
        IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable(env, schemeId, regionKey, formKey, dimensionValueSet, matchFeildDefines);
        return beforeDataTable;
    }

    public static String getPriorPeriod(String periodStr) {
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        String priorPeriodStr = periodWrapper.toString();
        return priorPeriodStr;
    }

    public static boolean isSameYear(String priorPeriodStr, String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        PeriodWrapper priorPeriodWrapper = new PeriodWrapper(priorPeriodStr);
        return periodWrapper.getYear() == priorPeriodWrapper.getYear();
    }

    public static Map<String, FormDefine> getFormDefinesByEnv(String getSchemeId, List<String> formIds, List<FormDefine> formDefines) {
        List formDDefinitions = null;
        if (formDefines == null) {
            try {
                IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
                formDDefinitions = runTimeViewController.queryAllFormDefinesByFormScheme(getSchemeId);
            }
            catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        } else {
            formDDefinitions = formDefines;
        }
        if (CollectionUtils.isEmpty(formDDefinitions)) {
            return null;
        }
        Map formDefineMap = formDDefinitions.stream().filter(Objects::nonNull).filter(formDefine -> {
            Optional<String> optional = formIds.stream().filter(formId -> formDefine.getKey().equals(formId)).findAny();
            boolean present = optional.isPresent();
            return present;
        }).collect(Collectors.toConcurrentMap(formDefine -> formDefine.getKey(), Function.identity()));
        return Collections.unmodifiableMap(formDefineMap);
    }

    public static DefinitionsCache getDefinitionsCache() throws ParseException {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        return new DefinitionsCache(context);
    }

    public static ConversionSystemTaskEO getConversionSystemTaskEO(String taskId, String schemeId) {
        ConversionSystemTaskDao taskSchemeDao = (ConversionSystemTaskDao)SpringContextUtils.getBean(ConversionSystemTaskDao.class);
        ConversionSystemTaskEO taskSchemeEO = taskSchemeDao.queryByTaskAndScheme(taskId, schemeId);
        if (taskSchemeEO == null) {
            taskSchemeEO = new ConversionSystemTaskEO();
            taskSchemeEO.setTaskId(taskId);
            taskSchemeEO.setSchemeId(schemeId);
        }
        return taskSchemeEO;
    }

    public static List<GcConversionIndexRateInfo> getConversionIndexRateInfos(GcConversionOrgAndFormContextEnv formContextEnv, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, Map<String, BigDecimal> rateInfos) {
        String beforeCurrencyCode = formContextEnv.getBeforeCurrencyCode();
        String afterCurrencyCode = formContextEnv.getAfterCurrencyCode();
        ArrayList<GcConversionIndexRateInfo> indexRateInfos = new ArrayList<GcConversionIndexRateInfo>();
        if (!CollectionUtils.isEmpty(systemItems)) {
            systemItems.stream().forEach(systemItem -> {
                GcConversionIndexRateInfo indexRateInfo = new GcConversionIndexRateInfo();
                indexRateInfo.setRateSchemeCode(taskSchemeEO.getRateSchemeCode());
                indexRateInfo.setTaskId(taskSchemeEO.getTaskId());
                indexRateInfo.setSchemeId(taskSchemeEO.getSchemeId());
                indexRateInfo.setFormId(systemItem.getFormId());
                indexRateInfo.setIndexId(systemItem.getIndexId());
                indexRateInfo.setRateTypeCode(systemItem.getRateTypeCode());
                if (rateInfos != null && rateInfos.size() > 0) {
                    int lastIndex = indexRateInfo.getRateTypeCode().lastIndexOf(95);
                    String rateTypeCode = lastIndex != -1 ? indexRateInfo.getRateTypeCode().substring(0, lastIndex) : indexRateInfo.getRateTypeCode();
                    indexRateInfo.setRateValue((BigDecimal)rateInfos.get(rateTypeCode));
                }
                indexRateInfo.setRateFormula(systemItem.getRateFormula());
                indexRateInfo.setSourceCurrecyCode(beforeCurrencyCode);
                indexRateInfo.setTargeCurrencyCode(afterCurrencyCode);
                indexRateInfos.add(indexRateInfo);
            });
        }
        return indexRateInfos;
    }

    public static Map<DataRegionDefine, Set<TableModelDefine>> getFormTableDefines(String formId) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        List dataRegionDefines = runTimeViewController.getAllRegionsInForm(formId);
        if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
            return null;
        }
        HashMap<DataRegionDefine, Set<TableModelDefine>> map = new HashMap<DataRegionDefine, Set<TableModelDefine>>();
        dataRegionDefines.stream().filter(Objects::nonNull).forEach(dataRegionDefine -> {
            List deployInfos;
            Set mapValue = (Set)map.get(dataRegionDefine);
            if (mapValue == null) {
                map.put((DataRegionDefine)dataRegionDefine, (Set<TableModelDefine>)new TreeSet<TableModelDefine>(new Comparator<TableModelDefine>(){

                    @Override
                    public int compare(TableModelDefine o1, TableModelDefine o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                }));
            }
            if (CollectionUtils.isEmpty((Collection)(deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()).toArray(new String[0]))))) {
                return;
            }
            deployInfos.stream().filter(deployInfo -> {
                boolean isAllowPost;
                String dataTableKey = deployInfo.getDataTableKey();
                DataTable dataTable = runtimeDataSchemeService.getDataTable(dataTableKey);
                switch (dataTable.getKind()) {
                    case TABLE_KIND_DICTIONARY: 
                    case TABLE_KIND_ENTITY_PERIOD: 
                    case TABLE_KIND_MEASUREMENT_UNIT: 
                    case TABLE_KIND_SYSTEM: 
                    case TABLE_KIND_ENTITY: {
                        isAllowPost = false;
                        break;
                    }
                    default: {
                        isAllowPost = true;
                    }
                }
                if (dataTable.getCode().equalsIgnoreCase("GC_OFFSETVCHRITEM")) {
                    isAllowPost = false;
                }
                return isAllowPost;
            }).forEach(deployInfo -> {
                TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(deployInfo.getTableName());
                ((Set)map.get(dataRegionDefine)).add(tableModelDefine);
            });
        });
        if (map == null || map.size() == 0) {
            return null;
        }
        return map;
    }

    public static String checkAllowConversionByOrg(String taskKey, String formSchemeKey, String orgType, String orgId, String orgTitle, String orgTypeId, String currencyId, String periodStr, String selectAdjustCode) {
        FormulaSchemeConfigService formulaSchemeConfigService = (FormulaSchemeConfigService)SpringContextUtils.getBean(FormulaSchemeConfigService.class);
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        Map dimensionSetMap = DimensionUtils.generateDimMap(null, null, (String)currencyId, (String)orgTypeId, (String)"0", (String)taskKey);
        try {
            String convertSystemSchemeId;
            FormulaSchemeConfigDTO formulaSchemeConfigDTO = formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(formSchemeKey, orgId, dimensionSetMap);
            if (formulaSchemeConfigDTO != null && "false".equals(convertSystemSchemeId = formulaSchemeConfigDTO.getConvertSystemSchemeId())) {
                String errorMsg = "\u5355\u4f4d[" + (StringUtils.isEmpty((String)orgTitle) ? orgId : orgTitle) + "]\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97";
                LOGGER.debug("\u5355\u4f4d[{}]\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97\uff0c\u539f\u56e0\uff1a{}\u3002", (Object)orgId, (Object)"\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u914d\u7f6e\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97");
                return errorMsg;
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        String option = nvwaSystemOptionService.get("ALLOW_UPLOADSTATE_CONVERSION", "ALLOW_UPLOADSTATE_CONVERSION");
        if (!"1".equals(option)) {
            DimensionParamsVO params = new DimensionParamsVO();
            params.setTaskId(taskKey);
            params.setSchemeId(formSchemeKey);
            params.setOrgId(orgId);
            params.setOrgType(orgType);
            params.setOrgTypeId(orgTypeId);
            params.setPeriodStr(periodStr);
            params.setSelectAdjustCode(selectAdjustCode);
            ReadWriteAccessDesc writeAbleInfo = new UploadStateTool().writeable(params);
            if (!Boolean.TRUE.equals(writeAbleInfo.getAble())) {
                String errorMsg = "\u5355\u4f4d[" + (StringUtils.isEmpty((String)orgTitle) ? orgId : orgTitle) + "]\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97\uff0c \u539f\u56e0\uff1a" + writeAbleInfo.getDesc();
                LOGGER.debug("\u5355\u4f4d[{}]\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97\uff0c\u539f\u56e0\uff1a{}\u3002", (Object)orgId, (Object)writeAbleInfo.getDesc());
                return errorMsg;
            }
        }
        return "";
    }

    public List<GcConversionWorkPaperCurrencyEnv> getGcConversionWorkPaperCurrencyEnvList() {
        return this.gcConversionWorkPaperCurrencyEnvList;
    }

    public void setGcConversionWorkPaperCurrencyEnvList(List<GcConversionWorkPaperCurrencyEnv> gcConversionWorkPaperCurrencyEnvList) {
        this.gcConversionWorkPaperCurrencyEnvList = gcConversionWorkPaperCurrencyEnvList;
    }

    public GcConversionOrgAndFormContextEnv getOrgAndFormEnv() {
        return this.orgAndFormEnv;
    }

    public void setOrgAndFormEnv(GcConversionOrgAndFormContextEnv orgAndFormEnv) {
        this.orgAndFormEnv = orgAndFormEnv;
    }

    public List<String> getDimFieldNames() {
        return this.dimFieldNames;
    }

    public void setDimFieldNames(List<String> dimFieldNames) {
        this.dimFieldNames = dimFieldNames;
    }

    public TableModelRunInfo getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(TableModelRunInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public JSONArray getConversionDatas() {
        return this.conversionDatas;
    }

    public void setConversionDatas(JSONArray conversionDatas) {
        this.conversionDatas = conversionDatas;
    }

    public FieldDefine getMatchFieldDefine() {
        return this.matchFieldDefine;
    }

    public void setMatchFieldDefine(FieldDefine matchFieldDefine) {
        this.matchFieldDefine = matchFieldDefine;
    }

    public GcConversionWorkPaperEnv getConversionWorkPaperEnv() {
        return this.conversionWorkPaperEnv;
    }

    public void setConversionWorkPaperEnv(GcConversionWorkPaperEnv conversionWorkPaperEnv) {
        this.conversionWorkPaperEnv = conversionWorkPaperEnv;
    }

    public List<ConversionSystemItemEO> getSystemItems() {
        return this.systemItems;
    }

    public void setSystemItems(List<ConversionSystemItemEO> systemItems) {
        this.systemItems = systemItems;
    }
}

