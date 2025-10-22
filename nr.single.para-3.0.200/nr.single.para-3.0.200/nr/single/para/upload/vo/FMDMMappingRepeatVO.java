/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.FMDMMappingDTO;

public class FMDMMappingRepeatVO {
    private String dataSchemeKey;
    private List<FMDMMappingDTO> repeatList;
    private List<FMDMMappingDTO> defaultCodeList;

    public List<FMDMMappingDTO> getRepeatList() {
        if (this.repeatList == null) {
            this.repeatList = new ArrayList<FMDMMappingDTO>();
        }
        return this.repeatList;
    }

    public void setRepeatList(List<FMDMMappingDTO> repeatList) {
        this.repeatList = repeatList;
    }

    public List<FMDMMappingDTO> getDefaultCodeList() {
        if (this.defaultCodeList == null) {
            this.defaultCodeList = new ArrayList<FMDMMappingDTO>();
        }
        return this.defaultCodeList;
    }

    public void setDefaultCodeList(List<FMDMMappingDTO> defaultCodeList) {
        this.defaultCodeList = defaultCodeList;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

