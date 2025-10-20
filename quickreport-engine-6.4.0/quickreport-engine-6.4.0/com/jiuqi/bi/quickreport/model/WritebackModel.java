/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.StreamException
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.quickreport.model.ReportModelException;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.quickreport.writeback.TableInfo;
import com.jiuqi.bi.quickreport.writeback.WritebackFolder;
import com.jiuqi.bi.quickreport.writeback.WritebackTableType;
import com.jiuqi.bi.util.StreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WritebackModel
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 8936311409451907291L;
    private String sheetName;
    private String interfaceTitle;
    private String interfaceType;
    private TableInfo tableInfo;
    private WritebackTableType tableType;
    private List<TableField> fields;
    private List<String> keyFields;
    private GridData gridData = new GridData();
    private List<WritebackFolder> tablePath;
    private boolean printToConsole;
    public static final String SHEETS_DIR = "writebackSheets";
    public static final String GRID_EXT = ".grid";
    public static final String INFO_EXT = ".info";
    private static final String TAG_SHEETNAME = "sheetName";
    private static final String TAG_ITITLE = "interfaceTitle";
    private static final String TAG_ITYPE = "interfaceType";
    private static final String TAG_TABLEINFO = "tableInfo";
    private static final String TAG_TABLETYPE = "tableType";
    private static final String TAG_FIELDS = "fields";
    private static final String TAG_KEYFIELDS = "keyFields";
    private static final String TAG_TABLEPATH = "tablePath";
    private static final String TAG_PRINTTOCONSOLE = "printToConsole";

    public WritebackModel() {
        this.fields = new ArrayList<TableField>();
        this.keyFields = new ArrayList<String>();
        this.tablePath = new ArrayList<WritebackFolder>();
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getInterfaceTitle() {
        return this.interfaceTitle;
    }

    public void setInterfaceTitle(String interfaceTitle) {
        this.interfaceTitle = interfaceTitle;
    }

    public String getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public TableInfo getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public WritebackTableType getTableType() {
        return this.tableType;
    }

    public void setTableType(WritebackTableType tableType) {
        this.tableType = tableType;
    }

    public List<TableField> getFields() {
        return this.fields;
    }

    public List<TableField> getKeyTableFields() {
        ArrayList<TableField> keyTableFields = new ArrayList<TableField>();
        for (TableField tableField : this.fields) {
            if (!this.keyFields.contains(tableField.getName())) continue;
            keyTableFields.add(tableField);
        }
        return keyTableFields;
    }

    public TableField findField(String fieldName) {
        for (TableField field : this.fields) {
            if (!fieldName.equalsIgnoreCase(field.getName())) continue;
            return field;
        }
        return null;
    }

    public List<String> getKeyFields() {
        return this.keyFields;
    }

    public boolean isKey(String fieldName) {
        for (String key : this.keyFields) {
            if (!fieldName.equalsIgnoreCase(key)) continue;
            return true;
        }
        return false;
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
    }

    public List<WritebackFolder> getTablePath() {
        return this.tablePath;
    }

    public void save(ZipOutputStream zip) throws IOException, ReportModelException, JSONException {
        this.saveGridData(zip);
        this.saveModelInfo(zip);
    }

    private void saveGridData(ZipOutputStream zip) throws IOException, ReportModelException {
        zip.putNextEntry(new ZipEntry("writebackSheets/" + this.sheetName + GRID_EXT));
        try {
            try {
                this.gridData.saveToStream((OutputStream)zip);
            }
            catch (StreamException e) {
                throw new ReportModelException(e);
            }
        }
        finally {
            zip.closeEntry();
        }
    }

    private void saveModelInfo(ZipOutputStream zip) throws IOException, JSONException {
        zip.putNextEntry(new ZipEntry("writebackSheets/" + this.sheetName + INFO_EXT));
        try {
            JSONObject maps = this.infosToJSON();
            JSONHelper.writeJSONObject(zip, maps);
        }
        finally {
            zip.closeEntry();
        }
    }

    private JSONObject infosToJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(TAG_SHEETNAME, (Object)this.sheetName);
        obj.put(TAG_ITITLE, (Object)this.interfaceTitle);
        obj.put(TAG_ITYPE, (Object)this.interfaceType);
        JSONObject tableInfoObj = new JSONObject();
        this.tableInfo.save(tableInfoObj);
        obj.put(TAG_TABLEINFO, (Object)tableInfoObj);
        obj.put(TAG_TABLETYPE, this.tableType.value());
        JSONArray fieldArray = new JSONArray();
        for (TableField tableField : this.fields) {
            JSONObject fieldObj = new JSONObject();
            tableField.save(fieldObj);
            fieldArray.put((Object)fieldObj);
        }
        obj.put(TAG_FIELDS, (Object)fieldArray);
        JSONArray keyFieldArray = new JSONArray();
        for (String keyField : this.keyFields) {
            keyFieldArray.put((Object)keyField);
        }
        obj.put(TAG_KEYFIELDS, (Object)keyFieldArray);
        JSONArray jSONArray = new JSONArray();
        for (WritebackFolder wbFolder : this.tablePath) {
            JSONObject wbFolderObj = new JSONObject();
            wbFolder.save(wbFolderObj);
            jSONArray.put((Object)wbFolderObj);
        }
        obj.put(TAG_TABLEPATH, (Object)jSONArray);
        obj.put(TAG_PRINTTOCONSOLE, this.printToConsole);
        return obj;
    }

    public void load(ZipInputStream zip, String extName) throws ReportModelException, IOException, JSONException {
        if (GRID_EXT.equalsIgnoreCase(extName)) {
            this.loadGridData(zip);
        } else if (INFO_EXT.equalsIgnoreCase(extName)) {
            this.loadModelInfo(zip);
        }
    }

    private void loadGridData(ZipInputStream zip) throws ReportModelException {
        try {
            this.gridData.loadFromStream((InputStream)zip);
        }
        catch (StreamException e) {
            throw new ReportModelException(e);
        }
    }

    private void loadModelInfo(ZipInputStream zip) throws IOException, JSONException {
        JSONObject json = JSONHelper.readJSONObject(zip);
        this.json2Infos(json);
    }

    private void json2Infos(JSONObject json) throws JSONException {
        this.sheetName = json.getString(TAG_SHEETNAME);
        this.interfaceTitle = json.getString(TAG_ITITLE);
        this.interfaceType = json.getString(TAG_ITYPE);
        JSONObject tableInfoObj = json.getJSONObject(TAG_TABLEINFO);
        TableInfo tableInfo = new TableInfo();
        tableInfo.load(tableInfoObj);
        this.tableInfo = tableInfo;
        int tableTypeValue = json.getInt(TAG_TABLETYPE);
        this.tableType = WritebackTableType.valueOf(tableTypeValue);
        JSONArray fieldsArray = json.getJSONArray(TAG_FIELDS);
        for (int i = 0; i < fieldsArray.length(); ++i) {
            JSONObject fieldObj = fieldsArray.getJSONObject(i);
            TableField field = new TableField();
            field.load(fieldObj);
            this.fields.add(field);
        }
        JSONArray keyFeildsArray = json.getJSONArray(TAG_KEYFIELDS);
        for (int i = 0; i < keyFeildsArray.length(); ++i) {
            this.keyFields.add(keyFeildsArray.getString(i));
        }
        JSONArray tablePathArray = json.getJSONArray(TAG_TABLEPATH);
        for (int i = 0; i < tablePathArray.length(); ++i) {
            JSONObject writebackFolderObj = tablePathArray.getJSONObject(i);
            WritebackFolder folder = new WritebackFolder();
            folder.load(writebackFolderObj);
            this.tablePath.add(folder);
        }
        this.printToConsole = json.getBoolean(TAG_PRINTTOCONSOLE);
    }

    public boolean isPrintToConsole() {
        return this.printToConsole;
    }

    public void setPrintToConsole(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    public WritebackModel clone() {
        try {
            WritebackModel cloned = (WritebackModel)super.clone();
            cloned.fields = new ArrayList<TableField>();
            for (TableField field : this.fields) {
                cloned.fields.add((TableField)field.clone());
            }
            cloned.keyFields = new ArrayList<String>();
            for (String keyField : this.keyFields) {
                cloned.keyFields.add(keyField);
            }
            cloned.tablePath = new ArrayList<WritebackFolder>();
            for (WritebackFolder writebackFolder : this.tablePath) {
                cloned.tablePath.add((WritebackFolder)writebackFolder.clone());
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public String toString() {
        return this.sheetName;
    }
}

