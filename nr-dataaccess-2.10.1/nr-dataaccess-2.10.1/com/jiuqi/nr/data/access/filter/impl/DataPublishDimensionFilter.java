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
import com.jiuqi.nr.data.access.service.impl.DataPublishAccessServiceImpl;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class DataPublishDimensionFilter
implements DimensionFilter {
    @Autowired
    private DataPublishAccessServiceImpl dataPublishAccessServiceImpl;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public DimensionValueSet filter(DimensionValueSet masterKey, String formSchemeKey, String formOrGroupKey, AccessLevel accessLevel) {
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        DimensionValueSet newMasterKey = this.dataAccesslUtil.filterReportDims(formScheme, masterKey);
        return newMasterKey;
    }

    @Override
    public String getAccessName() {
        return this.dataPublishAccessServiceImpl.name();
    }
}

