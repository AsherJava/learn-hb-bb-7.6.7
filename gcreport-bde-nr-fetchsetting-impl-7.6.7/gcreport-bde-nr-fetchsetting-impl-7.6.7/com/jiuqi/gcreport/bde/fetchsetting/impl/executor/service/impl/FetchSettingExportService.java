/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.impl;

import com.jiuqi.bde.common.constant.RegionTypeEnum;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelRegionInfo;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelCellStyleGroup;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelExplainInfo;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.FixedFetchSettingExcelRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.FloatFetchSettingExcelRowHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingExportService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FetchSettingExportService
implements IFetchSettingExportService {
    public static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingExportService.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FixedFetchSettingExcelRowHandler fixedFetchSettingExcelRowHandler;
    @Autowired
    private FloatFetchSettingExcelRowHandler floatFetchSettingExcelRowHandler;

    @Override
    public String getBizType() {
        return BizTypeEnum.NR.getCode();
    }

    @Override
    public List<ExportExcelSheet> listExportExcelSheets(ExportContext context, FetchSettingExportContext fetchSettingExportContext, Workbook workbook) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        context.getVarMap().put("FETCH_SOURCE", fetchSettingExportContext.getBizModels());
        context.getVarMap().put("DIMENSION", fetchSettingExportContext.getDimensions());
        context.getVarMap().put("FIXCOLUMNS", fetchSettingExportContext.getFixColumns());
        context.getVarMap().put("FLOATCOLUMNS", fetchSettingExportContext.getFloatColumns());
        context.getVarMap().put("MD_AGING", fetchSettingExportContext.getAgingBaseDataList());
        exportExcelSheets.add(this.getFillingInstructions(workbook, fetchSettingExportContext));
        int sheetNo = 1;
        for (ImpExpFormDefine formDefine : fetchSettingExportContext.getFormDefines()) {
            ArrayList<ExcelRegionInfo> excelRegionInfos = new ArrayList<ExcelRegionInfo>();
            ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
            context.getVarMap().put(String.valueOf(sheetNo), excelRegionInfos);
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), FetchSettingNrUtil.getSheetTitleByForm(formDefine));
            List regions = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            List floatRegions = regions.stream().filter(item -> DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)item.getRegionKind())).collect(Collectors.toList());
            List fixRegions = regions.stream().filter(item -> DataRegionKind.DATA_REGION_SIMPLE.equals((Object)item.getRegionKind())).collect(Collectors.toList());
            for (DataRegionDefine regionDefine : fixRegions) {
                List<Object[]> fixSetting = this.fixedFetchSettingExcelRowHandler.handleExportData(fetchSettingExportContext, new FetchSettingCond(fetchSettingExportContext.getFetchSchemeId(), fetchSettingExportContext.getFormSchemeId(), formDefine.getKey(), regionDefine.getKey()));
                if (CollectionUtils.isEmpty(fixSetting)) continue;
                excelRegionInfos.add(new ExcelRegionInfo(RegionTypeEnum.FIXED, rowDatas.size(), rowDatas.size() + fixSetting.size()));
                rowDatas.addAll(fixSetting);
            }
            for (DataRegionDefine regionDefine : floatRegions) {
                List<Object[]> floatSetting;
                block6: {
                    floatSetting = null;
                    try {
                        floatSetting = this.floatFetchSettingExcelRowHandler.handleExportData(fetchSettingExportContext, new FetchSettingCond(fetchSettingExportContext.getFetchSchemeId(), fetchSettingExportContext.getFormSchemeId(), formDefine.getKey(), regionDefine.getKey()));
                        if (CollectionUtils.isEmpty(floatSetting)) {
                        }
                        break block6;
                    }
                    catch (Exception e) {
                        LOGGER.info("\u6d6e\u52a8\u89e3\u6790\u5931\u8d25\uff0c\u5bfc\u51fa\u8df3\u8fc7", e);
                    }
                    continue;
                }
                excelRegionInfos.add(new ExcelRegionInfo(RegionTypeEnum.FLOAT, rowDatas.size(), rowDatas.size() + floatSetting.size()));
                rowDatas.addAll(floatSetting);
            }
            if (CollectionUtils.isEmpty(rowDatas)) continue;
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            exportExcelSheets.add(exportExcelSheet);
            ++sheetNo;
        }
        return exportExcelSheets;
    }

    private ExportExcelSheet getFillingInstructions(Workbook workbook, FetchSettingExportContext fetchSettingExportContext) {
        ExportExcelSheet excelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u586b\u62a5\u8bf4\u660e");
        ExcelCellStyleGroup excelCellStyleGroup = new ExcelCellStyleGroup(workbook);
        List<String[]> titles = ExcelExplainInfo.listExportExcelExplainInfo(fetchSettingExportContext);
        for (String[] titleRow : titles) {
            for (int i = 0; i < titleRow.length; ++i) {
                excelSheet.getHeadCellStyleCache().put(i, excelCellStyleGroup.getHeadStringStyle());
                excelSheet.getContentCellStyleCache().put(i, excelCellStyleGroup.getContentStringStyle());
                excelSheet.getContentCellTypeCache().put(i, CellType.STRING);
            }
            ArrayList<String[]> rowDatas = new ArrayList<String[]>();
            rowDatas.add(titleRow);
            excelSheet.getRowDatas().addAll(rowDatas);
        }
        return excelSheet;
    }
}

