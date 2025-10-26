package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_show_sales_summary")
public class ShowSalesSummary {

    @Id
    @Column(name = "showId")  // Map to actual column name
    private Long showId;

    @Column(name = "showDate")
    private String showDate;

    @Column(name = "showTime")
    private String showTime;

    @Column(name = "movieTitle")
    private String movieTitle;

    @Column(name = "hallName")
    private String hallName;

    @Column(name = "totalSeatsBooked")
    private Integer totalSeatsBooked;

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getTotalSeatsBooked() {
        return totalSeatsBooked;
    }

    public void setTotalSeatsBooked(Integer totalSeatsBooked) {
        this.totalSeatsBooked = totalSeatsBooked;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @Column(name = "totalRevenue")
    private Double totalRevenue;
}