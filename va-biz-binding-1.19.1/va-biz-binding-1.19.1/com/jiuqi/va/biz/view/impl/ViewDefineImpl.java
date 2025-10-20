/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.impl;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewDefineImpl
extends PluginDefineImpl
implements ViewDefine {
    List<Map<String, Object>> schemes;
    Map<String, Object> template;
    private String defaultSchemeCode;
    private transient Map<String, String> wizardFirstViews;
    private volatile transient Composite templateObject;
    protected transient Map<String, Map<String, Set<String>>> schemeFields;
    protected transient Map<String, Set<String>> schemeActions;

    public Map<String, Set<String>> getSchemeActions() {
        return this.schemeActions == null ? new HashMap() : this.schemeActions;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Composite getTemplate() {
        if (this.templateObject == null) {
            if (this.template == null || this.template.isEmpty()) {
                return null;
            }
            ViewDefineImpl viewDefineImpl = this;
            synchronized (viewDefineImpl) {
                if (this.templateObject == null) {
                    this.templateObject = (Composite)ControlManagerImpl.createControl(this.template);
                }
            }
        }
        return this.templateObject;
    }

    @Override
    public List<Map<String, Object>> getSchemes() {
        return this.schemes;
    }

    public Map<String, Map<String, Set<String>>> getSchemeFields() {
        return this.schemeFields == null ? new HashMap() : this.schemeFields;
    }

    public void setTemplate(Map<String, Object> template) {
        this.template = template;
    }

    public void setSchemes(List<Map<String, Object>> schemes) {
        this.schemes = schemes;
    }

    public String getDefaultSchemeCode() {
        return this.defaultSchemeCode;
    }

    public void setDefaultSchemeCode(String defaultSchemeCode) {
        this.defaultSchemeCode = defaultSchemeCode;
    }

    public Map<String, String> getWizardFirstViews() {
        if (this.wizardFirstViews == null) {
            this.wizardFirstViews = new HashMap<String, String>();
        }
        return this.wizardFirstViews;
    }

    public void setWizardFirstViews(Map<String, String> wizardFirstViews) {
        this.wizardFirstViews = wizardFirstViews;
    }
}

