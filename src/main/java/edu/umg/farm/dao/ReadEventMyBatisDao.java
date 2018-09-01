package edu.umg.farm.dao;

import edu.umg.farm.dao.model.ReadEvent;
import org.apache.ibatis.session.SqlSession;

public class ReadEventMyBatisDao implements ReadEventDao {

    private SqlSession sqlSession;

    public ReadEventMyBatisDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public ReadEvent saveReadEvent(ReadEvent readEvent) {

        sqlSession.insert("ReadEventDao.newEvent", readEvent);

        return readEvent;
    }
}
