/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;

public class TaskGroupStream
extends AbstractParamStream<TaskGroupDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public TaskGroupStream(TaskGroupDefine param) {
        super(param);
    }

    public TaskGroupStream(TaskGroupDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(TaskGroupDefine taskGroupDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadTask(taskGroupDefine.getKey());
    }

    @Override
    TaskGroupDefine transI18n(TaskGroupDefine taskDefine) {
        return taskDefine;
    }
}

