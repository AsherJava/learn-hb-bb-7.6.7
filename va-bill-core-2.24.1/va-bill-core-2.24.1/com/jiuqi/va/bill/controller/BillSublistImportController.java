/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.BillSublistImportVO;
import com.jiuqi.va.bill.service.BillSublistImportService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/sublist/import"})
public class BillSublistImportController {
    @Autowired
    private BillSublistImportService billSublistImportService;

    @GetMapping(value={"/progress/{key}"})
    R list(@PathVariable(value="key") String key) {
        try {
            BillSublistImportVO billSublistImportVO = this.billSublistImportService.getProgress(key);
            return R.ok().put("progress", (Object)billSublistImportVO);
        }
        catch (Exception e) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.workflowaction.handlefailed"));
        }
    }
}

