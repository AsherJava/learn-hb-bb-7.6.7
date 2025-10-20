/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.task.web;

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
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

@Api(value="taskConditionBox", description="\u5408\u5e76\u4f53\u7cfb")
@CrossOrigin
@RestController
@RequestMapping(value={"/api/gcreport/v1/taskbox"})
public class TaskConditionBoxController {
    private static final Logger logger = LoggerFactory.getLogger(TaskConditionBoxController.class);
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

    @GetMapping(value={"/all-data-scheme"})
    @ResponseBody
    public BusinessResponseEntity<List<DataScheme>> listAllDataSchemes() {
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        String orgTypeDimKey = "MD_GCORGTYPE@BASE";
        String currencyDimKey = "MD_CURRENCY@BASE";
        HashSet<String> dataDimensionSet = new HashSet<String>(Arrays.asList(orgTypeDimKey, currencyDimKey, "MD_GCADJTYPE@BASE", "ADJUST"));
        if (!CollectionUtils.isEmpty((Collection)allDataScheme)) {
            allDataScheme = allDataScheme.stream().filter(item -> {
                List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(item.getKey());
                if (CollectionUtils.isEmpty((Collection)dataSchemeDimension)) {
                    return false;
                }
                boolean hasOrgType = false;
                boolean hasCurrency = false;
                for (DataDimension dataDimension : dataSchemeDimension) {
                    if (!DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType()) || StringUtils.isEmpty((String)dataDimension.getDimKey())) continue;
                    String dimKey = dataDimension.getDimKey().trim();
                    if (!dataDimensionSet.contains(dimKey)) {
                        return false;
                    }
                    if (currencyDimKey.equals(dimKey)) {
                        hasCurrency = true;
                    }
                    if (!orgTypeDimKey.equals(dimKey)) continue;
                    hasOrgType = true;
                }
                return hasCurrency && hasOrgType;
            }).collect(Collectors.toList());
        }
        return BusinessResponseEntity.ok((Object)allDataScheme);
    }

    @GetMapping(value={"/tasks"})
    @ResponseBody
    public BusinessResponseEntity<List<TaskOptionVO>> getTasks() {
        List allTaskDefines = this.runTimeAuthViewController.getAllReportTaskDefines();
        List<TaskDefine> filtedTaskDefines = allTaskDefines.stream().filter(Objects::nonNull).filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).collect(Collectors.toList());
        ArrayList tasks = new ArrayList();
        filtedTaskDefines.forEach(task -> tasks.add(new TaskOptionVO(task.getKey(), task.getTitle(), task.getDataScheme(), task.getFromPeriod(), task.getToPeriod())));
        return BusinessResponseEntity.ok(tasks);
    }

    @GetMapping(value={"/task/{taskId}"})
    @ResponseBody
    public BusinessResponseEntity<TaskOptionVO> getTask(@PathVariable(value="taskId") String taskId) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskId);
        if (taskDefine == null) {
            return BusinessResponseEntity.error();
        }
        TaskOptionVO taskOptionVO = new TaskOptionVO(taskDefine.getKey(), taskDefine.getTitle(), taskDefine.getDataScheme(), taskDefine.getFromPeriod(), taskDefine.getToPeriod());
        TaskOrgLinkListStream taskOrgLinkListStream = this.iRunTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        if (taskOrgLinkList != null && taskOrgLinkList.size() > 1) {
            taskOptionVO.setEnableMultiOrg(1);
        }
        ArrayList<TaskOrgDataVO> taskOrgDataList = new ArrayList<TaskOrgDataVO>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
            TaskOrgDataVO taskOrgData = new TaskOrgDataVO();
            String entity = taskOrgLinkDefine.getEntity();
            if (StringUtils.isEmpty((String)entity)) continue;
            TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(entity);
            taskOrgData.setId(orgTableModelDefine.getName());
            if (!StringUtils.isEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgDataList.add(taskOrgData);
        }
        taskOptionVO.setEntityScopeList(taskOrgDataList);
        return BusinessResponseEntity.ok((Object)taskOptionVO);
    }

    @GetMapping(value={"/tasks/{taskId}/schemes"})
    @ResponseBody
    public BusinessResponseEntity<TaskConditionBoxVO> getSchemes(@PathVariable(value="taskId") String taskId) throws Exception {
        TaskDefine designTaskDefine = this.reportQueryProvider.getRunTimeViewController().queryTaskDefine(taskId);
        if (null == designTaskDefine) {
            return null;
        }
        TaskConditionBoxVO boxVO = new TaskConditionBoxVO();
        List<FormSchemeDefine> formSchemeDefines = this.reportQueryProvider.queryFormSchemeByTask(taskId).stream().filter(formSchemeDefine -> this.authorityProvider.canReadFormScheme(formSchemeDefine.getKey())).collect(Collectors.toList());
        logger.debug(Thread.currentThread().getName() + ": " + formSchemeDefines.toString());
        TaskOrgLinkListStream taskOrgLinkListStream = this.iRunTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        if (taskOrgLinkList != null && taskOrgLinkList.size() > 1) {
            boxVO.setEnableMultiOrg(1);
        }
        ArrayList<TaskOrgDataVO> taskOrgDataList = new ArrayList<TaskOrgDataVO>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
            TaskOrgDataVO taskOrgData = new TaskOrgDataVO();
            String entity = taskOrgLinkDefine.getEntity();
            if (StringUtils.isEmpty((String)entity)) continue;
            TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(entity);
            taskOrgData.setId(orgTableModelDefine.getName());
            if (!StringUtils.isEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgDataList.add(taskOrgData);
        }
        boxVO.setTaskId(taskId);
        boxVO.setTaskTitle(designTaskDefine.getTitle());
        this.convertObjs2VO(boxVO, formSchemeDefines);
        boxVO.setEntityScopeList(taskOrgDataList);
        return BusinessResponseEntity.ok((Object)boxVO);
    }

    @GetMapping(value={"/scheme/{schemeId}"})
    @ResponseBody
    public BusinessResponseEntity<Scheme> getScheme(@PathVariable(value="schemeId") String schemeId) {
        FormSchemeDefine schemeDefine = this.reportQueryProvider.getRunTimeViewController().getFormScheme(schemeId);
        if (null == schemeDefine) {
            return null;
        }
        Scheme scheme = this.convertSchemeDefinToScheme(schemeDefine);
        return BusinessResponseEntity.ok((Object)scheme);
    }

    private TaskConditionBoxVO convertObjs2VO(TaskConditionBoxVO boxVO, List<FormSchemeDefine> schemeDefines) {
        ArrayList<Scheme> schemeList = new ArrayList<Scheme>();
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
        String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey(schemeDefine.getKey());
        Scheme scheme = new Scheme();
        scheme.setUnitDefine(unitDefine);
        scheme.setUnitTitle(unitTitle);
        scheme.setCurrencyDefine(currencyDefine);
        scheme.setGcorgtypeDefine(gcorgtypeDefine);
        scheme.setFromPeriod(fromToPeriodByFormSchemeKey[0]);
        scheme.setToPeriod(fromToPeriodByFormSchemeKey[1]);
        TaskPeriodUtils.setSchemeTimeByFormSchemeDefine(scheme, schemeDefine);
        TaskPeriodUtils.setDefaultTime(scheme, schemeDefine);
        return scheme;
    }
}

