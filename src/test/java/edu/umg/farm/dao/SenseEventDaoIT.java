package edu.umg.farm.dao;


import edu.umg.farm.BaseIT;
import edu.umg.farm.dao.model.ReadEvent;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class SenseEventDaoIT extends BaseIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReadEventDao readEventDao;

    @Test
    public void newEventTest() {

        var savedEvent = readEventDao.saveReadEvent(buildEvent());

        var expectedEvent = getEventById(savedEvent.getId());

        assertThat(savedEvent).isEqualToIgnoringGivenFields(expectedEvent, "created");
    }

    @Test
    public void getLatestEvents() {

        var event = readEventDao.saveReadEvent(buildEvent());
//        var expectedEvent = getEventById(event.getId());

        List<ReadEvent> events = readEventDao.getLatestReadEvents(10);

        assertTrue(events.size() <= 10);
    }

    private ReadEvent buildEvent() {
        return ReadEvent.builder()
                .temperatureValue(10)
                .temperatureDimension("C")
                .temperatureThreshold(20)
                .humidityThreshold(10)
                .humidityValue(10)
                .readError(false)
                .pumpActivated(true)
                .message("message")
                .build();
    }

    private ReadEvent getEventById(long id) {

        return jdbcTemplate.queryForObject(SqlStatements.SELECT_SENSE_EVENT.toString(), new Object[] { id }, (rs, i) ->
                ReadEvent.builder()
                        .id(rs.getLong("id"))
                        .created(rs.getDate("created"))
                        .temperatureValue(rs.getDouble("temperature_value"))
                        .temperatureDimension(rs.getString("temperature_dimension"))
                        .temperatureThreshold(rs.getDouble("temperature_threshold"))
                        .humidityValue(rs.getDouble("humidity_value"))
                        .humidityThreshold(rs.getDouble("humidity_threshold"))
                        .readError(rs.getBoolean("read_error"))
                        .pumpActivated(rs.getBoolean("pump_activated"))
                        .message(rs.getString("message"))
                        .build()
        );
    }

    private static class SqlStatements {

        public static SQL SELECT_SENSE_EVENT = new SQL() {{
            SELECT("*");
            FROM("sense_history");
            WHERE("id = ?");
        }};
    }
}
