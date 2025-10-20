/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.ErrorInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.NodeToken
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.nvwa.authz.feign.client.NvwaAuthorityClient
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulasVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.biz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.ErrorInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.NodeToken;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nvwa.authz.feign.client.NvwaAuthorityClient;
import com.jiuqi.va.biz.domain.BillAppParamDTO;
import com.jiuqi.va.biz.domain.FormulasCheckDTO;
import com.jiuqi.va.biz.domain.GrammarTreeVO;
import com.jiuqi.va.biz.domain.SecureDataDTO;
import com.jiuqi.va.biz.domain.TableAndFieldSearchDTO;
import com.jiuqi.va.biz.domain.TableAndFieldSearchVO;
import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.decenc.DecEncFile;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.service.IBizService;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulasVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bizBinding"})
public class BizController {
    private static final Logger logger = LoggerFactory.getLogger(BizController.class);
    @Autowired
    private IBizService bizService;
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired(required=false)
    private List<DecEncFile> decEncFiles;
    @Autowired
    private NvwaAuthorityClient nvwaAuthorityClient;

    @PostMapping(value={"/sensitiveData"})
    public com.jiuqi.va.biz.utils.R<String> getSensitiveData(@RequestBody SecureDataDTO param) {
        String checkInfo = this.checkQueryParam(param);
        if (checkInfo != null) {
            return com.jiuqi.va.biz.utils.R.error(checkInfo);
        }
        LogUtil.add((String)"\u5355\u636e", (String)"\u654f\u611f\u6570\u636e\u67e5\u770b", (String)param.getBizCode(), (String)(param.getTableName() + "[" + param.getFieldName() + "]\uff1a" + param.getId()), null);
        Authority authority = this.nvwaAuthorityClient.query("22222222-5555-5555-5555-222222222222", (String)ShiroUtil.getUser().getExtInfo("contextIdentity"), "99999999-0000-0000-0000-999999999999");
        if (!Authority.ALLOW.equals((Object)authority)) {
            return com.jiuqi.va.biz.utils.R.error(BizBindingI18nUtil.getMessage("va.bizbinding.sensitive.data.not.permission"));
        }
        try {
            return com.jiuqi.va.biz.utils.R.ok(this.bizService.getSensitiveData(param));
        }
        catch (Exception e) {
            logger.error("\u89e3\u5bc6\u5931\u8d25", e);
            return com.jiuqi.va.biz.utils.R.error("\u89e3\u5bc6\u5931\u8d25", e);
        }
    }

    @PostMapping(value={"/formulas/retrieval"})
    public com.jiuqi.va.biz.utils.R formulasRetrieval(@RequestBody TenantDO param) {
        String searchKey = (String)param.getExtInfo("searchKey");
        List functions = (List)param.getExtInfo("functions");
        if (!StringUtils.hasText(searchKey)) {
            return com.jiuqi.va.biz.utils.R.error("\u67e5\u8be2\u5185\u5bb9\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return com.jiuqi.va.biz.utils.R.ok(this.bizService.handleFormulasRetrieval(searchKey, functions));
    }

    @GetMapping(value={"/formulas"})
    @Deprecated
    public com.jiuqi.va.biz.utils.R<Map<String, List<FormulasVO>>> gatherFormulas() {
        com.jiuqi.va.biz.utils.R<Map<String, List<FormulasVO>>> r = com.jiuqi.va.biz.utils.R.ok(FunctionUtils.gatherFormulas());
        return r;
    }

    @GetMapping(value={"/formulasFilter"})
    public com.jiuqi.va.biz.utils.R gatherFormulasFilter() {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("formulaMap", FunctionUtils.gatherFormulas());
        map.put("isPrivateCategory", FunctionUtils.getFilterCategories());
        try {
            return com.jiuqi.va.biz.utils.R.ok(objectMapper.writeValueAsString(map));
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return com.jiuqi.va.biz.utils.R.error("\u6536\u96c6\u516c\u5f0f\u5f02\u5e38", e);
        }
    }

    protected void printParamDeclaration(StringBuilder buffer, List<IParameter> parameters, boolean isInfiniteParameter) {
        boolean ommitable = false;
        boolean flag = false;
        for (IParameter p : parameters) {
            if (p.isOmitable() && !ommitable) {
                ommitable = true;
                buffer.append('[');
            }
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(p.name());
        }
        if (isInfiniteParameter && !parameters.isEmpty()) {
            buffer.append(", ...");
        }
        if (ommitable) {
            buffer.append(']');
        }
    }

    @PostMapping(value={"/formulas/triggerFields"})
    public com.jiuqi.va.biz.utils.R<Map<String, Set<String>>> getTriggerFields(@RequestBody FormulasCheckDTO param) {
        ModelDataContext context = new ModelDataContext(param.getModelDefine());
        HashMap map = new HashMap();
        for (FormulaImpl formula : param.getFormulas()) {
            try {
                String expression = formula.getExpression();
                IExpression iExpression = ModelFormulaHandle.getInstance().parse(context, expression, formula.getFormulaType());
                iExpression.forEach(node -> {
                    if (node instanceof ModelNode) {
                        ModelNode modelNode = (ModelNode)((Object)node);
                        Set fields = map.computeIfAbsent(modelNode.tableDefine.getName(), key -> new HashSet());
                        fields.add(modelNode.fieldDefine.getName());
                    } else if (node instanceof CountDataNode) {
                        CountDataNode countDataNode = (CountDataNode)((Object)node);
                        Set fields = map.computeIfAbsent(countDataNode.getModelNode().tableDefine.getName(), key -> new HashSet());
                        fields.add(countDataNode.getModelNode().fieldDefine.getName());
                    }
                });
            }
            catch (Exception e) {
                return com.jiuqi.va.biz.utils.R.error(e.getMessage());
            }
        }
        return com.jiuqi.va.biz.utils.R.ok(map);
    }

    @PostMapping(value={"/formulas/check"})
    public com.jiuqi.va.biz.utils.R<List<GrammarTreeVO>> checkFormula(@RequestBody FormulasCheckDTO paramDTO) {
        ModelDataContext context = new ModelDataContext(paramDTO.getModelDefine());
        ArrayList grammarTreeVOs = new ArrayList();
        StringBuffer msgs = new StringBuffer();
        paramDTO.getFormulas().stream().forEach(formula -> {
            try {
                int type;
                String expression = formula.getExpression();
                IExpression iExpression = ModelFormulaHandle.getInstance().parse(context, expression, formula.getFormulaType());
                int resultType = paramDTO.getResultTypes(formula.getId());
                boolean rightIsAnyType = false;
                if (resultType != 0 && BizController.checkTypeEqual(resultType, type = iExpression.getType((IContext)context))) {
                    if (type == 0) {
                        rightIsAnyType = true;
                    } else {
                        throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.formulatypeexception"));
                    }
                }
                IASTNode node = iExpression.getChild(0);
                FormulaUtils.checkFormulaTable(node);
                GrammarTreeVO grammarTree = FormulaUtils.createGrammarTree(node);
                if (rightIsAnyType) {
                    grammarTree.setWarnMsg("\u672a\u77e5\u7684\u8fd4\u56de\u7c7b\u578b");
                }
                grammarTreeVOs.add(grammarTree);
            }
            catch (Exception e) {
                msgs.append(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.formulacheckfailed", new Object[]{formula.getExpression()}) + e.getMessage()).append("##");
            }
        });
        if (!StringUtils.isEmpty(msgs.toString())) {
            return new com.jiuqi.va.biz.utils.R<Object>(1, msgs.substring(0, msgs.length() - 2), "", null);
        }
        return com.jiuqi.va.biz.utils.R.ok(grammarTreeVOs);
    }

    @PostMapping(value={"/formulas/check/withInfo"})
    public com.jiuqi.va.biz.utils.R<Object> checkFormulaWithInfo(@RequestBody FormulasCheckDTO paramDTO) {
        ModelDataContext context = new ModelDataContext(paramDTO.getModelDefine());
        ArrayList grammarTreeVOs = new ArrayList();
        StringBuffer msgs = new StringBuffer();
        ErrorInfo[] errorInfo = new ErrorInfo[]{null};
        paramDTO.getFormulas().stream().forEach(formula -> {
            try {
                int type;
                String expression = formula.getExpression();
                IExpression iExpression = ModelFormulaHandle.getInstance().parse(context, expression, formula.getFormulaType());
                int resultType = paramDTO.getResultTypes(formula.getId());
                boolean rightIsAnyType = false;
                if (resultType != 0 && BizController.checkTypeEqual(resultType, type = iExpression.getType((IContext)context))) {
                    if (type == 0) {
                        rightIsAnyType = true;
                    } else {
                        throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.formulatypeexception"));
                    }
                }
                IASTNode node = iExpression.getChild(0);
                FormulaUtils.checkFormulaTable(node);
                GrammarTreeVO grammarTree = FormulaUtils.createGrammarTree(node);
                if (rightIsAnyType) {
                    grammarTree.setWarnMsg("\u672a\u77e5\u7684\u8fd4\u56de\u7c7b\u578b");
                }
                grammarTreeVOs.add(grammarTree);
            }
            catch (ParseException parseException) {
                List details = ((ParseException)parseException.getCause()).getDetails();
                if (!CollectionUtils.isEmpty(details)) {
                    errorInfo[0] = (ErrorInfo)details.get(0);
                }
                msgs.append(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.formulacheckfailed", new Object[]{formula.getExpression()}) + parseException.getMessage()).append("##");
            }
            catch (Exception e) {
                msgs.append(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.formulacheckfailed", new Object[]{formula.getExpression()}) + e.getMessage()).append("##");
            }
        });
        if (StringUtils.hasText(msgs.toString())) {
            HashMap<String, Integer> info = new HashMap<String, Integer>();
            if (errorInfo[0] != null) {
                NodeToken token = errorInfo[0].getToken();
                info.put("startLineNumber", token.line());
                info.put("endLineNumber", token.line());
                info.put("startColumn", token.column() + 1);
                info.put("endColumn", token.column() + 1);
            }
            return new com.jiuqi.va.biz.utils.R<Object>(1, msgs.substring(0, msgs.length() - 2), "", info);
        }
        return com.jiuqi.va.biz.utils.R.ok(grammarTreeVOs);
    }

    private static boolean checkTypeEqual(int resultType, int type) {
        if (resultType == 10 || resultType == 3) {
            return type != 10 && type != 3;
        }
        return resultType != type;
    }

    @PostMapping(value={"/formulas/search"})
    public com.jiuqi.va.biz.utils.R<List<TableAndFieldSearchVO>> handleSearchTable(@RequestBody TableAndFieldSearchDTO paramDO) {
        List<TableAndFieldSearchVO> andFieldSearchVOs = this.bizService.handleTableAndFieldSearh(paramDO.getModelDefine(), paramDO.getSearchText());
        return com.jiuqi.va.biz.utils.R.ok(andFieldSearchVOs);
    }

    @PostMapping(value={"/formulas/search/tableFields"})
    public com.jiuqi.va.biz.utils.R<List<TableAndFieldSearchVO>> handleSearchTableFields(@RequestBody TableAndFieldSearchDTO paramDO) {
        List<TableAndFieldSearchVO> andFieldSearchVOs = this.bizService.handleTableAndFieldSearh(paramDO.getBillData(), paramDO.getSearchText());
        return com.jiuqi.va.biz.utils.R.ok(andFieldSearchVOs);
    }

    @PostMapping(value={"/formulas/reftable"})
    public DataModelDO getRefTable(@RequestBody DataModelDTO param) {
        return this.bizService.getRefTable(param.getName());
    }

    @PostMapping(value={"/plugins/list"})
    public R getAllPlugins(@RequestBody TenantDO param) {
        List<Object> plugins = new ArrayList();
        try {
            plugins = this.bizService.getPlugins((String)param.getExtInfo("modelType"), (String)param.getExtInfo("uniqueCode"));
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.getpluginexception"), e);
        }
        R r = R.ok();
        return r.put("plugins", plugins);
    }

    @PostMapping(value={"/i18n/plugins/list"})
    public R getI18nPlugins(@RequestBody TenantDO param) {
        ArrayList i18nPlugins = new ArrayList();
        try {
            List<Map<String, Object>> plugins = this.bizService.getPlugins((String)param.getExtInfo("modelType"), (String)param.getExtInfo("uniqueCode"));
            if (plugins != null && plugins.size() > 0) {
                for (Map<String, Object> plugin : plugins) {
                    I18nPlugin i18nPlugin = (I18nPlugin)this.i18nPluginManager.find(plugin.get("name").toString());
                    if (i18nPlugin == null) continue;
                    HashMap<String, Object> i18nPluginMap = new HashMap<String, Object>();
                    i18nPluginMap.put("name", i18nPlugin.getName() + "&plugin");
                    i18nPluginMap.put("title", i18nPlugin.getTitle() + "\uff08\u63d2\u4ef6\uff09");
                    i18nPluginMap.put("groupFlag", i18nPlugin.isGroup());
                    i18nPluginMap.put("categoryFlag", i18nPlugin.isGroup());
                    i18nPlugins.add(i18nPluginMap);
                }
            }
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.bizcontroller.getpluginexception"), e);
        }
        R r = R.ok();
        return r.put("plugins", i18nPlugins);
    }

    @GetMapping(value={"/decencs"})
    public com.jiuqi.va.biz.utils.R getDecEncs() {
        if (CollectionUtils.isEmpty(this.decEncFiles)) {
            return com.jiuqi.va.biz.utils.R.ok();
        }
        ArrayList results = new ArrayList();
        for (DecEncFile decEncFile : this.decEncFiles) {
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("name", decEncFile.getName());
            result.put("title", decEncFile.getTitle());
            results.add(result);
        }
        return com.jiuqi.va.biz.utils.R.ok(results);
    }

    @PostMapping(value={"/billApp/param/get"})
    com.jiuqi.va.biz.utils.R getBillAppParam(@RequestBody BillAppParamDTO billAppParamDTO) {
        if (billAppParamDTO.getBillCode() == null || billAppParamDTO.getVerifyCodeByUserName() == null) {
            return com.jiuqi.va.biz.utils.R.error("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Map<String, Object> param = this.bizService.getBillAppParam(billAppParamDTO);
        if (param == null) {
            return com.jiuqi.va.biz.utils.R.error("\u53c2\u6570\u83b7\u53d6\u5931\u8d25");
        }
        return com.jiuqi.va.biz.utils.R.ok(param);
    }

    private String checkQueryParam(SecureDataDTO param) {
        if (!StringUtils.hasText(param.getBizCode())) {
            return "\u4e1a\u52a1\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a";
        }
        if (!StringUtils.hasText(param.getTableName())) {
            return "\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a";
        }
        if (!StringUtils.hasText(param.getFieldName())) {
            return "\u5b57\u6bb5\u540d\u4e0d\u80fd\u4e3a\u7a7a";
        }
        if (!StringUtils.hasText(param.getId())) {
            return "\u884cID\u4e0d\u80fd\u4e3a\u7a7a";
        }
        return null;
    }
}

