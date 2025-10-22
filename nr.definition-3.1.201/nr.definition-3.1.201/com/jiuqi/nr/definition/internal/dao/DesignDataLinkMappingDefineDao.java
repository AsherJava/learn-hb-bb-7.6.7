/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignDataLinkMappingDefineDao
extends BaseDao {
    private Class<DesignDataLinkMappingDefineImpl> implClz = DesignDataLinkMappingDefineImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public List<DesignDataLinkMappingDefine> getByFormKey(String formKey) {
        return super.list(new String[]{"formKey"}, new Object[]{formKey}, this.implClz);
    }

    public void deleteByFormKey(String formKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formKey"}, new Object[]{formKey});
    }

    public void insertDataLinkMapping(String formKey, List<DesignDataLinkMappingDefine> content) throws BeanParaException, DBParaException {
        if (null == content || content.isEmpty()) {
            this.deleteByFormKey(formKey);
            return;
        }
        this.deleteByFormKey(formKey);
        super.insert(content.toArray());
    }
}

