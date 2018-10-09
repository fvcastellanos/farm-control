package edu.umg.farm.dao;

import edu.umg.farm.dao.model.PumpActivation;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.dao.model.ValueRead;

import java.util.List;
import java.util.Optional;

public interface ReadEventDao {

    ReadEvent saveReadEvent(ReadEvent readEvent);

    List<ReadEvent> getLatestReadEvents(int limit);

    List<ValueRead> temperatureLatestReads(int limit);

    List<ValueRead> humidityLatestReads(int limit);

    Optional<PumpActivation> pumpActivations();

}
