/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.fml.var.AbstractContextVar
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package nr.single.para.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import nr.single.para.var.JIOSQCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VarSQ
extends AbstractContextVar {
    private static final Logger logger = LoggerFactory.getLogger(VarSQ.class);
    private static final long serialVersionUID = 5338173258759034073L;

    public VarSQ() {
        super("SQ", "\u5f53\u524d\u65f6\u671f\u539f\u59cb\u7f16\u7801", 6);
    }

    public Object getVarValue(IContext context) {
        JIOSQCache cache;
        String sqMap;
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        QueryContext qContext = (QueryContext)context;
        IFmlExecEnvironment env = qContext.getExeContext().getEnv();
        FormSchemeDefine formSchemeDefine = null;
        if (env instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment reprotEnv = (ReportFmlExecEnvironment)env;
            try {
                formSchemeDefine = reprotEnv.getFormSchemeDefine();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (null != formSchemeDefine && StringUtils.isNotEmpty((String)(sqMap = (cache = (JIOSQCache)BeanUtil.getBean(JIOSQCache.class)).getSQMap(formSchemeDefine.getKey(), period.toString())))) {
            return sqMap;
        }
        ExecutorContext exeContext = qContext.getExeContext();
        return exeContext.getEnv().getPeriodAdapter(exeContext).getCustomPeriodStr(period.toString());
    }

    public void setVarValue(Object value) {
    }
}

