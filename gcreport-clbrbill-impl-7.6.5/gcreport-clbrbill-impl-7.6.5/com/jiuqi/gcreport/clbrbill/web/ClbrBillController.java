/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbrbill.web;

import com.jiuqi.gcreport.clbrbill.dto.ClbrOperatorDTO;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillService;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/clbrbill"})
public class ClbrBillController {
    @Autowired
    private ClbrBillService clbrBillService;

    @PostMapping(value={"/clbrOperate"})
    public BusinessResponseEntity<Object> clbrOperate(@RequestBody ClbrOperatorDTO params) {
        try {
            Object result = this.clbrBillService.clbrOperate(params);
            return BusinessResponseEntity.ok((Object)result);
        }
        catch (Throwable e) {
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }
}

