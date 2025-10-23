/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.tree.core.CustomDataProviderComparator;
import com.jiuqi.nr.summary.tree.core.ITreeService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractTreeService
implements ITreeService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTreeService.class);
    private static final String method_buildTreeNode = "buildTreeNode";
    private final List<TreeNodeBuilder<?>> customDataProviderList = new ArrayList();
    private final Map<String, Method> buildTreeNodeMethodMap = new HashMap<String, Method>();

    @Override
    public abstract String getId();

    public abstract void registerCustomDataProviders(List<TreeNodeBuilder<?>> var1);

    @Override
    public List<TreeNode> getRoots(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        return this.getNodes(treeQueryParam);
    }

    @Override
    public List<TreeNode> getChilds(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        return this.getNodes(treeQueryParam);
    }

    private List<TreeNode> getNodes(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        this.registerCustomDataProvider();
        ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (TreeNodeBuilder<?> dataProvider : this.customDataProviderList) {
            List<?> customDatas;
            boolean needQuery = dataProvider.needQuery(treeQueryParam);
            if (!needQuery || CollectionUtils.isEmpty(customDatas = dataProvider.queryData(treeQueryParam))) continue;
            try {
                Method buildTreeNodeMethod = this.buildTreeNodeMethodMap.get(dataProvider.getClass().getName());
                for (Object data : customDatas) {
                    TreeNode treeNode = (TreeNode)buildTreeNodeMethod.invoke(dataProvider, data, treeQueryParam);
                    treeNodes.add(treeNode);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SummaryCommonException(SummaryErrorEnum.TREE_LOAD_FAILED);
            }
        }
        return treeNodes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerCustomDataProvider() {
        AbstractTreeService abstractTreeService = this;
        synchronized (abstractTreeService) {
            if (CollectionUtils.isEmpty(this.customDataProviderList)) {
                this.registerCustomDataProviders(this.customDataProviderList);
                if (!CollectionUtils.isEmpty(this.customDataProviderList)) {
                    this.customDataProviderList.forEach(builder -> {
                        Class<?> clazz = builder.getClass();
                        if (!this.buildTreeNodeMethodMap.containsKey(clazz.getName())) {
                            Arrays.asList(clazz.getMethods()).forEach(method -> {
                                if (method_buildTreeNode.equals(method.getName())) {
                                    this.buildTreeNodeMethodMap.put(clazz.getName(), (Method)method);
                                }
                            });
                        }
                    });
                    this.customDataProviderList.sort(new CustomDataProviderComparator());
                }
            }
        }
    }
}

