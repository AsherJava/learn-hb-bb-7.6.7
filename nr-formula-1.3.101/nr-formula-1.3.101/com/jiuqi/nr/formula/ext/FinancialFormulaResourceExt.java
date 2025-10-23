/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.formula.ext;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.formula.ext.FinancialFormulaResourceDataProvider;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialFormulaResourceExt
extends AbstractFormSchemeResourceFactory {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaSchemeService formulaSchemeService;
    public static final String CODE = "FINANCIAL_FORMULA_RESOURCE";
    public static final String TITLE = "\u8d22\u52a1\u516c\u5f0f\u65b9\u6848\u7ba1\u7406";

    public String code() {
        return CODE;
    }

    public String title() {
        return TITLE;
    }

    public double order() {
        return 4.0;
    }

    public boolean enable(String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return false;
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        if (task == null) {
            return false;
        }
        return task.getEfdcSwitch();
    }

    public IResourceDataProvider dataProvider() {
        return new FinancialFormulaResourceDataProvider(this.formulaSchemeService, this.designTimeViewController);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }

    public ComponentDefine component() {
        ComponentDefine componentDefine = this.buildDefaultComponent();
        componentDefine.setEntry("financialFormulaScheme");
        return componentDefine;
    }

    protected ComponentDefine buildDefaultComponent() {
        ComponentDefine componentDefine = new ComponentDefine();
        componentDefine.setComponentName("formulaManage");
        componentDefine.setProductLine("@nr");
        return componentDefine;
    }
}

