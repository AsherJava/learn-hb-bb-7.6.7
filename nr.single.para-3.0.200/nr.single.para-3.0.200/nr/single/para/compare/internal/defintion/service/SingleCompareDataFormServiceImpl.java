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
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFormDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFormServiceImpl
implements ISingleCompareDataFormService {
    @Autowired
    private ICompareDataDao<CompareDataFormDO> compareDataDao;
    private final Function<CompareDataFormDO, CompareDataFormDTO> toDto = Convert::cdformDo;
    private final Function<List<CompareDataFormDO>, List<CompareDataFormDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFormDTO> list(CompareDataFormDTO compareDataDTO) {
        ArrayList<CompareDataFormDTO> list = new ArrayList<CompareDataFormDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFormDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataFormDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataFormDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataFormDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFormDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFormDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormDTO> list2 = new ArrayList<CompareDataFormDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormDTO> list2 = new ArrayList<CompareDataFormDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFormDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

