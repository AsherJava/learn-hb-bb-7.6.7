/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.efdc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.bean.EntityDataObject;
import com.jiuqi.nr.efdc.internal.pojo.AssistEntitys;
import com.jiuqi.nr.efdc.internal.pojo.EntityQueryVO;
import com.jiuqi.nr.efdc.internal.utils.EFDCConstants;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import com.jiuqi.nr.efdc.service.DataCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/efdc"})
@Api(tags={"EFDC"})
public class DataQueryCenterController {
    @Autowired
    private DataCenterService dataQueryService;
    ObjectMapper mappper = new ObjectMapper();

    @GetMapping(value={"/init-task"})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u4efb\u52a1")
    public NrResult initTaskList() {
        NrResult allTask = this.dataQueryService.getAllTask();
        return allTask;
    }

    @GetMapping(value={"/query-formulascheme"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u65b9\u6848\u4e0e\u53d6\u6570\u65b9\u6848")
    public NrResult queryFormulaScheme(@RequestParam(value="schemeKey", name="schemeKey") String schemeKey) {
        String fromScheme = null;
        NrResult result = new NrResult();
        HashMap<String, List<FormulaSchemeDefine>> map = new HashMap<String, List<FormulaSchemeDefine>>(2);
        fromScheme = schemeKey;
        try {
            List<FormulaSchemeDefine> fromSchemes = this.dataQueryService.getAllFormulaSchemeByFromScheme(fromScheme);
            map.put("fromSchemes", fromSchemes);
            List<FormulaSchemeDefine> rptFormulaSchemes = this.dataQueryService.getAllRPTFormulaSchemeDefinesByFormScheme(fromScheme);
            map.put("RPTFromSchemes", rptFormulaSchemes);
            result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
            result.setData(map);
        }
        catch (Exception e) {
            result.setStatus(EFDCConstants.RETURN_FAIL);
            result.setMsg("Query Error!");
        }
        return result;
    }

    @GetMapping(value={"/query-entitydata"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u4e3b\u4f53\u4e2d\u5355\u4f4d\u6570\u636e")
    public NrResult queryEntityDataByKey(@RequestParam(value="tableKey") String key) {
        NrResult result = NrResult.ok();
        String tableKey = key;
        List<EntityDataObject> entityData = this.dataQueryService.getEntityData(tableKey);
        result.setData(entityData);
        return result;
    }

    @PostMapping(value={"/query-dimension"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5176\u4ed6\u7ef4\u5ea6\u7684\u6570\u636e")
    public NrResult queryAssistDimension(@Valid @RequestBody EntityQueryVO param) {
        NrResult result = NrResult.ok();
        List<AssistEntitys> data = this.dataQueryService.getAssistDimData(param);
        result.setData(data);
        return result;
    }

    @GetMapping(value={"/query-entity"})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e3b\u4f53\u89c6\u56fe")
    public NrResult queryEntityByKey(@RequestParam(value="tableKey") String key) {
        NrResult result = new NrResult();
        String tableKey = key;
        result = this.dataQueryService.getEntity(tableKey);
        return result;
    }

    @PostMapping(value={"/query-children"})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u5b50\u8282\u70b9")
    public NrResult queryEntityChildren(@Valid @RequestBody EntityQueryVO param) {
        NrResult result = new NrResult();
        List<EntityDataObject> childrenData = this.dataQueryService.getChildrenData(param.getDimKey(), param.getViewKey());
        if (childrenData.size() > 0) {
            result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        } else {
            result.setStatus(EFDCConstants.RETURN_400_SUCCESS);
        }
        result.setData(childrenData);
        return result;
    }

    @GetMapping(value={"/query-period"})
    @ApiOperation(value="\u67e5\u8be2\u65f6\u671f")
    public NrResult getPeriod(@RequestParam(value="type") String type) {
        NrResult result = new NrResult();
        PeriodWrapper period = this.dataQueryService.transformToPeriod(type);
        result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        result.setData(period.toString());
        return result;
    }

    @GetMapping(value={"/selected-entity/{selectorKey}"})
    @ApiOperation(value="\u83b7\u53d6\u9009\u62e9\u7684\u5355\u4f4d")
    public NrResult getSelectedEntity(@PathVariable String selectorKey) {
        NrResult result = new NrResult();
        List<String> selectedEntity = this.dataQueryService.getSelectedEntity(selectorKey);
        result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        result.setData(selectedEntity);
        return result;
    }
}

