package edu.umg.farm.service;

import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.ReadEvent;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InformationService {

    private static Logger logger = LoggerFactory.getLogger(InformationService.class);

    private ReadEventDao readEventDao;

    public InformationService(ReadEventDao readEventDao) {
        this.readEventDao = readEventDao;
    }

    public Either<String, List<ReadEvent>> getLatestReadEvents(int limit) {

        try {

            var events = readEventDao.getLatestReadEvents(limit);

            return Either.right(events);
        } catch (Exception ex) {
            logger.error("can't retrieve read events - ", ex);
            return Either.left("can't retrieve read events");
        }
    }

}
