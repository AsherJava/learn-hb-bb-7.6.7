/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterUtils {
    public static IParameterValueFormat createValueFormat(AbstractParameterDataSourceModel model) {
        return ParameterDataSourceManager.getInstance().getFactory(model.getType()).createValueFormat(model);
    }

    public static List<Object> parseValueList(AbstractParameterDataSourceModel model, List<String> value) throws ParameterException {
        if (value == null) {
            return null;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        ArrayList<Object> list = new ArrayList<Object>();
        for (String v : value) {
            list.add(format.parse(v));
        }
        return list;
    }

    public static List<String> formatValueList(AbstractParameterDataSourceModel model, List<Object> value) throws ParameterException {
        if (value == null) {
            return null;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        ArrayList<String> list = new ArrayList<String>();
        for (Object v : value) {
            list.add(format.format(v));
        }
        return list;
    }

    public static List<String> intersect(List<String> candidate, List<String> ref) {
        HashSet set = new HashSet();
        candidate.forEach(c -> set.add(c));
        return ref.stream().filter(c -> set.contains(c)).collect(Collectors.toList());
    }

    public static ParameterResultset intersect(ParameterResultset candidate, ParameterResultset ref) {
        HashSet set = new HashSet();
        candidate.forEach(c -> set.add(c.getValue()));
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        ref.iterator().forEachRemaining(c -> {
            if (set.contains(c.getValue())) {
                items.add((ParameterResultItem)c);
            }
        });
        return new ParameterResultset(items);
    }

    public static List<String> topo(IParameterEnv env, List<String> paramNames) throws ParameterException {
        List<ParameterModel> pmodels = env.getParameterModels();
        ParameterCalculator calculator = new ParameterCalculator(env.getUserId(), pmodels);
        return calculator.getParameterTopoList();
    }

    public static List<String> distinctParamNames(IParameterEnv env, List<String> paramNames) {
        ArrayList<String> distinctedNames = new ArrayList<String>();
        HashSet<ParameterModel> paramSet = new HashSet<ParameterModel>();
        for (int i = paramNames.size() - 1; i >= 0; --i) {
            String paramName = paramNames.get(i);
            ParameterModel pModel = env.getParameterModelByName(paramName);
            if (pModel == null || paramSet.contains(pModel)) continue;
            paramSet.add(pModel);
            distinctedNames.add(0, paramName);
        }
        return distinctedNames;
    }
}

