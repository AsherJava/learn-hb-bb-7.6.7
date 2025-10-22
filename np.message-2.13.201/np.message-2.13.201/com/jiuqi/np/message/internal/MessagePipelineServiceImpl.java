/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.np.message.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiuqi.np.message.constants.MessageHandleStateEnum;
import com.jiuqi.np.message.pojo.MagResult;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.pojo.MessagePacketPO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.np.message.task.StarterMessageProcessor;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessagePipelineServiceImpl
implements MessagePipelineService {
    private static final Logger logger = LoggerFactory.getLogger(MessagePipelineServiceImpl.class);
    private final StarterMessageProcessor starterMessageProcessor;

    public MessagePipelineServiceImpl(StarterMessageProcessor starterMessageProcessor) {
        this.starterMessageProcessor = starterMessageProcessor;
    }

    @Override
    @Transactional
    public boolean send(MessageDTO messageDTO) {
        logger.debug(String.format("Received message: %s", messageDTO));
        if (messageDTO.getId() == null) {
            messageDTO.setId(UUID.randomUUID().toString());
        }
        MessagePacketPO messagePacketPO = new MessagePacketPO();
        this.populateMessagePacketDO(messagePacketPO, messageDTO);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)new JavaTimeModule());
        try {
            messagePacketPO.setMsgBody(mapper.writeValueAsString((Object)messageDTO));
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        if (messagePacketPO.getId() == null) {
            messagePacketPO.setId(UUID.randomUUID().toString());
        }
        boolean handleMessage = this.handleMessage(messagePacketPO);
        return handleMessage;
    }

    private void populateMessagePacketDO(MessagePacketPO messagePacketPO, MessageDTO messageDTO) {
        messagePacketPO.setTitle(messageDTO.getTitle());
        messagePacketPO.setType(messageDTO.getType());
        messagePacketPO.setSendTime(Timestamp.from(Instant.now()));
        messagePacketPO.setState(MessageHandleStateEnum.UNHANDLED.getNum());
        messagePacketPO.setThreadId(Thread.currentThread().getId());
    }

    public boolean handleMessage(MessagePacketPO messagePacketPOS) {
        boolean doHandle = false;
        if (messagePacketPOS != null) {
            try {
                doHandle = this.doHandle(messagePacketPOS);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return doHandle;
    }

    private boolean doHandle(MessagePacketPO messagePacketPO) {
        MagResult re = new MagResult();
        messagePacketPO.setState(MessageHandleStateEnum.HANDLING.getNum());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)new JavaTimeModule());
        MessageDTO messageDTO = null;
        try {
            messageDTO = (MessageDTO)mapper.readValue(messagePacketPO.getMsgBody(), MessageDTO.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (messageDTO != null) {
            this.starterMessageProcessor.handle(messageDTO, re);
        }
        return re.isSuccess();
    }
}

