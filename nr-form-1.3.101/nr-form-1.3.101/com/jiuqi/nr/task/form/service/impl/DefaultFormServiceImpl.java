/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultFormServiceImpl {
    @Autowired
    private DesignTimeViewController designTimeViewController;

    public String insertDefaultFormGroup(String formSchemeKey) {
        DesignFormGroupDefine newFormGroup = this.designTimeViewController.initFormGroup();
        newFormGroup.setTitle("\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4");
        newFormGroup.setFormSchemeKey(formSchemeKey);
        this.designTimeViewController.insertFormGroup(newFormGroup);
        return newFormGroup.getKey();
    }

    public String insertDefaultForm(String formSchemeKey) {
        DesignFormDefine newForm = this.designTimeViewController.initForm();
        newForm.setTitle("\u9ed8\u8ba4\u62a5\u8868");
        newForm.setFormScheme(formSchemeKey);
        this.designTimeViewController.insertForm(newForm);
        return newForm.getKey();
    }
}

