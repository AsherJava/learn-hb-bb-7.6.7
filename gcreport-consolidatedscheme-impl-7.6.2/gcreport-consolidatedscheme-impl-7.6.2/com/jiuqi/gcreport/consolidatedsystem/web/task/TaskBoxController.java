/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO
 *  com.jiuqi.gcreport.common.task.vo.TaskOptionVO
 *  com.jiuqi.gcreport.common.task.vo.TaskOrgDataVO
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.task;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO;
import com.jiuqi.gcreport.common.task.vo.TaskOptionVO;
import com.jiuqi.gcreport.common.task.vo.TaskOrgDataVO;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value={"/api/gcreport/v1/taskbox"})
public class TaskBoxController {
    private static final Logger logger = LoggerFactory.getLogger(TaskBoxController.class);
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private NpReportQueryProvider reportQueryProvider;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @GetMapping(value={"/boundedTasks"})
    @ResponseBody
    public BusinessResponseEntity<List<TaskOptionVO>> getTasks() {
        List allTaskDefines = this.runTimeAuthViewController.getAllReportTaskDefines();
        HashSet<String> taskIds = new HashSet<String>(this.consolidatedTaskService.getAllBoundTasks());
        List tasks = allTaskDefines.stream().filter(Objects::nonNull).filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).filter(taskDefine -> taskIds.contains(taskDefine.getKey())).map(task -> new TaskOptionVO(task.getKey(), task.getTitle(), task.getDataScheme(), task.getFromPeriod(), task.getToPeriod())).collect(Collectors.toList());
        return BusinessResponseEntity.ok(tasks);
    }

    @GetMapping(value={"/boundedTasksForMenu"})
    @ResponseBody
    public List<TaskOptionVO> getTasksForMenu() {
        List allTaskDefines = this.runTimeAuthViewController.getAllReportTaskDefines();
        HashSet<String> taskIds = new HashSet<String>(this.consolidatedTaskService.getAllBoundTasks());
        return allTaskDefines.stream().filter(Objects::nonNull).filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).filter(taskDefine -> taskIds.contains(taskDefine.getKey())).map(task -> new TaskOptionVO(task.getKey(), task.getTitle(), task.getDataScheme(), task.getFromPeriod(), task.getToPeriod())).collect(Collectors.toList());
    }

    @GetMapping(value={"/boundedTasks/{taskId}/schemes"})
    @ResponseBody
    public BusinessResponseEntity<TaskConditionBoxVO> getSchemes(@PathVariable(value="taskId") String taskId) throws Exception {
        TaskDefine designTaskDefine = this.reportQueryProvider.getRunTimeViewController().queryTaskDefine(taskId);
        if (designTaskDefine == null) {
            return null;
        }
        TaskConditionBoxVO boxVO = new TaskConditionBoxVO();
        List<FormSchemeDefine> formSchemeDefines = this.reportQueryProvider.queryFormSchemeByTask(taskId).stream().filter(formScheme -> this.authorityProvider.canReadFormScheme(formScheme.getKey())).collect(Collectors.toList());
        logger.debug("{}: {}", (Object)Thread.currentThread().getName(), (Object)formSchemeDefines);
        TaskOrgLinkListStream taskOrgLinkListStream = this.iRunTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        List taskOrgDataList = taskOrgLinkList.stream().filter(link -> !StringUtils.isEmpty((String)link.getEntity())).map(link -> {
            TableModelDefine orgTableModel = iEntityMetaService.getTableModel(link.getEntity());
            String title = StringUtils.isEmpty((String)link.getEntityAlias()) ? iEntityMetaService.queryEntity(link.getEntity()).getTitle() : link.getEntityAlias();
            TaskOrgDataVO vo = new TaskOrgDataVO();
            vo.setId(orgTableModel.getName());
            vo.setTitle(title);
            return vo;
        }).collect(Collectors.toList());
        HashSet<String> orgTypes = new HashSet<String>(this.consolidatedTaskService.listOrgTypeByTaskId(taskId));
        List filteredTaskOrgDataList = taskOrgDataList.stream().filter(orgData -> orgTypes.contains(orgData.getId())).collect(Collectors.toList());
        if (filteredTaskOrgDataList != null && filteredTaskOrgDataList.size() > 1) {
            boxVO.setEnableMultiOrg(Integer.valueOf(1));
        }
        boxVO.setTaskId(taskId);
        boxVO.setTaskTitle(designTaskDefine.getTitle());
        this.convertObjs2VO(boxVO, formSchemeDefines);
        boxVO.setEntityScopeList(filteredTaskOrgDataList);
        return BusinessResponseEntity.ok((Object)boxVO);
    }

    private TaskConditionBoxVO convertObjs2VO(TaskConditionBoxVO boxVO, List<FormSchemeDefine> schemeDefines) {
        ArrayList schemeList = new ArrayList();
        if (!CollectionUtils.isEmpty(schemeDefines)) {
            schemeDefines.forEach(schemeDefine -> {
                Scheme scheme = this.convertSchemeDefinToScheme((FormSchemeDefine)schemeDefine);
                schemeList.add(scheme);
            });
        }
        boxVO.setSchemeList(schemeList);
        return boxVO;
    }

    public Scheme convertSchemeDefinToScheme(FormSchemeDefine schemeDefine) {
        String unitDefine = null;
        String currencyDefine = null;
        String gcorgtypeDefine = null;
        String unitTitle = null;
        ArrayList<String> defines = new ArrayList<String>();
        TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(schemeDefine.getDw());
        unitDefine = orgTableModelDefine.getName();
        if (unitDefine.toUpperCase().startsWith("MD_ORG_")) {
            unitTitle = orgTableModelDefine.getTitle();
        } else {
            unitDefine = null;
        }
        if (!ObjectUtils.isEmpty(schemeDefine.getDims())) {
            String[] entityIds = schemeDefine.getDims().split(";");
            try {
                for (String entityId : entityIds) {
                    TableModelDefine designTableDefine = this.entityMetaService.getTableModel(entityId);
                    if (designTableDefine == null) continue;
                    String mTableName = designTableDefine.getName();
                    if ("MD_CURRENCY".equals(mTableName)) {
                        currencyDefine = mTableName;
                        continue;
                    }
                    if ("MD_GCORGTYPE".equals(mTableName)) {
                        gcorgtypeDefine = mTableName;
                        continue;
                    }
                    defines.add(designTableDefine.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)schemeDefine.getKey());
        Scheme scheme = new Scheme();
        scheme.setUnitDefine(unitDefine);
        scheme.setUnitTitle(unitTitle);
        scheme.setCurrencyDefine(currencyDefine);
        scheme.setGcorgtypeDefine(gcorgtypeDefine);
        scheme.setFromPeriod(fromToPeriodByFormSchemeKey[0]);
        scheme.setToPeriod(fromToPeriodByFormSchemeKey[1]);
        TaskPeriodUtils.setSchemeTimeByFormSchemeDefine((Scheme)scheme, (FormSchemeDefine)schemeDefine);
        TaskPeriodUtils.setDefaultTime((Scheme)scheme, (FormSchemeDefine)schemeDefine);
        return scheme;
    }
}

