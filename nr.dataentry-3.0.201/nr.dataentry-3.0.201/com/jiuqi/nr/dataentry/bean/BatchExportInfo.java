/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.ExportRuleSettings;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BatchExportInfo
extends JtableLog
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String fileType;
    private String compressionType;
    private String excelType;
    private boolean cellBackGround;
    private boolean emptyTable;
    private boolean tableTab;
    private boolean expExcelDirSheet;
    private boolean sumData;
    private boolean createFileByReport;
    private boolean exportEnclosure;
    private String location;
    private String zipLocation;
    private String fileName;
    private String configKey;
    private String printSchemeKey;
    private boolean arithmeticBackground;
    private String downLoadKey;
    private String splitMark;
    private boolean exportEmptyTable;
    private boolean exportETFile;
    private boolean multiplePeriod;
    private String multiplePeriodInfo;
    private Integer periodType;
    private String secretLevel;
    private String thisSecretLevel;
    private boolean specifyPath = false;
    private List<RegionFilterListInfo> regionFilterListInfo;
    private ExportRuleSettings ruleSettings;
    private String excelExpPath;
    private int formSize = 0;
    private String exportWorkDir;
    private boolean exportZero;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }

    public ExportRuleSettings getRuleSettings() {
        return this.ruleSettings;
    }

    public void setRuleSettings(ExportRuleSettings ruleSettings) {
        this.ruleSettings = ruleSettings;
    }

    public int getFormSize() {
        return this.formSize;
    }

    public void setFormSize(int formSize) {
        this.formSize = formSize;
    }

    public String getSplitMark() {
        return this.splitMark;
    }

    public void setSplitMark(String splitMark) {
        this.splitMark = splitMark;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExcelType() {
        return this.excelType;
    }

    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }

    public boolean isCellBackGround() {
        return this.cellBackGround;
    }

    public void setCellBackGround(boolean cellBackGround) {
        this.cellBackGround = cellBackGround;
    }

    public boolean isEmptyTable() {
        return this.emptyTable;
    }

    public void setEmptyTable(boolean emptyTable) {
        this.emptyTable = emptyTable;
    }

    public boolean isTableTab() {
        return this.tableTab;
    }

    public void setTableTab(boolean tableTab) {
        this.tableTab = tableTab;
    }

    public boolean isCreateFileByReport() {
        return this.createFileByReport;
    }

    public void setCreateFileByReport(boolean createFileByReport) {
        this.createFileByReport = createFileByReport;
    }

    public boolean isExportEnclosure() {
        return this.exportEnclosure;
    }

    public void setExportEnclosure(boolean exportEnclosure) {
        this.exportEnclosure = exportEnclosure;
    }

    public List<String> getFormKeys() {
        String formKey = this.context.getFormKey();
        if (null == formKey || "".equals(formKey)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(this.context.getFormKey().split(";"));
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public boolean isArithmeticBackground() {
        return this.arithmeticBackground;
    }

    public void setArithmeticBackground(boolean arithmeticBackground) {
        this.arithmeticBackground = arithmeticBackground;
    }

    public String getDownLoadKey() {
        return this.downLoadKey;
    }

    public void setDownLoadKey(String downLoadKey) {
        this.downLoadKey = downLoadKey;
    }

    public String getCompressionType() {
        return this.compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }

    public boolean isMultiplePeriod() {
        return this.multiplePeriod;
    }

    public void setMultiplePeriod(boolean multiplePeriod) {
        this.multiplePeriod = multiplePeriod;
    }

    public String getMultiplePeriodInfo() {
        return this.multiplePeriodInfo;
    }

    public void setMultiplePeriodInfo(String multiplePeriodInfo) {
        this.multiplePeriodInfo = multiplePeriodInfo;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getThisSecretLevel() {
        return this.thisSecretLevel;
    }

    public void setThisSecretLevel(String thisSecretLevel) {
        this.thisSecretLevel = thisSecretLevel;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        HashMap<String, String> other = new HashMap<String, String>();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
        if (StringUtils.isNotEmpty((String)this.context.getFormKey())) {
            other.put("formKeys", this.context.getFormKey());
        }
        return logParam;
    }

    public boolean isExportETFile() {
        return this.exportETFile;
    }

    public void setExportETFile(boolean exportETFile) {
        this.exportETFile = exportETFile;
    }

    public boolean isSpecifyPath() {
        return this.specifyPath;
    }

    public void setSpecifyPath(boolean specifyPath) {
        this.specifyPath = specifyPath;
    }

    public List<RegionFilterListInfo> getRegionFilterListInfo() {
        return this.regionFilterListInfo;
    }

    public void setRegionFilterListInfo(List<RegionFilterListInfo> regionFilterListInfo) {
        this.regionFilterListInfo = regionFilterListInfo;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getZipLocation() {
        return this.zipLocation;
    }

    public void setZipLocation(String zipLocation) {
        this.zipLocation = zipLocation;
    }

    public String getExcelExpPath() {
        return this.excelExpPath;
    }

    public void setExcelExpPath(String excelExpPath) {
        this.excelExpPath = excelExpPath;
    }

    public boolean isExpExcelDirSheet() {
        return this.expExcelDirSheet;
    }

    public void setExpExcelDirSheet(boolean expExcelDirSheet) {
        this.expExcelDirSheet = expExcelDirSheet;
    }

    public boolean isExportZero() {
        return this.exportZero;
    }

    public void setExportZero(boolean exportZero) {
        this.exportZero = exportZero;
    }

    public String getExportWorkDir() {
        return this.exportWorkDir;
    }

    public void setExportWorkDir(String exportWorkDir) {
        this.exportWorkDir = exportWorkDir;
    }
}

