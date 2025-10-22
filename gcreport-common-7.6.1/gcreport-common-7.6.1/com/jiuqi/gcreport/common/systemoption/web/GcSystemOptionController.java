/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.systemoption.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/gcSystemOption"})
public class GcSystemOptionController {
    @GetMapping(value={"/getFinancialCubes"})
    public BusinessResponseEntity<Boolean> getFinancialCubes() {
        String optionValue = GcSystermOptionTool.getOptionValue("FINANCIAL_CUBES_ENABLE");
        return BusinessResponseEntity.ok((Object)"1".equals(optionValue));
    }
}

