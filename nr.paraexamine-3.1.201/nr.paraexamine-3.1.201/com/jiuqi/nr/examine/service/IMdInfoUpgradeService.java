/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.examine.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeRecordDTO;

public interface IMdInfoUpgradeService {
    public MdInfoUpgradeRecordDTO queryFailedUpgradeRecord(String var1);

    public void insertUpgradeRecord(MdInfoUpgradeRecordDTO var1);

    public void updateUpgradeRecord(MdInfoUpgradeRecordDTO var1);

    public void upgradeParam(MdInfoUpgradeRecordDTO var1) throws JQException;

    public void upgradeMdInfoTable(MdInfoUpgradeRecordDTO var1);

    public void upgradeData(MdInfoUpgradeRecordDTO var1);
}

