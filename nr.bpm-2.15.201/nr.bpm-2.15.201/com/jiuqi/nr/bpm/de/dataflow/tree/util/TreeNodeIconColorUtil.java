/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.bpm.de.dataflow.tree.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.de.dataflow.tree.NodeInfo;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.upload.utils.NodeColorEnum;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="tree-node-color")
public class TreeNodeIconColorUtil
implements ISystemOptionDeclare {
    private static final String TREENODEID = "nr-tree-node-id";
    private static final String TREENODEICONCOLOR = "TREE-NODE-ICON-COLOR";
    @Autowired
    private ISystemOptionOperator systemOptionOperator;
    private String nodeInfoStrCache = null;

    public String getId() {
        return TREENODEID;
    }

    public String getTitle() {
        return "\u6811\u5f62\u8282\u70b9\u989c\u8272";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "treenodecolor-plugin";
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return TreeNodeIconColorUtil.TREENODEICONCOLOR;
            }

            public String getTitle() {
                return "\u8bbe\u7f6e\u6811\u5f62\u7ed3\u70b9\u989c\u8272";
            }

            public String getDefaultValue() {
                NodeColorEnum[] values;
                if (TreeNodeIconColorUtil.this.nodeInfoStrCache != null) {
                    return TreeNodeIconColorUtil.this.nodeInfoStrCache;
                }
                NodeInfo nodeInfo = new NodeInfo();
                ArrayList<TreeNodeColorInfo> list = new ArrayList<TreeNodeColorInfo>();
                TreeNodeColorInfo treeNodeColorInfo = null;
                for (NodeColorEnum value : values = NodeColorEnum.values()) {
                    treeNodeColorInfo = new TreeNodeColorInfo();
                    treeNodeColorInfo.setStateName(value.getTitle());
                    treeNodeColorInfo.setState(value.getUploadState().name());
                    treeNodeColorInfo.setIcon(value.getIcon());
                    treeNodeColorInfo.setColor(value.getColor());
                    list.add(treeNodeColorInfo);
                }
                nodeInfo.setInfoItems(list);
                nodeInfo.setType(NodeColorEnum.Type.ICON.name());
                nodeInfo.setOptionId(TreeNodeIconColorUtil.TREENODEICONCOLOR);
                nodeInfo.setSystemId(TreeNodeIconColorUtil.TREENODEID);
                ObjectMapper objectMapper = new ObjectMapper();
                String nodeInfoStr = null;
                try {
                    nodeInfoStr = objectMapper.writeValueAsString((Object)nodeInfo);
                }
                catch (JsonProcessingException e) {
                    throw new RuntimeException();
                }
                TreeNodeIconColorUtil.this.nodeInfoStrCache = nodeInfoStr;
                return nodeInfoStr;
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

