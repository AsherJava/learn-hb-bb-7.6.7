/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.transfer.AbstractBusinessMetaTransferFactory
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServerLevel
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.manage;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FullCoverageImportBusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.manage.ZBQueryModelTransfer;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.SerializeUtils;
import com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.transfer.AbstractBusinessMetaTransferFactory;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServerLevel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ZBQueryMetaTransferFactory
extends AbstractBusinessMetaTransferFactory {
    private static final String ID = "com.jiuqi.nr.zbquery.manage";
    private final IParamLevelManager paramLevelManager;
    private final ResourceTreeNodeDao resourceTreeNodeDao;
    private final ZBQueryInfoService zbQueryInfoService;
    private static final String KEY_ZBQEURYINFO = "zbqueryinfo";
    private static final String KEY_ZBQEURYDATA = "zbquerydata";

    @Autowired
    public ZBQueryMetaTransferFactory(IParamLevelManager paramLevelManager, ResourceTreeNodeDao resourceTreeNodeDao, ZBQueryInfoService zbQueryInfoService) {
        this.paramLevelManager = paramLevelManager;
        this.resourceTreeNodeDao = resourceTreeNodeDao;
        this.zbQueryInfoService = zbQueryInfoService;
    }

    public String getBusinessId() {
        return ID;
    }

    public String getTitle() {
        return "\u6307\u6807\u7efc\u5408\u67e5\u8be2";
    }

    public String getIcon(BusinessNode node) {
        return null;
    }

    protected String getModifiedTime(String guid) throws TransferException {
        try {
            ResourceTreeNode node = this.resourceTreeNodeDao.get(guid);
            if (node == null) {
                throw new TransferException("\u65e0\u6cd5\u67e5\u8be2\u8282\u70b9\u8d44\u6e90\u4fe1\u606f");
            }
            return node.getModifyTime();
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    protected IModelTransfer createModelTransfer() {
        ApplicationContext appCxt = SpringBeanUtils.getApplicationContext();
        return appCxt.getBean(ZBQueryModelTransfer.class);
    }

    protected IDataTransfer createDataTransfer() {
        return null;
    }

    protected List<ResItem> getRelatedBusiness(String guid) throws TransferException {
        return null;
    }

    protected List<String> getDependenceFactoryIds() {
        return null;
    }

    public boolean supportExport(String guid) throws TransferException {
        return true;
    }

    public boolean supportFullCoverageImport() {
        return true;
    }

    public void fullCoverageImportModel(IImportContext context, List<FullCoverageImportBusinessNode> nodes) throws TransferException {
        try {
            int srcPacketLevel = context.getSrcPacketLevel();
            boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
            SubServerLevel serverLevel = this.paramLevelManager.getLevel();
            List<ResourceTreeNode> resourceTreeQueryNodes = this.getAllChildrenQueryNodes(nodes);
            ImportProcessContext processContext = new ImportProcessContext();
            processContext.setNodes(nodes);
            processContext.setResourceTreeQueryNodes(resourceTreeQueryNodes);
            processContext.setSrcPacketLevel(srcPacketLevel);
            if (!openParamLevel) {
                this.importProcess(processContext, ImportProcessContext::getResourceTreeQueryNodes, new LevelSetter1());
            } else {
                int serverLevelValue = serverLevel.getValue();
                if (srcPacketLevel == 0) {
                    this.importProcess(processContext, new ZeroAndServerLevelNodeFilter(), new LevelSetter1());
                } else {
                    if (srcPacketLevel > serverLevelValue) {
                        throw new TransferException("\u5bfc\u51fa\u7aef\u670d\u52a1\u7ea7\u6b21\u5927\u4e8e\u5bfc\u5165\u7aef\u670d\u52a1\u7ea7\u6b21\uff0c\u65e0\u6cd5\u5bfc\u5165");
                    }
                    if (srcPacketLevel == serverLevelValue) {
                        this.importProcess(processContext, new ZeroAndServerLevelNodeFilter(), new LevelSetter2());
                    } else {
                        this.importProcess(processContext, new LessPacketLevelNodeFilter(), new LevelSetter3());
                    }
                }
            }
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    private void importProcess(ImportProcessContext processContext, DeleteNodeFilter deleteNodeFilter, LevelSetter leverSetter) throws Exception {
        this.deleteQueryInfo(deleteNodeFilter.filter(processContext));
        this.importWithLevel(processContext, leverSetter);
    }

    private List<ResourceTreeNode> getAllChildrenQueryNodes(List<FullCoverageImportBusinessNode> nodes) throws Exception {
        ArrayList<ResourceTreeNode> resourceTreeQueryNodes = new ArrayList<ResourceTreeNode>();
        ArrayList<String> rootFolderTitles = new ArrayList<String>();
        block0: for (FullCoverageImportBusinessNode node : nodes) {
            String[] nodePath = node.getPath().split(Pattern.quote(FileSystems.getDefault().getSeparator()));
            for (int i = 0; i < nodePath.length; ++i) {
                if (!nodePath[i].equals("DataAnalysisResourceCategory_ID")) continue;
                String folderTitle = nodePath[i + 1];
                if (rootFolderTitles.contains(folderTitle)) continue block0;
                rootFolderTitles.add(folderTitle);
                ResourceTreeNode rootFolder = this.resourceTreeNodeDao.getFolderByTitle("root", folderTitle);
                if (rootFolder == null) continue block0;
                resourceTreeQueryNodes.addAll(this.getAllChildrenQueryNodes(rootFolder.getGuid()));
                continue block0;
            }
        }
        return resourceTreeQueryNodes;
    }

    private List<ResourceTreeNode> getAllChildrenQueryNodes(String parentFolderGuid) throws Exception {
        List<Object> childQueryNodes = this.resourceTreeNodeDao.getChildren(parentFolderGuid, Collections.singletonList(ID));
        childQueryNodes = childQueryNodes == null ? new ArrayList() : childQueryNodes.stream().filter(node -> !node.isFolder()).collect(Collectors.toList());
        List childNodes = this.resourceTreeNodeDao.getChildren(parentFolderGuid);
        for (ResourceTreeNode child : childNodes) {
            if (!child.isFolder()) continue;
            childQueryNodes.addAll(this.getAllChildrenQueryNodes(child.getGuid()));
        }
        return childQueryNodes;
    }

    private void deleteQueryInfo(List<ResourceTreeNode> deleteQueryNodes) throws Exception {
        List<String> deleteQueryInfoIds = deleteQueryNodes.stream().map(ResourceTreeNode::getGuid).collect(Collectors.toList());
        this.zbQueryInfoService.deleteQueryInfoByIds(deleteQueryInfoIds);
        for (String deleteQueryInfoId : deleteQueryInfoIds) {
            this.resourceTreeNodeDao.delete(deleteQueryInfoId);
        }
    }

    private void importWithLevel(ImportProcessContext processContext, LevelSetter leverSetter) throws Exception {
        for (FullCoverageImportBusinessNode node : processContext.getNodes()) {
            String nodeId = node.getBusinessNode().getGuid();
            ZBQueryInfo findZbQueryInfo = this.zbQueryInfoService.getQueryInfoById(nodeId);
            if (findZbQueryInfo != null) {
                this.zbQueryInfoService.deleteQueryInfoById(nodeId);
                this.resourceTreeNodeDao.delete(nodeId);
            }
            byte[] data = node.getData();
            ZBQueryInfo info = new ZBQueryInfo();
            JSONObject json_zbqueryInfo = new JSONObject(new String(data, StandardCharsets.UTF_8));
            info.fromJson(json_zbqueryInfo.getJSONObject(KEY_ZBQEURYINFO));
            leverSetter.setLevel(processContext, info, node);
            ResourceTreeNode resourceTreeNode = this.getResourceTreeNode(node, info);
            this.zbQueryInfoService.addQueryInfo(info);
            this.resourceTreeNodeDao.insert(resourceTreeNode);
            byte[] modelDataByte = json_zbqueryInfo.getString(KEY_ZBQEURYDATA).getBytes(StandardCharsets.UTF_8);
            ZBQueryModel model = SerializeUtils.jsonDeserialize(modelDataByte);
            if (model == null) continue;
            this.zbQueryInfoService.saveQueryInfoData(model, info.getId());
        }
    }

    private ResourceTreeNode getResourceTreeNode(FullCoverageImportBusinessNode node, ZBQueryInfo info) {
        ResourceTreeNode resourceTreeNode = new ResourceTreeNode();
        resourceTreeNode.setGuid(info.getId());
        resourceTreeNode.setTitle(info.getTitle());
        resourceTreeNode.setType(ID);
        resourceTreeNode.setFolder(false);
        resourceTreeNode.setParent(node.getFolderGuid());
        resourceTreeNode.setModifyTime(String.valueOf(info.getModifyTime()));
        return resourceTreeNode;
    }

    private int getZbQueryInfoLevel(ZBQueryInfo info) {
        String level = info.getLevel();
        if (StringUtils.hasLength(level)) {
            return Integer.parseInt(level);
        }
        return 0;
    }

    class LevelSetter3
    implements LevelSetter {
        LevelSetter3() {
        }

        @Override
        public void setLevel(ImportProcessContext processContext, ZBQueryInfo zbQueryInfo, FullCoverageImportBusinessNode node) {
            int nodeLevel = ZBQueryMetaTransferFactory.this.getZbQueryInfoLevel(zbQueryInfo);
            if (nodeLevel <= processContext.getSrcPacketLevel() && nodeLevel != 0) {
                zbQueryInfo.setLevel(String.valueOf(nodeLevel));
            } else {
                zbQueryInfo.setLevel(String.valueOf(processContext.getSrcPacketLevel()));
            }
        }
    }

    class LevelSetter2
    implements LevelSetter {
        LevelSetter2() {
        }

        @Override
        public void setLevel(ImportProcessContext processContext, ZBQueryInfo zbQueryInfo, FullCoverageImportBusinessNode node) {
            int nodeLevel = ZBQueryMetaTransferFactory.this.getZbQueryInfoLevel(zbQueryInfo);
            if (nodeLevel < processContext.getServerLevel().getValue() && nodeLevel != 0) {
                zbQueryInfo.setLevel(String.valueOf(nodeLevel));
            } else {
                zbQueryInfo.setLevel("0");
            }
        }
    }

    class LevelSetter1
    implements LevelSetter {
        LevelSetter1() {
        }

        @Override
        public void setLevel(ImportProcessContext processContext, ZBQueryInfo zbQueryInfo, FullCoverageImportBusinessNode node) {
            zbQueryInfo.setLevel("0");
        }
    }

    class LessPacketLevelNodeFilter
    implements DeleteNodeFilter {
        LessPacketLevelNodeFilter() {
        }

        @Override
        public List<ResourceTreeNode> filter(ImportProcessContext processContext) {
            return processContext.getResourceTreeQueryNodes().stream().filter((? super T resourceTreeNode) -> {
                ZBQueryInfo zbQueryInfo = ZBQueryMetaTransferFactory.this.zbQueryInfoService.getQueryInfoById(resourceTreeNode.getGuid());
                return StringUtils.hasLength(zbQueryInfo.getLevel()) && !zbQueryInfo.getLevel().equals("0") && Integer.parseInt(zbQueryInfo.getLevel()) <= processContext.getSrcPacketLevel();
            }).collect(Collectors.toList());
        }
    }

    class ZeroAndServerLevelNodeFilter
    implements DeleteNodeFilter {
        ZeroAndServerLevelNodeFilter() {
        }

        @Override
        public List<ResourceTreeNode> filter(ImportProcessContext processContext) {
            return processContext.getResourceTreeQueryNodes().stream().filter((? super T resourceTreeNode) -> {
                ZBQueryInfo zbQueryInfo = ZBQueryMetaTransferFactory.this.zbQueryInfoService.getQueryInfoById(resourceTreeNode.getGuid());
                return zbQueryInfo.getLevel().equals("0") || zbQueryInfo.getLevel().equals(String.valueOf(processContext.getServerLevel().getValue()));
            }).collect(Collectors.toList());
        }
    }

    @FunctionalInterface
    static interface DeleteNodeFilter {
        public List<ResourceTreeNode> filter(ImportProcessContext var1);
    }

    @FunctionalInterface
    static interface LevelSetter {
        public void setLevel(ImportProcessContext var1, ZBQueryInfo var2, FullCoverageImportBusinessNode var3);
    }

    class ImportProcessContext {
        private int srcPacketLevel;
        private final boolean openParamLevel;
        private final SubServerLevel serverLevel;
        private List<ResourceTreeNode> resourceTreeQueryNodes;
        private List<FullCoverageImportBusinessNode> nodes;

        public ImportProcessContext() {
            this.openParamLevel = ZBQueryMetaTransferFactory.this.paramLevelManager.isOpenParamLevel();
            this.serverLevel = ZBQueryMetaTransferFactory.this.paramLevelManager.getLevel();
        }

        public int getSrcPacketLevel() {
            return this.srcPacketLevel;
        }

        public boolean isOpenParamLevel() {
            return this.openParamLevel;
        }

        public SubServerLevel getServerLevel() {
            return this.serverLevel;
        }

        public List<ResourceTreeNode> getResourceTreeQueryNodes() {
            return this.resourceTreeQueryNodes;
        }

        public void setSrcPacketLevel(int srcPacketLevel) {
            this.srcPacketLevel = srcPacketLevel;
        }

        public void setResourceTreeQueryNodes(List<ResourceTreeNode> resourceTreeQueryNodes) {
            this.resourceTreeQueryNodes = resourceTreeQueryNodes;
        }

        public List<FullCoverageImportBusinessNode> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<FullCoverageImportBusinessNode> nodes) {
            this.nodes = nodes;
        }
    }
}

