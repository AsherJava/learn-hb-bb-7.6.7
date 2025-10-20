/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.Insert
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.basedata.domain.BaseDataCommonlyUsedDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.Set;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VaBaseDataCommonlyUsedDao
extends BaseOptMapper<BaseDataCommonlyUsedDO> {
    @Select(value={"select count(0) as cnt from BASEDATA_COMMONLY_FLAG where USERID=#{createuser} and DEFINENAME=#{tableName}"})
    public int countFlag(BaseDataDTO var1);

    @Insert(value={"insert into BASEDATA_COMMONLY_FLAG(ID,USERID,DEFINENAME) values (#{id},#{createuser},#{tableName})"})
    public int addFlag(BaseDataDTO var1);

    @Delete(value={"delete from BASEDATA_COMMONLY_FLAG where USERID=#{createuser} and DEFINENAME=#{tableName}"})
    public int removeFlag(BaseDataDTO var1);

    @Select(value={"select OBJECTCODE from BASEDATA_COMMONLY_USED where USERID=#{createuser} and DEFINENAME=#{tableName}"})
    public Set<String> listObjectcode(BaseDataDTO var1);
}

