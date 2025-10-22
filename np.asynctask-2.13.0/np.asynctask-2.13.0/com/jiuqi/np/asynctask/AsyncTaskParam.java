/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import java.io.Serializable;

public class AsyncTaskParam
implements Serializable {
    private static final long serialVersionUID = -6322978877180574128L;
    private volatile String contentType = "application/octet-stream";
    private volatile String contentEncoding;
    public static final String CONTENT_TYPE_BYTES = "application/octet-stream";
    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
    public static final String CONTENT_TYPE_SERIALIZED_OBJECT = "application/x-java-serialized-object";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_JSON_ALT = "text/x-json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private final byte[] body;

    public AsyncTaskParam(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return this.body;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }
}

