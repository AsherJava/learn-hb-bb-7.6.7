/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 */
package com.jiuqi.nr.designer.web.factory;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignerFactory {
    @Autowired
    private IDesignTimeViewController dsCtller;
    @Autowired
    private IFormulaDesignTimeController fmlCtller;
    @Autowired
    private IDataDefinitionDesignTimeController tbDefineCtller;
    @Autowired
    private IViewDeployController deployController;
    private static DesignerFactory designerFactory = new DesignerFactory();

    private DesignerFactory() {
    }

    public IDesignTimeViewController getDsCtller() {
        return this.dsCtller;
    }

    public IViewDeployController getDeployCtller() {
        return this.deployController;
    }

    public IFormulaDesignTimeController getFmlCtller() {
        return this.fmlCtller;
    }

    public IDataDefinitionDesignTimeController getTbDefineCtller() {
        return this.tbDefineCtller;
    }

    public static DesignerFactory getDesignerFactory() {
        return designerFactory;
    }
}

