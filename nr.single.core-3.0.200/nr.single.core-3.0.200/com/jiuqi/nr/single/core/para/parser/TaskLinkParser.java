/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskLinkParser {
    private static final Logger logger = LoggerFactory.getLogger(TaskLinkParser.class);
    private String dirName;

    public void InitDirName(String dirName) {
        this.dirName = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws StreamException {
        String file = this.dirName + "PARA" + File.separatorChar + "TaskLink.INI";
        Ini ini = null;
        try {
            paraInfo.getTaskLinkList().clear();
            ini = new Ini();
            ini.loadIniFile(file);
            for (int i = 1; i <= 100; ++i) {
                String taskPath = ini.ReadString("LinkTasks", String.valueOf(i), "");
                String taskCurDb = ini.ReadString("LinkTasks", String.valueOf(i) + "_DB", "");
                String taskCurFomula = ini.ReadString("LinkTasks", String.valueOf(i) + "_Cur", "");
                String taskLinkFomula = ini.ReadString("LinkTasks", String.valueOf(i) + "_Map", "");
                String taskLinkPeriod = ini.ReadString("LinkTasks", String.valueOf(i) + "_LinkPeriod", "");
                String taskLinkFlag = ini.ReadString("LinkTasks", String.valueOf(i) + "_Flag", "");
                String taskLinkTips = ini.ReadString("LinkTasks", String.valueOf(i) + "_Tips", "");
                if (taskLinkFlag.isEmpty()) continue;
                LinkTaskBean linkTask = new LinkTaskBean();
                linkTask.setTaskPath(taskPath);
                linkTask.setTaskDB(taskCurDb);
                linkTask.setCurFomula(taskCurFomula);
                linkTask.setLinkFomula(taskLinkFomula);
                linkTask.setLinkPeriod(taskLinkPeriod);
                linkTask.setLinkTaskFlag(taskLinkFlag);
                linkTask.setLinkTips(taskLinkTips);
                linkTask.setLinkNumber(String.valueOf(i));
                paraInfo.getTaskLinkList().add(linkTask);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

