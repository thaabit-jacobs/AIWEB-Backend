package com.AI_WEB_APP.AIWEB.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

//annotations used from springboot
@Entity
@Data
@ToString
@Table(name = "Researchers")
@NoArgsConstructor
@AllArgsConstructor

//reaserch class
public class Researcher {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    //create column Surname
    @Column(name = "Surname")
    private String surname;

    //create column initials
    @Column(name = "Initials")
    private String initials;

    //create column Title
    @Column(name = "Title")
    private String title;

    //create column Institutions
    @Column(name = "Institutions")
    private String institution;

    //create column Rating
    @Column(name = "Rating")
    private String rating;

    //create column startDate
    @Column(name = "RatingStartDate")
    private Date startDate;

    //create column end date
    @Column(name = "RatingEndDate")
    private Date endDate;

    //create column primary fields
    @Column(name = "PrimaryFields")
    private String primaryFields;

    //create column secondary fields
    @Column(name = "SecondaryFields")
    private String secondaryFields;

    //create column specialisations
    @Column(name = "Specialisations", columnDefinition="TEXT", length = 2048)
    private String specialisations;

    //constructor
    public Researcher(String surname, String initials, String title, String institution, String rating, Date start, Date end, String primary, String secondary, String specialisations) {
        this.surname = surname;
        this.initials =initials;
        this.title = title;
        this.institution = institution;
        this.rating = rating;
        this.startDate = start;
        this.endDate = end;
        this.primaryFields = primary;
        this.secondaryFields = secondary;
        this.specialisations = specialisations;

    }
}
