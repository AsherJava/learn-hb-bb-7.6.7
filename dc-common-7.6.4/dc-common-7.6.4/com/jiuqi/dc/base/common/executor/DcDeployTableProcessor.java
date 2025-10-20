/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.IndexModelDao
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.dc.base.common.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.utils.DefinitionUtil;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.IndexModelDao;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DcDeployTableProcessor
extends DeployTableProcessor {
    private static Logger logger = LoggerFactory.getLogger(DcDeployTableProcessor.class);
    private final DefinitionTableV definitionTable;
    private final DesignDataModelService designDataModelService;
    private final DataModelDeployService dataModelDeployService;
    private final ColumnModelDao columnModelDao;
    private final IndexModelDao indexModelDao;
    private final DataModelService dataModelService;

    public static DcDeployTableProcessor newInstance(DefinitionTableV definitionTable) {
        return new DcDeployTableProcessor(definitionTable);
    }

    private DcDeployTableProcessor(DefinitionTableV definitionTable) {
        super(definitionTable);
        this.definitionTable = definitionTable;
        this.designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        this.dataModelDeployService = (DataModelDeployService)SpringContextUtils.getBean(DataModelDeployService.class);
        this.columnModelDao = (ColumnModelDao)SpringContextUtils.getBean(ColumnModelDao.class);
        this.indexModelDao = (IndexModelDao)SpringContextUtils.getBean(IndexModelDao.class);
        this.dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
    }

    public void deploy() throws Exception {
        this.publishLog = new StringBuilder("\u521d\u59cb\u5316\u8868" + this.definitionTable.getTableName() + ": \n ");
        List<String> dbColumnList = DefinitionUtil.getTableColumn(this.definitionTable);
        this.invalidFields = new ArrayList();
        this.bizFieldIds = new String[this.definitionTable.getBizKeyFields().length];
        this.deployCatalog(this.definitionTable.getOwnerGroup());
        DesignTableModelDefine designTableDefine = this.deployTableDefine();
        this.definitionTable.setKey(designTableDefine.getID());
        this.deployColumns();
        this.processInvalidFields();
        this.deployIndexs();
        this.resetTableKeys();
        if (this.needDeployTableDefine) {
            try {
                if (!CollectionUtils.isEmpty(dbColumnList)) {
                    List columnModelDefineList = this.designDataModelService.getColumnModelDefinesByTable(designTableDefine.getID());
                    String newPrimaryKeyId = columnModelDefineList.stream().filter(e -> e.getCode().equals(this.definitionTable.getBizKeyFields()[0])).findFirst().map(IModelDefineItem::getID).orElse("");
                    if (!Objects.equals(newPrimaryKeyId, designTableDefine.getKeys())) {
                        designTableDefine.setKeys(null);
                        designTableDefine.setBizKeys(null);
                        this.designDataModelService.updateTableModelDefines(new DesignTableModelDefine[]{designTableDefine});
                    }
                    DataModelRegisterService dataModelRegisterService = (DataModelRegisterService)SpringContextUtils.getBean(DataModelRegisterService.class);
                    dataModelRegisterService.registerTable(designTableDefine.getID());
                    if (!Objects.equals(newPrimaryKeyId, designTableDefine.getKeys())) {
                        designTableDefine.setKeys(newPrimaryKeyId);
                        designTableDefine.setBizKeys(newPrimaryKeyId);
                        this.designDataModelService.updateTableModelDefines(new DesignTableModelDefine[]{designTableDefine});
                    }
                    Map<String, String> columnMap = columnModelDefineList.stream().collect(Collectors.toMap(IModelDefineItem::getID, ColumnModelDefine::getName, (k1, k2) -> k1));
                    List deleteFieldIdList = columnModelDefineList.stream().filter(e -> !dbColumnList.contains(e.getName())).map(IModelDefineItem::getID).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(deleteFieldIdList)) {
                        this.columnModelDao.delete(deleteFieldIdList.toArray());
                    }
                    this.handleIndex(designTableDefine, columnMap);
                }
            }
            catch (Exception e2) {
                logger.error("\u3010{}\u3011\u8868\u53d1\u5e03\u524d\u5904\u7406\u5931\u8d25", (Object)designTableDefine.getName(), (Object)e2);
            }
            this.dataModelDeployService.deployTable(designTableDefine.getID());
            logger.info(this.publishLog.toString());
        }
    }

    private void handleIndex(DesignTableModelDefine designTableDefine, Map<String, String> columnMap) {
        List designIndexsList = this.designDataModelService.getIndexsByTable(designTableDefine.getID());
        if (!CollectionUtils.isEmpty((Collection)designIndexsList)) {
            Map<String, Set<String>> tableIndexMap = DefinitionUtil.getTableIndexMap(this.definitionTable);
            List<String> deleteIndexDesIdList = designIndexsList.stream().filter(e -> Optional.ofNullable(this.definitionTable.getIndexs()).orElse(CollectionUtils.newArrayList()).stream().noneMatch(o -> {
                StringJoiner joiner = new StringJoiner(";");
                Stream.of(o.getColumnIds()).forEach(joiner::add);
                return !Objects.equals(o.getTitle(), e.getName()) || Objects.equals(joiner.toString(), e.getFieldIDs());
            })).map(IndexModelDefine::getID).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(deleteIndexDesIdList)) {
                this.designDataModelService.deleteIndexModelDefines(deleteIndexDesIdList.toArray(new String[0]));
            }
            List deleteIndexIdList = designIndexsList.stream().filter(e -> {
                Set indexColumnSet = Arrays.stream(e.getFieldIDs().split(";")).map(o -> columnMap.getOrDefault(o, (String)o)).collect(Collectors.toSet());
                return tableIndexMap.values().stream().noneMatch(v -> CollectionUtils.isEmpty((Collection)CollectionUtils.diff((Collection)indexColumnSet, (Collection)v)) && CollectionUtils.isEmpty((Collection)CollectionUtils.diff((Collection)v, (Collection)indexColumnSet)));
            }).map(IndexModelDefine::getID).collect(Collectors.toList());
            ArrayList indexIdList = CollectionUtils.newArrayList();
            indexIdList.addAll(deleteIndexDesIdList);
            indexIdList.addAll(deleteIndexIdList);
            if (!CollectionUtils.isEmpty((Collection)indexIdList)) {
                this.indexModelDao.delete(indexIdList.toArray());
            }
        }
    }

    public DesignTableModelDefine repair() throws Exception {
        this.publishLog = new StringBuilder("\u8868" + this.definitionTable.getTableName() + "\u8bbe\u8ba1\u671f\u4fee\u590d: \n ");
        this.invalidFields = new ArrayList();
        this.bizFieldIds = new String[this.definitionTable.getBizKeyFields().length];
        this.deployCatalog(this.definitionTable.getOwnerGroup());
        DesignTableModelDefine designTableDefine = this.deployTableDefine();
        this.definitionTable.setKey(designTableDefine.getID());
        this.deployColumns();
        this.processInvalidFields();
        this.deployIndexs();
        this.resetTableKeys();
        return designTableDefine;
    }
}

