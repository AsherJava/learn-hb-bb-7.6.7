/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.Panel;
import java.util.List;
import java.util.Map;

public class PanelDynamic
extends Panel {
    private String renderMode;
    private int panelCount;
    private int minPanelCount;
    private int maxPanelCount;
    private List<Element> templateElements;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean allowAddPanel = true;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean allowRemovePanel = true;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean showRangeInProgress = true;
    private boolean confirmDelete;
    private boolean defaultValueFromLastPanel;
    private String confirmDeleteText;
    private String panelAddText;
    private String panelRemoveText;
    private String templateTitle;
    private String templateDescription;
    private String noEntriesText;
    private String panelPrevText;
    private String panelNextText;
    private List<Map<String, Object>> defaultValue;
    private Map<String, Object> defaultPanelValue;
    private String templateTitleLocation;
    private String panelRemoveButtonLocation;
    private String keyDuplicationError;
    private String keyName;

    public List<Element> getTemplateElements() {
        return this.templateElements;
    }

    public void setTemplateElements(List<Element> templateElements) {
        this.templateElements = templateElements;
    }

    public String getRenderMode() {
        return this.renderMode;
    }

    public void setRenderMode(String renderMode) {
        this.renderMode = renderMode;
    }

    public int getPanelCount() {
        return this.panelCount;
    }

    public void setPanelCount(int panelCount) {
        this.panelCount = panelCount;
    }

    public int getMinPanelCount() {
        return this.minPanelCount;
    }

    public void setMinPanelCount(int minPanelCount) {
        this.minPanelCount = minPanelCount;
    }

    public int getMaxPanelCount() {
        return this.maxPanelCount;
    }

    public void setMaxPanelCount(int maxPanelCount) {
        this.maxPanelCount = maxPanelCount;
    }

    public boolean isAllowAddPanel() {
        return this.allowAddPanel;
    }

    public void setAllowAddPanel(boolean allowAddPanel) {
        this.allowAddPanel = allowAddPanel;
    }

    public boolean isAllowRemovePanel() {
        return this.allowRemovePanel;
    }

    public void setAllowRemovePanel(boolean allowRemovePanel) {
        this.allowRemovePanel = allowRemovePanel;
    }

    public boolean isShowRangeInProgress() {
        return this.showRangeInProgress;
    }

    public void setShowRangeInProgress(boolean showRangeInProgress) {
        this.showRangeInProgress = showRangeInProgress;
    }

    public boolean isConfirmDelete() {
        return this.confirmDelete;
    }

    public void setConfirmDelete(boolean confirmDelete) {
        this.confirmDelete = confirmDelete;
    }

    public boolean isDefaultValueFromLastPanel() {
        return this.defaultValueFromLastPanel;
    }

    public void setDefaultValueFromLastPanel(boolean defaultValueFromLastPanel) {
        this.defaultValueFromLastPanel = defaultValueFromLastPanel;
    }

    public String getConfirmDeleteText() {
        return this.confirmDeleteText;
    }

    public void setConfirmDeleteText(String confirmDeleteText) {
        this.confirmDeleteText = confirmDeleteText;
    }

    public String getPanelAddText() {
        return this.panelAddText;
    }

    public void setPanelAddText(String panelAddText) {
        this.panelAddText = panelAddText;
    }

    public String getPanelRemoveText() {
        return this.panelRemoveText;
    }

    public void setPanelRemoveText(String panelRemoveText) {
        this.panelRemoveText = panelRemoveText;
    }

    public String getTemplateTitle() {
        return this.templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateDescription() {
        return this.templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String getNoEntriesText() {
        return this.noEntriesText;
    }

    public void setNoEntriesText(String noEntriesText) {
        this.noEntriesText = noEntriesText;
    }

    public String getPanelPrevText() {
        return this.panelPrevText;
    }

    public void setPanelPrevText(String panelPrevText) {
        this.panelPrevText = panelPrevText;
    }

    public String getPanelNextText() {
        return this.panelNextText;
    }

    public void setPanelNextText(String panelNextText) {
        this.panelNextText = panelNextText;
    }

    public List<Map<String, Object>> getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(List<Map<String, Object>> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<String, Object> getDefaultPanelValue() {
        return this.defaultPanelValue;
    }

    public void setDefaultPanelValue(Map<String, Object> defaultPanelValue) {
        this.defaultPanelValue = defaultPanelValue;
    }

    public String getTemplateTitleLocation() {
        return this.templateTitleLocation;
    }

    public void setTemplateTitleLocation(String templateTitleLocation) {
        this.templateTitleLocation = templateTitleLocation;
    }

    public String getPanelRemoveButtonLocation() {
        return this.panelRemoveButtonLocation;
    }

    public void setPanelRemoveButtonLocation(String panelRemoveButtonLocation) {
        this.panelRemoveButtonLocation = panelRemoveButtonLocation;
    }

    public String getKeyDuplicationError() {
        return this.keyDuplicationError;
    }

    public void setKeyDuplicationError(String keyDuplicationError) {
        this.keyDuplicationError = keyDuplicationError;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}

