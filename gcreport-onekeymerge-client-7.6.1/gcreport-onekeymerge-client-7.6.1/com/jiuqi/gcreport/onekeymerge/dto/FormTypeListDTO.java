/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import com.jiuqi.gcreport.onekeymerge.dto.UnitType;
import java.util.List;

public class FormTypeListDTO {
    private List<String> allForm;
    private List<String> baseForm;
    private List<String> floatForm;

    public List<String> getFormsByOrgType(UnitType unitType) {
        if (unitType == UnitType.DIFFUNIT) {
            return this.baseForm;
        }
        if (unitType == UnitType.HBUNIT) {
            return this.baseForm;
        }
        if (unitType == UnitType.NONESTATE) {
            return this.allForm;
        }
        return null;
    }

    public FormTypeListDTO() {
    }

    public FormTypeListDTO(List<String> allForm, List<String> baseForm, List<String> floatForm) {
        this.allForm = allForm;
        this.baseForm = baseForm;
        this.floatForm = floatForm;
    }

    public List<String> getAllForm() {
        return this.allForm;
    }

    public void setAllForm(List<String> allForm) {
        this.allForm = allForm;
    }

    public List<String> getBaseForm() {
        return this.baseForm;
    }

    public void setBaseForm(List<String> baseForm) {
        this.baseForm = baseForm;
    }

    public List<String> getFloatForm() {
        return this.floatForm;
    }

    public void setFloatForm(List<String> floatForm) {
        this.floatForm = floatForm;
    }
}

