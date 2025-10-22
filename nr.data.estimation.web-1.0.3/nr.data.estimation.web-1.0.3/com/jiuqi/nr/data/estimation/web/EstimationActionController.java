/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.estimation.web;

import com.jiuqi.nr.data.estimation.web.request.ActionOfCalculateParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckFormsParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckSnapShotParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfEstimationParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfImpFormDataParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfSaveFormDataParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfWriteFormData;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeDataCalculateService;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeDataInputService;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeDataOutputService;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeFormRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/estimation/action"})
@Api(tags={"what-if\u6d4b\u7b97\u529f\u80fd\u6309\u94ae"})
public class EstimationActionController {
    @Resource
    private EstimationSchemeFormRestService formRestService;
    @Resource
    private EstimationSchemeDataInputService dataInputService;
    @Resource
    private EstimationSchemeDataOutputService dataOutputService;
    @Resource
    private EstimationSchemeDataCalculateService calculateService;

    @ResponseBody
    @ApiOperation(value="\u9009\u62e9\u62a5\u8868")
    @PostMapping(value={"/reset/estimation-scheme-forms"})
    public String restEstimationSchemeForms(@Valid @RequestBody ActionOfCheckFormsParam actionParameter) {
        return this.formRestService.restEstimationSchemeForms(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u4ece\u5f55\u5165\u8fd8\u539f")
    @PostMapping(value={"/take-data/from-original"})
    public String restoreFromOriginal(@Valid @RequestBody ActionOfCheckFormsParam actionParameter) {
        return this.dataInputService.restoreFromOriginal(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u4ece\u5feb\u7167\u8fd8\u539f")
    @PostMapping(value={"/take-data/from-snapshot"})
    public String restoreFromSnapshot(@Valid @RequestBody ActionOfCheckSnapShotParam actionParameter) {
        return this.dataInputService.restoreFromSnapshot(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u4eceexcel\u5bfc\u5165")
    @PostMapping(value={"/take-data/from-excel-import"})
    public String restoreFromExcelImport(@Valid @RequestBody ActionOfImpFormDataParam actionParameter) {
        return this.dataInputService.restoreFromExcelImport(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u5f53\u524d\u8868\u53d6\u6570")
    @PostMapping(value={"/take-data/one-form-with-formula"})
    public String calculateWitOneForm(@Valid @RequestBody ActionOfCalculateParam actionParameter) {
        return this.calculateService.calculateWitOneForm(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u6240\u6709\u8868\u53d6\u6570")
    @PostMapping(value={"/take-data/all-form-with-formula"})
    public String calculateWitAllForm(@Valid @RequestBody ActionOfCalculateParam actionParameter) {
        return this.calculateService.calculateWitAllForm(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u6d4b\u7b97")
    @PostMapping(value={"/execute/do-data-calculate"})
    public String doDataCalculate(@Valid @RequestBody ActionOfEstimationParam actionParameter) {
        return this.calculateService.doDataCalculate(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u6682\u5b58")
    @PostMapping(value={"/execute/do-save-form-data"})
    public String doSaveFormData(@Valid @RequestBody ActionOfSaveFormDataParam actionParameter) {
        return this.dataOutputService.doSaveFormData(actionParameter);
    }

    @ResponseBody
    @ApiOperation(value="\u56de\u5199")
    @PostMapping(value={"/execute/do-write-form-data"})
    public String doWriteFormData(@Valid @RequestBody ActionOfWriteFormData actionParameter) {
        return this.dataOutputService.doWriteFormData(actionParameter);
    }
}

