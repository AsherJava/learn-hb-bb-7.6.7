/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.anal;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.anal.AnalCellFormulaItem;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableRegion;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableSet;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportParser;
import com.jiuqi.nr.single.core.para.parser.table.ZbParser;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalTableInfo {
    private static final Logger logger = LoggerFactory.getLogger(AnalTableInfo.class);
    private RepInfo repInfo;
    private GridPrintMan printMan;
    private List<AnalCellFormulaItem> fetchFormulas;
    private List<AnalCellFormulaItem> colConditions;
    private List<AnalCellFormulaItem> rowConditions;
    private Map<String, AnalTableRegion> regionInfos;
    private String filter;
    private AnalTableSet parent;
    private String showCondition;
    private String showCaption;
    private String unitFindMode;
    private String FindCondition;
    private boolean AutoExecute;

    public RepInfo getRepInfo() {
        if (this.repInfo == null) {
            this.repInfo = new RepInfo();
        }
        return this.repInfo;
    }

    public void setRepInfo(RepInfo repInfo) {
        this.repInfo = repInfo;
    }

    public final void LoadFromJQT(String sFile, Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        HashMap<String, ReportFormImpl> repMap = new HashMap<String, ReportFormImpl>();
        ReportParser rpParser = new ReportParser(repMap);
        RepInfo repInfoEx = rpParser.initReport(stream, jqtFile);
        if (this.repInfo != null) {
            repInfoEx.setCode(this.repInfo.getCode());
            repInfoEx.setTableType(this.repInfo.getTableType());
            repInfoEx.setTitle(this.repInfo.getTitle());
            repInfoEx.setSubTitle(this.repInfo.getSubTitle());
            repInfoEx.setSubNo(this.repInfo.getSubNo());
            repInfoEx.setMoneyUnit(this.repInfo.getMoneyUnit());
            repInfoEx.setFilter(this.repInfo.getFilter());
            repInfoEx.setGather(this.repInfo.getGather());
            repInfoEx.setCalcView(this.repInfo.getCalcView());
        }
        this.repInfo = repInfoEx;
        ZbParser zbParser = new ZbParser(repMap);
        FieldDefs zbMainBodyInfo = zbParser.BuildPara(sFile, null, jqtFile, this.repInfo.getCode(), this.repInfo, this.repInfo.getTableTypeCode());
        if (zbMainBodyInfo != null) {
            this.repInfo.addDefs(zbMainBodyInfo);
        }
    }

    public final void LoadPrint(Grid2Data gridData, Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        this.printMan = new GridPrintMan(gridData);
        this.printMan.LoadFromJQT(stream, jqtFile);
    }

    public final void LoadFormulas(String sFile) {
        try {
            Ini ini = new Ini();
            ini.loadIniFile(sFile);
            this.loadFetchFormulas(ini);
            this.loadRowCondFormulas(ini);
            this.loadColCondFormulas(ini);
            if (this.repInfo != null && this.repInfo.getDefs() != null && this.repInfo.getDefs().getSubMbs() != null && this.repInfo.getDefs().getSubMbs().size() > 0) {
                for (FieldDefs def : this.repInfo.getDefs().getSubMbs()) {
                    this.loadRegionFormulas(ini, def.getRegionInfo().getMapArea().getFloatRangeStartNo());
                }
            }
            this.loadInnerInfo(ini);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void loadInnerInfo(Ini ini) {
        this.showCondition = ini.ReadString("General", "ShowCondition", "");
        this.showCaption = ini.ReadString("General", "ShowCaption", "");
        this.unitFindMode = ini.ReadString("General", "UnitFindMode", "");
        this.FindCondition = ini.ReadString("General", "FindCondition", "");
        this.AutoExecute = "1".equalsIgnoreCase(ini.ReadString("General", "AutoExecute", "0"));
    }

    private void loadFetchFormulas(Ini ini) {
        String aSecName = "FetchFormula";
        String countCode = ini.ReadString(aSecName, "Count", "0");
        if (StringUtils.isNotEmpty((String)countCode)) {
            int count = Integer.parseInt(countCode);
            for (int i = 0; i < count; ++i) {
                String ident = String.format("%1$s%2$s", "Item_", i);
                String value = ini.ReadString(aSecName, ident, "");
                if (!StringUtils.isNotEmpty((String)value)) continue;
                AnalCellFormulaItem cell = new AnalCellFormulaItem();
                int pos1 = (value = value.replace("&equals;", "=").trim()).indexOf("=");
                if (pos1 > 0) {
                    String rightFormula;
                    block14: {
                        String leftFormula = value.substring(0, pos1);
                        rightFormula = value.substring(pos1 + 1, value.length());
                        int pos21 = leftFormula.indexOf("[");
                        int pos22 = leftFormula.indexOf(",");
                        int pos23 = leftFormula.indexOf("]");
                        if (pos21 >= 0 && pos22 > pos21 && pos23 > pos22) {
                            String codeRow = leftFormula.substring(pos21 + 1, pos22);
                            String codeCol = leftFormula.substring(pos22 + 1, pos23);
                            try {
                                if ("*".equalsIgnoreCase(codeCol)) {
                                    cell.setColNum(-1);
                                } else {
                                    cell.setColNum(Integer.parseInt(codeCol));
                                }
                            }
                            catch (Exception ex) {
                                cell.setColNum(-1);
                            }
                            try {
                                if ("*".equalsIgnoreCase(codeRow)) {
                                    cell.setRowNum(-1);
                                    break block14;
                                }
                                cell.setRowNum(Integer.parseInt(codeRow));
                            }
                            catch (Exception ex) {
                                cell.setRowNum(-1);
                            }
                        } else if (pos21 >= 0 && pos23 > pos21) {
                            String codeField = leftFormula.substring(pos21 + 1, pos23);
                            cell.setFieldCode(codeField);
                        } else {
                            cell.setFieldCode(leftFormula);
                        }
                    }
                    cell.setFormula(rightFormula);
                }
                cell.setOldFormula(value);
                this.getFetchFormulas().add(cell);
            }
        }
    }

    private void loadRowCondFormulas(Ini ini) {
        String aSecName = "RowCondition";
        String countCode = ini.ReadString(aSecName, "Count", "0");
        if (StringUtils.isNotEmpty((String)countCode)) {
            int count = Integer.parseInt(countCode);
            for (int i = 0; i < count; ++i) {
                String ident = String.format("%1$s%2$s", "Item_", i);
                String value = ini.ReadString(aSecName, ident, "");
                if (!StringUtils.isNotEmpty((String)value)) continue;
                AnalCellFormulaItem cell = new AnalCellFormulaItem();
                value = value.replace("&equals;", "=").trim();
                cell.setFormula(value);
                cell.setOldFormula(value);
                cell.setPosY(i);
                this.getRowConditions().add(cell);
            }
        }
    }

    private void loadColCondFormulas(Ini ini) {
        String aSecName = "ColCondition";
        String countCode = ini.ReadString(aSecName, "Count", "0");
        if (StringUtils.isNotEmpty((String)countCode)) {
            int count = Integer.parseInt(countCode);
            for (int i = 0; i < count; ++i) {
                String ident = String.format("%1$s%2$s", "Item_", i);
                String value = ini.ReadString(aSecName, ident, "");
                if (!StringUtils.isNotEmpty((String)value)) continue;
                AnalCellFormulaItem cell = new AnalCellFormulaItem();
                value = value.replace("&equals;", "=").trim();
                cell.setFormula(value);
                cell.setOldFormula(value);
                cell.setPosX(i);
                this.getColConditions().add(cell);
            }
        }
    }

    private void loadRegionFormulas(Ini ini, int floatingRow) {
        String aSecName = "F" + String.valueOf(floatingRow);
        if (ini.SectionExists(aSecName)) {
            AnalTableRegion region = new AnalTableRegion();
            region.setFloatingRow(floatingRow);
            String countCode = ini.ReadString(aSecName, "Count", "0");
            if (StringUtils.isNotEmpty((String)countCode)) {
                int count = Integer.parseInt(countCode);
                for (int i = 0; i < count; ++i) {
                    String ident = String.format("%1$s%2$s", "Item_", i);
                    String value = ini.ReadString(aSecName, ident, "");
                    if (!StringUtils.isNotEmpty((String)value)) continue;
                    AnalCellFormulaItem exp = new AnalCellFormulaItem();
                    value = value.replace("&equals;", "=").trim();
                    exp.setFormula(value);
                    exp.setOldFormula(value);
                    exp.setPosX(i);
                    region.getStatFormulas().add(exp);
                }
            }
            String listCondition = ini.ReadString(aSecName, "ListCondition", "");
            String listFilter = ini.ReadString(aSecName, "ListFilter", "");
            String sortFields = ini.ReadString(aSecName, "SortFields", "");
            String sortFlags = ini.ReadString(aSecName, "SortFlags", "");
            String maxRowCountCode = ini.ReadString(aSecName, "MaxRowCount", "");
            String classifyFields = ini.ReadString(aSecName, "ClassifyFields", "");
            String classifyWidths = ini.ReadString(aSecName, "ClassifyWidths", "");
            String classifySumOnly = ini.ReadString(aSecName, "ClassifySumOnly", "");
            String keyFields = ini.ReadString(aSecName, "SYS_KeyFields", "");
            String dataIsEmpty = ini.ReadString(aSecName, "SYS_Empty", "0");
            region.setListCondition(listCondition);
            region.setListFilter(listFilter);
            region.setSortFields(sortFields);
            region.setSortFlags(sortFlags);
            region.setClassifyFields(classifyFields);
            region.setClassifyWidths(classifyWidths);
            region.setKeyFields(keyFields);
            region.setEmpty("1".equalsIgnoreCase(dataIsEmpty));
            try {
                region.setMaxRowCount(Integer.parseInt(maxRowCountCode));
            }
            catch (Exception ex) {
                region.setMaxRowCount(0);
            }
            try {
                if (StringUtils.isEmpty((String)classifySumOnly)) {
                    region.setClassifySumOnly(false);
                } else if ("0".equalsIgnoreCase(classifySumOnly)) {
                    region.setClassifySumOnly(false);
                } else if ("1".equalsIgnoreCase(classifySumOnly)) {
                    region.setClassifySumOnly(true);
                } else {
                    region.setClassifySumOnly(Boolean.parseBoolean(classifySumOnly));
                }
            }
            catch (Exception ex) {
                region.setClassifySumOnly(false);
            }
            this.getRegionInfos().put(String.valueOf(floatingRow), region);
        }
    }

    public List<AnalCellFormulaItem> getFetchFormulas() {
        if (this.fetchFormulas == null) {
            this.fetchFormulas = new ArrayList<AnalCellFormulaItem>();
        }
        return this.fetchFormulas;
    }

    public void setFetchFormulas(List<AnalCellFormulaItem> fetchFormulas) {
        this.fetchFormulas = fetchFormulas;
    }

    public List<AnalCellFormulaItem> getColConditions() {
        if (this.colConditions == null) {
            this.colConditions = new ArrayList<AnalCellFormulaItem>();
        }
        return this.colConditions;
    }

    public void setColConditions(List<AnalCellFormulaItem> colConditions) {
        this.colConditions = colConditions;
    }

    public List<AnalCellFormulaItem> getRowConditions() {
        if (this.rowConditions == null) {
            this.rowConditions = new ArrayList<AnalCellFormulaItem>();
        }
        return this.rowConditions;
    }

    public void setRowConditions(List<AnalCellFormulaItem> rowConditions) {
        this.rowConditions = rowConditions;
    }

    public Map<String, AnalTableRegion> getRegionInfos() {
        if (this.regionInfos == null) {
            this.regionInfos = new LinkedHashMap<String, AnalTableRegion>();
        }
        return this.regionInfos;
    }

    public void setRegionInfos(Map<String, AnalTableRegion> regionInfos) {
        this.regionInfos = regionInfos;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public AnalTableSet getParent() {
        return this.parent;
    }

    public void setParent(AnalTableSet parent) {
        this.parent = parent;
    }

    public String getShowCondition() {
        return this.showCondition;
    }

    public void setShowCondition(String showCondition) {
        this.showCondition = showCondition;
    }

    public String getShowCaption() {
        return this.showCaption;
    }

    public void setShowCaption(String showCaption) {
        this.showCaption = showCaption;
    }

    public String getUnitFindMode() {
        return this.unitFindMode;
    }

    public void setUnitFindMode(String unitFindMode) {
        this.unitFindMode = unitFindMode;
    }

    public String getFindCondition() {
        return this.FindCondition;
    }

    public void setFindCondition(String findCondition) {
        this.FindCondition = findCondition;
    }

    public boolean isAutoExecute() {
        return this.AutoExecute;
    }

    public void setAutoExecute(boolean autoExecute) {
        this.AutoExecute = autoExecute;
    }
}

