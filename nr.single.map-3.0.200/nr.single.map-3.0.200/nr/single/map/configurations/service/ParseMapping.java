/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.service;

import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.data.facade.SingleFileFormulaItem;

public abstract class ParseMapping {
    private List<ZbMapping> zbInfo;
    private List<SingleFileFormulaItem> formulaInfo;
    private UnitMapping unitInfo;
    private List<RuleMap> mapRule;
    private String schemeKey;

    public void initData() {
        this.zbInfo = new ArrayList<ZbMapping>();
        this.formulaInfo = new ArrayList<SingleFileFormulaItem>();
        this.unitInfo = new UnitMapping();
        this.mapRule = new ArrayList<RuleMap>();
        this.schemeKey = null;
    }

    public abstract int parseFileMapping(byte[] var1);

    public abstract void convertFromConfig(ISingleMappingConfig var1);

    public abstract String buildTextFile();

    public abstract IllegalData getErrorData();

    public List<ZbMapping> getZbInfo() {
        return this.zbInfo;
    }

    public void setZbInfo(List<ZbMapping> zbInfo) {
        this.zbInfo = zbInfo;
    }

    public List<SingleFileFormulaItem> getFormulaInfo() {
        return this.formulaInfo;
    }

    public void setFormulaInfo(List<SingleFileFormulaItem> items) {
        this.formulaInfo = items;
    }

    public void addFormulaInfo(SingleFileFormulaItem item) {
        if (this.formulaInfo == null) {
            this.formulaInfo = new ArrayList<SingleFileFormulaItem>();
        }
        this.formulaInfo.add(item);
    }

    public UnitMapping getUnitInfo() {
        return this.unitInfo;
    }

    public void setUnitInfo(UnitMapping unitInfo) {
        this.unitInfo = unitInfo;
    }

    public List<RuleMap> getMapRule() {
        return this.mapRule;
    }

    public void setMapRule(List<RuleMap> mapRule) {
        this.mapRule = mapRule;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

