/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.common.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.paramcheck.bean.CheckResult;
import com.jiuqi.nr.common.paramcheck.bean.FixParam;
import com.jiuqi.nr.common.paramcheck.bean.ParamCheckVO;
import com.jiuqi.nr.common.paramcheck.internal.CheckManagerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/paramcheck"})
public class ParamCheckController {
    @Autowired
    private CheckManagerService checkManagerService;

    @GetMapping(value={"get-bean"})
    public List<ParamCheckVO> getAllBeans() {
        return this.checkManagerService.getAllParamCheckService();
    }

    @GetMapping(value={"check-param/{beanName}"})
    public CheckResult checkParam(@PathVariable String beanName) throws JQException {
        return this.checkManagerService.executeParamCheck(beanName);
    }

    @PostMapping(value={"fix-param"})
    public CheckResult fixParam(@RequestBody FixParam param) throws JQException {
        return this.checkManagerService.executeParamFix(param);
    }
}

