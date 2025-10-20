/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.workflow.domain.WorkflowExtendQueryDTO;

public interface WorkflowExtendQueryService {
    public PageVO<UserDO> queryUser(WorkflowExtendQueryDTO var1);

    public PageVO<BaseDataDO> queryStaff(WorkflowExtendQueryDTO var1);
}

