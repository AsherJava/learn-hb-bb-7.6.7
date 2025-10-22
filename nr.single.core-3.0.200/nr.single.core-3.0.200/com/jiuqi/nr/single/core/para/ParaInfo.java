/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.single.core.para;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.SingleConsts;
import com.jiuqi.nr.single.core.para.FormulaInfo;
import com.jiuqi.nr.single.core.para.FormulaVariableInfo;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.NBRolPeriod;
import com.jiuqi.nr.single.core.para.consts.PeriodType;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.impl.ReportDataImpl;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalInfo;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.GroupKeyInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParaInfo {
    private static final Logger logger = LoggerFactory.getLogger(ParaInfo.class);
    private String taskFlag;
    private String fileFlag;
    private String taskYear;
    private String taskType;
    private String taskName;
    private String taskGroup;
    private String time;
    private String version;
    private String taskDir;
    private String floatOrderField;
    private String netFileFlag;
    private String netTaskCode;
    private String netTaskTitle;
    private boolean isModify = false;
    private Map<String, String> reportGroupMap = new LinkedHashMap<String, String>();
    private List<GroupKeyInfo> reportGroupList = new ArrayList<GroupKeyInfo>();
    private List<String> reportGroupNameList = new ArrayList<String>();
    private boolean mulPage = false;
    private String curGroupName = null;
    private boolean ValueEmptyByEnumCtrl = false;
    private Map<String, List<ZBInfo>> jioZBMap = new LinkedHashMap<String, List<ZBInfo>>();
    private Map<String, String> basedataNameMap = new HashMap<String, String>();
    private List<String> fmzbList = new ArrayList<String>();
    private List<String> exceptionList = new ArrayList<String>();
    private Map<String, JQTFileMap> jqtList = new LinkedHashMap<String, JQTFileMap>();
    private Map<String, EnumsItemModel> enumItems = new HashMap<String, EnumsItemModel>();
    private List<RepInfo> repList = new ArrayList<RepInfo>();
    private Map<String, Map<String, List<FormulaInfo>>> fmlmgr = new LinkedHashMap<String, Map<String, List<FormulaInfo>>>();
    private Map<String, Map<String, GridPrintMan>> printmgr = new LinkedHashMap<String, Map<String, GridPrintMan>>();
    private List<LinkTaskBean> taskLinkList = new ArrayList<LinkTaskBean>();
    private Map<String, QueryModalInfo> horzQueryModalList;
    private Map<String, QueryModalInfo> vertQueryModalList;
    private List<FormulaVariableInfo> formulaVariables;
    private AnalParaInfo analInfo;
    private AnalParaInfo innerAnalInfo;
    private int paraType = 0;
    private boolean iniFieldMap = false;
    private boolean hasGetRepCodeList = false;
    private Map<String, RepInfo> repCodeList = new HashMap<String, RepInfo>();

    public List<LinkTaskBean> getTaskLinkList() {
        return this.taskLinkList;
    }

    public Map<String, QueryModalInfo> getHorzQueryModalList() {
        return this.horzQueryModalList;
    }

    public void setHorzQueryModalList(Map<String, QueryModalInfo> horzQueryModalList) {
        this.horzQueryModalList = horzQueryModalList;
    }

    public Map<String, QueryModalInfo> getVertQueryModalList() {
        return this.vertQueryModalList;
    }

    public void setVertQueryModalList(Map<String, QueryModalInfo> vertQueryModalList) {
        this.vertQueryModalList = vertQueryModalList;
    }

    public ParaInfo(String taskDir) {
        this.taskDir = taskDir;
    }

    public final boolean isModify() {
        return this.isModify;
    }

    public final void setModify(boolean isModify) {
        this.isModify = isModify;
    }

    public final Map<String, String> getReportGroupMap() {
        return this.reportGroupMap;
    }

    public final List<GroupKeyInfo> getReportGroupList() {
        return this.reportGroupList;
    }

    public final void setReportGroupMap(Map<String, String> reportGropuMap) {
        this.reportGroupMap = reportGropuMap;
    }

    public final void setReportGroupList(List<GroupKeyInfo> reportGroupList) {
        this.reportGroupList = reportGroupList;
    }

    public final List<String> getReportGroupNameList() {
        return this.reportGroupNameList;
    }

    public final void AddReportGroupMap(String key, String value_ren) {
        this.reportGroupMap.put(key, value_ren);
        this.reportGroupList.add(new GroupKeyInfo(key, value_ren, null));
    }

    public final void AddReportGroupName(String groupName) {
        this.reportGroupNameList.add(groupName);
    }

    public final String getPrefix() {
        return this.taskFlag;
    }

    public final void setPrefix(String prefix) {
        this.taskFlag = prefix;
    }

    public final String getFileFlag() {
        return this.fileFlag;
    }

    public final void setFileFlag(String value) {
        this.fileFlag = value;
    }

    public final String getTaskYear() {
        return this.taskYear;
    }

    public final void setTaskYear(String value) {
        this.taskYear = value;
    }

    public final void setTime(String value) {
        this.time = value;
    }

    public final String getTaskTime() {
        return this.time;
    }

    public final void setVersion(String value) {
        this.version = value;
    }

    public final String getTaskVerion() {
        return this.version;
    }

    public final Map<String, List<ZBInfo>> getJioZBMap() {
        return this.jioZBMap;
    }

    public final void AddJioZBMap(String key, List<ZBInfo> value_ren) {
        this.jioZBMap.put(key, value_ren);
        HashMap<String, String> zbMap = new HashMap<String, String>();
        for (ZBInfo zbInfo : value_ren) {
            zbMap.put(zbInfo.getFieldName(), zbInfo.getFieldName());
        }
    }

    public final List<String> getFmzbList() {
        return this.fmzbList;
    }

    public final void setFmzbList(List<String> fmzbList) {
        this.fmzbList = fmzbList;
    }

    public final List<String> getExceptionList() {
        return this.exceptionList;
    }

    public final void setExceptionList(List<String> exceptionList) {
        this.exceptionList = exceptionList;
    }

    public final Map<String, String> getBasedataNameMap() {
        return this.basedataNameMap;
    }

    public final void setBasedataNameMap(Map<String, String> basedataNameMap) {
        this.basedataNameMap = basedataNameMap;
    }

    public final String getTaskType() {
        return this.taskType;
    }

    public final void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public final void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public final String getTaskName() {
        return this.taskName;
    }

    public final void setTaskGroup(String value) {
        this.taskGroup = value;
    }

    public final String getTaskGroup() {
        return this.taskGroup;
    }

    public String getNetTaskCode() {
        return this.netTaskCode;
    }

    public void setNetTaskCode(String netTaskCode) {
        this.netTaskCode = netTaskCode;
    }

    public String getNetTaskTitle() {
        return this.netTaskTitle;
    }

    public void setNetTaskTitle(String netTaskTitle) {
        this.netTaskTitle = netTaskTitle;
    }

    public final String getNetFileFlag() {
        if (StringUtils.isEmpty((String)this.netFileFlag)) {
            return this.fileFlag;
        }
        return this.netFileFlag;
    }

    public final void setNetFileFlag(String value) {
        this.netFileFlag = value;
    }

    public final Map<String, RepInfo> getRepInfoCodeList() {
        if (this.hasGetRepCodeList) {
            return this.repCodeList;
        }
        this.repCodeList.clear();
        for (RepInfo repinfo : this.repList) {
            this.repCodeList.put(repinfo.getCode(), repinfo);
        }
        this.hasGetRepCodeList = true;
        return this.repCodeList;
    }

    public final void AddRepInfo(RepInfo rep) {
        this.repList.add(rep);
        this.hasGetRepCodeList = false;
    }

    public final int getTableCount() {
        return this.repList.size();
    }

    public final Map<String, EnumsItemModel> getEnunList() {
        return this.enumItems;
    }

    public final void AddFmlGroup(String fmlGroup, Map<String, List<FormulaInfo>> formulaMap) {
        this.fmlmgr.put(fmlGroup, formulaMap);
    }

    public final Map<String, Map<String, List<FormulaInfo>>> getFmlMgr() {
        return this.fmlmgr;
    }

    public final void AddPrintGroup(String printGroup, Map<String, GridPrintMan> printMans) {
        this.printmgr.put(printGroup, printMans);
    }

    public final Map<String, Map<String, GridPrintMan>> getPrtMgr() {
        return this.printmgr;
    }

    private FMRepInfo getFmRepInfo2() {
        if (this.repList.size() > 0) {
            try {
                return (FMRepInfo)this.repList.get(0);
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                return null;
            }
        }
        return null;
    }

    public final FMRepInfo getFmRepInfo() {
        return this.getFmRepInfo2();
    }

    public final List<RepInfo> getRepInfos() {
        if (this.repList == null) {
            this.repList = new ArrayList<RepInfo>();
        }
        return this.repList;
    }

    public final EnumsItemModel getSQEnum() {
        ZBInfo zb;
        int sqid = -1;
        FMRepInfo fmInfo = this.getFmRepInfo();
        if (fmInfo != null) {
            sqid = fmInfo.getSQFieldId();
        }
        EnumsItemModel oldEnum = null;
        if (sqid > 0 && fmInfo != null && null != (zb = fmInfo.getDefs().getZbs().get(sqid)).getEnumInfo()) {
            oldEnum = this.getEnunList().get(zb.getEnumInfo().getEnumIdenty());
        }
        return oldEnum;
    }

    private void getCodeList(FMRepInfo fmRepInfo, List<String> codeList, List<String> nameList, List<String> nbCodeList) {
        List<ZBInfo> zbList = fmRepInfo.getDefs().getZbs();
        ZBInfo zbInfo = null;
        if (fmRepInfo.getSQFieldId() < zbList.size()) {
            zbInfo = zbList.get(fmRepInfo.getSQFieldId());
        }
        if (zbInfo != null) {
            EnumInfo enumInfo = zbInfo.getEnumInfo();
            if (enumInfo != null) {
                String enumIdenty = enumInfo.getEnumIdenty();
                if (this.enumItems.containsKey(enumIdenty)) {
                    EnumsItemModel enumModal = this.enumItems.get(enumIdenty);
                    Map<String, DataInfo> datas = enumModal.getEnumItemList();
                    codeList.clear();
                    nbCodeList.clear();
                    for (String key : datas.keySet()) {
                        codeList.add(key);
                        nbCodeList.add(key);
                        DataInfo data = datas.get(key);
                        nameList.add(data.getName());
                    }
                }
            } else {
                codeList.add("01");
                nbCodeList.add("01");
                nameList.add("\u4e00\u6708");
                codeList.add("02");
                nbCodeList.add("02");
                nameList.add("\u4e8c\u6708");
                codeList.add("03");
                nbCodeList.add("03");
                nameList.add("\u4e09\u6708");
                codeList.add("04");
                nbCodeList.add("04");
                nameList.add("\u56db\u6708");
                codeList.add("05");
                nbCodeList.add("05");
                nameList.add("\u4e94\u6708");
                codeList.add("06");
                nbCodeList.add("06");
                nameList.add("\u516d\u6708");
                codeList.add("07");
                nbCodeList.add("07");
                nameList.add("\u4e03\u6708");
                codeList.add("08");
                nbCodeList.add("08");
                nameList.add("\u516b\u6708");
                codeList.add("09");
                nbCodeList.add("09");
                nameList.add("\u4e5d\u6708");
                codeList.add("10");
                nbCodeList.add("10");
                nameList.add("\u5341\u6708");
                codeList.add("11");
                nbCodeList.add("11");
                nameList.add("\u5341\u4e00\u6708");
                codeList.add("12");
                nbCodeList.add("12");
                nameList.add("\u5341\u4e8c\u6708");
            }
        }
    }

    private String getNBDefaultSQGS() {
        FMRepInfo fmRepInfo = this.getFmRepInfo();
        if (fmRepInfo == null || fmRepInfo.getSQFieldId() <= 0) {
            return "";
        }
        List<ZBInfo> zbList = fmRepInfo.getDefs().getZbs();
        ZBInfo zbInfo = null;
        if (fmRepInfo.getSQFieldId() < zbList.size()) {
            zbInfo = zbList.get(fmRepInfo.getSQFieldId());
        }
        if (zbInfo == null) {
            return "";
        }
        EnumInfo enumInfo = zbInfo.getEnumInfo();
        if (enumInfo == null) {
            return "";
        }
        String enumIdenty = enumInfo.getEnumIdenty();
        if (this.enumItems.containsKey(enumIdenty)) {
            EnumsItemModel enumModal = this.enumItems.get(enumIdenty);
            if (enumModal == null) {
                return "";
            }
            if (enumModal.getCodeLen() == 6) {
                return "YYYYMM";
            }
            if (enumModal.getCodeLen() == 2) {
                return "MM";
            }
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void getTaskPerriod(String taskPeriodType, Map<String, NBRolPeriod> codeList, List<String> nameList) throws IOException, StreamException {
        String oldTaskYear = this.taskYear;
        try {
            int i;
            FMRepInfo fmRepInfo;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date(0L);
            this.taskYear = sdf.format(date);
            String gs = this.getSQGS();
            String sqType = taskPeriodType;
            if (StringUtils.isEmpty((String)gs)) {
                gs = this.getNBDefaultSQGS();
            }
            int YLen = 0;
            for (int i2 = 0; i2 < gs.length() && gs.charAt(i2) == 'Y'; ++i2) {
                ++YLen;
            }
            ArrayList<String> tmpCodelist = new ArrayList<String>();
            ArrayList<String> nbCodeList = new ArrayList<String>();
            PeriodType enumTaskType = SingleConsts.ChangepeiodType(this.taskType);
            if (enumTaskType == PeriodType.HALFYEAR || enumTaskType == PeriodType.YEAR) {
                if (sqType.equals("H")) {
                    tmpCodelist.add("0001");
                    nbCodeList.add("");
                    nameList.add(this.taskYear + "\u5e74\u4e0a\u534a\u5e74");
                    tmpCodelist.add("0002");
                    nbCodeList.add("");
                    nameList.add(this.taskYear + "\u5e74\u4e0b\u534a\u5e74");
                } else {
                    tmpCodelist.add("0001");
                    nbCodeList.add("");
                    nameList.add(this.taskYear + "\u5e74");
                    if (StringUtils.isEmpty((String)sqType)) {
                        if (enumTaskType == PeriodType.HALFYEAR) {
                            sqType = "H";
                        }
                        if (enumTaskType == PeriodType.YEAR) {
                            sqType = "N";
                        }
                    }
                }
            } else if (enumTaskType == PeriodType.SEASON) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    tmpCodelist.add("0001");
                    tmpCodelist.add("0002");
                    tmpCodelist.add("0003");
                    tmpCodelist.add("0004");
                    nbCodeList.add("01");
                    nbCodeList.add("02");
                    nbCodeList.add("03");
                    nbCodeList.add("04");
                    nameList.add("\u7b2c\u4e00\u5b63\u5ea6");
                    nameList.add("\u7b2c\u4e8c\u5b63\u5ea6");
                    nameList.add("\u7b2c\u4e09\u5b63\u5ea6");
                    nameList.add("\u7b2c\u56db\u5b63\u5ea6");
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "J";
                }
            } else if (enumTaskType == PeriodType.MONTH) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    for (i = 1; i <= 12; ++i) {
                        tmpCodelist.add(new Integer(i).toString());
                        if (i < 10) {
                            nbCodeList.add("0" + new Integer(i).toString());
                        } else {
                            nbCodeList.add(new Integer(i).toString());
                        }
                        nameList.add(String.format("\u7b2c%1$s\u6708", i));
                    }
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "Y";
                }
            } else if (enumTaskType == PeriodType.TENDAY) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    for (i = 1; i <= 36; ++i) {
                        tmpCodelist.add(new Integer(i).toString());
                        if (i < 10) {
                            nbCodeList.add("0" + new Integer(i).toString());
                        } else {
                            nbCodeList.add(new Integer(i).toString());
                        }
                        nameList.add(String.format("\u7b2c%1$s\u65ec", i));
                    }
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "T";
                }
            } else if (enumTaskType == PeriodType.WEEK) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    for (i = 1; i <= 53; ++i) {
                        tmpCodelist.add(new Integer(i).toString());
                        if (i < 10) {
                            nbCodeList.add("0" + new Integer(i).toString());
                        } else {
                            nbCodeList.add(new Integer(i).toString());
                        }
                        nameList.add(String.format("\u7b2c%1$s\u5468", i));
                    }
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "W";
                }
            } else if (enumTaskType == PeriodType.DAY) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    for (i = 1; i <= 365; ++i) {
                        tmpCodelist.add(new Integer(i).toString());
                        if (i < 10) {
                            nbCodeList.add("0" + new Integer(i).toString());
                        } else {
                            nbCodeList.add(new Integer(i).toString());
                        }
                        nameList.add(String.format("\u7b2c%1$s\u5929", i));
                    }
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "D";
                }
            } else if (enumTaskType == PeriodType.CUSTOM) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                } else {
                    for (i = 1; i <= 9999; ++i) {
                        tmpCodelist.add(new Integer(i).toString());
                        nameList.add(String.format("\u7b2c%1$s\u671f", i));
                    }
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "B";
                }
            } else if (enumTaskType == PeriodType.UNKNOWN) {
                fmRepInfo = this.getFmRepInfo();
                if (fmRepInfo.getSQFieldId() > 0) {
                    this.getCodeList(fmRepInfo, tmpCodelist, nameList, nbCodeList);
                }
                if (StringUtils.isEmpty((String)sqType)) {
                    sqType = "U";
                }
            }
            int index = 0;
            codeList.clear();
            for (String code : tmpCodelist) {
                String code1 = String.valueOf(index + 1);
                String code2 = this.taskYear;
                if (YLen == 0) {
                    if (code1.length() < 4) {
                        code1 = new String("0000").substring(0, 4 - code1.length()) + code1;
                    } else if (code1.length() >= 8) {
                        code2 = code1.substring(0, 4);
                        code1 = code1.substring(code1.length() - 4, code1.length() - 4 + 4);
                    } else if (code1.length() > 4) {
                        code1 = code1.substring(code1.length() - 4, code1.length() - 4 + 4);
                    }
                } else {
                    if (YLen > 4) {
                        YLen = 4;
                    }
                    if ((code2 = YLen <= 3 ? code2.substring(0, 4 - YLen) + code1.substring(0, YLen) : new String("0000").substring(0, 4 - YLen) + code1.substring(0, YLen)).length() > 4) {
                        code2 = code2.substring(0, 4);
                    }
                    if ((code1 = code1.substring(YLen, code1.length())).length() > 4) {
                        code1 = code1.substring(code1.length() - 4, code1.length() - 4 + 4);
                    }
                    if (code1.equals("")) {
                        code1 = "1";
                    }
                    code1 = new String("0000").substring(0, 4 - code1.length()) + code1;
                }
                code1 = code2 + sqType + code1;
                NBRolPeriod nbRolPeriod = new NBRolPeriod();
                nbRolPeriod.setNBPCode((String)nbCodeList.get(index));
                nbRolPeriod.setNBPTitle(nameList.get(index));
                nbRolPeriod.setRolPCode(code1);
                nbRolPeriod.setRolPTitle(nameList.get(index));
                codeList.put(code1, nbRolPeriod);
                ++index;
            }
        }
        finally {
            this.taskYear = oldTaskYear;
        }
    }

    public String getParaDir() {
        return this.taskDir + "PARA" + File.separatorChar;
    }

    public String getTaskDir() {
        return this.taskDir;
    }

    private String getSQGS() throws IOException, StreamException {
        String RolSetFile = this.getParaDir() + "NBRolSet.ini";
        File aFile = new File(RolSetFile);
        if (!aFile.exists()) {
            return "";
        }
        Ini ini = new Ini();
        ini.loadIniFile(RolSetFile);
        String v = ini.ReadString("Period", "Set", "");
        return v;
    }

    private String getSQType() throws IOException, StreamException {
        String RolSetFile = this.getParaDir() + "NBRolSet.ini";
        File aFile = new File(RolSetFile);
        if (!aFile.exists()) {
            return "";
        }
        Ini ini = new Ini();
        ini.loadIniFile(RolSetFile);
        String v = ini.ReadString("Task", "SQType", "");
        return v;
    }

    public final void AddJQTFile(JQTFileMap jqtFile) {
        this.jqtList.put(jqtFile.getCode(), jqtFile);
    }

    public final Map<String, JQTFileMap> getJqtFileMaps() {
        return this.jqtList;
    }

    public final JQTFileMap getJqtFileMap(String code) {
        JQTFileMap jqtFile = null;
        if (this.jqtList.containsKey(code)) {
            jqtFile = this.jqtList.get(code);
        } else {
            jqtFile = new JQTFileMap();
            jqtFile.setCode(code);
        }
        return jqtFile;
    }

    public final String getPeriodField() {
        FMRepInfo repInfo = this.getFmRepInfo();
        if (repInfo == null) {
            return "";
        }
        return repInfo.getSQField();
    }

    public ReportDataImpl getFMReportData() throws Exception {
        FMRepInfo repInfo = this.getFmRepInfo();
        if (repInfo == null) {
            throw new Exception("\u8bf7\u68c0\u67e5\u53c2\u6570\uff0c\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868");
        }
        Grid2Data fmGrid = new Grid2Data();
        List<String> fmZBs = this.getFmzbList();
        Map<String, ZBInfo> fmZBInfoList = repInfo.getDefs().getAllZbInfosPair();
        fmGrid.setColumnCount(3);
        fmGrid.setRowCount(fmZBs.size() + 1);
        fmGrid.setColumnHidden(0, true);
        fmGrid.setRowHidden(0, true);
        fmGrid.setColumnWidth(1, repInfo.getColWidth());
        fmGrid.setColumnWidth(2, repInfo.getColWidth());
        fmGrid.setColumnAutoWidth(1, true);
        int curRow = 1;
        for (int i = 0; i < fmZBs.size(); ++i) {
            String fieldName = fmZBs.get(i);
            ZBInfo zbInfo = fmZBInfoList.get(fieldName);
            if (zbInfo == null) {
                logger.info("\u5c01\u9762\u4ee3\u7801\u754c\u9762\u6307\u6807\u4e0d\u5b58\u5728\uff1a" + fieldName);
                continue;
            }
            if (null != zbInfo.getEnumInfo() && StringUtils.isNotEmpty((String)zbInfo.getEnumInfo().getEnumIdenty())) {
                zbInfo.getEnumInfo().setShowValueType((byte)2);
            }
            GridCellData gridCell = fmGrid.getGridCellData(1, curRow);
            gridCell.setShowText(zbInfo.getZbTitle());
            gridCell.setEditText(zbInfo.getZbTitle());
            gridCell.setSilverHead(true);
            gridCell.setBottomBorderStyle(1);
            gridCell.setRightBorderStyle(1);
            gridCell.setEditable(false);
            int fontSize = 11;
            gridCell.setPersistenceData("fontSize", String.valueOf(fontSize));
            int pixelValue = Math.round((float)fontSize * 96.0f / 72.0f);
            gridCell.setFontSize(pixelValue);
            gridCell.setForeGroundColor(0);
            GridCellData gridCell2 = fmGrid.getGridCellData(2, curRow);
            gridCell2.setSilverHead(false);
            gridCell2.setBottomBorderStyle(1);
            gridCell2.setRightBorderStyle(1);
            gridCell2.setEditable(true);
            fontSize = 11;
            gridCell2.setPersistenceData("fontSize", String.valueOf(fontSize));
            pixelValue = Math.round((float)fontSize * 96.0f / 72.0f);
            gridCell2.setFontSize(pixelValue);
            gridCell2.setForeGroundColor(0);
            int[] gridPos = zbInfo.getGridPos();
            gridPos[0] = 2;
            gridPos[1] = curRow++;
            zbInfo.setGridPos(gridPos);
        }
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)fmGrid));
        repInfo.setReportData(dataImpl);
        dataImpl.setColCount(fmGrid.getColumnCount());
        dataImpl.setRowCount(fmGrid.getRowCount());
        repInfo.setColCount(fmGrid.getColumnCount());
        repInfo.setRowCount(fmGrid.getRowCount());
        return dataImpl;
    }

    public ReportDataImpl getFJReportData(RepInfo repInfo) throws Exception {
        if (repInfo == null) {
            throw new Exception("\u8bf7\u68c0\u67e5\u53c2\u6570\uff0c\u6ca1\u6709\u9644\u4ef6\u4fe1\u606f\u8868\u6216\u6587\u5b57\u4fe1\u606f\u8868");
        }
        String fjFieldName = "";
        String fjFieldTile = "";
        String oldFieldTile = "";
        if (repInfo.getTableType() == ReportTableType.RTT_BLOBTABLE) {
            fjFieldName = "FJFIELD";
            fjFieldTile = repInfo.getTitle();
            oldFieldTile = "\u9644\u4ef6\u4fe1\u606f\u8868\u6307\u6807";
        } else if (repInfo.getTableType() == ReportTableType.RTT_WORDTABLE) {
            fjFieldName = "WZFIELD";
            fjFieldTile = repInfo.getTitle();
            oldFieldTile = "\u6587\u5b57\u4fe1\u606f\u8868\u6307\u6807";
        } else {
            throw new Exception("\u8bf7\u68c0\u67e5\u53c2\u6570\uff0c\u6ca1\u6709\u9644\u4ef6\u4fe1\u606f\u8868\u6216\u6587\u5b57\u4fe1\u606f\u8868");
        }
        if (!repInfo.getDefs().getZbInfosPair().containsKey(fjFieldName)) {
            repInfo.getDefs().clearDatas();
            ZBInfo zb = new ZBInfo();
            zb.setFieldName(fjFieldName);
            zb.setDataType(ZBDataType.ATTATCHMENT);
            zb.setDecimal((byte)0);
            zb.setLength(5000);
            zb.setGridPos(new int[]{1, 2});
            zb.setNumPos(new int[]{1, 1});
            zb.setZbTitle(fjFieldTile);
            repInfo.getDefs().getZbs().add(zb);
        }
        Grid2Data fmGrid = new Grid2Data();
        List<ZBInfo> fmZBs = repInfo.getDefs().getZbsNoZDM();
        Map<String, ZBInfo> fmZBInfoList = repInfo.getDefs().getAllZbInfosPair();
        fmGrid.setColumnCount(3);
        fmGrid.setRowCount(fmZBs.size() + 1);
        fmGrid.setColumnHidden(0, true);
        fmGrid.setRowHidden(0, true);
        fmGrid.setColumnWidth(1, 300);
        fmGrid.setColumnWidth(2, 300);
        fmGrid.setColumnAutoWidth(1, true);
        int curRow = 1;
        for (int i = 0; i < fmZBs.size(); ++i) {
            ZBInfo zbInfo = fmZBs.get(i);
            String fieldName = zbInfo.getFieldName();
            GridCellData gridCell = fmGrid.getGridCellData(1, curRow);
            if (fieldName.equalsIgnoreCase("FJFIELD") || fieldName.equalsIgnoreCase("WZFIELD")) {
                gridCell.setShowText(oldFieldTile);
                gridCell.setEditText(oldFieldTile);
            } else {
                gridCell.setShowText(zbInfo.getZbTitle());
                gridCell.setEditText(zbInfo.getZbTitle());
            }
            gridCell.setSilverHead(true);
            gridCell.setBottomBorderColor(0x3F3F3F);
            gridCell.setBottomBorderStyle(1);
            gridCell.setRightBorderColor(0x3F3F3F);
            gridCell.setRightBorderStyle(1);
            gridCell.setEditable(false);
            int fontSize = 11;
            gridCell.setPersistenceData("fontSize", String.valueOf(fontSize));
            int pixelValue = Math.round((float)fontSize * 96.0f / 72.0f);
            gridCell.setFontSize(pixelValue);
            gridCell.setForeGroundColor(0);
            GridCellData gridCell2 = fmGrid.getGridCellData(2, curRow);
            gridCell2.setSilverHead(false);
            gridCell2.setBottomBorderColor(0x3F3F3F);
            gridCell2.setBottomBorderStyle(1);
            gridCell2.setRightBorderColor(0x3F3F3F);
            gridCell2.setRightBorderStyle(1);
            gridCell2.setEditable(true);
            fontSize = 11;
            gridCell2.setPersistenceData("fontSize", String.valueOf(fontSize));
            pixelValue = Math.round((float)fontSize * 96.0f / 72.0f);
            gridCell2.setFontSize(pixelValue);
            gridCell2.setForeGroundColor(0);
            int[] gridPos = zbInfo.getGridPos();
            if (null != gridPos) {
                gridPos[0] = 2;
                gridPos[1] = curRow;
                zbInfo.setGridPos(gridPos);
            }
            ++curRow;
        }
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)fmGrid));
        repInfo.setReportData(dataImpl);
        dataImpl.setColCount(fmGrid.getColumnCount());
        dataImpl.setRowCount(fmGrid.getRowCount());
        repInfo.setColCount(fmGrid.getColumnCount());
        repInfo.setRowCount(fmGrid.getRowCount());
        return dataImpl;
    }

    public boolean isMulPage() {
        return this.mulPage;
    }

    public void setMulPage(boolean mulPage) {
        this.mulPage = mulPage;
    }

    public String getCurGroupName() {
        return this.curGroupName;
    }

    public void setCurGroupName(String curGroupName) {
        this.curGroupName = curGroupName;
    }

    public String getFloatOrderField() {
        return this.floatOrderField;
    }

    public void setFloatOrderField(String floatOrderField) {
        this.floatOrderField = floatOrderField;
    }

    public AnalParaInfo getAnalInfo() {
        if (this.analInfo == null) {
            this.analInfo = new AnalParaInfo();
            ParaInfo analParaInfo = new ParaInfo(this.getTaskDir());
            analParaInfo.setParaType(1);
            this.analInfo.setBaseParaInfo(this);
            this.analInfo.setAnalParaInfo(analParaInfo);
        }
        return this.analInfo;
    }

    public void setAnalInfo(AnalParaInfo analInfo) {
        this.analInfo = analInfo;
    }

    public AnalParaInfo getInnerAnalInfo() {
        if (this.innerAnalInfo == null) {
            this.innerAnalInfo = new AnalParaInfo();
            ParaInfo analParaInfo = new ParaInfo(this.getTaskDir());
            analParaInfo.setParaType(1);
            this.innerAnalInfo.setBaseParaInfo(this);
            this.innerAnalInfo.setAnalParaInfo(analParaInfo);
        }
        return this.innerAnalInfo;
    }

    public void setInnerAnalInfo(AnalParaInfo innerAnalInfo) {
        this.innerAnalInfo = innerAnalInfo;
    }

    public int getParaType() {
        return this.paraType;
    }

    public void setParaType(int paraType) {
        this.paraType = paraType;
    }

    public List<FormulaVariableInfo> getFormulaVariables() {
        if (this.formulaVariables == null) {
            this.formulaVariables = new ArrayList<FormulaVariableInfo>();
        }
        return this.formulaVariables;
    }

    public void setFormulaVariables(List<FormulaVariableInfo> formulaVariables) {
        this.formulaVariables = formulaVariables;
    }

    public boolean isValueEmptyByEnumCtrl() {
        return this.ValueEmptyByEnumCtrl;
    }

    public void setValueEmptyByEnumCtrl(boolean valueEmptyByEnumCtrl) {
        this.ValueEmptyByEnumCtrl = valueEmptyByEnumCtrl;
    }

    public boolean isIniFieldMap() {
        return this.iniFieldMap;
    }

    public void setIniFieldMap(boolean iniFieldMap) {
        this.iniFieldMap = iniFieldMap;
    }
}

