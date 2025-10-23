/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource
 *  com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataQuery
 *  com.jiuqi.nr.entity.adapter.provider.QueryOptions$TreeType
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nr.entity.param.impl.EntityQueryParam
 */
package com.jiuqi.nr.subdatabase.org.ext;

import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataQuery;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class OrgDataSubTableQueryProvider
extends OrgDataQuery {
    public OrgDataSubTableQueryProvider(OrgDataSource orgDataSource, IEntityQueryParam query, String entityId) {
        super(orgDataSource, query);
        EntityQueryParam queryParam = new EntityQueryParam();
        BeanUtils.copyProperties(query, queryParam);
        queryParam.setEntityId(entityId);
        this.query = queryParam;
    }

    public EntityResultSet getAllData() {
        return super.getAllData();
    }

    public EntityResultSet simpleQueryByKeys() {
        return super.simpleQueryByKeys();
    }

    public EntityResultSet getRootData() {
        return super.getRootData();
    }

    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... entityKeyData) {
        return super.getChildData(treeType, entityKeyData);
    }

    public EntityResultSet findByEntityKeys() {
        return super.findByEntityKeys();
    }

    public String getParent(String queryCode) {
        return super.getParent(queryCode);
    }

    public String getParent(Map<String, Object> rowData) {
        return super.getParent(rowData);
    }

    public int getMaxDepth() {
        return super.getMaxDepth();
    }

    public int getMaxDepthByEntityKey(String entityKeyData) {
        return super.getMaxDepthByEntityKey(entityKeyData);
    }

    public EntityResultSet findByCodes() {
        return super.findByCodes();
    }

    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        return super.getChildCount(entityKeyData, treeType);
    }

    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        return super.getChildCountByParent(parentKey, treeType);
    }

    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        return super.getParentsEntityKeyDataPath(entityKeyData);
    }

    public String[] getParentsEntityKeyDataPath(Map<String, Object> rowData) {
        return super.getParentsEntityKeyDataPath(rowData);
    }

    public int getTotalCount() {
        return super.getTotalCount();
    }
}

