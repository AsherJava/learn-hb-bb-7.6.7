/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.common.DefineQuerySqlParseInfoVO
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.BadSqlGrammarException
 *  org.springframework.jdbc.CannotGetJdbcConnectionException
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.jiuqi.va.query.handle;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.common.DefineQuerySqlParseInfoVO;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(value={"com.jiuqi.va.query"})
@Order(value=-2147483648)
public class DCQueryExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(DCQueryExceptionHandler.class);

    @ExceptionHandler(value={DefinedQueryRuntimeException.class})
    public <T> BusinessResponseEntity<T> handleRuntimeException(DefinedQueryRuntimeException e) {
        LOGGER.error(e.getMessage(), e);
        return BusinessResponseEntity.error((String)e.getMessage());
    }

    @ExceptionHandler(value={DefinedQuerySqlException.class})
    public BusinessResponseEntity<DefineQuerySqlParseInfoVO> handleSqlException(DefinedQuerySqlException definedQuerySqlException) {
        LOGGER.error(definedQuerySqlException.getMessage(), (Throwable)definedQuerySqlException);
        return BusinessResponseEntity.handleSqlException((DefinedQuerySqlException)definedQuerySqlException);
    }

    @ExceptionHandler(value={Exception.class})
    public <T> BusinessResponseEntity<T> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return BusinessResponseEntity.error((String)e.getMessage());
    }

    @ExceptionHandler(value={DataAccessException.class})
    public <T> BusinessResponseEntity<T> handleJdbcAccessException(DataAccessException ex) {
        LOGGER.error("\u6570\u636e\u5e93\u8bbf\u95ee\u5f02\u5e38\uff1a{}", (Object)ex.getMessage(), (Object)ex);
        String message = "\u7cfb\u7edf\u5904\u7406\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\u9519\u8bef\u3002";
        if (ex instanceof BadSqlGrammarException) {
            message = "SQL\u8bed\u6cd5\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\u67e5\u8be2\u8bed\u53e5";
        } else if (ex instanceof CannotGetJdbcConnectionException) {
            message = "\u65e0\u6cd5\u8fde\u63a5\u5230\u6570\u636e\u5e93\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u5e93\u914d\u7f6e\u6216\u670d\u52a1\u72b6\u6001";
        }
        return BusinessResponseEntity.error((String)message);
    }
}

