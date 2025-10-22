/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.estimation.storage.dim.IEstimationDimTableHelper
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.listener;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.dim.IEstimationDimTableHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DeployEstimationDataBase
implements ApplicationListener<DataSchemeDeployEvent> {
    @Resource
    public IRuntimeDataSchemeService runtimeDataSchemeService;
    @Resource
    private IDataSchemeSubDatabaseHelper schemeSubDatabaseHelper;
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEstimationDimTableHelper dimTableHelper;

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        DataScheme dataScheme;
        String dataSchemeKey = event.getSource().getDataSchemeKey();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return;
        }
        if (!this.dimTableHelper.existEstimationDimTable(dataSchemeKey)) {
            this.dimTableHelper.createEstimationDimTable(dataSchemeKey);
        }
        if ((dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey)) == null) {
            this.schemeSubDatabaseHelper.deleteSubDatabase(dataSchemeKey, "_DE_", false);
            return;
        }
        if (this.schemeSubDatabaseHelper.existSubDatabase(dataSchemeKey, "_DE_")) {
            return;
        }
        if (this.canCreateSubDatabase(dataScheme)) {
            this.schemeSubDatabaseHelper.createSubDatabase(dataSchemeKey, "_DE_");
        }
    }

    private boolean canCreateSubDatabase(DataScheme dataScheme) {
        List allEstimationSchemes = this.estimationSchemeTemplateService.findAllSchemeTemplate().stream().filter(e -> e.getFormSchemeDefine() != null).collect(Collectors.toList());
        List formSchemeList = allEstimationSchemes.stream().map(IEstimationSchemeTemplate::getFormSchemeDefine).collect(Collectors.toList());
        Set taskIdList = formSchemeList.stream().map(FormSchemeDefine::getTaskKey).collect(Collectors.toSet());
        List taskDefines = taskIdList.stream().map(taskId -> this.runTimeViewController.queryTaskDefine(taskId)).collect(Collectors.toList());
        List referDataSchemes = taskDefines.stream().map(taskDefine -> this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme())).collect(Collectors.toList());
        return referDataSchemes.stream().anyMatch(v -> v.getKey().equals(dataScheme.getKey()));
    }
}

