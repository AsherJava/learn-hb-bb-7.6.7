/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIdentityInfo
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.ObjectIdGenerators$IntSequenceGenerator
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class SingleFileRegionInfoImpl
implements SingleFileRegionInfo {
    private static final long serialVersionUID = -3810806727110869922L;
    private List<SingleFileFieldInfo> feilds = new ArrayList<SingleFileFieldInfo>();
    private List<SingleFileRegionInfo> subRegions = new ArrayList<SingleFileRegionInfo>();
    private boolean isFixTable;
    private List<String> floatCodes = new ArrayList<String>();
    private int floatingIndex = -1;
    private String netRegionKey;

    @Override
    @JsonProperty(value="feilds")
    public List<SingleFileFieldInfo> getFields() {
        return this.feilds;
    }

    @Override
    public void setFeilds(List<SingleFileFieldInfo> feilds) {
        this.feilds = feilds;
    }

    @Override
    public List<SingleFileRegionInfo> getSubRegions() {
        return this.subRegions;
    }

    @Override
    public void setSubRegions(List<SingleFileRegionInfo> subRegions) {
        this.subRegions = subRegions;
    }

    @Override
    @JsonProperty(value="isFixTable")
    public boolean getIsFixTable() {
        return this.isFixTable;
    }

    @Override
    public void setFixTable(boolean fixTable) {
        this.isFixTable = fixTable;
    }

    @Override
    public List<String> getFloatCodes() {
        return this.floatCodes;
    }

    @Override
    public void setFloatCodes(List<String> floatCodes) {
        this.floatCodes = floatCodes;
    }

    @Override
    public int getFloatingIndex() {
        return this.floatingIndex;
    }

    @Override
    public void setFloatingIndex(int floatingIndex) {
        this.floatingIndex = floatingIndex;
    }

    @Override
    public String getNetRegionKey() {
        return this.netRegionKey;
    }

    @Override
    public void setNetRegionKey(String regionKey) {
        this.netRegionKey = regionKey;
    }

    @Override
    @JsonIgnore
    public SingleFileFieldInfo getNewField() {
        return new SingleFileFieldInfoImpl();
    }

    @Override
    @JsonIgnore
    public SingleFileRegionInfo getNewSubRegion() {
        return new SingleFileRegionInfoImpl();
    }

    @Override
    public void copyFrom(SingleFileRegionInfo info) {
        this.isFixTable = info.getIsFixTable();
        this.floatingIndex = info.getFloatingIndex();
        this.netRegionKey = info.getNetRegionKey();
        this.floatCodes.clear();
        for (String code : info.getFloatCodes()) {
            this.floatCodes.add(code);
        }
        this.getFields().clear();
        for (SingleFileFieldInfo field : info.getFields()) {
            SingleFileFieldInfoImpl fieldNew = new SingleFileFieldInfoImpl();
            fieldNew.copyFrom(field);
            this.getFields().add(fieldNew);
        }
        this.subRegions.clear();
        for (SingleFileRegionInfo sub : info.getSubRegions()) {
            SingleFileRegionInfoImpl subNew = new SingleFileRegionInfoImpl();
            subNew.copyFrom(sub);
            this.subRegions.add(subNew);
        }
    }
}

