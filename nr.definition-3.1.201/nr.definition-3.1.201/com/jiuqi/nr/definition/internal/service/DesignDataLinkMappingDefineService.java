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
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDataLinkMappingDefineDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignDataLinkMappingDefineService {
    @Autowired
    private DesignDataLinkMappingDefineDao designDataLinkMappingDefineDao;

    public void saveOrUpdateDataLinkMapping(String formKey, List<DesignDataLinkMappingDefine> content) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.insertDataLinkMapping(formKey, content);
    }

    public List<DesignDataLinkMappingDefine> getDataLinkMapping(String formKey) {
        return this.designDataLinkMappingDefineDao.getByFormKey(formKey);
    }

    public void deleteDataLinkMappingByFormKey(String formKey) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.deleteByFormKey(formKey);
    }

    public void insertDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.insert(designDataLinkMappingDefine);
    }

    public void insertDataLinkMappingDefine(DesignDataLinkMappingDefine[] designDataLinkMappingDefines) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.insert(designDataLinkMappingDefines);
    }

    public void updateDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.update(designDataLinkMappingDefine);
    }

    public void updateDataLinkMappingDefine(DesignDataLinkMappingDefine[] designDataLinkMappingDefines) throws BeanParaException, DBParaException {
        this.designDataLinkMappingDefineDao.update(designDataLinkMappingDefines);
    }
}

