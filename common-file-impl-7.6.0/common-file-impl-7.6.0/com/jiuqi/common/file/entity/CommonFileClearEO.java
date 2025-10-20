/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.common.file.entity;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_COMMON_FILE_CLEAR", title="\u6587\u4ef6\u6a21\u5757\u4e34\u65f6\u6587\u4ef6\u5b9a\u65f6\u6e05\u7406\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_COMMONFILECLEAR_ID", columnsFields={"ID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class CommonFileClearEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_COMMON_FILE_CLEAR";
    @DBColumn(nameInDB="ossFileKey", title="\u4e34\u65f6\u6587\u4ef6\u5bf9\u5e94\u7684oss\u5b58\u50a8key", dbType=DBColumn.DBType.Varchar, length=36)
    private String ossFileKey;
    @DBColumn(nameInDB="ossBucket", title="\u4e34\u65f6\u6587\u4ef6\u5bf9\u5e94\u7684oss\u5b58\u50a8\u6876", dbType=DBColumn.DBType.Varchar, length=200)
    private String ossBucket;
    @DBColumn(nameInDB="createtimestamp", title="\u4e34\u65f6\u6587\u4ef6\u751f\u6210\u65f6\u7684\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Long)
    private Long createtimestamp;
    @DBColumn(nameInDB="memo", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=100)
    private String memo;

    public CommonFileClearEO() {
    }

    public CommonFileClearEO(String ossFileKey, String ossBucket) {
        this(ossFileKey, ossBucket, null);
    }

    public CommonFileClearEO(String ossFileKey, String ossBucket, String memo) {
        this.setId(UUIDUtils.newUUIDStr());
        this.createtimestamp = DateUtils.now().getTime();
        this.ossFileKey = ossFileKey;
        this.ossBucket = ossBucket;
        this.memo = memo;
    }

    public String getOssFileKey() {
        return this.ossFileKey;
    }

    public void setOssFileKey(String ossFileKey) {
        this.ossFileKey = ossFileKey;
    }

    public Long getCreatetimestamp() {
        return this.createtimestamp;
    }

    public void setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
    }

    public String getOssBucket() {
        return this.ossBucket;
    }

    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

