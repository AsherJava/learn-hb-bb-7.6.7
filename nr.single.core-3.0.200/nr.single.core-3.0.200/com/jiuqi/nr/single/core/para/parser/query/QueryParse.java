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
package com.jiuqi.nr.single.core.para.parser.query;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalCondition;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryParse {
    private static final Logger logger = LoggerFactory.getLogger(QueryParse.class);
    private String taskDir;
    private String Current;

    public final void InitDirName(String dirName) {
        this.taskDir = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws IOException, StreamException {
        Map<String, QueryModalInfo> modalList = this.InitQueryModalInfo(true);
        String sFile = "";
        for (QueryModalInfo modal : modalList.values()) {
            sFile = this.taskDir + "QUERY" + File.separatorChar + modal.getModalName() + ".CHX";
            File file = new File(FilenameUtils.normalize(sFile));
            if (!file.exists()) continue;
            JQTFileMap jqtFile = this.InitJQTFileMap(sFile);
            MemStream stream = new MemStream();
            stream.loadFromFile(sFile);
            stream.seek(0L, 0);
            modal.loadFromJQT((Stream)stream, jqtFile);
            Grid2Data gridData = this.GetJioGridDta(modal.getRepInfo());
            stream.seek(0L, 0);
            modal.loadPrint(gridData, (Stream)stream, jqtFile);
            stream.seek(0L, 0);
            modal.loadFormula((Stream)stream, jqtFile);
        }
        paraInfo.setHorzQueryModalList(modalList);
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
            logger.error(e.getMessage(), e);
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

    private Map<String, QueryModalInfo> InitQueryModalInfo(boolean isHorz) throws IOException, StreamException {
        LinkedHashMap<String, QueryModalInfo> result = new LinkedHashMap<String, QueryModalInfo>();
        String file = this.taskDir + "QUERY" + File.separatorChar + "QueryMdl.Lst";
        Ini ini = new Ini();
        ini.loadIniFile(file);
        String groupFile = this.taskDir + "PARA" + File.separatorChar + "QueryGrp.Lst";
        Ini groupIni = new Ini();
        groupIni.loadIniFile(groupFile);
        if (isHorz) {
            String secName = "Horz";
            String groupSecName = "HorzGroup";
            this.loadModalList(result, secName, groupSecName);
        } else {
            String secName = "Vert";
            String groupSecName = "VertGroup";
            this.loadModalList(result, secName, groupSecName);
        }
        return result;
    }

    private void loadModalList(Map<String, QueryModalInfo> modalList, String aSecName, String groupSecName) throws StreamException, IOException {
        String file = this.taskDir + "QUERY" + File.separatorChar + "QueryMdl.Lst";
        Ini ini = new Ini();
        ini.loadIniFile(file);
        String groupFile = this.taskDir + "PARA" + File.separatorChar + "QueryGrp.Lst";
        Ini groupIni = new Ini();
        groupIni.loadIniFile(groupFile);
        String countCode = ini.ReadString(aSecName, "Count", "0");
        if (StringUtils.isNotEmpty((String)countCode)) {
            int count = Integer.parseInt(countCode);
            for (int i = 0; i < count; ++i) {
                String ident = String.format("%1$s%2$s", "Item_", i);
                String value = ini.ReadString(aSecName, ident, "");
                if (StringUtils.isEmpty((String)value)) continue;
                QueryModalInfo modal = new QueryModalInfo();
                modal.setModalName(value);
                String group = groupIni.ReadString(groupSecName, ident, "");
                modal.setGroupNames(group);
                String aCondSecName = aSecName + "@" + modal.getModalName();
                this.loadModalCondition(modal, ini, aCondSecName);
                modalList.put(modal.getModalName(), modal);
            }
        }
    }

    private void loadModalCondition(QueryModalInfo modal, Ini ini, String secName) {
        ArrayList<QueryModalCondition> condList = new ArrayList<QueryModalCondition>();
        String countCode = ini.ReadString(secName, "Count", "0");
        String indexCode = ini.ReadString(secName, "Index", "0");
        if (StringUtils.isNotEmpty((String)countCode)) {
            int count = Integer.parseInt(countCode);
            for (int i = 0; i < count; ++i) {
                String bdsIdent = String.format("%1$s%2$s", "BDS", i);
                String smIdent = String.format("%1$s%2$s", "SM", i);
                String bdsValue = ini.ReadString(secName, bdsIdent, "");
                String smValue = ini.ReadString(secName, smIdent, "");
                QueryModalCondition cond = new QueryModalCondition();
                cond.setExpression(bdsValue);
                cond.setTitle(smValue);
                condList.add(cond);
            }
        }
        int condIndex = -1;
        if (StringUtils.isNotEmpty((String)indexCode)) {
            condIndex = Integer.parseInt(indexCode);
        }
        modal.setConditionList(condList);
        modal.setConditionIndex(condIndex);
    }
}

