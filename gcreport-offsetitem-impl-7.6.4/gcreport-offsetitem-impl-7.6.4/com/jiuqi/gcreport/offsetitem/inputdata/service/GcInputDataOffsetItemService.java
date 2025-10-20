/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.offsetitem.inputdata.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GcInputDataOffsetItemService {
    public void cancelInputOffsetByOffsetGroupId(Collection<String> var1, String var2);

    public void mappingRule(String var1, String var2, String var3, String var4, String var5);

    public void updateMemoById(String var1, String var2, String var3);

    public Pagination<Map<String, Object>> queryUnOffsetRecords(QueryParamsVO var1, Boolean var2);

    public List<DesignFieldDefineVO> listUnOffsetColumnSelects(String var1, String var2);

    public Map<String, String> initFieldCode2DictTableMap(List<String> var1, String var2);

    public List<Map<String, Object>> queryUnOffset(QueryParamsVO var1, boolean var2);

    public Set<String> queryInputDataOffsetGroupIds(QueryParamsVO var1);

    public void getUnOffsetTabExportExcelSheet(List<ExportExcelSheet> var1, GcOffsetExecutorVO var2);

    public void getUnOffsetParentTabExportExcelSheet(List<ExportExcelSheet> var1, GcOffsetExecutorVO var2);

    public List<DesignFieldDefineVO> getAllFieldsByTableName(String var1, Set<String> var2);

    public void setRuleTitle(Map<String, Object> var1, Map<String, AbstractUnionRule> var2);

    public void setOtherShowColumnDictTitle(Map<String, Object> var1, List<String> var2, Map<String, String> var3, boolean var4);
}

