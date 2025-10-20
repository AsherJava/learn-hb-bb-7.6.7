/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import java.util.List;

public interface SameCtrlExtractService {
    public void extractSameCtrlData(SameCtrlExtractDataVO var1);

    public Pagination<SameCtrlOffSetItemVO> listOffsetSameCtrlManage(SameCtrlOffsetCond var1);

    public void disEnable(List<String> var1);

    public void enable(List<String> var1);

    public void delOffset(List<String> var1);
}

