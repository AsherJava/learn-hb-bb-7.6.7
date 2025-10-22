/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 */
package com.jiuqi.nr.jtable.query.dataengine.env;

import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.query.dataengine.env.ExecEnvironmentProvider;
import com.jiuqi.nr.jtable.query.dataengine.env.ExecutorContextHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecutorContextHelperImpl
implements ExecutorContextHelper {
    private Map<String, ExecEnvironmentProvider> envMapper;

    @Autowired
    public ExecutorContextHelperImpl(List<ExecEnvironmentProvider> envList) {
        this.envMapper = this.buildEnvMapper(envList);
    }

    @Override
    public IFmlExecEnvironment getExecEnvironment(JtableContext jtableContext, IFmlExecEnvironment oriExecEnv) {
        Map<String, Object> variableMap = jtableContext.getVariableMap();
        if (variableMap != null && variableMap.containsKey("execEnvironmentProvider")) {
            String execEnvInstanceID;
            ExecEnvironmentProvider execEnvironmentProvider;
            IFmlExecEnvironment execEnvironment;
            Object o = variableMap.get("execEnvironmentProvider");
            if (this.envMapper != null && o != null && (execEnvironment = (execEnvironmentProvider = this.envMapper.get(execEnvInstanceID = o.toString())).getExecEnvironment(jtableContext, oriExecEnv)) != null) {
                return execEnvironment;
            }
        }
        return oriExecEnv;
    }

    private Map<String, ExecEnvironmentProvider> buildEnvMapper(List<ExecEnvironmentProvider> envList) {
        if (envList != null && !envList.isEmpty()) {
            this.envMapper = new HashMap<String, ExecEnvironmentProvider>();
            envList.forEach(execEnvironmentProvider -> this.envMapper.put(execEnvironmentProvider.getInstanceId(), (ExecEnvironmentProvider)execEnvironmentProvider));
            return this.envMapper;
        }
        return null;
    }
}

