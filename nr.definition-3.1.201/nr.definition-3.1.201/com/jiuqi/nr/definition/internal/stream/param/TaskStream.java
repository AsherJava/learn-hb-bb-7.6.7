/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeTaskDefine;

public class TaskStream
extends AbstractParamStream<TaskDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public TaskStream(TaskDefine param) {
        super(param);
    }

    public TaskStream(TaskDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(TaskDefine taskDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadTask(taskDefine.getKey());
    }

    @Override
    TaskDefine transI18n(TaskDefine taskDefine) {
        I18nRunTimeTaskDefine i18nRunTimeTaskDefine = new I18nRunTimeTaskDefine(taskDefine);
        i18nRunTimeTaskDefine.setTitle(this.languageController.getTaskTitle(taskDefine.getKey(), null));
        return i18nRunTimeTaskDefine;
    }
}

