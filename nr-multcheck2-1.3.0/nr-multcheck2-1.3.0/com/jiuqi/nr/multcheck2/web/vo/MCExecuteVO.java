/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.web.vo.MCHistorySchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckItemVO;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.nr.multcheck2.web.vo.TaskTreeNodeVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MCExecuteVO {
    private String errorMsg;
    private List<ITree<TaskTreeNodeVO>> taskTree;
    private List<TaskDefine> tasks;
    private List<MultcheckScheme> schemes;
    private List<MultcheckItemVO> items;
    private List<MCLabel> orgLinks;
    private String begin;
    private String end;
    private List<String> periods;
    private String curPeriod;
    private boolean adjust;
    private List<Map<String, String>> customArr;
    private int periodType;
    private IEntityDefine orgEntity;
    private List<IEntityDefine> entitys;
    private Map<String, List<MCLabel>> entityLabels;
    private List<MCLabel> orgList;
    private List<ITree<OrgTreeNode>> orgTree;
    private int orgSize;
    private String formScheme;
    List<MCHistorySchemeVO> historySchemes;
    private boolean showLastResult;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<ITree<TaskTreeNodeVO>> getTaskTree() {
        return this.taskTree;
    }

    public void setTaskTree(List<ITree<TaskTreeNodeVO>> taskTree) {
        this.taskTree = taskTree;
    }

    public List<TaskDefine> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<TaskDefine> tasks) {
        this.tasks = tasks;
    }

    public List<MultcheckScheme> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(List<MultcheckScheme> schemes) {
        this.schemes = schemes;
    }

    public List<MultcheckItemVO> getItems() {
        return this.items;
    }

    public void setItems(List<MultcheckItemVO> items) {
        this.items = items;
    }

    public String getBegin() {
        return this.begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    public String getCurPeriod() {
        return this.curPeriod;
    }

    public void setCurPeriod(String curPeriod) {
        this.curPeriod = curPeriod;
    }

    public boolean isAdjust() {
        return this.adjust;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    public List<Map<String, String>> getCustomArr() {
        return this.customArr;
    }

    public void setCustomArr(List<Map<String, String>> customArr) {
        this.customArr = customArr;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public List<MCLabel> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<MCLabel> orgList) {
        this.orgList = orgList;
    }

    public IEntityDefine getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(IEntityDefine orgEntity) {
        this.orgEntity = orgEntity;
    }

    public List<IEntityDefine> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<IEntityDefine> entitys) {
        this.entitys = entitys;
    }

    public void addEntity(IEntityDefine entity) {
        if (this.entitys == null) {
            this.entitys = new ArrayList<IEntityDefine>();
        }
        this.entitys.add(entity);
    }

    public Map<String, List<MCLabel>> getEntityLabels() {
        return this.entityLabels;
    }

    public void setEntityLabels(Map<String, List<MCLabel>> entityLabels) {
        this.entityLabels = entityLabels;
    }

    public List<ITree<OrgTreeNode>> getOrgTree() {
        return this.orgTree;
    }

    public void setOrgTree(List<ITree<OrgTreeNode>> orgTree) {
        this.orgTree = orgTree;
    }

    public int getOrgSize() {
        return this.orgSize;
    }

    public void setOrgSize(int orgSize) {
        this.orgSize = orgSize;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public List<MCHistorySchemeVO> getHistorySchemes() {
        return this.historySchemes;
    }

    public void setHistorySchemes(List<MCHistorySchemeVO> historySchemes) {
        this.historySchemes = historySchemes;
    }

    public boolean isShowLastResult() {
        return this.showLastResult;
    }

    public void setShowLastResult(boolean showLastResult) {
        this.showLastResult = showLastResult;
    }

    public List<MCLabel> getOrgLinks() {
        return this.orgLinks;
    }

    public void setOrgLinks(List<MCLabel> orgLinks) {
        this.orgLinks = orgLinks;
    }
}

