package com.example.journalApp.Controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal/v2")
public class JournalEntryControllerv2 {

    private Map<Long,JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll()
    {
        return journalEntryService.getAllEntries();
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id)
    {
        return journalEntryService.getEntryById(id).orElse(null);
    }

    @PostMapping
    public JournalEntry CreateEntry(@RequestBody JournalEntry myEntry)
    {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @DeleteMapping("/id/{id}")
    public void DeleteEntryById(@PathVariable ObjectId id)
    {
        journalEntryService.DeleteEntryById(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry NewEntry)
    {
        JournalEntry myEntry = journalEntryService.getEntryById(id).orElse(null);
        if(myEntry != null)
        {
            myEntry.setTitle(NewEntry.getTitle() != null && !NewEntry.getTitle().isEmpty() ? NewEntry.getTitle() : myEntry.getTitle());
            myEntry.setContent(NewEntry.getContent() != null && !NewEntry.getContent().isEmpty() ? NewEntry.getContent() : myEntry.getContent());
        }
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

}
