/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle
 *  com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.MultiDimensionalDataSet
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy
 *  com.jiuqi.nr.datacrud.spi.IEntityTableFactory
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.TypeFormatStrategy
 *  com.jiuqi.nr.datacrud.util.TypeStrategyUtil
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.api.IRunTimePrintController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.option.core.ZeroShowValue
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.data.excel.exception.ExcelException;
import com.jiuqi.nr.data.excel.export.ExcelPrintSetup;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import com.jiuqi.nr.data.excel.extend.ISheetNameProvider;
import com.jiuqi.nr.data.excel.extend.ISheetNameProviderFactory;
import com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider;
import com.jiuqi.nr.data.excel.extend.param.SheetNameEnv;
import com.jiuqi.nr.data.excel.extend.param.SheetNameType;
import com.jiuqi.nr.data.excel.extend.param.ShowSetting;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.DWShow;
import com.jiuqi.nr.data.excel.obj.ExcelExportEnv;
import com.jiuqi.nr.data.excel.obj.ExpFormFolding;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.obj.FilterSort;
import com.jiuqi.nr.data.excel.obj.FormShow;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.param.BaseExpPar;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.data.excel.param.SingleExpPar;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.data.excel.service.impl.ExportEnumFormatStrategy;
import com.jiuqi.nr.data.excel.service.internal.IExportOptionsService;
import com.jiuqi.nr.data.excel.service.internal.IFormDataService;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.option.core.ZeroShowValue;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class ExportCacheImpl
implements ExportCache {
    private static final Logger logger = LoggerFactory.getLogger(ExportCacheImpl.class);
    private static final IFormDataService formDataService = (IFormDataService)BeanUtil.getBean(IFormDataService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private static final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private static final IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
    private static final IDataQueryService dataQueryService = (IDataQueryService)BeanUtil.getBean(IDataQueryService.class);
    private static final IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private static final IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
    private IExportOptionsService exportOptionsService;
    private static final RegionRelationFactory regionRelationFactory = (RegionRelationFactory)BeanUtil.getBean(RegionRelationFactory.class);
    private static final IEntityTableFactory entityTableFactory = (IEntityTableFactory)BeanUtil.getBean(IEntityTableFactory.class);
    private final Map<String, FormDefine> formMap = new HashMap<String, FormDefine>();
    private final Map<String, Grid2Data> formStyleMap = new HashMap<String, Grid2Data>();
    private final Map<String, DataLinkDefine> dataLinkDefineMap = new HashMap<String, DataLinkDefine>();
    private final Map<String, FormSchemeDefine> formSchemeDefineMap = new HashMap<String, FormSchemeDefine>();
    private final Map<String, TaskDefine> taskDefineMap = new HashMap<String, TaskDefine>();
    private final Map<String, List<String>> fieldKeysInRegionMap = new HashMap<String, List<String>>();
    private final Map<String, FieldDefine> fieldDefineMap = new HashMap<String, FieldDefine>();
    private final Map<String, FieldDefine> masterFieldDefineMap = new HashMap<String, FieldDefine>();
    private final Map<String, TableDefine> tableDefineMap = new HashMap<String, TableDefine>();
    private final Map<String, List<String>> enumDataMap = new HashMap<String, List<String>>();
    private final Map<String, List<IEntityRefer>> entityReferMap = new HashMap<String, List<IEntityRefer>>();
    private final Map<String, List<DataLinkDefine>> allLinkInRegionMap = new HashMap<String, List<DataLinkDefine>>();
    private final Map<String, RegionSettingDefine> regionSettingDefineMap = new HashMap<String, RegionSettingDefine>();
    private final Map<String, List<DataLinkDefine>> linksInFormByFieldMap = new HashMap<String, List<DataLinkDefine>>();
    private final Map<String, List<RegionTabSettingDefine>> tabsInRegionMap = new HashMap<String, List<RegionTabSettingDefine>>();
    private final Map<String, List<DataRegionDefine>> sortedRegionInFormMap = new HashMap<String, List<DataRegionDefine>>();
    private final Map<String, MultiDimensionalDataSet> multiDimensionalDataSetMap = new HashMap<String, MultiDimensionalDataSet>();
    private final Map<String, Integer> regionQueryBatchMap = new HashMap<String, Integer>();
    private final Map<String, Map<String, IEntityRow>> entityRowMap = new HashMap<String, Map<String, IEntityRow>>();
    private final Date queryVersionDate;
    private Map<String, List<ConditionalStyle>> link2ConditionalStyle = new HashMap<String, List<ConditionalStyle>>();
    private final Map<String, List<ConditionalStyle>> form2ConditionalStyle = new HashMap<String, List<ConditionalStyle>>();
    private Map<String, Set<DimensionValueSet>> unconditionalStyleDims = new HashMap<String, Set<DimensionValueSet>>();
    private boolean autoFillIsNullTable;
    private final ExcelExportEnv env;
    private final List<String> fileGroupKeys = new ArrayList<String>();
    private final Map<String, IEntityDefine> entityDefineMap = new HashMap<String, IEntityDefine>();
    private String zeroShow;
    private String dataSnapshotId;
    private final ExportOps ops;
    private final FormSchemeDefine curFormScheme;
    private Map<String, FilterSort> filterSortMap = new HashMap<String, FilterSort>();
    private boolean gridDataFormatted;
    private ReportFmlExecEnvironment fmlExecEnvironment;
    private final Map<String, List<ExpFormFolding>> formFoldingMap = new HashMap<String, List<ExpFormFolding>>();
    private final Map<String, Map<String, PrintSettingDefine>> printSettingMap = new HashMap<String, Map<String, PrintSettingDefine>>(1);
    private IRunTimePrintController runTimePrintController;
    private DataValueBalanceActuatorFactory dataValueBalanceActuatorFactory;
    private final Map<String, DataValueFormatter> formDataValueFormatters = new HashMap<String, DataValueFormatter>(3);
    private final Map<String, IDataValueBalanceActuator> formDataValueBalanceActuators = new HashMap<String, IDataValueBalanceActuator>(3);
    private final Map<String, Integer> formMeasureDecimals = new HashMap<String, Integer>(3);
    private DataValueFormatterBuilderFactory builderFactory;
    private TypeStrategyUtil typeStrategyUtil;
    private Integer measureChangeDefaultDecimal;
    private Map<String, GridCellStyleData> cellStyleCache;
    private final Map<String, List<Fml>> fmlCache = new HashMap<String, List<Fml>>();
    private IFormulaRunTimeController formulaRunTimeController;
    private Boolean expFml;
    private final CustomCellStyleProvider customCellStyleProvider;
    private ExportEnumFormatStrategy exportEnumFormatStrategy;
    @Nullable
    private List<DimensionCollection> batchQuerySplitDims;
    private DimensionBuildUtil dimensionBuildUtil;
    private ISheetNameProvider sheetNameProvider;
    private INvwaSystemOptionService systemOptionService;

    public ExportCacheImpl(Date queryVersionDate, FormSchemeDefine formSchemeDefine, BaseExpPar baseExpPar) {
        this.initBeans();
        this.ops = baseExpPar.getOps();
        this.customCellStyleProvider = baseExpPar.getCustomCellStyleProvider();
        this.queryVersionDate = queryVersionDate;
        this.curFormScheme = formSchemeDefine;
        this.env = new ExcelExportEnv(formSchemeDefine.getTaskKey(), formSchemeDefine.getKey());
        this.initSettingOps();
    }

    private void initSettingOps() {
        this.autoFillIsNullTable = this.exportOptionsService.autoFillIsNullTable();
        this.zeroShow = this.initZeroShow(this.curFormScheme.getTaskKey());
        this.measureChangeDefaultDecimal = this.exportOptionsService.getMeasureChangeDefaultDecimal(this.curFormScheme.getTaskKey());
        this.exportEnumFormatStrategy = new ExportEnumFormatStrategy(entityMetaService, entityTableFactory, regionRelationFactory, runTimeViewController);
    }

    private String initZeroShow(String taskKey) {
        String zeroShowSetting = this.exportOptionsService.getZeroShow(taskKey);
        ZeroShowValue enumByValue = ZeroShowValue.getEnumByValue((String)zeroShowSetting);
        if (ZeroShowValue.ORIGINAL_VALUE == enumByValue) {
            return null;
        }
        if (ZeroShowValue.NULL_VALUE == enumByValue) {
            return "";
        }
        if (null == enumByValue) {
            return null;
        }
        return enumByValue.getValue();
    }

    private void initBeans() {
        this.runTimePrintController = (IRunTimePrintController)BeanUtil.getBean(IRunTimePrintController.class);
        this.dataValueBalanceActuatorFactory = (DataValueBalanceActuatorFactory)BeanUtil.getBean(DataValueBalanceActuatorFactory.class);
        this.builderFactory = (DataValueFormatterBuilderFactory)BeanUtil.getBean(DataValueFormatterBuilderFactory.class);
        this.typeStrategyUtil = (TypeStrategyUtil)BeanUtil.getBean(TypeStrategyUtil.class);
        this.exportOptionsService = (IExportOptionsService)BeanUtil.getBean(IExportOptionsService.class);
        this.formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        this.dimensionBuildUtil = (DimensionBuildUtil)BeanUtil.getBean(DimensionBuildUtil.class);
        this.systemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
    }

    @Override
    public FormDefine getFormByKey(String formKey) {
        if (this.formMap.containsKey(formKey)) {
            return this.formMap.get(formKey);
        }
        FormDefine formDefine = runTimeViewController.queryFormById(formKey);
        this.formMap.put(formKey, formDefine);
        return formDefine;
    }

    @Override
    public Grid2Data getFormStyle(String formKey) {
        Grid2Data formStyle;
        if (this.formStyleMap.containsKey(formKey)) {
            formStyle = this.formStyleMap.get(formKey);
        } else {
            formStyle = formDataService.getFormStyle(formKey);
            this.formStyleMap.put(formKey, formStyle);
        }
        return Grid2Data.bytesToGrid((byte[])Grid2Data.gridToBytes((Grid2Data)formStyle));
    }

    @Override
    public DataLinkDefine getDataLink(String dataLinkKey) {
        if (this.dataLinkDefineMap.containsKey(dataLinkKey)) {
            return this.dataLinkDefineMap.get(dataLinkKey);
        }
        DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefine(dataLinkKey);
        this.dataLinkDefineMap.put(dataLinkKey, dataLinkDefine);
        return dataLinkDefine;
    }

    @Override
    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        if (this.formSchemeDefineMap.containsKey(formSchemeKey)) {
            return this.formSchemeDefineMap.get(formSchemeKey);
        }
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        this.formSchemeDefineMap.put(formSchemeKey, formScheme);
        return formScheme;
    }

    @Override
    public List<String> getFieldKeysInRegion(String dataRegionKey) {
        if (this.fieldKeysInRegionMap.containsKey(dataRegionKey)) {
            return this.fieldKeysInRegionMap.get(dataRegionKey);
        }
        List fieldKeysInRegion = runTimeViewController.getFieldKeysInRegion(dataRegionKey);
        this.fieldKeysInRegionMap.put(dataRegionKey, fieldKeysInRegion);
        return fieldKeysInRegion;
    }

    @Override
    public FieldDefine queryFieldDefine(String fieldKey) {
        if (this.fieldDefineMap.containsKey(fieldKey)) {
            return this.fieldDefineMap.get(fieldKey);
        }
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = runTimeViewController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.fieldDefineMap.put(fieldKey, fieldDefine);
        return fieldDefine;
    }

    @Override
    public FieldDefine queryMasterFieldDefine(String fieldKey) {
        if (this.masterFieldDefineMap.containsKey(fieldKey)) {
            return this.masterFieldDefineMap.get(fieldKey);
        }
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.masterFieldDefineMap.put(fieldKey, fieldDefine);
        return fieldDefine;
    }

    @Override
    public TableDefine queryTableDefine(String tableKey) {
        if (this.tableDefineMap.containsKey(tableKey)) {
            return this.tableDefineMap.get(tableKey);
        }
        TableDefine tableDefine = null;
        try {
            tableDefine = dataDefinitionRuntimeController.queryTableDefine(tableKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.tableDefineMap.put(tableKey, tableDefine);
        return tableDefine;
    }

    @Override
    public List<DataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        String key = formKey + fieldKey;
        if (this.linksInFormByFieldMap.containsKey(key)) {
            return this.linksInFormByFieldMap.get(key);
        }
        List linksInFormByField = runTimeViewController.getLinksInFormByField(formKey, fieldKey);
        this.linksInFormByFieldMap.put(key, linksInFormByField);
        return linksInFormByField;
    }

    @Override
    public RegionSettingDefine getRegionSetting(String dataRegionKey) {
        if (this.regionSettingDefineMap.containsKey(dataRegionKey)) {
            return this.regionSettingDefineMap.get(dataRegionKey);
        }
        RegionSettingDefine regionSetting = runTimeViewController.getRegionSetting(dataRegionKey);
        this.regionSettingDefineMap.put(dataRegionKey, regionSetting);
        return regionSetting;
    }

    @Override
    public List<DataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        if (this.allLinkInRegionMap.containsKey(dataRegionKey)) {
            return this.allLinkInRegionMap.get(dataRegionKey);
        }
        List allLinksInRegion = runTimeViewController.getAllLinksInRegion(dataRegionKey);
        this.allLinkInRegionMap.put(dataRegionKey, allLinksInRegion);
        return allLinksInRegion;
    }

    @Override
    public List<DataRegionDefine> getSortedRegionsByForm(String formKey) {
        if (this.sortedRegionInFormMap.containsKey(formKey)) {
            return this.sortedRegionInFormMap.get(formKey);
        }
        List allRegionsInForm = runTimeViewController.getAllRegionsInForm(formKey);
        ArrayList<DataRegionDefine> fixRegins = new ArrayList<DataRegionDefine>();
        ArrayList<DataRegionDefine> floatRegins = new ArrayList<DataRegionDefine>();
        for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                fixRegins.add(dataRegionDefine);
                continue;
            }
            floatRegins.add(dataRegionDefine);
        }
        if (floatRegins.size() > 0 && ((DataRegionDefine)floatRegins.get(0)).getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            floatRegins.sort(Comparator.comparingInt(DataRegionDefine::getRegionLeft));
        } else {
            floatRegins.sort(Comparator.comparingInt(DataRegionDefine::getRegionTop));
        }
        fixRegins.addAll(floatRegins);
        this.sortedRegionInFormMap.put(formKey, fixRegins);
        return fixRegins;
    }

    @Override
    public List<RegionTabSettingDefine> getTabsByRegion(String dataRegionKey) {
        List regionTabSetting;
        if (this.tabsInRegionMap.containsKey(dataRegionKey)) {
            return this.tabsInRegionMap.get(dataRegionKey);
        }
        ArrayList<RegionTabSettingDefine> regionTabs = new ArrayList<RegionTabSettingDefine>();
        RegionSettingDefine regionSetting = runTimeViewController.getRegionSetting(dataRegionKey);
        if (regionSetting != null && null != (regionTabSetting = regionSetting.getRegionTabSetting())) {
            regionTabs.addAll(regionTabSetting);
        }
        this.tabsInRegionMap.put(dataRegionKey, regionTabs);
        return regionTabs;
    }

    @Override
    @Nullable
    public MultiDimensionalDataSet getMultiDimensionalDataSet(String dataRegionKey, SheetInfo sheetInfo) {
        MultiDimensionalDataSet multiDimensionalDataSet;
        String sheetDimStr = sheetInfo.getDimensionCombination().toDimensionValueSet().toString();
        if (this.multiDimensionalDataSetMap.containsKey(dataRegionKey)) {
            multiDimensionalDataSet = this.multiDimensionalDataSetMap.get(dataRegionKey);
            if (multiDimensionalDataSet == null || multiDimensionalDataSet.containsRegionDataSet(sheetInfo.getDimensionCombination())) {
                logger.debug("{}-\u5f53\u524d\u7ef4\u5ea6{}\u5728\u7f13\u5b58\u4e2d\u627e\u5230\u6570\u636e", (Object)dataRegionKey, (Object)sheetDimStr);
                return multiDimensionalDataSet;
            }
            logger.debug("{}-\u7f13\u5b58\u4e2d\u7684\u6279\u91cf\u6570\u636e\u96c6\u4e0d\u5305\u542b\u5f53\u524d\u7ef4\u5ea6{}\uff0c\u9700\u8981\u67e5\u8be2\u4e0b\u4e00\u6279\u6b21\u6570\u636e", (Object)dataRegionKey, (Object)sheetDimStr);
            this.multiDimensionalDataSetMap.remove(dataRegionKey);
        }
        multiDimensionalDataSet = null;
        if (CollectionUtils.isEmpty(this.batchQuerySplitDims) || this.batchQuerySplitDims.size() == 1) {
            DimensionCollection curQueryDim = sheetInfo.getDimensionCollection();
            logger.debug("{}-\u672a\u62c6\u5206\u56fa\u5b9a\u533a\u57df\u6279\u91cf\u67e5\u8be2\u7ef4\u5ea6\uff0c\u5f53\u524d\u7ef4\u5ea6{}\uff0c\u5f53\u524d\u6279\u91cf\u67e5\u8be2\u7ef4\u5ea6{}", dataRegionKey, sheetDimStr, curQueryDim);
            multiDimensionalDataSet = this.getMultiDimensionalDataSet(dataRegionKey, sheetInfo, curQueryDim);
        } else {
            int curBatch;
            int splitSize = this.batchQuerySplitDims.size();
            try {
                for (curBatch = this.regionQueryBatchMap.containsKey(dataRegionKey) ? this.regionQueryBatchMap.get(dataRegionKey) + 1 : 0; curBatch < splitSize; ++curBatch) {
                    DimensionCollection curQueryDim = this.batchQuerySplitDims.get(curBatch);
                    logger.debug("{}-\u5f53\u524d\u67e5\u8be2\u6279\u6b21{}/\u603b\u6279\u6b21{},\u5f53\u524d\u6279\u6b21\u6279\u91cf\u67e5\u8be2\u7ef4\u5ea6{}", dataRegionKey, curBatch, splitSize, curQueryDim);
                    MultiDimensionalDataSet queryResult = this.getMultiDimensionalDataSet(dataRegionKey, sheetInfo, curQueryDim);
                    if (queryResult != null && queryResult.containsRegionDataSet(sheetInfo.getDimensionCombination())) {
                        logger.debug("{}-\u5f53\u524d\u67e5\u8be2\u6279\u6b21{}/\u603b\u6279\u6b21{},\u5f53\u524d\u7ef4\u5ea6{}\u5c5e\u4e8e\u8be5\u6279\u6b21", dataRegionKey, curBatch, splitSize, sheetDimStr);
                        multiDimensionalDataSet = queryResult;
                        break;
                    }
                    logger.debug("{}-\u5f53\u524d\u67e5\u8be2\u6279\u6b21{}/\u603b\u6279\u6b21{},\u5f53\u524d\u7ef4\u5ea6{}\u4e0d\u5c5e\u4e8e\u8be5\u6279\u6b21", dataRegionKey, curBatch, splitSize, sheetDimStr);
                }
            }
            catch (Exception e) {
                logger.error("{}:\u5f53\u524d\u67e5\u8be2\u6279\u6b21={},\u62c6\u5206\u6279\u6b21={},\u5f53\u524d\u7ef4\u5ea6={}", e.getMessage(), curBatch, splitSize, sheetDimStr, e);
            }
            this.regionQueryBatchMap.put(dataRegionKey, curBatch);
            if (curBatch >= splitSize) {
                logger.error("\u62c6\u5206\u56fa\u5b9a\u533a\u57df\u6279\u91cf\u67e5\u8be2\u7ef4\u5ea6\u5f02\u5e38\uff0c\u5f53\u524d\u6279\u6b21\u5927\u4e8e\u62c6\u5206\u6279\u6b21\u6570\uff0c\u5f53\u524d\u67e5\u8be2\u6279\u6b21={},\u62c6\u5206\u6279\u6b21={},\u5f53\u524d\u7ef4\u5ea6={}", curBatch, splitSize, sheetDimStr);
            }
        }
        this.multiDimensionalDataSetMap.put(dataRegionKey, multiDimensionalDataSet);
        return multiDimensionalDataSet;
    }

    private MultiDimensionalDataSet getMultiDimensionalDataSet(String dataRegionKey, SheetInfo sheetInfo, DimensionCollection curQueryDim) {
        Map<String, List<RowFilter>> rowFilter = sheetInfo.getExportOps().getRowFilter();
        Map<String, List<LinkSort>> linkSort = sheetInfo.getExportOps().getLinkSort();
        QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(dataRegionKey, curQueryDim).whereRegionFilter();
        queryInfoBuilder.setDesensitized(true);
        if (rowFilter.containsKey(dataRegionKey)) {
            rowFilter.get(dataRegionKey).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (linkSort.containsKey(dataRegionKey)) {
            linkSort.get(dataRegionKey).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).orderBy(arg_0));
        }
        if (sheetInfo.getExportOps().getExportMeasureSetting() != null) {
            queryInfoBuilder.setMeasure(sheetInfo.getExportOps().getExportMeasureSetting().toMeasure());
        }
        queryInfoBuilder.setFormulaSchemeKey(sheetInfo.getExportOps().getFormulaSchemeKey());
        IQueryInfo queryInfo = queryInfoBuilder.build();
        RegionGradeInfo regionGradeInfo = this.getRegionGradeInfo(dataRegionKey);
        return dataQueryService.queryMultiDimRegionData(queryInfo, regionGradeInfo);
    }

    private RegionGradeInfo getRegionGradeInfo(String dataRegionKey) {
        RegionGradeInfo regionGradeInfo = null;
        Map<String, RegionGradeInfo> gradeInfos = this.ops.getGradeInfos();
        if (!CollectionUtils.isEmpty(gradeInfos)) {
            regionGradeInfo = gradeInfos.get(dataRegionKey);
        }
        return regionGradeInfo;
    }

    @Override
    public List<IEntityRefer> getEntityRefer(String entityId) {
        if (this.entityReferMap.containsKey(entityId)) {
            return this.entityReferMap.get(entityId);
        }
        List entityRefer = entityMetaService.getEntityRefer(entityId);
        this.entityReferMap.put(entityId, entityRefer);
        return entityRefer;
    }

    @Override
    public TaskDefine getTaskDefine(String taskKey) {
        if (this.taskDefineMap.containsKey(taskKey)) {
            return this.taskDefineMap.get(taskKey);
        }
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskKey);
        this.taskDefineMap.put(taskKey, taskDefine);
        return taskDefine;
    }

    @Override
    public Map<String, List<String>> getEnumDataMap() {
        return this.enumDataMap;
    }

    @Override
    public IEntityRow getEntityData(String entityId, String entityCode) {
        if (this.entityRowMap.containsKey(entityId)) {
            return this.entityRowMap.get(entityId).get(entityCode);
        }
        EntityViewDefine entityViewDefine = entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery query = entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        if (this.queryVersionDate != null) {
            query.setQueryVersionDate(this.queryVersionDate);
        }
        query.setAuthorityOperations(AuthorityType.Read);
        query.maskedData();
        try {
            IEntityTable entityTable = query.executeReader((IContext)new ExecutorContext(dataDefinitionRuntimeController));
            Map collect = entityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, Function.identity()));
            this.entityRowMap.put(entityId, collect);
            return (IEntityRow)collect.get(entityCode);
        }
        catch (Exception e) {
            throw new ExcelException(e);
        }
    }

    @Override
    public Map<String, List<ConditionalStyle>> link2ConditionalStyle() {
        return this.link2ConditionalStyle;
    }

    @Override
    public List<ConditionalStyle> getConditionalStyleByForm(IConditionalStyleController conditionalStyleController, String formKey) {
        List<Object> conditionalStyles;
        if (this.form2ConditionalStyle.containsKey(formKey)) {
            conditionalStyles = this.form2ConditionalStyle.get(formKey);
        } else {
            List allCSInForm = conditionalStyleController.getAllCSInForm(formKey);
            conditionalStyles = CollectionUtils.isEmpty(allCSInForm) ? Collections.emptyList() : allCSInForm.stream().sorted(Comparator.comparing(ConditionalStyle::getOrder).reversed()).collect(Collectors.toList());
            this.form2ConditionalStyle.put(formKey, conditionalStyles);
        }
        return conditionalStyles;
    }

    @Override
    public Map<String, Set<DimensionValueSet>> unconditionalStyleDims() {
        return this.unconditionalStyleDims;
    }

    @Override
    public boolean autoFillIsNullTable() {
        return this.autoFillIsNullTable;
    }

    @Override
    public ExcelExportEnv getExcelExportEnv(DimensionCombination dimensionCombination) {
        this.env.setDimensionCombination(dimensionCombination);
        return this.env;
    }

    @Override
    public List<String> getFileGroupKeys() {
        return this.fileGroupKeys;
    }

    @Override
    @NonNull
    public DataValueFormatter getDataValueFormatter(String formKey) {
        String measureUnit = this.getFormByKey(formKey).getMeasureUnit();
        if (this.formDataValueFormatters.containsKey(measureUnit)) {
            return this.formDataValueFormatters.get(measureUnit);
        }
        DataValueFormatter dataValueFormatter = this.buildFormDataValueFormatter(formKey);
        this.formDataValueFormatters.put(measureUnit, dataValueFormatter);
        return dataValueFormatter;
    }

    @Override
    public IEntityDefine getEntityDefine(String entityId) {
        if (this.entityDefineMap.containsKey(entityId)) {
            return this.entityDefineMap.get(entityId);
        }
        IEntityDefine entity = entityMetaService.queryEntity(entityId);
        this.entityDefineMap.put(entityId, entity);
        return entity;
    }

    @Override
    public String getZeroShow() {
        return this.zeroShow;
    }

    @Override
    public String getDataSnapshotId() {
        return this.dataSnapshotId;
    }

    public void setDataSnapshotId(String dataSnapshotId) {
        this.dataSnapshotId = dataSnapshotId;
    }

    @Override
    public ExportOps getExportOps() {
        return this.ops;
    }

    @Override
    public FormSchemeDefine getCurFormScheme() {
        return this.curFormScheme;
    }

    @Override
    public FilterSort getFilterSort(DimensionCombination dimensionCombination) {
        String dimensionName = entityMetaService.getDimensionName(this.curFormScheme.getDw());
        String dwCode = String.valueOf(dimensionCombination.getValue(dimensionName));
        if (this.filterSortMap.containsKey(dwCode)) {
            return this.filterSortMap.get(dwCode);
        }
        FilterSort filterSort = ExportUtil.getFilterSort(this.ops.getConditions(), dimensionCombination, this);
        this.filterSortMap.put(dwCode, filterSort);
        return filterSort;
    }

    @Override
    public boolean isGridDataFormatted() {
        return this.gridDataFormatted;
    }

    public void setGridDataFormatted(boolean gridDataFormatted) {
        this.gridDataFormatted = gridDataFormatted;
    }

    @Override
    public ReportFmlExecEnvironment getFmlExecEnv(IRunTimeViewController runTimeViewController, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IEntityViewRunTimeController entityViewRunTimeController) {
        if (this.fmlExecEnvironment == null) {
            this.fmlExecEnvironment = new ReportFmlExecEnvironment(runTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, this.curFormScheme.getKey(), null, null);
        }
        return this.fmlExecEnvironment;
    }

    @Override
    public List<ExpFormFolding> getExpFormFoldingOps(String formKey) {
        List<ExpFormFolding> result;
        if (this.formFoldingMap.containsKey(formKey)) {
            return this.formFoldingMap.get(formKey);
        }
        Map<String, List<ExpFormFolding>> expFormFoldings = this.getExportOps().getExpFormFoldings();
        if (!CollectionUtils.isEmpty(expFormFoldings) && expFormFoldings.containsKey(formKey)) {
            List<ExpFormFolding> opsFormFoldings = expFormFoldings.get(formKey);
            result = !CollectionUtils.isEmpty(opsFormFoldings) ? new ArrayList<ExpFormFolding>(opsFormFoldings) : Collections.emptyList();
        } else {
            List formFoldingDefines = runTimeViewController.listFormFoldingByFormKey(formKey);
            if (!CollectionUtils.isEmpty(formFoldingDefines)) {
                result = new ArrayList();
                formFoldingDefines.forEach(o -> result.add(new ExpFormFolding((FormFoldingDefine)o)));
            } else {
                result = Collections.emptyList();
            }
        }
        this.formFoldingMap.put(formKey, result);
        return result;
    }

    @Override
    public ExcelPrintSetup getExcelPrintSetup(String printSchemeKey, String formKey) {
        PrintSettingDefine printSettingDefine = this.getPrintSettingDefine(printSchemeKey, formKey);
        if (printSettingDefine == null) {
            printSettingDefine = this.runTimePrintController.getDefaultPrintSettingDefine(printSchemeKey, formKey);
        }
        return new ExcelPrintSetup(printSettingDefine);
    }

    @Nullable
    private PrintSettingDefine getPrintSettingDefine(String printSchemeKey, String formKey) {
        if (StringUtils.isEmpty((String)printSchemeKey) || StringUtils.isEmpty((String)formKey)) {
            return null;
        }
        if (this.printSettingMap.containsKey(printSchemeKey)) {
            Map<String, PrintSettingDefine> mapByForm = this.printSettingMap.get(printSchemeKey);
            return mapByForm.get(formKey);
        }
        List printSettingDefines = this.runTimePrintController.listPrintSettingDefine(printSchemeKey);
        if (CollectionUtils.isEmpty(printSettingDefines)) {
            this.printSettingMap.put(printSchemeKey, Collections.emptyMap());
            return null;
        }
        HashMap<String, PrintSettingDefine> mapByForm = new HashMap<String, PrintSettingDefine>();
        this.printSettingMap.put(printSchemeKey, mapByForm);
        PrintSettingDefine result = null;
        for (PrintSettingDefine printSettingDefine : printSettingDefines) {
            String printSettingFormKey = printSettingDefine.getFormKey();
            if (formKey.equals(printSettingFormKey)) {
                result = printSettingDefine;
            }
            mapByForm.put(printSettingFormKey, printSettingDefine);
        }
        return result;
    }

    @Override
    public boolean isMeasure() {
        return this.ops.getExportMeasureSetting() != null && (StringUtils.isNotEmpty((String)this.ops.getExportMeasureSetting().getKey()) && StringUtils.isNotEmpty((String)this.ops.getExportMeasureSetting().getCode()) || this.ops.getExportMeasureSetting().getDecimal() >= 0);
    }

    @Override
    @Nullable
    public IDataValueBalanceActuator getBalanceActuator(String formKey) {
        String measureUnit = this.getFormByKey(formKey).getMeasureUnit();
        if (this.formDataValueBalanceActuators.containsKey(measureUnit)) {
            return this.formDataValueBalanceActuators.get(measureUnit);
        }
        IDataValueBalanceActuator dataValueBalanceActuator = this.buildFormBalanceActuator(formKey);
        this.formDataValueBalanceActuators.put(measureUnit, dataValueBalanceActuator);
        return dataValueBalanceActuator;
    }

    @Nullable
    private IDataValueBalanceActuator buildFormBalanceActuator(String formKey) {
        if (this.formUseMeasure(formKey)) {
            IDataValueBalanceActuator dataValueBalanceActuator = this.dataValueBalanceActuatorFactory.getDataValueBalanceActuator();
            Measure measure = this.ops.getExportMeasureSetting().toMeasure();
            if (measure != null) {
                dataValueBalanceActuator.setMeasure(measure);
            }
            Integer measureDecimal = this.getMeasureDecimal(formKey);
            dataValueBalanceActuator.setNumDecimalPlaces(measureDecimal);
            return dataValueBalanceActuator;
        }
        return null;
    }

    @NonNull
    private DataValueFormatter buildFormDataValueFormatter(String formKey) {
        DataValueFormatterBuilder formatterBuilder = this.builderFactory.createFormatterBuilder().installFormatStrategy();
        SysNumberTypeStrategy sysNumberTypeStrategy = this.typeStrategyUtil.initSysNumberTypeStrategy();
        sysNumberTypeStrategy.setThousands(this.ops.getThousands());
        if (this.formUseMeasure(formKey)) {
            Measure measure = this.ops.getExportMeasureSetting().toMeasure();
            if (measure != null) {
                sysNumberTypeStrategy.setSelectMeasure(measure);
            }
            sysNumberTypeStrategy.setEnableBalanceFormula(this.isGridDataFormatted());
            Integer measureDecimal = this.getMeasureDecimal(formKey);
            sysNumberTypeStrategy.setNumDecimalPlaces(measureDecimal);
        }
        sysNumberTypeStrategy.setGlobalNumDecimalPlaces(this.ops.getDisplayDecimalPlaces());
        formatterBuilder.registerFormatStrategy(DataFieldType.INTEGER.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
        formatterBuilder.registerFormatStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
        formatterBuilder.registerFormatStrategy(DataFieldType.STRING.getValue(), (TypeFormatStrategy)this.exportEnumFormatStrategy);
        return formatterBuilder.build();
    }

    private boolean formUseMeasure(String formKey) {
        return this.isMeasure() && ExportUtil.formOpenMeasure(this.getFormByKey(formKey));
    }

    @Override
    public Integer getMeasureDecimal(String formKey) {
        String measureUnit = this.getFormByKey(formKey).getMeasureUnit();
        if (this.formMeasureDecimals.containsKey(measureUnit)) {
            return this.formMeasureDecimals.get(measureUnit);
        }
        Integer formMeasureDecimal = this.buildFormMeasureDecimal(formKey);
        this.formMeasureDecimals.put(measureUnit, formMeasureDecimal);
        return formMeasureDecimal;
    }

    private Integer buildFormMeasureDecimal(String formKey) {
        String measureValue;
        String[] measureStr;
        FormDefine formByKey;
        String measureUnit;
        ExportMeasureSetting exportMeasureSetting = this.ops.getExportMeasureSetting();
        if (exportMeasureSetting != null && StringUtils.isNotEmpty((String)(measureUnit = (formByKey = this.getFormByKey(formKey)).getMeasureUnit())) && (measureStr = measureUnit.split(";")).length == 2 && !"NotDimession".equalsIgnoreCase(measureValue = measureStr[1])) {
            int decimal = exportMeasureSetting.getDecimal();
            if (decimal < 0) {
                String exportMeasureCode = exportMeasureSetting.getCode();
                return StringUtils.isEmpty((String)exportMeasureCode) || measureValue.equals(exportMeasureCode) ? null : this.measureChangeDefaultDecimal;
            }
            return decimal;
        }
        return null;
    }

    @Override
    public void initCellStyleCache(@NonNull Grid2Data formStyle) {
        int rowCount = formStyle.getRowCount();
        int columnCount = formStyle.getColumnCount();
        this.cellStyleCache = new HashMap<String, GridCellStyleData>(rowCount * columnCount);
        for (int r = 0; r < rowCount; ++r) {
            for (int c = 0; c < columnCount; ++c) {
                GridCellData gridCellData = formStyle.getGridCellData(c, r);
                if (gridCellData == null) continue;
                gridCellData.setDataExFromString(null);
                GridCellStyleData cellStyleData = gridCellData.getCellStyleData();
                String cellStyleCacheKey = this.getCellStyleCacheKey(r, c);
                this.cellStyleCache.put(cellStyleCacheKey, cellStyleData);
            }
        }
    }

    @Override
    public GridCellStyleData getCellStyle(int rowIndex, int colIndex) {
        String cellStyleCacheKey = this.getCellStyleCacheKey(rowIndex, colIndex);
        return this.cellStyleCache.get(cellStyleCacheKey);
    }

    private String getCellStyleCacheKey(int rowIndex, int colIndex) {
        return rowIndex + ";" + colIndex;
    }

    @Override
    @NonNull
    public List<Fml> getParsedFormulasByForm(String formKey) {
        if (this.fmlCache.containsKey(formKey)) {
            return this.fmlCache.get(formKey);
        }
        ArrayList<Fml> parsedFormulas = new ArrayList<Fml>();
        this.fmlCache.put(formKey, parsedFormulas);
        Collection calcCellDataLinks = this.formulaRunTimeController.getCalcCellDataLinks(this.getExportOps().getFormulaSchemeKey(), formKey);
        if (CollectionUtils.isEmpty(calcCellDataLinks)) {
            return parsedFormulas;
        }
        block2: for (String calcCellDataLink : calcCellDataLinks) {
            List parsedExpressionByDataLink;
            DataLinkDefine dataLink = this.getDataLink(calcCellDataLink);
            if (dataLink == null || CollectionUtils.isEmpty(parsedExpressionByDataLink = this.formulaRunTimeController.getParsedExpressionByDataLink(dataLink.getUniqueCode(), this.getExportOps().getFormulaSchemeKey(), null, DataEngineConsts.FormulaType.CALCULATE))) continue;
            for (IParsedExpression expression : parsedExpressionByDataLink) {
                Fml fml;
                block8: {
                    if (expression.getAssignNode() == null || expression.getAssignNode().getDataModelLink() == null || !dataLink.getUniqueCode().equals(expression.getAssignNode().getDataModelLink().getDataLinkCode())) continue;
                    if (expression instanceof CheckExpression) {
                        try {
                            if (!((CheckExpression)expression).support(Language.EXCEL)) {
                                logger.debug("{}\u4e0d\u652f\u6301Excel\u8bed\u6cd5\u8f6c\u8bd1", (Object)expression.getSource().getFormula());
                            }
                            break block8;
                        }
                        catch (Exception e) {
                            logger.debug("{}\u4e0d\u652f\u6301Excel\u8bed\u6cd5\u8f6c\u8bd1:{}", expression.getSource().getFormula(), e.getMessage(), e);
                        }
                        continue;
                    }
                }
                if ((fml = this.parseExpression(expression)) == null) continue;
                parsedFormulas.add(fml);
                continue block2;
            }
        }
        return parsedFormulas;
    }

    @Nullable
    private Fml parseExpression(IParsedExpression expr) {
        DynamicDataNode assignNode = expr.getAssignNode();
        IExpression realExpression = expr.getRealExpression();
        if (assignNode == null || realExpression == null) {
            return null;
        }
        DataModelLinkColumn assignDataModelLink = assignNode.getDataModelLink();
        if (assignDataModelLink == null || assignDataModelLink.getReportInfo() == null) {
            return null;
        }
        String assignDataLinkCode = assignDataModelLink.getDataLinkCode();
        String formKey = assignDataModelLink.getReportInfo().getReportKey();
        DataLinkDefine assignDataLink = runTimeViewController.queryDataLinkDefineByUniquecode(formKey, assignDataLinkCode);
        if (assignDataLink == null) {
            return null;
        }
        String regionKey = assignDataLink.getRegionKey();
        DataRegionDefine assignRegion = runTimeViewController.queryDataRegionDefine(regionKey);
        if (assignRegion == null) {
            return null;
        }
        Fml fml = new Fml();
        FmlNode assignFmlNode = new FmlNode();
        assignFmlNode.setDataLinkKey(assignDataLink.getKey());
        assignFmlNode.setPosX(assignDataLink.getPosX());
        assignFmlNode.setPosY(assignDataLink.getPosY());
        assignFmlNode.setFormKey(formKey);
        assignFmlNode.setRegionKey(regionKey);
        assignFmlNode.setDataRegionKind(assignRegion.getRegionKind());
        fml.setAssignNode(assignFmlNode);
        fml.setExpressionKey(expr.getKey());
        ArrayList<FmlNode> formulaNodes = new ArrayList<FmlNode>();
        fml.setNodes(formulaNodes);
        boolean assignFloatRegion = assignRegion.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || assignRegion.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST;
        for (IASTNode child : realExpression) {
            boolean nodeFloatRegion;
            DataModelLinkColumn dataLink;
            if (!(child instanceof DynamicDataNode) || null == (dataLink = ((DynamicDataNode)child).getDataModelLink())) continue;
            if (dataLink.getReportInfo() == null || StringUtils.isEmpty((String)dataLink.getReportInfo().getReportKey())) {
                return null;
            }
            String nodeFormKey = dataLink.getReportInfo().getReportKey();
            DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefineByUniquecode(nodeFormKey, dataLink.getDataLinkCode());
            if (dataLinkDefine == null) {
                return null;
            }
            String nodeRegionKey = dataLinkDefine.getRegionKey();
            DataRegionDefine nodeRegion = runTimeViewController.queryDataRegionDefine(nodeRegionKey);
            if (nodeRegion == null) {
                return null;
            }
            boolean bl = nodeFloatRegion = nodeRegion.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || nodeRegion.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST;
            if (assignFloatRegion && nodeFloatRegion && !assignRegion.getKey().equals(nodeRegion.getKey())) {
                return null;
            }
            FmlNode fmlNode = new FmlNode();
            fmlNode.setDataLinkKey(dataLinkDefine.getKey());
            fmlNode.setPosX(dataLinkDefine.getPosX());
            fmlNode.setPosY(dataLinkDefine.getPosY());
            fmlNode.setFormKey(nodeFormKey);
            fmlNode.setRegionKey(nodeRegionKey);
            fmlNode.setDataRegionKind(nodeRegion.getRegionKind());
            formulaNodes.add(fmlNode);
        }
        return fml;
    }

    @Override
    public boolean expFml() {
        if (this.expFml == null) {
            this.expFml = StringUtils.isEmpty((String)this.getDataSnapshotId()) && (this.getExportOps().isExpFml() || this.exportOptionsService.expExcelFormula(this.getCurFormScheme().getTaskKey()));
        }
        return this.expFml;
    }

    @Override
    @NonNull
    public Map<String, CustomGridCellStyle> getCustomGridCellStyle(DimensionCombination dimensionCombination, String formKey) {
        if (this.customCellStyleProvider == null) {
            return Collections.emptyMap();
        }
        Map<String, CustomGridCellStyle> result = this.customCellStyleProvider.provideCustomCellStyles(dimensionCombination, formKey);
        return result == null ? Collections.emptyMap() : result;
    }

    @Override
    public void initBatchQueryDims(FormSchemeDefine formScheme, List<FormDefine> expForms, DimensionCollection dimensionCollection) {
        Integer batchQueryDimCount = this.getBatchQueryDimCount(expForms);
        logger.debug("batchQueryDimCount: {}", (Object)batchQueryDimCount);
        if (batchQueryDimCount == null || batchQueryDimCount <= 0) {
            return;
        }
        this.batchQuerySplitDims = new ArrayList<DimensionCollection>();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        int totalSize = dimensionCombinations.size();
        logger.debug("totalSize: {}", (Object)totalSize);
        for (int i = 0; i < totalSize; i += batchQueryDimCount.intValue()) {
            int end = Math.min(i + batchQueryDimCount, totalSize);
            List splitDims = dimensionCombinations.subList(i, end);
            DimensionValueSet merge = ExportUtil.merge(splitDims.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList()));
            this.batchQuerySplitDims.add(this.dimensionBuildUtil.getDimensionCollection(merge, formScheme.getKey(), (SpecificDimBuilder)FixedDimBuilder.getInstance()));
            logger.debug("batchQuerySplitDim: {}", (Object)merge);
        }
    }

    @Nullable
    private Integer getBatchQueryDimCount(List<FormDefine> expForms) {
        if (CollectionUtils.isEmpty(expForms)) {
            logger.debug("expForms is empty");
            return null;
        }
        int memPerfLevel = this.exportOptionsService.getMemPerfLevel();
        logger.debug("memPerfLevel = {}", (Object)memPerfLevel);
        if (memPerfLevel <= 0) {
            return null;
        }
        int linksCount = 0;
        for (FormDefine form : expForms) {
            List allLinksInRegion;
            Optional<DataRegionDefine> any;
            List allRegionsInForm = runTimeViewController.getAllRegionsInForm(form.getKey());
            if (CollectionUtils.isEmpty(allRegionsInForm) || !(any = allRegionsInForm.stream().filter(r -> DataRegionKind.DATA_REGION_SIMPLE == r.getRegionKind()).findAny()).isPresent() || CollectionUtils.isEmpty(allLinksInRegion = runTimeViewController.getAllLinksInRegion(any.get().getKey()))) continue;
            linksCount += allLinksInRegion.size();
        }
        if (linksCount <= 0) {
            return null;
        }
        return 0x4B00000 * memPerfLevel / (linksCount * 40);
    }

    @Override
    public ISheetNameProvider getSheetNameProvider() {
        return this.sheetNameProvider;
    }

    public void setSheetNameProvider(ISheetNameProviderFactory sheetNameProviderFactory, BaseExpPar expPar, SheetNameType sheetNameType) {
        if (sheetNameProviderFactory == null) {
            return;
        }
        DimensionCollection dimensionCollection = null;
        if (expPar instanceof SingleExpPar) {
            SingleExpPar singleExpPar = (SingleExpPar)expPar;
            dimensionCollection = ExportUtil.toDimensionCollection(singleExpPar.getDimensionCombination());
        } else if (expPar instanceof BatchExpPar) {
            BatchExpPar batchExpPar = (BatchExpPar)expPar;
            dimensionCollection = batchExpPar.getDimensionCollection();
        }
        TitleShowSetting titleShowSetting = expPar.getOps().getTitleShowSetting();
        String sysSeparator = this.exportOptionsService.getSysSeparator(titleShowSetting);
        ShowSetting showSetting = new ShowSetting(this.getDwShows(titleShowSetting), this.getFormShows(titleShowSetting), sheetNameType, sysSeparator);
        SheetNameEnv sheetNameEnv = new SheetNameEnv(this.getCurFormScheme(), dimensionCollection, showSetting);
        this.sheetNameProvider = sheetNameProviderFactory.getSheetNameProvider(sheetNameEnv);
    }

    private List<DWShow> getDwShows(TitleShowSetting titleShowSetting) {
        String excelNameOp = titleShowSetting != null && titleShowSetting.getDwShowSetting() != null ? titleShowSetting.getDwShowSetting() : this.systemOptionService.get("nr-data-entry-export", "EXCEL_NAME");
        String[] excelNameCom = excelNameOp.split("[\\[\\]\",\\s]");
        ArrayList<DWShow> result = new ArrayList<DWShow>(4);
        for (String op : excelNameCom) {
            if ("0".equals(op)) {
                result.add(DWShow.TITLE);
                continue;
            }
            if ("1".equals(op)) {
                result.add(DWShow.CODE);
                continue;
            }
            if ("2".equals(op)) {
                result.add(DWShow.PERIOD_TITLE);
                continue;
            }
            if (!"3".equals(op)) continue;
            result.add(DWShow.TASK_TITLE);
        }
        if (result.isEmpty()) {
            result.add(DWShow.TITLE);
        }
        return result;
    }

    private List<FormShow> getFormShows(TitleShowSetting titleShowSetting) {
        String sheetNameOp = titleShowSetting != null && titleShowSetting.getFormShowSetting() != null ? titleShowSetting.getFormShowSetting() : this.systemOptionService.get("nr-data-entry-export", "SHEET_NAME");
        String[] sheetNameCom = sheetNameOp.split("[\\[\\]\",\\s]");
        ArrayList<FormShow> result = new ArrayList<FormShow>(3);
        for (String op : sheetNameCom) {
            if ("0".equals(op)) {
                result.add(FormShow.TITLE);
                continue;
            }
            if ("1".equals(op)) {
                result.add(FormShow.CODE);
                continue;
            }
            if (!"2".equals(op)) continue;
            result.add(FormShow.SERIAL_NUM);
        }
        if (result.isEmpty()) {
            result.add(FormShow.CODE);
            result.add(FormShow.TITLE);
        }
        return result;
    }
}

