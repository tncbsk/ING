package com.ing.creditModule.mapper;

import com.ing.creditModule.dto.LoanDTO;
import com.ing.creditModule.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    /**
     * Convert Loan object to LoanDTO automatically
     * During to conversion , only name and surname should be part of request
     * @param loan
     * @return
     */
    @Mapping(target = "name", source = "loan.customer.name")
    @Mapping(target = "surname", source = "loan.customer.surname")
    LoanDTO covertToLoanDto(Loan loan);

    List<LoanDTO> covertToLoanDto(List<Loan> loan);

}
