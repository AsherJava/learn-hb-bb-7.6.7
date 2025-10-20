/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import java.util.List;

public class TaskGroupListStream
extends AbstractParamListStream<TaskGroupDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public TaskGroupListStream(List<TaskGroupDefine> param) {
        super(param);
    }

    public TaskGroupListStream(List<TaskGroupDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(TaskGroupDefine TaskGroupDefine2, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadTask(TaskGroupDefine2.getKey());
    }

    @Override
    TaskGroupDefine transI18n(TaskGroupDefine TaskGroupDefine2) {
        return TaskGroupDefine2;
    }
}

