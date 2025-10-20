/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nTaskOrgLinkDefine;
import java.util.List;

public class TaskOrgLinkListStream
extends AbstractParamListStream<TaskOrgLinkDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    TaskOrgLinkListStream(List<TaskOrgLinkDefine> param) {
        super(param);
    }

    public TaskOrgLinkListStream(List<TaskOrgLinkDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(TaskOrgLinkDefine taskOrgLinkDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadOrgBoundToTask(taskOrgLinkDefine.getTask(), taskOrgLinkDefine.getEntity());
    }

    @Override
    TaskOrgLinkDefine transI18n(TaskOrgLinkDefine taskOrgLinkDefine) {
        I18nTaskOrgLinkDefine i18nTaskOrgLinkDefine = new I18nTaskOrgLinkDefine(taskOrgLinkDefine);
        i18nTaskOrgLinkDefine.setI18nEntityAlias(this.languageController.getTaskOrgLink(i18nTaskOrgLinkDefine.getKey(), null));
        return i18nTaskOrgLinkDefine;
    }
}

