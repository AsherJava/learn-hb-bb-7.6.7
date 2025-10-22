/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO;
import java.util.List;

public interface ClbrDataQueryService {
    public ClbrOverViewVO queryClbrOverView(ClbrDataQueryConditon var1);

    public List<ClbrOverViewVO> listClbrOverView(ClbrDataQueryConditon var1);

    public PageInfo<ClbrBillVO> queryDetailsTotal(ClbrDataQueryConditon var1);

    public PageInfo<ClbrBillVO> queryConfirmDetails(ClbrDataQueryConditon var1);

    public PageInfo<ClbrBillVO> queryPartConfirmDetails(ClbrDataQueryConditon var1);

    public PageInfo<ClbrBillVO> queryNotConfirmDetails(ClbrDataQueryConditon var1);

    public PageInfo<ClbrBillVO> queryRejectDetails(ClbrDataQueryConditon var1);
}

