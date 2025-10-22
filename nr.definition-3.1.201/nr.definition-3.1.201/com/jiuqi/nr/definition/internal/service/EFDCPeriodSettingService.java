/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.internal.dao.EFDCPeriodSettingDao;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EFDCPeriodSettingService {
    @Autowired
    private EFDCPeriodSettingDao efdcPeriodSettingDao;

    public EFDCPeriodSettingDefineImpl queryByKey(String key) {
        return this.efdcPeriodSettingDao.queryByKey(key);
    }

    public EFDCPeriodSettingDefineImpl queryByFormulaSchemeKey(String formulaSchemeKey) {
        return this.efdcPeriodSettingDao.queryByFormulaSchemeKey(formulaSchemeKey);
    }

    public void insertEFDCPeriodSetting(EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl) throws Exception {
        if (StringUtils.isEmpty((String)efdcPeriodSettingDefineImpl.getKey())) {
            efdcPeriodSettingDefineImpl.setKey(UUIDUtils.getKey());
        }
        this.efdcPeriodSettingDao.insert(efdcPeriodSettingDefineImpl);
    }

    public void updateEFDCPeriodSetting(EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl) throws Exception {
        this.efdcPeriodSettingDao.update(efdcPeriodSettingDefineImpl);
    }

    public void deleteEFDCPeriodSetting(String key) throws Exception {
        this.efdcPeriodSettingDao.deleteEFDCPeriodSetting(key);
    }

    public void deleteEFDCPeriodSettingByFSKey(String formulaSchemeKey) throws Exception {
        this.efdcPeriodSettingDao.deleteEFDCPeriodSettingByFSKey(formulaSchemeKey);
    }
}

