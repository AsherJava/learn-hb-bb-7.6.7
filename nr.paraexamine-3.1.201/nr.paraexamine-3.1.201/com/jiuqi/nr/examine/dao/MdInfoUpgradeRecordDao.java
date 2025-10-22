/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.examine.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.examine.bean.MdInfoUpgradeRecordDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MdInfoUpgradeRecordDao
extends BaseDao {
    public Class<MdInfoUpgradeRecordDO> getClz() {
        return MdInfoUpgradeRecordDO.class;
    }

    public List<MdInfoUpgradeRecordDO> getAllMdInfoUpgrade(String dataSchemeKey) {
        return super.list(new String[]{"dataSchemeKey"}, new Object[]{dataSchemeKey}, this.getClz());
    }

    public MdInfoUpgradeRecordDO getLastMdInfoUpgrade(String dataSchemeKey) {
        List<MdInfoUpgradeRecordDO> allMdInfoUpgrade = this.getAllMdInfoUpgrade(dataSchemeKey);
        if (allMdInfoUpgrade.isEmpty()) {
            return null;
        }
        return allMdInfoUpgrade.stream().min((m1, m2) -> m1.getUpgradeTime().compareTo(m2.getUpgradeTime())).orElse(null);
    }

    public MdInfoUpgradeRecordDO getFailedMdInfoUpgrade(String dataSchemeKey) {
        List list = super.list(new String[]{"dataSchemeKey", "upgradeSucceed"}, new Object[]{dataSchemeKey, 0}, this.getClz());
        return list.stream().min((m1, m2) -> m1.getUpgradeTime().compareTo(m2.getUpgradeTime())).orElse(null);
    }

    public void upgradeMdInfoUpgrade(MdInfoUpgradeRecordDO obj) {
        try {
            super.update((Object)obj);
        }
        catch (DBParaException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertMdInfoUpgrade(MdInfoUpgradeRecordDO obj) {
        try {
            super.insert((Object)obj);
        }
        catch (DBParaException e) {
            throw new RuntimeException(e);
        }
    }
}

