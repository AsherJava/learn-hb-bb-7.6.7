/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionDAO
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder$StateChangeCallBack
 */
package com.jiuqi.nvwa.sf;

import com.jiuqi.bi.core.nodekeeper.DistributionDAO;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.util.StackTraceUtil;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStateChangeCallback
implements ServiceNodeStateHolder.StateChangeCallBack {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void change(ServiceNodeState state) {
        StackTraceUtil.printStackTrace("ServerStateChangeCallback:state:" + state);
        try (Connection connection = Framework.getInstance().getConnectionProvider().getConnection();){
            DistributionDAO.updateApplicationState((Connection)connection, (ServiceNodeState)state, (String)DistributionManager.getInstance().getMachineName());
        }
        catch (SQLException e) {
            this.logger.error("\u670d\u52a1\u72b6\u6001\u767b\u8bb0\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        if (state == ServiceNodeState.STOP) {
            return;
        }
        if (ServiceNodeStateHolder.getState() != ServiceNodeState.STOP) {
            try {
                Framework.getInstance().initInternal();
            }
            catch (Exception e) {
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.STOP);
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                }
                throw new RuntimeException(e);
            }
        }
    }
}

