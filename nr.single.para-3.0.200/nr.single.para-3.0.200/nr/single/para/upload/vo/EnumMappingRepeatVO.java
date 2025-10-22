/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.EnumDefineMappingDTO;

public class EnumMappingRepeatVO {
    private List<EnumDefineMappingDTO> repeatList;
    private List<EnumDefineMappingDTO> defaultCodeList;
    private String schemePrefix;

    public List<EnumDefineMappingDTO> getRepeatList() {
        if (this.repeatList == null) {
            this.repeatList = new ArrayList<EnumDefineMappingDTO>();
        }
        return this.repeatList;
    }

    public void setRepeatList(List<EnumDefineMappingDTO> repeatList) {
        this.repeatList = repeatList;
    }

    public List<EnumDefineMappingDTO> getDefaultCodeList() {
        if (this.defaultCodeList == null) {
            this.defaultCodeList = new ArrayList<EnumDefineMappingDTO>();
        }
        return this.defaultCodeList;
    }

    public void setDefaultCodeList(List<EnumDefineMappingDTO> defaultCodeList) {
        this.defaultCodeList = defaultCodeList;
    }

    public String getSchemePrefix() {
        return this.schemePrefix;
    }

    public void setSchemePrefix(String schemePrefix) {
        this.schemePrefix = schemePrefix;
    }
}

