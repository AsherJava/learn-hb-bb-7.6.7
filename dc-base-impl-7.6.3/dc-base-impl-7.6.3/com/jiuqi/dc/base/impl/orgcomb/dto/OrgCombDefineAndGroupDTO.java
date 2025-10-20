/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 */
package com.jiuqi.dc.base.impl.orgcomb.dto;

import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;

public class OrgCombDefineAndGroupDTO {
    private OrgCombGroupDO group;
    private OrgCombDefineVO define;

    public OrgCombGroupDO getGroup() {
        return this.group;
    }

    public void setGroup(OrgCombGroupDO group) {
        this.group = group;
    }

    public OrgCombDefineVO getDefine() {
        return this.define;
    }

    public void setDefine(OrgCombDefineVO define) {
        this.define = define;
    }
}

