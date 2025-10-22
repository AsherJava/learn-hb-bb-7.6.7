/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;

@JsonDeserialize(as=SingleFileTaskInfoImpl.class)
public interface SingleFileTaskInfo
extends Serializable {
    public String getSingleTaskFlag();

    public void setSingleTaskFlag(String var1);

    public String getSingleTaskTitle();

    public void setSingleTaskTitle(String var1);

    public String getSingleFileFlag();

    public void setSingleFileFlag(String var1);

    public String getSingleTaskYear();

    public void setSingleTaskYear(String var1);

    public String getSingleTaskPeriod();

    public void setSingleTaskPeriod(String var1);

    public String getSingleTaskTime();

    public void setSingleTaskTime(String var1);

    public String getSingleFloatOrderFiled();

    public void setSingleFloatOrderField(String var1);

    public String getNetTaskKey();

    public void setNetTaskKey(String var1);

    public String getNetTaskFlag();

    public void setNetTaskFlag(String var1);

    public String getNetTaskTitle();

    public void setNetTaskTitle(String var1);

    public String getNetFormSchemeKey();

    public void setNetFormSchemeKey(String var1);

    public String getNetFormSchemeFlag();

    public void setNetFormSchemeFlag(String var1);

    public String getNetFormSchemeTitle();

    public void setNetFormSchemeTitle(String var1);

    public String getParaVersion();

    public void setParaVersion(String var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);

    public String getUploadFileName();

    public void setUploadFileName(String var1);

    public void copyFrom(SingleFileTaskInfo var1);
}

