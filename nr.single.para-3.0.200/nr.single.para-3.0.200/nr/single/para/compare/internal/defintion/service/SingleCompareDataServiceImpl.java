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
import nr.single.para.compare.definition.CompareDataDTO;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.ISingleCompareDataService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataServiceImpl
implements ISingleCompareDataService {
    @Autowired
    private ICompareDataDao<CompareDataDO> compareDataDao;
    private final Function<CompareDataDO, CompareDataDTO> toDto = Convert::cd2Do;
    private final Function<List<CompareDataDO>, List<CompareDataDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<ICompareData> list(CompareDataDTO compareDataDTO) {
        ArrayList<ICompareData> list = new ArrayList<ICompareData>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<ICompareData>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataDTO> list2 = new ArrayList<CompareDataDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataDTO> list2 = new ArrayList<CompareDataDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

