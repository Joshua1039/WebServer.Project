package com.modernwave.web.server.framework.core;

import org.apache.log4j.Logger;

public class Paging {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public long totalCount = 0;
	
	public long totalListSize = 0; 			//����¡�� ����� �Ǽ�
	public long startRowNum =0;				//����¡�� ����� ���ۃ�
	public long endRowNum = 0;					//����¡�� ����� ���ᰪ
	public long viewListSize = 30;			//�ѹ��� ������ ����� ���� 
	public long currPageNum = 0;			//���� ������ ������ ��ȣ
	
	public long startPageNum = 0;			//���� ������ ��ȣ
	public long endPageNum = 0;				//���� ������ ��ȣ
	public long viewPageSize = 10;			//�ѹ��� ������ �������� ����
	public long totalPageSize = 0;			//�� �������� ����
	public long currBlockNum = 0;			//���� ������ block�� ��ȣ    1|2|3|4|5 6|7|8|9|10
	
	public long prePageNum = 0;
	public long nextPageNum = 0;
	public boolean isPrevious = false;
	public boolean isNext = false;
	
	public Paging(){
	}
	
	public Paging(String currPgNum, long totalRowCnt){
		init((currPgNum == null)?1:Integer.parseInt(currPgNum), totalRowCnt);
	}
	
	public Paging(int currPgNum, long totalRowCnt){
		init(currPgNum, totalRowCnt);
	}
	
	
	
	public void init(String currPgNum, long totalRowCnt){
		init((currPgNum == null)?1:Integer.parseInt(currPgNum), totalRowCnt);
	}
	
	public void init(int currPgNum, long totalRowCnt){
		log.debug("currPageNum["+currPgNum+"]totalRowCnt["+totalRowCnt+"]");
		this.totalCount = totalRowCnt;
		if(currPgNum <= 0 || totalRowCnt <= 0){
			this.totalCount = 0;
			this.startRowNum = 0;
			this.endRowNum = 0;
			return;
		}
			
		this.totalListSize = totalRowCnt -1;			//0������� ����
		this.currPageNum = currPgNum -1;			//0������� ����
		
		//����¡�� ��Ͽ� ���� ���
		long tempEndNum = (currPageNum+1)*viewListSize;
		startRowNum = ((currPageNum)*viewListSize)+1 ;		//currPageNum�� 1����̱⶧���� -1 , �ٵȰ���� �����ٶ��� 1����̱⶧���� +1
		endRowNum = (tempEndNum > totalListSize) ? totalListSize+1: tempEndNum;
		
		
		//totalListSize�� 1����̱⶧���� 0������� �������ش�.
		//����¡�� block (ex:   ����  6|7|8|9|10 ����) �� ���� ���
		totalPageSize = ((totalListSize)/viewListSize) +1;
//		System.out.println("totalPageSize["+totalPageSize+"]");
		currBlockNum = (currPageNum)/viewPageSize + 1;
//		System.out.println("currBlockNum["+currBlockNum+"]");
		
		long tempCurrBlockNum = currBlockNum-1;		//0��� 
		startPageNum = ((tempCurrBlockNum)*viewPageSize)+1;	//1����̱� ������
		
		long tempEndPageNum = (tempCurrBlockNum+1)*viewPageSize;
		
		endPageNum = (tempEndPageNum > totalPageSize)?totalPageSize:tempEndPageNum;
		
		if(tempCurrBlockNum > 0){
			isPrevious = true;
			prePageNum = startPageNum -1;
		}
		
		if(endPageNum < totalPageSize){
			isNext = true;
			nextPageNum = endPageNum + 1;
		}
		
		
		currPageNum++;		//1����� ���ڷ� �������ش�
		log.debug("startRowNum["+startRowNum+"]endRowNum["+endRowNum+"] previous["+isPrevious+ "]prePageNum["+prePageNum+"] startPageNum["+startPageNum+"]endPageNum["+endPageNum+"]next[" +isNext+"]nextPageNum["+nextPageNum+"]currPageNum["+currPageNum+"]");
	}
	
	public static void main(String[] arg){
		new Paging (1, 0);
//		new Paging (13, 150);
//		new Paging (14, 150);
//		new Paging (15, 151);
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public long getTotalListSize() {
		return totalListSize;
	}

	public void setTotalListSize(long totalListSize) {
		this.totalListSize = totalListSize;
	}

	public long getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(long startRowNum) {
		this.startRowNum = startRowNum;
	}

	public long getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(long endRowNum) {
		this.endRowNum = endRowNum;
	}

	public long getViewListSize() {
		return viewListSize;
	}

	public void setViewListSize(long viewListSize) {
		this.viewListSize = viewListSize;
	}

	public long getCurrPageNum() {
		return currPageNum;
	}

	public void setCurrPageNum(long currPageNum) {
		this.currPageNum = currPageNum;
	}

	public long getStartPageNum() {
		return startPageNum;
	}

	public void setStartPageNum(long startPageNum) {
		this.startPageNum = startPageNum;
	}

	public long getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(long endPageNum) {
		this.endPageNum = endPageNum;
	}

	public long getViewPageSize() {
		return viewPageSize;
	}

	public void setViewPageSize(long viewPageSize) {
		this.viewPageSize = viewPageSize;
	}

	public long getTotalPageSize() {
		return totalPageSize;
	}

	public void setTotalPageSize(long totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	public long getCurrBlockNum() {
		return currBlockNum;
	}

	public void setCurrBlockNum(long currBlockNum) {
		this.currBlockNum = currBlockNum;
	}

	public long getPrePageNum() {
		return prePageNum;
	}

	public void setPrePageNum(long prePageNum) {
		this.prePageNum = prePageNum;
	}

	public long getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(long nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public boolean isPrevious() {
		return isPrevious;
	}

	public void setPrevious(boolean isPrevious) {
		this.isPrevious = isPrevious;
	}

	public boolean isNext() {
		return isNext;
	}

	public void setNext(boolean isNext) {
		this.isNext = isNext;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	

}
