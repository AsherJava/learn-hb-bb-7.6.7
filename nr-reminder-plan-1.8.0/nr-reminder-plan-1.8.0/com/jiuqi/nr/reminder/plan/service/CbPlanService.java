/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.reminder.plan.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.TaskInfo;
import com.jiuqi.nr.reminder.plan.TreeNode;
import com.jiuqi.nr.reminder.plan.web.CbPlanVO;
import java.util.List;

public interface CbPlanService {
    public String addCbPlan(CbPlanDTO var1) throws JQException;

    public void updateCbPlan(CbPlanDTO var1) throws JQException;

    public void execPlan(String var1);

    public CbPlanDTO queryByPlanId(String var1);

    public void enablePlan(String var1) throws JQException;

    public void disablePlan(String var1) throws JQException;

    public void deletePlan(String var1) throws JQException;

    public List<CbPlanVO> queryByPlan(PagerInfo var1);

    public int countPlan();

    public TaskInfo queryTaskInfo(String var1);

    public List<TreeNode> task();

    public List<TreeNode> task(String var1);

    public List<TreeNode> task(TreeNode var1);
}

