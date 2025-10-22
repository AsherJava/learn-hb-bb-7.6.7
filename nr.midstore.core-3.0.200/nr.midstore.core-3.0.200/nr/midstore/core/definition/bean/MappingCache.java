/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.util.HashMap;
import java.util.Map;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.definition.bean.mapping.ZBMappingInfo;

public class MappingCache {
    private Map<String, ZBMappingInfo> zbMapingInfos;
    private Map<String, ZBMappingInfo> zbMapingInfosOld;
    private Map<String, ZBMappingInfo> srcZbMapingInfos;
    private Map<String, ZBMappingInfo> srcZbMapingInfosOld;
    private Map<String, UnitMappingInfo> unitMappingInfos;
    private Map<String, UnitMappingInfo> srcUnitMappingInfos;
    private Map<String, PeriodMapingInfo> periodMappingInfos;
    private Map<String, PeriodMapingInfo> srcPeriodMappingInfos;
    private Map<String, EnumMappingInfo> enumMapingInfos;
    private Map<String, UnitMappingInfo> unitRuleMapInfos;

    public Map<String, ZBMappingInfo> getZbMapingInfos() {
        if (this.zbMapingInfos == null) {
            this.zbMapingInfos = new HashMap<String, ZBMappingInfo>();
        }
        return this.zbMapingInfos;
    }

    public void setZbMapingInfos(Map<String, ZBMappingInfo> zbMapingInfos) {
        this.zbMapingInfos = zbMapingInfos;
    }

    public Map<String, UnitMappingInfo> getUnitMappingInfos() {
        if (this.unitMappingInfos == null) {
            this.unitMappingInfos = new HashMap<String, UnitMappingInfo>();
        }
        return this.unitMappingInfos;
    }

    public void setUnitMappingInfos(Map<String, UnitMappingInfo> unitMappingInfos) {
        this.unitMappingInfos = unitMappingInfos;
    }

    public Map<String, ZBMappingInfo> getSrcZbMapingInfos() {
        if (this.srcZbMapingInfos == null) {
            this.srcZbMapingInfos = new HashMap<String, ZBMappingInfo>();
        }
        return this.srcZbMapingInfos;
    }

    public void setSrcZbMapingInfos(Map<String, ZBMappingInfo> srcZbMapingInfos) {
        this.srcZbMapingInfos = srcZbMapingInfos;
    }

    public Map<String, UnitMappingInfo> getUnitRuleMapInfos() {
        return this.unitRuleMapInfos;
    }

    public void setUnitRuleMapInfos(Map<String, UnitMappingInfo> unitRuleMapInfos) {
        this.unitRuleMapInfos = unitRuleMapInfos;
    }

    public Map<String, UnitMappingInfo> getSrcUnitMappingInfos() {
        if (this.srcUnitMappingInfos == null) {
            this.srcUnitMappingInfos = new HashMap<String, UnitMappingInfo>();
        }
        return this.srcUnitMappingInfos;
    }

    public void setSrcUnitMappingInfos(Map<String, UnitMappingInfo> srcUnitMappingInfos) {
        this.srcUnitMappingInfos = srcUnitMappingInfos;
    }

    public Map<String, ZBMappingInfo> getZbMapingInfosOld() {
        if (this.zbMapingInfosOld == null) {
            this.zbMapingInfosOld = new HashMap<String, ZBMappingInfo>();
        }
        return this.zbMapingInfosOld;
    }

    public void setZbMapingInfosOld(Map<String, ZBMappingInfo> zbMapingInfosOld) {
        this.zbMapingInfosOld = zbMapingInfosOld;
    }

    public Map<String, ZBMappingInfo> getSrcZbMapingInfosOld() {
        if (this.srcZbMapingInfosOld == null) {
            this.srcZbMapingInfosOld = new HashMap<String, ZBMappingInfo>();
        }
        return this.srcZbMapingInfosOld;
    }

    public void setSrcZbMapingInfosOld(Map<String, ZBMappingInfo> srcZbMapingInfosOld) {
        this.srcZbMapingInfosOld = srcZbMapingInfosOld;
    }

    public Map<String, PeriodMapingInfo> getPeriodMappingInfos() {
        if (this.periodMappingInfos == null) {
            this.periodMappingInfos = new HashMap<String, PeriodMapingInfo>();
        }
        return this.periodMappingInfos;
    }

    public void setPeriodMappingInfos(Map<String, PeriodMapingInfo> periodMappingInfos) {
        this.periodMappingInfos = periodMappingInfos;
    }

    public Map<String, PeriodMapingInfo> getSrcPeriodMappingInfos() {
        if (this.srcPeriodMappingInfos == null) {
            this.srcPeriodMappingInfos = new HashMap<String, PeriodMapingInfo>();
        }
        return this.srcPeriodMappingInfos;
    }

    public void setSrcPeriodMappingInfos(Map<String, PeriodMapingInfo> srcPeriodMappingInfos) {
        this.srcPeriodMappingInfos = srcPeriodMappingInfos;
    }

    public Map<String, EnumMappingInfo> getEnumMapingInfos() {
        if (this.enumMapingInfos == null) {
            this.enumMapingInfos = new HashMap<String, EnumMappingInfo>();
        }
        return this.enumMapingInfos;
    }

    public void setEnumMapingInfos(Map<String, EnumMappingInfo> enumMapingInfos) {
        this.enumMapingInfos = enumMapingInfos;
    }
}

