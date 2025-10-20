/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.budget.common.utils.ResultUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.controller;

import com.jiuqi.budget.autoconfigure.Gmc3ProductLineConfig;
import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.common.utils.ResultUtil;
import com.jiuqi.budget.components.ProductNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/biz/product"})
public class BudProductController {
    @Autowired
    private Gmc3ProductLineConfig productLineConfig;

    @GetMapping(value={"/getType"})
    public ResultVO<String> getType() {
        return ResultUtil.ok(null, (Object)this.productLineConfig.getProduct().name());
    }

    @GetMapping(value={"/getName"})
    public ResultVO<String> getName() {
        return ResultUtil.ok(null, (Object)ProductNameUtil.getProductName());
    }
}

