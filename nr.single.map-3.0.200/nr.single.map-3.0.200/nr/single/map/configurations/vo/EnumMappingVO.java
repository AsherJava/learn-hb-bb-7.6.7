/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.vo;

import java.util.List;
import nr.single.map.configurations.vo.CommonEnumMapping;
import nr.single.map.configurations.vo.CommonTreeNode;

public class EnumMappingVO {
    private String configKey;
    private List<CommonTreeNode> enumTree;
    private List<CommonEnumMapping> enumInfos;

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public List<CommonTreeNode> getEnumTree() {
        return this.enumTree;
    }

    public void setEnumTree(List<CommonTreeNode> enumTree) {
        this.enumTree = enumTree;
    }

    public List<CommonEnumMapping> getEnumInfos() {
        return this.enumInfos;
    }

    public void setEnumInfos(List<CommonEnumMapping> enumInfos) {
        this.enumInfos = enumInfos;
    }
}

