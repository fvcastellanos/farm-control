package edu.umg.farm.configuration;

import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.ReadEventMyBatisDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {

    @Bean
    public ReadEventDao readEventDao(SqlSession sqlSession) {
        return new ReadEventMyBatisDao(sqlSession);
    }

}
