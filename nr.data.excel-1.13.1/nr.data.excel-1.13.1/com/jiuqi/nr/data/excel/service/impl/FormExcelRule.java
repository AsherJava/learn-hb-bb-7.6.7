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
import com.jiuqi.nr.data.excel.extend.param.SheetNameType;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="formExcelRule")
public class FormExcelRule
implements ExcelRule {
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
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
    private static final Logger logger = LoggerFactory.getLogger(FormExcelRule.class);

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
        List<FormDefine> formDefines = generateParam.getFormDefines();
        Map<String, String> formShowMap = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, o -> this.exportOptionsService.getFormShow(titleShowSetting, (FormDefine)o)));
        HashSet<String> dirFormKeys = new HashSet<String>();
        HashMap singleDimOrgValues = new HashMap();
        singleDims.forEach(key -> {
            Map cfr_ignored_0 = singleDimOrgValues.put(key, new HashMap());
        });
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Directory realDir;
            Object adjust;
            String dwValue = String.valueOf(dimensionCombination.getValue(dwDimName));
            for (String dim : singleDims) {
                Map dimMap = (Map)singleDimOrgValues.get(dim);
                String dimValue = String.valueOf(dimensionCombination.getValue(dim));
                if (dimMap.get(dwValue) == null) {
                    dimMap.put(dwValue, dimValue);
                    continue;
                }
                if (((String)dimMap.get(dwValue)).equals(dimValue)) continue;
                throw new ExcelException("\u5355\u4f4d\u3010" + dwValue + "\u3011\u7684\u5355\u503c\u7ef4\u5ea6\u3010" + dim + "\u3011\u5b58\u5728\u591a\u4e2a\u503c\uff0c\u53ef\u80fd\u4e3a\u53c2\u6570\u9519\u8bef\uff01\u8bf7\u68c0\u67e5\u53c2\u6570");
            }
            StringBuilder directory = new StringBuilder();
            directory.append(periodTitle).append(BatchExportConsts.SEPARATOR);
            if (enableAdjustPeriod && !"0".equals(adjust = String.valueOf(dimensionCombination.getValue("ADJUST")))) {
                IDimensionDataAdapter adapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName("ADJUST");
                String title = adapter.getTitle((String)adjust);
                directory.append(title).append(BatchExportConsts.SEPARATOR);
            }
            adjust = dimNames.iterator();
            while (adjust.hasNext()) {
                String dimName = (String)adjust.next();
                Iterator<FormDefine> value = String.valueOf(dimensionCombination.getValue(dimName));
                IDimensionDataAdapter adapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName(dimName);
                String title = adapter.getTitle((String)((Object)value));
                directory.append(title).append(BatchExportConsts.SEPARATOR);
            }
            Directory directory1 = new Directory(String.valueOf(directory));
            if (!directories.contains(directory1)) {
                directories.add(directory1);
            }
            if ((realDir = (Directory)directories.get(directories.indexOf(directory1))).getExcels() == null) {
                realDir.setExcels(new ArrayList<Excel>());
            }
            for (FormDefine formDefine : formDefines) {
                String dwShow;
                Excel excel;
                block27: {
                    IAccessResult access = generateParam.getBatchAccessResult().getAccess(dimensionCombination, formDefine.getKey());
                    try {
                        if (!access.haveAccess()) {
                        }
                        break block27;
                    }
                    catch (Exception exception) {
                        logger.error(exception.getMessage(), exception);
                    }
                    continue;
                }
                Excel excel2 = new Excel(realDir, formShowMap.get(formDefine.getKey()) + formDefine.getKey());
                if (dirFormKeys.add(realDir.getDirectory() + formDefine.getKey())) {
                    realDir.getExcels().add(excel2);
                }
                if ((excel = realDir.getExcels().get(realDir.getExcels().indexOf(excel2))).getSheets() == null) {
                    excel.setSheets(new ArrayList<Sheet>());
                }
                IDimensionDataAdapter dwAdapter = dimensionDataAdapterProvider.getDimensionDataAdapterByName(dwDimName);
                DimensionData dwData = dwAdapter.getDimensionData(dwValue);
                if (secretLevelEnable) {
                    SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(dimensionCombination.toDimensionValueSet(), formSchemeDefine.getKey());
                    dwShow = this.exportOptionsService.getDWShow(titleShowSetting, dwData, periodTitle, taskName, secretLevel);
                } else {
                    dwShow = this.exportOptionsService.getDWShow(titleShowSetting, dwData, periodTitle, taskName);
                }
                Sheet sheet = new Sheet(dimensionCombination, formDefine.getKey(), excel, dwShow, SheetType.NORMAL);
                excel.getSheets().add(sheet);
                if (!CollectionUtils.isEmpty(excel.getSheets())) continue;
                realDir.getExcels().remove(excel);
            }
            if (!CollectionUtils.isEmpty(realDir.getExcels())) continue;
            directories.remove(realDir);
        }
        String sysSeparator = this.exportOptionsService.getSysSeparator(titleShowSetting);
        for (Directory directory : directories) {
            directory.getExcels().forEach(o -> o.setFileName(o.getFileName().substring(0, o.getFileName().length() - 36)));
            Map<String, List<Excel>> excelGroupByFileName = directory.getExcels().stream().collect(Collectors.groupingBy(Excel::getFileName));
            for (Map.Entry<String, List<Excel>> entry : excelGroupByFileName.entrySet()) {
                if (entry.getValue().size() <= 1) continue;
                for (Excel excel1 : entry.getValue()) {
                    FormDefine form = this.runTimeViewController.queryFormById(excel1.getSheets().get(0).getFormKey());
                    excel1.setFileName(excel1.getFileName() + sysSeparator + form.getFormCode());
                }
            }
            for (Excel excel : directory.getExcels()) {
                Map<String, List<Sheet>> collect = excel.getSheets().stream().collect(Collectors.groupingBy(Sheet::getSheetName));
                ArrayList<Sheet> newSheets = new ArrayList<Sheet>();
                for (Map.Entry entry : collect.entrySet()) {
                    String sheetName = (String)entry.getKey();
                    List sheets = (List)entry.getValue();
                    if (sheets.size() > 1) {
                        for (Sheet sheet : sheets) {
                            String mdCode = String.valueOf(sheet.getDimensionCombination().getValue(dwDimName));
                            sheet.setSheetName(sheetName + sysSeparator + mdCode);
                            newSheets.add(sheet);
                        }
                        continue;
                    }
                    newSheets.add((Sheet)sheets.get(0));
                }
                ArrayList<Sheet> finalSheets = new ArrayList<Sheet>();
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    for (Sheet sheet : newSheets) {
                        if (!dimensionCombination.equals(sheet.getDimensionCombination())) continue;
                        finalSheets.add(sheet);
                        newSheets.remove(sheet);
                        break;
                    }
                    excel.setSheets(finalSheets);
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
        String dwShow = parseParam.getSheetName();
        String formShow = parseParam.getFileName();
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
        return "formExcelRule";
    }

    @Override
    public SheetNameType getSheetNameType() {
        return SheetNameType.DW;
    }
}

