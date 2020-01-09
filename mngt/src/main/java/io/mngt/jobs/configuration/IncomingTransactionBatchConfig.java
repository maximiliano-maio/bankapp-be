package io.mngt.jobs.configuration;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import io.mngt.entity.Transaction;
import io.mngt.jobs.domain.TransactionItem;
import io.mngt.jobs.processor.TransactionProcessor;

@Configuration
@EnableBatchProcessing
public class IncomingTransactionBatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("classpath:/files/transaction*.txt")
    private Resource[] transactionData;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcClassName;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final static Logger logger = LoggerFactory.getLogger("IncomingTransactionBatchConfig");

    @Bean
    public FlatFileItemReader<TransactionItem> reader() {
        return new FlatFileItemReaderBuilder<TransactionItem>()
            .name("transactionItemReader")
            .linesToSkip(1)
            .delimited()
            .names(new String[]{"id", "debitAccount", "creditAccount", "amount", "date"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<TransactionItem>(){
                { setTargetType(TransactionItem.class); }
            })
            .build();
        
    }

    @Bean
    public MultiResourceItemReader<TransactionItem> multiResourceItemReader() {
        MultiResourceItemReader<TransactionItem> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(transactionData);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @Bean
    public TransactionProcessor processor(){
        return new TransactionProcessor();
    }

    @Bean
    public DataSource datasource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(jdbcClassName);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        final String sql = "INSERT INTO transaction (id, debitAccount, creditAccount, amount, date, isAccountExternal, status) VALUES (nextval('hibernate_sequence'), :debitAccount, :creditAccount, :amount, :date, false, 0)";
        JdbcBatchItemWriterBuilder<Transaction> builder = new JdbcBatchItemWriterBuilder<>();
        builder.sql(sql);
        builder.dataSource(datasource());
        builder.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return builder.build();
   
    }

    @Bean
    public Step IncomingTransactionstep1(JdbcBatchItemWriter<Transaction> writer) {
        logger.info("+++ Incoming Transaction Step is running...");
        return steps.get("IncomingTransactionstep1")
            .<TransactionItem, Transaction> chunk(5)
            .reader(multiResourceItemReader())
            .processor(processor())
            .writer(writer)
            .build();
    }

    @Bean(name = "processIncomingTransactionJob")
    public Job processIncomingTransactionJob(JobCompletionNotificationListener listener, Step step) {
        logger.info("+++ Process incoming Job is running...");
        return jobs.get("processIncomingTransactionJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(step)
            .build();
    }

    
    

    

}

