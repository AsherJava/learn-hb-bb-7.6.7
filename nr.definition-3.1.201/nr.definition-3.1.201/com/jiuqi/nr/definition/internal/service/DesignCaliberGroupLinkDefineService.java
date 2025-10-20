/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.internal.dao.DesignCaliberGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignCaliberGroupLinkDefineService {
    @Autowired
    private DesignCaliberGroupLinkDao caliberDao;

    public List<DesignCaliberGroupLink> getAllLink(String caliberKey) throws Exception {
        return this.caliberDao.getCaliberGroupLinksByCaliberId(caliberKey);
    }

    public void updateCaliberLink(DesignCaliberGroupLink caliberGroupLink) throws BeanParaException, DBParaException {
        this.caliberDao.updateLink(caliberGroupLink);
    }

    public void deleteCaliberLink(DesignCaliberGroupLink groupLink) throws Exception {
        this.caliberDao.deleteLink(groupLink);
    }
}

