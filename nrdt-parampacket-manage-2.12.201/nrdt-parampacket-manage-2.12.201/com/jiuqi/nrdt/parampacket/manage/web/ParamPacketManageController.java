/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.transmission.data.controller.ProcessController
 *  com.jiuqi.nr.transmission.data.controller.TransmissionActionController
 *  com.jiuqi.nr.transmission.data.intf.TransmissionResult
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionState
 *  com.jiuqi.nr.transmission.data.vo.ImportOtherVO
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nrdt.parampacket.manage.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.transmission.data.controller.ProcessController;
import com.jiuqi.nr.transmission.data.controller.TransmissionActionController;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;
import com.jiuqi.nr.transmission.data.monitor.TransmissionState;
import com.jiuqi.nr.transmission.data.vo.ImportOtherVO;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketQuery;
import com.jiuqi.nrdt.parampacket.manage.bean.ResponseObj;
import com.jiuqi.nrdt.parampacket.manage.bean.TreeNode;
import com.jiuqi.nrdt.parampacket.manage.config.ParamPacketSuffixConfig;
import com.jiuqi.nrdt.parampacket.manage.i18n.ParamPacketManageI18nHelper;
import com.jiuqi.nrdt.parampacket.manage.i18n.ParamPacketManageI18nKeys;
import com.jiuqi.nrdt.parampacket.manage.service.IParamPacketManageService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/paramPacket/manage"})
public class ParamPacketManageController {
    @Autowired
    private IParamPacketManageService paramPacketManageService;
    @Autowired
    private TransmissionActionController transmissionActionController;
    @Autowired
    private ParamPacketManageI18nHelper i18nHelper;
    @Autowired
    private ProcessController processController;
    @Autowired
    private ParamPacketSuffixConfig paramPacketSuffixConfig;
    private static Logger logger = LoggerFactory.getLogger(ParamPacketManageController.class);

    @PostMapping(value={"/nvwaPattern/upload"})
    public AsyncTaskInfo importNewPatternData(@RequestParam(value="file") MultipartFile file, @RequestParam(value="importOtherVO") String importOtherVO) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        ImportOtherVO importOther = (ImportOtherVO)mapper.readValue(importOtherVO, ImportOtherVO.class);
        TransmissionResult transmissionResult = this.transmissionActionController.importFile(file, importOther);
        AsyncTaskInfo uploadAsyncInfo = new AsyncTaskInfo();
        uploadAsyncInfo.setId(transmissionResult.getExecuteKey());
        ObjectMapper objectMapper = new ObjectMapper();
        uploadAsyncInfo.setResult(objectMapper.writeValueAsString((Object)transmissionResult.getData()));
        return uploadAsyncInfo;
    }

    @GetMapping(value={"/nvwaPattern/process"})
    public AsyncTaskInfo queryNewPatternProcess(@RequestParam(value="progressId") String progressId) throws JQException {
        TransmissionMonitorInfo transmissionMonitorInfo = this.processController.loadProcess(progressId, 2);
        AsyncTaskInfo uploadAsyncInfo = new AsyncTaskInfo();
        uploadAsyncInfo.setId(transmissionMonitorInfo.getExecuteKey());
        uploadAsyncInfo.setProcess(transmissionMonitorInfo.getProcess());
        if (transmissionMonitorInfo.getStates() == TransmissionState.ERROR.getValue() || transmissionMonitorInfo.getStates() == TransmissionState.SOMESUCCESS.getValue()) {
            uploadAsyncInfo.setState(TaskState.ERROR);
        } else if (transmissionMonitorInfo.getStates() == TransmissionState.FINISHED.getValue()) {
            uploadAsyncInfo.setState(TaskState.FINISHED);
        } else {
            uploadAsyncInfo.setState(TaskState.PROCESSING);
        }
        uploadAsyncInfo.setDetail((Object)transmissionMonitorInfo.getResult());
        uploadAsyncInfo.setResult(transmissionMonitorInfo.getDetail());
        return uploadAsyncInfo;
    }

    @GetMapping(value={"/queryParamPacketByParent"})
    public ResponseObj queryParamPacketByParent(@RequestParam String parent) {
        int i;
        List<ParamPacket> paramPackets = this.paramPacketManageService.queryParamPacketByParent(parent);
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketManageService.queryParamPacketGroupByParent(parent);
        paramPackets.sort(new Comparator<ParamPacket>(){

            @Override
            public int compare(ParamPacket o1, ParamPacket o2) {
                int flag = o1.getTitle().compareTo(o2.getTitle());
                if (flag == 0) {
                    flag = o1.getCode().compareTo(o2.getCode());
                }
                return flag;
            }
        });
        paramPacketGroups.sort(new Comparator<ParamPacketGroup>(){

            @Override
            public int compare(ParamPacketGroup o1, ParamPacketGroup o2) {
                int flag = o1.getTitle().compareTo(o2.getTitle());
                return flag;
            }
        });
        ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (i = 0; i < paramPacketGroups.size(); ++i) {
            treeNodes.add(TreeNode.convertToTreeNode(paramPacketGroups.get(i)));
        }
        for (i = 0; i < paramPackets.size(); ++i) {
            treeNodes.add(TreeNode.convertToTreeNode(paramPackets.get(i)));
        }
        return ResponseObj.SUCCESS(treeNodes, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_QUERY.key, ParamPacketManageI18nKeys.SUCCESS_QUERY.title));
    }

    @GetMapping(value={"/queryParamPacket"})
    public ParamPacket queryParamPacket(@RequestParam(value="guid") String guid) {
        return this.paramPacketManageService.queryParanPacket(guid);
    }

    @GetMapping(value={"/queryParamPacketGroup"})
    public ResponseObj queryParamPacketGroup(@RequestParam String parent) {
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketManageService.queryParamPacketGroupByParent(parent);
        ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (int i = 0; i < paramPacketGroups.size(); ++i) {
            treeNodes.add(TreeNode.convertToTreeNode(paramPacketGroups.get(i)));
        }
        treeNodes.sort(new Comparator<TreeNode>(){

            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                int flag = o1.getTitle().compareTo(o2.getTitle());
                if (flag == 0) {
                    flag = o1.getCode().compareTo(o2.getCode());
                }
                return flag;
            }
        });
        return ResponseObj.SUCCESS(treeNodes, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_QUERY_GROUP.key, ParamPacketManageI18nKeys.SUCCESS_QUERY_GROUP.title));
    }

    @PostMapping(value={"/addParamPacket"})
    public ResponseObj addParamPacket(@Valid @RequestBody ParamPacket paramPacket) {
        ParamPacket packetByCode = this.paramPacketManageService.queryParamPacketByCode(paramPacket.getCode());
        if (packetByCode != null) {
            return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_EXIST_SAME_CODE.key, ParamPacketManageI18nKeys.ERROR_EXIST_SAME_CODE.title));
        }
        ParamPacket addParamPacket = this.paramPacketManageService.addParamPacket(paramPacket);
        return ResponseObj.SUCCESS(addParamPacket, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_ADD_PARAMPACKET.key, ParamPacketManageI18nKeys.SUCCESS_ADD_PARAMPACKET.title));
    }

    @PostMapping(value={"/updateParamPacket"})
    public ResponseObj updateParamPacket(@Valid @RequestBody ParamPacket paramPacket) {
        if (StringUtils.isEmpty((String)paramPacket.getGuid())) {
            return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_EMPTY_GUID.key, ParamPacketManageI18nKeys.ERROR_EMPTY_GUID.title));
        }
        ParamPacket updateParamPacket = this.paramPacketManageService.updateParamPacket(paramPacket);
        return ResponseObj.SUCCESS(updateParamPacket, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_UPDATE_PARAMPACKET.key, ParamPacketManageI18nKeys.SUCCESS_UPDATE_PARAMPACKET.title));
    }

    @GetMapping(value={"/deleteParamPacket"})
    public ResponseObj deleteParamPacket(@RequestParam String guid) {
        boolean isDeleted = this.paramPacketManageService.deleteParamPacket(guid);
        if (isDeleted) {
            return ResponseObj.SUCCESS("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_DELETE.key, ParamPacketManageI18nKeys.SUCCESS_DELETE.title));
        }
        return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_DELETE.key, ParamPacketManageI18nKeys.ERROR_DELETE.title));
    }

    @GetMapping(value={"/queryParamPacketGroupByParent"})
    public ResponseObj queryParamPacketGroupByParent(@RequestParam String parent) {
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketManageService.queryParamPacketGroupByParent(parent);
        return ResponseObj.SUCCESS(paramPacketGroups, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_QUERY.key, ParamPacketManageI18nKeys.SUCCESS_QUERY.title));
    }

    @PostMapping(value={"/addParamPacketGroup"})
    public ResponseObj addParamPacketGroup(@Valid @RequestBody ParamPacketGroup paramPacketGroup) {
        Optional<ParamPacketGroup> optional;
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketManageService.queryParamPacketGroupByParent(paramPacketGroup.getParent());
        if (!CollectionUtils.isEmpty(paramPacketGroups) && (optional = paramPacketGroups.stream().filter(group -> group.getTitle().equals(paramPacketGroup.getTitle())).findFirst()).isPresent()) {
            return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_SAME_GROUP_TITLE.key, ParamPacketManageI18nKeys.ERROR_SAME_GROUP_TITLE.title));
        }
        ParamPacketGroup addParamPacketGroup = this.paramPacketManageService.addParamPacketGroup(paramPacketGroup);
        return ResponseObj.SUCCESS(addParamPacketGroup, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_ADD_GROUP.key, ParamPacketManageI18nKeys.SUCCESS_ADD_GROUP.title));
    }

    @PostMapping(value={"/updateParamPacketGroup"})
    public ResponseObj updateParamPacketGroup(@Valid @RequestBody ParamPacketGroup paramPacketGroup) {
        Optional<ParamPacketGroup> optional;
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketManageService.queryParamPacketGroupByParent(paramPacketGroup.getParent());
        if (!CollectionUtils.isEmpty(paramPacketGroups) && (optional = paramPacketGroups.stream().filter(group -> group.getTitle().equals(paramPacketGroup.getTitle())).findFirst()).isPresent()) {
            return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_SAME_GROUP_TITLE.key, ParamPacketManageI18nKeys.ERROR_SAME_GROUP_TITLE.title));
        }
        ParamPacketGroup updateParamPacketGroup = this.paramPacketManageService.updateParamPacketGroup(paramPacketGroup);
        return ResponseObj.SUCCESS(updateParamPacketGroup, this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_ADD_GROUP.key, ParamPacketManageI18nKeys.SUCCESS_ADD_GROUP.title));
    }

    @GetMapping(value={"/deleteParamPacketGroup"})
    public ResponseObj deleteParamPacketGroup(@RequestParam String guid) {
        boolean isDeleted = this.paramPacketManageService.deleteParamPacketGroup(guid);
        if (isDeleted) {
            return ResponseObj.SUCCESS("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_DELETE_GROUP.key, ParamPacketManageI18nKeys.SUCCESS_DELETE_GROUP.title));
        }
        return ResponseObj.FAIL("", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_DELETE_GROUP.key, ParamPacketManageI18nKeys.ERROR_DELETE_GROUP.title));
    }

    @PostMapping(value={"/saveResource/{guid}/{needPacked}"})
    public AsyncTaskInfo saveResource(@RequestParam(value="resources") String nodeItems, @PathVariable(value="guid") String guid, @PathVariable(value="needPacked") Boolean needPacked) {
        AsyncTaskInfo asyncTaskInfo = this.paramPacketManageService.saveResource(nodeItems, guid, needPacked);
        return asyncTaskInfo;
    }

    @PostMapping(value={"/packedParamPacket/{guid}"})
    public AsyncTaskInfo packedParamPacket(@PathVariable(value="guid") String guid) {
        AsyncTaskInfo asyncTaskInfo = this.paramPacketManageService.packedParamPacket(guid);
        return asyncTaskInfo;
    }

    @GetMapping(value={"/downloadParamPacket"})
    String downloadParamPacket(String fileKey) throws UnsupportedEncodingException {
        byte[] bytes = (byte[])this.paramPacketManageService.downloadParamPacket(fileKey);
        return new String(bytes, "ISO-8859-1");
    }

    @PostMapping(value={"/exportParamPacket"})
    void exportParamPacket(@RequestParam(value="fileKey") String fileKey, HttpServletResponse resp) throws IOException {
        String fileName = "\u7cfb\u7edf\u8d44\u6e90" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String suffixName = this.paramPacketSuffixConfig.getSuffixName();
        resp.setContentType("application/octet-stream;charset=utf-8");
        resp.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            resp.setHeader("Content-Disposition", HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + URLEncoder.encode(new String(fileName.getBytes("UTF-8"), "iso-8859-1"), "iso-8859-1") + suffixName)));
        }
        catch (UnsupportedEncodingException e) {
            resp.setHeader("Content-Disposition", HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + fileName + suffixName)));
        }
        byte[] bytes = (byte[])this.paramPacketManageService.downloadParamPacket(fileKey);
        resp.getOutputStream().write(bytes);
        resp.getOutputStream().flush();
    }

    @GetMapping(value={"/queryParamPacketsByUser"})
    List<ParamPacket> queryParamPacketsByUser(@RequestParam(value="isEnabled") boolean isEnabled) {
        return this.paramPacketManageService.queryParamPacketByUser(isEnabled);
    }

    @GetMapping(value={"/getTskTransferFunctionCategoryId"})
    ResponseObj getTskTransferFunctionCategoryId() {
        return ResponseObj.SUCCESS("taskFunctionCategroy", this.i18nHelper.getMessage(ParamPacketManageI18nKeys.SUCCESS_QUERY.key, ParamPacketManageI18nKeys.SUCCESS_QUERY.title));
    }

    @GetMapping(value={"/updateParamPacketSuffix"})
    public Map<String, String> updateParamPacketSuffix(@RequestParam String suffixName) {
        this.paramPacketSuffixConfig.setSuffixName(suffixName);
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("data", "");
        res.put("message", "\u8bbe\u7f6e\u53c2\u6570\u5305\u540e\u7f00\u540d\u4e3a\uff1a" + suffixName);
        return res;
    }

    @PostMapping(value={"/resourceTree"})
    ResponseObj getResource(@Valid @RequestBody ParamPacketQuery paramPacketQuery) throws Exception {
        return this.paramPacketManageService.getResourcetree(paramPacketQuery);
    }
}

