/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.data.engine.fml.var.ContextVariableManager
 *  com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package nr.single.para.config;

import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.data.engine.fml.var.ContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import nr.single.para.var.VarSQ;
import org.springframework.stereotype.Component;

@Component
public class JIOStartUp
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
        IReportDynamicNodeProvider normalContextVariableManager = ExecutorContext.contextVariableManagerProvider.getNormalContextVariableManager();
        IReportDynamicNodeProvider priorityContextVariableManager = ExecutorContext.contextVariableManagerProvider.getPriorityContextVariableManager();
        ((ContextVariableManager)normalContextVariableManager).add((Variable)new VarSQ());
        ((PriorityContextVariableManager)priorityContextVariableManager).add((Variable)new VarSQ());
    }

    public void initWhenStarted(ServletContext context) {
    }
}

