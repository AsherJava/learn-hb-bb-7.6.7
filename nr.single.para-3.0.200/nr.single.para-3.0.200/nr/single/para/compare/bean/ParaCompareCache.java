/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package nr.single.para.compare.bean;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;

public class ParaCompareCache {
    private DesignDataScheme dataScheme;
    private DesignTaskDefine taskDefine;
    private DesignFormSchemeDefine formScheme;

    public DesignDataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DesignDataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public DesignTaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(DesignTaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public DesignFormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(DesignFormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }
}

