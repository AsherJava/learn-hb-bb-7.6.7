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
public class GetUnitDataFunction
extends NrFunction
implements IGcFunction {
    private final String FUNCTION_NAME = "GetUnitData";
    private final String GC_UNIT_FILTER_CACHE_KEY = "GcUnitFilterCacheKey";
    private final String ORG_CODE = "code";

    public GetUnitDataFunction() {
        this.parameters().add(new Parameter("field", 6, "\u5b57\u6bb5\u540d\u79f0"));
    }

    public String name() {
        return "GetUnitData";
    }

    public String title() {
        return "GetUnitData\u516c\u5f0f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)iContext;
        AbstractMap fetchDataS = ((FilterExecuteContext)qContext).getData();
        String fieldName = ((String)parameters.get(0).evaluate(iContext)).toLowerCase();
        Object result = null;
        if (fetchDataS.containsKey(fieldName)) {
            result = fetchDataS.get(fieldName);
            return result;
        }
        if (Objects.nonNull(fetchDataS.get("code"))) {
            String code = String.valueOf(fetchDataS.get("code"));
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(code);
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            OrgDO orgDO = ((OrgDataService)SpringBeanUtils.getBean(OrgDataService.class)).get(orgDTO);
            if (Objects.nonNull(orgDO) && orgDO.containsKey((Object)fieldName)) {
                result = orgDO.get((Object)fieldName);
            }
        }
        return result;
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuffer buffer = new StringBuffer(description);
        buffer.append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("GetUnitData(\"shortName\")").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\u542b\u4e49\u662f\u4ece\u5f53\u524d\u7ec4\u7ec7\u4e2d\u53d6\u51fa\u6539\u5b57\u6bb5\u7684\u503c, \u5982\u679c\u5f53\u524d\u7ec4\u7ec7\u4e2d\u6ca1\u6709\u8be5\u5b57\u6bb5\uff0c\u5219\u4ece\u57fa\u7840\u7ec4\u7ec7\u53bb\u53d6, \u90fd\u6ca1\u6709\u5219\u6ca1\u6709\u8fd4\u56de\u7a7a\u3002");
        return buffer.toString();
    }
}

