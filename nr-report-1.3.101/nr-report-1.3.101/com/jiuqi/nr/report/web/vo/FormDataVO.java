/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.report.web.vo;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.api.tree.TreeData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormDataVO
implements TreeData {
    private String code;
    private String title;

    public FormDataVO(String formCode, String formTitle) {
        this.code = formCode;
        this.title = formTitle;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

