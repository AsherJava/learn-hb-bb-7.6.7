/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.designer.reporttag.rest.vo;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormDataVO {
    private String formCode;
    private String formTitle;

    public FormDataVO(String formCode, String formTitle) {
        this.formCode = formCode;
        this.formTitle = formTitle;
    }

    public static List<FormDataVO> toFormDataVO(List<DesignFormDefine> formDefines) {
        if (formDefines != null) {
            ArrayList<FormDataVO> formDataVOS = new ArrayList<FormDataVO>();
            for (DesignFormDefine formDefine : formDefines) {
                formDataVOS.add(new FormDataVO(formDefine.getFormCode(), formDefine.getTitle()));
            }
            return formDataVOS;
        }
        return Collections.emptyList();
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }
}

