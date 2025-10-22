/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.todocat.TodoCatInfoDO
 *  com.jiuqi.va.domain.todocat.TodoCatModelEnum
 *  com.jiuqi.va.domain.todocat.TodoCatQueryDTO
 *  com.jiuqi.va.domain.todocat.config.QueryParam
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.todo.controller.todocat.VaTodoCatController
 *  com.jiuqi.va.todo.controller.todocat.VaTodoCatInfoController
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrGetTodoCountDTO;
import com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.todocat.TodoCatInfoDO;
import com.jiuqi.va.domain.todocat.TodoCatModelEnum;
import com.jiuqi.va.domain.todocat.TodoCatQueryDTO;
import com.jiuqi.va.domain.todocat.config.QueryParam;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.todo.controller.todocat.VaTodoCatController;
import com.jiuqi.va.todo.controller.todocat.VaTodoCatInfoController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGetTodoCountHandler
implements ClbrBusinessHandler<ClbrGetTodoCountDTO, Integer> {
    @Autowired
    VaTodoCatInfoController vaTodoCatInfoController;
    @Autowired
    VaTodoCatController vaTodoCatController;
    @Autowired
    UserService<User> userService;
    @Autowired
    SystemUserService sysUserService;

    @Override
    public final String getBusinessCode() {
        return "GETTODOCOUNT";
    }

    @Override
    public ClbrGetTodoCountDTO beforeHandler(Object content) {
        Map map = (Map)content;
        String userName = ConverterUtils.getAsString(map.get("userName"));
        if (StringUtils.isEmpty((String)userName)) {
            throw new BusinessRuntimeException("\u8bf7\u6c42\u53c2\u6570\u4e2d\u7528\u6237\u540d[userName]\u4e3a\u7a7a");
        }
        String clbrState = ConverterUtils.getAsString(map.get("clbrState"));
        if (StringUtils.isEmpty((String)clbrState)) {
            throw new BusinessRuntimeException("\u8bf7\u6c42\u53c2\u6570\u4e2d\u534f\u540c\u72b6\u6001[clbrState]\u4e3a\u7a7a");
        }
        ClbrGetTodoCountDTO dto = new ClbrGetTodoCountDTO();
        dto.setUserName(userName);
        dto.setClbrState(clbrState);
        return dto;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Integer handler(ClbrGetTodoCountDTO clbrBillTodoDTO) {
        this.changeUserContext(clbrBillTodoDTO.getUserName());
        int count = 0;
        try {
            TodoCatInfoDO todoCatInfoDO = new TodoCatInfoDO();
            R r = this.vaTodoCatInfoController.listTodoCatInfoDO(todoCatInfoDO);
            if (r.getCode() != 0) {
                throw new BusinessRuntimeException("\u5f85\u529e\u5217\u8868\u67e5\u8be2\u5931\u8d25\uff0c" + r.getMsg());
            }
            List todoCatInfoDOList = (List)r.get((Object)"data");
            for (TodoCatInfoDO catInfoDO : todoCatInfoDOList) {
                R countTodoData;
                if (!TodoCatModelEnum.COMMON.name().equals(catInfoDO.getModelName())) continue;
                TodoCatQueryDTO todoCatQueryDTO = new TodoCatQueryDTO();
                todoCatQueryDTO.setId(catInfoDO.getId().toString());
                todoCatQueryDTO.setQueryType(ClbrStatesEnum.REJECT.name().equals(clbrBillTodoDTO.getClbrState().toUpperCase(Locale.ROOT)) ? "REJECT" : "TODO");
                if (!StringUtils.isEmpty((String)clbrBillTodoDTO.getClbrState())) {
                    ArrayList<QueryParam> queryParams = new ArrayList<QueryParam>();
                    QueryParam queryParam = new QueryParam();
                    queryParams.add(queryParam);
                    queryParam.setTableName("GC_CLBRBILL");
                    queryParam.setColumnName("CLBRSTATE");
                    queryParam.setValue((Object)clbrBillTodoDTO.getClbrState());
                    todoCatQueryDTO.setQueryParams(queryParams);
                }
                if ((countTodoData = this.vaTodoCatController.countTodoData(todoCatQueryDTO)).getCode() != 0) {
                    throw new BusinessRuntimeException(catInfoDO.getTitle() + "\u67e5\u8be2\u4ee3\u529e\u6570\u91cf\u5f02\u5e38\uff1a" + countTodoData.getMsg());
                }
                count += Integer.parseInt(countTodoData.get((Object)"count").toString());
            }
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return count;
    }

    @Override
    public Integer afterHandler(ClbrGetTodoCountDTO content, Integer result) {
        return result;
    }

    private void changeUserContext(String userName) {
        User user = this.userService.findByUsername(userName).orElseGet(() -> (SystemUser)this.sysUserService.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException("\u534f\u540c\u7cfb\u7edf\u627e\u4e0d\u5230\u7528\u6237\u540d\u4e3a[" + userName + "]\u7684\u7528\u6237\u4fe1\u606f\u3002")));
        NpContextImpl contextImpl = new NpContextImpl();
        NpContextUser contextUser = new NpContextUser();
        contextUser.setName(user.getName());
        contextUser.setId(user.getId());
        contextUser.setNickname(user.getNickname());
        contextUser.setOrgCode(user.getOrgCode());
        contextUser.setDescription(user.getDescription());
        contextUser.setType(user.getUserType());
        contextImpl.setUser((ContextUser)contextUser);
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getName());
        contextImpl.setIdentity((ContextIdentity)contextIdentity);
        NpContextHolder.setContext((NpContext)contextImpl);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setId(user.getId());
        userDTO.setTenantName("__default_tenant__");
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getName());
        userDTO.setLoginDate(new Date());
        ShiroUtil.bindUser((UserLoginDTO)userDTO);
    }
}

