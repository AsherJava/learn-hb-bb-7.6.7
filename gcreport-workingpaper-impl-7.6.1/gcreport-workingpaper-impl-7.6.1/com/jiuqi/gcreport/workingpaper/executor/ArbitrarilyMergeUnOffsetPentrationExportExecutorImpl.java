/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum
 *  com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeService;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ArbitrarilyMergeUnOffsetPentrationExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private AdjustingOffsetEntryExportUtils adjustingOffsetEntryExportUtil;
    @Autowired
    private ArbitrarilyMergeService arbitrarilyMergeService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "ArbitrarilyMergeUnOffsetPentrationExportExecutor";
    }

    @Transactional(rollbackFor={Exception.class})
    public List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        WorkingPaperPentrationQueryCondtion condition = (WorkingPaperPentrationQueryCondtion)JsonUtils.readValue((String)context.getParam(), WorkingPaperPentrationQueryCondtion.class);
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(condition, false);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        Pagination<Map<String, Object>> unOffsetMap = this.arbitrarilyMergeService.getUnOffsetPentrationDatas(condition);
        UnOffsetTabExportExcelSheet offsetSheet = this.createSheet(workbook, queryParamsVO, context.isTemplateExportFlag(), unOffsetMap);
        exportExcelSheets.add((ExportExcelSheet)offsetSheet);
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    private UnOffsetTabExportExcelSheet createSheet(Workbook workbook, QueryParamsVO queryParamsVO, Boolean templateExportFlag, Pagination<Map<String, Object>> unOffsetMap) {
        SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        UnOffsetTabExportExcelSheet unOffsetTabExportExcelSheet = new UnOffsetTabExportExcelSheet(Integer.valueOf(0), "\u672a\u62b5\u9500\u5206\u5f55", Integer.valueOf(1));
        unOffsetTabExportExcelSheet.setAutoMergeHeadFlag(false);
        List otherShowColumns = queryParamsVO.getOtherShowColumns();
        ArrayList dimensionNumberList = new ArrayList();
        int[] cellColumns = new int[]{5, 6};
        String[] titles = this.adjustingOffsetEntryExportUtil.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt"});
        String[] keys = new String[]{"index", "UNITTITLE", "OPPUNITTITLE", "UNIONRULEID", "SUBJECTTITLE", "DEBITVALUE", "CREDITVALUE"};
        int index = 7;
        List gcOffSetVchrItemDTOS = unOffsetMap.getContent();
        String dataSource = StringUtils.hasLength(queryParamsVO.getDataSourceCode()) ? queryParamsVO.getDataSourceCode() : DataSourceEnum.INPUT_DATA.getCode();
        BitSet amtCellColumns = this.adjustingOffsetEntryExportUtil.getAmtCellColumns(otherShowColumns, queryParamsVO.getSystemId(), dataSource, cellColumns, dimensionNumberList, index);
        unOffsetTabExportExcelSheet.setAmtCellCol(amtCellColumns);
        ArrayList titleList = new ArrayList();
        ArrayList keyList = new ArrayList();
        Collections.addAll(titleList, titles);
        Collections.addAll(keyList, keys);
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            keyList.addAll(otherShowColumns);
            titleList.addAll(queryParamsVO.getOtherShowColumnTitles());
        }
        titles = new String[titleList.size()];
        titles = titleList.toArray(titles);
        keys = new String[keyList.size()];
        keys = keyList.toArray(keys);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titles);
        if (templateExportFlag.booleanValue()) {
            unOffsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return unOffsetTabExportExcelSheet;
        }
        int sn = 1;
        for (Map gcOffSetVchrItemDTO : gcOffSetVchrItemDTOS) {
            Object[] rowData = new Object[titleList.size()];
            Map stringObjectMap = gcOffSetVchrItemDTO;
            this.adjustingOffsetEntryExportUtil.adaptMsg(stringObjectMap);
            if (stringObjectMap.containsKey("UNIONRULETITLE")) {
                stringObjectMap.put("UNIONRULEID", stringObjectMap.get("UNIONRULETITLE"));
            } else if (stringObjectMap.containsKey("UNIONRULEID") && !StringUtils.isEmpty(stringObjectMap.get("UNIONRULEID"))) {
                String unionruleTitle = this.adjustingOffsetEntryExportUtil.getRuleTitle((String)stringObjectMap.get("UNIONRULEID"));
                stringObjectMap.put("UNIONRULEID", unionruleTitle);
            }
            if (!stringObjectMap.containsKey("index") && !StringUtils.isEmpty(gcOffSetVchrItemDTO.get("ID"))) {
                stringObjectMap.put("index", sn++);
            }
            for (int j = 0; j < keys.length; ++j) {
                Object valueObj = stringObjectMap.get(keys[j]);
                if (valueObj != null) {
                    if (this.adjustingOffsetEntryExportUtil.isAmt(keys[j]) || dimensionNumberList.contains(keys[j])) {
                        String value;
                        CellType cellType = (CellType)unOffsetTabExportExcelSheet.getContentCellTypeCache().get(j);
                        if (!CellType.NUMERIC.equals((Object)cellType)) {
                            unOffsetTabExportExcelSheet.getContentCellTypeCache().put(j, CellType.NUMERIC);
                            CellStyle cellStyle = (CellStyle)unOffsetTabExportExcelSheet.getContentCellStyleCache().get(j);
                            if (cellStyle == null) {
                                CellStyle contentAmtStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
                                contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
                                contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
                                unOffsetTabExportExcelSheet.getContentCellStyleCache().put(j, contentAmtStyle);
                            }
                        }
                        if ((value = valueObj.toString()).length() == 0) continue;
                        rowData[j] = Double.valueOf(value.replace(",", ""));
                        continue;
                    }
                    if (valueObj instanceof Date) {
                        rowData[j] = exportDateFormat.format((Date)valueObj);
                        continue;
                    }
                    rowData[j] = valueObj.toString();
                    continue;
                }
                rowData[j] = null;
            }
            rowDatas.add(rowData);
        }
        unOffsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
        return unOffsetTabExportExcelSheet;
    }
}

