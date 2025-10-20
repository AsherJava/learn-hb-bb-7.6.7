/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.bill.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.bill.service.DcVoucherBillListActionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcVoucherBillListActionController {
    public static final String API_PATH = "/api/datacenter/v1/dcVoucherBillListAction/";
    @Autowired
    private DcVoucherBillListActionService billListActionService;

    @PostMapping(value={"/api/datacenter/v1/dcVoucherBillListAction/unlock/{billDefineCode}"})
    public BusinessResponseEntity<String> unlock(@PathVariable(value="billDefineCode") String billDefineCode, @RequestBody List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return BusinessResponseEntity.ok((Object)"\u8bf7\u52fe\u9009\u6570\u636e\u540e\u5728\u53d6\u6d88\u9501\u5b9a\uff01");
        }
        return BusinessResponseEntity.ok((Object)this.billListActionService.unlock(billDefineCode, idList));
    }
}

