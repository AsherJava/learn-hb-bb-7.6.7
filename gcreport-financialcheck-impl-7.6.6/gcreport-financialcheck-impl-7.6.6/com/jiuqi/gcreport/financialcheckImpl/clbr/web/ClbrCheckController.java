/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.clbr.ClbrCheckClient
 *  com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.clbr.service.ClbrCheckService;
import com.jiuqi.gcreport.financialcheckapi.clbr.ClbrCheckClient;
import com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ClbrCheckController
implements ClbrCheckClient {
    @Autowired
    ClbrCheckService clbrCheckService;

    public BusinessResponseEntity updateVoucherGcNumber(@RequestBody List<ClbrVoucherItemVO> items) {
        this.clbrCheckService.updateVoucherGcNumber(items);
        return BusinessResponseEntity.ok();
    }
}

