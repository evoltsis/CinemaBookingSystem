package myPackage;

public class Movie {
	private String title;
	private String showTime;
	
	public Movie(String title,String showTime) {
		this.title = title;
		this.showTime = showTime;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	
}
