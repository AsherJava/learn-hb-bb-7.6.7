/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.bean.vo;

import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.common.I18nLanguageType;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import java.util.ArrayList;
import java.util.List;

public class I18nInitVO {
    private List<I18nBaseObj> languageType = new ArrayList<I18nBaseObj>();
    private List<I18nBaseObj> resourceType;

    public I18nInitVO() {
        this.languageType.add(new I18nBaseObj(String.valueOf(I18nLanguageType.ENGLISH.getValue()), I18nLanguageType.ENGLISH.getTitle()));
        this.resourceType = new ArrayList<I18nBaseObj>();
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.TASK_TITLE.getValue()), I18nResourceType.TASK_TITLE.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FORM_SCHEME_TITLE.getValue()), I18nResourceType.FORM_SCHEME_TITLE.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FORM_GROUP_TITLE.getValue()), I18nResourceType.FORM_GROUP_TITLE.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FORM.getValue()), I18nResourceType.FORM.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FLOAT_REGION_TAB_TITLE.getValue()), I18nResourceType.FLOAT_REGION_TAB_TITLE.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FORMULA_SCHEME_TITLE.getValue()), I18nResourceType.FORMULA_SCHEME_TITLE.getTitle()));
        this.resourceType.add(new I18nBaseObj(String.valueOf(I18nResourceType.FORMULA_DESC.getValue()), I18nResourceType.FORMULA_DESC.getTitle()));
    }

    public List<I18nBaseObj> getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(List<I18nBaseObj> languageType) {
        this.languageType = languageType;
    }

    public List<I18nBaseObj> getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(List<I18nBaseObj> resourceType) {
        this.resourceType = resourceType;
    }
}

