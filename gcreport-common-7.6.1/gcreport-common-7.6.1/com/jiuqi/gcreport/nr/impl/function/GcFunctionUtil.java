/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class GcFunctionUtil {
    public static String getOrgTableCode(QueryContext queryContext) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
        try {
            FormSchemeDefine formSchemeDefine = ((ReportFmlExecEnvironment)queryContext.getExeContext().getEnv()).getFormSchemeDefine();
            TableModelDefine tableDefine = entityMetaService.getTableModel(formSchemeDefine.getDw());
            return null == tableDefine ? "MD_ORG_CORPORATE" : tableDefine.getName();
        }
        catch (Exception e) {
            return "MD_ORG_CORPORATE";
        }
    }
}

