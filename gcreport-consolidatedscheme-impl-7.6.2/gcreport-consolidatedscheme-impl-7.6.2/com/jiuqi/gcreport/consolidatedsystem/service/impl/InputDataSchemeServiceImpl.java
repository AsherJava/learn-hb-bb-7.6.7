/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.event.InputDataSchemeChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.event.InputDataSchemeChangedEvent$InputDataSchemeChangedInfo
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl;

import com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.consolidatedsystem.event.InputDataSchemeChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class InputDataSchemeServiceImpl
implements InputDataSchemeService {
    @Autowired
    private InputDataSchemeDao inputDataSchemeDao;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<InputDataSchemeVO> listInputDataScheme() {
        List<InputDataSchemeVO> inputDataSchemeList = this.inputDataSchemeDao.loadAll().stream().map(inputDataScheme -> {
            InputDataSchemeVO inputDataSchemeVO = new InputDataSchemeVO();
            BeanUtils.copyProperties(inputDataScheme, inputDataSchemeVO);
            return inputDataSchemeVO;
        }).collect(Collectors.toList());
        return inputDataSchemeList;
    }

    @Override
    public InputDataSchemeVO getInputDataSchemeByDataSchemeKey(String dataSchemeKey) {
        InputDataSchemeEO inputDataScheme = this.inputDataSchemeDao.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
        if (inputDataScheme == null) {
            return null;
        }
        InputDataSchemeVO inputDataSchemeVO = new InputDataSchemeVO();
        BeanUtils.copyProperties((Object)inputDataScheme, inputDataSchemeVO);
        return inputDataSchemeVO;
    }

    @Override
    public void createInputDataScheme(InputDataSchemeVO inputDataSchemeVO) {
        if (null != this.inputDataSchemeDao.get((Serializable)((Object)inputDataSchemeVO.getId()))) {
            return;
        }
        if (null != this.inputDataSchemeDao.getInputDataSchemeByDataSchemeKey(inputDataSchemeVO.getDataSchemeKey())) {
            return;
        }
        InputDataSchemeEO inputDataSchemeEO = new InputDataSchemeEO();
        BeanUtils.copyProperties(inputDataSchemeVO, (Object)inputDataSchemeEO);
        this.inputDataSchemeDao.save(inputDataSchemeEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteInputDataSchemeByDataSchemeKey(final String dataSchemeKey) {
        this.inputDataSchemeDao.deleteInputDataSchemeByDataSchemeKey(dataSchemeKey);
        TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

            public void afterCommit() {
                InputDataSchemeServiceImpl.this.applicationContext.publishEvent((ApplicationEvent)new InputDataSchemeChangedEvent(new InputDataSchemeChangedEvent.InputDataSchemeChangedInfo(dataSchemeKey), NpContextHolder.getContext()));
            }
        });
    }
}

