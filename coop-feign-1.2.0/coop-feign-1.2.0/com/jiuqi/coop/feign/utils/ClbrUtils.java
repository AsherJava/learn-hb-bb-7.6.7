/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemResultDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.coop.feign.utils;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.coop.feign.config.domain.ClbrRecordDO;
import com.jiuqi.coop.feign.exception.ClbrBusinessException;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class ClbrUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(ClbrUtils.class);

    private ClbrUtils() {
    }

    public static boolean isCoop(BillModelImpl modelImpl) {
        DataImpl modelData = modelImpl.getData();
        DataFieldImpl interFlag = (DataFieldImpl)modelData.getMasterTable().getFields().find("INTERFLAG");
        if (interFlag == null) {
            return false;
        }
        return modelImpl.getMaster().getBoolean("INTERFLAG");
    }

    public static List<ClbrBillPushItemParamDTO> buildClbrPushItemDTO(List<ClbrRecordDO> clbrRecord, String addOrDelete) {
        return clbrRecord.stream().map(record -> {
            ClbrBillPushItemParamDTO clbrBillPushItemParamDTO = new ClbrBillPushItemParamDTO();
            BeanUtils.copyProperties(record, clbrRecord);
            clbrBillPushItemParamDTO.setOperate(addOrDelete);
            clbrBillPushItemParamDTO.setClbrBillType(Integer.valueOf(1));
            clbrBillPushItemParamDTO.setSrcId(record.getBillrowid());
            clbrBillPushItemParamDTO.setOppSrcId(record.getOppsrcid());
            String userName = record.getReceiveuser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userName);
            AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
            UserDO userDO = authUserClient.get(userDTO);
            if (userDO == null) {
                LOGGER.error("\u751f\u5355\u6620\u5c04RECEIVEUSER(\u63a5\u6536\u65b9\u7528\u6237)\u914d\u7f6e\u516c\u5f0f\u6267\u884c\u7ed3\u679c\u4e3a:{}", (Object)userName);
                throw new ClbrBusinessException("\u63a5\u6536\u65b9\u7528\u6237\u5e94\u4e3a\u7528\u6237\u767b\u5f55\u540d\uff0c\u8bf7\u68c0\u67e5\u5355\u636e\u751f\u6210\u534f\u540c\u8bb0\u5f55\u5b57\u6bb5\u6620\u5c04\u914d\u7f6e");
            }
            clbrBillPushItemParamDTO.setUserName(userName);
            clbrBillPushItemParamDTO.setUserTitle(userDO.getName());
            clbrBillPushItemParamDTO.setRelation(record.getSendrelationcode());
            clbrBillPushItemParamDTO.setClbrType(record.getRececlbrtypecode());
            clbrBillPushItemParamDTO.setClbrBillCode(record.getRecebillcode());
            clbrBillPushItemParamDTO.setAmount(record.getRecemoney());
            clbrBillPushItemParamDTO.setOppRelation(record.getRecerelationcode());
            if (!StringUtils.hasText(record.getSendrelationcode())) {
                return null;
            }
            clbrBillPushItemParamDTO.setOppClbrBillCode(record.getSendbillcode());
            clbrBillPushItemParamDTO.setOppUserName(record.getSenduser());
            clbrBillPushItemParamDTO.setSn(record.getSendbillcode());
            clbrBillPushItemParamDTO.setSysCode("JQ_FSSC");
            clbrBillPushItemParamDTO.setOrgCode(record.getSendiator());
            clbrBillPushItemParamDTO.setOppOrgCode(record.getReceiver());
            return clbrBillPushItemParamDTO;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<ClbrBillPushItemParamDTO> buildSendDeleteDTO(List<ClbrRecordDO> clbrRecord) {
        return clbrRecord.stream().map(record -> {
            ClbrBillPushItemParamDTO clbrBillPushItemParamDTO = new ClbrBillPushItemParamDTO();
            clbrBillPushItemParamDTO.setOperate("delete");
            clbrBillPushItemParamDTO.setSrcId(record.getBillrowid());
            clbrBillPushItemParamDTO.setClbrBillCode(record.getRecebillcode());
            clbrBillPushItemParamDTO.setSysCode("JQ_FSSC");
            return clbrBillPushItemParamDTO;
        }).collect(Collectors.toList());
    }

    public static String checkSendAndGetBillCode(BillModelImpl model) {
        if (ClbrUtils.isCoop(model)) {
            String billCode = model.getMaster().getString("BILLCODE");
            String bizSource = model.getMaster().getString("BIZSOURCE");
            if ("08".equals(bizSource)) {
                return null;
            }
            return billCode;
        }
        return null;
    }

    public static ClbrBillPushParamDTO buildDeleteItems(String billCode, List<ClbrRecordDO> localSendRecordList) {
        ClbrBillPushParamDTO clbrBillPushParamDTO = new ClbrBillPushParamDTO();
        clbrBillPushParamDTO.setSn(billCode);
        clbrBillPushParamDTO.setSysCode("JQ_FSSC");
        List<ClbrBillPushItemParamDTO> deleteItems = ClbrUtils.buildSendDeleteDTO(localSendRecordList);
        clbrBillPushParamDTO.setItems(deleteItems);
        return clbrBillPushParamDTO;
    }

    public static void checkResult(BusinessResponseEntity<ClbrBillPushResultDTO> responseEntity) {
        boolean success = responseEntity.isSuccess();
        if (success) {
            ClbrBillPushResultDTO responseData = (ClbrBillPushResultDTO)responseEntity.getData();
            Boolean status = responseData.getStatus();
            if (Objects.equals(status, Boolean.FALSE)) {
                throw new ClbrBusinessException(responseData.getMessage());
            }
        } else {
            throw new ClbrBusinessException(responseEntity.getErrorMessage());
        }
    }

    public static void checkResult(BusinessResponseEntity<ClbrBillPushResultDTO> responseEntity, List<ClbrRecordDO> receLocalClbrRecords, boolean masterTableFlag) {
        boolean success = responseEntity.isSuccess();
        if (success) {
            ClbrBillPushResultDTO responseData = (ClbrBillPushResultDTO)responseEntity.getData();
            Boolean status = responseData.getStatus();
            if (Objects.equals(status, Boolean.FALSE)) {
                if (masterTableFlag) {
                    String message = ((ClbrBillPushItemResultDTO)responseData.getItems().get(0)).getMessage();
                    throw new ClbrBusinessException(message);
                }
                List results = responseData.getItems().stream().filter(item -> item.getStatus() == false).map(item -> {
                    String srcId = item.getSrcId();
                    Optional<ClbrRecordDO> matchRecord = receLocalClbrRecords.stream().filter(record -> record.getBillrowid().equals(srcId)).findAny();
                    if (matchRecord.isPresent()) {
                        ClbrRecordDO clbrRecordDO = matchRecord.get();
                        String rowId = clbrRecordDO.getBillrowid();
                        CheckResultImpl result = new CheckResultImpl();
                        result.setCheckMessage(item.getMessage());
                        DataTargetImpl target = new DataTargetImpl();
                        target.setTargetType(DataTargetType.DATAROW);
                        target.setTableName(clbrRecordDO.getClbrtbname());
                        target.setRowID((UUID)Convert.cast((Object)rowId, UUID.class));
                        ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
                        targetList.add(target);
                        result.setTargetList(targetList);
                        return result;
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
                throw new BillException("\u8c03\u7528\u534f\u540c\u63a5\u53e3\u53d1\u751f\u5f02\u5e38", results);
            }
        } else {
            throw new ClbrBusinessException(responseEntity.getErrorMessage());
        }
    }
}

