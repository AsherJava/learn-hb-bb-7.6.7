/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 */
package com.jiuqi.gcreport.organization.impl.extend;

import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;

public interface UnitTreeNodeBuilder {
    public OrgDataVO buildTreeNode(OrgDataParam var1, OrgDataDO var2, OrgDisplaySchemeDO var3);
}

