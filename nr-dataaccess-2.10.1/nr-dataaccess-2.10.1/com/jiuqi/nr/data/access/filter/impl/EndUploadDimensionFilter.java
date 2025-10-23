/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.access.filter.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.filter.DimensionFilter;
import com.jiuqi.nr.data.access.service.impl.EndUploadAccessServiceImpl;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndUploadDimensionFilter
implements DimensionFilter {
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private EndUploadAccessServiceImpl endUploadAccessServiceImpl;

    @Override
    public DimensionValueSet filter(DimensionValueSet masterKey, String formSchemeKey, String formOrGroupKey, AccessLevel accessLevel) {
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        DimensionValueSet newMasterKey = new DimensionValueSet(masterKey);
        if (accessLevel == AccessLevel.FORM) {
            newMasterKey.setValue("US_FORMKEY", (Object)(Objects.nonNull(formOrGroupKey) ? formOrGroupKey : "00000000-0000-0000-0000-000000000000"));
        }
        newMasterKey = this.dataAccesslUtil.filterReportDims(formScheme, newMasterKey);
        return newMasterKey;
    }

    @Override
    public String getAccessName() {
        return this.endUploadAccessServiceImpl.name();
    }
}

