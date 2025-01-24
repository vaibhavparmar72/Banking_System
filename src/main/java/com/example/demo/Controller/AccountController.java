package com.example.demo.Controller;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.DTO;
import com.example.demo.Model.TransectionDetails;
import com.example.demo.Service.AccountService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bank")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("openAccount")
    public ResponseEntity<?> save(@RequestBody AccountDetails a){
        return accountService.save(a);
    }

    @PutMapping("withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader(value = "acNo")long acNo, @RequestHeader(value = "amount")int amount){
        return accountService.withdraw(acNo,amount);
    }

    @PutMapping("diposit/{acNo}/{amount}")
    public ResponseEntity<?> diposit(@PathVariable long acNo,@PathVariable int amount){
        return accountService.diposit(acNo,amount);
    }

    @PutMapping("transfer/{fromAcNo}/{toAcNo}/{amount}")
    public ResponseEntity<?> transfer(@PathVariable long fromAcNo,@PathVariable long toAcNo,@PathVariable int amount){
        return accountService.transfer(fromAcNo,toAcNo,amount);
    }

    @GetMapping("getDetails/{acNo}")
    public ResponseEntity<?> getDetails(@PathVariable long acNo){
        return accountService.getDetails(acNo);
    }

    @GetMapping("getStatment/{acNo}")
    public ResponseEntity<?> getStatment(@PathVariable long acNo){
        return accountService.getStatement(acNo);
    }
}
