/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.authorize.service.LicenceService
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.save.FormSaveContext
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.authorize.service.LicenceService;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.save.FormSaveContext;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.common.ExceptionEnum;
import com.jiuqi.nr.task.form.controller.dto.CheckFieldCodeBatchDTO;
import com.jiuqi.nr.task.form.controller.dto.EntityModeDTO;
import com.jiuqi.nr.task.form.controller.dto.FormTreeNode;
import com.jiuqi.nr.task.form.controller.dto.LinkMappingDataDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseFieldCodeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeCheckDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.vo.CheckFieldCodeBatchVO;
import com.jiuqi.nr.task.form.controller.vo.EnumFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FindFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FloatRegionVO;
import com.jiuqi.nr.task.form.controller.vo.FormUITreeNode;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.SaveResult;
import com.jiuqi.nr.task.form.dto.SimpleFormDesignerDTO;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import com.jiuqi.nr.task.form.link.dto.RefEntityLinkConfigDTO;
import com.jiuqi.nr.task.form.link.service.ILinkMappingService;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.service.IFormService;
import com.jiuqi.nr.task.form.service.IReverseModelingService;
import com.jiuqi.nr.task.form.table.IDataTableService;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.ExceptionUtils;
import com.jiuqi.nr.task.form.util.I18nNvwaToForm;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-designer/"})
@Api(tags={"\u8868\u5355\u8bbe\u8ba1"})
public class FormDesignController {
    public static final String FORM_DESIGN_URL = "/api/v1/form-designer/";
    @Autowired
    private IFormService formService;
    @Autowired
    private IDataTableService tableService;
    @Autowired
    private ILinkMappingService linkMappingService;
    @Autowired
    private IReverseModelingService reverseModelingService;
    @Autowired
    private LicenceService licenceService;

    @ApiOperation(value="\u67e5\u8be2\u7b80\u5355\u8868\u5355\u6570\u636e")
    @GetMapping(value={"init/{formKey}/{language}"})
    public SimpleFormDesignerDTO querySimpleFormData(@PathVariable String formKey, @PathVariable String language) {
        return this.formService.querySimpleFormData(formKey, I18nNvwaToForm.fromCode(language).getValue());
    }

    @ApiOperation(value="\u67e5\u8be2\u533a\u57df\u6570\u636e")
    @GetMapping(value={"/query/region/{regionKey}"})
    public DataRegionSettingDTO getRegionSetting(@PathVariable String regionKey) throws JQException {
        try {
            return this.formService.getRegionSetting(regionKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u94fe\u63a5\u6570\u636e")
    @GetMapping(value={"/query/link/{linkKey}"})
    public DataLinkSettingDTO getLinkSetting(@PathVariable String linkKey) throws JQException {
        try {
            return this.formService.getLinkSetting(linkKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6279\u91cf\u67e5\u8be2\u94fe\u63a5\u6570\u636e")
    @PostMapping(value={"/query/links"})
    public List<DataLinkSettingDTO> getLinkSetting(@RequestBody List<String> keys) throws JQException {
        try {
            return this.formService.listLinkSetting(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u6570\u636e")
    @GetMapping(value={"/query/field/{fieldKey}"})
    public DataFieldSettingDTO getFieldSetting(@PathVariable String fieldKey) throws JQException {
        try {
            return this.formService.getFieldSetting(fieldKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6279\u91cf\u67e5\u8be2\u6307\u6807\u6570\u636e")
    @PostMapping(value={"/query/fields"})
    public List<DataFieldSettingDTO> getFieldSetting(@RequestBody List<String> keys) throws JQException {
        try {
            return this.formService.listFieldSetting(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6279\u91cf\u67e5\u8be2\u6570\u636e\u8868\u5185\u7684\u6307\u6807\u6570\u636e")
    @GetMapping(value={"/query_fields_by_table/{tableKey}"})
    public List<DataFieldSettingDTO> getFieldSettingByTable(@PathVariable String tableKey) throws JQException {
        try {
            return this.formService.listFieldSettingByTable(tableKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u533a\u57df\u6570\u636e")
    @GetMapping(value={"/query/regions/{formKey}"})
    public List<DataRegionSettingDTO> listRegionSettings(@PathVariable String formKey) throws JQException {
        try {
            return this.formService.listRegionSettingByForm(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u94fe\u63a5\u6570\u636e")
    @GetMapping(value={"/query/links/{formKey}"})
    public List<DataLinkSettingDTO> listLinkSettings(@PathVariable String formKey) throws JQException {
        try {
            return this.formService.listLinkSettingByForm(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u6307\u6807\u6570\u636e")
    @GetMapping(value={"/query/fields/{formKey}"})
    public List<DataFieldSettingDTO> listFieldSettings(@PathVariable String formKey) throws JQException {
        try {
            return this.formService.listFieldSettingByForm(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u6269\u5c55\u7ec4\u4ef6\u6570\u636e")
    @GetMapping(value={"/query/components/{formKey}"})
    public List<ConfigDTO> listComponentSettings(@PathVariable String formKey) throws JQException {
        try {
            return this.formService.listComponentSetting(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u8868\u5355\u6570\u636e")
    @PostMapping(value={"save"})
    @TaskLog(operation="\u4fdd\u5b58\u8868\u5355\u6570\u636e")
    public SaveResult saveFormData(@RequestBody @SFDecrypt FormDesignerDTO formDesignerDTO) throws JQException {
        SaveResult saveResult;
        try {
            saveResult = this.formService.saveFormData(formDesignerDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_01, (Throwable)e);
        }
        if (saveResult != null && saveResult.isError()) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_03, (Object)saveResult);
        }
        return saveResult;
    }

    @ApiOperation(value="\u67e5\u8be2\u663e\u793a\u5b57\u6bb5")
    @GetMapping(value={"/show-fields/query/{key}"})
    public List<EnumFieldVO> queryShowFields(@PathVariable String key) throws JQException {
        try {
            return this.formService.queryShowFields(key);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6d6e\u52a8\u533a\u57df\u7ef4\u5ea6")
    @PostMapping(value={"/region/dimensions/query"})
    public List<String> queryRegionDimensions(@RequestBody FloatRegionVO regionVO) throws JQException {
        try {
            if (regionVO.getTableKey() == null || regionVO.getFieldKeys() != null) {
                return Collections.emptyList();
            }
            DataTableDTO table = this.tableService.getTable(regionVO.getTableKey());
            String[] bizKeys = table.getBizKeys();
            if (bizKeys == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(bizKeys).filter(regionVO.getFieldKeys()::contains).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u521b\u5efa\u6307\u6807")
    @PostMapping(value={"/reverse-modeling/create"})
    public ReverseModeDTO createFieldMode(@RequestBody ReverseModeParam param) throws JQException {
        try {
            return this.reverseModelingService.reverseModeling(param);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u6307\u6807\u6807\u8bc6\u91cd\u590d")
    @PostMapping(value={"/reverse-modeling/check/field"})
    public void checkFieldCode(@RequestBody ReverseModeCheckDTO checkDTO) throws JQException {
        try {
            this.reverseModelingService.checkFieldCode(checkDTO);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u6570\u636e\u8868\u6807\u8bc6\u91cd\u590d")
    @PostMapping(value={"/reverse-modeling/check-table-code"})
    public void checkTableCode(@RequestBody ReverseModeCheckDTO checkDTO) throws JQException {
        try {
            this.reverseModelingService.checkTableCode(checkDTO);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u53c2\u6570\u4fe1\u606f\u68c0\u67e5")
    @GetMapping(value={"globalCheck/{formKey}"})
    public CheckResult globalCheck(@PathVariable String formKey) throws JQException {
        return new CheckResult();
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u5b9a\u4e49")
    @GetMapping(value={"/query/form/{formKey}"})
    public FormDTO getFormDefine(@PathVariable String formKey) throws JQException {
        try {
            return this.formService.getFormDefine(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u8054\u52a8")
    @PostMapping(value={"/link-mapping/query/{formKey}"})
    public LinkMappingDataDTO queryLinkMapping(@PathVariable String formKey, @RequestBody List<LinkMappingDTO> list) throws JQException {
        try {
            return this.linkMappingService.queryLinkMappingData(formKey, list);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u7ea7\u6b21\u8054\u52a8\u679a\u4e3e\u7ea7\u6b21\u4fe1\u606f")
    @PostMapping(value={"/entity-level/query/"})
    public List<EntityModeDTO> queryEntityLevel(@RequestBody List<String> entityIds) throws JQException {
        try {
            return this.linkMappingService.queryEntityLevel(entityIds);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u62a5\u8868\u6811\u5f62")
    @GetMapping(value={"/formTreeByScheme/{formScheme}"})
    public List<FormTreeNode> formTreeByScheme(@PathVariable String formScheme) throws JQException {
        try {
            return this.formService.formTreeByScheme(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u66f4\u65b0\u8fdb\u5ea6")
    @GetMapping(value={"/query_formula_progress/{progressId}"})
    public ProgressItem queryFormulaProgress(@PathVariable String progressId) throws JQException {
        try {
            return this.formService.getProgress(progressId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u8868\u5185\u662f\u5426\u5b58\u5728\u6570\u636e")
    @PostMapping(value={"/table/check/data/"})
    public Map<String, Boolean> checkTableData(@RequestBody List<String> tableKeys) throws JQException {
        try {
            return this.formService.checkTableData(tableKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5efa\u6a21\u6388\u6743\u6743\u9650\u67e5\u8be2")
    @PostMapping(value={"/auth/query"})
    public Map<String, Object> designerAuth() throws JQException {
        HashMap<String, Object> authInfo = new HashMap<String, Object>();
        try {
            String authorizeConfigToExcel = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.excel");
            this.formService.getValidationResult(authorizeConfigToExcel, "Excel", authInfo);
            String authorizeConfigToJIO = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.jio");
            this.formService.getValidationResult(authorizeConfigToJIO, "JIO", authInfo);
            String authorizeConfigToDesigner = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.designer");
            this.formService.getValidationResult(authorizeConfigToDesigner, "DESIGNER", authInfo);
            try {
                String reverseModeling = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.reverseModeling");
                authInfo.put("REFLEX", reverseModeling);
                String report = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.report");
                this.formService.getValidationResult(report, "REPORT", authInfo);
            }
            catch (Exception e) {
                authInfo.put("REFLEX", "1");
                authInfo.put("REPORT", true);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0005, (Throwable)e);
        }
        return authInfo;
    }

    @ApiOperation(value="\u6839\u636e\u6307\u6807\u6807\u8bc6\u548c\u6570\u636e\u8868\u6807\u8bc6\u67e5\u8be2\u6307\u6807")
    @PostMapping(value={"/tableAndField/findByCode"})
    public FindFieldVO findFieldByTableCodeAndFieldCodes(@RequestBody List<String> codes) throws JQException {
        try {
            return this.formService.findFieldByTableCodeAndFieldCodes(codes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6279\u91cf\u6821\u9a8c\u6307\u6807\u6807\u8bc6")
    @PostMapping(value={"/batch-check/fieldCodes"})
    public List<CheckFieldCodeBatchVO> checkFieldCodes(@RequestBody List<CheckFieldCodeBatchDTO> fieldCodeBatchList) throws JQException {
        try {
            return this.formService.checkFieldCodes(fieldCodeBatchList);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0009, (Throwable)e);
        }
    }

    @ApiOperation(value="\u8bbe\u8ba1\u5668\u67e5\u8be2\u8868\u5355\u6811\u5f62")
    @GetMapping(value={"/form-tree/locate/{formSchemeKey}/{formKey}"})
    public List<UITreeNode<FormUITreeNode>> locateFormTree(@PathVariable String formSchemeKey, @PathVariable String formKey) throws JQException {
        try {
            return this.formService.locateSelectFormTree(formSchemeKey, formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0009, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6307\u6807\u6807\u8bc6\u6821\u9a8c\uff1a\u67e5\u8be2\u6570\u636e\u65b9\u6848\u4e0b\u6240\u6709\u6307\u6807\u6807\u8bc6")
    @GetMapping(value={"/reverse-modeling/allFieldCodes/{dataSchemeKey}"})
    public List<ReverseFieldCodeDTO> allFieldCodes(@PathVariable String dataSchemeKey) throws JQException {
        try {
            return this.reverseModelingService.queryAllFieldCodes(dataSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0009, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u57fa\u7840\u6570\u636e\u548c\u7ec4\u7ec7\u673a\u6784\u7684\u6807\u9898\u548c\u6807\u8bc6")
    @GetMapping(value={"/getLinkRefEntityConfig/{entityID}"})
    public RefEntityLinkConfigDTO getLinkRefEntityConfig(@PathVariable String entityID) throws JQException {
        try {
            return this.formService.getLinkRefEntityConfig(entityID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0010, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6253\u5370\u6a21\u7248\u662f\u5426\u9700\u8981\u4fee\u6539")
    @PostMapping(value={"/print/template/need/change"})
    public Boolean isPrintTemplateNeedChange(@RequestBody FormSaveContext context) throws JQException {
        try {
            return this.formService.isPrintTemplateNeedChange(context);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_02, (Throwable)e);
        }
    }
}

