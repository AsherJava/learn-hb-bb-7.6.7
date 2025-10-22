/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.clbr.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClbrBillDao
extends IBaseSqlGenericDAO<ClbrBillEO> {
    public List<ClbrBillEO> listInitiatorNotConfirmByUser(ClbrBillSsoConditionDTO var1);

    public List<ClbrBillEO> listByIds(Set<String> var1);

    public PageInfo<ClbrBillEO> listInitiatorByRelation(ClbrBillGenerateQueryDTO var1);

    public List<ClbrBillEO> listBySrcIds(List<String> var1);

    public ClbrBillEO queryBySrcId(String var1);

    public PageInfo<ClbrBillEO> listNotConfirmInitiatorPageByCondition(ClbrProcessCondition var1);

    public PageInfo<ClbrBillEO> listNotConfirmReceiverPageByCondition(ClbrProcessCondition var1);

    public PageInfo<ClbrBillEO> listClbrBillByClbrCodeAndClbrType(ClbrProcessCondition var1, ClbrBillTypeEnum var2);

    public Map<ClbrBillStateEnum, Integer> getClbrBillCountByRelationCodeAndBillTypeGroupByBillState(Set<String> var1, Integer var2);

    public Map<String, Double> getClbrBillAmountSumByRelationCodeAndBillType(Set<String> var1, Integer var2);

    public PageInfo<ClbrBillEO> listClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon var1, ClbrBillStateEnum var2);

    public PageInfo<ClbrBillEO> listAllClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon var1);

    public List<ClbrBillEO> listByClbrCode(Set<String> var1);

    public ClbrBillEO getClbrBillByClbrBillCode(String var1);

    public PageInfo<ClbrBillEO> queryArbitrationListPage(ClbrArbitrationQueryParamDTO var1);

    public List<ClbrBillEO> selectArbitrationTodoNum(String var1);

    public List<ClbrBillEO> selectUnClbrReceBillCode(Set<String> var1);

    public List<ClbrBillEO> queryBillMessage(ClbrBillReceBillCodeDTO var1);
}

