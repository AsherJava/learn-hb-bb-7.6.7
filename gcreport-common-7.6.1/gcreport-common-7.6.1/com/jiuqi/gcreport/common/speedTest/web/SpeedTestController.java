/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.speedTest.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.speedTest.vo.SpeedBaseInfoVO;
import com.jiuqi.gcreport.common.util.UrlUtils;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value={"/api/gcreport/v1//speedTest"})
public class SpeedTestController {
    @GetMapping(value={"/baseInfo"})
    public BusinessResponseEntity<SpeedBaseInfoVO> baseInfo(HttpServletRequest request) {
        SpeedBaseInfoVO baseInfoVO = new SpeedBaseInfoVO();
        baseInfoVO.setClientIp(UrlUtils.getIp());
        baseInfoVO.setServerName(request.getServerName());
        return BusinessResponseEntity.ok((Object)baseInfoVO);
    }

    @PostMapping(value={"/uploadSpeed"})
    public BusinessResponseEntity<String> uploadSpeed() {
        return BusinessResponseEntity.ok((Object)"success");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GetMapping(value={"/downloadSpeed"})
    public void downloadSpeed(HttpServletResponse response) throws Exception {
        response.setContentType("jqtest");
        StringBuffer data = new StringBuffer(524288);
        for (int j = 0; j < 524288; ++j) {
            data.append("*");
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(data.toString().getBytes());
            out.flush();
        }
        finally {
            if (null != out) {
                out.close();
            }
        }
    }
}

