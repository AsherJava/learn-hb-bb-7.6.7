/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.service.VaMessageService
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.service.VaMessageService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportDataSyncMessageUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncMessageUtils.class);

    private static void sendMessage(String msgType, String content, List<String> userNames) {
        if (CollectionUtils.isEmpty(userNames)) {
            return;
        }
        VaMessageService messageService = (VaMessageService)SpringContextUtils.getBean(VaMessageService.class);
        NvwaUserClient userClient = (NvwaUserClient)SpringContextUtils.getBean(NvwaUserClient.class);
        List userIds = userNames.stream().map(userName -> {
            UserDTO user = userClient.findByUsername(userName);
            if (user == null) {
                return null;
            }
            return user.getId();
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setReceiveUserIds(userIds);
        dto.setContent(content);
        dto.setMsgChannel(VaMessageOption.MsgChannel.AUTO);
        dto.setCreatetime(new Date());
        dto.setCreateuser(NpContextHolder.getContext().getUserName());
        dto.setTitle(msgType);
        dto.setGrouptype("\u901a\u77e5");
        dto.setMsgtype(msgType);
        messageService.addMsg(dto);
    }

    public static void sendParamRecieveMessage(ReportDataSyncReceiveTaskVO receiveTaskVO) {
        List<ReportDataSyncServerInfoVO> serverList = ((ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class)).listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverList)) {
            return;
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
        List<String> userIds = Arrays.asList(ConverterUtils.getAsString((Object)serverInfoVO.getContactIds(), (String)"").split(","));
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("\u60a8\u6709\u4e00\u6761\u53c2\u6570\u5305\u4e0b\u53d1\u6d88\u606f\uff0c\u8bf7\u67e5\u6536: \n");
        msgBuilder.append("\u53c2\u6570\u5305\u540d\u79f0\uff1a").append(receiveTaskVO.getTaskTitle()).append("\n");
        String version = null == receiveTaskVO.getSyncVersion() ? "--" : receiveTaskVO.getSyncVersion();
        msgBuilder.append("\u53c2\u6570\u5305\u7248\u672c\u53f7\uff1a").append(version).append("\n");
        msgBuilder.append("\u8bf7\u5728\u3010\u53c2\u6570\u66f4\u65b0\u7ba1\u7406\u3011\u4e2d\u67e5\u770b\u5e76\u53ca\u65f6\u66f4\u65b0\u3002\n");
        ReportDataSyncMessageUtils.sendMessage("\u53c2\u6570\u4e0b\u53d1", msgBuilder.toString(), userIds);
    }

    public static void sendRejectMessage(ReportDataSyncUploadDataTaskVO taskVO) {
        List<ReportDataSyncServerInfoVO> serverList = ((ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class)).listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverList)) {
            return;
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
        List<String> userNames = Arrays.asList(ConverterUtils.getAsString((Object)serverInfoVO.getContactIds(), (String)"").split(","));
        if (CollectionUtils.isEmpty(userNames)) {
            return;
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("\u60a8\u6709\u4e00\u6761\u9000\u56de\u6d88\u606f\uff0c\u8bf7\u67e5\u6536: \n");
        msgBuilder.append("\u9000\u56de\u4efb\u52a1\uff1a").append(taskVO.getTaskTitle()).append("\n");
        msgBuilder.append("\u9000\u56de\u65f6\u671f\uff1a").append(taskVO.getPeriodStr()).append("\n");
        msgBuilder.append("\u53ef\u8fdb\u5165\u3010\u6570\u636e\u4e0a\u4f20\u3011\u67e5\u770b\u5177\u4f53\u9000\u56de\u8bf4\u660e\u3002\n");
        ReportDataSyncMessageUtils.sendMessage("\u6570\u636e\u9000\u56de", msgBuilder.toString(), userNames);
    }

    public static void sendUploadDataMessage(ReportDataSyncUploadDataTaskVO taskVO) {
        ReportDataSyncServerListService serverListService = (ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class);
        ReportDataSyncServerInfoVO serverInfoVO = serverListService.queryServerInfoByOrgCode(taskVO.getOrgCode());
        if (serverInfoVO == null) {
            return;
        }
        List<String> userNames = Arrays.asList(ConverterUtils.getAsString((Object)serverInfoVO.getManageUserIds(), (String)"").split(","));
        if (CollectionUtils.isEmpty(userNames)) {
            return;
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("\u6536\u5230\u4e00\u6761\u5355\u4f4d\u6570\u636e\u4e0a\u62a5\u4fe1\u606f: \n");
        msgBuilder.append("\u5355\u4f4d\uff1a").append(taskVO.getOrgTitle()).append("\n");
        msgBuilder.append("\u4efb\u52a1\uff1a").append(taskVO.getTaskTitle()).append("\n");
        msgBuilder.append("\u65f6\u671f\uff1a").append(taskVO.getPeriodStr()).append("\n");
        msgBuilder.append("\u53ef\u8fdb\u5165\u3010\u4e0a\u4f20\u6570\u636e\u7ba1\u7406\u3011\u67e5\u770b\u63a5\u6536\u72b6\u6001\u3002\n");
        ReportDataSyncMessageUtils.sendMessage("\u6570\u636e\u4e0a\u62a5", msgBuilder.toString(), userNames);
    }

    public static void sendUrgeUploadMessage(ReportDataSyncUploadDataTaskVO taskVO) {
        List<ReportDataSyncServerInfoVO> serverList = ((ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class)).listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverList)) {
            return;
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
        List<String> userNames = Arrays.asList(ConverterUtils.getAsString((Object)serverInfoVO.getContactIds(), (String)"").split(","));
        if (CollectionUtils.isEmpty(userNames)) {
            return;
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("\u6536\u5230\u4e00\u6761\u6570\u636e\u50ac\u62a5\u4fe1\u606f: \n");
        msgBuilder.append("\u5355\u4f4d\uff1a").append(taskVO.getOrgTitle()).append("\n");
        msgBuilder.append("\u50ac\u62a5\u4efb\u52a1\uff1a").append(taskVO.getTaskTitle()).append("\n");
        msgBuilder.append("\u50ac\u62a5\u65f6\u671f\uff1a").append(taskVO.getPeriodStr()).append("\n");
        msgBuilder.append("\u8bf7\u53ca\u65f6\u5728\u3010\u6570\u636e\u4e0a\u4f20\u3011\u4e2d\u8fdb\u884c\u6570\u636e\u4e0a\u62a5\u5de5\u4f5c\u3002\n");
        ReportDataSyncMessageUtils.sendMessage("\u6570\u636e\u50ac\u62a5", msgBuilder.toString(), userNames);
    }
}

