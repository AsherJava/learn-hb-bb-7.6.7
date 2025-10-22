/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.access.api.IStateSecretLevelService
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.excel.consts.BatchExportConsts;
import com.jiuqi.nr.data.excel.exception.ExcelException;
import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.param.DataInfo;
import com.jiuqi.nr.data.excel.param.Directory;
import com.jiuqi.nr.data.excel.param.Excel;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.data.excel.param.GenerateParam;
import com.jiuqi.nr.data.excel.param.ParseParam;
import com.jiuqi.nr.data.excel.param.Sheet;
import com.jiuqi.nr.data.excel.param.SheetType;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.data.excel.service.impl.DimensionDataAdapterProviderImpl;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapterProvider;
import com.jiuqi.nr.data.excel.service.internal.IExportOptionsService;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="defaultExcelRule")
public class DefaultExcelRule
implements ExcelRule {
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IExportOptionsService exportOptionsService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private static final Logger logger = LoggerFactory.getLogger(DefaultExcelRule.class);

    @Override
    public List<Directory> generateExportInfo(GenerateParam generateParam) {
        FormSchemeDefine formSchemeDefine = generateParam.getFormSchemeDefine();
        TitleShowSetting titleShowSetting = generateParam.getTitleShowSetting();
        TaskDefine queryTaskDefine = this.viewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String taskName = queryTaskDefine.getTitle();
        String dwEntityId = this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dwEntityId);
        Map dimAttributeMap = this.dataSchemeService.getDataSchemeDimension(queryTaskDefine.getDataScheme()).stream().filter(e -> DimensionType.DIMENSION.equals((Object)e.getDimensionType())).collect(HashMap::new, (map, param) -> map.put(param.getDimKey(), param.getDimAttribute()), HashMap::putAll);
        ArrayList<String> singleDims = new ArrayList<String>();
        DimensionCollection dimensionCollection = generateParam.getDimensionCollection();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        ArrayList<String> dimNames = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)formSchemeDefine.getDims())) {
            for (String dim : formSchemeDefine.getDims().split(";")) {
                IEntityAttribute entityAttribute;
                String attribute = (String)dimAttributeMap.get(dim);
                String dimName = this.entityMetaService.getDimensionName(dim);
                if (StringUtils.isNotEmpty((String)attribute) && (entityAttribute = entityModel.getAttribute(attribute)) != null && !entityAttribute.isMultival()) {
                    singleDims.add(dimName);
                    continue;
                }
                dimNames.add(dimName);
            }
        }
        String dwDimName = this.entityMetaService.getDimensionName(dwEntityId);
        String periodDimName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        String pValue = String.valueOf(((DimensionCombination)dimensionCombinations.get(0)).getValue(periodDimName));
        DimensionValueSet mergeDimensionValueSet = ExportUtil.getMergeDimensionValueSet(dimensionCollection);
        DimensionDataAdapterProviderImpl dimensionDataAdapterProvider = new DimensionDataAdapterProviderImpl(formSchemeDefine, pValue, mergeDimensionValueSet);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime());
        String periodTitle = periodProvider.getPeriodTitle(pValue);
        boolean secretLevelEnable = this.stateSecretLevelService.secretLevelEnable(formSchemeDefine.getTaskKey());
        boolean enableAdjustPeriod = this.formSchemeService.enableAdjustPeriod(formSchemeDefine.getKey());
        ArrayList<Directory> directories = new ArrayList<Directory>();
        String sysSeparator = this.exportOptionsService.getSysSeparator(titleShowSetting);
        HashMap singleDimOrgValues = new HashMap();
        singleDims.forEach(key -> {
            Map cfr_ignored_0 = singleDimOrgValues.put(key, new HashMap());
        });
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            String dwShow;
            String string;
            String dwValue = String.valueOf(dimensionCombination.getValue(dwDimName));
            for (String string2 : singleDims) {
                Map dimMap = (Map)singleDimOrgValues.get(string2);
                String dimValue = String.valueOf(dimensionCombination.getValue(string2));
                if (dimMap.get(dwValue) == null) {
                    dimMap.put(dwValue, dimValue);
                    continue;
                }
                if (((String)dimMap.get(dwValue)).equals(dimValue)) continue;
                throw new ExcelException("\u5355\u4f4d\u3010" + dwValue + "\u3011\u7684\u5355\u503c\u7ef4\u5ea6\u3010" + string2 + "\u3011\u5b58\u5728\u591a\u4e2a\u503c\uff0c\u53ef\u80fd\u4e3a\u53c2\u6570\u9519\u8bef\uff01\u8bf7\u68c0\u67e5\u53c2\u6570");
            }
            StringBuilder directoryPath = new StringBuilder();
            directoryPath.append(periodTitle).append(BatchExportConsts.SEPARATOR);
            if (enableAdjustPeriod && !"0".equals(string = String.valueOf(dimensionCombination.getValue("ADJUST")))) {
                IDimensionDataAdapter adapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName("ADJUST");
                String title = adapter.getTitle(string);
                directoryPath.append(title).append(BatchExportConsts.SEPARATOR);
            }
            for (String dimName : dimNames) {
                String value = String.valueOf(dimensionCombination.getValue(dimName));
                IDimensionDataAdapter adapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName(dimName);
                String title = adapter.getTitle(value);
                directoryPath.append(title).append(BatchExportConsts.SEPARATOR);
            }
            Directory directory = new Directory(String.valueOf(directoryPath));
            IDimensionDataAdapter dwAdapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName(dwDimName);
            DimensionData dwData = dwAdapter.getDimensionData(dwValue);
            if (secretLevelEnable) {
                SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(dimensionCombination.toDimensionValueSet(), formSchemeDefine.getKey());
                dwShow = this.exportOptionsService.getDWShow(titleShowSetting, dwData, periodTitle, taskName, secretLevel);
            } else {
                dwShow = this.exportOptionsService.getDWShow(titleShowSetting, dwData, periodTitle, taskName);
            }
            if (directories.contains(directory)) {
                Directory directory2 = (Directory)directories.get(directories.indexOf(directory));
                if (directory2.getExcels() == null) {
                    directory2.setExcels(new ArrayList<Excel>());
                }
                Excel e2 = new Excel(directory2, dwShow);
                directory2.getExcels().add(e2);
            } else {
                directories.add(directory);
                directory.setExcels(new ArrayList<Excel>());
                directory.getExcels().add(new Excel(directory, dwShow));
            }
            HashMap repetitiveSheetMap = new HashMap();
            Directory realDir = (Directory)directories.get(directories.indexOf(directory));
            List<Excel> excels = realDir.getExcels();
            Excel excel = excels.get(excels.size() - 1);
            if (excel.getSheets() == null) {
                excel.setSheets(new ArrayList<Sheet>());
            }
            Map formMap = generateParam.getFormDefines().stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity(), (o1, o2) -> o1));
            for (FormDefine formDefine : generateParam.getFormDefines()) {
                block28: {
                    IAccessResult access = generateParam.getBatchAccessResult().getAccess(dimensionCombination, formDefine.getKey());
                    try {
                        if (!access.haveAccess()) {
                        }
                        break block28;
                    }
                    catch (Exception e3) {
                        logger.error(e3.getMessage(), e3);
                    }
                    continue;
                }
                String sheetName = this.exportOptionsService.getFormShow(titleShowSetting, formDefine);
                Sheet sheet = new Sheet(dimensionCombination, formDefine.getKey(), excel, sheetName, SheetType.NORMAL);
                if (excel.getSheets().contains(sheet)) {
                    if (repetitiveSheetMap.containsKey(sheetName)) {
                        ((List)repetitiveSheetMap.get(sheetName)).add(sheet);
                    } else {
                        Sheet repetitiveFirstSheet = excel.getSheets().get(excel.getSheets().indexOf(sheet));
                        ArrayList<Sheet> sheets = new ArrayList<Sheet>();
                        sheets.add(repetitiveFirstSheet);
                        sheets.add(sheet);
                        repetitiveSheetMap.put(sheetName, sheets);
                    }
                }
                excel.getSheets().add(sheet);
            }
            if (CollectionUtils.isEmpty(excel.getSheets())) {
                excels.remove(excel);
            }
            if (CollectionUtils.isEmpty(realDir.getExcels())) {
                directories.remove(realDir);
            }
            for (Map.Entry entry : repetitiveSheetMap.entrySet()) {
                for (Sheet sheet : (List)entry.getValue()) {
                    sheet.setSheetName((String)entry.getKey() + sysSeparator + ((FormDefine)formMap.get(sheet.getFormKey())).getFormCode());
                }
            }
        }
        for (Directory directory : directories) {
            Map<String, List<Excel>> collect = directory.getExcels().stream().collect(Collectors.groupingBy(Excel::toString));
            for (Map.Entry entry : collect.entrySet()) {
                if (((List)entry.getValue()).size() <= 1) continue;
                for (Excel excel : (List)entry.getValue()) {
                    String dwValue = String.valueOf(excel.getSheets().get(0).getDimensionCombination().getValue(dwDimName));
                    IDimensionDataAdapter dwAdapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName(dwDimName);
                    DimensionData dwData = dwAdapter.getDimensionData(dwValue);
                    excel.setFileName(excel.getFileName() + sysSeparator + dwData.getCode());
                }
            }
        }
        return directories;
    }

    @Override
    public DataInfo parseDataInfo(ParseParam parseParam) {
        IDimensionDataAdapterProvider dimensionDataAdapterProvider = parseParam.getDimensionDataAdapterProvider();
        FormSchemeDefine formSchemeDefine = parseParam.getFormSchemeDefine();
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(formSchemeDefine.getDateTime());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime());
        Map<String, List<IPeriodRow>> periodItemGroupByTitle = ExportUtil.getPeriodItemGroupByTitle(periodProvider);
        String[] dimEntityIds = formSchemeDefine.getDims().split(";");
        boolean enableAdjustPeriod = this.formSchemeService.enableAdjustPeriod(formSchemeDefine.getKey());
        String[] dims = parseParam.getDirectory().split(BatchExportConsts.SEPARATOR);
        String dwShow = parseParam.getFileName();
        String formShow = parseParam.getSheetName();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        int dimIndex = 0;
        for (int i = 0; i < dims.length; ++i) {
            if (i == 0) {
                String periodTitle = dims[i];
                String dateValue = ExportUtil.getDateValue(periodTitle, periodItemGroupByTitle);
                dimensionCombinationBuilder.setValue(periodEntity.getDimensionName(), formSchemeDefine.getDateTime(), (Object)dateValue);
                if (dimensionDataAdapterProvider != null) continue;
                dimensionDataAdapterProvider = new DimensionDataAdapterProviderImpl(formSchemeDefine, dateValue);
                continue;
            }
            if (i == 1 && enableAdjustPeriod) {
                dimensionCombinationBuilder.setValue("ADJUST", "ADJUST", (Object)dimensionDataAdapterProvider.getDimensionDataAdapterByName("ADJUST").getByTitle(dims[i]).get(0).getKeyData());
                continue;
            }
            String dimTitle = dims[i];
            String dimensionName = this.entityMetaService.getDimensionName(dimEntityIds[dimIndex]);
            dimensionCombinationBuilder.setValue(dimensionName, dimEntityIds[dimIndex], (Object)dimensionDataAdapterProvider.getDimensionDataAdapterByName(dimensionName).getByTitle(dimTitle).get(0).getKeyData());
        }
        String dwEntityId = this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw());
        dimensionCombinationBuilder.setDWValue(this.entityMetaService.getDimensionName(dwEntityId), dwEntityId, (Object)ExportUtil.getDw(dwShow, dimensionDataAdapterProvider.getDimensionDataAdapterById(dwEntityId), parseParam.getDwShowSetting(), parseParam.getSplitCharSetting()));
        return new DataInfo(dimensionCombinationBuilder.getCombination(), ExportUtil.getForm(formShow, parseParam.getFormFinder(), parseParam.getFormShowSetting(), parseParam.getSplitCharSetting()));
    }

    @Override
    public String name() {
        return "defaultExcelRule";
    }
}

