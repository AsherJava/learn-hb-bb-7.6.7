/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.message.constants.HistoryStatusEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  javax.validation.constraints.NotEmpty
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.message.constants.HistoryStatusEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.nr.message.manager.BusinessResponseEntity;
import com.jiuqi.nr.message.manager.MessageBeanUtils;
import com.jiuqi.nr.message.manager.MessageMgrController;
import com.jiuqi.nr.message.manager.MessageMgrService;
import com.jiuqi.nr.message.manager.pojo.MessageFormVO;
import com.jiuqi.nr.message.manager.pojo.MessageIdsDTO;
import com.jiuqi.nr.message.manager.pojo.MessageVO;
import com.jiuqi.nr.message.manager.pojo.PagingQueryVO;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class MessageMgrControllerImpl
implements MessageMgrController {
    private final MessageMgrService messageMgrService;

    public MessageMgrControllerImpl(MessageMgrService messageMgrService) {
        this.messageMgrService = messageMgrService;
    }

    @Override
    public BusinessResponseEntity<Boolean> saveSubmit(@RequestBody MessageFormVO requestVO) {
        requestVO.setCreateUser(NpContextHolder.getContext().getUserId());
        MessageDTO messageDTO = new MessageDTO();
        MessageBeanUtils.copyProperties(requestVO, messageDTO);
        return BusinessResponseEntity.ok(this.messageMgrService.push(messageDTO));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getUnread(@PathVariable(value="curPage") @NotEmpty String curPage, @PathVariable(value="pageSize") @NotEmpty String pageSize) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getUnread(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize)));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getRead(@PathVariable(value="curPage") String curPage, @PathVariable(value="pageSize") String pageSize) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getHistory(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize), HistoryStatusEnum.READ.getCode()));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getRubbish(@PathVariable(value="curPage") String curPage, @PathVariable(value="pageSize") String pageSize) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getHistory(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize), HistoryStatusEnum.RUBBISH.getCode()));
    }

    @Override
    public BusinessResponseEntity<MessageVO> readUnread(@PathVariable(value="messageId") String messageId, @PathVariable(value="userId") String userId) {
        userId = NpContextHolder.getContext().getUserId();
        MessageDTO messageDTO = this.messageMgrService.readUnread(messageId, userId);
        MessageVO messageVO = new MessageVO();
        MessageBeanUtils.copyProperties(messageDTO, messageVO);
        return BusinessResponseEntity.ok(messageVO);
    }

    @Override
    public BusinessResponseEntity<MessageVO> readRead(@PathVariable(value="messageId") String messageId) {
        MessageDTO messageDTO = this.messageMgrService.readRead(messageId);
        MessageVO messageVO = new MessageVO();
        MessageBeanUtils.copyProperties(messageDTO, messageVO);
        return BusinessResponseEntity.ok(messageVO);
    }

    @Override
    public BusinessResponseEntity<Boolean> markRead(@RequestBody MessageIdsDTO ids) {
        ids.setUserId(NpContextHolder.getContext().getUserId());
        return BusinessResponseEntity.ok(this.messageMgrService.unReadToHistory(ids.getUserId(), ids.getMessageIds(), HistoryStatusEnum.READ.getCode()));
    }

    @Override
    public BusinessResponseEntity<Boolean> unReadToRubbish(@RequestBody MessageIdsDTO ids) {
        ids.setUserId(NpContextHolder.getContext().getUserId());
        return BusinessResponseEntity.ok(this.messageMgrService.unReadToHistory(ids.getUserId(), ids.getMessageIds(), HistoryStatusEnum.RUBBISH.getCode()));
    }

    @Override
    public BusinessResponseEntity<Boolean> readToRubbish(@RequestBody MessageIdsDTO ids) {
        ids.setUserId(NpContextHolder.getContext().getUserId());
        return BusinessResponseEntity.ok(this.messageMgrService.updateHistoryStatus(ids, HistoryStatusEnum.RUBBISH.getCode()));
    }

    @Override
    public BusinessResponseEntity<Boolean> recover(@RequestBody MessageIdsDTO ids) {
        ids.setUserId(NpContextHolder.getContext().getUserId());
        return BusinessResponseEntity.ok(this.messageMgrService.updateHistoryStatus(ids, HistoryStatusEnum.READ.getCode()));
    }

    @Override
    public BusinessResponseEntity<Boolean> delete(@RequestBody MessageIdsDTO ids) {
        ids.setUserId(NpContextHolder.getContext().getUserId());
        return BusinessResponseEntity.ok(this.messageMgrService.updateHistoryStatus(ids, HistoryStatusEnum.DELETE.getCode()));
    }

    @Override
    public BusinessResponseEntity<List<String>> getReadInfo(@PathVariable(value="messageId") String messageId) {
        return BusinessResponseEntity.ok(this.messageMgrService.getReadInfo(messageId));
    }

    @Override
    public BusinessResponseEntity<Boolean> revoke(@PathVariable(value="messageId") String messageId) {
        return BusinessResponseEntity.ok(this.messageMgrService.revoke(messageId));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getUnreadByType(String curPage, String pageSize, int type) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getUnread(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize), type));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getReadByType(String curPage, String pageSize, int type) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getHistory(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize), HistoryStatusEnum.READ.getCode(), type));
    }

    @Override
    public BusinessResponseEntity<PagingQueryVO> getRubbishByType(String curPage, String pageSize, int type) {
        String userId = NpContextHolder.getContext().getUserId();
        return BusinessResponseEntity.ok(this.messageMgrService.getHistory(userId, Integer.parseInt(curPage), Integer.parseInt(pageSize), HistoryStatusEnum.RUBBISH.getCode(), type));
    }
}

