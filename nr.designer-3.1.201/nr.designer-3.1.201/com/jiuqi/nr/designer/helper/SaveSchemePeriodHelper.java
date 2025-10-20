/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.util.PeriodRangeSortUtils;
import com.jiuqi.nr.designer.util.SchemePeriodObj;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SaveSchemePeriodHelper {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;

    public void saveSchemePeriodLinks(List<SchemePeriodObj> objs) throws JQException {
        if (null != objs && objs.size() != 0) {
            DesignFormSchemeDefine formscheme = this.iDesignTimeViewController.queryFormSchemeDefine(objs.get(0).getScheme());
            IPeriodEntity iPeriod = this.schemeSearchEntity(formscheme);
            ArrayList allData = new ArrayList();
            for (SchemePeriodObj obj : objs) {
                if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)obj.getStart()) || com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)obj.getEnd())) {
                    ArrayList<DesignSchemePeriodLink> createlinks = new ArrayList<DesignSchemePeriodLink>();
                    DesignSchemePeriodLink def = new DesignSchemePeriodLink();
                    def.setSchemeKey(obj.getScheme());
                    def.setPeriodKey(null);
                    createlinks.add(def);
                    allData.addAll(createlinks);
                    continue;
                }
                List<IPeriodRow> splitPeriod = this.splitPeriod(obj, iPeriod.getKey());
                List<DesignSchemePeriodLinkDefine> createlinks = this.createlinks(obj.getScheme(), splitPeriod);
                allData.addAll(createlinks);
            }
            try {
                this.iDesignTimeViewController.deleteSchemePeriodLinkByTask(formscheme.getTaskKey());
                this.iDesignTimeViewController.inserSchemePeriodLink(allData);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_185, (Throwable)e);
            }
        }
    }

    public void reMarkSchemePeriodLink(DesignTaskDefine task, String type) throws JQException {
        List<SchemePeriodObj> schemePeriodObjs = this.queryLinksByTask(task.getKey());
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(task.getDateTime());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
        schemePeriodObjs = PeriodRangeSortUtils.resort(task.getFromPeriod(), task.getToPeriod(), schemePeriodObjs, periodProvider);
        String tempTime = null;
        if ("init".equals(type)) {
            this.saveSchemePeriodLinks(schemePeriodObjs);
        } else {
            if (schemePeriodObjs.isEmpty()) {
                List<SchemePeriodObj> queryLinksByTask = this.queryLinksByTask(task.getKey());
                if (queryLinksByTask.size() != 0) {
                    schemePeriodObjs.add(new SchemePeriodObj(this.queryLinksByTask(task.getKey()).get(0).getScheme(), task.getFromPeriod(), task.getToPeriod()));
                }
            } else {
                int i;
                for (i = 0; i < schemePeriodObjs.size() && periodProvider.comparePeriod(schemePeriodObjs.get(i).getStart(), task.getFromPeriod()) < 0; ++i) {
                    tempTime = task.getFromPeriod();
                    if (periodProvider.comparePeriod(tempTime, schemePeriodObjs.get(i).getEnd()) > 0) {
                        schemePeriodObjs.remove(schemePeriodObjs.get(i));
                        --i;
                        continue;
                    }
                    schemePeriodObjs.get(i).setStart(task.getFromPeriod());
                    break;
                }
                for (i = schemePeriodObjs.size() - 1; i >= 0 && periodProvider.comparePeriod(schemePeriodObjs.get(i).getEnd(), task.getToPeriod()) > 0; --i) {
                    tempTime = task.getToPeriod();
                    if (periodProvider.comparePeriod(tempTime, schemePeriodObjs.get(i).getStart()) >= 0) {
                        schemePeriodObjs.get(i).setEnd(task.getToPeriod());
                        break;
                    }
                    schemePeriodObjs.remove(schemePeriodObjs.get(i));
                }
            }
            this.saveSchemePeriodLinks(schemePeriodObjs);
        }
    }

    private IPeriodEntity schemeSearchEntity(DesignFormSchemeDefine formscheme) {
        String dateTime = this.iDesignTimeViewController.queryTaskDefine(formscheme.getTaskKey()).getDateTime();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity iPeriod = periodAdapter.getPeriodEntity(dateTime);
        return iPeriod;
    }

    public String queryDateTimeViewString(String formSchemeKey) throws JQException {
        DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return taskDefine.getDateTime();
    }

    public void createSchemePeriodLink(final String scheme, final String fromPeriod, final String toPeriod) throws JQException {
        try {
            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod) || com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)toPeriod)) {
                String dateTime = this.queryDateTimeViewString(scheme);
                final IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
                if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)toPeriod)) {
                    this.saveSchemePeriodLinks((List<SchemePeriodObj>)new ArrayList<SchemePeriodObj>(){
                        {
                            SchemePeriodObj obj = new SchemePeriodObj();
                            obj.setScheme(scheme);
                            obj.setStart(fromPeriod);
                            obj.setEnd(toPeriod);
                            obj.setEntity(periodEntity.getKey());
                            this.add(obj);
                        }
                    });
                } else if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod)) {
                    final String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dateTime).getPeriodCodeRegion();
                    if (periodCodeRegion[1] != null) {
                        this.saveSchemePeriodLinks((List<SchemePeriodObj>)new ArrayList<SchemePeriodObj>(){
                            {
                                SchemePeriodObj obj = new SchemePeriodObj();
                                obj.setScheme(scheme);
                                obj.setStart(fromPeriod);
                                obj.setEnd(periodCodeRegion[1]);
                                obj.setEntity(periodEntity.getKey());
                                this.add(obj);
                            }
                        });
                    }
                } else {
                    final String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dateTime).getPeriodCodeRegion();
                    if (periodCodeRegion[0] != null) {
                        this.saveSchemePeriodLinks((List<SchemePeriodObj>)new ArrayList<SchemePeriodObj>(){
                            {
                                SchemePeriodObj obj = new SchemePeriodObj();
                                obj.setScheme(scheme);
                                obj.setStart(periodCodeRegion[0]);
                                obj.setEnd(toPeriod);
                                obj.setEntity(periodEntity.getKey());
                                this.add(obj);
                            }
                        });
                    }
                }
            } else {
                this.iDesignTimeViewController.deleteSchemePeriodLinkByScheme(scheme);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_185, (Throwable)e);
        }
    }

    public List<String> queryEffectivePeriods(String schemeKey) throws JQException {
        ArrayList<String> effectivePeriods = new ArrayList<String>();
        List<SchemePeriodObj> queryLinksByScheme = this.queryLinksByScheme(schemeKey);
        String dateTime = this.queryDateTimeViewString(schemeKey);
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime);
        if (null != queryLinksByScheme && !queryLinksByScheme.isEmpty()) {
            for (SchemePeriodObj schemePeriodObj : queryLinksByScheme) {
                if (!StringUtils.hasLength(schemePeriodObj.getStart()) || !StringUtils.hasLength(schemePeriodObj.getEnd())) continue;
                if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code()) {
                    String start = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey()).getPeriodTitle(schemePeriodObj.getStart());
                    String end = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey()).getPeriodTitle(schemePeriodObj.getEnd());
                    effectivePeriods.add(start + "-" + end);
                    continue;
                }
                effectivePeriods.add(schemePeriodObj.getStart() + "-" + schemePeriodObj.getEnd());
            }
        }
        return effectivePeriods;
    }

    public List<SchemePeriodObj> queryLinksByScheme(String scheme) throws JQException {
        ArrayList<SchemePeriodObj> objs = new ArrayList<SchemePeriodObj>();
        try {
            List links = this.iDesignTimeViewController.querySchemePeriodLinkByScheme(scheme);
            if (null != links) {
                DesignFormSchemeDefine formscheme = this.iDesignTimeViewController.queryFormSchemeDefine(scheme);
                IPeriodEntity iPeriod = this.schemeSearchEntity(formscheme);
                objs.addAll(this.unSplitPeriod(links, iPeriod.getKey()));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_185, (Throwable)e);
        }
        return objs;
    }

    public List<SchemePeriodObj> queryLinksByTask(String task) throws JQException {
        List schemes = this.iDesignTimeViewController.queryFormSchemeByTask(task);
        ArrayList<SchemePeriodObj> list = new ArrayList<SchemePeriodObj>();
        for (DesignFormSchemeDefine define : schemes) {
            List<SchemePeriodObj> queryLinksByScheme = this.queryLinksByScheme(define.getKey());
            list.addAll(queryLinksByScheme);
        }
        return list;
    }

    private List<DesignSchemePeriodLinkDefine> createlinks(String scheme, List<IPeriodRow> list) {
        ArrayList<DesignSchemePeriodLinkDefine> defines = new ArrayList<DesignSchemePeriodLinkDefine>();
        if (null != list) {
            for (IPeriodRow row : list) {
                DesignSchemePeriodLink def = new DesignSchemePeriodLink();
                def.setIsdefault(false);
                def.setSchemeKey(scheme);
                def.setPeriodKey(row.getCode());
                defines.add((DesignSchemePeriodLinkDefine)def);
            }
        }
        return defines;
    }

    public List<SchemePeriodObj> unSplitPeriod(List<DesignSchemePeriodLinkDefine> list, String entityKey) {
        ArrayList<SchemePeriodObj> objs = new ArrayList<SchemePeriodObj>();
        if (null == list || list.size() == 0) {
            return objs;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (DesignSchemePeriodLinkDefine def : list) {
            if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)def.getPeriodKey())) {
                SchemePeriodObj objn = new SchemePeriodObj();
                objn.setScheme(def.getSchemeKey());
                objn.setStart(null);
                objn.setEnd(null);
                objs.add(objn);
                continue;
            }
            map.put(def.getPeriodKey(), def.getPeriodKey());
        }
        List collect = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
        String scheme = list.get(0).getSchemeKey();
        boolean islx = false;
        SchemePeriodObj obj = new SchemePeriodObj();
        for (int i = 0; i < collect.size(); ++i) {
            IPeriodRow row = (IPeriodRow)collect.get(i);
            if (row.getCode().equals(map.get(row.getCode()))) {
                if (islx) {
                    obj.setEnd(row.getCode());
                    continue;
                }
                if (i == collect.size() - 1) {
                    obj = new SchemePeriodObj();
                    objs.add(obj);
                    obj.setScheme(scheme);
                    obj.setStart(row.getCode());
                    obj.setEnd(row.getCode());
                    continue;
                }
                islx = true;
                obj = new SchemePeriodObj();
                objs.add(obj);
                obj.setScheme(scheme);
                obj.setStart(row.getCode());
                continue;
            }
            islx = false;
            if (!com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getScheme()) || i == 0) continue;
            obj.setEnd(((IPeriodRow)collect.get(i - 1)).getCode());
            obj = new SchemePeriodObj();
        }
        return objs;
    }

    public List<IPeriodRow> splitPeriod(SchemePeriodObj obj, String entityKey) {
        ArrayList<IPeriodRow> list = new ArrayList<IPeriodRow>();
        if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getScheme()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getStart()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getEnd())) {
            List collect = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
            boolean isStart = false;
            for (IPeriodRow row : collect) {
                if (row.getCode().equals(obj.getStart())) {
                    isStart = true;
                }
                if (isStart) {
                    list.add(row);
                }
                if (!row.getCode().equals(obj.getEnd())) continue;
                isStart = false;
            }
        }
        return list;
    }
}

