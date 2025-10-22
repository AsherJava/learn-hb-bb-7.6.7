/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import java.util.List;
import nr.single.para.upload.domain.EnumDataMappingDTO;

public class EnumDataDTO {
    private String enumCompareKey;
    private List<EnumDataMappingDTO> mappingList;

    public String getEnumCompareKey() {
        return this.enumCompareKey;
    }

    public void setEnumCompareKey(String enumCompareKey) {
        this.enumCompareKey = enumCompareKey;
    }

    public List<EnumDataMappingDTO> getMappingList() {
        return this.mappingList;
    }

    public void setMappingList(List<EnumDataMappingDTO> mappingList) {
        this.mappingList = mappingList;
    }
}

