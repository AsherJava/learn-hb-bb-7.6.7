/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.summary.internal.dao.ISummaryConfigDao;
import com.jiuqi.nr.summary.internal.entity.SummaryConfigDO;
import com.jiuqi.nr.summary.internal.service.ISummaryConfigService;
import com.jiuqi.nr.summary.vo.SummaryConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SummaryConfigServiceImpl
implements ISummaryConfigService {
    @Autowired
    private ISummaryConfigDao summaryConfigDao;

    @Override
    public void saveConfig(SummaryConfigVO configVO) {
        SummaryConfigDO configDO = this.summaryConfigDao.queryByMenuId(configVO.getMenuId());
        if (configDO != null) {
            this.summaryConfigDao.update(this.buildConfigDO(configVO));
        } else {
            this.summaryConfigDao.insert(this.buildConfigDO(configVO));
        }
    }

    @Override
    public SummaryConfigVO getConfig(String menuId) {
        SummaryConfigDO configDO = this.summaryConfigDao.queryByMenuId(menuId);
        if (configDO != null) {
            SummaryConfigVO configVO = new SummaryConfigVO();
            configVO.setKey(configDO.getKey());
            configVO.setMenuId(configDO.getMenuId());
            configVO.stringToItem(configDO.getConfig());
            return configVO;
        }
        return null;
    }

    private SummaryConfigDO buildConfigDO(SummaryConfigVO configVO) {
        SummaryConfigDO configDO = new SummaryConfigDO();
        configDO.setKey(configDO.getKey());
        if (!StringUtils.hasLength(configVO.getKey())) {
            configDO.setKey(UUIDUtils.getKey());
        }
        configDO.setMenuId(configVO.getMenuId());
        configDO.setConfig(configVO.itemToString());
        return configDO;
    }
}

