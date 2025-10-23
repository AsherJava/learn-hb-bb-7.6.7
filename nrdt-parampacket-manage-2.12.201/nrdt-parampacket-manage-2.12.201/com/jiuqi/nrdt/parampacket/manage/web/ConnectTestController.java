/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nvwa.certification.bean.NvwaPat
 *  com.jiuqi.nvwa.certification.service.INvwaPatService
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nrdt.parampacket.manage.web;

import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nrdt.parampacket.manage.bean.ResponseObj;
import com.jiuqi.nvwa.certification.bean.NvwaPat;
import com.jiuqi.nvwa.certification.service.INvwaPatService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/remote/connect-test/"})
public class ConnectTestController {
    private static final Logger logger = LoggerFactory.getLogger(ConnectTestController.class);
    @Autowired
    private INvwaPatService nvwaPatService;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private NvwaSystemUserClient systemUserClient;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @GetMapping(value={"/test"})
    public ResponseObj testConnect(@RequestParam String userName, HttpServletRequest request) {
        String userNameByPat = this.getUserNameByPat(request);
        if (!StringUtils.hasText(userNameByPat)) {
            return ResponseObj.FAIL(null, "\u8fde\u63a5\u5931\u8d25\uff0c\u7528\u6237\u540d\u4e3a\u7a7a");
        }
        if (!userNameByPat.equals(userName)) {
            return ResponseObj.FAIL(null, "\u8fde\u63a5\u5931\u8d25\uff0c\u7528\u6237\u540d\u4e0d\u5339\u914d");
        }
        return ResponseObj.SUCCESS(null, "\u8fde\u63a5\u6210\u529f");
    }

    @GetMapping(value={"/getUserNameByPat"})
    public String getUserNameByPat(HttpServletRequest request) {
        String authorization = request.getHeader("authorization-cer-pat");
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        NvwaPat pat = this.nvwaPatService.getByToken(authorization);
        String userId = pat.getUserId();
        UserDTO userDTO = this.nvwaUserClient.get(userId);
        if (null == userDTO) {
            userDTO = this.systemUserClient.get(userId);
        }
        if (null == userDTO) {
            return null;
        }
        return userDTO.getName();
    }

    @GetMapping(value={"/getRuntimeTasks"})
    public ResponseObj getRuntimeTasks() {
        try {
            List taskDefines = this.runTimeViewController.listAllTask();
            return ResponseObj.SUCCESS(taskDefines, "\u67e5\u8be2\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseObj.FAIL(null, "\u67e5\u8be2\u5931\u8d25");
        }
    }

    @GetMapping(value={"getFormSchemeKey"})
    public ResponseObj getFormSchemeKey(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) {
        try {
            SchemePeriodLinkDefine link = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
            return ResponseObj.SUCCESS(link.getSchemeKey(), "\u67e5\u8be2\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseObj.FAIL(null, "\u67e5\u8be2\u5931\u8d25");
        }
    }
}

