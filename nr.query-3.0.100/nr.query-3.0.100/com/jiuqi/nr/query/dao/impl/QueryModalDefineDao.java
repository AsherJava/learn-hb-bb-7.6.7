/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 */
package com.jiuqi.nr.query.dao.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.block.BlockInfo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.define.QueryBlockDao;
import com.jiuqi.nr.query.dao.define.QueryModalDao;
import com.jiuqi.nr.query.dao.impl.FloatOrderGenerator;
import com.jiuqi.nr.query.dao.impl.QueryBlockDefineDao;
import com.jiuqi.nr.query.deserializer.BlockInfoDeserializer;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.serializer.BlockInfoSerializer;
import com.jiuqi.nr.query.service.impl.DataQueryHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Component
public class QueryModalDefineDao
implements IQueryModalDefineDao {
    @Autowired
    private QueryModalDao dao;
    @Autowired
    QueryBlockDefineDao blockDefineDao;
    @Autowired
    private QueryBlockDao blockDao;
    @Autowired
    private QueryModelAuthorityProvider authority;
    private static final Logger logger = LoggerFactory.getLogger(QueryModalDefineDao.class);

    @Override
    public Boolean insertQueryModalDefine(QueryModalDefine queryModalDefine) {
        Assert.notNull((Object)queryModalDefine, "'QueryModalDefine' must not be null");
        try {
            String modelId = queryModalDefine.getId();
            QueryModalDefine modal = this.dao.queryDefineById(modelId);
            if (modal != null) {
                queryModalDefine.setModelExtension();
                queryModalDefine.setConditions();
                this.updateQueryModalDefine(queryModalDefine);
            } else {
                queryModalDefine.setModelExtension();
                queryModalDefine.setConditions();
                FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
                queryModalDefine.setOrder(floatOrderGenerator.next());
                this.dao.insertDefine(queryModalDefine);
                List<QueryBlockDefine> Blocks = queryModalDefine.getBlocks();
                if (Blocks != null && Blocks.size() > 0) {
                    for (QueryBlockDefine block : Blocks) {
                        DataQueryHelper gridHelper = new DataQueryHelper();
                        QueryBlockDefine blockDefine = null;
                        boolean hasUserFormTemp = block.getHasUserForm();
                        if (!hasUserFormTemp) {
                            block.setHasUserForm(false);
                        }
                        blockDefine = gridHelper.getQueryFormGrid(block, null, null);
                        blockDefine.setHasUserForm(hasUserFormTemp);
                        blockDefine.setModelID(modelId);
                        this.clearSortData(blockDefine);
                        if (QueryBlockType.QBT_CHART.equals((Object)blockDefine.getBlockInfo().getBlockType())) {
                            this.blockDao.updateDefine(blockDefine);
                            continue;
                        }
                        this.blockDao.insertDefine(blockDefine);
                    }
                }
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private boolean clearSortData(QueryBlockDefine block) {
        List<Object> selectedFields = new ArrayList();
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null) {
            Optional<QueryDimensionDefine> fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst();
            if (fieldDim.isPresent()) {
                QueryDimensionDefine fd = fieldDim.get();
                selectedFields = fd.getSelectFields();
                for (QuerySelectField querySelectField : selectedFields) {
                    querySelectField.setSort(new QueryItemSortDefine());
                }
            }
            for (QueryDimensionDefine dim : dims) {
                if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
                ArrayList<QuerySelectItem> arrayList = new ArrayList<QuerySelectItem>();
                dim.setSelectItems(arrayList);
            }
        }
        return true;
    }

    @Override
    public String updateQueryModalDefine(QueryModalDefine queryModelDefine) {
        Assert.notNull((Object)queryModelDefine, "'queryModelDefine'\u4e0d\u5141\u8bb8\u4e3aNULL");
        try {
            String modelId = queryModelDefine.getId();
            queryModelDefine.setModelExtension();
            queryModelDefine.setConditions();
            int queryModelResult = this.dao.updateDefine(queryModelDefine);
            if (queryModelResult > 0) {
                List<QueryBlockDefine> Blocks;
                List<QueryBlockDefine> blocks = this.blockDao.queryDefineByModelId(modelId);
                if (null != blocks && blocks.size() != 0) {
                    this.blockDao.deleteBydefinelId(queryModelDefine.getId());
                }
                if ((Blocks = queryModelDefine.getBlocks()) != null && Blocks.size() > 0) {
                    for (QueryBlockDefine block : Blocks) {
                        DataQueryHelper gridHelper = new DataQueryHelper();
                        QueryBlockDefine blockDefine = null;
                        boolean hasUserFormTemp = block.getHasUserForm();
                        if (!hasUserFormTemp) {
                            block.setHasUserForm(false);
                        }
                        blockDefine = gridHelper.getQueryFormGrid(block, null, null);
                        blockDefine.setModelID(modelId);
                        blockDefine.setHasUserForm(hasUserFormTemp);
                        this.clearSortData(blockDefine);
                        this.blockDao.insertDefine(blockDefine);
                    }
                }
                return "true";
            }
            return "false_permission";
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6a21\u677f\u9519\u8bef" + e.getMessage(), e);
            return "false_system";
        }
    }

    @Override
    public Boolean updateQueryModalNoOptBlock(QueryModalDefine model) {
        try {
            int queryModelResult = this.dao.updateDefine(model);
            if (queryModelResult > 0) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public String deleteQueryModalDefineById(String queryModalDefineId) {
        try {
            this.dao.deleteByModelId(queryModalDefineId);
            List<QueryBlockDefine> blocks = this.blockDao.queryDefineByModelId(queryModalDefineId);
            if (null != blocks && blocks.size() != 0) {
                this.blockDao.deleteBydefinelId(queryModalDefineId);
            }
            return "true";
        }
        catch (NullPointerException e) {
            return "false_permission";
        }
        catch (Exception e) {
            return "false_system";
        }
    }

    @Override
    public QueryModalDefine getQueryModalDefineById(String queryModalDefineId) {
        try {
            return this.dao.queryDefineById(queryModalDefineId);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public QueryModalDefine queryModalDefineById(String modalId) {
        try {
            QueryModalDefine queryModalDefine = this.dao.queryDefineById(modalId);
            if (queryModalDefine == null) {
                return null;
            }
            queryModalDefine.JsonToModelExtension(queryModalDefine.getModelExtension());
            if (queryModalDefine.getConditions() != null) {
                queryModalDefine.jsonTomodelCondition(queryModalDefine.getConditions());
            }
            List<Object> blocks = new ArrayList();
            ArrayList<QueryBlockDefine> gridDateBlocks = new ArrayList<QueryBlockDefine>();
            blocks = this.blockDefineDao.GetQueryBlockDefinesByModelId(modalId);
            for (QueryBlockDefine queryBlockDefine : blocks) {
                queryBlockDefine.setBlockInfoStr(new String(queryBlockDefine.getBlockInfoBlob()));
                SimpleModule module = new SimpleModule();
                module.addDeserializer(BlockInfo.class, (JsonDeserializer)new BlockInfoDeserializer());
                module.addSerializer(BlockInfo.class, (JsonSerializer)new BlockInfoSerializer());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule((Module)module);
                BlockInfo blockinfo = (BlockInfo)objectMapper.readValue(queryBlockDefine.getBlockInfoBlob(), BlockInfo.class);
                queryBlockDefine.setBlockInfoStr(objectMapper.writeValueAsString((Object)blockinfo));
                DataQueryHelper queryHelper = new DataQueryHelper();
                queryBlockDefine.setQueryType(queryModalDefine.getQueryType());
                gridDateBlocks.add(queryBlockDefine);
            }
            queryModalDefine.setBlocks(gridDateBlocks);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f", (String)("\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f\u6210\u529f\uff0c\u6a21\u677fid:" + modalId));
            return queryModalDefine;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getAllQueryModalDefines() {
        try {
            return this.dao.list();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getAllQueryTasks() {
        try {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getModalsByGroupId(String groupId) {
        try {
            return this.dao.getDefinesByGroupId(groupId);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getModalsByGroupId(String groupId, QueryModelType type) {
        try {
            String UserId = NpContextHolder.getContext().getUserId();
            List<QueryModalDefine> list = this.dao.getDefinesByGroupId(groupId, type);
            String finalUserId = UserId;
            list = list.stream().filter(queryModalDefine -> Objects.equals(queryModalDefine.getCreator(), finalUserId) || this.authority.canDelegateQueryModalResource(queryModalDefine.getGroupId(), "QueryModel")).collect(Collectors.toList());
            return list;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getModalsByCondition(String condition, String[] values) {
        try {
            return this.dao.getDefinesByCondition(condition, values);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getModalsByTitle(String title, String groupId) {
        try {
            return this.dao.getDefinesByTitle(title, groupId);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getOtherModals(QueryModelType type, List<QueryModalGroup> groups) {
        String otherModelGroupCondition = null;
        if (CollectionUtils.isEmpty(groups)) {
            otherModelGroupCondition = " ('b8079ac0-dc15-11e8-969b-64006a6432d8')";
        } else {
            otherModelGroupCondition = " ('b8079ac0-dc15-11e8-969b-64006a6432d8";
            for (QueryModalGroup queryModalGroup : groups) {
                otherModelGroupCondition = otherModelGroupCondition + "' , '" + queryModalGroup.getGroupId();
            }
            otherModelGroupCondition = otherModelGroupCondition + "')";
        }
        String otherModelCondition = " (QMD_GROUPID is  null and QMD_GROUPID not in" + otherModelGroupCondition + " )";
        String modelTypeCondition = " and QMD_TYPE in ('" + (Object)((Object)type) + "')";
        try {
            return this.dao.getDefinesByCondition(otherModelCondition + modelTypeCondition, null);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getChartModals() throws Exception {
        try {
            return this.dao.getAllChartModels();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalDefine> getAllModalsByModelType(QueryModelType type) {
        try {
            List<QueryModalDefine> models = this.dao.getDefinesByModelType(type.toString());
            return models;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

