/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO;
import java.util.List;

public interface GcDiffProcessService {
    public List<FormTreeVo> queryDiffProcessReports(String var1, String var2);

    public List<GcDiffProcessDataVO> queryDifferenceIntermediateDatas(GcDiffProcessCondition var1);

    public void transferGroupWithinToOutside(GcDiffProcessCondition var1);

    public ExportExcelSheet exportDiffProcessData(GcDiffProcessCondition var1);
}

