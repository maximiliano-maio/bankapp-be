package io.mngt.jobs.configuration;

/*
 * public final class AutoWiringSpringBeanJobFactory extends
 * SpringBeanJobFactory implements ApplicationContextAware {
 * 
 * private transient AutowireCapableBeanFactory beanFactory;
 * 
 * public void setApplicationContext(ApplicationContext applicationContext)
 * throws BeansException {
 * 
 * beanFactory = applicationContext.getAutowireCapableBeanFactory(); }
 * 
 * @Override protected Object createJobInstance(final TriggerFiredBundle bundle)
 * throws Exception {
 * 
 * final Object job = super.createJobInstance(bundle);
 * beanFactory.autowireBean(job); return job; }
 * 
 * }
 */