/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.formula.ext;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.formula.ext.FormulaResourceDataProvider;
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
public class FormulaResourceExt
extends AbstractFormSchemeResourceFactory {
    @Autowired
    private IFormulaSchemeService formulaSchemeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    public static final String CODE = "FORMULA_RESOURCE";
    public static final String TITLE = "\u516c\u5f0f\u65b9\u6848";

    public String code() {
        return CODE;
    }

    public String title() {
        return TITLE;
    }

    public double order() {
        return 1.0;
    }

    public boolean enable(String formSchemeKey) {
        return true;
    }

    public IResourceDataProvider dataProvider() {
        return new FormulaResourceDataProvider(this.formulaSchemeService, this.designTimeViewController);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }

    public ComponentDefine component() {
        ComponentDefine componentDefine = this.buildDefaultComponent();
        componentDefine.setEntry("reportFormulaScheme");
        return componentDefine;
    }

    protected ComponentDefine buildDefaultComponent() {
        ComponentDefine componentDefine = new ComponentDefine();
        componentDefine.setComponentName("formulaManage");
        componentDefine.setProductLine("@nr");
        return componentDefine;
    }
}

