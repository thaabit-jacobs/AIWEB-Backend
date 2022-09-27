package com.AI_WEB_APP.AIWEB.resporitory;

import com.AI_WEB_APP.AIWEB.model.Researcher;
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
public interface ResearcherRepo extends JpaRepository<Researcher, Integer> {
    List<Researcher> findBySurnameContaining(String surname);

    Researcher findByInstitution(String institution);
    List<Researcher> findBySpecialisationsContaining(String specialisation);


    
    //List<Researcher> findByRating(String rating);

   // List<Researcher> findByTitle(String title);

    

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
     */
/*
     * @Query(value =
     * "SELECT COUNT(*) AS InstitutionCount, Institutions FROM Researchers GROUP BY Institutions"
     * , nativeQuery = true)
     * List<InstitutionCount> findAllByInstitutionCount();
     */

}
