/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.office.excel2.WorksheetWriter2
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.data.engine.validation.CompareType
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpression
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  io.netty.util.internal.StringUtil
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.office.excel2.WorksheetWriter2;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.data.engine.validation.CompareType;
import com.jiuqi.nr.data.engine.validation.DataValidationExpression;
import com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectHelper;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.DimensionItemScop;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryEntityData;
import com.jiuqi.nr.query.block.QueryFilterDefine;
import com.jiuqi.nr.query.block.QueryGridExtension;
import com.jiuqi.nr.query.block.QueryGridPage;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QuerySortType;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.chart.ChartType;
import com.jiuqi.nr.query.chart.HttpUtils;
import com.jiuqi.nr.query.common.GridBlockType;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.impl.QueryBlockDefineDao;
import com.jiuqi.nr.query.dao.impl.QueryModalDefineDao;
import com.jiuqi.nr.query.defines.QueryEntityDataObject;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import com.jiuqi.nr.query.querymodal.QueryModelCategory;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.DataQueryCache;
import com.jiuqi.nr.query.service.impl.DimensionInfor;
import com.jiuqi.nr.query.service.impl.QueryExportData;
import com.jiuqi.nr.query.service.impl.QueryGrid;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataQueryHelper {
    private IRunTimeViewController viewController;
    private IDataAccessProvider dataAccessProvider;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IDataAssist dataAssist;
    private static final Logger logger = LoggerFactory.getLogger(DataQueryHelper.class);
    private IEntityViewRunTimeController entityViewRunTimeController;
    private DataQueryCache queryCache;
    private IQueryBlockDefineDao blockDao;
    private QueryModalDefineDao modelDao;
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private QueryModelAuthorityProvider querModelAuthorityProvider;
    private IPeriodEntityAdapter periodEntityAdapter;
    private QueryEntityUtil queryEntityUtil;
    private final String CONST_RowDimensionCount = "RowDimensionCount";

    public DataQueryHelper() {
        this.init();
    }

    private void init() {
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataAssist = this.dataAccessProvider.newDataAssist(new ExecutorContext(this.dataDefinitionRuntimeController));
        this.queryCache = new DataQueryCache();
        this.blockDao = (IQueryBlockDefineDao)BeanUtil.getBean(QueryBlockDefineDao.class);
        this.modelDao = (QueryModalDefineDao)BeanUtil.getBean(QueryModalDefineDao.class);
        this.querModelAuthorityProvider = (QueryModelAuthorityProvider)BeanUtil.getBean(QueryModelAuthorityProvider.class);
        this.queryEntityUtil = (QueryEntityUtil)BeanUtil.getBean(QueryEntityUtil.class);
        this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
    }

    private ITree<QueryEntityDataObject> buildTreeNode(EntityViewDefine view, IEntityTable rs, IEntityRow row) {
        int directChildCount = rs.getDirectChildCount(row.getEntityKeyData());
        ITree<QueryEntityDataObject> treeNode = this.buildTreeNode(view, row);
        ((QueryEntityDataObject)treeNode.getData()).setChildCount(directChildCount);
        treeNode.setLeaf(directChildCount == 0);
        return treeNode;
    }

    private ITree<QueryEntityDataObject> buildTreeNode(EntityViewDefine view, IEntityRow row) {
        QueryEntityDataObject entObj = QueryEntityDataObject.buildEntityData(row);
        entObj.setViewKey(view.getEntityId());
        ITree treeNode = new ITree((INode)entObj);
        treeNode.setIcons(null);
        return treeNode;
    }

    public List<ITree<QueryEntityDataObject>> queryEntityRoots(EntityViewDefine view, DimensionValueSet dateValSet) {
        ArrayList<ITree<QueryEntityDataObject>> roots = new ArrayList<ITree<QueryEntityDataObject>>();
        IEntityTable entityTable = QueryHelper.getEntityTableOnce(view, dateValSet);
        List rootRows = entityTable.getRootRows();
        for (IEntityRow row : rootRows) {
            ITree<QueryEntityDataObject> treeNode = this.buildTreeNode(view, entityTable, row);
            roots.add(treeNode);
        }
        return roots;
    }

    public List<ITree<QueryEntityDataObject>> queryEntityChildren(String entKey, EntityViewDefine view, DimensionValueSet dateValSet) {
        ArrayList<ITree<QueryEntityDataObject>> children = new ArrayList<ITree<QueryEntityDataObject>>();
        IEntityTable rs = QueryHelper.getEntityTableOnce(view, dateValSet);
        List childRows = rs.getChildRows(entKey);
        for (IEntityRow row : childRows) {
            ITree<QueryEntityDataObject> treeNode = this.buildTreeNode(view, rs, row);
            children.add(treeNode);
        }
        return children;
    }

    public List<ITree<QueryEntityDataObject>> queryEntityRootsByEntityAndDateV(HashMap<String, String> tb, DimensionValueSet dateValSet) {
        String key = tb.get("key");
        List<ITree<QueryEntityDataObject>> res = null;
        try {
            boolean falg = Boolean.parseBoolean(tb.get("isEntity"));
            UUID tableKey = falg ? UUID.fromString(key) : UUID.fromString(tb.get("viewKey"));
            EntityViewDefine view = null;
            if (key != null) {
                view = falg ? this.entityViewRunTimeController.buildEntityView(tableKey.toString()) : this.entityViewRunTimeController.buildEntityView(tableKey.toString());
            }
            res = falg ? this.queryEntityRoots(view, dateValSet) : this.queryEntityChildren(key, view, dateValSet);
            for (ITree<QueryEntityDataObject> re : res) {
                QueryEntityDataObject object = (QueryEntityDataObject)re.getData();
                String tablekey = falg ? key : this.queryEntityUtil.getEntityTablelDefineByView(view.getEntityId()).getID();
                object.setFieldValue("tableKey", tablekey);
            }
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
        return res;
    }

    public List<ITree<QueryEntityDataObject>> queryTreeRootByMasterKey(QueryDimensionDefine dim, DimensionValueSet dateValSet) {
        List<ITree<QueryEntityDataObject>> children = null;
        String viewId = dim.getViewId();
        try {
            if (dim.getSelectItems() != null && !dim.getSelectItems().isEmpty()) {
                children = this.queryEntityRootsByEntity(dim, dateValSet);
            } else {
                if (viewId == null || "".equals(viewId)) {
                    return null;
                }
                String tableKey = this.queryEntityUtil.getTableKeyByView(viewId);
                HashMap<String, String> tb = new HashMap<String, String>();
                tb.put("isEntity", "true");
                tb.put("key", tableKey);
                children = this.queryEntityRootsByEntityAndDateV(tb, dateValSet);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return children;
    }

    @Deprecated
    public List<ITree<QueryEntityDataObject>> queryEntityRootsByEntity(QueryDimensionDefine dim, DimensionValueSet dateValSet) {
        try {
            ArrayList<ITree<QueryEntityDataObject>> res = new ArrayList<ITree<QueryEntityDataObject>>();
            List<QuerySelectItem> selectItems = dim.getSelectItems();
            HashMap<String, ITree<QueryEntityDataObject>> selectItemMap = new HashMap<String, ITree<QueryEntityDataObject>>();
            String tableKey = this.queryEntityUtil.getTableKeyByView(dim.getViewId());
            HashMap<String, String> tb = new HashMap<String, String>();
            tb.put("isEntity", "true");
            tb.put("key", tableKey);
            List<ITree<QueryEntityDataObject>> rootNodes = this.queryEntityRootsByEntityAndDateV(tb, dateValSet);
            for (ITree<QueryEntityDataObject> node : rootNodes) {
                res.add(node);
                selectItemMap.put(node.getKey(), node);
            }
            for (QuerySelectItem item : selectItems) {
                if (StringUtils.isEmpty((String)item.getParentPath())) continue;
                String[] parentPaths = item.getParentPath().split(";");
                for (int i = 0; i < parentPaths.length; ++i) {
                    ITree parNode;
                    String key = parentPaths[i];
                    HashMap<String, String> tb1 = new HashMap<String, String>();
                    tb1.put("isEntity", "false");
                    tb1.put("key", key);
                    tb1.put("viewKey", dim.getViewId());
                    List<ITree<QueryEntityDataObject>> childNodes = this.queryEntityRootsByEntityAndDateV(tb1, dateValSet);
                    for (ITree<QueryEntityDataObject> node : childNodes) {
                        if (selectItemMap.containsKey(node.getKey())) continue;
                        selectItemMap.put(node.getKey(), node);
                    }
                    if (!selectItemMap.containsKey(key) || (parNode = (ITree)selectItemMap.get(key)).getChildren() != null && !parNode.getChildren().isEmpty()) continue;
                    parNode.setChildren(childNodes);
                }
            }
            return res;
        }
        catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public List<QueryEntityData> queryEntityData(List<QuerySelectItem> dimensions) {
        if (dimensions == null || dimensions.isEmpty()) {
            return null;
        }
        ArrayList<QueryEntityData> queryEntityList = new ArrayList<QueryEntityData>();
        HashMap<String, QueryEntityData> map = new HashMap<String, QueryEntityData>();
        for (int i = 0; i < dimensions.size(); ++i) {
            QuerySelectItem item = dimensions.get(i);
            QueryEntityData entityData = new QueryEntityData();
            entityData.setId(item.getCode());
            entityData.setTitle(item.getTitle());
            entityData.setIsLeaf(true);
            map.put(entityData.getId(), entityData);
            if (i == 0) {
                queryEntityList.add(entityData);
                continue;
            }
            QueryEntityData parentData = null;
            if (!StringUtils.isEmpty((String)item.getParentPath())) {
                String[] parentIds = item.getParentPath().split(";");
                parentData = this.findParent(map, parentIds);
            }
            if (null == parentData) {
                queryEntityList.add(entityData);
                continue;
            }
            List<QueryEntityData> childs = parentData.getChilds();
            if (childs == null) {
                childs = new ArrayList<QueryEntityData>();
            }
            childs.add(entityData);
            parentData.setChilds(childs);
            parentData.setIsLeaf(false);
        }
        return queryEntityList;
    }

    private QueryEntityData findParent(Map<String, QueryEntityData> map, String[] parentIds) {
        int index;
        for (int i = index = parentIds.length - 1; i >= 0; --i) {
            if (map.get(parentIds[i]) == null) continue;
            QueryEntityData parentData = map.get(parentIds[i]);
            return parentData;
        }
        return null;
    }

    public QueryBlockDefine getQueryFormGrid(QueryBlockDefine block, String id, GridBlockType type) {
        try {
            if (block == null) {
                ObjectMapper mapper = new ObjectMapper();
                block = new QueryBlockDefine();
                block.setTitle("\u65b0\u5efa\u67e5\u8be2\u5757");
                logger.debug("id" + id);
                if (StringUtils.isEmpty((String)id)) {
                    block.setId(UUID.randomUUID().toString());
                } else {
                    block.setId(id);
                }
                QueryGridExtension extension = new QueryGridExtension();
                extension.setGridBlockType(type);
                block.setBlockExtension(mapper.writeValueAsString((Object)extension));
                QueryDimensionDefine qd = new QueryDimensionDefine();
                qd.setLayoutType(QueryLayoutType.LYT_COL);
                qd.setSelectFields(new ArrayList<QuerySelectField>());
            }
            if (block.getHasUserForm()) {
                block.setGridData(block.getFormdata());
            } else {
                Grid2Data formGrid;
                if (block.getQueryDimensions() == null) {
                    formGrid = QueryHelper.getHFormStyle(block, false, true);
                } else {
                    block.setQueryDimensions(this.addDimType(block.getQueryDimensions()));
                    QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
                    GridBlockType gridBlockType = gridExtension.getGridBlockType();
                    if (GridBlockType.MIXED != gridBlockType) {
                        QueryGrid gridControl = new QueryGrid(block, false, true);
                        formGrid = gridControl.drawFormGrid();
                    } else {
                        formGrid = QueryHelper.getHFormStyle(block, false, true);
                    }
                }
                SimpleModule module = new SimpleModule();
                module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
                module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
                if (formGrid.getRowCount() > 0) {
                    formGrid.insertRows(0, 1, 0);
                    formGrid.setRowHidden(0, true);
                }
                if (null == block.getBlockInfo().getWordLabels()) {
                    block.getBlockInfo().setWordLabels(new ArrayList<WordLabelDefine>());
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule((Module)module);
                String result = mapper.writeValueAsString((Object)formGrid);
                block.setGridData(result);
                if (block.getFormdata() == null) {
                    block.setFormdata(result);
                }
                block.setBlockInfoBlob(mapper.writeValueAsString((Object)block.getBlockInfo()));
                if (StringUtil.isNullOrEmpty((String)block.getBlockInfo().getFormSchemeKey())) {
                    Optional<QuerySelectField> field;
                    FieldSelectHelper fieldhelper;
                    FormSchemeDefine formScheme;
                    Optional<QuerySelectField> masterField;
                    List<QuerySelectField> fields;
                    List<QueryDimensionDefine> dimes = block.getBlockInfo().getQueryDimensions();
                    if (dimes != null && !dimes.isEmpty() && (fields = block.getBlockInfo().getQueryDimensions().get(0).getSelectFields()) != null && !fields.isEmpty() && (masterField = fields.stream().filter(f -> f.getIsMaster().equals("true")).findFirst()).isPresent() && (formScheme = (fieldhelper = new FieldSelectHelper()).getFormSchemeByField((field = fields.stream().filter(f -> ((QuerySelectField)masterField.get()).getRegionKey().equals(f.getRegionKey()) && f.getIsMaster().equals("false")).findFirst()).get().getCode())) != null) {
                        block.setTaskDefStartPeriod(formScheme.getFromPeriod());
                        block.setTaskDefEndPeriod(formScheme.getToPeriod());
                    }
                } else {
                    String formSchemeKey = block.getBlockInfo().getFormSchemeKey();
                    if (formSchemeKey != null && !"".equals(formSchemeKey)) {
                        FormSchemeDefine formScheme = this.viewController.getFormScheme(block.getBlockInfo().getFormSchemeKey());
                        block.setTaskDefStartPeriod(formScheme.getFromPeriod());
                        block.setTaskDefEndPeriod(formScheme.getToPeriod());
                    }
                }
            }
            return block;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error("", ex);
            return null;
        }
    }

    public List<QueryDimensionDefine> addDimType(List<QueryDimensionDefine> oldDimensions) {
        ArrayList<QueryDimensionDefine> dims = new ArrayList<QueryDimensionDefine>();
        for (QueryDimensionDefine dim : oldDimensions) {
            if (dim.getDimensionType() == QueryDimensionType.QDT_ENTITY) {
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(dim.getViewId());
                boolean periodUnit = false;
                try {
                    periodUnit = this.queryEntityUtil.isPeriodUnit(entityView.getEntityId());
                }
                catch (JQException e) {
                    logger.error("\u5224\u65ad\u8be5\u89c6\u56fe\u5bf9\u5e94\u7684\u5b9e\u4f53\u662f\u5426\u4e3a\u65f6\u671f\u4e3b\u4f53\u51fa\u9519\uff01", e);
                }
                String dwDimName = this.dataAssist.getDimensionName(entityView);
                if (dim.getDimensionName() == null || dim.getDimensionName().equals("")) {
                    dim.setDimensionName(dwDimName);
                }
                dim.setIsPeriodDim(periodUnit);
                dims.add(dim);
                continue;
            }
            dims.add(dim);
        }
        return dims;
    }

    public List<QueryDimensionDefine> setDimConditionTitle(List<QueryDimensionDefine> queryDimensionDefines) {
        for (QueryDimensionDefine queryDimension : queryDimensionDefines) {
            EntityViewDefine entityView;
            if (queryDimension.getDimensionType() == QueryDimensionType.QDT_FIELD || QueryDimensionType.QDT_GRIDFIELD.equals((Object)queryDimension.getDimensionType()) || QueryDimensionType.QDT_UPLOADSTATUS.equals((Object)queryDimension.getDimensionType()) || queryDimension.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
            String title = "";
            if (queryDimension.isPeriodDim()) {
                try {
                    if (PeriodType.CUSTOM.equals((Object)queryDimension.getPeriodType())) {
                        entityView = QueryHelper.getEntityView(queryDimension.getViewId());
                        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityView.getEntityId());
                        List<QuerySelectItem> items = queryDimension.getSelectItems();
                        QuerySelectionType selectType = queryDimension.getExtensionSelectionType();
                        for (int j = 0; j < items.size(); ++j) {
                            QuerySelectItem item = items.get(j);
                            String itemtitle = item.getCode();
                            itemtitle = periodProvider.getPeriodTitle(itemtitle);
                            if (selectType == QuerySelectionType.SINGLE) {
                                title = title + itemtitle;
                                continue;
                            }
                            String splitStr = selectType == QuerySelectionType.RANGE ? "-" : ",";
                            title = title + (j < items.size() && j != 0 ? splitStr : " ");
                            title = title + itemtitle;
                        }
                        queryDimension.setConditionTitle(title);
                    }
                }
                catch (Exception e) {
                    logger.error("", e);
                }
                List<QuerySelectItem> items = queryDimension.getSelectItems();
                QuerySelectionType selectType = queryDimension.getExtensionSelectionType();
                for (int i = 0; i < items.size(); ++i) {
                    QuerySelectItem item = items.get(i);
                    String itemtitle = item.getTitle();
                    PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)itemtitle);
                    itemtitle = pw.toTitleString();
                    if (selectType == QuerySelectionType.SINGLE) {
                        title = title + itemtitle;
                        continue;
                    }
                    String splitStr = selectType == QuerySelectionType.RANGE ? "-" : ",";
                    title = title + (i < items.size() && i != 0 ? splitStr : " ");
                    title = title + itemtitle;
                }
            } else {
                entityView = QueryHelper.getEntityView(queryDimension.getViewId());
                if (entityView == null) {
                    try {
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryDimension.getViewId());
                        queryDimension.setViewId(fieldDefine.getEntityKey());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                IEntityTable entityTable = QueryHelper.getEntityTableOnce(entityView, null);
                if (queryDimension.getSelectItems() != null && !queryDimension.getSelectItems().isEmpty()) {
                    for (QuerySelectItem item : queryDimension.getSelectItems()) {
                        IEntityRow entityRow = entityTable.findByEntityKey(item.getCode());
                        if (entityRow == null) continue;
                        String rowtitle = entityRow.getTitle();
                        title = title + " " + rowtitle;
                    }
                }
            }
            queryDimension.setConditionTitle(title);
        }
        return queryDimensionDefines;
    }

    public QueryBlockDefine getChartFormGrid(QueryBlockDefine block, ChartType chartType, String linkedBlock, String dashboardUrl, String biToken, String datasettype, String auth, String isSimple) {
        try {
            QueryModalDefine modal = this.modelDao.getQueryModalDefineById(block.getModelID());
            block.setTitle("\u65b0\u5efa\u67e5\u8be2\u5757");
            if (!"SIMPLEOWER".equals(modal.getModelType().toString())) {
                block.getBlockInfo().setBlockType(QueryBlockType.QBT_CHART);
            }
            block.getBlockInfo().setChartType(chartType);
            block.getBlockInfo().setLinkedBlock(linkedBlock);
            QueryBlockDefine blockDefine = this.blockDao.GetQueryBlockDefineById(block.getId());
            String guid = block.getId();
            String addUrl = dashboardUrl + "/api/ms/dashboard/chart/item?" + "ticket" + "=" + auth;
            JSONObject item = new JSONObject();
            item.put("guid", (Object)guid);
            String title = URLEncoder.encode(block.getTitle(), "UTF-8");
            item.put("title", (Object)title);
            item.put("type", (Object)block.getBlockInfo().getChartType());
            item.put("datasetName", (Object)(datasettype + "@_@" + guid));
            String para = "item=" + item.toString();
            JSONObject result = HttpUtils.doRequest(addUrl, para, "POST");
            Boolean isSimpleBool = isSimple != null ? Boolean.parseBoolean(isSimple) : false;
            if (result.has("status") && "200".equals(result.get("status").toString()) && !isSimpleBool.booleanValue()) {
                if (blockDefine != null) {
                    block.converBlockToByte();
                    this.blockDao.UpdateQueryBlockDefine(block);
                } else {
                    this.blockDao.InsertQueryBlockDefine(block);
                }
            }
            logger.info("addUrl" + addUrl + para);
            logger.debug("response:" + result.toString());
            return block;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    private Grid2Data getDataGrid(QueryBlockDefine block, QueryModelType modelType, Boolean isExport) {
        try {
            QueryGrid gridControl = null;
            gridControl = modelType != QueryModelType.SIMPLEOWER ? new QueryGrid(block, isExport, false) : new QueryGrid(block, isExport);
            Grid2Data gridData = gridControl.drawFormGrid();
            return gridData;
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u7ed3\u679c\u751f\u6210QueryBlockDefine\u51fa\u9519:" + e.getMessage());
            return null;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f02\u5e38", e);
            return null;
        }
    }

    public QueryBlockDefine getQueryDataGrid(QueryBlockDefine block, QueryModelType queryModelCategory, Boolean isExport) {
        try {
            String taskdefendperiod = block.getMasterKeyValue("taskdefendperiod").replaceAll("\"", "");
            String taskdefstartperiod = block.getMasterKeyValue("taskdefstartperiod").replaceAll("\"", "");
            block.setTaskDefEndPeriod("null".equals(taskdefendperiod) ? null : taskdefendperiod);
            block.setTaskDefStartPeriod("null".equals(taskdefstartperiod) ? null : taskdefstartperiod);
            QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
            QueryGrid gridControl = null;
            gridControl = queryModelCategory != QueryModelType.SIMPLEOWER ? new QueryGrid(block, isExport, false) : new QueryGrid(block, isExport);
            Grid2Data gridData = gridControl.drawFormGrid();
            if (gridControl instanceof QueryGrid && gridControl.defina != null) {
                Boolean end = gridControl.defina.block.getEnd();
                List<ColumnWidth> columnWidth = gridControl.defina.block.getBlockInfo().getColumnWidth();
                int totalCount = gridControl.defina.block.getBlockInfo().getTotalCount();
                block.setEnd(end);
                block.getBlockInfo().setTotalCount(totalCount);
                block.getBlockInfo().setColumnWidth(columnWidth);
            }
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)gridData);
            block.setGridData(result);
            return block;
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u7ed3\u679c\u751f\u6210QueryBlockDefine\u51fa\u9519:" + e.getMessage());
            return null;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f02\u5e38", e);
            return null;
        }
    }

    public Grid2Data getPrintGrid2Data(HttpServletResponse response, HttpServletRequest request, QueryBlockDefine blockDefine, QueryModelType modeltype) {
        String queryMode = request.getHeader("QueryMode");
        QueryModelCategory queryModelCategory = QueryModelCategory.DATAQUERY;
        if (!StringUtils.isEmpty((String)queryMode)) {
            queryModelCategory = QueryModelCategory.valueOf(queryMode);
        }
        blockDefine.setIsPaging(false);
        Grid2Data gridData = this.getDataGrid(blockDefine, modeltype, true);
        return gridData;
    }

    public void printGrid2Data(HttpServletResponse response, HttpServletRequest request, ByteArrayOutputStream out, QueryBlockDefine block) {
        try {
            byte[] byteArray = out.toByteArray();
            QueryExportData data = new QueryExportData(block.getTitle(), byteArray);
            String agent = request.getHeader("User-Agent").toLowerCase();
            String fileName = block.getTitle() + ".pdf";
            int place = agent.indexOf("firefox");
            if (place > 0) {
                fileName.replace(" ", "_");
            }
            String resultFileName = new String(fileName.getBytes(), "iso8859-1");
            response.setContentType("application/octet-stream");
            response.setHeader(HtmlUtils.cleanHeaderValue((String)"Content-disposition"), HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + resultFileName)));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(data.getData());
            response.flushBuffer();
            if (outputStream != null) {
                outputStream.close();
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }

    public void Export(HttpServletResponse response, HttpServletRequest request, QueryBlockDefine blockDefine) {
        try {
            SXSSFSheet sheet = null;
            SXSSFWorkbook workbook = new SXSSFWorkbook(2000);
            sheet = workbook.createSheet(blockDefine.getTitle());
            sheet.setRowSumsBelow(false);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
            module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule((Module)module);
            this.queryCache.setExport(true);
            boolean isPaging = blockDefine.isPaging();
            blockDefine.setIsPaging(false);
            String queryMode = request.getHeader("QueryMode");
            QueryModelType queryModelCategory = QueryModelType.OWNER;
            if (!StringUtils.isEmpty((String)queryMode)) {
                if (queryMode.equals("SimpleQuery")) {
                    queryMode = "simpleOwer";
                }
                queryModelCategory = QueryModelType.valueOf(queryMode);
            }
            if (blockDefine.getBlockType() == QueryBlockType.QBT_CUSTOMENTRY) {
                blockDefine.getBlockInfo().setDimLevelShowType(2);
            }
            Grid2Data gridData = this.getDataGrid(blockDefine, queryModelCategory, true);
            gridData.setColumnWidth(1, 250);
            if (gridData != null && !"".equals(gridData)) {
                String conditionStr;
                if (blockDefine.getBlockInfo().getExportGridTitle()) {
                    gridData.insertRows(1, 1, 1);
                    gridData.mergeCells(1, 1, gridData.getColumnCount() - 1, 1);
                    GridCellData titleCellData = gridData.getGridCellData(1, 1);
                    titleCellData.setShowText(blockDefine.getTitle());
                    titleCellData.setEditText(blockDefine.getTitle());
                    titleCellData.setFontSize(16);
                    titleCellData.setHorzAlign(3);
                    titleCellData.setForeGroundColor(QueryConst.htmlColorToInt(blockDefine.getBlockInfo().getFontColor()));
                    gridData.setRowHeight(1, 50);
                }
                if (blockDefine.getBlockInfo().getExportGridCondition() && !StringUtil.isNullOrEmpty((String)(conditionStr = this.getBlockConditionStr(blockDefine)))) {
                    gridData.insertRows(2, 1, 1);
                    gridData.mergeCells(1, 2, gridData.getColumnCount() - 1, 2);
                    GridCellData conditionCellData = gridData.getGridCellData(1, 2);
                    conditionCellData.setShowText(conditionStr);
                    conditionCellData.setEditText(conditionStr);
                    conditionCellData.setFontSize(16);
                    conditionCellData.setHorzAlign(1);
                    gridData.setRowHeight(2, 40);
                }
            }
            int rowHeight = blockDefine.getBlockInfo().getRowHeight();
            int headerRowCount = gridData.getHeaderRowCount();
            int row = gridData.getRowCount();
            int col = gridData.getColumnCount();
            for (int i = 0; i < row; ++i) {
                if (i >= headerRowCount) {
                    gridData.setRowHeight(i, rowHeight);
                }
                for (int j = 0; j < col; ++j) {
                    GridCellData cell = gridData.getGridCellData(j, i);
                    JSONObject persistenceData = new JSONObject(cell.getDataExString());
                    if (!persistenceData.has("dataType")) {
                        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                    } else {
                        try {
                            String type = persistenceData.get("dataType").toString();
                            int celltype = Integer.parseInt(type);
                            cell.setDataType(celltype);
                            if (persistenceData.has("formatter")) {
                                cell.setFormatter(persistenceData.get("formatter").toString());
                                if (cell.getEditText().equals("\u2014\u2014")) {
                                    cell.setEditText(null);
                                }
                            }
                            if (celltype == 1 || celltype == 4) {
                                cell.setDataType(1);
                                cell.setEditText(null);
                            }
                        }
                        catch (Exception ex) {
                            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                        }
                    }
                    if (cell.getDepth() <= 0) continue;
                    String showText = cell.getShowText();
                    for (int k = 0; k < cell.getDepth(); ++k) {
                        showText = "   " + showText;
                    }
                    cell.setShowText(showText);
                }
            }
            WorksheetWriter2 worksheetWriter2 = new WorksheetWriter2(gridData, (Sheet)sheet, workbook);
            worksheetWriter2.writeSheet();
            ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
            workbook.write(os);
            workbook.dispose();
            byte[] byteArray = os.toByteArray();
            QueryExportData data = new QueryExportData(blockDefine.getTitle(), byteArray);
            String agent = request.getHeader("User-Agent").toLowerCase();
            String fileName = blockDefine.getTitle() + ".xlsx";
            int place = agent.indexOf("firefox");
            if (place > 0) {
                fileName.replace(" ", "_");
            }
            String resultFileName = new String(fileName.getBytes(), "iso8859-1");
            response.setContentType("application/octet-stream");
            response.setHeader(HtmlUtils.cleanHeaderValue((String)"Content-disposition"), HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + resultFileName)));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(data.getData());
            response.flushBuffer();
            if (outputStream != null) {
                outputStream.close();
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u9519\u8bef\uff1a", (Object)e.getMessage());
        }
    }

    public String getBlockConditionStr(QueryBlockDefine block) {
        String conditions = "";
        List<QueryDimensionDefine> dimensions = block.getQueryDimensions();
        if (dimensions.size() <= 1) {
            return "";
        }
        for (QueryDimensionDefine dimension : dimensions) {
            if (dimension.getDimensionType() == QueryDimensionType.QDT_FIELD || QueryDimensionType.QDT_GRIDFIELD.equals((Object)dimension.getDimensionType()) || QueryDimensionType.QDT_UPLOADSTATUS.equals((Object)dimension.getDimensionType()) || QueryDimensionType.QDT_DICTIONARY.equals((Object)dimension.getDimensionType()) || dimension.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
            String cstr = dimension.getTitle() != null ? dimension.getTitle() + "[" : " ";
            QuerySelectionType selectType = dimension.getExtensionSelectionType();
            List<QuerySelectItem> items = dimension.getSelectItems();
            for (int i = 0; i < items.size(); ++i) {
                QuerySelectItem item = items.get(i);
                String itemtitle = item.getTitle();
                if (dimension.isPeriodDim()) {
                    PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)itemtitle);
                    itemtitle = pw.toTitleString();
                }
                cstr = cstr + itemtitle;
                if (selectType == QuerySelectionType.SINGLE) {
                    String scopTitle = "";
                    scopTitle = DimensionItemScop.getTitle(dimension.getItemScop());
                    cstr = cstr + "(" + scopTitle + ")];";
                    continue;
                }
                String splitStr = selectType == QuerySelectionType.RANGE ? "-" : ",";
                cstr = cstr + (i == items.size() - 1 ? "];" : splitStr);
            }
            if (items.size() == 0) {
                if (selectType == QuerySelectionType.SINGLE) {
                    String scopTitle = DimensionItemScop.getTitle(dimension.getItemScop());
                    cstr = cstr + "(" + scopTitle + ")];";
                } else if (dimension.getTitle() != null) {
                    cstr = cstr + "];";
                }
            }
            conditions = conditions + cstr;
        }
        return conditions;
    }

    private String getFilterConditions(String condition, QueryItemSortDefine sortDefine, FieldDefine field) {
        List<QueryFilterDefine> filters;
        List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        if ((filters = sortDefine.getFilterCondition()) == null) {
            return condition;
        }
        String itemCondition = "";
        boolean isConditionNull = StringUtil.isNullOrEmpty((String)condition);
        int i = 0;
        for (QueryFilterDefine filter : filters) {
            if (filter.getSymbol() == null || filter.getValue() == null) continue;
            String expression = "";
            if (this.isNumField(field)) {
                DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.getSymbol(filter.getSymbol()), (Object)filter.getValue());
                expression = exp.toFormula();
            } else {
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(deployInfoByColumnKey.getTableName()).append("[").append(deployInfoByColumnKey.getFieldName()).append("]");
                String fieldCondition = fieldStr.toString();
                switch (filter.getSymbol().toString()) {
                    case "Start": {
                        expression = fieldCondition + " like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "NotStart": {
                        expression = fieldCondition + " not like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "End": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "'";
                        break;
                    }
                    case "NotEnd": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "' ";
                        break;
                    }
                    case "Contain": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "%'";
                        break;
                    }
                    case "NotContain": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "%' ";
                    }
                }
            }
            itemCondition = i > 0 ? itemCondition + String.format(" %s %s%s", sortDefine.getFilterRelation(), expression, isConditionNull ? "" : ")") : (isConditionNull ? itemCondition + expression : itemCondition + String.format(" AND (%s", expression));
            ++i;
        }
        if (StringUtil.isNullOrEmpty((String)condition)) {
            return itemCondition;
        }
        condition = condition + String.format(" OR (%s)", itemCondition);
        return condition;
    }

    private CompareType getSymbol(FilterSymbols symbol) {
        switch (symbol.toString()) {
            case "MoreThan": {
                return CompareType.MORE_THAN;
            }
            case "LessThan": {
                return CompareType.LESS_THAN;
            }
            case "MoreAndEque": {
                return CompareType.MORE_THAN_OR_EQUAL;
            }
            case "LessAndEque": {
                return CompareType.LESS_THAN_OR_EQUAL;
            }
            case "Eque": {
                return CompareType.EQUAL;
            }
            case "NotEque": {
                return CompareType.NOT_EQUAL;
            }
        }
        return CompareType.EQUAL;
    }

    public Set<String> getDimenSionsName(List<QueryDimensionDefine> queryDimensionDefineList, QueryLayoutType type) {
        QueryHelper queryHelper = new QueryHelper();
        HashSet<String> dimNameList = new HashSet<String>();
        try {
            int arrIndex = 0;
            String[] arr = new String[queryDimensionDefineList.size()];
            if (queryDimensionDefineList.size() > 0 || queryDimensionDefineList != null) {
                for (QueryDimensionDefine queryDimensionDefine : queryDimensionDefineList) {
                    EntityViewDefine entityView;
                    if (queryDimensionDefine.getLayoutType() != type || (entityView = this.entityViewRunTimeController.buildEntityView(queryDimensionDefine.getViewId())) == null) continue;
                    if (queryDimensionDefine.isHidden()) {
                        dimNameList.add(QueryHelper.getDimName(entityView));
                        continue;
                    }
                    arr[arrIndex] = QueryHelper.getDimName(entityView);
                    ++arrIndex;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5339\u914d\u7ef4\u5ea6\u540d\u5217\u8868\u5f02\u5e38" + e);
        }
        return dimNameList;
    }

    public IReadonlyTable getQueryDataTable(QueryBlockDefine block, QueryLayoutType queryLoutType, boolean isDetail) {
        try {
            boolean flag;
            LinkedHashMap<QueryDimensionType, LinkedHashMap<String, FieldDefine>> dimFiends;
            ReportFmlExecEnvironment environment;
            ExecutorContext executorContext;
            DimensionValueSet dimV;
            String valueConditions;
            String fieldCondition;
            IGroupingQuery groupQuery;
            String blockCondition;
            block89: {
                List<QuerySelectField> selectedFields;
                blockCondition = null;
                Set<Object> dimenSionNameList = new HashSet();
                if (!isDetail || queryLoutType != null) {
                    dimenSionNameList = this.getDimenSionsName(block.getQueryDimensions(), queryLoutType);
                }
                if ((selectedFields = this.getSelectFieldsInBlock(block)) == null || selectedFields.size() == 0) {
                    return null;
                }
                FieldDefine fieldDefine = null;
                groupQuery = this.dataAccessProvider.newGroupingQuery(null);
                QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
                boolean isGather = gridExtension.isShowGather();
                int j = 0;
                fieldCondition = "";
                valueConditions = "";
                this.queryCache.setSorted(false);
                boolean isFilterMode = false;
                for (QuerySelectField sf : selectedFields) {
                    if (!isFilterMode) {
                        isFilterMode = QueryHelper.checkIsFilterMode(sf);
                    }
                    boolean isMaster = Boolean.parseBoolean(sf.getIsMaster());
                    int colindex = 0;
                    if (sf.isHidden() && !isMaster) continue;
                    List<QueryStatisticsItem> statisticsFields = sf.getStatisticsFields();
                    if (statisticsFields != null && statisticsFields.size() > 0) {
                        for (int i = 0; i < statisticsFields.size(); ++i) {
                            QueryStatisticsItem item = statisticsFields.get(i);
                            groupQuery.addExpressionColumn(item.getFormulaExpression());
                        }
                    }
                    if (sf.getCustom()) {
                        groupQuery.addExpressionColumn(sf.getCustomValue());
                        continue;
                    }
                    QueryItemSortDefine sortDefine = sf.getSort();
                    FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(sf.getCode());
                    if (field == null) continue;
                    if (fieldDefine == null) {
                        fieldDefine = field;
                    }
                    if (!isDetail && sf.getIsGroupField() || !isDetail && isMaster) {
                        colindex = !dimenSionNameList.contains(this.dataAssist.getDimensionName(field)) ? groupQuery.addGroupColumn(field) : groupQuery.addColumn(field);
                    } else if (!sf.isHidden() || isMaster) {
                        colindex = groupQuery.addColumn(field);
                    }
                    if (isGather) {
                        if (sf.getGatherType() != FieldGatherType.FIELD_GATHER_NONE) {
                            groupQuery.setGatherType(j, sf.getGatherType());
                        } else {
                            groupQuery.setGatherType(j, field.getGatherType());
                        }
                    } else {
                        groupQuery.setGatherType(j, field.getGatherType());
                    }
                    if (sf.getIsSorted()) {
                        this.queryCache.setSorted(true);
                        if (sortDefine.getSortType() != null) {
                            groupQuery.addOrderByItem(field, sortDefine.getSortType() == QuerySortType.SORT_DESC);
                        }
                        fieldCondition = this.getFilterConditions(fieldCondition, sortDefine, field);
                        ArrayList<Object> fvs = sortDefine.getFilterValues();
                        if (fvs != null && fvs.size() > 0) {
                            groupQuery.setColumnFilterValueList(colindex, (ArrayList)fvs);
                        }
                    }
                    ++j;
                }
                dimV = new DimensionValueSet();
                executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                environment = new ReportFmlExecEnvironment(this.viewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, null);
                List<QueryDimensionDefine> dims = block.getQueryDimensions();
                dimFiends = new LinkedHashMap<QueryDimensionType, LinkedHashMap<String, FieldDefine>>();
                if (dims == null) break block89;
                List<QueryEntityData> selectNewTree = null;
                List<Object> selectDateList = null;
                for (QueryDimensionDefine dim : dims) {
                    LinkedHashMap<String, FieldDefine> fields;
                    block90: {
                        String dwDimName;
                        ArrayList<String> entitys;
                        block96: {
                            block95: {
                                Optional<QueryDimensionDefine> define;
                                block91: {
                                    PeriodType ptype;
                                    block92: {
                                        block93: {
                                            block94: {
                                                if (dim.getDimensionType() == QueryDimensionType.QDT_CUSTOM) {
                                                    List<QuerySelectItem> items = dim.getSelectItems();
                                                    if (items == null || items.size() <= 0) continue;
                                                    ArrayList<String> formulas = new ArrayList<String>();
                                                    for (QuerySelectItem querySelectItem : items) {
                                                        String formula;
                                                        if (StringUtils.isEmpty((String)querySelectItem.getTitle()) || StringUtils.isEmpty((String)(formula = querySelectItem.getTitle()))) continue;
                                                        formulas.add(formula);
                                                    }
                                                    if (formulas.size() == 0) continue;
                                                    if (formulas.size() == 1) {
                                                        blockCondition = (String)formulas.get(0);
                                                        continue;
                                                    }
                                                    blockCondition = "AND (";
                                                    boolean hasDot = false;
                                                    for (String formula : formulas) {
                                                        if (hasDot) {
                                                            blockCondition = blockCondition + ", ";
                                                        }
                                                        hasDot = true;
                                                        blockCondition = blockCondition + formula;
                                                    }
                                                    blockCondition = blockCondition + ")";
                                                    continue;
                                                }
                                                if (dim.getDimensionType() != QueryDimensionType.QDT_ENTITY) break block90;
                                                entitys = new ArrayList<String>();
                                                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(dim.getViewId());
                                                String tableTitle = this.queryEntityUtil.getTableTitleByViewKey(dim.getViewId());
                                                boolean bl = this.queryEntityUtil.isPeriodUnit(dim.getViewId());
                                                if (dim.getSelectItems() != null && !dim.getSelectItems().isEmpty() && dim.getLayoutType() != QueryLayoutType.LYT_CONDITION && !bl) {
                                                    selectNewTree = this.queryEntityData(dim.getSelectItems());
                                                }
                                                if (dim.getTitle() == null) {
                                                    dim.setTitle(tableTitle);
                                                }
                                                dwDimName = this.dataAssist.getDimensionName(entityView);
                                                if (!bl) break block91;
                                                if (dim.getSelectItems() == null || dim.getSelectItems().size() <= 0) break block92;
                                                if (selectDateList == null && QueryLayoutType.LYT_CONDITION != dim.getLayoutType()) {
                                                    switch (dim.getSelectType()) {
                                                        case MULTIITES: 
                                                        case SINGLE: {
                                                            selectDateList = new ArrayList<String>();
                                                            List<QuerySelectItem> timeSelect = dim.getSelectItems();
                                                            for (QuerySelectItem seItem : timeSelect) {
                                                                selectDateList.add(seItem.getTitle());
                                                            }
                                                            break;
                                                        }
                                                        case RANGE: 
                                                        case NONE: {
                                                            PeriodType ptype2 = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                                            String startTime = dim.getSelectItems().get(0).getTitle();
                                                            String endTime = dim.getSelectItems().get(1).getTitle();
                                                            selectDateList = this.getTimeFromToEnd(startTime, endTime, ptype2);
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (!(define = dims.stream().filter(dimension -> dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimension.getViewId().equals(dim.getViewId())).findFirst()).isPresent()) break block93;
                                                if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
                                                if (selectDateList != null) break block94;
                                                List<QuerySelectField> selectFieldsInBlock = QueryHelper.getSelectFieldsInBlock(block);
                                                boolean existSelectByField = selectFieldsInBlock.stream().anyMatch(selectField -> selectField.isSelectByField());
                                                switch (dim.getSelectType()) {
                                                    case MULTIITES: 
                                                    case SINGLE: {
                                                        selectDateList = new ArrayList();
                                                        List<QuerySelectItem> timeSelect = dim.getSelectItems();
                                                        Iterator iterator = timeSelect.iterator();
                                                        while (iterator.hasNext()) {
                                                            QuerySelectItem querySelectItem = (QuerySelectItem)iterator.next();
                                                            selectDateList.add(querySelectItem.getTitle());
                                                        }
                                                        break block93;
                                                    }
                                                    default: {
                                                        selectDateList = new ArrayList();
                                                        PeriodType ptype3 = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                                        String string = dim.getSelectItems().get(0).getTitle();
                                                        String endTime = dim.getSelectItems().get(1).getTitle();
                                                        groupQuery.setQueryPeriod(string, endTime, ptype3);
                                                        selectDateList = existSelectByField ? this.getAllTimeFromToEnd(string, endTime) : this.getTimeFromToEnd(string, endTime, ptype3);
                                                    }
                                                }
                                                break block93;
                                            }
                                            List<Object> conditionDateList = new ArrayList<String>();
                                            switch (dim.getSelectType()) {
                                                case MULTIITES: 
                                                case SINGLE: {
                                                    List<QuerySelectItem> timeSelect = dim.getSelectItems();
                                                    for (QuerySelectItem seItem : timeSelect) {
                                                        if (!selectDateList.contains(seItem.getTitle())) continue;
                                                        conditionDateList.add(seItem.getTitle());
                                                    }
                                                    selectDateList = conditionDateList;
                                                    break;
                                                }
                                                case RANGE: 
                                                case NONE: {
                                                    ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                                    String startTime = dim.getSelectItems().get(0).getTitle();
                                                    String string = dim.getSelectItems().get(1).getTitle();
                                                    conditionDateList = this.getTimeFromToEnd(startTime, string, ptype);
                                                    ArrayList<String> dateList = new ArrayList<String>();
                                                    for (String string2 : conditionDateList) {
                                                        if (!selectDateList.contains(string2)) continue;
                                                        dateList.add(string2);
                                                    }
                                                    selectDateList = dateList;
                                                    break;
                                                }
                                            }
                                        }
                                        if (selectDateList == null) continue;
                                        dimV.setValue(dwDimName, selectDateList);
                                        continue;
                                    }
                                    List<String> dateList = null;
                                    Iterator<QuerySelectItem> startTime = block.getTaskDefStartPeriod();
                                    String endTime = block.getTaskDefEndPeriod();
                                    if (StringUtil.isNullOrEmpty((String)((Object)startTime)) || StringUtil.isNullOrEmpty((String)endTime)) {
                                        ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                        dateList = this.getDefaultPeriodsData(ptype, null);
                                    } else {
                                        PeriodWrapper pw = new PeriodWrapper((String)((Object)startTime));
                                        int type = pw.getType();
                                        PeriodType periodType = PeriodType.fromType((int)type);
                                        dateList = this.getTimeFromToEnd((String)((Object)startTime), endTime, periodType);
                                    }
                                    if (dateList.size() <= 0 || dimV.hasValue(dwDimName)) continue;
                                    dimV.setValue(dwDimName, dateList);
                                    continue;
                                }
                                if (dim.getSelectItems() == null || dim.getSelectItems().size() <= 0) continue;
                                define = dims.stream().filter(dimension -> dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimension.getViewId().equals(dim.getViewId())).findFirst();
                                if ((selectNewTree == null || dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) && (selectNewTree == null || define.isPresent())) break block95;
                                switch (DimensionItemScop.getType(dim.getItemScop())) {
                                    case 2: {
                                        String currNodeCode;
                                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                            currNodeCode = selectItem.getCode();
                                            entitys.add(currNodeCode);
                                            QueryEntityData currData = this.findSelf(currNodeCode, selectNewTree);
                                            for (QueryEntityData selectData : currData.getChilds()) {
                                                entitys.add(selectData.getId());
                                            }
                                        }
                                        break block96;
                                    }
                                    case 3: {
                                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                            List<QueryEntityData> samelevelNodes = this.findSamelevelNode(selectItem.getCode(), selectNewTree);
                                            for (QueryEntityData queryEntityData : samelevelNodes) {
                                                entitys.add(queryEntityData.getId());
                                            }
                                        }
                                        break block96;
                                    }
                                    case 4: {
                                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                            entitys.add(selectItem.getCode());
                                        }
                                        break block96;
                                    }
                                    default: {
                                        String currNodeCode;
                                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                            currNodeCode = selectItem.getCode();
                                            QueryEntityData currData = this.findSelf(currNodeCode, selectNewTree);
                                            this.findChilds(currData, entitys);
                                        }
                                        break block96;
                                    }
                                }
                            }
                            if (selectNewTree != null) continue;
                            EntityViewDefine view = this.entityViewRunTimeController.buildEntityView(dim.getViewId());
                            DimensionValueSet valueSet = new DimensionValueSet();
                            IEntityTable rs = this.queryEntityUtil.getEntityTable(view, valueSet, null, null, true, null);
                            switch (DimensionItemScop.getType(dim.getItemScop())) {
                                case 2: {
                                    for (QuerySelectItem querySelectItem : dim.getSelectItems()) {
                                        entitys.add(querySelectItem.getCode());
                                        List rows = rs.getChildRows(querySelectItem.getCode().toString());
                                        for (IEntityRow iEntityRow : rows) {
                                            entitys.add(iEntityRow.getEntityKeyData());
                                        }
                                    }
                                    break;
                                }
                                case 3: {
                                    LinkedHashMap<String, String> parentKeys = new LinkedHashMap<String, String>();
                                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                        entitys.add(selectItem.getCode());
                                        IEntityRow row = rs.findByEntityKey(selectItem.getCode().toString());
                                        if (row == null) continue;
                                        String string = row.getParentEntityKey();
                                        List rows = rs.getChildRows(string);
                                        if (StringUtil.isNullOrEmpty((String)string)) {
                                            rows = rs.getRootRows();
                                        }
                                        if (parentKeys.containsKey(string)) continue;
                                        for (IEntityRow prow : rows) {
                                            entitys.add(prow.getEntityKeyData());
                                        }
                                        parentKeys.put(string, string);
                                    }
                                    break;
                                }
                                case 4: {
                                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                        entitys.add(selectItem.getCode());
                                    }
                                    break;
                                }
                                default: {
                                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                        entitys.add(selectItem.getCode());
                                        List rows = rs.getAllChildRows(selectItem.getCode().toString());
                                        for (IEntityRow row : rows) {
                                            entitys.add(row.getEntityKeyData());
                                        }
                                    }
                                }
                            }
                        }
                        if (entitys.size() > 0) {
                            dimV.setValue(dwDimName, entitys);
                            continue;
                        }
                        entitys.add("000000000000000000000000000000000000");
                        continue;
                    }
                    if (dim.getDimensionType() != QueryDimensionType.QDT_GRIDFIELD_ROW && dim.getDimensionType() != QueryDimensionType.QDT_GRIDFIELD_COL) continue;
                    String fieldId = dim.getViewId();
                    FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldId);
                    if (field != null) {
                        groupQuery.addGroupColumn(field);
                    }
                    if (dim.getTitle() == null && field != null) {
                        dim.setTitle(field.getTitle());
                    }
                    if ((fields = (LinkedHashMap<String, FieldDefine>)dimFiends.get((Object)dim.getDimensionType())) == null) {
                        fields = new LinkedHashMap<String, FieldDefine>();
                        dimFiends.put(dim.getDimensionType(), fields);
                    }
                    fields.put(field.getKey(), field);
                    this.queryCache.setDimFields(dim.getDimensionType(), fields);
                }
            }
            groupQuery.setWantDetail(block.isShowDetail() || isDetail);
            if (!StringUtil.isNullOrEmpty((String)fieldCondition)) {
                blockCondition = StringUtil.isNullOrEmpty(blockCondition) ? fieldCondition : String.format("{1} OR {2}", blockCondition, fieldCondition);
            }
            if (!StringUtil.isNullOrEmpty(blockCondition)) {
                groupQuery.setRowFilter(blockCondition);
            }
            if (!StringUtil.isNullOrEmpty((String)valueConditions)) {
                groupQuery.setRowFilter(valueConditions);
            }
            groupQuery.setMasterKeys(dimV);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            groupQuery.setSortGroupingAndDetailRows(true);
            boolean bl = flag = isDetail && this.queryCache.getExport() == false;
            if (flag) {
                int curPage;
                QueryGridPage tempPageData = block.getPageData();
                QueryGridPage pageData = new QueryGridPage();
                int n = curPage = tempPageData.getCurPageNum() - 1 == 0 ? 1 : tempPageData.getCurPageNum();
                if (tempPageData.getIsPageLoad() || tempPageData.getCurPageNum() == 1) {
                    pageData = tempPageData;
                    pageData.setCurPageNum(curPage);
                    pageData.setPreDimValueSet(tempPageData.getDimValueSet());
                    pageData.setPreItemIndex(tempPageData.getNextItemIndex());
                    pageData.setPreRowIndex(tempPageData.getNextRowIndex());
                    pageData.setPrePageNum(tempPageData.getCurPageNum());
                } else {
                    pageData.setCurPageNum(curPage);
                    pageData.setNextItemIndex(tempPageData.getPreItemIndex());
                    pageData.setNextRowIndex(tempPageData.getPreRowIndex());
                    pageData.setDimValueSet(tempPageData.getPreDimValueSet());
                }
                groupQuery.setPagingInfo(block.getPageData().getPageSize(), curPage - 1);
            } else {
                groupQuery.setSummarizingMethod(SummarizingMethod.RollUp);
            }
            IGroupingTable groupTable = groupQuery.executeReader(executorContext);
            int totalCount = groupTable.getTotalCount();
            if (flag) {
                int totalPage = (int)Math.ceil((double)totalCount / (double)block.getPageData().getPageSize());
                if (totalPage == 0) {
                    totalPage = 1;
                }
                totalCount = groupTable.getCount();
                block.getPageData().setTotalPage(totalPage);
            } else {
                block.getPageData().setTotalPage(1);
            }
            if (groupTable != null) {
                for (int rowIndex = 0; rowIndex < totalCount; ++rowIndex) {
                    IDataRow row = groupTable.getItem(rowIndex);
                    if (row.getGroupingFlag() < 0) continue;
                    for (QueryDimensionType type : dimFiends.keySet()) {
                        Map<String, List<String>> map = this.queryCache.getDimFieldValues(type);
                        Collection fields = ((Map)dimFiends.get((Object)type)).values();
                        if (fields.isEmpty()) continue;
                        for (FieldDefine field : fields) {
                            AbstractData value;
                            String string;
                            String key = field.getKey().toString();
                            List<String> values = map.get(key);
                            if (values == null) {
                                values = new ArrayList<String>();
                                map.put(key, values);
                            }
                            if (values.contains(string = this.getValue(field, (value = row.getValue(field)).getAsString()))) continue;
                            values.add(string);
                        }
                        this.queryCache.setDimFieldValues(type, map);
                    }
                }
            }
            return groupTable;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f02\u5e38", e);
            return null;
        }
    }

    public List<QueryEntityData> findSamelevelNode(String currNodeCode, List<QueryEntityData> selectNewTree) {
        for (QueryEntityData data : selectNewTree) {
            List<QueryEntityData> obj = null;
            if (data.getId().equals(currNodeCode)) {
                return selectNewTree;
            }
            if (data.getChilds() == null || data.getChilds().isEmpty() || (obj = this.findSamelevelNode(currNodeCode, data.getChilds())) == null) continue;
            return obj;
        }
        return null;
    }

    public void findLeafChilds(QueryEntityData currNodeData, List<String> entitys) {
        if (currNodeData == null) {
            return;
        }
        if (currNodeData.getChilds() != null && !currNodeData.getChilds().isEmpty()) {
            for (QueryEntityData data : currNodeData.getChilds()) {
                this.findLeafChilds(data, entitys);
            }
        } else {
            entitys.add(currNodeData.getId());
        }
    }

    public void findChilds(QueryEntityData currNodeData, List<String> entitys) {
        if (currNodeData == null) {
            return;
        }
        entitys.add(currNodeData.getId());
        if (currNodeData.getChilds() != null && !currNodeData.getChilds().isEmpty()) {
            for (QueryEntityData data : currNodeData.getChilds()) {
                this.findChilds(data, entitys);
            }
        }
    }

    public QueryEntityData findSelf(String currNodeCode, List<QueryEntityData> selectNewTree) {
        for (QueryEntityData data : selectNewTree) {
            if (data.getId().equals(currNodeCode)) {
                return data;
            }
            if (data.getChilds() == null || data.getChilds().isEmpty() || (data = this.findSelf(currNodeCode, data.getChilds())) == null) continue;
            return data;
        }
        return null;
    }

    private List<QuerySelectField> getSelectFieldsInBlock(QueryBlockDefine block) {
        Optional<QueryDimensionDefine> fieldDim;
        List<QuerySelectField> selectedFields = new ArrayList<QuerySelectField>();
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            selectedFields = fd.getSelectFields();
        }
        return selectedFields;
    }

    private int setSelectFieldsInBlock(QueryBlockDefine block, List<QuerySelectField> selectedFields) {
        Optional<QueryDimensionDefine> fieldDim;
        int masterCount = 0;
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            List<QuerySelectField> selectFieldTemp = fd.getSelectFields();
            if (selectFieldTemp == null || selectFieldTemp.size() == 0) {
                fd.setSelectFields(selectedFields);
            } else {
                block0: for (QuerySelectField field : selectFieldTemp) {
                    if (Boolean.parseBoolean(field.getIsMaster()) && !field.isHidden()) {
                        ++masterCount;
                    }
                    for (int j = 0; j < selectedFields.size(); ++j) {
                        if (selectedFields.get(j).getCode() != field.getCode()) continue;
                        field = selectedFields.get(j);
                        continue block0;
                    }
                }
            }
        }
        return masterCount;
    }

    private void initRow(QueryBlockDefine block, IFieldsInfo fields, IDataRow row, Grid2Data gridData, int rowIndex, DimensionValueSet curDim) {
        try {
            int totalColumnCount = gridData.getColumnCount();
            int colStart = 1;
            Map<String, Integer> cache = this.queryCache.getCacheNumbers();
            if (cache.containsKey("EntityColumnNumber")) {
                colStart = cache.get("EntityColumnNumber") - 1;
            }
            if (row == null) {
                for (int i = colStart; i < totalColumnCount - 1; ++i) {
                    GridCellData cell = gridData.getGridCellData(i + 1, rowIndex);
                    cell.setShowText(" ");
                    cell.setEditText(null);
                }
                return;
            }
            Map<String, DimensionInfor> allDimension = this.queryCache.getAllDimension();
            LinkedHashMap<String, String> allFieldDimensions = new LinkedHashMap<String, String>();
            List<QuerySelectField> selectedFields = this.getSelectFieldsInBlock(block);
            LinkedHashMap<String, QuerySelectField> allItems = new LinkedHashMap<String, QuerySelectField>();
            FieldDefine[] fieldDefines = new FieldDefine[selectedFields.size()];
            int j = 0;
            for (QuerySelectField selectItem : selectedFields) {
                String fieldCode = selectItem.getCode();
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
                if (field == null || selectItem.isHidden()) continue;
                fieldDefines[j] = field;
                if (!StringUtils.isEmpty((String)selectItem.getIsMaster())) {
                    allFieldDimensions.put(fieldCode, this.dataAssist.getDimensionName(field));
                }
                allItems.put(fieldCode, selectItem);
                ++j;
            }
            QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
            int rowHeight = gridExtension.getRowHeight();
            Map<String, Map<String, String>> allEnumValues = this.queryCache.getAllEnumValues();
            gridData.setRowHeight(rowIndex, rowHeight);
            for (int i = colStart; i < totalColumnCount - 1; ++i) {
                Object code;
                boolean isNullSum = false;
                FieldDefine curent = fieldDefines[i - colStart];
                QuerySelectField item = null;
                String fieldCode = curent.getKey();
                LinkedHashMap<String, String> itemValue = new LinkedHashMap();
                if (!allItems.containsKey(fieldCode)) {
                    itemValue = new LinkedHashMap();
                } else {
                    item = (QuerySelectField)allItems.get(fieldCode);
                    QueryItemSortDefine sortInfor = null;
                    sortInfor = item.getSort();
                    if (sortInfor != null) {
                        if (sortInfor.getData() == null) {
                            itemValue = new LinkedHashMap();
                            sortInfor.setData(itemValue);
                        } else {
                            itemValue = sortInfor.getData();
                        }
                    } else {
                        sortInfor = new QueryItemSortDefine();
                        itemValue = new LinkedHashMap();
                        sortInfor.setData(itemValue);
                        item.setSort(sortInfor);
                    }
                }
                GridCellData cell = gridData.getGridCellData(i + 1, rowIndex);
                AbstractData value = row.getValue(curent);
                cell.setPersistenceData("type", String.valueOf(QueryHelper.convertToHtmlDataType(curent.getType())));
                this.setDataTypeByFieldType(cell, curent);
                if (item != null && Boolean.parseBoolean(item.getIsMaster())) {
                    String dimName = (String)allFieldDimensions.get(fieldCode);
                    if (StringUtil.isNullOrEmpty((String)dimName)) {
                        cell.setShowText(" ");
                        cell.setEditText(null);
                        cell.setHorzAlign(3);
                        cell.setFitFontSize(false);
                        continue;
                    }
                    if (!curDim.hasValue(dimName)) {
                        cell.setShowText(" ");
                        cell.setEditText(null);
                        cell.setHorzAlign(3);
                        cell.setFitFontSize(false);
                        continue;
                    }
                    String PERIOD_DIM = "DATATIME";
                    if (!dimName.equals(PERIOD_DIM)) {
                        DimensionInfor dminfor = allDimension.get(dimName);
                        if (value.isNull) {
                            code = row.getRowKeys().getValue(dimName);
                            if (code != null) {
                                IEntityRow data = dminfor.getDimensionValue().get(code.toString());
                                if (data == null) {
                                    isNullSum = true;
                                    cell.setShowText(" ");
                                    cell.setEditText(null);
                                } else {
                                    cell.setShowText(data.getTitle());
                                    cell.setEditText(data.getTitle());
                                }
                            }
                        } else if (value.getAsString() == "\u2014\u2014") {
                            cell.setShowText(" ");
                            cell.setEditText(null);
                            isNullSum = true;
                        } else {
                            IEntityRow data = dminfor.getDimensionValue().get(value.getAsString());
                            if (data != null) {
                                String valStr = data.getTitle();
                                String code2 = data.getEntityKeyData();
                                if (!itemValue.containsKey(code2) && valStr != "\u2014\u2014") {
                                    itemValue.put(code2, valStr);
                                }
                                cell.setShowText(valStr);
                                cell.setEditText(valStr);
                            } else {
                                cell.setShowText("\u672a\u77e5");
                                cell.setEditText("\u672a\u77e5");
                            }
                        }
                    } else {
                        PeriodWrapper pw;
                        String titleString = "";
                        if (value.isNull) {
                            code = row.getRowKeys().getValue(dimName);
                            if (code != null) {
                                pw = PeriodUtil.getPeriodWrapper((String)code.toString());
                                titleString = pw.toTitleString();
                            }
                        } else if (value.getAsString() == "\u2014\u2014") {
                            cell.setShowText(" ");
                            cell.setShowText(null);
                            isNullSum = true;
                        } else {
                            String valueStr = value.getAsString();
                            pw = PeriodUtil.getPeriodWrapper((String)valueStr);
                            titleString = pw.toTitleString();
                            if (!itemValue.containsKey(valueStr) && titleString != "\u2014\u2014") {
                                itemValue.put(valueStr, titleString);
                            }
                        }
                        cell.setEditText(titleString);
                        cell.setShowText(titleString);
                    }
                } else {
                    try {
                        if (!value.isNull) {
                            String key = curent.getKey().toString();
                            String val = value.getAsString();
                            if (allEnumValues.containsKey(key)) {
                                String data = allEnumValues.get(key).get(val);
                                code = key;
                                if (!itemValue.containsKey(code) && data != "\u2014\u2014") {
                                    itemValue.put((String)code, val);
                                }
                                cell.setShowText(data);
                                cell.setEditText(data);
                            } else {
                                String valStr = value.getAsString();
                                if (!itemValue.containsKey(valStr) && valStr != "\u2014\u2014") {
                                    itemValue.put(valStr, valStr);
                                    valStr = this.getValue(curent, valStr);
                                    cell.setShowText(valStr);
                                    cell.setEditText(this.getAvailableText(value, curent.getType()));
                                }
                                if (curent.getType() == FieldType.FIELD_TYPE_LOGIC) {
                                    cell.setCheckable(value.getAsBool());
                                } else {
                                    cell.setShowText(valStr);
                                    cell.setEditText(this.getAvailableText(value, curent.getType()));
                                }
                            }
                        } else {
                            cell.setShowText("");
                            cell.setEditText("");
                        }
                    }
                    catch (Exception e) {
                        value = null;
                        Log.error((Exception)e);
                        logger.error(e.getMessage());
                    }
                }
                if (this.isNumField(curent)) {
                    cell.setHorzAlign(2);
                    cell.setFitFontSize(true);
                } else if (isNullSum) {
                    cell.setHorzAlign(3);
                    cell.setFitFontSize(false);
                } else {
                    cell.setHorzAlign(1);
                    cell.setFitFontSize(false);
                }
                cell.setForeGroundColor(0x626262);
                cell.setFontSize(13);
                cell.setBackGroundColor(0xFFFFFF);
                cell.setSelectable(false);
                cell.setBottomBorderStyle(1);
                cell.setRightBorderStyle(1);
                gridData.setColumnAutoWidth(i, true);
            }
        }
        catch (Exception ex) {
            logger.error("initRow \u5f02\u5e38", ex);
        }
    }

    private String getAvailableText(AbstractData value, FieldType type) throws DataTypeException {
        String res = null;
        switch (type) {
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                res = value.getAsObject().toString();
                break;
            }
            case FIELD_TYPE_STRING: {
                res = value.getAsString();
                break;
            }
            case FIELD_TYPE_LOGIC: {
                res = value.getAsObject().toString();
                break;
            }
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_TIME: 
            case FIELD_TYPE_TIME_STAMP: {
                res = value.getAsString();
                break;
            }
            default: {
                res = value.getAsString();
            }
        }
        return res;
    }

    public List<String> getDefaultPeriodsDataFromBlock(String start, String end, PeriodType ptype, IPeriodProvider custable) {
        boolean startIsNull = StringUtils.isEmpty((String)start);
        boolean endIsNull = StringUtils.isEmpty((String)end);
        Calendar dataTime = Calendar.getInstance();
        int startYear = 0;
        int endYear = 0;
        int startMonth = 0;
        int endMonth = 0;
        List<String> dateList = null;
        if (!startIsNull && endIsNull) {
            startYear = Integer.parseInt(start.substring(0, 4));
            startMonth = Integer.parseInt(start.substring(start.length() - 2, start.length()));
            dateList = this.getPeriodsEndTimeIsNull(startYear, startMonth, ptype);
        }
        if (startIsNull && !endIsNull) {
            int curYear = dataTime.get(1);
            int curMonth = dataTime.get(2) + 1;
            endYear = Integer.parseInt(end.substring(0, 4));
            endMonth = Integer.parseInt(end.substring(start.length() - 2, start.length()));
            if (curYear <= endYear) {
                String startPeriod = curYear + end.substring(4, 5) + (curMonth >= 10 ? "00" + curMonth : "000" + curMonth);
                try {
                    dateList = this.getTimeFromToEnd(startPeriod, end, ptype);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u65f6\u671f\u5f02\u5e38", e);
                }
            } else {
                dateList = new ArrayList<String>();
                dateList.add(end);
            }
        }
        if (startIsNull && endIsNull) {
            dateList = this.getDefaultPeriodsData(ptype, custable);
        }
        return dateList;
    }

    private List<String> getPeriodsEndTimeIsNull(int year, int month, PeriodType ptype) {
        ArrayList<String> dateList = new ArrayList<String>();
        switch (ptype.type()) {
            case 0: 
            case 1: {
                for (int i = year; i <= year + 11; ++i) {
                    String code = i + "N0001";
                    dateList.add(code);
                }
                break;
            }
            case 2: {
                for (int j = year; j <= year + 5; ++j) {
                    for (int i = month; i <= 2; ++i) {
                        String code = j + "H000" + i;
                        dateList.add(code);
                    }
                }
                break;
            }
            case 3: {
                for (int i = year; i <= year + 2; ++i) {
                    for (int l = month; l <= 4; ++l) {
                        String code = i + "J000" + l;
                        dateList.add(code);
                    }
                }
                break;
            }
            case 4: {
                for (int l = month; l <= 12; ++l) {
                    String code = year + (l < 10 ? "Y000" : "Y00") + l;
                    dateList.add(code);
                }
                break;
            }
            case 5: {
                for (int l = month; l <= 36; ++l) {
                    String code = year + (l >= 10 ? "X00" : "X000") + l;
                    dateList.add(code);
                }
                break;
            }
            case 6: {
                for (int l = month; l <= 365; ++l) {
                    String code = year + (l < 10 ? "R000" : (l > 99 ? "R0" : "R00")) + l;
                    dateList.add(code);
                }
                break;
            }
            case 7: {
                break;
            }
            default: {
                String code;
                for (int i = year - 11; i <= year; ++i) {
                    code = i + "N0001";
                    dateList.add(code);
                    for (int l = 1; l <= 12; ++l) {
                        code = year + (l < 10 ? "Y000" : "Y00") + l;
                        dateList.add(code);
                    }
                }
                for (int l = 1; l <= 365; ++l) {
                    code = year + (l < 10 ? "R000" : (l > 99 ? "R0" : "R00")) + l;
                    dateList.add(code);
                }
            }
        }
        return dateList;
    }

    public List<String> getDefaultPeriodsData(PeriodType ptype, IPeriodProvider custable) {
        Calendar dataTime = Calendar.getInstance();
        ArrayList<String> dateList = new ArrayList<String>();
        int year = dataTime.get(1);
        switch (ptype.type()) {
            case 0: 
            case 1: {
                for (int i = year - 11; i <= year; ++i) {
                    String code = i + "N0001";
                    dateList.add(code);
                }
                break;
            }
            case 2: {
                for (int j = year; j <= year; ++j) {
                    for (int i = 1; i <= 2; ++i) {
                        String code = j + "H000" + i;
                        dateList.add(code);
                    }
                }
                break;
            }
            case 3: {
                for (int i = year - 2; i <= year; ++i) {
                    for (int l = 1; l <= 4; ++l) {
                        String code = i + "J000" + l;
                        dateList.add(code);
                    }
                }
                break;
            }
            case 4: {
                for (int l = 1; l <= 12; ++l) {
                    String code = year + (l < 10 ? "Y000" : "Y00") + l;
                    dateList.add(code);
                }
                break;
            }
            case 5: {
                for (int l = 1; l <= 36; ++l) {
                    String code = year + (l >= 10 ? "X00" : "X000") + l;
                    dateList.add(code);
                }
                break;
            }
            case 6: {
                for (int l = 1; l <= 365; ++l) {
                    String code = year + (l < 10 ? "R000" : (l > 99 ? "R0" : "R00")) + l;
                    dateList.add(code);
                }
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                if (custable == null) break;
                List periodItems = custable.getPeriodItems();
                for (int i = 0; i < periodItems.size(); ++i) {
                    IPeriodRow row = (IPeriodRow)periodItems.get(i);
                    String code = row.getCode();
                    dateList.add(code);
                }
                break;
            }
            default: {
                String code;
                for (int i = year - 11; i <= year; ++i) {
                    code = i + "N0001";
                    dateList.add(code);
                    for (int l = 1; l <= 12; ++l) {
                        code = year + (l < 10 ? "Y000" : "Y00") + l;
                        dateList.add(code);
                    }
                }
                for (int l = 1; l <= 365; ++l) {
                    code = year + (l < 10 ? "R000" : (l > 99 ? "R0" : "R00")) + l;
                    dateList.add(code);
                }
            }
        }
        return dateList;
    }

    public List<String> getAllTimeFromToEnd(String startTime, String endTime) throws Exception {
        int i;
        int dayStart;
        int dayEnd;
        int monthEnd;
        int monthStart;
        int yearEnd;
        int yearStart;
        ArrayList<String> result = new ArrayList<String>();
        GregorianCalendar start = PeriodUtil.period2Calendar((String)startTime);
        GregorianCalendar end = PeriodUtil.period2Calendar((String)endTime);
        Calendar ca = Calendar.getInstance();
        int sysYear = ca.get(1);
        int sysMonth = ca.get(2);
        int sysDay = ca.get(6);
        if (startTime == "" && endTime == "") {
            result.add(sysYear + "N0001");
        } else {
            for (int i2 = Integer.parseInt(startTime.substring(0, 4)); i2 <= Integer.parseInt(endTime.substring(0, 4)); ++i2) {
                result.add(i2 + "N0001");
            }
        }
        if (startTime == "" && endTime == "") {
            yearStart = sysYear;
            yearEnd = sysYear;
            monthStart = sysMonth + 1;
            monthEnd = sysMonth + 1;
            dayStart = dayEnd = sysDay;
        } else {
            GregorianCalendar dayc1 = new GregorianCalendar();
            Date startDate = start.getTime();
            dayc1.setTime(startDate);
            yearStart = dayc1.get(1);
            monthStart = dayc1.get(2) + 1;
            dayStart = dayc1.get(6);
            Date endDate = end.getTime();
            dayc1.setTime(endDate);
            yearEnd = dayc1.get(1);
            monthEnd = dayc1.get(2) + 1;
            dayEnd = dayc1.get(6);
        }
        for (i = yearStart; i <= yearEnd; ++i) {
            for (int j = 1; j <= 12; ++j) {
                if (yearStart == yearEnd) {
                    if (monthStart > j || j > monthEnd) continue;
                    result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                    continue;
                }
                if (yearStart == i) {
                    if (monthStart > j) continue;
                    result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                    continue;
                }
                if (i == yearEnd) {
                    if (j > monthEnd) continue;
                    result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                    continue;
                }
                result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
            }
        }
        for (i = yearStart; i <= yearEnd; ++i) {
            for (int j = 1; j <= 365; ++j) {
                if (yearStart == yearEnd) {
                    if (dayStart > j || j > dayEnd) continue;
                    result.add(i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j);
                    continue;
                }
                if (yearStart == i) {
                    if (dayStart > j) continue;
                    result.add(i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j);
                    continue;
                }
                if (i == yearEnd) {
                    if (j > dayEnd) continue;
                    result.add(i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j);
                    continue;
                }
                result.add(i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j);
            }
        }
        return result;
    }

    public List<String> getTimeFromToEnd(String startTime, String endTime, PeriodType periodType) throws Exception {
        if (startTime == null || endTime == null || "".equals(startTime) || "".equals(endTime)) {
            return null;
        }
        ArrayList<String> result = new ArrayList<String>();
        Calendar ca = Calendar.getInstance();
        int sysYear = ca.get(1);
        int sysMonth = ca.get(2);
        boolean flag = startTime.substring(0, 4).equals(endTime.substring(0, 4));
        switch (periodType.type()) {
            case 0: 
            case 1: {
                if (startTime == "" && endTime == "") {
                    result.add(sysYear + "N0001");
                    break;
                }
                for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); ++i) {
                    result.add(i + "N0001");
                }
                break;
            }
            case 2: {
                for (int j = Integer.parseInt(startTime.substring(0, 4)); j <= Integer.parseInt(endTime.substring(0, 4)); ++j) {
                    for (int i = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length())); i <= (flag ? Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length())) : 2); ++i) {
                        result.add(j + "H000" + i);
                    }
                }
                break;
            }
            case 3: {
                for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); ++i) {
                    for (int l = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length())); l <= (flag ? Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length())) : 4); ++l) {
                        result.add(i + "J000" + l);
                    }
                }
                break;
            }
            case 4: {
                int monthEnd;
                int monthStart;
                int yearEnd;
                int yearStart;
                if (startTime == "" && endTime == "") {
                    yearStart = sysYear;
                    yearEnd = sysYear;
                    monthStart = sysMonth + 1;
                    monthEnd = sysMonth + 1;
                } else {
                    yearStart = Integer.parseInt(startTime.substring(0, 4));
                    monthStart = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length()));
                    yearEnd = Integer.parseInt(endTime.substring(0, 4));
                    monthEnd = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
                }
                for (int i = yearStart; i <= yearEnd; ++i) {
                    for (int j = 1; j <= 12; ++j) {
                        if (yearStart == yearEnd) {
                            if (monthStart > j || j > monthEnd) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        if (yearStart == i) {
                            if (monthStart > j) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        if (i == yearEnd) {
                            if (j > monthEnd) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                    }
                }
                break;
            }
            case 5: {
                for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); ++i) {
                    for (int l = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length())); l <= (flag ? Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length())) : 36); ++l) {
                        result.add(i + (l >= 10 ? "X00" : "X000") + l);
                    }
                }
                break;
            }
            case 6: {
                for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); ++i) {
                    for (int l = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length())); l <= (flag ? Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length())) : 365); ++l) {
                        result.add(i + (l < 10 ? "R000" : (l > 99 ? "R0" : "R00")) + l);
                    }
                }
                break;
            }
        }
        return result;
    }

    @Deprecated
    private List<IEntityRow> queryAllRows(EntityViewDefine view) {
        List rootRows = null;
        try {
            DimensionValueSet valueSet = new DimensionValueSet();
            IEntityTable rs = this.queryEntityUtil.getEntityTable(view, valueSet, null, null, true, null);
            rootRows = rs.getAllRows();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return rootRows;
    }

    public Grid2Data handleTransposeForm(QueryBlockDefine block, Grid2Data grid2Data) throws Exception {
        if (grid2Data.getHeaderRowCount() > 1) {
            grid2Data = QueryHelper.transposeGrid(block, grid2Data);
        }
        new Grid2Data();
        return grid2Data;
    }

    private void setDataTypeByFieldType(GridCellData cell, FieldDefine field) {
        if (field == null) {
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
            cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
            return;
        }
        int fieldType = field.getType().getValue();
        String digitStr = "";
        for (int i = 0; i < field.getFractionDigits(); ++i) {
            if (i == 0) {
                digitStr = digitStr + ".";
            }
            digitStr = digitStr + "0";
        }
        switch (fieldType) {
            case 1: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 2: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
                break;
            }
            case 3: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 4: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean)));
                cell.setPersistenceData("formatter", "\u662f/\u5426");
                break;
            }
            case 5: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("yyyy-mm-dd");
                cell.setPersistenceData("formatter", "yyyy-mm-dd");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            case 6: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("yyyy/m/d h:mm");
                cell.setPersistenceData("formatter", "yyyy/m/d h:mm");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            case 7: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
                break;
            }
            case 8: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 19: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("h:mm");
                cell.setPersistenceData("formatter", "h:mm");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            default: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
            }
        }
    }

    private boolean isNumField(FieldDefine field) {
        switch (field.getType()) {
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                return true;
            }
        }
        return false;
    }

    private String getDigits(int size) {
        String digitStr = "";
        for (int i = 0; i < size; ++i) {
            digitStr = digitStr + "0";
        }
        if (!StringUtil.isNullOrEmpty((String)digitStr)) {
            return "." + digitStr;
        }
        return digitStr;
    }

    private String getValue(FieldDefine curent, String valStr) {
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + this.getDigits(curent.getFractionDigits()));
        switch (curent.getType()) {
            case FIELD_TYPE_DECIMAL: {
                BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                BigDecimal deciValue = decimalValue.setScale((int)curent.getFractionDigits(), 4);
                return decimalFormat.format(deciValue);
            }
            case FIELD_TYPE_FLOAT: {
                double floatValue = Convert.toDouble((String)valStr);
                Double dbVal = Round.callFunction((Number)floatValue, (int)curent.getFractionDigits());
                return decimalFormat.format(dbVal);
            }
            case FIELD_TYPE_INTEGER: {
                Integer iVal = Convert.toInt((String)valStr);
                return decimalFormat.format(iVal);
            }
        }
        return valStr;
    }

    protected Object convertDataValue(FieldDefine fieldDefine, Object dataValue) {
        if (dataValue == null) {
            return null;
        }
        Object resultValue = dataValue;
        try {
            if (dataValue instanceof AbstractData) {
                resultValue = ((AbstractData)dataValue).getAsObject();
            }
            switch (fieldDefine.getType()) {
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal decimalValue = Convert.toBigDecimal((Object)dataValue);
                    resultValue = decimalValue.setScale((int)fieldDefine.getFractionDigits(), 4);
                    break;
                }
                case FIELD_TYPE_FLOAT: {
                    double floatValue = Convert.toDouble((Object)dataValue);
                    resultValue = Round.callFunction((Number)floatValue, (int)fieldDefine.getFractionDigits());
                    break;
                }
                case FIELD_TYPE_INTEGER: {
                    resultValue = Convert.toInt((Object)dataValue);
                    break;
                }
                case FIELD_TYPE_STRING: {
                    if (dataValue == null) break;
                    String formatValue = dataValue.toString();
                    if (formatValue.length() > fieldDefine.getSize()) {
                        formatValue = formatValue.substring(0, fieldDefine.getSize());
                    }
                    resultValue = formatValue;
                    break;
                }
                case FIELD_TYPE_UUID: {
                    if (dataValue == null) break;
                    if (dataValue instanceof UUID) {
                        resultValue = dataValue;
                        break;
                    }
                    if (dataValue instanceof String) {
                        resultValue = UUID.fromString((String)dataValue);
                        break;
                    }
                    if (dataValue instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)dataValue)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)dataValue);
                    break;
                }
                case FIELD_TYPE_LOGIC: {
                    resultValue = Convert.toBoolean((Object)dataValue);
                    break;
                }
            }
        }
        catch (Exception e) {
            resultValue = dataValue;
        }
        return resultValue;
    }

    private List<DataLinkDefine> getLinksInRegionByField(String regionKey, String fieldKey) {
        ArrayList<DataLinkDefine> dataLinks = new ArrayList<DataLinkDefine>();
        List allLinksInRegion = this.viewController.getAllLinksInRegion(regionKey);
        if (allLinksInRegion != null) {
            for (DataLinkDefine linkDefine : allLinksInRegion) {
                if (StringUtils.isEmpty((String)linkDefine.getLinkExpression()) || !linkDefine.getLinkExpression().equals(fieldKey.toString())) continue;
                dataLinks.add(linkDefine);
            }
        }
        return dataLinks;
    }

    List<QueryModalTreeNode> getDimensionBySelectFields(List<QuerySelectField> selectFields) throws Exception {
        ArrayList<QueryModalTreeNode> result = new ArrayList<QueryModalTreeNode>();
        ArrayList<FieldDefine> fieldDefineList = new ArrayList<FieldDefine>();
        HashMap<String, List<DataLinkDefine>> dataLinksMap = new HashMap<String, List<DataLinkDefine>>();
        for (QuerySelectField sfield : selectFields) {
            FieldDefine field;
            if (sfield.isHidden() || (field = this.dataDefinitionRuntimeController.queryFieldDefine(sfield.getCode())) == null) continue;
            fieldDefineList.add(field);
            List<DataLinkDefine> links = this.getLinksInRegionByField(sfield.getRegionKey(), field.getKey());
            if (links.size() <= 0) continue;
            if (!dataLinksMap.containsKey(field.getKey())) {
                dataLinksMap.put(field.getKey(), links);
                continue;
            }
            ((List)dataLinksMap.get(field.getKey())).addAll(links);
        }
        for (FieldDefine define : fieldDefineList) {
            QueryModalTreeNode node = new QueryModalTreeNode();
            String code = define.getKey();
            node.setCode(code.toString());
            node.setId(code);
            node.setTitle(define.getTitle());
            List dataLinks = (List)dataLinksMap.get(code);
            if (dataLinks != null && dataLinks.size() > 0 && define.getEntityKey() != null) {
                node.setViewid(define.getEntityKey());
            }
            result.add(node);
        }
        return result;
    }

    @Deprecated
    private Map<String, Map<String, String>> getEnumValuesByQuerySelectFields(List<QuerySelectField> allFields) throws Exception {
        LinkedHashMap<String, Map<String, String>> allEnumValues = new LinkedHashMap<String, Map<String, String>>();
        if (allFields.size() > 0) {
            List<QueryModalTreeNode> nodes = this.getDimensionBySelectFields(allFields);
            for (QueryModalTreeNode node : nodes) {
                if (node.getViewid() == null || allEnumValues.containsKey(node.getViewid().toString())) continue;
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(node.getViewid());
                List<IEntityRow> allRows = this.queryAllRows(entityView);
                HashMap<String, String> entityMap = new HashMap<String, String>();
                for (IEntityRow row : allRows) {
                    entityMap.put(row.getEntityKeyData(), row.getTitle());
                }
                allEnumValues.put(node.getCode(), entityMap);
            }
        }
        return allEnumValues;
    }
}

