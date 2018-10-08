package edu.umg.farm.dao;

import edu.umg.farm.dao.model.ReadEvent;

import java.util.List;

public interface ReadEventDao {

    ReadEvent saveReadEvent(ReadEvent readEvent);

    List<ReadEvent> getLatestReadEvents(int limit);

}
