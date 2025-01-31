package com.ing.creditModule.mapper;

import com.ing.creditModule.dto.LoanInstallmentDTO;
import com.ing.creditModule.entity.LoanInstallment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanInstallmentMapper {

    LoanInstallmentMapper INSTANCE = Mappers.getMapper(LoanInstallmentMapper.class);

    /**
     * Convert LoanInstallment object to LoanInstallmentDTO automatically
     * @param loan
     * @return
     */
    LoanInstallmentDTO covertToLoanInstallmentDto(LoanInstallment loan);

    /**
     * Convert List<LoanInstallment>  object to List<LoanInstallmentDTO> automatically
     * @param loan
     * @return
     */
    List<LoanInstallmentDTO> covertToLoanInstallmentDto(List<LoanInstallment> loan);

}
