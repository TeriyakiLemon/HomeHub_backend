package com.tianchen.homehub_backend.Service;

import com.tianchen.homehub_backend.model.Calendar;
import com.tianchen.homehub_backend.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository repository;

    public int addCalendar(Calendar calendar) {
        return repository.saveCalendar(calendar);
    }

    public List<Calendar> getAllCalendars() {
        return repository.findAll();
    }

    public Optional<Calendar> getCalendarById(Long id) {
        return repository.findById(id);
    }

    public List<Calendar> getCalendarsByDate(String date) {
        return repository.findByDate(date);
    }
}
