/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import java.util.Set;

public interface ClbrArbitrationService {
    public PageInfo<ClbrBillDTO> getArbitrationListPage(ClbrArbitrationQueryParamDTO var1);

    public Integer getArbitrationTodoNum(ClbrTodoCountVO var1);

    public void cancelSynergy(ClbrBillRejectDTO var1);

    public void consentArbitration(Set<String> var1);

    public void cancelRejectArbitration(ClbrArbitrationCancel var1);
}

