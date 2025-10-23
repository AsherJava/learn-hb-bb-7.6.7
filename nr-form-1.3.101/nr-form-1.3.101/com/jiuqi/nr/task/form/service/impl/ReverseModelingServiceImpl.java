/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.form.controller.dto.ReverseFieldCodeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeCheckDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;
import com.jiuqi.nr.task.form.service.IReverseModelingExecutor;
import com.jiuqi.nr.task.form.service.IReverseModelingService;
import com.jiuqi.nr.task.form.service.impl.FixReverseModelingExecutor;
import com.jiuqi.nr.task.form.service.impl.FloatReverseModelingExecutor;
import com.jiuqi.nr.task.form.service.reversemodel.IReverseModelDataProvider;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ReverseModelingServiceImpl
extends FormDefineResourceExtSupport
implements IReverseModelingService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataFieldDesignService dataFieldDesignService;
    private final List<IReverseModelingExecutor> reverseModelingExecutors = new ArrayList<IReverseModelingExecutor>();

    protected ReverseModelingServiceImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
        this.reverseModelingExecutors.add(new FixReverseModelingExecutor());
        this.reverseModelingExecutors.add(new FloatReverseModelingExecutor());
    }

    @Override
    public ReverseModeDTO reverseModeling(ReverseModeParam param) {
        Assert.notNull((Object)param.getDataSchemeKey(), "dataSchemeKey must not be null");
        Assert.notNull((Object)param.getFormKey(), "formKey must not be null");
        this.initParam(param);
        for (IFormDefineResourceExt defineResourceExt : this.getFormDefineResourceExts()) {
            List<IReverseModelingExecutor> reverseModelingExecutor;
            if (!defineResourceExt.getCode().equals(param.getFormType()) && (defineResourceExt.getFormType() == null || !defineResourceExt.getFormType().getCode().equals(param.getFormType()))) continue;
            IRegionConfigExt regionConfigExt = defineResourceExt.getRegionConfigExt();
            if (regionConfigExt == null || (reverseModelingExecutor = regionConfigExt.getReverseModelingExecutor()) == null) break;
            return this.reverseModeling(reverseModelingExecutor, param);
        }
        return this.reverseModeling(Collections.emptyList(), param);
    }

    private ReverseModeDTO reverseModeling(List<IReverseModelingExecutor> reverseModelingExecutor, ReverseModeParam param) {
        ReverseModeDTO res = new ReverseModeDTO();
        List<ReverseModeRegionDTO> regions = param.getRegions();
        if (regions != null) {
            for (ReverseModeRegionDTO region : regions) {
                IReverseModelingExecutor executor = this.getExecutor(reverseModelingExecutor, region.getRegionKind());
                if (region.getTableKey() == null) {
                    DataTableDTO table = executor.createTable(param, region);
                    region.setTableKey(table.getKey());
                    region.setTableCode(table.getCode());
                    res.getTables().add(table);
                }
                res.getFields().addAll(executor.createField(param, region));
            }
        }
        return res;
    }

    private IReverseModelingExecutor getExecutor(List<IReverseModelingExecutor> executors, Integer regionKind) {
        for (IReverseModelingExecutor executor : executors) {
            if (!executor.available(DataRegionKind.forValue((int)regionKind))) continue;
            return executor;
        }
        for (IReverseModelingExecutor executor : this.reverseModelingExecutors) {
            if (!executor.available(DataRegionKind.forValue((int)regionKind))) continue;
            return executor;
        }
        throw new FormRuntimeException("\u8be5\u533a\u57df\u7c7b\u578b\u4e0d\u652f\u6301\u5feb\u901f\u521b\u5efa\u6307\u6807");
    }

    private void initParam(ReverseModeParam param) {
        IReverseModelDataProvider reverseModelDataProvider = param.getReverseModelDataProvider();
        if (reverseModelDataProvider != null) {
            DesignFormDefine form = reverseModelDataProvider.getFormDefine();
            param.setFormCode(form.getFormCode());
            param.setFormTitle(form.getTitle());
            param.setFormType(form.getFormType().name());
            DesignFormGroupDefine designFormGroupDefine = reverseModelDataProvider.getFormGroupDefine();
            param.setFormGroupKey(designFormGroupDefine.getKey());
            param.setFormGroupTitle(designFormGroupDefine.getTitle());
            DesignFormSchemeDefine formScheme = reverseModelDataProvider.getFormSchemeDefine();
            param.setFormSchemeKey(formScheme.getKey());
            param.setFormSchemeTitle(formScheme.getTitle());
            DesignDataScheme dataScheme = reverseModelDataProvider.getDataScheme();
            param.setDataSchemeTitle(dataScheme.getTitle());
            param.setDataSchemePrefix(dataScheme.getPrefix());
            return;
        }
        DesignFormDefine form = this.designTimeViewController.getForm(param.getFormKey());
        if (form == null) {
            throw new FormRuntimeException("\u8868\u5355\u4e0d\u5b58\u5728");
        }
        param.setFormCode(form.getFormCode());
        param.setFormTitle(form.getTitle());
        param.setFormType(form.getFormType().name());
        List group = this.designTimeViewController.listFormGroupByForm(param.getFormKey());
        if (group.isEmpty()) {
            throw new FormRuntimeException("\u8868\u5355\u5206\u7ec4\u4e0d\u5b58\u5728");
        }
        DesignFormGroupDefine designFormGroupDefine = (DesignFormGroupDefine)group.get(0);
        param.setFormGroupKey(designFormGroupDefine.getKey());
        param.setFormGroupTitle(designFormGroupDefine.getTitle());
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(form.getFormScheme());
        if (formScheme == null) {
            throw new FormRuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        param.setFormSchemeKey(formScheme.getKey());
        param.setFormSchemeTitle(formScheme.getTitle());
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(param.getDataSchemeKey());
        if (dataScheme == null) {
            throw new FormRuntimeException("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        param.setDataSchemeTitle(dataScheme.getTitle());
        param.setDataSchemePrefix(dataScheme.getPrefix());
    }

    @Override
    public void checkFieldCode(ReverseModeCheckDTO checkDTO) {
        boolean match = false;
        if (checkDTO.getFieldKind() == DataFieldKind.FIELD.getValue()) {
            List fields = this.designDataSchemeService.getDataFieldByTable(checkDTO.getTableKey());
            match = fields.stream().anyMatch(field -> !checkDTO.getFieldKey().equals(field.getKey()) && field.getCode().equals(checkDTO.getFieldCode()));
        } else if (checkDTO.getFieldKind() == DataFieldKind.FIELD_ZB.getValue()) {
            List fields = this.designDataSchemeService.getAllDataFieldByKind(checkDTO.getDataSchemeKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD});
            match = fields.stream().anyMatch(field -> !checkDTO.getFieldKey().equals(field.getKey()) && field.getCode().equals(checkDTO.getFieldCode()));
        }
        if (match) {
            throw new FormRuntimeException("\u6307\u6807\u6807\u8bc6\u91cd\u590d");
        }
    }

    @Override
    public void checkTableCode(ReverseModeCheckDTO checkDTO) {
        DesignDataTable table;
        if (checkDTO.getTableCode() != null && (table = this.designDataSchemeService.getDataTableByCode(checkDTO.getTableCode().toUpperCase())) != null && !checkDTO.getTableKey().equals(table.getKey())) {
            throw new FormRuntimeException("\u6570\u636e\u8868\u6807\u8bc6\u91cd\u590d");
        }
    }

    @Override
    public List<ReverseFieldCodeDTO> queryAllFieldCodes(String dataSchemeKey) {
        List fields = this.designDataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
        ArrayList<ReverseFieldCodeDTO> res = new ArrayList<ReverseFieldCodeDTO>(fields.size());
        fields.forEach(field -> res.add(new ReverseFieldCodeDTO((DesignDataField)field)));
        return res;
    }
}

