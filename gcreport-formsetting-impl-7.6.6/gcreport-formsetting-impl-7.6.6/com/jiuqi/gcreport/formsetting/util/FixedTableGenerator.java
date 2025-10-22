/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.formsetting.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.util.StringUtils;

public class FixedTableGenerator {
    private IDesignTimeViewController nrDesignController = (IDesignTimeViewController)SpringContextUtils.getBean(IDesignTimeViewController.class);
    private IDesignDataSchemeService iDesignDataSchemeService = (IDesignDataSchemeService)SpringContextUtils.getBean(IDesignDataSchemeService.class);
    private DataSchemeService dataSchemeService = (DataSchemeService)SpringContextUtils.getBean(DataSchemeService.class);

    public DesignDataTable next(String tableCode, String formSchemeKey) throws Exception {
        DataSchemeDTO dataScheme = this.getDataSchemeByFormSechemeKey(formSchemeKey);
        DesignDataTable designTableDefine = this.createDataTable(dataScheme, tableCode);
        return designTableDefine;
    }

    private DesignDataTable createDataTable(DataSchemeDTO dataScheme, String tableCode) {
        DesignDataTable dataTable = this.iDesignDataSchemeService.initDataTable();
        dataTable.setDataSchemeKey(dataScheme.getKey());
        String prefix = dataScheme.getPrefix();
        if (!StringUtils.isEmpty((String)prefix)) {
            tableCode = prefix + "_" + tableCode;
        }
        dataTable.setCode(tableCode);
        dataTable.setDataTableType(DataTableType.TABLE);
        dataTable.setDataTableGatherType(DataTableGatherType.NONE);
        dataTable.setRepeatCode(Boolean.valueOf(true));
        dataTable.setTitle(dataScheme.getCode() + "_" + tableCode);
        dataTable.setDesc(dataScheme.getTitle() + "\u5185\u90e8\u5f55\u5165\u56fa\u5b9a\u533a\u57df\u5408\u8ba1");
        return dataTable;
    }

    private DataSchemeDTO getDataSchemeByFormSechemeKey(String formSchemeKey) {
        DesignFormSchemeDefine queryFormScheme = this.nrDesignController.queryFormSchemeDefine(formSchemeKey);
        if (queryFormScheme == null) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff0cformSchemeKey\uff1a" + formSchemeKey);
        }
        String taskKey = queryFormScheme.getTaskKey();
        DesignTaskDefine queryTaskDefine = this.nrDesignController.queryTaskDefine(taskKey);
        String dataSchemeKey = queryTaskDefine.getDataScheme();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4e3a\u7a7a\u3002");
        }
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        return dataScheme;
    }
}

