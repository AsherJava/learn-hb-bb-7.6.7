/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.FormulasVO
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.splitword.core.analysis.JiebaSegmenter
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.biz.service.impl;

import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.va.biz.domain.BillAppParamDTO;
import com.jiuqi.va.biz.domain.SecureDataDTO;
import com.jiuqi.va.biz.domain.TableAndFieldSearchVO;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.encrypt.VaSymmetricEncryptService;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.ruler.common.consts.RefTableFieldProvider;
import com.jiuqi.va.biz.ruler.impl.RulerExecutor;
import com.jiuqi.va.biz.service.IBizService;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.utils.VerifyUtils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.FormulasVO;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.splitword.core.analysis.JiebaSegmenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@Lazy(value=false)
public class BizService
implements IBizService,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BizService.class);
    @Autowired
    private DataModelClient DataModelClient;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private PluginManager pluginManager;
    @Autowired
    private BillCodeClient billCodeClient;
    @Autowired
    private VaSymmetricEncryptService vaSymmetricEncryptService;
    @Autowired
    CommonDao commonDao;
    private List<Map<String, Integer>> documentVectors;
    private JiebaSegmenter segmenter;
    private List<IFunction> functions;
    private Map<String, Integer> functionNameIndex;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.segmenter = new JiebaSegmenter();
    }

    public List<IFunction> getFunctions() {
        if (this.functions == null) {
            Map providerMap = ModelFunctionProvider.functionProviderMap;
            Collection values = providerMap.values();
            this.functions = new ArrayList<IFunction>(values.size());
            for (FunctionProvider functionProvider : values) {
                Iterator iterator = functionProvider.iterator();
                while (iterator.hasNext()) {
                    this.functions.add((IFunction)iterator.next());
                }
            }
        }
        return this.functions;
    }

    public Map<String, Integer> getFunctionNameIndex() {
        if (this.functionNameIndex == null) {
            List<IFunction> list = this.getFunctions();
            this.functionNameIndex = list.stream().collect(Collectors.toMap(IFunction::name, list::indexOf, (oldVal, newVal) -> oldVal, HashMap::new));
        }
        return this.functionNameIndex;
    }

    @Override
    public List<TableAndFieldSearchVO> handleTableAndFieldSearh(ModelDefine modelDefine, String searchText) {
        ArrayList<TableAndFieldSearchVO> tableNodes = new ArrayList<TableAndFieldSearchVO>();
        ArrayList<TableAndFieldSearchVO> fieldNodes = new ArrayList<TableAndFieldSearchVO>();
        DataDefine data = Convert.cast(modelDefine.getPlugins().get("data"), DataDefine.class);
        return this.searchTable(tableNodes, fieldNodes, data, searchText);
    }

    @Override
    public List<TableAndFieldSearchVO> handleTableAndFieldSearh(List<Map<String, Object>> billData, String searchText) {
        ArrayList<TableAndFieldSearchVO> tableNodes = new ArrayList<TableAndFieldSearchVO>();
        ArrayList<TableAndFieldSearchVO> fieldNodes = new ArrayList<TableAndFieldSearchVO>();
        return this.searchTable(tableNodes, fieldNodes, billData, searchText);
    }

    @Override
    public List<FormulasVO> handleFormulasRetrieval(String text, List<String> functionNames) {
        Map<String, Integer> queryVector = this.computeQueryVector(text);
        ArrayList<Double> similarities = new ArrayList<Double>();
        for (Map<String, Integer> map : this.getDocumentVectors()) {
            similarities.add(this.cosineSimilarity(map, queryVector));
        }
        Map<String, Integer> nameIndex = this.getFunctionNameIndex();
        if (functionNames.size() > 0) {
            for (String name : functionNames) {
                Integer index = nameIndex.get(name);
                if (index == null) continue;
                Double aDouble = (Double)similarities.get(index);
                if (aDouble == null || aDouble.equals(Double.NaN)) {
                    aDouble = 0.0;
                }
                similarities.set(index, aDouble + 1.0);
            }
        }
        ArrayList<IFunction> arrayList = new ArrayList<IFunction>(this.functions);
        List collect = arrayList.stream().filter(o -> (Double)similarities.get((Integer)nameIndex.get(o.name())) > 0.0).collect(Collectors.toList());
        collect.sort((f1, f2) -> Double.compare((Double)similarities.get((Integer)nameIndex.get(f2.name())), (Double)similarities.get((Integer)nameIndex.get(f1.name()))));
        ArrayList<FormulasVO> formulasVOS = new ArrayList<FormulasVO>();
        for (IFunction iFunction : collect) {
            FormulasVO formulasVO = this.getFormulasVO(iFunction);
            formulasVOS.add(formulasVO);
        }
        return formulasVOS;
    }

    @Override
    public String getSensitiveData(SecureDataDTO param) {
        String tableName = param.getTableName();
        String fieldName = param.getFieldName();
        String id = param.getId();
        SQL sql = new SQL();
        sql.SELECT(fieldName);
        sql.FROM(tableName);
        sql.WHERE("id = #{param.arg0}");
        SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql.toString());
        HashMap<String, String> sqlParam = new HashMap<String, String>();
        sqlParam.put("arg0", id);
        sqlDTO.setParam(sqlParam);
        String ciphertext = this.commonDao.getString(sqlDTO);
        if (!StringUtils.hasText(ciphertext)) {
            return "";
        }
        ArrayList<String> ciphertexts = new ArrayList<String>();
        ciphertexts.add(ciphertext);
        return this.vaSymmetricEncryptService.doDecrypt(ciphertexts).get(0);
    }

    private FormulasVO getFormulasVO(IFunction iFunction) {
        FormulasVO formulasVO = new FormulasVO();
        formulasVO.setName(iFunction.name());
        return formulasVO;
    }

    @Value(value="${biz.rule.execute.limit:5000}")
    public void setRuleExecuteLimit(int limit) {
        RulerExecutor.setLimit(limit);
    }

    @Value(value="${biz.monitor.billrule.enabled:false}")
    public void setRuleMonitorEnabled(boolean enabled) {
        RulerExecutor.setRuleMonitorEnabled(enabled);
    }

    @Value(value="${biz.monitor.billrule.threshold:10}")
    public void setRuleMonitorEnabled(int threshold) {
        RulerExecutor.setRuleMonitorThreshold(threshold);
    }

    @Value(value="${biz.debug:false}")
    public void setDebug(boolean value) {
        Env.setDEBUG(value);
    }

    @Value(value="${biz.p:''}")
    public void setP(String value) {
        if (Utils.isNotEmpty(value)) {
            Env.setP(Utils.md5Bytes(value.getBytes(Utils.utf8)));
        }
    }

    private List<TableAndFieldSearchVO> searchTable(List<TableAndFieldSearchVO> tableNodes, List<TableAndFieldSearchVO> fieldNodes, DataDefine data, String searchText) {
        data.getTables().stream().forEach(table -> {
            if (table.getName().contains(searchText) || table.getTitle().contains(searchText)) {
                this.addNode(table, tableNodes, String.format("%s[%s]", table.getTitle(), table.getName()), table.getName(), table.getId());
            }
            this.searchField(fieldNodes, (DataTableDefine)table, searchText);
        });
        fieldNodes.addAll(0, tableNodes);
        return fieldNodes;
    }

    private List<TableAndFieldSearchVO> searchTable(List<TableAndFieldSearchVO> tableNodes, List<TableAndFieldSearchVO> fieldNodes, List<Map<String, Object>> data, String searchText) {
        for (Map<String, Object> table : data) {
            if (String.valueOf(table.get("name")).contains(searchText) || String.valueOf(table.get("title")).contains(searchText)) {
                this.addNode(table, tableNodes, String.format("%s[%s]", table.get("title"), table.get("name")), String.valueOf(table.get("name")), Convert.cast(table.get("id"), UUID.class));
            }
            this.searchField(fieldNodes, table, searchText);
        }
        fieldNodes.addAll(0, tableNodes);
        return fieldNodes;
    }

    private void searchField(List<TableAndFieldSearchVO> fieldNodes, DataTableDefine table, String searchText) {
        table.getFields().stream().forEach(field -> {
            if (field.getName().contains(searchText) || field.getTitle().contains(searchText)) {
                this.addNode(field, fieldNodes, String.format("%s[%s](%s[%s])", field.getTitle(), field.getName(), table.getTitle(), table.getName()), String.format("%s[%s]", table.getName(), field.getName()), field.getId());
            }
            this.searchRefField((DataFieldDefine)field, fieldNodes, table, searchText);
        });
    }

    private void searchField(List<TableAndFieldSearchVO> fieldNodes, Map<String, Object> table, String searchText) {
        List fields = (List)table.get("fields");
        for (Map field : fields) {
            if (String.valueOf(field.get("name")).contains(searchText) || String.valueOf(field.get("title")).contains(searchText)) {
                this.addNode(field, fieldNodes, String.format("%s[%s](%s[%s])", field.get("title"), field.get("name"), table.get("title"), table.get("name")), String.format("%s[%s]", table.get("name"), field.get("name")), Convert.cast(field.get("id"), UUID.class), (String)field.get("columnAttr"));
            }
            this.searchRefField(field, fieldNodes, table, searchText);
        }
    }

    private void searchRefField(DataFieldDefine field, List<TableAndFieldSearchVO> fieldNodes, DataTableDefine table, String searchText) {
        if (!StringUtils.hasText(field.getRefTableName())) {
            return;
        }
        if (field.getRefTableType() == 2 || field.getRefTableType() == 3) {
            RefTableFieldProvider.getRefTableByRefType(field.getRefTableType()).forEach(o -> {
                if (o.getFieldName().contains(searchText) || o.getFieldTitle().contains(searchText)) {
                    this.addRefNode(o, fieldNodes, o.getFieldTitle(), o.getFieldName(), table, field);
                }
            });
        } else {
            DataModelDTO modelDTO = new DataModelDTO();
            modelDTO.setName(field.getRefTableName());
            DataModelDO modelDO = this.DataModelClient.get(modelDTO);
            if (modelDO == null) {
                return;
            }
            modelDO.getColumns().forEach(column -> {
                if (column.getColumnName().contains(searchText) || column.getColumnTitle().contains(searchText)) {
                    this.addRefNode(column, fieldNodes, column.getColumnTitle(), column.getColumnName(), table, field);
                }
            });
        }
    }

    private void searchRefField(Map<String, Object> field, List<TableAndFieldSearchVO> fieldNodes, Map<String, Object> table, String searchText) {
        if (ObjectUtils.isEmpty(field.get("refTableName"))) {
            return;
        }
        if (field.get("refTableType").equals(2) || field.get("refTableType").equals(3)) {
            RefTableFieldProvider.getRefTableByRefType((Integer)field.get("refTableType")).forEach(o -> {
                if (o.getFieldName().contains(searchText) || o.getFieldTitle().contains(searchText)) {
                    this.addRefNode(o, fieldNodes, o.getFieldTitle(), o.getFieldName(), table, field, "SYSTEM");
                }
            });
        } else {
            DataModelDTO modelDTO = new DataModelDTO();
            modelDTO.setName((String)field.get("refTableName"));
            DataModelDO modelDO = this.DataModelClient.get(modelDTO);
            if (modelDO == null) {
                return;
            }
            modelDO.getColumns().forEach(column -> {
                if (column.getColumnName().contains(searchText) || column.getColumnTitle().contains(searchText)) {
                    this.addRefNode(column, fieldNodes, column.getColumnTitle(), column.getColumnName(), table, field, column.getColumnAttr() == null ? null : column.getColumnAttr().name());
                }
            });
        }
    }

    private void addRefNode(Object nodeData, List<TableAndFieldSearchVO> fieldNodes, String refFieldTitle, String refFieldName, DataTableDefine table, DataFieldDefine field) {
        String title = String.format("%s[%s[%s]](%s[%s]\\%s[%s])", refFieldTitle, field.getRefTableName(), refFieldName, table.getTitle(), table.getName(), field.getTitle(), field.getName());
        this.addNode(nodeData, fieldNodes, title, String.format("%s[%s]", field.getRefTableName(), refFieldName), UUID.randomUUID());
    }

    private void addRefNode(Object nodeData, List<TableAndFieldSearchVO> fieldNodes, String refFieldTitle, String refFieldName, Map<String, Object> table, Map<String, Object> field, String columnAttr) {
        String title = String.format("%s[%s[%s]](%s[%s]\\%s[%s])", refFieldTitle, field.get("refTableName"), refFieldName, table.get("title"), table.get("name"), field.get("title"), field.get("name"));
        this.addNode(nodeData, fieldNodes, title, String.format("%s[%s]", field.get("refTableName"), refFieldName), UUID.randomUUID(), columnAttr);
    }

    private void addNode(Object nodeData, List<TableAndFieldSearchVO> nodes, String title, String formulaText, UUID id) {
        String name = "";
        if (nodeData instanceof DataTableDefine) {
            name = Convert.cast(nodeData, DataTableDefine.class).getName();
        } else if (nodeData instanceof DataFieldDefine) {
            name = Convert.cast(nodeData, DataFieldDefine.class).getName();
        } else if (nodeData instanceof DataModelColumn) {
            name = Convert.cast(nodeData, DataModelColumn.class).getColumnName();
        }
        TableAndFieldSearchVO tableAndFieldSearchVO = new TableAndFieldSearchVO(id, name, title, formulaText);
        nodes.add(tableAndFieldSearchVO);
    }

    private void addNode(Object nodeData, List<TableAndFieldSearchVO> nodes, String title, String formulaText, UUID id, String columnAttr) {
        String name = "";
        if (nodeData instanceof DataTableDefine) {
            name = Convert.cast(nodeData, DataTableDefine.class).getName();
        } else if (nodeData instanceof DataFieldDefine) {
            name = Convert.cast(nodeData, DataFieldDefine.class).getName();
        } else if (nodeData instanceof DataModelColumn) {
            name = Convert.cast(nodeData, DataModelColumn.class).getColumnName();
        }
        TableAndFieldSearchVO tableAndFieldSearchVO = new TableAndFieldSearchVO(id, name, title, formulaText, columnAttr);
        nodes.add(tableAndFieldSearchVO);
    }

    @Override
    public DataModelDO getRefTable(String tableName) {
        DataModelDO result = new DataModelDO();
        ArrayList columns = new ArrayList();
        result.setColumns(columns);
        RefTableFieldProvider.getRefTableByTableName(tableName).forEach(o -> {
            DataModelColumn column = new DataModelColumn();
            column.setColumnName(o.getFieldName());
            column.setColumnTitle(o.getFieldTitle());
            columns.add(column);
        });
        return result;
    }

    @Override
    public List<Map<String, Object>> getPlugins(String billType, String billDefineCode) throws InstantiationException, IllegalAccessException {
        ArrayList<Map<String, Object>> plugins = new ArrayList<Map<String, Object>>();
        ModelType modelType = (ModelType)this.modelManager.find(billType);
        if (modelType == null) {
            return plugins;
        }
        ModelDefine billDefine = modelType.getModelDefineClass().newInstance();
        List<String> dependPlugins = Arrays.asList(modelType.getDependPlugins());
        modelType.initModelDefine(billDefine, billDefineCode);
        TypedContainer<PluginDefine> loadedPlugins = billDefine.getPlugins();
        this.pluginManager.getPluginList(modelType.getModelClass()).forEach(plugin -> {
            if (!plugin.canAdd()) {
                return;
            }
            try {
                PluginDefine pluginDefine = null;
                try {
                    pluginDefine = (PluginDefine)loadedPlugins.get(plugin.getName());
                }
                catch (MissingObjectException e) {
                    pluginDefine = plugin.getPluginDefineClass().newInstance();
                    plugin.initPluginDefine(pluginDefine, billDefine);
                    plugin.declare(() -> {});
                }
                HashMap<String, Object> pluginMap = new HashMap<String, Object>();
                pluginMap.put("name", plugin.getName());
                pluginMap.put("title", plugin.getTitle());
                pluginMap.put("pluginDefine", pluginDefine);
                if (dependPlugins.contains(plugin.getName())) {
                    pluginMap.put("fixed", true);
                }
                pluginMap.put("depends", this.pluginManager.getDependPlugins(plugin.getName()));
                plugins.add(pluginMap);
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        return plugins;
    }

    @Override
    public Map<String, Object> getBillAppParam(BillAppParamDTO billAppParamDTO) {
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(billAppParamDTO.getBillCode());
        String username = ShiroUtil.getUser().getUsername();
        String userId = ShiroUtil.getUser().getId();
        ArrayList<String> userIds = new ArrayList<String>();
        userIds.add(username);
        billVerifyDTO.setUserIds(userIds);
        billVerifyDTO.setAuth(1);
        Map<String, String> verifyCodeByUserName = VerifyUtils.genVerifyCodeForUsers(billVerifyDTO);
        String verifyCodeByName = verifyCodeByUserName.get(username);
        if (!billAppParamDTO.getVerifyCodeByUserName().equals(verifyCodeByName)) {
            logger.error("\u5f53\u524d\u53c2\u6570\u6743\u9650\u5f15\u7528\u7801{}\uff0c\u65b0\u67e5\u8be2\u5f15\u7528\u7801{}, \u67e5\u8be2\u53c2\u6570username{}", billAppParamDTO.getVerifyCodeByUserName(), verifyCodeByName, username);
            return null;
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        BillVerifyDTO newVerifyDTO = new BillVerifyDTO();
        ArrayList<String> newUserIds = new ArrayList<String>();
        newUserIds.add(userId);
        newVerifyDTO.setUserIds(newUserIds);
        newVerifyDTO.setBillCode(billAppParamDTO.getBillCode());
        newVerifyDTO.setAuth(1);
        Map<String, String> verifyById = VerifyUtils.genVerifyCodeForUsers(newVerifyDTO);
        String verifyCodeById = verifyById.get(userId);
        result.put("verifyCode", verifyCodeById);
        if (billAppParamDTO.getDefineCode() == null) {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billAppParamDTO.getBillCode());
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            R uniqueCodeByBillCode = this.billCodeClient.getUniqueCodeByBillCode(tenant);
            if (uniqueCodeByBillCode.getCode() == 1) {
                logger.error(uniqueCodeByBillCode.getMsg());
                return null;
            }
            result.put("defineCode", uniqueCodeByBillCode.get((Object)"value"));
        } else {
            result.put("defineCode", billAppParamDTO.getDefineCode());
        }
        return result;
    }

    public double cosineSimilarity(Map<String, Integer> docVector, Map<String, Integer> queryVector) {
        double dotProduct = 0.0;
        double queryMagnitude = 0.0;
        double docMagnitude = 0.0;
        for (String term : queryVector.keySet()) {
            if (docVector.containsKey(term)) {
                dotProduct += (double)(queryVector.get(term) * docVector.get(term));
            }
            queryMagnitude += Math.pow(queryVector.get(term).intValue(), 2.0);
        }
        for (Integer freq : docVector.values()) {
            docMagnitude += Math.pow(freq.intValue(), 2.0);
        }
        return dotProduct / (Math.sqrt(queryMagnitude) * Math.sqrt(docMagnitude));
    }

    public Map<String, Integer> computeQueryVector(String query) {
        return this.getQueryVector(query);
    }

    public List<Map<String, Integer>> getDocumentVectors() {
        if (this.documentVectors == null) {
            this.documentVectors = this.computeDocumentVectors(this.getFunctions());
        }
        return this.documentVectors;
    }

    public List<Map<String, Integer>> computeDocumentVectors(List<IFunction> functions) {
        ArrayList<Map<String, Integer>> documentVectors = new ArrayList<Map<String, Integer>>();
        for (IFunction iFunction : functions) {
            FormulaDescription formulaDescription;
            HashMap<String, Integer> vector = new HashMap<String, Integer>();
            if (iFunction instanceof ModelFunction) {
                ModelFunction modelFunction = (ModelFunction)iFunction;
                formulaDescription = modelFunction.toFormulaDescription();
                if (formulaDescription == null) {
                    formulaDescription = FunctionUtils.handleFormula((IFunction)iFunction);
                }
            } else {
                formulaDescription = FunctionUtils.handleFormula((IFunction)iFunction);
            }
            List parameters = Optional.ofNullable(formulaDescription.getParameters()).orElseGet(ArrayList::new);
            for (ParameterDescription parameter : parameters) {
                String description;
                String name = parameter.getName();
                if (StringUtils.hasText(name)) {
                    vector.putAll(this.getTermFrequency(name));
                }
                if (!StringUtils.hasText(description = parameter.getDescription())) continue;
                vector.putAll(this.getTermFrequency(description));
            }
            String returnValue = formulaDescription.getReturnValue();
            if (StringUtils.hasText(returnValue)) {
                vector.putAll(this.getTermFrequency(returnValue));
            }
            List examples = Optional.ofNullable(formulaDescription.getExamples()).orElseGet(ArrayList::new);
            for (FormulaExample example : examples) {
                String scenario;
                String retValue;
                String formula;
                String definition = example.getDefinition();
                if (StringUtils.hasText(definition)) {
                    vector.putAll(this.getTermFrequency(definition));
                }
                if (StringUtils.hasText(formula = example.getFormula())) {
                    vector.putAll(this.getTermFrequency(formula));
                }
                if (StringUtils.hasText(retValue = example.getReturnValue())) {
                    vector.putAll(this.getTermFrequency(retValue));
                }
                if (!StringUtils.hasText(scenario = example.getScenario())) continue;
                vector.putAll(this.getTermFrequency(scenario));
            }
            documentVectors.add(vector);
        }
        return documentVectors;
    }

    private Map<String, Integer> getTermFrequency(String text) {
        return this.getQueryVector(text);
    }

    private Map<String, Integer> getQueryVector(String query) {
        HashMap<String, Integer> queryVector = new HashMap<String, Integer>();
        List wordList = this.segmenter.sentenceProcess(query);
        for (String term : wordList) {
            queryVector.put(term, queryVector.getOrDefault(term, 0) + 1);
        }
        return queryVector;
    }
}

