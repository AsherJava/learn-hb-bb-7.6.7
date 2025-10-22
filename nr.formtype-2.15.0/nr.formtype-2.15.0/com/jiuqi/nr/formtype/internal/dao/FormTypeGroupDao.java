/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.formtype.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeGroupDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormTypeGroupDao
extends BaseDao {
    public Class<FormTypeGroupDefineImpl> getClz() {
        return FormTypeGroupDefineImpl.class;
    }

    public List<FormTypeGroupDefine> getAll() {
        return super.list(this.getClz());
    }

    public FormTypeGroupDefine getById(String id) {
        return (FormTypeGroupDefine)super.getByKey((Object)id, this.getClz());
    }

    public List<FormTypeGroupDefine> getByGroup(String groupId) {
        return super.list(new String[]{"groupId"}, new Object[]{groupId}, this.getClz());
    }
}

