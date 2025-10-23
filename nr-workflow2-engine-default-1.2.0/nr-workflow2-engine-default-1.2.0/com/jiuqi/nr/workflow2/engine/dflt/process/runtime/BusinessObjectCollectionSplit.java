/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;

public class BusinessObjectCollectionSplit {
    private final FormSchemeDefine formScheme;
    private final IBusinessObjectCollection buzObjectCollection;

    public BusinessObjectCollectionSplit(FormSchemeDefine formScheme, IBusinessObjectCollection buzObjectCollection) {
        this.formScheme = formScheme;
        this.buzObjectCollection = buzObjectCollection;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public IBusinessObjectCollection getBuzObjectCollection() {
        return this.buzObjectCollection;
    }
}

