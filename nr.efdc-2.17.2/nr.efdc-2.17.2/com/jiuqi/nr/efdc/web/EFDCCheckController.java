/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.efdc.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.efdc.pojo.FormulaExceptionObj;
import com.jiuqi.nr.efdc.service.impl.EFDCCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value="EFDC\u516c\u5f0f:")
@JQRestController
public class EFDCCheckController {
    @Resource
    EFDCCheckService efdcCheckService;

    @ApiOperation(value="\u8c03\u7528EFDC\u63a5\u53e3\u6821\u9a8c\u516c\u5f0f")
    @PostMapping(value={"/api/efdcCheck"})
    public List<FormulaExceptionObj> checkFormula(@RequestBody List<FormulaExceptionObj> efdcFormulas) throws JQException {
        List<FormulaExceptionObj> checkres = this.efdcCheckService.checkEfdcFormula(efdcFormulas);
        return checkres;
    }
}

