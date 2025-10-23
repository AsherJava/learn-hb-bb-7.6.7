/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.xlib.runtime.Assert
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.task.dto.ValidateTimeDTO;
import com.jiuqi.nr.task.service.IValidateTimeService;
import com.jiuqi.nr.task.web.vo.ValidateTimeCheckResultVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeMergeVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeSettingVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import com.jiuqi.xlib.runtime.Assert;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ValidateTimeServiceImpl
implements IValidateTimeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public void insertDefaultSchemePeriodLink(String formSchemeKey, String fromPeriod, String toPeriod, String dateTime) {
        if (StringUtils.isNotEmpty((String)fromPeriod) || StringUtils.isNotEmpty((String)toPeriod)) {
            ArrayList<ValidateTimeDTO> validateTimes = new ArrayList<ValidateTimeDTO>();
            IPeriodEntity iPeriod = this.getPeriodByScheme(formSchemeKey);
            if (StringUtils.isNotEmpty((String)fromPeriod) && StringUtils.isNotEmpty((String)toPeriod)) {
                ValidateTimeDTO validateTime = new ValidateTimeDTO(fromPeriod, toPeriod, formSchemeKey, iPeriod.getKey());
                validateTimes.add(validateTime);
            } else if (StringUtils.isNotEmpty((String)fromPeriod)) {
                String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dateTime).getPeriodCodeRegion();
                if (periodCodeRegion[1] != null) {
                    ValidateTimeDTO validateTime = new ValidateTimeDTO(fromPeriod, periodCodeRegion[1], formSchemeKey, iPeriod.getKey());
                    validateTimes.add(validateTime);
                }
            } else {
                String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dateTime).getPeriodCodeRegion();
                if (periodCodeRegion[0] != null) {
                    ValidateTimeDTO validateTime = new ValidateTimeDTO(periodCodeRegion[0], toPeriod, formSchemeKey, iPeriod.getKey());
                    validateTimes.add(validateTime);
                }
            }
            this.save(validateTimes, 1);
        } else {
            this.designTimeViewController.deleteSchemePeriodLinkByFormScheme(formSchemeKey);
        }
    }

    @Override
    public List<ValidateTimeVO> queryByTask(String taskKey) {
        ArrayList<ValidateTimeVO> validateTimes = new ArrayList<ValidateTimeVO>();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskKey);
        if (CollectionUtils.isEmpty(formSchemeDefines)) {
            return new ArrayList<ValidateTimeVO>();
        }
        for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
            validateTimes.addAll(this.queryByFormScheme(formSchemeDefine.getKey()));
        }
        this.validateTimeSort(validateTimes, this.periodEngineService.getPeriodAdapter().getPeriodEntity(task.getDateTime()));
        return validateTimes;
    }

    @Override
    public List<ValidateTimeVO> queryByFormScheme(String fromSchemeKey) {
        ArrayList<ValidateTimeVO> validateTimes = new ArrayList<ValidateTimeVO>();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(fromSchemeKey);
        List periodLinks = this.designTimeViewController.listSchemePeriodLinkByFormScheme(fromSchemeKey);
        this.filterPeriodLinks(periodLinks);
        if (CollectionUtils.isEmpty(periodLinks)) {
            return validateTimes;
        }
        IPeriodEntity period = this.getPeriodByScheme(fromSchemeKey);
        List<ValidateTimeDTO> validateTimeDTOS = this.mergePeriodRowsByLinks(periodLinks, period.getKey());
        for (ValidateTimeDTO validateTimeDTO : validateTimeDTOS) {
            validateTimes.add(this.validateTimeDTO2VO(validateTimeDTO, formScheme, period.getKey()));
        }
        this.validateTimeSort(validateTimes, period);
        return validateTimes;
    }

    private void filterPeriodLinks(List<DesignSchemePeriodLinkDefine> periodLinkDefines) {
        if (!CollectionUtils.isEmpty(periodLinkDefines)) {
            periodLinkDefines.stream().filter(periodLinkDefine -> !StringUtils.isEmpty((String)periodLinkDefine.getPeriodKey())).collect(Collectors.toList());
        }
    }

    @Override
    public ValidateTimeSettingVO queryLimitedOptions(String taskKey) {
        ValidateTimeSettingVO formSchemesAndLimitPeriod = new ValidateTimeSettingVO();
        List designFormSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskKey);
        ArrayList<ValidateTimeVO> schemes = new ArrayList<ValidateTimeVO>();
        for (DesignFormSchemeDefine schemeDefine : designFormSchemeDefines) {
            ValidateTimeVO validateTime = new ValidateTimeVO(schemeDefine);
            schemes.add(validateTime);
        }
        formSchemesAndLimitPeriod.setFormSchemes(schemes);
        DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskKey);
        IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
        String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodByViewKey.getKey()).getPeriodCodeRegion();
        String taskStart = designTaskDefine.getFromPeriod();
        String taskEnd = designTaskDefine.getToPeriod();
        String useLimitStart = "";
        String useLimitEnd = "";
        useLimitStart = taskStart != null ? taskStart : periodCodeRegion[0];
        useLimitEnd = taskEnd != null ? taskEnd : periodCodeRegion[1];
        formSchemesAndLimitPeriod.setLimitStart(useLimitStart);
        formSchemesAndLimitPeriod.setLimitEnd(useLimitEnd);
        return formSchemesAndLimitPeriod;
    }

    @Override
    public List<ValidateTimeVO> merge(ValidateTimeMergeVO mergeVO) {
        ArrayList<ValidateTimeVO> validateTimes = new ArrayList<ValidateTimeVO>();
        ArrayList<ValidateTimeVO> couldMergeValidateTimes = new ArrayList<ValidateTimeVO>();
        for (ValidateTimeVO validateTime : mergeVO.getValidateTimes()) {
            if (!mergeVO.getFormSchemeKey().equals(validateTime.getFormSchemeKey())) continue;
            couldMergeValidateTimes.add(validateTime);
        }
        IPeriodEntity period = this.getPeriodByScheme(mergeVO.getFormSchemeKey());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(period.getKey());
        if (couldMergeValidateTimes.size() <= 1) {
            for (ValidateTimeVO validateTime : mergeVO.getValidateTimes()) {
                if (StringUtils.isEmpty((String)validateTime.getFromTimeTitle())) {
                    validateTime.setFromTimeTitle(periodProvider.getPeriodTitle(validateTime.getFrom()));
                }
                if (StringUtils.isEmpty((String)validateTime.getEndTimeTitle())) {
                    validateTime.setEndTimeTitle(periodProvider.getPeriodTitle(validateTime.getEnd()));
                }
                validateTimes.add(validateTime);
            }
            return validateTimes;
        }
        HashSet distinctPeriodRows = new HashSet();
        for (ValidateTimeVO validateTime : couldMergeValidateTimes) {
            List<IPeriodRow> periodRows = this.splitPeriod(new ValidateTimeDTO(validateTime, period.getKey()));
            List periodKeys = periodRows.stream().map(IPeriodRow::getCode).collect(Collectors.toList());
            distinctPeriodRows.addAll(periodKeys);
        }
        ArrayList<String> sortedPeriodKeys = new ArrayList<String>(distinctPeriodRows);
        sortedPeriodKeys.sort((arg_0, arg_1) -> ((IPeriodProvider)periodProvider).comparePeriod(arg_0, arg_1));
        List<ValidateTimeDTO> mergedValidateTimes = this.mergePeriodRowsByPeriodKeys(sortedPeriodKeys, mergeVO.getFormSchemeKey(), periodProvider);
        Map<String, DesignFormSchemeDefine> schemeMap = this.designTimeViewController.listFormSchemeByTask(mergeVO.getTaskKey()).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
        for (ValidateTimeDTO validateTimeDTO : mergedValidateTimes) {
            validateTimes.add(this.validateTimeDTO2VO(validateTimeDTO, schemeMap.get(validateTimeDTO.getFormSchemeKey()), period.getKey()));
        }
        ArrayList<ValidateTimeVO> updatedValidateTimes = new ArrayList<ValidateTimeVO>(mergeVO.getValidateTimes());
        updatedValidateTimes.removeIf(o -> o.getFormSchemeKey().equals(mergeVO.getFormSchemeKey()));
        updatedValidateTimes.addAll(validateTimes);
        updatedValidateTimes.sort((o1, o2) -> periodProvider.comparePeriod(o1.getFrom(), o2.getFrom()));
        return updatedValidateTimes;
    }

    @Override
    public ValidateTimeCheckResultVO check(List<ValidateTimeVO> validateTimes) {
        ValidateTimeCheckResultVO checkResult = new ValidateTimeCheckResultVO();
        if (validateTimes == null || validateTimes.size() <= 1) {
            return new ValidateTimeCheckResultVO();
        }
        HashSet<ValidateTimeVO> checkResults = new HashSet<ValidateTimeVO>();
        IPeriodEntity period = this.getPeriodByScheme(validateTimes.get(0).getFormSchemeKey());
        IPeriodEntity periodView = this.periodEngineService.getPeriodAdapter().getPeriodEntity(period.getKey());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodView.getKey());
        for (int i = 0; i < validateTimes.size(); ++i) {
            for (int j = i + 1; j <= validateTimes.size() - 1; ++j) {
                int b = periodProvider.comparePeriod(validateTimes.get(i).getEnd(), validateTimes.get(j).getFrom());
                int c = periodProvider.comparePeriod(validateTimes.get(i).getFrom(), validateTimes.get(j).getEnd());
                if (b < 0 || c > 0) continue;
                checkResults.add(validateTimes.get(i));
                checkResults.add(validateTimes.get(j));
            }
        }
        if (!CollectionUtils.isEmpty(checkResults)) {
            checkResult.setValidateDataTimes(new ArrayList<ValidateTimeVO>(checkResults));
            checkResult.setMessage("\u68c0\u6d4b\u5230\u5f53\u524d\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5b58\u5728\u51b2\u7a81\uff0c\u8bf7\u68c0\u67e5\uff01");
            checkResult.setCheckResult("error");
        } else {
            checkResult.setCheckResult("success");
        }
        return checkResult;
    }

    @Override
    public ValidateTimeCheckResultVO checkEmptyPeriod(List<ValidateTimeVO> validateTimes) {
        ValidateTimeCheckResultVO checkResult = new ValidateTimeCheckResultVO();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(validateTimes.get(0).getFormSchemeKey());
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(task.getDateTime());
        IPeriodEntity period = periodEntityAdapter.getPeriodEntity(task.getDateTime());
        String taskFromTime = task.getFromPeriod();
        String taskEndTime = task.getToPeriod();
        if (taskFromTime == null || taskEndTime == null) {
            if (taskFromTime != null) {
                taskEndTime = periodProvider.getPeriodCodeRegion()[1];
            } else {
                taskFromTime = periodProvider.getPeriodCodeRegion()[0];
            }
        }
        List<IPeriodRow> periodRows = this.splitPeriod(new ValidateTimeDTO(taskFromTime, taskEndTime, formScheme.getKey(), period.getKey()));
        List<String> periodKeys = periodRows.stream().map(IPeriodRow::getCode).collect(Collectors.toList());
        for (ValidateTimeVO validateTime : validateTimes) {
            List<IPeriodRow> usedValidateTime = this.splitPeriod(new ValidateTimeDTO(validateTime, period.getKey()));
            ArrayList<String> toRemoveRows = new ArrayList<String>();
            for (IPeriodRow iPeriodRow : usedValidateTime) {
                toRemoveRows.add(iPeriodRow.getCode());
            }
            periodKeys.removeAll(toRemoveRows);
        }
        periodKeys.sort((arg_0, arg_1) -> ((IPeriodProvider)periodProvider).comparePeriod(arg_0, arg_1));
        List<ValidateTimeDTO> mergedValidateTimes = this.mergePeriodRowsByPeriodKeys(periodKeys, null, periodProvider);
        ArrayList<ValidateTimeVO> emptyTime = new ArrayList<ValidateTimeVO>();
        if (mergedValidateTimes.size() > 0) {
            for (ValidateTimeDTO validateTimeDTO : mergedValidateTimes) {
                emptyTime.add(this.validateTimeDTO2VO(validateTimeDTO, null, period.getKey()));
            }
        }
        if (CollectionUtils.isEmpty(emptyTime)) {
            checkResult.setCheckResult("success");
        } else {
            checkResult.setCheckResult("warn");
            checkResult.setMessage("\u4ee5\u4e0b\u65f6\u95f4\u6bb5\u5b58\u5728\u7a7a\u7f3a\uff0c\u6570\u636e\u5f55\u5165\u4e2d\u5207\u6362\u81f3\u8be5\u65f6\u671f\u65f6\u4f1a\u51fa\u73b0\u9519\u8bef\uff0c\u8bf7\u786e\u8ba4\u3002");
            checkResult.setValidateDataTimes(emptyTime);
        }
        return checkResult;
    }

    @Override
    public ValidateTimeCheckResultVO checkAtTaskLimit(String taskKey, List<ValidateTimeVO> validateTimes) {
        ValidateTimeCheckResultVO checkResult = new ValidateTimeCheckResultVO();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(task.getDateTime());
        String taskFromTime = task.getFromPeriod();
        String taskEndTime = task.getToPeriod();
        ArrayList<ValidateTimeVO> errorTime = new ArrayList<ValidateTimeVO>();
        for (ValidateTimeVO vo : validateTimes) {
            if (periodProvider.comparePeriod(vo.getFrom(), taskFromTime) == -1) {
                checkResult.setMessage("\u6240\u8bbe\u7f6e\u751f\u6548\u65f6\u671f\u8d77\u59cb\u65f6\u95f4\u5c0f\u4e8e\u4efb\u52a1\u5f00\u59cb\u65f6\u671f");
                errorTime.add(vo);
                break;
            }
            if (taskEndTime == null || periodProvider.comparePeriod(vo.getEnd(), taskEndTime) != 1) continue;
            checkResult.setMessage("\u6240\u8bbe\u7f6e\u751f\u6548\u65f6\u671f\u622a\u6b62\u65f6\u95f4\u5927\u4e8e\u4efb\u52a1\u622a\u6b62\u65f6\u671f");
            errorTime.add(vo);
            break;
        }
        if (CollectionUtils.isEmpty(errorTime)) {
            checkResult.setCheckResult("success");
        } else {
            checkResult.setCheckResult("error");
            checkResult.setValidateDataTimes(errorTime);
        }
        return checkResult;
    }

    @Override
    public void save(List<ValidateTimeDTO> validateDateTimes, int saveType) {
        if (null != validateDateTimes && validateDateTimes.size() != 0) {
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(validateDateTimes.get(0).getFormSchemeKey());
            ArrayList<DesignSchemePeriodLinkDefine> periodLinks = new ArrayList<DesignSchemePeriodLinkDefine>();
            for (ValidateTimeDTO validateDateTime : validateDateTimes) {
                if (StringUtils.isEmpty((String)validateDateTime.getFrom()) || StringUtils.isEmpty((String)validateDateTime.getEnd())) {
                    DesignSchemePeriodLinkDefine def = this.designTimeViewController.initSchemePeriodLink();
                    def.setSchemeKey(validateDateTime.getFormSchemeKey());
                    def.setPeriodKey(null);
                    periodLinks.add(def);
                    continue;
                }
                List<IPeriodRow> periodRows = this.splitPeriod(validateDateTime);
                List<DesignSchemePeriodLinkDefine> allLinks = this.createLinks(validateDateTime.getFormSchemeKey(), periodRows);
                periodLinks.addAll(allLinks);
            }
            if (saveType == 0) {
                this.designTimeViewController.deleteSchemePeriodLinkByTask(formScheme.getTaskKey());
            } else {
                this.designTimeViewController.deleteSchemePeriodLinkByFormScheme(formScheme.getKey());
            }
            this.designTimeViewController.insertSchemePeriodLink(periodLinks.toArray(new DesignSchemePeriodLinkDefine[periodLinks.size()]));
        }
    }

    @Override
    public void doSave(List<ValidateTimeVO> validateDateTimes, int saveType) {
        IPeriodEntity period = this.getPeriodByScheme(validateDateTimes.get(0).getFormSchemeKey());
        ArrayList<ValidateTimeDTO> validateTimeDTOS = new ArrayList<ValidateTimeDTO>();
        for (ValidateTimeVO validateTime : validateDateTimes) {
            ValidateTimeDTO validateTimeDTO = new ValidateTimeDTO(validateTime, period.getKey());
            validateTimeDTOS.add(validateTimeDTO);
        }
        this.save(validateTimeDTOS, saveType);
    }

    private List<IPeriodRow> splitPeriod(ValidateTimeDTO validateTime) {
        Assert.notNull((Object)validateTime.getFrom(), (String)"FromTime must not be null");
        Assert.notNull((Object)validateTime.getEnd(), (String)"EndTime must not be null");
        Assert.notNull((Object)validateTime.getEntity(), (String)"Entity must not be null");
        ArrayList<IPeriodRow> periodRows = new ArrayList<IPeriodRow>();
        if (StringUtils.isNotEmpty((String)validateTime.getFormSchemeKey()) && StringUtils.isNotEmpty((String)validateTime.getFrom()) && StringUtils.isNotEmpty((String)validateTime.getEnd())) {
            List collect = this.periodEngineService.getPeriodAdapter().getPeriodProvider(validateTime.getEntity()).getPeriodItems();
            boolean start = false;
            for (IPeriodRow row : collect) {
                if (row.getCode().equals(validateTime.getFrom())) {
                    start = true;
                }
                if (start) {
                    periodRows.add(row);
                }
                if (!row.getCode().equals(validateTime.getEnd())) continue;
                start = false;
            }
        }
        return periodRows;
    }

    private List<ValidateTimeDTO> mergePeriodRowsByLinks(List<DesignSchemePeriodLinkDefine> linkDefines, String entityKey) {
        ArrayList<ValidateTimeDTO> validateTimes = new ArrayList<ValidateTimeDTO>();
        HashMap<String, String> map = new HashMap<String, String>();
        for (DesignSchemePeriodLinkDefine linkDefine : linkDefines) {
            if (StringUtils.isEmpty((String)linkDefine.getPeriodKey())) continue;
            map.put(linkDefine.getPeriodKey(), linkDefine.getPeriodKey());
        }
        List collect = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
        String scheme = linkDefines.get(0).getSchemeKey();
        boolean islx = false;
        ValidateTimeDTO obj = new ValidateTimeDTO();
        for (int i = 0; i < collect.size(); ++i) {
            IPeriodRow row = (IPeriodRow)collect.get(i);
            if (row.getCode().equals(map.get(row.getCode()))) {
                if (islx) {
                    obj.setEnd(row.getCode());
                    continue;
                }
                if (i == collect.size() - 1) {
                    obj = new ValidateTimeDTO();
                    obj.setFormSchemeKey(scheme);
                    obj.setFrom(row.getCode());
                    obj.setEnd(row.getCode());
                    validateTimes.add(obj);
                    continue;
                }
                islx = true;
                obj = new ValidateTimeDTO();
                obj.setFormSchemeKey(scheme);
                obj.setFrom(row.getCode());
                validateTimes.add(obj);
                continue;
            }
            islx = false;
            if (!StringUtils.isNotEmpty((String)obj.getFormSchemeKey()) || i == 0) continue;
            obj.setEnd(((IPeriodRow)collect.get(i - 1)).getCode());
            obj = new ValidateTimeDTO();
        }
        return validateTimes;
    }

    private ValidateTimeVO validateTimeDTO2VO(ValidateTimeDTO validateTimeDTO, DesignFormSchemeDefine formSchemeDefine, String periodKey) {
        ValidateTimeVO validateTimeVO = new ValidateTimeVO(validateTimeDTO, formSchemeDefine);
        validateTimeVO.setFromTimeTitle(this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodKey).getPeriodTitle(validateTimeDTO.getFrom()));
        validateTimeVO.setEndTimeTitle(this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodKey).getPeriodTitle(validateTimeDTO.getEnd()));
        return validateTimeVO;
    }

    private List<DesignSchemePeriodLinkDefine> createLinks(String scheme, List<IPeriodRow> periodRows) {
        ArrayList<DesignSchemePeriodLinkDefine> defines = new ArrayList<DesignSchemePeriodLinkDefine>();
        if (null != periodRows) {
            for (IPeriodRow row : periodRows) {
                DesignSchemePeriodLinkDefine def = this.designTimeViewController.initSchemePeriodLink();
                def.setIsdefault(false);
                def.setSchemeKey(scheme);
                def.setPeriodKey(row.getCode());
                defines.add(def);
            }
        }
        return defines;
    }

    private IPeriodEntity getPeriodByScheme(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        String dateTime = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey()).getDateTime();
        IPeriodEntity period = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
        return period;
    }

    private void validateTimeSort(List<ValidateTimeVO> validateTimes, final IPeriodEntity periodEntity) {
        final IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        validateTimes.sort(new Comparator<ValidateTimeVO>(){

            @Override
            public int compare(ValidateTimeVO o1, ValidateTimeVO o2) {
                return periodAdapter.getPeriodProvider(periodEntity.getKey()).comparePeriod(o1.getFrom(), o2.getFrom());
            }
        });
    }

    private List<ValidateTimeDTO> mergePeriodRowsByPeriodKeys(List<String> periodKeys, String formSchemeKey, IPeriodProvider periodProvider) {
        ArrayList<ValidateTimeDTO> mergedValidateTimes = new ArrayList<ValidateTimeDTO>();
        ValidateTimeDTO mergedStartAndEnd = new ValidateTimeDTO();
        for (int i = 0; i < periodKeys.size(); ++i) {
            mergedStartAndEnd.setFrom(periodKeys.get(i));
            for (int j = i; j < periodKeys.size() - 1; ++j) {
                if (periodProvider.priorPeriod(periodKeys.get(j + 1)).equals(periodKeys.get(j))) {
                    if (j + 1 != periodKeys.size() - 1) continue;
                    mergedStartAndEnd.setEnd(periodKeys.get(j + 1));
                    i = j + 1;
                    break;
                }
                mergedStartAndEnd.setEnd(periodKeys.get(j));
                i = j;
                break;
            }
            if (i == periodKeys.size() - 1) {
                mergedStartAndEnd.setEnd(periodKeys.get(i));
            }
            mergedValidateTimes.add(new ValidateTimeDTO(mergedStartAndEnd.getFrom(), mergedStartAndEnd.getEnd(), formSchemeKey));
        }
        return mergedValidateTimes;
    }
}

