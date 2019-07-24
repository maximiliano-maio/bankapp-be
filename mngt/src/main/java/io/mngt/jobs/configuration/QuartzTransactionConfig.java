package io.mngt.jobs.configuration;

/* 
*  @deprecated: Scheduling will be implemented through Spring
*/

// @Configuration
/* public class QuartzTransactionConfig {

  // @Autowired
  private ApplicationContext applicationContext;

  
  // @Bean
  public JobDetail jobDetail() {
    return JobBuilder.newJob().ofType(TransactionJob.class).storeDurably().withIdentity("Quartz_Transaction_Job")
        .withDescription("Scheduled Transaction job which performs credit/debit operations.").build();
  }

  // @Bean
  public Trigger trigger(JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job).withIdentity("Quartz_Transaction_trigger")
        .withDescription("Quartz Transaction trigger")
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(2))
        .build();
  }

  // @Bean
  public Scheduler scheduler(Trigger trigger, JobDetail job) throws SchedulerException, IOException {
    StdSchedulerFactory factory = new StdSchedulerFactory();
    
    factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

    Scheduler scheduler = factory.getScheduler();
    scheduler.setJobFactory(springBeanJobFactory());
    scheduler.scheduleJob(job, trigger);
    return scheduler;

  }

  // @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }
  
} */

