/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.data.access.util.DataPermissionUtil
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.data.access.util.DataPermissionUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.BaseFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class SplitDataStrategy
extends BaseFieldDataStrategy<IDataQuery>
implements IFieldDataStrategy {
    private final FieldDataProperties fieldDataProperties;

    public SplitDataStrategy(FieldDataStrategyFactory factory) {
        super(factory);
        this.fieldDataProperties = factory.getFieldDataProperties();
    }

    @Override
    protected IDataQuery getDataQuery(IFieldQueryInfo queryInfo, ParamRelation paramRelation) {
        return this.dataEngineService.getDataQuery();
    }

    @Override
    protected void execQuery() {
        IReadonlyTable readonlyTable;
        try {
            readonlyTable = ((IDataQuery)this.dataQuery).executeReader(this.context);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
        try {
            for (int i = 0; i < readonlyTable.getCount(); ++i) {
                IDataRow row = readonlyTable.getItem(i);
                this.readRowData(row);
            }
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u6d41\u5f0f\u8fd4\u56de\u5931\u8d25", e);
            throw new CrudException(4101, "\u6570\u636e\u6d41\u5f0f\u8fd4\u56de\u5931\u8d25");
        }
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
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            this.initDataQuery(queryInfo, fieldRelation, dimensionValueSet);
            ((IDataQuery)this.dataQuery).setMasterKeys(dimensionValueSet);
            this.addQueryCol(metaData);
            this.addFilter();
            this.addOrder();
            this.addPage(this.queryInfo.getPageInfo());
            this.execQuery();
        }
        this.dataReader.finish();
    }
}

