package application.models.response;

public class SortCommon {

	private Boolean sorted;	    
	private Boolean unsorted;
	private Boolean empty;
	
	public SortCommon() {}

	public SortCommon(Boolean sorted, Boolean unsorted, Boolean empty) {
		this.sorted = sorted;
		this.unsorted = unsorted;
		this.empty = empty;
	}

	public Boolean getSorted() {
		return sorted;
	}
	
	public Boolean getUnsorted() {
		return unsorted;
	}
	
	public Boolean getEmpty() {
		return empty;
	}
	
	public void setSorted(Boolean sorted) {
		this.sorted = sorted;
	}
	
	public void setUnsorted(Boolean unsorted) {
		this.unsorted = unsorted;
	}
	
	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}
	
}
