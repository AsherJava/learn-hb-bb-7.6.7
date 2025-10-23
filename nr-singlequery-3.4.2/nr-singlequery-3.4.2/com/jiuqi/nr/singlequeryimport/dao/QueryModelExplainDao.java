/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain
 */
package com.jiuqi.nr.singlequeryimport.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryModelExplainDao
extends BaseDao {
    private Class<QueryModelExplain> implClass = QueryModelExplain.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public QueryModelExplain getExplain(String modelId, String code, String period) throws Exception {
        return (QueryModelExplain)this.getBy(" model_id = ? and org_code = ? and period = ?", new Object[]{modelId, code, period}, this.implClass);
    }

    public int deleteExplain(String modelId, String code, String period) throws Exception {
        return this.deleteBy(new String[]{"modelId", "orgCode", "period"}, new Object[]{modelId, code, period});
    }

    public int insertExplain(QueryModelExplain explain) throws Exception {
        return this.insert(explain);
    }

    public int updateExplain(QueryModelExplain explain) throws Exception {
        return this.update(explain);
    }

    public List<QueryModelExplain> getExplainListByModeId(String key, String period) throws Exception {
        return this.list(new String[]{"modelId", "period"}, new Object[]{key, period}, this.implClass);
    }

    public int[] insertExplainList(List<QueryModelExplain> queryModelExplainList) throws Exception {
        return this.insert(queryModelExplainList.toArray());
    }

    public int[] upDataExplainList(List<QueryModelExplain> queryModelExplainList) throws Exception {
        return this.update(queryModelExplainList.toArray());
    }

    public int deleteExplainByModelId(String modelId, String period) throws Exception {
        return this.deleteBy(new String[]{"modelId", "period"}, new Object[]{modelId, period});
    }
}

