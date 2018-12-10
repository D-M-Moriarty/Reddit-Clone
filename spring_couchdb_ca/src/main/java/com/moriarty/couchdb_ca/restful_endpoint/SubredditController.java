package com.moriarty.couchdb_ca.restful_endpoint;

import com.moriarty.couchdb_ca.db_repository.SubredditRepository;
import com.moriarty.couchdb_ca.entity.Subreddit;
import com.moriarty.couchdb_ca.entity.User;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
// To allow for cross origin support CORS
@CrossOrigin(origins = "http://localhost:3000")
// base url
@RequestMapping("r")
public class SubredditController {
    @Autowired
    // Subreddit Repoitory instance
    private SubredditRepository subredditRepository;
    // HTTP GET all the subreddits from CouchDB
    @GetMapping("")
    public List<Subreddit> getAllSubreddits() {
        return subredditRepository.getAll();
    }
    // HTTP GET a subreddit by its id
    @GetMapping("/{id}")
    public Subreddit getSubredditById(@PathVariable String id) {
        return subredditRepository.get(id);
    }
    // HTTP POST add a new subreddit
    @PostMapping("")
    public Subreddit addSubreddit(@RequestBody Subreddit subreddit) {
        subredditRepository.add(subreddit);
        return subreddit;
    }
    // HTTP GET a subreddit by its name
    @GetMapping("/name/{name}")
    public List<Subreddit> findByName(@PathVariable String name) {
        return subredditRepository.findByName(name);
    }
    // HTTP GET a subreddit by its description
    @GetMapping("/description/{description}")
    public List<Subreddit> findByDescription(@PathVariable String description) {
        return subredditRepository.findByDescription(description);
    }
    // HTTP GET call to mapreduce to get the number of subreddits
    @GetMapping("/num_of_subreddits")
    public int getSumOfSubreddits() {
        return subredditRepository.getSumOfSubreddits();
    }
    // HTTP PUT to update a subreddit by its id
    @PutMapping("/{id}")
    public Subreddit updateSubreddit(@PathVariable String id, @RequestBody Subreddit subr) {
        Subreddit subreddit = subredditRepository.get(id);
        subr.setId(id);
        subr.setRevision(subreddit.getRevision());
        subredditRepository.update(subr);
        return subreddit;
    }
    // HTTP DELETE a subreddit by its id
    @DeleteMapping("/{id}")
    public void deleteSubreddit(@PathVariable String id) {
        Subreddit subreddit = subredditRepository.get(id);
        subredditRepository.remove(subreddit);
    }
}
