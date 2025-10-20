/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.apache.commons.lang3.text.StrSubstitutor
 */
package com.jiuqi.gcreport.nr.impl.function.impl.tfv;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.xlib.utils.StringUtil;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TFVFFunction
extends NrFunction {
    private final String FUNCTION_NAME = "TFVF";
    private static final Logger logger = LoggerFactory.getLogger(TFVFFunction.class);

    public TFVFFunction() {
        this.parameters().add(new Parameter("TableName", 6, "\u8868\u540d\u79f0"));
        this.parameters().add(new Parameter("FieldNames", 6, "\u5b57\u6bb5\u540d\u79f0\u5217\u8868, \u5206\u53f7\u9694\u5f00"));
        this.parameters().add(new Parameter("FloatZbNames", 6, "\u6d6e\u52a8\u884c \u884c\u5217\u53f7 \u5217\u8868, \u5206\u53f7\u9694\u5f00"));
        this.parameters().add(new Parameter("CondiStr", 6, "\u6761\u4ef6\uff1a\u5982\u9700\u4ece\u5f53\u524d\u6570\u636e\u5f55\u5165\u4e2d\u83b7\u53d6\u6570\u636e\uff0c\u5219\u9700\u5c06\u5b57\u6bb5\u540d\u7528##\u5305\u88f9\u8d77\u6765\uff0c\u5982#AMT#\uff0c\u82e5\u662f\u5b57\u7b26\u578b\u5219\u9700\u7528'##'\u5305\u88f9\u8d77\u6765\uff0c\u5982'#OPPUNITID#'"));
        this.parameters().add(new Parameter("GroupBy", 6, "\u6c47\u603b\u5b57\u6bb5"));
        this.parameters().add(new Parameter("OrderBy", 6, "\u6392\u5e8f\u5b57\u6bb5"));
    }

    public String name() {
        return "TFVF";
    }

    public String title() {
        return "TFVF\u516c\u5f0f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        IDataTable destResult;
        String[] floatZbNamesArr;
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        String sql = "select {0} from {1} where {2}  {3} {4}";
        String tableName = (String)parameters.get(0).evaluate(iContext);
        String fieldNames = (String)parameters.get(1).evaluate(iContext);
        String floatZbNames = (String)parameters.get(2).evaluate(iContext);
        String conDiStr = (String)parameters.get(3).evaluate(iContext);
        String groupBy = (String)parameters.get(4).evaluate(iContext);
        String orderBy = (String)parameters.get(5).evaluate(iContext);
        CharSequence[] fieldNamesArr = fieldNames.split(";");
        if (fieldNamesArr.length != (floatZbNamesArr = floatZbNames.split(";")).length) {
            throw new BusinessRuntimeException("\u5b57\u6bb5\u540d\u79f0\u5217\u8868\u4e0e\u6d6e\u52a8\u884c\u6307\u6807\u5217\u8868\u4e0d\u80fd\u4e00\u4e00\u5bf9\u5e94\uff0c\u8bf7\u68c0\u67e5\u516c\u5f0f");
        }
        String queryFields = String.join((CharSequence)",", fieldNamesArr);
        QueryContext queryContext = (QueryContext)iContext;
        DimensionValueSet masterKeys = queryContext.getCurrentMasterKey();
        HashMap<String, Object> fetchDataS = new HashMap<String, Object>(16);
        for (int i = 0; i < masterKeys.size(); ++i) {
            fetchDataS.put(masterKeys.getName(i), masterKeys.getValue(i));
        }
        conDiStr = StrSubstitutor.replace((Object)conDiStr, fetchDataS, (String)"#", (String)"#");
        String finalSql = MessageFormat.format(sql, queryFields, tableName, conDiStr, StringUtil.isEmpty((String)groupBy) ? "" : "group by " + groupBy, StringUtil.isEmpty((String)orderBy) ? "" : " order by " + orderBy);
        logger.info("TFVF\u516c\u5f0f\u7684\u67e5\u8be2\u8bed\u53e5\u4e3a\uff1a" + finalSql);
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(finalSql, new Object[0]);
        logger.info(finalSql + "\u7684\u67e5\u8be2\u7ed3\u679c\u4e3a\uff1a" + maps.toString());
        String defaultGroupName = queryContext.getDefaultGroupName();
        IFmlExecEnvironment env = queryContext.getExeContext().getEnv();
        IDataModelLinkFinder dataModelLinkFinder = env.getDataModelLinkFinder();
        ReportInfo reportInfo = dataModelLinkFinder.findReportInfo(defaultGroupName);
        String regionKey = "";
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        for (int i = 0; i < floatZbNamesArr.length; ++i) {
            String floatZbAxis = floatZbNamesArr[i];
            RowCol rowCol = new RowCol(floatZbAxis);
            DataModelLinkColumn dataColumn = dataModelLinkFinder.findDataColumn(reportInfo, rowCol.getRowNum(), rowCol.getColNum(), false);
            if (Objects.isNull(dataColumn)) {
                throw new BusinessRuntimeException(MessageFormat.format("{0} \u5bf9\u5e94\u7684\u6d6e\u52a8\u884c\u6307\u6807\u4e0d\u5b58\u5728", floatZbAxis));
            }
            if (!(StringUtil.isEmpty((String)regionKey) || StringUtil.isEmpty((String)dataColumn.getRegion()) || regionKey.equals(dataColumn.getRegion()))) {
                throw new BusinessRuntimeException("\u9009\u62e9\u7684\u6d6e\u52a8\u884c\u6307\u6807\u4e0d\u5c5e\u4e8e\u540c\u4e00\u4e2a\u533a\u57df");
            }
            regionKey = dataColumn.getRegion();
            DataLinkDefine dataLinkDefine = iRunTimeViewController.queryDataLinkDefineByColRow(reportInfo.getReportKey(), rowCol.getColNum(), rowCol.getRowNum());
            try {
                FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
                fieldDefines.add(fieldDefine);
                continue;
            }
            catch (Exception e) {
                logger.error("TFVF\u516c\u5f0f\u6267\u884c-\u67e5\u8be2\u6307\u6807\u5f02\u5e38\uff1a{}", (Object)floatZbAxis);
            }
        }
        FieldDefine orderDefine = null;
        try {
            orderDefine = dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", ((FieldDefine)fieldDefines.get(0)).getOwnerTableKey());
        }
        catch (Exception e) {
            logger.error("TFVF\u516c\u5f0f\u67e5\u8be2\u6d6e\u52a8\u884c\u6392\u5e8f\u6307\u6807\u5931\u8d25");
            throw new BusinessRuntimeException("TFVF\u516c\u5f0f\u67e5\u8be2\u6d6e\u52a8\u884c\u6392\u5e8f\u6307\u6807\u5931\u8d25");
        }
        fieldDefines.add(orderDefine);
        ArrayList fieldRaws = new ArrayList();
        double floatOrder = 0.111111;
        for (int i = 0; i < maps.size(); ++i) {
            ArrayList<String> fieldValues = new ArrayList<String>();
            fieldRaws.add(fieldValues);
            Map map = (Map)maps.get(i);
            for (int j = 0; j < fieldNamesArr.length; ++j) {
                String fieldName = ((String)fieldNamesArr[j]).toUpperCase();
                fieldValues.add(Objects.isNull(map.get(fieldName)) ? null : map.get(fieldName).toString());
            }
            fieldValues.add(String.valueOf((double)i + floatOrder));
        }
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        IDataQuery dataQuery = this.getDataQuery(regionKey, reportInfo.getReportKey(), queryContext, fieldDefines);
        try {
            destResult = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            logger.error("TFVF\u516c\u5f0f\u6267\u884c\u516c\u5f0f\u5f15\u64ce\u51fa\u9519,\u533a\u57df\u53c2\u6570");
            throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u67e5\u8be2\u51fa\u9519", e);
        }
        destResult.deleteAll();
        try {
            for (int i = 0; i < fieldRaws.size(); ++i) {
                List fieldValues = (List)fieldRaws.get(i);
                DimensionValueSet rowKey = new DimensionValueSet(queryContext.getCurrentMasterKey());
                rowKey.setValue("RECORDKEY", (Object)UUIDUtils.newUUIDStr());
                IDataRow dataRow = destResult.appendRow(rowKey);
                for (int j = 0; j < fieldDefines.size(); ++j) {
                    FieldDefine fieldDefine = (FieldDefine)fieldDefines.get(j);
                    String fieldValue = (String)fieldValues.get(j);
                    dataRow.setValue(fieldDefine, (Object)fieldValue);
                }
            }
            destResult.commitChanges(true);
        }
        catch (Exception e) {
            logger.error("TFVF\u516c\u5f0f\u4fdd\u5b58\u6570\u636e\u5931\u8d25");
        }
        return true;
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuffer buffer = new StringBuffer(description);
        return buffer.toString();
    }

    public IDataQuery getDataQuery(String regionKey, String formKey, QueryContext queryContext, List<FieldDefine> fieldDefines) {
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanUtils.getBean(IDataAccessProvider.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        FormDefine formDefine = runTimeViewController.queryFormById(formKey);
        try {
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formDefine.getFormScheme());
            queryEnvironment.setRegionKey(regionKey);
            queryEnvironment.setFormKey(formKey);
            queryEnvironment.setFormCode(formDefine.getFormCode());
            IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setMasterKeys(queryContext.getCurrentMasterKey());
            if (!CollectionUtils.isEmpty(fieldDefines)) {
                for (FieldDefine fieldDefine : fieldDefines) {
                    dataQuery.addColumn(fieldDefine);
                }
            }
            return dataQuery;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("\u6839\u636e\u67e5\u8be2\u73af\u5883\u83b7\u53d6\u67e5\u8be2\u63a5\u53e3\u5b9e\u4f8b\u5f02\u5e38", ex);
        }
    }

    class RowCol {
        private int rowNum;
        private int colNum;

        public RowCol() {
        }

        public RowCol(String axis) {
            int leftBracketIndex = axis.indexOf("[");
            int rightBracketIndex = axis.indexOf("]");
            String cellXY = axis.substring(leftBracketIndex + 1, rightBracketIndex);
            String[] xy = cellXY.split(",");
            this.rowNum = Integer.parseInt(xy[0]);
            this.colNum = Integer.parseInt(xy[1]);
        }

        public int getRowNum() {
            return this.rowNum;
        }

        public void setRowNum(int rowNum) {
            this.rowNum = rowNum;
        }

        public int getColNum() {
            return this.colNum;
        }

        public void setColNum(int colNum) {
            this.colNum = colNum;
        }
    }
}

