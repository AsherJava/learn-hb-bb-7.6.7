/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;

public class BaseDataSchemeVO
extends BaseDataVO {
    private String prefix;
    private boolean autoGeneration;
    private int deployState = -1;
    private String dataGroupKey;
    private String creatorTitle;
    private String creator;
    private DataSchemeType type;
    private boolean readonly;
    private String level;
    private boolean enableGatherDB;
    private String zbSchemeKey;
    private String zbSchemeTitle;
    private String zbSchemeVersion;
    private Integer zbSchemeVersionStatus;
    private String zbSchemePeriod;
    private String zbSchemePeriodTitle;
    private String encryptScene;
    private String calibre;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isAutoGeneration() {
        return this.autoGeneration;
    }

    public void setAutoGeneration(boolean autoGeneration) {
        this.autoGeneration = autoGeneration;
    }

    public int getDeployState() {
        return this.deployState;
    }

    public void setDeployState(int deployState) {
        this.deployState = deployState;
    }

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public DataSchemeType getType() {
        return this.type;
    }

    public void setType(DataSchemeType type) {
        this.type = type;
    }

    public String getCreatorTitle() {
        return this.creatorTitle;
    }

    public void setCreatorTitle(String creatorTitle) {
        this.creatorTitle = creatorTitle;
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isEnableGatherDB() {
        return this.enableGatherDB;
    }

    public void setEnableGatherDB(boolean enableGatherDB) {
        this.enableGatherDB = enableGatherDB;
    }

    public String getEncryptScene() {
        return this.encryptScene;
    }

    public void setEncryptScene(String encryptScene) {
        this.encryptScene = encryptScene;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeTitle() {
        return this.zbSchemeTitle;
    }

    public void setZbSchemeTitle(String zbSchemeTitle) {
        this.zbSchemeTitle = zbSchemeTitle;
    }

    public Boolean getRelatedZbScheme() {
        return this.getZbSchemeKey() != null && this.getZbSchemeVersion() != null;
    }

    public Boolean isRelatedZbScheme() {
        return this.getZbSchemeKey() != null && this.getZbSchemeVersion() != null;
    }

    public String getCalibre() {
        return this.calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public Boolean isEnableCalibre() {
        return this.calibre != null && !this.calibre.isEmpty();
    }

    public String getZbSchemeVersion() {
        return this.zbSchemeVersion;
    }

    public void setZbSchemeVersion(String zbSchemeVersion) {
        this.zbSchemeVersion = zbSchemeVersion;
    }

    public String getZbSchemePeriodTitle() {
        return this.zbSchemePeriodTitle;
    }

    public void setZbSchemePeriodTitle(String zbSchemePeriodTitle) {
        this.zbSchemePeriodTitle = zbSchemePeriodTitle;
    }

    public String getZbSchemePeriod() {
        return this.zbSchemePeriod;
    }

    public void setZbSchemePeriod(String zbSchemePeriod) {
        this.zbSchemePeriod = zbSchemePeriod;
    }

    public Integer getZbSchemeVersionStatus() {
        return this.zbSchemeVersionStatus;
    }

    public void setZbSchemeVersionStatus(Integer zbSchemeVersionStatus) {
        this.zbSchemeVersionStatus = zbSchemeVersionStatus;
    }
}

