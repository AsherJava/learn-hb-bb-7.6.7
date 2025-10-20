/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.datav.chart.ChartException
 *  com.jiuqi.nvwa.datav.chart.ChartMapException
 *  com.jiuqi.nvwa.datav.dashboard.controller.R
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.theme.exception.DashboardThemeException
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nvwa.datav.dashboard.exception;

import com.jiuqi.nvwa.datav.chart.ChartException;
import com.jiuqi.nvwa.datav.chart.ChartMapException;
import com.jiuqi.nvwa.datav.dashboard.controller.R;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.theme.exception.DashboardThemeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages={"com.jiuqi.nvwa.datav.dashboard", "com.jiuqi.nvwa.datav.chart"})
@Order(value=-2147483648)
public class DashboardExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DashboardExceptionHandler.class);

    @ExceptionHandler(value={DashboardException.class})
    @ResponseBody
    public String handleDashboardException(DashboardException e) {
        this.logger.error("\u5973\u5a32\u4eea\u8868\u76d8\uff1a" + e.getMessage(), e);
        e.printStackTrace();
        return R.error((int)500, (String)e.getMessage()).jsonString();
    }

    @ExceptionHandler(value={ChartException.class})
    @ResponseBody
    public String handleChartException(ChartException e) {
        this.logger.error("\u5973\u5a32\u4eea\u8868\u76d8\u56fe\u8868\uff1a" + e.getMessage(), e);
        e.printStackTrace();
        return R.error((int)500, (String)e.getMessage()).jsonString();
    }

    @ExceptionHandler(value={DashboardThemeException.class})
    @ResponseBody
    public String handleDashboardThemeException(DashboardThemeException e) {
        this.logger.error("\u5973\u5a32\u4eea\u8868\u76d8\u4e3b\u9898\uff1a" + e.getMessage(), (Throwable)e);
        e.printStackTrace();
        return R.error((int)500, (String)e.getMessage()).jsonString();
    }

    @ExceptionHandler(value={ChartMapException.class})
    @ResponseBody
    public String handleChartMapException(ChartMapException e) {
        this.logger.error("\u5973\u5a32\u4eea\u8868\u76d8\u5730\u56fe\u7ba1\u7406\uff1a" + e.getMessage(), (Throwable)e);
        e.printStackTrace();
        return R.error((int)500, (String)e.getMessage()).jsonString();
    }
}

