/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink
 *  com.jiuqi.nr.period.cache.ClearCache
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.modal.impl.PeriodDefineImpl
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.nr.period.service.PeriodService
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import com.jiuqi.nr.period.cache.ClearCache;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.util.ISingleSchemePeriodUtil;
import nr.single.para.parain.util.SingleSchemePeriodObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class SingleSchemePeriodUtilImpl
implements ISingleSchemePeriodUtil {
    private static final Logger log = LoggerFactory.getLogger(SingleSchemePeriodUtilImpl.class);
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDateService;
    @Autowired
    private ClearCache clearCache;

    @Override
    public void saveSchemePeriodLinks(List<SingleSchemePeriodObj> objs, String dateEntityId) throws Exception {
        if (null != objs && objs.size() != 0) {
            IPeriodEntity iPeriod;
            DesignFormSchemeDefine formscheme = this.iDesignTimeViewController.queryFormSchemeDefine(objs.get(0).getScheme());
            if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)dateEntityId) && (iPeriod = this.schemeSearchEntity(formscheme)) != null) {
                dateEntityId = iPeriod.getKey();
            }
            ArrayList allData = new ArrayList();
            for (SingleSchemePeriodObj obj : objs) {
                if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)obj.getStart()) || com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)obj.getEnd())) {
                    ArrayList<DesignSchemePeriodLink> createlinks = new ArrayList<DesignSchemePeriodLink>();
                    DesignSchemePeriodLink def = new DesignSchemePeriodLink();
                    def.setSchemeKey(obj.getScheme());
                    def.setPeriodKey(null);
                    createlinks.add(def);
                    allData.addAll(createlinks);
                    continue;
                }
                List<IPeriodRow> splitPeriod = this.splitPeriod(obj, dateEntityId);
                List<DesignSchemePeriodLinkDefine> createlinks = this.createlinks(obj.getScheme(), splitPeriod);
                allData.addAll(createlinks);
            }
            try {
                if (allData.size() > 0) {
                    this.iDesignTimeViewController.deleteSchemePeriodLinkByScheme(formscheme.getKey());
                    this.iDesignTimeViewController.inserSchemePeriodLink(allData);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new Exception("\u4fdd\u5b58\u6309\u65b9\u6848\u65f6\u671f\u6620\u5c04\u51fa\u9519");
            }
        }
    }

    public void saveSchemePeriodLinkDefines(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        if (null != defines && defines.size() != 0) {
            try {
                this.iDesignTimeViewController.deleteSchemePeriodLinkByScheme(defines.get(0).getSchemeKey());
                this.iDesignTimeViewController.inserSchemePeriodLink(defines);
            }
            catch (Exception e) {
                throw new Exception("\u4fdd\u5b58\u6309\u65b9\u6848\u65f6\u671f\u6620\u5c04\u51fa\u9519");
            }
        }
    }

    public DesignSchemePeriodLinkDefine querySchemeByPeriodAndTask(String period, String task) throws Exception {
        DesignSchemePeriodLinkDefine define = null;
        try {
            define = this.iDesignTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, task);
        }
        catch (Exception e) {
            throw new Exception("\u67e5\u8a62\u6309\u65b9\u6848\u65f6\u671f\u6620\u5c04\u51fa\u9519:" + e.getMessage());
        }
        return define;
    }

    private IPeriodEntity schemeSearchEntity(DesignFormSchemeDefine formscheme) {
        String dateTime = this.iDesignTimeViewController.queryTaskDefine(formscheme.getTaskKey()).getDateTime();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity iPeriod = periodAdapter.getPeriodEntity(dateTime);
        return iPeriod;
    }

    private String queryDateTimeViewString(String formSchemeKey) throws JQException {
        DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return taskDefine.getDateTime();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void createSchemePeriodLink(TaskImportContext importContext, final String scheme, final String fromPeriod, final String toPeriod, String dateEntityId) throws Exception {
        try {
            String minPeriod = fromPeriod;
            String maxPeriod = toPeriod;
            ArrayList<Object> periodList = new ArrayList<Object>();
            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)toPeriod)) {
                this.autoExtensionDefaultPeriod(fromPeriod, toPeriod, dateEntityId);
                periodList.add(fromPeriod);
                if (fromPeriod.compareTo(toPeriod) < 0) {
                    Object curPeriod = fromPeriod;
                    Object nextPeroid = this.periodAdapter.getPeriodProvider(dateEntityId).nextPeriod((String)curPeriod);
                    while (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)nextPeroid) && !toPeriod.equalsIgnoreCase((String)nextPeroid)) {
                        periodList.add(nextPeroid);
                        curPeriod = nextPeroid;
                        nextPeroid = this.periodAdapter.getPeriodProvider(dateEntityId).nextPeriod((String)curPeriod);
                        if (((String)nextPeroid).compareTo(toPeriod) == 0) {
                            periodList.add(nextPeroid);
                        } else if (((String)nextPeroid).compareTo(toPeriod) > 0) break;
                        if (!"1900N0001".equalsIgnoreCase((String)nextPeroid)) continue;
                    }
                    maxPeriod = toPeriod;
                }
            } else if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod)) {
                periodList.add(fromPeriod);
            } else if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)toPeriod)) {
                periodList.add(toPeriod);
            }
            HashSet<String> curPeiodSet = new HashSet<String>();
            for (String string : periodList) {
                curPeiodSet.add(string);
            }
            DesignFormSchemeDefine formscheme = importContext.getSchemeInfoCache().getFormScheme();
            if (formscheme == null) {
                formscheme = this.iDesignTimeViewController.queryFormSchemeDefine(scheme);
            }
            if (formscheme != null) {
                void var11_15;
                DesignTaskDefine designTaskDefine = importContext.getTaskDefine();
                if (designTaskDefine == null) {
                    DesignTaskDefine designTaskDefine2 = this.iDesignTimeViewController.queryTaskDefine(formscheme.getTaskKey());
                    importContext.setTaskDefine(designTaskDefine2);
                }
                List oldSchemePeriod = this.iDesignTimeViewController.querySchemePeriodLinkByScheme(scheme);
                this.iDesignTimeViewController.deleteSchemePeriodLinkByScheme(scheme);
                List taskPeriodLinks = this.iDesignTimeViewController.querySchemePeriodLinkByTask(formscheme.getTaskKey());
                ArrayList<DesignSchemePeriodLinkDefine> formSchemePeriodLinks = new ArrayList<DesignSchemePeriodLinkDefine>();
                HashMap<String, DesignSchemePeriodLinkDefine> taskPeriodLinkMap = new HashMap<String, DesignSchemePeriodLinkDefine>();
                HashMap<String, DesignSchemePeriodLinkDefine> otherPeriodLinkMap = new HashMap<String, DesignSchemePeriodLinkDefine>();
                HashMap<String, DesignSchemePeriodLinkDefine> formSchemePeriodLinkMap = new HashMap<String, DesignSchemePeriodLinkDefine>();
                if (taskPeriodLinks != null && taskPeriodLinks.size() > 0) {
                    for (DesignSchemePeriodLinkDefine periodLink : taskPeriodLinks) {
                        taskPeriodLinkMap.put(periodLink.getPeriodKey(), periodLink);
                        if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)periodLink.getSchemeKey()) && periodLink.getSchemeKey().equalsIgnoreCase(scheme)) {
                            formSchemePeriodLinks.add(periodLink);
                            formSchemePeriodLinkMap.put(periodLink.getPeriodKey(), periodLink);
                        } else {
                            otherPeriodLinkMap.put(periodLink.getPeriodKey(), periodLink);
                        }
                        if (!com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)periodLink.getPeriodKey())) continue;
                        if (minPeriod.compareTo(periodLink.getPeriodKey()) > 0) {
                            minPeriod = periodLink.getPeriodKey();
                        }
                        if (maxPeriod.compareTo(periodLink.getPeriodKey()) >= 0) continue;
                        maxPeriod = periodLink.getPeriodKey();
                    }
                }
                if (periodList.size() > 0) {
                    HashMap<String, DesignSchemePeriodLink> newSchemePeriods = new HashMap<String, DesignSchemePeriodLink>();
                    ArrayList<DesignSchemePeriodLink> addList = new ArrayList<DesignSchemePeriodLink>();
                    for (String string : periodList) {
                        if (!taskPeriodLinkMap.containsKey(string)) {
                            DesignSchemePeriodLink obj = new DesignSchemePeriodLink();
                            obj.setSchemeKey(scheme);
                            obj.setPeriodKey(string);
                            addList.add(obj);
                            newSchemePeriods.put(obj.getPeriodKey(), obj);
                            continue;
                        }
                        DesignSchemePeriodLinkDefine oldObj = (DesignSchemePeriodLinkDefine)taskPeriodLinkMap.get(string);
                        if (!scheme.equalsIgnoreCase(oldObj.getSchemeKey())) continue;
                        DesignSchemePeriodLink obj = new DesignSchemePeriodLink();
                        obj.setSchemeKey(scheme);
                        obj.setPeriodKey(string);
                        addList.add(obj);
                        newSchemePeriods.put(obj.getPeriodKey(), obj);
                    }
                    String oldSchemeMinPeriod = "";
                    String string = "";
                    if (oldSchemePeriod != null && !oldSchemePeriod.isEmpty()) {
                        DesignSchemePeriodLink obj;
                        void var21_29;
                        String curPeriod;
                        oldSchemeMinPeriod = ((DesignSchemePeriodLinkDefine)oldSchemePeriod.get(0)).getPeriodKey();
                        String string2 = ((DesignSchemePeriodLinkDefine)oldSchemePeriod.get(oldSchemePeriod.size() - 1)).getPeriodKey();
                        for (DesignSchemePeriodLinkDefine periodLink : oldSchemePeriod) {
                            curPeriod = periodLink.getPeriodKey();
                            formSchemePeriodLinks.add(periodLink);
                            formSchemePeriodLinkMap.put(periodLink.getPeriodKey(), periodLink);
                            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)periodLink.getPeriodKey())) {
                                if (minPeriod.compareTo(periodLink.getPeriodKey()) > 0) {
                                    minPeriod = periodLink.getPeriodKey();
                                }
                                if (maxPeriod.compareTo(periodLink.getPeriodKey()) < 0) {
                                    maxPeriod = periodLink.getPeriodKey();
                                }
                                if (oldSchemeMinPeriod.compareTo(periodLink.getPeriodKey()) > 0) {
                                    oldSchemeMinPeriod = periodLink.getPeriodKey();
                                }
                                if (var21_29.compareTo(periodLink.getPeriodKey()) < 0) {
                                    String string3 = periodLink.getPeriodKey();
                                }
                            }
                            if (newSchemePeriods.containsKey(curPeriod) || taskPeriodLinkMap.containsKey(curPeriod)) continue;
                            if (!taskPeriodLinkMap.containsKey(curPeriod)) {
                                DesignSchemePeriodLink obj2 = new DesignSchemePeriodLink();
                                obj2.setSchemeKey(scheme);
                                obj2.setPeriodKey(curPeriod);
                                addList.add(obj2);
                                newSchemePeriods.put(obj2.getPeriodKey(), obj2);
                                continue;
                            }
                            DesignSchemePeriodLinkDefine oldObj = (DesignSchemePeriodLinkDefine)taskPeriodLinkMap.get(curPeriod);
                            if (!scheme.equalsIgnoreCase(oldObj.getSchemeKey())) continue;
                            obj = new DesignSchemePeriodLink();
                            obj.setSchemeKey(scheme);
                            obj.setPeriodKey(curPeriod);
                            addList.add(obj);
                            newSchemePeriods.put(obj.getPeriodKey(), obj);
                        }
                        if (taskPeriodLinkMap.isEmpty()) {
                            String startPeriod = "";
                            String endPeriod = "";
                            if (oldSchemeMinPeriod.compareTo(toPeriod) > 0) {
                                startPeriod = toPeriod;
                                endPeriod = oldSchemeMinPeriod;
                            } else if (var21_29.compareTo(fromPeriod) < 0) {
                                startPeriod = var21_29;
                                endPeriod = fromPeriod;
                            }
                            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)startPeriod) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)endPeriod)) {
                                curPeriod = startPeriod;
                                String nextPeroid = this.periodAdapter.getPeriodProvider(dateEntityId).nextPeriod(curPeriod);
                                while (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)nextPeroid) && !endPeriod.equalsIgnoreCase(nextPeroid)) {
                                    curPeriod = nextPeroid;
                                    if (!newSchemePeriods.containsKey(curPeriod) && !taskPeriodLinkMap.containsKey(curPeriod)) {
                                        obj = new DesignSchemePeriodLink();
                                        obj.setSchemeKey(scheme);
                                        obj.setPeriodKey(curPeriod);
                                        addList.add(obj);
                                        newSchemePeriods.put(obj.getPeriodKey(), obj);
                                    }
                                    if ((nextPeroid = this.periodAdapter.getPeriodProvider(dateEntityId).nextPeriod(curPeriod)).compareTo(endPeriod) <= 0 && !"1900N0001".equalsIgnoreCase(nextPeroid)) continue;
                                }
                            }
                        }
                    }
                    if (!addList.isEmpty()) {
                        this.iDesignTimeViewController.inserSchemePeriodLink(addList);
                    }
                } else {
                    if (!formSchemePeriodLinkMap.isEmpty()) {
                        return;
                    }
                    DesignSchemePeriodLinkDefine curperiodLink1 = (DesignSchemePeriodLinkDefine)taskPeriodLinkMap.get(fromPeriod);
                    if (curperiodLink1 != null) {
                        return;
                    }
                    DesignSchemePeriodLinkDefine curperiodLink2 = (DesignSchemePeriodLinkDefine)taskPeriodLinkMap.get(toPeriod);
                    if (curperiodLink2 != null) {
                        return;
                    }
                    if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)fromPeriod) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)toPeriod)) {
                        this.saveSchemePeriodLinks((List<SingleSchemePeriodObj>)new ArrayList<SingleSchemePeriodObj>(){
                            {
                                SingleSchemePeriodObj obj = new SingleSchemePeriodObj();
                                obj.setScheme(scheme);
                                obj.setStart(fromPeriod);
                                obj.setEnd(toPeriod);
                                this.add(obj);
                            }
                        }, dateEntityId);
                    } else {
                        this.iDesignTimeViewController.deleteSchemePeriodLinkByScheme(scheme);
                        this.iDesignTimeViewController.inserSchemePeriodLink((List)new ArrayList<DesignSchemePeriodLinkDefine>(){
                            {
                                DesignSchemePeriodLink obj = new DesignSchemePeriodLink();
                                obj.setSchemeKey(scheme);
                                obj.setPeriodKey(null);
                                this.add(obj);
                            }
                        });
                    }
                }
                boolean changeTaskPeriod = false;
                if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)var11_15.getFromPeriod()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)minPeriod) && !minPeriod.equalsIgnoreCase(var11_15.getFromPeriod())) {
                    var11_15.setFromPeriod(minPeriod);
                    changeTaskPeriod = true;
                }
                if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)var11_15.getToPeriod()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)maxPeriod) && !maxPeriod.equalsIgnoreCase(var11_15.getToPeriod())) {
                    var11_15.setToPeriod(maxPeriod);
                    changeTaskPeriod = true;
                }
                if (changeTaskPeriod) {
                    this.iDesignTimeViewController.updateTaskDefine((DesignTaskDefine)var11_15);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("\u521b\u5efa\u6309\u65b9\u6848\u65f6\u671f\u6620\u5c04\u51fa\u9519:" + e.getMessage());
        }
    }

    public List<String> queryEffectivePeriods(String schemeKey) throws Exception {
        ArrayList<String> effectivePeriods = new ArrayList<String>();
        List<SingleSchemePeriodObj> queryLinksByScheme = this.queryLinksByScheme(schemeKey);
        if (null != queryLinksByScheme && !queryLinksByScheme.isEmpty()) {
            for (SingleSchemePeriodObj SingleSchemePeriodObj2 : queryLinksByScheme) {
                if (!StringUtils.hasLength(SingleSchemePeriodObj2.getStart()) || !StringUtils.hasLength(SingleSchemePeriodObj2.getEnd())) continue;
                effectivePeriods.add(SingleSchemePeriodObj2.getStart() + "-" + SingleSchemePeriodObj2.getEnd());
            }
        }
        return effectivePeriods;
    }

    public List<SingleSchemePeriodObj> queryLinksByScheme(String scheme) throws Exception {
        ArrayList<SingleSchemePeriodObj> objs = new ArrayList<SingleSchemePeriodObj>();
        try {
            List links = this.iDesignTimeViewController.querySchemePeriodLinkByScheme(scheme);
            if (null != links) {
                DesignFormSchemeDefine formscheme = this.iDesignTimeViewController.queryFormSchemeDefine(scheme);
                IPeriodEntity iPeriod = this.schemeSearchEntity(formscheme);
                objs.addAll(this.unSplitPeriod(links, iPeriod.getKey()));
            }
        }
        catch (Exception e) {
            throw new Exception("\u67e5\u8be2\u6309\u8868\u5355\u65f6\u671f\u6620\u5c04\u51fa\u9519:" + e.getMessage());
        }
        return objs;
    }

    public List<SingleSchemePeriodObj> queryLinksByTask(String task) throws Exception {
        List schemes = this.iDesignTimeViewController.queryFormSchemeByTask(task);
        ArrayList<SingleSchemePeriodObj> list = new ArrayList<SingleSchemePeriodObj>();
        for (DesignFormSchemeDefine define : schemes) {
            List<SingleSchemePeriodObj> queryLinksByScheme = this.queryLinksByScheme(define.getKey());
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

    private List<IPeriodRow> splitPeriod(String entityKey, String paragraph) {
        String[] split;
        ArrayList<IPeriodRow> list = new ArrayList<IPeriodRow>();
        if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)entityKey) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)paragraph) && paragraph.indexOf("-") != -1 && (split = paragraph.split("-")).length == 2 && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)split[0]) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)split[1])) {
            List items = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
            List collect = items.stream().filter(s -> null != s.getStartDate() && null != s.getEndDate()).collect(Collectors.toList());
            collect = collect.stream().sorted(Comparator.comparing(IPeriodRow::getStartDate)).collect(Collectors.toList());
            boolean isStart = false;
            for (IPeriodRow row : collect) {
                if (row.getCode().equals(split[0])) {
                    isStart = true;
                }
                if (isStart) {
                    list.add(row);
                }
                if (!row.getCode().equals(split[1])) continue;
                isStart = false;
            }
        }
        return list;
    }

    public List<SingleSchemePeriodObj> unSplitPeriod(List<DesignSchemePeriodLinkDefine> list, String entityKey) {
        ArrayList<SingleSchemePeriodObj> objs = new ArrayList<SingleSchemePeriodObj>();
        if (null == list || list.size() == 0) {
            return objs;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (DesignSchemePeriodLinkDefine def : list) {
            if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)def.getPeriodKey())) {
                SingleSchemePeriodObj objn = new SingleSchemePeriodObj();
                objn.setScheme(def.getSchemeKey());
                objn.setStart(null);
                objn.setEnd(null);
                objs.add(objn);
                continue;
            }
            map.put(def.getPeriodKey(), def.getPeriodKey());
        }
        List items = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
        List collect = items.stream().filter(s -> null != s.getStartDate() && null != s.getEndDate()).collect(Collectors.toList());
        collect = collect.stream().sorted(Comparator.comparing(IPeriodRow::getStartDate)).collect(Collectors.toList());
        String scheme = list.get(0).getSchemeKey();
        boolean islx = false;
        SingleSchemePeriodObj obj = new SingleSchemePeriodObj();
        for (int i = 0; i < collect.size(); ++i) {
            IPeriodRow row = (IPeriodRow)collect.get(i);
            if (row.getCode().equals(map.get(row.getCode()))) {
                if (islx) {
                    obj.setEnd(row.getCode());
                    continue;
                }
                if (i == collect.size() - 1) {
                    obj = new SingleSchemePeriodObj();
                    objs.add(obj);
                    obj.setScheme(scheme);
                    obj.setStart(row.getCode());
                    obj.setEnd(row.getCode());
                    continue;
                }
                islx = true;
                obj = new SingleSchemePeriodObj();
                objs.add(obj);
                obj.setScheme(scheme);
                obj.setStart(row.getCode());
                continue;
            }
            islx = false;
            if (!com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getScheme()) || i == 0) continue;
            obj.setEnd(((IPeriodRow)collect.get(i - 1)).getCode());
            obj = new SingleSchemePeriodObj();
        }
        return objs;
    }

    public List<IPeriodRow> splitPeriod(SingleSchemePeriodObj obj, String entityKey) {
        ArrayList<IPeriodRow> list = new ArrayList<IPeriodRow>();
        if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getScheme()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getStart()) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)obj.getEnd())) {
            List items = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityKey).getPeriodItems();
            List collect = items.stream().filter(s -> null != s.getStartDate() && null != s.getEndDate()).collect(Collectors.toList());
            collect = collect.stream().sorted(Comparator.comparing(IPeriodRow::getStartDate)).collect(Collectors.toList());
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

    private void autoExtensionDefaultPeriod(String startPeriod, String endPeriod, String dateEntityId) {
        try {
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(dateEntityId);
            String[] defaultPeriods = this.getDefaultPeriodRanges(periodEntity.getCode());
            boolean isNeedChange = false;
            String changeStartPeriod = defaultPeriods[0];
            String changeToPeriod = defaultPeriods[1];
            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)changeStartPeriod) && changeStartPeriod.compareTo(startPeriod) > 0) {
                changeStartPeriod = startPeriod;
                isNeedChange = true;
            }
            if (com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)changeToPeriod) && changeToPeriod.compareTo(endPeriod) < 0) {
                changeToPeriod = endPeriod;
                isNeedChange = true;
            }
            if (isNeedChange && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)changeStartPeriod) && com.jiuqi.nr.period.common.utils.StringUtils.isNotEmpty((String)changeToPeriod)) {
                this.extensionDefaultPeriod(periodEntity.getCode(), periodEntity.getCode(), changeStartPeriod, changeToPeriod);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void extensionDefaultPeriod(String periodEntityCode, String periodEntityType, String startPeriodCode, String endPeiodCode) throws Exception {
        PeriodDefineImpl ipe = new PeriodDefineImpl();
        ipe.setCode(periodEntityCode);
        ipe.setType(PeriodUtils.periodOfType((String)periodEntityType));
        this.periodService.extensionDefaultPeriod((IPeriodEntity)ipe, startPeriodCode, endPeiodCode);
        this.clearCache.clearCache();
    }

    private String[] getDefaultPeriodRanges(String periodEntityCode) throws Exception {
        String[] periods = new String[2];
        List periodDataDefines = this.periodDateService.queryPeriodDataByPeriodCodeLanguage(periodEntityCode, this.getLanguage());
        periods[0] = PeriodUtils.minDefine((List)periodDataDefines).getCode();
        periods[1] = PeriodUtils.maxDefine((List)periodDataDefines).getCode();
        return periods;
    }

    private String getLanguage() {
        return NpContextHolder.getContext().getLocale().getLanguage();
    }
}

