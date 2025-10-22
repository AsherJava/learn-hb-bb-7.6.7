/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.dwdm;

import com.jiuqi.nr.entity.component.dwdm.OrgAttributeDTO;
import java.util.List;

public class OrgIDCAttributeDTO {
    private List<OrgAttributeDTO> attributes;
    private String dwdm;

    public List<OrgAttributeDTO> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<OrgAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public String getDwdm() {
        return this.dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }
}

