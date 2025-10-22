/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.constants.HistoryStatusEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.np.message.constants.HistoryStatusEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.nr.message.manager.MessageBeanUtils;
import com.jiuqi.nr.message.manager.MessageMgrService;
import com.jiuqi.nr.message.manager.ParticipantHelper;
import com.jiuqi.nr.message.manager.internal.MessageMainDao;
import com.jiuqi.nr.message.manager.internal.MessageStatusDao;
import com.jiuqi.nr.message.manager.internal.ParticipantDao;
import com.jiuqi.nr.message.manager.pojo.MessageIdsDTO;
import com.jiuqi.nr.message.manager.pojo.MessageMainPO;
import com.jiuqi.nr.message.manager.pojo.MessageStatusPO;
import com.jiuqi.nr.message.manager.pojo.MessageVO;
import com.jiuqi.nr.message.manager.pojo.PageDTO;
import com.jiuqi.nr.message.manager.pojo.PagingQueryVO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class MessageMgrServiceImpl
implements MessageMgrService {
    private static final Logger logger = LoggerFactory.getLogger(MessageMgrServiceImpl.class);
    private final MessagePipelineService messagePipelineService;
    private final MessageMainDao messageMainDao;
    private final ParticipantDao participantDao;
    private final MessageStatusDao messageStatusDao;
    private final ParticipantHelper participantHelper;

    public MessageMgrServiceImpl(MessagePipelineService messagePipelineService, MessageMainDao messageMainDao, ParticipantDao participantDao, MessageStatusDao messageStatusDao, ParticipantHelper participantHelper) {
        this.messagePipelineService = messagePipelineService;
        this.messageMainDao = messageMainDao;
        this.participantDao = participantDao;
        this.messageStatusDao = messageStatusDao;
        this.participantHelper = participantHelper;
    }

    @Override
    @Transactional
    public boolean push(MessageDTO messageDTO) {
        return this.messagePipelineService.send(messageDTO);
    }

    @Override
    @Transactional
    public PagingQueryVO getUnread(String userId, int currentPage, int pageSize) {
        List<String> messageIds = this.getAllMessageIds(userId);
        List<String> readMessageIds = this.messageStatusDao.findMessageIdByUserId(userId);
        messageIds = messageIds.stream().filter(e -> !readMessageIds.contains(e)).collect(Collectors.toList());
        int totalRows = messageIds.size();
        return this.buildPagingQueryVO(totalRows, currentPage, pageSize, messageIds);
    }

    private List<String> getAllMessageIds(String userId) {
        Timestamp currentTime = Timestamp.from(Instant.now());
        List<String> participantIds = this.participantHelper.collectRelatedParticipantId(userId);
        List<String> messageIds = this.participantDao.findMsgIdByParticipantIdAndValidTimeAndInvalidTime(participantIds, currentTime, currentTime);
        return messageIds.stream().distinct().collect(Collectors.toList());
    }

    private List<String> getAllMessageIdByType(String userId, int type) {
        Timestamp currentTime = Timestamp.from(Instant.now());
        List<String> participantIds = this.participantHelper.collectRelatedParticipantId(userId);
        List<String> messageIds = this.participantDao.findMsgIdByParticipantIdAndType(participantIds, currentTime, currentTime, type);
        return messageIds.stream().distinct().collect(Collectors.toList());
    }

    private List<String> getAllMessageId(String userId) {
        Timestamp currentTime = Timestamp.from(Instant.now());
        List<String> participantIds = this.participantHelper.collectRelatedParticipantId(userId);
        List<String> messageIds = this.participantDao.findMsgIdByParticipantId(participantIds, currentTime, currentTime);
        return messageIds.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagingQueryVO getHistory(String userId, int currentPage, int pageSize, int historyStatus) {
        List<String> allMessageIds = this.messageStatusDao.findMessageIdByUserIdAndStatus(userId, historyStatus, new PageDTO(currentPage, pageSize));
        return this.buildPagingQueryVO(allMessageIds.size(), currentPage, pageSize, allMessageIds);
    }

    private PagingQueryVO buildPagingQueryVO(int totalRows, int currentPage, int pageSize, List<String> messageIdList) {
        LinkedList<MessageVO> messageVOList = new LinkedList<MessageVO>();
        if (messageIdList != null && !messageIdList.isEmpty()) {
            List<MessageMainPO> messageMainPOList = this.messageMainDao.findByIds(messageIdList, currentPage, pageSize);
            for (MessageMainPO messageMainPO : messageMainPOList) {
                String param;
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(messageMainPO, messageVO);
                if (!StringUtils.isEmpty(messageMainPO.getParam()) && (param = messageMainPO.getParam()) != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(param);
                        messageVO.setParam(jsonObject.toMap());
                    }
                    catch (JSONException jSONException) {
                        // empty catch block
                    }
                }
                messageVO.setValidTime(messageMainPO.getValidTime().toInstant());
                messageVO.setId(messageMainPO.getMsgId());
                messageVOList.add(messageVO);
            }
            totalRows = messageMainPOList.size();
        }
        PagingQueryVO pagingQueryVO = new PagingQueryVO(totalRows, currentPage, pageSize);
        pagingQueryVO.setListVO(messageVOList);
        return pagingQueryVO;
    }

    @Override
    @Transactional
    public MessageDTO readUnread(String messageId, String userId) {
        MessageDTO messageDTO = null;
        Optional<MessageMainPO> messageMainPO = this.messageMainDao.findById(messageId);
        if (messageMainPO.isPresent()) {
            messageDTO = new MessageDTO();
            MessageBeanUtils.copyProperties(messageMainPO.get(), messageDTO);
            MessageStatusPO messageStatusPO = new MessageStatusPO();
            messageStatusPO.setMsgId(messageId);
            messageStatusPO.setUserId(userId);
            messageStatusPO.setStatus(HistoryStatusEnum.READ.getCode());
            this.messageStatusDao.save(messageStatusPO);
        }
        return messageDTO;
    }

    @Override
    @Transactional
    public MessageDTO readRead(String messageId) {
        Optional<MessageMainPO> messageMainPO = this.messageMainDao.findById(messageId);
        MessageDTO messageDTO = null;
        if (messageMainPO.isPresent()) {
            messageDTO = new MessageDTO();
            MessageBeanUtils.copyProperties(messageMainPO.get(), messageDTO);
        }
        return messageDTO;
    }

    @Override
    @Transactional
    public boolean unReadToHistory(String userId, List<String> msgIds, int status) {
        this.messageStatusDao.saveOrUpdate(userId, msgIds, status);
        return true;
    }

    @Override
    @Transactional
    public boolean updateHistoryStatus(MessageIdsDTO ids, int status) {
        try {
            this.messageStatusDao.updateStatus(ids.getUserId(), ids.getMessageIds(), status);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public List<String> getReadInfo(String msgId) {
        return this.messageStatusDao.findUserIdByMessageId(msgId);
    }

    @Override
    @Transactional
    public boolean revoke(String msgId) {
        try {
            this.messageMainDao.deleteById(msgId);
            this.participantDao.deleteById(msgId);
            this.messageStatusDao.deleteById(msgId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public PagingQueryVO getUnread(String userId, int currentPage, int pageSize, int type) {
        List<Object> messageIds = new ArrayList();
        messageIds = type != -1 ? this.getAllMessageIdByType(userId, type) : this.getAllMessageId(userId);
        List<String> readMessageIds = this.messageStatusDao.findMessageIdByUserId(userId);
        messageIds = messageIds.stream().filter(e -> !readMessageIds.contains(e)).collect(Collectors.toList());
        int totalRows = messageIds.size();
        return this.buildPagingQueryVO(totalRows, currentPage, pageSize, messageIds, type);
    }

    @Override
    public PagingQueryVO getHistory(String userId, int currentPage, int pageSize, int historyStatus, int type) {
        List<String> allMessageIds = this.messageStatusDao.findMessageIdByUserIdAndStatus(userId, historyStatus, new PageDTO(currentPage, pageSize));
        return this.buildPagingQueryVO(allMessageIds.size(), currentPage, pageSize, allMessageIds, type);
    }

    private PagingQueryVO buildPagingQueryVO(int totalRows, int currentPage, int pageSize, List<String> messageIdList, int type) {
        LinkedList<MessageVO> messageVOList = new LinkedList<MessageVO>();
        if (messageIdList != null && !messageIdList.isEmpty()) {
            List<MessageMainPO> messageMainPOList = this.messageMainDao.findByIds(messageIdList, currentPage, pageSize);
            for (MessageMainPO messageMainPO : messageMainPOList) {
                String param;
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(messageMainPO, messageVO);
                if (!StringUtils.isEmpty(messageMainPO.getParam()) && (param = messageMainPO.getParam()) != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(param);
                        messageVO.setParam(jsonObject.toMap());
                    }
                    catch (JSONException jSONException) {
                        // empty catch block
                    }
                }
                messageVO.setValidTime(messageMainPO.getValidTime().toInstant());
                messageVO.setId(messageMainPO.getMsgId());
                messageVOList.add(messageVO);
            }
        }
        PagingQueryVO pagingQueryVO = new PagingQueryVO(totalRows, currentPage, pageSize);
        pagingQueryVO.setListVO(messageVOList);
        return pagingQueryVO;
    }
}

