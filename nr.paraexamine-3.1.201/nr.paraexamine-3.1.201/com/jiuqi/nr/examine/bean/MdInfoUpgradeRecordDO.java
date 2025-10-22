/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.examine.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAMEXAMINE_MDINFO_UPGRADE")
public class MdInfoUpgradeRecordDO {
    @DBAnno.DBField(dbField="UPGRADE_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="UPGRADE_DS_KEY")
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="UPGRADE_SUCCEED", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean upgradeSucceed;
    @DBAnno.DBField(dbField="UPGRADE_MESSAGE", dbType=Clob.class)
    private String upgradeMessage;
    @DBAnno.DBField(dbField="UPGRADE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, isOrder=true, autoDate=true)
    private Date upgradeTime;
    @DBAnno.DBField(dbField="UPGRADE_PARAMS", dbType=Clob.class)
    private String upgradeParams;
    @DBAnno.DBField(dbField="UPGRADE_DATA_RECORD", dbType=Clob.class)
    private String dataUpgradeRecord;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
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

    public Date getUpgradeTime() {
        return this.upgradeTime;
    }

    public void setUpgradeTime(Date upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public String getUpgradeParams() {
        return this.upgradeParams;
    }

    public void setUpgradeParams(String upgradeParams) {
        this.upgradeParams = upgradeParams;
    }

    public String getDataUpgradeRecord() {
        return this.dataUpgradeRecord;
    }

    public void setDataUpgradeRecord(String dataUpgradeRecord) {
        this.dataUpgradeRecord = dataUpgradeRecord;
    }
}

