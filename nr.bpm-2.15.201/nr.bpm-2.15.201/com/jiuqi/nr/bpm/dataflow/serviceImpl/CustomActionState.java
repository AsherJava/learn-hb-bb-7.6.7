/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.bpm.dataflow.serviceImpl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.upload.utils.ObtainCustomName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class CustomActionState {
    @Autowired
    private ObtainCustomName obtainCustomName;
    @Autowired
    ITreeNodeIconColorService treeNodeIconColorService;
    @Autowired
    @Qualifier(value="process_btn")
    private I18nHelper i18nHelper;
    private final String CHINESE = "zh";
    private final String EMPTY_STR = "";

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    private String getLanguageName(String actionCode) {
        String language = this.getLanguage();
        if (language.equals("") || language.equals("zh")) {
            return "";
        }
        if (actionCode == null || this.i18nHelper.getMessage(actionCode).equals("")) {
            return "";
        }
        return this.i18nHelper.getMessage(actionCode);
    }

    public String getCustomStateDes(String formSchemeKey, String stateCode, String taskCode) {
        String stateName = this.obtainCustomName.getStateNameByStateCode(formSchemeKey, stateCode, taskCode);
        stateName = this.setI18nDes(stateName, stateCode);
        return stateName;
    }

    public String getCustomActionDes(String formSchemeKey, String actionCode, String taskCode) {
        String actionName = this.obtainCustomName.getActionNameByActionCode(formSchemeKey, actionCode, taskCode);
        actionName = this.setI18nDes(actionName, actionCode);
        return actionName;
    }

    private String setI18nDes(String name, String actionCode) {
        String nameTemp = name;
        String languageName = this.getLanguageName(actionCode);
        if (!languageName.equals("")) {
            return languageName;
        }
        return nameTemp;
    }

    public String getIcon(String stateCode) {
        TreeNodeColorInfo nodeIconColor = this.treeNodeIconColorService.getNodeIconColorByUploadState(stateCode);
        if (nodeIconColor != null) {
            return nodeIconColor.getIcon();
        }
        return null;
    }

    public String getColor(String stateCode) {
        TreeNodeColorInfo nodeIconColor = this.treeNodeIconColorService.getNodeIconColorByUploadState(stateCode);
        if (nodeIconColor != null) {
            return nodeIconColor.getColor();
        }
        return null;
    }
}

