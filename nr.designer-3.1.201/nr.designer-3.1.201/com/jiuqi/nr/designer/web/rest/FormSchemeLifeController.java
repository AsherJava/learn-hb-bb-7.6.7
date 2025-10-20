/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.util.SchemePeriodObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeLifeMergeParams;
import com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeLifeSave;
import com.jiuqi.nr.designer.web.facade.FormSchemesAndLimitPeriod;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u62a5\u8868\u65b9\u6848\u5173\u8054\u751f\u6548\u65f6\u671f\u6a21\u5757"})
public class FormSchemeLifeController {
    @Autowired
    private SaveSchemePeriodHelper saveSchemePeriodHelper;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    private Logger logger = LoggerFactory.getLogger(FormSchemeLifeController.class);

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u6240\u6709\u751f\u6548\u65f6\u671f")
    @RequestMapping(value={"/life-of-formscheme/{formSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSchemeLifeObj> getLifeOfScheme(@PathVariable String formSchemeKey) throws JQException {
        try {
            List<SchemePeriodObj> schemePeriodObjs = this.saveSchemePeriodHelper.queryLinksByScheme(formSchemeKey);
            String dateTime = this.saveSchemePeriodHelper.queryDateTimeViewString(formSchemeKey);
            final IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
            if (null == schemePeriodObjs || schemePeriodObjs.isEmpty()) {
                return Collections.emptyList();
            }
            final IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            ArrayList<FormSchemeLifeObj> formSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>();
            for (SchemePeriodObj schemePeriodObj : schemePeriodObjs) {
                FormSchemeLifeObj obj = new FormSchemeLifeObj();
                obj.setRecordKey(UUID.randomUUID().toString());
                obj.setKey(schemePeriodObj.getScheme());
                obj.setFromPeriod(schemePeriodObj.getStart());
                obj.setToPeriod(schemePeriodObj.getEnd());
                if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code()) {
                    String fromPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).getPeriodTitle(obj.getFromPeriod());
                    obj.setFromPeriod(obj.getFromPeriod());
                    obj.setFromPeriodTitle(fromPeriod);
                    String toPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).getPeriodTitle(obj.getToPeriod());
                    obj.setToPeriod(obj.getToPeriod());
                    obj.setToPeriodTitle(toPeriod);
                }
                formSchemeLifeObjs.add(obj);
            }
            formSchemeLifeObjs.sort(new Comparator<FormSchemeLifeObj>(){

                @Override
                public int compare(FormSchemeLifeObj o1, FormSchemeLifeObj o2) {
                    return periodAdapter.getPeriodProvider(periodEntity.getKey()).comparePeriod(o1.getFromPeriod(), o2.getFromPeriod());
                }
            });
            return formSchemeLifeObjs;
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_185, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u4efb\u52a1\u4e0b\u5168\u90e8\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u751f\u6548\u65f6\u671f")
    @RequestMapping(value={"/all-formschemes-life/{taskKey}"}, method={RequestMethod.GET})
    public List<FormSchemeLifeObj> getAllFormSchemesLife(@PathVariable String taskKey) throws JQException {
        try {
            List<SchemePeriodObj> schemePeriodObjsList = this.saveSchemePeriodHelper.queryLinksByTask(taskKey);
            DesignTaskDefine queryTaskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
            final IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity iPeriodByViewKey = periodAdapter.getPeriodEntity(queryTaskDefine.getDateTime());
            final IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(iPeriodByViewKey.getKey());
            if (null == schemePeriodObjsList || schemePeriodObjsList.isEmpty()) {
                return new ArrayList<FormSchemeLifeObj>();
            }
            Map<String, DesignFormSchemeDefine> schemeMap = this.iDesignTimeViewController.queryFormSchemeByTask(taskKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
            ArrayList<FormSchemeLifeObj> formSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>();
            for (SchemePeriodObj schemePeriodObj : schemePeriodObjsList) {
                FormSchemeLifeObj formSchemeLifeObj = FormSchemeLifeObj.toFormSchemeLifeObj(schemePeriodObj, schemeMap);
                if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code()) {
                    String fromPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).getPeriodTitle(formSchemeLifeObj.getFromPeriod());
                    formSchemeLifeObj.setFromPeriod(formSchemeLifeObj.getFromPeriod());
                    formSchemeLifeObj.setFromPeriodTitle(fromPeriod);
                    String toPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).getPeriodTitle(formSchemeLifeObj.getToPeriod());
                    formSchemeLifeObj.setToPeriod(formSchemeLifeObj.getToPeriod());
                    formSchemeLifeObj.setToPeriodTitle(toPeriod);
                }
                formSchemeLifeObjs.add(formSchemeLifeObj);
            }
            formSchemeLifeObjs.sort(new Comparator<FormSchemeLifeObj>(){

                @Override
                public int compare(FormSchemeLifeObj o1, FormSchemeLifeObj o2) {
                    return periodAdapter.getPeriodProvider(periodEntity.getKey()).comparePeriod(o1.getFromPeriod(), o2.getFromPeriod());
                }
            });
            return formSchemeLifeObjs;
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_185, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u4efb\u52a1\u4e0b\u5168\u90e8\u62a5\u8868\u65b9\u6848\u548c\u65f6\u671f\u9009\u62e9\u9650\u5236")
    @RequestMapping(value={"/all-formschemes/{taskKey}"}, method={RequestMethod.GET})
    public FormSchemesAndLimitPeriod getAllFormSchemes(@PathVariable String taskKey) throws JQException {
        FormSchemesAndLimitPeriod formSchemesAndLimitPeriod = new FormSchemesAndLimitPeriod();
        try {
            List designFormSchemeDefines = this.iDesignTimeViewController.queryFormSchemeByTask(taskKey);
            ArrayList<FormSchemeLifeObj> formSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>();
            for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
                formSchemeLifeObjs.add(new FormSchemeLifeObj(designFormSchemeDefine.getKey(), designFormSchemeDefine.getFormSchemeCode(), designFormSchemeDefine.getTitle()));
            }
            DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
            IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
            String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodByViewKey.getKey()).getPeriodCodeRegion();
            formSchemesAndLimitPeriod.setFormSchemes(formSchemeLifeObjs);
            String taskStart = this.iDesignTimeViewController.queryTaskDefine(taskKey).getFromPeriod();
            String taskEnd = this.iDesignTimeViewController.queryTaskDefine(taskKey).getToPeriod();
            String useLimitStart = "";
            String useLimitEnd = "";
            useLimitStart = taskStart != null ? taskStart : periodCodeRegion[0];
            useLimitEnd = taskEnd != null ? taskEnd : periodCodeRegion[1];
            formSchemesAndLimitPeriod.setLimitStart(useLimitStart);
            formSchemesAndLimitPeriod.setLimitEnd(useLimitEnd);
            return formSchemesAndLimitPeriod;
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_193, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u51b2\u7a81\u524d\u5408\u5e76\u540c\u4e00\u62a5\u8868\u65b9\u6848\u7684\u91cd\u53e0\u6216\u8005\u8fde\u7eed\u7684\u65f6\u95f4\u6bb5")
    @RequestMapping(value={"/merge-formscheme-life"}, method={RequestMethod.POST})
    public List<FormSchemeLifeObj> mergeSchemeLife(@RequestBody FormSchemeLifeMergeParams params) throws JQException {
        ArrayList<SchemePeriodObj> mergingLifeList = new ArrayList<SchemePeriodObj>();
        for (FormSchemeLifeObj formSchemeLifeObj : params.getFormSchemeLifeObjs()) {
            if (!formSchemeLifeObj.getKey().equals(params.getFormSchemeKey())) continue;
            mergingLifeList.add(new SchemePeriodObj(params.getFormSchemeKey(), formSchemeLifeObj.getFromPeriod(), formSchemeLifeObj.getToPeriod()));
        }
        if (mergingLifeList.size() == 0) {
            return new ArrayList<FormSchemeLifeObj>(params.getFormSchemeLifeObjs());
        }
        String dateTime = this.saveSchemePeriodHelper.queryDateTimeViewString(((SchemePeriodObj)mergingLifeList.get(0)).getScheme());
        IPeriodEntity periodView = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
        final IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodView.getKey());
        if (mergingLifeList.size() <= 1) {
            ArrayList<FormSchemeLifeObj> allFormSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>(params.getFormSchemeLifeObjs());
            allFormSchemeLifeObjs.sort(new Comparator<FormSchemeLifeObj>(){

                @Override
                public int compare(FormSchemeLifeObj o1, FormSchemeLifeObj o2) {
                    return periodProvider.comparePeriod(o1.getFromPeriod(), o2.getFromPeriod());
                }
            });
            return allFormSchemeLifeObjs;
        }
        HashSet mergedRowsSet = new HashSet();
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(params.getTaskKey());
        IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
        for (SchemePeriodObj schemePeriodObj : mergingLifeList) {
            List<IPeriodRow> toMerge = this.saveSchemePeriodHelper.splitPeriod(schemePeriodObj, iPeriodByViewKey.getKey());
            ArrayList<String> toMergeRows = new ArrayList<String>();
            for (IPeriodRow iPeriodRow : toMerge) {
                toMergeRows.add(iPeriodRow.getCode());
            }
            mergedRowsSet.addAll(toMergeRows);
        }
        ArrayList<String> mergedRowsList = new ArrayList<String>(mergedRowsSet);
        mergedRowsList.sort(new Comparator<String>(){

            @Override
            public int compare(String o1, String o2) {
                return periodProvider.comparePeriod(o1, o2);
            }
        });
        ArrayList<SchemePeriodObj> mergedSchemePeriodObjs = new ArrayList<SchemePeriodObj>();
        FormSchemeLifeObj onlyStartAndEnd = new FormSchemeLifeObj();
        for (int i = 0; i < mergedRowsList.size(); ++i) {
            onlyStartAndEnd.setFromPeriod((String)mergedRowsList.get(i));
            for (int j = i; j < mergedRowsList.size() - 1; ++j) {
                if (periodProvider.priorPeriod((String)mergedRowsList.get(j + 1)).equals(mergedRowsList.get(j))) {
                    if (j + 1 != mergedRowsList.size() - 1) continue;
                    onlyStartAndEnd.setToPeriod((String)mergedRowsList.get(j + 1));
                    i = j + 1;
                    break;
                }
                onlyStartAndEnd.setToPeriod((String)mergedRowsList.get(j));
                i = j;
                break;
            }
            if (i == mergedRowsList.size() - 1) {
                onlyStartAndEnd.setToPeriod((String)mergedRowsList.get(i));
            }
            mergedSchemePeriodObjs.add(new SchemePeriodObj(params.getFormSchemeKey(), onlyStartAndEnd.getFromPeriod(), onlyStartAndEnd.getToPeriod()));
        }
        Map<String, DesignFormSchemeDefine> schemeMap = this.iDesignTimeViewController.queryFormSchemeByTask(params.getTaskKey()).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
        ArrayList<FormSchemeLifeObj> mergedFormSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>();
        for (SchemePeriodObj schemePeriodObj : mergedSchemePeriodObjs) {
            if (PeriodType.CUSTOM.code() == periodView.getPeriodType().code()) {
                FormSchemeLifeObj formSchemeLifeObj = FormSchemeLifeObj.toFormSchemeLifeObj(schemePeriodObj, schemeMap);
                formSchemeLifeObj.setFromPeriodTitle(periodProvider.getPeriodTitle(formSchemeLifeObj.getFromPeriod()));
                formSchemeLifeObj.setToPeriodTitle(periodProvider.getPeriodTitle(formSchemeLifeObj.getToPeriod()));
                mergedFormSchemeLifeObjs.add(formSchemeLifeObj);
                continue;
            }
            mergedFormSchemeLifeObjs.add(FormSchemeLifeObj.toFormSchemeLifeObj(schemePeriodObj, schemeMap));
        }
        ArrayList<FormSchemeLifeObj> arrayList = new ArrayList<FormSchemeLifeObj>(params.getFormSchemeLifeObjs());
        arrayList.removeIf(o -> o.getKey().equals(params.getFormSchemeKey()));
        arrayList.addAll(mergedFormSchemeLifeObjs);
        arrayList.sort(new Comparator<FormSchemeLifeObj>(){

            @Override
            public int compare(FormSchemeLifeObj o1, FormSchemeLifeObj o2) {
                return periodProvider.comparePeriod(o1.getFromPeriod(), o2.getFromPeriod());
            }
        });
        return arrayList;
    }

    @ApiOperation(value="\u4fdd\u5b58\u65f6\u6821\u9a8c\u65f6\u671f\u6bb5\u65f6\u95f4\u7684\u51b2\u7a81")
    @RequestMapping(value={"/check-formschemes-life-conflict"}, method={RequestMethod.POST})
    public Set<FormSchemeLifeObj> checkFormSchemeLifeConflict(@RequestBody List<FormSchemeLifeObj> formSchemeLifeObjs) throws JQException {
        try {
            if (formSchemeLifeObjs == null || formSchemeLifeObjs.size() <= 1) {
                return new HashSet<FormSchemeLifeObj>();
            }
            HashSet<FormSchemeLifeObj> confFormSchemeLifeObjs = new HashSet<FormSchemeLifeObj>();
            ArrayList<FormSchemeLifeObj> cloneFormSchemeLifeObjs = new ArrayList<FormSchemeLifeObj>(formSchemeLifeObjs);
            String dateTime = this.saveSchemePeriodHelper.queryDateTimeViewString(((FormSchemeLifeObj)cloneFormSchemeLifeObjs.get(0)).getKey());
            for (FormSchemeLifeObj formSchemeLifeObj : formSchemeLifeObjs) {
                for (FormSchemeLifeObj cloneFormSchemeLifeObj : cloneFormSchemeLifeObjs) {
                    if (formSchemeLifeObj.getRecordKey().equals(cloneFormSchemeLifeObj.getRecordKey())) continue;
                    IPeriodEntity periodView = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
                    IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodView.getKey());
                    int b = periodProvider.comparePeriod(formSchemeLifeObj.getToPeriod(), cloneFormSchemeLifeObj.getFromPeriod());
                    int c = periodProvider.comparePeriod(formSchemeLifeObj.getFromPeriod(), cloneFormSchemeLifeObj.getToPeriod());
                    if (b < 0 || c > 0) continue;
                    confFormSchemeLifeObjs.add(formSchemeLifeObj);
                    confFormSchemeLifeObjs.add(cloneFormSchemeLifeObj);
                }
            }
            return confFormSchemeLifeObjs;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_191, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u524d\u63d0\u793a\u5f53\u524d\u4efb\u52a1\u7684\u7a7a\u4f59\u65f6\u95f4\u6bb5")
    @RequestMapping(value={"/check-formschemes-life-empty"}, method={RequestMethod.POST})
    public List<FormSchemeLifeObj> checkFormSchemeLifeEmpty(@RequestBody FormSchemeLifeSave formSchemeLifeSave) throws JQException {
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formSchemeLifeSave.getTaskKey());
        IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodByViewKey.getKey());
        List periodItems = periodProvider.getPeriodItems();
        String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
        String periodMin = periodCodeRegion[0];
        String periodMax = periodCodeRegion[1];
        String taskFrom = this.iDesignTimeViewController.queryTaskDefine(formSchemeLifeSave.getTaskKey()).getFromPeriod();
        String taskTo = this.iDesignTimeViewController.queryTaskDefine(formSchemeLifeSave.getTaskKey()).getToPeriod();
        if (null == taskFrom) {
            taskFrom = periodMin;
        }
        if (null == taskTo) {
            taskTo = periodMax;
        }
        String minPeriod = taskFrom;
        String maxPeriod = taskTo;
        ArrayList<FormSchemeLifeObj> taskEmptyPeriods = new ArrayList<FormSchemeLifeObj>();
        if (formSchemeLifeSave.getFormSchemeLifeObjs().length == 0) {
            if (minPeriod != null && maxPeriod != null) {
                taskEmptyPeriods.add(new FormSchemeLifeObj(minPeriod, maxPeriod));
                return taskEmptyPeriods;
            }
            taskEmptyPeriods.add(new FormSchemeLifeObj(periodMin, periodMax));
            return taskEmptyPeriods;
        }
        ArrayList<SchemePeriodObj> schemeLifeObjList = new ArrayList<SchemePeriodObj>();
        for (FormSchemeLifeObj formSchemeLifeObj : formSchemeLifeSave.getFormSchemeLifeObjs()) {
            schemeLifeObjList.add(new SchemePeriodObj(formSchemeLifeObj.getKey(), formSchemeLifeObj.getFromPeriod(), formSchemeLifeObj.getToPeriod()));
        }
        ArrayList<String> periodRows = new ArrayList<String>();
        for (IPeriodRow periodItem : periodItems) {
            periodRows.add(periodItem.getCode());
        }
        if (minPeriod != null && maxPeriod != null) {
            periodRows.removeIf(o -> periodProvider.comparePeriod(o, minPeriod) < 0);
            periodRows.removeIf(o -> periodProvider.comparePeriod(o, maxPeriod) > 0);
        }
        for (SchemePeriodObj schemePeriodObj : schemeLifeObjList) {
            List<IPeriodRow> toRemove = this.saveSchemePeriodHelper.splitPeriod(schemePeriodObj, iPeriodByViewKey.getKey());
            ArrayList<String> toRemoveRows = new ArrayList<String>();
            for (IPeriodRow iPeriodRow : toRemove) {
                toRemoveRows.add(iPeriodRow.getCode());
            }
            periodRows.removeAll(toRemoveRows);
        }
        FormSchemeLifeObj formSchemeLifeObj = new FormSchemeLifeObj();
        for (int i = 0; i < periodRows.size(); ++i) {
            formSchemeLifeObj.setFromPeriod((String)periodRows.get(i));
            for (int j = i; j < periodRows.size() - 1; ++j) {
                if (periodProvider.priorPeriod((String)periodRows.get(j + 1)).equals(periodRows.get(j))) {
                    if (j + 1 != periodRows.size() - 1) continue;
                    formSchemeLifeObj.setToPeriod((String)periodRows.get(j + 1));
                    i = j + 1;
                    break;
                }
                formSchemeLifeObj.setToPeriod((String)periodRows.get(j));
                i = j;
                break;
            }
            if (i == periodRows.size() - 1) {
                formSchemeLifeObj.setToPeriod((String)periodRows.get(i));
            }
            if (PeriodType.CUSTOM.code() == iPeriodByViewKey.getPeriodType().code()) {
                FormSchemeLifeObj obj = new FormSchemeLifeObj(formSchemeLifeObj.getFromPeriod(), formSchemeLifeObj.getToPeriod());
                obj.setFromPeriodTitle(periodProvider.getPeriodTitle(formSchemeLifeObj.getFromPeriod()));
                obj.setToPeriodTitle(periodProvider.getPeriodTitle(formSchemeLifeObj.getToPeriod()));
                taskEmptyPeriods.add(obj);
                continue;
            }
            taskEmptyPeriods.add(new FormSchemeLifeObj(formSchemeLifeObj.getFromPeriod(), formSchemeLifeObj.getToPeriod()));
        }
        return taskEmptyPeriods;
    }

    @ApiOperation(value="\u6821\u9a8c\u540e\u4fdd\u5b58\u5168\u90e8\u62a5\u8868\u65b9\u6848\u5173\u8054\u751f\u6548\u65f6\u671f")
    @RequestMapping(value={"/save-formschemes-life"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public void saveFormSchemesLife(@RequestBody FormSchemeLifeSave formSchemeLifeSave) throws JQException {
        ArrayList<SchemePeriodObj> schemePeriodObjs = new ArrayList<SchemePeriodObj>(formSchemeLifeSave.getFormSchemeLifeObjs().length);
        if (formSchemeLifeSave.getFormSchemeLifeObjs() != null && formSchemeLifeSave.getFormSchemeLifeObjs().length != 0) {
            for (FormSchemeLifeObj obj : formSchemeLifeSave.getFormSchemeLifeObjs()) {
                SchemePeriodObj schemePeriodObj = new SchemePeriodObj();
                schemePeriodObj.setScheme(obj.getKey());
                schemePeriodObj.setStart(obj.getFromPeriod());
                schemePeriodObj.setEnd(obj.getToPeriod());
                schemePeriodObjs.add(schemePeriodObj);
            }
        } else {
            try {
                this.iDesignTimeViewController.deleteSchemePeriodLinkByTask(formSchemeLifeSave.getTaskKey());
                this.logger.info("\u4fdd\u5b58\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error("\u4fdd\u5b58\u5931\u8d25");
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_186, (Throwable)e);
            }
        }
        try {
            this.saveSchemePeriodHelper.saveSchemePeriodLinks(schemePeriodObjs);
            this.logger.info("\u4fdd\u5b58\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error("\u4fdd\u5b58\u5931\u8d25");
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_186, (Throwable)e);
        }
    }
}

