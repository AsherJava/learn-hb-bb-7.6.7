/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.option.BillAttachOptionDO;
import com.jiuqi.va.bill.service.BillAttachOptionService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/attach"})
public class BillAttachOptionController {
    @Autowired
    private BillAttachOptionService billAttachOptionService;

    @PostMapping(value={"/option/list"})
    List<OptionItemVO> list(@RequestBody OptionItemDTO param) {
        return this.billAttachOptionService.list(param);
    }

    @PostMapping(value={"/option/update"})
    R update(@RequestBody BillAttachOptionDO option) {
        return this.billAttachOptionService.update(option);
    }
}

