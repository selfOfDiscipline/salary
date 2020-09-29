package com.tyzq.salary.mapper;

import com.tyzq.salary.model.SalaryNonPersonTax;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工资类的非个人所得税对应的税率及速算扣款表 Mapper 接口
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-18
 */
@Mapper
public interface SalaryNonPersonTaxMapper extends BaseMapper<SalaryNonPersonTax> {

}
