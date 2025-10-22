/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDeleteDao;
import com.jiuqi.gcreport.clbr.entity.ClbrBillDeleteEO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.service.ClbrDataQueryService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClbrDataQueryServiceImpl
implements ClbrDataQueryService {
    @Autowired
    private ClbrBillDao clbrBillDao;
    @Autowired
    private ClbrBillDeleteDao clbrBillDeleteDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ClbrOverViewVO queryClbrOverView(ClbrDataQueryConditon clbrDataQueryConditon) {
        ClbrOverViewVO clbrOverView = new ClbrOverViewVO();
        Set<String> codes = this.getBaseDataCodes(clbrDataQueryConditon.getRelation());
        Map<ClbrBillStateEnum, Integer> clbrBillStateCount = this.clbrBillDao.getClbrBillCountByRelationCodeAndBillTypeGroupByBillState(codes, clbrDataQueryConditon.getClbrBillType());
        Integer confirmBillCount = clbrBillStateCount.get(ClbrBillStateEnum.CONFIRM) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.CONFIRM);
        Integer notConfirmBillCount = clbrBillStateCount.get(ClbrBillStateEnum.INIT) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.INIT);
        Integer rejectBillCount = this.clbrBillDeleteDao.getRejectClbrBillCountByRelationCodeAndBillType(codes, clbrDataQueryConditon.getClbrBillType());
        Integer partConfirmBillCount = clbrBillStateCount.get(ClbrBillStateEnum.PARTCONFIRM) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.PARTCONFIRM);
        Integer clbrBillCount = confirmBillCount + notConfirmBillCount + rejectBillCount + partConfirmBillCount;
        double rate = clbrBillCount > 0 ? 100.0 * (double)confirmBillCount.intValue() / (double)clbrBillCount.intValue() : 0.0;
        clbrOverView.setRate(NumberUtils.round((double)rate));
        clbrOverView.setTotal(clbrBillCount);
        clbrOverView.setConfirmCount(confirmBillCount);
        clbrOverView.setNotConfirmCount(notConfirmBillCount);
        clbrOverView.setRejectCount(rejectBillCount);
        clbrOverView.setPartConfirmCount(partConfirmBillCount);
        return clbrOverView;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrOverViewVO> listClbrOverView(ClbrDataQueryConditon clbrDataQueryConditon) {
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        List baseDatas = new ArrayList();
        if (clbrDataQueryConditon.isPageSelect()) {
            if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getRelation())) {
                GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrDataQueryConditon.getRelation());
                if (null == baseData) {
                    baseDatas = baseDataCenterTool.queryRootBasedataItems("MD_RELATION");
                } else if (clbrDataQueryConditon.isOrgParentsFlag()) {
                    baseDatas.add(baseData);
                } else {
                    baseDatas = baseDataCenterTool.queryBasedataItemsByParentid("MD_RELATION", clbrDataQueryConditon.getRelation());
                    if (CollectionUtils.isEmpty(baseDatas)) {
                        baseDatas = new ArrayList();
                    }
                }
            } else {
                baseDatas = baseDataCenterTool.queryRootBasedataItems("MD_RELATION");
            }
        } else if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getRelation())) {
            GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrDataQueryConditon.getRelation());
            if (null == baseData) {
                baseDatas = baseDataCenterTool.queryBasedataItems("MD_RELATION");
            } else {
                baseDatas = baseDataCenterTool.queryBasedataItemsByParentid("MD_RELATION", clbrDataQueryConditon.getRelation());
                if (CollectionUtils.isEmpty(baseDatas)) {
                    baseDatas = new ArrayList();
                }
            }
        } else {
            baseDatas = baseDataCenterTool.queryBasedataItems("MD_RELATION");
        }
        return this.listClbrOverView(baseDatas, clbrDataQueryConditon);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillVO> queryDetailsTotal(ClbrDataQueryConditon clbrDataQueryConditon) {
        PageInfo<ClbrBillEO> clbrBillPages = this.clbrBillDao.listAllClbrBillDetailsByRelationCodeAndBillType(clbrDataQueryConditon);
        return ClbrBillConverter.convertEO2VO(clbrBillPages);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillVO> queryConfirmDetails(ClbrDataQueryConditon clbrDataQueryConditon) {
        PageInfo<ClbrBillEO> clbrBillPages = this.clbrBillDao.listClbrBillDetailsByRelationCodeAndBillType(clbrDataQueryConditon, ClbrBillStateEnum.CONFIRM);
        return ClbrBillConverter.convertEO2VO(clbrBillPages);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillVO> queryPartConfirmDetails(ClbrDataQueryConditon clbrDataQueryConditon) {
        PageInfo<ClbrBillEO> clbrBillPages = this.clbrBillDao.listClbrBillDetailsByRelationCodeAndBillType(clbrDataQueryConditon, ClbrBillStateEnum.PARTCONFIRM);
        return ClbrBillConverter.convertEO2VO(clbrBillPages);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillVO> queryNotConfirmDetails(ClbrDataQueryConditon clbrDataQueryConditon) {
        PageInfo<ClbrBillEO> clbrBillPages = this.clbrBillDao.listClbrBillDetailsByRelationCodeAndBillType(clbrDataQueryConditon, ClbrBillStateEnum.INIT);
        return ClbrBillConverter.convertEO2VO(clbrBillPages);
    }

    @Override
    public PageInfo<ClbrBillVO> queryRejectDetails(ClbrDataQueryConditon clbrDataQueryConditon) {
        PageInfo<ClbrBillDeleteEO> clbrBillPages = this.clbrBillDeleteDao.listClbrBillDetailsByRelationCodeAndBillType(clbrDataQueryConditon, ClbrBillStateEnum.REJECT);
        return ClbrBillConverter.convertDeleteEO2VO(clbrBillPages);
    }

    private List<ClbrOverViewVO> listClbrOverView(List<GcBaseData> baseDatas, ClbrDataQueryConditon clbrDataQueryConditon) {
        ArrayList<ClbrOverViewVO> clbrOverViews = new ArrayList<ClbrOverViewVO>();
        if (CollectionUtils.isEmpty(baseDatas)) {
            return clbrOverViews;
        }
        for (GcBaseData baseData : baseDatas) {
            Map<ClbrBillStateEnum, Integer> clbrBillStateCount;
            ClbrOverViewVO clbrOverView = new ClbrOverViewVO();
            boolean isParentFlag = false;
            List baseDatasByParentid = GcBaseDataCenterTool.getInstance().getBasedataService().queryAllWithSelfItemsByParentid("MD_RELATION", baseData.getCode());
            Set<String> codes = baseDatasByParentid.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
            if (baseDatasByParentid.size() > 1) {
                isParentFlag = true;
            }
            Integer confirmBillCount = (clbrBillStateCount = this.clbrBillDao.getClbrBillCountByRelationCodeAndBillTypeGroupByBillState(codes, clbrDataQueryConditon.getClbrBillType())).get(ClbrBillStateEnum.CONFIRM) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.CONFIRM);
            Integer notConfirmBillCount = clbrBillStateCount.get(ClbrBillStateEnum.INIT) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.INIT);
            Integer rejectBillCount = this.clbrBillDeleteDao.getRejectClbrBillCountByRelationCodeAndBillType(codes, clbrDataQueryConditon.getClbrBillType());
            Integer partConfirmBillCount = clbrBillStateCount.get(ClbrBillStateEnum.PARTCONFIRM) == null ? Integer.valueOf(0) : clbrBillStateCount.get(ClbrBillStateEnum.PARTCONFIRM);
            Integer clbrBillCount = confirmBillCount + notConfirmBillCount + rejectBillCount + partConfirmBillCount;
            Map<String, Double> amountSumMap = this.clbrBillDao.getClbrBillAmountSumByRelationCodeAndBillType(codes, clbrDataQueryConditon.getClbrBillType());
            Double verifyAmount = amountSumMap.get("VERIFYEDAMOUNT") == null ? Double.valueOf(0.0) : amountSumMap.get("VERIFYEDAMOUNT");
            Double noverifyAmount = amountSumMap.get("NOVERIFYAMOUNT") == null ? Double.valueOf(0.0) : amountSumMap.get("NOVERIFYAMOUNT");
            double clbrBillRate = clbrBillCount == 0 ? 0.0 : 100.0 * (double)confirmBillCount.intValue() / (double)clbrBillCount.intValue();
            double confirmRate = clbrBillCount == 0 ? 0.0 : 100.0 * (double)confirmBillCount.intValue() / (double)clbrBillCount.intValue();
            double partConfirmRate = clbrBillCount == 0 ? 0.0 : 100.0 * (double)partConfirmBillCount.intValue() / (double)clbrBillCount.intValue();
            double notConfirmRate = clbrBillCount == 0 ? 0.0 : 100.0 * (double)notConfirmBillCount.intValue() / (double)clbrBillCount.intValue();
            double rejectRate = clbrBillCount == 0 ? 0.0 : 100.0 * (double)rejectBillCount.intValue() / (double)clbrBillCount.intValue();
            clbrOverView.setRelation(baseData.getCode());
            clbrOverView.setRelationTitle(baseData.getTitle());
            clbrOverView.setTotal(clbrBillCount);
            clbrOverView.setRate(NumberUtils.round((double)clbrBillRate));
            clbrOverView.setConfirmCount(confirmBillCount);
            clbrOverView.setConfirmRate(NumberUtils.round((double)confirmRate));
            clbrOverView.setNotConfirmCount(notConfirmBillCount);
            clbrOverView.setNotConfirmRate(NumberUtils.round((double)notConfirmRate));
            clbrOverView.setRejectCount(rejectBillCount);
            clbrOverView.setRejectRate(NumberUtils.round((double)rejectRate));
            clbrOverView.setPartConfirmCount(partConfirmBillCount);
            clbrOverView.setPartConfirmRate(NumberUtils.round((double)partConfirmRate));
            clbrOverView.setNotConfirmAmount(noverifyAmount);
            clbrOverView.setConfirmAmount(verifyAmount);
            clbrOverView.setHasChildren(isParentFlag);
            clbrOverViews.add(clbrOverView);
        }
        return clbrOverViews;
    }

    private Set<String> getBaseDataCodes(String code) {
        if (StringUtils.isEmpty((String)code)) {
            List baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION");
            return baseDatas.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        }
        GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", code);
        if (null == baseData) {
            return Collections.emptySet();
        }
        List baseDatas = GcBaseDataCenterTool.getInstance().queryAllWithSelfItemsByParentid("MD_RELATION", code);
        Set<String> codes = new HashSet<String>();
        if (!CollectionUtils.isEmpty((Collection)baseDatas)) {
            codes = baseDatas.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
        }
        return codes;
    }
}

