package edu.umg.farm.configuration;

import edu.umg.farm.quartz.jobs.LoggerJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import static org.quartz.JobBuilder.newJob;

@Configuration
@ConditionalOnProperty("farm.control.scheduler.on")
public class SchedulerConfig {

    @Value("${farm.job.start.delay:5000}")
    private long triggerStartDelay;

    @Value("${farm.job.repeat.interval:30000}")
    private long repeatInterval;

    @Bean
    public JobDetail readSensorsJobDetail() {
        return newJob()
                .ofType(LoggerJob.class)
                .withIdentity("LoggerJob", "FarmGroup")
                .storeDurably(true)
                .build();
    }

    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetail readSensorsJobDetail) {

        var triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(readSensorsJobDetail);
        triggerFactoryBean.setStartDelay(triggerStartDelay);
        triggerFactoryBean.setRepeatInterval(repeatInterval);
        triggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);

        return triggerFactoryBean;
    }

}
