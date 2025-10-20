/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 *  org.apache.poi.ss.usermodel.CellStyle
 */
package com.jiuqi.gcreport.workingpaper.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrItemDTO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.ss.usermodel.CellStyle;

public interface ArbitrarilyMergeOffSetItemAdjustService {
    public GcOffSetVchrDTO save(GcOffSetVchrDTO var1);

    public List<ArbitrarilyMergeOffSetVchrDTO> batchRySaveBySrcGroupId(List<ArbitrarilyMergeOffSetVchrDTO> var1);

    public ArbitrarilyMergeOffSetVchrItemAdjustEO convertRyDTO2EO(ArbitrarilyMergeOffSetVchrItemDTO var1);

    public void checkRyGroupDTO(ArbitrarilyMergeOffSetVchrDTO var1);

    public void deleteRyBySrcOffsetGroupIds(String var1, List<String> var2, int var3, int var4, int var5, String var6, String var7);

    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> queryRyOffsetRecordsByWhere(String[] var1, Object[] var2, ArbitrarilyMergeInputAdjustQueryCondi var3);

    public Pagination<Map<String, Object>> getRyOffsetEntry(QueryParamsVO var1, boolean var2);

    public List<Map<String, Object>> sumRyOffsetValueGroupBySubjectcode(QueryParamsVO var1);

    public Pagination<Map<String, Object>> assembleOffsetEntry(Pagination<Map<String, Object>> var1, QueryParamsVO var2);

    public ExportExcelSheet exportRySheet(String var1, QueryParamsVO var2, Pagination<Map<String, Object>> var3, Map<String, CellStyle> var4, boolean var5);

    public Set<String> deleteOffsetEntrys(QueryParamsVO var1);

    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> var1, Integer var2, GcTaskBaseArguments var3);

    public PaginationDto<GcOffSetVchrItemAdjustEO> listWithFullGroup(QueryParamsDTO var1);

    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO var1);

    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> listWithOnlyItemsByRy(QueryParamsDTO var1);
}

