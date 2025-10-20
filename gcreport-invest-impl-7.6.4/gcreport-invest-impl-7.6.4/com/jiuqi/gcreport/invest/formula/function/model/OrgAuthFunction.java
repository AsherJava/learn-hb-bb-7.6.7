/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrgAuthFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public OrgAuthFunction() {
        this.parameters().add(new Parameter("UnitCode", 6, "\u7ec4\u7ec7\u673a\u6784\u6807\u8bc6", false));
        this.parameters().add(new Parameter("AuthorityCode", 6, "\u6743\u9650\u6807\u8bc6", true));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u7528\u6237\u5bf9\u6307\u5b9a\u5355\u4f4d\u662f\u5426\u6709\u6743\u9650";
    }

    public String name() {
        return "hasOrgAuth";
    }

    public String title() {
        return "\u83b7\u53d6\u7528\u6237\u5bf9\u6307\u5b9a\u5355\u4f4d\u662f\u5426\u6709\u6743\u9650";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("UnitCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Authority").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u6743\u9650\u6807\u8bc6(NONE,ACCESS,WRITE)\u3002\u4e3a\u7a7a\u65f6\uff0c\u9ed8\u8ba4\u5224\u65ad\u5199\u6743\u9650").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u8fd4\u56de\u662f\u5426\u6709\u6743\u9650").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u7528\u6237\u5bf9\u8d44\u4ea7\u53f0\u8d26\u4e2d\u672c\u65b9\u6216\u5bf9\u65b9\u5355\u4f4d\u662f\u5426\u6709\u5199\u6743\u9650 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("hasOrgAuth(GC_COMMONASSETBILL[OPPUNITCODE]) OR hasOrgAuth(GC_COMMONASSETBILL[UNITCODE])").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        boolean flag = this.resetNpContext();
        try {
            String periodStr;
            YearPeriodObject yp;
            GcOrgCenterService orgTool;
            GcOrgCacheVO orgCacheVO;
            IASTNode node0 = parameters.get(0);
            Object node0Data = node0.evaluate(context);
            if (null == node0Data) {
                Object var6_6 = null;
                return var6_6;
            }
            GcAuthorityType authority = GcAuthorityType.ACCESS;
            if (parameters.size() > 1) {
                String authorityCode = (String)parameters.get(1).evaluate(context);
                try {
                    authority = GcAuthorityType.find((String)authorityCode);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            Boolean bl = null != (orgCacheVO = (orgTool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)authority, (YearPeriodObject)(yp = new YearPeriodObject(null, periodStr = Calendar.getInstance().get(1) + "Y0012")))).getOrgByCode((String)node0Data));
            return bl;
        }
        finally {
            if (!flag) {
                NpContextHolder.clearContext();
            }
        }
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() > 1) {
            String authorityCode = (String)parameters.get(1).evaluate(context);
            try {
                GcAuthorityType.find((String)authorityCode);
            }
            catch (Exception e) {
                throw new SyntaxException(parameters.get(1).getToken(), "hasOrgAuth\u51fd\u6570\u7b2c\u4e8c\u4e2a\u6743\u9650\u53c2\u6570(" + authorityCode + ")\u4e0d\u5b58\u5728\u5bf9\u5e94\u7684\u6743\u9650");
            }
        }
        return super.validate(context, parameters);
    }

    private boolean resetNpContext() {
        if (null == NpContextHolder.getContext() || null != NpContextHolder.getContext() && null == NpContextHolder.getContext().getUser()) {
            UserLoginDTO user = ShiroUtil.getUser();
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            NpContextUser userContext = new NpContextUser();
            userContext.setId(user.getId());
            userContext.setName(user.getUsername());
            userContext.setNickname(user.getUsername());
            userContext.setOrgCode(user.getLoginUnit());
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(userContext.getId());
            identity.setTitle(userContext.getFullname());
            identity.setOrgCode(user.getLoginUnit());
            npContext.setIdentity((ContextIdentity)identity);
            npContext.setUser((ContextUser)userContext);
            npContext.setLoginDate(user.getLoginDate());
            npContext.setTenant(user.getTenantName());
            NpContextHolder.setContext((NpContext)npContext);
            return false;
        }
        return true;
    }
}

