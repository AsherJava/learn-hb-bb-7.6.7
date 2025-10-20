/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobSchedulerStateBean;
import com.jiuqi.bi.core.jobs.bean.SchedulerState;
import com.jiuqi.bi.core.jobs.dao.SchedulerStateDao;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulerStateManager {
    private static final SchedulerStateManager instance = new SchedulerStateManager();

    private SchedulerStateManager() {
    }

    public static final SchedulerStateManager getInstance() {
        return instance;
    }

    public void starting(String schedulerName) throws JobsException {
        this.updateState(schedulerName, SchedulerState.STARTING);
    }

    public void started(String schedulerName) throws JobsException {
        this.updateState(schedulerName, SchedulerState.STARTED);
    }

    public void shuttingdown(String schedulerName) throws JobsException {
        this.updateState(schedulerName, SchedulerState.SHUTTINGDOWN);
    }

    public void shutdown(String schedulerName) throws JobsException {
        this.updateState(schedulerName, SchedulerState.SHUTDOWN);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, List<JobSchedulerStateBean>> getAllStates() throws JobsException {
        HashMap<String, List<JobSchedulerStateBean>> result = new HashMap<String, List<JobSchedulerStateBean>>();
        try {
            DistributionManager distributionManager = DistributionManager.getInstance();
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                List<JobSchedulerStateBean> states = SchedulerStateDao.selectAll(conn);
                for (JobSchedulerStateBean state : states) {
                    Node node = distributionManager.findNodeByName(state.getNode());
                    if (node == null || !node.isAlive()) continue;
                    ArrayList<JobSchedulerStateBean> curList = (ArrayList<JobSchedulerStateBean>)result.get(state.getNode());
                    if (curList == null) {
                        curList = new ArrayList<JobSchedulerStateBean>();
                    }
                    curList.add(state);
                    result.put(state.getNode(), curList);
                }
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
        return result;
    }

    private void updateState(String schedulerName, SchedulerState state) throws JobsException {
        try {
            Node curNode = DistributionManager.getInstance().getSelfNode();
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                int count = SchedulerStateDao.update(conn, curNode.getName(), schedulerName, state);
                if (count == 0) {
                    SchedulerStateDao.insert(conn, curNode.getName(), schedulerName, state);
                }
            }
        }
        catch (DistributionException | SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }
}

