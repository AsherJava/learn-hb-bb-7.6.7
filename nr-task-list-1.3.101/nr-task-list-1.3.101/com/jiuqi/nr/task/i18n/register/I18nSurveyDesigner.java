/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 */
package com.jiuqi.nr.task.i18n.register;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.i18n.register.I18nAbstractDesigner;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class I18nSurveyDesigner
extends I18nAbstractDesigner {
    @Override
    public Set<Integer> type() {
        HashSet<Integer> surveyType = new HashSet<Integer>();
        surveyType.add(FormType.FORM_TYPE_SURVEY.getValue());
        return surveyType;
    }

    @Override
    public ComponentDefine component() {
        return new ComponentDefine("survey-plugin", "@nr", "app");
    }
}

