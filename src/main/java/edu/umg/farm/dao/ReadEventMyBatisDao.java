package edu.umg.farm.dao;

import edu.umg.farm.dao.model.PumpActivation;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.dao.model.ValueRead;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ReadEvent> getLatestReadEvents(int limit) {

        return sqlSession.selectList("ReadEventDao.readLatestEvents", limit);
    }

    @Override
    public List<ValueRead> temperatureLatestReads(int limit) {
        return sqlSession.selectList("ReadEventDao.temperatureLatestEvents", limit);
    }

    @Override
    public List<ValueRead> humidityLatestReads(int limit) {
        return sqlSession.selectList("ReadEventDao.humidityLatestEvents", limit);
    }

    @Override
    public Optional<PumpActivation> pumpActivations() {
        return Optional.ofNullable(sqlSession.selectOne("ReadEventDao.pumpActivation"));
    }

}
