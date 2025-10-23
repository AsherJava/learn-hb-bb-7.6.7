/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.dataanalyze.CustomConfigItem
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.IAnalyzeResourceDataProvider
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.zbquery.manage;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.bean.ZBQueryExt;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nvwa.dataanalyze.CustomConfigItem;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.IAnalyzeResourceDataProvider;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@DataAnalyzeResource(type="com.jiuqi.nr.zbquery.manage", title="\u67e5\u8be2\u6a21\u677f", pluginName="nr-zbquery-manage", createWinSize={472, 210}, editWinSize={472, 210}, copyWinSize={472, 210}, icon="nr-iconfont icon-16_DH_A_NR_guoluchaxun", order=15, supperHyperlink=true)
public class ZBQueryManageDataProvider
implements IAnalyzeResourceDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryManageDataProvider.class);
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;

    public ResourceTreeNode doGet(ResourceTreeNode node) {
        ZBQueryInfo zbQueryInfo = this.zbQueryInfoService.getQueryInfoById(node.getGuid());
        try {
            ZBQueryModel zbQueryModel = this.zbQueryInfoService.getQueryInfoData(node.getGuid());
            ZBQueryExt zbQueryExt = new ZBQueryExt();
            zbQueryExt.setDesc(zbQueryInfo.getDescription());
            if (zbQueryModel != null) {
                zbQueryExt.setExtendedDatas(zbQueryModel.getExtendedDatas());
            }
            node.setExtData(zbQueryExt.toJSONString());
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        node.setTitle(zbQueryInfo.getTitle());
        return node;
    }

    @Transactional
    public ResourceTreeNode doNew(ResourceTreeNode node) throws DataAnalyzeResourceException {
        this.resourceTreeNodeService.add(node);
        try {
            ZBQueryExt zbQueryExt = this.parseExtendDataFromNode(node);
            ZBQueryInfo zbQueryInfo = new ZBQueryInfo(node.getGuid(), node.getTitle(), zbQueryExt.getDesc());
            zbQueryInfo.setLevel("0");
            this.zbQueryInfoService.addQueryInfo(zbQueryInfo);
            if (zbQueryExt.getExtendedDatas() != null) {
                this.insertExtendedDatas(zbQueryExt.getExtendedDatas(), node.getGuid());
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new DataAnalyzeResourceException(e.getMessage(), (Throwable)e);
        }
        return node;
    }

    @Transactional
    public ResourceTreeNode doCopy(String srcNodeGuid, ResourceTreeNode targetNode) throws DataAnalyzeResourceException {
        this.resourceTreeNodeService.add(targetNode);
        ZBQueryExt zbQueryExt = this.parseExtendDataFromNode(targetNode);
        ResourceTreeNode node = this.resourceTreeNodeService.get(srcNodeGuid);
        ZBQueryInfo zbQueryInfo = new ZBQueryInfo(targetNode.getGuid(), targetNode.getTitle(), zbQueryExt.getDesc());
        ZBQueryInfo srcZbQueryInfo = this.zbQueryInfoService.getQueryInfoById(srcNodeGuid);
        zbQueryInfo.setLevel(srcZbQueryInfo.getLevel());
        try {
            zbQueryInfo = this.zbQueryInfoService.saveAsQueryInfo(srcNodeGuid, zbQueryInfo);
            this.handleExtendedDataStorage(zbQueryExt, targetNode.getGuid());
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new DataAnalyzeResourceException(e.getMessage(), (Throwable)e);
        }
        return new ResourceTreeNode(zbQueryInfo.getId(), node.getName(), zbQueryInfo.getTitle(), node.getType(), targetNode.getParent());
    }

    @Transactional
    public void doEdit(ResourceTreeNode node) throws DataAnalyzeResourceException {
        this.resourceTreeNodeService.update(node);
        ZBQueryExt zbQueryExt = this.parseExtendDataFromNode(node);
        ZBQueryInfo zbQueryInfo = new ZBQueryInfo(node.getGuid(), node.getTitle(), zbQueryExt.getDesc());
        try {
            this.zbQueryInfoService.modifyQueryInfo(zbQueryInfo);
            this.handleExtendedDataStorage(zbQueryExt, node.getGuid());
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new DataAnalyzeResourceException(e.getMessage(), (Throwable)e);
        }
    }

    @Transactional
    public void delete(String guid) throws DataAnalyzeResourceException {
        this.resourceTreeNodeService.delete(guid);
        try {
            this.zbQueryInfoService.deleteQueryInfoById(guid);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new DataAnalyzeResourceException(e.getMessage(), (Throwable)e);
        }
    }

    public List<CustomConfigItem> getCustomConfigItems() {
        ArrayList<CustomConfigItem> configs = new ArrayList<CustomConfigItem>();
        configs.add(new CustomConfigItem("showEditArea", "\u663e\u793a\u914d\u7f6e\u533a\u57df", "checkbox", "false"));
        configs.add(new CustomConfigItem("onlyShowRowColEditArea", "\u4ec5\u663e\u793a\u884c\u5217\u914d\u7f6e\u533a\u57df\uff08\u9700\u52fe\u9009\u663e\u793a\u914d\u7f6e\u533a\u57df\u65f6\u751f\u6548\uff09", "checkbox", "false"));
        configs.add(new CustomConfigItem("autoRefresh", "\u81ea\u52a8\u5237\u65b0", "checkbox", "false"));
        return configs;
    }

    public List<CustomConfigItem> getTemplateCustomConfigItems() {
        ArrayList<CustomConfigItem> configs = new ArrayList<CustomConfigItem>();
        configs.add(new CustomConfigItem("showSourceTree", "\u663e\u793a\u8d44\u6e90\u6811", "checkbox", "true", Arrays.asList("com.jiuqi.nr.dataset.zbquery")));
        configs.add(new CustomConfigItem("showZbSelector", "\u663e\u793a\u6309\u8868\u6837\u6dfb\u52a0", "checkbox", "true", Arrays.asList("com.jiuqi.nr.dataset.zbquery")));
        configs.add(new CustomConfigItem("autoRefresh", "\u81ea\u52a8\u5237\u65b0", "checkbox", "false", Arrays.asList("com.jiuqi.nr.dataset.zbquery")));
        configs.add(new CustomConfigItem("hiddenDimensions", "\u9690\u85cf\u7ef4\u5ea6", "textarea", null, Arrays.asList("com.jiuqi.nr.dataset.zbquery"), "\u793a\u4f8b\uff1a[{\"name\":\"MD_BZ\", \"value\":\"01\"},{\"name\":\"MD_KM\"}]"));
        return configs;
    }

    private ZBQueryExt parseExtendDataFromNode(ResourceTreeNode node) {
        String extData = node.getExtData();
        return ZBQueryExt.parseFromString(extData);
    }

    private void handleExtendedDataStorage(ZBQueryExt zbQueryExt, String nodeId) throws JQException {
        ZBQueryModel zbQueryModel = this.zbQueryInfoService.getQueryInfoData(nodeId);
        if (zbQueryModel != null) {
            this.updateExtendedDatas(zbQueryModel, zbQueryExt.getExtendedDatas(), nodeId);
        } else if (zbQueryExt.getExtendedDatas() != null) {
            this.insertExtendedDatas(zbQueryExt.getExtendedDatas(), nodeId);
        }
    }

    private void insertExtendedDatas(Map<String, String> extendedDatas, String nodeId) throws JQException {
        ZBQueryModel zbQueryModel = this.zbQueryInfoService.createQueryModel();
        zbQueryModel.setExtendedDatas(extendedDatas);
        this.zbQueryInfoService.saveQueryInfoData(zbQueryModel, nodeId);
    }

    private void updateExtendedDatas(ZBQueryModel zbQueryModel, Map<String, String> extendedDatas, String nodeId) throws JQException {
        zbQueryModel.setExtendedDatas(extendedDatas);
        this.zbQueryInfoService.saveQueryInfoData(zbQueryModel, nodeId);
    }
}

