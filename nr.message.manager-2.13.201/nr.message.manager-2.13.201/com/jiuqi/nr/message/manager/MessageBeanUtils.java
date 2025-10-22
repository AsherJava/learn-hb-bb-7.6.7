/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.pojo.MessageDTO
 */
package com.jiuqi.nr.message.manager;

import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.nr.message.manager.pojo.MessageFormVO;
import com.jiuqi.nr.message.manager.pojo.MessageMainPO;
import com.jiuqi.nr.message.manager.pojo.MessageVO;
import java.sql.Timestamp;
import org.springframework.beans.BeanUtils;

@Deprecated
public class MessageBeanUtils {
    public static void copyProperties(MessageFormVO messageFormVO, MessageDTO messageDTO) {
        BeanUtils.copyProperties(messageFormVO, messageDTO);
    }

    public static void copyProperties(MessageDTO messageDTO, MessageVO messageVO) {
        BeanUtils.copyProperties(messageDTO, messageVO);
    }

    public static void copyProperties(MessageMainPO messageMainPO, MessageDTO messageDTO) {
        BeanUtils.copyProperties(messageMainPO, messageDTO);
        messageDTO.setId(messageMainPO.getMsgId());
        messageDTO.setValidTime(messageMainPO.getValidTime().toInstant());
        if (messageMainPO.getInvalidTime() != null) {
            messageDTO.setInvalidTime(messageMainPO.getInvalidTime().toInstant());
        }
    }

    public static void copyProperties(MessageDTO messageDTO, MessageMainPO messageMainPO) {
        BeanUtils.copyProperties(messageDTO, messageMainPO);
        messageMainPO.setMsgId(messageDTO.getId());
        messageMainPO.setValidTime(messageDTO.getValidTime() != null ? Timestamp.from(messageDTO.getValidTime()) : null);
        messageMainPO.setInvalidTime(messageDTO.getInvalidTime() != null ? Timestamp.from(messageDTO.getInvalidTime()) : null);
    }
}

