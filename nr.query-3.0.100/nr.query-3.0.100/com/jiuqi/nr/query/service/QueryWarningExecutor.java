/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryWarningExecutor {
    private static final Logger log = LoggerFactory.getLogger(QueryWarningExecutor.class);
    MemoryDataSet<FieldDefine> dataSet = new MemoryDataSet();
    Map<String, Integer> fieldColumns = new HashMap<String, Integer>();

    public QueryWarningExecutor(List<FieldDefine> fields) {
        for (int i = 0; i < fields.size(); ++i) {
            FieldDefine field = fields.get(i);
            Column col = new Column(field.getCode(), field.getType().getValue());
            this.fieldColumns.put(field.getKey(), i);
            this.dataSet.getMetadata().addColumn(col);
        }
    }

    public int getFieldIndex(String fieldCode) {
        if (this.fieldColumns.containsKey(fieldCode)) {
            return this.fieldColumns.get(fieldCode);
        }
        return -1;
    }

    public int setValue(int row, int col, String fieldCode, Object value) {
        DataRow drow = null;
        int index = this.getFieldIndex(fieldCode);
        if (this.dataSet.size() > row) {
            drow = this.dataSet.get(row);
        }
        if (drow == null) {
            drow = this.dataSet.add();
        }
        drow.setValue(index, value);
        return index;
    }

    public Map<Integer, Map<Integer, Map<String, AbstractData>>> executor(Map<Integer, Map<String, List<String>>> formulas) {
        try {
            HashMap<Integer, Map<Integer, Map<String, AbstractData>>> values = new HashMap<Integer, Map<Integer, Map<String, AbstractData>>>();
            DataSetExprEvaluator evaluator = new DataSetExprEvaluator(this.dataSet);
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
            for (Integer col : formulas.keySet()) {
                String formula = "";
                Map<String, List<String>> ff = formulas.get(col);
                HashMap warningRows = (HashMap)values.get(col);
                if (warningRows == null) {
                    warningRows = new HashMap();
                }
                for (String code : ff.keySet()) {
                    List<String> formulaList = ff.get(code);
                    try {
                        for (int i = 0; i < formulaList.size(); ++i) {
                            formula = formulaList.get(i);
                            evaluator.prepare(context, null, formula);
                            for (int j = 0; j < this.dataSet.size(); ++j) {
                                HashMap<String, AbstractData> rows = (HashMap<String, AbstractData>)warningRows.get(j);
                                if (rows == null) {
                                    rows = new HashMap<String, AbstractData>();
                                }
                                AbstractData result = evaluator.evaluate(this.dataSet.get(j));
                                rows.put(code, result);
                                warningRows.put(j, rows);
                            }
                        }
                    }
                    catch (Exception ex) {
                        LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)("\u9884\u8b66\u516c\u5f0f\uff1a\u2018" + formula + "\u2019,\u5ba1\u6838\u9519\u8bef" + ex.getMessage()));
                    }
                }
                values.put(col, warningRows);
            }
            return values;
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)("\u9884\u8b66\u5ba1\u6838\u9519\u8bef" + ex.getMessage()));
            log.error(ex.getMessage(), ex);
            return null;
        }
    }
}

