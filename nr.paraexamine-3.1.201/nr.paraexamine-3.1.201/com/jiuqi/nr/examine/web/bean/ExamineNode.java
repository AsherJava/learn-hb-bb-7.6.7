/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.examine.common.ExamineType;
import com.jiuqi.nr.examine.common.RestoreStatus;
import com.jiuqi.nr.examine.web.bean.ExamineDetailInfoVO;
import com.jiuqi.nr.examine.web.bean.ExamineNodeType;
import com.jiuqi.nr.examine.web.bean.RestoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="\u8282\u70b9\u7c7b", description="\u8282\u70b9\u7c7b")
public class ExamineNode {
    @ApiModelProperty(value="\u6807\u9898")
    public String title;
    @ApiModelProperty(value="\u7c7b\u578b")
    public int type;
    @ApiModelProperty(value="\u7ed3\u679c\u8282\u70b9")
    public List<ExamineNode> children;
    @ApiModelProperty(value="\u68c0\u67e5\u4fe1\u606f")
    public List<ExamineDetailInfoVO> infos;
    @ApiModelProperty(value="\u4fee\u590d\u7edf\u8ba1\u4fe1\u606f")
    public RestoreInfo restoreInfo;
    @JsonIgnore
    private Map<ExamineNodeType, ExamineNode> nodeMap;

    public ExamineNode getNode(ExamineNodeType type) {
        ExamineNode node;
        if (this.nodeMap == null) {
            this.nodeMap = new HashMap<ExamineNodeType, ExamineNode>();
        }
        if ((node = this.nodeMap.get((Object)type)) == null) {
            node = new ExamineNode();
            node.title = type.getMsg();
            node.type = type.getValue();
            node.children = new ArrayList<ExamineNode>();
            node.infos = new ArrayList<ExamineDetailInfoVO>();
            this.nodeMap.put(type, node);
        }
        return node;
    }

    public void fixChildren() {
        if (this.nodeMap != null) {
            this.children = new ArrayList<ExamineNode>();
            this.nodeMap.values().stream().forEach(m -> this.children.add((ExamineNode)m));
        }
        this.computeRestoreInfo();
    }

    protected void computeRestoreInfo() {
        this.restoreInfo = new RestoreInfo();
        if (this.children != null && this.children.size() > 0) {
            this.children.stream().forEach(c -> {
                if (c.restoreInfo == null) {
                    c.computeRestoreInfo();
                }
                this.restoreInfo.add(c.restoreInfo);
            });
        } else if (this.infos != null) {
            this.infos.stream().forEach(i -> {
                ++this.restoreInfo.total;
                if (i.examineType == ExamineType.ERROR.getValue()) {
                    ++this.restoreInfo.error;
                } else if (i.examineType == ExamineType.QUOTE.getValue()) {
                    ++this.restoreInfo.quote;
                } else if (i.examineType == ExamineType.REFUSE.getValue()) {
                    ++this.restoreInfo.refuse;
                }
                if (i.restoreStatus == RestoreStatus.FAILED.getValue()) {
                    ++this.restoreInfo.fail;
                } else if (i.restoreStatus == RestoreStatus.IGNORE.getValue()) {
                    ++this.restoreInfo.ignore;
                } else if (i.restoreStatus == RestoreStatus.MARKSUCCESS.getValue()) {
                    ++this.restoreInfo.markSuccess;
                } else if (i.restoreStatus == RestoreStatus.SUCCESS.getValue()) {
                    ++this.restoreInfo.success;
                } else if (i.restoreStatus == RestoreStatus.UNRESTORE.getValue()) {
                    ++this.restoreInfo.unDo;
                }
            });
        }
    }
}

