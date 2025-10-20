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

import com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import java.util.List;
import java.util.Map;

public interface ClbrSystemAdapter {
    public ClbrGenerateAttributeDTO queryClbrGenerateAttribute();

    public Object generateOppClbrBill(List<ClbrBillDTO> var1);

    public boolean sendClbrBillRejectMessage(List<ClbrBillRejectMsgDTO> var1, String var2);

    public boolean sendClbrBillConfirmMessage(ClbrBillConfirmMsgContext var1, List<ClbrBillConfirmMsgDTO> var2, String var3);

    public List<Map<String, Object>> allowCancelClbrBill(List<ClbrBillDTO> var1);

    public boolean sendClbrBillCancelMessage(ClbrBilCancelMsgContext var1, List<ClbrBillCancelMsgDTO> var2);

    public String getSysCode();

    public String getBillSsoUrl(ClbrBillDTO var1, String var2);
}

