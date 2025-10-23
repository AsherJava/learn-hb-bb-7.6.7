/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.ext.face.impl.analysis;

import com.jiuqi.nr.task.api.face.IFormTypeExt;
import com.jiuqi.nr.task.form.ext.face.IComponentConfigExt;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.face.ILinkConfigExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;
import com.jiuqi.nr.task.form.ext.face.impl.analysis.AnalysisExtendInfo;
import com.jiuqi.nr.task.form.ext.face.impl.analysis.AnalysisFormType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AnalysisResourceExt
implements IFormDefineResourceExt {
    @Value(value="${jiuqi.nr.task.form.analysis.enable:true}")
    private boolean enableAnalysis;

    @Override
    public String getCode() {
        return "analysis-plugin";
    }

    @Override
    public String prodLine() {
        return "@nr";
    }

    @Override
    public String getTitle() {
        return "\u5206\u6790\u8868";
    }

    @Override
    public double getOrder() {
        return 2.0;
    }

    @Override
    public IFormTypeExt getFormType() {
        return new AnalysisFormType();
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
        AnalysisExtendInfo analysisExtendInfo = new AnalysisExtendInfo();
        analysisExtendInfo.setEnableAnalysis(this.enableAnalysis);
        return analysisExtendInfo;
    }
}

