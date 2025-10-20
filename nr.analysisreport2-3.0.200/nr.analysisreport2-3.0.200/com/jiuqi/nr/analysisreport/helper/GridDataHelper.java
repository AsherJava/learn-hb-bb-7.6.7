/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.CellMap
 *  com.jiuqi.bi.quickreport.model.ExpandMode
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.nr.analysisreport.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.nr.analysisreport.biservice.bi.IBIIntegrationServices;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.GenerateContext;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GridDataHelper {
    public static final String NR_FORM = "NR_FORM";
    public static final String BI_QUICK_FORM = "BI_QUICK_FORM";
    public static final String NVWA_QUICK_FORM = "NVWA_QUICK_FORM";
    public static final String FLOAT_ROWS = "FLOAT_ROWS";
    public static final String FLOAT_COLS = "FLOAT_COLS";
    public static final int MAX_FLOAT_COUNT = 16;
    public static final String GRIDDATA_TYPE = "gridDataType";
    public static final String VARKEY = "varKey";
    @Autowired
    private IBIIntegrationServices biIntegrateServices;
    @Autowired
    private AnalysisHelper analysisHelper;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private QuickReportModelService quickReportModelService;

    public Object genGridData(Map<String, String> params) throws Exception {
        String gridDataType;
        switch (gridDataType = params.get(GRIDDATA_TYPE)) {
            case "NR_FORM": {
                return this.getNrFormGridData(params);
            }
            case "BI_QUICK_FORM": {
                return this.getBIQuickFormGridData(params);
            }
            case "NVWA_QUICK_FORM": {
                return this.getNVWAQuickFormGridData(params);
            }
        }
        return null;
    }

    private Object getNVWAQuickFormGridData(Map<String, String> params) throws Exception {
        String varKey = params.get(VARKEY);
        QuickReport report = this.quickReportModelService.getQuickReportByGuidOrId(varKey);
        WorksheetModel primarySheet = (WorksheetModel)report.getWorksheets().get(0);
        GridData gridData = primarySheet.getGriddata();
        Grid2Data grid2Data = new Grid2Data();
        GridDataTransform.gridDataToGrid2Data((GridData)gridData, (Grid2Data)grid2Data);
        GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
        this.setCustomStyle(grid2Data);
        Map<String, HashSet<Integer>> floatInfo = this.getFloatRowAndCol(params);
        this.hideRowOrCol(grid2Data, floatInfo);
        return this.covert2CellBook(grid2Data);
    }

    private void setCustomStyle(Grid2Data grid2Data) {
        int rowCount = grid2Data.getRowCount();
        int colCount = grid2Data.getColumnCount();
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                GridCellData gridCellData = grid2Data.getGridCellData(j, i);
                gridCellData.setWrapLine(true);
                gridCellData.setFitFontSize(false);
            }
        }
    }

    private void hideRowOrCol(Grid2Data grid2Data, Map<String, HashSet<Integer>> rowCloFloat) {
        int columnCount;
        int rowCount;
        boolean isRowFloat = CollectionUtils.isEmpty((Collection)rowCloFloat.get(FLOAT_ROWS));
        if (isRowFloat && rowCount > 16) {
            for (rowCount = grid2Data.getRowCount(); rowCount > 16; --rowCount) {
                grid2Data.setRowHidden(rowCount - 1, true);
            }
            return;
        }
        boolean isColFloat = CollectionUtils.isEmpty((Collection)rowCloFloat.get(FLOAT_COLS));
        if (isColFloat && columnCount > 16) {
            for (columnCount = grid2Data.getColumnCount(); columnCount > 16; --columnCount) {
                grid2Data.setColumnHidden(columnCount - 1, true);
            }
            return;
        }
    }

    public Map<String, HashSet<Integer>> rowColFloat(QuickReport report) {
        HashMap<String, HashSet<Integer>> floatRowCol = new HashMap<String, HashSet<Integer>>();
        floatRowCol.put(FLOAT_ROWS, new HashSet());
        floatRowCol.put(FLOAT_COLS, new HashSet());
        WorksheetModel sheet = report.getPrimarySheet();
        List cellMap = sheet.getCellMaps();
        for (CellMap cell : cellMap) {
            if (cell.getExpandMode() == ExpandMode.ROWEXPANDING) {
                ((HashSet)floatRowCol.get(FLOAT_ROWS)).add(cell.getPosition().row());
            }
            if (cell.getExpandMode() != ExpandMode.COLEXPANDING) continue;
            ((HashSet)floatRowCol.get(FLOAT_COLS)).add(cell.getPosition().col());
        }
        return floatRowCol;
    }

    private Object getBIQuickFormGridData(Map<String, String> params) throws JsonProcessingException {
        String varKey = params.get(VARKEY);
        byte[] response = this.biIntegrateServices.ExportBIReport(varKey, new HashMap<String, List<String>>(), "\u5206\u6790\u62a5\u544a");
        Grid2Data grid2Data = new Grid2Data();
        GridData gridData = GridData.bytesToGrid((byte[])response);
        AnaUtils.dataTodata2(gridData, grid2Data);
        return this.covert2CellBook(grid2Data);
    }

    private Object getNrFormGridData(Map<String, String> params) throws Exception {
        String templateKey = params.get("templateKey");
        String varKey = params.get(VARKEY);
        String taskId = params.get("taskId");
        AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(templateKey);
        GenerateContext generateContext = new GenerateContext();
        generateContext.setPrintData(reportDefine.getPrintData());
        List formGroupsByFormKey = this.iRunTimeViewController.getFormGroupsByFormKey(varKey);
        FormGroupDefine formGroupDefine = (FormGroupDefine)formGroupsByFormKey.get(0);
        String formSchemeKey = formGroupDefine.getFormSchemeKey();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        List entitys = this.jtableParamService.getEntityList(formSchemeKey);
        Map<String, DimensionValue> dimensionSet = AnalysisHelper.entityListAndUnitsCalcDimensionValue(entitys, generateContext.getUnitMapList(), generateContext.getPeriodCode());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(taskKey);
        jtableContext.setFormKey(varKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setDimensionSet(dimensionSet);
        List formulaSchemeDefines = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            if (formulaSchemeDefine == null || StringUtil.isNullOrEmpty((String)formulaSchemeDefine.getKey()) || !formulaSchemeDefine.isDefault()) continue;
            jtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
        }
        Grid2Data grid2Data = this.jtableParamService.getGridData(varKey);
        this.setCustomStyle(grid2Data);
        Map<String, HashSet<Integer>> floatInfo = this.getFloatRowAndCol(params);
        this.hideRowOrCol(grid2Data, floatInfo);
        return this.covert2CellBook(grid2Data);
    }

    private Object covert2CellBook(Grid2Data grid2Data) throws JsonProcessingException {
        if (!grid2Data.isRowHidden(0) || !grid2Data.isColumnHidden(0)) {
            grid2Data.setRowHidden(0, true);
            grid2Data.setColumnHidden(0, true);
        }
        CellBook cellBook = new CellBook();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)cellBook, (String)UUID.randomUUID().toString());
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(CellBook.class, (JsonSerializer)new CellBookSerialize());
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)cellBook);
    }

    public Map<String, HashSet<Integer>> getFloatRowAndCol(Map<String, String> params) throws Exception {
        String gridDataType;
        switch (gridDataType = params.get(GRIDDATA_TYPE)) {
            case "NR_FORM": {
                return this.getNrFormFloatRowAndCol(params);
            }
            case "BI_QUICK_FORM": {
                return this.bIQuickFormIsFloat(params);
            }
            case "NVWA_QUICK_FORM": {
                return this.nVWAQuickFormIsFloat(params);
            }
        }
        HashMap<String, HashSet<Integer>> rowColFloat = new HashMap<String, HashSet<Integer>>();
        rowColFloat.put(FLOAT_ROWS, new HashSet());
        rowColFloat.put(FLOAT_COLS, new HashSet());
        return rowColFloat;
    }

    private Map<String, HashSet<Integer>> nVWAQuickFormIsFloat(Map<String, String> params) throws Exception {
        String varKey = params.get(VARKEY);
        QuickReport report = this.quickReportModelService.getQuickReportByGuidOrId(varKey);
        return this.rowColFloat(report);
    }

    private Map<String, HashSet<Integer>> bIQuickFormIsFloat(Map<String, String> params) {
        HashMap<String, HashSet<Integer>> rowColFloat = new HashMap<String, HashSet<Integer>>();
        rowColFloat.put(FLOAT_ROWS, new HashSet());
        rowColFloat.put(FLOAT_COLS, new HashSet());
        return rowColFloat;
    }

    private Map<String, HashSet<Integer>> getNrFormFloatRowAndCol(Map<String, String> params) {
        String varKey = params.get(VARKEY);
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(varKey);
        HashMap<String, HashSet<Integer>> floatRowAndCol = new HashMap<String, HashSet<Integer>>();
        floatRowAndCol.put(FLOAT_ROWS, new HashSet());
        floatRowAndCol.put(FLOAT_COLS, new HashSet());
        FormType formDefineType = formDefine.getFormType();
        if (formDefineType == FormType.FORM_TYPE_FLOAT) {
            IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
            List regions = jtableParamService.getRegions(varKey);
            for (RegionData region : regions) {
                List links;
                if (region.getType() == 2) {
                    links = jtableParamService.getLinks(region.getKey());
                    links.stream().forEach(e -> ((HashSet)floatRowAndCol.get(FLOAT_COLS)).add(e.getCol()));
                    continue;
                }
                if (region.getType() != 3) continue;
                links = jtableParamService.getLinks(region.getKey());
                links.stream().forEach(e -> ((HashSet)floatRowAndCol.get(FLOAT_ROWS)).add(e.getRow()));
            }
        }
        return floatRowAndCol;
    }
}

