/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

public class EnueSheetUtil {
    private static final Logger logger = LoggerFactory.getLogger(EnueSheetUtil.class);
    private IRunTimeViewController controller;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IJtableEntityService jtableEntityService;
    private IDataAccessProvider dataAccessProvider;
    private IEntityMetaService entityMetaService;
    private IJtableResourceService jtableResourceService;
    private Map<String, List<String>> menuMap;
    private int labelRowCont = 0;
    private int DataSnapshotRows;
    private int dropDownSize = 0;

    public EnueSheetUtil(IRunTimeViewController controller, IEntityViewRunTimeController entityViewRunTimeController, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IJtableEntityService jtableEntityService, IDataAccessProvider dataAccessProvider, IEntityMetaService entityMetaService, IJtableResourceService jtableResourceService, Map<String, List<String>> menuMap, int labelRowCont, int DataSnapshotRows, int dropDownSize) {
        this.controller = controller;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.jtableEntityService = jtableEntityService;
        this.dataAccessProvider = dataAccessProvider;
        this.entityMetaService = entityMetaService;
        this.jtableResourceService = jtableResourceService;
        this.menuMap = menuMap;
        this.labelRowCont = labelRowCont;
        this.DataSnapshotRows = DataSnapshotRows;
        this.dropDownSize = dropDownSize;
    }

    public void setEnueSheet(IExportFacade info, SXSSFWorkbook workbook, JtableContext jtableContext) {
        try {
            FormDefine formDefine = this.controller.queryFormById(jtableContext.getFormKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                this.coverCodeTableEnumProcessing(info, workbook, jtableContext);
            } else {
                this.enumProcessingInTable(info, workbook, jtableContext);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void enumProcessingInTable(IExportFacade info, SXSSFWorkbook workbook, JtableContext jtableContext) throws Exception {
        IReportExportDataSet reportExportData = this.jtableResourceService.getReportExportData(info.getContext());
        List regionDataList = reportExportData.getDataRegionList();
        List dataRegionDefineList = this.controller.getAllRegionsInForm(jtableContext.getFormKey());
        for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
            List regionTabs;
            List fieldKeys;
            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                fieldKeys = this.controller.getFieldKeysInRegion(dataRegionDefine.getKey());
                for (String fieldkey : fieldKeys) {
                    FieldDefine fieldDefine = this.controller.queryFieldDefine(fieldkey);
                    if (null == fieldDefine || !StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                    List dataLinkDefines = this.controller.getLinksInFormByField(jtableContext.getFormKey(), fieldkey);
                    for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                        List<String> menus = this.getEnumData(info, dataLinkDefine, fieldDefine.getEntityKey());
                        if (menus != null && menus.size() > this.dropDownSize) continue;
                        String enueName = fieldDefine.getEntityKey() + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
                        this.setDropDownBox(workbook, info.getSheetName(), menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, 0, false, false);
                    }
                }
                continue;
            }
            fieldKeys = this.controller.getFieldKeysInRegion(dataRegionDefine.getKey());
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(jtableContext.getFormSchemeKey());
            queryEnvironment.setRegionKey(dataRegionDefine.getKey());
            queryEnvironment.setFormKey(jtableContext.getFormKey());
            IGroupingQuery groupingQuery = this.dataAccessProvider.newGroupingQuery(queryEnvironment);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.controller, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            DimensionValueSet masterKeys = DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet());
            groupingQuery.setMasterKeys(masterKeys);
            TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(this.controller.queryFieldDefine((String)fieldKeys.get(0)).getOwnerTableKey());
            String[] entityMasterKeys = tableDefine.getBizKeyFieldsID();
            for (int i = 0; i < entityMasterKeys.length; ++i) {
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(entityMasterKeys[i]);
                groupingQuery.addColumn(fieldDefine);
                groupingQuery.setGatherType(i, FieldGatherType.FIELD_GATHER_COUNT);
            }
            int totalCount = 0;
            List<Object> tabs = new ArrayList();
            if (info.isExportAllLable()) {
                for (RegionData regionData : regionDataList) {
                    if (!regionData.getKey().equals(dataRegionDefine.getKey())) continue;
                    regionTabs = regionData.getTabs();
                    tabs = regionTabs.stream().map(t -> t.getFilter()).collect(Collectors.toList());
                    break;
                }
            } else if (!info.isExportAllLable() && null != info.getTabs() && info.getTabs().size() != 0) {
                for (RegionData regionData : regionDataList) {
                    if (!regionData.getKey().equals(dataRegionDefine.getKey())) continue;
                    regionTabs = regionData.getTabs();
                    for (RegionTab regionTab : regionTabs) {
                        if (!info.getTabs().contains(regionTab.getTitle())) continue;
                        tabs.add(regionTab.getFilter());
                    }
                }
            } else if (!info.isExportAllLable() && null == info.getTabs()) {
                for (RegionData regionData : regionDataList) {
                    if (!regionData.getKey().equals(dataRegionDefine.getKey())) continue;
                    regionTabs = regionData.getTabs();
                    if (regionTabs.isEmpty()) break;
                    tabs.add(((RegionTab)regionTabs.get(0)).getFilter());
                    break;
                }
            }
            if (!tabs.isEmpty()) {
                if (tabs.size() != 1) {
                    totalCount += tabs.size();
                }
                for (String string : tabs) {
                    StringBuilder filter = new StringBuilder();
                    groupingQuery.setRowFilter(string);
                    IGroupingTable iGroupingTable = groupingQuery.executeReader(executorContext);
                    for (int i = 0; i < iGroupingTable.getTotalCount(); ++i) {
                        String totalCountAboutDoubleStr = iGroupingTable.getItem(i).getAsString(i);
                        String totalCountAboutIntStr = "";
                        totalCountAboutIntStr = totalCountAboutDoubleStr.contains(".") ? totalCountAboutDoubleStr.substring(0, totalCountAboutDoubleStr.indexOf(".")) : totalCountAboutDoubleStr;
                        if (tabs.indexOf(string) == tabs.size() - 1 && totalCountAboutIntStr.equals("0")) {
                            totalCountAboutIntStr = "1";
                        }
                        totalCount += Integer.parseInt(totalCountAboutIntStr);
                    }
                }
            } else {
                void var20_28;
                IGroupingTable iGroupingTable = groupingQuery.executeReader(executorContext);
                String string = iGroupingTable.getItem(0).getAsString(0);
                if (jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID")) {
                    String string2 = this.DataSnapshotRows + "";
                }
                Object totalCountAboutIntStr = "";
                totalCountAboutIntStr = var20_28.contains(".") ? var20_28.substring(0, var20_28.indexOf(".")) : var20_28;
                totalCount += Integer.parseInt(totalCountAboutIntStr);
            }
            for (String string : fieldKeys) {
                FieldDefine fieldDefine = this.controller.queryFieldDefine(string);
                if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                List dataLinkDefines = this.controller.getLinksInFormByField(jtableContext.getFormKey(), string);
                this.floatingAreaEnumProcessing(info, workbook, dataRegionDefine, fieldDefine, dataLinkDefines, totalCount);
            }
        }
    }

    private void floatingAreaEnumProcessing(IExportFacade info, SXSSFWorkbook workbook, DataRegionDefine dataRegionDefine, FieldDefine fieldDefine, List<DataLinkDefine> dataLinkDefines, int totalCount) throws Exception {
        block3: {
            block2: {
                if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_ROW_LIST) break block2;
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    List<String> menus = this.getEnumData(info, dataLinkDefine, fieldDefine.getEntityKey());
                    if (menus != null && menus.size() > this.dropDownSize) continue;
                    String enueName = fieldDefine.getEntityKey() + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
                    this.setDropDownBox(workbook, info.getSheetName(), menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, totalCount - 1 <= 0 ? 0 : totalCount - 1, true, true);
                }
                break block3;
            }
            if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_COLUMN_LIST) break block3;
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                List<String> menus = this.getEnumData(info, dataLinkDefine, fieldDefine.getEntityKey());
                if (menus != null && menus.size() > this.dropDownSize) continue;
                String enueName = fieldDefine.getEntityKey() + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
                this.setDropDownBox(workbook, info.getSheetName(), menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, totalCount - 1 <= 0 ? 0 : totalCount - 1, true, false);
            }
        }
    }

    private void coverCodeTableEnumProcessing(IExportFacade info, SXSSFWorkbook workbook, JtableContext jtableContext) throws Exception {
        try {
            TaskDefine taskDefine = this.controller.queryTaskDefine(jtableContext.getTaskKey());
            List entityRefer = this.entityMetaService.getEntityRefer(taskDefine.getDw());
            List dataRegionDefineList = this.controller.getAllRegionsInForm(jtableContext.getFormKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                List dataLinkDefines = this.controller.getAllLinksInRegion(dataRegionDefine.getKey());
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    if (dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0) continue;
                    String entityId = null;
                    FieldDefine fieldDefine = this.controller.queryFieldDefine(dataLinkDefine.getLinkExpression());
                    if (null != fieldDefine && StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                        entityId = fieldDefine.getEntityKey();
                    }
                    for (IEntityRefer refer : entityRefer) {
                        if (!dataLinkDefine.getLinkExpression().equals(refer.getOwnField())) continue;
                        entityId = refer.getReferEntityId();
                    }
                    if (!StringUtils.isNotEmpty((String)entityId)) continue;
                    List<String> menus = this.getEnumData(info, dataLinkDefine, entityId);
                    String enueName = entityId + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
                    this.setDropDownBox(workbook, info.getSheetName(), menus, dataLinkDefine.getPosX(), dataLinkDefine.getPosY(), enueName, 0, false, false);
                }
            }
        }
        catch (FMDMQueryException e) {
            logger.error("\u67e5\u8be2\u62a5\u9519" + e.getMessage(), e);
        }
    }

    private List<String> getEnumData(IExportFacade info, DataLinkDefine dataLinkDefine, String referEntityId) {
        List<String> menus = new ArrayList<String>();
        if (null != dataLinkDefine) {
            String enueName = referEntityId + dataLinkDefine.getFilterTemplate() + dataLinkDefine.getFilterExpression() + dataLinkDefine.isIgnorePermissions();
            List<String> menuss = this.menuMap.get(enueName);
            if (null != menuss && menuss.size() != 0) {
                menus = menuss;
            } else {
                EntityViewDefine entityViewDefine = this.controller.getViewByLinkDefineKey(dataLinkDefine.getKey());
                List allEntityKey = this.jtableEntityService.getAllEntityKey(referEntityId, info.getContext(), entityViewDefine);
                EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
                entityQueryByKeysInfo.setContext(info.getContext());
                entityQueryByKeysInfo.setEntityViewKey(referEntityId);
                entityQueryByKeysInfo.setDataLinkKey(dataLinkDefine.getKey());
                entityQueryByKeysInfo.setEntityKeys(allEntityKey);
                EntityByKeysReturnInfo entityByKeysReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
                Map entitys = entityByKeysReturnInfo.getEntitys();
                if (!entitys.isEmpty()) {
                    for (String key : allEntityKey) {
                        if (!entitys.containsKey(key)) continue;
                        EntityData entityData = (EntityData)entitys.get(key);
                        menus.add(entityData.getCode() + "|" + entityData.getTitle());
                    }
                }
                this.menuMap.put(enueName, menus);
            }
        }
        return menus;
    }

    private void setDropDownBox(SXSSFWorkbook wb, String sheetName, List<String> values, Integer rowNum, Integer colNum, String enueName, int totalCount, boolean isFloat, boolean isRowFloat) {
        colNum = colNum + this.labelRowCont;
        int sheetTotal = wb.getNumberOfSheets();
        if (values != null && values.size() != 0) {
            String colNumString;
            String hiddenSheetName = "HIDDENSHEETNAME";
            SXSSFSheet hiddenSheet = wb.getSheet(hiddenSheetName);
            if (hiddenSheet == null) {
                hiddenSheet = wb.createSheet(hiddenSheetName);
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
                strFormula = hiddenSheetName + "!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            } else {
                colNumString = CellReference.convertNumToColString(duplicateColumnNum);
                strFormula = hiddenSheetName + "!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            }
            XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(3, strFormula);
            CellRangeAddressList regions = null;
            regions = isFloat && isRowFloat ? new CellRangeAddressList(colNum - 1, colNum + totalCount - 1, rowNum - 1, rowNum - 1) : (isFloat && !isRowFloat ? new CellRangeAddressList(colNum - 1, colNum - 1, rowNum - 1, rowNum + totalCount - 1) : new CellRangeAddressList(colNum - 1, colNum - 1, rowNum - 1, rowNum - 1));
            DataValidationHelper help = hiddenSheet.getDataValidationHelper();
            DataValidation validation = help.createValidation(constraint, regions);
            SXSSFSheet sheet1 = wb.getSheet(sheetName);
            sheet1.addValidationData(validation);
        }
    }
}

