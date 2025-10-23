/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.ext.face.impl.survey;

import com.jiuqi.nr.task.api.face.IFormTypeExt;
import com.jiuqi.nr.task.form.ext.face.IComponentConfigExt;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.face.ILinkConfigExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;
import com.jiuqi.nr.task.form.ext.face.impl.survey.SurveyExtendInfo;
import com.jiuqi.nr.task.form.ext.face.impl.survey.SurveyFormType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SurveyResourceExt
implements IFormDefineResourceExt {
    @Value(value="${jiuqi.nr.task.form.survey.enable:true}")
    private boolean enableSurvey;

    @Override
    public String getCode() {
        return "survey-plugin";
    }

    @Override
    public String prodLine() {
        return "@nr";
    }

    @Override
    public String getTitle() {
        return "\u65b0\u5efa\u95ee\u5377";
    }

    @Override
    public double getOrder() {
        return 1.0;
    }

    @Override
    public IFormTypeExt getFormType() {
        return new SurveyFormType();
    }

    @Override
    public IRegionConfigExt getRegionConfigExt() {
        return null;
    }

    @Override
    public ILinkConfigExt getLinkConfigExt() {
        return null;
    }

    @Override
    public IComponentConfigExt getComponentConfigExt() {
        return null;
    }

    @Override
    public IExtendInfo extendConfig(String formSchemeKey) {
        SurveyExtendInfo surveyExtendInfo = new SurveyExtendInfo();
        surveyExtendInfo.setEnableSurvey(this.enableSurvey);
        return surveyExtendInfo;
    }
}

