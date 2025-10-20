/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import com.jiuqi.gcreport.dimension.vo.EffectScopeTreeVO;
import com.jiuqi.gcreport.dimension.vo.FieldTypeVO;
import java.util.List;

public class DimensionInitVO {
    private List<EffectScopeTreeVO> effectScopeVOS;
    private List<FieldTypeVO> addFieldTypes;
    private List<FieldTypeVO> editFieldTypes;
    private Boolean masterFlag;

    public List<FieldTypeVO> getAddFieldTypes() {
        return this.addFieldTypes;
    }

    public void setAddFieldTypes(List<FieldTypeVO> addFieldTypes) {
        this.addFieldTypes = addFieldTypes;
    }

    public List<EffectScopeTreeVO> getEffectScopeVOS() {
        return this.effectScopeVOS;
    }

    public void setEffectScopeVOS(List<EffectScopeTreeVO> effectScopeVOS) {
        this.effectScopeVOS = effectScopeVOS;
    }

    public Boolean getMasterFlag() {
        return this.masterFlag;
    }

    public void setMasterFlag(Boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    public List<FieldTypeVO> getEditFieldTypes() {
        return this.editFieldTypes;
    }

    public void setEditFieldTypes(List<FieldTypeVO> editFieldTypes) {
        this.editFieldTypes = editFieldTypes;
    }
}

