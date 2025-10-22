/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.attachment.deploy;

import com.jiuqi.nr.attachment.deploy.ColumnInfo;
import com.jiuqi.nr.attachment.deploy.FilePoolObserver;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;

public class ManualDeployTableExecutor {
    private DesignDataModelService designDataModelService;
    private DataModelDeployService dataModelDeployService;
    private FilePoolObserver filePoolObserver;
    private static final String TASK_KEY = "TASK_KEY";
    private static final String FORM_KEY = "FORM_KEY";

    public ManualDeployTableExecutor() {
        if (null == this.designDataModelService) {
            this.designDataModelService = (DesignDataModelService)BeanUtil.getBean(DesignDataModelService.class);
        }
        if (null == this.dataModelDeployService) {
            this.dataModelDeployService = (DataModelDeployService)BeanUtil.getBean(DataModelDeployService.class);
        }
        if (null == this.filePoolObserver) {
            this.filePoolObserver = (FilePoolObserver)BeanUtil.getBean(FilePoolObserver.class);
        }
    }

    public void deleteRow(DataScheme dataScheme) throws Exception {
        if (null == dataScheme) {
            return;
        }
        String filePoolTableName = "NR_FILE_" + dataScheme.getBizCode();
        DesignTableModelDefine filePoolTable = this.designDataModelService.getTableModelDefineByCode(filePoolTableName);
        ArrayList<String> ids = new ArrayList<String>();
        List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (DesignColumnModelDefine oldColumn : oldColumns) {
            if (!oldColumn.getCode().equals(TASK_KEY) && !oldColumn.getCode().equals(FORM_KEY)) continue;
            ids.add(oldColumn.getID());
        }
        if (2 == ids.size()) {
            this.designDataModelService.deleteColumnModelDefines(ids.toArray(new String[2]));
            this.designDataModelService.updateTableModelDefine(filePoolTable);
            this.dataModelDeployService.deployTableUnCheck(filePoolTable.getID());
        }
    }

    public void insertCol(DataScheme dataScheme) throws Exception {
        if (null == dataScheme) {
            return;
        }
        String filePoolTableName = "NR_FILE_" + dataScheme.getBizCode();
        DesignTableModelDefine filePoolTable = this.designDataModelService.getTableModelDefineByCode(filePoolTableName);
        if (null == filePoolTable) {
            return;
        }
        List columnModelDefinesByTable = this.designDataModelService.getColumnModelDefinesByTable(filePoolTableName);
        for (DesignColumnModelDefine designColumnModelDefine : columnModelDefinesByTable) {
            if (!designColumnModelDefine.getCode().equals("ISDELETE")) continue;
            return;
        }
        ColumnInfo columnInfo = new ColumnInfo("ISDELETE", "ISDELETE", ColumnModelType.STRING, 1, "");
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(filePoolTable.getID());
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(columnInfo.getReferFieldID());
        fieldDefine.setDefaultValue("0");
        this.designDataModelService.insertColumnModelDefine(fieldDefine);
        this.designDataModelService.updateTableModelDefine(filePoolTable);
        this.dataModelDeployService.deployTableUnCheck(filePoolTable.getID());
    }

    public boolean manuaDdeployFilePoolTable(DataScheme dataScheme) throws Exception {
        if (null == dataScheme) {
            return false;
        }
        this.filePoolObserver.getDeployTableBydataSchemeKey(dataScheme.getKey());
        return true;
    }
}

