/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.billcore.offsetcheck.api.BillOffsetCheckClient
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.billcore.offsetcheck.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.billcore.offsetcheck.api.BillOffsetCheckClient;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.service.BillOffsetCheckService;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillOffsetCheckController
implements BillOffsetCheckClient {
    @Autowired
    BillOffsetCheckService billOffsetCheckService;

    public BusinessResponseEntity<Pagination<BillOffsetCheckInfoDTO>> listOffsetCheckInfos(GcDataTraceCondi condi) {
        Pagination<BillOffsetCheckInfoDTO> offsetPage = this.billOffsetCheckService.dataTraceOffsetCheckList(condi);
        return BusinessResponseEntity.ok(offsetPage);
    }

    public BusinessResponseEntity<List<OffsetTraceResultVO>> offsetCheckAmtTrace(GcDataTraceCondi condi) {
        return BusinessResponseEntity.ok(this.billOffsetCheckService.offsetCheckAmtTrace(condi));
    }
}

