/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OrgRegulatory
extends Function
implements IReportFunction {
    private OrgIdentityService orgIdentityService;
    private static final long serialVersionUID = -392103826295664927L;

    public String name() {
        return "getOrgRegulatory";
    }

    public String title() {
        return "\u76d1\u7ba1\u7ec4\u7ec7";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        NpContext context;
        if (this.orgIdentityService == null) {
            this.orgIdentityService = (OrgIdentityService)BeanUtil.getBean(OrgIdentityService.class);
        }
        if ((context = NpContextHolder.getContext()) == null) {
            return new ArrayData(6, Collections.emptyList());
        }
        ContextUser user = context.getUser();
        if (user == null) {
            return new ArrayData(6, Collections.emptyList());
        }
        String userId = user.getId();
        Collection grantedOrg = this.orgIdentityService.getGrantedOrg(userId);
        if (grantedOrg == null) {
            return new ArrayData(6, Collections.emptyList());
        }
        return new ArrayData(6, new ArrayList(grantedOrg));
    }
}

