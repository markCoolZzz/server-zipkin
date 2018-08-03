package com.dcits.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by markCool on 2018/7/31
 */

@Component
public class DependencyQuartz implements InitializingBean {

    @Resource
    DependencyOperate  dependencyOperate;

    private  final Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet()  {
        try {
            logger.info("dependency quartz start..."+new Date());
            dependencyOperate.startTimer();
        }catch (Exception e){
            logger.error("dependency error," + e.getMessage());
        }
    }
}
