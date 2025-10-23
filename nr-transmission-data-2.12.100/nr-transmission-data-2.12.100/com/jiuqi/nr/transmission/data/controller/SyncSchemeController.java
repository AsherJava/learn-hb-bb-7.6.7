/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.mapping2.common.NrMappingUtil
 *  com.jiuqi.nr.mapping2.provider.NrMappingParam
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.service.PeriodAdapterService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.service.PeriodAdapterService;
import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeSchemeException;
import com.jiuqi.nr.transmission.data.intf.CopySchemeParam;
import com.jiuqi.nr.transmission.data.intf.FormSchemeParam;
import com.jiuqi.nr.transmission.data.intf.TaskParam;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import com.jiuqi.nr.transmission.data.vo.ReselectPeriodVO;
import com.jiuqi.nr.transmission.data.vo.SyncSchemeVO;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/sync/scheme/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u65b9\u6848\u670d\u52a1"})
public class SyncSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(SyncSchemeController.class);
    @Autowired
    private ISyncSchemeService syncSchemeService;
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    PeriodAdapterService periodAdapterService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IReportParamService reportParamService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IMappingSchemeService mappingSchemeService;

    @ApiOperation(value="\u65b0\u589e\u65b9\u6848")
    @PostMapping(value={"insert_scheme"})
    public boolean insertScheme(@RequestBody SyncSchemeVO groupVO) {
        return this.syncSchemeService.insert(groupVO);
    }

    @ApiOperation(value="\u5220\u9664\u65b9\u6848")
    @PostMapping(value={"delete_scheme/{schemeKey}"})
    public boolean deleteScheme(@PathVariable String schemeKey) {
        return this.syncSchemeService.delete(schemeKey);
    }

    @ApiOperation(value="\u67e5\u627e\u65b9\u6848")
    @GetMapping(value={"get_scheme/{schemeKey}"})
    public SyncSchemeVO get(@PathVariable String schemeKey) throws Exception {
        SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.get(schemeKey);
        SyncSchemeVO voInstance = SyncSchemeVO.getVOInstance(syncSchemeDTO);
        if (voInstance.getSchemeParam().getPeriod() != 2) {
            ReselectPeriodVO periodValue = this.reportParamService.getPeriodValue(syncSchemeDTO.getSchemeParam().getPeriod(), syncSchemeDTO.getSchemeParam().getTask());
            voInstance.setFormSchemeKey(periodValue.getFormSchemeKey());
            voInstance.getSchemeParam().setPeriodValue(periodValue.getPeriod());
        } else {
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(syncSchemeDTO.getSchemeParam().getPeriodValue(), syncSchemeDTO.getSchemeParam().getTask());
                String formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
                voInstance.setFormSchemeKey(formSchemeKey);
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2\u4efb\u52a1\uff1a" + syncSchemeDTO.getSchemeParam().getTask() + "\uff0c\u65f6\u671f\uff1a" + syncSchemeDTO.getSchemeParam().getPeriodValue() + "\u5bf9\u5e94\u7684\u65b9\u6848\u9519\u8bef");
            }
        }
        return voInstance;
    }

    @ApiOperation(value="\u67e5\u627e\u5e26\u6709\u540c\u6b65\u5386\u53f2\u4fe1\u606f\u7684\u65b9\u6848")
    @GetMapping(value={"get_scheme_with_history/{schemeKey}"})
    public SyncSchemeVO getWithHistory(@PathVariable String schemeKey) {
        SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.get(schemeKey);
        SyncSchemeVO syncSchemeVO = SyncSchemeVO.getVOInstance(syncSchemeDTO);
        List<SyncHistoryDTO> historyDTos = this.syncHistoryService.getByScheme(schemeKey);
        if (historyDTos != null && historyDTos.size() > 0) {
            historyDTos.sort(Comparator.comparing(SyncHistoryDO::getStartTime));
            syncSchemeVO.setAlllSyncHistory(historyDTos);
            syncSchemeVO.setLastSyncHistory(historyDTos.get(historyDTos.size() - 1));
        }
        return syncSchemeVO;
    }

    @ApiOperation(value="\u641c\u7d22\u5168\u90e8\u65b9\u6848")
    @GetMapping(value={"get_all_scheme"})
    public List<SyncSchemeVO> getAll() {
        SyncSchemeDTO schemeDTO = new SyncSchemeDTO();
        List<SyncSchemeDTO> list = this.syncSchemeService.list(schemeDTO);
        List<SyncSchemeVO> syncSchemeVOS = SyncSchemeVO.toVOListInstance(list);
        this.getVOTaskTitle(syncSchemeVOS);
        return syncSchemeVOS;
    }

    @ApiOperation(value="\u641c\u7d22\u5206\u7ec4\u4e0b\u7684\u65b9\u6848")
    @GetMapping(value={"get_scheme_for_group/{groupKey}"})
    public List<SyncSchemeVO> getByGroup(@PathVariable String groupKey) {
        SyncSchemeDTO schemeDTO = new SyncSchemeDTO();
        schemeDTO.setGroup(groupKey);
        List<SyncSchemeDTO> list = this.syncSchemeService.list(schemeDTO);
        List<SyncSchemeVO> syncSchemeVOS = SyncSchemeVO.toVOListInstance(list);
        this.getVOTaskTitle(syncSchemeVOS);
        return syncSchemeVOS;
    }

    @ApiOperation(value="\u641c\u7d22\u5206\u7ec4\u4e0b\u5e26\u540c\u6b65\u5386\u53f2\u7684\u65b9\u6848")
    @GetMapping(value={"get_scheme_with_history_for_group/{groupKey}"})
    public List<SyncSchemeVO> getByGroupWithHistory(@PathVariable String groupKey) {
        SyncSchemeDTO schemeDTO = new SyncSchemeDTO();
        schemeDTO.setGroup(groupKey);
        List<SyncSchemeDTO> list = this.syncSchemeService.list(schemeDTO);
        List<String> schemeKeys = list.stream().map(SyncSchemeDO::getKey).collect(Collectors.toList());
        List<SyncHistoryDTO> historys = this.syncHistoryService.getBySchemes(schemeKeys);
        List<SyncSchemeVO> syncSchemeVOS = SyncSchemeVO.toVOListInstance(list);
        Map<String, List<SyncHistoryDTO>> map = historys.stream().collect(Collectors.groupingBy(SyncHistoryDO::getSchemeKey));
        this.getVOTaskTitle(syncSchemeVOS, map);
        return syncSchemeVOS;
    }

    @ApiOperation(value="\u4fee\u6539\u65b9\u6848")
    @PostMapping(value={"update_scheme"})
    public boolean updateScheme(@RequestBody SyncSchemeVO groupVO) {
        return this.syncSchemeService.update(groupVO);
    }

    @ApiOperation(value="\u53e6\u5b58\u4e3a\u65b9\u6848")
    @PostMapping(value={"copy_scheme"})
    public boolean copyScheme(@RequestBody CopySchemeParam copySchemeParam) {
        SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.get(copySchemeParam.getSourceSchemeKey());
        syncSchemeDTO.setTitle(copySchemeParam.getNewTitle());
        syncSchemeDTO.setGroup(copySchemeParam.getNewGroup());
        syncSchemeDTO.setCode(copySchemeParam.getNewCode());
        syncSchemeDTO.setDesc(copySchemeParam.getNewDesc());
        return this.syncSchemeService.insert(syncSchemeDTO);
    }

    @ApiOperation(value="\u5224\u65ad\u65b9\u6848\u4ee3\u7801\u662f\u5426\u76f8\u540c")
    @PostMapping(value={"scheme_code_check"})
    public boolean checkSchemeCode(@RequestBody String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        List<SyncSchemeDTO> syncSchemeDTOS = this.syncSchemeService.listWithOutParam();
        return syncSchemeDTOS.stream().anyMatch(s -> s.getCode().equals(code));
    }

    @ApiOperation(value="\u5224\u65ad\u65b9\u6848\u540d\u79f0\u662f\u5426\u76f8\u540c")
    @PostMapping(value={"scheme_title_check"})
    public boolean checkSchemeName(@RequestBody String title) {
        if (!StringUtils.hasText(title)) {
            return false;
        }
        List<SyncSchemeDTO> syncSchemeDTOS = this.syncSchemeService.listWithOutParam();
        return syncSchemeDTOS.stream().anyMatch(s -> s.getTitle().equals(title));
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u670d\u52a1\u4e0b\u4efb\u52a1\u53c2\u6570")
    @PostMapping(value={"get_all_task_param"})
    public List<TaskParam> getAllTaskParam() throws Exception {
        ArrayList<TaskParam> allTaskParams = new ArrayList<TaskParam>();
        List<String> taskList = this.reportParamService.getTaskList();
        if (CollectionUtils.isEmpty(taskList)) {
            logger.info("\u591a\u7ea7\u90e8\u7f72\uff0c\u4e3b\u5b50\u670d\u52a1\uff0c\u67e5\u8be2\u51fa\u6765\u7684\u5173\u8054\u4efb\u52a1\u4e3a\u7a7a");
        }
        List allSchemes = this.mappingSchemeService.getAllSchemes();
        HashMap<String, List> mappingSchemeMaps = new HashMap<String, List>();
        for (MappingScheme allScheme : allSchemes) {
            NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam((MappingScheme)allScheme);
            if (nrMappingParam == null || !StringUtils.hasText(nrMappingParam.getTaskKey()) || !StringUtils.hasText(nrMappingParam.getFormSchemeKey())) continue;
            mappingSchemeMaps.computeIfAbsent(nrMappingParam.getTaskKey(), key -> new ArrayList()).add(new MappingSchemeVO(allScheme.getKey(), allScheme.getTitle(), nrMappingParam.getFormSchemeKey()));
        }
        for (String taskKey : taskList) {
            String tasFromPeriod;
            String taskToPeriod;
            logger.info("\u591a\u7ea7\u90e8\u7f72\u4e3b\u5b50\u670d\u52a1\u5173\u8054\u4efb\u52a1key\u4e3a\uff1a{}", (Object)taskKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            TaskParam taskParam = new TaskParam();
            logger.info("\u591a\u7ea7\u90e8\u7f72\u4e3b\u5b50\u670d\u52a1\u5173\u8054\u7684\u4efb\u52a1\u5728\u5f53\u524d\u670d\u52a1\u67e5\u8be2\u51fa\u6765\u7684\u4efb\u52a1\u7684\u540d\u79f0\u4e3a\uff1a{}", (Object)taskDefine.getTitle());
            taskParam.setTaskKey(taskDefine.getKey());
            taskParam.setTaskTitle(taskDefine.getTitle());
            String dw = taskDefine.getDw();
            taskParam.setTaskDw(dw);
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dw);
            if (iEntityDefine != null) {
                taskParam.setTaskDwTitle(iEntityDefine.getTitle());
            } else {
                taskParam.setTaskDwTitle("\u5b9e\u4f53\u67e5\u8be2\u4e3a\u7a7a");
            }
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dw);
            String dataScheme = taskDefine.getDataScheme();
            List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme, DimensionType.DIMENSION);
            List entityDefines = dataSchemeDimension.stream().filter(a -> a.getDimAttribute() == null || a.getDimAttribute() != null && entityModel.getAttribute(a.getDimAttribute()).isMultival()).map(a -> this.entityMetaService.queryEntity(a.getDimKey())).collect(Collectors.toList());
            for (IEntityDefine entityDefine : entityDefines) {
                if (entityDefine == null || entityDefine.getDimensionName().equals(dw) || entityDefine.getDimensionName().equals("ADJUST")) continue;
                taskParam.getTaskDimToTitle().put(entityDefine.getDimensionName(), entityDefine.getTitle());
            }
            taskParam.setFromPeriod(taskDefine.getFromPeriod());
            taskParam.setToPeriod(taskDefine.getToPeriod());
            String dateTime = taskDefine.getDateTime();
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(dateTime);
            String thenPeriod = periodProvider.getCurPeriod().getCode();
            int taskPeriodOffset = taskDefine.getTaskPeriodOffset();
            if (taskPeriodOffset > 0) {
                for (int i = 0; i < taskPeriodOffset; ++i) {
                    thenPeriod = periodProvider.nextPeriod(thenPeriod);
                }
            } else if (taskPeriodOffset < 0) {
                for (int i = 0; i < -taskPeriodOffset; ++i) {
                    thenPeriod = periodProvider.priorPeriod(thenPeriod);
                }
            }
            if (periodProvider.comparePeriod(taskToPeriod = taskDefine.getToPeriod(), thenPeriod) < 0) {
                thenPeriod = taskToPeriod;
            }
            if (periodProvider.comparePeriod(tasFromPeriod = taskDefine.getFromPeriod(), thenPeriod) > 0) {
                thenPeriod = tasFromPeriod;
            }
            taskParam.setThenPeriod(thenPeriod);
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(thenPeriod, taskDefine.getKey());
                String formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
                if (StringUtils.hasText(formSchemeKey)) {
                    taskParam.setFormSchemeKey(formSchemeKey);
                } else {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                    taskParam.setFormSchemeKey(((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
                }
                taskParam.setThenFormSchemeKey(taskParam.getFormSchemeKey());
            }
            catch (Exception e) {
                logger.error(String.format("\u67e5\u8be2\u4efb\u52a1:%s\u65f6\u671f:%s\u5173\u8054\u7684\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38", thenPeriod, taskDefine.getKey()));
            }
            List mappingSchemeForTask = (List)mappingSchemeMaps.get(taskKey);
            if (!CollectionUtils.isEmpty(mappingSchemeForTask)) {
                Map<String, List<MappingSchemeVO>> groupBy = mappingSchemeForTask.stream().collect(Collectors.groupingBy(MappingSchemeVO::getFormSchemeKey));
                taskParam.setMappingSchemeMapsForTask(groupBy);
                taskParam.setMappingSchemes(groupBy.get(taskParam.getFormSchemeKey()));
            }
            if (taskDefine.getPeriodType() == PeriodType.CUSTOM) {
                taskParam.setUndefinePeriod(true);
                List periodItems = periodProvider.getPeriodItems();
                taskParam.setUndefinePeriodValues(periodItems);
            } else {
                taskParam.setUndefinePeriod(false);
            }
            allTaskParams.add(taskParam);
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u4e3b\u5b50\u670d\u52a1\u5173\u8054\u7684\u4efb\u52a1\u6700\u7ec8\u67e5\u8be2\u6570\u91cf\u4e3a\uff1a{} ,\u5faa\u73af\u6253\u5370\u5176\u540d\u79f0", (Object)allTaskParams.size());
        if (allTaskParams.size() > 0) {
            for (TaskParam allTaskParam : allTaskParams) {
                logger.info("\u540d\u79f0\u4e3a\uff1a{} ", (Object)allTaskParam.getTaskTitle());
            }
        }
        return allTaskParams;
    }

    @ApiOperation(value="\u8ba1\u7b97\u65f6\u671f\u503c")
    @GetMapping(value={"get_task_period_value/{period}/{task}"})
    public ReselectPeriodVO getPeriodValue(@PathVariable int period, @PathVariable String task) {
        return this.reportParamService.getPeriodValue(period, task);
    }

    @ApiOperation(value="\u6839\u636e\u65f6\u671f\u548c\u4efb\u52a1\u7684key\u67e5\u8be2\u62a5\u8868\u65b9\u6848")
    @PostMapping(value={"get_task_period_form_scheme"})
    public String getFormSchemeKey(@RequestBody FormSchemeParam formSchemeParam) throws JQException {
        String formSchemeKey;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(formSchemeParam.getPeriodValue(), formSchemeParam.getTaskKey());
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            if (!StringUtils.hasText(formSchemeKey)) {
                List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(formSchemeParam.getTaskKey());
                formSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u67e5\u8be2\u4efb\u52a1:%s\u65f6\u671f:%s\u5173\u8054\u7684\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38", formSchemeParam.getPeriodValue(), formSchemeParam.getTaskKey()));
            throw new JQException((ErrorEnum)SchemeSchemeException.SEARCH_SCHEME_FORM_SCHEME_ERROR, (Throwable)e);
        }
        return formSchemeKey;
    }

    @ApiOperation(value="\u83b7\u53d6\u91cd\u9009\u65f6\u671f\u540e\u7684\u5355\u4f4d\u3001\u62a5\u8868\u65b9\u6848\u3001\u6620\u5c04\u65b9\u6848")
    @PostMapping(value={"reselect"})
    public ReselectPeriodVO reselect(@RequestBody ReselectPeriodVO reselectPeriodVO) {
        return this.reportParamService.reselectPeriod(reselectPeriodVO);
    }

    public void getVOTaskTitle(List<SyncSchemeVO> syncSchemeVOS) {
        for (SyncSchemeVO syncSchemeVO : syncSchemeVOS) {
            TaskDefine taskDefine;
            SyncSchemeParamDO schemeParam = syncSchemeVO.getSchemeParam();
            if (schemeParam == null || (taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeVO.getSchemeParam().getTask())) == null) continue;
            syncSchemeVO.setTaskTitle(taskDefine.getTitle());
        }
    }

    public void getVOTaskTitle(List<SyncSchemeVO> syncSchemeVOS, Map<String, List<SyncHistoryDTO>> map) {
        for (SyncSchemeVO syncSchemeVO : syncSchemeVOS) {
            TaskDefine taskDefine;
            SyncSchemeParamDO schemeParam;
            List<SyncHistoryDTO> syncHistoryDTOS = map.get(syncSchemeVO.getKey());
            if (syncHistoryDTOS != null && syncHistoryDTOS.size() > 0) {
                syncHistoryDTOS.sort(Comparator.comparing(SyncHistoryDO::getStartTime));
                syncSchemeVO.setAlllSyncHistory(syncHistoryDTOS);
                syncSchemeVO.setLastSyncHistory(syncHistoryDTOS.get(syncHistoryDTOS.size() - 1));
            }
            if ((schemeParam = syncSchemeVO.getSchemeParam()) == null || (taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeVO.getSchemeParam().getTask())) == null) continue;
            syncSchemeVO.setTaskTitle(taskDefine.getTitle());
        }
    }
}

