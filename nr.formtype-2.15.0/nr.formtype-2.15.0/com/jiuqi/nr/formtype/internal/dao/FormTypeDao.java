/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.formtype.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDefineImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class FormTypeDao
extends BaseDao {
    public Class<FormTypeDefineImpl> getClz() {
        return FormTypeDefineImpl.class;
    }

    public List<FormTypeDefine> getAll() {
        return super.list(this.getClz());
    }

    public FormTypeDefine getById(String id) {
        return (FormTypeDefine)super.getByKey((Object)id, this.getClz());
    }

    public List<FormTypeDefine> getByIds(String ... ids) {
        if (null == ids || 0 == ids.length) {
            return Collections.emptyList();
        }
        if (1 == ids.length) {
            return Collections.singletonList(this.getById(ids[0]));
        }
        String sqlWhere = String.format(" %s in (%s) ", "FT_ID", Arrays.stream(ids).map(id -> "?").collect(Collectors.joining(",")));
        return super.list(sqlWhere, (Object[])ids, this.getClz());
    }

    public FormTypeDefine getByCode(String code) {
        String sqlWhere = String.format(" %s = ? ", "FT_CODE");
        return (FormTypeDefine)super.getBy(sqlWhere, new Object[]{code}, this.getClz());
    }

    public List<FormTypeDefine> getByCodes(String ... codes) {
        if (null == codes || 0 == codes.length) {
            return Collections.emptyList();
        }
        if (1 == codes.length) {
            String sqlWhere = String.format(" %s = ? ", "FT_CODE");
            FormTypeDefine formTypeDefine = (FormTypeDefine)super.getBy(sqlWhere, (Object[])codes, this.getClz());
            if (null == formTypeDefine) {
                return Collections.emptyList();
            }
            return Collections.singletonList(formTypeDefine);
        }
        String sqlWhere = String.format(" %s in (%s) ", "FT_ID", Arrays.stream(codes).map(id -> "?").collect(Collectors.joining(",")));
        return super.list(sqlWhere, (Object[])codes, this.getClz());
    }

    public List<FormTypeDefine> getByGroupId(String groupId) {
        return super.list(new String[]{"groupId"}, new Object[]{groupId}, this.getClz());
    }

    public List<FormTypeDefine> serach(String keyword) {
        if (!StringUtils.hasLength(keyword)) {
            return Collections.emptyList();
        }
        keyword = "%" + keyword + "%";
        return super.list(String.format(" %s LIKE ? OR %s LIKE ? ", "FT_CODE", "FT_TITLE"), new Object[]{keyword, keyword}, this.getClz());
    }
}

