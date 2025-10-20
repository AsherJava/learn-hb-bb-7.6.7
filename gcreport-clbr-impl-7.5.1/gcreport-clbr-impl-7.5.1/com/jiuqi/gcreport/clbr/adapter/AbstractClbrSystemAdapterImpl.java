/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO
 */
package com.jiuqi.gcreport.clbr.adapter;

import com.jiuqi.gcreport.clbr.adapter.ClbrSystemAdapter;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractClbrSystemAdapterImpl
implements ClbrSystemAdapter {
    @Override
    public ClbrGenerateAttributeDTO queryClbrGenerateAttribute() {
        return new ClbrGenerateAttributeDTO();
    }

    @Override
    public Object generateOppClbrBill(List<ClbrBillDTO> initiatorClbrBillDTOs) {
        return true;
    }

    @Override
    public boolean sendClbrBillRejectMessage(List<ClbrBillRejectMsgDTO> clbrBillRejectMsgDTOs, String rejectReason) {
        return true;
    }

    @Override
    public boolean sendClbrBillConfirmMessage(ClbrBillConfirmMsgContext context, List<ClbrBillConfirmMsgDTO> clbrBillConfirmMsgDTOs, String clbrOperateType) {
        return true;
    }

    @Override
    public boolean sendClbrBillCancelMessage(ClbrBilCancelMsgContext clbrBilCancelMsgContext, List<ClbrBillCancelMsgDTO> clbrBillCancelMsgDTOS) {
        return true;
    }

    @Override
    public List<Map<String, Object>> allowCancelClbrBill(List<ClbrBillDTO> clbrBillDTOs) {
        return new ArrayList<Map<String, Object>>();
    }

    @Override
    public String getBillSsoUrl(ClbrBillDTO clbrBillDTO, String userName) {
        throw new UnsupportedOperationException();
    }
}

