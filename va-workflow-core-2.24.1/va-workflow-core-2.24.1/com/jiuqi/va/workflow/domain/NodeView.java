/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.user.UserDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.user.UserDO;
import java.util.Date;
import java.util.List;

public class NodeView {
    private String nodeName;
    private String nodeCode;
    private String nodeState;
    private Date nodeTime;
    private Integer nodeType;
    private UserDO myNodeUser;
    private List<UserDO> nodeUser;
    private List<UserDO> plusApprovaledUsers;
    private String approvalResult;
    private String approvalComment;
    private Integer commentColor;
    private Long nodeHoldingTime;
    private boolean expend;
    private List<NodeView> nodeViews;

    public String getNodeState() {
        return this.nodeState;
    }

    public void setNodeState(String nodeState) {
        this.nodeState = nodeState;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public UserDO getMyNodeUser() {
        return this.myNodeUser;
    }

    public void setMyNodeUser(UserDO myNodeUser) {
        this.myNodeUser = myNodeUser;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<UserDO> getNodeUser() {
        return this.nodeUser;
    }

    public void setNodeUser(List<UserDO> nodeUser) {
        this.nodeUser = nodeUser;
    }

    public Date getNodeTime() {
        return this.nodeTime;
    }

    public void setNodeTime(Date nodeTime) {
        this.nodeTime = nodeTime;
    }

    public Integer getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getApprovalResult() {
        return this.approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getApprovalComment() {
        return this.approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public Long getNodeHoldingTime() {
        return this.nodeHoldingTime;
    }

    public void setNodeHoldingTime(Long nodeHoldingTime) {
        this.nodeHoldingTime = nodeHoldingTime;
    }

    public List<NodeView> getNodeViews() {
        return this.nodeViews;
    }

    public void setNodeViews(List<NodeView> nodeViews) {
        this.nodeViews = nodeViews;
    }

    public boolean isExpend() {
        return this.expend;
    }

    public void setExpend(boolean expend) {
        this.expend = expend;
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Integer getCommentColor() {
        return this.commentColor;
    }

    public void setCommentColor(Integer commentColor) {
        this.commentColor = commentColor;
    }

    public List<UserDO> getPlusApprovaledUsers() {
        return this.plusApprovaledUsers;
    }

    public void setPlusApprovaledUsers(List<UserDO> plusApprovaledUsers) {
        this.plusApprovaledUsers = plusApprovaledUsers;
    }
}

