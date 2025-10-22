/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nvwa.nlpr.extend.NvwaNlprResourceCategory
 *  com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService
 *  com.jiuqi.nvwa.nlpr.resource.Resource
 *  com.jiuqi.nvwa.nlpr.util.NvwaRouteJumpFactory
 *  com.jiuqi.nvwa.nlpr.vo.ConfigVo
 *  com.jiuqi.nvwa.nlpr.vo.LocationVo
 *  com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport
 */
package com.jiuqi.nr.query.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nvwa.nlpr.extend.NvwaNlprResourceCategory;
import com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService;
import com.jiuqi.nvwa.nlpr.resource.Resource;
import com.jiuqi.nvwa.nlpr.util.NvwaRouteJumpFactory;
import com.jiuqi.nvwa.nlpr.vo.ConfigVo;
import com.jiuqi.nvwa.nlpr.vo.LocationVo;
import com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryNvwaNlprResourceCategory
implements NvwaNlprResourceCategory {
    private static final Logger log = LoggerFactory.getLogger(QueryNvwaNlprResourceCategory.class);
    @Autowired
    private NvwaRouteJumpFactory nvwaRouteJumpFactory;

    public String getId() {
        return "queryResourceCategory_Id";
    }

    public String getCode() {
        return "queryResourceCategory_Code";
    }

    public String getTitel() {
        return "\u6570\u636e\u67e5\u8be2";
    }

    public List<Resource> getChildrens(String resourceId, String params) {
        return null;
    }

    public LocationVo buildURL(NLPResourceSupport resourceSupport) {
        LocationVo locationVo = new LocationVo();
        NvwaRouteJumpService routeJumpService = this.nvwaRouteJumpFactory.getRouteJumpService(resourceSupport);
        ConfigVo appConfigVo = routeJumpService.buildConfig(resourceSupport);
        String frontEndUrl = resourceSupport.getfrontEndUrl();
        String url = frontEndUrl + "/#/sso?jumpType=app&name=" + appConfigVo.getFunction();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("appConfig", appConfigVo.getConfig());
            String appConfig = mapper.writeValueAsString(resultMap);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] textByte = appConfig.getBytes("UTF-8");
            String encodedText = encoder.encodeToString(textByte);
            url = url + "&os-jump=" + encodedText;
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"AI\u6307\u6807\u67e5\u8be2\u9519\u8bef\uff0c\u521b\u5efaURL\u9519\u8bef", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage()));
        }
        catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"AI\u6307\u6807\u67e5\u8be2\u9519\u8bef\uff0c\u521b\u5efaURL\u9519\u8bef", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage()));
        }
        locationVo.setUrl(url);
        locationVo.setDescribe(appConfigVo.getDescribe());
        locationVo.setSuccess(appConfigVo.isSuccess());
        locationVo.setMsg(appConfigVo.getMsg());
        return locationVo;
    }
}

