/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.querytable.intf.QueryTable
 *  com.jiuqi.va.biz.querytable.intf.QueryTableParamItem
 *  com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO
 *  com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO
 *  com.jiuqi.va.content.feign.client.VaSignatureFeignClient
 *  com.jiuqi.va.content.feign.domain.SignatureInfoDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessInfo
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.querytable.intf.QueryTable;
import com.jiuqi.va.biz.querytable.intf.QueryTableParamItem;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO;
import com.jiuqi.va.content.feign.client.VaSignatureFeignClient;
import com.jiuqi.va.content.feign.domain.SignatureInfoDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowProcessInfo;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class WorkflowProcessInfoQueryTable
implements QueryTable {
    private static final String QUERYTABLENAME = "workflowProcessInfo-table";
    private static final String QUERYTABLETITLE = "\u6d41\u7a0b\u4fe1\u606f\u9ed8\u8ba4\u5b57\u6bb5";
    @Autowired
    private WorkflowServerClient workflowServerClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private VaSignatureFeignClient signatureFeignClient;

    public String getName() {
        return QUERYTABLENAME;
    }

    public String getTitle() {
        return QUERYTABLETITLE;
    }

    public List<QueryTableColumnVO> getColumns() {
        ArrayList<QueryTableColumnVO> columns = new ArrayList<QueryTableColumnVO>();
        columns.add(new QueryTableColumnVO("nodeNameArea", "\u8282\u70b9\u540d\u79f0\u533a\u57df"));
        columns.add(new QueryTableColumnVO("completecomment", "\u5ba1\u6279\u610f\u89c1"));
        columns.add(new QueryTableColumnVO("signatureArea", "\u7b7e\u540d\u533a\u57df"));
        columns.add(new QueryTableColumnVO("completetime", "\u5ba1\u6279\u65f6\u95f4"));
        return columns;
    }

    public List<QueryTableParamItem> getQueryParams() {
        return null;
    }

    public List<QueryTableDataDTO> getQueryTableDataDTO(Map<String, Object> params) {
        TenantDO tenantDO = (TenantDO)params.get("tenantDO");
        R processInfo = this.workflowServerClient.getProcessInfo(tenantDO);
        List info = (List)processInfo.get((Object)"info");
        if (CollectionUtils.isEmpty(info)) {
            return new ArrayList<QueryTableDataDTO>();
        }
        List<WorkflowProcessInfo> infoList = info.stream().map(o -> (WorkflowProcessInfo)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)o), WorkflowProcessInfo.class)).collect(Collectors.toList());
        boolean isCheckAllNodes = (Boolean)tenantDO.getExtInfo("isCheckAllNodes");
        List selectNodes = (List)tenantDO.getExtInfo("selectNodes");
        List selectFields = (List)tenantDO.getExtInfo("selectFields");
        HashMap<String, UserDO> userMap = new HashMap<String, UserDO>();
        QueryTableDataDTO queryTableDataDTO = new QueryTableDataDTO();
        HashMap<String, String> rowData = new HashMap<String, String>();
        List<Map<String, Object>> infoMap = this.filterWorkflowProcessInfo(infoList, selectNodes, selectFields, userMap, isCheckAllNodes);
        rowData.put("rowData", JSONUtil.toJSONString(infoMap));
        queryTableDataDTO.setRowData(rowData);
        ArrayList<QueryTableDataDTO> result = new ArrayList<QueryTableDataDTO>();
        result.add(queryTableDataDTO);
        return result;
    }

    private List<Map<String, Object>> filterWorkflowProcessInfo(List<WorkflowProcessInfo> info, List<String> selectNodes, List<String> selectFields, Map<String, UserDO> userMap, boolean isCheckAllNodes) {
        ArrayList<Map<String, Object>> infoMap = new ArrayList<Map<String, Object>>();
        for (WorkflowProcessInfo workflowProcessInfo : info) {
            List<Map<String, Object>> approvaInfos;
            if ("\u6d41\u7a0b\u7ed3\u675f".equals(workflowProcessInfo.getNodeNameArea()) || !isCheckAllNodes && !selectNodes.contains(workflowProcessInfo.getNodeCode())) continue;
            HashMap<String, Object> nodeInfo = new HashMap<String, Object>();
            nodeInfo.put("nodeNameArea", workflowProcessInfo.getNodeNameArea());
            nodeInfo.put("submitFlag", workflowProcessInfo.isSubmitFlag());
            nodeInfo.put("counterSignFlag", workflowProcessInfo.isCounterSignFlag());
            if (!CollectionUtils.isEmpty(workflowProcessInfo.getApprovaInfo())) {
                approvaInfos = this.getfilterInfos(selectFields, userMap, workflowProcessInfo.getApprovaInfo(), workflowProcessInfo.isRejectState());
                nodeInfo.put("approvaInfo", approvaInfos);
            }
            if (!CollectionUtils.isEmpty(workflowProcessInfo.getDelegateInfo())) {
                approvaInfos = this.getfilterInfos(selectFields, userMap, workflowProcessInfo.getDelegateInfo(), workflowProcessInfo.isRejectState());
                nodeInfo.put("delegateInfo", approvaInfos);
            }
            if (!CollectionUtils.isEmpty(workflowProcessInfo.getPlusApprovaInfo())) {
                approvaInfos = this.getfilterInfos(selectFields, userMap, workflowProcessInfo.getPlusApprovaInfo(), workflowProcessInfo.isRejectState());
                nodeInfo.put("plusApprovaInfo", approvaInfos);
            }
            if (!CollectionUtils.isEmpty(workflowProcessInfo.getTodoTransferInfo())) {
                approvaInfos = this.getfilterInfos(selectFields, userMap, workflowProcessInfo.getTodoTransferInfo(), workflowProcessInfo.isRejectState());
                nodeInfo.put("todoTransferInfo", approvaInfos);
            }
            if (!CollectionUtils.isEmpty(workflowProcessInfo.getGatewayInfo())) {
                ArrayList<List<Map<String, Object>>> gatewayInfos = new ArrayList<List<Map<String, Object>>>();
                List gatewayInfo = workflowProcessInfo.getGatewayInfo();
                for (List infos : gatewayInfo) {
                    List<Map<String, Object>> maps = this.filterWorkflowProcessInfo(infos, selectNodes, selectFields, userMap, isCheckAllNodes);
                    gatewayInfos.add(maps);
                }
                nodeInfo.put("gatewayInfo", gatewayInfos);
            }
            if (nodeInfo.size() == 1) continue;
            infoMap.add(nodeInfo);
        }
        return infoMap;
    }

    private List<Map<String, Object>> getfilterInfos(List<String> selectFields, Map<String, UserDO> userMap, List<ProcessNodeDO> processNodeDOS, boolean rejectState) {
        ArrayList<Map<String, Object>> approvaInfos = new ArrayList<Map<String, Object>>();
        for (ProcessNodeDO processNodeDO : processNodeDOS) {
            HashMap<String, Object> approvaInfo = new HashMap<String, Object>();
            Map processNodeMap = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)processNodeDO));
            if (!ObjectUtils.isEmpty(processNodeMap.get("receivetime"))) {
                processNodeMap.put("receivetime", BillUtils.dateStrConvert(String.valueOf(processNodeMap.get("receivetime")), "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss"));
            }
            if (!ObjectUtils.isEmpty(processNodeMap.get("completetime"))) {
                processNodeMap.put("completetime", BillUtils.dateStrConvert(String.valueOf(processNodeMap.get("completetime")), "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss"));
            }
            for (String selectField : selectFields) {
                if ("signatureArea".equals(selectField)) {
                    UserDO nodeUserInfo = this.getNodeUserInfo(processNodeDO, userMap);
                    if (nodeUserInfo == null) {
                        approvaInfo.put("signatureArea", null);
                        approvaInfo.put("pictureFlag", false);
                        continue;
                    }
                    SignatureInfoDTO signatureInfoDTO = new SignatureInfoDTO();
                    signatureInfoDTO.setSigowner(UUID.fromString(nodeUserInfo.getId()));
                    R exist = this.signatureFeignClient.exist(signatureInfoDTO);
                    if (exist.getCode() == 0 && ((Boolean)exist.get((Object)"exist")).booleanValue()) {
                        approvaInfo.put("signatureArea", UUID.fromString(nodeUserInfo.getId()));
                        approvaInfo.put("pictureFlag", true);
                        continue;
                    }
                    approvaInfo.put("signatureArea", nodeUserInfo.getName());
                    approvaInfo.put("pictureFlag", false);
                    continue;
                }
                approvaInfo.put(selectField, processNodeMap.get(selectField));
            }
            if (rejectState) {
                approvaInfo.put("commentColor", 0);
            } else {
                approvaInfo.put("commentColor", 1);
            }
            approvaInfo.put("orderNum", processNodeDO.getCompletetime().getTime());
            approvaInfos.add(approvaInfo);
        }
        return approvaInfos;
    }

    private UserDO getNodeUserInfo(ProcessNodeDO processNode, Map<String, UserDO> userMap) {
        UserDO userDO = null;
        if (processNode.getCompleteuserid() != null) {
            userDO = userMap.get(processNode.getCompleteuserid());
            if (userDO == null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(processNode.getCompleteuserid());
                userDO = this.authUserClient.get(userDTO);
                if (userDO != null) {
                    userMap.put(userDO.getId(), userDO);
                }
            }
            if (userDO == null && processNode.getCompleteusername() != null) {
                userDO = new UserDO();
                userDO.setName(processNode.getCompleteusername());
            }
        } else if (processNode.getCompleteusercode() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(processNode.getCompleteusercode());
            userDO = this.authUserClient.get(userDTO);
            if (userDO == null && processNode.getCompleteusername() != null) {
                userDO = new UserDO();
                userDO.setName(processNode.getCompleteusername());
            }
        } else if (processNode.getCompleteusername() != null) {
            userDO = new UserDO();
            userDO.setName(processNode.getCompleteusername());
        }
        return userDO;
    }
}

