/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.utils.BillCoreI18nUtil
 *  com.jiuqi.va.bill.utils.BillUtils
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.impl.value.ListContainerImpl
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.feign.client.BillClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import com.jiuqi.va.feign.client.BillClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class BackWriteFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger("BackWriteFunction");
    public static ThreadLocal<Map<String, String>> defineCache = new ThreadLocal();
    public static ThreadLocal<Map<String, BillModel>> modelCache = new ThreadLocal();
    public static ThreadLocal<Map<String, Boolean>> tableCache = new ThreadLocal();
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private BillUtils billUtil;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public BackWriteFunction() {
        this.parameters().add(new Parameter("BillCodeField", 0, "\u5355\u636e\u7f16\u53f7\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("BackWriteProperties", 6, "\u53cd\u5199\u5c5e\u6027\uff1a\u6e90\u8868\u5b57\u6bb5\u4e0e\u53d6\u503c\u516c\u5f0f\u4e0e\u53cd\u5199\u7c7b\u578b", false));
        this.parameters().add(new Parameter("ItemTableUniqueFlagField", 0, "\u5b50\u8868\u552f\u4e00\u6807\u8bc6\u5b57\u6bb5", true));
    }

    public String addDescribe() {
        return "\u6839\u636e\u5f53\u524d\u5355\u636e\u7684\u5b57\u6bb5\u53cd\u5199\u6e90\u5355\u4e0a\u7684\u5b57\u6bb5\u3002\u7ed3\u5408\u53cd\u5199\u89c4\u5219\u4f7f\u7528\uff0c\u4ec5\u652f\u6301\u53cd\u5199\u6e90\u5355\uff0c\u4e0d\u652f\u6301\u53cd\u5199\u672c\u5355\u3002";
    }

    public String name() {
        return "BackWriteFunction";
    }

    public String title() {
        return "\u53cd\u5199\u516c\u5f0f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        HashMap defineTemp = new HashMap();
        if (defineCache.get() == null) {
            defineCache.set(defineTemp);
        }
        HashMap modelTemp = new HashMap();
        if (modelCache.get() == null) {
            modelCache.set(modelTemp);
        }
        HashMap tableTemp = new HashMap();
        if (tableCache.get() == null) {
            tableCache.set(tableTemp);
        }
        IASTNode billCodeFieldNode = list.get(0);
        IASTNode evaluateExpressionNode = list.get(1);
        IASTNode itemTableFieldNode = list.size() == 3 ? list.get(2) : null;
        BillModel thisModel = (BillModel)((ModelDataContext)iContext).model;
        if (itemTableFieldNode != null) {
            this.backWriteItemTable((ModelDataContext)iContext, evaluateExpressionNode, itemTableFieldNode, thisModel, billCodeFieldNode);
        } else {
            ArrayList<String> billCodeList = new ArrayList<String>();
            Object srcBillCodes = billCodeFieldNode.evaluate(iContext);
            if (srcBillCodes instanceof ArrayData) {
                ((ArrayData)srcBillCodes).forEach(item -> billCodeList.add(item.toString()));
            } else {
                billCodeList.add(0, (String)srcBillCodes);
            }
            this.backWriteMasterTable(iContext, evaluateExpressionNode, billCodeList, thisModel);
            billCodeList.clear();
        }
        return null;
    }

    private void backWriteMasterTable(IContext iContext, IASTNode evaluateExpressionNode, List<String> billCodeList, BillModel thisModel) throws SyntaxException {
        String billCode = thisModel.getMaster().getString("BILLCODE");
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97\u5f00\u59cb\u6267\u884c\u65b9\u6cd5backWriteMasterTable\uff0c\u5355\u636e\u7f16\u53f7\u4e3a\uff1a" + billCode);
        BillClient billClient = null;
        boolean sameModule = true;
        for (String srcBillCode : billCodeList) {
            String[] singleEvalExpressions;
            sameModule = this.isSameModule(thisModel, srcBillCode);
            BillDataDTO billDataDTO = new BillDataDTO();
            billDataDTO.setBillCode(srcBillCode);
            R srcDataRes = null;
            if (sameModule) {
                srcDataRes = BillUtils.load((BillDataDTO)billDataDTO);
            } else {
                if (billClient == null) {
                    billClient = VaBillFeignUtil.getBillClientByBillCode((String)srcBillCode, (String)ShiroUtil.getTenantName());
                }
                srcDataRes = billClient.load(billDataDTO);
            }
            if (srcDataRes.getCode() != 0) {
                LOGGER.warn("\u5931\u8d25\uff0c\u65e0\u6cd5\u83b7\u53d6\u6e90\u5355\u6570\u636e\uff0c" + srcDataRes.getMsg());
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.nofindsrcbilldata") + srcDataRes.getMsg());
            }
            Object srcData = srcDataRes.get((Object)"data");
            Object evaluateExpressionObj = evaluateExpressionNode.evaluate(iContext);
            String multiEvalExpression = (String)evaluateExpressionObj;
            for (String singleEvalExpression : singleEvalExpressions = multiEvalExpression.split("#")) {
                String[] backFieldAndBackValueAndType = singleEvalExpression.split("&");
                if (backFieldAndBackValueAndType.length != 3) {
                    throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.paramcounterror"));
                }
                HashMap<String, String> map = new HashMap<String, String>(8);
                map.put("billCode", srcBillCode);
                TenantDO tenantDO = new TenantDO();
                tenantDO.setExtInfo(map);
                String backTableAndBackField = backFieldAndBackValueAndType[0];
                String backTable = backTableAndBackField.substring(0, backTableAndBackField.indexOf("["));
                String backField = backTableAndBackField.substring(backTableAndBackField.indexOf("[") + 1, backTableAndBackField.length() - 1);
                ModelDataContext modelDataContext = new ModelDataContext((Model)thisModel);
                HashMap rowMap = new HashMap();
                Object backWriteValue = null;
                for (Map.Entry entry : ((ModelDataContext)iContext).getParams().entrySet()) {
                    rowMap.put(entry.getKey(), (DataRow)entry.getValue());
                }
                FormulaUtils.adjustFormulaRows((Data)((Data)thisModel.getPlugins().get(Data.class)), rowMap);
                try {
                    IExpression iExpression = ModelFormulaHandle.getInstance().parse(modelDataContext, backFieldAndBackValueAndType[1], FormulaType.EVALUATE);
                    backWriteValue = FormulaUtils.evaluate((Model)thisModel, (IExpression)iExpression, rowMap);
                }
                catch (Exception e) {
                    throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.parse.error") + e.getMessage());
                }
                if (!(srcData instanceof Map)) continue;
                List oldTableDataRowList = (List)((Map)srcData).get(backTable);
                if (oldTableDataRowList == null || oldTableDataRowList.size() == 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.nofindtablebysrcbill", (Object[])new Object[]{backTable}));
                }
                Map oldTableRow = (Map)oldTableDataRowList.get(0);
                this.backWriteByType(backFieldAndBackValueAndType[2], backField, backWriteValue, oldTableRow, billCode, srcBillCode);
            }
            BillDataDTO newBillData = new BillDataDTO();
            newBillData.setBillCode(srcBillCode);
            newBillData.setBillData((Map)srcData);
            if (sameModule) {
                R updateRes = BillUtils.update((BillDataDTO)newBillData);
                if (updateRes.getCode() == 0) continue;
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.backwritefailed") + updateRes.getMsg());
            }
            new TransactionCommitHandler().transactionCommitHandler(newBillData);
        }
    }

    private void backWriteItemTable(ModelDataContext context, IASTNode evaluateExpressionNode, IASTNode itemTableFieldNode, BillModel thisModel, IASTNode billCodeFieldNode) {
        Object object;
        String billcode = thisModel.getMaster().getString("BILLCODE");
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97\u5f00\u59cb\u6267\u884c\u65b9\u6cd5backWriteItemTable\uff0c\u5355\u636e\u7f16\u53f7\u4e3a\uff1a" + billcode);
        HashMap<String, Map> billCodeWithBackData = new HashMap<String, Map>(8);
        String param3Str = itemTableFieldNode.toString();
        String itemTableCode = param3Str.substring(0, param3Str.indexOf("["));
        DataTable itemTable = thisModel.getTable(itemTableCode);
        ListContainer rows = itemTable.getRows();
        ArrayList<DataRow> datarows = new ArrayList<DataRow>();
        Map params = context.getParams();
        if (!ObjectUtils.isEmpty(params) && params.containsKey(itemTableCode) && !ObjectUtils.isEmpty(object = params.get(itemTableCode)) && object instanceof DataRow) {
            datarows.add((DataRow)object);
        }
        if (datarows.size() != 0) {
            rows = ListContainerImpl.create(datarows);
        }
        Data data = thisModel.getData();
        String multiEvalExpression = evaluateExpressionNode.toString();
        String[] singleEvalExpressions = multiEvalExpression.split("#");
        HashMap<String, Object> expressionMap = new HashMap<String, Object>();
        try {
            IExpression expressionBillCode = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)thisModel), billCodeFieldNode.toString(), FormulaType.EVALUATE);
            expressionMap.put(billCodeFieldNode.toString(), expressionBillCode);
            String[] expressionParam3Str = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)thisModel), param3Str, FormulaType.EVALUATE);
            expressionMap.put(param3Str, expressionParam3Str);
        }
        catch (SyntaxException e1) {
            LOGGER.error("\u516c\u5f0f\u7f16\u8bd1\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570", e1);
            throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.formulaexecuteexception"));
        }
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97,\u68c0\u67e5\u53cd\u5199\u7684\u8868\u662f\u5426\u662f\u4e3b\u8868\u5f00\u59cb");
        boolean checkTable = false;
        for (String singleEvalExpression : singleEvalExpressions) {
            singleEvalExpression = singleEvalExpression.replace("\\", "");
            String[] backFieldAndBackValueAndType = singleEvalExpression.split("&");
            try {
                String formulaStr = backFieldAndBackValueAndType[1];
                IExpression expressionFormulaStr = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)thisModel), formulaStr, FormulaType.EVALUATE);
                expressionMap.put(formulaStr, expressionFormulaStr);
            }
            catch (SyntaxException e) {
                LOGGER.error("\u516c\u5f0f\u7f16\u8bd1\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u7b2c\u4e8c\u4e2a\u53c2\u6570", e);
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.formulaexecuteexception"));
            }
            String backTableAndBackField = backFieldAndBackValueAndType[0].replace("\"", "");
            String backTable = backTableAndBackField.substring(0, backTableAndBackField.indexOf("["));
            if (tableCache.get() != null && tableCache.get().containsKey(backTable)) {
                checkTable = tableCache.get().get(backTable);
                continue;
            }
            checkTable = this.isMasterTable(backTable);
            tableCache.get().put(backTable, checkTable);
        }
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97,\u68c0\u67e5\u53cd\u5199\u7684\u8868\u662f\u5426\u662f\u4e3b\u8868\u7ed3\u675f{}", (Object)checkTable);
        boolean isMasterTable = checkTable;
        rows.forEach((i, row) -> itemTable.getFields().stream().findAny().ifPresent(o -> {
            boolean sameModule = false;
            for (String singleEvalExpression : singleEvalExpressions) {
                String[] backFieldAndBackValueAndType = (singleEvalExpression = singleEvalExpression.replace("\\", "")).split("&");
                if (backFieldAndBackValueAndType.length != 3) {
                    throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.paramcounterror_2"));
                }
                String formulaStr = backFieldAndBackValueAndType[1];
                String backTableAndBackField = backFieldAndBackValueAndType[0].replace("\"", "");
                String backWriteType = backFieldAndBackValueAndType[2].replace("\"", "");
                String backTable = backTableAndBackField.substring(0, backTableAndBackField.indexOf("["));
                String backField = backTableAndBackField.substring(backTableAndBackField.indexOf("[") + 1, backTableAndBackField.length() - 1);
                Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(k -> o.getDefine().getTable().getName(), v -> v));
                FormulaUtils.adjustFormulaRows((Data)data, rowMap);
                try {
                    Object backWriteValue = FormulaUtils.evaluate((Model)thisModel, (IExpression)((IExpression)expressionMap.get(formulaStr)), rowMap);
                    Object backWriteValueItemRowId = FormulaUtils.evaluate((Model)thisModel, (IExpression)((IExpression)expressionMap.get(param3Str)), rowMap);
                    String srcBillCode = (String)FormulaUtils.evaluate((Model)thisModel, (IExpression)((IExpression)expressionMap.get(billCodeFieldNode.toString())), rowMap);
                    sameModule = sameModule ? sameModule : this.isSameModule(thisModel, srcBillCode);
                    BillClient billClient = null;
                    BillDataDTO billDataDTO = new BillDataDTO();
                    billDataDTO.setBillCode(srcBillCode);
                    String defineCode = null;
                    if (defineCache.get().containsKey(srcBillCode)) {
                        defineCode = defineCache.get().get(srcBillCode);
                    }
                    Map srcData = null;
                    if (sameModule) {
                        if (defineCode == null) {
                            defineCode = BillUtils.getDefineCodeByBillCode((String)srcBillCode);
                            defineCache.get().put(srcBillCode, defineCode);
                        }
                        BillModel model = null;
                        if (modelCache.get().containsKey(defineCode)) {
                            model = modelCache.get().get(defineCode);
                        } else {
                            BillContextImpl contextImpl = new BillContextImpl();
                            contextImpl.setDisableVerify(true);
                            model = this.billDefineService.createModel((BillContext)contextImpl, defineCode);
                            modelCache.get().put(defineCode, model);
                        }
                        model.loadByCode(srcBillCode);
                        srcData = model.getData().getTablesData();
                    } else {
                        if (billClient == null) {
                            billClient = VaBillFeignUtil.getBillClientByBillCode((String)srcBillCode, (String)ShiroUtil.getTenantName());
                        }
                        R srcDataRes = billClient.load(billDataDTO);
                        Object dataTemp = srcDataRes.get((Object)"data");
                        srcData = (Map)dataTemp;
                    }
                    List srcTable = (List)srcData.get(backTable);
                    if (billCodeWithBackData.get(srcBillCode) == null) {
                        if (isMasterTable) {
                            Map oldTableRow = (Map)srcTable.get(0);
                            this.backWriteByType(backWriteType, backField, backWriteValue, oldTableRow, billcode, srcBillCode);
                        } else {
                            srcTable.stream().filter(rowDataMap -> rowDataMap.get("ID").toString().equals(String.valueOf(backWriteValueItemRowId))).findFirst().ifPresent(rowDataMap -> {
                                this.backWriteByType(backWriteType, backField, backWriteValue, (Map<String, Object>)rowDataMap, billcode, srcBillCode);
                                this.cacheChangedItemRowDatas(backTable, srcBillCode, (Map<String, Object>)rowDataMap);
                            });
                        }
                        billCodeWithBackData.put(srcBillCode, srcData);
                        continue;
                    }
                    Map stringListMap = (Map)billCodeWithBackData.get(srcBillCode);
                    List srcItemRows = (List)stringListMap.get(backTable);
                    if (isMasterTable) {
                        this.backWriteByType(backWriteType, backField, backWriteValue, (Map)srcItemRows.get(0), billcode, srcBillCode);
                    } else {
                        srcItemRows.stream().filter(rowDataMap -> rowDataMap.get("ID").toString().equals(String.valueOf(backWriteValueItemRowId))).findFirst().ifPresent(rowDataMap -> {
                            this.backWriteByType(backWriteType, backField, backWriteValue, (Map<String, Object>)rowDataMap, billcode, srcBillCode);
                            this.cacheChangedItemRowDatas(backTable, srcBillCode, (Map<String, Object>)rowDataMap);
                        });
                    }
                    billCodeWithBackData.put(srcBillCode, stringListMap);
                }
                catch (SyntaxException e) {
                    LOGGER.error("\u516c\u5f0f\u6267\u884c\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u7b2c\u4e8c\u4e2a\u53c2\u6570\u7684\u7b2c\u4e8c\u4e2a\u5c5e\u6027\u6216\u8005\u7b2c\u4e09\u4e2a\u53c2\u6570", e);
                    throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.formulaexecuteexception"));
                }
            }
        }));
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97\u7ed3\u675f\uff0c\u5355\u636e\u7f16\u53f7\u4e3a\uff1a" + billcode + "\u6267\u884c\u66f4\u65b0\u8bb0\u5f55\u6570" + billCodeWithBackData.size());
        billCodeWithBackData.forEach((billCode, backData) -> {
            String defineCode = defineCache.get().get(billCode);
            BillDataDTO newBillData = new BillDataDTO();
            newBillData.setBillCode(billCode);
            newBillData.setDefineCode(defineCode);
            newBillData.setBillData(backData);
            boolean sameModule = false;
            boolean bl = sameModule = sameModule ? sameModule : this.isSameModule(thisModel, (String)billCode);
            if (sameModule) {
                try {
                    BillModel model = null;
                    if (defineCode != null && modelCache.get().containsKey(defineCode)) {
                        model = modelCache.get().get(defineCode);
                    } else {
                        BillContextImpl contextImpl = new BillContextImpl();
                        contextImpl.setDisableVerify(true);
                        model = this.billDefineService.createModel((BillContext)contextImpl, defineCode);
                        modelCache.get().put(defineCode, model);
                    }
                    model.loadByCode(billCode);
                    model.getData().edit();
                    model.getData().setTablesData(backData);
                    model.getData().save();
                }
                catch (Exception e) {
                    LOGGER.error(billCode + "\u53cd\u5199\u5931\u8d25\uff1a" + e.getMessage(), e);
                    throw new BillException(billCode + BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.backwritefailed") + e.getMessage());
                }
            } else {
                new TransactionCommitHandler().transactionCommitHandler(newBillData);
            }
        });
        LOGGER.info("BackWriteFunction\u53cd\u5199\u51fd\u6570\u8fd0\u7b97\u7ed3\u675f\uff0c\u6267\u884c\u66f4\u65b0\u7ed3\u675f, \u5355\u636e\u7f16\u53f7\u4e3a\uff1a" + billcode);
    }

    private boolean isMasterTable(String tableName) {
        boolean masterTable = true;
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setBiztype(DataModelType.BizType.BILL);
        dataModelDTO.setName(tableName);
        DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
        if (dataModelDO != null) {
            for (DataModelColumn dmc : dataModelDO.getColumns()) {
                if (!dmc.getColumnName().equals("MASTERID")) continue;
                masterTable = false;
                break;
            }
        }
        return masterTable;
    }

    private boolean isSameModule(BillModel thisModel, String billCode) {
        String dModule = thisModel.getDefine().getName().substring(0, thisModel.getDefine().getName().indexOf("_"));
        String srcModule = null;
        String defineCode = defineCache.get().get(billCode);
        if (defineCode != null) {
            srcModule = defineCode.substring(0, defineCode.indexOf("_"));
        } else {
            defineCode = BillUtils.getDefineCodeByBillCode((String)billCode);
            defineCache.get().put(billCode, defineCode);
            srcModule = defineCode.substring(0, defineCode.indexOf("_"));
        }
        return dModule.equals(srcModule);
    }

    private void backWriteByType(String backWriteType, String backField, Object backWriteValue, Map<String, Object> oldTableRow, String billCode, String srcBillCode) {
        if (backWriteType.equals(BackWriteTypeEnum.COVER.code)) {
            if (backWriteValue instanceof ArrayData) {
                ArrayList<Object> list = new ArrayList<Object>();
                ArrayData arrayData = (ArrayData)backWriteValue;
                for (int i = 0; i < arrayData.size(); ++i) {
                    Object fieldValue = arrayData.get(i);
                    if (ObjectUtils.isEmpty(fieldValue)) continue;
                    list.add(fieldValue);
                }
                oldTableRow.put(backField, list);
                return;
            }
            LOGGER.info("\u8986\u76d6\u6a21\u5f0f\uff1a\u5355\u636e{}\u53cd\u5199\u5230\u76ee\u6807\u5355\u636e{}\u5b57\u6bb5{}\uff0c\u76ee\u6807\u5355\u636e\u53cd\u5199\u524d\u5b57\u6bb5\u503c\u4e3a{}\u53cd\u5199\u540e\u5b57\u6bb5\u503c\u4e3a{}", billCode, srcBillCode, backField, oldTableRow.get(backField), backWriteValue);
            oldTableRow.put(backField, backWriteValue);
        } else {
            assert (backWriteType.equals(BackWriteTypeEnum.ACCUMULATION.code));
            Object srcValueObj = oldTableRow.get(backField);
            if (srcValueObj == null) {
                srcValueObj = 0.0;
            }
            Object object = backWriteValue = backWriteValue == null ? Double.valueOf(0.0) : backWriteValue;
            if (srcValueObj instanceof Number && backWriteValue instanceof Number) {
                BigDecimal srcValueBigDecimal = new BigDecimal(srcValueObj.toString());
                BigDecimal bigDecimal = new BigDecimal(backWriteValue.toString());
                BigDecimal backValueBigDecimal = srcValueBigDecimal.add(bigDecimal);
                LOGGER.info("\u7d2f\u52a0\u6a21\u5f0f\uff1a\u5355\u636e{}\u53cd\u5199\u5230\u76ee\u6807\u5355\u636e{}\u5b57\u6bb5{}\uff0c\u76ee\u6807\u5355\u636e\u53cd\u5199\u524d\u5b57\u6bb5\u503c\u4e3a{}\u53cd\u5199\u540e\u5b57\u6bb5\u503c\u4e3a{}", billCode, srcBillCode, backField, oldTableRow.get(backField), backValueBigDecimal.doubleValue());
                oldTableRow.put(backField, backValueBigDecimal.doubleValue());
            } else {
                LOGGER.error("\u975e\u6570\u503c\u578b\u5b57\u6bb5\u65e0\u6cd5\u7d2f\u52a0");
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.fieldunableaccumulation"));
            }
        }
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(256);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("BillCodeField\uff1a").append(DataType.toString((int)6)).append("\uff1b \u5f53\u524d\u5355\u636e\u4e0a\u6e90\u5355\u7684\u5355\u636e\u7f16\u53f7\u5b57\u6bb5\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("BackWriteProperties\uff1a").append(DataType.toString((int)0)).append("\uff1b \u5305\u542b\u4e09\u4e2a\u5b50\u53c2\u6570\uff0c\u6e90\u8868\u5b57\u6bb5\u4e0e\u53d6\u503c\u516c\u5f0f\u4e0e\u53cd\u5199\u7c7b\u578b\uff0c\u53cd\u5199\u591a\u4e2a\u5b57\u6bb5\u7528#\u5206\u9694\u5f00\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SrcField\uff1a").append(DataType.toString((int)3)).append("\uff1b \u6e90\u8868\u5b57\u6bb5--\u6e90\u5355\u8981\u88ab\u53cd\u5199\u7684\u5b57\u6bb5\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("EvaluateExp\uff1a").append(DataType.toString((int)3)).append("\uff1b \u53d6\u503c\u516c\u5f0f--\u4efb\u610f\u7c7b\u578b\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("BackWriteType\uff1a").append(DataType.toString((int)3)).append("\uff1b \u53cd\u5199\u7c7b\u578b--0\u4ee3\u8868\u8986\u76d6\uff0c1\u4ee3\u8868\u7d2f\u52a0\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("ItemTableUniqueFlagField\uff1a").append(DataType.toString((int)0)).append("\uff1b \u53cd\u5199\u5b50\u8868\u65f6\uff0c\u5f53\u524d\u5355\u636e\u4e0a\u6e90\u5355\u5b50\u8868\u884c\u7684UUID\u5b57\u6bb5\uff0c\u975e\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append("null").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b(\u53cd\u5199\u4e3b\u8868)\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6839\u636e\u62a5\u9500\u5355\u62a5\u9500\u4e8b\u7531(MEMO)\u53cd\u5199(\u8986\u76d6)\u7533\u8bf7\u5355\u4e0a\u7684\u4e8b\u7531(MEMO)\u5b57\u6bb5,\u5e76\u6839\u636e\u62a5\u9500\u5355\u4e0a\u7684\u62a5\u9500\u91d1\u989d\u53cd\u5199(\u7d2f\u52a0)\u7533\u8bf7\u5355\u4e0a\u7684\u7533\u8bf7\u91d1\u989d(BILLMONEY)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("BackWriteFunction(FO_EXPENSEBILL_LOANITEM[LOANCODE], \"FO_APPLOANBILL[MEMO]&FO_EXPENSEBILL[MEMO]&0#FO_APPLOANBILL[BILLMONEY]&FO_EXPENSEBILL[BILLMONEY]&1\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u8fd4\u56de\u503c\u4e3anull").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b(\u53cd\u5199\u5b50\u8868)\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6839\u636e\u62a5\u9500\u5355\u6c47\u603b\u5b50\u8868\u4e8b\u7531(MEMO)\u53cd\u5199(\u8986\u76d6)\u7533\u8bf7\u5355\u5b50\u8868\u4e0a\u7684\u4e8b\u7531(MEMO)\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("BackWriteFunction(FO_EXPENSEBILL_ITEM[REFBILLCODE],\"FO_APPLOANBILL_ITEM[MEMO]&FO_EXPENSEBILL_ITEM[MEMO]&0\", FO_EXPENSEBILL_ITEM[SRCBILLID])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u8fd4\u56de\u503c\u4e3anull");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0) + "\uff1b");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("BillCodeField", DataType.toString((int)6), "\u5f53\u524d\u5355\u636e\u4e0a\u6e90\u5355\u7684\u5355\u636e\u7f16\u53f7\u5b57\u6bb5\u3002", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("BackWriteProperties", DataType.toString((int)0), "\u5305\u542b\u4e09\u4e2a\u5b50\u53c2\u6570\uff0c\u6e90\u8868\u5b57\u6bb5\u4e0e\u53d6\u503c\u516c\u5f0f\u4e0e\u53cd\u5199\u7c7b\u578b\uff0c\u53cd\u5199\u591a\u4e2a\u5b57\u6bb5\u7528#\u5206\u9694\u5f00\uff0c\u5fc5\u9700\u3002SrcField\uff1a\u6570\u503c\uff1b \u6e90\u8868\u5b57\u6bb5--\u6e90\u5355\u8981\u88ab\u53cd\u5199\u7684\u5b57\u6bb5\uff0c\u5fc5\u9700\u3002EvaluateExp\uff1a\u6570\u503c\uff1b \u53d6\u503c\u516c\u5f0f--\u4efb\u610f\u7c7b\u578b\uff0c\u5fc5\u9700\u3002BackWriteType\uff1a\u6570\u503c\uff1b \u53cd\u5199\u7c7b\u578b--0\u4ee3\u8868\u8986\u76d6\uff0c1\u4ee3\u8868\u7d2f\u52a0\uff0c\u5fc5\u9700\u3002", Boolean.valueOf(true));
        ParameterDescription parameterDescription5 = new ParameterDescription("ItemTableUniqueFlagField", DataType.toString((int)0), "\u53cd\u5199\u5b50\u8868\u65f6\uff0c\u5f53\u524d\u5355\u636e\u4e0a\u6e90\u5355\u5b50\u8868\u884c\u7684UUID\u5b57\u6bb5\u3002", Boolean.valueOf(false));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription5);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u52a8\u652f\u7533\u8bf7\u5355\u4e0a\u6709\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u91d1\u989d\uff0c\u65b9\u6848\u8bc4\u4ef7\u5355\u4fdd\u5b58\u540e\u53c8\u4f1a\u5360\u7528\u5230\u8bc4\u4ef7\u91d1\u989d\uff0c\u6240\u4ee5\u8981\u60f3\u52a8\u652f\u7533\u8bf7\u5355\u4e0a\u7684\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u91d1\u989d\u6b63\u786e\uff0c\u5c31\u8981\u5728\u65b9\u6848\u8bc4\u4ef7\u5355\u4e0a\u6dfb\u52a0\u4fdd\u5b58\u540e\u53cd\u5199\u65f6\u673a\uff0c\u5c06\u672c\u6b21\u5360\u7528\u7684\u91d1\u989d\u52a0\u4e0a\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u7684\u53cd\u5199\u5230\u52a8\u652f\u7533\u8bf7\u5355\u7684\u8be5\u5b57\u6bb5\u4e0a\uff0c\u4f46\u662f\u5355\u636e\u5e9f\u6b62\u540e\u4e5f\u8981\u66f4\u65b0\u52a8\u652f\u7533\u8bf7\u5355\u4e0a\u7684\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u91d1\u989d\u3002", "BackWriteFunction(FO_BPS_EVALREQ_ITEM[BPS_APPBILLCODE],\"FO_APPLOANBILL_ITEM[BPS_ADDEVALMONEY]&FO_BPS_EVALREQ_ITEM[BPS_ADDEVALMONEY]+FO_BPS_EVALREQ_ITEM[BPS_EVALMONEY]&0\",FO_BPS_EVALREQ_ITEM[BPS_APPBILLITEMID])", "", "\u5728\u65b9\u6848\u8bc4\u4ef7\u5355\u53cd\u5199\u89c4\u5219\u4fdd\u5b58\u540e\u548c\u5e9f\u6b62\u540e\u6dfb\u52a0\u516c\u5f0f\uff0c\u6839\u636e\u65b9\u6848\u8bc4\u4ef7\u5355\u5b50\u8868\u4e0a\u5f15\u7528\u7684\u52a8\u652f\u7533\u8bf7\u5355\u5355\u53f7\uff08FO_BPS_EVALREQ_ITEM[BPS_APPBILLCODE]\uff09\uff0c\u7528\u65b9\u6848\u8bc4\u4ef7\u5355\u4e0a\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u91d1\u989d+\u672c\u6b21\u8bc4\u4ef7\u5360\u7528\u91d1\u989d\uff08FO_BPS_EVALREQ_ITEM[BPS_ADDEVALMONEY]+FO_BPS_EVALREQ_ITEM[BPS_EVALMONEY]\uff09\u53cd\u5199\u52a8\u652f\u7533\u8bf7\u5355\u5b50\u8868\u7684\u7d2f\u8ba1\u8bc4\u4ef7\u5360\u7528\u91d1\u989d\uff08FO_APPLOANBILL_ITEM[BPS_ADDEVALMONEY]\uff09\uff0c\u8986\u76d6\u53cd\u5199\u3002");
        FormulaExample formulaExample1 = new FormulaExample("\u65b9\u6848\u8bc4\u4ef7\u5355\u5f15\u7528\u4e0b\u62e8\u5355\u3001\u5206\u914d\u5355\u3001\u5151\u73b0\u660e\u7ec6\u8868\u7b49\u5355\u636e\u65f6\uff0c\u65b9\u6848\u8bc4\u4ef7\u5355\u5355\u636e\u63d0\u4ea4\u540e\u3001\u5ba1\u6279\u540e\u6216\u9a73\u56de\u540e\uff0c\u540c\u6b65\u66f4\u65b0\u5f15\u7528\u7684\u4e0b\u62e8\u5355\u3001\u5206\u914d\u5355\u7b49\u5355\u636e\u7684\u5355\u636e\u72b6\u6001\uff0c\u907f\u514d\u91cd\u590d\u5ba1\u6279\u3002", "BackWriteFunction(FO_BPS_EVALBILL[BPS_CASHBILLCODE],\"FO_BPS_CASHBILL[BILLSTATE]&FO_BPS_EVALBILL[BILLSTATE]&0\")", "", "\u5728\u65b9\u6848\u8bc4\u4ef7\u5355\u53cd\u5199\u89c4\u5219\u63d0\u4ea4\u540e\u6216\u5ba1\u6279\u901a\u8fc7\u540e\u6dfb\u52a0\u516c\u5f0f\uff0c\u6839\u636e\u65b9\u6848\u8bc4\u4ef7\u5355\u4e0a\u5f15\u7528\u7684\u5151\u73b0\u660e\u7ec6\u5355\u5355\u53f7\uff08FO_BPS_EVALBILL[BPS_CASHBILLCODE]\uff09\uff0c\u7528\u65b9\u6848\u8bc4\u4ef7\u5355\u5355\u636e\u72b6\u6001\uff08FO_BPS_EVALBILL[BILLSTATE]\uff09\u53cd\u5199\u5151\u73b0\u660e\u7ec6\u5355\u5355\u636e\u72b6\u6001\uff08FO_BPS_CASHBILL[BILLSTATE]\uff09\uff0c\u8986\u76d6\u53cd\u5199\u3002");
        FormulaExample formulaExample2 = new FormulaExample("\u5ba1\u6279\u901a\u8fc7\u540e\u53cd\u5199\u5408\u540c\u5f15\u7528\u72b6\u6001\u3002", "BackWriteFunction(FO_ZCL_HTXX[ZCL_SRCBILLCODE],\"FO_ZCL_HTZB[ZCL_REFERENCEFLAG]&true&0\",FO_ZCL_HTXX[ZCL_SRCITEMID])", "", "\u5728\u5ba1\u6279\u901a\u8fc7\u540e\u53cd\u5199\u65f6\u673a\u6dfb\u52a0\u516c\u5f0f\uff0c\u6839\u636e\u62a5\u9500\u5355\u7684\u5408\u540c\u5b50\u8868\uff08FO_ZCL_HTXX\uff09\u7684\uff08ZCL_SRCITEMID\uff09\u5b57\u6bb5\u627e\u5230\u5f15\u7528\u7684\u5408\u540c\u5355\u636e\u7684\u5b50\u8868\u8bb0\u5f55\uff0c\u66f4\u65b0\u5408\u540c\u5f15\u7528\u72b6\u6001\uff08ZCL_REFERENCEFLAG\uff09\u5b57\u6bb5\u4e3atrue\n\u540c\u65f6\u4e00\u8d77\u4f7f\u7528\u7684\u8fd8\u6709\u5de5\u4f5c\u6d41\u9a73\u56de\u540e\u548c\u5355\u636e\u5e9f\u6b62\u540e\u65f6\u673a\uff0c\u5c06\u5408\u540c\u5f15\u7528\u72b6\u6001\uff08ZCL_REFERENCEFLAG\uff09\u5b57\u6bb5\u66f4\u65b0\u4e3afalse");
        formulaExamples.add(formulaExample);
        formulaExamples.add(formulaExample1);
        formulaExamples.add(formulaExample2);
        formulaDescription.setExamples(formulaExamples);
        ArrayList<String> notes = new ArrayList<String>();
        notes.add("\u53cd\u5199\u516c\u5f0f\u4ec5\u652f\u6301\u53cd\u5199\u6e90\u5355\uff0c\u4e0d\u652f\u6301\u53cd\u5199\u672c\u5355");
        notes.add("\u53cd\u5199\u516c\u5f0f\u9700\u7ed3\u5408\u53cd\u5199\u89c4\u5219\u4f7f\u7528\uff0c\u4e14\u4e00\u822c\u9700\u6b63\u5411\u64cd\u4f5c\u548c\u9006\u5411\u64cd\u4f5c\u4e00\u8d77\u4f7f\u7528\u3002\u4f8b\u5982\u4fdd\u5b58\u540e\u66f4\u65b0\u5b57\u6bb5\u4e3atrue\uff0c\u5219\u5220\u9664\u540e\u3001\u5e9f\u6b62\u540e\u66f4\u65b0\u4e3afalse\uff1b\u540c\u7406\u63d0\u4ea4\u540e\u548c\u9a73\u56de\u540e\u914d\u5408\u4f7f\u7528");
        formulaDescription.setNotes(notes);
        return formulaDescription;
    }

    private void cacheChangedItemRowDatas(String tableName, String srcBillCode, Map<String, Object> rowData) {
        String redisDataKey = "backWriteFunction:billData:" + srcBillCode;
        String redisDataValue = (String)this.stringRedisTemplate.opsForValue().get((Object)redisDataKey);
        if (StringUtils.hasText(redisDataValue)) {
            Map billDataMap = JSONUtil.parseMap((String)redisDataValue);
            Object rowDatas = billDataMap.get(tableName);
            if (rowDatas == null) {
                ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                list.add(rowData);
                billDataMap.put(tableName, rowDatas);
            } else {
                List list = (List)rowDatas;
                List exsitRowList = list.stream().filter(e -> e.get("ID").toString().equals(rowData.get("ID").toString())).collect(Collectors.toList());
                if (exsitRowList.isEmpty()) {
                    list.add(rowData);
                } else {
                    Map existRowData = (Map)exsitRowList.get(0);
                    existRowData.putAll(rowData);
                }
            }
            this.stringRedisTemplate.opsForValue().set((Object)redisDataKey, (Object)JSONUtil.toJSONString((Object)billDataMap), 30L, TimeUnit.SECONDS);
        } else {
            HashMap<String, List> billDataMap = new HashMap<String, List>();
            List rowDatas = billDataMap.computeIfAbsent(tableName, e -> new ArrayList());
            rowDatas.add(rowData);
            this.stringRedisTemplate.opsForValue().set((Object)redisDataKey, (Object)JSONUtil.toJSONString(billDataMap), 30L, TimeUnit.SECONDS);
        }
    }

    class TransactionCommitHandler {
        TransactionCommitHandler() {
        }

        private void transactionCommitHandler(final BillDataDTO newBillData) {
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                    public void afterCommit() {
                        TransactionCommitHandler.this.updateBill(newBillData);
                    }
                });
            } else {
                this.updateBill(newBillData);
            }
        }

        private void updateBill(BillDataDTO newBillData) {
            BillClient billClient = null;
            if (billClient == null) {
                billClient = VaBillFeignUtil.getBillClientByBillCode((String)newBillData.getBillCode(), (String)ShiroUtil.getTenantName());
            }
            R srcDataRes = billClient.load(newBillData);
            Object srcData = srcDataRes.get((Object)"data");
            this.getChangedItemRowDataByCache(newBillData);
            this.changeVer(newBillData, srcData);
            R updateRes = billClient.update(newBillData);
            if (updateRes.getCode() != 0) {
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.backwritefunction.backwritefailed") + updateRes.getMsg());
            }
        }

        private void changeVer(BillDataDTO newBillData, Object srcData) {
            Map billData = newBillData.getBillData();
            for (Map.Entry data : billData.entrySet()) {
                List dbRowData = (List)((Map)srcData).get(data.getKey());
                for (Map dbData : dbRowData) {
                    block2: for (Map.Entry row : dbData.entrySet()) {
                        if (!((String)row.getKey()).equals("VER")) continue;
                        for (int i = 0; i < ((List)data.getValue()).size(); ++i) {
                            if (!((Map)((List)data.getValue()).get(i)).get("ID").toString().equals(dbData.get("ID").toString())) continue;
                            ((Map)((List)data.getValue()).get(i)).put("VER", row.getValue());
                            continue block2;
                        }
                    }
                }
            }
        }

        private void getChangedItemRowDataByCache(BillDataDTO newBillData) {
            String redisDataKey = "backWriteFunction:billData:" + newBillData.getBillCode();
            String redisDataValue = (String)BackWriteFunction.this.stringRedisTemplate.opsForValue().get((Object)redisDataKey);
            if (!StringUtils.hasText(redisDataValue)) {
                return;
            }
            Map billTableDatas = newBillData.getBillData();
            Map billDataMap = JSONUtil.parseMap((String)redisDataValue);
            for (Map.Entry cacheTableDataEntry : billDataMap.entrySet()) {
                List cacheTableRowDatas = (List)cacheTableDataEntry.getValue();
                String tableName = (String)cacheTableDataEntry.getKey();
                List tableDatas = (List)billTableDatas.get(tableName);
                if (cacheTableRowDatas == null || tableDatas == null) continue;
                for (Map cacheBillData : cacheTableRowDatas) {
                    for (Map billData : tableDatas) {
                        if (!billData.get("ID").toString().equals(cacheBillData.get("ID").toString())) continue;
                        billData.putAll(cacheBillData);
                    }
                }
            }
        }
    }

    private static enum BackWriteTypeEnum {
        COVER("0"),
        ACCUMULATION("1");

        String code;

        private BackWriteTypeEnum(String code) {
            this.code = code;
        }
    }
}

