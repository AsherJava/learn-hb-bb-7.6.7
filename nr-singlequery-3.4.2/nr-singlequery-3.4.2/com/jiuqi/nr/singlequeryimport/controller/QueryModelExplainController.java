/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain
 *  com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject
 *  io.swagger.annotations.Api
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModelExplainImpl;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u67e5\u8be2\u60c5\u51b5\u8bf4\u660e"})
@RequestMapping(value={"/queryModelExplain"})
public class QueryModelExplainController {
    Logger logger = LoggerFactory.getLogger(QueryModelExplainController.class);
    @Autowired
    QueryModelExplainImpl queryModelExplain;

    @RequestMapping(value={"/addExplain"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject addExplain(@RequestBody @Valid QueryModelExplain explain) throws Exception {
        try {
            if (null == explain.getId()) {
                explain.setId(UUID.randomUUID().toString());
            }
            return this.queryModelExplain.addExplain(explain);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/upDataExplain"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject upDataExplain(@RequestBody @Valid QueryModelExplain explain) throws Exception {
        try {
            return this.queryModelExplain.upDataExplain(explain);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/getExplain"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getExplain(@RequestParam String modelId, @RequestParam String orgCode, @RequestParam String period) {
        try {
            return this.queryModelExplain.getExplainByModelIdAndCode(modelId, orgCode, period);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/deleteExplain"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject deleteExplain(@RequestParam String modelId, @RequestParam String orgCode, @RequestParam String period) {
        try {
            return this.queryModelExplain.deleteExplainByModelIdAndCode(modelId, orgCode, period);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/batchAddExplain"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject batchAddExplain(@RequestBody List<QueryModelExplain> explains) {
        try {
            for (QueryModelExplain explain : explains) {
                if (null != explain.getId()) continue;
                explain.setId(UUID.randomUUID().toString());
            }
            return this.queryModelExplain.batchAddExplainByModelId(explains);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "Error");
        }
    }

    @RequestMapping(value={"/batchUpDataExplain"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject batchUpDataExplain(@RequestBody List<QueryModelExplain> explains) {
        try {
            return this.queryModelExplain.batchUpDataExplain(explains);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/deleteExplainByModelId"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject deleteExplainByModelId(@RequestParam String modelId, @RequestParam String period) throws Exception {
        try {
            return this.queryModelExplain.deleteExplainByModelId(modelId, period);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/getExplainByModelId"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getExplainByModelId(@RequestParam String modelId, @RequestParam String period) throws Exception {
        try {
            return this.queryModelExplain.getExplainByModelId(modelId, period);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }
}

