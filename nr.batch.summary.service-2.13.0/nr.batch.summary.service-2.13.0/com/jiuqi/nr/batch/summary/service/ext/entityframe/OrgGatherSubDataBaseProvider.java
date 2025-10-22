/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.internal.adapter.CalibreAdapter
 *  com.jiuqi.nr.calibre2.internal.adapter.CalibreResultSet
 *  com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataResultSet
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.QueryOptions$TreeType
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.BaseDataGatherResultSet;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CalibreGatherResultSet;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreAdapter;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreResultSet;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataResultSet;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import java.util.Map;

public class OrgGatherSubDataBaseProvider
implements IDataQueryProvider {
    private boolean isCalibre;
    private final IEntityQueryParam query;
    private IDataQueryProvider entityQuery;
    private CalibreAdapter calibreAdapter;
    private Map<String, CorporateEntityData> corporateColumn2Value;

    public OrgGatherSubDataBaseProvider(IEntityQueryParam query, IDataQueryProvider entityQuery, Map<String, CorporateEntityData> corporateColumn2Value) {
        this.query = query;
        this.entityQuery = entityQuery;
        this.corporateColumn2Value = corporateColumn2Value;
    }

    public OrgGatherSubDataBaseProvider(IEntityQueryParam query, CalibreAdapter calibreAdapter, Map<String, CorporateEntityData> corporateColumn2Value) {
        this.query = query;
        this.isCalibre = true;
        this.calibreAdapter = calibreAdapter;
        this.corporateColumn2Value = corporateColumn2Value;
    }

    public EntityResultSet getAllData() {
        if (this.isCalibre) {
            EntityResultSet resultSet = this.calibreAdapter.getAllRows(this.query);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.getAllData();
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public EntityResultSet simpleQueryByKeys() {
        if (this.isCalibre) {
            EntityResultSet resultSet = this.calibreAdapter.simpleQuery(this.query);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.simpleQueryByKeys();
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public EntityResultSet getRootData() {
        if (this.isCalibre) {
            EntityResultSet resultSet = this.calibreAdapter.getRootRows(this.query);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.getRootData();
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... entityKeyData) {
        if (entityKeyData == null) {
            return null;
        }
        if (this.isCalibre) {
            EntityResultSet resultSet = QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType) ? this.calibreAdapter.getChildRows(this.query, entityKeyData) : this.calibreAdapter.getAllChildRows(this.query, entityKeyData[0]);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.getChildData(treeType, entityKeyData);
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public EntityResultSet findByEntityKeys() {
        if (this.isCalibre) {
            EntityResultSet resultSet = this.calibreAdapter.findByEntityKeys(this.query);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.findByEntityKeys();
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public String getParent(String queryCode) {
        if (this.isCalibre) {
            return this.calibreAdapter.getParent(this.query, queryCode);
        }
        return this.entityQuery.getParent(queryCode);
    }

    public String getParent(Map<String, Object> rowData) {
        if (this.isCalibre) {
            return this.calibreAdapter.getParent(this.query, rowData);
        }
        return this.entityQuery.getParent(rowData);
    }

    public int getMaxDepth() {
        if (this.isCalibre) {
            return this.calibreAdapter.getMaxDepth(this.query);
        }
        return this.entityQuery.getMaxDepth();
    }

    public int getMaxDepthByEntityKey(String entityKeyData) {
        if (this.isCalibre) {
            return this.calibreAdapter.getMaxDepthByEntityKey(this.query, entityKeyData);
        }
        return this.entityQuery.getMaxDepthByEntityKey(entityKeyData);
    }

    public EntityResultSet findByCodes() {
        if (this.isCalibre) {
            EntityResultSet resultSet = this.calibreAdapter.findByCode(this.query);
            return new CalibreGatherResultSet((CalibreResultSet)resultSet, this.corporateColumn2Value);
        }
        EntityResultSet resultSet = this.entityQuery.findByCodes();
        return new BaseDataGatherResultSet((BaseDataResultSet)resultSet, this.corporateColumn2Value);
    }

    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        if (this.isCalibre) {
            if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                return this.calibreAdapter.getDirectChildCount(this.query, entityKeyData);
            }
            return this.calibreAdapter.getAllChildCount(this.query, entityKeyData);
        }
        return this.entityQuery.getChildCount(entityKeyData, treeType);
    }

    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        if (this.isCalibre) {
            if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                return this.calibreAdapter.getDirectChildCountByParent(this.query, parentKey);
            }
            return this.calibreAdapter.getAllChildCountByParent(this.query, parentKey);
        }
        return this.entityQuery.getChildCountByParent(parentKey, treeType);
    }

    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        if (this.isCalibre) {
            return this.calibreAdapter.getParentsEntityKeyDataPath(this.query, entityKeyData);
        }
        return this.entityQuery.getParentsEntityKeyDataPath(entityKeyData);
    }

    public String[] getParentsEntityKeyDataPath(Map<String, Object> rowData) {
        if (this.isCalibre) {
            return this.calibreAdapter.getParentsEntityKeyDataPath(this.query, rowData);
        }
        return this.entityQuery.getParentsEntityKeyDataPath(rowData);
    }

    public int getTotalCount() {
        if (this.isCalibre) {
            return this.calibreAdapter.getTotalCount(this.query);
        }
        return this.entityQuery.getTotalCount();
    }
}

