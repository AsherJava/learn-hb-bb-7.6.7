/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import java.util.List;
import java.util.Set;

public interface ClbrBillService {
    public ClbrBillPushResultDTO checkBatchPush(ClbrBillPushParamDTO var1);

    public ClbrBillPushResultDTO batchPush(ClbrBillPushParamDTO var1);

    public List<ClbrBillConfirmMsgDTO> confirm(ClbrBillConfirmTypeEnum var1, List<ClbrBillEO> var2, List<ClbrBillEO> var3, boolean var4, String var5, String var6);

    public ClbrBillDTO query(String var1, String var2);

    public Integer countInitiatorNotConfirmByUser(String var1, String var2, String var3, String var4);

    public List<ClbrBillDTO> listInitiatorNotConfirmByUser(ClbrBillSsoConditionDTO var1);

    public Integer countProcessInitiatorNotConfirmByUser(String var1, String var2, String var3, String var4);

    public PageInfo<ClbrBillCheckVO> listProcessInitiatorNotConfirmByUser(ClbrProcessCondition var1, String var2);

    public PageInfo<ClbrBillDTO> listByReceiverInfo(ClbrBillSsoConditionDTO var1);

    public PageInfo<ClbrBillDTO> listInitiatorNotConfirmBySsoCondition(ClbrBillSsoConditionDTO var1);

    public PageInfo<ClbrBillDTO> listInitiatorConfirmByRelation(ClbrBillGenerateQueryParamDTO var1);

    public PageInfo<ClbrBillDTO> listInitiatorNotConfirmByRelation(ClbrBillGenerateQueryParamDTO var1);

    public ClbrGenerateAttributeDTO queryClbrGenerateAttribute(String var1);

    public Object generateOppClbrBill(Set<String> var1);

    public void rejectClbrBill(ClbrBillRejectDTO var1);

    public void cancelClbrBill(Set<String> var1);

    public void cancelSingleClbrBill(Set<String> var1);

    public void manualConfirmByGrouping(Set<String> var1);

    public void manualConfirm(ClbrBillManualConfirmParamDTO var1);

    public String showBill(String var1);

    public void rejectArbitrationClbrBill(Set<String> var1);

    public List<ClbrBillEO> queryReceBillCodeState(Set<String> var1);
}

