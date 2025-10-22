/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.jtable.params.base.IFormStructureData;
import com.jiuqi.nr.jtable.params.base.RegionSimpleData;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="FromGridData", description="\u62a5\u8868\u8868\u683c\u8868\u6837\u4e0e\u6620\u5c04\u53c2\u6570")
public class FromGridData
implements IFormStructureData {
    @ApiModelProperty(value="\u533a\u57df\u5217\u8868", name="regions")
    private List<RegionSimpleData> regions = new ArrayList<RegionSimpleData>();
    @ApiModelProperty(value="\u7f57\u5217\u57fa\u7840\u6570\u636e\uff08key:\u57fa\u7840\u6570\u636ekey;value:\u57fa\u7840\u6570\u636e\u5217\u8868\uff09", name="entityData")
    private Map<String, List<EntityData>> entityData = new HashMap<String, List<EntityData>>();
    @ApiModelProperty(value="\u8fd0\u7b97\u5355\u5143\u683cKey\u5217\u8868", name="calcDataLinks")
    private List<String> calcDataLinks = new ArrayList<String>();
    @ApiModelProperty(value="\u53d6\u6570\u5355\u5143\u683cKey\u5217\u8868", name="extractDataLinks")
    private List<ExtractCellInfo> extractDataLinks = new ArrayList<ExtractCellInfo>();
    @ApiModelProperty(value="\u516c\u5f0f\u5355\u5143\u683cKey\u5217\u8868", name="formulaDataLinks")
    private List<String> formulaDataLinks = new ArrayList<String>();
    @ApiModelProperty(value="\u884c\u5217\u6298\u53e0\u4fe1\u606f", name="rowFoldDataMap")
    private Map<String, Map<String, FormFoldingDefine>> formFoldData = new HashMap<String, Map<String, FormFoldingDefine>>();

    public List<RegionSimpleData> getRegions() {
        return this.regions;
    }

    public void setRegions(List<RegionSimpleData> regions) {
        this.regions = regions;
    }

    public Map<String, List<EntityData>> getEntityData() {
        return this.entityData;
    }

    public void setEntityData(Map<String, List<EntityData>> entityData) {
        this.entityData = entityData;
    }

    public List<String> getCalcDataLinks() {
        return this.calcDataLinks;
    }

    public void setCalcDataLinks(List<String> calcDataLinks) {
        this.calcDataLinks = calcDataLinks;
    }

    public List<ExtractCellInfo> getExtractDataLinks() {
        return this.extractDataLinks;
    }

    public void setExtractDataLinks(List<ExtractCellInfo> extractDataLinks) {
        this.extractDataLinks = extractDataLinks;
    }

    public List<String> getFormulaDataLinks() {
        return this.formulaDataLinks;
    }

    public void setFormulaDataLinks(List<String> formulaDataLinks) {
        this.formulaDataLinks = formulaDataLinks;
    }

    public Map<String, Map<String, FormFoldingDefine>> getFormFoldData() {
        return this.formFoldData;
    }

    public void setFormFoldData(Map<String, Map<String, FormFoldingDefine>> formFoldData) {
        this.formFoldData = formFoldData;
    }
}

