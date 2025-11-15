package application.models.response;

public class PageableCommon {
	
	// ================================
	//        ATRIBUTOS PRINCIPALES
	// ================================
	private Integer pageNumber;
    private Integer pageSize;
    private SortCommon sort;
    private Long offset;
    private Boolean paged;
    private Boolean unpaged;
    
	// ================================
	//        CONSTRUCTORES
	// ================================
	public PageableCommon() {}
	
	public PageableCommon(Integer pageNumber, Integer pageSize, SortCommon sort, Long offset, Boolean paged,
			Boolean unpaged) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sort = sort;
		this.offset = offset;
		this.paged = paged;
		this.unpaged = unpaged;
	}
	
	// ================================
	//        GETTERS & SETTERS
	// ================================
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public SortCommon getSort() {
		return sort;
	}
	
	public Long getOffset() {
		return offset;
	}
	
	public Boolean getPaged() {
		return paged;
	}
	
	public Boolean getUnpaged() {
		return unpaged;
	}
	
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setSort(SortCommon sort) {
		this.sort = sort;
	}
	
	public void setOffset(Long offset) {
		this.offset = offset;
	}
	
	public void setPaged(Boolean paged) {
		this.paged = paged;
	}
	
	public void setUnpaged(Boolean unpaged) {
		this.unpaged = unpaged;
	}
    
}
