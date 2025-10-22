/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.offsetitem.init.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface GcOffSetInitService {
    public StringBuffer importData(MultipartFile var1, QueryParamsVO var2, String var3);

    public List<GcBusinessTypeCountVO> rootBusinessTypes(OffsetItemInitQueryParamsVO var1);

    public Map<String, Integer> countEveryBusinessType(OffsetItemInitQueryParamsVO var1);

    public Pagination<Map<String, Object>> queryOffsetEntry(OffsetItemInitQueryParamsVO var1);

    public Pagination<Map<String, Object>> getOffsetEntry(OffsetItemInitQueryParamsVO var1);

    public List<Map<String, Object>> getPartFieldOffsetEntry(OffsetItemInitQueryParamsVO var1);

    public void deleteAllInitOffsetEntrys(OffsetItemInitQueryParamsVO var1);

    @Transactional(rollbackFor={Exception.class})
    public void deleteInitAdjustSameSrcId(Collection<String> var1, String var2);

    public void batchSave(List<GcOffSetVchrDTO> var1);

    public GcOffSetVchrDTO getOne(String var1);

    public List<GcOffSetVchrItemVO> getInvestmentOffsetItemByMrecids(List<String> var1);

    public GcOffSetVchrDTO save(GcOffSetVchrDTO var1);

    @Transactional(rollbackFor={Exception.class})
    public void deleteOffsetEntrysBySrcOffsetGroupId(Collection<String> var1, String var2, Integer var3, Integer var4, String var5, String var6);

    public List<GcOffSetVchrItemDTO> findByInvestment(GcOffsetItemQueryCondi var1);

    public List<GcOffSetVchrDTO> queryOffSetGroupDTOs(OffsetItemInitQueryParamsVO var1);

    public ExportExcelSheet exportSheet(int var1, OffsetItemInitQueryParamsVO var2, GcBusinessTypeCountVO var3, Map<String, CellStyle> var4, ExportContext var5);

    public boolean hasOffsetRecordByUnitAndRuleType(String var1, Integer var2, boolean var3);

    public void changeAssetTitle(String var1, String var2, Integer var3, String var4, String var5);

    public void downloadErrorExcel(String var1, HttpServletResponse var2);

    public void updateOffsetInitDisabledFlag(List<String> var1, boolean var2);

    public List<GcOffSetVchrItemDTO> queryOffsetingEntryDTOSort(OffsetItemInitQueryParamsVO var1);
}

