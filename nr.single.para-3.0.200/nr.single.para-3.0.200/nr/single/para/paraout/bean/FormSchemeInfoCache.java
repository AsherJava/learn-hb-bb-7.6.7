/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package nr.single.para.paraout.bean;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;

public class FormSchemeInfoCache {
    private DesignFormSchemeDefine formScheme;
    private DesignTaskDefine taskDefine;
    private DesignDataScheme dataScheme;

    public DesignFormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(DesignFormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public DesignTaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(DesignTaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public DesignDataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DesignDataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }
}

