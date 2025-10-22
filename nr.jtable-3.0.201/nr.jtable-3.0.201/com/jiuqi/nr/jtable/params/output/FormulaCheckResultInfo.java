/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaNodeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="FormulaCheckResultInfo", description="\u516c\u5f0f\u5ba1\u6838\u7ed3\u679c")
public class FormulaCheckResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5ba1\u6838\u7ed3\u679ckey\uff08\u6839\u636e \u516c\u5f0f\u65b9\u6848  \u516c\u5f0f  \u4e3b\u4f53\u7ef4\u5ea6 \u7b49\u8054\u5408\u751f\u6210\uff09", name="key")
    private String key;
    @ApiModelProperty(value="\u516c\u5f0f\u4fe1\u606f", name="formula")
    private FormulaData formula;
    @ApiModelProperty(value="\u516c\u5f0f\u5de6\u4fa7\u503c", name="left")
    private String left;
    @ApiModelProperty(value="\u516c\u5f0f\u53f3\u4fa7\u503c", name="right")
    private String right;
    @ApiModelProperty(value="\u516c\u5f0f\u5dee\u989d", name="difference")
    private String difference;
    @ApiModelProperty(value="\u516c\u5f0f\u8282\u70b9\u5217\u8868", name="nodes")
    private List<FormulaNodeInfo> nodes = new ArrayList<FormulaNodeInfo>();
    @ApiModelProperty(value="\u7ef4\u5ea6\u7d22\u5f15", name="dimensionIndex")
    private int dimensionIndex;
    @ApiModelProperty(value="\u7ef4\u5ea6\u6807\u9898map(key:\u7ef4\u5ea6\u540d;value:\u7ef4\u5ea6\u6807\u9898)", name="dimensionTitle")
    private Map<String, String> dimensionTitle = new HashMap<String, String>();
    @ApiModelProperty(value="\u5355\u4f4dkey", name="unitKey")
    private String unitKey;
    @ApiModelProperty(value="\u5355\u4f4dCode", name="unitCode")
    private String unitCode;
    @ApiModelProperty(value="\u5355\u4f4d\u540d\u79f0", name="unitTitle")
    private String unitTitle;
    @ApiModelProperty(value="\u5ba1\u6838\u9519\u8bef\u8bf4\u660ekey\uff08\u4e0e\u5ba1\u6838\u7ed3\u679ckey\u4e00\u81f4\uff09", name="desKey")
    private String desKey;
    @ApiModelProperty(value="\u5ba1\u6838\u9519\u8bef\u8bf4\u660e", name="descriptionInfo")
    private DescriptionInfo descriptionInfo = new DescriptionInfo();
    private List<Integer> ckdDescIndexList;
    private List<String> formulaType;

    public List<String> getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(List<String> formulaType) {
        this.formulaType = formulaType;
    }

    public List<Integer> getCkdDescIndexList() {
        return this.ckdDescIndexList;
    }

    public void setCkdDescIndexList(List<Integer> ckdDescIndexList) {
        this.ckdDescIndexList = ckdDescIndexList;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FormulaData getFormula() {
        return this.formula;
    }

    public void setFormula(FormulaData formula) {
        this.formula = formula;
    }

    public String getLeft() {
        return this.left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDifference() {
        return this.difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public List<FormulaNodeInfo> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<FormulaNodeInfo> nodes) {
        this.nodes = nodes;
    }

    public int getDimensionIndex() {
        return this.dimensionIndex;
    }

    public void setDimensionIndex(int dimensionIndex) {
        this.dimensionIndex = dimensionIndex;
    }

    public Map<String, String> getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(Map<String, String> dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getDesKey() {
        return this.desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public DescriptionInfo getDescriptionInfo() {
        return this.descriptionInfo;
    }

    public void setDescriptionInfo(DescriptionInfo descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }
}

