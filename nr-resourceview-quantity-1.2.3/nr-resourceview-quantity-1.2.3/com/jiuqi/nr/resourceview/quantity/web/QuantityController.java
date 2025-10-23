/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.resourceview.quantity.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.resourceview.quantity.util.QuantityConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/nvwa-quantity"})
public class QuantityController {
    private static final Logger logger = LoggerFactory.getLogger(QuantityController.class);
    @Autowired
    private IQuantityService quantityService;

    @GetMapping(value={"/base/{parentId}"})
    public boolean hasBase(@PathVariable String parentId) throws JQException {
        if (parentId.startsWith("QI")) {
            return this.quantityService.hasBase4QuantityCategory(QuantityConvert.getRealId(parentId));
        }
        if (parentId.startsWith("QC")) {
            return this.quantityService.hasBase4QuantityUnit(QuantityConvert.getRealId(parentId));
        }
        return false;
    }
}

