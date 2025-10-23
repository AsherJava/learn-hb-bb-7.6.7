/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeDesignService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataSchemeServiceImpl
implements DataSchemeDesignService {
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;

    @Override
    public List<DesignDataScheme> searchByKeyword(String keyword) {
        Assert.notNull((Object)keyword, "title must not be null.");
        List<DesignDataSchemeDO> r = this.dataSchemeDao.searchBy(keyword);
        return new ArrayList<DesignDataScheme>(r);
    }

    @Override
    public DesignDataScheme getByParentAndTitle(String title, String parent) {
        Assert.notNull((Object)title, "title must not be null.");
        Assert.notNull((Object)parent, "parent must not be null.");
        return this.dataSchemeDao.getBy(title, parent).stream().findFirst().orElse(null);
    }
}

