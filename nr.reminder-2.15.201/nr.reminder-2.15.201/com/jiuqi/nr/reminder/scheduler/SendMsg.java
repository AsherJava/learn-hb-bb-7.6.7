/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.message.constants.HandleModeEnum
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.message.constants.ParticipantTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  com.jiuqi.va.social.mail.domain.SendMailCustomDTO
 *  com.jiuqi.va.social.mail.service.VaMailService
 */
package com.jiuqi.nr.reminder.scheduler;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.spi.ReminderMessageListener;
import com.jiuqi.nr.reminder.untils.EntityHelper;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import com.jiuqi.va.social.mail.domain.SendMailCustomDTO;
import com.jiuqi.va.social.mail.service.VaMailService;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SendMsg {
    @Autowired
    private MessagePipelineService pipelineService;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    EntityHelper entityHelper;
    @Autowired
    private EntityQueryManager entityQueryManager;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private VaMailService vaMailService;
    @Autowired
    private AuthUserClient userClient;
    @Autowired(required=false)
    private ReminderMessageListener reminderMessageListener;

    public void send(List<Reminder> reminderList, NpContextImpl context) throws Exception {
        if (reminderList == null) {
            throw new Exception("\u6ca1\u6709\u53ef\u50ac\u62a5\u7684\u5bf9\u8c61");
        }
        HashMap<String, List<String>> userMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> ccUserMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> userErrorMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> ccUserErrorMaps = new HashMap<String, List<String>>();
        ArrayList<String> unitIds = new ArrayList<String>();
        ArrayList<String> unitIdsError = new ArrayList<String>();
        for (Reminder reminder : reminderList) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(UUID.randomUUID().toString());
            messageDTO.setType(MessageTypeEnum.TODO.getCode());
            messageDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
            messageDTO.setParticipantType(ParticipantTypeEnum.USER.getCode());
            ArrayList<String> userIdList = new ArrayList<String>();
            for (String userId : reminder.getUserIds()) {
                boolean isSendUser = this.reminderRepository.findUserState(userId);
                if (!isSendUser) continue;
                userIdList.add(userId);
            }
            messageDTO.setParticipants(userIdList);
            messageDTO.setCcParticipantType(ParticipantTypeEnum.USER.getCode());
            messageDTO.setContent(reminder.getContent());
            ArrayList<Integer> mode = new ArrayList<Integer>();
            if (reminder.getHandleMethod() != null) {
                mode.addAll(reminder.getHandleMethod());
            }
            if (reminder.getExecuteTime() != null) {
                Instant instant = reminder.getExecuteTime().atZone(ZoneId.systemDefault()).toInstant();
                messageDTO.setValidTime(instant);
            } else {
                messageDTO.setValidTime(Instant.now());
            }
            if (userIdList.isEmpty()) {
                LogHelper.warn((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u81ea\u52a8\u50ac\u62a5\u53d1\u9001\u90ae\u4ef6\u60c5\u51b5", (String)("\u5355\u4f4d\u3010" + reminder.getUnitId() + "\u3011\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7528\u6237"));
                continue;
            }
            if (this.reminderMessageListener != null) {
                this.reminderMessageListener.sendMessageBefore(reminder, messageDTO);
            }
            List<Object> ccUserIdList = new ArrayList();
            for (Integer modeItem : mode) {
                VaMessageSendDTO dto;
                if (HandleModeEnum.MAIL.getCode().equals(modeItem) || HandleModeEnum.COMPLEX_MAIL.getCode().equals(modeItem)) {
                    SendMailCustomDTO customDTO = new SendMailCustomDTO();
                    customDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
                    customDTO.setContent(reminder.getContent());
                    customDTO.setUsers(this.getUsers(userIdList));
                    if (customDTO.getUsers().isEmpty()) break;
                    List<String> ccParticipants = reminder.getCcParticipants();
                    ccUserIdList = ccParticipants;
                    if (!CollectionUtils.isEmpty(ccParticipants)) {
                        customDTO.setCcUsers(this.getUsers(ccParticipants));
                    }
                    this.vaMailService.sendMessageCustom(customDTO);
                }
                if (HandleModeEnum.SYSTEM.getCode().equals(modeItem)) {
                    dto = new VaMessageSendDTO();
                    dto.setGrouptype("\u901a\u77e5");
                    dto.setMsgtype("\u50ac\u62a5\u901a\u77e5");
                    dto.setReceiveUserIds(userIdList);
                    dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
                    String content = reminder.getContent();
                    String removeTagMsg = SendMsg.removeTag(content);
                    if (removeTagMsg.length() > 200) {
                        removeTagMsg = removeTagMsg.substring(0, 190) + "......";
                    }
                    dto.setContent(content);
                    dto.setTitle(removeTagMsg);
                    this.messageClient.addMsg(dto);
                }
                if (!HandleModeEnum.SHORT_MESSAGE.getCode().equals(modeItem)) continue;
                dto = new VaMessageSendDTO();
                dto.setGrouptype("\u901a\u77e5");
                dto.setMsgtype("\u50ac\u62a5\u901a\u77e5");
                dto.setReceiveUserIds(userIdList);
                dto.setMsgChannel(VaMessageOption.MsgChannel.SMS);
                dto.setContent(reminder.getContent());
                dto.setTitle("\u50ac\u62a5\u901a\u77e5");
                this.messageClient.addMsg(dto);
            }
            mode.clear();
            mode.add(HandleModeEnum.SYSTEM.getCode());
            messageDTO.setHandleMode(mode);
            boolean ifSendOk = this.pipelineService.send(messageDTO);
            if (!ifSendOk) {
                userErrorMaps.put(reminder.getUnitId(), userIdList);
                ccUserErrorMaps.put(reminder.getUnitId(), ccUserIdList);
                unitIdsError.add(reminder.getUnitId());
                continue;
            }
            userMaps.put(reminder.getUnitId(), userIdList);
            ccUserMaps.put(reminder.getUnitId(), ccUserIdList);
            unitIds.add(reminder.getUnitId());
        }
        this.LogSendEmail(reminderList, context, unitIds, unitIdsError, userMaps, ccUserMaps, userErrorMaps, ccUserErrorMaps);
    }

    private List<UserDO> getUsers(List<String> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.emptyList();
        }
        UserDTO userParam = new UserDTO();
        userParam.setUserIds(userIdList.toArray(new String[0]));
        userParam.setLockflag(Integer.valueOf(0));
        userParam.setStopflag(Integer.valueOf(0));
        PageVO userList = this.userClient.list(userParam);
        if (userList == null) {
            return Collections.emptyList();
        }
        if (userList.getRows() == null) {
            return Collections.emptyList();
        }
        return userList.getRows();
    }

    private void LogSendEmail(List<Reminder> reminderList, NpContextImpl context, List<String> unitIds, List<String> unitIdsError, Map<String, List<String>> userMaps, Map<String, List<String>> ccUserMaps, Map<String, List<String>> userErrorMaps, Map<String, List<String>> ccUserErrorMaps) {
        if (reminderList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringErrorBuilder = new StringBuilder();
            FormSchemeDefine formScheme = this.viewController.getFormScheme(reminderList.get(0).getFormSchemeId());
            this.npApplication.runAsContext((NpContext)context, () -> {
                String ccUserName;
                String userName;
                List ccUserIdList;
                List userIdList;
                List allRows;
                IEntityTable entityQuerySet;
                EntityViewDefine entityViewDefine;
                DimensionValueSet buildDimension;
                ArrayList<String> unitIdd;
                if (unitIds == null || unitIds.size() == 0) {
                    stringBuilder.append("\u65e0, ");
                }
                for (String unitId : unitIds) {
                    unitIdd = new ArrayList<String>();
                    unitIdd.add(unitId);
                    buildDimension = this.entityHelper.buildDimension(((Reminder)reminderList.get(0)).getFormSchemeId(), unitIdd);
                    entityViewDefine = this.entityHelper.getParentEntityViewDefine(((Reminder)reminderList.get(0)).getFormSchemeId());
                    entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, ((Reminder)reminderList.get(0)).getFormSchemeId());
                    allRows = entityQuerySet.getAllRows();
                    stringBuilder.append("\u5355\u4f4d\u540d\u79f0\uff1a").append(((IEntityRow)allRows.get(0)).getTitle()).append(", ");
                    userIdList = (List)userMaps.get(unitId);
                    ccUserIdList = (List)ccUserMaps.get(unitId);
                    stringBuilder.append("\u6536\u4ef6\u7528\u6237\u540d\u79f0\uff1a");
                    if (userIdList == null || userIdList.size() == 0) {
                        stringBuilder.append("\u65e0, ");
                    } else {
                        for (String userId : userIdList) {
                            userName = this.reminderRepository.findUserName(userId);
                            stringBuilder.append(userName).append(", ");
                        }
                    }
                    stringBuilder.append("\u6284\u9001\u7528\u6237\u540d\u79f0\uff1a");
                    if (ccUserIdList == null || ccUserIdList.size() == 0) {
                        stringBuilder.append("\u65e0, ");
                        continue;
                    }
                    for (String ccUserId : ccUserIdList) {
                        ccUserName = this.reminderRepository.findUserName(ccUserId);
                        stringBuilder.append(ccUserName).append(", ");
                    }
                }
                if (unitIdsError == null || unitIdsError.size() == 0) {
                    stringErrorBuilder.append("\u65e0, ");
                } else {
                    for (String unitId : unitIdsError) {
                        String userEmail;
                        unitIdd = new ArrayList();
                        unitIdd.add(unitId);
                        buildDimension = this.entityHelper.buildDimension(((Reminder)reminderList.get(0)).getFormSchemeId(), unitIdd);
                        entityViewDefine = this.entityHelper.getParentEntityViewDefine(((Reminder)reminderList.get(0)).getFormSchemeId());
                        entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, ((Reminder)reminderList.get(0)).getFormSchemeId());
                        allRows = entityQuerySet.getAllRows();
                        stringErrorBuilder.append("\u5355\u4f4d\u540d\u79f0\uff1a").append(((IEntityRow)allRows.get(0)).getTitle()).append(", ");
                        userIdList = (List)userErrorMaps.get(unitId);
                        ccUserIdList = (List)ccUserErrorMaps.get(unitId);
                        stringErrorBuilder.append("\u6536\u4ef6\u7528\u6237\u540d\u79f0\uff1a");
                        if (userIdList == null || userIdList.size() == 0) {
                            stringErrorBuilder.append("\u65e0, ");
                        } else {
                            for (String userId : userIdList) {
                                userName = this.reminderRepository.findUserName(userId);
                                stringErrorBuilder.append(userName).append(", ");
                                userEmail = this.reminderRepository.findUserEmail(userId);
                                stringErrorBuilder.append("\u90ae\u7bb1\uff1a ").append(userEmail).append(", ");
                            }
                        }
                        stringErrorBuilder.append("\u6284\u9001\u7528\u6237\u540d\u79f0\uff1a");
                        if (ccUserIdList == null || ccUserIdList.size() == 0) {
                            stringErrorBuilder.append("\u65e0, ");
                            continue;
                        }
                        for (String ccUserId : ccUserIdList) {
                            ccUserName = this.reminderRepository.findUserName(ccUserId);
                            stringErrorBuilder.append(ccUserName).append(", ");
                            userEmail = this.reminderRepository.findUserEmail(ccUserId);
                            stringErrorBuilder.append("\u90ae\u7bb1\uff1a ").append(userEmail).append(", ");
                        }
                    }
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sendTime = formatter.format(new Date());
                LogHelper.info((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u81ea\u52a8\u50ac\u62a5\u53d1\u9001\u90ae\u4ef6\u60c5\u51b5", (String)("\u50ac\u62a5\u65b9\u6848\uff1a " + formScheme.getTitle() + " , \u53d1\u9001\u90ae\u4ef6\u6210\u529f\u7684\u5355\u4f4d: " + stringBuilder + " \u53d1\u9001\u90ae\u4ef6\u5931\u8d25\u7684\u5355\u4f4d: " + stringErrorBuilder + " \u53d1\u4ef6\u65f6\u95f4: " + sendTime), new HashMap());
            });
        }
    }

    public static String removeTag(String html) {
        if (html == null) {
            return "";
        }
        String htmlStr = html;
        String regEx_html = "<[^>]+>";
        Pattern p_html = Pattern.compile(regEx_html, 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr;
    }
}

