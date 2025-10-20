/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogVO
 *  com.jiuqi.gcreport.billcore.vo.AccountLockVO
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.billcore.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.dao.AccountLockDao;
import com.jiuqi.gcreport.billcore.dao.AccountLockLogDao;
import com.jiuqi.gcreport.billcore.entity.AccountLockEO;
import com.jiuqi.gcreport.billcore.entity.AccountLockLogEO;
import com.jiuqi.gcreport.billcore.enums.AccountLockInfoEnum;
import com.jiuqi.gcreport.billcore.service.AccountLockService;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogVO;
import com.jiuqi.gcreport.billcore.vo.AccountLockVO;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class AccountLockServiceImpl
implements AccountLockService {
    private static final Logger logger = LoggerFactory.getLogger(AccountLockServiceImpl.class);
    @Autowired
    private AccountLockDao accountLockDao;
    @Autowired
    private AccountLockLogDao accountLockLogDao;

    @Override
    public List<AccountLockVO> listAccountLocks(int acctYear) {
        List<AccountLockEO> accountLockEOS = this.accountLockDao.listAccountLocks(acctYear);
        if (CollectionUtils.isEmpty(accountLockEOS)) {
            return null;
        }
        Set accountTypeSet = accountLockEOS.stream().map(item -> item.getAccountType()).collect(Collectors.toSet());
        List<String> accountTypeList = Arrays.asList(AccountLockInfoEnum.INVEST.getCode(), AccountLockInfoEnum.ASSET.getCode(), AccountLockInfoEnum.LEASE.getCode());
        accountTypeList.forEach(accountType -> {
            if (!accountTypeSet.contains(accountType)) {
                AccountLockEO accountLockEO = new AccountLockEO();
                accountLockEO.setAccountType((String)accountType);
                accountLockEOS.add(accountLockEO);
            }
        });
        List<AccountLockVO> accountLockVOS = accountLockEOS.stream().map(eo -> {
            AccountLockVO accountLockAO = new AccountLockVO();
            BeanUtils.copyProperties(eo, accountLockAO);
            accountLockAO.setStatusName(null == AccountLockInfoEnum.getEnumTitleByValue(eo.getStatus()) ? AccountLockInfoEnum.UNLOCK.getTitle() : AccountLockInfoEnum.getEnumTitleByValue(eo.getStatus()));
            accountLockAO.setAccountName(AccountLockInfoEnum.getEnumTitleByValue(eo.getAccountType()));
            return accountLockAO;
        }).collect(Collectors.toList());
        return accountLockVOS;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateLock(List<String> lockAccountTypeList, boolean lockFlag, int acctYear) {
        lockAccountTypeList.forEach(accountType -> this.accountLockDao.deleteByAccountType((String)accountType, acctYear));
        List accountLockList = lockAccountTypeList.stream().map(accountLockType -> {
            AccountLockEO accountLockEO = new AccountLockEO();
            accountLockEO.setId(UUIDOrderUtils.newUUIDStr());
            accountLockEO.setAccountType((String)accountLockType);
            accountLockEO.setModifiedUser(NpContextHolder.getContext().getUserName());
            accountLockEO.setModifiedTime(new Date());
            accountLockEO.setStatus(true == lockFlag ? "1" : "0");
            if (accountLockType.equals(AccountLockInfoEnum.INVEST.getCode())) {
                accountLockEO.setAcctYear(acctYear);
            }
            return accountLockEO;
        }).collect(Collectors.toList());
        this.accountLockDao.addBatch(accountLockList);
        List accountLockLogList = lockAccountTypeList.stream().map(lockAccountType -> {
            AccountLockLogEO accountLockLogEO = new AccountLockLogEO();
            accountLockLogEO.setId(UUIDOrderUtils.newUUIDStr());
            accountLockLogEO.setAccountType((String)lockAccountType);
            accountLockLogEO.setModifiedUser(NpContextHolder.getContext().getUserName());
            accountLockLogEO.setModifiedTime(new Date());
            accountLockLogEO.setOperating(true == lockFlag ? "\u9501\u5b9a" : "\u5f00\u542f");
            return accountLockLogEO;
        }).collect(Collectors.toList());
        this.accountLockLogDao.addBatch(accountLockLogList);
    }

    @Override
    public PageInfo<AccountLockLogVO> listAccountLockLogs(AccountLockLogQueryParam queryParam) {
        PageInfo<AccountLockLogEO> eoPageInfo = this.accountLockLogDao.listAccountLockLogs(queryParam);
        if (eoPageInfo.getSize() == 0) {
            return PageInfo.empty();
        }
        List accountLockLogVOS = eoPageInfo.getList().stream().map(eo -> {
            AccountLockLogVO accountLockLogVO = new AccountLockLogVO();
            BeanUtils.copyProperties(eo, accountLockLogVO);
            accountLockLogVO.setAccountName(AccountLockInfoEnum.getEnumTitleByValue(eo.getAccountType()));
            return accountLockLogVO;
        }).collect(Collectors.toList());
        return PageInfo.of(accountLockLogVOS, (int)eoPageInfo.getSize());
    }

    @Override
    public String getLockStatus(String accountType, Integer acctYear) {
        return this.accountLockDao.getLockStatus(accountType, acctYear);
    }
}

