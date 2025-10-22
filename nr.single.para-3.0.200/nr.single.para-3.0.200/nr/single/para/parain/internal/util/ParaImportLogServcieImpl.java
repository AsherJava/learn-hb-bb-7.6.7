/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.office.excel2.CacheSXSSFWorkbook
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.data.PathUtil
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import nr.single.map.data.PathUtil;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.bean.ParaImportItemResult;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.common.FieldUseType;
import nr.single.para.compare.internal.util.CompareUtil;
import nr.single.para.parain.util.IParaImportFileServcie;
import nr.single.para.parain.util.IParaImportLogServcie;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ParaImportLogServcieImpl
implements IParaImportLogServcie {
    private static final Logger logger = LoggerFactory.getLogger(ParaImportLogServcieImpl.class);
    private static final int LOG_COLWITH = 6000;
    @Autowired
    private IParaImportFileServcie fileService;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String makeDetailLogFile(ParaImportResult result, AsyncTaskMonitor asyncMonitor) throws Exception {
        String fileKey = null;
        String fileName = "\u53c2\u6570\u5bfc\u5165\u65e5\u5fd7\u6587\u4ef6" + CompareUtil.getTempTimeCode() + ".xls";
        String filePath = CompareUtil.getCompareFilePath();
        try {
            String filePath2 = this.exportToLogfing(filePath, fileName, result, asyncMonitor);
            if (StringUtils.isNotEmpty((CharSequence)filePath2)) {
                fileKey = this.fileService.uploadFile(fileName, filePath2);
                result.setLogFileKey(fileKey);
                result.setLogFile(fileName);
                PathUtil.deleteFile((String)filePath2);
            }
        }
        finally {
            PathUtil.deleteDir((String)filePath);
        }
        return fileKey;
    }

    private String exportToLogfing(String filePath, String fileName, ParaImportResult result, AsyncTaskMonitor asyncMonitor) throws Exception {
        filePath = filePath + fileName;
        CacheSXSSFWorkbook sxWorkbook = new CacheSXSSFWorkbook(2000);
        try {
            ParaImportInfoResult enumsResult = null;
            ParaImportInfoResult formsResult = null;
            if (result.getInfoDic().containsKey((Object)CompareDataType.DATA_ENUM)) {
                enumsResult = result.getInfoDic().get((Object)CompareDataType.DATA_ENUM);
            }
            if (result.getInfoDic().containsKey((Object)CompareDataType.DATA_FORM)) {
                formsResult = result.getInfoDic().get((Object)CompareDataType.DATA_FORM);
            }
            try {
                this.writeStatLog(result.getInfos(), sxWorkbook, "\u7edf\u8ba1");
            }
            catch (Exception e2) {
                logger.error("\u751f\u6210\u7edf\u8ba1\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
            }
            double secStep = 0.05 / (double)(result.getInfos().size() + 1);
            double oldStep = 0.95;
            for (ParaImportInfoResult data : result.getInfos()) {
                try {
                    if (asyncMonitor != null) {
                        asyncMonitor.progressAndMessage(oldStep + secStep, "\u751f\u6210\u65e5\u5fd7\u6587\u4ef6\uff1a" + data.getDataType().getTitle());
                    }
                    if (data.getDataType() == CompareDataType.DATA_FMDMFIELD) {
                        try {
                            this.writeFMDMLog(data, sxWorkbook, "\u5c01\u9762\u4ee3\u7801");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u5c01\u9762\u4ee3\u7801\u5b57\u6bb5\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_ENUM) {
                        try {
                            this.writeEnumLog(data, sxWorkbook, "\u679a\u4e3e");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u679a\u4e3e\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_ENUMITEM) {
                        try {
                            this.writeEnumItemLog(data, enumsResult, sxWorkbook, "\u679a\u4e3e\u9879");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u679a\u4e3e\u9879\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_FORM) {
                        try {
                            this.writeFormLog(data, sxWorkbook, "\u8868\u5355");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u8868\u5355\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_FIELD) {
                        try {
                            this.writeFieldLog(data, formsResult, sxWorkbook, "\u6307\u6807");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u6307\u6807\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_FORMULA) {
                        try {
                            this.writeFormulaSchemeLog(data, sxWorkbook, "\u516c\u5f0f\u65b9\u6848");
                            this.writeFormulaLog(data, sxWorkbook, "\u516c\u5f0f");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u516c\u5f0f\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_PRINTTITEM) {
                        try {
                            this.writePrintLog(data, sxWorkbook, "\u6253\u5370\u65b9\u6848");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u6253\u5370\u65b9\u6848\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() == CompareDataType.DATA_TASKLINK) {
                        try {
                            this.writeTaskLinkLog(data, sxWorkbook, "\u5173\u8054\u4efb\u52a1");
                        }
                        catch (Exception e2) {
                            logger.error("\u751f\u6210\u5173\u8054\u4efb\u52a1\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                        }
                        continue;
                    }
                    if (data.getDataType() != CompareDataType.DATA_EXCEPTION) continue;
                    try {
                        if (data.getItems().size() <= 0) continue;
                        this.writeExceptionLog(data, sxWorkbook, "\u5f02\u5e38\u4fe1\u606f");
                    }
                    catch (Exception e2) {
                        logger.error("\u751f\u6210\u5f02\u5e38\u4fe1\u606f\u65e5\u5fd7\u51fa\u9519\uff1a" + e2.getMessage(), e2);
                    }
                }
                catch (Exception e1) {
                    logger.error("\u751f\u6210\u65e5\u5fd7\u51fa\u9519\u539f\u56e0\uff1a" + e1.getMessage(), e1);
                }
            }
            try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)filePath));){
                sxWorkbook.write((OutputStream)out);
            }
            logger.error("\u751f\u6210\u65e5\u5fd7\u6587\u4ef6\u5b8c\u6210");
        }
        catch (Exception e) {
            filePath = null;
            logger.error("\u751f\u6210\u65e5\u5fd7\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        finally {
            if (sxWorkbook != null) {
                sxWorkbook.dispose();
            }
        }
        return filePath;
    }

    @Override
    public void deleteLogFile(String fileKey) {
        this.fileService.deleteFile(fileKey);
    }

    private void writeStatLog(List<ParaImportInfoResult> datas, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        HashSet<CompareUpdateType> hasTypes = new HashSet<CompareUpdateType>();
        ArrayList<CompareUpdateType> sortUpdateTypes = new ArrayList<CompareUpdateType>();
        sortUpdateTypes.add(CompareUpdateType.UPDATE_NEW);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_IGNORE);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_OVER);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_UNOVER);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_APPOINT);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_RECODE);
        sortUpdateTypes.add(CompareUpdateType.UPDATE_KEEP);
        sortUpdateTypes.add(CompareUpdateType.UPDATA_USENET);
        sortUpdateTypes.add(CompareUpdateType.UPDATA_USESINGLE);
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5bfc\u5165\u5185\u5bb9");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u66f4\u65b0\u65b9\u5f0f");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u6570\u91cf\u7edf\u8ba1");
        int row = 1;
        for (i = 0; i < datas.size(); ++i) {
            ParaImportInfoResult data = datas.get(i);
            String importTitle = "";
            String statTitle = "";
            HashMap updateDic = new HashMap();
            for (ParaImportInfoResult item : data.getItems()) {
                List<ParaImportInfoResult> itemList = null;
                if (updateDic.containsKey((Object)item.getUpdateType())) {
                    itemList = (List)updateDic.get((Object)item.getUpdateType());
                } else {
                    itemList = new ArrayList();
                    updateDic.put(item.getUpdateType(), itemList);
                }
                itemList.add(item);
            }
            if (data.getDataType() == CompareDataType.DATA_FMDMFIELD) {
                importTitle = "\u5c01\u9762\u4ee3\u7801";
                statTitle = "\u5c01\u9762\u4ee3\u7801\u6307\u6807";
            } else if (data.getDataType() == CompareDataType.DATA_ENUM) {
                importTitle = "\u679a\u4e3e";
                statTitle = "\u679a\u4e3e";
            } else if (data.getDataType() == CompareDataType.DATA_ENUMITEM) {
                importTitle = "\u679a\u4e3e\u9879";
                statTitle = "\u679a\u4e3e\u9879";
            } else if (data.getDataType() == CompareDataType.DATA_FORM) {
                importTitle = "\u8868\u5355";
                statTitle = "\u8868\u5355";
            } else if (data.getDataType() == CompareDataType.DATA_FIELD) {
                importTitle = "\u6307\u6807";
                statTitle = "\u6307\u6807";
            } else if (data.getDataType() == CompareDataType.DATA_FORMULA) {
                importTitle = "\u516c\u5f0f";
                statTitle = "\u516c\u5f0f";
            } else if (data.getDataType() == CompareDataType.DATA_PRINTTITEM) {
                importTitle = "\u6253\u5370\u65b9\u6848";
                statTitle = "\u6253\u5370\u6a21\u677f";
            } else if (data.getDataType() == CompareDataType.DATA_TASKLINK) {
                importTitle = "\u5173\u8054\u4efb\u52a1";
                statTitle = "\u5173\u8054\u4efb\u52a1";
            }
            if (data.getDataType() == CompareDataType.DATA_FORMULA) {
                xssRow = sheet1.createRow(row);
                xssCell = xssRow.createCell(0);
                xssCell.setCellValue("\u516c\u5f0f\u65b9\u6848");
                xssCell = xssRow.createCell(1);
                xssCell.setCellValue("");
                xssCell = xssRow.createCell(2);
                xssCell.setCellValue(data.getItems().size() + "\u4e2a\u516c\u5f0f\u65b9\u6848");
                ++row;
                continue;
            }
            if (data.getDataType() == CompareDataType.DATA_TASKLINK) {
                xssRow = sheet1.createRow(row);
                xssCell = xssRow.createCell(0);
                xssCell.setCellValue(importTitle);
                xssCell = xssRow.createCell(1);
                xssCell.setCellValue("");
                xssCell = xssRow.createCell(2);
                xssCell.setCellValue(data.getItems().size() + "\u4e2a" + statTitle);
                ++row;
                continue;
            }
            boolean isFirst = false;
            for (CompareUpdateType updateType : sortUpdateTypes) {
                if (!updateDic.containsKey((Object)updateType)) continue;
                List itemList = (List)updateDic.get((Object)updateType);
                xssRow = sheet1.createRow(row);
                xssCell = xssRow.createCell(0);
                if (!isFirst) {
                    xssCell.setCellValue(importTitle);
                    isFirst = true;
                } else {
                    xssCell.setCellValue("");
                }
                xssCell = xssRow.createCell(1);
                if (updateType != null) {
                    xssCell.setCellValue(updateType.getTitle());
                }
                xssCell = xssRow.createCell(2);
                xssCell.setCellValue(itemList.size() + "\u4e2a" + statTitle);
                ++row;
                hasTypes.add(updateType);
            }
        }
        for (i = 0; i <= 2; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeFMDMLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u5b57\u6bb5\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u5b57\u6bb5\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u5c5e\u6027");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u5b57\u6bb5\u6807\u8bc6");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u7f51\u62a5\u5b57\u6bb5\u540d\u79f0");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u53d8\u5316\u5185\u5bb9");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u6240\u5c5e\u8868");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(9);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult field = data.getItems().get(i);
            CompareDataFMDMFieldDTO fmdmFieldDTO = (CompareDataFMDMFieldDTO)field.getData();
            SXSSFRow dataRow = sheet1.createRow(i + 1);
            SXSSFCell dataCell = null;
            dataCell = dataRow.createCell(0);
            dataCell.setCellValue(field.getSingleCode());
            dataCell = dataRow.createCell(1);
            dataCell.setCellValue(field.getSingleTitle());
            dataCell = dataRow.createCell(2);
            if (fmdmFieldDTO != null && fmdmFieldDTO.getSingleUseType() != null) {
                dataCell.setCellValue(fmdmFieldDTO.getSingleUseType().getTitle());
            }
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(field.getNetCode());
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(field.getNetTitle());
            dataCell = dataRow.createCell(5);
            if (fmdmFieldDTO != null && fmdmFieldDTO.getSingleUseType() == FieldUseType.USE_QYMC) {
                dataCell.setCellValue("");
            } else {
                dataCell.setCellValue(field.getChangeType().getTitle());
            }
            dataCell = dataRow.createCell(6);
            if (fmdmFieldDTO != null && fmdmFieldDTO.getSingleUseType() == FieldUseType.USE_QYMC) {
                dataCell.setCellValue("\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u7c7b\u578bNAME\u5b57\u6bb5");
            } else {
                dataCell.setCellValue(field.getUpdateType().getTitle());
            }
            dataCell = dataRow.createCell(7);
            CompareDataFMDMFieldDTO fieldDTO = (CompareDataFMDMFieldDTO)field.getData();
            if (fieldDTO.getOwnerTableType() == CompareTableType.TABLE_FIX) {
                dataCell.setCellValue("\u6307\u6807\u8868");
            }
            if (fieldDTO.getOwnerTableType() == CompareTableType.TABLE_MDINFO) {
                dataCell.setCellValue("\u5c01\u9762\u4fe1\u606f\u8868");
            } else {
                dataCell.setCellValue("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
            }
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue("\u6210\u529f");
            if (!field.isSuccess()) {
                dataCell.setCellValue("\u5931\u8d25");
            }
            dataCell = dataRow.createCell(9);
            dataCell.setCellValue(field.getMessage());
        }
        for (i = 0; i <= 9; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeEnumLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u679a\u4e3e\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u679a\u4e3e\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u6807\u8bc6");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u540d\u79f0");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u5bf9\u6bd4\u7ed3\u679c");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u679a\u4e3e\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(dataLog.getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(dataLog.getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(dataLog.getNetCode());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(dataLog.getNetTitle());
            xssCell = xssRow.createCell(4);
            xssCell.setCellValue(dataLog.getChangeType().getTitle());
            xssCell = xssRow.createCell(5);
            xssCell.setCellValue(dataLog.getUpdateType().getTitle());
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue("\u6210\u529f");
            if (!dataLog.isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 7; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeEnumItemLog(ParaImportInfoResult data, ParaImportInfoResult enumsResult, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1;
        HashMap<String, ParaImportInfoResult> enumInfos = new HashMap<String, ParaImportInfoResult>();
        if (enumsResult != null) {
            for (ParaImportInfoResult enumResult : enumsResult.getItems()) {
                enumInfos.put(enumResult.getCompareDataKey(), enumResult);
            }
        }
        if (null == (sheet1 = sxWorkbook.getSheet(sheetName))) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u679a\u4e3e\u9879\u7f16\u7801");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u679a\u4e3e\u9879\u542b\u4e49");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u9879\u7f16\u7801");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u9879\u542b\u4e49");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u6807\u8bc6");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u7f51\u62a5\u679a\u4e3e\u540d\u79f0");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u5bf9\u6bd4\u7ed3\u679c");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u679a\u4e3e\u9879\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(9);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            CompareDataEnumItemDTO enumItemDTO = (CompareDataEnumItemDTO)dataLog.getData();
            ParaImportInfoResult enumInfo = null;
            if (enumItemDTO != null && enumInfos.containsKey(enumItemDTO.getEnumCompareKey())) {
                enumInfo = (ParaImportInfoResult)enumInfos.get(enumItemDTO.getEnumCompareKey());
            }
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(dataLog.getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(dataLog.getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(dataLog.getNetCode());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(dataLog.getNetTitle());
            xssCell = xssRow.createCell(4);
            if (enumInfo != null) {
                xssCell.setCellValue(enumInfo.getNetCode());
            }
            xssCell = xssRow.createCell(5);
            if (enumInfo != null) {
                xssCell.setCellValue(enumInfo.getNetTitle());
            }
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue(dataLog.getChangeType().getTitle());
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue(dataLog.getUpdateType().getTitle());
            xssCell = xssRow.createCell(8);
            xssCell.setCellValue("\u6210\u529f");
            if (!dataLog.isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(9);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 9; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeFormLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = null;
        SXSSFCell xssCell = null;
        xssRow = sheet1.createRow(0);
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u62a5\u8868\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u62a5\u8868\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u7f51\u62a5\u62a5\u8868\u6807\u8bc6");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u62a5\u8868\u540d\u79f0");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u53d8\u5316\u5185\u5bb9");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(dataLog.getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(dataLog.getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(dataLog.getNetCode());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(dataLog.getNetTitle());
            xssCell = xssRow.createCell(4);
            xssCell.setCellValue(dataLog.getChangeType().getTitle());
            xssCell = xssRow.createCell(5);
            xssCell.setCellValue(dataLog.getUpdateType().getTitle());
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue("\u6210\u529f");
            if (!dataLog.isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 7; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeFieldLog(ParaImportInfoResult data, ParaImportInfoResult formsResult, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        List forms;
        List tables;
        CompareDataFieldDTO fieldDTO;
        Object dataLog;
        int i;
        SXSSFSheet sheet1;
        HashMap<String, ParaImportInfoResult> formInfos = new HashMap<String, ParaImportInfoResult>();
        if (formsResult != null) {
            for (ParaImportInfoResult formResult : formsResult.getItems()) {
                formInfos.put(formResult.getCompareDataKey(), formResult);
            }
        }
        if (null == (sheet1 = sxWorkbook.getSheet(sheetName))) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u6307\u6807\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u6307\u6807\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u5355\u673a\u7248\u5339\u914d\u540d\u79f0");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u5339\u914d\u540d\u79f0");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u7f51\u62a5\u6307\u6807\u6807\u8bc6");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u7f51\u62a5\u6307\u6807\u540d\u79f0");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u7f51\u62a5\u8868\u5355\u6807\u8bc6");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u7f51\u62a5\u8868\u5355\u540d\u79f0");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u6240\u5728\u8868\u6807\u8bc6");
        xssCell = xssRow.createCell(9);
        xssCell.setCellValue("\u6240\u5728\u8868\u540d\u79f0");
        xssCell = xssRow.createCell(10);
        xssCell.setCellValue("\u53d8\u5316\u5185\u5bb9");
        xssCell = xssRow.createCell(11);
        xssCell.setCellValue("\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(12);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(13);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        HashMap<String, DesignFormDefine> netForms = new HashMap<String, DesignFormDefine>();
        HashMap<String, DesignDataTable> netDataTables = new HashMap<String, DesignDataTable>();
        ArrayList<String> netDataTableKeys = new ArrayList<String>();
        HashSet<String> netDataTableSet = new HashSet<String>();
        ArrayList<String> netFormKeys = new ArrayList<String>();
        HashSet<String> netFormSet = new HashSet<String>();
        for (i = 0; i < data.getItems().size(); ++i) {
            dataLog = data.getItems().get(i);
            if (StringUtils.isNotEmpty((CharSequence)((ParaImportItemResult)dataLog).getParentNetKey()) && !netDataTableSet.contains(((ParaImportItemResult)dataLog).getParentNetKey())) {
                netDataTableKeys.add(((ParaImportItemResult)dataLog).getParentNetKey());
                netDataTableSet.add(((ParaImportItemResult)dataLog).getParentNetKey());
            }
            if ((fieldDTO = (CompareDataFieldDTO)((ParaImportItemResult)dataLog).getData()) == null || !StringUtils.isNotEmpty((CharSequence)fieldDTO.getNetFormKey()) || !StringUtils.isNotEmpty((CharSequence)fieldDTO.getFormCompareKey()) || formInfos.containsKey(fieldDTO.getFormCompareKey()) || netFormSet.contains(fieldDTO.getNetFormKey())) continue;
            netFormKeys.add(fieldDTO.getNetFormKey());
            netFormSet.add(fieldDTO.getNetFormKey());
        }
        if (!netDataTableKeys.isEmpty() && (tables = this.dataSchemeService.getDataTables(netDataTableKeys)) != null && !tables.isEmpty()) {
            for (DesignDataTable table : tables) {
                netDataTables.put(table.getKey(), table);
            }
        }
        if (!netFormKeys.isEmpty() && (forms = this.viewController.getSimpleFormDefines(netFormKeys)) != null && !forms.isEmpty()) {
            for (DesignFormDefine form : forms) {
                netForms.put(form.getKey(), form);
            }
        }
        for (i = 0; i < data.getItems().size(); ++i) {
            dataLog = data.getItems().get(i);
            fieldDTO = (CompareDataFieldDTO)((ParaImportItemResult)dataLog).getData();
            DesignFormDefine formDefine = null;
            DesignDataField dataField = null;
            DesignDataTable dataTable = null;
            ParaImportInfoResult formInfo = null;
            if (formInfos.containsKey(fieldDTO.getFormCompareKey())) {
                formInfo = (ParaImportInfoResult)formInfos.get(fieldDTO.getFormCompareKey());
            }
            if (formInfo == null && StringUtils.isNotEmpty((CharSequence)fieldDTO.getNetFormKey())) {
                if (netForms.containsKey(fieldDTO.getNetFormKey())) {
                    formDefine = (DesignFormDefine)netForms.get(fieldDTO.getNetFormKey());
                } else {
                    formDefine = this.viewController.querySoftFormDefine(fieldDTO.getNetFormKey());
                    netForms.put(fieldDTO.getNetFormKey(), formDefine);
                }
            }
            if (StringUtils.isNotEmpty((CharSequence)((ParaImportItemResult)dataLog).getParentNetKey())) {
                if (netDataTables.containsKey(((ParaImportItemResult)dataLog).getParentNetKey())) {
                    dataTable = (DesignDataTable)netDataTables.get(((ParaImportItemResult)dataLog).getParentNetKey());
                } else {
                    dataTable = this.dataSchemeService.getDataTable(((ParaImportItemResult)dataLog).getParentNetKey());
                    netDataTables.put(((ParaImportItemResult)dataLog).getParentNetKey(), dataTable);
                }
            } else if (StringUtils.isNotEmpty((CharSequence)fieldDTO.getNetKey())) {
                dataField = this.dataSchemeService.getDataField(((ParaImportItemResult)dataLog).getNetKey());
                if (netDataTables.containsKey(dataField.getDataTableKey())) {
                    dataTable = (DesignDataTable)netDataTables.get(dataField.getDataTableKey());
                } else {
                    dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey());
                    netDataTables.put(dataField.getDataTableKey(), dataTable);
                }
            }
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(fieldDTO.getSingleMatchTitle());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(fieldDTO.getNetMatchTitle());
            xssCell = xssRow.createCell(4);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getNetCode());
            xssCell = xssRow.createCell(5);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getNetTitle());
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue("");
            if (formInfo != null) {
                xssCell.setCellValue(formInfo.getNetCode());
            } else if (formDefine != null) {
                xssCell.setCellValue(formDefine.getFormCode());
            }
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue("");
            if (formInfo != null) {
                xssCell.setCellValue(formInfo.getNetTitle());
            } else if (formDefine != null) {
                xssCell.setCellValue(formDefine.getTitle());
            }
            xssCell = xssRow.createCell(8);
            xssCell.setCellValue("");
            if (dataTable != null) {
                xssCell.setCellValue(dataTable.getCode());
            }
            xssCell = xssRow.createCell(9);
            xssCell.setCellValue("");
            if (dataTable != null) {
                xssCell.setCellValue(dataTable.getTitle());
            }
            xssCell = xssRow.createCell(10);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getChangeType().getTitle());
            xssCell = xssRow.createCell(11);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getUpdateType().getTitle());
            xssCell = xssRow.createCell(12);
            xssCell.setCellValue("\u6210\u529f");
            if (!((ParaImportItemResult)dataLog).isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(13);
            xssCell.setCellValue(((ParaImportItemResult)dataLog).getMessage());
        }
        for (i = 0; i <= 13; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeFormulaSchemeLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u516c\u5f0f\u65b9\u6848");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u7f51\u62a5\u516c\u5f0f\u65b9\u6848");
        int rowcount = 0;
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult schemeLog = data.getItems().get(i);
            xssRow = sheet1.createRow(++rowcount);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(schemeLog.getSingleTitle());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(schemeLog.getNetTitle());
        }
        for (i = 0; i <= 2; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeFormulaLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u516c\u5f0f\u65b9\u6848");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u516c\u5f0f\u6807\u8bc6");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u5355\u673a\u7248\u516c\u5f0f\u8868\u8fbe\u5f0f");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u516c\u5f0f\u65b9\u6848");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u7f51\u62a5\u516c\u5f0f\u7f16\u53f7");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u7f51\u62a5\u516c\u5f0f\u8868\u8fbe\u5f0f");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u6240\u5c5e\u8868");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(9);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        int rowcount = 0;
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult schemeLog = data.getItems().get(i);
            for (int j = 0; j < schemeLog.getItems().size(); ++j) {
                ParaImportInfoResult formLog = schemeLog.getItems().get(j);
                for (int k = 0; k < formLog.getItems().size(); ++k) {
                    ParaImportInfoResult formulaLog = formLog.getItems().get(k);
                    SXSSFRow formulaXssRow = sheet1.createRow(++rowcount);
                    xssCell = null;
                    xssCell = formulaXssRow.createCell(0);
                    xssCell.setCellValue(schemeLog.getSingleTitle());
                    xssCell = formulaXssRow.createCell(1);
                    xssCell.setCellValue(formulaLog.getSingleCode());
                    xssCell = formulaXssRow.createCell(2);
                    xssCell.setCellValue(formulaLog.getSingleTitle());
                    xssCell = formulaXssRow.createCell(3);
                    xssCell.setCellValue(schemeLog.getSingleTitle());
                    xssCell = formulaXssRow.createCell(4);
                    xssCell.setCellValue(formulaLog.getNetCode());
                    xssCell = formulaXssRow.createCell(5);
                    xssCell.setCellValue(formulaLog.getNetTitle());
                    xssCell = formulaXssRow.createCell(6);
                    xssCell.setCellValue(formLog.getSingleCode());
                    xssCell = formulaXssRow.createCell(7);
                    xssCell.setCellValue(formulaLog.getUpdateType().getTitle());
                    xssCell = formulaXssRow.createCell(8);
                    xssCell.setCellValue("\u6210\u529f");
                    if (!formulaLog.isSuccess()) {
                        xssCell.setCellValue("\u5931\u8d25");
                    }
                    xssCell = formulaXssRow.createCell(9);
                    xssCell.setCellValue(formulaLog.getMessage());
                }
            }
        }
        for (i = 0; i <= 8; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writePrintLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u62a5\u8868\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u62a5\u8868\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u7f51\u62a5\u62a5\u8868\u6807\u8bc6");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u7f51\u62a5\u62a5\u8868\u540d\u79f0");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u6253\u5370\u65b9\u6848");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u53d8\u5316\u5185\u5bb9");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u5904\u7406\u65b9\u5f0f");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            CompareDataPrintItemDTO printDTO = (CompareDataPrintItemDTO)dataLog.getData();
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(printDTO.getSingleFormCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(printDTO.getSingleFormTitile());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(printDTO.getNetFormCode());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(printDTO.getNetFormTitle());
            xssCell = xssRow.createCell(4);
            xssCell.setCellValue(printDTO.getNetPrintScheme());
            xssCell = xssRow.createCell(5);
            xssCell.setCellValue(dataLog.getChangeType().getTitle());
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue(dataLog.getUpdateType().getTitle());
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue("\u6210\u529f");
            if (!dataLog.isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(8);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 8; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeTaskLinkLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5355\u673a\u7248\u4efb\u52a1\u6807\u8bc6");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5355\u673a\u7248\u4efb\u52a1\u540d\u79f0");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u5e74\u5ea6");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u4efb\u52a1\u7c7b\u522b");
        xssCell = xssRow.createCell(4);
        xssCell.setCellValue("\u5173\u8054\u7f51\u62a5\u4efb\u52a1");
        xssCell = xssRow.createCell(5);
        xssCell.setCellValue("\u5173\u8054\u62a5\u8868\u65b9\u6848");
        xssCell = xssRow.createCell(6);
        xssCell.setCellValue("\u5f53\u524d\u4efb\u52a1\u8868\u8fbe\u5f0f");
        xssCell = xssRow.createCell(7);
        xssCell.setCellValue("\u5173\u8054\u4efb\u52a1\u5bf9\u5e94\u8868\u8fbe\u5f0f");
        xssCell = xssRow.createCell(8);
        xssCell.setCellValue("\u72b6\u6001");
        xssCell = xssRow.createCell(9);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            CompareDataTaskLinkDTO linkDTO = (CompareDataTaskLinkDTO)dataLog.getData();
            DesignTaskDefine linktaskDefine = null;
            DesignFormSchemeDefine formScheme = null;
            if (StringUtils.isNotEmpty((CharSequence)linkDTO.getNetTaskKey())) {
                linktaskDefine = this.viewController.queryTaskDefine(linkDTO.getNetTaskKey());
            }
            if (StringUtils.isNotEmpty((CharSequence)linkDTO.getNetFormSchemeKey())) {
                formScheme = this.viewController.queryFormSchemeDefine(linkDTO.getNetFormSchemeKey());
            }
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(linkDTO.getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(linkDTO.getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(linkDTO.getSingleTaskYear());
            xssCell = xssRow.createCell(3);
            if (linkDTO.getSingleTaskType() != null) {
                xssCell.setCellValue(linkDTO.getSingleTaskType().title());
            }
            xssCell = xssRow.createCell(4);
            xssCell.setCellValue("");
            if (linktaskDefine != null) {
                xssCell.setCellValue(linktaskDefine.getTitle());
            }
            xssCell = xssRow.createCell(5);
            xssCell.setCellValue("");
            if (formScheme != null) {
                xssCell.setCellValue(formScheme.getTitle());
            }
            xssCell = xssRow.createCell(6);
            xssCell.setCellValue(linkDTO.getSingleCurrentExp());
            xssCell = xssRow.createCell(7);
            xssCell.setCellValue(linkDTO.getSingleLinkExp());
            xssCell = xssRow.createCell(8);
            xssCell.setCellValue("\u6210\u529f");
            if (!dataLog.isSuccess()) {
                xssCell.setCellValue("\u5931\u8d25");
            }
            xssCell = xssRow.createCell(9);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 9; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }

    private void writeExceptionLog(ParaImportInfoResult data, CacheSXSSFWorkbook sxWorkbook, String sheetName) {
        int i;
        SXSSFSheet sheet1 = sxWorkbook.getSheet(sheetName);
        if (null == sheet1) {
            sheet1 = sxWorkbook.createSheet(sheetName);
            sheet1.setRowSumsBelow(false);
        }
        SXSSFRow xssRow = sheet1.createRow(0);
        SXSSFCell xssCell = null;
        xssCell = xssRow.createCell(0);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f\u7f16\u7801");
        xssCell = xssRow.createCell(1);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f\u5206\u7c7b");
        xssCell = xssRow.createCell(2);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f\u5185\u5bb9");
        xssCell = xssRow.createCell(3);
        xssCell.setCellValue("\u5f02\u5e38\u4fe1\u606f\u63cf\u8ff0");
        for (i = 0; i < data.getItems().size(); ++i) {
            ParaImportInfoResult dataLog = data.getItems().get(i);
            xssRow = sheet1.createRow(i + 1);
            xssCell = null;
            xssCell = xssRow.createCell(0);
            xssCell.setCellValue(dataLog.getSingleCode());
            xssCell = xssRow.createCell(1);
            xssCell.setCellValue(dataLog.getSingleTitle());
            xssCell = xssRow.createCell(2);
            xssCell.setCellValue(dataLog.getNetTitle());
            xssCell = xssRow.createCell(3);
            xssCell.setCellValue(dataLog.getMessage());
        }
        for (i = 0; i <= 3; ++i) {
            sheet1.setColumnWidth(i, 6000);
        }
        sheet1.createFreezePane(0, 1);
    }
}

