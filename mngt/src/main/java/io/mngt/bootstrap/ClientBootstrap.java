package io.mngt.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.domain.CheckBookOrder;
import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.domain.Loan;
import io.mngt.repositories.CheckBookOrderRepository;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.LoanRepository;

@Component
@Profile("default")
public class ClientBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private CheckBookOrderRepository checkBookOrderRepository;
  @Autowired
  private LoanRepository loanRepository;
  
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // Population for testing purpose:    
    initSetClientData();
  }

  private void initSetClientData() {
    
    clientRepository.deleteAll();
    checkBookOrderRepository.deleteAll();
    
    // Client 1:
    Client client = new Client("338011321", "Maxi", "Maio", "1");

    ContactInfo clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0515819763");
    clientContactInfo.setEmail("maxi_maio@hotmail.com");
    clientContactInfo.setDistributionAgreement(2);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    CheckBookOrder checks = new CheckBookOrder(100, "Ordered", "A", 0, 50, 50, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    Loan loan = new Loan("Morgage", 2000000, 120, (float) 1.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Personal", 5000, 60, (float) 6.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------

    // Client 2:
    client = new Client("101032700", "Israel", "Israeli", "1");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0534292553");
    clientContactInfo.setEmail("ing.m.maio@gmail.com");
    clientContactInfo.setDistributionAgreement(1);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(101, "Delivered", "B", 101, 201, 100, "USD");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Morgage", 50000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Personal", 20000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 3:
    client = new Client("101032701", "Moshe", "Klein", "5");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(5);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(102, "In branch", "A", 100, 150, 50, "USD");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Car", 50000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Remodelation", 200000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 4:
    client = new Client("101032702", "Moshe", "Dayan", "4");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(4);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(103, "Returned", "C", 1000, 1200, 200, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Car", 20000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Remodelation", 200000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 5:
    client = new Client("101032703", "Meital", "Moshe", "3");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("034328987");
    clientContactInfo.setDistributionAgreement(3);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(104, "Waiting", "B", 3050, 3300, 250, "EUR");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Car", 50000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Remodelation", 200000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Mortgage", 5000000, 320, (float) 1.2);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Personal", 2000, 12, (float) 7.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 6:
    client = new Client("101032704", "Oded", "David", "2");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0534228883");
    clientContactInfo.setDistributionAgreement(4);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(105, "Delivered", "B", 0, 500, 500, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Car", 50000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Mortgage", 1000000, 48, (float) 1.3);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 7:
    client = new Client("101032705", "Dana", "Stein", "5");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setEmail("ing.m.maio@gmail.com");
    clientContactInfo.setDistributionAgreement(5);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);
    
    checks = new CheckBookOrder(106, "Rejected", "A", 10000, 10050, 50, "USD");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    loan = new Loan("Car", 50000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Remodelation", 200000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);
    // ----------------------------------
    // Client 8:
    client = new Client("101032706", "Mohammad", "Ahmed", "2");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0530000000");
    clientContactInfo.setEmail("ahmed@gmail.com");
    clientContactInfo.setDistributionAgreement(4);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);
    
    checks = new CheckBookOrder(107, "Rejected", "A", 1020, 1070, 50, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);
    // ----------------------------------
    // Client 9:
    client = new Client("101032707", "Abu", "Mazen", "1");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setEmail("rashut_palestinit@gmail.com");
    clientContactInfo.setDistributionAgreement(1);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder();
    checks.setClient(client);
    checkBookOrderRepository.save(checks);    
    // ----------------------------------
    // Client 10:
    client = new Client("101032708", "Bibi", "N.", "2");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(1);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder();
    checks.setClient(client);
    checkBookOrderRepository.save(checks);
    // ----------------------------------
    // Client 11:
    client = new Client("101032709", "David", "Bitan", "5");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(5);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(120, "Delivered", "B", 0, 0, 0, "");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);
    // ----------------------------------

  }


}