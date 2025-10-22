/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

public class EnueSheetUtil {
    private static final Logger logger = LoggerFactory.getLogger(EnueSheetUtil.class);
    private static final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private static final IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private static final PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
    private static final IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
    private static final IFilterTemplateService filterTemplateService = (IFilterTemplateService)BeanUtil.getBean(IFilterTemplateService.class);
    private static IOEntityIsolateCondition entityIsolateCondition;

    private EnueSheetUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static IOEntityIsolateCondition getEntityIsolateCondition() {
        return entityIsolateCondition;
    }

    public static void setEntityIsolateCondition(IOEntityIsolateCondition entityIsolateCondition) {
        EnueSheetUtil.entityIsolateCondition = entityIsolateCondition;
    }

    public static void setEnueSheet(SXSSFWorkbook workbook, SheetInfo sheetInfo, ExportCache exportCache, int dropDownSize) {
        try {
            FormDefine formByKey = exportCache.getFormByKey(sheetInfo.getFormKey());
            if (formByKey.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                EnueSheetUtil.coverCodeTableEnumProcessing(workbook, sheetInfo, exportCache, dropDownSize);
            } else {
                EnueSheetUtil.enumProcessingInTable(workbook, sheetInfo, exportCache, dropDownSize);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private static void enumProcessingInTable(SXSSFWorkbook workbook, SheetInfo sheetInfo, ExportCache exportCache, int dropDownSize) throws Exception {
        List<DataRegionDefine> dataRegionDefineList = exportCache.getSortedRegionsByForm(sheetInfo.getFormKey());
        int shiftNum = 0;
        for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
            List<String> fieldKeys;
            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                fieldKeys = exportCache.getFieldKeysInRegion(dataRegionDefine.getKey());
                for (String fieldkey : fieldKeys) {
                    FieldDefine fieldDefine = exportCache.queryFieldDefine(fieldkey);
                    if (null == fieldDefine || !StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                    List<DataLinkDefine> dataLinkDefines = exportCache.getLinksInFormByField(sheetInfo.getFormKey(), fieldkey);
                    for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                        String enueName = EnueSheetUtil.getEnueName(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey());
                        List<String> menus = EnueSheetUtil.getEnumData(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey(), enueName, dropDownSize);
                        EnueSheetUtil.setDropDownBox(workbook, sheetInfo, menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, 0, false, false, shiftNum);
                    }
                }
                continue;
            }
            fieldKeys = exportCache.getFieldKeysInRegion(dataRegionDefine.getKey());
            Integer rowCount = sheetInfo.getDataCountMap().get(dataRegionDefine.getKey());
            int totalCount = rowCount == null ? 0 : Math.max(rowCount, 0);
            for (String fieldkey : fieldKeys) {
                FieldDefine fieldDefine = exportCache.queryFieldDefine(fieldkey);
                if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                List<DataLinkDefine> dataLinkDefines = exportCache.getLinksInFormByField(sheetInfo.getFormKey(), fieldkey);
                EnueSheetUtil.floatingAreaEnumProcessing(sheetInfo, exportCache, workbook, dataRegionDefine, fieldDefine, dataLinkDefines, totalCount, dropDownSize, shiftNum);
            }
            shiftNum += Math.max(totalCount - 1, 0);
        }
    }

    private static String getEnueName(SheetInfo sheetInfo, ExportCache exportCache, DataLinkDefine dataLinkDefine, String entityId) {
        String enueName = entityId + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
        IEntityDefine entityDefine = exportCache.getEntityDefine(entityId);
        if (entityDefine.getIsolation() != 0) {
            FormSchemeDefine formScheme = exportCache.getFormScheme(sheetInfo.getFormSchemeKey());
            IEntityDefine dw = exportCache.getEntityDefine(formScheme.getDw());
            Object value = sheetInfo.getDimensionCombination().getValue(dw.getDimensionName());
            enueName = enueName + String.valueOf(value);
        }
        return enueName;
    }

    private static void floatingAreaEnumProcessing(SheetInfo sheetInfo, ExportCache exportCache, SXSSFWorkbook workbook, DataRegionDefine dataRegionDefine, FieldDefine fieldDefine, List<DataLinkDefine> dataLinkDefines, int totalCount, int dropDownSize, int shiftNum) {
        block3: {
            block2: {
                if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_ROW_LIST) break block2;
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    String enueName = EnueSheetUtil.getEnueName(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey());
                    List<String> menus = EnueSheetUtil.getEnumData(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey(), enueName, dropDownSize);
                    EnueSheetUtil.setDropDownBox(workbook, sheetInfo, menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, Math.max(totalCount - 1, 0), true, true, shiftNum);
                }
                break block3;
            }
            if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_COLUMN_LIST) break block3;
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                String enueName = EnueSheetUtil.getEnueName(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey());
                List<String> menus = EnueSheetUtil.getEnumData(sheetInfo, exportCache, dataLinkDefine, fieldDefine.getEntityKey(), enueName, dropDownSize);
                EnueSheetUtil.setDropDownBox(workbook, sheetInfo, menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, Math.max(totalCount - 1, 0), true, false, shiftNum);
            }
        }
    }

    private static void coverCodeTableEnumProcessing(SXSSFWorkbook workbook, SheetInfo sheetInfo, ExportCache exportCache, int dropDownSize) throws Exception {
        try {
            TaskDefine taskDefine = exportCache.getTaskDefine(sheetInfo.getTaskKey());
            List<IEntityRefer> entityRefer = exportCache.getEntityRefer(taskDefine.getDw());
            List<DataRegionDefine> dataRegionDefineList = exportCache.getSortedRegionsByForm(sheetInfo.getFormKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                List<DataLinkDefine> dataLinkDefines = exportCache.getAllLinksInRegion(dataRegionDefine.getKey());
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    if (dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0) continue;
                    String entityId = null;
                    FieldDefine fieldDefine = exportCache.queryFieldDefine(dataLinkDefine.getLinkExpression());
                    if (null != fieldDefine && StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                        entityId = fieldDefine.getEntityKey();
                    }
                    for (IEntityRefer refer : entityRefer) {
                        if (!dataLinkDefine.getLinkExpression().equals(refer.getOwnField())) continue;
                        entityId = refer.getReferEntityId();
                    }
                    if (!StringUtils.isNotEmpty((String)entityId)) continue;
                    String enueName = EnueSheetUtil.getEnueName(sheetInfo, exportCache, dataLinkDefine, entityId);
                    List<String> menus = EnueSheetUtil.getEnumData(sheetInfo, exportCache, dataLinkDefine, entityId, enueName, dropDownSize);
                    EnueSheetUtil.setDropDownBox(workbook, sheetInfo, menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, 0, false, false, 0);
                }
            }
        }
        catch (FMDMQueryException e) {
            logger.error("\u67e5\u8be2\u62a5\u9519" + e.getMessage(), e);
        }
    }

    @Nullable
    private static List<String> getEnumData(SheetInfo sheetInfo, ExportCache exportCache, DataLinkDefine dataLinkDefine, String referEntityId, String cacheName, int dropDownSize) {
        List<String> enums = null;
        if (null != dataLinkDefine) {
            List<String> cacheEnums = exportCache.getEnumDataMap().get(cacheName);
            if (null != cacheEnums) {
                enums = cacheEnums;
            } else {
                enums = Collections.emptyList();
                FormSchemeDefine formScheme = exportCache.getFormScheme(sheetInfo.getFormSchemeKey());
                String dateTime = formScheme.getDateTime();
                DimensionCombination dimensionCombination = sheetInfo.getDimensionCombination();
                String period = String.valueOf(dimensionCombination.toDimensionValueSet().getValue("DATATIME"));
                IEntityTable entityTable = EnueSheetUtil.getEntityTable(referEntityId, dateTime, period, dataLinkDefine, exportCache, sheetInfo);
                assert (entityTable != null);
                int totalCount = entityTable.getTotalCount();
                if (dropDownSize < 0 || totalCount <= dropDownSize) {
                    List allRows = entityTable.getAllRows();
                    if (allRows != null && !allRows.isEmpty()) {
                        enums = new ArrayList<String>(allRows.size());
                        for (IEntityRow entityRow : allRows) {
                            enums.add(entityRow.getCode() + "|" + entityRow.getTitle());
                        }
                    }
                } else {
                    logger.warn("\u5bfc\u51faExcel\u679a\u4e3e{}\u6570\u636e\u91cf\u4e3a{}\u8d85\u51fa\u7cfb\u7edf\u914d\u7f6e\u6700\u5927\u6570\u91cf{}\uff0c\u4e0d\u5bfc\u51fa\u4e0b\u62c9", cacheName, totalCount, dropDownSize);
                }
                exportCache.getEnumDataMap().put(cacheName, enums);
            }
        }
        return enums;
    }

    private static void setDropDownBox(SXSSFWorkbook wb, SheetInfo sheetInfo, List<String> values, Integer rowNum, Integer colNum, String enueName, int totalCount, boolean isFloat, boolean isRowFloat, int shiftNum) {
        colNum = colNum + sheetInfo.getUpperLabelRowCount();
        String sheetName = sheetInfo.getSheetName();
        int sheetTotal = wb.getNumberOfSheets();
        if (values != null && !values.isEmpty()) {
            String colNumString;
            SXSSFSheet hiddenSheet = wb.getSheet("HIDDENSHEETNAME");
            if (hiddenSheet == null) {
                hiddenSheet = wb.createSheet("HIDDENSHEETNAME");
                hiddenSheet.setRandomAccessWindowSize(-1);
                wb.setSheetHidden(sheetTotal, true);
            }
            int duplicateColumnNum = -1;
            int lastCellNumOfFirstRow = -1;
            if (-1 != hiddenSheet.getFirstRowNum()) {
                SXSSFRow firstRow = hiddenSheet.getRow(0);
                lastCellNumOfFirstRow = firstRow.getLastCellNum();
                for (int i = 0; i < lastCellNumOfFirstRow; ++i) {
                    SXSSFCell enueNameCell = firstRow.getCell(i);
                    if (!enueName.equals(enueNameCell.getStringCellValue())) continue;
                    duplicateColumnNum = i;
                    break;
                }
            }
            String strFormula = "";
            if (duplicateColumnNum == -1) {
                int i;
                SXSSFCell cell;
                SXSSFRow row;
                if (-1 == hiddenSheet.getFirstRowNum()) {
                    row = hiddenSheet.createRow(0);
                    cell = row.createCell(0);
                    lastCellNumOfFirstRow = 0;
                    cell.setCellValue(enueName);
                    for (i = 0; i < values.size(); ++i) {
                        row = hiddenSheet.createRow(i + 1);
                        cell = row.createCell(0);
                        cell.setCellValue(values.get(i));
                    }
                } else {
                    row = hiddenSheet.getRow(0);
                    cell = row.createCell(lastCellNumOfFirstRow);
                    cell.setCellValue(enueName);
                    for (i = 0; i < values.size(); ++i) {
                        row = null != hiddenSheet.getRow(i + 1) ? hiddenSheet.getRow(i + 1) : hiddenSheet.createRow(i + 1);
                        cell = row.createCell(lastCellNumOfFirstRow);
                        cell.setCellValue(values.get(i));
                    }
                }
                colNumString = CellReference.convertNumToColString(lastCellNumOfFirstRow);
                strFormula = "HIDDENSHEETNAME!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            } else {
                colNumString = CellReference.convertNumToColString(duplicateColumnNum);
                strFormula = "HIDDENSHEETNAME!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            }
            XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(3, strFormula);
            CellRangeAddressList regions = isFloat ? (isRowFloat ? new CellRangeAddressList(colNum - 1 + shiftNum, colNum + totalCount - 1 + shiftNum, rowNum - 1, rowNum - 1) : new CellRangeAddressList(colNum - 1, colNum - 1, rowNum - 1 + shiftNum, rowNum + totalCount - 1 + shiftNum)) : new CellRangeAddressList(colNum - 1, colNum - 1, rowNum - 1, rowNum - 1);
            DataValidationHelper help = hiddenSheet.getDataValidationHelper();
            DataValidation validation = help.createValidation(constraint, regions);
            SXSSFSheet sheet1 = wb.getSheet(sheetName);
            sheet1.addValidationData(validation);
        }
    }

    private static IEntityTable getEntityTable(String entityId, String periodEntityId, String period, DataLinkDefine dataLinkDefine, ExportCache exportCache, SheetInfo sheetInfo) {
        Date entityQueryVersionDate = EnueSheetUtil.getEntityQueryVersionDate(periodEntityId, period);
        IEntityQuery query = EnueSheetUtil.getEntityQuery(entityId, entityQueryVersionDate, dataLinkDefine, exportCache, sheetInfo, period);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        try {
            return query.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53ID\u4e3a" + entityId + "\u7684\u5b9e\u4f53\u672a\u627e\u5230:" + e.getMessage(), e);
            return null;
        }
    }

    private static Date getEntityQueryVersionDate(String periodEntityId, String period) {
        IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        Date versionDate = null;
        try {
            versionDate = periodProvider.getPeriodDateRegion(period)[1];
        }
        catch (ParseException e) {
            logger.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
        }
        return versionDate;
    }

    private static IEntityQuery getEntityQuery(String entityId, Date queryVersionDate, DataLinkDefine dataLinkDefine, ExportCache exportCache, SheetInfo sheetInfo, String period) {
        String filterExp = null;
        if (StringUtils.isNotEmpty((String)dataLinkDefine.getFilterTemplate())) {
            FilterTemplateDTO filterTemplate = filterTemplateService.getFilterTemplate(dataLinkDefine.getFilterTemplate());
            if (filterTemplate != null) {
                filterExp = filterTemplate.getFilterContent();
            }
        } else {
            filterExp = dataLinkDefine.getFilterExpression();
        }
        EntityViewDefine entityViewDefine = entityViewRunTimeController.buildEntityView(entityId, filterExp, !dataLinkDefine.isIgnorePermissions());
        IEntityQuery query = entityDataService.newEntityQuery();
        query.setExpression(entityViewDefine.getRowFilterExpression());
        query.setEntityView(entityViewDefine);
        if (queryVersionDate != null) {
            query.setQueryVersionDate(queryVersionDate);
        }
        query.setAuthorityOperations(AuthorityType.Read);
        query.maskedData();
        IEntityDefine entityDefine = exportCache.getEntityDefine(entityId);
        if (entityDefine.getIsolation() != 0) {
            FormSchemeDefine formScheme = exportCache.getFormScheme(sheetInfo.getFormSchemeKey());
            IEntityDefine dw = exportCache.getEntityDefine(formScheme.getDw());
            Object value = sheetInfo.getDimensionCombination().getValue(dw.getDimensionName());
            if (entityIsolateCondition != null) {
                String queryIsoCondition = entityIsolateCondition.queryIsoCondition(formScheme.getTaskKey(), period, entityId);
                query.setIsolateCondition(queryIsoCondition);
            } else {
                query.setIsolateCondition(String.valueOf(value));
            }
        }
        query.sorted(true);
        return query;
    }
}

