/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.util.Map;

public class FormUploadState {
    private Map<String, String> stateDisMap;
    private Map<String, String> formsState;
    private boolean isIcon;

    public Map<String, String> getStateDisMap() {
        return this.stateDisMap;
    }

    public void setStateDisMap(Map<String, String> stateDisMap) {
        this.stateDisMap = stateDisMap;
    }

    public Map<String, String> getFormsState() {
        return this.formsState;
    }

    public void setFormsState(Map<String, String> formsState) {
        this.formsState = formsState;
    }

    public boolean isIcon() {
        return this.isIcon;
    }

    public void setIsIcon(boolean icon) {
        this.isIcon = icon;
    }
}

