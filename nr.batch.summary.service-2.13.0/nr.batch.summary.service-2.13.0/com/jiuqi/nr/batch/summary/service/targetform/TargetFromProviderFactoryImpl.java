/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactory;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TargetFromProviderFactoryImpl
implements TargetFromProviderFactory {
    @Resource
    public DataModelService dataModelService;
    @Resource
    public IFormSchemeService formSchemeService;
    @Resource
    public IRunTimeViewController runTimeViewController;
    @Resource
    public DataFieldDeployInfoService dataFieldDeployInfoService;
    @Resource
    public IRuntimeDataSchemeService runtimeDataSchemeService;
    @Resource
    public IEntityMetaService entityMetaService;
    @Resource
    public IDataBaseTableProvider iDataBaseTableProvider;
    @Resource
    public SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;

    @Override
    public TargetFromProvider getTargetFromProvider(SummaryScheme summaryScheme) {
        return new TargetFromProviderImpl(this, summaryScheme, this.iDataBaseTableProvider);
    }

    public FormSchemeDefine queryFormSchemeDefine(String taskId, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskId);
            if (schemePeriodLinkDefine != null) {
                return this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

