/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping.bean.UnitMapping
 */
package nr.midstore.core.definition.bean.mapping;

import com.jiuqi.nr.mapping.bean.UnitMapping;

public class UnitMappingInfo {
    private UnitMapping unitMapping;
    private String unitCode;
    private String unitMapingCode;

    public UnitMappingInfo() {
    }

    public UnitMappingInfo(UnitMapping unitMapping) {
        this.unitMapping = unitMapping;
        this.unitCode = unitMapping.getUnitCode();
        this.unitMapingCode = unitMapping.getMapping();
    }

    public UnitMapping getUnitMapping() {
        return this.unitMapping;
    }

    public void setUnitMapping(UnitMapping unitMapping) {
        this.unitMapping = unitMapping;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitMapingCode() {
        return this.unitMapingCode;
    }

    public void setUnitMapingCode(String unitMapingCode) {
        this.unitMapingCode = unitMapingCode;
    }
}

