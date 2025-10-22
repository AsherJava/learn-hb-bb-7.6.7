/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.maker;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.BBBTInfo;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.JQTHeader;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.maker.table.FMDMMaker;
import com.jiuqi.nr.single.core.para.maker.table.ReportMaker;
import com.jiuqi.nr.single.core.para.maker.table.ZbMaker;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.GroupKeyInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
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

public class JQTMaker {
    private static final Logger logger = LoggerFactory.getLogger(JQTMaker.class);
    private String dirName;
    private List<String> fileNames;
    private HashSet<String> fjrpNames;
    private HashSet<String> wzrpNames;
    private HashSet<String> fixTables;
    private HashSet<String> floatTables;
    private ParaInfo paraInfo;
    private Map<String, ReportFormImpl> repMap;
    private Map<String, BBBTInfo> infoList = new HashMap<String, BBBTInfo>();

    public void InitDirName(String dirName) {
        this.dirName = dirName.charAt(dirName.length() - 1) == File.separatorChar ? dirName : dirName + File.separatorChar;
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
            ReadUtil.skipStream((Stream)iStream, 96);
            jqtFileMap.init((Stream)iStream);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return jqtFileMap;
    }

    public void saveJQTFileMap(String filePath, JQTFileMap jqtFileMap) throws StreamException, IOException {
        MemStream iStream = null;
        try {
            iStream = new MemStream();
            iStream.seek(0L, 0);
            JQTHeader header = new JQTHeader();
            header.save((Stream)iStream);
            jqtFileMap.save((Stream)iStream);
            iStream.saveToFile(filePath);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void saveJIOReports() throws Exception {
        String prefix = this.paraInfo.getPrefix() == null ? "" : this.paraInfo.getPrefix();
        this.fileNames = new ArrayList<String>();
        this.fjrpNames = new HashSet();
        this.wzrpNames = new HashSet();
        this.fixTables = new HashSet();
        this.floatTables = new HashSet();
        String afile = this.dirName + "PARA" + File.separatorChar + "BBBT.DBF";
        try {
            this.createReportDBF(afile);
            IDbfTable writer = DbfTableUtil.getDbfTable(afile);
            this.infoList = new HashMap<String, BBBTInfo>();
            for (int i = 0; i < this.paraInfo.getTableCount(); ++i) {
                RepInfo rep = this.paraInfo.getRepInfos().get(i);
                if (rep.isFMDM()) continue;
                DataRow rowObjects = new DataRow();
                rowObjects.setColumns(writer.getTable().getColumns());
                String name = rep.getCode();
                String bblx = rep.getTableTypeCode();
                String title = rep.getTitle();
                String subTitle = rep.getSubTitle();
                String subNo = rep.getSubNo();
                String jedw = rep.getMoneyUnit();
                String hzfs = "";
                boolean isZDSC = rep.getCalcView();
                String filter = rep.getFilter();
                rowObjects.setValue(1, (Object)name);
                rowObjects.setValue(2, (Object)bblx);
                rowObjects.setValue(3, (Object)title);
                rowObjects.setValue(4, (Object)subTitle);
                rowObjects.setValue(5, (Object)subNo);
                rowObjects.setValue(6, (Object)jedw);
                rowObjects.setValue(7, (Object)hzfs);
                rowObjects.setValue(10, (Object)isZDSC);
                rowObjects.setValue(11, (Object)filter);
                BBBTInfo info = new BBBTInfo();
                ReportFormImpl reportFormImpl = new ReportFormImpl();
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
                        this.floatTables.add(name);
                    }
                    this.fileNames.add(name);
                }
                this.repMap.put(name, reportFormImpl);
                writer.getTable().getRows().add(rowObjects);
            }
            writer.saveData();
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void createReportDBF(String afile) throws Exception {
        DBFCreator ATable = new DBFCreator();
        ATable.addField("Index", 'N', 6, 0);
        ATable.addField("BBBS", 'C', 20, 0);
        ATable.addField("BBLX", 'C', 1, 0);
        ATable.addField("ZBT", 'C', 100, 0);
        ATable.addField("FBT", 'C', 100, 0);
        ATable.addField("BBBH", 'C', 20, 0);
        ATable.addField("JEDW", 'C', 20, 0);
        ATable.addField("HZFS", 'C', 1, 0);
        ATable.addField("BTB", 'L', 1, 0);
        ATable.addField("reserved0", 'N', 12, 0);
        ATable.addField("reserved1", 'C', 4, 0);
        ATable.addField("TXTJ", 'C', 254, 0);
        ATable.addField("YCB", 'L', 1, 0);
        ATable.createTable(afile);
    }

    private void saveJIOReportGroups() throws Exception {
        String afile = this.dirName + "PARA" + File.separatorChar + "BBFilter";
        Ini ini = new Ini();
        for (String groupName : this.paraInfo.getReportGroupNameList()) {
            ini.WriteString("Sys_FilterList", groupName, "");
        }
        for (GroupKeyInfo group : this.paraInfo.getReportGroupList()) {
            ini.WriteString(group.getValue(), group.getCode(), "");
        }
        ini.saveIni(afile);
    }

    public final void saveInfos(ParaInfo paraInfo) throws Exception {
        this.paraInfo = paraInfo;
        String prefix = paraInfo.getPrefix() == null ? "" : paraInfo.getPrefix();
        String name = "FMDM";
        String afile = this.dirName + "PARA" + File.separatorChar + name + ".JQT";
        JQTFileMap jqtFileMap = paraInfo.getJqtFileMap(name);
        this.saveJQTFileMap(afile, jqtFileMap);
        FMDMMaker maker = new FMDMMaker();
        FMRepInfo fmRepInfo = paraInfo.getFmRepInfo();
        maker.saveFMDM(fmRepInfo, afile, paraInfo, jqtFileMap);
        ZbMaker zbMaker = new ZbMaker(this.repMap);
        FieldDefs zbMainBodyInfo = zbMaker.savePara(fmRepInfo.getDefs(), afile, paraInfo, jqtFileMap, name, fmRepInfo, null);
        fmRepInfo.getStyleBMPId(this.dirName + File.separatorChar + "PARA");
        zbMaker.setZDMLength(fmRepInfo.getZDMLength());
        this.saveJIOReports();
        this.saveJIOReportGroups();
        ReportMaker rpMaker = new ReportMaker(this.repMap);
        for (RepInfo repInfo : paraInfo.getRepInfos()) {
            String rptName = repInfo.getCode();
            afile = this.dirName + "PARA" + File.separatorChar + rptName + ".JQT";
            jqtFileMap = paraInfo.getJqtFileMap(rptName);
            if (repInfo.getTableType() == ReportTableType.RTT_BLOBTABLE) {
                jqtFileMap = new JQTFileMap();
            } else if (repInfo.getTableType() == ReportTableType.RTT_WORDTABLE) {
                jqtFileMap = new JQTFileMap();
            }
            if (jqtFileMap == null) continue;
            if (!this.fjrpNames.contains(rptName) && !this.wzrpNames.contains(rptName)) {
                repInfo = rpMaker.savePara(repInfo, afile, paraInfo, jqtFileMap, rptName, this.floatTables.contains(rptName));
            }
            BBBTInfo bbbtInfo = this.infoList.get(rptName);
            zbMaker.savePara(repInfo.getDefs(), afile, paraInfo, jqtFileMap, rptName, repInfo, bbbtInfo.getBblx());
        }
        this.saveLrgs(fmRepInfo);
    }

    private void saveLrgs(FMRepInfo fmRepInfo) {
        String aLrgsFile = this.dirName + "PARA" + File.separatorChar + "SYS_LRGS.DAT";
        Ini ini = new Ini();
        for (ZBInfo zb : fmRepInfo.getDefs().getZbs()) {
            ini.WriteString(fmRepInfo.getCode() + ".Default", zb.getFieldName(), zb.getDefaultValue());
        }
        for (RepInfo repInfo2 : this.paraInfo.getRepInfos()) {
            for (ZBInfo zb : repInfo2.getDefs().getZbs()) {
                ini.WriteString(repInfo2.getCode() + ".Default", zb.getFieldName(), zb.getDefaultValue());
            }
            for (FieldDefs def : repInfo2.getDefs().getSubMbs()) {
                for (ZBInfo zb : def.getZbs()) {
                    ini.WriteString(repInfo2.getCode() + ".Default", zb.getFieldName(), zb.getDefaultValue());
                }
            }
        }
        ini.saveIni(aLrgsFile);
    }
}

