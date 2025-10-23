/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.ext.face.impl.form;

import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.task.api.face.IFormTypeExt;
import com.jiuqi.nr.task.form.ext.face.IComponentConfigExt;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.face.ILinkConfigExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DefaultFormExt
implements IFormDefineResourceExt {
    @Autowired
    protected IDesignDataSchemeService designDataSchemeService;
    @Autowired
    protected IDesignTimeViewController designTimeViewController;

    @Override
    public String prodLine() {
        return "";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public double getOrder() {
        return 0.0;
    }

    @Override
    public IFormTypeExt getFormType() {
        return null;
    }

    @Override
    public IRegionConfigExt getRegionConfigExt() {
        return null;
    }

    @Override
    public ILinkConfigExt getLinkConfigExt() {
        return null;
    }

    @Override
    public IComponentConfigExt getComponentConfigExt() {
        return null;
    }

    protected boolean isZbScheme(String formSchemeKey) {
        return false;
    }
}

