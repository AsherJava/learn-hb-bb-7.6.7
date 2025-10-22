/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo
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
package com.jiuqi.nr.unit.treeimpl.web;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.treeimpl.web.request.UpdateCaptionFieldsContext;
import com.jiuqi.nr.unit.treeimpl.web.service.ICaptionFieldsSettingService;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
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
@RequestMapping(value={"/api/v2/data-entry-unit-tree/settings"})
@Api(tags={"\u5f55\u5165\u5355\u4f4d\u6811\u914d\u7f6e\u4fe1\u606f\u8bf7\u6c42-API"})
public class CaptionFieldSettingsController {
    @Resource
    private ICaptionFieldsSettingService service;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IEntityMetaService metaService;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u663e\u793a\u5c01\u9762\u4ee3\u7801\u5c5e\u6027\u5b57\u6bb5\u8bbe\u7f6e")
    @GetMapping(value={"/inquire-caption-fields"})
    public FMDMCaptionFieldSettingInfo inquireCaptionFields(@RequestParam(name="formSchemeKey") String formSchemeKey) {
        return this.service.inquireCaptionFields(formSchemeKey);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u663e\u793a\u5c01\u9762\u4ee3\u7801\u5c5e\u6027\u5b57\u6bb5\u4fdd\u5b58")
    @PostMapping(value={"/save-caption-fields"})
    public String saveCaptionFieldsScheme(@Valid @RequestBody FMDMCaptionFieldsSaveInfo scheme) {
        return this.service.saveCaptionFieldsScheme(scheme) + "";
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u663e\u793a\u5c01\u9762\u4ee3\u7801\u5c5e\u6027\u5b57\u6bb5-\u5e94\u7528\u5230\u5168\u5c40")
    @PostMapping(value={"/apply-global-caption-fields"})
    public String applyGlobalCaptionFields(@Valid @RequestBody FMDMCaptionFieldsSaveInfo scheme) {
        return this.service.saveCaptionFieldsScheme(scheme, "out_of_user_settings") + "";
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u589e\u51cf\u663e\u793a\u5b57\u6bb5")
    @PostMapping(value={"/update-caption-fields"})
    public String updateCaptionFields(@Valid @RequestBody FMDMCaptionFieldsUpdateInfo updateInfo) {
        return this.service.updateCaptionFieldsScheme(updateInfo) + "";
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u67e5\u8be2\u4e3b\u4f53\u5b57\u6bb5")
    @GetMapping(value={"/entity-fields"})
    public List<EntityFiledInfo> querySortFields(@RequestParam(name="entityId") String entityId) {
        return this.service.querySortFields(entityId);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u6811-\u589e\u51cf\u663e\u793a\u5b57\u6bb5")
    @PostMapping(value={"/update-caption-code-field"})
    public String updateCaptionCodeField(@RequestBody UpdateCaptionFieldsContext params) {
        IUnitTreeContext treeContext = this.contextBuilder.createTreeContext(params.getContextData());
        FMDMCaptionFieldsUpdateInfo updateInfo = new FMDMCaptionFieldsUpdateInfo();
        FormSchemeDefine formScheme = treeContext.getFormScheme();
        if (formScheme != null) {
            IEntityDefine entityDefine = treeContext.getEntityDefine();
            IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
            updateInfo.setFormSchemeKey(formScheme.getKey());
            ArrayList<String> updateFields = new ArrayList<String>();
            updateFields.add(entityModel.getCodeField().getID());
            if (params.isShowCodeInfo()) {
                updateInfo.setAddCaptionFields(updateFields);
            } else {
                updateInfo.setDelCaptionFields(updateFields);
            }
            return this.service.updateCaptionFieldsScheme(updateInfo) + "";
        }
        return "";
    }
}

