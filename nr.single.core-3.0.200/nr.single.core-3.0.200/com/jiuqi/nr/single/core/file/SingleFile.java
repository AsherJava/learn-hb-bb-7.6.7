/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 */
package com.jiuqi.nr.single.core.file;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFileHead;
import com.jiuqi.nr.single.core.file.SingleFileInfoContain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public interface SingleFile {
    public SingleFileInfoContain getInfo();

    public SingleFileHead getHead();

    public String getSourceFile();

    public void setSourceFile(String var1);

    public String getDestFile();

    public void setDestFile(String var1);

    public String getUsageSign();

    public void setUsageSign(String var1);

    public String getJobName();

    public void setJobName(String var1);

    public boolean getHavePW();

    public void setHavePW(boolean var1);

    public String getPW();

    public void setPW(String var1);

    public boolean getCanceled();

    public void setCanceled(boolean var1);

    public long getJobID();

    public void makeJio(String var1, String var2) throws SingleFileException;

    public void makeJio(FileInputStream var1, FileOutputStream var2) throws SingleFileException;

    public void unMakeJio(String var1, String var2) throws SingleFileException;

    public void unMakeJio(FileInputStream var1, FileOutputStream var2) throws SingleFileException;

    public void writeTaskSign(String var1) throws SingleFileException;

    public void readHead(FileInputStream var1) throws SingleFileException;

    public void readHead(MemStream var1, SingleFileHead var2) throws SingleFileException;

    public void writeHead(FileOutputStream var1) throws SingleFileException;

    public byte[] getStringData(String var1, int var2) throws SingleFileException;

    public void infoLoad(FileInputStream var1) throws SingleFileException;

    public void infoLoad(String var1) throws SingleFileException;

    public void readHead(FileInputStream var1, SingleFileHead var2) throws SingleFileException;

    public void infoLoad(byte[] var1) throws SingleFileException;

    public List<InOutDataType> getInOutData();

    public void setInOutData(List<InOutDataType> var1);

    public String getTaskName();

    public void setTaskName(String var1);

    public String getTaskFlag();

    public void setTaskFlag(String var1);

    public String getFileFlag();

    public void setFileFlag(String var1);

    public String getTaskYear();

    public void setTaskYear(String var1);

    public String getTaskPeriod();

    public void setTaskPeriod(String var1);

    public String getTaskTime();

    public void setTaskTime(String var1);

    public String getTaskVersion();

    public void setTaskVersion(String var1);

    public String getTaskGroup();

    public void setTaskGroup(String var1);

    public boolean isInputClien();

    public void setInputClien(boolean var1);

    public String getNetPeriodT();

    public void setNetPeriodT(String var1);
}

