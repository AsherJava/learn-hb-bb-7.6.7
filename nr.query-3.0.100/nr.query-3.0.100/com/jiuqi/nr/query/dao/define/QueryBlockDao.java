/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.dao.impl.TransUtil;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryBlockDao
extends BaseDao {
    private String modelId = "modelID";
    private Class<QueryBlockDefine> implClass = QueryBlockDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<QueryBlockDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void delete(String[] keys) throws Exception {
        this.delete(keys);
    }

    public void deleteBydefinelId(String modelID) throws Exception {
        this.deleteBy(new String[]{this.modelId}, new Object[]{modelID});
    }

    public QueryBlockDefine queryDefineById(String id) throws Exception {
        return (QueryBlockDefine)this.getByKey(id, this.implClass);
    }

    public List<QueryBlockDefine> queryDefineByModelId(String id) throws Exception {
        List defines = this.list(new String[]{this.modelId}, new Object[]{id}, this.implClass);
        return defines;
    }

    public int insertDefine(QueryBlockDefine define) throws Exception {
        define.converBlockToByte();
        return this.insert(define);
    }

    public int[] insertDefines(List<QueryBlockDefine> defines) throws Exception {
        for (QueryBlockDefine block : defines) {
            block.converBlockToByte();
        }
        return this.insert(defines.toArray());
    }

    public int updateDefine(QueryBlockDefine define) throws Exception {
        define.converBlockToByte();
        return this.update(define);
    }
}

