/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintParse {
    private static final Logger log = LoggerFactory.getLogger(PrintParse.class);
    private String taskDir;
    private String Current;

    public final void InitDirName(String dirName) {
        this.taskDir = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws IOException, StreamException {
        List<String> printGroupList = this.IniPrintGroup();
        Map<String, RepInfo> repList = paraInfo.getRepInfoCodeList();
        String sFile = "";
        for (String printGroup : printGroupList) {
            if (printGroup.equals("\u9ed8\u8ba4\u6253\u5370\u65b9\u6848")) {
                HashMap<String, GridPrintMan> printMans = new HashMap<String, GridPrintMan>();
                Map<String, JQTFileMap> jqtList = paraInfo.getJqtFileMaps();
                Iterator<String> iterator = repList.keySet().iterator();
                while (iterator.hasNext()) {
                    String rep;
                    String tableFlag = rep = iterator.next();
                    sFile = this.taskDir + "PARA" + File.separatorChar + tableFlag + ".JQT";
                    File file = new File(FilenameUtils.normalize(sFile));
                    if (!file.exists()) continue;
                    JQTFileMap jqtFile = null;
                    if (jqtList.containsKey(tableFlag)) {
                        jqtFile = jqtList.get(tableFlag);
                    }
                    if (jqtFile == null) continue;
                    if (tableFlag.equals("FMDM")) {
                        sFile = this.taskDir + "PARA" + File.separatorChar + tableFlag + ".PRN";
                        File jqtFMDMFile = new File(FilenameUtils.normalize(sFile));
                        if (!jqtFMDMFile.exists()) continue;
                        JQTFileMap jqtFileFMDM = this.InitJQTFileMap(sFile);
                        Grid2Data gridData = null;
                        MemStream stream = new MemStream();
                        stream.loadFromFile(sFile);
                        GridPrintMan printMan = new GridPrintMan(gridData);
                        try {
                            printMan.LoadDefaultFMDMFromJQT((Stream)stream, jqtFileFMDM);
                        }
                        catch (Exception ex) {
                            log.error(tableFlag + "\u6253\u5370\u5b58\u5728\u95ee\u9898\uff1a" + ex.getMessage(), ex);
                        }
                        printMans.put(tableFlag, printMan);
                        continue;
                    }
                    RepInfo repInfo = repList.get(rep);
                    Grid2Data gridData = this.GetJioGrid2Data(repInfo);
                    MemStream stream = new MemStream();
                    stream.loadFromFile(sFile);
                    GridPrintMan printMan = new GridPrintMan(gridData);
                    try {
                        printMan.LoadFromJQT((Stream)stream, jqtFile);
                    }
                    catch (Exception ex) {
                        log.error(tableFlag + "\u6253\u5370\u5b58\u5728\u95ee\u9898\uff1a" + ex.getMessage(), ex);
                    }
                    printMans.put(tableFlag, printMan);
                }
                this.parseDefaultSchemeHZFMDM(paraInfo, printMans);
                paraInfo.AddPrintGroup(printGroup, printMans);
                continue;
            }
            this.parseNotDefaultScheme(paraInfo, printGroup);
        }
    }

    private void parseNotDefaultScheme(ParaInfo paraInfo, String schemeName) throws IOException, StreamException {
        Map<String, RepInfo> repList = paraInfo.getRepInfoCodeList();
        HashMap<String, GridPrintMan> printMans = new HashMap<String, GridPrintMan>();
        String sFile = paraInfo.getParaDir() + schemeName + ".PRN";
        File file = new File(FilenameUtils.normalize(sFile));
        if (!file.exists()) {
            log.info("\u89e3\u6790\u6253\u5370\uff1a" + schemeName + ":\u65b9\u6848 \u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
            return;
        }
        int nameIndexPtrStart = 68;
        MemStream is = new MemStream();
        is.loadFromFile(sFile);
        is.seek(0L, 0);
        ReadUtil.skipStream((Stream)is, nameIndexPtrStart);
        int[] decodeLoad = ReadUtil.decodeLoad((Stream)is, 2, 45890);
        is.seek(0L, 0);
        ReadUtil.skipStream((Stream)is, decodeLoad[0]);
        int count = ReadUtil.readIntValue((Stream)is);
        HashMap<String, int[]> map = new HashMap<String, int[]>();
        for (int i = 0; i < count; ++i) {
            String name = ReadUtil.readStreams((Stream)is);
            int[] pos = new int[]{ReadUtil.readIntValue((Stream)is), ReadUtil.readIntValue((Stream)is)};
            map.put(name, pos);
        }
        Iterator<String> iterator = repList.keySet().iterator();
        while (iterator.hasNext()) {
            String rep;
            String tableFlag = rep = iterator.next();
            this.parseNotDefaultSchemeTable(paraInfo, printMans, repList, tableFlag, is, map);
        }
        this.parseNotDefaultSchemeTable(paraInfo, printMans, repList, "SYS_HZFMDY", is, map);
        paraInfo.AddPrintGroup(schemeName, printMans);
    }

    public void parseDefaultSchemeHZFMDM(ParaInfo paraInfo, Map<String, GridPrintMan> printMans) throws StreamException, IOException {
        String sFile = this.taskDir + "PARA" + File.separatorChar + "HZFMDY.PRN";
        File file = new File(FilenameUtils.normalize(sFile));
        if (!file.exists()) {
            return;
        }
        JQTFileMap jqtFile = this.InitJQTFileMap(sFile);
        Grid2Data gridData = null;
        MemStream stream = new MemStream();
        stream.loadFromFile(sFile);
        GridPrintMan printMan = new GridPrintMan(gridData);
        try {
            printMan.LoadDefaultFMDMFromJQT((Stream)stream, jqtFile);
        }
        catch (Exception ex) {
            log.error("HZFMDY\u6c47\u603b\u5c01\u9762\u6253\u5370\u5b58\u5728\u95ee\u9898\uff1a" + ex.getMessage(), ex);
        }
        printMans.put("SYS_HZFMDY", printMan);
    }

    public void parseNotDefaultSchemeTable(ParaInfo paraInfo, Map<String, GridPrintMan> printMans, Map<String, RepInfo> repList, String tableFlag, MemStream is, Map<String, int[]> map) throws StreamException, IOException {
        if (map.size() == 0) {
            return;
        }
        int[] pos = map.get(tableFlag);
        if (pos == null || pos.length < 1) {
            return;
        }
        is.seek(0L, 0);
        ReadUtil.skipStream((Stream)is, pos[0]);
        if (tableFlag.equalsIgnoreCase("FMDM") || tableFlag.equalsIgnoreCase("SYS_HZFMDY")) {
            Grid2Data gridData = null;
            GridPrintMan printMan = new GridPrintMan(gridData);
            printMan.LoadNotDefaultFMDMFromStream((Stream)is);
            printMans.put(tableFlag, printMan);
        } else {
            RepInfo repInfo = repList.get(tableFlag);
            Grid2Data gridData = this.GetJioGrid2Data(repInfo);
            GridPrintMan printMan = new GridPrintMan(gridData);
            printMan.LoadFromStream((Stream)is);
            printMans.put(tableFlag, printMan);
        }
    }

    public JQTFileMap InitJQTFileMap(String filePath) throws StreamException, IOException {
        JQTFileMap jqtFileMap = new JQTFileMap();
        File jqtFile = new File(FilenameUtils.normalize(filePath));
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
            log.error(e.getMessage(), e);
        }
        return jqtFileMap;
    }

    private Grid2Data GetJioGridDta(RepInfo repInfo) {
        if (repInfo == null || repInfo.getReportData() == null) {
            return null;
        }
        Grid2Data data2 = Grid2Data.bytesToGrid((byte[])repInfo.getReportData().getGridBytes());
        return data2;
    }

    private Grid2Data GetJioGrid2Data(RepInfo repInfo) {
        if (repInfo == null || repInfo.getReportData() == null) {
            return null;
        }
        Grid2Data data2 = Grid2Data.bytesToGrid((byte[])repInfo.getReportData().getGridBytes());
        return data2;
    }

    private List<String> IniPrintGroup() throws IOException, StreamException {
        ArrayList<String> result = new ArrayList<String>();
        String file = this.taskDir + "PARA" + File.separatorChar + "ParamSet.Lst";
        Ini ini = new Ini();
        ini.loadIniFile(file);
        this.Current = ini.ReadString("PrtSettings", "Current", "");
        result.add("\u9ed8\u8ba4\u6253\u5370\u65b9\u6848");
        int count = Integer.parseInt(ini.ReadString("PrtSettings", "Count", "0"));
        for (int i = 0; i < count; ++i) {
            String ident = String.format("%1$s%2$s", "Item_", i);
            String value = ini.ReadString("PrtSettings", ident, "");
            if (StringUtils.isEmpty((String)value)) continue;
            result.add(value);
        }
        return result;
    }
}

