/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.dao;

import com.jiuqi.nr.datascheme.i18n.dao.AbstractDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeI18nDao
extends AbstractDataSchemeI18nDao<DataSchemeI18nDO> {
    @Override
    public Class<DataSchemeI18nDO> getClz() {
        return DataSchemeI18nDO.class;
    }
}

