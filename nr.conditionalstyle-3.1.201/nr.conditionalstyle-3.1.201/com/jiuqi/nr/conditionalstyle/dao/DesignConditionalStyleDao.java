/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.conditionalstyle.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignConditionalStyleDao
extends BaseDao {
    private Class<DesignConditionalStyleImpl> implClz = DesignConditionalStyleImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public List<DesignConditionalStyle> getCSByPos(String formKey, int x, int y) {
        return super.list("CS_FORM_KEY = ? AND CS_POS_X = ? AND CS_POS_Y = ?", new Object[]{formKey, x, y}, this.implClz);
    }

    public List<DesignConditionalStyle> getCSByForm(String formKey) {
        return super.list("CS_FORM_KEY = ?", new Object[]{formKey}, this.implClz);
    }

    public void deleteCS(List<DesignConditionalStyle> param) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("NR_PARAM_CONDITIONAL_STYLE_DES").append(" WHERE CS_KEY = ?");
        String s = sql.toString();
        for (DesignConditionalStyle cs : param) {
            this.jdbcTemplate.update(s, new Object[]{cs.getKey()});
        }
    }
}

