package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

      ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);



//      // get account and transaction service beans
//        AccountService accountService = container.getBean(AccountService.class);
//        TransactionService transactionService = container.getBean(TransactionService.class);
//
//        // create 2 accounts: sender & receiver
//        AccountDTO sender = accountService.createNewAccount(BigDecimal.valueOf(7000), new Date(),
//                AccountType.CHECKING, 145332L);
//        AccountDTO receiver = accountService.createNewAccount(BigDecimal.valueOf(5000), new Date(),
//                AccountType.SAVING, 187990L);
//        AccountDTO receiver2 = null;
//
//        accountService.listAllAccount().forEach(System.out :: println);
//
//        transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1");
//
//        System.out.println(transactionService.findAllTransactions().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
