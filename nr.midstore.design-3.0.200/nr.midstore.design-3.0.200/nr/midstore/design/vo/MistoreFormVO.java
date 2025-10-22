/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package nr.midstore.design.vo;

import com.jiuqi.nr.definition.common.FormType;
import nr.midstore.design.domain.CommonParamDTO;

public class MistoreFormVO
extends CommonParamDTO {
    private String groupKey;
    private FormType formType;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public FormType getFormType() {
        return this.formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }
}

