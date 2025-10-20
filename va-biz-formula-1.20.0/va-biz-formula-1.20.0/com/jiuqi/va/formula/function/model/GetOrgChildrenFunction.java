/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class GetOrgChildrenFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;
    @Autowired
    private OrgDataClient orgDataClient;

    public GetOrgChildrenFunction() {
        this.parameters().add(new Parameter("codes", 0, "\u673a\u6784\u6807\u8bc6", false));
        this.parameters().add(new Parameter("category", 6, "\u673a\u6784\u7c7b\u578b", true));
        this.parameters().add(new Parameter("self", 1, "\u67e5\u8be2\u7ed3\u679c\u662f\u5426\u5305\u542b\u81ea\u8eab", true));
        this.parameters().add(new Parameter("allChildren", 1, "\u67e5\u8be2\u6240\u6709\u4e0b\u7ea7\u6216\u76f4\u63a5\u4e0b\u7ea7", true));
        this.parameters().add(new Parameter("authType", 1, "\u662f\u5426\u5ffd\u7565\u6743\u9650", true));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u673a\u6784\uff08\u652f\u6301\u591a\u4e2a\uff09\u7684\u6240\u6709\u4e0b\u7ea7";
    }

    public String name() {
        return "GetOrgChildren";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u673a\u6784\uff08\u652f\u6301\u591a\u4e2a\uff09\u7684\u6240\u6709\u4e0b\u7ea7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ArrayData codes = this.getCodes(parameters, context);
        String category = this.getCategory(parameters, context);
        Boolean self = this.getBooleanParameter(parameters, context, 2, true);
        Boolean allChildren = this.getBooleanParameter(parameters, context, 3, true);
        Boolean authType = this.getBooleanParameter(parameters, context, 4, false);
        ArrayList orgCodes = new ArrayList();
        for (Object code : codes) {
            OrgDTO orgDTO = this.createOrgDTO(category, (String)code, self, allChildren, authType);
            PageVO pageVO = this.orgDataClient.list(orgDTO);
            if (pageVO == null || pageVO.getTotal() <= 0) continue;
            orgCodes.addAll(pageVO.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList()));
        }
        return new ArrayData(6, orgCodes);
    }

    private ArrayData getCodes(List<IASTNode> parameters, IContext context) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        Object evaluate = node0.evaluate(context);
        if (ObjectUtils.isEmpty(evaluate)) {
            return new ArrayData(6, new ArrayList());
        }
        if (evaluate instanceof ArrayData) {
            return (ArrayData)evaluate;
        }
        return new ArrayData(6, Collections.singletonList(evaluate.toString()));
    }

    private String getCategory(List<IASTNode> parameters, IContext context) throws SyntaxException {
        IASTNode iastNode;
        String node;
        if (parameters.size() > 1 && StringUtils.hasText(node = (String)(iastNode = parameters.get(1)).evaluate(context))) {
            return node.toUpperCase();
        }
        return "MD_ORG";
    }

    private Boolean getBooleanParameter(List<IASTNode> parameters, IContext context, int index, boolean defaultValue) throws SyntaxException {
        if (parameters.size() > index) {
            IASTNode iastNode = parameters.get(index);
            return (Boolean)iastNode.evaluate(context);
        }
        return defaultValue;
    }

    private OrgDTO createOrgDTO(String category, String code, boolean self, boolean allChildren, boolean authType) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(category);
        orgDTO.setCode(code);
        orgDTO.setQueryChildrenType(this.getQueryChildrenType(self, allChildren));
        if (authType) {
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        }
        return orgDTO;
    }

    private OrgDataOption.QueryChildrenType getQueryChildrenType(boolean self, boolean allChildren) {
        if (self) {
            return allChildren ? OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF : OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF;
        }
        return allChildren ? OrgDataOption.QueryChildrenType.ALL_CHILDREN : OrgDataOption.QueryChildrenType.DIRECT_CHILDREN;
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + "\uff1b\u8fd4\u56de\u6307\u5b9a\u673a\u6784\u7684\u6240\u6709\u4e0b\u7ea7");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("codes");
        parameterDescription.setType(DataType.toString((int)11));
        parameterDescription.setDescription("\u673a\u6784\u6807\u8bc6");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("category");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u673a\u6784\u7c7b\u578b\uff0c\u53ef\u4e3a\u7a7a\uff0c\u9ed8\u8ba4MD_ORG");
        parameterDescription1.setRequired(false);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("self");
        parameterDescription2.setType(DataType.toString((int)1));
        parameterDescription2.setDescription("\u67e5\u8be2\u7ed3\u679c\u662f\u5426\u5305\u542b\u81ea\u8eab\uff0c\u53ef\u4e3a\u7a7a\uff0c\u9ed8\u8ba4true");
        parameterDescription2.setRequired(false);
        ParameterDescription parameterDescription4 = new ParameterDescription();
        parameterDescription4.setName("allChildren");
        parameterDescription4.setType(DataType.toString((int)1));
        parameterDescription4.setDescription("\u67e5\u8be2\u8303\u56f4\uff0c\u4e3atrue\u65f6\u67e5\u8be2\u6240\u6709\u4e0b\u7ea7\uff0c\u4e3afalse\u65f6\u67e5\u8be2\u76f4\u63a5\u4e0b\u7ea7\uff0c\u53ef\u4e3a\u7a7a\uff0c\u9ed8\u8ba4true");
        parameterDescription4.setRequired(false);
        ParameterDescription parameterDescription3 = new ParameterDescription();
        parameterDescription3.setName("authType");
        parameterDescription3.setType(DataType.toString((int)1));
        parameterDescription3.setDescription("\u662f\u5426\u5ffd\u7565\u6743\u9650\uff0c\u53ef\u4e3a\u7a7a\uff0c\u9ed8\u8ba4false");
        parameterDescription3.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription4);
        parameterDescriptions.add(parameterDescription3);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u673a\u6784\u4ee3\u7801\u4e3aTEST1\u548cTEST2\u7684\u6240\u6709\u4e0b\u7ea7");
        formulaExample.setFormula("GetOrgChildren({'TEST1', 'TEST2'}, 'MD_ORG', true, true, false)");
        formulaExample.setReturnValue("['TEST2','TEST3']");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

