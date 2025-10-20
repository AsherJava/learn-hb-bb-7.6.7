/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VaEnumDataDao
extends BaseOptMapper<EnumDataDO> {
    @Select(value={"select distinct BIZTYPE,DESCRIPTION from ENUMDATA_INFO order by BIZTYPE asc"})
    public List<EnumDataDO> listBiztype(EnumDataDTO var1);

    @Select(value={"select * from ENUMDATA_INFO where VER >= #{ver, jdbcType=NUMERIC}"})
    public List<EnumDataDO> selectByStartVer(EnumDataDTO var1);

    @Select(value={"select * from ENUMDATA_INFO where VER > #{ver, jdbcType=NUMERIC}"})
    public List<EnumDataDO> selectGreaterVer(EnumDataDTO var1);
}

