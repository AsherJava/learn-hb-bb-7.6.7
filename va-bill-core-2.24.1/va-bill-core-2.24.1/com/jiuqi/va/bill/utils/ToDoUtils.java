/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.HashMap;
import java.util.Map;

public class ToDoUtils {
    public static void deleteRejectToDo(String bizCode, String bizType) {
        TodoClient TodoClient2 = (TodoClient)ApplicationContextRegister.getBean(TodoClient.class);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setBizCode(bizCode);
        taskDTO.setBizType((Object)bizType);
        taskDTO.addExtInfo("JTOKENID", (Object)ToDoUtils.getJTOKENID());
        ShiroUtil.unbindUser();
        PageVO listReject = TodoClient2.listReject(taskDTO);
        if (listReject == null || listReject.getRows() == null || listReject.getRows().size() == 0) {
            return;
        }
        for (Map reject : listReject.getRows()) {
            if (reject.get("BIZCODE") == null || !reject.get("BIZCODE").equals(bizCode)) continue;
            TenantDO tenantDO = new TenantDO();
            HashMap mapComplete = new HashMap();
            mapComplete.put("PROCESSID", reject.get("PROCESSID"));
            mapComplete.put("PROCESSSTATUS", 1);
            mapComplete.put("TASKID", reject.get("TASKID"));
            mapComplete.put("TASKTYPE", VaTodoTaskTypeEnum.REJECT.getValue());
            mapComplete.put("COMPLETERESULT", 1);
            HashMap extinfo = new HashMap();
            extinfo.put("completeTodoTask", mapComplete);
            tenantDO.setExtInfo(extinfo);
            R complete = TodoClient2.complete(tenantDO);
            if (complete.getCode() != 1) continue;
            LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)bizType, (String)bizCode, (String)complete.getMsg());
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.todoutil.billdeletefailed"));
        }
    }

    public static String getJTOKENID() {
        if (ShiroUtil.getUser() == null) {
            return "";
        }
        AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(ShiroUtil.getTenantName());
        userDTO.setUsername(ShiroUtil.getUser().getUsername());
        userDTO.setCheckPwd(false);
        userDTO.addExtInfo("loginDate", (Object)ShiroUtil.getUser().getLoginDate());
        R authR = authUserClient.getLoginToken(userDTO);
        if (authR != null && authR.getCode() == 0 && authR.get((Object)"token") != null) {
            return (String)authR.get((Object)"token");
        }
        return "";
    }
}

