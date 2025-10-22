/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.ZBMappingDTO;

public class ZBMappingRepeatVO {
    private List<ZBMappingDTO> repeatList;
    private List<ZBMappingDTO> defaultCodeList;
    private String formCode;

    public List<ZBMappingDTO> getRepeatList() {
        if (this.repeatList == null) {
            this.repeatList = new ArrayList<ZBMappingDTO>();
        }
        return this.repeatList;
    }

    public void setRepeatList(List<ZBMappingDTO> repeatList) {
        this.repeatList = repeatList;
    }

    public List<ZBMappingDTO> getDefaultCodeList() {
        if (this.defaultCodeList == null) {
            this.defaultCodeList = new ArrayList<ZBMappingDTO>();
        }
        return this.defaultCodeList;
    }

    public void setDefaultCodeList(List<ZBMappingDTO> defaultCodeList) {
        this.defaultCodeList = defaultCodeList;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
}

