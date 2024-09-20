package com.tianchen.homehub_backend.Controller;

import com.tianchen.homehub_backend.Service.CalendarService;
import com.tianchen.homehub_backend.model.Calendar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/Add")
    public ResponseEntity<String> addCalendar(@RequestBody Calendar calendar) {
        int result = calendarService.addCalendar(calendar);
        if (result == 1) {
            return ResponseEntity.ok("Calendar event added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add calendar event");
        }
    }

    @GetMapping("/Get")
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        List<Calendar> calendars = calendarService.getAllCalendars();
        return ResponseEntity.ok(calendars);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<Calendar> getCalendarById(@PathVariable Long id) {
        Optional<Calendar> calendar = calendarService.getCalendarById(id);
        return calendar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getByDate")
    public ResponseEntity<List<Calendar>> getCalendarsByDate(@RequestParam String date) {
        List<Calendar> calendars = calendarService.getCalendarsByDate(date);
        return ResponseEntity.ok(calendars);
    }
}
