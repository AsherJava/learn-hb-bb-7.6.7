/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService
 *  com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.service.OrgDataService
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Alias
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.ExpressionVisitor
 *  net.sf.jsqlparser.expression.ExpressionVisitorAdapter
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SelectItem
 *  net.sf.jsqlparser.statement.select.SetOperationList
 */
package com.jiuqi.bde.plugin.common.utils;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.common.utils.config.AssistDimensionFetchUtilConfig;
import com.jiuqi.bde.plugin.common.utils.entity.AssistExtendDimHolder;
import com.jiuqi.bde.plugin.common.utils.entity.BaseDataHolder;
import com.jiuqi.bde.plugin.common.utils.entity.FetchDataJoinContext;
import com.jiuqi.bde.plugin.common.utils.entity.OrgDataHolder;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class AssistDimensionFetchUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssistDimensionFetchUtil.class);
    private static final Executor ASSIST_DIMENSION_FETCH_THREAD_POOL = new ThreadPoolExecutor(AssistDimensionFetchUtilConfig.getCorePoolSize(), AssistDimensionFetchUtilConfig.getMaxPoolSize(), AssistDimensionFetchUtilConfig.getKeepAliveTime(), TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(AssistDimensionFetchUtilConfig.getQueueSize()), r -> new Thread(r, "assist-dimension-fetch-thread"));
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private FetchDimensionService fetchDimensionService;
    @Autowired
    private AssistExtendDimService assistExtendDimService;
    @Autowired
    private OrgDataService orgDataService;
    private volatile String bizDataModelEnumCode = DEFAULT_BIZ_DATA_MODEL_CODE;
    private static final String DEFAULT_BIZ_DATA_MODEL_CODE = BizDataModelEnum.ASSBALANCEMODEL.getCode();

    public String getBizDataModelEnumCode() {
        return this.bizDataModelEnumCode;
    }

    public void setBizDataModelEnumCode(String bizDataModelEnumCode) {
        this.bizDataModelEnumCode = bizDataModelEnumCode;
    }

    public FetchDataJoinContext prepareContext(Set<String> queriedDimensionCodes, String orgCode, String orgVer, String orgType) {
        FetchDataJoinContext context = new FetchDataJoinContext();
        if (CollectionUtils.isEmpty(queriedDimensionCodes)) {
            return context;
        }
        FetchDataJoinContext.QueriedDimensionHolder holder = this.separateAssistDimAndAssistExtendDim(queriedDimensionCodes);
        context.setQueriedDimensionHolder(holder);
        List<AssistExtendDimVO> queriedAssistExtendDimensions = holder.getQueriedAssistExtendDimensions();
        ArrayList<AssistExtendDimVO> orgAssistExtendDimensions = new ArrayList<AssistExtendDimVO>();
        HashMap<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap = new HashMap<String, AssistExtendDimVO>(8);
        for (AssistExtendDimVO assistExtendDim : queriedAssistExtendDimensions) {
            if ("MD_ORG".equals(assistExtendDim.getAssistDimCode())) {
                orgAssistExtendDimensions.add(assistExtendDim);
                continue;
            }
            assistExtendDimCodeToAssistExtendDimMap.put(assistExtendDim.getCode(), assistExtendDim);
        }
        AssistExtendDimHolder assistExtendDimHolder = new AssistExtendDimHolder(assistExtendDimCodeToAssistExtendDimMap);
        context.setAssistExtendDimHolder(assistExtendDimHolder);
        BaseDataHolder baseDataHolder = this.buildBaseDataTableNameToBaseDataInfoMap(assistExtendDimCodeToAssistExtendDimMap.values(), null);
        context.setBaseDataHolder(baseDataHolder);
        OrgDataHolder orgDataHolder = new OrgDataHolder();
        if (CollectionUtils.isEmpty(orgAssistExtendDimensions)) {
            orgDataHolder.setDataExists(false);
            context.setOrgDataHolder(orgDataHolder);
            context.setDataReady(true);
            return context;
        }
        if (StringUtils.isEmpty((String)orgCode) && !CollectionUtils.isEmpty(orgAssistExtendDimensions)) {
            throw new IllegalArgumentException("\u4f20\u5165\u7684\u53c2\u6570\u7ec4\u7ec7\u673a\u6784\u7f16\u7801\uff1aorgCode \u4e3a\u7a7a\uff0c\u4f46\u662f\u5b58\u5728\u9700\u8981\u67e5\u8be2\u7684\u7ec4\u7ec7\u673a\u6784\u5c5e\u6027\uff0c\u8bf7\u4f20\u5165\u7ec4\u7ec7\u673a\u6784\u7f16\u7801");
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setOrgcode(orgCode);
        if (!StringUtils.isEmpty((String)orgVer)) {
            Date[] dateArr = GcPeriodUtils.getDateArr((String)orgVer);
            Date beginDate = dateArr[0];
            Date endDate = dateArr[1];
            orgDTO.setValidtime(beginDate);
            orgDTO.setInvalidtime(endDate);
            orgDTO.setVersionDate(beginDate);
        }
        orgDTO.setCategoryname(orgType);
        List rows = this.orgDataService.list(orgDTO).getRows();
        if (CollectionUtils.isEmpty((Collection)rows)) {
            LOGGER.error("com.jiuqi.bde.plugin.common.utils.AssistDimensionFetchUtil.prepareContext \u4f20\u5165\u7684\u7ec4\u7ec7\u673a\u6784\u7f16\u7801\u3010{}\u3011\u4e0d\u5b58\u5728, \u8bf7\u68c0\u67e5\u4f20\u5165\u53c2\u6570\u4ee5\u53ca\u5355\u4f4d\u6620\u5c04\u914d\u7f6e\u662f\u5426\u6b63\u786e", (Object)orgCode);
            orgDataHolder.setDataExists(false);
            context.setOrgDataHolder(orgDataHolder);
            context.setDataReady(true);
            return context;
        }
        Assert.isTrue((rows.size() == 1 ? 1 : 0) != 0, (String)"\u4e0d\u5141\u8bb8\u4f20\u5165\u7684\u7ec4\u7ec7\u673a\u6784\u7f16\u7801\u5bf9\u5e94\u591a\u4e2a\u7ec4\u7ec7\u673a\u6784\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u662f\u5426\u6b63\u786e", (Object[])new Object[0]);
        OrgDO orgDO = (OrgDO)rows.get(0);
        orgDataHolder.setOrgDO(orgDO);
        orgDataHolder.setOrgAssistExtendDimensions(orgAssistExtendDimensions);
        orgDataHolder.setDataExists(true);
        context.setOrgDataHolder(orgDataHolder);
        context.setDataReady(true);
        return context;
    }

    private BaseDataHolder buildBaseDataTableNameToBaseDataInfoMap(Collection<AssistExtendDimVO> assistExtendDimensions, Map<String, String> correctAssistDimCodeMap) {
        ConcurrentHashMap<String, Map<String, BaseDataDO>> baseDataTableNameToBaseDataInfoMap = new ConcurrentHashMap<String, Map<String, BaseDataDO>>(assistExtendDimensions.size());
        if (CollectionUtils.isEmpty(assistExtendDimensions)) {
            return new BaseDataHolder(baseDataTableNameToBaseDataInfoMap);
        }
        CompletableFuture<Void> futures = CompletableFuture.allOf((CompletableFuture[])assistExtendDimensions.stream().map(assistExtendDimVO -> CompletableFuture.supplyAsync(() -> {
            String assistExtendDimTableName = assistExtendDimVO.getAssistDimCode();
            BaseDataDTO dto = new BaseDataDTO();
            dto.setAuthType(BaseDataOption.AuthType.NONE);
            dto.setTableName(assistExtendDimTableName);
            dto.setPagination(Boolean.valueOf(false));
            List list = this.baseDataService.list(dto).getRows();
            Map<String, BaseDataDO> map = list.stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item));
            if (correctAssistDimCodeMap != null && correctAssistDimCodeMap.containsKey(assistExtendDimTableName)) {
                assistExtendDimTableName = (String)correctAssistDimCodeMap.get(assistExtendDimTableName);
            }
            return new AbstractMap.SimpleEntry<String, Map<String, BaseDataDO>>(assistExtendDimTableName, map);
        }, ASSIST_DIMENSION_FETCH_THREAD_POOL).thenAccept(entry -> {
            Map cfr_ignored_0 = (Map)baseDataTableNameToBaseDataInfoMap.put((String)entry.getKey(), (Map<String, BaseDataDO>)entry.getValue());
        })).toArray(CompletableFuture[]::new));
        futures.join();
        return new BaseDataHolder(baseDataTableNameToBaseDataInfoMap);
    }

    private void doJoin(FetchData fetchData, FetchDataJoinContext context) {
        if (context == null || !context.isDataReady()) {
            return;
        }
        int numPartitions = AssistDimensionFetchUtilConfig.getNumPartitions();
        int maxNumPartitions = AssistDimensionFetchUtilConfig.getMaxNumPartitions();
        if (numPartitions <= 0 || numPartitions > maxNumPartitions) {
            throw new IllegalArgumentException(String.format("\u8fdb\u884c\u5e76\u884c\u5408\u5e76\u65f6\u4e0d\u5141\u8bb8\u62c6\u5206\u4e3a%s\u4e2a\u5206\u7ec4\uff0c\u5141\u8bb8\u533a\u95f4\u4e3a\u3010%s, %s\u3011", numPartitions, 0, maxNumPartitions));
        }
        Map<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap = context.getAssistExtendDimHolder().getAssistExtendDimCodeToAssistExtendDimMap();
        Map<String, Map<String, BaseDataDO>> baseDataTableNameToBaseDataInfoMap = context.getBaseDataHolder().getBaseDataTableNameToBaseDataInfoMap();
        Set<String> queriedAssistExtendDimCodes = assistExtendDimCodeToAssistExtendDimMap.keySet();
        List rows = fetchData.getRowDatas();
        int dataSize = rows.size();
        ArrayList<CompletableFuture<Void>> rowFutures = new ArrayList<CompletableFuture<Void>>();
        Map columnIndexMap = fetchData.getColumns();
        int size = columnIndexMap.size();
        for (String neededJoinExtendDimCode : queriedAssistExtendDimCodes) {
            columnIndexMap.put(neededJoinExtendDimCode, size);
            ++size;
        }
        OrgDataHolder orgDataHolder = context.getOrgDataHolder();
        if (!orgDataHolder.isDataExists() && CollectionUtils.isEmpty(queriedAssistExtendDimCodes)) {
            return;
        }
        List<AssistExtendDimVO> orgAssistExtendDimensions = orgDataHolder.getOrgAssistExtendDimensions();
        if (orgDataHolder.isDataExists()) {
            Assert.isNotNull(orgAssistExtendDimensions);
            Set orgAssistExtendDimCodes = orgAssistExtendDimensions.stream().map(AssistExtendDimVO::getCode).collect(Collectors.toSet());
            for (String orgAssistExtendDimCode : orgAssistExtendDimCodes) {
                columnIndexMap.put(orgAssistExtendDimCode, size);
                ++size;
            }
        }
        int orgAssistExtendDimensionsSize = orgAssistExtendDimensions == null ? 0 : orgAssistExtendDimensions.size();
        int partitionSize = (dataSize + numPartitions - 1) / numPartitions;
        for (int partitionCount = 0; partitionCount < numPartitions; ++partitionCount) {
            int start = partitionCount * partitionSize;
            int end = Math.min(start + partitionSize, dataSize);
            if (start >= dataSize) break;
            CompletableFuture<Void> rowFuture = CompletableFuture.runAsync(() -> {
                for (int i = start; i < end; ++i) {
                    Object[] row = (Object[])rows.get(i);
                    Object[] newRow = new Object[row.length + queriedAssistExtendDimCodes.size() + orgAssistExtendDimensionsSize];
                    System.arraycopy(row, 0, newRow, 0, row.length);
                    rows.set(i, newRow);
                    for (String neededJoinExtendDimCode : queriedAssistExtendDimCodes) {
                        Object joinData;
                        AssistExtendDimVO extendDimension = (AssistExtendDimVO)assistExtendDimCodeToAssistExtendDimMap.get(neededJoinExtendDimCode);
                        Assert.isNotNull((Object)extendDimension, (String)String.format("\u672a\u627e\u5230\u7ef4\u5ea6\u5c5e\u6027\u3010%s\u3011\u5bf9\u5e94\u7684\u7ef4\u5ea6\u6570\u636e\uff0c\u65e0\u6cd5\u5339\u914d", neededJoinExtendDimCode), (Object[])new Object[0]);
                        String dimensionCodeOrTableName = extendDimension.getAssistDimCode();
                        String extendDimFieldCode = extendDimension.getRefField();
                        Map baseDatas = (Map)baseDataTableNameToBaseDataInfoMap.get(dimensionCodeOrTableName);
                        Integer neededJoinExtendDimIndex = (Integer)columnIndexMap.get(neededJoinExtendDimCode);
                        Assert.isNotNull((Object)neededJoinExtendDimIndex);
                        Assert.isTrue((neededJoinExtendDimIndex < newRow.length ? 1 : 0) != 0);
                        if ("MD_ACCTSUBJECT".equals(dimensionCodeOrTableName)) {
                            dimensionCodeOrTableName = "SUBJECTCODE";
                        }
                        Integer dimensionIndex = (Integer)columnIndexMap.get(dimensionCodeOrTableName);
                        Assert.isNotNull((Object)dimensionIndex, (String)String.format("\u6570\u636e\u52a0\u8f7d\u5c42\u672a\u8fd4\u56de\u7ef4\u5ea6\u5c5e\u6027\u3010%s\u3011\u5bf9\u5e94\u7684\u7ef4\u5ea6\u6570\u636e\uff0c\u65e0\u6cd5\u5339\u914d", neededJoinExtendDimCode), (Object[])new Object[0]);
                        Assert.isTrue((dimensionIndex < newRow.length ? 1 : 0) != 0);
                        if (newRow[dimensionIndex] == null) {
                            newRow[neededJoinExtendDimIndex.intValue()] = "";
                            continue;
                        }
                        String equalCode = newRow[dimensionIndex].toString();
                        BaseDataDO baseData = (BaseDataDO)baseDatas.get(equalCode);
                        if (baseData == null) continue;
                        newRow[neededJoinExtendDimIndex.intValue()] = joinData = baseData.getValueOf(extendDimFieldCode);
                    }
                    if (!orgDataHolder.isDataExists()) continue;
                    OrgDO orgData = context.getOrgDataHolder().getOrgDO();
                    assert (orgAssistExtendDimensions != null);
                    for (AssistExtendDimVO neededJoinOrgExtendDim : orgAssistExtendDimensions) {
                        Object joinData;
                        Integer neededJoinExtendDimIndex = (Integer)columnIndexMap.get(neededJoinOrgExtendDim.getCode());
                        newRow[neededJoinExtendDimIndex.intValue()] = joinData = orgData.getValueOf(neededJoinOrgExtendDim.getRefField());
                    }
                }
            }, ASSIST_DIMENSION_FETCH_THREAD_POOL);
            rowFutures.add(rowFuture);
        }
        rowFutures.forEach(CompletableFuture::join);
        fetchData.setColumns(columnIndexMap);
    }

    public void joinAssistExtendDimFieldValues(FetchData fetchData, Set<String> queriedDimensionCodes, String orgCode, String orgType, String orgVer) {
        FetchDataJoinContext context = this.prepareContext(queriedDimensionCodes, orgCode, orgVer, orgType);
        this.doJoin(fetchData, context);
    }

    public FetchDataJoinContext.QueriedDimensionHolder separateAssistDimAndAssistExtendDim(Collection<String> dimensionCodes) {
        Map<String, DimensionVO> dataModelDimensionCodes = this.fetchDimensionService.listAllDimensionByDataModel(this.getBizDataModelEnumCode()).stream().map(assistDimension -> new AbstractMap.SimpleEntry<String, DimensionVO>(assistDimension.getCode(), (DimensionVO)assistDimension)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, AssistExtendDimVO> allStartedAssistExtendDimensionCodes = this.assistExtendDimService.getAllStartAssistExtendDim().stream().map(assistExtendDim -> new AbstractMap.SimpleEntry<String, AssistExtendDimVO>(assistExtendDim.getCode(), (AssistExtendDimVO)assistExtendDim)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        ArrayList<DimensionVO> assistDimensions = new ArrayList<DimensionVO>();
        ArrayList<AssistExtendDimVO> assistExtendDims = new ArrayList<AssistExtendDimVO>();
        dimensionCodes.forEach(code -> {
            if (allStartedAssistExtendDimensionCodes.containsKey(code)) {
                assistExtendDims.add((AssistExtendDimVO)allStartedAssistExtendDimensionCodes.get(code));
            } else if (dataModelDimensionCodes.containsKey(code)) {
                assistDimensions.add((DimensionVO)dataModelDimensionCodes.get(code));
            }
        });
        return new FetchDataJoinContext.QueriedDimensionHolder(assistDimensions, assistExtendDims);
    }

    public Set<String> parseQueriedDimCodesFromSql(String seniorSql) {
        HashSet queriedDimCodes = CollectionUtils.newHashSet();
        try {
            List selectItems;
            PlainSelect plainSelect;
            Statement parse = CCJSqlParserUtil.parse((String)seniorSql);
            Select select = (Select)parse;
            if (select.getSelectBody() instanceof SetOperationList) {
                SetOperationList operationList = (SetOperationList)select.getSelectBody();
                if (CollectionUtils.isEmpty((Collection)operationList.getSelects())) {
                    throw new BusinessRuntimeException("\u8fd0\u7b97\u7c7b\u578bSQL\u903b\u8f91\u6ca1\u6709\u5305\u542b\u67e5\u8be2\u4e3b\u4f53");
                }
                plainSelect = (PlainSelect)operationList.getSelects().get(0);
                selectItems = plainSelect.getSelectItems();
            } else {
                plainSelect = (PlainSelect)select.getSelectBody();
                selectItems = plainSelect.getSelectItems();
            }
            if (plainSelect.getGroupBy() != null && plainSelect.getGroupBy().getGroupByExpressionList() != null) {
                final ArrayList<String> groupByDimensionCodes = new ArrayList<String>();
                List groupByExpressions = plainSelect.getGroupBy().getGroupByExpressionList().getExpressions();
                if (groupByExpressions != null) {
                    for (Expression expression : groupByExpressions) {
                        if (expression instanceof Column) {
                            groupByDimensionCodes.add(((Column)expression).getColumnName().toUpperCase());
                            continue;
                        }
                        expression.accept((ExpressionVisitor)new ExpressionVisitorAdapter(){

                            public void visit(Column column) {
                                groupByDimensionCodes.add(column.getColumnName().toUpperCase());
                            }
                        });
                    }
                }
                queriedDimCodes.addAll(groupByDimensionCodes);
            }
            if (CollectionUtils.isEmpty((Collection)selectItems)) {
                return queriedDimCodes;
            }
            for (SelectItem item : selectItems) {
                SelectExpressionItem expressionItem = (SelectExpressionItem)item;
                String columnName = AssistDimensionFetchUtil.parseColumnName(expressionItem).toUpperCase();
                queriedDimCodes.add(columnName);
            }
        }
        catch (JSQLParserException e) {
            throw new BusinessRuntimeException("SQL\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5:" + e.getMessage(), (Throwable)e);
        }
        return queriedDimCodes;
    }

    private static String parseColumnName(SelectExpressionItem expressionItem) {
        Alias alias = expressionItem.getAlias();
        if (alias != null) {
            return alias.getName().contains("\"") ? alias.getName().replace("\"", "") : alias.getName();
        }
        if (expressionItem.getExpression() instanceof Column) {
            Column expression = (Column)expressionItem.getExpression();
            return expression.getColumnName();
        }
        return expressionItem.getExpression().toString();
    }

    /*
     * WARNING - void declaration
     */
    public String getJoinSql(Set<String> queriedDimensionCodes, String orgCode, String orgType, String orgVer, String cacheTableName) {
        void var18_28;
        String sql = " (select ${ORG_VALUES_SQL} ${ASSIST_DIM_SELECT_SQL} ${ASSIST_EXTEND_DIM_SELECT_SQL} from ${CACHE_TABLE_NAME} T ${JOIN_PART_SQL} ) ";
        FetchDataJoinContext ctx = this.prepareContext(queriedDimensionCodes, orgCode, orgVer, orgType);
        Set queriedAssistDimensionCodes = ctx.getQueriedDimensionHolder().getQueriedAssistDimensions().stream().map(DimensionVO::getCode).collect(Collectors.toSet());
        List<String> assistDimSelectFields = BizDataModelEnum.XJLLMODEL.getCode().equals(this.bizDataModelEnumCode) ? AssistDimensionFetchUtil.getXJLLBaseSelectFields() : AssistDimensionFetchUtil.getBaseSelectFields();
        assistDimSelectFields.addAll(queriedAssistDimensionCodes);
        ctx.getQueriedDimensionHolder().getQueriedAssistExtendDimensions().forEach(assistExtendDim -> {
            if (!(assistExtendDim == null || "MD_ACCTSUBJECT".equals(assistExtendDim.getAssistDimCode()) || "MD_ORG".equals(assistExtendDim.getAssistDimCode()) || queriedAssistDimensionCodes.contains(assistExtendDim.getAssistDimCode()))) {
                assistDimSelectFields.add(assistExtendDim.getAssistDimCode());
            }
        });
        String assistDimSelectSql = CollectionUtil.join(assistDimSelectFields, (String)",");
        Map<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap = ctx.getAssistExtendDimHolder().getAssistExtendDimCodeToAssistExtendDimMap();
        HashMap<Object, List> baseDataTableToSelectFieldsMap = new HashMap<Object, List>(assistExtendDimCodeToAssistExtendDimMap.size());
        HashMap<Object, String> baseDataTableToJoinFieldMap = new HashMap<Object, String>(assistExtendDimCodeToAssistExtendDimMap.size());
        for (AssistExtendDimVO assistExtendDimVO : assistExtendDimCodeToAssistExtendDimMap.values()) {
            if (assistExtendDimVO == null) continue;
            String baseDataTable = assistExtendDimVO.getAssistDimCode();
            List list = baseDataTableToSelectFieldsMap.getOrDefault(baseDataTable, new ArrayList());
            list.add(assistExtendDimVO.getRefField());
            baseDataTableToSelectFieldsMap.put(baseDataTable, list);
            baseDataTableToJoinFieldMap.put(baseDataTable, "code");
        }
        ArrayList<String> joinTableSelectFields = new ArrayList<String>();
        ArrayList<String> joinOnSql = new ArrayList<String>();
        for (Map.Entry entry : baseDataTableToSelectFieldsMap.entrySet()) {
            String string = "%1$s.%2$s AS %3$s";
            String tableName = (String)entry.getKey();
            List fields = (List)entry.getValue();
            for (String field : fields) {
                joinTableSelectFields.add(String.format(string, tableName, field, tableName + "_" + field));
            }
        }
        String assistExtendDimSelectSql = CollectionUtil.join(joinTableSelectFields, (String)",");
        if (!StringUtils.isEmpty((String)assistExtendDimSelectSql)) {
            assistExtendDimSelectSql = ", " + assistExtendDimSelectSql;
        }
        for (Map.Entry entry : baseDataTableToJoinFieldMap.entrySet()) {
            String s = "JOIN %1$s ON %2$s.%3$s = T.%4$s";
            String table = (String)entry.getKey();
            String field = (String)entry.getValue();
            String format = String.format(s, table, table, field, table);
            if ("MD_ACCTSUBJECT".equals(table)) {
                format = String.format(s, table, table, field, "SUBJECTCODE");
            }
            joinOnSql.add(format);
        }
        String string = CollectionUtil.join(joinOnSql, (String)" ");
        String string2 = "";
        if (ctx.getOrgDataHolder().isDataExists()) {
            String string3;
            ArrayList<String> orgFields = new ArrayList<String>();
            OrgDO orgData = ctx.getOrgDataHolder().getOrgDO();
            if (orgData != null) {
                for (AssistExtendDimVO org : ctx.getOrgDataHolder().getOrgAssistExtendDimensions()) {
                    String refField = org.getRefField();
                    Object obj = orgData.getValueOf(refField);
                    String value = obj == null ? "''" : obj.toString();
                    orgFields.add(String.format("'%s' AS %s", value, org.getCode()));
                }
            }
            if (!StringUtils.isEmpty((String)(string3 = CollectionUtil.join(orgFields, (String)",")))) {
                String string4 = string3 + ",";
            }
        }
        Variable variable = new Variable();
        variable.put("ORG_VALUES_SQL", (String)var18_28);
        variable.put("ASSIST_DIM_SELECT_SQL", assistDimSelectSql);
        variable.put("ASSIST_EXTEND_DIM_SELECT_SQL", assistExtendDimSelectSql);
        variable.put("CACHE_TABLE_NAME", cacheTableName);
        variable.put("JOIN_PART_SQL", string);
        return VariableParseUtil.parse((String)" (select ${ORG_VALUES_SQL} ${ASSIST_DIM_SELECT_SQL} ${ASSIST_EXTEND_DIM_SELECT_SQL} from ${CACHE_TABLE_NAME} T ${JOIN_PART_SQL} ) ", (Map)variable.getVariableMap());
    }

    private static List<String> getBaseSelectFields() {
        ArrayList<String> assistDimSelectFields = new ArrayList<String>();
        assistDimSelectFields.add("BIZCOMBID");
        assistDimSelectFields.add("SUBJECTCODE");
        assistDimSelectFields.add("CURRENCYCODE");
        assistDimSelectFields.add("T.ORIENT");
        assistDimSelectFields.add(FetchTypeEnum.NC.getCode());
        assistDimSelectFields.add(FetchTypeEnum.C.getCode());
        assistDimSelectFields.add(FetchTypeEnum.JF.getCode());
        assistDimSelectFields.add(FetchTypeEnum.DF.getCode());
        assistDimSelectFields.add(FetchTypeEnum.JL.getCode());
        assistDimSelectFields.add(FetchTypeEnum.DL.getCode());
        assistDimSelectFields.add(FetchTypeEnum.YE.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WNC.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WC.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WJF.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WDF.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WJL.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WDL.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WYE.getCode());
        return assistDimSelectFields;
    }

    private static List<String> getXJLLBaseSelectFields() {
        ArrayList<String> assistDimSelectFields = new ArrayList<String>();
        assistDimSelectFields.add("BIZCOMBID");
        assistDimSelectFields.add("SUBJECTCODE");
        assistDimSelectFields.add("CURRENCYCODE");
        assistDimSelectFields.add("CFITEMCODE");
        assistDimSelectFields.add("T.ORIENT");
        assistDimSelectFields.add(FetchTypeEnum.BQNUM.getCode());
        assistDimSelectFields.add(FetchTypeEnum.LJNUM.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WBQNUM.getCode());
        assistDimSelectFields.add(FetchTypeEnum.WLJNUM.getCode());
        return assistDimSelectFields;
    }
}

