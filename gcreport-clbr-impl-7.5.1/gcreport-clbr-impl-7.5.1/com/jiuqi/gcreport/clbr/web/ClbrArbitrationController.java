/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.clbr.api.ClbrArbitrationClient
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbr.api.ClbrArbitrationClient;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.service.ClbrArbitrationService;
import com.jiuqi.gcreport.clbr.service.ClbrBillService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClbrArbitrationController
implements ClbrArbitrationClient {
    @Autowired
    private ClbrArbitrationService clbrArbitrationService;
    @Autowired
    private ClbrBillService clbrBillService;

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryArbitrationList(ClbrArbitrationQueryParamDTO clbrArbitrationQueryParamDTO) {
        PageInfo<ClbrBillDTO> arbitrationListPage = this.clbrArbitrationService.getArbitrationListPage(clbrArbitrationQueryParamDTO);
        PageInfo<ClbrBillVO> voPageInfo = ClbrBillConverter.convertDTO2VO(arbitrationListPage);
        return BusinessResponseEntity.ok(voPageInfo);
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> singleQueryArbitrationList(ClbrArbitrationQueryParamDTO clbrArbitrationQueryParamDTO) {
        PageInfo<ClbrBillDTO> arbitrationListPage = this.clbrArbitrationService.getArbitrationListPage(clbrArbitrationQueryParamDTO);
        PageInfo<ClbrBillVO> voPageInfo = ClbrBillConverter.convertDTO2VO(arbitrationListPage);
        return BusinessResponseEntity.ok(voPageInfo);
    }

    public BusinessResponseEntity<Object> rejectClbrArbitrationCancel(ClbrArbitrationCancel clbrArbitrationCancel) {
        if (StringUtils.isEmpty((String)clbrArbitrationCancel.getArbitrationReject())) {
            return BusinessResponseEntity.error((String)"301", (String)"\u4ef2\u88c1\u9a73\u56de\u539f\u56e0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.clbrArbitrationService.cancelRejectArbitration(clbrArbitrationCancel);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Integer> getArbitrationBacklogNum(ClbrTodoCountVO clbrTodoCountVO) {
        Integer arbitrationTodoNum = this.clbrArbitrationService.getArbitrationTodoNum(clbrTodoCountVO);
        return BusinessResponseEntity.ok((Object)arbitrationTodoNum);
    }

    public BusinessResponseEntity<Object> synergyCancel(ClbrBillRejectDTO clbrBillRejectDTO) {
        if (StringUtils.isEmpty((String)clbrBillRejectDTO.getRejectReason())) {
            return BusinessResponseEntity.error((String)"301", (String)"\u534f\u540c\u9a73\u56de\u539f\u56e0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.clbrArbitrationService.cancelSynergy(clbrBillRejectDTO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> rejectClbrArbitrationConsent(Set<String> clbrBillIds) {
        this.clbrArbitrationService.consentArbitration(clbrBillIds);
        return BusinessResponseEntity.ok();
    }
}

