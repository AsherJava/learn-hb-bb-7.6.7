/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileTableInfoImpl;

public class SingleFileFmdmInfoImpl
extends SingleFileTableInfoImpl
implements SingleFileFmdmInfo {
    private static final long serialVersionUID = 7772780716421304740L;
    private List<String> zdmFieldCodes = new ArrayList<String>();
    private int pZdmLength;
    private String pBBLXField;
    private String pDWDMField;
    private String pDWMCField;
    private String pXJHSField;
    private String pSJDMField;
    private String pZBDMField;
    private String pSNDMField;
    private String pXBYSField;
    private String pPeriodField;
    private String pLevelField;

    @Override
    public List<String> getZdmFieldCodes() {
        return this.zdmFieldCodes;
    }

    @Override
    public void setZdmFieldCodes(List<String> zdmFieldCodes) {
        this.zdmFieldCodes = zdmFieldCodes;
    }

    @Override
    public int getZdmLength() {
        return this.pZdmLength;
    }

    @Override
    public void setZdmLength(int len) {
        this.pZdmLength = len;
    }

    @Override
    @JsonProperty(value="pBBLXField")
    public String getBBLXField() {
        return this.pBBLXField;
    }

    @Override
    @JsonProperty(value="pDWDMField")
    public String getDWDMField() {
        return this.pDWDMField;
    }

    @Override
    @JsonProperty(value="pDWMCField")
    public String getDWMCField() {
        return this.pDWMCField;
    }

    @Override
    @JsonProperty(value="pXJHSField")
    public String getXJHSField() {
        return this.pXJHSField;
    }

    @Override
    @JsonProperty(value="pSJDMField")
    public String getSJDMField() {
        return this.pSJDMField;
    }

    @Override
    @JsonProperty(value="pZBDMField")
    public String getZBDMField() {
        return this.pZBDMField;
    }

    @Override
    @JsonProperty(value="pSNDMField")
    public String getSNDMField() {
        return this.pSNDMField;
    }

    @Override
    @JsonProperty(value="pXBYSField")
    public String getXBYSField() {
        return this.pXBYSField;
    }

    @Override
    @JsonProperty(value="pPeriodField")
    public String getPeriodField() {
        return this.pPeriodField;
    }

    @Override
    @JsonProperty(value="pLevelField")
    public String getLevelField() {
        return this.pLevelField;
    }

    @Override
    @JsonProperty(value="pBBLXField")
    public void setBBLXField(String field) {
        this.pBBLXField = field;
    }

    @Override
    @JsonProperty(value="pDWDMField")
    public void setDWDMField(String field) {
        this.pDWDMField = field;
    }

    @Override
    @JsonProperty(value="pDWMCField")
    public void setDWMCField(String field) {
        this.pDWMCField = field;
    }

    @Override
    @JsonProperty(value="pXJHSField")
    public void setXJHSField(String field) {
        this.pXJHSField = field;
    }

    @Override
    @JsonProperty(value="pSJDMField")
    public void setSJDMField(String field) {
        this.pSJDMField = field;
    }

    @Override
    @JsonProperty(value="pZBDMField")
    public void setZBDMField(String field) {
        this.pZBDMField = field;
    }

    @Override
    @JsonProperty(value="pSNDMField")
    public void setSNDMField(String field) {
        this.pSNDMField = field;
    }

    @Override
    @JsonProperty(value="pXBYSField")
    public void setXBYSField(String field) {
        this.pXBYSField = field;
    }

    @Override
    @JsonProperty(value="pPeriodField")
    public void setPeriodField(String field) {
        this.pPeriodField = field;
    }

    @Override
    @JsonProperty(value="pLevelField")
    public void setLevelField(String field) {
        this.pLevelField = field;
    }

    @Override
    public void copyFrom(SingleFileTableInfo info) {
        super.copyFrom(info);
        SingleFileFmdmInfo fmInfo = (SingleFileFmdmInfo)info;
        this.pZdmLength = fmInfo.getZdmLength();
        this.pBBLXField = fmInfo.getBBLXField();
        this.pDWDMField = fmInfo.getDWDMField();
        this.pDWMCField = fmInfo.getDWMCField();
        this.pXJHSField = fmInfo.getXJHSField();
        this.pSJDMField = fmInfo.getSJDMField();
        this.pZBDMField = fmInfo.getZBDMField();
        this.pSNDMField = fmInfo.getSNDMField();
        this.pXBYSField = fmInfo.getXBYSField();
        this.pPeriodField = fmInfo.getPeriodField();
        this.pLevelField = fmInfo.getLevelField();
        this.zdmFieldCodes.clear();
        for (String code : fmInfo.getZdmFieldCodes()) {
            this.zdmFieldCodes.add(code);
        }
    }
}

