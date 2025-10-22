/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.subsystem.core.dao.DO.MainServerDO
 *  com.jiuqi.nvwa.subsystem.core.dao.IMainServerDao
 *  com.jiuqi.nvwa.subsystem.core.dao.ISubServerLevelDao
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServerLevel
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.subsystem.core.dao.DO.MainServerDO;
import com.jiuqi.nvwa.subsystem.core.dao.IMainServerDao;
import com.jiuqi.nvwa.subsystem.core.dao.ISubServerLevelDao;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServerLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GcParamLevellanager
implements IParamLevelManager {
    @Autowired
    ReportDataSyncServerInfoClient reportDataSyncServerInfoClient;

    public boolean isOpenParamLevel() {
        IMainServerDao mainServerDao = (IMainServerDao)SpringBeanUtils.getBean(IMainServerDao.class);
        MainServerDO main = mainServerDao.getMain();
        return main != null && main.isEnable() && main.isEnableLevel();
    }

    public SubServerLevel getLevel() {
        SubServerLevel noneLevel = new SubServerLevel();
        IMainServerDao mainServerDao = (IMainServerDao)SpringBeanUtils.getBean(IMainServerDao.class);
        MainServerDO main = mainServerDao.getMain();
        if (main == null || !main.isEnable() || !main.isEnableLevel()) {
            noneLevel.setValue(0);
            noneLevel.setTitle("\u672a\u542f\u7528\u53c2\u6570\u5c42\u7ea7");
            return noneLevel;
        }
        ISubServerLevelDao subServerLevelDao = (ISubServerLevelDao)SpringBeanUtils.getBean(ISubServerLevelDao.class);
        for (SubServerLevel level : subServerLevelDao.allLevels()) {
            if (level.getValue() != main.getServerLevel()) continue;
            return level;
        }
        noneLevel.setValue(main.getServerLevel());
        noneLevel.setTitle("\u672a\u5b9a\u4e49\u7684\u5c42\u7ea7\uff1a" + noneLevel.getValue());
        return noneLevel;
    }
}

