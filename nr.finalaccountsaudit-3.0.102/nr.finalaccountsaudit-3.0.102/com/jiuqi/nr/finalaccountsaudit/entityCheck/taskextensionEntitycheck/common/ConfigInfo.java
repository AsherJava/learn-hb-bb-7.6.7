/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.MatchingInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConfigInfo {
    private String assTaskKey;
    private String assTaskTitle;
    private String assFormSchemeKey;
    private String assFormSchemeTitle;
    private Association association;
    private MatchingInfo matchingInfo;
    private ConfigData configData;
    private List<String> filterValue;
    private String sndmFormula;
    private String expandConfiguration;

    public String getExpandConfiguration() {
        return this.expandConfiguration;
    }

    public void setExpandConfiguration(String expandConfiguration) {
        this.expandConfiguration = expandConfiguration;
    }

    public String getAssTaskTitle() {
        return this.assTaskTitle;
    }

    public void setAssTaskTitle(String assTaskTitle) {
        this.assTaskTitle = assTaskTitle;
    }

    public String getAssFormSchemeTitle() {
        return this.assFormSchemeTitle;
    }

    public void setAssFormSchemeTitle(String assFormSchemeTitle) {
        this.assFormSchemeTitle = assFormSchemeTitle;
    }

    public String getSndmFormula() {
        return this.sndmFormula;
    }

    public void setSndmFormula(String sndmFormula) {
        this.sndmFormula = sndmFormula;
    }

    public List<String> getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(List<String> filterValue) {
        this.filterValue = filterValue;
    }

    public MatchingInfo getMatchingInfo() {
        return this.matchingInfo;
    }

    public void setMatchingInfo(MatchingInfo matchingInfo) {
        this.matchingInfo = matchingInfo;
    }

    public String getAssTaskKey() {
        return this.assTaskKey;
    }

    public void setAssTaskKey(String assTaskKey) {
        this.assTaskKey = assTaskKey;
    }

    public String getAssFormSchemeKey() {
        return this.assFormSchemeKey;
    }

    public void setAssFormSchemeKey(String assFormSchemeKey) {
        this.assFormSchemeKey = assFormSchemeKey;
    }

    public Association getAssociation() {
        return this.association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public ConfigData getConfigData() {
        return this.configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}

