/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrBillTodoUrlDTO;
import com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum;
import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGetTodoListSsoUrlHandler
implements ClbrBusinessHandler<ClbrBillTodoUrlDTO, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGetTodoListSsoUrlHandler.class);
    @Autowired
    INvwaAppService nvwaAppService;
    @Autowired
    INvwaAppRequestManage nvwaAppRequestManage;

    @Override
    public final String getBusinessCode() {
        return "GETTODOLISTSSOURL";
    }

    @Override
    public ClbrBillTodoUrlDTO beforeHandler(Object content) {
        Map map = (Map)content;
        String userName = ConverterUtils.getAsString(map.get("userName"));
        if (StringUtils.isEmpty((String)userName)) {
            throw new BusinessRuntimeException("\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clbrState = ConverterUtils.getAsString(map.get("clbrState"));
        if (StringUtils.isEmpty((String)clbrState)) {
            throw new BusinessRuntimeException("\u534f\u540c\u72b6\u6001\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clientId = ConverterUtils.getAsString(map.get("clientId"));
        if (StringUtils.isEmpty((String)clientId)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aefID\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clientSecret = ConverterUtils.getAsString(map.get("clientSecret"));
        if (StringUtils.isEmpty((String)clientSecret)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u5bc6\u94a5\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ClbrBillTodoUrlDTO dto = new ClbrBillTodoUrlDTO();
        dto.setClientId(clientId);
        dto.setClientSecret(clientSecret);
        dto.setUserName(userName);
        dto.setClbrState(clbrState);
        return dto;
    }

    @Override
    public final String handler(ClbrBillTodoUrlDTO clbrBillTodoUrlDTO) {
        NvwaApp nvwaApp = this.checkClientId(clbrBillTodoUrlDTO.getClientId(), clbrBillTodoUrlDTO.getClientSecret());
        String frontendURL = nvwaApp.getFrontendURL();
        if (StringUtils.isEmpty((String)frontendURL)) {
            throw new BusinessRuntimeException("\u8ba4\u8bc1\u670d\u52a1\u4e2d\u672a\u914d\u7f6e\u524d\u7aef\u5730\u5740\uff0c\u8bf7\u68c0\u67e5\u76f8\u5173\u914d\u7f6e\u3002");
        }
        NvwaSsoBuildDTO nvwaSsoBuildDTO = new NvwaSsoBuildDTO();
        nvwaSsoBuildDTO.setAppName("todo-app");
        nvwaSsoBuildDTO.setUserName(clbrBillTodoUrlDTO.getUserName());
        nvwaSsoBuildDTO.setScope("@nvwa");
        nvwaSsoBuildDTO.setFrontAddress(frontendURL);
        String clbrState = clbrBillTodoUrlDTO.getClbrState().toUpperCase(Locale.ROOT);
        if (ClbrStatesEnum.SUBMIT.name().equals(clbrState)) {
            nvwaSsoBuildDTO.setExpose("VaTodoCatList");
            nvwaSsoBuildDTO.setTitle("\u5f85\u529e\u5206\u7c7b\u5217\u8868");
        } else if (ClbrStatesEnum.REJECT.name().equals(clbrState)) {
            nvwaSsoBuildDTO.setExpose("VaReject");
            nvwaSsoBuildDTO.setTitle("\u6211\u7684\u9a73\u56de\u5f85\u529e");
        } else {
            LOGGER.info("\u83b7\u53d6\u5f85\u529e\u5217\u8868\u5355\u70b9\u5730\u5740\u4e0d\u652f\u6301{}\u72b6\u6001", (Object)clbrState);
            throw new BusinessRuntimeException("\u83b7\u53d6\u5f85\u529e\u5217\u8868\u5355\u70b9\u5730\u5740\u4e0d\u652f\u6301" + clbrState + "\u72b6\u6001");
        }
        return this.nvwaAppRequestManage.buildCurSsoLocation(nvwaSsoBuildDTO);
    }

    @Override
    public String afterHandler(ClbrBillTodoUrlDTO content, String result) {
        return result;
    }

    private NvwaApp checkClientId(String clientId, String clientSecret) {
        NvwaApp nvwaApp = this.nvwaAppService.selectByClientid(clientId);
        if (Objects.isNull(nvwaApp)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u8ba4\u8bc1\u5931\u8d25");
        }
        if (!nvwaApp.getClientsecret().equals(clientSecret)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u8ba4\u8bc1\u5931\u8d25");
        }
        return nvwaApp;
    }
}

