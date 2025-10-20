/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.helper.BizViewTemplateDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface IMetaDataHelperDao
extends BaseOptMapper<BizViewTemplateDO> {
    @Update(value={"update biz_view_template set template = #{template},createtime = #{createTime} where name = #{name} "})
    public void updateByName(BizViewTemplateDO var1);

    @SelectProvider(type=IMetaDataHelperProvider.class, method="list")
    public List<BizViewTemplateDO> list(BizViewTemplateDO var1);

    public static class IMetaDataHelperProvider {
        public String list(BizViewTemplateDO bizViewTemplateDO) {
            SQL sql = new SQL();
            String name = bizViewTemplateDO.getName();
            if (StringUtils.hasText(name)) {
                String key = "%" + name + "%";
                bizViewTemplateDO.setName(key);
                sql.SELECT("*");
                sql.FROM("biz_view_template");
                sql.WHERE("name like #{name} ");
                if (StringUtils.hasText(bizViewTemplateDO.getBizType())) {
                    sql.WHERE("bizType = #{bizType}");
                }
                sql.ORDER_BY("createtime desc");
                return sql.toString();
            }
            if (StringUtils.hasText(bizViewTemplateDO.getBizType())) {
                sql.SELECT("*");
                sql.FROM("biz_view_template");
                if (bizViewTemplateDO.getBizType().equalsIgnoreCase("Bill")) {
                    sql.WHERE("bizType = #{bizType} or bizType is null");
                } else {
                    sql.WHERE("bizType = #{bizType}");
                }
                sql.ORDER_BY("createtime desc");
                return sql.toString();
            }
            sql.SELECT("*");
            sql.FROM("biz_view_template");
            sql.WHERE("bizType is null");
            sql.ORDER_BY("createtime desc");
            return sql.toString();
        }
    }
}

