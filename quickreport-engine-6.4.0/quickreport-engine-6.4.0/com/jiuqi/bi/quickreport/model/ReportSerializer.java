/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.print.PaperSize
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.office.excel.print.Zoom
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterOwner
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.print.PaperSize;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.office.excel.print.Zoom;
import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.advancedparameter.AdvancedParameterConfig;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.HeaderMode;
import com.jiuqi.bi.quickreport.model.HyperlinkType;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.quickreport.model.ParameterInfo;
import com.jiuqi.bi.quickreport.model.PrintTemplete;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.ReportModelException;
import com.jiuqi.bi.quickreport.model.ReportParamType;
import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterOwner;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportSerializer {
    private final QuickReport report;
    private IReportListener listener;
    private static final String MANIFEST = "manifest.mf";
    private static final String MF_VERSION = "version";
    private static final String MF_NAME = "name";
    private static final String MF_TITLE = "title";
    private static final String MF_ZERO_MODE = "zeroConvertMode";
    private static final String MF_NULL_MODE = "nullConvertMode";
    private static final String MF_ROWHEADER_MODE = "rowHeaderMode";
    private static final String MF_PAGEINFO = "pageInfo";
    private static final String MF_REF_DATASETS = "refDatasets";
    private static final String MF_WORKSHEETS = "worksheets";
    private static final String MF_PRIMARYSHEET = "primarySheet";
    private static final String MF_PROPERTIES = "properties";
    private static final String MF_EXCEL_MODE = "excelMode";
    private static final String MF_DEBUG_CONFIG = "debug";
    private static final String MF_EXCEL_PRINT = "excelPrint";
    private static final String MF_EXCEL_INFO = "excelInfo";
    private static final String MF_ALTERNATE_COLOR = "alternateColor";
    private static final String PARAMCONFIG = "parameters.conf";
    private static final String PRINTCONFIG = "printTempletes.conf";
    private static final String PC_PARAMINFOS = "paramInfos";
    private static final String PC_TEMPLETES = "printTempletes";
    public static final int RPTX_VERSION_1_0 = 65536;
    public static final int RPTX_VERSION_1_1 = 65537;
    public static final int RPTX_VERSION_2_0 = 131072;
    public static final int RPTX_VERSION_2_1 = 131073;
    public static final int RPTX_VERSION_3_0 = 196608;
    public static final int RPTX_VERSION_4_0 = 262144;
    public static final int RPTX_VERSION_4_1 = 262145;
    public static final int RPTX_VERSION_5_0 = 327680;
    public static final int RPTX_VERSION_5_1 = 327681;
    public static final int RPTX_CUR_VERSION = 327681;
    private static final int MAJOR_VERSION_MASK = -65536;

    public ReportSerializer(QuickReport report) {
        this.report = report;
    }

    public ReportSerializer setListener(IReportListener listener) {
        this.listener = Objects.requireNonNull(listener);
        return this;
    }

    public QuickReport getReport() {
        return this.report;
    }

    public void save(OutputStream outStream) throws ReportModelException {
        try (ZipOutputStream zip = new ZipOutputStream(outStream);){
            this.saveManifest(zip);
            this.saveParameters(zip);
            this.saveWorksheets(zip);
            this.saveWritebackSheets(zip);
            this.saveAdvancedParaConfig(zip);
            this.report.getScript().save(zip);
            this.savePrintTempletes(zip);
        }
        catch (IOException | JSONException e) {
            throw new ReportModelException(e);
        }
    }

    public int load(InputStream inStream) throws ReportModelException {
        Cloneable model;
        this.clear();
        ArrayList<String> sheetNames = new ArrayList<String>();
        HashMap<String, WorksheetModel> sheetsMap = new HashMap<String, WorksheetModel>();
        HashMap<String, WritebackModel> writebackMap = new HashMap<String, WritebackModel>();
        int version = 327681;
        try (ZipInputStream zip = new ZipInputStream(inStream);){
            ZipEntry entry = zip.getNextEntry();
            while (entry != null) {
                if (MANIFEST.equals(entry.getName())) {
                    version = this.loadManifest(zip, sheetNames);
                    this.checkVersion(version);
                } else if (PARAMCONFIG.equals(entry.getName())) {
                    this.loadParameters(zip, version);
                } else if (entry.getName().startsWith(MF_WORKSHEETS)) {
                    this.loadWorksheet(entry.getName(), zip, sheetsMap, version);
                } else if (entry.getName().startsWith("scripts")) {
                    this.loadScript(entry.getName(), zip);
                } else if (entry.getName().startsWith("writebackSheets")) {
                    this.loadWritebackSheet(entry.getName(), zip, writebackMap);
                } else if (entry.getName().startsWith("advancedParaConf")) {
                    this.loadAdvanceParaConfig(entry.getName(), zip);
                } else if (PRINTCONFIG.equals(entry.getName())) {
                    this.loadPrintTempletes(zip);
                }
                entry = zip.getNextEntry();
            }
        }
        catch (IOException | JSONException e) {
            throw new ReportModelException(e);
        }
        for (String sheetName : sheetNames) {
            model = (WorksheetModel)sheetsMap.get(sheetName);
            if (model == null) continue;
            this.report.getWorksheets().add((WorksheetModel)model);
        }
        for (String sheetName : sheetNames) {
            model = (WritebackModel)writebackMap.get(sheetName);
            if (model == null) continue;
            this.report.getWritebackSheets().add((WritebackModel)model);
        }
        this.excelInfo2PrintSetting();
        if (this.listener != null) {
            try {
                this.listener.openReport(this.report, version);
            }
            catch (ReportEngineException e) {
                throw new ReportModelException(e);
            }
        }
        return version;
    }

    private void checkVersion(int version) throws ReportModelException {
        if ((version & 0xFFFF0000) > 327680) {
            throw new ReportModelException("\u5206\u6790\u8868\u6a21\u677f\u8bfb\u53d6\u5931\u8d25\uff0c\u5f53\u524d\u6a21\u677f\u4e3a\u9ad8\u7248\u672c\u7a0b\u5e8f\u8bbe\u8ba1\uff0c\u5f53\u524d\u7a0b\u5e8f\u7248\u672c\u8fc7\u4f4e\uff0c\u65e0\u6cd5\u6253\u5f00\u6a21\u677f");
        }
    }

    private void clear() {
        this.report.getRefDataSets().clear();
        this.report.getWorksheets().clear();
        this.report.getParameters().clear();
        this.report.getScript().clear();
        this.report.getPrintTempletes().clear();
        this.report.getPageInfo().getDSPageInfos().clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveManifest(ZipOutputStream zip) throws IOException, JSONException {
        zip.putNextEntry(new ZipEntry(MANIFEST));
        try {
            JSONObject mf = new JSONObject();
            mf.put(MF_VERSION, (Object)ReportSerializer.versionToString(327681));
            mf.put(MF_NAME, (Object)this.report.getName());
            mf.put(MF_TITLE, (Object)this.report.getTitle());
            mf.put(MF_ZERO_MODE, (Object)this.report.getZeroConvertMode().toString());
            mf.put(MF_NULL_MODE, (Object)this.report.getNullConvertMode().toString());
            mf.put(MF_ROWHEADER_MODE, (Object)this.report.getRowHeaderMode().toString());
            mf.put(MF_EXCEL_MODE, this.report.isExcelMode());
            mf.put(MF_PAGEINFO, (Object)this.report.getPageInfo().toJSON());
            JSONArray jsonArray = new JSONArray();
            for (DataSetInfo dataSetInfo : this.report.getRefDataSets()) {
                jsonArray.put((Object)dataSetInfo.toJSON());
            }
            mf.put(MF_REF_DATASETS, (Object)jsonArray);
            mf.put(MF_WORKSHEETS, this.getSheetNames());
            mf.put(MF_PRIMARYSHEET, (Object)this.report.getPrimarySheetName());
            mf.put(MF_PROPERTIES, (Map)this.report.getProperties());
            mf.put(MF_DEBUG_CONFIG, (Object)this.report.getDebugConfig().toJSON());
            mf.put(MF_EXCEL_INFO, (Object)this.report.getExcelInfo().toJSON());
            mf.put(MF_ALTERNATE_COLOR, (Object)this.report.getAlternateColor().toJSON());
            JSONHelper.writeJSONObject(zip, mf);
        }
        finally {
            zip.closeEntry();
        }
    }

    private int loadManifest(ZipInputStream zip, List<String> sheetNames) throws IOException, JSONException {
        JSONObject excelInfoObj;
        JSONObject debugObj;
        String[] propKeys;
        JSONObject mf = JSONHelper.readJSONObject(zip);
        int version = this.loadVersion(mf);
        this.report.setName(mf.optString(MF_NAME));
        this.report.setTitle(mf.optString(MF_TITLE));
        this.report.setZeroConvertMode(ValueConvertMode.valueOf(mf.optString(MF_ZERO_MODE)));
        this.report.setNullConvertMode(ValueConvertMode.valueOf(mf.optString(MF_NULL_MODE)));
        if (mf.has(MF_ROWHEADER_MODE)) {
            this.report.setRowHeaderMode(HeaderMode.valueOf(mf.optString(MF_ROWHEADER_MODE)));
        } else {
            this.report.setRowHeaderMode(HeaderMode.MERGE);
        }
        this.report.setExcelMode(mf.optBoolean(MF_EXCEL_MODE));
        this.report.getPageInfo().fromJSON(mf.optJSONObject(MF_PAGEINFO));
        JSONArray refDSArr = mf.optJSONArray(MF_REF_DATASETS);
        for (int i = 0; i < refDSArr.length(); ++i) {
            Object obj = refDSArr.get(i);
            DataSetInfo dataSetInfo = new DataSetInfo();
            if (obj instanceof JSONObject) {
                dataSetInfo.fromJSON(refDSArr.optJSONObject(i));
            } else {
                dataSetInfo.setId(refDSArr.optString(i));
            }
            this.report.getRefDataSets().add(dataSetInfo);
        }
        JSONArray namesArr = mf.optJSONArray(MF_WORKSHEETS);
        for (int i = 0; i < namesArr.length(); ++i) {
            sheetNames.add(namesArr.optString(i));
        }
        this.report.setPrimarySheetName(mf.optString(MF_PRIMARYSHEET));
        JSONObject propsObj = mf.optJSONObject(MF_PROPERTIES);
        if (propsObj != null && (propKeys = JSONObject.getNames((JSONObject)propsObj)) != null) {
            for (String key : propKeys) {
                this.report.getProperties().put(key, propsObj.get(key));
            }
        }
        if ((debugObj = mf.optJSONObject(MF_DEBUG_CONFIG)) != null) {
            this.report.getDebugConfig().fromJSON(debugObj);
        }
        if ((excelInfoObj = mf.optJSONObject(MF_EXCEL_INFO)) != null) {
            this.report.getExcelInfo().fromJSON(excelInfoObj);
        } else {
            JSONObject excelPrintObj = mf.optJSONObject(MF_EXCEL_PRINT);
            if (excelPrintObj != null) {
                this.report.getExcelInfo().fromJSON(excelPrintObj);
            }
        }
        JSONObject alterColorObj = mf.optJSONObject(MF_ALTERNATE_COLOR);
        if (alterColorObj != null) {
            this.report.getAlternateColor().fromJSON(alterColorObj);
        }
        return version;
    }

    private int loadVersion(JSONObject mf) throws IOException {
        int version = 327681;
        String verStr = mf.optString(MF_VERSION);
        if (!StringUtils.isEmpty((String)verStr) && (version = ReportSerializer.stringToVersion(verStr)) > 327681) {
            throw new IOException("\u5f15\u64ce\u652f\u6301\u5206\u6790\u8868\u6587\u4ef6\u7248\u672c\u4e3a[" + ReportSerializer.versionToString(327681) + "]\uff0c\u65e0\u6cd5\u8bfb\u53d6\u5f53\u524d\u6587\u4ef6\u7248\u672c\uff1a" + verStr);
        }
        return version;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveParameters(ZipOutputStream zip) throws IOException, JSONException {
        zip.putNextEntry(new ZipEntry(PARAMCONFIG));
        try {
            JSONObject conf = new JSONObject();
            JSONArray params = new JSONArray();
            for (ParameterInfo paramInfo : this.report.getParameters()) {
                JSONObject paramObj = paramInfo.toJSON();
                params.put((Object)paramObj);
            }
            conf.put(PC_PARAMINFOS, (Object)params);
            JSONHelper.writeJSONObject(zip, conf);
        }
        finally {
            zip.closeEntry();
        }
    }

    private void loadParameters(ZipInputStream zip, int version) throws IOException, JSONException, ReportModelException {
        JSONObject conf = JSONHelper.readJSONObject(zip);
        JSONArray paramArr = conf.optJSONArray(PC_PARAMINFOS);
        if (paramArr == null) {
            return;
        }
        HashMap<ParameterInfo, JSONObject> paramJsons = new HashMap<ParameterInfo, JSONObject>();
        HashMap<ArrayKey, List<ParameterInfo>> paramGroups = new HashMap<ArrayKey, List<ParameterInfo>>();
        this.loadParamInfos(paramArr, paramJsons, paramGroups, version);
        for (List groupParams : paramGroups.values()) {
            this.loadParamModels(groupParams, paramJsons);
        }
    }

    private void saveWorksheets(ZipOutputStream zip) throws IOException, JSONException, ReportModelException {
        for (WorksheetModel sheet : this.report.getWorksheets()) {
            sheet.save(zip);
        }
    }

    private void loadWorksheet(String entryName, ZipInputStream zip, Map<String, WorksheetModel> sheetsMap, int version) throws ReportModelException, IOException, JSONException {
        String[] sheetNames = ReportSerializer.parseSheetName(entryName);
        WorksheetModel sheetModel = sheetsMap.get(sheetNames[0]);
        if (sheetModel == null) {
            sheetModel = new WorksheetModel();
            sheetModel.setName(sheetNames[0]);
            sheetsMap.put(sheetNames[0], sheetModel);
        }
        sheetModel.load(zip, sheetNames[1]);
        if (version < 262144) {
            this.updateHyperlinkFontColors(sheetModel);
        }
    }

    private void loadScript(String entryName, ZipInputStream zip) throws IOException, JSONException {
        this.report.getScript().load(zip, entryName);
    }

    private void saveWritebackSheets(ZipOutputStream zip) throws ReportModelException, IOException, JSONException {
        for (WritebackModel writebackModel : this.report.getWritebackSheets()) {
            writebackModel.save(zip);
        }
    }

    private void loadWritebackSheet(String entryName, ZipInputStream zip, Map<String, WritebackModel> writebackMap) throws ReportModelException, IOException, JSONException {
        String[] sheetNames = ReportSerializer.parseSheetName(entryName);
        WritebackModel writebackModel = writebackMap.get(sheetNames[0]);
        if (writebackModel == null) {
            writebackModel = new WritebackModel();
            writebackModel.setSheetName(sheetNames[0]);
            writebackMap.put(sheetNames[0], writebackModel);
        }
        writebackModel.load(zip, sheetNames[1]);
    }

    @Deprecated
    private void saveAdvancedParaConfig(ZipOutputStream zip) throws IOException, JSONException {
        if (this.report.getAdvancedParaConfig() != null) {
            this.report.getAdvancedParaConfig().save(zip);
        }
    }

    @Deprecated
    private void loadAdvanceParaConfig(String name, ZipInputStream zip) throws IOException, JSONException {
        AdvancedParameterConfig advanceParaConfig = new AdvancedParameterConfig();
        advanceParaConfig.load(name, zip);
        this.report.setAdvancedParaConfig(advanceParaConfig);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    private void savePrintTempletes(ZipOutputStream zip) throws IOException, JSONException {
        if (this.report.getPrintTempletes().isEmpty()) {
            return;
        }
        zip.putNextEntry(new ZipEntry(PRINTCONFIG));
        try {
            JSONObject conf = new JSONObject();
            JSONArray json_printTempletes = new JSONArray();
            for (PrintTemplete printTemplete : this.report.getPrintTempletes()) {
                JSONObject json_templete = printTemplete.toJSON();
                json_printTempletes.put((Object)json_templete);
            }
            conf.put(PC_TEMPLETES, (Object)json_printTempletes);
            JSONHelper.writeJSONObject(zip, conf);
        }
        finally {
            zip.closeEntry();
        }
    }

    @Deprecated
    private void loadPrintTempletes(ZipInputStream zip) throws IOException, JSONException {
        JSONObject conf = JSONHelper.readJSONObject(zip);
        JSONArray json_printTempletes = conf.optJSONArray(PC_TEMPLETES);
        if (json_printTempletes == null) {
            return;
        }
        for (int i = 0; i < json_printTempletes.length(); ++i) {
            JSONObject json_templete = json_printTempletes.optJSONObject(i);
            PrintTemplete printTemplete = new PrintTemplete();
            printTemplete.fromJSON(json_templete);
            this.report.getPrintTempletes().add(printTemplete);
        }
    }

    private void excelInfo2PrintSetting() {
        if (this.report.getPrimarySheet().getPrintSetting() != null) {
            return;
        }
        if (!this.report.getExcelInfo().isPrintEnabled()) {
            return;
        }
        WorksheetModel primarySheetModel = this.report.getPrimarySheet();
        if (primarySheetModel == null) {
            return;
        }
        PrintSetting printSetting = new PrintSetting();
        switch (this.report.getExcelInfo().getOrientation()) {
            case HORIZONTAL: {
                printSetting.setLandscape(true);
                break;
            }
            case VERTICAL: {
                printSetting.setLandscape(false);
                break;
            }
        }
        switch (this.report.getExcelInfo().getPaperSize()) {
            case A3: {
                printSetting.setPaperSize(PaperSize.A3_PAPER);
                break;
            }
            case A4: {
                printSetting.setPaperSize(PaperSize.A4_PAPER);
                break;
            }
            case A5: {
                printSetting.setPaperSize(PaperSize.A5_PAPER);
                break;
            }
            case B4: {
                printSetting.setPaperSize(PaperSize.B4_PAPER);
                break;
            }
            case B5: {
                printSetting.setPaperSize(PaperSize.B5_PAPER);
                break;
            }
        }
        switch (this.report.getExcelInfo().getFitMode()) {
            case NONE: {
                printSetting.setZoom(Zoom.NONE);
                break;
            }
            case ALL: {
                printSetting.setZoom(Zoom.FIT_PAGE);
                break;
            }
            case ALL_COLUMNS: {
                printSetting.setZoom(Zoom.FIT_COLS);
                break;
            }
            case ALL_ROWS: {
                printSetting.setZoom(Zoom.FIT_ROWS);
                break;
            }
        }
        primarySheetModel.setPrintSetting(printSetting);
    }

    private List<String> getSheetNames() {
        ArrayList<String> sheetNames = new ArrayList<String>();
        for (WorksheetModel sheet : this.report.getWorksheets()) {
            sheetNames.add(sheet.getName());
        }
        for (WritebackModel writebackModel : this.report.getWritebackSheets()) {
            sheetNames.add(writebackModel.getSheetName());
        }
        return sheetNames;
    }

    private void loadParamInfos(JSONArray paramArr, Map<ParameterInfo, JSONObject> paramJsons, Map<ArrayKey, List<ParameterInfo>> paramGroups, int version) throws ReportModelException {
        for (int i = 0; i < paramArr.length(); ++i) {
            JSONObject paramJson = paramArr.getJSONObject(i);
            ParameterInfo paramInfo = new ParameterInfo();
            try {
                paramInfo.fromJSON(paramJson, version, false);
            }
            catch (ParameterStorageException e) {
                throw new ReportModelException(e);
            }
            this.report.getParameters().add(paramInfo);
            if (paramInfo.getType() == ReportParamType.LOCAL) {
                ParameterModel paramModel = paramInfo.getParamModel();
                ParameterOwner pOwner = paramModel.getOwner();
                if (pOwner == null) {
                    String ownerType = "report.local";
                    String ownerName = this.report.getName();
                    paramModel.setOwner(new ParameterOwner(ownerType, ownerName));
                    continue;
                }
                if (StringUtils.equals((String)pOwner.getType(), (String)"report.local")) continue;
                pOwner.setType("report.local");
                continue;
            }
            ArrayKey key = null;
            switch (paramInfo.getType()) {
                case PUBLIC: {
                    key = ArrayKey.of((Object[])new Object[]{paramInfo.getStorageType()});
                    break;
                }
                case FROMDATASET: {
                    key = ArrayKey.of((Object[])new Object[]{paramInfo.getStorageType(), paramInfo.getDatasetName()});
                }
            }
            if (key == null) continue;
            paramJsons.put(paramInfo, paramJson);
            List groupParams = paramGroups.computeIfAbsent(key, k -> new ArrayList());
            groupParams.add(paramInfo);
        }
    }

    private void loadParamModels(List<ParameterInfo> paramInfos, Map<ParameterInfo, JSONObject> paramJsons) throws ReportModelException {
        List<String> paramNames = paramInfos.stream().map(ParameterInfo::getName).collect(Collectors.toList());
        List<ParameterModel> paramModels = this.findParamModels(paramInfos.get(0), paramNames);
        if (paramInfos.size() != paramModels.size()) {
            throw new ReportModelException("\u52a0\u8f7d\u53c2\u6570\u5931\u8d25\uff1a" + paramInfos);
        }
        for (ParameterInfo paramInfo : paramInfos) {
            JSONObject modelJson = paramJsons.get(paramInfo);
            if (modelJson == null) {
                throw new ReportModelException("\u52a0\u8f7d\u53c2\u6570\u6570\u636e\u9519\u8bef\uff1a" + paramInfo.getName());
            }
            Optional<ParameterModel> paramModel = paramModels.stream().filter(m -> m != null && m.getName().equalsIgnoreCase(paramInfo.getName())).findAny();
            if (paramModel.isPresent()) {
                paramInfo.setParamModel(paramModel.get(), modelJson);
                continue;
            }
            ReportLog.openLogger().error("\u52a0\u8f7d\u62a5\u8868\u201c" + this.report.getName() + "\u201d\u53c2\u6570\u4e0d\u5b58\u5728\uff1a" + paramInfo.getName());
        }
    }

    private List<ParameterModel> findParamModels(ParameterInfo paramInfo, List<String> paramNames) throws ReportModelException {
        if (this.listener != null) {
            List<ParameterModel> paramModels;
            try {
                paramModels = paramInfo.getType() == ReportParamType.PUBLIC ? this.listener.openParamModels(paramNames, paramInfo.getStorageType()) : this.listener.openParamModels(paramNames, paramInfo.getDatasetName(), "com.jiuqi.bi.dataset", paramInfo.getStorageType());
            }
            catch (ReportEngineException e) {
                throw new ReportModelException(e);
            }
            if (paramModels != null) {
                return paramModels;
            }
        }
        try {
            if (paramInfo.getType() == ReportParamType.PUBLIC) {
                return ParameterStorageManager.getInstance().findModels(paramNames, paramInfo.getStorageType());
            }
            return ParameterStorageManager.getInstance().findModels(paramNames, paramInfo.getDatasetName(), "com.jiuqi.bi.dataset", paramInfo.getStorageType());
        }
        catch (ParameterStorageException e) {
            throw new ReportModelException(e);
        }
    }

    private void updateHyperlinkFontColors(WorksheetModel sheetModel) {
        GridData grid = sheetModel.getGriddata();
        for (CellMap cellMap : sheetModel.getCellMaps()) {
            GridCell cell;
            if (cellMap.getHyperlink().getType() == HyperlinkType.NONE || cellMap.getPosition().col() >= grid.getColCount() || cellMap.getPosition().row() >= grid.getRowCount() || (cell = grid.getCell(cellMap.getPosition().col(), cellMap.getPosition().row())).getFontColor() != 0) continue;
            cell.setFontColor(255);
            grid.setCell(cell);
        }
    }

    private static String[] parseSheetName(String entryName) throws ReportModelException {
        int p1 = entryName.indexOf(47);
        int p2 = entryName.lastIndexOf(46);
        if (p1 <= 0 || p2 <= 0) {
            throw new ReportModelException("\u52a0\u8f7d\u9875\u7b7e\u5931\u8d25\uff0c\u65e0\u6cd5\u89e3\u6790\u9875\u7b7e\u6587\u4ef6\u540d\uff1a" + entryName);
        }
        String sheetName = entryName.substring(p1 + 1, p2);
        String sheetExt = entryName.substring(p2);
        return new String[]{sheetName, sheetExt};
    }

    public static String versionToString(int version) {
        int major = version >>> 16 & 0xFFFF;
        int minor = version & 0xFFFF;
        return major + "." + minor;
    }

    public static int stringToVersion(String text) {
        int p = text.indexOf(46);
        if (p == -1) {
            throw new NumberFormatException("\u9519\u8bef\u7684\u7248\u672c\u683c\u5f0f\uff1a" + text);
        }
        int major = Integer.parseInt(text.substring(0, p));
        int minor = Integer.parseInt(text.substring(p + 1));
        return major << 16 | minor;
    }
}

