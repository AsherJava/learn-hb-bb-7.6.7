/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.BeanParaException
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.internal.dao.DesignEntityLinkageDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignEntityLinkageDefineImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignEntityLinkageDefineService {
    @Autowired
    private DesignEntityLinkageDefineDao linkageDefineDao;

    public String insertDesignEntityLinkageDefine(DesignEntityLinkageDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.linkageDefineDao.insert(define);
        return define.getKey();
    }

    public DesignEntityLinkageDefine queryDesignEntityLinkageDefine(String taskKey) throws BeanParaException {
        return (DesignEntityLinkageDefine)this.linkageDefineDao.getByKey(taskKey, DesignEntityLinkageDefineImpl.class);
    }

    public void updateEntityLinkageDefine(DesignEntityLinkageDefine define) throws Exception {
        this.linkageDefineDao.update(define);
    }
}

