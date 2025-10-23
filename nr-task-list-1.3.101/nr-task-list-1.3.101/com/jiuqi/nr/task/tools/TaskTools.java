/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDimensionFilter
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.nr.task.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.task.dto.TaskDetailDTO;
import com.jiuqi.nr.task.dto.TaskDimension;
import com.jiuqi.nr.task.dto.ValidateTimeDTO;
import com.jiuqi.nr.task.service.IValidateTimeService;
import com.jiuqi.nr.task.web.vo.TaskDimensionVO;
import com.jiuqi.nr.task.web.vo.TaskOrgListVO;
import com.jiuqi.nr.task.web.vo.TaskParamVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TaskTools {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IValidateTimeService validateTimeService;

    public void setDimToTaskParam(List<DesignDataDimension> dimensions, TaskParamVO taskParam) {
        ArrayList<DesignDataDimension> orgScopes = new ArrayList<DesignDataDimension>();
        ArrayList<DesignDataDimension> dims = new ArrayList<DesignDataDimension>();
        block6: for (int i = 0; i < dimensions.size(); ++i) {
            DesignDataDimension dimension = dimensions.get(i);
            switch (dimension.getDimensionType()) {
                case PERIOD: {
                    IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimension.getDimKey());
                    taskParam.setPeriodType(periodEntity.getTitle());
                    String firstStartData = this.getFirstStartData(dimension.getDimKey());
                    if (StringUtils.isNotEmpty((String)firstStartData)) {
                        taskParam.setStarterPeriod(firstStartData);
                    }
                    taskParam.setDateTime(dimension.getDimKey());
                    continue block6;
                }
                case UNIT: {
                    IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(dimension.getDimKey());
                    taskParam.setDw(new TaskDimensionVO(true, dimension.getDimKey(), iEntityDefine.getTitle(), null));
                    continue block6;
                }
                case UNIT_SCOPE: {
                    orgScopes.add(dimension);
                    continue block6;
                }
                case DIMENSION: {
                    dims.add(dimension);
                    continue block6;
                }
            }
        }
        if (!orgScopes.isEmpty()) {
            taskParam.setOrgDimScope(this.setOrgScopeToTaskDimVO(orgScopes));
        } else {
            taskParam.setOrgDimScope(Arrays.asList(taskParam.getDw()));
        }
        if (!dims.isEmpty()) {
            taskParam.setDims(this.setDimsToTaskDimVO(dims));
        }
    }

    public List<TaskDimensionVO> setOrgScopeToTaskDimVO(List<DesignDataDimension> dimensions) {
        return this.getTaskDimensionVOS(dimensions, false);
    }

    public List<TaskDimensionVO> setDimsToTaskDimVO(List<DesignDataDimension> dimensions) {
        return this.getTaskDimensionVOS(dimensions, true);
    }

    private List<TaskDimensionVO> getTaskDimensionVOS(List<DesignDataDimension> dimensions, boolean isDim) {
        ArrayList<TaskDimensionVO> dims = new ArrayList<TaskDimensionVO>();
        dimensions.forEach(i -> {
            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(i.getDimKey());
            if (iEntityDefine != null) {
                TaskDimensionVO taskDimension = isDim ? new TaskDimensionVO(i.getDimKey(), iEntityDefine.getTitle(), false, null) : new TaskDimensionVO(true, i.getDimKey(), iEntityDefine.getTitle(), null);
                dims.add(taskDimension);
            }
        });
        return dims;
    }

    public void insertDimFilter(List<TaskDimension> dimensions, String taskKey) {
        ArrayList<DesignDimensionFilter> filterList = new ArrayList<DesignDimensionFilter>();
        for (TaskDimension taskDimension : dimensions) {
            DesignDimensionFilter dimensionFilter = this.designTimeViewController.initIDimensionFilter();
            dimensionFilter.setTaskKey(taskKey);
            dimensionFilter.setEntityId(taskDimension.getEntityId());
            dimensionFilter.setType(DimensionFilterType.LIST_SELECT);
            dimensionFilter.setListType(DimensionFilterListType.BLACK_LIST);
            dimensionFilter.setList(taskDimension.getKeys());
            filterList.add(dimensionFilter);
        }
        this.designTimeViewController.insertIDimensionFilter(filterList.toArray(new DesignDimensionFilter[filterList.size()]));
    }

    public void setTaskOrgList(DesignTaskDefine task, TaskParamVO taskParam, List<DesignDataDimension> dimensions) {
        List<DesignDataDimension> orgDimScope = dimensions.stream().filter(dimension -> dimension.getDimensionType() == DimensionType.UNIT_SCOPE).collect(Collectors.toList());
        Set dataSchemeUnits = orgDimScope.stream().map(DataDimension::getDimKey).collect(Collectors.toSet());
        List taskOrgLinks = this.designTimeViewController.listTaskOrgLinkByTask(task.getKey());
        Set taskUnits = taskOrgLinks.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toSet());
        ArrayList usedTaskOrgLinks = new ArrayList();
        boolean updateTaskOrgLink = true;
        if (orgDimScope.isEmpty()) {
            String newDw;
            List unit = dimensions.stream().filter(dimension -> dimension.getDimensionType() == DimensionType.UNIT).collect(Collectors.toList());
            String dw = task.getDw();
            if (dw.equals(newDw = ((DesignDataDimension)unit.get(0)).getDimKey())) {
                IEntityDefine entityDefine = this.iEntityMetaService.queryEntity(dw);
                taskParam.setDw(new TaskDimensionVO(true, dw, entityDefine.getTitle(), null));
            } else {
                IEntityDefine newEntity = this.iEntityMetaService.queryEntity(newDw);
                taskParam.setDw(new TaskDimensionVO(true, newDw, newEntity.getTitle(), null));
            }
            taskParam.setOrgDimScope(Arrays.asList(taskParam.getDw()));
            if (taskUnits.contains(taskParam.getDw()) && taskUnits.size() == 1) {
                updateTaskOrgLink = false;
            }
        } else {
            int originTaskUnitSize = taskUnits.size();
            taskUnits.retainAll(dataSchemeUnits);
            if (!CollectionUtils.isEmpty(taskUnits)) {
                List rightUnits = taskOrgLinks.stream().filter(taskOrgLink -> taskUnits.contains(taskOrgLink.getEntity())).collect(Collectors.toList());
                List sortedRightUnits = rightUnits.stream().sorted(Comparator.comparing(TaskOrgLinkDefine::getOrder)).collect(Collectors.toList());
                usedTaskOrgLinks.addAll(sortedRightUnits);
                if (taskUnits.contains(task.getDw())) {
                    IEntityDefine dwEntity = this.iEntityMetaService.queryEntity(task.getDw());
                    taskParam.setDw(new TaskDimensionVO(true, task.getDw(), dwEntity.getTitle(), null));
                } else {
                    IEntityDefine usedEntity = this.iEntityMetaService.queryEntity(((TaskOrgLinkDefine)sortedRightUnits.get(0)).getEntity());
                    taskParam.setDw(new TaskDimensionVO(true, ((TaskOrgLinkDefine)sortedRightUnits.get(0)).getEntity(), usedEntity.getTitle(), null));
                }
                if (taskUnits.size() == originTaskUnitSize) {
                    updateTaskOrgLink = false;
                }
            } else {
                IEntityDefine usedEntity = this.iEntityMetaService.queryEntity(((DesignDataDimension)orgDimScope.get(0)).getDimKey());
                taskParam.setDw(new TaskDimensionVO(true, ((DesignDataDimension)orgDimScope.get(0)).getDimKey(), usedEntity.getTitle(), null));
            }
            taskParam.setOrgDimScope(this.setOrgScopeToTaskDimVO(orgDimScope));
        }
        ArrayList<TaskOrgListVO> orgVo = new ArrayList<TaskOrgListVO>();
        for (TaskOrgLinkDefine orgLinkDefine : usedTaskOrgLinks) {
            if (!dataSchemeUnits.contains(orgLinkDefine.getEntity())) continue;
            orgVo.add(new TaskOrgListVO(orgLinkDefine, this.iEntityMetaService));
        }
        if (CollectionUtils.isEmpty(orgVo)) {
            TaskOrgLinkDefine usedTaskOrg = this.buildNewTaskOrgLink(taskParam.getDw().getEntityID(), task.getKey());
            orgVo.add(new TaskOrgListVO(usedTaskOrg, this.iEntityMetaService));
        }
        taskParam.setOrgList(orgVo);
        if (updateTaskOrgLink) {
            this.updateTaskOrgLink(orgVo, task.getKey());
        }
        if (!task.getDw().equals(taskParam.getDw().getEntityID())) {
            task.setDw(taskParam.getDw().getEntityID());
            this.designTimeViewController.updateTask(task);
        }
    }

    private TaskOrgLinkDefine buildNewTaskOrgLink(String entityId, String taskKey) {
        TaskOrgLinkDefineImpl orgLink = new TaskOrgLinkDefineImpl();
        orgLink.setKey(UUIDUtils.getKey());
        orgLink.setTask(taskKey);
        orgLink.setEntity(entityId);
        orgLink.setEntityAlias(null);
        orgLink.setOrder(OrderGenerator.newOrder());
        return orgLink;
    }

    public void setDims(DesignTaskDefine task, TaskParamVO taskParam, List<DesignDataDimension> dimensions) {
        List dataSchemeDimensions = dimensions.stream().filter(dimension -> dimension.getDimensionType() == DimensionType.DIMENSION).collect(Collectors.toList());
        List designDimensionFilter = this.designTimeViewController.listDimensionFilterByTask(taskParam.getKey());
        Map<String, DesignDimensionFilter> dimFilterMap = designDimensionFilter.stream().collect(Collectors.toMap(IDimensionFilter::getEntityId, v -> v));
        ArrayList<TaskDimensionVO> dimsVO = new ArrayList<TaskDimensionVO>();
        List dimKeys = dataSchemeDimensions.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        dimKeys.removeIf(AdjustUtils::isAdjust);
        String newDims = dimKeys.stream().collect(Collectors.joining(";"));
        for (String dimKey : dimKeys) {
            TaskDimensionVO taskDimension = null;
            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(dimKey);
            if (iEntityDefine == null) continue;
            if (dimFilterMap.get(dimKey) != null && !CollectionUtils.isEmpty(dimFilterMap.get(dimKey).getList())) {
                List<String> filterObjCodes = this.getDimFilterObjCodes(dimKey);
                taskDimension = dimFilterMap.get(dimKey).getList().size() == filterObjCodes.size() ? new TaskDimensionVO(dimKey, iEntityDefine.getTitle(), true, null) : new TaskDimensionVO(dimKey, iEntityDefine.getTitle(), false, JSONUtil.toJSONString((Object)dimFilterMap.get(dimKey).getList()));
            } else {
                taskDimension = new TaskDimensionVO(dimKey, iEntityDefine.getTitle(), false, null);
            }
            dimsVO.add(taskDimension);
        }
        taskParam.setDims(dimsVO);
        if (task.getDims() == null && newDims == null || task.getDims() != null && newDims != null && task.getDims().equals(newDims)) {
            return;
        }
        task.setDims(newDims);
        this.designTimeViewController.updateTask(task);
    }

    public void setPeriod(DesignTaskDefine task, TaskParamVO taskParam, List<DesignDataDimension> dimensions) {
        List period = dimensions.stream().filter(dimension -> dimension.getDimensionType() == DimensionType.PERIOD).collect(Collectors.toList());
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(((DesignDataDimension)period.get(0)).getDimKey());
        taskParam.setPeriodType(periodEntity.getTitle());
        taskParam.setPeriodOffset(task.getTaskPeriodOffset());
        taskParam.setDateTime(task.getDateTime());
        this.adjustTaskFromAndEnd(task, taskParam, periodEntity);
    }

    public void updateDimFilter(List<TaskDimension> dimensions, String taskKey) {
        this.designTimeViewController.deleteIDimensionFilterByTask(taskKey);
        this.insertDimFilter(dimensions, taskKey);
    }

    public void updateTaskOrgLink(List<TaskOrgListVO> taskOrgList, String taskKey) {
        this.designTimeViewController.deleteTaskOrgLinkByTask(taskKey);
        this.insertTaskOrgLink(taskOrgList, taskKey);
    }

    public void adjustTaskFromAndEnd(DesignTaskDefine taskDefine, TaskParamVO param, IPeriodEntity periodEntity) {
        IPeriodEntity originPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
        if (originPeriodEntity == null) {
            this.coverPeriod(taskDefine, param, periodEntity);
            return;
        }
        if (originPeriodEntity.getKey().equals(periodEntity.getKey())) {
            param.setStarterPeriod(taskDefine.getFromPeriod());
            param.setEndPeriod(taskDefine.getToPeriod());
            return;
        }
        if (originPeriodEntity.getPeriodType().equals((Object)periodEntity.getPeriodType())) {
            if (PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType())) {
                this.coverPeriod(taskDefine, param, periodEntity);
            }
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
            String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
            String newPeriodFrom = periodCodeRegion[0];
            String newPeriodEnd = periodCodeRegion[1];
            String oldPeriodFrom = taskDefine.getFromPeriod();
            String oldPeriodEnd = taskDefine.getToPeriod();
            if (PeriodUtils.isPeriod13((String)originPeriodEntity.getCode(), (PeriodType)originPeriodEntity.getPeriodType()) && periodEntity.getCode().equals("Y")) {
                String endMonth;
                String formMonth = oldPeriodFrom.substring(oldPeriodFrom.length() - 2);
                if (Integer.valueOf(formMonth) == 0) {
                    oldPeriodFrom = oldPeriodFrom.substring(0, oldPeriodFrom.length() - 2) + "01";
                }
                if (Integer.valueOf(endMonth = oldPeriodEnd.substring(oldPeriodEnd.length() - 2)) > 12) {
                    oldPeriodEnd = oldPeriodEnd.substring(0, oldPeriodEnd.length() - 2) + "12";
                }
            }
            if (periodProvider.comparePeriod(newPeriodFrom, oldPeriodFrom) < 0) {
                newPeriodFrom = oldPeriodFrom;
            }
            if (periodProvider.comparePeriod(newPeriodEnd, oldPeriodEnd) > 0) {
                newPeriodEnd = oldPeriodEnd;
            }
            param.setStarterPeriod(newPeriodFrom);
            param.setEndPeriod(newPeriodEnd);
            taskDefine.setDateTime(periodEntity.getCode());
            this.updateSchemeValidateTime(newPeriodFrom, newPeriodEnd, taskDefine, true);
            this.designTimeViewController.updateTask(taskDefine);
        } else {
            this.coverPeriod(taskDefine, param, periodEntity);
        }
    }

    private void coverPeriod(DesignTaskDefine taskDefine, TaskParamVO param, IPeriodEntity periodEntity) {
        String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey()).getPeriodCodeRegion();
        param.setStarterPeriod(periodCodeRegion[0]);
        param.setEndPeriod(periodCodeRegion[1]);
        taskDefine.setFromPeriod(periodCodeRegion[0]);
        taskDefine.setToPeriod(periodCodeRegion[1]);
        taskDefine.setDateTime(periodEntity.getCode());
        this.designTimeViewController.updateTask(taskDefine);
        List formSchemes = this.designTimeViewController.listFormSchemeByTask(param.getKey());
        ArrayList<ValidateTimeDTO> validateTimeDTO = new ArrayList<ValidateTimeDTO>();
        for (DesignFormSchemeDefine scheme : formSchemes) {
            ValidateTimeDTO validateTime = new ValidateTimeDTO();
            if (!scheme.getKey().equals(((DesignFormSchemeDefine)formSchemes.get(0)).getKey())) continue;
            validateTime.setFormSchemeKey(scheme.getKey());
            validateTime.setFrom(param.getStarterPeriod());
            validateTime.setEnd(param.getEndPeriod());
            validateTime.setEntity(periodEntity.getCode());
            validateTimeDTO.add(validateTime);
        }
        this.validateTimeService.save(validateTimeDTO, 0);
    }

    public void updateSchemeValidateTime(String updateFromPeriod, String updateEndPeriod, DesignTaskDefine taskDefine, boolean forceRemark) {
        if (StringUtils.isNotEmpty((String)updateFromPeriod) || StringUtils.isNotEmpty((String)updateEndPeriod)) {
            if (StringUtils.isNotEmpty((String)updateFromPeriod) && StringUtils.isNotEmpty((String)updateEndPeriod)) {
                if (!updateFromPeriod.equals(taskDefine.getFromPeriod()) || !updateEndPeriod.equals(taskDefine.getToPeriod()) || forceRemark) {
                    this.reMarkSchemePeriodLink(updateFromPeriod, updateEndPeriod, taskDefine);
                    taskDefine.setFromPeriod(updateFromPeriod);
                    taskDefine.setToPeriod(updateEndPeriod);
                }
            } else {
                boolean taskEndIsNull = false;
                String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodCodeRegion();
                if (StringUtils.isEmpty((String)updateFromPeriod) && StringUtils.isNotEmpty((String)periodCodeRegion[0])) {
                    updateFromPeriod = periodCodeRegion[0];
                }
                if (StringUtils.isEmpty((String)updateEndPeriod) && StringUtils.isNotEmpty((String)periodCodeRegion[1])) {
                    taskEndIsNull = true;
                    updateEndPeriod = periodCodeRegion[1];
                }
                this.reMarkSchemePeriodLink(updateFromPeriod, updateEndPeriod, taskDefine);
                taskDefine.setFromPeriod(updateFromPeriod);
                if (taskEndIsNull) {
                    taskDefine.setToPeriod(null);
                } else {
                    taskDefine.setToPeriod(updateEndPeriod);
                }
            }
        }
    }

    private void reMarkSchemePeriodLink(String updateFromPeriod, String updateEndPeriod, DesignTaskDefine taskDefine) {
        List<ValidateTimeVO> validateTimes = this.validateTimeService.queryByTask(taskDefine.getKey());
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
        String tempTime = null;
        if (!CollectionUtils.isEmpty(validateTimes) && validateTimes.size() == 1) {
            ValidateTimeVO validateTime = validateTimes.get(0);
            validateTime.setFrom(updateFromPeriod);
            validateTime.setEnd(updateEndPeriod);
        } else {
            int i;
            for (i = 0; i < validateTimes.size() && periodProvider.comparePeriod(validateTimes.get(i).getFrom(), updateFromPeriod) < 0; ++i) {
                tempTime = updateFromPeriod;
                if (periodProvider.comparePeriod(tempTime, validateTimes.get(i).getEnd()) > 0) {
                    validateTimes.remove(validateTimes.get(i));
                    --i;
                    continue;
                }
                validateTimes.get(i).setFrom(updateFromPeriod);
                break;
            }
            for (i = validateTimes.size() - 1; i >= 0 && periodProvider.comparePeriod(validateTimes.get(i).getEnd(), updateEndPeriod) > 0; --i) {
                tempTime = updateEndPeriod;
                if (periodProvider.comparePeriod(tempTime, validateTimes.get(i).getFrom()) >= 0) {
                    validateTimes.get(i).setEnd(updateEndPeriod);
                    break;
                }
                validateTimes.remove(validateTimes.get(i));
            }
        }
        if (CollectionUtils.isEmpty(validateTimes)) {
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskDefine.getKey());
            ValidateTimeVO vo = new ValidateTimeVO();
            vo.setFormSchemeKey(((DesignFormSchemeDefine)formSchemeDefines.get(0)).getKey());
            vo.setFrom(updateFromPeriod);
            vo.setEnd(updateEndPeriod);
            validateTimes.add(vo);
        }
        ArrayList<ValidateTimeDTO> saveValidateTimes = new ArrayList<ValidateTimeDTO>();
        for (ValidateTimeVO validateTime : validateTimes) {
            ValidateTimeDTO validateTimeDTO = new ValidateTimeDTO(validateTime, periodEntity.getKey());
            saveValidateTimes.add(validateTimeDTO);
        }
        this.validateTimeService.save(saveValidateTimes, 0);
    }

    public void insertTaskOrgLink(List<TaskOrgListVO> taskOrgList, String taskKey) {
        ArrayList<TaskOrgLinkDefineImpl> orgLinkDefines = new ArrayList<TaskOrgLinkDefineImpl>();
        if (!CollectionUtils.isEmpty(taskOrgList)) {
            for (TaskOrgListVO org : taskOrgList) {
                TaskOrgLinkDefineImpl orgLinkDefine = new TaskOrgLinkDefineImpl();
                orgLinkDefine.setKey(StringUtils.isEmpty((String)org.getKey()) ? UUIDUtils.getKey() : org.getKey());
                orgLinkDefine.setTask(taskKey);
                orgLinkDefine.setEntity(org.getEntityId());
                orgLinkDefine.setEntityAlias(org.getEntityAlias());
                orgLinkDefine.setOrder(StringUtils.isEmpty((String)org.getOrder()) ? OrderGenerator.newOrder() : org.getOrder());
                orgLinkDefines.add(orgLinkDefine);
            }
            this.designTimeViewController.insertTaskOrgLink(orgLinkDefines.toArray(new TaskOrgLinkDefine[orgLinkDefines.size()]));
        }
    }

    public List<String> getDimFilterObjCodes(String entityId) {
        String tableName = BaseDataAdapterUtil.isBaseData((String)entityId) ? entityId.substring(0, entityId.indexOf("@")) : null;
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        PageVO filterObj = this.baseDataClient.list(baseDataDTO);
        List<String> filterObjCode = filterObj.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
        return filterObjCode;
    }

    public String getNewTaskTitle() {
        String newFormTaskBaseTitle;
        int index = 0;
        String newFormTaskTitle = newFormTaskBaseTitle = "\u9ed8\u8ba4\u4efb\u52a1";
        List taskDefines = this.designTimeViewController.listAllTask();
        if (taskDefines != null && taskDefines.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (DesignTaskDefine taskDefine : taskDefines) {
                    String title = taskDefine.getTitle();
                    if (!newFormTaskTitle.equals(title)) continue;
                    newFormTaskTitle = newFormTaskBaseTitle + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        return newFormTaskTitle;
    }

    public String getFirstStartData(String dimKey) {
        String periodDate;
        block7: {
            IPeriodProvider periodProvider;
            IPeriodEntity periodEntity;
            block6: {
                periodDate = null;
                periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimKey);
                periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
                if (!PeriodType.YEAR.equals((Object)periodEntity.getPeriodType())) break block6;
                String currPeriod = periodProvider.getCurPeriod().getCode();
                if (null == currPeriod) break block7;
                String priorPeriod = periodProvider.priorPeriod(currPeriod);
                List periodItems = periodProvider.getPeriodItems();
                boolean periodDataHave = false;
                for (IPeriodRow periodItem : periodItems) {
                    if (!periodItem.getCode().equals(priorPeriod)) continue;
                    periodDate = priorPeriod;
                    periodDataHave = true;
                    break;
                }
                if (periodDataHave) break block7;
                periodDate = currPeriod;
                break block7;
            }
            if (PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType())) {
                List periodItems = periodProvider.getPeriodItems();
                if (periodItems.size() != 0) {
                    periodDate = ((IPeriodRow)periodItems.get(0)).getCode();
                }
            } else {
                List periodItems = periodProvider.getPeriodItems();
                IPeriodRow currPeriod = periodProvider.getCurPeriod();
                if (null != currPeriod) {
                    for (IPeriodRow periodItem : periodItems) {
                        if (periodDate != null || periodItem.getStartDate().getYear() < currPeriod.getStartDate().getYear()) continue;
                        periodDate = periodItem.getCode();
                    }
                }
            }
        }
        return periodDate;
    }

    public LinkedHashSet<String> judgeUpdate(Set<String> originGroupKeys, List<String> targetGroupKeys) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        result.addAll(originGroupKeys);
        result.retainAll(targetGroupKeys);
        return result;
    }

    public LinkedHashSet<String> judgeAdd(Set<String> originGroupKeys, List<String> targetGroupKeys) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        result.addAll(targetGroupKeys);
        result.removeAll(originGroupKeys);
        return result;
    }

    public LinkedHashSet<String> judgeDelete(Set<String> originGroupKeys, List<String> targetGroupKeys) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        result.addAll(originGroupKeys);
        result.removeAll(targetGroupKeys);
        return result;
    }

    public void doTaskGroupLinksAction(String taskKey, LinkedHashSet<String> needActionTaskGroupLinks, String actionType) {
        ArrayList<DesignTaskGroupLink> taskGroupLinks = new ArrayList<DesignTaskGroupLink>();
        for (String groupLink : needActionTaskGroupLinks) {
            DesignTaskGroupLink taskGroupLink = this.designTimeViewController.initTaskGroupLink();
            taskGroupLink.setTaskKey(taskKey);
            taskGroupLink.setGroupKey(groupLink);
            taskGroupLinks.add(taskGroupLink);
        }
        switch (actionType) {
            case "insert": {
                this.designTimeViewController.insertTaskGroupLink(taskGroupLinks.toArray(new DesignTaskGroupLink[taskGroupLinks.size()]));
                break;
            }
            case "delete": {
                this.designTimeViewController.deleteTaskGroupLink(taskGroupLinks.toArray(new DesignTaskGroupLink[taskGroupLinks.size()]));
                break;
            }
        }
    }

    public TaskDetailDTO buildTaskDetailByTaskParam(TaskParamVO taskParam) {
        TaskDetailDTO.Builder builder = new TaskDetailDTO.Builder(taskParam.getDataScheme());
        ArrayList<TaskDimension> reportDims = new ArrayList<TaskDimension>();
        if (taskParam.getDims() != null) {
            for (TaskDimensionVO dimensionVO : taskParam.getDims()) {
                TaskDimension taskDimension;
                if (dimensionVO.getAllUnit().booleanValue()) {
                    List<String> filterObjCode = this.getDimFilterObjCodes(dimensionVO.getEntityID());
                    taskDimension = new TaskDimension(dimensionVO, filterObjCode);
                } else if (dimensionVO.getFilterMessage() != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        taskDimension = new TaskDimension(dimensionVO, (List)objectMapper.readValue(dimensionVO.getFilterMessage(), List.class));
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    taskDimension = new TaskDimension(dimensionVO);
                }
                reportDims.add(taskDimension);
            }
        }
        return builder.setKey(taskParam.getKey()).setCode(taskParam.getCode()).setTitle(taskParam.getTitle()).setGroup(taskParam.getGroup()).setDataSchemeTitle(taskParam.getDataSchemeTitle()).setDw(new TaskDimension(taskParam.getDw())).setDims(reportDims).setPeriodType(PeriodType.fromTitle((String)taskParam.getPeriodType())).setPeriodOffset(taskParam.getPeriodOffset()).setStarterPeriod(taskParam.getStarterPeriod()).setEndPeriod(taskParam.getEndPeriod()).setDateTime(taskParam.getDateTime()).setAutoStartFill(null).setAutoCloseFill(null).setMeasureUnit(null).setGatherType(null).setFormulaSyntaxStyle(null).setEnableEFDC(null).setEntityViewsInEFDC(null).setTaskOrgList(taskParam.getOrgList()).build();
    }
}

