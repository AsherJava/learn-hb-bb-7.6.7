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
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFMDMFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFMDMFieldServiceImpl
implements ISingleCompareDataFMDMFieldService {
    @Autowired
    private ICompareDataDao<CompareDataFMDMFieldDO> compareDataDao;
    private final Function<CompareDataFMDMFieldDO, CompareDataFMDMFieldDTO> toDto = Convert::cdff2Do;
    private final Function<List<CompareDataFMDMFieldDO>, List<CompareDataFMDMFieldDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFMDMFieldDTO> list(CompareDataFMDMFieldDTO compareDataDTO) {
        ArrayList<CompareDataFMDMFieldDTO> list = new ArrayList<CompareDataFMDMFieldDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFMDMFieldDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataFMDMFieldDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataFMDMFieldDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataFMDMFieldDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFMDMFieldDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFMDMFieldDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFMDMFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFMDMFieldDTO> list2 = new ArrayList<CompareDataFMDMFieldDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFMDMFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFMDMFieldDTO> list2 = new ArrayList<CompareDataFMDMFieldDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFMDMFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFMDMFieldDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

