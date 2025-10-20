/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import java.util.List;
import java.util.Set;

public interface ClbrProcessService {
    public PageInfo<ClbrNotConfirmBillVO> queryNotConfirm(ClbrProcessCondition var1, ClbrProcessListTypeEnum var2);

    public PageInfo<ClbrBillCheckVO> queryConfirm(ClbrProcessCondition var1, String var2);

    public PageInfo<ClbrBillCheckVO> convertClbrConfirmBill(PageInfo<ClbrBillCheckEO> var1);

    public ClbrBillVO getConfirmByClbrBillCode(String var1);

    public PageInfo<ClbrBillVO> queryByClbrCode(ClbrProcessCondition var1, ClbrBillTypeEnum var2);

    public void cancalByAmountCheckIds(Set<String> var1);

    public List<TransferColumnVo> queryAllShowFields(String var1);
}

