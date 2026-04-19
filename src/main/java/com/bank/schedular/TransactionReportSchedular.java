package com.bank.schedular;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bank.dao.TransactionRepository;
import com.bank.entity.Transaction;

@Component
public class TransactionReportSchedular {
	
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Scheduled(fixedRate = 10000)  // every 10 seconds
	public void generateTransactionReport() {
		
		List<Transaction> transactions = transactionRepository.findAll();
		
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "transaction_report_" + timestamp + ".csv";
        
        try (FileWriter writer = new FileWriter(filename)) {
        	
        	//csv Header
            writer.append("TransactionID,Amount,TransactionType,FromAccount,ToAccount,Timestamp\n");
            
        	for(Transaction tx : transactions) {
        		
        		writer.append(String.valueOf(tx.getId())).append(",");
        		writer.append(String.valueOf(tx.getAmount())).append(",");
        		writer.append(String.valueOf(tx.getTransactionType())).append(",");
        		writer.append(tx.getFromAccount() != null ? tx.getFromAccount().getAccountNumber() : "").append(",");
        		writer.append(tx.getToAccount() != null ? tx.getToAccount().getAccountNumber() : "").append(",");
        		writer.append(tx.getTimestamp().toString()).append("\n");
        		
        	}
        	System.out.println("✅ Transaction report generated: " + filename);
        	
			
		} catch (Exception e) {
			// TODO: handle exception
			 System.err.println("❌ Failed to write CSV: " + e.getMessage());
		}
	}
	
//	💥 Bottom line:
//		✅ For a demo/small-scale (few lakh records): → BufferedWriter is fine.
//
//		✅ For production or growing data scale: → prefer OpenCSV or Apache Commons CSV.
//
//		✅ If your CSV is downloaded or consumed by users → library helps handle special characters (commas, quotes inside data, line breaks).
//	

}
