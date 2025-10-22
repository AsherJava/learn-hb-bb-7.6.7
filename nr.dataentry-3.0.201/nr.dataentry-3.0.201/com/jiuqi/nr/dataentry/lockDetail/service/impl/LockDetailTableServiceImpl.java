/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.api.IStateFormLockService
 *  com.jiuqi.nr.data.access.api.param.FormLockHistory
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.lockDetail.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.FormLockHistory;
import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailParamInfo;
import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailReturnInfo;
import com.jiuqi.nr.dataentry.lockDetail.service.LockDetailTableService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LockDetailTableServiceImpl
implements LockDetailTableService {
    @Autowired
    private IStateFormLockService iStateFormLockService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public List<LockDetailReturnInfo> queryLockDetailList(LockDetailParamInfo lockDetailParamInfo) {
        Assert.notNull((Object)lockDetailParamInfo.getFormKey(), "form is must not be null!");
        ArrayList<LockDetailReturnInfo> lockDetailReturnInfoList = new ArrayList<LockDetailReturnInfo>();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map dimensionSet = lockDetailParamInfo.getJtableContext().getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        List formLockHistories = this.iStateFormLockService.getLockHistory(dimensionCombination, lockDetailParamInfo.getJtableContext().getFormSchemeKey(), lockDetailParamInfo.getFormKey());
        if (!formLockHistories.isEmpty()) {
            formLockHistories = formLockHistories.stream().sorted((x, y) -> y.getOperTime().compareTo(x.getOperTime())).collect(Collectors.toList());
        }
        FormDefine formDefine = this.runTimeViewController.queryFormById(lockDetailParamInfo.getFormKey());
        Set<String> userIdList = formLockHistories.stream().map(FormLockHistory::getUserID).collect(Collectors.toSet());
        List usersList = this.userService.get(userIdList.toArray(new String[0]));
        usersList.addAll(this.systemUserService.get(userIdList.toArray(new String[0])));
        HashMap<String, String> userIDAndNameMap = new HashMap<String, String>();
        for (User user : usersList) {
            String userName = user.getNickname();
            if (StringUtils.isEmpty((String)userName)) {
                userName = StringUtils.isEmpty((String)user.getName()) ? "\u7cfb\u7edf\u7ba1\u7406\u5458" : user.getName();
            }
            userIDAndNameMap.put(user.getId(), userName);
        }
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FormLockHistory formLockHistory : formLockHistories) {
            LockDetailReturnInfo lockDetailReturnInfo = new LockDetailReturnInfo();
            lockDetailReturnInfo.setLockDetailId(formLockHistory.getId());
            lockDetailReturnInfo.setLockFormKeyTitle(formDefine.getTitle());
            lockDetailReturnInfo.setLockUserName((String)userIDAndNameMap.get(formLockHistory.getUserID()));
            lockDetailReturnInfo.setLockType(formLockHistory.getOper());
            Date date = formLockHistory.getOperTime();
            String formatDateFolder = simple.format(date);
            lockDetailReturnInfo.setLockDateTime(formatDateFolder);
            lockDetailReturnInfoList.add(lockDetailReturnInfo);
        }
        return lockDetailReturnInfoList;
    }
}

