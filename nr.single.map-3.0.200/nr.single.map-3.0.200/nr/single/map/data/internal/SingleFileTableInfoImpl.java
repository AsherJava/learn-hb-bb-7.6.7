/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileRegionInfoImpl;

public class SingleFileTableInfoImpl
implements SingleFileTableInfo {
    private static final long serialVersionUID = 672896799648716010L;
    private String singleTableCode;
    private String singleTableTitle;
    private int singleTableType;
    private String netFormCode;
    private String netFormKey;
    private SingleFileRegionInfo region = new SingleFileRegionInfoImpl();

    @Override
    public String getSingleTableCode() {
        return this.singleTableCode;
    }

    @Override
    public void setSingleTableCode(String singleTableCode) {
        this.singleTableCode = singleTableCode;
    }

    @Override
    public String getSingleTableTitle() {
        return this.singleTableTitle;
    }

    @Override
    public void setSingleTableTitle(String singleTableTitle) {
        this.singleTableTitle = singleTableTitle;
    }

    @Override
    @JsonProperty(value="region")
    public SingleFileRegionInfo getRegion() {
        return this.region;
    }

    @Override
    public String getNetFormCode() {
        return this.netFormCode;
    }

    @Override
    public void setNetFormCode(String formCode) {
        this.netFormCode = formCode;
    }

    @Override
    public String getNetFormKey() {
        return this.netFormKey;
    }

    @Override
    public void setNetFormKey(String formKey) {
        this.netFormKey = formKey;
    }

    @Override
    public void setRegion(SingleFileRegionInfo region) {
        this.region = region;
    }

    @Override
    public void copyFrom(SingleFileTableInfo info) {
        this.singleTableCode = info.getSingleTableCode();
        this.singleTableTitle = info.getSingleTableTitle();
        this.netFormCode = info.getNetFormCode();
        this.netFormKey = info.getNetFormKey();
        this.singleTableType = info.getSingleTableType();
        this.getRegion().copyFrom(info.getRegion());
    }

    @Override
    public int getSingleTableType() {
        return this.singleTableType;
    }

    @Override
    public void setSingleTableType(int Tabletype) {
        this.singleTableType = Tabletype;
    }
}

