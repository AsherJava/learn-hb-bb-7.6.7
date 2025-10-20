/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class GetAttachmentUrlFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    @Value(value="${feignClient.bizAttachmentServer.name}")
    private String bizAttachmentServerName;
    @Value(value="${feignClient.bizAttachmentServer.path}")
    private String bizAttachmentServerPath;
    @Value(value="${server.port}")
    private String serverPort;

    public String addDescribe() {
        return "\u83b7\u53d6\u9644\u4ef6URL";
    }

    public GetAttachmentUrlFunction() {
        this.parameters().add(new Parameter("QuoteCode", 0, "\u9644\u4ef6\u5f15\u7528\u7801", false));
        this.parameters().add(new Parameter("AttachNum", 0, "\u9644\u4ef6\u5f20\u6570", false));
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return false;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ResponseEntity exchange;
        RestTemplate restTemplate;
        String url;
        Object quoteCodeObj = parameters.get(0).evaluate(context);
        if (ObjectUtils.isEmpty(quoteCodeObj)) {
            return null;
        }
        String quoteCode = (String)quoteCodeObj;
        StringBuilder result = new StringBuilder();
        boolean beanExist = ApplicationContextRegister.getApplicationContext().containsBean("vaAttachmentBizController");
        HttpHeaders headers = new HttpHeaders();
        String token = ShiroUtil.getSubjct().getSession().getId().toString();
        headers.set("Authorization", token);
        HttpEntity httpEntity = new HttpEntity((MultiValueMap)headers);
        if (beanExist) {
            url = "http://127.0.0.1:" + this.serverPort + "/bizAttachment/list/" + quoteCode;
            restTemplate = new RestTemplate();
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, List.class, new Object[0]);
        } else {
            url = "http://" + this.bizAttachmentServerName + ("/".equals(this.bizAttachmentServerPath) ? "/bizAttachment/list/" : this.bizAttachmentServerPath + "/bizAttachment/list/") + quoteCode;
            restTemplate = new RestTemplate();
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, List.class, new Object[0]);
        }
        List jsonArray = (List)exchange.getBody();
        if (jsonArray == null) {
            return null;
        }
        result.append("/bizAttachment/download/");
        result.append(quoteCode);
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            Map map = (Map)jsonArray.get(i);
            Integer status = (Integer)map.get("status");
            if (status != null && status == 2) continue;
            String id = (String)map.get("id");
            results.add(result + "/" + id + "?JTOKENID=" + token);
        }
        return StringUtils.arrayToDelimitedString(results.toArray(), "|");
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 6;
    }

    public String name() {
        return "GetAttachmentUrl";
    }

    public String title() {
        return "\u83b7\u53d6\u9644\u4ef6URL";
    }

    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    public void toDeclaration(StringBuilder buffer) {
        try {
            int retType = this.getResultType(null, null);
            buffer.append(DataType.toExpression((int)retType)).append(' ').append(this.name()).append('(');
        }
        catch (SyntaxException e) {
            throw new SyntaxRuntimeException((Throwable)e);
        }
        this.printParamDeclaration(buffer);
        buffer.append(");");
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("quoteCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u9644\u4ef6\u5f15\u7528\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("quoteCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u9644\u4ef6\u5f20\u6570\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u8fd4\u56de\u9644\u4ef6URL\uff08\u67e5\u8be2\u7ed3\u679c\u6709\u591a\u6761\u65f6\uff0c\u4ee5|\u5206\u9694\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u62a5\u9500\u5355\u4e0a\u4f20\u9644\u4ef6\u7684URL  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetAttachmentUrl(FO_EXPENSEBILL[QUOTECODE], FO_EXPENSEBILL[ATTACHNUM])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("http://127.0.0.1:8000/vcserver/bizAttachment/download/202109-553a732a-f955-4236-9db0-e9aa82b76923/e233037f-f4b9-406e-8f41-089ea2706163?JTOKENID=03d02bf3-bc9d-43b7-ac6c-736c1f4b1b25");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u9644\u4ef6URL\uff08\u67e5\u8be2\u7ed3\u679c\u6709\u591a\u6761\u65f6\uff0c\u4ee5|\u5206\u9694\uff09");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("quoteCode", DataType.toString((int)6), "\u9644\u4ef6\u5f15\u7528\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("attachNum", DataType.toString((int)6), "\u9644\u4ef6\u5f20\u6570\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u53d6\u62a5\u9500\u5355\u4e0a\u4f20\u9644\u4ef6\u7684URL", "GetAttachmentUrl(FO_EXPENSEBILL[QUOTECODE], FO_EXPENSEBILL[ATTACHNUM])", "http://127.0.0.1:8000/vcserver/bizAttachment/download/202109-553a732a-f955-4236-9db0-e9aa82b76923/e233037f-f4b9-406e-8f41-089ea2706163?JTOKENID=03d02bf3-bc9d-43b7-ac6c-736c1f4b1b25");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

