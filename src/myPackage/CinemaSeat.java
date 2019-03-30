package myPackage;


import java.time.*;
import java.util.Date;
public class CinemaSeat {
	private String seatNumber;
	
	private LocalDate movieDate;
	private Movie movie ;
	
	public CinemaSeat(String seatNumber, LocalDate movieDate, Movie movie) {
		this.seatNumber = seatNumber;
		this.movieDate = movieDate;
		this.movie = movie;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public LocalDate getMovieDate() {
		return movieDate;
	}
	public void setMovieDate(LocalDate movieDate) {
		this.movieDate = movieDate;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	

	
}
