package com.example.demo.Repository;

import com.example.demo.Model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails,Long> {

    AccountDetails findByAcNo(long accountNumber);
}
