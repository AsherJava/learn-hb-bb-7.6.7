/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.samecontrol.service.GcSameCtrlOffsetExportService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcSameCtrlOffsetExportServiceImpl
implements GcSameCtrlOffsetExportService {
    @Autowired
    private SameCtrlExtractService sameCtrlExtractService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        SameCtrlOffsetCond sameCtrlOffsetCond = (SameCtrlOffsetCond)JsonUtils.readValue((String)context.getParam(), SameCtrlOffsetCond.class);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(sameCtrlOffsetCond.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u540c\u63a7\u7ba1\u7406", (String)("\u6279\u91cf\u5bfc\u51fa-\u65f6\u671f" + sameCtrlOffsetCond.getPeriodStr() + "-\u4efb\u52a1" + taskDefine.getTitle()), (String)"");
        ArrayList<ExportExcelSheet> exportExcelSheetList = new ArrayList<ExportExcelSheet>();
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), "\u540c\u63a7\u7ba1\u7406\u5206\u5f55", Integer.valueOf(1));
        mastSheet.getRowDatas().addAll(this.getHeadData());
        List<SameCtrlOffSetItemVO> sameCtrlOffSetItemVOList = this.listSameCtrlOffset(sameCtrlOffsetCond);
        if (!CollectionUtils.isEmpty(sameCtrlOffSetItemVOList)) {
            mastSheet.getRowDatas().addAll(this.getDataList(sameCtrlOffSetItemVOList));
            Map<String, List<SameCtrlOffSetItemVO>> mRecid2VoList = sameCtrlOffSetItemVOList.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemVO::getmRecid));
            this.mergedRegion(mRecid2VoList, sameCtrlOffSetItemVOList, mastSheet);
        }
        exportExcelSheetList.add(mastSheet);
        return exportExcelSheetList;
    }

    private void mergedRegion(Map<String, List<SameCtrlOffSetItemVO>> mRecid2VoList, List<SameCtrlOffSetItemVO> sameCtrlOffSetItemVOList, ExportExcelSheet mastSheet) {
        ArrayList mRecidList = new ArrayList();
        AtomicInteger count = new AtomicInteger();
        sameCtrlOffSetItemVOList.forEach(sameCtrlOffSetItemVO -> {
            if (!mRecidList.contains(sameCtrlOffSetItemVO.getmRecid())) {
                mRecidList.add(sameCtrlOffSetItemVO.getmRecid());
                int currGroupSize = ((List)mRecid2VoList.get(sameCtrlOffSetItemVO.getmRecid())).size();
                int rowStart = count.addAndGet(1);
                int rowEnd = count.addAndGet(currGroupSize - 1);
                this.addMergedRegion(mastSheet, rowStart, rowEnd, 0, 0);
                this.addMergedRegion(mastSheet, rowStart, rowEnd, 1, 1);
            }
        });
    }

    private void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    private List<SameCtrlOffSetItemVO> listSameCtrlOffset(SameCtrlOffsetCond sameCtrlOffsetCond) {
        sameCtrlOffsetCond.setPageNum(-1);
        sameCtrlOffsetCond.setPageSize(-1);
        Pagination<SameCtrlOffSetItemVO> pagination = this.sameCtrlExtractService.listOffsetSameCtrlManage(sameCtrlOffsetCond);
        return pagination.getContent();
    }

    private List<Object[]> getDataList(List<SameCtrlOffSetItemVO> sameCtrlOffSetItemVOList) {
        ArrayList<Object[]> rowDataList = new ArrayList<Object[]>();
        AtomicInteger count = new AtomicInteger();
        ArrayList mRecidList = new ArrayList();
        sameCtrlOffSetItemVOList.forEach(sameCtrlOffSetItemVO -> {
            Object[] dataObject = new Object[11];
            if (!mRecidList.contains(sameCtrlOffSetItemVO.getmRecid())) {
                count.getAndIncrement();
                mRecidList.add(sameCtrlOffSetItemVO.getmRecid());
            }
            dataObject[0] = count.get();
            dataObject[1] = sameCtrlOffSetItemVO.getSourceMethodTtile();
            dataObject[2] = sameCtrlOffSetItemVO.getSameCtrlSrcTypeTitle();
            dataObject[3] = sameCtrlOffSetItemVO.getRuleTitle();
            dataObject[4] = sameCtrlOffSetItemVO.getUnitTitle();
            dataObject[5] = sameCtrlOffSetItemVO.getOppUnitTitle();
            dataObject[6] = sameCtrlOffSetItemVO.getSubjectTitle();
            dataObject[7] = sameCtrlOffSetItemVO.getOffsetDebitStr();
            dataObject[8] = sameCtrlOffSetItemVO.getOffsetCreditStr();
            dataObject[9] = sameCtrlOffSetItemVO.getDiffStr();
            dataObject[10] = StringUtils.isEmpty((String)sameCtrlOffSetItemVO.getMemo()) ? "\u65e0" : sameCtrlOffSetItemVO.getMemo();
            rowDataList.add(dataObject);
        });
        return rowDataList;
    }

    private List<Object[]> getHeadData() {
        ArrayList<Object[]> rowDataList = new ArrayList<Object[]>();
        Object[] headTitles = new Object[]{"\u5e8f\u53f7", "\u6765\u6e90\u65b9\u5f0f", "\u62b5\u9500\u6765\u6e90\u7c7b\u578b", "\u5408\u5e76\u89c4\u5219", "\u672c\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d", "\u79d1\u76ee", "\u501f\u65b9\u91d1\u989d", "\u8d37\u65b9\u91d1\u989d", "\u5dee\u989d", "\u63cf\u8ff0"};
        rowDataList.add(headTitles);
        return rowDataList;
    }
}

