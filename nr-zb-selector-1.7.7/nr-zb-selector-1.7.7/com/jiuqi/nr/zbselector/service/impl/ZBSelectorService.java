/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.query.service.impl.QueryDimensionHelper
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbselector.service.impl;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nr.zbselector.bean.TaskNodeType;
import com.jiuqi.nr.zbselector.bean.TaskTreeNode;
import com.jiuqi.nr.zbselector.bean.impl.TaskLinkImpl;
import com.jiuqi.nr.zbselector.service.IZBSelectorService;
import com.jiuqi.nr.zbselector.utils.ZBSelectorConvert;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ZBSelectorService
implements IZBSelectorService {
    private static final Logger logger = LoggerFactory.getLogger(ZBSelectorService.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private QueryDimensionHelper dimensionHelper;

    @Override
    public List<ITree<TaskTreeNode>> getRootTaskTreeNodes(boolean complete) {
        if (!complete) {
            return this.getChildTaskTreeNodes(null);
        }
        List<ITree<TaskTreeNode>> childTaskTreeNodes = this.getChildTaskTreeNodes(null);
        this.getChildTaskTreeNodes4Recursion(childTaskTreeNodes);
        return childTaskTreeNodes;
    }

    @Override
    public List<ITree<TaskTreeNode>> getChildTaskTreeNodes(String taskGroupKey) {
        ArrayList<ITree<TaskTreeNode>> iTrees = new ArrayList<ITree<TaskTreeNode>>();
        List groupITrees = this.getChildTaskGroups(taskGroupKey).stream().map(ZBSelectorConvert::TGD2TTN).map(ZBSelectorConvert::TTN2IT).collect(Collectors.toList());
        iTrees.addAll(groupITrees);
        List taskITrees = this.getChildTasks(taskGroupKey).stream().map(taskDefine -> ZBSelectorConvert.TD2TTN(taskDefine, null)).map(ZBSelectorConvert::TTN2IT).collect(Collectors.toList());
        iTrees.addAll(taskITrees);
        return iTrees;
    }

    @Override
    public String getPeriodObjByTask(String taskKey, String period) {
        try {
            if (StringUtils.hasLength(taskKey) && !taskKey.equals("undefined")) {
                SchemePeriodLinkDefine periodLinkDefine;
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
                JSONObject json = new JSONObject();
                if (!StringUtils.hasLength(period) || period.equals("undefined")) {
                    String toPeriod;
                    String fromPeriod = taskDefine.getFromPeriod();
                    if (!StringUtils.hasLength(fromPeriod)) {
                        fromPeriod = periodProvider.getPeriodCodeRegion()[0];
                    }
                    if (!StringUtils.hasLength(toPeriod = taskDefine.getToPeriod())) {
                        String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
                        toPeriod = periodCodeRegion[periodCodeRegion.length - 1];
                    }
                    json.put("begin", (Object)fromPeriod);
                    json.put("end", (Object)toPeriod);
                    if (taskDefine.getPeriodType() != PeriodType.CUSTOM) {
                        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)taskDefine.getTaskPeriodOffset());
                        period = currentPeriod.toString();
                        json.put("curr", (Object)currentPeriod.toString());
                        json.put("currTitle", (Object)periodProvider.getPeriodTitle(currentPeriod));
                    } else {
                        IPeriodRow curPeriod = periodProvider.getCurPeriod();
                        period = curPeriod.getCode();
                        json.put("curr", (Object)curPeriod.getCode());
                        json.put("currTitle", (Object)curPeriod.getTitle());
                        String periodList = this.dimensionHelper.getPeriodListByMasterKey(taskDefine.getDateTime());
                        json.put("customArr", (Object)periodList);
                    }
                }
                if (ObjectUtils.isEmpty(periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey))) {
                    List schemePeriodLinkDefines = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
                    if (!CollectionUtils.isEmpty(schemePeriodLinkDefines)) {
                        json.put("curr", (Object)((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey());
                        json.put("currTitle", (Object)periodProvider.getPeriodTitle(((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey()));
                        json.put("formSchemeId", (Object)((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getSchemeKey());
                    } else {
                        FormSchemeDefine formSchemeDefine = (FormSchemeDefine)this.runTimeViewController.queryFormSchemeByTask(taskKey).get(0);
                        json.put("formSchemeId", (Object)formSchemeDefine.getKey());
                    }
                } else {
                    json.put("formSchemeId", (Object)periodLinkDefine.getSchemeKey());
                }
                return json.toString();
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u65f6\u671f\u5bf9\u8c61", (String)"\u5931\u8d25\uff0ctaskKey\u4e3a\u7a7a");
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u65f6\u671f\u5bf9\u8c61\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e.getMessage()));
            return null;
        }
    }

    private void getChildTaskTreeNodes4Recursion(List<ITree<TaskTreeNode>> childTaskTreeNodes) {
        childTaskTreeNodes.forEach(taskTreeNodeITree -> {
            TaskTreeNode taskTreeNode = (TaskTreeNode)taskTreeNodeITree.getData();
            if (taskTreeNode.getType() == TaskNodeType.TASKGROUP) {
                List<ITree<TaskTreeNode>> cchildTaskTreeNodes = this.getChildTaskTreeNodes(taskTreeNode.getKey());
                taskTreeNodeITree.setChildren(cchildTaskTreeNodes);
                this.getChildTaskTreeNodes4Recursion(cchildTaskTreeNodes);
            }
        });
    }

    private List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey) {
        return this.runTimeViewController.getChildTaskGroups(taskGroupKey, false).stream().filter(taskGroupDefine -> this.authorityProvider.canReadTaskGroup(taskGroupDefine.getKey())).collect(Collectors.toList());
    }

    private List<TaskDefine> getChildTasks(String taskGroupKey) {
        return this.runTimeViewController.getAllRunTimeTasksInGroup(taskGroupKey).stream().filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).collect(Collectors.toList());
    }

    @Override
    public List<TaskLinkImpl> getTaskLinksByFormScheme(String formSchemeKey) {
        return this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey).stream().sorted((o1, o2) -> {
            if (StringUtils.hasLength(o1.getOrder()) && StringUtils.hasLength(o2.getOrder())) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
            return 0;
        }).map(taskLinkDefine -> {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
            if (formSchemeDefine == null) {
                return null;
            }
            return new TaskLinkImpl((TaskLinkDefine)taskLinkDefine, formSchemeDefine.getTaskKey());
        }).filter(taskLink -> taskLink != null).collect(Collectors.toList());
    }
}

