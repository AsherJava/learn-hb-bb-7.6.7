/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.dao.IBizResDataDao;
import com.jiuqi.va.bizmeta.domain.bizres.BizResDataDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResDataDTO;
import com.jiuqi.va.bizmeta.service.IBizResDataService;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizResDataServiceImpl
implements IBizResDataService {
    @Autowired
    private IBizResDataDao bizResDataDao;

    @Override
    public R add(BizResDataDO bizResDataDO) {
        return this.bizResDataDao.insert((Object)bizResDataDO) == 0 ? R.error((String)"\u8d44\u6e90\u6587\u4ef6\u65b0\u589e\u5931\u8d25") : R.ok();
    }

    @Override
    public R deletes(BizResDataDTO bizResDataDTO) {
        return null;
    }
}

