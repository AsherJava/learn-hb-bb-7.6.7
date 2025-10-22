/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.upload.domain.BaseCompareDTO;

public class FormulaFormMappingDTO
extends BaseCompareDTO {
    private CompareChangeType itemChangeType;

    public CompareChangeType getItemChangeType() {
        return this.itemChangeType;
    }

    public void setItemChangeType(CompareChangeType itemChangeType) {
        this.itemChangeType = itemChangeType;
    }
}

