/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;

public class AIFieldData {
    DesignDataLinkDefine designDataLinkDefine;
    DesignFieldDefine designFieldDefine;

    public AIFieldData(DesignDataLinkDefine designDataLinkDefine, DesignFieldDefine designFieldDefine) {
        this.designDataLinkDefine = designDataLinkDefine;
        this.designFieldDefine = designFieldDefine;
    }

    public DesignDataLinkDefine getDesignDataLinkDefine() {
        return this.designDataLinkDefine;
    }

    public void setDesignDataLinkDefine(DesignDataLinkDefine designDataLinkDefine) {
        this.designDataLinkDefine = designDataLinkDefine;
    }

    public DesignFieldDefine getDesignFieldDefine() {
        return this.designFieldDefine;
    }

    public void setDesignFieldDefine(DesignFieldDefine designFieldDefine) {
        this.designFieldDefine = designFieldDefine;
    }
}

