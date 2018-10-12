package edu.umg.farm.quartz.jobs;

import edu.umg.farm.service.FarmService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoggerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(LoggerJob.class);

    @Autowired
    private FarmService farmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        logger.info("job triggered");

        farmService.checkSoil();
    }
}
