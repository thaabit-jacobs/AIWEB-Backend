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
@Table(name = "ResearchArticles")
@NoArgsConstructor
@AllArgsConstructor



public class ResearchArticles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Publication", columnDefinition="TEXT", length = 2048)
    private String publication;

    @Column(name="Authors", columnDefinition="TEXT", length = 2048)
    private String authors;


    @Column(name="AuthorFullNames", columnDefinition="TEXT", length = 2048)
    private String fullNames;

    @Column(name="ArticleTitle", columnDefinition="TEXT", length = 2048)
    private String articleTitle;

    @Column(name="SourceTitle", columnDefinition="TEXT", length = 2048)
    private String sourceTitle;

    @Column(name="Language")
    private String language;

    @Column(name="DocumentType", columnDefinition="TEXT", length = 2048)
    private String documentType;

    @Column(name="ConferenceTitle", columnDefinition="TEXT", length = 2048)
    private String conferenceTitle;

    @Column(name="ConferenceDate")
    private String conferenceDate;

    @Column(name="ConferenceLocation", columnDefinition="TEXT", length = 2048)
    private String conferenceLocation;

    @Column(name="AuthorKeywords", columnDefinition="TEXT", length = 2048)
    private String keywords;

    @Column(name="KeywordPlus", columnDefinition="TEXT", length = 2048)
    private String keywordPlus;

    @Column(name="ArticleDescription", columnDefinition="TEXT", length = 2048)
    private String articleAbstract;

    @Column(name="CitedReferenceCount")
    private int citedCount;

    @Column(name="DayUsageCount")
    private int dayUsageCount;

    @Column(name="SinceUsageCount")
    private int sinceUsageCount;

    @Column(name="ISSN")
    private String issn;

    @Column(name="JournalAbbreviation", columnDefinition="TEXT", length = 2048)
    private String abbreviation;

    @Column(name="PublicationYear", columnDefinition="TEXT", length = 2048)
    private String year;

    @Column(name="DOI", columnDefinition="TEXT", length = 2048)
    private String doi;
    @Column(name="DOILink", columnDefinition="TEXT", length = 2048)
    private String doiLink;

    //constructor
    public ResearchArticles(String publication, String authors,
                            String fullNames, String articleTitle, String sourceTitle,
                            String language, String documentType, String conferenceTitle, String conferenceDate,
                            String conferenceLocation, String keyword, String keywordPlus, String articleAbstract, int citedCount,
                            int dayCitedUsage, int yearCited, String issn, String abb, String pubYear, String doi, String doiLink)
    {
        this.publication = publication;
        this.authors = authors;
        this.fullNames = fullNames;
        this.articleTitle = articleTitle;
        this.sourceTitle = sourceTitle;
        this.language = language;
        this.documentType = documentType;
        this.conferenceTitle = conferenceTitle;
        this.conferenceDate = conferenceDate;
        this.conferenceLocation = conferenceLocation;
        this.keywords = keyword;
        this.keywordPlus = keywordPlus;
        this.articleAbstract = articleAbstract;
        this.citedCount = citedCount;
        this.dayUsageCount = dayCitedUsage;
        this.sinceUsageCount = yearCited;
        this.issn = issn;
        this.abbreviation = abb;
        this.year = pubYear;
        this.doi = doi;
        this.doiLink = doiLink;

    }


}
