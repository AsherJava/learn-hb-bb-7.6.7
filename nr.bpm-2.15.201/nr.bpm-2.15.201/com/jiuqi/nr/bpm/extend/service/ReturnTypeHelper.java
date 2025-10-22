/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.extend.service;

import com.jiuqi.nr.bpm.extend.service.ReturnTypeHelpService;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReturnTypeHelper
implements ReturnTypeHelpService {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private DataModelService dataModelService;

    @Override
    public TableModelDefine getStateHistoryTableModelDefine(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(taskId, period);
        if (formSchemeDefine == null) {
            return null;
        }
        String tableCode = TableConstant.getSysUploadRecordTableName(formSchemeDefine.getKey());
        return this.dataModelService.getTableModelDefineByCode(tableCode);
    }

    private FormSchemeDefine getFormSchemeDefine(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        }
        catch (Exception var5) {
            Exception e = var5;
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        return schemePeriodLinkDefine == null ? null : this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }
}

