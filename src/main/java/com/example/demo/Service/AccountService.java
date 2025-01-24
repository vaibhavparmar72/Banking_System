package com.example.demo.Service;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.DTO;
import com.example.demo.Model.TransectionDetails;
import com.example.demo.Model.Types;

import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.TransectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransectionRepository transectionRepository;


    public ResponseEntity<?> save(AccountDetails a) {
        try{
            a.setAcNo(autoG());
            accountRepository.save(a);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Successfully Account is Open...",HttpStatus.CREATED);
    }


    public ResponseEntity<?> withdraw(long acNo, int amount) {
        try{
            AccountDetails accountDetails = accountRepository.findByAcNo(acNo);
            if(accountDetails == null){
                return new ResponseEntity<>("Account Not found...",HttpStatus.NOT_FOUND);
            }
            if(amount <= 0){
                return new ResponseEntity<>("Amount Must be GraterThan 0",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(accountDetails.getBalance() < amount){
                return new ResponseEntity<>("Insufficient Balance.",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            accountDetails.setBalance(accountDetails.getBalance() - amount);
            accountRepository.save(accountDetails);
            TransectionRecord(accountDetails, Types.Withdraw,amount);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Withdrawal successful.",HttpStatus.OK);
    }

    public ResponseEntity<?> diposit(long acNo, int amount) {
        try{
            AccountDetails accountDetails = accountRepository.findByAcNo(acNo);
            if(accountDetails==null){
                return new ResponseEntity<>("Account not Found",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(amount <= 0){
                return new ResponseEntity<>("Amount Must be GraterThan 0",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            accountDetails.setBalance(accountDetails.getBalance()+amount);
            accountRepository.save(accountDetails);
            TransectionRecord(accountDetails,Types.Diposit,amount);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Diposit Successful.",HttpStatus.OK);
    }

    private void TransectionRecord(AccountDetails accountDetails, Types types, int amount) throws RuntimeException{
        TransectionDetails transectionDetails = new TransectionDetails();
        transectionDetails.setAccountDetails(accountDetails);
        transectionDetails.setTid(autoG());
        transectionDetails.setAmount(amount);
        transectionDetails.setTransectionType(String.valueOf(types));
        transectionDetails.setDate(String.valueOf(LocalDate.now()));
        transectionRepository.save(transectionDetails);
    }

    public ResponseEntity<?> transfer(long fromAcNo, long toAcNo, int amount) {
        try{
            AccountDetails accountFrom = accountRepository.findByAcNo(fromAcNo);
            AccountDetails accountTo = accountRepository.findByAcNo(toAcNo);
            if(accountFrom == null || accountTo == null){
                return new ResponseEntity<>("Acount Not Found",HttpStatus.NOT_FOUND);
            }

            if(accountFrom.getBalance() < amount){
                return new ResponseEntity<>("Insufficient Balance.",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            withdraw(accountFrom.getAcNo(),amount);
            diposit(accountTo.getAcNo(),amount);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Successfully transfer.",HttpStatus.OK);

    }

    public ResponseEntity<?> getDetails(long acNo) {
        AccountDetails accountDetails;
        try{
            accountDetails =  accountRepository.findByAcNo(acNo);
            if(accountDetails == null){
                return new ResponseEntity<>("Account not found..",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accountDetails,HttpStatus.OK);
    }

    public ResponseEntity<?> getStatement(long acNo) {
        List<DTO>list=new ArrayList<>();
        try{
            List<TransectionDetails> allStatement = transectionRepository.findAll();

            if(accountRepository.findByAcNo(acNo)==null)
                return new ResponseEntity<>("Acount Not Found",HttpStatus.NOT_FOUND);

            for( int i=0;i<allStatement.size();i++) {
                TransectionDetails transectionDetails=allStatement.get(i);
                AccountDetails accountDetails=transectionDetails.getAccountDetails();
                if(accountDetails.getAcNo() != acNo) continue;
                DTO dto=new DTO();
                dto.setAmount(transectionDetails.getAmount());
                dto.setDate(transectionDetails.getDate());
                dto.setType(transectionDetails.getTransectionType());
                list.add(dto);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    // for AutoGenrate Number
    public long autoG(){
        UUID id = UUID.randomUUID();
        long num = Math.abs(id.getMostSignificantBits());
        while(String.valueOf(num).length() < 10){
            num *= 10;
        }
        return num % 1_000_000_0000L;
    }
}
