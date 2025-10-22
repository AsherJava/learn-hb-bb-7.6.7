/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.util.FloatOrderCalc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;

@ApiModel(value="FloatRegionSaveStructure", description="\u6d6e\u52a8\u533a\u57df\u4fdd\u5b58\u65f6\u53c2\u6570")
public class FloatRegionSaveStructure {
    @ApiModelProperty(value="\u4fdd\u5b58\u6570\u636e", name="saveDataBuilder")
    private SaveDataBuilder saveDataBuilder;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e", name="region")
    private RegionData region;
    @ApiModelProperty(value="\u5f53\u524d\u7ef4\u5ea6", name="dimensionValueSet")
    private DimensionValueSet dimensionValueSet;
    @ApiModelProperty(value="\u4e1a\u52a1\u4e3b\u952e\u6307\u6807\u96c6\u5408", name="bizKeyOrderFields")
    private List<FieldData> bizKeyOrderFields;
    @ApiModelProperty(value="\u4e1a\u52a1\u4e4b\u95f4\u5b57\u7b26\u4e32\u4e0e\u884cid\u7684\u5bf9\u5e94", name="bizKeyToRowIdMap")
    private Map<String, String> bizKeyToRowIdMap;
    @ApiModelProperty(value="\u5f53\u524d\u533a\u57df\u7684\u6240\u6709\u94fe\u63a5\u53c2\u6570", name="regionMetaDatas")
    private List<MetaData> regionMetaDatas;
    @ApiModelProperty(value="\u5f53\u524d\u533a\u57df\u5173\u7cfb", name="regionRelation")
    private RegionRelation regionRelation;
    @ApiModelProperty(value="floatOrder\u8ba1\u7b97", name="floatOrderCalc")
    private FloatOrderCalc floatOrderCalc;
    @ApiModelProperty(value="\u4fdd\u5b58\u65f6\u63d0\u4ea4\u7684\u884c\u7d22\u5f15", name="rowIndex")
    private Integer rowIndex;
    @ApiModelProperty(value="\u884c\u7d22\u5f15\u4e0e\u884cid\u7684\u5bf9\u5e94", name="rowIndexToRowIdMap")
    private Map<Integer, String> rowIndexToRowIdMap;

    public SaveDataBuilder getSaveDataBuilder() {
        return this.saveDataBuilder;
    }

    public void setSaveDataBuilder(SaveDataBuilder saveDataBuilder) {
        this.saveDataBuilder = saveDataBuilder;
    }

    public RegionData getRegion() {
        return this.region;
    }

    public void setRegion(RegionData region) {
        this.region = region;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public List<FieldData> getBizKeyOrderFields() {
        return this.bizKeyOrderFields;
    }

    public void setBizKeyOrderFields(List<FieldData> bizKeyOrderFields) {
        this.bizKeyOrderFields = bizKeyOrderFields;
    }

    public Map<String, String> getBizKeyToRowIdMap() {
        return this.bizKeyToRowIdMap;
    }

    public void setBizKeyToRowIdMap(Map<String, String> bizKeyToRowIdMap) {
        this.bizKeyToRowIdMap = bizKeyToRowIdMap;
    }

    public List<MetaData> getRegionMetaDatas() {
        return this.regionMetaDatas;
    }

    public void setRegionMetaDatas(List<MetaData> regionMetaDatas) {
        this.regionMetaDatas = regionMetaDatas;
    }

    public RegionRelation getRegionRelation() {
        return this.regionRelation;
    }

    public void setRegionRelation(RegionRelation regionRelation) {
        this.regionRelation = regionRelation;
    }

    public FloatOrderCalc getFloatOrderCalc() {
        return this.floatOrderCalc;
    }

    public void setFloatOrderCalc(FloatOrderCalc floatOrderCalc) {
        this.floatOrderCalc = floatOrderCalc;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Map<Integer, String> getRowIndexToRowIdMap() {
        return this.rowIndexToRowIdMap;
    }

    public void setRowIndexToRowIdMap(Map<Integer, String> rowIndexToRowIdMap) {
        this.rowIndexToRowIdMap = rowIndexToRowIdMap;
    }
}

