/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.message.TodoActionEvent
 *  com.jiuqi.np.message.TodoBatchActionEvent
 *  com.jiuqi.np.message.TodoCompleteEvent
 *  com.jiuqi.np.message.internal.ParticipantService
 */
package com.jiuqi.nr.todo.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.message.TodoActionEvent;
import com.jiuqi.np.message.TodoBatchActionEvent;
import com.jiuqi.np.message.TodoCompleteEvent;
import com.jiuqi.np.message.internal.ParticipantService;
import com.jiuqi.nr.todo.TodoRepository;
import com.jiuqi.nr.todo.TodoService;
import com.jiuqi.nr.todo.bean.Infoview;
import com.jiuqi.nr.todo.bean.Locate;
import com.jiuqi.nr.todo.bean.Param;
import com.jiuqi.nr.todo.bean.TodoParam;
import com.jiuqi.nr.todo.bean.TodoResult;
import com.jiuqi.nr.todo.cache.TodoCacheManager;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.nr.todo.inter.ITodoResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl
implements TodoService {
    public static final int READED = 1;
    private ApplicationEventPublisher applicationEventPublisher;
    private TodoRepository todoRepository;
    private TodoCacheManager todoCacheManager;
    private final ParticipantService participantService;
    @Autowired(required=false)
    Map<String, ITodoResult> todoResult;

    public TodoServiceImpl(ApplicationEventPublisher applicationEventPublisher, TodoRepository todoRepository, TodoCacheManager todoCacheManager, ParticipantService participantService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.todoRepository = todoRepository;
        this.todoCacheManager = todoCacheManager;
        this.participantService = participantService;
    }

    @Override
    public boolean complete(List<String> formSchemeKey, String messageId, String userId) {
        if (formSchemeKey != null) {
            return this.todoRepository.updateCompleteTime(formSchemeKey, LocalDateTime.now());
        }
        return this.todoRepository.updateCompleteTime(messageId, userId, LocalDateTime.now());
    }

    @Override
    public void doAction(List<String> messageId, String apppName, String userId, String actionId, Map<String, Object> extendParams) {
        if (messageId.size() > 1) {
            TodoBatchActionEvent event = new TodoBatchActionEvent();
            event.setUserId(userId);
            event.setActionId(actionId);
            event.setExtendParams(extendParams);
            event.setAppName(apppName);
            event.setMessageId(messageId);
            this.applicationEventPublisher.publishEvent(event);
        } else {
            TodoActionEvent event = new TodoActionEvent();
            event.setMessageId(messageId.get(0));
            event.setUserId(userId);
            event.setActionId(actionId);
            event.setAppName(apppName);
            event.setExtendParams(extendParams);
            this.applicationEventPublisher.publishEvent(event);
        }
    }

    @Override
    public boolean existNewTodo(String userId, long latestTime) {
        List participantIds = this.participantService.findParticipantId(userId);
        long latestTodoTime = this.todoCacheManager.getLatestTodoTime(participantIds);
        return latestTodoTime >= latestTime;
    }

    @EventListener
    public void onTodoComplete(TodoCompleteEvent event) {
        this.complete(event.getFormSchemeKey(), event.getMessageId(), event.getUserId());
    }

    @Override
    public TodoResult queryTodoParam(TodoParam todoParam) {
        TodoResult todoResult = new TodoResult();
        String customTodoparam = todoParam.getTodoparam();
        if (customTodoparam != null) {
            todoResult.setParam(customTodoparam);
            todoResult.setType("2");
        } else {
            todoResult.setAppName("dataentry");
            Infoview infoview = new Infoview();
            infoview.setName("upload_result");
            infoview.setType("batch_workFlow_result");
            ObjectMapper objMapper = new ObjectMapper();
            try {
                Map detailMap = (Map)objMapper.readValue(todoParam.getDetailResult(), (TypeReference)new TypeReference<Map<String, Object>>(){});
                infoview.setArgs(detailMap);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
            Locate locate = new Locate();
            locate.setInfoview(infoview);
            Param param = new Param();
            param.setTaskId(todoParam.getTaskKey());
            param.setFormSchemeId(todoParam.getFormSchemeKey());
            param.setPeriod(todoParam.getPeriod());
            param.setUnitViewEntityRange(todoParam.getUnitViewEntityRange());
            param.setLocate(locate);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String paramStr = objectMapper.writeValueAsString((Object)param);
                todoResult.setParam(paramStr);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
            todoResult.setOpenWay("FUNCTAB");
            todoResult.setTitle("\u6570\u636e\u5f55\u5165-" + todoParam.getTaskTitle());
            todoResult.setType("1");
        }
        return todoResult;
    }

    @Override
    public List<TodoVO> todoList(List<String> participantIds, int type) {
        List<Object> todoList = new ArrayList();
        todoList = 1 == type ? this.todoRepository.compeleteTodoList(participantIds) : this.todoRepository.list(participantIds);
        return todoList;
    }
}

