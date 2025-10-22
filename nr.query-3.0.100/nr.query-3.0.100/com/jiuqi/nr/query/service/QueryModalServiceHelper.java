/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.dataset.dao.IDataSetGroupDao;
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.service.IQueryAuthority;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryModalServiceHelper {
    private static final Logger log = LoggerFactory.getLogger(QueryModalServiceHelper.class);
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private IQueryAuthority authority;
    @Autowired
    private IDataSetGroupDao datasetGroupDao;
    @Autowired
    private IQueryModalGroupDao groupDao;

    public String insertModal(QueryModalDefine modal) {
        try {
            List<Object> modals = new ArrayList();
            if (StringUtils.isEmpty((String)modal.getGroupId())) {
                modal.setGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
            }
            modals = this.modelDao.getModalsByTitle(modal.getTitle(), modal.getGroupId());
            boolean type_name_match = modals.stream().anyMatch(item -> item.getModelType() == modal.getModelType());
            if (null == modals || modals.size() == 0 || !type_name_match) {
                if (QueryModelType.DATASET.equals((Object)modal.getModelType())) {
                    String groupId = modal.getGroupId();
                    this.initNode(groupId);
                    if ("0000-0000-0000-00000".equals(groupId)) {
                        modal.setGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
                    }
                }
                modal.setUpdateTime(new Date());
                if (this.modelDao.insertQueryModalDefine(modal).booleanValue()) {
                    this.authority.grantAllPrivilegesForQueryModel(modal.getId(), modal.getModelType());
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)("\u4fdd\u5b58\u6a21\u677f\u6210\u529f\uff0cid:" + modal.getId()));
                    return "true";
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)"\u4fdd\u5b58\u6a21\u677f\u5931\u8d25");
                return "false";
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)"\u4fdd\u5b58\u6a21\u677f\uff1a\u6a21\u677f\u540d\u91cd\u590d");
            return "duplicate_template_title";
        }
        catch (Exception e) {
            Log.error((Exception)e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f\u5f02\u5e38", (String)("\u4fdd\u5b58\u6a21\u677f\u5f02\u5e38\u4fe1\u606f\uff1a" + e));
            return "false";
        }
    }

    public String changeModelOrder(String curNodeCode, String firstCode, String secondCode, String groupId) {
        if (StringUtils.isEmpty((String)curNodeCode)) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fee\u6539Order", (String)("\u5931\u8d25\uff0ccurNodeCode:" + curNodeCode + "\u4e0d\u80fd\u4e3a\u7a7a"));
            return "false";
        }
        if (StringUtils.isEmpty((String)groupId)) {
            try {
                QueryModalDefine curModel = this.modelDao.getQueryModalDefineById(curNodeCode);
                if (StringUtils.isEmpty((String)firstCode) && !StringUtils.isEmpty((String)secondCode)) {
                    QueryModalDefine nextModel = this.modelDao.getQueryModalDefineById(secondCode);
                    if (nextModel == null) {
                        return "false";
                    }
                    curModel.setOrder(nextModel.getOrder() - 0.01);
                } else if (!StringUtils.isEmpty((String)firstCode) && !StringUtils.isEmpty((String)secondCode)) {
                    QueryModalDefine preModel = this.modelDao.getQueryModalDefineById(firstCode);
                    QueryModalDefine nextModel = this.modelDao.getQueryModalDefineById(secondCode);
                    if (nextModel == null || preModel == null) {
                        return "false";
                    }
                    double curNewOrder = (preModel.getOrder() + nextModel.getOrder()) / 2.0;
                    curModel.setOrder(curNewOrder);
                } else {
                    QueryModalDefine preModel = this.modelDao.getQueryModalDefineById(firstCode);
                    if (preModel == null) {
                        return "false";
                    }
                    curModel.setOrder(preModel.getOrder() + 1.0);
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fee\u6539Order", (String)("\u6210\u529f\uff0ccurNodeCode:" + curNodeCode + "\uff0cOrder\uff1a" + curModel.getOrder()));
                return this.modelDao.updateQueryModalNoOptBlock(curModel).toString();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                QueryModalDefine curModel = this.modelDao.getQueryModalDefineById(curNodeCode);
                List<QueryModalDefine> repeatNameModels = this.modelDao.getModalsByTitle(curModel.getTitle(), groupId);
                if (repeatNameModels != null && !repeatNameModels.isEmpty()) {
                    return "Duplicate Title";
                }
                if (StringUtils.isEmpty((String)firstCode) && StringUtils.isEmpty((String)secondCode) || !StringUtils.isEmpty((String)firstCode) && StringUtils.isEmpty((String)secondCode)) {
                    List<QueryModalDefine> models = this.modelDao.getModalsByGroupId(groupId);
                    if (models != null && !models.isEmpty()) {
                        QueryModalDefine lastModel = models.get(models.size() - 1);
                        curModel.setOrder(lastModel.getOrder() + 1.0);
                    }
                } else if (StringUtils.isEmpty((String)firstCode) && !StringUtils.isEmpty((String)secondCode)) {
                    QueryModalDefine nextModel = this.modelDao.getQueryModalDefineById(secondCode);
                    if (nextModel == null) {
                        return "false";
                    }
                    curModel.setOrder(nextModel.getOrder() - 0.01);
                } else if (!StringUtils.isEmpty((String)firstCode) && !StringUtils.isEmpty((String)secondCode)) {
                    QueryModalDefine preModel = this.modelDao.getQueryModalDefineById(firstCode);
                    QueryModalDefine nextModel = this.modelDao.getQueryModalDefineById(secondCode);
                    if (nextModel == null || preModel == null) {
                        return "false";
                    }
                    double curNewOrder = (preModel.getOrder() + nextModel.getOrder()) / 2.0;
                    curModel.setOrder(curNewOrder);
                }
                curModel.setGroupId(groupId);
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fee\u6539Order\u548c\u5206\u7ec4", (String)("\u6210\u529f\uff0ccurNodeCode:" + curNodeCode + "\uff0cgroupId\uff1a" + groupId + "\uff0cOrder\uff1a" + curModel.getOrder()));
                return this.modelDao.updateQueryModalNoOptBlock(curModel).toString();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fee\u6539Order", (String)("\u5931\u8d25\uff0ccurNodeCode:" + curNodeCode));
        return "false";
    }

    public String changeModelTitle(String modelId, String title) {
        QueryModalDefine curModel = this.modelDao.getQueryModalDefineById(modelId);
        if (curModel == null) {
            return "model_not_found!";
        }
        try {
            boolean type_name_match;
            List<QueryModalDefine> modals = this.modelDao.getModalsByTitle(title, curModel.getGroupId());
            boolean bl = type_name_match = modals == null ? false : modals.stream().anyMatch(item -> item.getModelType() == curModel.getModelType());
            if (null == modals || modals.size() == 0 || !type_name_match) {
                if (QueryModelType.DATASET.equals((Object)curModel.getModelType())) {
                    String groupId = curModel.getGroupId();
                    this.initNode(groupId);
                    if ("0000-0000-0000-00000".equals(groupId)) {
                        curModel.setGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
                    }
                }
                curModel.setTitle(title);
                curModel.setUpdateTime(new Date());
                if (this.modelDao.updateQueryModalNoOptBlock(curModel).booleanValue()) {
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)("\u4fdd\u5b58\u6a21\u677f\u6210\u529f\uff0cid:" + curModel.getId()));
                    return "true";
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)"\u4fdd\u5b58\u6a21\u677f\u5931\u8d25");
                return "false";
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u4fdd\u5b58\u6a21\u677f", (String)"\u4fdd\u5b58\u6a21\u677f\uff1a\u6a21\u677f\u540d\u91cd\u590d");
            return "duplicate_template_title";
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    private void initNode(String groupId) {
        if ("0000-0000-0000-00000".equals(groupId)) {
            return;
        }
        QueryModalGroup queryModalGroup = this.groupDao.GetQueryModalGroupById(groupId);
        if (queryModalGroup != null) {
            return;
        }
        DataSetGroupDefine datasetGroup = this.datasetGroupDao.QueryDataSetGroupById(groupId);
        if (datasetGroup != null) {
            QueryModalGroup modalgroup = this.buildQueryModalGroup(datasetGroup);
            if (!"0000-0000-0000-00000".equals(datasetGroup.getParent())) {
                this.groupDao.InsertQueryModalGroup(modalgroup);
                this.initNode(datasetGroup.getParent());
            } else {
                modalgroup.setParentGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
                this.groupDao.InsertQueryModalGroup(modalgroup);
            }
        }
    }

    private QueryModalGroup buildQueryModalGroup(DataSetGroupDefine dataserGroup) {
        QueryModalGroup queryModalGroup = new QueryModalGroup();
        queryModalGroup.setGroupId(dataserGroup.getId());
        queryModalGroup.setModelType(QueryModelType.DATASET);
        queryModalGroup.setGroupName(dataserGroup.getTitle());
        queryModalGroup.setGroupOrder(dataserGroup.getOrder());
        queryModalGroup.setParentGroupId(dataserGroup.getParent());
        queryModalGroup.setUpdateTime(new Date());
        queryModalGroup.setCreator(NpContextHolder.getContext().getUserId().toString());
        return queryModalGroup;
    }
}

