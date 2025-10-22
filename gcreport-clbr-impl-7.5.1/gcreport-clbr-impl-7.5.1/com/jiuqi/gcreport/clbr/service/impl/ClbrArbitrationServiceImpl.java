/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dao.ClbrSchemeDao;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum;
import com.jiuqi.gcreport.clbr.service.ClbrArbitrationService;
import com.jiuqi.gcreport.clbr.service.ClbrBillService;
import com.jiuqi.gcreport.clbr.util.ClbrTypePenetrationControlUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ClbrArbitrationServiceImpl
implements ClbrArbitrationService {
    @Autowired
    private ClbrBillDao clbrBillDao;
    @Autowired
    private ClbrBillService clbrBillService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ClbrSchemeDao clbrSchemeDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillDTO> getArbitrationListPage(ClbrArbitrationQueryParamDTO clbrArbitrationQueryParamDTO) {
        PageInfo<ClbrBillEO> clbrBillEOPageInfo = this.clbrBillDao.queryArbitrationListPage(clbrArbitrationQueryParamDTO);
        ClbrTypePenetrationControlUtils.validPenetrationControl(clbrBillEOPageInfo.getList());
        PageInfo<ClbrBillDTO> dtoPageInfo = ClbrBillConverter.convertEO2DTO(clbrBillEOPageInfo);
        return dtoPageInfo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelRejectArbitration(ClbrArbitrationCancel clbrArbitrationCancel) {
        Set clbrBillIds = clbrArbitrationCancel.getClbrBillIds();
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        for (ClbrBillEO clbrBillEO : clbrBillEOs) {
            clbrBillEO.setBillState(ClbrBillStateEnum.INIT.getCode());
            clbrBillEO.setRejectionMessage("");
            clbrBillEO.setRemark(clbrArbitrationCancel.getArbitrationReject());
        }
        int[] ints = this.clbrBillDao.updateBatch(clbrBillEOs);
        if (ints.length != clbrBillEOs.size()) {
            throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u4ef2\u88c1\u53d6\u6d88\u64cd\u4f5c\u5f02\u5e38");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Integer getArbitrationTodoNum(ClbrTodoCountVO clbrTodoCountVO) {
        ArrayList<ClbrBillEO> clbrBillEOArrayList;
        String userName = clbrTodoCountVO.getUserName();
        this.changeUserContext(userName);
        try {
            String shiroUtilUsername = ShiroUtil.getUser().getUsername();
            if (!StringUtils.hasText(shiroUtilUsername)) {
                throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u5f02\u5e38");
            }
            List<ClbrBillEO> clbrBillEOS = this.clbrBillDao.selectArbitrationTodoNum(shiroUtilUsername);
            if (CollectionUtils.isEmpty(clbrBillEOS)) {
                Integer n = 0;
                return n;
            }
            clbrBillEOArrayList = new ArrayList<ClbrBillEO>();
            for (ClbrBillEO clbrBillEO : clbrBillEOS) {
                String[] split = clbrBillEO.getArbitrationUserName().split(",");
                List<String> userNameStrings = Arrays.asList(split);
                if (!userNameStrings.contains(shiroUtilUsername)) continue;
                clbrBillEOArrayList.add(clbrBillEO);
            }
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return clbrBillEOArrayList.size();
    }

    private void changeUserContext(String userName) {
        Optional sysUser;
        User user = null;
        Optional userOptional = this.userService.findByUsername(userName);
        if (userOptional.isPresent()) {
            user = (User)userOptional.get();
        }
        if ((sysUser = this.systemUserService.findByUsername(userName)).isPresent()) {
            user = (User)sysUser.get();
        }
        Assert.isTrue(!ObjectUtils.isEmpty(user), "\u534f\u540c\u7cfb\u7edf\u627e\u4e0d\u5230\u7528\u6237\u540d\u4e3a[" + userName + "]\u7684\u7528\u6237\u4fe1\u606f\u3002");
        NpContextImpl contextImpl = new NpContextImpl();
        NpContextUser contextUser = new NpContextUser();
        contextUser.setName(user.getName());
        contextUser.setId(user.getId());
        contextUser.setNickname(user.getNickname());
        contextUser.setOrgCode(user.getOrgCode());
        contextUser.setDescription(user.getDescription());
        contextUser.setType(user.getUserType());
        contextImpl.setUser((ContextUser)contextUser);
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getName());
        contextImpl.setIdentity((ContextIdentity)contextIdentity);
        NpContextHolder.setContext((NpContext)contextImpl);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setId(user.getId());
        userDTO.setTenantName("__default_tenant__");
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getName());
        ShiroUtil.bindUser((UserLoginDTO)userDTO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelSynergy(ClbrBillRejectDTO clbrBillRejectDTO) {
        int[] i;
        Set clbrBillIds = clbrBillRejectDTO.getClbrBillIds();
        String rejectReason = clbrBillRejectDTO.getRejectReason();
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        ArrayList<ClbrBillEO> clbrRigidityBillEOs = new ArrayList<ClbrBillEO>();
        for (ClbrBillEO clbrBillEO : clbrBillEOs) {
            String clbrSchemeId = clbrBillEO.getClbrSchemeId();
            if (ObjectUtils.isEmpty(clbrSchemeId)) {
                return;
            }
            ClbrSchemeEO schemeEO = new ClbrSchemeEO();
            schemeEO.setId(clbrSchemeId);
            ClbrSchemeEO clbrSchemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)schemeEO);
            if (clbrSchemeEO == null) {
                return;
            }
            ClbrFlowControlEnum clbrFlowControlEnum = ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType());
            if (ClbrFlowControlEnum.WEAK.equals((Object)clbrFlowControlEnum)) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u534f\u540c\u65b9\u6848\u3010" + clbrSchemeEO.getTitle() + "\u3011\u6d41\u7a0b\u63a7\u5236\u4e3a\u67d4\u6027\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de\uff0c\u8bf7\u4fee\u6539\u8be5\u6761\u65b9\u6848\u7684\u6d41\u7a0b\u63a7\u5236\u4e3a\u521a\u6027\u540e\u9a73\u56de");
            }
            if (clbrBillEO.getVerifyedAmount() > 0.0 || ClbrBillStateEnum.PARTCONFIRM.getCode().equals(clbrBillEO.getBillState())) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u5355\u636e\u8fdb\u884c\u8fc7\u534f\u540c\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de");
            }
            clbrRigidityBillEOs.add(clbrBillEO);
        }
        HashSet<String> clbrNoArbitrationUserBillIds = new HashSet<String>();
        ArrayList<ClbrBillEO> clbrArbitrationUserBillEOs = new ArrayList<ClbrBillEO>();
        for (ClbrBillEO clbrRigidityBillEO : clbrRigidityBillEOs) {
            String arbitrationUserName = clbrRigidityBillEO.getArbitrationUserName();
            if (!StringUtils.hasText(arbitrationUserName)) {
                clbrNoArbitrationUserBillIds.add(clbrRigidityBillEO.getId());
                continue;
            }
            clbrRigidityBillEO.setBillState(ClbrBillStateEnum.ARBITRATION.getCode());
            clbrRigidityBillEO.setRejectionMessage(rejectReason);
            clbrArbitrationUserBillEOs.add(clbrRigidityBillEO);
        }
        if (!CollectionUtils.isEmpty(clbrNoArbitrationUserBillIds)) {
            ClbrBillRejectDTO clbrBillRejectNoUserDTO = new ClbrBillRejectDTO();
            clbrBillRejectNoUserDTO.setClbrBillIds(clbrNoArbitrationUserBillIds);
            clbrBillRejectNoUserDTO.setRejectReason(rejectReason);
            this.clbrBillService.rejectClbrBill(clbrBillRejectNoUserDTO);
        }
        if (!CollectionUtils.isEmpty(clbrArbitrationUserBillEOs) && (i = this.clbrBillDao.updateBatch(clbrArbitrationUserBillEOs)).length != clbrArbitrationUserBillEOs.size()) {
            throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u534f\u540c\u9a73\u56de\u64cd\u4f5c\u5f02\u5e38");
        }
    }

    @Override
    public void consentArbitration(Set<String> clbrBillIds) {
        this.clbrBillService.rejectArbitrationClbrBill(clbrBillIds);
    }
}

