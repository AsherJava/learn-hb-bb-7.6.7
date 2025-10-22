/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.designer.web.facade.AutoMappingVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
public class AutoMappingController {
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @ApiOperation(value="\u6839\u636e\u6307\u6807code\u67e5\u8be2\u6307\u6807")
    @PostMapping(value={"query-field-by-code/{dataSchemeKey}/{formKey}"})
    public List<AutoMappingVO> queryFieldByCode(@RequestBody List<AutoMappingVO> list, @PathVariable String dataSchemeKey, @PathVariable String formKey) {
        ArrayList<AutoMappingVO> result = new ArrayList<AutoMappingVO>();
        List allFields = this.dataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
        HashMap<String, DesignDataField> exist = new HashMap<String, DesignDataField>();
        for (DesignDataField obj : allFields) {
            exist.put(obj.getCode(), obj);
        }
        for (AutoMappingVO vo : list) {
            DesignDataField dataField = (DesignDataField)exist.get(vo.getDfCode().toUpperCase());
            DesignDataTable dataTable = null;
            if (null != dataField) {
                dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey());
            }
            boolean zb = true;
            zb = StringUtils.isNotEmpty((String)vo.getDtCode()) ? dataField != null && dataTable != null && dataTable.getCode().equals(vo.getDtCode()) : dataField != null;
            if (zb) {
                if (StringUtils.isEmpty((String)vo.getDtCode())) {
                    vo.setFieldKey(dataField.getKey());
                    vo.setDataTableType(dataTable.getDataTableType());
                    result.add(vo);
                    continue;
                }
                if (!dataTable.getCode().equals(vo.getDtCode())) continue;
                vo.setFieldKey(dataField.getKey());
                vo.setDataTableType(this.dataSchemeService.getDataTable(dataField.getDataTableKey()).getDataTableType());
                result.add(vo);
                continue;
            }
            DesignFormDefine formDefine = this.iDesignTimeViewController.querySoftFormDefine(formKey);
            if (!formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) continue;
            DesignFormSchemeDefine formSchemeDefine = this.iDesignTimeViewController.queryFormSchemeDefine(formDefine.getFormScheme());
            DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
            IEntityModel entityModel = this.iEntityMetaService.getEntityModel(designTaskDefine.getDw());
            if (StringUtils.isNotEmpty((String)vo.getDtCode()) && !iEntityDefine.getCode().equals(vo.getDtCode())) continue;
            List showFields = entityModel.getShowFields();
            for (IEntityAttribute showField : showFields) {
                if (!showField.getCode().equals(vo.getDfCode().toUpperCase())) continue;
                vo.setFieldKey(showField.getCode());
                vo.setFmdm(true);
                result.add(vo);
            }
        }
        return result;
    }

    @ApiOperation(value="\u6839\u636e\u5b58\u50a8\u8868code\u548c\u5b57\u6bb5code\u67e5\u8be2\u5b57\u6bb5")
    @PostMapping(value={"query-field-by-twocode/{dataSchemeKey}"})
    public List<AutoMappingVO> queryFieldByTwoCode(@RequestBody List<AutoMappingVO> list, @PathVariable String dataSchemeKey) {
        ArrayList<AutoMappingVO> result = new ArrayList<AutoMappingVO>();
        List exist = new ArrayList();
        String dt_code = "";
        for (AutoMappingVO obj : list) {
            DesignDataField field;
            DesignDataTable table = this.dataSchemeService.getDataTableByCode(obj.getDtCode());
            if (table == null || !table.getDataSchemeKey().equals(dataSchemeKey) || table.getDataTableType() != DataTableType.DETAIL && table.getDataTableType() != DataTableType.ACCOUNT || (field = this.dataSchemeService.getDataFieldByTableKeyAndCode(table.getKey(), obj.getDfCode())) == null) continue;
            dt_code = obj.getDtCode();
            exist = this.dataSchemeService.getDataFieldByTableCode(obj.getDtCode());
            break;
        }
        if (!exist.isEmpty()) {
            for (AutoMappingVO obj : list) {
                if (!obj.getDtCode().equalsIgnoreCase(dt_code)) continue;
                for (DesignDataField field : exist) {
                    if (!obj.getDfCode().equalsIgnoreCase(field.getCode())) continue;
                    obj.setFieldKey(field.getKey());
                    obj.setDataTableType(this.dataSchemeService.getDataTable(field.getDataTableKey()).getDataTableType());
                    result.add(obj);
                }
            }
        }
        return result;
    }
}

