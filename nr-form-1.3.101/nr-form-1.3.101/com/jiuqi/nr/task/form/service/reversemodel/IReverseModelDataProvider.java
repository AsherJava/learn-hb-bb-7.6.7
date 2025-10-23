/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.form.service.reversemodel;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import java.util.List;

public interface IReverseModelDataProvider {
    public List<DesignDataTable> getAllDataTable();

    public List<DesignDataField> getFields(ReverseModeParam var1, ReverseModeRegionDTO var2);

    public DesignDataScheme getDataScheme();

    public DesignFormSchemeDefine getFormSchemeDefine();

    public DesignFormDefine getFormDefine();

    public DesignFormGroupDefine getFormGroupDefine();
}

