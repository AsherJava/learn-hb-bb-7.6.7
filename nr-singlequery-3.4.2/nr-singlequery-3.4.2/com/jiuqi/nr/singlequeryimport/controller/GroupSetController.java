/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.designer.util.EntityDefineObject
 *  com.jiuqi.nr.designer.web.rest.FormulaParserController
 *  com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject
 *  io.swagger.annotations.Api
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.designer.util.EntityDefineObject;
import com.jiuqi.nr.designer.web.rest.FormulaParserController;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.EnumParsms;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import com.jiuqi.nr.singlequeryimport.service.GroupSetService;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u83b7\u53d6\u5206\u7ec4\u8bbe\u7f6e\u7684\u679a\u4e3e\u503c"})
@RequestMapping(value={"/customGroupSet"})
public class GroupSetController {
    @Autowired
    GroupSetService groupSetService;
    @Autowired
    FormulaParserController formulaParserController;
    @Autowired
    IRunTimeViewController iRunTimeViewController;

    @RequestMapping(value={"/getLinks"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getLinks(@RequestBody List<EnumParsms> enumParsmsList) throws JQException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (EnumParsms e : enumParsmsList) {
            ResultObject menu = this.getMenu(e.getFormkey(), e.getLinkExpression());
            if (!menu.isSuccess() && null == menu.getData()) continue;
            result.put(e.getCode(), menu.getData());
        }
        return new ResultObject(result);
    }

    @RequestMapping(value={"/getMenu"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getMenu(@RequestParam String formKey, @RequestParam String linkExpression) throws JQException {
        List<EntityDefineObject> links = this.groupSetService.getLinks(formKey, linkExpression);
        if (!links.isEmpty()) {
            return new ResultObject((Object)this.groupSetService.getMnum(links.get(0).getCode()));
        }
        return new ResultObject(false, "\u8be5\u6307\u6807\u6ca1\u6709\u5173\u8054\u5b9e\u4f53\u6570\u636e");
    }
}

