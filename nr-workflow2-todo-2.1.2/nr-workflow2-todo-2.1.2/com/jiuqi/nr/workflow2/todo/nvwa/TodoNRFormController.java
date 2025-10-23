/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.todocat.model.TodoCatModel
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.workflow2.todo.nvwa;

import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.todocat.model.TodoCatModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value={"/nr"})
@RestController
public class TodoNRFormController {
    @Resource
    private List<TodoCatModel> todoCatModels;
    @Autowired
    @Qualifier(value="todoSinglePeriodDao_1_0")
    private TodoSinglePeriodDao todoSinglePeriodDao_1_0;

    @PostMapping(value={"/biz/todocat/model/list"})
    R getTodoCatModelList(@Valid @RequestBody TenantDO tenantDO) {
        R r = new R();
        String moduleName = (String)tenantDO.getExtInfo("moduleName");
        List todoModels = this.todoCatModels.stream().filter(todoCatModel -> moduleName.equals(todoCatModel.getModuleName())).collect(Collectors.toList());
        r.put("todoModels", (Object)JSONUtil.toJSONString(todoModels));
        return r;
    }

    @PostMapping(value={"/biz/todocat/extend/count"})
    R countTodoCatExtend(@Valid @RequestBody TenantDO tenantDO) {
        R response = new R();
        Object configInfo = tenantDO.getExtInfo("config");
        if (configInfo == null || configInfo.toString().isEmpty()) {
            response.put("count", (Object)0);
            return response;
        }
        String configStr = configInfo.toString();
        JSONObject configJSON = new JSONObject(configStr);
        response.put("count", (Object)(configJSON.isNull("taskId") ? 0 : this.todoSinglePeriodDao_1_0.getTaskTodoQuantity(configJSON.getString("taskId"))));
        return response;
    }
}

