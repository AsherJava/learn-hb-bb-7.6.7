/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.estimation.web;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationSchemeInfo;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeManageService;
import com.jiuqi.nr.data.estimation.web.tasktree.EstimationTaskTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/estimation/manager"})
@Api(tags={"what-if\u6d4b\u7b97\u65b9\u6848\u7ba1\u7406\u529f\u80fdAPI"})
public class EstimationSchemeManageController {
    @Resource
    private EstimationSchemeManageService service;

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u4efb\u52a1-\u62a5\u8868\u65b9\u6848-\u6811")
    @GetMapping(value={"/loading-task-tree"})
    public List<ITree<EstimationTaskTreeNode>> loadingTaskTree() {
        return this.service.loadingTaskTree();
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6d4b\u7b97\u65b9\u6848")
    @GetMapping(value={"/loading-manage-scheme"})
    public EstimationSchemeInfo loadManageEstimationScheme(@RequestParam(name="formSchemeId") String formSchemeId) {
        return this.service.loadManageEstimationScheme(formSchemeId);
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b-\u6d4b\u7b97\u65b9\u6848-\u7684\u6d4b\u7b97\u516c\u5f0f\u65b9\u6848")
    @GetMapping(value={"/loading-calc-formula-schemes"})
    public List<EstimationFormulaSchemeInfo> loadEstimationCalcFormulaSchemes(@RequestParam(name="formSchemeId") String formSchemeId) {
        return this.service.loadEstimationCalcFormulaSchemes(formSchemeId);
    }

    @ResponseBody
    @ApiOperation(value="\u6821\u9a8c\u6d4b\u7b97\u65b9\u6848\u6807\u8bc6\u662f\u5426\u91cd\u590d")
    @GetMapping(value={"/valid-scheme-code"})
    public boolean validEstimationSchemeCode(@RequestParam(name="formSchemeId") String formSchemeId, @RequestParam(name="schemeCode") String schemeCode) {
        return this.service.validEstimationSchemeCode(formSchemeId, schemeCode);
    }

    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u7ba1\u7406\u5458\u5efa\u7684\u6d4b\u7b97\u65b9\u6848")
    @PostMapping(value={"/save-manage-scheme"})
    public String saveEstimationScheme(@Valid @RequestBody EstimationSchemeInfo schemeInfo) {
        return this.service.saveEstimationScheme(schemeInfo);
    }
}

