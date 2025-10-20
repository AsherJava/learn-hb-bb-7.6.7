/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.manager.PageDataSetReader;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.model.DefaultGroupPageDataSetProvider;
import com.jiuqi.bi.dataset.model.DefaultPageDataSetProvider;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.IDataSetWithDistinctProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.DataSetStorageManager;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSetManager
implements IDataSetManager {
    @Override
    public BIDataSet open(IDSContext context, String dsName, String datasetType) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        DSContext dsCxt;
        DSModel model = this.findModel(dsName, datasetType);
        if (context != null && (dsCxt = (DSContext)context).getDsModel() == null) {
            dsCxt.setDsModel(model);
        }
        return this.open(context, model);
    }

    @Override
    public BIDataSet open(IDSContext context, DSModel model) throws DataSetTypeNotFoundException, BIDataSetException {
        SortItem[] sortItems;
        FilterItem[] filters;
        DSDataProviderFactory factory = this.getDSDataProviderFactory(model);
        IDataSetProvider provider = factory.createDataSetProvider(model);
        MemoryDataSet<BIDataSetFieldInfo> memory = DSModelFactoryManager.createMemoryDataSet(model);
        provider.open(memory, context);
        TimeKeyBuilder.buildTimeKey(memory);
        BIDataSetImpl biDataSet = new BIDataSetImpl(memory);
        if (context != null) {
            try {
                biDataSet.setParameterEnv(context.getEnhancedParameterEnv());
                biDataSet.setLogger(context.getLogger());
            }
            catch (ParameterException e) {
                throw new BIDataSetException("\u521b\u5efa\u53c2\u6570\u67e5\u8be2\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
        biDataSet._calcField(context);
        if (context != null && !context.isFiltered() && (filters = context.getAllFilterItem()) != null && filters.length > 0) {
            biDataSet = (BIDataSetImpl)biDataSet.filter(Arrays.asList(filters));
            context.markFiltered();
        }
        if (context != null && !context.isSorted() && (sortItems = context.getSortItems()) != null && sortItems.length > 0) {
            biDataSet = (BIDataSetImpl)biDataSet.sort(Arrays.asList(sortItems));
            context.markSorted();
        }
        return biDataSet;
    }

    @Override
    public PageDataSetReader openPageDataSet(IDSContext context, String dsName, String datasetType) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        return this.openPageDataSet(context, dsName, datasetType, false);
    }

    @Override
    public PageDataSetReader openPageDataSet(IDSContext context, DSModel model) throws DataSetTypeNotFoundException, BIDataSetException {
        return this.openPageDataSet(context, model, false);
    }

    @Override
    public PageDataSetReader openPageDataSet(IDSContext context, String dsName, String datasetType, boolean inMemory) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        DSModel model = this.findModel(dsName, datasetType);
        return this.openPageDataSet(context, model, inMemory);
    }

    @Override
    public PageDataSetReader openPageDataSet(IDSContext context, DSModel model, boolean inMemory) throws DataSetTypeNotFoundException, BIDataSetException {
        IPageDataSetProvider provider;
        DSDataProviderFactory factory = this.getDSDataProviderFactory(model);
        if (inMemory) {
            provider = new DefaultPageDataSetProvider(model, factory.createDataSetProvider(model));
        } else {
            provider = factory.createPageDataSetProvider(model);
            if (provider == null) {
                provider = new DefaultPageDataSetProvider(model, factory.createDataSetProvider(model));
                inMemory = true;
            }
        }
        provider.setPageSize(20);
        return new PageDataSetReader(model, provider, inMemory);
    }

    @Override
    public PageDataSetReader openGroupPageDataSet(IDSContext context, String groupFieldName, String dsName, String datasetType) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        DSModel model = this.findModel(dsName, datasetType);
        return this.openGroupPageDataSet(context, groupFieldName, model);
    }

    @Override
    public PageDataSetReader openGroupPageDataSet(IDSContext context, String groupFieldName, DSModel model) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        return this.openGroupPageDataSet(context, groupFieldName, model, false);
    }

    @Override
    public PageDataSetReader openGroupPageDataSet(IDSContext context, String groupFieldName, DSModel model, boolean inMemory) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException {
        IPageDataSetProvider provider;
        if (model.findField(groupFieldName) == null) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5206\u9875\u65f6\u6307\u5b9a\u7684\u5206\u7ec4\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + model.getName() + "." + groupFieldName);
        }
        DSDataProviderFactory factory = this.getDSDataProviderFactory(model);
        if (inMemory) {
            provider = new DefaultGroupPageDataSetProvider(model, factory.createDataSetProvider(model), groupFieldName);
        } else {
            provider = factory.createGroupDataSetProvider(model, groupFieldName);
            if (provider == null) {
                provider = new DefaultGroupPageDataSetProvider(model, factory.createDataSetProvider(model), groupFieldName);
                inMemory = true;
            }
        }
        provider.setPageSize(20);
        return new PageDataSetReader(model, provider, inMemory);
    }

    @Override
    public DSModel findModel(String dsName, String datasetType) throws BIDataSetNotFoundException, BIDataSetException {
        try {
            DSModel model = DataSetStorageManager.getInstance().findModel(dsName, datasetType);
            if (model == null) {
                throw new BIDataSetNotFoundException(dsName);
            }
            return model;
        }
        catch (DataSetStorageException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    protected DSDataProviderFactory getDSDataProviderFactory(DSModel model) throws DataSetTypeNotFoundException {
        DSDataProviderFactory factory = DSModelFactoryManager.getInstance().getDataProviderFactory(model.getType());
        if (factory == null) {
            throw new DataSetTypeNotFoundException(model.getType());
        }
        return factory;
    }

    @Override
    public DSField findField(String dsName, String datasetType, String fieldName) throws BIDataSetNotFoundException, BIDataSetException {
        DSModel dsModel = this.findModel(dsName, datasetType);
        List<DSField> dsFields = dsModel.getFields();
        if (dsFields != null && dsFields.size() != 0) {
            for (DSField dsField : dsFields) {
                if (!dsField.getName().equalsIgnoreCase(fieldName)) continue;
                return dsField;
            }
        }
        throw new BIDataSetException("\u672a\u627e\u5230\u5b57\u6bb5:" + fieldName);
    }

    @Override
    public DSHierarchy findHierarchy(String dsName, String datasetType, String hierachyName) throws BIDataSetNotFoundException, BIDataSetException {
        DSModel dsModel = this.findModel(dsName, datasetType);
        List<DSHierarchy> hiers = dsModel.getHiers();
        if (hiers != null && hiers.size() != 0) {
            for (DSHierarchy dsHierarchy : hiers) {
                if (!dsHierarchy.getName().equals(hierachyName)) continue;
                return dsHierarchy;
            }
        }
        throw new BIDataSetException("\u672a\u627e\u5230\u5c42\u6b21:" + hierachyName);
    }

    @Override
    public MemoryDataSet<BIDataSetFieldInfo> distinct(IDSContext context, DSModel model, String fieldName) throws DataSetTypeNotFoundException, BIDataSetException {
        DSDataProviderFactory factory = this.getDSDataProviderFactory(model);
        IDataSetProvider provider = factory.createDataSetProvider(model);
        MemoryDataSet memory = new MemoryDataSet();
        List<DSField> fields = model.getFields();
        for (DSField field : fields) {
            if (!field.getName().equalsIgnoreCase(fieldName)) continue;
            if (field.getFieldType() == FieldType.MEASURE) {
                throw new BIDataSetException("distinct\u64cd\u4f5c\u53ea\u80fd\u5bf9\u975e\u5ea6\u91cf\u5b57\u6bb5\u8fdb\u884c\u64cd\u4f5c");
            }
            Column column = new Column(field.getName(), field.getValType());
            column.setTitle(field.getTitle());
            BIDataSetFieldInfo info = DSUtils.transform(field);
            column.setInfo((Object)info);
            memory.getMetadata().getColumns().add(column);
            break;
        }
        if (provider instanceof IDataSetWithDistinctProvider) {
            ((IDataSetWithDistinctProvider)provider).distinct((MemoryDataSet<BIDataSetFieldInfo>)memory, context);
            return memory;
        }
        if (memory.getMetadata().getColumns().isEmpty()) {
            return memory;
        }
        BIDataSet basedataSet = this.open(context, model);
        ArrayList<String> distinctFields = new ArrayList<String>();
        distinctFields.add(fieldName);
        BIDataSet distinctDataset = basedataSet.distinct(distinctFields);
        MemoryDataSet result = new MemoryDataSet();
        for (int col = 0; col < memory.getMetadata().getColumns().size(); ++col) {
            result.getMetadata().getColumns().add(memory.getMetadata().getColumns().get(col));
        }
        try {
            for (int i = 0; i < distinctDataset.getRecordCount(); ++i) {
                BIDataRow biDataRow = distinctDataset.get(i);
                Object[] row = new Object[memory.getMetadata().getColumns().size()];
                for (int j = 0; j < memory.getMetadata().getColumns().size(); ++j) {
                    row[j] = biDataRow.getValue(j);
                }
                result.add(row);
            }
        }
        catch (DataSetException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        return result;
    }
}

