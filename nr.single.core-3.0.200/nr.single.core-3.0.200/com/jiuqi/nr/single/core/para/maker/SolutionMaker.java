/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.maker;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionMaker {
    private static final Logger logger = LoggerFactory.getLogger(SolutionMaker.class);
    private String dirName;

    public void initDirName(String dirName) {
        this.dirName = dirName;
    }

    public final void make(ParaInfo paraInfo) throws StreamException {
        String file = this.dirName + "TASKSIGN.TSK";
        Ini ini = null;
        try {
            ini = new Ini();
            ini.WriteString("General", "Name", paraInfo.getTaskName());
            ini.WriteString("General", "Flag", paraInfo.getPrefix());
            ini.WriteString("General", "FileFlag", paraInfo.getFileFlag());
            ini.WriteString("General", "Year", paraInfo.getTaskYear());
            ini.WriteString("General", "Period", paraInfo.getPeriodField());
            ini.WriteString("General", "Group", paraInfo.getTaskGroup());
            ini.WriteString("General", "Time", paraInfo.getTaskTime());
            ini.WriteString("General", "Version", paraInfo.getTaskVerion());
            ini.saveIni(file);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

