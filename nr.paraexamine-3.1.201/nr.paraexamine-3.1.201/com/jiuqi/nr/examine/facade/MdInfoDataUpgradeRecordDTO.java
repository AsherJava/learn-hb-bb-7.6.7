/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.facade;

import java.util.HashMap;
import java.util.Map;

public class MdInfoDataUpgradeRecordDTO {
    private boolean upgradeSucceed = false;
    private String upgradeMessage = "\u672a\u5347\u7ea7";
    private String sourceTableKey;
    private String sourceTableName;
    private String mdInfoTableName;
    private Map<String, String> sourceFiledNameMap = new HashMap<String, String>();
    private Map<String, String> mdInfoFieldNameMap = new HashMap<String, String>();

    public MdInfoDataUpgradeRecordDTO() {
    }

    public MdInfoDataUpgradeRecordDTO(String sourceTableKey, String sourceTableName, String mdInfoTableName) {
        this();
        this.sourceTableKey = sourceTableKey;
        this.sourceTableName = sourceTableName;
        this.mdInfoTableName = mdInfoTableName;
    }

    public boolean isUpgradeSucceed() {
        return this.upgradeSucceed;
    }

    public void setUpgradeSucceed(boolean upgradeSucceed) {
        this.upgradeSucceed = upgradeSucceed;
    }

    public String getUpgradeMessage() {
        return this.upgradeMessage;
    }

    public void setUpgradeMessage(String upgradeMessage) {
        this.upgradeMessage = upgradeMessage;
    }

    public String getSourceTableKey() {
        return this.sourceTableKey;
    }

    public void setSourceTableKey(String sourceTableKey) {
        this.sourceTableKey = sourceTableKey;
    }

    public String getSourceTableName() {
        return this.sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getMdInfoTableName() {
        return this.mdInfoTableName;
    }

    public void setMdInfoTableName(String mdInfoTableName) {
        this.mdInfoTableName = mdInfoTableName;
    }

    public Map<String, String> getSourceFiledNameMap() {
        return this.sourceFiledNameMap;
    }

    public void setSourceFiledNameMap(Map<String, String> sourceFiledNameMap) {
        this.sourceFiledNameMap = sourceFiledNameMap;
    }

    public Map<String, String> getMdInfoFieldNameMap() {
        return this.mdInfoFieldNameMap;
    }

    public void setMdInfoFieldNameMap(Map<String, String> mdInfoFieldNameMap) {
        this.mdInfoFieldNameMap = mdInfoFieldNameMap;
    }
}

