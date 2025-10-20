/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.dao.VaBaseDataCommonlyUsedDao;
import com.jiuqi.va.basedata.domain.BaseDataCommonlyUsedDO;
import com.jiuqi.va.basedata.service.BaseDataCommonlyUsedService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDataCommonlyUsedServiceImpl
implements BaseDataCommonlyUsedService {
    @Autowired
    private VaBaseDataCommonlyUsedDao commonlyUsedDao;

    @Override
    public Set<String> listObjectcode(BaseDataDTO param) {
        return this.commonlyUsedDao.listObjectcode(param);
    }

    @Override
    public R changeData(BaseDataDTO param) {
        BaseDataCommonlyUsedDO usedParam = new BaseDataCommonlyUsedDO();
        usedParam.setUserid(ShiroUtil.getUser().getId());
        usedParam.setDefinename(param.getTableName());
        this.commonlyUsedDao.delete((Object)usedParam);
        for (String objcode : param.getBaseDataObjectcodes()) {
            usedParam.setId(UUID.randomUUID());
            usedParam.setObjectcode(objcode);
            this.commonlyUsedDao.insert((Object)usedParam);
        }
        return R.ok();
    }

    @Override
    public R removeData(BaseDataDTO param) {
        BaseDataCommonlyUsedDO usedParam = new BaseDataCommonlyUsedDO();
        usedParam.setUserid(ShiroUtil.getUser().getId());
        usedParam.setDefinename(param.getTableName());
        for (String objcode : param.getBaseDataObjectcodes()) {
            usedParam.setObjectcode(objcode);
            this.commonlyUsedDao.delete((Object)usedParam);
        }
        return R.ok();
    }

    @Override
    public R countFlag(BaseDataDTO param) {
        param.setCreateuser(ShiroUtil.getUser().getId());
        int cnt = this.commonlyUsedDao.countFlag(param);
        if (cnt == 0) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R changeFlag(BaseDataDTO param) {
        param.setCreateuser(ShiroUtil.getUser().getId());
        this.commonlyUsedDao.removeFlag(param);
        if (param.getStopflag() == 0) {
            param.setId(UUID.randomUUID());
            this.commonlyUsedDao.addFlag(param);
        }
        return R.ok();
    }
}

