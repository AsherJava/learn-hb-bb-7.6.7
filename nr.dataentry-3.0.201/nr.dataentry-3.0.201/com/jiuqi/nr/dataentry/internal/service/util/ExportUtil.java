/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.util.ColorUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider
 *  com.jiuqi.nr.data.excel.obj.CustomGridCellStyle
 *  com.jiuqi.nr.data.excel.obj.ExportMeasureSetting
 *  com.jiuqi.nr.data.excel.obj.ExportOps
 *  com.jiuqi.nr.data.excel.param.CellQueryInfo
 *  com.jiuqi.nr.data.excel.param.FilterCondition
 *  com.jiuqi.nr.data.excel.param.SingleExpPar
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.CellQueryInfo
 *  com.jiuqi.nr.jtable.params.input.FilterCondition
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$BackgroundStyle
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.np.util.ColorUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.param.CellQueryInfo;
import com.jiuqi.nr.data.excel.param.FilterCondition;
import com.jiuqi.nr.data.excel.param.SingleExpPar;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.dataentry.provider.CustomCellStyleProviderImpl;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ExportUtil {
    private static final IDataEntryParamService dataEntryParamService = (IDataEntryParamService)BeanUtil.getBean(IDataEntryParamService.class);
    private static final DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
    private static final IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private static final INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);

    public static String getExpTempPath() {
        return System.getProperty("java.io.tmpdir") + File.separator + "JQ" + File.separator + "EXP" + new Date().getTime() + File.separator;
    }

    public static SingleExpPar getSingleExpPar(ExportParam exportParam) {
        Map<String, List<CellQueryInfo>> cellQueryInfoMap;
        SingleExpPar dataExpPar = new SingleExpPar();
        if (exportParam.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            dataExpPar.setDataSnapshotId(((DimensionValue)exportParam.getContext().getDimensionSet().get("DATASNAPSHOTID")).getValue());
        }
        List<FormData> forms = DataEntryUtil.getAllForms(dataEntryParamService, exportParam, exportParam.getFormKeys());
        dataExpPar.setForms(forms.stream().map(FormData::getKey).collect(Collectors.toList()));
        dataExpPar.setFormSchemeKey(exportParam.getContext().getFormSchemeKey());
        DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(exportParam.getContext().getDimensionSet(), exportParam.getContext().getFormSchemeKey());
        dataExpPar.setDimensionCombination((DimensionCombination)dimensionCollection.getDimensionCombinations().get(0));
        ExportOps exportOps = new ExportOps();
        exportOps.setExpExcelDirSheet(exportParam.isExpExcelDirSheet());
        exportOps.setEt(exportParam.isExportETFile());
        String millennial = exportParam.getContext().getMillennial();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)millennial) && !millennial.equals("0")) {
            exportOps.setThousands(Boolean.valueOf(millennial.equals("1")));
        }
        Map measureMap = exportParam.getContext().getMeasureMap();
        ExportMeasureSetting exportMeasureSetting = new ExportMeasureSetting();
        for (Map.Entry entry : measureMap.entrySet()) {
            exportMeasureSetting.setKey((String)entry.getKey());
            exportMeasureSetting.setCode((String)entry.getValue());
        }
        if (StringUtils.hasText(exportParam.getContext().getDecimal())) {
            exportMeasureSetting.setDecimal(Integer.parseInt(exportParam.getContext().getDecimal()));
        }
        exportOps.setExportMeasureSetting(exportMeasureSetting);
        exportOps.setExpCellBColor(exportParam.isBackground());
        exportOps.setExpFml(exportParam.isArithmeticFormula());
        exportOps.setEmptyForm(exportParam.isExportEmptyTable());
        exportOps.setFormulaSchemeKey(exportParam.getContext().getFormulaSchemeKey());
        exportOps.setPrintSchemeKey(exportParam.getPrintSchemeKey());
        exportOps.setOnlyStyle(exportParam.isOnlyStyle());
        exportOps.setSumData(exportParam.isSumData());
        List<String> tabs = exportParam.getTabs();
        if (!CollectionUtils.isEmpty(tabs) && forms.size() == 1) {
            HashMap tabMap = new HashMap();
            FormData formData = forms.get(0);
            List allRegionsInForm = runTimeViewController.getAllRegionsInForm(formData.getKey());
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List regionTabSetting;
                RegionSettingDefine regionSetting = runTimeViewController.getRegionSetting(dataRegionDefine.getKey());
                if (regionSetting == null || null == (regionTabSetting = regionSetting.getRegionTabSetting())) continue;
                for (RegionTabSettingDefine regionTab : regionTabSetting) {
                    if (!exportParam.isExportAllLable() && !tabs.contains(regionTab.getTitle())) continue;
                    if (!tabMap.containsKey(dataRegionDefine.getKey())) {
                        tabMap.put(dataRegionDefine.getKey(), new ArrayList());
                    }
                    ((List)tabMap.get(dataRegionDefine.getKey())).add(regionTab.getTitle());
                }
            }
            exportOps.setTabs(tabMap);
        }
        if (exportParam.isArithmeticBackground()) {
            CustomCellStyleProviderImpl customGridCellStyle = new CustomCellStyleProviderImpl(exportParam.getContext());
            dataExpPar.setCustomCellStyleProvider((CustomCellStyleProvider)customGridCellStyle);
        }
        exportOps.setLabel(exportParam.isLabel());
        List<RegionFilterListInfo> regionFilterListInfo = exportParam.getRegionFilterListInfo();
        Map<String, List<RowFilter>> rowFilterMap = ExportUtil.getRowFilterMap(regionFilterListInfo);
        if (!CollectionUtils.isEmpty(rowFilterMap)) {
            exportOps.setRowFilter(rowFilterMap);
        }
        if (!CollectionUtils.isEmpty(cellQueryInfoMap = ExportUtil.getCellQueryInfoMap(exportParam.getConditions()))) {
            exportOps.setConditions(cellQueryInfoMap);
        }
        dataExpPar.setOps(exportOps);
        return dataExpPar;
    }

    public static Map<String, List<RowFilter>> getRowFilterMap(List<RegionFilterListInfo> regionFilterListInfo) {
        HashMap<String, List<RowFilter>> rowFilterMap = new HashMap<String, List<RowFilter>>();
        if (!CollectionUtils.isEmpty(regionFilterListInfo)) {
            for (RegionFilterListInfo filter : regionFilterListInfo) {
                ArrayList<FormulaFilter> rowFilters = new ArrayList<FormulaFilter>();
                if (!CollectionUtils.isEmpty(filter.getFilterConditions())) {
                    for (String filterCondition : filter.getFilterConditions()) {
                        if (!StringUtils.hasText(filterCondition)) continue;
                        rowFilters.add(new FormulaFilter(filterCondition));
                    }
                }
                if (rowFilters.size() <= 0) continue;
                rowFilterMap.put(filter.getAreaKey(), rowFilters);
            }
        }
        return rowFilterMap;
    }

    public static Map<String, List<CellQueryInfo>> getCellQueryInfoMap(Map<String, List<com.jiuqi.nr.jtable.params.input.CellQueryInfo>> mapByRegion) {
        if (CollectionUtils.isEmpty(mapByRegion)) {
            return Collections.emptyMap();
        }
        HashMap<String, List<CellQueryInfo>> result = new HashMap<String, List<CellQueryInfo>>();
        for (Map.Entry<String, List<com.jiuqi.nr.jtable.params.input.CellQueryInfo>> e : mapByRegion.entrySet()) {
            ArrayList<CellQueryInfo> cellQueryInfos = new ArrayList<CellQueryInfo>();
            for (com.jiuqi.nr.jtable.params.input.CellQueryInfo cellQueryInfo : e.getValue()) {
                CellQueryInfo cell = new CellQueryInfo();
                cell.setCellKey(cellQueryInfo.getCellKey());
                cell.setSort(cellQueryInfo.getSort());
                cell.setFilterFormula(cellQueryInfo.getFilterFormula());
                cell.setAttendedMode(cellQueryInfo.getAttendedMode());
                cell.setShortcuts(cellQueryInfo.getShortcuts());
                cell.setInList(cellQueryInfo.getInList());
                cell.setOutList(cellQueryInfo.getOutList());
                List opList = cellQueryInfo.getOpList();
                if (!CollectionUtils.isEmpty(opList)) {
                    ArrayList<FilterCondition> excelOpList = new ArrayList<FilterCondition>();
                    for (com.jiuqi.nr.jtable.params.input.FilterCondition filterCondition : opList) {
                        FilterCondition excelCon = new FilterCondition();
                        excelCon.setOpCode(filterCondition.getOpCode());
                        excelCon.setOpValue(filterCondition.getOpValue());
                        excelOpList.add(excelCon);
                    }
                    cell.setOpList(excelOpList);
                }
                cellQueryInfos.add(cell);
            }
            result.put(e.getKey(), cellQueryInfos);
        }
        return result;
    }

    public static String getCalCellColor() {
        String s = systemOptionService.get("nr-data-entry-group", "JTABLE_AUTOCALC_COLOR");
        return StringUtils.hasText(s) ? s : "#D6F6EF";
    }

    public static String getEFDCCellColor() {
        String s = systemOptionService.get("nr-data-entry-group", "JTABLE_EFDC_COLOR");
        return StringUtils.hasText(s) ? s : "#FBEEC4";
    }

    public static Map<String, CustomGridCellStyle> getCalCellStyles(JtableContext jtableContext, List<String> expForms) {
        HashMap<String, CustomGridCellStyle> cellStyles = new HashMap<String, CustomGridCellStyle>();
        for (String expForm : expForms) {
            JtableContext expContext = new JtableContext(jtableContext);
            expContext.setFormKey(expForm);
            List calcDataLinks = jtableParamService.getCalcDataLinks(expContext);
            List extractDataLinks = jtableParamService.getExtractDataLinkList(expContext);
            HashSet calLinks = new HashSet(calcDataLinks);
            HashSet extractDataLinkSet = new HashSet(extractDataLinks);
            for (String calLink : calLinks) {
                CustomGridCellStyle style = new CustomGridCellStyle();
                style.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                style.setBackGroundColor(ColorUtil.htmlColorToInt((String)ExportUtil.getCalCellColor()));
                cellStyles.put(calLink, style);
            }
            for (String extractDataLink : extractDataLinkSet) {
                int color = ColorUtil.htmlColorToInt((String)ExportUtil.getEFDCCellColor());
                if (calLinks.contains(extractDataLink)) {
                    color = ColorUtil.mergeColor((int)ColorUtil.htmlColorToInt((String)ExportUtil.getCalCellColor()), (int)ColorUtil.htmlColorToInt((String)ExportUtil.getEFDCCellColor()));
                }
                CustomGridCellStyle style = new CustomGridCellStyle();
                style.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                style.setBackGroundColor(color);
                cellStyles.put(extractDataLink, style);
            }
        }
        return cellStyles;
    }

    public static void handleMdCurDim(Map<String, DimensionValue> useDim, Map<String, DimensionValue> originalDim) {
        if (useDim.containsKey("MD_CURRENCY") && originalDim.containsKey("MD_CURRENCY")) {
            useDim.put("MD_CURRENCY", originalDim.get("MD_CURRENCY"));
        }
    }
}

