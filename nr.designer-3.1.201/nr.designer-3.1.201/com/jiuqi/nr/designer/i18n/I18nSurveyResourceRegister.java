/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.designer.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.designer.i18n.I18NSurveyItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class I18nSurveyResourceRegister
implements I18NResource {
    private static final long serialVersionUID = 8263502384702168674L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u4efb\u52a1\u8bbe\u8ba1/\u95ee\u5377\u6309\u94ae";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem(I18NSurveyItem.I18NSurvey.I18N_BASICS_QUESTION.code, I18NSurveyItem.I18NSurvey.I18N_BASICS_QUESTION.title));
            resourceObjects.add(new I18NResourceItem(I18NSurveyItem.I18NSurvey.I18N_CHOICE_QUESTION.code, I18NSurveyItem.I18NSurvey.I18N_CHOICE_QUESTION.title));
            resourceObjects.add(new I18NResourceItem(I18NSurveyItem.I18NSurvey.I18N_EXPERT_QUESTION.code, I18NSurveyItem.I18NSurvey.I18N_EXPERT_QUESTION.title));
            resourceObjects.add(new I18NResourceItem(I18NSurveyItem.I18NSurvey.I18N_ASSIST_QUESTION.code, I18NSurveyItem.I18NSurvey.I18N_ASSIST_QUESTION.title));
        }
        return resourceObjects;
    }
}

