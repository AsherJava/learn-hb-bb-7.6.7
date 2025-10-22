/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.language;

import com.jiuqi.nr.datascheme.i18n.language.LanguageTypeDO;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class LanguageTypeDao
extends BaseDao {
    public Class<LanguageTypeDO> getClz() {
        return LanguageTypeDO.class;
    }

    public LanguageTypeDO query(String languageTypeKey) {
        if (StringUtils.hasLength(languageTypeKey)) {
            return super.getByKey(languageTypeKey, this.getClz());
        }
        return null;
    }

    public List<LanguageTypeDO> queryAll() {
        return super.list(this.getClz());
    }

    public LanguageTypeDO queryDefault() {
        return super.getBy(" LT_ISDEFAULT=? ", new Object[]{"1"}, this.getClz());
    }

    public LanguageTypeDO queryByMsg(String msg) {
        return super.getBy(" LT_MESSAGE=? ", new Object[]{msg}, this.getClz());
    }
}

