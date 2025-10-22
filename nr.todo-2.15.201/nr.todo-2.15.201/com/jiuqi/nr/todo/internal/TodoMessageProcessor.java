/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.task.AbstractMessageProcessor
 */
package com.jiuqi.nr.todo.internal;

import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.task.AbstractMessageProcessor;
import com.jiuqi.nr.todo.TodoRepository;
import com.jiuqi.nr.todo.cache.TodoCacheManager;
import com.jiuqi.nr.todo.entity.TodoPO;
import com.jiuqi.nr.todo.entity.TodoVO;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TodoMessageProcessor
extends AbstractMessageProcessor {
    private final TodoRepository todoRepository;
    private final TodoCacheManager todoCacheManager;

    public TodoMessageProcessor(TodoRepository todoRepository, TodoCacheManager todoCacheManager) {
        this.todoRepository = todoRepository;
        this.todoCacheManager = todoCacheManager;
    }

    protected boolean process(MessageDTO messageDTO) {
        TodoVO todoVO;
        boolean save = false;
        TodoPO todoPO = new TodoPO();
        todoPO.setMsgId(messageDTO.getId());
        StringBuilder tag = new StringBuilder();
        if (messageDTO.getTag() != null) {
            for (String item : messageDTO.getTag()) {
                tag.append(item).append(";");
            }
            todoPO.setTag(tag.toString());
        }
        todoPO.setActionName(messageDTO.getActionName());
        todoPO.setTodoType(messageDTO.getTodoType());
        Map param = messageDTO.getParam();
        if (param != null) {
            String todoParam = (String)param.get("todoParam");
            todoPO.setTodoParam(todoParam);
            String formSchemeKey = (String)param.get("formSchemeId");
            todoPO.setFormSchemeKey(formSchemeKey);
        }
        if ((todoVO = this.todoRepository.get(messageDTO.getId())) != null) {
            this.todoRepository.delete(messageDTO.getId());
        }
        if (param != null) {
            String otherContent = (String)param.get("otherContent");
            todoPO.setOtherContent(otherContent);
            String otherTitle = (String)param.get("otherTitle");
            todoPO.setOtherTitle(otherTitle);
            String otherActionName = (String)param.get("otherActionName");
            todoPO.setOtherLangActionName(otherActionName);
            String otherParam = (String)param.get("otherParam");
            todoPO.setOtherParam(otherParam);
        }
        save = this.todoRepository.save(todoPO);
        this.updateCachedCreateTime(messageDTO);
        return save;
    }

    private void updateCachedCreateTime(MessageDTO messageDTO) {
        this.todoCacheManager.updateTodoTime(messageDTO.getParticipants(), System.currentTimeMillis());
    }

    protected boolean support(MessageDTO messageDTO) {
        return messageDTO.getType().equals(MessageTypeEnum.TODO.getCode());
    }
}

