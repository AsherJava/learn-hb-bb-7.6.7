/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.CopyFileCache
 *  com.jiuqi.va.attachment.domain.CopyProgressDO
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.biz.front.FrontModelDefine
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.extend.service.impl;

import com.jiuqi.va.attachment.config.CopyFileCache;
import com.jiuqi.va.attachment.domain.CopyProgressDO;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.extend.domain.BillReuseField;
import com.jiuqi.va.extend.service.BillCopyActionExtend;
import com.jiuqi.va.extend.service.VaBillExtendService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class VaBillExtendServiceImpl
implements VaBillExtendService {
    @Autowired(required=false)
    private List<BillCopyActionExtend> billCopyActionExtends;
    @Autowired(required=false)
    private StringRedisTemplate redisTemplate;
    private static final List<String> NOT_COPY_FIELDS = new ArrayList<String>();

    @Override
    public List<BillReuseField> filterFields(BillDefine billDefine) {
        FrontModelDefine view = new FrontModelDefine((ModelDefine)billDefine, false, "view");
        Map tableFields = view.getTableFields();
        if (CollectionUtils.isEmpty(tableFields)) {
            return new ArrayList<BillReuseField>();
        }
        List<BillReuseField> fields = new ArrayList<BillReuseField>();
        for (String tableName : tableFields.keySet()) {
            Set strings = (Set)tableFields.get(tableName);
            if (CollectionUtils.isEmpty(strings)) continue;
            DataTableDefine dataTableDefine = (DataTableDefine)billDefine.getData().getTables().get(tableName);
            for (String field : strings) {
                DataFieldDefine define = (DataFieldDefine)dataTableDefine.getFields().get(field);
                BillReuseField billReuseField = new BillReuseField();
                fields.add(billReuseField);
                billReuseField.setId(define.getName() + dataTableDefine.getName());
                billReuseField.setFieldCode(define.getName());
                billReuseField.setTableCode(dataTableDefine.getName());
                billReuseField.setFieldName(define.getTitle());
                billReuseField.setTableName(dataTableDefine.getTitle());
                billReuseField.setMappingType(define.getRefTableType());
            }
        }
        Iterator iterator = fields.iterator();
        while (iterator.hasNext()) {
            BillReuseField next = (BillReuseField)iterator.next();
            if (!NOT_COPY_FIELDS.contains(next.getFieldCode())) continue;
            iterator.remove();
        }
        if (this.billCopyActionExtends == null) {
            return fields;
        }
        for (BillCopyActionExtend billCopyActionExtend : this.billCopyActionExtends) {
            fields = billCopyActionExtend.filterFields(fields);
        }
        return fields;
    }

    @Override
    public CopyProgressDO getCopyProgress(String key) {
        if (EnvConfig.getRedisEnable()) {
            String s = (String)this.redisTemplate.opsForValue().get((Object)key);
            return (CopyProgressDO)JSONUtil.parseObject((String)s, CopyProgressDO.class);
        }
        return (CopyProgressDO)CopyFileCache.progress.get(key);
    }

    static {
        NOT_COPY_FIELDS.add("ID");
        NOT_COPY_FIELDS.add("VER");
        NOT_COPY_FIELDS.add("BILLCODE");
        NOT_COPY_FIELDS.add("BILLDATE");
        NOT_COPY_FIELDS.add("CREATETIME");
        NOT_COPY_FIELDS.add("CREATEUSER");
        NOT_COPY_FIELDS.add("DEFINECODE");
        NOT_COPY_FIELDS.add("BILLSTATE");
        NOT_COPY_FIELDS.add("BIZSTATE");
        NOT_COPY_FIELDS.add("MASTERID");
    }
}

