/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter;

import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import java.util.Map;

public interface EntityDataAdapter {
    public EntityResultSet getAllRows(IEntityQueryParam var1);

    public EntityResultSet getRootRows(IEntityQueryParam var1);

    public EntityResultSet getChildRows(IEntityQueryParam var1, String ... var2);

    public EntityResultSet getAllChildRows(IEntityQueryParam var1, String var2);

    public EntityResultSet findByEntityKeys(IEntityQueryParam var1);

    public String getParent(IEntityQueryParam var1, String var2);

    public String getParent(IEntityQueryParam var1, Map<String, Object> var2);

    public int getMaxDepth(IEntityQueryParam var1);

    public int getMaxDepthByEntityKey(IEntityQueryParam var1, String var2);

    public EntityResultSet findByCode(IEntityQueryParam var1);

    public int getDirectChildCount(IEntityQueryParam var1, String var2);

    public int getAllChildCount(IEntityQueryParam var1, String var2);

    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam var1, String var2);

    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam var1, String var2);

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam var1, String var2);

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam var1, Map<String, Object> var2);

    public int getTotalCount(IEntityQueryParam var1);

    public EntityUpdateResult insertRows(IEntityUpdateParam var1) throws EntityUpdateException;

    public EntityUpdateResult deleteRows(IEntityDeleteParam var1) throws EntityUpdateException;

    public EntityUpdateResult updateRows(IEntityUpdateParam var1) throws EntityUpdateException;

    public EntityCheckResult rowsCheck(IEntityUpdateParam var1, boolean var2) throws EntityUpdateException;

    public EntityResultSet simpleQuery(IEntityQueryParam var1);
}

