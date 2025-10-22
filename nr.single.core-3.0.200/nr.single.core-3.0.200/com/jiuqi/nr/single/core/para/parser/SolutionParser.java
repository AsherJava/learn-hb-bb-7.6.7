/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionParser {
    private static final Logger logger = LoggerFactory.getLogger(SolutionParser.class);
    private String dirName;

    public void InitDirName(String dirName) {
        this.dirName = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws StreamException {
        String file = this.dirName + "TASKSIGN.TSK";
        Ini ini = null;
        try {
            ini = new Ini();
            ini.loadIniFile(file);
            this.parseTaskInfo(paraInfo, ini);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        String floatOrderFile = this.dirName + "PARA" + File.separatorChar + "FloatOrderCtrl.dat";
        File file1 = new File(FilenameUtils.normalize(floatOrderFile));
        if (file1.exists()) {
            paraInfo.setFloatOrderField("SYS_ORDER");
        }
    }

    public final void parseTaskInfo(ParaInfo paraInfo, Ini ini) {
        String taskName = ini.ReadString("General", "Name", "");
        String taskFlag = ini.ReadString("General", "Flag", "");
        String fileFlag = ini.ReadString("General", "FileFlag", "");
        String taskYear = ini.ReadString("General", "Year", "");
        String taskPeriod = ini.ReadString("General", "Period", "N");
        String taskGroup = ini.ReadString("General", "Group", "");
        String taskTime = ini.ReadString("General", "Time", "");
        String taskVersion = ini.ReadString("General", "Version", "");
        paraInfo.setTaskName(taskName);
        paraInfo.setPrefix(taskFlag);
        paraInfo.setFileFlag(fileFlag);
        paraInfo.setTaskYear(taskYear);
        paraInfo.setTaskType(taskPeriod);
        paraInfo.setTaskGroup(taskGroup);
        paraInfo.setTime(taskTime);
        paraInfo.setVersion(taskVersion);
    }
}

