/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.lib.util.SingleConvert
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.anal;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.FormulaInfo;
import com.jiuqi.nr.single.core.para.FormulaVariableInfo;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.FormulaParse;
import com.jiuqi.nr.single.core.para.parser.anal.AnalCellFormulaItem;
import com.jiuqi.nr.single.core.para.parser.anal.AnalGetInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableSet;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.table.FMDMParser;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZbParser;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataRowCollection;
import com.jiuqi.nr.single.lib.util.SingleConvert;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalParse {
    private static final Logger logger = LoggerFactory.getLogger(AnalParse.class);
    private String taskDir;

    public final void InitDirName(String dirName) {
        this.taskDir = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws Exception {
        this.loadAnalTaskInfo(paraInfo);
    }

    public final void parseInner(ParaInfo paraInfo) throws Exception {
        this.loadInnerAnalTaskInfo(paraInfo);
    }

    public final void loadAnalTaskInfo(ParaInfo paraInfo) throws Exception {
        this.loadAnalInfo(paraInfo);
        this.copyFromBaseParaInfo(paraInfo.getAnalInfo(), paraInfo);
        try {
            this.copyConditionsTitle(paraInfo.getAnalInfo());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        if (paraInfo.getAnalInfo().getRootTableSets().size() > 0) {
            for (int i = 0; i < paraInfo.getAnalInfo().getRootTableSets().size(); ++i) {
                AnalTableSet tableSet = paraInfo.getAnalInfo().getRootTableSets().get(i);
                AnalParaInfo subAnalInfo = new AnalParaInfo();
                ParaInfo analSubParaInfo = new ParaInfo(paraInfo.getTaskDir());
                analSubParaInfo.setParaType(1);
                subAnalInfo.setAnalParaInfo(analSubParaInfo);
                subAnalInfo.setFilterFormula(tableSet.getFilter());
                this.copySubFromBaseParaInfo(paraInfo.getAnalInfo(), paraInfo, subAnalInfo, tableSet, i);
                paraInfo.getAnalInfo().getSubAnalInfos().add(subAnalInfo);
            }
        }
    }

    public final void loadInnerAnalTaskInfo(ParaInfo paraInfo) throws Exception {
        this.loadInnerAnalTableInfo(paraInfo);
        this.copyInnerFromBaseParaInfo(paraInfo.getInnerAnalInfo(), paraInfo);
        try {
            this.copyConditionsTitle(paraInfo.getInnerAnalInfo());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        AnalGetInfo getInfo = this.loadAnalGet();
        paraInfo.getInnerAnalInfo().setAnalGetInfo(getInfo);
    }

    private void copyConditionsTitle(AnalParaInfo analParaInfo) {
        for (AnalTableInfo analTable : analParaInfo.getAnalTableList().values()) {
            Grid2Data gridData;
            analTable.getRepInfo();
            if ("FMDM".equalsIgnoreCase(analTable.getRepInfo().getCode()) || (gridData = this.GetJioGridDta(analTable.getRepInfo())) == null || analTable.getRepInfo().getTitleArr() == null) continue;
            int rowCount = gridData.getRowCount();
            HashMap<String, AnalCellFormulaItem> colTitleDic = new HashMap<String, AnalCellFormulaItem>();
            for (AnalCellFormulaItem cellItem : analTable.getColConditions()) {
                int posX = cellItem.getPosX();
                String colTitle = null;
                if (rowCount > 1) {
                    colTitle = gridData.getGridCellData(posX, 1).getEditText();
                }
                if (StringUtils.isEmpty(colTitle) && rowCount > 2) {
                    colTitle = gridData.getGridCellData(posX, 2).getEditText();
                }
                if (StringUtils.isEmpty(colTitle)) {
                    colTitle = "\u7b2c" + String.valueOf(posX) + "\u680f";
                }
                if (colTitleDic.containsKey(colTitle)) {
                    colTitle = colTitle + "_" + String.valueOf(posX);
                }
                cellItem.setTitle(colTitle);
                colTitleDic.put(colTitle, cellItem);
            }
            HashMap<String, AnalCellFormulaItem> rowTitleDic = new HashMap<String, AnalCellFormulaItem>();
            for (AnalCellFormulaItem cellItem : analTable.getRowConditions()) {
                int posY = cellItem.getPosY();
                String rowTitle = null;
                if (rowCount > 1) {
                    rowTitle = gridData.getGridCellData(1, posY).getEditText();
                }
                if (StringUtils.isEmpty(rowTitle) && rowCount > 2) {
                    rowTitle = gridData.getGridCellData(2, posY).getEditText();
                }
                if (StringUtils.isEmpty(rowTitle)) {
                    rowTitle = "\u7b2c" + String.valueOf(posY) + "\u884c";
                }
                if (rowTitleDic.containsKey(rowTitle)) {
                    rowTitle = rowTitle + "_" + String.valueOf(posY);
                }
                cellItem.setTitle(rowTitle);
                rowTitleDic.put(rowTitle, cellItem);
            }
        }
    }

    private void loadAnalInfo(ParaInfo paraInfo) throws Exception {
        Map<String, AnalTableInfo> analTableList = this.loadAnalTableList();
        paraInfo.getAnalInfo().setAnalTableList(analTableList);
        for (AnalTableInfo table : analTableList.values()) {
            String tableFlag = table.getRepInfo().getCode();
            String sFile = this.taskDir + "ANALPARA" + File.separatorChar + (String)tableFlag + ".JQT";
            JQTFileMap jqtFile = this.InitJQTFileMap(sFile);
            if (jqtFile == null) {
                throw new Exception((String)tableFlag + "\u672a\u5b9a\u4e49");
            }
            boolean isFMDM = "CSFMDM".equalsIgnoreCase(tableFlag);
            if (!isFMDM) {
                jqtFile.setCode(tableFlag);
                MemStream stream = new MemStream();
                stream.loadFromFile(sFile);
                stream.seek(0L, 0);
                table.LoadFromJQT(sFile, (Stream)stream, jqtFile);
                Grid2Data gridData = this.GetJioGridDta(table.getRepInfo());
                stream.seek(0L, 0);
                table.LoadPrint(gridData, (Stream)stream, jqtFile);
                String infoFile = this.taskDir + "ANALPARA" + File.separatorChar + (String)tableFlag + ".DAT";
                table.LoadFormulas(infoFile);
                continue;
            }
            jqtFile.setCode(tableFlag);
            FMDMParser parser = new FMDMParser();
            FMRepInfo fmRepInfo = parser.BuildFMDM(sFile, paraInfo.getAnalInfo().getFmZbList(), jqtFile);
            if (analTableList.containsKey(tableFlag)) {
                this.initFMDMRepInfo(fmRepInfo);
                fmRepInfo.setCode(tableFlag);
                analTableList.get(tableFlag).setRepInfo(fmRepInfo);
            }
            fmRepInfo.setFMDM(true);
            ZbParser zbParser = new ZbParser(null);
            FieldDefs zbMainBodyInfo = zbParser.BuildPara(sFile, paraInfo, jqtFile, "FMDM", fmRepInfo, null);
            fmRepInfo.addDefs(zbMainBodyInfo);
            fmRepInfo.getStyleBMPId(this.taskDir + File.separatorChar + "ANALPARA");
            zbParser.setZDMLength(fmRepInfo.getZDMLength());
        }
        Map<String, AnalTableSet> analTabsetList = this.InitAnalInfo(paraInfo.getAnalInfo());
        paraInfo.getAnalInfo().setAnalTabsetList(analTabsetList);
        ArrayList<AnalTableSet> rootTableSets = new ArrayList<AnalTableSet>();
        for (AnalTableSet tableSet : analTabsetList.values()) {
            String parentCode = tableSet.getParentCode();
            if (!StringUtils.isEmpty((String)parentCode)) continue;
            rootTableSets.add(tableSet);
            tableSet.loadAllSubTables();
        }
        paraInfo.getAnalInfo().setRootTableSets(rootTableSets);
        FormulaParse formulaParse = new FormulaParse();
        String formulaFile = this.taskDir + "ANALPARA" + File.separatorChar + "SYS_GS.DAT";
        if (this.isFileExist(formulaFile)) {
            Map<String, List<FormulaInfo>> formulas = formulaParse.LoadFormulasFromFile(formulaFile);
            paraInfo.getAnalInfo().setFormulas(formulas);
        }
        String varFile = this.taskDir + "ANALPARA" + File.separatorChar + "SYS_GSBL.Ini";
        if (this.isFileExist(formulaFile)) {
            List<FormulaVariableInfo> formualVarlist = formulaParse.LoadFormulaVarialbesFromFile(varFile);
            paraInfo.getAnalInfo().setFormulaVariables(formualVarlist);
        }
    }

    private void loadInnerAnalTableInfo(ParaInfo paraInfo) throws Exception {
        HashMap<String, AnalTableInfo> analTableList = new HashMap<String, AnalTableInfo>();
        paraInfo.getInnerAnalInfo().setAnalTableList(analTableList);
        for (RepInfo rep : paraInfo.getRepInfos()) {
            String infoFile;
            File file;
            String tableFlag = rep.getCode();
            boolean isFMDM = "FMDM".equalsIgnoreCase(tableFlag);
            if (isFMDM || !(file = new File(infoFile = this.taskDir + "PARA" + File.separatorChar + tableFlag + ".DAT")).exists()) continue;
            AnalTableInfo table = new AnalTableInfo();
            table.setRepInfo(rep);
            analTableList.put(rep.getCode(), table);
            table.LoadFormulas(infoFile);
        }
        HashMap<String, AnalTableSet> analTabsetList = new HashMap<String, AnalTableSet>();
        paraInfo.getInnerAnalInfo().setAnalTabsetList(analTabsetList);
        if (analTableList.size() > 0) {
            AnalTableSet tableSet = new AnalTableSet();
            tableSet.setCode("test01");
            tableSet.setTitle("");
            analTabsetList.put(tableSet.getCode(), tableSet);
        }
        ArrayList<AnalTableSet> rootTableSets = new ArrayList<AnalTableSet>();
        for (AnalTableSet tableSet : analTabsetList.values()) {
            String parentCode = tableSet.getParentCode();
            if (!StringUtils.isEmpty((String)parentCode)) continue;
            rootTableSets.add(tableSet);
            tableSet.loadAllSubTables();
        }
        paraInfo.getAnalInfo().setRootTableSets(rootTableSets);
    }

    private void copySubFromBaseParaInfo(AnalParaInfo analInfo, ParaInfo baseParaInfo, AnalParaInfo subAnalInfo, AnalTableSet tableSet, int subIndex) {
        subAnalInfo.getAnalParaInfo().setPrefix(tableSet.getCode());
        subAnalInfo.getAnalParaInfo().setFileFlag(baseParaInfo.getFileFlag());
        subAnalInfo.getAnalParaInfo().setTaskYear(baseParaInfo.getTaskYear());
        subAnalInfo.getAnalParaInfo().setTaskType(baseParaInfo.getTaskType());
        subAnalInfo.getAnalParaInfo().setTaskName(tableSet.getTitle());
        subAnalInfo.getAnalParaInfo().setTaskGroup(baseParaInfo.getTaskGroup());
        subAnalInfo.getAnalParaInfo().setTime(baseParaInfo.getTaskTime());
        subAnalInfo.getAnalParaInfo().setVersion(baseParaInfo.getTaskVerion());
        subAnalInfo.getAnalParaInfo().setFloatOrderField(baseParaInfo.getFloatOrderField());
        subAnalInfo.getAnalParaInfo().getRepInfos().add(analInfo.getAnalParaInfo().getRepInfos().get(0));
        for (AnalTableInfo table : tableSet.getSubAllTables()) {
            RepInfo rep = table.getRepInfo();
            subAnalInfo.getAnalParaInfo().getRepInfos().add(rep);
            subAnalInfo.getAnalTableList().put(rep.getCode(), table);
            if (analInfo.getFormulas().containsKey(rep.getCode())) {
                List<FormulaInfo> oldCalcFormulas = analInfo.getFormulas().get(rep.getCode());
                subAnalInfo.getFormulas().put(rep.getCode(), oldCalcFormulas);
            }
            if (!analInfo.getFetchFormulas().containsKey(rep.getCode())) continue;
            List<FormulaInfo> oldFetchFormulas = analInfo.getFetchFormulas().get(rep.getCode());
            subAnalInfo.getFetchFormulas().put(rep.getCode(), oldFetchFormulas);
        }
        for (AnalTableSet subTableSet : tableSet.getSubTabSets()) {
            subAnalInfo.getAnalTabsetList().put(subTableSet.getCode(), subTableSet);
            subAnalInfo.getRootTableSets().add(subTableSet);
            subAnalInfo.getAnalParaInfo().AddReportGroupName(subTableSet.getTitle());
            for (AnalTableInfo subAnalTable : subTableSet.getSubAllTables()) {
                subAnalInfo.getAnalParaInfo().AddReportGroupMap(subAnalTable.getRepInfo().getCode(), subTableSet.getTitle());
            }
        }
        String bjFlag = "SYS_ALLTABLE";
        if (analInfo.getFormulas().containsKey(bjFlag)) {
            subAnalInfo.getFormulas().put(bjFlag, analInfo.getFormulas().get(bjFlag));
        }
        subAnalInfo.getAnalParaInfo().AddFmlGroup("\u5206\u6790\u53d6\u6570\u516c\u5f0f", subAnalInfo.getFetchFormulas());
        subAnalInfo.getAnalParaInfo().AddFmlGroup("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848", subAnalInfo.getFormulas());
        HashMap<String, GridPrintMan> printMans = new HashMap<String, GridPrintMan>();
        subAnalInfo.getAnalParaInfo().AddPrintGroup("\u9ed8\u8ba4\u6253\u5370\u65b9\u6848", printMans);
    }

    private void copyFromBaseParaInfo(AnalParaInfo analInfo, ParaInfo baseParaInfo) {
        analInfo.getAnalParaInfo().setPrefix(baseParaInfo.getPrefix() + "_Anal");
        analInfo.getAnalParaInfo().setFileFlag(baseParaInfo.getFileFlag());
        analInfo.getAnalParaInfo().setTaskYear(baseParaInfo.getTaskYear());
        analInfo.getAnalParaInfo().setTaskType(baseParaInfo.getTaskType());
        analInfo.getAnalParaInfo().setTaskName(baseParaInfo.getTaskName() + "\u5206\u6790\u8868");
        analInfo.getAnalParaInfo().setTaskGroup(baseParaInfo.getTaskGroup());
        analInfo.getAnalParaInfo().setTime(baseParaInfo.getTaskTime());
        analInfo.getAnalParaInfo().setVersion(baseParaInfo.getTaskVerion());
        analInfo.getAnalParaInfo().setFloatOrderField(baseParaInfo.getFloatOrderField());
        int tableIndex = 1;
        for (AnalTableInfo table : analInfo.getAnalTableList().values()) {
            RepInfo rep = table.getRepInfo();
            analInfo.getAnalParaInfo().getRepInfos().add(table.getRepInfo());
            ArrayList<FormulaInfo> formulas = new ArrayList<FormulaInfo>();
            int formulaIndex = 1;
            for (AnalCellFormulaItem item : table.getFetchFormulas()) {
                int colNum = item.getColNum();
                int rowNum = item.getRowNum();
                ZBInfo zbInfo = null;
                if (colNum <= 0 && rowNum <= 0) {
                    String fieldCode = item.getFieldCode();
                    zbInfo = rep.findZBInfoByFieldCode(fieldCode);
                    if (zbInfo != null && zbInfo.getGridPos() != null) {
                        if (zbInfo.getGridPos().length > 1) {
                            item.setPosX(zbInfo.getGridPos()[0]);
                        }
                        if (zbInfo.getGridPos().length > 1) {
                            item.setPosY(zbInfo.getGridPos()[1]);
                        }
                        item.setTitle(zbInfo.getZbTitle());
                    }
                } else {
                    if (rowNum < 0) {
                        rowNum = 1;
                    }
                    if (colNum < 0) {
                        colNum = 1;
                    }
                    if ((zbInfo = rep.findZBInfoByColRowNum(colNum, rowNum)) != null && zbInfo.getGridPos() != null) {
                        if (item.getColNum() >= 0) {
                            item.setPosX(zbInfo.getGridPos()[0]);
                        }
                        if (item.getRowNum() >= 0) {
                            item.setPosY(zbInfo.getGridPos()[1]);
                        }
                        item.setTitle(zbInfo.getZbTitle());
                    }
                }
                FormulaInfo finfo = new FormulaInfo();
                this.copyfomulaFromItem(finfo, item);
                String aCode = String.valueOf(tableIndex);
                aCode = formulaIndex < 10 ? aCode + "0" + String.valueOf(formulaIndex) : (formulaIndex < 100 ? aCode + "" + String.valueOf(formulaIndex) : aCode + "-" + String.valueOf(formulaIndex));
                finfo.setCode(aCode);
                formulas.add(finfo);
                ++formulaIndex;
            }
            analInfo.getFetchFormulas().put(table.getRepInfo().getCode(), formulas);
            ++tableIndex;
        }
        analInfo.getAnalParaInfo().AddFmlGroup("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848", analInfo.getFormulas());
        analInfo.getAnalParaInfo().AddFmlGroup("\u5206\u6790\u53d6\u6570\u516c\u5f0f", analInfo.getFetchFormulas());
    }

    private void copyInnerFromBaseParaInfo(AnalParaInfo analInfo, ParaInfo baseParaInfo) {
        analInfo.getAnalParaInfo().setPrefix(baseParaInfo.getPrefix() + "_Anal");
        analInfo.getAnalParaInfo().setFileFlag(baseParaInfo.getFileFlag());
        analInfo.getAnalParaInfo().setTaskYear(baseParaInfo.getTaskYear());
        analInfo.getAnalParaInfo().setTaskType(baseParaInfo.getTaskType());
        analInfo.getAnalParaInfo().setTaskName(baseParaInfo.getTaskName() + "\u5206\u6790\u8868");
        analInfo.getAnalParaInfo().setTaskGroup(baseParaInfo.getTaskGroup());
        analInfo.getAnalParaInfo().setTime(baseParaInfo.getTaskTime());
        analInfo.getAnalParaInfo().setVersion(baseParaInfo.getTaskVerion());
        analInfo.getAnalParaInfo().setFloatOrderField(baseParaInfo.getFloatOrderField());
        int tableIndex = 1;
        for (AnalTableInfo table : analInfo.getAnalTableList().values()) {
            RepInfo rep = table.getRepInfo();
            analInfo.getAnalParaInfo().getRepInfos().add(table.getRepInfo());
            ArrayList<FormulaInfo> formulas = new ArrayList<FormulaInfo>();
            int formulaIndex = 1;
            for (AnalCellFormulaItem item : table.getFetchFormulas()) {
                int colNum = item.getColNum();
                int rowNum = item.getRowNum();
                ZBInfo zbInfo = null;
                if (colNum <= 0 && rowNum <= 0) {
                    String fieldCode = item.getFieldCode();
                    zbInfo = rep.findZBInfoByFieldCode(fieldCode);
                    if (zbInfo != null && zbInfo.getGridPos() != null) {
                        if (zbInfo.getGridPos().length > 1) {
                            item.setPosX(zbInfo.getGridPos()[0]);
                        }
                        if (zbInfo.getGridPos().length > 1) {
                            item.setPosY(zbInfo.getGridPos()[1]);
                        }
                        item.setTitle(zbInfo.getZbTitle());
                    }
                } else {
                    if (rowNum < 0) {
                        rowNum = 1;
                    }
                    if (colNum < 0) {
                        colNum = 1;
                    }
                    if ((zbInfo = rep.findZBInfoByColRowNum(colNum, rowNum)) != null && zbInfo.getGridPos() != null) {
                        if (item.getColNum() >= 0) {
                            item.setPosX(zbInfo.getGridPos()[0]);
                        }
                        if (item.getRowNum() >= 0) {
                            item.setPosY(zbInfo.getGridPos()[1]);
                        }
                        item.setTitle(zbInfo.getZbTitle());
                    }
                }
                FormulaInfo finfo = new FormulaInfo();
                this.copyfomulaFromItem(finfo, item);
                String aCode = String.valueOf(tableIndex);
                aCode = formulaIndex < 10 ? aCode + "0" + String.valueOf(formulaIndex) : (formulaIndex < 100 ? aCode + "" + String.valueOf(formulaIndex) : aCode + "-" + String.valueOf(formulaIndex));
                finfo.setCode(aCode);
                formulas.add(finfo);
                ++formulaIndex;
            }
            analInfo.getFetchFormulas().put(table.getRepInfo().getCode(), formulas);
            ++tableIndex;
        }
        if (analInfo.getFetchFormulas() != null && analInfo.getFetchFormulas().size() > 0) {
            analInfo.getAnalParaInfo().AddFmlGroup("\u5206\u6790\u53d6\u6570\u516c\u5f0f", analInfo.getFetchFormulas());
            baseParaInfo.AddFmlGroup("\u5206\u6790\u53d6\u6570\u516c\u5f0f", analInfo.getFetchFormulas());
        }
    }

    private void copyfomulaFromItem(FormulaInfo finfo, AnalCellFormulaItem item) {
        finfo.setCalcType("0010");
        finfo.setUserLevel("A");
        finfo.setExpression(item.getOldFormula());
    }

    private JQTFileMap InitJQTFileMap(String filePath) throws StreamException, IOException {
        JQTFileMap jqtFileMap = new JQTFileMap();
        File jqtFile = new File(filePath);
        if (!jqtFile.exists()) {
            return null;
        }
        MemStream iStream = null;
        try {
            iStream = new MemStream();
            iStream.loadFromFile(filePath);
            iStream.seek(0L, 0);
            ReadUtil.skipStream((Stream)iStream, 96);
            jqtFileMap.init((Stream)iStream);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return jqtFileMap;
    }

    private Map<String, AnalTableSet> InitAnalInfo(AnalParaInfo analInfo) throws IOException, StreamException {
        LinkedHashMap<String, AnalTableSet> result = new LinkedHashMap<String, AnalTableSet>();
        String file = this.taskDir + "ANALPARA" + File.separatorChar + "Tabset.DBF";
        if (this.isFileExist(file)) {
            try {
                AnalTableSet lastTableSet = null;
                IDbfTable reader = DbfTableUtil.getDbfTable(file);
                DataRowCollection dataRows = reader.getTable().getRows();
                for (int i = 0; i < dataRows.size(); ++i) {
                    DataRow rowObjects = (DataRow)dataRows.get(i);
                    String flag = ((String)rowObjects.getValue(0)).trim().toUpperCase();
                    String tabflag = ((String)rowObjects.getValue(1)).trim().toUpperCase();
                    String caption = ((String)rowObjects.getValue(2)).trim();
                    String descript = ((String)rowObjects.getValue(3)).trim();
                    String fitrange = ((String)rowObjects.getValue(4)).trim();
                    String filter = ((String)rowObjects.getValue(5)).trim();
                    if (StringUtils.isNotEmpty((String)flag)) {
                        AnalTableSet tableSet = new AnalTableSet();
                        tableSet.setCode(flag);
                        tableSet.setParentCode(tabflag);
                        tableSet.setTitle(caption);
                        tableSet.setDescript(descript);
                        tableSet.setFilter(filter);
                        try {
                            tableSet.setFitRange(SingleConvert.toInt((String)fitrange));
                        }
                        catch (Exception ex) {
                            tableSet.setFitRange(0);
                        }
                        result.put(flag, tableSet);
                        if (StringUtils.isNotEmpty((String)tabflag) && tabflag.indexOf("@") == 0) {
                            String parentCode = tabflag.substring(1, tabflag.length());
                            tableSet.setParentCode(parentCode);
                            if (result.containsKey(parentCode)) {
                                AnalTableSet parentTableSet = (AnalTableSet)result.get(parentCode);
                                parentTableSet.getSubTabSets().add(tableSet);
                                tableSet.setParent(parentTableSet);
                            } else {
                                tableSet.setParentCode(null);
                            }
                        }
                        lastTableSet = tableSet;
                        continue;
                    }
                    if (!StringUtils.isNotEmpty((String)tabflag) || tabflag.indexOf("@") >= 0 || !analInfo.getAnalTableList().containsKey(tabflag) || lastTableSet == null) continue;
                    lastTableSet.getSubRepCodes().add(tabflag);
                    AnalTableInfo tableInfo = analInfo.getAnalTableList().get(tabflag);
                    lastTableSet.getSubTables().add(tableInfo);
                    tableInfo.setParent(lastTableSet);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private Grid2Data GetJioGridDta(RepInfo repInfo) {
        if (repInfo == null || repInfo.getReportData() == null) {
            return null;
        }
        Grid2Data data2 = Grid2Data.bytesToGrid((byte[])repInfo.getReportData().getGridBytes());
        return data2;
    }

    private Map<String, AnalTableInfo> loadAnalTableList() {
        String file;
        LinkedHashMap<String, AnalTableInfo> analTableList = new LinkedHashMap<String, AnalTableInfo>();
        String fmFile = this.taskDir + "ANALPARA" + File.separatorChar + "CSFMDM.JQT";
        if (this.isFileExist(fmFile)) {
            AnalTableInfo fmTableInfo = new AnalTableInfo();
            fmTableInfo.getRepInfo().setCode("CSFMDM");
            this.initFMDMRepInfo(fmTableInfo.getRepInfo());
            analTableList.put("CSFMDM", fmTableInfo);
        }
        if (this.isFileExist(file = this.taskDir + "ANALPARA" + File.separatorChar + "BBBT.DBF")) {
            try {
                IDbfTable reader = DbfTableUtil.getDbfTable(file);
                DataRowCollection dataRows = reader.getTable().getRows();
                for (int i = 0; i < dataRows.size(); ++i) {
                    DataRow rowObjects = (DataRow)dataRows.get(i);
                    String name = ((String)rowObjects.getValue(1)).trim().toUpperCase();
                    String bblx = ((String)rowObjects.getValue(2)).trim().toUpperCase();
                    String title = ((String)rowObjects.getValue(3)).trim();
                    String subTitle = ((String)rowObjects.getValue(4)).trim();
                    String subNo = ((String)rowObjects.getValue(5)).trim();
                    String jedw = ((String)rowObjects.getValue(6)).trim();
                    String hzfs = ((String)rowObjects.getValue(7)).trim();
                    boolean isZDSC = ((String)rowObjects.getValue(10)).toUpperCase().trim().equals("T");
                    String filter = ((String)rowObjects.getValue(11)).trim();
                    AnalTableInfo tableInfo = new AnalTableInfo();
                    tableInfo.getRepInfo().setCode(name);
                    tableInfo.getRepInfo().setTableType(bblx);
                    tableInfo.getRepInfo().setTitle(title);
                    tableInfo.getRepInfo().setSubTitle(subTitle);
                    tableInfo.getRepInfo().setSubNo(subNo);
                    tableInfo.getRepInfo().setMoneyUnit(jedw);
                    tableInfo.getRepInfo().setGather(!hzfs.equals("N"));
                    tableInfo.getRepInfo().setCalcView(isZDSC);
                    tableInfo.setFilter(filter);
                    analTableList.put(name, tableInfo);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return analTableList;
    }

    private AnalGetInfo loadAnalGet() {
        String infoFile = this.taskDir + "PARA" + File.separatorChar + "AnalGet.Ini";
        File file = new File(infoFile);
        if (!file.exists()) {
            return null;
        }
        AnalGetInfo analGetInfo = new AnalGetInfo();
        Ini ini = new Ini();
        try {
            ini.loadIniFile(infoFile);
            analGetInfo.setMenuCaption(ini.ReadString("SYS_Info", "MenuCaption", ""));
            analGetInfo.setSourceTask(Integer.parseInt(ini.ReadString("SYS_Info", "SourceTask", "-1")));
            analGetInfo.setSourceUnitSelect(ini.ReadString("SYS_Info", "SourceUnitSelect", ""));
            analGetInfo.setShowTableSel("1".equalsIgnoreCase(ini.ReadString("SYS_Info", "ShowTableSel", "0")));
            analGetInfo.setUnitSelAll("1".equalsIgnoreCase(ini.ReadString("SYS_Info", "UnitSelAll", "0")));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return analGetInfo;
    }

    private boolean isFileExist(String sFile) {
        File file = new File(sFile);
        return file.exists();
    }

    private void initFMDMRepInfo(RepInfo fmRepInfo) {
        fmRepInfo.setCode("CSFMDM");
        fmRepInfo.setTableType("X");
        fmRepInfo.setTitle("\u5c01\u9762\u4ee3\u7801");
        fmRepInfo.setSubTitle("");
        fmRepInfo.setSubNo("");
        fmRepInfo.setMoneyUnit("");
        fmRepInfo.setFilter("");
        fmRepInfo.setGather(false);
        fmRepInfo.setCalcView(false);
    }
}

