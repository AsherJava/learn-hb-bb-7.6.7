/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.upload.domain.BaseCompareDTO;

public class EnumDefineMappingDTO
extends BaseCompareDTO {
    private CompareContextType enumItemCompareType;
    private Integer singleCodeLen;
    private Integer netCodeLen;
    private CompareChangeType enumItemChangeType;

    public CompareContextType getEnumItemCompareType() {
        return this.enumItemCompareType;
    }

    public void setEnumItemCompareType(CompareContextType enumItemCompareType) {
        this.enumItemCompareType = enumItemCompareType;
    }

    public Integer getSingleCodeLen() {
        return this.singleCodeLen;
    }

    public void setSingleCodeLen(Integer singleCodeLen) {
        this.singleCodeLen = singleCodeLen;
    }

    public Integer getNetCodeLen() {
        return this.netCodeLen;
    }

    public void setNetCodeLen(Integer netCodeLen) {
        this.netCodeLen = netCodeLen;
    }

    public CompareChangeType getEnumItemChangeType() {
        return this.enumItemChangeType;
    }

    public void setEnumItemChangeType(CompareChangeType enumItemChangeType) {
        this.enumItemChangeType = enumItemChangeType;
    }
}

