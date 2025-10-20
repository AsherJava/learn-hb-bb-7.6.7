/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller.action;

import com.jiuqi.va.bill.domain.action.CustomActionParamDTO;
import com.jiuqi.va.bill.service.action.BillActionService;
import com.jiuqi.va.biz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value={"/bill/action"})
@RestController
public class BillActionController {
    @Autowired
    private BillActionService billActionService;

    @PostMapping(value={"/custom-action/execute"})
    public R<String> billListCustomActionExecute(@RequestBody CustomActionParamDTO customActionParamDTO) {
        return this.billActionService.billListCustomActionExecute(customActionParamDTO);
    }
}

