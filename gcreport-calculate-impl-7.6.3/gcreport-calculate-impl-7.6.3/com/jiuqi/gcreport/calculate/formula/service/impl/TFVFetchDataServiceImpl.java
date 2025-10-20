/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.nr.impl.function.impl.tfv.TFVFetchDataService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.gcreport.calculate.formula.service.impl;

import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.nr.impl.function.impl.tfv.TFVFetchDataService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class TFVFetchDataServiceImpl
implements TFVFetchDataService {
    public Map<String, Object> fetchData(ExecutorContext exeContext) {
        GcReportSimpleExecutorContext context = (GcReportSimpleExecutorContext)exeContext;
        HashMap<String, Object> fields = Objects.isNull(context.getData()) ? new HashMap(8) : context.getData().getFields();
        DimensionValueSet varDimensionValueSet = context.getVarDimensionValueSet();
        if (varDimensionValueSet.size() != 0) {
            for (int i = 0; i < varDimensionValueSet.size(); ++i) {
                fields.put(varDimensionValueSet.getName(i), varDimensionValueSet.getValue(i));
            }
        }
        return fields;
    }
}

