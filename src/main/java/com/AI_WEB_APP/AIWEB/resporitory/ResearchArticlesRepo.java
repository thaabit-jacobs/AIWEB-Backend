package com.AI_WEB_APP.AIWEB.resporitory;

import com.AI_WEB_APP.AIWEB.model.ResearchArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Interface that implements the Repository from springboot
 * Repository has built-in functions like save, find, etc
 * Use these functions in ResearcherService to manipulate and create queries in the database
 * */

@Repository
public interface ResearchArticlesRepo extends JpaRepository<ResearchArticles, Long> {
    ResearchArticles findByAuthors(String name);

    ResearchArticles findByid(Long id);
    ResearchArticles findByissn(String issn);
    //use this method, not exact match
    List<ResearchArticles> findByAuthorsContaining(String authname);

    List<ResearchArticles> findByArticleTitleContaining(String title);

    List<ResearchArticles> findByKeywordsContaining(String keyword);
    List<ResearchArticles> findByIssnContaining(String issn);

    //ResearchArticles findByIssn(String issn);

    //ResearchArticles findBydoi(String doi);
    
}