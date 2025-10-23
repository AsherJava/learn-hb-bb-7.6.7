/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 */
package com.jiuqi.nr.task.auth.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.task.auth.IAuthCheckService;
import com.jiuqi.nr.task.exception.TaskException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAuthCheckServiceImpl
implements IAuthCheckService {
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;

    @Override
    public void checkTaskAuth(String taskKey) {
        if (!this.definitionAuthority.canModeling(taskKey)) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.NO_AUTHORITY);
        }
    }

    @Override
    public void checkTaskGroupAuth(String taskGroupKey) {
        if (!this.definitionAuthority.canTaskGroupModeling(taskGroupKey)) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.NO_AUTHORITY);
        }
    }

    @Override
    public void checkFormSchemeAuth(String formSchemeKey) {
        if (!this.definitionAuthority.canFormSchemeModeling(formSchemeKey)) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.NO_AUTHORITY);
        }
    }
}

