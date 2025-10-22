/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel
 *  com.jiuqi.nr.data.access.filter.DimensionFilter
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.filter.DimensionFilter;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowDimensionFilter
implements DimensionFilter {
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    public DimensionValueSet filter(DimensionValueSet masterKey, String formSchemeKey, String formOrGroupKey, AccessLevel accessLevel) {
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        DimensionValueSet newMasterKey = new DimensionValueSet(masterKey);
        newMasterKey = this.dataAccesslUtil.filterReportDims(formScheme, newMasterKey);
        return newMasterKey;
    }

    public String getAccessName() {
        return "upload";
    }
}

