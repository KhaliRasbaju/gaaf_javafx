package application.models.response;

import java.util.List;

public class ReporteInventarioProductoBodega {

    // ================================
    //        ATRIBUTOS PRINCIPALES
    // ================================
	private List<InventarioProductoBodega> content;
    private PageableCommon pageable;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
    private Integer size;
    private Integer number;
    private SortCommon sort;
    private Integer numberOfElements;
    private Boolean first;
    private Boolean empty;
    
    // ================================
    //        CONSTRUCTORES
    // ================================
	public ReporteInventarioProductoBodega() {}

	public ReporteInventarioProductoBodega(List<InventarioProductoBodega> content, PageableCommon pageable,
			Long totalElements, Integer totalPages, Boolean last, Integer size, Integer number, SortCommon sort,
			Integer numberOfElements, Boolean first, Boolean empty) {
		this.content = content;
		this.pageable = pageable;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
		this.size = size;
		this.number = number;
		this.sort = sort;
		this.numberOfElements = numberOfElements;
		this.first = first;
		this.empty = empty;
	}

	// ================================
	//        GETTERS & SETTERS
	// ================================
	public List<InventarioProductoBodega> getContent() {
		return content;
	}

	public PageableCommon getPageable() {
		return pageable;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Boolean getLast() {
		return last;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getNumber() {
		return number;
	}

	public SortCommon getSort() {
		return sort;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public Boolean getFirst() {
		return first;
	}

	public Boolean getEmpty() {
		return empty;
	}

	public void setContent(List<InventarioProductoBodega> content) {
		this.content = content;
	}

	public void setPageable(PageableCommon pageable) {
		this.pageable = pageable;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setSort(SortCommon sort) {
		this.sort = sort;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}
	    
}
