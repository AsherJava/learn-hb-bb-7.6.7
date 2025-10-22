/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.amount.test;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.nr.designer.amount.init.AmountCreateProcessor;
import com.jiuqi.nr.designer.amount.service.AmountService;
import com.jiuqi.nr.designer.web.treebean.AmountObject;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
public class WebRest {
    @Autowired
    private AmountService amountService;

    @ApiOperation(value="amountInterface")
    @GetMapping(value={"/amountInterface"})
    public AmountObject getAllEntityView() throws Exception {
        List<AmountObject> queryAllAmount = this.amountService.queryAllAmount();
        List<AmountObject> queryAmountByParent = this.amountService.queryAmountByParent("RENMINBI");
        AmountObject queryById = this.amountService.queryById(queryAllAmount.get(0).getId());
        AmountObject queryByCode = this.amountService.queryByCode(queryAllAmount.get(0).getCode());
        return null;
    }

    @ApiOperation(value="initamount")
    @GetMapping(value={"/initamount"})
    public void initAmount() throws Exception {
        Logger logger = LoggerFactory.getLogger(AmountCreateProcessor.class);
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u91cf\u7eb2\u6570\u636e");
        AmountCreateProcessor bean = (AmountCreateProcessor)SpringUtil.getBean(AmountCreateProcessor.class);
        bean.createData();
        logger.info("\u91cf\u7eb2\u6570\u636e\u521d\u59cb\u5316\u5b8c\u6210");
    }

    @ApiOperation(value="\u91cf\u7eb2")
    @GetMapping(value={"/queryAllAmount/{parent}"})
    public List<AmountObject> getAllAmount(@PathVariable String parent) throws Exception {
        List<AmountObject> queryAmountByParent = this.amountService.queryAmountByParent(parent);
        return queryAmountByParent;
    }
}

