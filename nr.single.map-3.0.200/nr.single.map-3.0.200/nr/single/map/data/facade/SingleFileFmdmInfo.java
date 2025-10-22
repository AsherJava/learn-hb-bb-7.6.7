/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;

@JsonDeserialize(as=SingleFileFmdmInfoImpl.class)
public interface SingleFileFmdmInfo
extends SingleFileTableInfo {
    public List<String> getZdmFieldCodes();

    public void setZdmFieldCodes(List<String> var1);

    public int getZdmLength();

    public String getBBLXField();

    public String getDWDMField();

    public String getDWMCField();

    public String getXJHSField();

    public String getSJDMField();

    public String getZBDMField();

    public String getSNDMField();

    public String getXBYSField();

    public String getPeriodField();

    public String getLevelField();

    public void setZdmLength(int var1);

    public void setBBLXField(String var1);

    public void setDWDMField(String var1);

    public void setDWMCField(String var1);

    public void setXJHSField(String var1);

    public void setSJDMField(String var1);

    public void setZBDMField(String var1);

    public void setSNDMField(String var1);

    public void setXBYSField(String var1);

    public void setPeriodField(String var1);

    public void setLevelField(String var1);
}

