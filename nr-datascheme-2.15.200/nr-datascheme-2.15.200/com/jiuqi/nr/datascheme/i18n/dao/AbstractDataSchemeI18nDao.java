/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.dao;

import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.util.List;
import org.springframework.util.StringUtils;

public abstract class AbstractDataSchemeI18nDao<D extends DataSchemeI18nDO>
extends BaseDao {
    public abstract Class<D> getClz();

    public List<D> getBySchemeKey(String dataSchemeKey, String type) {
        if (StringUtils.hasLength(type)) {
            return super.list(new String[]{"DI_DS_KEY", "DI_TYPE"}, new Object[]{dataSchemeKey, type}, this.getClz());
        }
        return super.list(new String[]{"DI_DS_KEY"}, new Object[]{dataSchemeKey}, this.getClz());
    }

    public D getByFieldKey(String dataFieldKey, String type) {
        String sql = String.format(" %s=? and %s=? ", "DI_KEY", "DI_TYPE");
        return (D)((DataSchemeI18nDO)super.getBy(sql, new Object[]{dataFieldKey, type}, this.getClz()));
    }
}

