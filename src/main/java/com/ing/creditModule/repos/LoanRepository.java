package com.ing.creditModule.repos;

import com.ing.creditModule.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * This Query find All loan based on CustomerID and if exist number of installment or isPaid
     * @param customerId
     * @param numberOfInstallment
     * @param isPaid
     * @return
     */
    @Query("SELECT l FROM Loan l WHERE l.customer.id = :customerId " +
            "AND (:numberOfInstallment IS NULL OR l.numberOfInstallment = :numberOfInstallment) " +
            "AND (:isPaid IS NULL OR l.isPaid = :isPaid)")
    List<Loan> findLoansByCustomerIdAndOtherFields(@Param("customerId") Long customerId, @Param("numberOfInstallment") Integer numberOfInstallment,
                                                   @Param("isPaid") Boolean isPaid);
}
