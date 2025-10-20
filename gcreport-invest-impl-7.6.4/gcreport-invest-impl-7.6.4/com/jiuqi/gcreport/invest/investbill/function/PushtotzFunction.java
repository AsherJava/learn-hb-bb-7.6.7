/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.value.NamedContainerImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.invest.investbill.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class PushtotzFunction
extends AdvanceFunction
implements INrFunction {
    private static Logger LOGGER = LoggerFactory.getLogger(PushtotzFunction.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private BillDefineService billDefineService;

    public PushtotzFunction() {
        this.parameters().add(new Parameter("floatTableName2BillDefineNameRelation", 6, "\u63a8\u6570\u6d6e\u52a8\u884c\u548c\u76ee\u6807\u5355\u636e\u5b9a\u4e49\u5bf9\u5e94\u5173\u7cfb\u3002\u683c\u5f0f\u662f\uff1a\u6d6e\u52a8\u884c\u5b58\u50a8\u8868\u6807\u8bc6:\u5355\u636e\u5b9a\u4e49\u6807\u8bc6\u5bf9\u5e94\u7684UNIQUECODE\u503c\u3002", false));
        this.parameters().add(new Parameter("pushType", 6, "\u53c2\u65702\uff1a\u63a8\u6570\u65b9\u5f0f\uff0c1:\u63a8\u4e3b\u8868\u6570\u636e\u548c\u5b50\u8868\u6570\u636e\uff0c2:\u53ea\u63a8\u9001\u5b50\u8868\u6570\u636e\u3002", false));
        this.parameters().add(new Parameter("billUniqueIndexFields", 6, "\u53c2\u65703\uff1a\u8bc6\u522b\u4e1a\u52a1\u5355\u636e\u552f\u4e00\u7ec4\u5408\u4e3b\u952e\u7684\u5b57\u6bb5\u3002\u5982\u679c\u6709\u591a\u4e2a\u7ec4\u5408\u4e1a\u52a1\u4e3b\u952e\u5b57\u6bb5\uff0c\u7528\u201d,\u201d\u9694\u5f00\u3002\u6295\u8d44\u53f0\u8d26\u683c\u5f0f\u5982\uff1aUNITCODE,INVESTEDUNIT ; \u6295\u8d44\u53f0\u8d26\u683c\u5f0f\u5982\uff1aUNITCODE\u3001OPPUNITCODE", false));
        this.parameters().add(new Parameter("float2BillMasterFieldRelation", 6, "\u53c2\u65704\uff1a\u6d6e\u52a8\u884c\u4e2d\u6307\u6807\u548c\u76ee\u6807\u5355\u636e\u4e3b\u8868\u5bf9\u5e94\u5173\u7cfb\u3002\u683c\u5f0f\u662f\u201d\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5\u3002", false));
        this.parameters().add(new Parameter("float2BillDetailFieldRelation", 6, "\u53c2\u65705\uff1a\u6d6e\u52a8\u884c\u4e2d\u6307\u6807\u548c\u76ee\u6807\u53f0\u8d26\u5b50\u8868\u5bf9\u5e94\u5173\u7cfb\u3002\u5982\u679c\u6709\u591a\u4e2a\u5b50\u8868\uff0c\u7528\u201d;\u201d\u9694\u5f00\u3002\u591a\u4e2a\u5b50\u8868\u5fc5\u987b\u6309\u7167\u987a\u5e8f\u8bbe\u7f6e\u3002\u683c\u5f0f\u662f\uff1a\u201d\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u5b50\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5;\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u5b50\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5,\u6d6e\u52a8\u884c\u6307\u6807:\u5355\u636e\u4e3b\u8868\u5b57\u6bb5\u3002", false));
        this.parameters().add(new Parameter("floatFilterCondtion", 6, "\u53c2\u65706\uff1a\u6d6e\u52a8\u884c\u8fc7\u6ee4\u6761\u4ef6\uff0c\u6ee1\u8db3\u8fc7\u6ee4\u6761\u4ef6\u7684\u6570\u636e\u624d\u63a8\u9001\u5230\u5355\u636e\uff0c\u975e\u5fc5\u586b\u3002", true));
        this.parameters().add(new Parameter("updateByChangeDateFlag", 1, "\u53c2\u65707\uff1a\u6295\u8d44\u53f0\u8d26\u5b50\u8868\u662f\u5426\u9700\u8981\u6839\u636e\u3010\u53d8\u52a8\u573a\u666f+\u53d8\u52a8\u65f6\u671f\u3011\u5224\u65ad\u66f4\u65b0/\u65b0\u589e\uff0c\u975e\u5fc5\u586b\u3002", true));
    }

    public String name() {
        return "pushtotz";
    }

    public String title() {
        return "\u6d6e\u52a8\u884c\u63a8\u6570\u5230\u53f0\u8d26";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 1;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Boolean evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        ExecutorContext executorContext = queryContext.getExeContext();
        DimensionValueSet dimensionValueSet = queryContext.getCurrentMasterKey();
        String floatTableName2BillDefineNameRelation = (String)parameters.get(0).evaluate(null);
        String[] floatTableName2BillDefineNameRelationArr = floatTableName2BillDefineNameRelation.split(":");
        String floatTableName = floatTableName2BillDefineNameRelationArr[0];
        String billDefineName = floatTableName2BillDefineNameRelationArr[1];
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo((String)billDefineName);
        if (billInfoVo == null || ObjectUtils.isEmpty(billInfoVo.getMasterTableName())) {
            throw new SyntaxException("\u6d6e\u52a8\u884c\u63a8\u6570\u5230\u53f0\u8d26-\u53c2\u65701\u76ee\u6807\u5355\u636e\u5b9a\u4e49" + billDefineName + "\u5728\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\u3002");
        }
        String float2BillMasterFieldRelation = ConverterUtils.getAsString((Object)parameters.get(3).evaluate(null), (String)"");
        HashMap bill2FloatMasterFieldMap = new HashMap();
        if (!ObjectUtils.isEmpty(float2BillMasterFieldRelation)) {
            Stream.of(float2BillMasterFieldRelation.split(",")).forEach(singleMasterFieldRelation -> {
                String[] singleMasterFieldRelationArr = singleMasterFieldRelation.split(":");
                String floatMasterField = singleMasterFieldRelationArr[0];
                String billMasterField = singleMasterFieldRelationArr[1];
                bill2FloatMasterFieldMap.put(billMasterField, floatMasterField);
            });
        }
        String multiFloat2BillDetailFieldRelation = ConverterUtils.getAsString((Object)parameters.get(4).evaluate(null), (String)"");
        String[] float2BillDetailFieldRelations = multiFloat2BillDetailFieldRelation.split(";");
        ArrayList multiBill2FloatItemFieldMaps = new ArrayList();
        for (int i = 0; i < float2BillDetailFieldRelations.length; ++i) {
            String float2BillDetailFieldRelation = float2BillDetailFieldRelations[i];
            if (StringUtils.isEmpty((String)float2BillDetailFieldRelation)) continue;
            HashMap bill2FloatItemFieldMap = new HashMap();
            if (!ObjectUtils.isEmpty(float2BillDetailFieldRelation)) {
                Stream.of(float2BillDetailFieldRelation.split(",")).forEach(singleItemFieldRelation -> {
                    String[] singleItemFieldRelationArr = singleItemFieldRelation.split(":");
                    String floatItemField = singleItemFieldRelationArr[0];
                    String billItemField = singleItemFieldRelationArr[1];
                    bill2FloatItemFieldMap.put(billItemField, floatItemField);
                });
            }
            multiBill2FloatItemFieldMaps.add(bill2FloatItemFieldMap);
        }
        String floatFilterCondtion = ConverterUtils.getAsString((Object)parameters.get(5).evaluate(null), (String)"");
        TableModelRunInfo tableInfo = this.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(floatTableName);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        this.dataAccessProvider.newDataAssist(executorContext);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setMasterKeys(dimensionValueSet);
        dataQuery.setRowFilter(floatFilterCondtion);
        ArrayList<IDataRow> floatDataRows = new ArrayList<IDataRow>();
        try {
            Collection fieldDefines = tableInfo.getColumnFieldMap().values();
            fieldDefines.forEach(fieldDefine -> dataQuery.addColumn(fieldDefine));
            IDataTable dataTable = dataQuery.executeQuery(executorContext);
            int totalCount = dataTable.getTotalCount();
            if (totalCount == 0) {
                return true;
            }
            for (int i = 0; i < totalCount; ++i) {
                IDataRow floatDataRow2 = dataTable.getItem(i);
                floatDataRows.add(floatDataRow2);
            }
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        String pushType = ConverterUtils.getAsString((Object)parameters.get(1).evaluate(null), (String)"");
        String billUniqueIndexFields = ConverterUtils.getAsString((Object)parameters.get(2).evaluate(null), (String)"");
        if (ObjectUtils.isEmpty(billUniqueIndexFields)) {
            throw new SyntaxException("\u6d6e\u52a8\u884c\u63a8\u6570\u5230\u53f0\u8d26\u51fd\u6570-\u53c2\u65704\u4e1a\u52a1\u552f\u4e00\u7ec4\u5408\u4e3b\u952e\u5b57\u6bb5\u4fe1\u606f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        List<String> billUniqueIndexFieldNames = Arrays.asList(billUniqueIndexFields.split(","));
        boolean containsAll = bill2FloatMasterFieldMap.keySet().containsAll(billUniqueIndexFieldNames);
        if (!containsAll) {
            throw new SyntaxException("\u6d6e\u52a8\u884c\u63a8\u6570\u5230\u53f0\u8d26-\u53c2\u65703\u4e1a\u52a1\u552f\u4e00\u7ec4\u5408\u4e3b\u952e\u5b57\u6bb5\u4fe1\u606f\u5fc5\u987b\u5728\u53c2\u65704\u4e3b\u8868\u5b57\u6bb5\u6620\u5c04\u90fd\u5b58\u5728\u3002");
        }
        HashMap<String, List> billUniqueIndexGroupKey2FloatDataRowsMap = new HashMap<String, List>();
        floatDataRows.forEach(floatDataRow -> {
            StringBuilder billUniqueIndexGroupKeyBuilder = new StringBuilder();
            for (int i = 0; i < billUniqueIndexFieldNames.size(); ++i) {
                String floatField = (String)bill2FloatMasterFieldMap.get(billUniqueIndexFieldNames.get(i));
                Object billUniqueFieldValue = this.getFloatRowFieldValueByFieldName(tableInfo, (IDataRow)floatDataRow, floatField);
                billUniqueIndexGroupKeyBuilder.append("-").append(billUniqueFieldValue);
            }
            String billUniqueIndexGroupKey = billUniqueIndexGroupKeyBuilder.toString();
            if (billUniqueIndexGroupKey2FloatDataRowsMap.get(billUniqueIndexGroupKey) == null) {
                billUniqueIndexGroupKey2FloatDataRowsMap.put(billUniqueIndexGroupKey, new ArrayList());
            }
            ((List)billUniqueIndexGroupKey2FloatDataRowsMap.get(billUniqueIndexGroupKey)).add(floatDataRow);
        });
        Boolean updateByChangeDateFlag = ConverterUtils.getAsBoolean((Object)(parameters.size() < 7 ? Boolean.valueOf(true) : parameters.get(6).evaluate(null)), (Boolean)true);
        ArrayList billModels = new ArrayList();
        billUniqueIndexGroupKey2FloatDataRowsMap.forEach((billUniqueIndexGroupKey, groupFloatDataRows) -> {
            BillModelImpl billModel;
            if (CollectionUtils.isEmpty((Collection)groupFloatDataRows)) {
                return;
            }
            IDataRow floatDataRow = (IDataRow)groupFloatDataRows.get(0);
            HashMap<String, Object> billUniqueField2ValueMap = new HashMap<String, Object>();
            for (int i = 0; i < billUniqueIndexFieldNames.size(); ++i) {
                String billUniqueField2 = (String)billUniqueIndexFieldNames.get(i);
                String floatField = (String)bill2FloatMasterFieldMap.get(billUniqueField2);
                Object billUniqueFieldValue2 = this.getFloatRowFieldValueByFieldName(tableInfo, floatDataRow, floatField);
                billUniqueField2ValueMap.put(billUniqueField2, billUniqueFieldValue2);
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select distinct billcode ").append(" from ").append(billInfoVo.getMasterTableName());
            sqlBuilder.append(" where 1=1 ");
            ArrayList params = new ArrayList();
            billUniqueField2ValueMap.forEach((billUniqueField, billUniqueFieldValue) -> {
                sqlBuilder.append(" and ").append((String)billUniqueField).append("=?");
                params.add(billUniqueFieldValue);
            });
            List billCodes = EntNativeSqlDefaultDao.getInstance().selectFirstList(String.class, sqlBuilder.toString(), params.toArray());
            if (CollectionUtils.isEmpty((Collection)billCodes)) {
                billModel = this.createBillModel(billDefineName);
                String billCode = InvestBillTool.getBillCode((String)billDefineName, (String)((String)billModel.getMaster().getValue("UNITCODE")));
                billModel.getMaster().setValue("BILLCODE", (Object)billCode);
            } else if (billCodes.size() == 1) {
                billModel = this.loadBillModel((String)billCodes.get(0), billDefineName);
                billModel.edit();
            } else {
                throw new BusinessRuntimeException("\u53c2\u65703\u552f\u4e00\u7ec4\u5408\u4e3b\u952e\u7ef4\u5ea6\u4e0b\u5339\u914d\u5230\u591a\u6761\u5355\u636e\u53f0\u8d26\u6570\u636e\uff0c\u65e0\u6cd5\u6267\u884c\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\u3002" + billCodes.toString());
            }
            if ("1".equals(pushType)) {
                this.fillMasterData(billModel, tableInfo, (List<IDataRow>)groupFloatDataRows, bill2FloatMasterFieldMap);
                this.fillDetailData(updateByChangeDateFlag, billModel, tableInfo, (List<IDataRow>)groupFloatDataRows, multiBill2FloatItemFieldMaps);
            } else if ("2".equals(pushType)) {
                this.fillDetailData(updateByChangeDateFlag, billModel, tableInfo, (List<IDataRow>)groupFloatDataRows, multiBill2FloatItemFieldMaps);
            } else {
                throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u63a8\u6570\u5230\u53f0\u8d26pushType\u53c2\u6570\u4e0d\u6b63\u786e\u3002");
            }
            billModels.add(billModel);
        });
        if (!CollectionUtils.isEmpty(billModels)) {
            billModels.stream().forEach(model -> this.saveBillModel((BillModelImpl)model));
        }
        return true;
    }

    private DefinitionsCache getDefinitionsCache() throws ParseException {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        DefinitionsCache definitionsCache = new DefinitionsCache(context);
        return definitionsCache;
    }

    private Object getFloatRowFieldValueByFieldName(TableModelRunInfo tableInfo, IDataRow floatDataRow, String floatFieldName) {
        ColumnModelDefine columnModelDefine = tableInfo.getFieldByName(floatFieldName);
        if (columnModelDefine == null) {
            LOGGER.warn(tableInfo.getTableModelDefine().getName() + "\u8868\u627e\u4e0d\u5230\u6807\u8bc6\u4e3a" + floatFieldName + "\u7684\u6307\u6807\u4fe1\u606f\uff0c\u9ed8\u8ba4\u76f4\u63a5\u5c06\u6620\u5c04\u503c[" + floatFieldName + "]\u8d4b\u503c\u5230\u5355\u636e\u3002");
            return floatFieldName;
        }
        FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(columnModelDefine);
        AbstractData value = floatDataRow.getValue(fieldDefine);
        if (AbstractData.isNull((AbstractData)value)) {
            return null;
        }
        Object fieldValue = value.getAsObject();
        return fieldValue;
    }

    private BillModelImpl loadBillModel(String billCode, String defineCode) {
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, defineCode);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.loadByCode(billCode);
        return model;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveBillModel(BillModelImpl billModel) {
        List checkMessages = billModel.getRuler().getRulerExecutor().beforeAction("save");
        if (checkMessages != null && checkMessages.size() > 0) {
            List uniqueCheckMessages = checkMessages.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<CheckResult>(Comparator.comparing(CheckResult::getCheckMessage))), ArrayList::new));
            StringBuffer saveErrMsg = new StringBuffer();
            for (CheckResult checkMessage : uniqueCheckMessages) {
                saveErrMsg.append(checkMessage.getCheckMessage() + "\u3001");
            }
            throw new BillException("\u53f0\u8d26\u5355\u636e\u4fdd\u5b58\u524d\u6821\u9a8c\u672a\u901a\u8fc7\uff0c" + saveErrMsg.toString());
        }
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        ActionResponse response = new ActionResponse();
        try {
            SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
            billModel.executeAction((Action)saveAction, request, response);
        }
        catch (Exception e) {
            LOGGER.error(String.format("\u6295\u8d44\u5355\u4f4d: %1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s, \u63a8\u9001\u53f0\u8d26\u5355\u636e\u5f02\u5e38", billModel.getMaster().getValue("UNITCODE"), billModel.getMaster().getValue("INVESTEDUNIT")) + e.getMessage(), e);
        }
        finally {
            billModel.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    private BillModelImpl fillMasterData(BillModelImpl billModel, TableModelRunInfo tableInfo, List<IDataRow> groupFloatDataRows, Map<String, String> bill2FloatMasterFieldMap) {
        IDataRow dataRow = groupFloatDataRows.get(0);
        NamedContainerImpl fields = billModel.getData().getMasterTable().getDefine().getFields();
        for (int i = 0; i < fields.size(); ++i) {
            String floatMasterField;
            DataFieldDefineImpl field = (DataFieldDefineImpl)fields.get(i);
            if (ObjectUtils.isEmpty(field.getFieldName()) || "ID".equals(field) || "VER".equals(field) || "DEFINECODE".equals(field) || "BILLCODE".equals(field) || "CHECKUSER".equals(field) || "BILLDATE".equals(field) || "CREATETIME".equals(field) || "BILLSTATE".equals(field) || "DEFINECODE".equals(field) || (floatMasterField = bill2FloatMasterFieldMap.get(field.getFieldName())) == null) continue;
            Object fieldValue = this.getFloatRowFieldValueByFieldName(tableInfo, dataRow, floatMasterField);
            billModel.getMaster().setValue(field.getFieldName(), fieldValue);
        }
        billModel.getMaster().setValue("VER", (Object)OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
        return billModel;
    }

    private BillModelImpl fillDetailData(Boolean updateByChangeDateFlag, BillModelImpl billModel, TableModelRunInfo tableInfo, List<IDataRow> groupFloatDataRows, List<Map<String, String>> multiBill2FloatItemFieldMap) {
        if (Boolean.TRUE.equals(updateByChangeDateFlag)) {
            this.fillDetailDataByChangeDate(billModel, tableInfo, groupFloatDataRows, multiBill2FloatItemFieldMap);
            return billModel;
        }
        this.fillDetailData(billModel, tableInfo, groupFloatDataRows, multiBill2FloatItemFieldMap);
        return billModel;
    }

    private BillModelImpl fillDetailDataByChangeDate(BillModelImpl billModel, TableModelRunInfo tableInfo, List<IDataRow> groupFloatDataRows, List<Map<String, String>> multiBill2FloatItemFieldMap) {
        List detailTables = billModel.getDefine().getData().getTableList().stream().filter(dataTableDefineImpl -> !dataTableDefineImpl.getTableName().equalsIgnoreCase(billModel.getMasterTable().getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(detailTables)) {
            return billModel;
        }
        for (int i = 0; i < multiBill2FloatItemFieldMap.size(); ++i) {
            Map<String, String> bill2FloatItemFieldMap = multiBill2FloatItemFieldMap.get(i);
            DataTableDefineImpl detailTable = (DataTableDefineImpl)detailTables.get(i);
            List oldDetailRowsData = billModel.getTable(detailTable.getTableName()).getRowsData();
            LinkedHashMap oldDetailRowsByChangeDate = oldDetailRowsData.stream().collect(Collectors.groupingBy(oldDetailRow -> {
                String changeScenario = ConverterUtils.getAsString(oldDetailRow.get("CHANGESCENARIO"));
                String yyyyMMddChangeDate = null;
                Object changeDate = oldDetailRow.get("CHANGEDATE");
                if (!ObjectUtils.isEmpty(changeDate)) {
                    yyyyMMddChangeDate = DateUtils.format((Date)((Date)changeDate), (String)DateUtils.DEFAULT_DATE_FORMAT);
                }
                return changeScenario + "-" + yyyyMMddChangeDate;
            }, LinkedHashMap::new, Collectors.mapping(Function.identity(), Collectors.toList())));
            LinkedHashMap groupFloatDataRowsByChangeDate = groupFloatDataRows.stream().collect(Collectors.toMap(groupFloatDataRow -> {
                IFieldsInfo rowFieldsInfo = groupFloatDataRow.getFieldsInfo();
                int fieldCount = rowFieldsInfo.getFieldCount();
                String changeScenario = null;
                String yyyyMMddChangeDate = null;
                for (int index = 0; index < fieldCount; ++index) {
                    AbstractData value;
                    FieldDefine field = rowFieldsInfo.getFieldDefine(index);
                    if (field == null) continue;
                    String fieldCode = field.getCode().toUpperCase();
                    if (ConverterUtils.getAsString(bill2FloatItemFieldMap.get("CHANGESCENARIO"), (String)"null").equals(fieldCode)) {
                        value = groupFloatDataRow.getValue(field);
                        if (value.isNull) continue;
                        changeScenario = value.getAsString();
                        continue;
                    }
                    if (!ConverterUtils.getAsString(bill2FloatItemFieldMap.get("CHANGEDATE"), (String)"null").equals(fieldCode)) continue;
                    value = groupFloatDataRow.getValue(field);
                    if (value.isNull) continue;
                    yyyyMMddChangeDate = DateUtils.format((Date)value.getAsDateObj(), (String)DateUtils.DEFAULT_DATE_FORMAT);
                }
                return changeScenario + "-" + yyyyMMddChangeDate;
            }, groupFloatDataRow -> groupFloatDataRow, (existing, replacement) -> existing, LinkedHashMap::new));
            oldDetailRowsByChangeDate.forEach((key, oldDetailRowsDatas) -> oldDetailRowsDatas.stream().forEach(oldDetailRowsDataMap -> {
                Object oldDetailRowSrctype = oldDetailRowsDataMap.get("SRCTYPE");
                Object oldDetailRowId = oldDetailRowsDataMap.get("ID");
                if ("6666".equals(String.valueOf(oldDetailRowSrctype)) && groupFloatDataRowsByChangeDate.containsKey(key) && oldDetailRowsDataMap.get("ID") != null) {
                    billModel.getTable(detailTable.getName()).deleteRowById(oldDetailRowId);
                }
            }));
            NamedContainerImpl fieldList = ((DataTableImpl)billModel.getData().getTables().get(detailTable.getName())).getDefine().getFields();
            groupFloatDataRows.forEach(groupFloatDataRow -> {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("VER", OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                map.put("MASTERID", billModel.getMaster().getData().get("ID"));
                map.put("SRCTYPE", "6666");
                map.put("SRCDATATIME", new Date());
                for (int index = 0; index < fieldList.size(); ++index) {
                    String floatDetailField;
                    DataFieldDefineImpl field = (DataFieldDefineImpl)fieldList.get(index);
                    if (ObjectUtils.isEmpty(field.getFieldName()) || "ID".equals(field.getFieldName()) || "VER".equals(field.getFieldName()) || "MASTERID".equals(field.getFieldName()) || (floatDetailField = (String)bill2FloatItemFieldMap.get(field.getFieldName())) == null) continue;
                    Object fieldValue = this.getFloatRowFieldValueByFieldName(tableInfo, (IDataRow)groupFloatDataRow, floatDetailField);
                    map.put(field.getFieldName(), fieldValue);
                }
                DataRow dataRow = billModel.getTable(detailTable.getName()).appendRow();
                dataRow.setData(map);
            });
        }
        return billModel;
    }

    private BillModelImpl fillDetailData(BillModelImpl billModel, TableModelRunInfo tableInfo, List<IDataRow> groupFloatDataRows, List<Map<String, String>> multiBill2FloatItemFieldMap) {
        List detailTables = billModel.getDefine().getData().getTableList().stream().filter(dataTableDefineImpl -> !dataTableDefineImpl.getTableName().equalsIgnoreCase(billModel.getMasterTable().getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(detailTables)) {
            return billModel;
        }
        for (int i = 0; i < multiBill2FloatItemFieldMap.size(); ++i) {
            Map<String, String> bill2FloatItemFieldMap = multiBill2FloatItemFieldMap.get(i);
            DataTableDefineImpl detailTable = (DataTableDefineImpl)detailTables.get(i);
            List oldDetailRowsData = billModel.getTable(detailTable.getTableName()).getRowsData();
            oldDetailRowsData.stream().forEach(oldDetailRow -> {
                Object srctype = oldDetailRow.get("SRCTYPE");
                if ("6666".equals(String.valueOf(srctype)) && oldDetailRow.get("ID") != null) {
                    billModel.getTable(detailTable.getName()).deleteRowById(oldDetailRow.get("ID"));
                }
            });
            NamedContainerImpl fieldList = ((DataTableImpl)billModel.getData().getTables().get(detailTable.getName())).getDefine().getFields();
            groupFloatDataRows.forEach(groupFloatDataRow -> {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("VER", OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                map.put("MASTERID", billModel.getMaster().getData().get("ID"));
                map.put("SRCTYPE", "6666");
                map.put("SRCDATATIME", new Date());
                for (int index = 0; index < fieldList.size(); ++index) {
                    String floatDetailField;
                    DataFieldDefineImpl field = (DataFieldDefineImpl)fieldList.get(index);
                    if (ObjectUtils.isEmpty(field.getFieldName()) || "ID".equals(field.getFieldName()) || "VER".equals(field.getFieldName()) || "MASTERID".equals(field.getFieldName()) || (floatDetailField = (String)bill2FloatItemFieldMap.get(field.getFieldName())) == null) continue;
                    Object fieldValue = this.getFloatRowFieldValueByFieldName(tableInfo, (IDataRow)groupFloatDataRow, floatDetailField);
                    map.put(field.getFieldName(), fieldValue);
                }
                DataRow dataRow = billModel.getTable(detailTable.getName()).appendRow();
                dataRow.setData(map);
            });
        }
        return billModel;
    }

    private BillModelImpl createBillModel(String billDefineName) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContext, billDefineName);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.add();
        return model;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String rateType = (String)parameters.get(0).evaluate(null);
        if (StringUtils.isEmpty((String)rateType)) {
            throw new SyntaxException(parameters.get(0).getToken(), "PushToInvestBillFunction\u7b2c\u4e00\u4e2a\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return super.validate(context, parameters);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("    ").append("\u589e\u52a0\u8fd0\u7b97\u516c\u5f0f\u51fd\u6570\uff0c\u8fd0\u7b97\u65f6\uff0c\u628a\u62a5\u8868\u6d6e\u52a8\u884c\u4e2d\u5f55\u5165\u7684\u6295\u8d44\uff0c\u56fa\u5b9a\u8d44\u4ea7\u53f0\u8d26\u6570\u636e\u9700\u8981\u63a8\u5230\u5bf9\u5e94\u7684\u53f0\u8d26\u4e2d\u3002").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR).append("\u793a\u4f8b1\uff1a\u63a8\u9001\u8d44\u4ea7\u53f0\u8d26\u5355\u636e\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR).append("    ").append("pushtotz(\"GC_INPUTDATA:GC_COMMONASSETBILL\", \"1\", \"UNITCODE,OPPUNITCODE\", \"ORGCODE:UNITCODE,OPPUNITID:OPPUNITCODE,FLOATFEILD1:BILLMFEILD1,FLOATFEILD2:BILLMFEILD2\", \"FLOATFEILD1:BILLMFEILD1,FLOATFEILD2:BILLMFEILD2,FLOATFEILD3:BILLDFEILD3\", \"\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR).append("\u793a\u4f8b1\uff1a\u63a8\u9001\u6295\u8d44\u53f0\u8d26\u5355\u636e\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR).append("    ").append("pushtotz(\"GC_INPUTDATA:INVESTBILL\", \"2\", \"UNITCODE,INVESTEDUNIT\", \"ORGCODE:UNITCODE,OPPUNITID:INVESTEDUNIT,FLOATFEILD1:BILLMFEILD1,FLOATFEILD2:BILLMFEILD2\", \"FLOATFEILD1:BILLDFEILD1,FLOATFEILD2:BILLDFEILD2,,FLOATFEILD1:BILLDFEILD1,FLOATFEILD3:BILLDFEILD3\", \"\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

