/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.multcheck2.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.asynctask.MCDataEntryBatchRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.asynctask.MCDataEntryRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.asynctask.MultcheckRealTimeTaskExecutor;
import com.jiuqi.nr.multcheck2.bean.MCHistoryScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.result.ResultDetailVO;
import com.jiuqi.nr.multcheck2.web.vo.CheckItemPluginVO;
import com.jiuqi.nr.multcheck2.web.vo.MCExecuteParamVO;
import com.jiuqi.nr.multcheck2.web.vo.MCExecuteVO;
import com.jiuqi.nr.multcheck2.web.vo.MCHistorySchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckItemParamVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckItemVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckSchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.OrgExportVO;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import com.jiuqi.nr.multcheck2.web.vo.ResultVO;
import com.jiuqi.nr.multcheck2.web.vo.SchemeTreeVO;
import com.jiuqi.nr.multcheck2.web.vo.TaskTreeNodeVO;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/multcheck2"})
public class MCSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(MCSchemeController.class);
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService envService;
    @Autowired
    private IMCResultService resultService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeServie;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private IRunTimeViewController dRunTime;
    @Autowired
    private IMCOrgService mcOrgService;

    @GetMapping(value={"/scheme/taskTree"})
    @ApiOperation(value="\u65b0\u5efa\u65b9\u6848\uff1a\uff1a\u4efb\u52a1\u6811\u5f62")
    public List<ITree<TaskTreeNodeVO>> taskTree() throws Exception {
        ArrayList<ITree<TaskTreeNodeVO>> taskTree = new ArrayList<ITree<TaskTreeNodeVO>>();
        taskTree.add(this.envService.buildRootNode("", false));
        return taskTree;
    }

    @GetMapping(value={"/scheme/task-rule-tree"})
    @ApiOperation(value="\u65b0\u5efa\u65b9\u6848\uff1a\uff1a\u4efb\u52a1\u6811\u5f62")
    public SchemeTreeVO taskRuleTree() throws Exception {
        SchemeTreeVO res = new SchemeTreeVO();
        ArrayList<ITree<TaskTreeNodeVO>> taskTree = new ArrayList<ITree<TaskTreeNodeVO>>();
        taskTree.add(this.envService.buildRootNode("", false));
        res.setTask(taskTree);
        return res;
    }

    @GetMapping(value={"/scheme/get/{key}"})
    @ApiOperation(value="\u7f16\u8f91\u754c\u9762\u83b7\u53d6\u65b9\u6848")
    public MultcheckSchemeVO get(@PathVariable(value="key") String key) {
        return this.envService.getSchemeByKey(key);
    }

    @PostMapping(value={"/item/add"})
    @ApiOperation(value="\u65b0\u589e")
    public ResultVO add(@Valid @RequestBody @SFDecrypt MultcheckItem item) {
        return this.schemeService.addItem(item);
    }

    @GetMapping(value={"/item/get-list/{schemeKey}/{formSchemeKey}"})
    @ApiOperation(value="\u5ba1\u6838\u65b9\u6848\u4e0b\u7684\u5ba1\u6838\u9879")
    public MultcheckItemParamVO getList(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="formSchemeKey") String formSchemeKey) {
        MultcheckItemParamVO res = new MultcheckItemParamVO();
        List<MultcheckItem> items = this.schemeService.getItemList(schemeKey);
        ArrayList<MultcheckItemVO> itemVOS = new ArrayList<MultcheckItemVO>();
        res.setItems(itemVOS);
        ArrayList<String> supportDimensionTypes = new ArrayList<String>();
        res.setSupportDimensionTypes(supportDimensionTypes);
        for (MultcheckItem item : items) {
            MultcheckItemVO itemVO = new MultcheckItemVO();
            BeanUtils.copyProperties(item, itemVO);
            itemVO.setConfig(null);
            IMultcheckItemProvider provider = this.envService.getProvider(item.getType());
            if (provider == null) continue;
            itemVO.setDescription(provider.getItemDescribe(formSchemeKey, item));
            itemVO.setTypeTitle(provider.getTitle());
            if (provider.supportDimension()) {
                supportDimensionTypes.add(item.getType());
            }
            itemVO.setSupportDimension(provider.supportDimension());
            itemVOS.add(itemVO);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List<DataDimension> dimsForReport = this.mcDimService.getOtherDimsForReport(taskDefine.getDataScheme());
        if (!CollectionUtils.isEmpty(dimsForReport)) {
            for (DataDimension dim : dimsForReport) {
                res.addDim(this.entityMetaService.queryEntity(dim.getDimKey()));
            }
            String reportDim = this.schemeService.getReportDim(schemeKey);
            if (StringUtils.hasText(reportDim)) {
                try {
                    ReportDimVO reportDimVO = SerializeUtil.deserializeFromJson(reportDim, ReportDimVO.class);
                    res.setSchemeDims(reportDimVO.getSchemeDimSet());
                    res.setItemsDims(reportDimVO.getItemsDimSet());
                }
                catch (Exception e) {
                    logger.error(schemeKey + "\uff1a\uff1a" + reportDim);
                    logger.error("\u83b7\u53d6\u5ba1\u6838\u65b9\u6848\u4e0b\u7684\u8865\u5168\u60c5\u666f\u5f02\u5e38\uff1a" + e.getMessage(), e);
                }
            }
            res.setHasDimsForReport(true);
        }
        res.setTaskKey(taskDefine.getKey());
        res.setAdjust(this.dataSchemeServie.enableAdjustPeriod(taskDefine.getDataScheme()));
        return res;
    }

    @GetMapping(value={"/item/get-plugin"})
    @ApiOperation(value="\u5ba1\u6838\u9879\u914d\u7f6e\u63d2\u4ef6")
    public List<CheckItemPluginVO> getPlugin() {
        ArrayList<CheckItemPluginVO> pluginVOS = new ArrayList<CheckItemPluginVO>();
        for (IMultcheckItemProvider provider : this.envService.getProviderList()) {
            CheckItemPluginVO pluginVO = new CheckItemPluginVO();
            pluginVO.setType(provider.getType());
            pluginVO.setTitle(provider.getTitle());
            pluginVO.setPluginInfo(provider.getPropertyPluginInfo());
            pluginVOS.add(pluginVO);
        }
        return pluginVOS;
    }

    @PostMapping(value={"/item/save/report-dim/{key}"})
    @ApiOperation(value="\u4fdd\u5b58\u65b9\u6848\u7684\u8865\u5168\u60c5\u666f")
    public void saveReportDim(@PathVariable(value="key") String key, @RequestBody ReportDimVO dim) {
        try {
            this.schemeService.saveReportDim(key, SerializeUtil.serializeToJson(dim));
        }
        catch (Exception e) {
            logger.error(key);
            logger.error("\u4fdd\u5b58\u65b9\u6848\u7684\u8865\u5168\u60c5\u666f\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    @PostMapping(value={"/item/delete/{key}"})
    @ApiOperation(value="\u5220\u9664")
    public void delete(@PathVariable(value="key") String key) {
        this.schemeService.deleteItem(key);
    }

    @GetMapping(value={"/item/get-config/{key}"})
    @ApiOperation(value="\u4fee\u6539\u5ba1\u6838\u9879\uff1a\uff1a\u914d\u7f6e\u53c2\u6570")
    public String getConfig(@PathVariable(value="key") String key) {
        key = HtmlUtils.cleanUrlXSS((String)key);
        return HtmlUtils.cleanUrlXSS((String)this.schemeService.getItemConfig(key));
    }

    @PostMapping(value={"/item/modify"})
    @ApiOperation(value="\u4fee\u6539\u5ba1\u6838\u9879")
    public ResultVO modify(@Valid @RequestBody @SFDecrypt MultcheckItem item) {
        return this.schemeService.modifyItem(item);
    }

    @PostMapping(value={"/item/move"})
    @ApiOperation(value="\u4fee\u6539\u5ba1\u6838\u9879\u79fb\u52a8")
    public void move(@RequestBody List<String> keys) {
        this.schemeService.moveItem(keys.get(0), keys.get(1));
    }

    @GetMapping(value={"/batch/init-task-list"})
    @ApiOperation(value="\u529f\u80fd\u53c2\u6570::\u4efb\u52a1\u5217\u8868")
    public List<TaskDefine> initTaskList() {
        List<String> allTasks = this.schemeService.getAllTask();
        return this.envService.getAllTaskDefines().stream().filter(e -> allTasks.contains(e.getKey())).collect(Collectors.toList());
    }

    @GetMapping(value={"/batch/init-scheme-tree"})
    @ApiOperation(value="\u529f\u80fd\u53c2\u6570::\u5ba1\u6838\u65b9\u6848\u6811\u5f62")
    public List<Map<String, Object>> initSchemeTree() {
        ArrayList<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
        List<MultcheckScheme> allSchemes = this.schemeService.getAllSchemes();
        if (CollectionUtils.isEmpty(allSchemes)) {
            return tree;
        }
        Map<String, List<MultcheckScheme>> taskToSchemes = allSchemes.stream().collect(Collectors.groupingBy(MultcheckScheme::getTask));
        List allTask = this.runTimeViewController.getAllTaskDefines();
        List schemeTask = allTask.stream().filter(e -> taskToSchemes.containsKey(e.getKey())).collect(Collectors.toList());
        for (TaskDefine taskDefine : schemeTask) {
            HashMap<String, Object> tNode = new HashMap<String, Object>();
            tNode.put("id", taskDefine.getKey());
            tNode.put("label", taskDefine.getTitle());
            ArrayList tChildren = new ArrayList();
            tNode.put("children", tChildren);
            Map<String, List<MultcheckScheme>> formToScheme = taskToSchemes.get(taskDefine.getKey()).stream().collect(Collectors.groupingBy(MultcheckScheme::getFormScheme));
            for (String formSchemeKey : formToScheme.keySet()) {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                if (formScheme == null) continue;
                HashMap<String, Object> fNode = new HashMap<String, Object>();
                fNode.put("id", formScheme.getKey());
                fNode.put("label", formScheme.getTitle());
                ArrayList fChildren = new ArrayList();
                fNode.put("children", fChildren);
                for (MultcheckScheme scheme : formToScheme.get(formSchemeKey)) {
                    HashMap<String, String> sNode = new HashMap<String, String>();
                    sNode.put("id", scheme.getKey());
                    sNode.put("label", scheme.getTitle());
                    fChildren.add(sNode);
                }
                tChildren.add(fNode);
            }
            tree.add(tNode);
        }
        return tree;
    }

    @PostMapping(value={"/batch/init"})
    @ApiOperation(value="\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762\uff1ashowTask showScheme tasks")
    public MCExecuteVO init(@Valid @RequestBody MCExecuteParamVO param) {
        MCExecuteVO res = new MCExecuteVO();
        try {
            if (StringUtils.hasText(param.getAppointScheme())) {
                return this.dealAppointScheme(param, res);
            }
            if (this.check(param.isShowTask(), param.getTasks(), res)) {
                return res;
            }
            if (CollectionUtils.isEmpty(param.getTasks())) {
                ArrayList<ITree<TaskTreeNodeVO>> taskTree = new ArrayList<ITree<TaskTreeNodeVO>>();
                taskTree.add(this.envService.buildRootNode("", true));
                res.setTaskTree(taskTree);
            } else {
                this.buildTaskList(res, param.getTasks());
                TaskDefine taskDefine = res.getTasks().get(0);
                this.buildOrgLinks(res, taskDefine.getKey(), null);
                res.setAdjust(this.dataSchemeServie.enableAdjustPeriod(taskDefine.getDataScheme()));
                this.buildPeriod(res, taskDefine, param.getPeriod());
                if (param.isShowScheme()) {
                    this.buildMCSchemeList(res, taskDefine, res.getCurPeriod(), null);
                    this.buildEntity(res, taskDefine, res.getFormScheme(), res.getCurPeriod());
                    if (!CollectionUtils.isEmpty(res.getSchemes())) {
                        this.buildMCItem(res, res.getSchemes().get(0).getKey());
                        res.setHistorySchemes(this.envService.getHistorySchemeByUserSource(res.getSchemes().get(0).getKey()));
                    }
                } else {
                    SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(res.getCurPeriod(), taskDefine.getKey());
                    if (scheme == null) {
                        throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_002);
                    }
                    res.setFormScheme(scheme.getSchemeKey());
                    HashMap<String, List<MCLabel>> entityLabels = new HashMap<String, List<MCLabel>>();
                    List<DataDimension> dataDimensions = this.mcDimService.getReportDims(taskDefine.getDataScheme());
                    if (!CollectionUtils.isEmpty(dataDimensions)) {
                        for (DataDimension dim : dataDimensions) {
                            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                            res.addEntity(entityDefine);
                            List<MCLabel> labels = this.getMCLabels(taskDefine, scheme.getSchemeKey(), res.getCurPeriod(), entityDefine);
                            if ("MD_CURRENCY@BASE".equals(dim.getDimKey())) {
                                labels.add(0, new MCLabel("PROVIDER_BASECURRENCY", "\u672c\u4f4d\u5e01"));
                                labels.add(1, new MCLabel("PROVIDER_PBASECURRENCY", "\u4e0a\u7ea7\u672c\u4f4d\u5e01"));
                            }
                            entityLabels.put(dim.getDimKey(), labels);
                        }
                    }
                    res.setEntityLabels(entityLabels);
                }
                res.setShowLastResult(this.resultService.hasLastResult(res.getTasks().get(0).getKey(), res.getCurPeriod(), res.getOrgEntity().getId(), CollectionUtils.isEmpty(res.getSchemes()) ? null : res.getSchemes().get(0).getKey()));
            }
        }
        catch (JQException e) {
            res.setErrorMsg(e.getErrorMessage());
        }
        catch (Exception e) {
            res.setErrorMsg(e.getMessage());
            logger.error("\u7efc\u5408\u5ba1\u6838\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
        return res;
    }

    private void buildOrgLinks(MCExecuteVO res, String taskKey, MCExecuteParamVO param) {
        if (param != null && StringUtils.hasText(param.getOrg())) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(param.getOrg());
            res.setOrgEntity(entityDefine);
        }
        ArrayList<MCLabel> orgLinks = new ArrayList<MCLabel>();
        List orgLinkDefs = this.dRunTime.listTaskOrgLinkByTask(taskKey);
        for (TaskOrgLinkDefine orgLinkDef : orgLinkDefs) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgLinkDef.getEntity());
            if (entityDefine == null) continue;
            if (res.getOrgEntity() == null) {
                res.setOrgEntity(entityDefine);
            }
            MCLabel vo = new MCLabel();
            vo.setCode(entityDefine.getId());
            vo.setTitle(entityDefine.getTitle());
            orgLinks.add(vo);
        }
        res.setOrgLinks(orgLinks);
    }

    private MCExecuteVO dealAppointScheme(MCExecuteParamVO param, MCExecuteVO res) throws Exception {
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(param.getAppointScheme());
        if (scheme == null) {
            res.setErrorMsg("\u53c2\u6570\u9519\u8bef\uff1a\u8bf7\u7ed1\u5b9a\u6b63\u786e\u7684\u7efc\u5408\u5ba1\u6838\u65b9\u6848\uff01");
            return res;
        }
        ArrayList<MultcheckScheme> schemes = new ArrayList<MultcheckScheme>();
        schemes.add(scheme);
        res.setSchemes(schemes);
        ArrayList<String> taskKeys = new ArrayList<String>();
        taskKeys.add(scheme.getTask());
        this.buildTaskList(res, taskKeys);
        TaskDefine taskDefine = res.getTasks().get(0);
        res.setOrgEntity(this.entityMetaService.queryEntity(scheme.getOrg()));
        ArrayList<MCLabel> orgLinks = new ArrayList<MCLabel>();
        res.setOrgLinks(orgLinks);
        List orgLinkDefs = this.dRunTime.listTaskOrgLinkByTask(scheme.getTask());
        for (TaskOrgLinkDefine orgLinkDef : orgLinkDefs) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgLinkDef.getEntity());
            if (entityDefine == null) continue;
            if (res.getOrgEntity() == null) {
                res.setOrgEntity(entityDefine);
            }
            MCLabel vo = new MCLabel();
            vo.setCode(entityDefine.getId());
            vo.setTitle(entityDefine.getTitle());
            orgLinks.add(vo);
        }
        res.setAdjust(this.dataSchemeServie.enableAdjustPeriod(taskDefine.getDataScheme()));
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(scheme.getFormScheme());
        List periodList = this.runTimeViewController.querySchemePeriodLinkByScheme(formScheme.getKey());
        if (!CollectionUtils.isEmpty(periodList)) {
            res.setPeriods(periodList.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList()));
            res.setCurPeriod(this.buildCurrentPeriod(formScheme.getDateTime(), res.getPeriods()));
        } else {
            String begin = formScheme.getFromPeriod();
            String end = formScheme.getToPeriod();
            res.setBegin(begin);
            res.setEnd(end);
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            String curPeriod = periodProvider.getCurPeriod().getCode();
            if (begin != null && periodProvider.comparePeriod(begin, curPeriod) >= 0) {
                curPeriod = begin;
            } else if (end != null && periodProvider.comparePeriod(curPeriod, end) >= 0) {
                curPeriod = end;
            }
            res.setCurPeriod(curPeriod);
        }
        if (StringUtils.hasText(param.getPeriod())) {
            res.setCurPeriod(param.getPeriod());
        }
        res.setPeriodType(taskDefine.getPeriodType().type());
        this.buildEntity(res, taskDefine, formScheme.getKey(), res.getCurPeriod());
        this.buildMCItem(res, res.getSchemes().get(0).getKey());
        res.setHistorySchemes(this.envService.getHistorySchemeByUserSource(res.getSchemes().get(0).getKey()));
        res.setShowLastResult(this.resultService.hasLastResult(res.getTasks().get(0).getKey(), res.getCurPeriod(), res.getOrgEntity().getId(), CollectionUtils.isEmpty(res.getSchemes()) ? null : res.getSchemes().get(0).getKey()));
        return res;
    }

    private String buildCurrentPeriod(String entityId, List<String> periods) {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityId);
        String curPeriod = periodProvider.getCurPeriod().getCode();
        if (CollectionUtils.isEmpty(periods)) {
            return curPeriod;
        }
        if (periods.size() == 1) {
            return periods.get(0);
        }
        if (periods.contains(curPeriod)) {
            return curPeriod;
        }
        String begin = periods.get(0);
        String end = periods.get(periods.size() - 1);
        if (periodProvider.comparePeriod(begin, curPeriod) >= 0) {
            return begin;
        }
        if (periodProvider.comparePeriod(curPeriod, end) >= 0) {
            return end;
        }
        return curPeriod;
    }

    @PostMapping(value={"/batch/tp-change"})
    @ApiOperation(value="\u4efb\u52a1\u65f6\u671f\u53d8\u5316\uff1atask period showScheme")
    public MCExecuteVO tpChange(@Valid @RequestBody MCExecuteParamVO param) {
        MCExecuteVO res = new MCExecuteVO();
        try {
            if (StringUtils.hasText(param.getAppointScheme())) {
                return this.dealAppointScheme(param, res);
            }
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(param.getTask());
            if (taskDefine == null) {
                throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_004);
            }
            this.buildOrgLinks(res, taskDefine.getKey(), param);
            res.setAdjust(this.dataSchemeServie.enableAdjustPeriod(taskDefine.getDataScheme()));
            this.buildPeriod(res, taskDefine, param.getPeriod());
            String period = res.getCurPeriod();
            if (param.isShowScheme()) {
                this.buildMCSchemeList(res, taskDefine, period, param);
                this.buildEntity(res, taskDefine, res.getFormScheme(), period);
                if (!CollectionUtils.isEmpty(res.getSchemes())) {
                    this.buildMCItem(res, res.getSchemes().get(0).getKey());
                    res.setHistorySchemes(this.envService.getHistorySchemeByUserSource(res.getSchemes().get(0).getKey()));
                }
            } else {
                SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
                if (scheme == null) {
                    throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_002);
                }
                res.setFormScheme(scheme.getSchemeKey());
                HashMap<String, List<MCLabel>> entityLabels = new HashMap<String, List<MCLabel>>();
                List<DataDimension> dataDimensions = this.mcDimService.getReportDims(taskDefine.getDataScheme());
                if (!CollectionUtils.isEmpty(dataDimensions)) {
                    for (DataDimension dim : dataDimensions) {
                        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                        res.addEntity(entityDefine);
                        List<MCLabel> labels = this.getMCLabels(taskDefine, scheme.getSchemeKey(), period, entityDefine);
                        if ("MD_CURRENCY@BASE".equals(dim.getDimKey())) {
                            labels.add(0, new MCLabel("PROVIDER_BASECURRENCY", "\u672c\u4f4d\u5e01"));
                            labels.add(1, new MCLabel("PROVIDER_PBASECURRENCY", "\u4e0a\u7ea7\u672c\u4f4d\u5e01"));
                        }
                        entityLabels.put(dim.getDimKey(), labels);
                    }
                }
                res.setEntityLabels(entityLabels);
            }
            res.setShowLastResult(this.resultService.hasLastResult(param.getTask(), res.getCurPeriod(), res.getOrgEntity().getId(), CollectionUtils.isEmpty(res.getSchemes()) ? null : res.getSchemes().get(0).getKey()));
        }
        catch (JQException e) {
            res.setErrorMsg(e.getErrorMessage());
        }
        catch (Exception e) {
            res.setErrorMsg(e.getMessage());
            logger.error("\u7efc\u5408\u5ba1\u6838\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
        return res;
    }

    @PostMapping(value={"/batch/mcs-change"})
    @ApiOperation(value="\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53d8\u5316 task mcScheme period showScheme")
    public MCExecuteVO mcsChange(@Valid @RequestBody MCExecuteParamVO param) {
        MCExecuteVO res = new MCExecuteVO();
        try {
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(param.getTask());
            if (taskDefine == null) {
                res.setErrorMsg("\u7ed1\u5b9a\u4efb\u52a1\u88ab\u5220\u9664");
                return res;
            }
            res.setAdjust(this.dataSchemeServie.enableAdjustPeriod(taskDefine.getDataScheme()));
            this.buildMCItem(res, param.getMcScheme());
            res.setShowLastResult(this.resultService.hasLastResult(param.getTask(), param.getPeriod(), param.getOrg(), param.getMcScheme()));
            res.setHistorySchemes(this.envService.getHistorySchemeByUserSource(param.getMcScheme()));
        }
        catch (Exception e) {
            res.setErrorMsg(e.getMessage());
            logger.error("\u7efc\u5408\u5ba1\u6838\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
        return res;
    }

    @PostMapping(value={"/batch/get-orgtree"})
    public MCExecuteVO getSchemeOrgTree(@Valid @RequestBody MCExecuteParamVO param) {
        MCExecuteVO res = new MCExecuteVO();
        try {
            MultcheckScheme mcScheme = this.schemeService.getSchemeByKey(param.getMcScheme());
            if (mcScheme.getOrgType() != OrgType.ALL) {
                MCOrgTreeDTO orgTreeDTO = this.mcOrgService.getOrgTreeBySchemePeriod(mcScheme, param.getPeriod());
                res.setOrgTree(orgTreeDTO.getTreeList());
                res.setOrgSize(orgTreeDTO.getSize());
            } else {
                res.setOrgTree(new ArrayList<ITree<OrgTreeNode>>());
            }
        }
        catch (Exception e) {
            res.setErrorMsg(e.getMessage());
            logger.error("\u7efc\u5408\u5ba1\u6838\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
        return res;
    }

    @PostMapping(value={"/batch/run"})
    @ApiOperation(value="\u6267\u884c\u7efc\u5408\u5ba1\u6838\u65b9\u6848")
    public AsyncTaskInfo batchMCScheme(@Valid @RequestBody MCRunVO vo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)vo));
        if (CheckSource.ENTRY.value() == vo.getSource().value()) {
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MCDataEntryBatchRealTimeTaskExecutor());
            npRealTimeTaskInfo.setTaskKey(vo.getTask());
        } else {
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MultcheckRealTimeTaskExecutor());
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/batch/result/{runId}/{task}"})
    @ApiOperation(value="\u67e5\u770b\u5ba1\u6838\u7ed3\u679c")
    public ResultDetailVO result(@PathVariable(value="runId") String runId, @PathVariable(value="task") String task) throws Exception {
        return this.resultService.buildResult(runId, task);
    }

    @GetMapping(value={"/batch/get-last-result/{task}/{period}/{org}", "/batch/get-last-result/{task}/{period}/{org}/{scheme}"})
    @ApiOperation(value="\u4e0a\u6b21\u5ba1\u6838\u7ed3\u679c")
    public ResultDetailVO getLastResult(@PathVariable(value="task") String task, @PathVariable(value="period") String period, @PathVariable(value="org") String org, @PathVariable(required=false, name="scheme") String scheme) throws Exception {
        return this.resultService.buildLastResult(task, period, org, scheme);
    }

    @PostMapping(value={"/batch/history-scheme/add"})
    @ApiOperation(value="\u6dfb\u52a0\u5386\u53f2\u65b9\u6848")
    public MCHistorySchemeVO addHistoryScheme(@Valid @RequestBody MCHistoryScheme scheme) throws Exception {
        MCHistoryScheme newScheme = this.envService.addHistoryScheme(scheme);
        MCHistorySchemeVO vo = new MCHistorySchemeVO();
        BeanUtils.copyProperties(newScheme, vo);
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(newScheme.getTask());
        IPeriodProvider provider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        vo.setPeriodTitle(provider.getPeriodTitle(vo.getPeriod()));
        return vo;
    }

    @GetMapping(value={"/batch/history-scheme/get/{source}"})
    @ApiOperation(value="\u67e5\u770b\u5f53\u524d\u5ba1\u6838\u65b9\u6848\u5386\u53f2\u65b9\u6848")
    public List<MCHistorySchemeVO> getHisSchemeBySource(@PathVariable(value="source") String source) throws Exception {
        return this.envService.getHistorySchemeByUserSource(source);
    }

    @GetMapping(value={"/batch/history-scheme/get-config/{source}"})
    @ApiOperation(value="\u67e5\u770b\u5f53\u524d\u5ba1\u6838\u65b9\u6848\u5386\u53f2\u65b9\u6848config")
    public String getHisSchemeConfigBySource(@PathVariable(value="source") String source) {
        source = HtmlUtils.cleanUrlXSS((String)source);
        return HtmlUtils.cleanUrlXSS((String)this.envService.getHisSchemeConfigByKey(source));
    }

    @PostMapping(value={"/batch/history-scheme/delete/{key}"})
    @ApiOperation(value="\u5220\u9664\u5386\u53f2\u65b9\u6848")
    public ResultVO deleteHistorySchemeByKey(@PathVariable(value="key") String key) {
        this.envService.delHistorySchemeByKey(key);
        return ResultVO.success(key);
    }

    @PostMapping(value={"/batch/history-scheme/batch-delete"})
    @ApiOperation(value="\u5220\u9664\u5386\u53f2\u65b9\u6848")
    public void batchDeleteResult(@RequestBody List<String> keys) {
        this.envService.batchDeleteHistory(keys);
    }

    @PostMapping(value={"/dataentry/run"})
    @ApiOperation(value="\u5f55\u5165\u6267\u884c\u7efc\u5408\u5ba1\u6838")
    public AsyncTaskInfo executeTask(@Valid @RequestBody MCRunVO param) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MCDataEntryRealTimeTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/dataentry/get-async-res/{id}"})
    public MCExecuteResult getAsyncRes(@PathVariable(value="id") String id) {
        Object res = this.asyncTaskDao.queryDetail(id);
        if (res == null) {
            return null;
        }
        return (MCExecuteResult)res;
    }

    @PostMapping(value={"/dataentry/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@RequestBody List<OrgExportVO> orgs, HttpServletResponse response) throws JQException {
        this.envService.exportResult(orgs, response);
    }

    private boolean check(boolean showTask, List<String> tasks, MCExecuteVO res) {
        if (!showTask && CollectionUtils.isEmpty(tasks)) {
            res.setErrorMsg("\u53c2\u6570\u9519\u8bef\uff1a\u9700\u7ed1\u5b9a\u4efb\u52a1\uff01");
            return true;
        }
        if (!showTask && tasks.size() > 1) {
            res.setErrorMsg("\u53c2\u6570\u9519\u8bef\uff1a\u53ea\u80fd\u7ed1\u5b9a\u4e00\u4e2a\u4efb\u52a1\uff01");
            return true;
        }
        return false;
    }

    private void buildMCSchemeList(MCExecuteVO res, TaskDefine taskDefine, String period, MCExecuteParamVO param) throws Exception {
        SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
        if (scheme == null) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_002);
        }
        res.setFormScheme(scheme.getSchemeKey());
        String appointOrg = null;
        appointOrg = param == null || !StringUtils.hasText(param.getOrg()) ? res.getOrgLinks().get(0).getCode() : param.getOrg();
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByFSAndOrg(scheme.getSchemeKey(), appointOrg);
        if (CollectionUtils.isEmpty(schemes)) {
            return;
        }
        Iterator<MultcheckScheme> iterator = schemes.iterator();
        while (iterator.hasNext()) {
            MultcheckScheme mcScheme = iterator.next();
            List<MultcheckItem> itemInfoList = this.schemeService.getItemInfoList(mcScheme.getKey());
            if (!itemInfoList.isEmpty()) continue;
            iterator.remove();
        }
        if (CollectionUtils.isEmpty(schemes)) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_005);
        }
        res.setSchemes(schemes);
    }

    private void buildPeriod(MCExecuteVO res, TaskDefine taskDefine, String period) throws Exception {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        String begin = taskDefine.getFromPeriod();
        String end = taskDefine.getToPeriod();
        if (!StringUtils.hasText(begin) || !StringUtils.hasText(end)) {
            List periodList = this.runTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
            res.setPeriods(periodList.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList()));
        } else {
            res.setBegin(begin);
            res.setEnd(end);
        }
        if (StringUtils.hasText(period)) {
            res.setCurPeriod(period);
        } else {
            String curPeriod = periodProvider.getCurPeriod().getCode();
            if (begin != null && periodProvider.comparePeriod(begin, curPeriod) >= 0) {
                curPeriod = begin;
            } else if (end != null && periodProvider.comparePeriod(curPeriod, end) >= 0) {
                curPeriod = end;
            }
            res.setCurPeriod(curPeriod);
        }
        if (PeriodType.CUSTOM == taskDefine.getPeriodType()) {
            Map<String, String> periodMap = periodProvider.getPeriodItems().stream().collect(Collectors.toMap(IPeriodRow::getCode, IPeriodRow::getTitle));
            ArrayList<Map<String, String>> customArr = new ArrayList<Map<String, String>>();
            while (periodProvider.comparePeriod(begin, end) <= 0) {
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("code", begin);
                m.put("title", periodMap.get(begin));
                customArr.add(m);
                begin = periodProvider.nextPeriod(begin);
            }
            res.setCustomArr(customArr);
        }
        res.setPeriodType(taskDefine.getPeriodType().type());
    }

    private void buildMCItem(MCExecuteVO res, String mcScheme) {
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(mcScheme);
        List<MultcheckItem> items = this.schemeService.getItemList(mcScheme);
        ArrayList<MultcheckItemVO> itemVOS = new ArrayList<MultcheckItemVO>();
        for (MultcheckItem item : items) {
            MultcheckItemVO itemVO = new MultcheckItemVO();
            BeanUtils.copyProperties(item, itemVO);
            IMultcheckItemProvider provider = this.envService.getProvider(item.getType());
            if (provider == null) continue;
            itemVO.setNeedChangeConfig(provider.canChangeConfig());
            itemVO.setDescription(provider.getRunItemDescribe(scheme.getFormScheme(), item));
            itemVO.setTypeTitle(provider.getTitle());
            itemVO.setPluginInfo(provider.getRunPropertyPluginInfo());
            itemVOS.add(itemVO);
        }
        res.setItems(itemVOS);
    }

    private void buildEntity(MCExecuteVO res, TaskDefine taskDefine, String formSchemeKey, String period) throws Exception {
        HashMap<String, List<MCLabel>> entityLabels = new HashMap<String, List<MCLabel>>();
        String dimsStr = taskDefine.getDims();
        if (StringUtils.hasText(dimsStr)) {
            List<DataDimension> dataDimensions = this.mcDimService.getDynamicDimsForPage(taskDefine.getDataScheme());
            for (DataDimension dim : dataDimensions) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                res.addEntity(entityDefine);
                List<MCLabel> labels = this.getMCLabels(taskDefine, formSchemeKey, period, entityDefine);
                if ("MD_CURRENCY@BASE".equals(dim.getDimKey())) {
                    labels.add(0, new MCLabel("PROVIDER_BASECURRENCY", "\u672c\u4f4d\u5e01"));
                    labels.add(1, new MCLabel("PROVIDER_PBASECURRENCY", "\u4e0a\u7ea7\u672c\u4f4d\u5e01"));
                }
                entityLabels.put(entityDefine.getDimensionName(), labels);
            }
        }
        res.setEntityLabels(entityLabels);
    }

    private List<MCLabel> getMCLabels(TaskDefine taskDefine, String formSchemeKey, String period, IEntityDefine entityDefine) throws Exception {
        List<IEntityRow> allRow = this.mcDimService.getEntityAllRow(entityDefine.getId(), formSchemeKey, taskDefine.getKey(), period);
        ArrayList<MCLabel> labels = new ArrayList<MCLabel>();
        for (IEntityRow row : allRow) {
            labels.add(new MCLabel(row.getCode(), row.getTitle()));
        }
        return labels;
    }

    private void buildTaskList(MCExecuteVO res, List<String> taskKeys) throws Exception {
        ArrayList<TaskDefine> tasks = new ArrayList<TaskDefine>();
        for (String taskKey : taskKeys) {
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(taskKey);
            if (taskDefine == null) continue;
            tasks.add(taskDefine);
        }
        if (CollectionUtils.isEmpty(tasks)) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.INIT_001);
        }
        res.setTasks(tasks);
    }
}

