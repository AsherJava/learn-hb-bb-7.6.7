/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.advancedparameter.AdvancedParameterConfig;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.model.AlternateColor;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.DebugConfig;
import com.jiuqi.bi.quickreport.model.HeaderMode;
import com.jiuqi.bi.quickreport.model.PageInfo;
import com.jiuqi.bi.quickreport.model.ParameterInfo;
import com.jiuqi.bi.quickreport.model.PrintTemplete;
import com.jiuqi.bi.quickreport.model.ReportModelException;
import com.jiuqi.bi.quickreport.model.ReportScript;
import com.jiuqi.bi.quickreport.model.ReportSerializer;
import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.print.ExcelInfo;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class QuickReport
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String title;
    private List<ParameterInfo> parameters = new ArrayList<ParameterInfo>();
    private List<DataSetInfo> refDataSets = new ArrayList<DataSetInfo>();
    private ValueConvertMode zeroConvertMode = ValueConvertMode.NONE;
    private ValueConvertMode nullConvertMode = ValueConvertMode.NONE;
    private HeaderMode rowHeaderMode = HeaderMode.MERGE;
    private List<WorksheetModel> worksheets = new ArrayList<WorksheetModel>();
    private List<WritebackModel> writebackSheets = new ArrayList<WritebackModel>();
    private String primarySheetName;
    private PageInfo pageInfo = new PageInfo();
    private ReportScript script = new ReportScript();
    private boolean excelMode;
    private Properties properties = new Properties();
    private DebugConfig debugConfig = new DebugConfig();
    private ExcelInfo excelInfo = new ExcelInfo();
    private List<PrintTemplete> printTempletes = new ArrayList<PrintTemplete>();
    private AlternateColor alternateColor = new AlternateColor();
    private AdvancedParameterConfig advanceParaConfig;
    @Deprecated
    public static final int RPTX_VERSION_1_0 = 65536;
    @Deprecated
    public static final int RPTX_VERSION_1_1 = 65537;
    @Deprecated
    public static final int RPTX_VERSION_2_0 = 131072;
    @Deprecated
    public static final int RPTX_CUR_VERSION = 327681;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ValueConvertMode getZeroConvertMode() {
        return this.zeroConvertMode;
    }

    public void setZeroConvertMode(ValueConvertMode zeroConvertMode) {
        this.zeroConvertMode = zeroConvertMode;
    }

    public ValueConvertMode getNullConvertMode() {
        return this.nullConvertMode;
    }

    public void setNullConvertMode(ValueConvertMode nullConvertMode) {
        this.nullConvertMode = nullConvertMode;
    }

    public HeaderMode getRowHeaderMode() {
        return this.rowHeaderMode;
    }

    public void setRowHeaderMode(HeaderMode rowHeaderMode) {
        this.rowHeaderMode = rowHeaderMode;
    }

    public boolean isExcelMode() {
        return this.excelMode;
    }

    public void setExcelMode(boolean excelMode) {
        this.excelMode = excelMode;
    }

    public List<ParameterInfo> getParameters() {
        return this.parameters;
    }

    public List<DataSetInfo> getRefDataSets() {
        return this.refDataSets;
    }

    public List<WorksheetModel> getWorksheets() {
        return this.worksheets;
    }

    public String getPrimarySheetName() {
        return this.primarySheetName;
    }

    public void setPrimarySheetName(String primarySheetName) {
        this.primarySheetName = primarySheetName;
    }

    public WorksheetModel getPrimarySheet() {
        if (this.primarySheetName == null) {
            return null;
        }
        for (WorksheetModel sheet : this.worksheets) {
            if (!this.primarySheetName.equalsIgnoreCase(sheet.getName())) continue;
            return sheet;
        }
        return null;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public ReportScript getScript() {
        return this.script;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public DebugConfig getDebugConfig() {
        return this.debugConfig;
    }

    public ExcelInfo getExcelInfo() {
        return this.excelInfo;
    }

    @Deprecated
    public List<PrintTemplete> getPrintTempletes() {
        return this.printTempletes;
    }

    public Object clone() {
        QuickReport result;
        try {
            result = (QuickReport)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.parameters = new ArrayList<ParameterInfo>(this.parameters.size());
        for (ParameterInfo param : this.parameters) {
            result.parameters.add((ParameterInfo)param.clone());
        }
        result.refDataSets = new ArrayList<DataSetInfo>(this.refDataSets.size());
        for (DataSetInfo dataSetInfo : this.refDataSets) {
            result.refDataSets.add((DataSetInfo)dataSetInfo.clone());
        }
        result.worksheets = new ArrayList<WorksheetModel>(this.worksheets.size());
        for (WorksheetModel sheet : this.worksheets) {
            result.worksheets.add((WorksheetModel)sheet.clone());
        }
        result.pageInfo = this.pageInfo.clone();
        result.script = (ReportScript)this.script.clone();
        result.debugConfig = (DebugConfig)this.debugConfig.clone();
        result.excelInfo = (ExcelInfo)this.excelInfo.clone();
        result.printTempletes = new ArrayList<PrintTemplete>(this.printTempletes.size());
        for (PrintTemplete printTemplete : this.printTempletes) {
            result.printTempletes.add((PrintTemplete)printTemplete.clone());
        }
        result.alternateColor = this.alternateColor.clone();
        return result;
    }

    public String toString() {
        return this.name + " " + this.title;
    }

    public void save(String fileName) throws ReportModelException {
        try (FileOutputStream fileStream = new FileOutputStream(fileName);){
            this.save(fileStream);
        }
        catch (IOException e) {
            throw new ReportModelException(e);
        }
    }

    public void load(String fileName) throws ReportModelException {
        try (FileInputStream fileStream = new FileInputStream(fileName);){
            this.load(fileStream);
        }
        catch (IOException e) {
            throw new ReportModelException(e);
        }
    }

    public void save(OutputStream outStream) throws ReportModelException {
        ReportSerializer saver = new ReportSerializer(this);
        saver.save(outStream);
    }

    public void load(InputStream inStream) throws ReportModelException {
        ReportSerializer loader = new ReportSerializer(this);
        loader.load(inStream);
    }

    public void load(InputStream inStream, IReportListener listener) throws ReportModelException {
        ReportSerializer loader = new ReportSerializer(this).setListener(listener);
        loader.load(inStream);
    }

    public List<ParameterModel> getParamModels() {
        ArrayList<ParameterModel> models = new ArrayList<ParameterModel>();
        for (ParameterInfo info : this.parameters) {
            models.add(info.getParamModel());
        }
        return models;
    }

    @Deprecated
    public List<com.jiuqi.bi.parameter.model.ParameterModel> getParameterModels() {
        ArrayList<com.jiuqi.bi.parameter.model.ParameterModel> models = new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>();
        if (this.parameters.size() != 0) {
            for (ParameterInfo info : this.parameters) {
                models.add(info.getModel());
            }
        }
        return models;
    }

    @Deprecated
    public AdvancedParameterConfig getAdvancedParaConfig() {
        return this.advanceParaConfig;
    }

    @Deprecated
    public void setAdvancedParaConfig(AdvancedParameterConfig advanceParaConfig) {
        this.advanceParaConfig = advanceParaConfig;
    }

    public List<WritebackModel> getWritebackSheets() {
        return this.writebackSheets;
    }

    public AlternateColor getAlternateColor() {
        return this.alternateColor;
    }
}

