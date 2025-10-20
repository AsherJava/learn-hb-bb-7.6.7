/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.cache.internal.redis.IRedisLock
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.nr.impl.function.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.np.cache.internal.redis.IRedisLock;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomSerialFunction
extends NrFunction {
    private final Logger logger = LoggerFactory.getLogger(CustomSerialFunction.class);
    public static final String FUNCTION_NAME = "customSerial";
    private static final String COSTOMSERIAL_FUNCTUON_LOCK = "COSTOMSERIAL_FUNCTUON_LOCK";

    public CustomSerialFunction() {
        this.parameters().add(new Parameter("field", 0, "\u6570\u636e\u6307\u6807"));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u751f\u6210\u6d41\u6c34\u53f7\u81ea\u589eid";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)iContext;
        ExecutorContext context = queryContext.getExeContext();
        CellDataNode cellDataNode = (CellDataNode)list.get(0);
        int numCount = Integer.parseInt(list.get(1).toString());
        String paramStr = this.handlerParam(iContext, list);
        IDataQuery dataQuery = this.buildQueryParam(cellDataNode, context);
        TableDefine tableDefine = null;
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        DynamicDataNode dynamicDataNode = (DynamicDataNode)cellDataNode.getChild(0);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IRedisLock redisLock = (IRedisLock)SpringContextUtils.getBean(IRedisLock.class);
        try {
            tableDefine = dataDefinitionRuntimeController.queryTableDefineByCode(dynamicDataNode.getQueryField().getTableName());
            fieldDefines.add(dataDefinitionRuntimeController.queryFieldByCodeInTable("BIZKEYORDER", tableDefine.getKey()));
            fieldDefines.add(dataDefinitionRuntimeController.queryFieldByCodeInTable(dynamicDataNode.getQueryField().getFieldCode(), tableDefine.getKey()));
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5355\u5b9a\u4e49\u5f02\u5e38", e);
        }
        fieldDefines.forEach(arg_0 -> ((IDataQuery)dataQuery).addColumn(arg_0));
        DimensionValueSet masterKeys = queryContext.getMasterKeys();
        dataQuery.setMasterKeys(masterKeys);
        String lockKey = this.buildLockKey(masterKeys, tableDefine.getCode());
        if (redisLock.lock(COSTOMSERIAL_FUNCTUON_LOCK + lockKey, 300000L) == null) {
            throw new BusinessRuntimeException("\u7cfb\u7edf\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        try {
            IDataTable dataTable = this.queryDataRow(dataQuery, context);
            this.saveDateRow(dataTable, fieldDefines, numCount, paramStr);
        }
        finally {
            redisLock.unLock(COSTOMSERIAL_FUNCTUON_LOCK + lockKey);
        }
        return null;
    }

    private IDataQuery buildQueryParam(CellDataNode cellDataNode, ExecutorContext context) {
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)context.getEnv();
        String regionId = cellDataNode.getDataModelLinkColumn().getRegion();
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(env.getFormSchemeKey());
        queryEnvironment.setRegionKey(regionId);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        return dataQuery;
    }

    private IDataTable queryDataRow(IDataQuery dataQuery, ExecutorContext context) {
        IDataTable dataTable = null;
        try {
            dataTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5355\u6570\u636e\u5f02\u5e38", e);
        }
        return dataTable;
    }

    private void saveDateRow(IDataTable dataTable, List<FieldDefine> fieldDefines, int numCount, String paramStr) {
        int count = dataTable.getCount();
        if (count == 0) {
            return;
        }
        ArrayList<String> dataRowIds = new ArrayList<String>();
        for (int i = 0; i < count; ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            AbstractData offsetState = dataRow.getValue(fieldDefines.get(1));
            if (offsetState.isNull) continue;
            dataRowIds.add(offsetState.getAsString());
        }
        if (dataRowIds.size() == count) {
            return;
        }
        Optional<String> maxSerialOpt = dataRowIds.stream().max(Comparator.comparingInt(item -> {
            String serialNum = item.substring(item.length() - numCount);
            return Integer.parseInt(serialNum);
        }));
        int maxSerialNum = 0;
        if (maxSerialOpt.isPresent()) {
            String maxSerial = maxSerialOpt.get();
            String serialNumStr = maxSerial.substring(maxSerial.length() - numCount);
            maxSerialNum = Integer.parseInt(serialNumStr);
        }
        for (int i = 0; i < count; ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            AbstractData offsetState = dataRow.getValue(fieldDefines.get(1));
            if (!offsetState.isNull) continue;
            dataRow.setValue(fieldDefines.get(1), (Object)(paramStr + this.generateSerial(numCount, ++maxSerialNum)));
        }
        try {
            dataTable.commitChanges(true);
        }
        catch (Exception e) {
            this.logger.error("\u4fdd\u5b58\u6570\u636e\u7ed3\u679c\u96c6\u5f02\u5e38", e);
        }
    }

    private String generateSerial(int numCount, int maxSerialNum) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numCount; ++i) {
            stringBuilder.append("0");
        }
        stringBuilder.append(maxSerialNum);
        return stringBuilder.substring(stringBuilder.length() - numCount);
    }

    private String handlerParam(IContext iContext, List<IASTNode> list) {
        if (list.size() <= 2) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < list.size(); ++i) {
            IASTNode iastNode = list.get(i);
            try {
                Object evaluate = iastNode.evaluate(iContext);
                if (evaluate instanceof GregorianCalendar) {
                    GregorianCalendar customSerialFunction = (GregorianCalendar)evaluate;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
                    Date date = customSerialFunction.getTime();
                    String dateStr = simpleDateFormat.format(date);
                    stringBuilder.append(dateStr);
                    continue;
                }
                stringBuilder.append(evaluate.toString());
                continue;
            }
            catch (SyntaxException e) {
                this.logger.error("\u6267\u884c\u516c\u5f0f\u53d8\u91cf\u5f02\u5e38{}", (Object)iastNode, (Object)e);
            }
        }
        return stringBuilder.toString();
    }

    private String buildLockKey(DimensionValueSet masterKeys, String tableName) {
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>((o1, o2) -> -o1.compareTo((String)o2));
        for (int i = 0; i < masterKeys.size(); ++i) {
            String dimensionName = masterKeys.getName(i);
            Object value2 = masterKeys.getValue(dimensionName);
            treeMap.put(dimensionName, value2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        treeMap.forEach((key, value) -> stringBuilder.append(value));
        stringBuilder.append(tableName);
        return stringBuilder.toString();
    }
}

