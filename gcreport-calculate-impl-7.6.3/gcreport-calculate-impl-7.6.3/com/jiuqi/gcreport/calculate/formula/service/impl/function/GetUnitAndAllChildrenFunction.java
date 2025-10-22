/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.nr.impl.function.NrFunction
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.expression.filter.parse.FilterExecuteContext
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.expression.filter.parse.FilterExecuteContext;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class GetUnitAndAllChildrenFunction
extends NrFunction
implements IGcFunction {
    private final String FUNCTION_NAME = "GetUnitAndAllChildren";
    private final String GC_UNIT_FILTER_CACHE_KEY = "GcUnitFilterCacheKey";

    public GetUnitAndAllChildrenFunction() {
        this.parameters().add(new Parameter("orgCode", 6, "\u5355\u4f4dcode", false));
    }

    public String name() {
        return "GetUnitAndAllChildren";
    }

    public String title() {
        return "GetUnitAndAllChildren\u516c\u5f0f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        String parents;
        QueryContext qContext = (QueryContext)iContext;
        AbstractMap fetchDataS = ((FilterExecuteContext)qContext).getData();
        String parentOrgCode = (String)parameters.get(0).evaluate(iContext);
        Object result = null;
        if (fetchDataS.containsKey("parents") && (parents = (String)fetchDataS.get("parents")).contains(parentOrgCode)) {
            return "true";
        }
        if (Objects.nonNull(fetchDataS.get("code"))) {
            String parents2;
            String code = String.valueOf(fetchDataS.get("code"));
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(code);
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            OrgDO orgDO = ((OrgDataService)SpringBeanUtils.getBean(OrgDataService.class)).get(orgDTO);
            if (Objects.nonNull(orgDO) && orgDO.containsKey((Object)"parents") && (parents2 = (String)orgDO.get((Object)"parents")).contains(parentOrgCode)) {
                return "true";
            }
        }
        return result;
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuffer buffer = new StringBuffer(description);
        buffer.append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("GetUnitAndAllChildren(\"orgCode\")=\"true\"").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\u542b\u4e49\u662f\u83b7\u53d6\u5f53\u524d\u7248\u672c\u673a\u6784\u4e0b\u8be5\u673a\u6784\u53ca\u5176\u6240\u6709\u4e0b\u7ea7\u5355\u4f4d");
        return buffer.toString();
    }
}

