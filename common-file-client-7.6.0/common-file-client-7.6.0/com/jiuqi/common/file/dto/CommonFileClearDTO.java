/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.file.dto;

public class CommonFileClearDTO {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_COMMON_FILE_CLEAR";
    private String id;
    private String ossFileKey;
    private String ossBucket;
    private Long createtimestamp;
    private String memo;

    public CommonFileClearDTO() {
    }

    public CommonFileClearDTO(String id, String ossBucket, String ossFileKey, Long createtimestamp) {
        this(id, ossBucket, ossFileKey, createtimestamp, null);
    }

    public CommonFileClearDTO(String id, String ossBucket, String ossFileKey, Long createtimestamp, String memo) {
        this.id = id;
        this.ossBucket = ossBucket;
        this.ossFileKey = ossFileKey;
        this.createtimestamp = createtimestamp;
        this.memo = memo;
    }

    public String getOssFileKey() {
        return this.ossFileKey;
    }

    public void setOssFileKey(String ossFileKey) {
        this.ossFileKey = ossFileKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOssBucket() {
        return this.ossBucket;
    }

    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }

    public Long getCreatetimestamp() {
        return this.createtimestamp;
    }

    public void setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
    }
}

