/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParameterEnvUtils {
    public static List<String> distinctParaAliasNameList(IParameterEnv env, List<String> paraAliasNames) {
        ArrayList<String> distinctAliasNames = new ArrayList<String>();
        HashSet<ParameterModel> paramSet = new HashSet<ParameterModel>();
        for (int i = paraAliasNames.size() - 1; i >= 0; --i) {
            String aliasName = paraAliasNames.get(i);
            ParameterModel pModel = env.getParameterModelByName(aliasName);
            if (pModel == null) {
                pModel = env.getParameterWithAlias(aliasName);
            }
            if (pModel == null || paramSet.contains(pModel)) continue;
            paramSet.add(pModel);
            distinctAliasNames.add(0, aliasName);
        }
        return distinctAliasNames;
    }

    public static List<String> sortParaAliasNameList(IParameterEnv env, List<String> paraAliasNames) {
        ArrayList<String> newParaAliasList = new ArrayList<String>();
        for (String paraAliasName : paraAliasNames) {
            ParameterEnvUtils.sortParaAliasNameList(paraAliasName, env, paraAliasNames, newParaAliasList);
        }
        return newParaAliasList;
    }

    private static void sortParaAliasNameList(String paraAliasName, IParameterEnv env, List<String> oldParaList, List<String> newParaAliasList) {
        ParameterModel paraModel = env.getParameterWithAlias(paraAliasName);
        if (paraModel == null) {
            newParaAliasList.add(paraAliasName);
            return;
        }
        List<String> cascadeParaNameList = env.getCascadeParameters(paraModel.getName());
        if (cascadeParaNameList == null || cascadeParaNameList.size() == 0) {
            if (!newParaAliasList.contains(paraAliasName)) {
                newParaAliasList.add(paraAliasName);
            }
        } else {
            for (String cascadeParaName : cascadeParaNameList) {
                ParameterModel cascadeParaModel = env.getParameterModelByName(cascadeParaName);
                String cascadeParaAliasName = cascadeParaModel.getAlias();
                if (!StringUtils.isNotEmpty((String)cascadeParaAliasName) || !oldParaList.contains(cascadeParaAliasName)) continue;
                ParameterEnvUtils.sortParaAliasNameList(cascadeParaAliasName, env, oldParaList, newParaAliasList);
            }
            if (!newParaAliasList.contains(paraAliasName)) {
                newParaAliasList.add(paraAliasName);
            }
        }
    }

    public static List<String> sortParaNameList(IParameterEnv env, List<String> paraNames) {
        ArrayList<String> newParaList = new ArrayList<String>();
        for (String paraName : paraNames) {
            ParameterEnvUtils.sortParaNameList(paraName, env, paraNames, newParaList);
        }
        return newParaList;
    }

    private static void sortParaNameList(String paraName, IParameterEnv env, List<String> oldParaList, List<String> newParaList) {
        ParameterModel paraModel = env.getParameterModelByName(paraName);
        if (paraModel == null) {
            newParaList.add(paraName);
            return;
        }
        List<String> cascadeParaNameList = env.getCascadeParameters(paraName);
        if (cascadeParaNameList == null || cascadeParaNameList.size() == 0) {
            if (!newParaList.contains(paraName)) {
                newParaList.add(paraName);
            }
        } else {
            for (String cascadeParaName : cascadeParaNameList) {
                if (!oldParaList.contains(cascadeParaName)) continue;
                ParameterEnvUtils.sortParaNameList(cascadeParaName, env, oldParaList, newParaList);
            }
            if (!newParaList.contains(paraName)) {
                newParaList.add(paraName);
            }
        }
    }
}

