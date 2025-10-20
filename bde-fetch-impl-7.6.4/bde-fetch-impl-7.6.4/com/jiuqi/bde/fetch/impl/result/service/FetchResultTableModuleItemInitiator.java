/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig
 *  com.jiuqi.bde.common.init.IBdeModuleItemInitiator
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.CatalogModelDefineImpl
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletContext
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.fetch.impl.result.service;

import com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig;
import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.bde.fetch.impl.result.entity.TableIndexVO;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableEnum;
import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableStatusEnum;
import com.jiuqi.bde.fetch.impl.result.service.FetchResultMappingService;
import com.jiuqi.bde.fetch.impl.result.util.FetchResultTableUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.CatalogModelDefineImpl;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FetchResultTableModuleItemInitiator
implements IBdeModuleItemInitiator {
    private final Logger LOGGER = LoggerFactory.getLogger(FetchResultTableModuleItemInitiator.class);

    public String getName() {
        return "\u7ed3\u679c\u8868\u521d\u59cb\u5316";
    }

    public void init(ServletContext context) throws Exception {
    }

    public int getOrder() {
        return 1000;
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        if (!BdeCommonUtil.isStandaloneServer()) {
            this.LOGGER.info("\u5f53\u524d\u670d\u52a1\u975e\u72ec\u7acb\u8fd0\u884c\u7684BDE\u670d\u52a1\uff0c\u8df3\u8fc7\u521d\u59cb\u5316\u7ed3\u679c\u8868");
            return;
        }
        String PROP_SYNC_TABLE = "jiuqi.gcreport.env.inittabledefine";
        Boolean syncTable = ((Environment)ApplicationContextRegister.getBean(Environment.class)).getProperty("jiuqi.gcreport.env.inittabledefine", Boolean.class, true);
        if (!syncTable.booleanValue()) {
            this.LOGGER.info("\u672a\u5f00\u542f\u8868\u7ed3\u6784\u540c\u6b65\uff0c\u8df3\u8fc7\u521d\u59cb\u5316\u7ed3\u679c\u8868");
            return;
        }
        this.LOGGER.info("\u521d\u59cb\u5316\u7ed3\u679c\u8868\u5f00\u59cb");
        try {
            this.initMappingTable();
        }
        catch (Exception e) {
            String PROP_DBTYPE = "spring.datasource.dbType";
            String dbType = ((Environment)ApplicationContextRegister.getBean(Environment.class)).getRequiredProperty("spring.datasource.dbType", String.class);
            if (SqlUtil.isSQLUniqueException((String)dbType, (Exception)e)) {
                this.LOGGER.info("\u7cfb\u7edf\u521d\u59cb\u5316\u6620\u5c04\u8868\u65f6\u629b\u51fa\u552f\u4e00\u7d22\u5f15\u5f02\u5e38\uff0c\u7cfb\u7edf\u8bc6\u522b\u4e3a\u5e76\u53d1\u64cd\u4f5c\u5bfc\u81f4\uff0c\u91c7\u7528\u517c\u5bb9\u7b56\u7565\u5904\u7406", e);
            }
            this.LOGGER.error("\n=========================================================\n=========================================================\n\u7cfb\u7edf\u521d\u59cb\u5316\u6620\u5c04\u8868\u5931\u8d25\uff0cBDE\u6a21\u5757\u53ef\u80fd\u51fa\u73b0\u81f4\u547d\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5BDE_RESULT_MAPPING\u7ed3\u679c\u8868\u521d\u59cb\u60c5\u51b5\n=========================================================\n=========================================================\n", e);
        }
        CatalogModelService catalogModelService = (CatalogModelService)SpringContextUtils.getBean(CatalogModelService.class);
        if (catalogModelService.getCatalogModelDefine("11000000-0000-0000-0000-000000000002") == null) {
            this.LOGGER.info("\u672a\u68c0\u6d4b\u5230\u6570\u636e\u5efa\u6a21\u76ee\u5f55\uff0c\u521b\u5efa\u76ee\u5f55");
            CatalogModelDefineImpl designCatalogModelDefine = new CatalogModelDefineImpl();
            designCatalogModelDefine.setID("11000000-0000-0000-0000-000000000002");
            designCatalogModelDefine.setTitle("BDE");
            catalogModelService.insertCatalogModelDefine((DesignCatalogModelDefine)designCatalogModelDefine);
            this.LOGGER.info("\u521b\u5efa\u5b8c\u6210");
        }
        ArrayList<FetchResultMappingEO> mappingEOS = new ArrayList<FetchResultMappingEO>();
        for (int i = 1; i <= FetchResultConfig.fetchResultTableNum; ++i) {
            boolean tableUsable = true;
            for (FetchResultTableEnum fetchResultTableEnum : FetchResultTableEnum.values()) {
                try {
                    tableUsable = true;
                    this.initTable(fetchResultTableEnum, i);
                }
                catch (Exception e) {
                    String PROP_DBTYPE = "spring.datasource.dbType";
                    String dbType = ((Environment)ApplicationContextRegister.getBean(Environment.class)).getRequiredProperty("spring.datasource.dbType", String.class);
                    if (SqlUtil.isSQLUniqueException((String)dbType, (Exception)e)) {
                        this.LOGGER.info("\u7cfb\u7edf\u521b\u5efa{}\u629b\u51fa\u552f\u4e00\u7d22\u5f15\u5f02\u5e38\uff0c\u7cfb\u7edf\u8bc6\u522b\u4e3a\u5e76\u53d1\u64cd\u4f5c\u5bfc\u81f4\uff0c\u91c7\u7528\u517c\u5bb9\u7b56\u7565\u5904\u7406", (Object)FetchResultTableUtil.getTableName(fetchResultTableEnum.getTableName(), i), (Object)e);
                    } else {
                        this.LOGGER.error(String.format("\u521b\u5efa%1$s\u5931\u8d25", FetchResultTableUtil.getTableName(fetchResultTableEnum.getTableName(), i)), e);
                    }
                    tableUsable = false;
                }
            }
            if (tableUsable) {
                mappingEOS.add(new FetchResultMappingEO(i, FetchResultTableStatusEnum.AVAIABLE));
                continue;
            }
            mappingEOS.add(new FetchResultMappingEO(i, FetchResultTableStatusEnum.STOP));
        }
        this.initMappingTableData(mappingEOS);
        this.LOGGER.info("\u521d\u59cb\u5316\u7ed3\u679c\u8868\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public void initTable(FetchResultTableEnum fetchResultTableEnum, int index) throws Exception {
        String tableName = FetchResultTableUtil.getTableName(fetchResultTableEnum.getTableName(), index);
        DesignTableModelDefine fixTableModel = FetchResultTableUtil.getResultTable(tableName, "BDE\u7ed3\u679c\u5217");
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        DesignTableModelDefine oldFixTableModel = designDataModelService.getTableModelDefineByCode(tableName);
        if (oldFixTableModel == null) {
            fixTableModel.setCreateTime(new Date());
            designDataModelService.insertTableModelDefine(fixTableModel);
        } else {
            fixTableModel.setID(oldFixTableModel.getID());
            designDataModelService.updateTableModelDefine(fixTableModel);
        }
        List<Object> columnModel = new ArrayList();
        List<Object> tableIndexVOS = new ArrayList();
        columnModel = fetchResultTableEnum.getFieldList(fixTableModel.getID());
        tableIndexVOS = fetchResultTableEnum.getIndexList();
        List designColumnModelDefineList = designDataModelService.getColumnModelDefinesByTable(fixTableModel.getID());
        Map columnModelMap = designColumnModelDefineList.stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity(), (K1, K2) -> K1));
        int order = 0;
        for (DesignColumnModelDefine designColumnModelDefine : columnModel) {
            designColumnModelDefine.setAggrType(AggrType.NONE);
            designColumnModelDefine.setApplyType(ApplyType.NONE);
            designColumnModelDefine.setOrder((double)order);
            if (columnModelMap.get(designColumnModelDefine.getCode()) == null) {
                designDataModelService.insertColumnModelDefine(designColumnModelDefine);
            } else {
                designColumnModelDefine.setID(((DesignColumnModelDefine)columnModelMap.get(designColumnModelDefine.getCode())).getID());
                designDataModelService.updateColumnModelDefine(designColumnModelDefine);
                columnModelMap.remove(designColumnModelDefine.getCode());
            }
            ++order;
        }
        if (columnModelMap.size() > 0) {
            for (DesignColumnModelDefine designColumnModelDefine : columnModelMap.values()) {
                designDataModelService.deleteColumnModelDefine(designColumnModelDefine.getID());
            }
        }
        List fields = designDataModelService.getColumnModelDefinesByTable(fixTableModel.getID());
        Map<String, DesignColumnModelDefine> map = fields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, e -> e));
        List indexs = designDataModelService.getIndexsByTable(fixTableModel.getID());
        if (CollectionUtils.isEmpty((Collection)indexs)) {
            for (TableIndexVO tableIndexVO : tableIndexVOS) {
                String[] stringArray = new String[tableIndexVO.getFields().size()];
                for (int i = 0; i < tableIndexVO.getFields().size(); ++i) {
                    stringArray[i] = map.get(tableIndexVO.getFields().get(i)).getID();
                }
                if (tableIndexVO.getIndexType() != null) {
                    designDataModelService.addIndexToTable(fixTableModel.getID(), stringArray, String.format("%1$s_%2$d", tableIndexVO.getIndexName(), index), tableIndexVO.getIndexType());
                    continue;
                }
                fixTableModel.setKeys(CollectionUtil.join(Arrays.asList(stringArray), (String)";"));
                designDataModelService.updateTableModelDefine(fixTableModel);
            }
        } else {
            Map<String, List<DesignIndexModelDefine>> IndexGroupByName = indexs.stream().collect(Collectors.groupingBy(IndexModelDefine::getName));
            for (TableIndexVO tableIndexVO : tableIndexVOS) {
                if (IndexGroupByName.get(String.format("%1$s_%2$d", tableIndexVO.getIndexName(), index)) != null) continue;
                String[] indexFields = new String[tableIndexVO.getFields().size()];
                for (int i = 0; i < tableIndexVO.getFields().size(); ++i) {
                    indexFields[i] = map.get(tableIndexVO.getFields().get(i)).getID();
                }
                if (tableIndexVO.getIndexType() != null) {
                    designDataModelService.addIndexToTable(fixTableModel.getID(), indexFields, String.format("%1$s_%2$d", tableIndexVO.getIndexName(), index), tableIndexVO.getIndexType());
                    continue;
                }
                fixTableModel.setKeys(CollectionUtil.join(tableIndexVO.getFields(), (String)";"));
                designDataModelService.updateTableModelDefine(fixTableModel);
            }
        }
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringContextUtils.getBean(DataModelDeployService.class);
        dataModelDeployService.deployTable(fixTableModel.getID());
    }

    @Transactional(rollbackFor={Exception.class})
    public void initMappingTable() throws Exception {
        List indexs;
        DesignTableModelDefine oldFixTableModel;
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringContextUtils.getBean(DataModelDeployService.class);
        DesignTableModelDefine designTableModel = oldFixTableModel = designDataModelService.getTableModelDefineByCode("BDE_RESULT_MAPPING");
        if (oldFixTableModel == null) {
            DesignTableModelDefine fixTableModel = FetchResultTableUtil.getResultTable("BDE_RESULT_MAPPING", "BDE\u7ed3\u679c\u8868\u6620\u5c04\u8868");
            designDataModelService.insertTableModelDefine(fixTableModel);
            boolean order = false;
            List<DesignColumnModelDefine> columnModel = FetchResultTableUtil.getResultMappingColumn(fixTableModel.getID());
            for (DesignColumnModelDefine designColumnModelDefine : columnModel) {
                designColumnModelDefine.setAggrType(AggrType.NONE);
                designColumnModelDefine.setApplyType(ApplyType.NONE);
                designColumnModelDefine.setOrder((double)order);
                designDataModelService.insertColumnModelDefine(designColumnModelDefine);
            }
            designTableModel = fixTableModel;
        }
        if (CollectionUtils.isEmpty((Collection)(indexs = designDataModelService.getIndexsByTable(designTableModel.getID())))) {
            List fields = designDataModelService.getColumnModelDefinesByTable(designTableModel.getID());
            Map<String, DesignColumnModelDefine> fieldGroupByCode = fields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, e -> e));
            List<TableIndexVO> tableIndexVOS = FetchResultTableUtil.getResultMappingIndexList();
            for (TableIndexVO tableIndexVO : tableIndexVOS) {
                String[] indexFields = new String[tableIndexVO.getFields().size()];
                for (int i = 0; i < tableIndexVO.getFields().size(); ++i) {
                    indexFields[i] = fieldGroupByCode.get(tableIndexVO.getFields().get(i)).getID();
                }
                designDataModelService.addIndexToTable(designTableModel.getID(), indexFields, tableIndexVO.getIndexName(), tableIndexVO.getIndexType());
            }
        }
        dataModelDeployService.deployTable(designTableModel.getID());
    }

    @Transactional(rollbackFor={Exception.class})
    public void initMappingTableData(List<FetchResultMappingEO> mappingEOS) {
        ArrayList<FetchResultMappingEO> insertFetchResultMappingEOList = new ArrayList<FetchResultMappingEO>();
        FetchResultMappingService fetchResultMappingService = (FetchResultMappingService)SpringContextUtils.getBean(FetchResultMappingService.class);
        if (fetchResultMappingService == null) {
            return;
        }
        for (FetchResultMappingEO fetchResultMappingEO : mappingEOS) {
            FetchResultMappingEO oldMapping = fetchResultMappingService.getMappingEOByRouteNum(fetchResultMappingEO.getRouteNum());
            if (oldMapping == null) {
                insertFetchResultMappingEOList.add(fetchResultMappingEO);
                continue;
            }
            if (fetchResultMappingEO.getRouteStatus().equals((Object)oldMapping.getRouteStatus())) continue;
            fetchResultMappingService.updateRouteStatus(fetchResultMappingEO);
        }
        fetchResultMappingService.insertFetchResultMapping(insertFetchResultMappingEOList);
    }
}

