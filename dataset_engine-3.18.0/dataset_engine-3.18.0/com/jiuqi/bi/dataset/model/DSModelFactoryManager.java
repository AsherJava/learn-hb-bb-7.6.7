/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.embedded.EmbeddedDSDataProviderFactory;
import com.jiuqi.bi.dataset.embedded.EmbeddedDSModelFactory;
import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelException;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.model.DefaultDSModel;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.sql.SQLDSDataProviderFactory;
import com.jiuqi.bi.dataset.sql.SQLDSModelFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DSModelFactoryManager {
    private static final DSModelFactoryManager _instance = new DSModelFactoryManager();
    private Map<String, DSModelFactory> factories = new HashMap<String, DSModelFactory>();
    private Map<String, DSDataProviderFactory> dataProviderFactories = new HashMap<String, DSDataProviderFactory>();

    private DSModelFactoryManager() {
        this.registerFactory(new SQLDSModelFactory());
        this.registerDataProviderFactory(new SQLDSDataProviderFactory());
        this.registerFactory(new EmbeddedDSModelFactory());
        this.registerDataProviderFactory(new EmbeddedDSDataProviderFactory());
    }

    public static DSModelFactoryManager getInstance() {
        return _instance;
    }

    public void registerFactory(DSModelFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    public void unregisterFactory(DSModelFactory factory) {
        this.factories.remove(factory.getType());
    }

    public DSModelFactory getFactory(String type) {
        if (type != null && type.equals("com.jiuqi.bi.dataset.sql")) {
            type = "com.jiuqi.bi.dataset.sq";
        }
        return this.factories.get(type);
    }

    public void registerDataProviderFactory(DSDataProviderFactory factory) {
        this.dataProviderFactories.put(factory.getType(), factory);
    }

    public void unregisterDataProviderFactory(DSDataProviderFactory factory) {
        this.dataProviderFactories.remove(factory.getType());
    }

    public DSDataProviderFactory getDataProviderFactory(String type) {
        if (type.equals("com.jiuqi.bi.dataset.sql")) {
            type = "com.jiuqi.bi.dataset.sq";
        }
        return this.dataProviderFactories.get(type);
    }

    public DSModel createDSModel(String type) throws DataSetTypeNotFoundException {
        DSModelFactory factory = this.getFactory(type);
        if (factory == null) {
            throw new DataSetTypeNotFoundException(type);
        }
        return factory.createDataSetModel();
    }

    public DSModel createDSModel(JSONObject dsModelJSON) throws DataSetTypeNotFoundException, DSModelException {
        String type;
        try {
            type = dsModelJSON.getJSONObject("dataset").getString("type");
        }
        catch (JSONException e) {
            throw new DSModelException("\u89e3\u6790JSON\u5b57\u7b26\u4e32\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        if (type == null) {
            throw new DSModelException("JSON\u5b57\u7b26\u4e32\u683c\u5f0f\u9519\u8bef\uff0c\u672a\u627e\u5230\u6570\u636e\u96c6\u6a21\u578b\u7684\u7c7b\u578b\u4fe1\u606f");
        }
        DSModel dsModel = this.createDSModel(type);
        dsModel.fromJSON(dsModelJSON);
        return dsModel;
    }

    public DSModel createDefaultDSModel(JSONObject json) throws DSModelException {
        String type;
        try {
            type = json.getJSONObject("dataset").getString("type");
        }
        catch (JSONException e) {
            throw new DSModelException("\u89e3\u6790JSON\u5b57\u7b26\u4e32\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        DefaultDSModel model = new DefaultDSModel();
        model.setType(type);
        model.fromJSON(json);
        return model;
    }

    public Iterator<DSModelFactory> iterator() {
        return this.factories.values().iterator();
    }

    public Iterator<DSDataProviderFactory> dataProviderIterator() {
        return this.dataProviderFactories.values().iterator();
    }

    public static MemoryDataSet<BIDataSetFieldInfo> createMemoryDataSet(DSModel model) {
        MemoryDataSet memory = new MemoryDataSet();
        Metadata metadata = memory.getMetadata();
        List<DSField> fields = model.getFields();
        List columns = metadata.getColumns();
        for (int i = 0; i < fields.size(); ++i) {
            DSField field = fields.get(i);
            Column column = new Column(field.getName(), field.getValType());
            column.setTitle(field.getTitle());
            BIDataSetFieldInfo info = DSUtils.transform(field);
            if (field.getFieldType() == FieldType.MEASURE) {
                if (field.getApplyType() == null) {
                    info.setApplyType(ApplyType.PERIOD);
                } else {
                    info.setApplyType(field.getApplyType());
                }
            }
            column.setInfo((Object)info);
            columns.add(column);
        }
        List<DSHierarchy> hierarchies = model.getHiers();
        ArrayList<DSHierarchy> cloned = new ArrayList<DSHierarchy>();
        for (DSHierarchy hierarchy : hierarchies) {
            cloned.add(hierarchy.clone());
        }
        metadata.getProperties().put("HIERARCHY", cloned);
        if (model.getMinFiscalMonth() >= 0 || model.getMaxFiscalMonth() >= 0) {
            JSONObject j = new JSONObject();
            j.put("min", model.getMinFiscalMonth());
            j.put("max", model.getMaxFiscalMonth());
            metadata.getProperties().put("FiscalMonth", j.toString());
        }
        return memory;
    }
}

