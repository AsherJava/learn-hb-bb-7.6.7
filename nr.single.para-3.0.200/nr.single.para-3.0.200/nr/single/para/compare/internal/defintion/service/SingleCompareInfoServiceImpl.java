/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareInfoDO;
import nr.single.para.compare.internal.defintion.dao.ICompareInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareInfoServiceImpl
implements ISingleCompareInfoService {
    @Autowired
    private ICompareInfoDao<CompareInfoDO> compareInfoDao;
    private final Function<CompareInfoDO, CompareInfoDTO> toDto = Convert::ci2Do;
    private final Function<List<CompareInfoDO>, List<CompareInfoDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareInfoDTO> list(CompareInfoDTO compareInfoDTO) {
        ArrayList<CompareInfoDTO> list = new ArrayList<CompareInfoDTO>();
        if (StringUtils.isNotEmpty((String)compareInfoDTO.getKey())) {
            CompareInfoDO obj = this.compareInfoDao.get(compareInfoDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareInfoDTO.getFormSchemeKey())) {
            List<CompareInfoDO> list2 = this.compareInfoDao.getByformScheme(compareInfoDTO.getFormSchemeKey());
            list.addAll((Collection<CompareInfoDTO>)this.list2Dto.apply(list2));
        } else if (StringUtils.isNotEmpty((String)compareInfoDTO.getTaskKey())) {
            List<CompareInfoDO> list2 = this.compareInfoDao.getByTask(compareInfoDTO.getTaskKey());
            list.addAll((Collection<CompareInfoDTO>)this.list2Dto.apply(list2));
        } else {
            List<CompareInfoDO> list2 = this.compareInfoDao.getAll();
            list.addAll((Collection<CompareInfoDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public CompareInfoDTO getByKey(String infoKey) {
        CompareInfoDO obj = this.compareInfoDao.get(infoKey);
        return this.toDto.apply(obj);
    }

    @Override
    public void add(CompareInfoDTO compareInfoDTO) throws SingleCompareException {
        this.compareInfoDao.insert(compareInfoDTO);
    }

    @Override
    public void update(CompareInfoDTO compareInfoDTO) throws SingleCompareException {
        this.compareInfoDao.update(compareInfoDTO);
    }

    @Override
    public void delete(CompareInfoDTO compareInfoDTO) throws SingleCompareException {
        if (compareInfoDTO != null && StringUtils.isNotEmpty((String)compareInfoDTO.getKey())) {
            this.compareInfoDao.delete(compareInfoDTO.getKey());
        }
    }

    @Override
    public void batchAdd(List<CompareInfoDTO> compareInfoDTOs) throws SingleCompareException {
        ArrayList<CompareInfoDTO> list2 = new ArrayList<CompareInfoDTO>();
        list2.addAll(compareInfoDTOs);
        this.compareInfoDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareInfoDTO> compareInfoDTOs) throws SingleCompareException {
        ArrayList<CompareInfoDTO> list2 = new ArrayList<CompareInfoDTO>();
        list2.addAll(compareInfoDTOs);
        this.compareInfoDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareInfoDTO> compareInfoDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        ArrayList list3 = new ArrayList();
        for (CompareInfoDO compareInfoDO : compareInfoDTOs) {
            list2.add(compareInfoDO.getKey());
        }
        this.compareInfoDao.batchDelete(list2);
    }
}

