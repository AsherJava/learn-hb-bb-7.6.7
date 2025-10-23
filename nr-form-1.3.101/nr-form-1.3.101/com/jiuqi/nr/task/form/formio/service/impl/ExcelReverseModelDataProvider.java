/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.form.formio.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.formio.context.FormImportContext;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.internal.MergeBaseData;
import com.jiuqi.nr.task.form.service.reversemodel.IReverseModelDataProvider;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import com.jiuqi.nr.task.form.util.TableBeanUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ExcelReverseModelDataProvider
implements IReverseModelDataProvider {
    private String currentFile;
    private DesignDataScheme dataScheme;
    private DesignFormSchemeDefine formSchemeDefine;
    private DesignFormGroupDefine formGroupDefine;
    private final List<DesignDataTable> dataTables = new ArrayList<DesignDataTable>();
    private final List<DesignDataField> fields = new ArrayList<DesignDataField>();
    private FormImportContext formImportContext;
    private final Map<String, FormImportContext> formImportContextMap = new HashMap<String, FormImportContext>();
    private final IDesignDataSchemeService designDataSchemeService;
    private final Map<String, Map<String, ImportReverseResultDTO>> importReverseResultDTOMap = new HashMap<String, Map<String, ImportReverseResultDTO>>();
    private final MergeBaseData mergeBaseData = new MergeBaseData();

    public ExcelReverseModelDataProvider(IDesignDataSchemeService designDataSchemeService) {
        this.designDataSchemeService = designDataSchemeService;
        if (designDataSchemeService != null) {
            this.dataTables.addAll(designDataSchemeService.getAllDataTable());
        }
    }

    public String getCurrentFile() {
        return this.currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
        this.importReverseResultDTOMap.put(currentFile, new LinkedHashMap());
    }

    @Override
    public List<DesignDataTable> getAllDataTable() {
        return this.getDataTables();
    }

    @Override
    public List<DesignDataField> getFields(ReverseModeParam param, ReverseModeRegionDTO region) {
        return this.getFields();
    }

    @Override
    public DesignDataScheme getDataScheme() {
        return this.dataScheme;
    }

    @Override
    public DesignFormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    @Override
    public DesignFormDefine getFormDefine() {
        return this.getCurrentContext().getCurrentForm();
    }

    @Override
    public DesignFormGroupDefine getFormGroupDefine() {
        return this.formGroupDefine;
    }

    public MergeBaseData getMergeBaseData() {
        return this.mergeBaseData;
    }

    public void changeImportContext(FormImportContext formImportContext) {
        if (this.getDataScheme() == null) {
            this.setDataScheme(formImportContext.getDataScheme());
            this.fields.addAll(this.designDataSchemeService.getAllDataFieldByKind(this.getDataScheme().getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB}));
            this.formSchemeDefine = formImportContext.getFormSchemeDefine();
            this.formGroupDefine = formImportContext.getGroupDefine();
        }
        this.formImportContext = formImportContext;
        if (!this.formImportContextMap.containsKey(formImportContext.getCurrentSheet().getSheetName())) {
            this.formImportContextMap.put(formImportContext.getCurrentSheet().getSheetName(), formImportContext);
        }
    }

    public void changeImportContext(String sheetName) {
        FormImportContext formImportContext = this.formImportContextMap.get(sheetName);
        if (formImportContext == null) {
            throw new FormRuntimeException("\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u8868\u5355\u4e0a\u4e0b\u6587");
        }
        this.changeImportContext(formImportContext);
    }

    public void setDataScheme(DesignDataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public void setFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public void setFormGroupDefine(DesignFormGroupDefine formGroupDefine) {
        this.formGroupDefine = formGroupDefine;
    }

    public FormImportContext getCurrentContext() {
        return this.formImportContext;
    }

    public List<DesignDataTable> getDataTables() {
        return this.dataTables;
    }

    public List<DesignDataField> getFields() {
        return this.fields;
    }

    public void setImportReverseResultDTO(ImportReverseResultDTO importReverseResultDTO) {
        if (importReverseResultDTO.getFieldPosMap() != null) {
            List dataFields = importReverseResultDTO.getFieldPosMap().values().stream().map(t -> {
                DesignDataField designDataField = this.designDataSchemeService.initDataField();
                FieldBeanUtils.toDefine(t, designDataField);
                return designDataField;
            }).collect(Collectors.toList());
            this.getFields().addAll(dataFields);
        }
        if (importReverseResultDTO.getDataTableDTO() != null) {
            DataTableDTO dataTableDTO = importReverseResultDTO.getDataTableDTO();
            DesignDataTable designDataTable = this.designDataSchemeService.initDataTable();
            TableBeanUtils.copyProperty(dataTableDTO, designDataTable);
            this.getDataTables().add(designDataTable);
        }
        this.getCurrentImportReverseResultDTOMap().put(this.getCurrentContext().getCurrentSheet().getSheetName(), importReverseResultDTO);
    }

    public Map<String, ImportReverseResultDTO> getCurrentImportReverseResultDTOMap() {
        return this.importReverseResultDTOMap.get(this.getCurrentFile());
    }

    public Map<String, Map<String, ImportReverseResultDTO>> getImportReverseResultDTOMap() {
        return this.importReverseResultDTOMap;
    }

    public ImportReverseResultDTO getCurrentImportReverseResultDTO() {
        return this.getCurrentImportReverseResultDTOMap().get(this.getCurrentContext().getCurrentSheet().getSheetName());
    }
}

