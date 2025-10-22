/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.examine.web.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.examine.web.bean.BaseDimVO;
import com.jiuqi.nr.examine.web.bean.MdInfoFieldUpgradeVO;
import com.jiuqi.nr.examine.web.bean.MdInfoFormUpgradeVO;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MdInfoUpgradeVO {
    private String dataSchemeKey;
    private boolean upgradeFailed;
    private String upgradeMessage;
    private List<String> dataFieldKeys;
    private Map<String, String> dataDimValues;
    private Map<String, BaseDimVO> dataDims;
    private List<MdInfoFormUpgradeVO> fmdmForms;
    private List<MdInfoFieldUpgradeVO> mdInfoFields;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public List<String> getDataFieldKeys() {
        return this.dataFieldKeys;
    }

    public void setDataFieldKeys(List<String> dataFieldKeys) {
        this.dataFieldKeys = dataFieldKeys;
    }

    public Map<String, String> getDataDimValues() {
        return this.dataDimValues;
    }

    public void setDataDimValues(Map<String, String> dataDimValues) {
        this.dataDimValues = dataDimValues;
    }

    public List<MdInfoFormUpgradeVO> getFmdmForms() {
        return this.fmdmForms;
    }

    public void setFmdmForms(List<MdInfoFormUpgradeVO> fmdmForms) {
        this.fmdmForms = fmdmForms;
    }

    public boolean isUpgradeFailed() {
        return this.upgradeFailed;
    }

    public void setUpgradeFailed(boolean upgradeFailed) {
        this.upgradeFailed = upgradeFailed;
    }

    public String getUpgradeMessage() {
        return this.upgradeMessage;
    }

    public void setUpgradeMessage(String upgradeMessage) {
        this.upgradeMessage = upgradeMessage;
    }

    public Map<String, BaseDimVO> getDataDims() {
        return this.dataDims;
    }

    public void setDataDims(Map<String, BaseDimVO> dataDims) {
        this.dataDims = dataDims;
    }

    public List<MdInfoFieldUpgradeVO> getMdInfoFields() {
        return this.mdInfoFields;
    }

    public void setMdInfoFields(List<MdInfoFieldUpgradeVO> mdInfoFields) {
        this.mdInfoFields = mdInfoFields;
    }
}

