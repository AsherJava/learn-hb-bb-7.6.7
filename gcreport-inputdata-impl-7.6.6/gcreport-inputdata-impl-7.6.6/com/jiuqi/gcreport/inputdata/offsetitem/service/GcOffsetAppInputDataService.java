/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service;

import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GcOffsetAppInputDataService {
    public Pagination<Map<String, Object>> listUnOffsetRecords(QueryParamsVO var1, String var2, List<Object> var3);

    public Map<String, Object> getObject(Map<String, Object> var1);

    public void initParentMergeUnitCondition(StringBuffer var1, List<Object> var2, String var3, Date var4, String var5);

    public void initValidtimeCondition(StringBuffer var1, List<Object> var2, Date var3);

    public void initMergeUnitCondition(StringBuffer var1, List<Object> var2, String var3, String var4, Date var5, int var6);

    public void initUnitCondition(QueryParamsVO var1, StringBuffer var2, GcOrgCenterService var3);

    public void initPeriodCondition(QueryParamsVO var1, List<Object> var2, StringBuffer var3);

    public void initOtherCondition(StringBuffer var1, Map<String, Object> var2, String var3, String var4);

    public StringBuffer selectFields(QueryParamsVO var1);

    public String getUnitWhere(List<String> var1, QueryParamsVO var2, GcOrgCenterService var3, String var4);

    public String getOppUnitWhere(List<String> var1, QueryParamsVO var2, GcOrgCenterService var3, String var4);

    public void addRuleWhereSql(StringBuffer var1, List var2);

    public String getUnitParents(String var1, GcOrgCenterService var2);

    public StringBuffer filterUnitScenes(QueryParamsVO var1, List<Object> var2, GcOrgCenterService var3, StringBuffer var4);

    public void unitScenesInitUnitCondition(QueryParamsVO var1, StringBuffer var2);

    public String unitScenesGetUnitWhere(String var1, List<String> var2, QueryParamsVO var3);

    public StringBuffer getQueryUnOffsetRecordsSql(QueryParamsVO var1, List<Object> var2, Boolean var3);

    public void setTitles(Pagination<Map<String, Object>> var1, QueryParamsVO var2, String var3);

    public Map<String, String> initFieldCode2DictTableMap(List<String> var1, String var2);

    public void setSubjectTitle(String var1, Map<String, Object> var2, Map<String, String> var3, String var4);

    public void updateUnitAndSubjectTitle(boolean var1, Map<String, Object> var2);

    public void setRuleTitle(Map<String, Object> var1, Map<String, AbstractUnionRule> var2);

    public void setBusinessTypeCodeTitle(Map<String, Object> var1, Map<String, String> var2);

    public void setOtherShowColumnDictTitle(Map<String, Object> var1, List<String> var2, Map<String, String> var3, boolean var4);

    public String getFetchSetTitle(Map<String, Object> var1, AbstractUnionRule var2);

    public void cancelInputOffset(Collection<String> var1, InputWriteNecLimitCondition var2);

    public void cancelLockedInputOffset(String var1, String var2);

    public void cancelLockedInputOffsetByCurrency(String var1, String var2, List<String> var3);

    public void cancelInputOffsetByOffsetGroupId(Collection<String> var1, String var2);

    public void calc(QueryParamsVO var1);
}

