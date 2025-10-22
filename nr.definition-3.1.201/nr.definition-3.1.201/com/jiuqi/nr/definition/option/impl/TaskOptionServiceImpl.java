/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 */
package com.jiuqi.nr.definition.option.impl;

import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignTaskDefineDao;
import com.jiuqi.nr.definition.option.TaskOptionService;
import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionWrapper;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.option.core.TaskOptionDefineDTO;
import com.jiuqi.nr.definition.option.core.TaskOptionGroupDTO;
import com.jiuqi.nr.definition.option.core.TaskOptionPageDTO;
import com.jiuqi.nr.definition.option.core.TaskOptionVO;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.core.ZeroShowValue;
import com.jiuqi.nr.definition.option.dao.TaskOptionDao;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
@Subscriber(channels={"com.jiuqi.nr.definition.taskoption"})
public class TaskOptionServiceImpl
implements TaskOptionService,
ITaskOptionController,
MessageSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(TaskOptionServiceImpl.class);
    private final TaskOptionDao taskOptionDao;
    private final DesignTaskDefineDao taskDefineDao;
    private final Map<String, TaskOptionDefine> optionDefineMap;
    private final List<TaskOptionPage> optionPages;
    private final List<TaskOptionGroup> optionGroups;
    @Autowired
    private MessagePublisher messageService;
    private static final ConcurrentMap<String, ConcurrentMap<String, TaskOption>> CACHE = new ConcurrentHashMap<String, ConcurrentMap<String, TaskOption>>(64);
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();

    public TaskOptionServiceImpl(@Autowired TaskOptionDao taskOptionDao, @Autowired(required=false) List<TaskOptionDefine> optionDefines, @Autowired(required=false) List<TaskOptionPage> optionPages, @Autowired(required=false) List<TaskOptionGroup> optionGroups, @Autowired DesignTaskDefineDao taskDefineDao) {
        this.optionPages = optionPages;
        this.optionGroups = optionGroups;
        this.taskOptionDao = taskOptionDao;
        this.taskDefineDao = taskDefineDao;
        if (optionDefines != null) {
            this.optionDefineMap = new HashMap<String, TaskOptionDefine>(optionDefines.size());
            for (TaskOptionDefine optionDefine : optionDefines) {
                this.optionDefineMap.put(optionDefine.getKey(), optionDefine);
            }
        } else {
            this.optionDefineMap = Collections.emptyMap();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getValue(String taskKey, String key) {
        if (taskKey == null) {
            return null;
        }
        if (key == null) {
            return null;
        }
        if (!this.optionDefineMap.containsKey(key)) {
            return null;
        }
        ConcurrentMap taskOptionMap = (ConcurrentMap)CACHE.get(taskKey);
        if (taskOptionMap != null) {
            TaskOption taskOptionEntity = (TaskOption)taskOptionMap.get(key);
            if (taskOptionEntity != null) {
                return taskOptionEntity.getValue();
            }
            return null;
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(taskKey);
        synchronized (mutex) {
            taskOptionMap = (ConcurrentMap)CACHE.get(taskKey);
            if (taskOptionMap != null) {
                TaskOption taskOptionEntity = (TaskOption)taskOptionMap.get(key);
                if (taskOptionEntity != null) {
                    return taskOptionEntity.getValue();
                }
                return null;
            }
            String tmpValue = null;
            List<TaskOption> mergeOptions = this.merge(taskKey);
            ConcurrentHashMap<String, TaskOption> tmp = new ConcurrentHashMap<String, TaskOption>(mergeOptions.size());
            for (TaskOption option : mergeOptions) {
                if (key.equals(option.getKey())) {
                    tmpValue = option.getValue();
                }
                tmp.put(option.getKey(), option);
            }
            CACHE.put(taskKey, tmp);
            return tmpValue;
        }
    }

    private List<TaskOption> merge(String taskKey) {
        List<TaskOption> options = this.taskOptionDao.getOptions(taskKey);
        HashMap<String, TaskOptionDefine> tmp = new HashMap<String, TaskOptionDefine>(this.optionDefineMap);
        ArrayList<TaskOption> res = new ArrayList<TaskOption>(this.optionDefineMap.size());
        for (TaskOption taskOption : options) {
            String value;
            if (tmp.containsKey(taskOption.getKey())) {
                res.add(taskOption);
            }
            if ((value = taskOption.getValue()) == null) {
                taskOption.setValue("");
            }
            tmp.remove(taskOption.getKey());
        }
        for (Map.Entry entry : tmp.entrySet()) {
            res.add(new TaskOption(taskKey, (String)entry.getKey(), ((TaskOptionDefine)entry.getValue()).getDefaultValue(taskKey)));
        }
        return res;
    }

    @Override
    public void setValue(String taskKey, String key, String value) {
        if (taskKey == null) {
            return;
        }
        if (key == null) {
            return;
        }
        if (!this.optionDefineMap.containsKey(key)) {
            return;
        }
        this.taskOptionDao.setOption(new TaskOption(taskKey, key, value));
        try {
            DesignTaskDefine defineByKey = this.taskDefineDao.getDefineByKey(taskKey);
            defineByKey.setUpdateTime(new Date());
            this.taskDefineDao.update(defineByKey);
        }
        catch (Exception exception) {
            // empty catch block
        }
        CACHE.remove(taskKey);
        this.clear(taskKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TaskOption> getOptions(String taskKey) {
        ConcurrentMap taskOptions = (ConcurrentMap)CACHE.get(taskKey);
        if (taskOptions != null) {
            return taskOptions.values().stream().map(TaskOption::clone).collect(Collectors.toList());
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(taskKey);
        synchronized (mutex) {
            taskOptions = (ConcurrentMap)CACHE.get(taskKey);
            if (taskOptions != null) {
                return taskOptions.values().stream().map(TaskOption::clone).collect(Collectors.toList());
            }
            List<TaskOption> mergeOptions = this.merge(taskKey);
            ConcurrentHashMap<String, TaskOption> tmp = new ConcurrentHashMap<String, TaskOption>(mergeOptions.size());
            for (TaskOption option : mergeOptions) {
                tmp.put(option.getKey(), option.clone());
            }
            CACHE.put(taskKey, tmp);
            return mergeOptions;
        }
    }

    @Override
    public void setOptions(List<TaskOption> options) {
        if (CollectionUtils.isEmpty(options)) {
            return;
        }
        Map<String, List<TaskOption>> taskValues = options.stream().collect(Collectors.groupingBy(TaskOption::getTaskKey));
        this.taskOptionDao.setOptions(options);
        for (String taskKey : taskValues.keySet()) {
            try {
                DesignTaskDefine defineByKey = this.taskDefineDao.getDefineByKey(taskKey);
                defineByKey.setUpdateTime(new Date());
                this.taskDefineDao.update(defineByKey);
            }
            catch (Exception exception) {
                // empty catch block
            }
            CACHE.remove(taskKey);
            this.clear(taskKey);
        }
    }

    @Override
    public OptionWrapper exit(String taskKey, String key) {
        if (taskKey == null) {
            return OptionWrapper.empty();
        }
        if (key == null) {
            return OptionWrapper.empty();
        }
        TaskOption option = this.taskOptionDao.getOption(taskKey, key);
        if (option == null) {
            return OptionWrapper.empty();
        }
        return OptionWrapper.of(option.getValue());
    }

    @Override
    public List<TaskOption> getAllOptions() {
        throw new UnsupportedOperationException("\u6682\u672a\u9002\u914d");
    }

    @Override
    public TaskOptionVO getOptionDefines(String taskKey) {
        Assert.notNull((Object)taskKey, "this argument is required; it must not be null");
        List<TaskOption> options = this.getOptions(taskKey);
        List<Object> optionDefines = new ArrayList(this.optionDefineMap.size());
        for (TaskOption option : options) {
            TaskOptionDefine optionDefine = this.optionDefineMap.get(option.getKey());
            if (optionDefine == null) continue;
            TaskOptionDefineDTO dto = new TaskOptionDefineDTO(optionDefine, taskKey);
            dto.setValue(option.getValue());
            if ("NUMBER_ZERO_SHOW".equals(optionDefine.getKey()) && " ".equals(option.getValue())) {
                dto.setValue(ZeroShowValue.NULL_VALUE.getValue());
            }
            optionDefines.add(dto);
        }
        optionDefines = optionDefines.stream().filter(r -> r.getVisibleType(taskKey) != VisibleType.HIDE).collect(Collectors.toList());
        Map<String, TaskOptionDefineDTO> keyMap = optionDefines.stream().collect(Collectors.toMap(TaskOptionDefineDTO::getKey, Function.identity()));
        HashMap<String, List<String>> relationMap = new HashMap<String, List<String>>();
        for (int i = 0; i < optionDefines.size(); ++i) {
            Object item;
            TaskOptionDefineDTO optionDefine = (TaskOptionDefineDTO)optionDefines.get(i);
            Optional<TaskOptionGroup> group2 = this.optionGroups.stream().filter(e -> e.getTitle().equals(optionDefine.getGroupTitle())).findFirst();
            if (group2.isPresent()) {
                optionDefine.setTaskOptionGroup(group2.get());
            }
            if ((item = optionDefine.getRelationTaskOptionItem()) == null) continue;
            String value = ((TaskOptionDefineDTO)keyMap.get(((RelationTaskOptionItem)item).getFromKey())).getValue();
            if (((RelationTaskOptionItem)item).getFromValues().indexOf(value) < 0) {
                if (((RelationTaskOptionItem)item).getAffectedMode() == AffectedMode.EDITABLE) {
                    optionDefine.setEditable(false);
                } else if (((RelationTaskOptionItem)item).getAffectedMode() == AffectedMode.VISIBLE) {
                    optionDefine.setVisible(false);
                }
            }
            if (CollectionUtils.isEmpty((Collection)relationMap.get(((RelationTaskOptionItem)item).getFromKey()))) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(optionDefine.getKey());
                relationMap.put(((RelationTaskOptionItem)item).getFromKey(), list);
                continue;
            }
            ((List)relationMap.get(((RelationTaskOptionItem)item).getFromKey())).add(optionDefine.getKey());
        }
        Map<String, List<TaskOptionDefineDTO>> defineDTOMap = optionDefines.stream().collect(Collectors.groupingBy(TaskOptionDefineDTO::getPageAndGroup));
        ArrayList<TaskOptionPageDTO> pageDTOList = new ArrayList<TaskOptionPageDTO>();
        List orderPages = this.optionPages.stream().filter(e -> e.getVisibleType(taskKey) != VisibleType.HIDE).sorted(Comparator.comparing(TaskOptionPage::getOrder)).collect(Collectors.toList());
        for (TaskOptionPage page : orderPages) {
            TaskOptionPageDTO taskOptionPageDTO = new TaskOptionPageDTO();
            taskOptionPageDTO.setTitle(page.getTitle());
            ArrayList<TaskOptionGroupDTO> groupDTOList = new ArrayList<TaskOptionGroupDTO>();
            List groups = this.optionGroups.stream().filter(group -> group.getPageTitle().equals(page.getTitle())).sorted(Comparator.comparing(TaskOptionGroup::getOrder)).collect(Collectors.toList());
            for (TaskOptionGroup group3 : groups) {
                TaskOptionGroupDTO taskOptionGroupDTO = new TaskOptionGroupDTO(group3);
                List<TaskOptionDefineDTO> optList = defineDTOMap.get(page.getTitle() + ";" + (group3.getTitle() == null ? "" : group3.getTitle()));
                if (optList == null) {
                    logger.warn(page.getTitle() + ";" + (group3.getTitle() == null ? "" : group3.getTitle()) + " \u4e0b\u6ca1\u6709\u9009\u9879");
                    continue;
                }
                Collections.sort(optList);
                taskOptionGroupDTO.setOption(optList.stream().map(TaskOptionDefineDTO::getKey).collect(Collectors.toList()));
                groupDTOList.add(taskOptionGroupDTO);
            }
            List<TaskOptionDefineDTO> optList = defineDTOMap.get(page.getTitle() + ";");
            if (!CollectionUtils.isEmpty(optList)) {
                TaskOptionGroupDTO taskOptionGroupDTO = new TaskOptionGroupDTO();
                taskOptionGroupDTO.setTitle("");
                Collections.sort(optList);
                taskOptionGroupDTO.setOption(optList.stream().map(TaskOptionDefineDTO::getKey).collect(Collectors.toList()));
                groupDTOList.add(taskOptionGroupDTO);
            }
            taskOptionPageDTO.setGroup(groupDTOList);
            pageDTOList.add(taskOptionPageDTO);
        }
        TaskOptionVO vo = new TaskOptionVO();
        vo.setList(pageDTOList);
        vo.setKeyMap(keyMap);
        vo.setRelationMap(relationMap);
        return vo;
    }

    @Override
    public void clear(String taskKey) {
        if (taskKey == null) {
            CACHE.clear();
        } else {
            CACHE.remove(taskKey);
        }
        this.messageService.publishMessage("com.jiuqi.nr.definition.taskoption", (Object)taskKey);
    }

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        if (!fromThisInstance && "com.jiuqi.nr.definition.taskoption".equals(channel)) {
            if (message == null) {
                CACHE.clear();
            } else {
                CACHE.remove(message.toString());
            }
        }
    }
}

