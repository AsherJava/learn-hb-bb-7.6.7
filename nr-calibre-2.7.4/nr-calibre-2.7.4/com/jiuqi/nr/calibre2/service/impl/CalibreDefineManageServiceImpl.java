/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.ICalibreReferService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.exception.CalibreDefineException;
import com.jiuqi.nr.calibre2.exception.CalibreDefineServiceException;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreDefineDao;
import com.jiuqi.nr.calibre2.service.ICalibreDataManageService;
import com.jiuqi.nr.calibre2.service.ICalibreDefineManageService;
import com.jiuqi.nr.calibre2.vo.BatchCalibreDataOptionsVO;
import com.jiuqi.nr.calibre2.vo.ReferenceCalibreVO;
import com.jiuqi.nr.calibre2.vo.ReferenceCheckVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class CalibreDefineManageServiceImpl
implements ICalibreDefineManageService {
    @Autowired
    ICalibreDefineService calibreDefineService;
    @Autowired
    ICalibreDefineDao calibreDefineDao;
    @Autowired
    private ICalibreReferService referService;
    @Autowired
    ICalibreDataManageService calibreDataManageService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int[] moveCalibre(CalibreDefineDTO calibreDefineDTO) throws Exception {
        int moveNumber;
        List<CalibreDefineDTO> calibreDefineDTOList = this.calibreDefineService.list(calibreDefineDTO).getData();
        List allCalibreKeyList = calibreDefineDTOList.stream().map(CalibreDefineDO::getKey).collect(Collectors.toList());
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        List<String> moveList = calibreDefineDTO.getMoveKeys();
        Integer direction = calibreDefineDTO.getDirection();
        direction = direction == 0 ? Integer.valueOf(-1) : Integer.valueOf(1);
        int moveNumbers = moveNumber = moveList.size();
        int location = allCalibreKeyList.indexOf(moveList.get(0));
        int lastLocation = allCalibreKeyList.indexOf(moveList.get(moveNumber - 1));
        if (lastLocation - location != moveNumber - 1) {
            throw new JQException((ErrorEnum)CalibreDefineException.MOVE_CALIBRE_ERROR, "\u79fb\u52a8\u9009\u62e9\u7684\u53e3\u5f84\u4e0d\u8fde\u7eed");
        }
        while (moveNumber != 0) {
            Object[] o = new Object[]{calibreDefineDTOList.get(location + moveNumber - 1 + direction).getOrder(), calibreDefineDTOList.get(location + moveNumber - 1).getKey()};
            batchArgs.add(o);
            --moveNumber;
        }
        Object[] o2 = direction == -1 ? new Object[]{calibreDefineDTOList.get(location + moveNumbers - 1).getOrder(), calibreDefineDTOList.get(location - 1).getKey()} : new Object[]{calibreDefineDTOList.get(location).getOrder(), calibreDefineDTOList.get(location + moveNumbers).getKey()};
        batchArgs.add(o2);
        return this.calibreDefineDao.batchUpdateOrder(batchArgs);
    }

    @Override
    public Boolean isSameCalibreCode(String newCode) throws CalibreDefineServiceException {
        if (newCode != null) {
            CalibreDefineDO calibreDefineDO = this.calibreDefineDao.getByCode(newCode);
            return calibreDefineDO == null;
        }
        throw new CalibreDefineServiceException("\u53e3\u5f84code\u4e3a\u7a7a");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> batchDeleteCalibreDefine(List<String> keys) throws CalibreDefineServiceException {
        ArrayList<UpdateResult> updateResultList = new ArrayList<UpdateResult>();
        for (String key : keys) {
            BatchCalibreDataOptionsVO vo = new BatchCalibreDataOptionsVO();
            vo.setAll(true);
            vo.setDefineKey(key);
            try {
                this.calibreDataManageService.batchDelete(vo, true);
            }
            catch (Exception e) {
                throw new CalibreDefineServiceException("\u53e3\u5f84\u5b9a\u4e49\u5185\u53e3\u5f84\u6570\u636e\u5220\u9664\u5931\u8d25", e);
            }
            Result<UpdateResult> result = this.calibreDefineService.delete(key);
            updateResultList.add(result.getData());
        }
        return Result.success(updateResultList, "\u5220\u9664\u53e3\u5f84\u6210\u529f");
    }

    @Override
    public ReferenceCheckVO deleteCheck(ReferenceCalibreVO referenceCalibreVO) {
        ReferenceCheckVO vo = new ReferenceCheckVO();
        List<ReferenceCalibreVO> refer = this.referService.getRefer(referenceCalibreVO.getCalibreDefineCode(), referenceCalibreVO.getCalibreKeys());
        for (ReferenceCalibreVO calibreVO : refer) {
            if (calibreVO.isEnableDelete()) continue;
            vo.addItem(calibreVO);
        }
        vo.setEnableDelete(CollectionUtils.isEmpty(vo.getItems()));
        return vo;
    }

    @Override
    public ReferenceCheckVO betchDeleteCheck(List<String> deleteDefineCodes) {
        ReferenceCheckVO vo = new ReferenceCheckVO();
        for (String code : deleteDefineCodes) {
            List<ReferenceCalibreVO> refer = this.referService.getRefer(code, null);
            for (ReferenceCalibreVO calibreVO : refer) {
                if (calibreVO.isEnableDelete()) continue;
                vo.addItem(calibreVO);
            }
        }
        vo.setEnableDelete(CollectionUtils.isEmpty(vo.getItems()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> copy(CalibreDefineDTO defineDTO) throws CalibreDefineServiceException {
        String sourceCalibreKey = defineDTO.getKey();
        List<UpdateResult> updateResults = null;
        try {
            this.calibreDefineService.add(defineDTO);
            updateResults = this.calibreDataManageService.copyCalibreData(sourceCalibreKey, defineDTO.getCode());
        }
        catch (Exception e) {
            throw new CalibreDefineServiceException(e.getMessage(), e);
        }
        return Result.success(updateResults, "\u590d\u5236\u53e3\u5f84\u6210\u529f");
    }
}

