package de.lhind.internship.mini.project;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.repository.HotelRepository;
import de.lhind.internship.mini.project.service.HotelService;
import de.lhind.internship.mini.project.util.LogTestHelper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Same idea as the original Log4j demo's PriorityLevelTest: prove that the
 * configured level filters out anything less severe, for every level Log4j
 * defines. createHotel() always fires exactly TRACE, DEBUG, then INFO — so
 * how many of those 3 events survive tells us the filter worked correctly.
 */
class PriorityLevelTest {

    private static final String LOGGER_NAME = "de.lhind.internship.mini.project.service.HotelService";

    private List<LogEvent> runCreateHotelAt(Level level) {
        HotelRepository repo = Mockito.mock(HotelRepository.class);
        HotelService service = new HotelService(repo);
        Hotel saved = new Hotel();
        saved.setId(1);
        saved.setName("Test Hotel");
        when(repo.save(any())).thenReturn(saved);

        HotelDTO dto = HotelDTO.builder().name("Test Hotel").city("Berlin").address("X").starRating(3).build();
        return LogTestHelper.capture(LOGGER_NAME, level, () -> service.createHotel(dto));
    }

    @Test
    void whenLevelTrace_allThreeEventsVisible() {
        assertEquals(3, runCreateHotelAt(Level.TRACE).size()); // TRACE + DEBUG + INFO
    }

    @Test
    void whenLevelDebug_debugAndInfoVisible() {
        assertEquals(2, runCreateHotelAt(Level.DEBUG).size());
    }

    @Test
    void whenLevelInfo_onlyInfoVisible() {
        List<LogEvent> events = runCreateHotelAt(Level.INFO);
        assertEquals(1, events.size());
        assertEquals(Level.INFO, events.get(0).getLevel());
    }

    @Test
    void whenLevelOff_noMessagesProduced() {
        assertTrue(runCreateHotelAt(Level.OFF).isEmpty());
    }

    @Test
    void whenLevelAll_everythingVisible() {
        assertEquals(3, runCreateHotelAt(Level.ALL).size());
    }
}
