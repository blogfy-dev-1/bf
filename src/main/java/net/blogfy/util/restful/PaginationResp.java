package net.blogfy.util.restful;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.Getter;
import lombok.Setter;

public class PaginationResp<T> {
	
	@Getter
	@Setter
	private long currPage; // 当前第几页
	
	@Getter
	@Setter
	private long totalPages; // 总共几页
	
	@Getter
	@Setter
	private long totalRecords; // 总记录数
	
	@Getter
	@Setter
	private long pageSize; // 每页记录数
	
	@Getter
	@Setter
	private List<T> dataList;
	
	public PaginationResp(IPage<T> iPage) {
		currPage = iPage.getCurrent();
		totalPages = iPage.getPages();
		totalRecords = iPage.getTotal();
		pageSize = iPage.getSize();
		dataList = iPage.getRecords();
	}
	
}
