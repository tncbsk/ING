package com.ing.creditModule.repos;

import com.ing.creditModule.entity.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    /**
     * This query find All Loan Installment List by loan ID
     * @param loanId
     * @return
     */
    List<LoanInstallment> findByLoanId(Long loanId);

    /**
     * This query find all Loan Installment List by Loan Id and isPaid
     * @param loanId
     * @param isPaid
     * @return
     */
    List<LoanInstallment> findByLoanIdAndIsPaid(Long loanId , Boolean isPaid);
}
