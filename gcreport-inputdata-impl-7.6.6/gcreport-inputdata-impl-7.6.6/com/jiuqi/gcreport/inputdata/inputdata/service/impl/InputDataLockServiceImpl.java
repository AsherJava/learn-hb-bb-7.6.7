/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataLockDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataNoDependServiceDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataLockEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class InputDataLockServiceImpl
implements InputDataLockService {
    private InputDataNoDependServiceDao inputDataDao;
    private InputDataLockDao inputDataLockDao;
    private Logger logger = LoggerFactory.getLogger(InputDataLockServiceImpl.class);
    private final char[] lowerHexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public InputDataLockServiceImpl(InputDataNoDependServiceDao inputDataDao, InputDataLockDao inputDataLockDao) {
        this.inputDataDao = inputDataDao;
        this.inputDataLockDao = inputDataLockDao;
    }

    @Override
    public String tryLock(Collection<String> inputItemIds, InputWriteNecLimitCondition inputWriteNecLimitCondition, String lockSrc) {
        String lockId = this.tryLock(inputItemIds, inputWriteNecLimitCondition, 15L, TimeUnit.SECONDS, lockSrc);
        if (StringUtils.isEmpty(lockId)) {
            ((InputDataLockServiceImpl)SpringContextUtils.getBean(InputDataLockServiceImpl.class)).deleteExpiredData();
        }
        return lockId;
    }

    @Override
    public String tryLock(Collection<String> inputItemIds, InputWriteNecLimitCondition inputWriteNecLimitCondition, long timeout, TimeUnit unit, String lockSrc) {
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return "";
        }
        String ip = DistributionManager.getInstance().self().getIp();
        String firstInputItemId = inputItemIds.stream().findFirst().get();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusNanos(unit.toNanos(timeout));
        while (true) {
            String lockId = UUIDUtils.newUUIDStr();
            try {
                return this.saveInputDataLock(inputItemIds, inputWriteNecLimitCondition, lockId, lockSrc, ip);
            }
            catch (Exception e) {
                try {
                    this.logger.debug(String.format("\u5185\u90e8\u8868\u52a0\u9501\u5931\u8d25\uff0c \u7ef4\u5ea6\u4fe1\u606f: %1$s ,inputDataIds: %2$s\uff0c\u8be6\u60c5\uff1a", inputWriteNecLimitCondition.getCurrenctCode(), inputItemIds), e);
                    ((InputDataLockServiceImpl)SpringContextUtils.getBean(InputDataLockServiceImpl.class)).deleteExpiredData();
                    TimeUnit.MILLISECONDS.sleep(300L);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    return null;
                }
                if (LocalDateTime.now().isBefore(endTime)) continue;
                return null;
            }
            break;
        }
    }

    public String saveInputDataLock(Collection<String> inputItemIds, InputWriteNecLimitCondition inputWriteNecLimitCondition, String lockId, String lockSrc, String ip) {
        List<InputDataEO> inputItems = this.inputDataDao.queryCheckOffsetGroupId(inputItemIds, inputWriteNecLimitCondition);
        if (inputItemIds.size() != inputItems.size()) {
            this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u4e0a\u9501\uff0c\u5f53\u524d\u5185\u90e8\u8868\u6570\u636e\u4e0e\u67e5\u8be2\u5185\u90e8\u8868\u6570\u636e\u6570\u91cf\u4e0d\u4e00\u81f4\uff0c\u5185\u90e8\u8868id\uff1a" + inputItemIds.toString() + " \u7ef4\u5ea6\u4fe1\u606f\uff1a" + inputWriteNecLimitCondition.toString());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.offsetlockinputdatamsg"));
        }
        ((InputDataLockServiceImpl)SpringContextUtils.getBean(InputDataLockServiceImpl.class)).lockInputDataItems(inputItems, lockId, lockSrc, ip);
        try {
            List<InputDataEO> inputLockItems = this.inputDataDao.queryCheckOffsetGroupIdByLockId(lockId, inputWriteNecLimitCondition.getTaskId());
            ((InputDataLockServiceImpl)SpringContextUtils.getBean(InputDataLockServiceImpl.class)).lockBaseOnLockedItem(inputLockItems, lockId);
        }
        catch (Exception e) {
            ((InputDataLockServiceImpl)SpringContextUtils.getBean(InputDataLockServiceImpl.class)).unlock(lockId);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return lockId;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public String lockInputDataItems(List<InputDataEO> inputItems, String lockId, String lockSrc, String ip) {
        HashSet groupOffsetGroupIds = new HashSet();
        HashSet groupCheckGroupIds = new HashSet();
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        long createTime = System.currentTimeMillis();
        List inputDataLockItems = inputItems.stream().map(inputItem -> {
            InputDataLockEO inputDataLock = new InputDataLockEO();
            inputDataLock.setLockId(lockId);
            inputDataLock.setInputItemId(inputItem.getId());
            if (!StringUtils.isEmpty(inputItem.getOffsetGroupId()) && groupOffsetGroupIds.add(inputItem.getOffsetGroupId())) {
                inputDataLock.setOffsetGroupId(inputItem.getOffsetGroupId());
            } else {
                inputDataLock.setOffsetGroupId(this.newUuidStrEndWithSpecial());
            }
            if (!StringUtils.isEmpty(inputItem.getCheckGroupId()) && groupCheckGroupIds.add(inputItem.getCheckGroupId())) {
                inputDataLock.setCheckGroupId(inputItem.getCheckGroupId());
            } else {
                inputDataLock.setCheckGroupId(this.newUuidStrEndWithSpecial());
            }
            inputDataLock.setLockSrc(lockSrc);
            inputDataLock.setIpAddress(ip);
            inputDataLock.setUserName(user.getName());
            inputDataLock.setCreateTime(createTime);
            inputDataLock.setCurrencyCode(inputItem.getCurrency());
            return inputDataLock;
        }).collect(Collectors.toList());
        this.inputDataLockDao.saveAll(inputDataLockItems);
        return lockId;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void lockBaseOnLockedItem(List<InputDataEO> inputLockItems, String lockId) {
        List<InputDataLockEO> inputLocks = this.inputDataLockDao.queryByLockId(lockId);
        HashMap inputId2GroupId = new HashMap();
        inputLockItems.forEach(inputItem -> inputId2GroupId.put(inputItem.getId(), inputItem.getOffsetGroupId()));
        HashMap inputIdCheckGroupId = new HashMap();
        inputLockItems.forEach(inputItem -> inputIdCheckGroupId.put(inputItem.getId(), inputItem.getCheckGroupId()));
        Set lockedGroupIds = inputLocks.stream().map(InputDataLockEO::getOffsetGroupId).filter(StringUtils::hasText).collect(Collectors.toSet());
        Set lockedCheckGroupIds = inputLocks.stream().map(InputDataLockEO::getCheckGroupId).filter(StringUtils::hasText).collect(Collectors.toSet());
        List<InputDataLockEO> needUpdateGroupIdLocks = inputLocks.stream().filter(inputLock -> {
            String checkGroupId;
            String inputItemId = inputLock.getInputItemId();
            String correctGroupId = (String)inputId2GroupId.get(inputItemId);
            boolean isExist = false;
            if (!lockedGroupIds.contains(correctGroupId) && !ObjectUtils.isEmpty(correctGroupId)) {
                lockedGroupIds.add(correctGroupId);
                isExist = true;
            }
            if (!lockedCheckGroupIds.contains(checkGroupId = (String)inputIdCheckGroupId.get(inputItemId)) && !ObjectUtils.isEmpty(checkGroupId)) {
                lockedCheckGroupIds.add(checkGroupId);
                isExist = true;
            }
            return isExist;
        }).peek(inputLock -> {
            inputLock.setOffsetGroupId((String)inputId2GroupId.get(inputLock.getInputItemId()));
            inputLock.setCheckGroupId((String)inputIdCheckGroupId.get(inputLock.getInputItemId()));
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(needUpdateGroupIdLocks)) {
            this.inputDataLockDao.updateGroupId(needUpdateGroupIdLocks);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void deleteExpiredData() {
        try {
            List<InputDataLockEO> lockEOList = this.inputDataLockDao.queryExpiredData(LocalDateTime.now().minusMinutes(10L).toInstant(ZoneOffset.of("+8")).toEpochMilli());
            if (lockEOList != null && lockEOList.size() > 0) {
                lockEOList.forEach(inputDataLockEO -> this.inputDataLockDao.deleteByLockId(inputDataLockEO.getLockId()));
            }
        }
        catch (Exception e) {
            this.logger.error(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.deletealllockidmsg"), e);
        }
    }

    @Override
    public String queryUserNameByInputItemId(Collection<String> inputItemIds) {
        List<InputDataLockEO> inputDataLockEOS = this.inputDataLockDao.queryUserNameByInputItemId(inputItemIds);
        for (InputDataLockEO inputItemId : inputDataLockEOS) {
            if (inputItemId == null || StringUtils.isEmpty(inputItemId.getUserName())) continue;
            return inputItemId.getUserName();
        }
        return "";
    }

    private String newUuidStrEndWithSpecial() {
        UUID id = UUID.randomUUID();
        char[] charArray = new char[37];
        long least = id.getLeastSignificantBits();
        int index = 35;
        for (int i = 15; i >= 8; --i) {
            byte b = (byte)least;
            charArray[index--] = this.parseLowerChar(b & 0xF);
            charArray[index--] = this.parseLowerChar(b >>> 4 & 0xF);
            least >>>= 8;
            if (i != 10) continue;
            charArray[index--] = 45;
        }
        charArray[index--] = 45;
        long most = id.getMostSignificantBits();
        for (int i = 7; i >= 0; --i) {
            byte b = (byte)most;
            charArray[index--] = this.parseLowerChar(b & 0xF);
            charArray[index--] = this.parseLowerChar(b >>> 4 & 0xF);
            most >>>= 8;
            if (i != 6 && i != 4) continue;
            charArray[index--] = 45;
        }
        charArray[36] = 35;
        return new String(charArray);
    }

    private char parseLowerChar(int val) {
        if (val < 0 || val > 15) {
            throw new IllegalArgumentException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.parsecharexceptionmsg") + val);
        }
        return this.lowerHexDigits[val];
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void unlock(String lockId) {
        if (StringUtils.isEmpty(lockId)) {
            return;
        }
        try {
            this.inputDataLockDao.deleteByLockId(lockId);
        }
        catch (Exception e) {
            this.logger.error(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.deletelockidmsg"), (Object)lockId, (Object)e);
        }
    }
}

