/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.bill.service.BillRuleOptionService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/rule"})
public class BillRuleOptionController {
    @Autowired
    private BillRuleOptionService billRuleOptionService;

    @PostMapping(value={"/option/list"})
    List<BillRuleOptionVO> list(@RequestBody OptionItemDTO param) {
        return this.billRuleOptionService.list(param);
    }

    @PostMapping(value={"/option/update"})
    R update(@RequestBody BillRuleOptionVO option) {
        return this.billRuleOptionService.update(option);
    }
}

