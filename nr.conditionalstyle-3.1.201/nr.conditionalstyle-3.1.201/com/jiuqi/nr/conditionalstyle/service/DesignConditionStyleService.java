/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.conditionalstyle.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.conditionalstyle.dao.DesignConditionalStyleDao;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignConditionStyleService {
    @Autowired
    DesignConditionalStyleDao conditionalStyleDao;

    public void insertCS(Object[] param) throws DBParaException {
        this.conditionalStyleDao.insert(param);
    }

    public void updateCS(Object[] param) throws DBParaException {
        this.conditionalStyleDao.update(param);
    }

    public void deleteCS(List<DesignConditionalStyle> param) throws DBParaException {
        this.conditionalStyleDao.deleteCS(param);
    }

    public List<DesignConditionalStyle> getCSByPos(String formKey, int x, int y) {
        return this.conditionalStyleDao.getCSByPos(formKey, x, y);
    }

    public List<DesignConditionalStyle> getCSByForm(String formKey) {
        return this.conditionalStyleDao.getCSByForm(formKey);
    }
}

