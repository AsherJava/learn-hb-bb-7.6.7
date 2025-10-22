/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.query.dao.IQueryModalDefineDao
 *  com.jiuqi.nr.query.dao.IQueryModalGroupDao
 *  com.jiuqi.nr.query.querymodal.QueryModalDefine
 *  com.jiuqi.nr.query.querymodal.QueryModalGroup
 *  com.jiuqi.nr.query.querymodal.QueryModelType
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.service.rest;

import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckEnum;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckItemImpl;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.TaskShortInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.controller.IMultCheckConfigController;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/multCheckConfig"})
public class MultCheckConfigService {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckConfigService.class);
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    IQueryModalDefineDao queryModalDefineDao;
    @Autowired
    IMultCheckConfigController multCheckConfigController;
    @Autowired
    IQueryModalGroupDao queryModalGroupDao;
    @Autowired
    ResourceTreeNodeService resourceTreeNodeService;

    @RequestMapping(value={"/getMultCheckList"}, method={RequestMethod.GET})
    public List<IMultCheckItemBase> getMultCheckList(String formSchemeKey) throws Exception {
        ArrayList<IMultCheckItemBase> result = new ArrayList<IMultCheckItemBase>();
        for (MultCheckEnum multCheckEnum : MultCheckEnum.values()) {
            ArrayList<IMultCheckItemBase> groupItem;
            List<IMultCheckItemBase> groupTmp;
            MultCheckItemImpl item = new MultCheckItemImpl(multCheckEnum.getKey(), multCheckEnum.getKey(), multCheckEnum.getDesc());
            if (multCheckEnum == MultCheckEnum.GSSH) {
                ArrayList<IMultCheckItemBase> childrenItem = new ArrayList<IMultCheckItemBase>();
                List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                    MultCheckItemImpl item0 = new MultCheckItemImpl(formulaSchemeDefine.getKey(), multCheckEnum.getKey(), formulaSchemeDefine.getTitle());
                    childrenItem.add(item0);
                }
                item.setChildren(childrenItem);
            } else if (multCheckEnum == MultCheckEnum.DATA_ANALYSIS) {
                groupTmp = this.getQuickReportBaseItemListByType(multCheckEnum, "root");
                groupItem = new ArrayList<IMultCheckItemBase>();
                for (IMultCheckItemBase itemBase : groupTmp) {
                    if (itemBase.getChildren() == null) continue;
                    groupItem.add(itemBase);
                }
                item.setChildren(groupItem);
            } else if (multCheckEnum == MultCheckEnum.ZB_QUERY_TEMPLATE) {
                groupTmp = this.getZbqueryBaseItemListByType(multCheckEnum, "root");
                groupItem = new ArrayList();
                for (IMultCheckItemBase itemBase : groupTmp) {
                    if (itemBase.getChildren() == null) continue;
                    groupItem.add(itemBase);
                }
                item.setChildren(groupItem);
            } else if (multCheckEnum == MultCheckEnum.QUERY_TEMPLATE) {
                ArrayList<IMultCheckItemBase> groupItem2 = new ArrayList<IMultCheckItemBase>();
                List queryModalGroups = this.queryModalGroupDao.GetAllQueryModalGroups(QueryModelType.SIMPLEOWER);
                for (QueryModalGroup queryModalGroup : queryModalGroups) {
                    MultCheckItemImpl group0 = new MultCheckItemImpl(queryModalGroup.getGroupId(), multCheckEnum.getKey(), queryModalGroup.getGroupName());
                    ArrayList<IMultCheckItemBase> childrenItem = new ArrayList<IMultCheckItemBase>();
                    List queryModalDefines = this.queryModalDefineDao.getModalsByGroupId(queryModalGroup.getGroupId());
                    if (queryModalDefines != null) {
                        for (QueryModalDefine queryModalDefine : queryModalDefines) {
                            MultCheckItemImpl item0 = new MultCheckItemImpl(queryModalDefine.getId(), multCheckEnum.getKey(), queryModalDefine.getTitle());
                            childrenItem.add(item0);
                        }
                        group0.setChildren(childrenItem);
                    }
                    groupItem2.add(group0);
                }
                ArrayList queryModalDefines = new ArrayList();
                List rootModalDefines = this.queryModalDefineDao.getModalsByGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
                List nullModalDefines = this.queryModalDefineDao.getModalsByGroupId(null);
                queryModalDefines.addAll(rootModalDefines);
                queryModalDefines.addAll(nullModalDefines);
                if (queryModalDefines != null) {
                    for (QueryModalDefine queryModalDefine : queryModalDefines) {
                        if (queryModalDefine.getModelType() != QueryModelType.SIMPLEOWER) continue;
                        MultCheckItemImpl item0 = new MultCheckItemImpl(queryModalDefine.getId(), multCheckEnum.getKey(), queryModalDefine.getTitle());
                        groupItem2.add(item0);
                    }
                }
                item.setChildren(groupItem2);
            }
            result.add(item);
        }
        return result;
    }

    @RequestMapping(value={"/getRelationTaskAndCurrentTask"}, method={RequestMethod.GET})
    public List<TaskShortInfo> getRelationTaskAndCurrentTask(String taskkey, String formSchemeKey) {
        try {
            return this.multCheckConfigController.getRelationTaskAndCurrentTask(taskkey, formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/getPeriodTypeByScheme"}, method={RequestMethod.GET})
    public int getPeriodTypeByScheme(String formSchemeKey) {
        try {
            return this.multCheckConfigController.getPeriodTypeByScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return -1;
        }
    }

    private List<IMultCheckItemBase> getQuickReportBaseItemListByType(MultCheckEnum multCheckEnum, String forderid) throws Exception {
        ArrayList<IMultCheckItemBase> groupItem = new ArrayList<IMultCheckItemBase>();
        List treeNodes = this.resourceTreeNodeService.getChildren(forderid);
        for (ResourceTreeNode treeNode : treeNodes) {
            if (!treeNode.isFolder()) {
                if (!"com.jiuqi.nvwa.quickreport.business".equals(treeNode.getType())) continue;
                String checkType = "dataAnalysis";
                MultCheckItemImpl group0 = new MultCheckItemImpl(treeNode.getGuid(), checkType, treeNode.getTitle());
                groupItem.add(group0);
                continue;
            }
            List<IMultCheckItemBase> checkItemBases = this.getQuickReportBaseItemListByType(multCheckEnum, treeNode.getGuid());
            if (checkItemBases.size() <= 0) continue;
            MultCheckItemImpl item0 = new MultCheckItemImpl(treeNode.getGuid(), multCheckEnum.getKey(), treeNode.getTitle());
            groupItem.add(item0);
            item0.setChildren(checkItemBases);
        }
        return groupItem;
    }

    private List<IMultCheckItemBase> getZbqueryBaseItemListByType(MultCheckEnum multCheckEnum, String forderid) throws Exception {
        ArrayList<IMultCheckItemBase> groupItem = new ArrayList<IMultCheckItemBase>();
        List treeNodes = this.resourceTreeNodeService.getChildren(forderid);
        for (ResourceTreeNode treeNode : treeNodes) {
            if (!treeNode.isFolder()) {
                if (!"com.jiuqi.nr.zbquery.manage".equals(treeNode.getType())) continue;
                String checkType = "zbQueryTemplate";
                MultCheckItemImpl group0 = new MultCheckItemImpl(treeNode.getGuid(), checkType, treeNode.getTitle());
                groupItem.add(group0);
                continue;
            }
            List<IMultCheckItemBase> checkItemBases = this.getZbqueryBaseItemListByType(multCheckEnum, treeNode.getGuid());
            if (checkItemBases.size() <= 0) continue;
            MultCheckItemImpl item0 = new MultCheckItemImpl(treeNode.getGuid(), multCheckEnum.getKey(), treeNode.getTitle());
            groupItem.add(item0);
            item0.setChildren(checkItemBases);
        }
        return groupItem;
    }
}

