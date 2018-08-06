package com.dcits.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zipkin.dependencies.ZipkinDependenciesJob;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by markCool on 2018/8/1.
 */
@Component
public class DependencyOperate implements Job {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    //时间间隔，单位s
    private static int intervalTime = 120;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("start dependency ... ");
        /*try {
            Method method = ZipkinDependenciesJob.class.getMethod("main", String[].class);
            method.invoke(ZipkinDependenciesJob.class.newInstance(), new Object[]{ new String[]{}});
        } catch (Exception e) {
            logger.error("ZipkinDependenciesJob main exception ... " + e.getMessage());
        }*/
        logger.info("dependency end ... ");
    }

    public void startTimer()  {
        try {
            SchedulerFactory schedFact=new StdSchedulerFactory();
            Scheduler sched=schedFact.getScheduler();
            JobDetail jobDetail= JobBuilder.newJob(DependencyOperate.class).withIdentity("dependency_Job","dependency_Group").build();//countTraceJob为JOD的名称
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dependency_Trigger").
                    startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(intervalTime).repeatForever()).build();
            sched.scheduleJob(jobDetail, trigger);
            sched.start();
            logger.info("dependency quartz start ！！！" + new Date().toString());
        }catch (Exception e){
            logger.error("dependency quartz error, " + e.getMessage());
        }
    }

    static String[] pathToUberJar() throws UnsupportedEncodingException {
        URL jarFile = ZipkinDependenciesJob.class.getProtectionDomain().getCodeSource().getLocation();
        return (new File(jarFile.getPath())).isDirectory()?null:new String[]{URLDecoder.decode(jarFile.getPath(), "UTF-8")};
    }
}
