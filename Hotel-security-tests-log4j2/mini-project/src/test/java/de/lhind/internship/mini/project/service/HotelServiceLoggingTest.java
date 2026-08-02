package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.repository.HotelRepository;
import de.lhind.internship.mini.project.util.LogTestHelper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HotelServiceLoggingTest {

    private static final String LOGGER_NAME = "de.lhind.internship.mini.project.service.HotelService";

    @Test
    void createHotel_success_logsTraceDebugInfo() {
        HotelRepository repo = Mockito.mock(HotelRepository.class);
        HotelService service = new HotelService(repo);

        Hotel saved = new Hotel();
        saved.setId(1);
        saved.setName("Grand Plaza");
        when(repo.save(any())).thenReturn(saved);

        HotelDTO dto = HotelDTO.builder().name("Grand Plaza").city("Berlin").address("Main St 1").starRating(4).build();

        List<LogEvent> events = LogTestHelper.capture(LOGGER_NAME, Level.TRACE, () -> service.createHotel(dto));

        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.TRACE));
        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.DEBUG));
        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.INFO));
    }

    @Test
    void getHotel_notFound_logsWarnAndThrows() {
        HotelRepository repo = Mockito.mock(HotelRepository.class);
        HotelService service = new HotelService(repo);
        when(repo.findById(99)).thenReturn(Optional.empty());

        List<LogEvent> events = LogTestHelper.capture(LOGGER_NAME, Level.TRACE,
                () -> assertThrows(ResponseStatusException.class, () -> service.getHotel(99)));

        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.WARN));
    }
}
