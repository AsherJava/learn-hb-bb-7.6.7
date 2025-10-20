/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.BillDTO;
import com.jiuqi.va.bill.service.BillService;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill"})
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping(value={"data/load"})
    public PageVO<Map<String, List<Map<String, Object>>>> getBillList(@RequestBody BillDTO billDTO) {
        return this.billService.getBill(billDTO);
    }
}

