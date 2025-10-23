/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.singlequeryimport.bean.ModalDefine;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.DataQueryParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryLevelsParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.RepeatedJudgmentParams;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.common.ContrastContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QueryModleService {
    public List<QueryModelNode> getTaskModel() throws Exception;

    public List<QueryModelNode> getFormSchemeModel(String var1) throws Exception;

    public List<QueryModelNode> getGroupModel(String var1) throws Exception;

    public List<QueryModelNode> getModel(String var1, String var2) throws Exception;

    public List<QueryModelNode> getModelData(String var1) throws Exception;

    public Integer saveData(QueryModel var1) throws DBParaException;

    public Integer upData(QueryModel var1) throws DBParaException;

    public void changeModelName(String var1, String var2) throws Exception;

    public void changeGroupName(String var1, String var2, String var3, String var4) throws Exception;

    public void deletGroup(String var1, String var2, String var3) throws Exception;

    public QueryModel getModelById(QueryModel var1) throws Exception;

    public Integer deleteMyModle(String var1) throws Exception;

    public int[] batchDelete(List var1) throws Exception;

    public StringBuffer saveMyModle(ModalDefine var1, String var2, String var3, ContrastContext var4) throws Exception;

    public Set<String> getGroupList(String var1, String var2) throws Exception;

    public String updateOrder(List<QueryModelNode> var1) throws Exception;

    public QueryModel getQueryModelByKey(String var1) throws Exception;

    public QueryModel copyModel(String var1, String var2) throws Exception;

    public Integer moveModel(String var1, String var2) throws Exception;

    public String disUse(List<QueryModelNode> var1) throws Exception;

    public Boolean repeatedJudgment(RepeatedJudgmentParams var1) throws Exception;

    public List<Integer> getLevelByCode(QueryLevelsParams var1);

    public List<Integer> getLevelList(String var1) throws Exception;

    public List<ArrayList<Object>> dataQuery(DataQueryParams var1) throws Exception;

    public List<QueryModelNode> getDisUseModel(String var1, String var2) throws Exception;

    public Map<String, String> getModelsFilter(List<String> var1);
}

