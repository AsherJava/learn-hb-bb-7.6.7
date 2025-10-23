/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.param.transfer.definition.DefinitionTransferFactory
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  org.jdom2.Element
 */
package com.jiuqi.nr.singlequeryimport.deploy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.param.transfer.definition.DefinitionTransferFactory;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.deploy.BmjsQueryBusinessManager;
import com.jiuqi.nr.singlequeryimport.deploy.BmjsQueryFolderManager;
import com.jiuqi.nr.singlequeryimport.deploy.BmjsQueryModelTransfer;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BmjsQueryTransferFactory
extends TransferFactory {
    private static final Logger logger = LoggerFactory.getLogger(BmjsQueryTransferFactory.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    @Autowired
    QueryModeleDao queryModeleDao;
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    DefinitionTransferFactory definitionTransferFactory;
    @Autowired
    BmjsQueryFolderManager bmjsQueryFolderManager;
    @Autowired
    BmjsQueryBusinessManager bmjsQueryBusinessManager;
    @Autowired
    BmjsQueryModelTransfer bmjsQueryModelTransfer;
    @Autowired
    AuthShareService authShareService;
    @Autowired
    IParamLevelManager paramLevelManager;

    public String getId() {
        return "QUERY_TRANSFER_FACTORY_ID";
    }

    public String getTitle() {
        return "\u67e5\u8be2\u6a21\u677f";
    }

    public String getModuleId() {
        return "com.jiuqi.bmjs";
    }

    public boolean supportExport(String s) {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) {
        return this.bmjsQueryModelTransfer;
    }

    public IConfigTransfer createConfigTransfer() {
        return null;
    }

    public IPublisher createPublisher() {
        return null;
    }

    public IDataTransfer createDataTransfer(String s) {
        return null;
    }

    public boolean supportExportData(String s) {
        return false;
    }

    public List<NameMapperBean> handleMapper() {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) {
        return null;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.bmjsQueryFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.bmjsQueryBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        return null;
    }

    public String getIcon() {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String s) throws TransferException {
        String[] split = s.split("#");
        ArrayList<ResItem> resItems = new ArrayList<ResItem>();
        this.definitionTransferFactory.getRelatedBusinessOfTheTask(resItems, split[0]);
        this.definitionTransferFactory.getRelatedBusinessOfTheFormScheme(resItems, split[1]);
        return resItems;
    }

    public List<String> getDependenceFactoryIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DEFINITION_TRANSFER_FACTORY_ID");
        return list;
    }

    public int getOrder() {
        return -126;
    }

    public void toDocumentExtra(Element folderElement, FolderNode folderNode) {
    }

    public FolderNode createFolderNode() {
        logger.info("createFolderNode");
        return null;
    }

    public void loadFolderExtra(Element element, FolderNode folderNode) {
    }

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
    }

    public boolean supportFullCoverageImport() {
        return true;
    }

    public void fullCoverageImportModel(IImportContext context, List<FullCoverageImportBusinessNode> nodes) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Object> queryModels = new ArrayList<Object>();
        try {
            int serveLevel = this.paramLevelManager.getLevel().getValue();
            int srcPacketLevel = context.getSrcPacketLevel();
            if (!nodes.isEmpty()) {
                for (FullCoverageImportBusinessNode node : nodes) {
                    queryModels.add(objectMapper.readValue(node.getData(), QueryModel.class));
                }
                Map<GroupKey, List<QueryModel>> collect = queryModels.stream().collect(Collectors.groupingBy(qm -> new GroupKey(qm.getTaskKey(), qm.getFormschemeKey())));
                logger.info("\u5f00\u59cb\u5168\u91cf\u5bfc\u5165,\u5f53\u524d\u670d\u52a1\u7684\u7ea7\u6b21\u662f{}\uff0c\u53c2\u6570\u5305\u7684\u7ea7\u6b21\u662f{}", (Object)serveLevel, (Object)srcPacketLevel);
                for (GroupKey group : collect.keySet()) {
                    this.importModel(serveLevel, srcPacketLevel, collect.get(group), group);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - void declaration
     */
    void importModel(int serveLevel, int srcPacketLevel, List<QueryModel> queryModels, GroupKey group) throws Exception {
        HashSet<Integer> levels;
        if (0 == serveLevel) {
            this.queryModeleDao.deleteAll(group.formschemeKey, group.taskKey);
            for (QueryModel queryModel : queryModels) {
                queryModel.setLevel(0);
                this.changeModel(queryModel);
            }
        }
        if (serveLevel != 0 && srcPacketLevel == 0) {
            levels = new HashSet<Integer>();
            levels.add(0);
            levels.add(serveLevel);
            this.queryModeleDao.deleteByLevel(group.formschemeKey, group.taskKey, levels);
            for (QueryModel queryModel : queryModels) {
                queryModel.setLevel(0);
                this.changeModel(queryModel);
            }
        }
        if (serveLevel != 0 && srcPacketLevel != 0) {
            if (srcPacketLevel > serveLevel) {
                logger.info("\u53c2\u6570\u4e0d\u80fd\u4ece\u4f4e\u7ea7\u522b\u7684\u670d\u52a1\u5bfc\u5165\u5230\u9ad8\u7ea7\u522b\u7684\u670d\u52a1\u4e2d\u53bb");
                throw new Exception("\u53c2\u6570\u4e0d\u80fd\u4ece\u4f4e\u7ea7\u522b\u7684\u670d\u52a1\u5bfc\u5165\u5230\u9ad8\u7ea7\u522b\u7684\u670d\u52a1\u4e2d\u53bb");
            }
            if (srcPacketLevel == serveLevel) {
                levels = new HashSet();
                levels.add(0);
                levels.add(serveLevel);
                this.queryModeleDao.deleteByLevel(group.formschemeKey, group.taskKey, levels);
                for (QueryModel queryModel : queryModels) {
                    if (queryModel.getLevel() >= serveLevel) {
                        queryModel.setLevel(0);
                    }
                    this.changeModel(queryModel);
                }
            }
            if (srcPacketLevel < serveLevel) {
                void var6_12;
                levels = new HashSet();
                boolean bl = true;
                while (var6_12 <= srcPacketLevel) {
                    levels.add((int)var6_12);
                    ++var6_12;
                }
                this.queryModeleDao.deleteByLevel(group.formschemeKey, group.taskKey, levels);
                for (QueryModel queryModel : queryModels) {
                    if (queryModel.getLevel() > srcPacketLevel || queryModel.getLevel() == 0) {
                        queryModel.setLevel(srcPacketLevel);
                    }
                    this.changeModel(queryModel);
                }
            }
        }
    }

    void changeModel(QueryModel queryModel) throws Exception {
        if (null == this.queryModeleDao.getData(queryModel.getKey())) {
            this.queryModeleDao.insert(queryModel);
            logger.info("\u5168\u91cf\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u67e5\u8be2\u6a21\u677f\uff0c\u65b0\u589e\u6a21\u677f---\u300b" + queryModel.getItemTitle());
        } else {
            this.queryModeleDao.update(queryModel);
            logger.info("\u5168\u91cf\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u67e5\u8be2\u6a21\u677f\uff0c\u66f4\u65b0\u6a21\u677f---\u300b" + queryModel.getItemTitle());
        }
        if (StringUtils.hasText(queryModel.getItemTitle())) {
            this.authShareService.addCurUserPrivilege(queryModel.getKey(), FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
        }
        if (StringUtils.hasText(queryModel.getGroup())) {
            this.authShareService.addCurUserGroupPrivilege(queryModel.getFormschemeKey(), queryModel.getGroup(), FinalaccountQueryAuthResourceType.FQ_GROUP);
        }
    }

    public class GroupKey {
        private final String taskKey;
        private final String formschemeKey;

        public GroupKey(String taskKey, String formschemeKey) {
            this.taskKey = taskKey;
            this.formschemeKey = formschemeKey;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            GroupKey groupKey = (GroupKey)o;
            return Objects.equals(this.taskKey, groupKey.taskKey) && Objects.equals(this.formschemeKey, groupKey.formschemeKey);
        }

        public int hashCode() {
            return Objects.hash(this.taskKey, this.formschemeKey);
        }
    }
}

