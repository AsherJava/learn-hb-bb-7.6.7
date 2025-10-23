/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.service.check;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ErrorData;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormParamType;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import com.jiuqi.nr.task.form.table.IDataTableService;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FieldCheckService
implements IFormCheckService {
    private static final Logger log = LoggerFactory.getLogger(FieldCheckService.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private DataTableValidator dataTableValidator;
    @Autowired
    private IDataTableService dataTableService;

    @Override
    public CheckResult doCheck(FormDesignerDTO formDesigner) {
        CheckResult checkResult = CheckResult.successResult();
        List<DataFieldSettingDTO> fieldSettings = formDesigner.getDataFieldSetting();
        if (CollectionUtils.isEmpty(fieldSettings)) {
            return checkResult;
        }
        List<DataTableDTO> tableSetting = formDesigner.getTableSetting();
        List<DataTableDTO> tables = tableSetting.stream().filter(t -> t.getStatus() == Constants.DataStatus.NEW).collect(Collectors.toList());
        try {
            this.dataTableService.insertReverseModeTables(tables);
            ArrayList fieldKeys = new ArrayList();
            Map<String, List<DesignDataField>> fieldsMap = fieldSettings.stream().peek(f -> {
                if (f.getStatus() == Constants.DataStatus.DELETE) {
                    fieldKeys.add(f.getKey());
                }
            }).filter(f -> f.getStatus() == Constants.DataStatus.NEW || f.getStatus() == Constants.DataStatus.MODIFY).map(f -> {
                DesignDataField designDataField = this.designDataSchemeService.initDataField();
                FieldBeanUtils.toDefine(f, designDataField);
                return designDataField;
            }).collect(Collectors.groupingBy(DataField::getDataTableKey));
            if (!CollectionUtils.isEmpty(fieldKeys)) {
                this.designDataSchemeService.deleteDataFields(fieldKeys);
            }
            fieldsMap.forEach((k, v) -> {
                try {
                    this.fieldValidator.checkField((Collection)v);
                }
                catch (SchemeDataException e) {
                    log.error(e.getMessage(), e);
                    checkResult.addErrorData(this.error(FormParamType.FIELD, e.getMessage()));
                }
            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            checkResult.addErrorData(this.error(FormParamType.DATATABLE, e.getMessage()));
        }
        return checkResult;
    }

    @Override
    public CheckResult doCheck(String formKey) {
        return null;
    }

    private ErrorData error(FormParamType type, String message) {
        ErrorData errorData = new ErrorData();
        errorData.setParamType(type);
        errorData.setMessage(message);
        return errorData;
    }
}

