/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.enums.SubjectExportEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectExportUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectDataExportTask {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedSystemDao systemDao;

    public ExportExcelSheet exportExcelSheet(ExportContext context, String systemId, Workbook workbook) {
        Map<String, String> titleMap = SubjectExportUtils.getSubjectExcelColumnTitleMap();
        ArrayList<String> columnKeys = new ArrayList<String>(titleMap.keySet());
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] headDatas = new Object[columnKeys.size()];
        for (int j = 0; j < columnKeys.size(); ++j) {
            String key = (String)columnKeys.get(j);
            headDatas[j] = titleMap.get(key);
        }
        rowDatas.add(headDatas);
        if (!context.isTemplateExportFlag()) {
            List<Map<String, Object>> dataList = this.exportSubjects(systemId, context);
            dataList.stream().forEach(exportData -> {
                Object[] rowData = new Object[columnKeys.size()];
                for (int j = 0; j < columnKeys.size(); ++j) {
                    String key = (String)columnKeys.get(j);
                    rowData[j] = exportData.get(key);
                }
                rowDatas.add(rowData);
            });
        }
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u4f53\u7cfb\u79d1\u76ee");
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        Map contentCellStyleCache = exportExcelSheet.getContentCellStyleCache();
        short format = workbook.createDataFormat().getFormat("@");
        for (int columnIndex = 0; columnIndex < headDatas.length; ++columnIndex) {
            CellStyle contentCellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
            contentCellStyle.setDataFormat(format);
            contentCellStyleCache.put(columnIndex, contentCellStyle);
        }
        return exportExcelSheet;
    }

    public List<Map<String, Object>> exportSubjects(String systemId, ExportContext context) {
        ConsolidatedSystemEO system = (ConsolidatedSystemEO)this.systemDao.get((Serializable)((Object)systemId));
        Assert.isNotNull((Object)((Object)system), (String)"\u627e\u4e0d\u5230\u5f53\u524d\u4f53\u7cfb.", (Object[])new Object[0]);
        List<ConsolidatedSubjectEO> allSubjects = this.consolidatedSubjectService.listSubjectsBySystemIdWithSortOrder(systemId);
        if (null != context) {
            context.getProgressData().setProgressValueAndRefresh(0.5);
        }
        List<Map<String, Object>> dataList = allSubjects.stream().map(eo -> this.convertEO2ExcelModel((ConsolidatedSubjectEO)eo)).collect(Collectors.toList());
        return dataList;
    }

    private Map<String, Object> convertEO2ExcelModel(ConsolidatedSubjectEO eo) {
        if (eo == null) {
            return null;
        }
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(SubjectExportEnum.CODE.getCode(), eo.getCode());
        dataMap.put(SubjectExportEnum.TITLE.getCode(), eo.getTitle());
        if (eo.getParentCode() != null) {
            ConsolidatedSubjectEO parentEO = this.consolidatedSubjectService.getSubjectByCode(eo.getSystemId(), eo.getParentCode());
            dataMap.put(SubjectExportEnum.PARENTCODE.getCode(), parentEO == null ? null : parentEO.getCode());
        }
        dataMap.put(SubjectExportEnum.STATUS.getCode(), CSConst.CONSOLIDATION_DISABLED.equals(eo.getConsolidationFlag()) ? CSConst.CONSOLIDATION_DISABLED_WORD : CSConst.CONSOLIDATION_ENABLED_WORD);
        dataMap.put(SubjectExportEnum.CONSOLIDATIONTYPE.getCode(), CSConst.CONSOLIDATION_AMOUNT.equals(eo.getConsolidationType()) ? CSConst.CONSOLIDATION_AMOUNT_WORD : CSConst.CONSOLIDATION_BALANCE_WORD);
        dataMap.put(SubjectExportEnum.ORIENT.getCode(), CSConst.CONSOLIDATION_LEND.equals(eo.getOrient()) ? CSConst.CONSOLIDATION_LEND_WORD : CSConst.CONSOLIDATION_BORROW_WORD);
        dataMap.put(SubjectExportEnum.ATTRI.getCode(), SubjectAttributeEnum.getEnumLabel((Integer)eo.getAttri()));
        dataMap.put(SubjectExportEnum.BOUNDINDEXPATH.getCode(), eo.getBoundIndexPath());
        dataMap.putAll(eo.getMultilingualNames());
        return dataMap;
    }
}

