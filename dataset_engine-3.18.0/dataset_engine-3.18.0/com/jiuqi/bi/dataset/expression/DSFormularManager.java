/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.expression.DSDynamicNodeProvider;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.expression.ParamNodeWithoutEnvProvider;
import com.jiuqi.bi.dataset.function.DSFunctionProvider;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import java.util.HashMap;
import java.util.List;

public class DSFormularManager {
    public static final int DATASET_TYPE = 5100;
    private static DSFormularManager dsFormularManager;

    private DSFormularManager() {
    }

    public static DSFormularManager getInstance() {
        return dsFormularManager;
    }

    public DatasetFormulaParser createParser(BIDataSetImpl biDataSet) {
        DatasetFormulaParser parser = new DatasetFormulaParser();
        parser.registerFunctionProvider(DSFunctionProvider.DS_PROVIDER);
        List columns = biDataSet.getMetadata().getColumns();
        HashMap<String, BIDataSetFieldInfo> fieldInfos = new HashMap<String, BIDataSetFieldInfo>();
        for (Column column : columns) {
            fieldInfos.put(column.getName().toUpperCase(), (BIDataSetFieldInfo)column.getInfo());
        }
        if (biDataSet.getEnhancedParameterEnv() != null) {
            parser.registerDynamicNodeProvider((IDynamicNodeProvider)new ParamNodeProvider(biDataSet.getEnhancedParameterEnv()));
        }
        parser.registerDynamicNodeProvider(new DSDynamicNodeProvider(fieldInfos));
        return parser;
    }

    public DatasetFormulaParser createParser(DSModel dsModel) throws SyntaxException {
        DatasetFormulaParser parser = new DatasetFormulaParser();
        parser.registerFunctionProvider(DSFunctionProvider.DS_PROVIDER);
        List<DSField> fields = dsModel.getFields();
        HashMap<String, BIDataSetFieldInfo> fieldInfos = new HashMap<String, BIDataSetFieldInfo>();
        for (DSField field : fields) {
            fieldInfos.put(field.getName().toUpperCase(), DSUtils.transform(field));
        }
        BIDataSetFieldInfo rownum = new BIDataSetFieldInfo();
        rownum.setFieldType(null);
        rownum.setValType(DataType.INTEGER.value());
        rownum.setKeyField("SYS_ROWNUM");
        rownum.setName("SYS_ROWNUM");
        fieldInfos.put("SYS_ROWNUM", rownum);
        DSDynamicNodeProvider dsDynamicNodeProvider = new DSDynamicNodeProvider(fieldInfos);
        dsDynamicNodeProvider.setDSModel(dsModel);
        parser.registerDynamicNodeProvider(dsDynamicNodeProvider);
        parser.registerDynamicNodeProvider(new ParamNodeWithoutEnvProvider(dsModel.getParameterModels()));
        return parser;
    }

    public DatasetFormulaParser createParser(DSContext context) throws SyntaxException {
        DSModel dsModel = context.getDsModel();
        MemoryDataSet<BIDataSetFieldInfo> memoryDataSet = DSModelFactoryManager.createMemoryDataSet(dsModel);
        try {
            BIDataSetImpl biDataSet = new BIDataSetImpl(memoryDataSet);
            biDataSet.setParameterEnv(context.getEnhancedParameterEnv());
            biDataSet.setLogger(context.getLogger());
            return this.createParser(biDataSet);
        }
        catch (ParameterException e) {
            throw new SyntaxException("\u521b\u5efa\u53c2\u6570\u6267\u884c\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    static {
        com.jiuqi.bi.syntax.DataType.registerUserDataType((int)5100, (String)"dataset", (String)"\u6570\u636e\u96c6");
        dsFormularManager = new DSFormularManager();
    }
}

