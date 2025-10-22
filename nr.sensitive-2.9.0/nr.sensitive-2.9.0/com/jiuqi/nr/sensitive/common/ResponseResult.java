/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 */
package com.jiuqi.nr.sensitive.common;

import org.springframework.http.HttpStatus;

public class ResponseResult<T> {
    private HttpStatus status;
    private String msg;
    protected T data;

    public ResponseResult() {
    }

    public ResponseResult(HttpStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(HttpStatus.OK, "\u8bf7\u6c42\u6210\u529f", data);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<T>(HttpStatus.OK, msg, data);
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult<Object>(HttpStatus.INTERNAL_SERVER_ERROR, "\u540e\u7aef\u670d\u52a1\u5f02\u5e38\uff01", null);
    }

    public static <T> ResponseResult<T> error(Throwable throwable) {
        return new ResponseResult<String>(HttpStatus.INTERNAL_SERVER_ERROR, "\u540e\u7aef\u670d\u52a1\u5f02\u5e38!", ResponseResult.parseException(throwable));
    }

    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<Object>(HttpStatus.INTERNAL_SERVER_ERROR, msg, null);
    }

    public static <T> ResponseResult<T> error(String msg, Throwable throwable) {
        return new ResponseResult<String>(HttpStatus.INTERNAL_SERVER_ERROR, msg, ResponseResult.parseException(throwable));
    }

    public static <T> ResponseResult<T> ofPage(int pageSize, int currentPage, T data) {
        return new PageResponseResult().page(pageSize, currentPage).ofData(data);
    }

    private static String parseException(Throwable throwable) {
        StackTraceElement[] trace;
        StringBuffer errorMsg = new StringBuffer("");
        if (throwable == null) {
            return errorMsg.toString();
        }
        for (StackTraceElement element : trace = throwable.getStackTrace()) {
            String className = element.getClassName();
            if (!className.contains("com.jiuqi.nr")) continue;
            String method = element.getMethodName();
            int line = element.getLineNumber();
            errorMsg.append("\u9519\u8bef:");
            errorMsg.append(throwable.toString()).append(",");
            errorMsg.append("\u5b9a\u4f4d:\u7c7b\u3010").append(className).append("\u3011");
            errorMsg.append("\u65b9\u6cd5\u3010").append(method).append("\u3011");
            errorMsg.append("\u884c\u53f7\u3010").append(line).append("\u3011");
            break;
        }
        if (errorMsg.length() <= 0) {
            errorMsg.append(throwable.toString());
        }
        return errorMsg.toString();
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    static final class PageResponseResult<T>
    extends ResponseResult<T> {
        private int currentPage;
        private int pageSize;

        PageResponseResult() {
        }

        public PageResponseResult<T> page(int pageSize, int currentPage) {
            this.pageSize = pageSize;
            this.currentPage = currentPage;
            return this;
        }

        public PageResponseResult<T> ofData(T data) {
            this.data = data;
            return this;
        }

        public int getCurrentPage() {
            return this.currentPage;
        }

        public int getPageSize() {
            return this.pageSize;
        }
    }
}

