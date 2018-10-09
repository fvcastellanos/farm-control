package edu.umg.farm.service;

import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.PumpActivation;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.dao.model.ValueRead;
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

    public Either<String, List<ValueRead>> getTemperatureEvents(int limit) {

        try {

            var events = readEventDao.temperatureLatestReads(limit);

            return Either.right(events);

        } catch (Exception ex) {
            logger.error("can't get latest temperature events - ", ex);
            return Either.left("cant' retrieve temperature events");
        }
    }

    public Either<String, List<ValueRead>> getHumidityEvents(int limit) {

        try {

            var events = readEventDao.humidityLatestReads(limit);

            return Either.right(events);

        } catch (Exception ex) {
            logger.error("can't get latest humidity events - ", ex);
            return Either.left("cant' retrieve humidity events");
        }
    }

    public Either<String, PumpActivation> getPumpActions() {

        try {

            var actions = readEventDao.pumpActivations();

            return actions.isPresent() ? Either.right(actions.get())
                    : Either.left("can't get actions");

        } catch (Exception ex) {
            logger.error("can't get pump actions - ", ex);
            return Either.left("cant' retrieve pump actions");
        }
    }

}
