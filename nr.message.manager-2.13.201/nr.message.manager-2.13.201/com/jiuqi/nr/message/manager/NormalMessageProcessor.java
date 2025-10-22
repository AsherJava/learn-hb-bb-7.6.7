/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.message.constants.HandleModeEnum
 *  com.jiuqi.np.message.constants.ParticipantTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.task.AbstractMessageProcessor
 */
package com.jiuqi.nr.message.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.task.AbstractMessageProcessor;
import com.jiuqi.nr.message.manager.Constants;
import com.jiuqi.nr.message.manager.MessageBeanUtils;
import com.jiuqi.nr.message.manager.internal.MessageMainDao;
import com.jiuqi.nr.message.manager.internal.ParticipantDao;
import com.jiuqi.nr.message.manager.pojo.MessageMainPO;
import com.jiuqi.nr.message.manager.pojo.ParticipantPO;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NormalMessageProcessor
extends AbstractMessageProcessor {
    public static final Logger logger = LoggerFactory.getLogger(NormalMessageProcessor.class.getName());
    private final MessageMainDao messageMainDao;
    private final ParticipantDao participantDao;

    public NormalMessageProcessor(MessageMainDao messageMainDao, ParticipantDao participantDao) {
        this.messageMainDao = messageMainDao;
        this.participantDao = participantDao;
    }

    protected boolean support(MessageDTO messageDTO) {
        return messageDTO.getHandleMode().contains(HandleModeEnum.SYSTEM.getCode());
    }

    protected boolean process(MessageDTO messageDTO) {
        ArrayList<ParticipantPO> participantPOList;
        boolean save = false;
        logger.debug("Received Message: " + messageDTO);
        MessageMainPO messageMainPO = new MessageMainPO();
        this.populateMessageMainDO(messageMainPO, messageDTO);
        messageMainPO.setMsgId(messageDTO.getId());
        Optional<MessageMainPO> find = this.messageMainDao.findById(messageDTO.getId());
        if (find.isPresent()) {
            this.messageMainDao.deleteById(messageDTO.getId());
        }
        save = this.messageMainDao.save(messageMainPO);
        this.participantDao.deleteById(messageDTO.getId());
        if (messageDTO.getParticipantType() == ParticipantTypeEnum.SYSTEM.getCode()) {
            participantPOList = new ArrayList<ParticipantPO>(1);
            ParticipantPO participantPO = new ParticipantPO();
            participantPO.setMsgId(messageMainPO.getMsgId());
            participantPO.setParticipantId("FFFFFFFF-FFFF-FFFF-MMMM-FFFFFFFFFFFF");
            participantPO.setParticipantType(messageDTO.getParticipantType());
            participantPO.setValidTime(messageMainPO.getValidTime());
            participantPO.setInvalidTime(Constants.DEFAULT_INVALID_TIME);
            participantPOList.add(participantPO);
        } else {
            participantPOList = new ArrayList(messageDTO.getParticipants().size());
            for (String participantId : messageDTO.getParticipants().stream().distinct().collect(Collectors.toList())) {
                ParticipantPO participantPO = new ParticipantPO();
                participantPO.setParticipantId(participantId);
                participantPO.setMsgId(messageDTO.getId());
                participantPO.setParticipantType(messageDTO.getParticipantType());
                participantPO.setValidTime(messageMainPO.getValidTime());
                participantPO.setInvalidTime(Constants.DEFAULT_INVALID_TIME);
                participantPOList.add(participantPO);
            }
        }
        this.participantDao.saveAll(participantPOList);
        return save;
    }

    private void populateMessageMainDO(MessageMainPO messageMainPO, MessageDTO messageDTO) {
        Map param;
        MessageBeanUtils.copyProperties(messageDTO, messageMainPO);
        if (messageMainPO.getMsgId() == null) {
            messageMainPO.setMsgId(UUID.randomUUID().toString());
        }
        if ((param = messageDTO.getParam()) != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String paramString = mapper.writeValueAsString((Object)param);
                messageMainPO.setParam(paramString);
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        messageMainPO.setCreateTime(Timestamp.from(Instant.now()));
        messageMainPO.setYear(Calendar.getInstance().get(1));
        if (messageMainPO.getInvalidTime() == null) {
            messageMainPO.setInvalidTime(Constants.DEFAULT_INVALID_TIME);
        }
        if (messageMainPO.getValidTime() == null) {
            messageMainPO.setValidTime(Timestamp.valueOf(LocalDateTime.now()));
        }
    }
}

