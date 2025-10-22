/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.util.StringUtils
 */
package nr.single.para.paraout.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.util.StringUtils;
import java.util.List;
import nr.single.para.parain.service.IParaImportCommonService;
import nr.single.para.paraout.bean.TaskExportContext;
import nr.single.para.paraout.service.ITaskDefineEportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDefineEportServiceImpl
implements ITaskDefineEportService {
    private static final Logger log = LoggerFactory.getLogger(TaskDefineEportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IParaImportCommonService paraCommonService;

    @Override
    public void exportTaskGroupDefines(TaskExportContext exportContext) {
        List groups = this.designTimeViewController.getGroupByTask(exportContext.getTaskKey());
        if (groups != null && !groups.isEmpty()) {
            ParaInfo para = exportContext.getParaInfo();
            para.setTaskGroup(((DesignTaskGroupDefine)groups.get(0)).getTitle());
        }
    }

    @Override
    public void exportTaskDefine(TaskExportContext exportContext) {
        DesignFormSchemeDefine formScheme = exportContext.getSchemeInfo().getFormScheme();
        DesignTaskDefine taskDefine = exportContext.getSchemeInfo().getTaskDefine();
        DesignDataScheme dataScheme = exportContext.getSchemeInfo().getDataScheme();
        ParaInfo para = exportContext.getParaInfo();
        para.setPrefix(taskDefine.getTaskCode());
        para.setTaskName(taskDefine.getTitle());
        para.setFileFlag(dataScheme.getPrefix());
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
        para.setTaskType(this.paraCommonService.getPeriodTypeCode(periodEntity.getPeriodType()));
        try {
            List periodLinks = this.designTimeViewController.querySchemePeriodLinkByScheme(exportContext.getFormSchemeKey());
            String periodCode = null;
            if (!periodLinks.isEmpty()) {
                DesignSchemePeriodLinkDefine periodLink = (DesignSchemePeriodLinkDefine)periodLinks.get(0);
                periodCode = periodLink.getPeriodKey();
            } else if (StringUtils.isNotEmpty((String)taskDefine.getFromPeriod())) {
                periodCode = taskDefine.getFromPeriod();
            } else if (StringUtils.isNotEmpty((String)taskDefine.getToPeriod())) {
                periodCode = taskDefine.getToPeriod();
            }
            if (periodCode != null && StringUtils.isNotEmpty((String)periodCode)) {
                String year = periodCode.substring(0, 4);
                para.setTaskYear(year);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void initCahce(TaskExportContext exportContext) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.queryFormSchemeDefine(exportContext.getFormSchemeKey());
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        exportContext.setTaskKey(taskDefine.getKey());
        exportContext.getSchemeInfo().setFormScheme(formScheme);
        exportContext.getSchemeInfo().setTaskDefine(taskDefine);
        exportContext.getSchemeInfo().setDataScheme(dataScheme);
    }
}

