/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.internal.dao.AbstractReportTemplateDao;
import com.jiuqi.nr.definition.internal.impl.DesignReportTemplateDefineImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DesignReportTemplateDao
extends AbstractReportTemplateDao<DesignReportTemplateDefineImpl> {
    @Override
    public Class<DesignReportTemplateDefineImpl> getClz() {
        return DesignReportTemplateDefineImpl.class;
    }

    public void deleteByKeys(String ... keys) throws BeanParaException, DBParaException {
        if (null == keys || 0 == keys.length) {
            return;
        }
        super.delete((Object[])keys);
    }

    public void deleteByFormScheme(String formSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formSchemeKey"}, (Object[])new String[]{formSchemeKey});
    }
}

