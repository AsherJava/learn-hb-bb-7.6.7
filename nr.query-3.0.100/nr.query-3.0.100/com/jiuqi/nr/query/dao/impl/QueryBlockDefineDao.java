/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao.impl;

import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.define.QueryBlockDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class QueryBlockDefineDao
implements IQueryBlockDefineDao {
    private static final Logger log = LoggerFactory.getLogger(QueryBlockDefineDao.class);
    @Autowired
    private QueryBlockDao queryDao;

    @Override
    public Boolean InsertQueryBlockDefine(QueryBlockDefine queryBlockDefine) {
        Assert.notNull((Object)queryBlockDefine, "'queryBlockDefine' must not be null");
        try {
            this.clearSortData(queryBlockDefine);
            this.queryDao.insertDefine(queryBlockDefine);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private boolean clearSortData(QueryBlockDefine block) {
        Optional<QueryDimensionDefine> fieldDim;
        List<Object> selectedFields = new ArrayList();
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            selectedFields = fd.getSelectFields();
            for (QuerySelectField querySelectField : selectedFields) {
                querySelectField.setSort(new QueryItemSortDefine());
            }
        }
        return true;
    }

    @Override
    public Boolean UpdateQueryBlockDefine(QueryBlockDefine queryBlockDefine) {
        Assert.notNull((Object)queryBlockDefine, "'queryBlockDefine' must not be null");
        try {
            this.clearSortData(queryBlockDefine);
            int result = this.queryDao.update(queryBlockDefine);
            return result > 0;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean DeleteQueryBlockDefineById(String queryBlockDefineId) {
        try {
            int result = this.queryDao.delete(queryBlockDefineId);
            return result > 0;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public QueryBlockDefine GetQueryBlockDefineById(String queryBlockDefineId) {
        try {
            return this.queryDao.queryDefineById(queryBlockDefineId);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryBlockDefine> GetQueryBlockDefinesByModelId(String modelId) {
        try {
            return this.queryDao.queryDefineByModelId(modelId);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}

