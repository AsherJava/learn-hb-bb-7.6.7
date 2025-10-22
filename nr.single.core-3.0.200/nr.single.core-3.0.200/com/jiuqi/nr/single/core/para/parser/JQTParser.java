/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.BBBTInfo;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.JQTHeader;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.table.FMDMParser;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.GroupKeyInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportParser;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZbParser;
import com.jiuqi.nr.single.core.para.util.FormulaParseUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataRowCollection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JQTParser {
    private static final Logger logger = LoggerFactory.getLogger(JQTParser.class);
    private String dirName;
    private List<String> fileNames;
    private HashSet<String> fjrpNames;
    private HashSet<String> wzrpNames;
    private HashSet<String> fixTables;
    private HashSet<String> rowFloatTables;
    private HashSet<String> colFloatTables;
    private ParaInfo paraInfo;
    private Map<String, ReportFormImpl> repMap;
    private Map<String, BBBTInfo> infoList = new HashMap<String, BBBTInfo>();
    private List<String> groupNames = new ArrayList<String>();
    private Map<String, String> groupReportMap;
    private List<GroupKeyInfo> groupReportList;
    private boolean mulPage = false;
    private String curFilter = null;

    public void InitDirName(String dirName) {
        this.dirName = dirName.charAt(dirName.length() - 1) == File.separatorChar ? dirName : dirName + File.separatorChar;
    }

    private void ModiryRpFilter(String prefix) {
        for (String key : this.repMap.keySet()) {
            ReportFormImpl reportFormImpl = this.repMap.get(key);
            String filter = reportFormImpl.getFilter();
            if (!(filter != null && !filter.trim().equals("") || filter != null && !filter.trim().equals(""))) continue;
            String newFilter = FormulaParseUtil.ConvertFormula(null, filter, null, this.paraInfo, reportFormImpl.getName());
            if (filter.toLowerCase().equals(newFilter.toLowerCase())) continue;
            reportFormImpl.setFilter(newFilter);
        }
    }

    private List<ZBInfo> GetJIOZBMap(FieldDefs zbMainBodyInfo) {
        List<FieldDefs> subMbs = zbMainBodyInfo.getSubMbs();
        List<ZBInfo> zbs = zbMainBodyInfo.getZbs();
        if (subMbs != null && subMbs.size() != 0) {
            for (FieldDefs subMb : subMbs) {
                List<ZBInfo> subZbs = this.GetJIOZBMap(subMb);
                if (zbs == null) {
                    zbs = new ArrayList<ZBInfo>();
                }
                if (subZbs == null) continue;
                ZBInfo[] zbArray = subZbs.toArray(new ZBInfo[0]);
                zbs = new ArrayList<ZBInfo>();
                for (ZBInfo zb : zbArray) {
                    zbs.add(zb);
                }
            }
        }
        return zbs;
    }

    public JQTFileMap InitJQTFileMap(String filePath) throws StreamException, IOException {
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
            JQTHeader header = new JQTHeader();
            header.init((Stream)iStream);
            jqtFileMap.init((Stream)iStream);
            jqtFileMap.setHeader(header);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return jqtFileMap;
    }

    private void InitJIOReports() throws Exception {
        String prefix = this.paraInfo.getPrefix() == null ? "" : this.paraInfo.getPrefix();
        this.fileNames = new ArrayList<String>();
        this.fjrpNames = new HashSet();
        this.wzrpNames = new HashSet();
        this.fixTables = new HashSet();
        this.rowFloatTables = new HashSet();
        this.colFloatTables = new HashSet();
        String afile = this.dirName + "PARA" + File.separatorChar + "BBBT.DBF";
        try {
            IDbfTable reader = DbfTableUtil.getDbfTable(afile);
            this.infoList = new HashMap<String, BBBTInfo>();
            DataRowCollection dataRows = reader.getTable().getRows();
            for (int i = 0; i < dataRows.size(); ++i) {
                DataRow rowObjects = (DataRow)dataRows.get(i);
                BBBTInfo info = new BBBTInfo();
                ReportFormImpl reportFormImpl = new ReportFormImpl();
                String name = ((String)rowObjects.getValue(1)).trim().toUpperCase();
                String bblx = ((String)rowObjects.getValue(2)).trim().toUpperCase();
                String title = ((String)rowObjects.getValue(3)).trim();
                String subTitle = ((String)rowObjects.getValue(4)).trim();
                String subNo = ((String)rowObjects.getValue(5)).trim();
                String jedw = ((String)rowObjects.getValue(6)).trim();
                String hzfs = ((String)rowObjects.getValue(7)).trim();
                boolean isZDSC = ((String)rowObjects.getValue(10)).toUpperCase().trim().equals("T");
                String filter = ((String)rowObjects.getValue(11)).trim();
                reportFormImpl.setName(!StringUtils.isEmpty((String)prefix) ? prefix + name : name);
                reportFormImpl.setTitle(title);
                reportFormImpl.setSubTitle(subTitle);
                reportFormImpl.setSubNo(subNo);
                reportFormImpl.setFilter(filter);
                reportFormImpl.setFormType("CRID");
                reportFormImpl.setType(0);
                reportFormImpl.setGather(!hzfs.equals("N"));
                reportFormImpl.setCalcView(isZDSC);
                reportFormImpl.setJEDW(jedw);
                info.setName(name);
                info.setBblx(bblx);
                info.setHzfs(hzfs);
                if (this.repMap == null) {
                    this.repMap = new HashMap<String, ReportFormImpl>();
                }
                this.infoList.put(name, info);
                if (new String("F").equals(bblx.toUpperCase())) {
                    this.fjrpNames.add(name);
                    this.fileNames.add(name);
                } else if (new String("W").equals(bblx.toUpperCase())) {
                    this.wzrpNames.add(name);
                    this.fileNames.add(name);
                } else {
                    if (new String("X").equals(bblx.toUpperCase())) {
                        this.fixTables.add(name);
                    }
                    if (new String("R").equals(bblx.toUpperCase())) {
                        this.rowFloatTables.add(name);
                    }
                    if (new String("C").equals(bblx.toUpperCase())) {
                        this.colFloatTables.add(name);
                    }
                    this.fileNames.add(name);
                }
                this.repMap.put(name, reportFormImpl);
            }
        }
        catch (DbfException e) {
            logger.error(e.getMessage(), e);
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void InitJIOReportGroups() throws Exception {
        this.groupReportMap = new HashMap<String, String>();
        this.groupReportList = new ArrayList<GroupKeyInfo>();
        String afile = this.dirName + "PARA" + File.separatorChar + "BBFilter";
        Ini ini = new Ini();
        ini.loadIniFile(afile);
        this.mulPage = "1".equalsIgnoreCase(ini.ReadString("Sys_FilterList", "MulPage", "0"));
        this.curFilter = ini.ReadString("Sys_FilterList", "Sys_CurFilter", "");
        ArrayList<String> list = ini.ReadSection("Sys_FilterList");
        if (null != list) {
            for (String group : list) {
                this.groupNames.add(group);
                ArrayList<String> reportCodes = ini.ReadSection(group);
                if (null == reportCodes) continue;
                for (String code : reportCodes) {
                    this.groupReportList.add(new GroupKeyInfo(code, group, null));
                    this.groupReportMap.put(code, group);
                }
            }
        }
    }

    private void InitFMDM() {
        String prefix = this.paraInfo.getPrefix() == null ? "" : this.paraInfo.getPrefix();
        ReportFormImpl reportFormImpl = new ReportFormImpl();
        reportFormImpl.setName(prefix + "FMDM");
        reportFormImpl.setTitle("\u5c01\u9762\u4ee3\u7801");
        reportFormImpl.setSubTitle("");
        reportFormImpl.setSubNo("");
        reportFormImpl.setFilter("");
        reportFormImpl.setFormType("CRID");
        reportFormImpl.setType(0);
        this.repMap = new HashMap<String, ReportFormImpl>();
        this.repMap.put("FMDM", reportFormImpl);
    }

    private FMRepInfo initFMDMRepInfo(ZbParser zbParser) throws Exception {
        FMRepInfo fmRepInfo = null;
        String name = "FMDM";
        String afile = this.dirName + "PARA" + File.separatorChar + name + ".JQT";
        JQTFileMap jqtFileMap = this.InitJQTFileMap(afile);
        if (jqtFileMap == null) {
            logger.info("\u5c01\u9762\u4ee3\u7801\u672a\u5b9a\u4e49");
            fmRepInfo = new FMRepInfo();
            fmRepInfo.setCode("FMDM");
            fmRepInfo.setTitle("\u5c01\u9762\u4ee3\u7801");
            FieldDefs zbMainBodyInfo = new FieldDefs();
            fmRepInfo.addDefs(zbMainBodyInfo);
            fmRepInfo.setColWidth(200);
            fmRepInfo.setNotDefine(true);
            this.paraInfo.AddRepInfo(fmRepInfo);
        } else {
            jqtFileMap.setCode(name);
            this.paraInfo.AddJQTFile(jqtFileMap);
            FMDMParser parser = new FMDMParser();
            fmRepInfo = parser.BuildFMDM(afile, this.paraInfo.getFmzbList(), jqtFileMap);
            if (this.repMap.containsKey(name)) {
                ReportFormImpl rep = this.repMap.get(name);
                fmRepInfo.setTitle(rep.getTitle());
                fmRepInfo.setSubNo(rep.getSubNo());
                fmRepInfo.setSubTitle(rep.getSubTitle());
            }
            fmRepInfo.setFMDM(true);
            this.paraInfo.AddRepInfo(fmRepInfo);
            FieldDefs zbMainBodyInfo = zbParser.BuildPara(afile, this.paraInfo, jqtFileMap, name, fmRepInfo, null);
            fmRepInfo.addDefs(zbMainBodyInfo);
            fmRepInfo.getStyleBMPId(this.dirName + File.separatorChar + "PARA");
            zbParser.setZDMLength(fmRepInfo.getZDMLength());
        }
        return fmRepInfo;
    }

    public final void InitInfos(ParaInfo paraInfo) throws Exception {
        this.paraInfo = paraInfo;
        String prefix = paraInfo.getPrefix() == null ? "" : paraInfo.getPrefix();
        this.InitFMDM();
        ZbParser zbParser = new ZbParser(this.repMap);
        FMRepInfo fmRepInfo = this.initFMDMRepInfo(zbParser);
        this.InitJIOReports();
        this.InitJIOReportGroups();
        for (String groupName : this.groupNames) {
            paraInfo.AddReportGroupName(groupName);
        }
        HashMap<String, String> reportGroupMap2 = new HashMap<String, String>();
        ArrayList<GroupKeyInfo> reportGroupList2 = new ArrayList<GroupKeyInfo>();
        if (this.groupReportList.size() > 1) {
            for (GroupKeyInfo group : this.groupReportList) {
                String groupName = group.getValue();
                if (reportGroupMap2.containsKey(group.getCode())) {
                    groupName = (String)reportGroupMap2.get(group.getCode()) + "," + groupName;
                }
                reportGroupMap2.put(group.getCode(), groupName);
                reportGroupList2.add(new GroupKeyInfo(group.getCode(), group.getValue(), null));
            }
        }
        paraInfo.setReportGroupMap(reportGroupMap2);
        paraInfo.setReportGroupList(reportGroupList2);
        paraInfo.setMulPage(this.mulPage);
        paraInfo.setCurGroupName(this.curFilter);
        JQTFileMap jqtFileMap = null;
        String afile = null;
        FieldDefs zbMainBodyInfo = null;
        ReportParser rpParser = new ReportParser(this.repMap);
        RepInfo repInfo = null;
        for (String rptName : this.fileNames) {
            afile = this.dirName + "PARA" + File.separatorChar + rptName + ".JQT";
            jqtFileMap = this.InitJQTFileMap(afile);
            if (this.fjrpNames.contains(rptName)) {
                jqtFileMap = new JQTFileMap();
            } else if (this.wzrpNames.contains(rptName)) {
                jqtFileMap = new JQTFileMap();
            }
            if (jqtFileMap == null) continue;
            jqtFileMap.setCode(rptName);
            paraInfo.AddJQTFile(jqtFileMap);
            if (!this.fjrpNames.contains(rptName) && !this.wzrpNames.contains(rptName)) {
                repInfo = rpParser.buildPara(afile, paraInfo, jqtFileMap, rptName, this.rowFloatTables.contains(rptName) || this.colFloatTables.contains(rptName));
            }
            if (this.fixTables.contains(rptName)) {
                repInfo.setTableType(ReportTableType.RTT_FIXTABLE);
            }
            if (this.rowFloatTables.contains(rptName)) {
                repInfo.setTableType(ReportTableType.RTT_ROWFLOATTABLE);
            }
            if (this.colFloatTables.contains(rptName)) {
                repInfo.setTableType(ReportTableType.RTT_COLFLOATTALBE);
            }
            if (this.fjrpNames.contains(rptName)) {
                repInfo = new RepInfo();
                repInfo.setCode(rptName);
                repInfo.setTableType(ReportTableType.RTT_BLOBTABLE);
            }
            if (this.wzrpNames.contains(rptName)) {
                repInfo = new RepInfo();
                repInfo.setCode(rptName);
                repInfo.setTableType(ReportTableType.RTT_WORDTABLE);
            }
            if (this.repMap.containsKey(rptName)) {
                ReportFormImpl rep = this.repMap.get(rptName);
                repInfo.setTitle(rep.getTitle());
                repInfo.setSubNo(rep.getSubNo());
                repInfo.setSubTitle(rep.getSubTitle());
                repInfo.setFilter(rep.getFilter());
                repInfo.setGather(rep.getGather());
                repInfo.setMoneyUnit(rep.getJEDW());
                repInfo.setCalcView(rep.getCalcView());
            }
            paraInfo.AddRepInfo(repInfo);
            if (!StringUtils.isEmpty((String)prefix)) {
                repInfo.setCode(repInfo.getCode());
            }
            BBBTInfo bbbtInfo = this.infoList.get(rptName);
            try {
                zbMainBodyInfo = zbParser.BuildPara(afile, paraInfo, jqtFileMap, rptName, repInfo, bbbtInfo.getBblx());
            }
            catch (Exception e) {
                logger.info("\u62a5\u8868\u89e3\u6790\u5f02\u5e38\uff1a" + rptName);
                throw e;
            }
            if (zbMainBodyInfo == null) continue;
            repInfo.addDefs(zbMainBodyInfo);
        }
        String aLrgsFile = this.dirName + "PARA" + File.separatorChar + "SYS_LRGS.DAT";
        Ini ini = new Ini();
        ini.loadIniFile(aLrgsFile);
        if (fmRepInfo != null) {
            for (ZBInfo zb : fmRepInfo.getDefs().getZbs()) {
                zb.setDefaultValue(ini.ReadString(fmRepInfo.getCode() + ".Default", zb.getFieldName(), ""));
            }
        }
        for (RepInfo repInfo2 : paraInfo.getRepInfos()) {
            for (ZBInfo zb : repInfo2.getDefs().getZbs()) {
                zb.setDefaultValue(ini.ReadString(repInfo2.getCode() + ".Default", zb.getFieldName(), ""));
            }
            for (FieldDefs def : repInfo2.getDefs().getSubMbs()) {
                for (ZBInfo zb : def.getZbs()) {
                    zb.setDefaultValue(ini.ReadString(repInfo2.getCode() + ".Default", zb.getFieldName(), ""));
                }
            }
        }
    }
}

