/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignCaliberGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignCaliberGroupLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignCaliberLinkService {
    @Autowired
    private DesignCaliberGroupLinkDao caliberGroupLinkDao;

    public String insertCaliberGroupDefine(DesignCaliberGroupDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.caliberGroupLinkDao.insert(define);
        return define.getKey();
    }
}

