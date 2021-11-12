package com.modernwave.web.server.model;

public class UserVo {

	public String userId;				//사용자 id
	public String userName;				//사용자 이름
	public String deptCode;				//부서코드
	public String branchCode;			//사업장 코드
	public String gradeCode;			//권한 코드 (1.관리자 2.평가자)
	public String userTheme;			//사용자 테마
	public String listenYn;				//녹취조회청취 메뉴 청취여부
	public String saveYn;				//녹취조회청취 메뉴 저장여부
	
	public String toString(){
		return "userid["+userId+"]userName["+userName+"]";
	}

//
//	public static void main(String[] a){
//		UserVo user = new UserVo();
//		user.setUserId("aaaa");
//		System.out.println(user.toString());
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getUserTheme() {
		return userTheme;
	}
	
	public void setUserTheme(String userTheme) {
		this.userTheme = userTheme;
	}
	
	public String getListenYn() {
		return listenYn;
	}
	
	public void setListenYn(String listenYn) {
		this.listenYn = listenYn;
	}

	public String getSaveYn() {
		return saveYn;
	}
	
	public void setSaveYn(String saveYn) {
		this.saveYn = saveYn;
	}
	
}
