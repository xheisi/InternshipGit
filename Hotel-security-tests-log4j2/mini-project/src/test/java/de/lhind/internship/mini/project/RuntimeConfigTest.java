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
 * Mirrors changing level="TRACE" to level="WARN" in log4j2.xml and restarting
 * the app — except here LogTestHelper re-applies the level directly (same
 * mechanism Log4j itself uses when it re-reads the XML), so we can prove the
 * "no code change needed" behavior in a single automated test instead of
 * manually restarting the app twice.
 */
class RuntimeConfigTest {

    private static final String LOGGER_NAME = "de.lhind.internship.mini.project.service.HotelService";

    @Test
    void changingLevelAtRuntime_changesVisibleOutput_noRestartOrRecompileNeeded() {
        HotelRepository repo = Mockito.mock(HotelRepository.class);
        HotelService service = new HotelService(repo);
        Hotel saved = new Hotel();
        saved.setId(1);
        saved.setName("Runtime Hotel");
        when(repo.save(any())).thenReturn(saved);

        HotelDTO dto = HotelDTO.builder().name("Runtime Hotel").city("Berlin").address("X").starRating(3).build();

        // "Before": level="TRACE" in log4j2.xml
        List<LogEvent> beforeChange = LogTestHelper.capture(LOGGER_NAME, Level.TRACE, () -> service.createHotel(dto));
        assertEquals(3, beforeChange.size());

        // "After": level changed to WARN, app restarted — TRACE/DEBUG/INFO now filtered
        List<LogEvent> afterChange = LogTestHelper.capture(LOGGER_NAME, Level.WARN, () -> service.createHotel(dto));
        assertTrue(afterChange.isEmpty());
    }
}
