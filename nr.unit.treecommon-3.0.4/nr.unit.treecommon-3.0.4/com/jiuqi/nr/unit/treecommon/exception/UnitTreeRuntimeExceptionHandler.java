/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.CodeEnum
 *  com.jiuqi.np.core.model.Result
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.unit.treecommon.exception;

import com.jiuqi.np.core.model.CodeEnum;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(value=1000)
@Configuration
@ControllerAdvice
public class UnitTreeRuntimeExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(UnitTreeRuntimeExceptionHandler.class);

    @ExceptionHandler(value={UnitTreeRuntimeException.class})
    @ResponseBody
    public Result<String> globalException(UnitTreeRuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(500);
        LOGGER.error("requestURL:{}", (Object)request.getRequestURL());
        LOGGER.error("\u672a\u77e5\u5f02\u5e38\uff01", e);
        Locale locale = LocaleContextHolder.getLocale();
        String msg = e.getMessage();
        String data = "\u7cfb\u7edf\u51fa\u73b0\u672a\u77e5\u5f02\u5e38";
        if (locale.getLanguage().equalsIgnoreCase(new Locale("en").getLanguage())) {
            msg = "The server is wandering, please try again later!";
            data = "Unknown exception occurred in the system";
        }
        return Result.failedWith((String)CodeEnum.INTERNAL_SERVER_ERROR.getCode(), (String)msg, (Object)data);
    }
}

