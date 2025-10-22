/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.excel.importexcel.cache;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.designer.excel.importexcel.common.AIFieldData;
import com.jiuqi.nr.designer.excel.importexcel.common.FloatRegionInfo;
import com.jiuqi.nr.designer.excel.importexcel.common.FormulaImportContext;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportContext {
    private String formKey;
    private String schemeKey;
    private Grid2Data grid2Data;
    private DesignFormDefine designFormDefine;
    private FormulaImportContext formulaImportContext;
    private Map<Integer, FloatRegionInfo> floatMess = new HashMap<Integer, FloatRegionInfo>();
    private List<DesignDataRegionDefine> regoinData = new ArrayList<DesignDataRegionDefine>();
    private Map<String, Map<String, DesignTableDefine>> tableData = new HashMap<String, Map<String, DesignTableDefine>>();
    private Map<String, Map<String, DesignDataLinkDefine>> linkData = new HashMap<String, Map<String, DesignDataLinkDefine>>();
    private Map<String, Map<String, DesignFieldDefine>> fieldData = new HashMap<String, Map<String, DesignFieldDefine>>();
    private String formMasterEntitiesKey;
    private List<Object> jsonArray = new ArrayList<Object>();
    private List<AIFieldData> aIFieldDataList = new ArrayList<AIFieldData>();
    private boolean urlUsable = false;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public Grid2Data getGrid2Data() {
        return this.grid2Data;
    }

    public void setGrid2Data(Grid2Data grid2Data) {
        this.grid2Data = grid2Data;
    }

    public DesignFormDefine getDesignFormDefine() {
        return this.designFormDefine;
    }

    public void setDesignFormDefine(DesignFormDefine designFormDefine) {
        this.designFormDefine = designFormDefine;
    }

    public Map<Integer, FloatRegionInfo> getFloatMess() {
        return this.floatMess;
    }

    public void setFloatMess(Map<Integer, FloatRegionInfo> floatMess) {
        this.floatMess = floatMess;
    }

    public List<DesignDataRegionDefine> getRegoinData() {
        return this.regoinData;
    }

    public void setRegoinData(List<DesignDataRegionDefine> regoinData) {
        this.regoinData = regoinData;
    }

    public Map<String, Map<String, DesignTableDefine>> getTableData() {
        return this.tableData;
    }

    public void setTableData(Map<String, Map<String, DesignTableDefine>> tableData) {
        this.tableData = tableData;
    }

    public Map<String, Map<String, DesignDataLinkDefine>> getLinkData() {
        return this.linkData;
    }

    public void setLinkData(Map<String, Map<String, DesignDataLinkDefine>> linkData) {
        this.linkData = linkData;
    }

    public Map<String, Map<String, DesignFieldDefine>> getFieldData() {
        return this.fieldData;
    }

    public void setFieldData(Map<String, Map<String, DesignFieldDefine>> fieldData) {
        this.fieldData = fieldData;
    }

    public String getFormMasterEntitiesKey() {
        return this.formMasterEntitiesKey;
    }

    public void setFormMasterEntitiesKey(String formMasterEntitiesKey) {
        this.formMasterEntitiesKey = formMasterEntitiesKey;
    }

    public List<Object> getJsonArray() {
        return this.jsonArray;
    }

    public void setJsonArray(List<Object> jsonArray) {
        this.jsonArray = jsonArray;
    }

    public boolean isUrlUsable() {
        return this.urlUsable;
    }

    public void setUrlUsable(boolean urlUsable) {
        this.urlUsable = urlUsable;
    }

    public List<AIFieldData> getAIFieldDataList() {
        return this.aIFieldDataList;
    }

    public void setAIFieldDataList(List<AIFieldData> aIFieldDataList) {
        this.aIFieldDataList = aIFieldDataList;
    }

    public FormulaImportContext getFormulaImportContext() {
        return this.formulaImportContext;
    }

    public void setFormulaImportContext(FormulaImportContext formulaImportContext) {
        this.formulaImportContext = formulaImportContext;
    }
}

