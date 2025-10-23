/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public abstract class AbstractDataSchemeSupport {
    private final IDesignTimeViewController designTimeViewController;
    private final IDesignDataSchemeService designDataSchemeService;

    protected AbstractDataSchemeSupport(IDesignTimeViewController designTimeViewController, IDesignDataSchemeService designDataSchemeService) {
        this.designTimeViewController = designTimeViewController;
        this.designDataSchemeService = designDataSchemeService;
    }

    public IDesignTimeViewController getDesignTimeViewController() {
        return this.designTimeViewController;
    }

    public IDesignDataSchemeService getDesignDataSchemeService() {
        return this.designDataSchemeService;
    }

    public DataScheme getDataScheme(String formKey) {
        DesignTaskDefine task = this.getDesignTimeViewController().getTask(formKey);
        if (task != null && this.getDesignDataSchemeService() != null) {
            return this.getDesignDataSchemeService().getDataScheme(task.getKey());
        }
        return null;
    }

    public FormSchemeDefine getFormScheme(String formKey) {
        DesignFormDefine form = this.getDesignTimeViewController().getForm(formKey);
        if (form != null) {
            return this.getDesignTimeViewController().getFormScheme(form.getFormScheme());
        }
        return null;
    }

    public TaskDefine getTask(String formKey) {
        DesignFormSchemeDefine formScheme = this.getDesignTimeViewController().getFormScheme(formKey);
        if (formScheme != null) {
            return this.getDesignTimeViewController().getTask(formScheme.getTaskKey());
        }
        return null;
    }
}

