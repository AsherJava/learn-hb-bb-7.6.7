/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  nr.midstore2.data.extension.bean.ReportDataSourceDTO
 *  nr.midstore2.data.midstoreresult.bean.BatchResultsFilter
 *  nr.midstore2.data.midstoreresult.bean.MidstoreResult
 *  nr.midstore2.data.midstoreresult.service.IBatchMidstoreResultService
 *  nr.midstore2.data.param.IReportMidstoreSchemeQueryService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.midstore2.batch.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.midstore2.batch.asynctask.BatchMidstoreAsyncTaskExecutor;
import com.jiuqi.nr.midstore2.batch.web.vo.MidstoreExecuteVO;
import com.jiuqi.nr.midstore2.batch.web.vo.MidstoreRunVO;
import com.jiuqi.nr.midstore2.batch.web.vo.MidstoreSchemeVO;
import com.jiuqi.nr.midstore2.batch.web.vo.ParameterChangeVO;
import com.jiuqi.nr.midstore2.batch.web.vo.ParameterVO;
import com.jiuqi.nr.midstore2.batch.web.vo.TaskTreeNodeVO;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.Valid;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.midstoreresult.bean.BatchResultsFilter;
import nr.midstore2.data.midstoreresult.bean.MidstoreResult;
import nr.midstore2.data.midstoreresult.service.IBatchMidstoreResultService;
import nr.midstore2.data.param.IReportMidstoreSchemeQueryService;
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
@RequestMapping(value={"/api/v1/midstore2"})
public class BatchMidstoreWeb {
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    private IReportMidstoreSchemeQueryService nrMidstoreService;
    @Autowired
    private IMidstoreSourceService sourceService;
    @Autowired
    private IMidstoreSchemeInfoService infoService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IBatchMidstoreResultService batchMidstoreResultService;

    @GetMapping(value={"/init-task-list"})
    @ApiOperation(value="\u521d\u59cb\u5316\u529f\u80fd\u53c2\u6570\u4efb\u52a1\u5217\u8868")
    public List<TaskDefine> initTaskList() {
        return this.runTimeAuthView.getAllTaskDefines();
    }

    @PostMapping(value={"/get-scheme-list"})
    @ApiOperation(value="\u6839\u636e\u4ea4\u6362\u6a21\u5f0f\u548c\u4efb\u52a1\u8ba1\u7b97\u4e2d\u95f4\u5e93\u4ea4\u6362\u65b9\u6848\u5217\u8868")
    public List<MidstoreSchemeDTO> getSchemeList(@Valid @RequestBody ParameterChangeVO vo) {
        if (!StringUtils.hasText(vo.getExchangeMode()) || CollectionUtils.isEmpty(vo.getTaskKeys())) {
            return Collections.EMPTY_LIST;
        }
        List schemes = this.nrMidstoreService.getSchemesByTasks(vo.getTaskKeys());
        return schemes.stream().filter(e -> Integer.parseInt(vo.getExchangeMode()) == e.getExchangeMode().getValue()).collect(Collectors.toList());
    }

    @PostMapping(value={"/init"})
    @ApiOperation(value="\u521d\u59cb\u5316\u529f\u80fd\u9875\u9762")
    public MidstoreExecuteVO init(@Valid @RequestBody ParameterVO parameter) throws Exception {
        MidstoreExecuteVO res = new MidstoreExecuteVO();
        String errorMsg = this.check(parameter, res);
        if (StringUtils.hasText(errorMsg)) {
            res.setErrorMsg(errorMsg);
            return res;
        }
        if (CollectionUtils.isEmpty(parameter.getTaskKeys())) {
            ArrayList<ITree<TaskTreeNodeVO>> taskTree = new ArrayList<ITree<TaskTreeNodeVO>>();
            taskTree.add(this.buildRootNode(""));
            res.setTaskTree(taskTree);
        } else {
            TaskDefine taskDefine = this.getTaskDefine(parameter.getTaskKeys());
            if (taskDefine == null) {
                res.setErrorMsg("\u7ed1\u5b9a\u4efb\u52a1\u88ab\u5220\u9664");
                return res;
            }
            this.buildTaskList(res, parameter.getTaskKeys());
            this.buildMidstoreScheme(res, parameter, taskDefine);
            this.buildMidstoreExecuteVO(res, taskDefine);
        }
        return res;
    }

    @PostMapping(value={"/change"})
    @ApiOperation(value="\u9875\u9762\u9009\u9879\u53d1\u751f\u53d8\u5316\u65f6\u5904\u7406")
    public MidstoreExecuteVO change(@Valid @RequestBody ParameterVO parameter) throws Exception {
        MidstoreExecuteVO res = new MidstoreExecuteVO();
        TaskDefine taskDefine = this.getTaskDefine(parameter.getTaskKeys());
        if (taskDefine == null) {
            res.setErrorMsg("\u7ed1\u5b9a\u4efb\u52a1\u88ab\u5220\u9664");
            return res;
        }
        this.buildMidstoreScheme(res, parameter, taskDefine);
        this.buildMidstoreExecuteVO(res, taskDefine);
        return res;
    }

    private TaskDefine getTaskDefine(List<String> taskKeys) throws Exception {
        String taskKey;
        TaskDefine taskDefine = null;
        Iterator<String> iterator = taskKeys.iterator();
        while (iterator.hasNext() && (taskDefine = this.runTimeAuthView.queryTaskDefine(taskKey = iterator.next())) == null) {
        }
        return taskDefine;
    }

    @PostMapping(value={"/batch-run"})
    public AsyncTaskInfo batchMidstore(@Valid @RequestBody MidstoreRunVO vo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)vo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchMidstoreAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/get-result-list/{exchangeMode}/{task}"})
    @ApiOperation(value="\u83b7\u53d6\u6267\u884c\u8bb0\u5f55\u5217\u8868")
    public List<MidstoreResult> getResultList(@PathVariable String task, @PathVariable String exchangeMode) {
        BatchResultsFilter filter = new BatchResultsFilter();
        filter.setUser(NpContextHolder.getContext().getUserId());
        filter.setTask(task);
        filter.setExchangeMode(exchangeMode);
        return this.batchMidstoreResultService.getBatchResults(filter).stream().peek(e -> {
            e.setTask(this.runTime.queryTaskDefine(e.getTask()).getTitle());
            e.setExchangeMode("0".equals(e.getExchangeMode()) ? "\u63d0\u53d6" : "\u63a8\u9001");
        }).collect(Collectors.toList());
    }

    @PostMapping(value={"/get-result-detail/{key}"})
    @ApiOperation(value="\u83b7\u53d6\u6267\u884c\u8bb0\u5f55\u8be6\u60c5")
    public String getResultDetail(@PathVariable String key) {
        key = HtmlUtils.cleanUrlXSS((String)key);
        return HtmlUtils.cleanUrlXSS((String)this.batchMidstoreResultService.getResultByKey(key));
    }

    @PostMapping(value={"/delete-result/{key}"})
    @ApiOperation(value="\u5220\u9664\u6267\u884c\u8bb0\u5f55")
    public void deleteResult(@PathVariable String key) {
        this.batchMidstoreResultService.deleteResult(key);
    }

    @PostMapping(value={"/batch-delete-result"})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6267\u884c\u8bb0\u5f55")
    public void batchDeleteResult(@RequestBody List<String> keys) {
        this.batchMidstoreResultService.batchDeleteResult(keys);
    }

    private String check(ParameterVO parameter, MidstoreExecuteVO res) {
        List<String> taskKeys = parameter.getTaskKeys();
        String exchangeMode = parameter.getExchangeMode();
        List<String> midstoreList = parameter.getMidstoreList();
        if (!StringUtils.hasText(exchangeMode)) {
            return "\u53c2\u6570\u9519\u8bef\uff1a\u4ea4\u6362\u6a21\u5f0f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01";
        }
        if (CollectionUtils.isEmpty(taskKeys) && !parameter.isShowTask()) {
            return "\u53c2\u6570\u9519\u8bef\uff1a\u9700\u7ed1\u5b9a\u4efb\u52a1\uff01";
        }
        if (CollectionUtils.isEmpty(midstoreList) && !parameter.isShowMidstore()) {
            return "\u53c2\u6570\u9519\u8bef\uff1a\u9700\u7ed1\u5b9a\u4e2d\u95f4\u5e93\u65b9\u6848";
        }
        if (!CollectionUtils.isEmpty(midstoreList)) {
            List schemes = this.nrMidstoreService.getSchemesBySchemeKeys(midstoreList);
            for (MidstoreSchemeDTO scheme : schemes) {
                String schemeTitle = " " + scheme.getTitle() + "[" + scheme.getCode() + "] ";
                MidstoreSchemeInfoDTO schemeInfo = this.infoService.getBySchemeKey(scheme.getKey());
                if (schemeInfo == null) {
                    return "\u53c2\u6570\u9519\u8bef\uff1a\u4e2d\u95f4\u5e93\u65b9\u6848" + schemeTitle + "\u672a\u53d1\u5e03";
                }
                if (Integer.parseInt(exchangeMode) == scheme.getExchangeMode().getValue()) continue;
                return "\u53c2\u6570\u9519\u8bef\uff1a\u4e2d\u95f4\u5e93\u65b9\u6848" + schemeTitle + "\u4ea4\u6362\u6a21\u5f0f\u4e3a\uff1a" + scheme.getExchangeMode().getTitle();
            }
            if (!CollectionUtils.isEmpty(taskKeys)) {
                List list = this.nrMidstoreService.getDataSoruceBySchemeKeys(midstoreList);
                for (ReportDataSourceDTO dto : list) {
                    if (taskKeys.contains(dto.getTaskKey())) continue;
                    MidstoreSchemeDTO schemeDTO = this.nrMidstoreService.getSchemeByKey(dto.getSchemeKey());
                    return "\u53c2\u6570\u9519\u8bef\uff1a\u4e2d\u95f4\u5e93\u65b9\u6848 " + schemeDTO.getTitle() + "[" + schemeDTO.getCode() + "] \u4e0e\u4efb\u52a1\u53c2\u6570\u4e0d\u4e00\u81f4";
                }
            }
        }
        return "";
    }

    private void buildTaskList(MidstoreExecuteVO res, List<String> taskKeys) throws Exception {
        ArrayList<TaskDefine> tasks = new ArrayList<TaskDefine>();
        for (String taskKey : taskKeys) {
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(taskKey);
            if (taskDefine == null) continue;
            tasks.add(taskDefine);
        }
        res.setTasks(tasks);
    }

    private void buildMidstoreScheme(MidstoreExecuteVO res, ParameterVO parameter, TaskDefine taskDefine) {
        if (CollectionUtils.isEmpty(parameter.getMidstoreList()) && taskDefine == null) {
            return;
        }
        HashMap<String, List<MidstoreSchemeVO>> map = new HashMap<String, List<MidstoreSchemeVO>>();
        List schemeInfos = CollectionUtils.isEmpty(parameter.getMidstoreList()) ? this.nrMidstoreService.getSchemesByTask(taskDefine.getKey(), PublishStateType.PUBLISHSTATE_SUCCESS) : this.nrMidstoreService.getSchemesBySchemeKeys(parameter.getMidstoreList(), PublishStateType.PUBLISHSTATE_SUCCESS);
        for (MidstoreSchemeDTO scheme : schemeInfos) {
            ReportDataSourceDTO sourceDto;
            String taskKey;
            if (scheme.getExchangeMode() == null) continue;
            MidstoreSchemeVO vo = new MidstoreSchemeVO(scheme);
            MidstoreSourceDTO source = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field");
            if (source == null || !StringUtils.hasText(taskKey = (sourceDto = new ReportDataSourceDTO(source)).getTaskKey()) || Integer.parseInt(parameter.getExchangeMode()) != scheme.getExchangeMode().getValue()) continue;
            vo.setHasDimension(sourceDto.isUseDimensionField());
            vo.setDimSetMap(sourceDto.getDimSetMap());
            MidstoreSchemeInfoDTO infoDTO = this.infoService.getBySchemeKey(scheme.getKey());
            if (infoDTO != null) {
                vo.setOrgName(infoDTO.getOrgDataName());
            }
            if (map.containsKey(taskKey)) {
                ((List)map.get(taskKey)).add(vo);
                continue;
            }
            ArrayList<MidstoreSchemeVO> list = new ArrayList<MidstoreSchemeVO>();
            list.add(vo);
            map.put(taskKey, list);
        }
        res.setSchemes(map);
    }

    private void buildMidstoreExecuteVO(MidstoreExecuteVO res, TaskDefine taskDefine) throws Exception {
        this.buildPeriod(res, taskDefine);
        res.addEntity(this.entityMetaService.queryEntity(taskDefine.getDw()));
        String dimsStr = taskDefine.getDims();
        if (StringUtils.hasText(dimsStr)) {
            String[] dims = dimsStr.split(";");
            List dataDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            Map dimMap = dataDimensions.stream().collect(Collectors.toMap(DataDimension::getDimKey, Function.identity()));
            for (String key : dims) {
                if (DataSchemeUtils.isSingleSelect((DataDimension)((DataDimension)dimMap.get(key)))) continue;
                res.addEntity(this.entityMetaService.queryEntity(key));
            }
        }
    }

    private ITree<TaskTreeNodeVO> buildRootNode(String taskKey) {
        TaskTreeNodeVO root = new TaskTreeNodeVO();
        root.setKey("00000000000000000000000000000000");
        root.setCode("00000000000000000000000000000000");
        root.setTitle("\u5168\u90e8\u4efb\u52a1");
        root.setType("GROUP");
        ITree node = new ITree((INode)root);
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<TaskTreeNodeVO>> children = new ArrayList<ITree<TaskTreeNodeVO>>();
        this.buildTaskTree(children, taskKey);
        node.setChildren(children);
        return node;
    }

    private void buildTaskTree(List<ITree<TaskTreeNodeVO>> children, String taskKey) {
        ArrayList<TaskGroupDefine> allTaskGroup = new ArrayList<TaskGroupDefine>();
        HashSet<String> groupSet = new HashSet<String>();
        List allTaskDefines = this.runTimeAuthView.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            List groups = this.runTimeAuthView.getGroupByTask(taskDefine.getKey());
            for (TaskGroupDefine group : groups) {
                if (!groupSet.add(group.getKey())) continue;
                allTaskGroup.add(group);
            }
        }
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup, taskKey);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            children.add(this.convertTaskTreeNode(task2, taskKey));
        }
    }

    private void buildChildTree(String parentId, List<TaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<TaskTreeNodeVO>> children, Set<String> tasksHasGroup, String taskKey) {
        List links;
        for (TaskGroupDefine group : allTaskGroup) {
            if (!this.equals(group.getParentKey(), parentId)) continue;
            ITree<TaskTreeNodeVO> node = BatchMidstoreWeb.convertGroupTreeNode(group);
            children.add(node);
            ArrayList<ITree<TaskTreeNodeVO>> nodeChildren = new ArrayList<ITree<TaskTreeNodeVO>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup, taskKey);
            node.setChildren(nodeChildren);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(parentId))) {
            taskList = links.stream().map(DesignTaskGroupLink::getTaskKey).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine task = allTaskMap.get(key);
                if (task == null) continue;
                children.add(this.convertTaskTreeNode(task, taskKey));
            }
        }
    }

    private boolean equals(String a, String b) {
        if (!StringUtils.hasText(a)) {
            a = null;
        }
        if (!StringUtils.hasText(b)) {
            b = null;
        }
        return a == null ? b == null : a.equals(b);
    }

    private static ITree<TaskTreeNodeVO> convertGroupTreeNode(TaskGroupDefine group) {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(group.getKey());
        r.setCode(group.getCode());
        r.setTitle(group.getTitle());
        r.setType("GROUP");
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_fenzu"});
        node.setLeaf(true);
        return node;
    }

    private ITree<TaskTreeNodeVO> convertTaskTreeNode(TaskDefine task, String taskKey) {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(task.getKey());
        r.setCode(task.getTaskCode());
        r.setTitle(task.getTitle());
        r.setType("TASK");
        r.setDataSchemeKey(task.getDataScheme());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_shujufangan"});
        node.setLeaf(true);
        node.setSelected(task.getKey().equals(taskKey));
        return node;
    }

    private void buildPeriod(MidstoreExecuteVO res, TaskDefine taskDefine) throws Exception {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        String begin = taskDefine.getFromPeriod();
        String end = taskDefine.getToPeriod();
        if (!StringUtils.hasText(begin) || !StringUtils.hasText(end)) {
            List periodList = this.runTime.querySchemePeriodLinkByTask(taskDefine.getKey());
            res.setPeriods(periodList.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList()));
        } else {
            res.setBegin(begin);
            res.setEnd(end);
        }
        String curPeriod = periodProvider.getCurPeriod().getCode();
        if (begin != null && periodProvider.comparePeriod(begin, curPeriod) >= 0) {
            curPeriod = begin;
        } else if (end != null && periodProvider.comparePeriod(curPeriod, end) >= 0) {
            curPeriod = end;
        }
        res.setCurPeriod(curPeriod);
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
}

