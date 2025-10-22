/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import com.jiuqi.nr.definition.internal.impl.FormFieldInfoDefineImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormFieldInfoDefineDao
extends BaseDao {
    private static final String GET_BY_TABLE = String.format("SELECT A.%s, A.%s, A.%s, F.%s FROM %s A LEFT JOIN %s F ON F.%s = A.%s LEFT JOIN %s B ON A.%s = B.%s WHERE B.%s = ?", "TASK_KEY", "FORM_KEY", "FIELD_KEY", "FM_FORMSCHEME", "NR_PARAM_FORM_FIELD_INFO", "NR_PARAM_FORM", "FM_KEY", "FORM_KEY", "NR_DATASCHEME_FIELD", "FIELD_KEY", "DF_KEY", "DF_DT_KEY");

    public Class<FormFieldInfoDefineImpl> getClz() {
        return FormFieldInfoDefineImpl.class;
    }

    public void deleteByTask(String taskKey) {
        super.deleteBy(new String[]{"TASK_KEY"}, new Object[]{taskKey});
    }

    public List<FormFieldInfoDefine> getByDataTableKey(String dataTableKey) {
        return (List)this.jdbcTemplate.query(GET_BY_TABLE, ps -> ps.setString(1, dataTableKey), rs -> {
            ArrayList<FormFieldInfoDefineImpl> infos = new ArrayList<FormFieldInfoDefineImpl>();
            while (rs.next()) {
                FormFieldInfoDefineImpl info = new FormFieldInfoDefineImpl();
                info.setTaskKey(rs.getString(1));
                info.setFormKey(rs.getString(2));
                info.setFieldKey(rs.getString(3));
                info.setFormSchemeKey(rs.getString(4));
                infos.add(info);
            }
            return infos;
        });
    }
}

