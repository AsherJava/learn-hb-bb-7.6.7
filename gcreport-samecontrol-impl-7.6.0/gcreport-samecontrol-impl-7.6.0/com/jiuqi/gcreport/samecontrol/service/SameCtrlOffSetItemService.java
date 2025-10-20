/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  org.apache.poi.ss.usermodel.CellStyle
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;

public interface SameCtrlOffSetItemService {
    public Pagination<SameCtrlOffSetItemVO> listOffsets(SameCtrlOffsetCond var1);

    public void extractData(SameCtrlChgEnvContext var1);

    public void deleteOffsetEntrysByMrecid(SameCtrlOffsetCond var1);

    public List<SameCtrlOffSetItemVO> queryInputAdjustment(String var1);

    public void saveInputAdjustment(List<List<SameCtrlOffSetItemVO>> var1);

    public SameCtrlChgOrgEO getSameCtrlChgOrg(SameCtrlOffsetCond var1, GcOrgCenterService var2);

    public List<GcOrgCacheVO> listUnitPaths(SameCtrlOffsetCond var1);

    public void deleteInputAdjustment(SameCtrlOffsetCond var1);

    public List<Map<String, Object>> sumOffsetsBySameSubjectCode(SameCtrlOffsetCond var1, List<String> var2);

    public List<ExportExcelSheet> exportSameCtrlOffsetDatas(SameCtrlOffsetCond var1, Map<String, CellStyle> var2, ExportContext var3);
}

