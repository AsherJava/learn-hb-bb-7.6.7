/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.nr.data.access.util.DataPermissionUtil
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.nr.data.access.util.DataPermissionUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.SingleTableDataStrategy;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class SplitSingleTableDataStrategy
extends SingleTableDataStrategy
implements IDataRowReader,
IFieldDataStrategy {
    private final FieldDataProperties fieldDataProperties;

    public SplitSingleTableDataStrategy(FieldDataStrategyFactory factory) {
        super(factory);
        this.fieldDataProperties = factory.getFieldDataProperties();
    }

    @Override
    public void queryTableData(IFieldQueryInfo queryInfo, FieldRelation fieldRelation, IDataReader dataReader) throws CrudException {
        this.relation = fieldRelation;
        this.queryInfo = queryInfo;
        this.dataReader = dataReader;
        Iterator<String> fieldStr = queryInfo.selectFieldItr();
        ArrayList<String> keys = new ArrayList<String>();
        while (fieldStr.hasNext()) {
            keys.add(fieldStr.next());
        }
        List<IMetaData> metaData = fieldRelation.getMetaData(keys);
        this.dataReader.start(new ArrayList<IMetaData>(metaData), -1L);
        Set<DimensionValueSet> accessMasterKeys = this.getAccessMasterKeys(queryInfo);
        if (CollectionUtils.isEmpty(accessMasterKeys)) {
            this.dataReader.finish();
            return;
        }
        Collection dimensionValueSets = DataPermissionUtil.mergeDimensionValue(accessMasterKeys, (String)this.relation.getDwDimName());
        dimensionValueSets = DataPermissionUtil.groupByDwSize((Collection)dimensionValueSets, (String)this.relation.getDwDimName(), (int)this.fieldDataProperties.getBatchSize());
        int batch = 0;
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            logger.info("\u5206\u6279\u6b21\u6d41\u5f0f\u8bfb\u53d6\u67e5\u8be2\u6570\u636e\u4e2d {}/{}, \u6279\u6b21\u5927\u5c0f {}", ++batch, dimensionValueSets.size(), this.fieldDataProperties.getBatchSize());
            this.initDataQuery(queryInfo, fieldRelation, dimensionValueSet);
            ((IDataQuery)this.dataQuery).setMasterKeys(dimensionValueSet);
            this.addQueryCol(metaData);
            this.addFilter();
            this.addOrder();
            this.execQuery();
        }
        this.dataReader.finish();
    }

    @Override
    public void start(QueryContext qContext, IFieldsInfo fieldsInfo) {
    }

    @Override
    public void finish(QueryContext qContext) {
    }
}

