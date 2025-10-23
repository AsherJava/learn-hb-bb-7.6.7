/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.data.access.filter.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.filter.DimensionFilter;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.Objects;

public class WorkFlowDimensionFilter
implements DimensionFilter {
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public DimensionValueSet filter(DimensionValueSet masterKey, String formSchemeKey, String formOrGroupKey, AccessLevel accessLevel) {
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        DimensionValueSet newMasterKey = new DimensionValueSet(masterKey);
        if (workFlowType != WorkFlowType.ENTITY) {
            newMasterKey.setValue("WL_FORMKEY", (Object)(Objects.nonNull(formOrGroupKey) ? formOrGroupKey : "00000000-0000-0000-0000-000000000000"));
        } else {
            newMasterKey.setValue("WL_FORMKEY", (Object)"00000000-0000-0000-0000-000000000000");
        }
        newMasterKey = this.dataAccesslUtil.filterReportDims(formScheme, newMasterKey);
        return newMasterKey;
    }

    @Override
    public String getAccessName() {
        return "upload";
    }
}

