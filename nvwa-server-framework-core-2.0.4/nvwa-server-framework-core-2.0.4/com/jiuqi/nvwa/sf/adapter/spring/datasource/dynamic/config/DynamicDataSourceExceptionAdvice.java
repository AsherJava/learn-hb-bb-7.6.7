/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.Result
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config;

import com.jiuqi.np.core.model.Result;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DynamicDataSourceInitFailedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class DynamicDataSourceExceptionAdvice {
    @ExceptionHandler(value={DataSourceCreationException.class})
    public Result<String> handleDataSourceCreationException(DataSourceCreationException err) {
        return Result.failed((String)("\u6570\u636e\u6e90\u521b\u5efa\u5931\u8d25: " + err.getMessage()));
    }

    @ExceptionHandler(value={DataSourceNotFoundException.class})
    public Result<String> handleDataSourceNotFoundException(DataSourceNotFoundException err) {
        return Result.failed((String)("\u6570\u636e\u6e90\u4e0d\u5b58\u5728: " + err.getMessage()));
    }

    @ExceptionHandler(value={DynamicDataSourceInitFailedException.class})
    public Result<String> handleDynamicDataSourceInitFailedException(DynamicDataSourceInitFailedException err) {
        return Result.failed((String)("\u52a8\u6001\u6570\u636e\u6e90\u521d\u59cb\u5316\u5931\u8d25: " + err.getMessage()));
    }
}

