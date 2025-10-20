/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcOffSetAppOffsetService {
    public Collection<GcOffSetVchrDTO> batchSave(Collection<GcOffSetVchrDTO> var1);

    public Pagination<Map<String, Object>> listSumTabRecords(QueryParamsVO var1);

    public GcOffSetVchrDTO save(GcOffSetVchrDTO var1);

    public void batchDelete(Collection<String> var1, String var2, Integer var3, Integer var4, String var5, String var6);

    public GcOffSetVchrItemDTO convertEO2DTO(GcOffSetVchrItemAdjustEO var1);

    public GcOffSetVchrItemVO convertDTO2VO(GcOffSetVchrItemDTO var1);

    public Set<String> deleteAllOffsetEntrys(QueryParamsVO var1);

    public void cancelRuleData(String var1, String var2, String var3, String var4, List<String> var5, String var6, String var7);

    public List<DesignFieldDefineVO> listOffsetColumnSelects();

    public Pagination<Map<String, Object>> listOffsetEntrys(QueryParamsVO var1, boolean var2);

    public Pagination<Map<String, Object>> listOffsetEntrys(QueryParamsVO var1);

    public Set<String> deleteOffsetEntrys(QueryParamsVO var1);

    public List<GcOffSetVchrItemAdjustEO> listOffsetRecordsByWhere(String[] var1, Object[] var2);

    public List<GcOffSetVchrItemAdjustEO> listFairValueFuncRecords(QueryParamsVO var1);

    public List<GcOrgCacheVO> getOffsetOrgData(QueryParamsVO var1);

    public List<GcOrgCacheVO> getMergeUnitOrgData(QueryParamsVO var1);

    public List<GcOffSetVchrItemVO> writeOffShow(@PathVariable(value="mrecids") @RequestBody List<String> var1, @RequestBody QueryParamsVO var2);

    public void writeOffSave(@PathVariable(value="mrecids") @RequestBody List<String> var1, @RequestBody QueryParamsVO var2);

    public GcOrgCacheVO getHBOrgByCE(String var1, String var2, String var3);

    public void cancelInputOffsetByOffsetGroupId(Collection<String> var1, String var2);

    public List<Map<String, Object>> convertAdjustData2View(List<GcOffSetVchrItemAdjustEO> var1);

    public void updateOffsetDisabledFlag(List<String> var1, boolean var2);

    public void updateMemo(Map<String, Object> var1);

    public int getMemoLength(String var1);

    public List<Map<String, Object>> getPartFieldOffsetEntrys(QueryParamsDTO var1);

    public List<Map<String, Object>> sumOffsetValueGroupBySubjectcode(QueryParamsVO var1);

    public Pagination<Map<String, Object>> listOffsetRecordsForAction(QueryParamsVO var1);

    public ReadWriteAccessDesc writeableByOrgCodeAndDiffCode(DimensionParamsVO var1);
}

