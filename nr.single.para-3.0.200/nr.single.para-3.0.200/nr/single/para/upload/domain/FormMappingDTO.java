/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.upload.domain.BaseCompareDTO;

public class FormMappingDTO
extends BaseCompareDTO {
    CompareContextType compareType;
    boolean iniFieldMap;

    public CompareContextType getCompareType() {
        return this.compareType;
    }

    public void setCompareType(CompareContextType compareType) {
        this.compareType = compareType;
    }

    public boolean isIniFieldMap() {
        return this.iniFieldMap;
    }

    public void setIniFieldMap(boolean iniFieldMap) {
        this.iniFieldMap = iniFieldMap;
    }
}

