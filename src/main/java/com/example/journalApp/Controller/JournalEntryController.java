package com.example.journalApp.Controller;

import com.example.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll()
    {
        System.out.println(journalEntries.size());
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable long id)
    {
        return journalEntries.get(id);
    }

    @PostMapping
    boolean CreateEntry(@RequestBody JournalEntry myEntry)
    {
        journalEntries.put(myEntry.getId(), myEntry);
        System.out.println(myEntry.toString());
        System.out.println(journalEntries.size());
        return true;
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry DeleteEntryById(@PathVariable long id)
    {
        return journalEntries.remove(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntryById(@PathVariable long id, @RequestBody JournalEntry entry)
    {
        return journalEntries.put(id,entry);
    }

}
