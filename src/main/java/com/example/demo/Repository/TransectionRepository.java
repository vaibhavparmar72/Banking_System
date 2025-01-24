package com.example.demo.Repository;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.DTO;
import com.example.demo.Model.TransectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TransectionRepository extends JpaRepository<TransectionDetails, Long> {

    default List<TransectionDetails> getStatement(List<TransectionDetails> allStatement, long acNo) {
        List<TransectionDetails> ans = new ArrayList<>();

        for(TransectionDetails temp:allStatement){
            if(temp.getAccountDetails().getAcNo() == acNo){
                ans.add(temp);
            }
        }
        return ans;
    }


//    @Query(nativeQuery = true, value = "SELECT * FROM transection_details  where account_details_ac_no =:acNo")
//    List<DTO> getTransection(@Param("acNo") long acNo);

//    @Query(nativeQuery = true, value = "SELECT t.amount,t.date,t.transection_type FROM transection_details t WHERE t.account_details_ac_no = ?1")
//    List<DTO> getTransection(@Param("acNo") long acNo);

}

//public
