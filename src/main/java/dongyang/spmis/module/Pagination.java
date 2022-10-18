package dongyang.spmis.module;


public class Pagination {
	

	/** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
	private int pageSize = 10;
	
	/** 2. 페이징된 버튼의 블럭당 최대 개수 
	 *  페이징된 버튼들이 노출되는 최대 갯수
	 * **/
	private int blockSize = 10;

	/** 3. 현재 페이지 **/
	private int page = 1;

	/** 4. 현재 블럭 **/
	private int block = 1;

	/** 5. 총 게시글 수 **/
	private int totalListCnt;

	/** 6. 총 페이지 수 **/
	private int totalPageCnt;

	/** 7. 총 블럭 수 
	 *  blockSize로 인해서 생성된 블럭의 총 개수
	 * **/
	private int totalBlockCnt;

	/** 8. 블럭 시작 페이지 **/
	private int startPage = 1;

	/** 9. 블럭 마지막 페이지 **/
	private int endPage = 1;

	/** 10. DB 접근 시작 index 
	 *  DB에서 접근해서 게시물을 select할 때, 어디서부터 가져올 것인지에 대한 offset 역할을 해주는 부분
	 * **/
	private int startIndex = 0;

	/** 11. 이전 블럭의 마지막 페이지 **/
	private int prevBlock;

	/** 12. 다음 블럭의 시작 페이지 **/
	private int nextBlock;

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getBlockSize() {
		return blockSize;
	}


	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getBlock() {
		return block;
	}


	public void setBlock(int block) {
		this.block = block;
	}


	public int getTotalListCnt() {
		return totalListCnt;
	}


	public void setTotalListCnt(int totalListCnt) {
		this.totalListCnt = totalListCnt;
	}


	public int getTotalPageCnt() {
		return totalPageCnt;
	}


	public void setTotalPageCnt(int totalPageCnt) {
		this.totalPageCnt = totalPageCnt;
	}


	public int getTotalBlockCnt() {
		return totalBlockCnt;
	}


	public void setTotalBlockCnt(int totalBlockCnt) {
		this.totalBlockCnt = totalBlockCnt;
	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	public int getPrevBlock() {
		return prevBlock;
	}


	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}


	public int getNextBlock() {
		return nextBlock;
	}


	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}


	
	
	
	// 총 게시물 수와 현재 페이지를 controller로부터 받아온다.
	public Pagination(int totalListCnt, int page) {
		
		// 총 게시물 수	- totalListCnt
				// 현재 페이지	- page
				
				
			/** 3. 현재 페이지 **/
			setPage(page);
			
	        
			/** 5. 총 게시글 수 **/
			setTotalListCnt(totalListCnt);


			/** 6. 총 페이지 수 **/
			// 한 페이지의 최대 개수를 총 게시물 수 * 1.0로 나누어주고 올림 해준다.
			// 총 페이지 수 를 구할 수 있다.
			setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));


			/** 7. 총 블럭 수 **/
			// 한 블럭의 최대 개수를 총  페이지의 수 * 1.0로 나누어주고 올림 해준다.
			// 총 블럭 수를 구할 수 있다.
			setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));
			
	        
			/** 4. 현재 블럭 **/
			// 현재 페이지 * 1.0을 블록의 최대 개수로 나누어주고 올림한다.
			// 현재 블록을 구할 수 있다.
			setBlock((int) Math.ceil((page * 1.0)/blockSize)); 


			/** 8. 블럭 시작 페이지 **/
			setStartPage((block - 1) * blockSize + 1);
			
	        
			/** 9. 블럭 마지막 페이지 **/
			setEndPage(startPage + blockSize - 1);
			
	        
			/* === 블럭 마지막 페이지에 대한 validation ===*/
			if(endPage > totalPageCnt){this.endPage = totalPageCnt;}
			
	        
			/** 11. 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
			setPrevBlock((block * blockSize) - blockSize);


			/* === 이전 블럭에 대한 validation === */
			if(prevBlock < 1) {this.prevBlock = 1;}


			/** 12. 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
			setNextBlock((block * blockSize) + 1);
			
	        
			/* === 다음 블럭에 대한 validation ===*/
			if(nextBlock > totalPageCnt) {nextBlock = totalPageCnt;}
			
	        
			/** 10. DB 접근 시작 index **/
			setStartIndex((page-1) * pageSize);
		
	}

}
