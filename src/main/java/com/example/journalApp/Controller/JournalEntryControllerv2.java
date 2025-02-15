package com.example.journalApp.Controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id)
    {
        Optional<JournalEntry> entry = journalEntryService.getEntryById(id);
        if(entry.isPresent())
        {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> CreateEntry(@RequestBody JournalEntry myEntry)
    {
        if(Objects.requireNonNull(myEntry).getTitle() != null || Objects.requireNonNull(myEntry).getContent() != null){
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> DeleteEntryById(@PathVariable ObjectId id)
    {
        JournalEntry myEntry = journalEntryService.getEntryById(id).orElse(null);
        if(myEntry != null) {
            journalEntryService.DeleteEntryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry NewEntry)
    {
        JournalEntry myEntry = journalEntryService.getEntryById(id).orElse(null);
        if(myEntry != null)
        {
            myEntry.setTitle(NewEntry.getTitle() != null && !NewEntry.getTitle().isEmpty() ? NewEntry.getTitle() : myEntry.getTitle());
            myEntry.setContent(NewEntry.getContent() != null && !NewEntry.getContent().isEmpty() ? NewEntry.getContent() : myEntry.getContent());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
