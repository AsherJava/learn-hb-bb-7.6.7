/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.financialcheckapi.reltxquery.vo;

import com.jiuqi.common.base.util.UUIDUtils;
import java.util.List;
import java.util.Map;

public class RelTxCheckQueryTableDataVO {
    private String id = UUIDUtils.newUUIDStr();
    private Integer level;
    private String checkState;
    private String assetUnit;
    private Map<String, String> assetUnitMap;
    private String assetLevel2Unit;
    private String debtUnit;
    private Map<String, String> debtUnitMap;
    private String debtLevel2Unit;
    private String originalCurr;
    private Map<String, String> originalCurrMap;
    private String checkAttribute;
    private Map<String, String> checkAttributeMap;
    private String assetAmt;
    private String debtAmt;
    private String diffAmt;
    private Integer BusinessRole;
    private List<RelTxCheckQueryTableDataVO> children;
    private boolean hasChild = false;

    public boolean getHasChild() {
        return this.hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getAssetUnit() {
        return this.assetUnit;
    }

    public void setAssetUnit(String assetUnit) {
        this.assetUnit = assetUnit;
    }

    public Map<String, String> getAssetUnitMap() {
        return this.assetUnitMap;
    }

    public void setAssetUnitMap(Map<String, String> assetUnitMap) {
        this.assetUnitMap = assetUnitMap;
    }

    public String getAssetLevel2Unit() {
        return this.assetLevel2Unit;
    }

    public void setAssetLevel2Unit(String assetLevel2Unit) {
        this.assetLevel2Unit = assetLevel2Unit;
    }

    public String getDebtUnit() {
        return this.debtUnit;
    }

    public void setDebtUnit(String debtUnit) {
        this.debtUnit = debtUnit;
    }

    public Map<String, String> getDebtUnitMap() {
        return this.debtUnitMap;
    }

    public void setDebtUnitMap(Map<String, String> debtUnitMap) {
        this.debtUnitMap = debtUnitMap;
    }

    public String getDebtLevel2Unit() {
        return this.debtLevel2Unit;
    }

    public void setDebtLevel2Unit(String debtLevel2Unit) {
        this.debtLevel2Unit = debtLevel2Unit;
    }

    public String getOriginalCurr() {
        return this.originalCurr;
    }

    public void setOriginalCurr(String originalCurr) {
        this.originalCurr = originalCurr;
    }

    public Map<String, String> getOriginalCurrMap() {
        return this.originalCurrMap;
    }

    public void setOriginalCurrMap(Map<String, String> originalCurrMap) {
        this.originalCurrMap = originalCurrMap;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }

    public Map<String, String> getCheckAttributeMap() {
        return this.checkAttributeMap;
    }

    public void setCheckAttributeMap(Map<String, String> checkAttributeMap) {
        this.checkAttributeMap = checkAttributeMap;
    }

    public String getAssetAmt() {
        return this.assetAmt;
    }

    public void setAssetAmt(String assetAmt) {
        this.assetAmt = assetAmt;
    }

    public String getDebtAmt() {
        return this.debtAmt;
    }

    public void setDebtAmt(String debtAmt) {
        this.debtAmt = debtAmt;
    }

    public String getDiffAmt() {
        return this.diffAmt;
    }

    public void setDiffAmt(String diffAmt) {
        this.diffAmt = diffAmt;
    }

    public List<RelTxCheckQueryTableDataVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<RelTxCheckQueryTableDataVO> children) {
        this.children = children;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBusinessRole() {
        return this.BusinessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.BusinessRole = businessRole;
    }
}

