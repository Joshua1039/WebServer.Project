package com.modernwave.web.server.model;

public class UserVo {

	public String userId;				//����� id
	public String userName;				//����� �̸�
	public String deptCode;				//�μ��ڵ�
	public String branchCode;			//����� �ڵ�
	public String gradeCode;			//���� �ڵ� (1.������ 2.����)
	public String userTheme;			//����� �׸�
	public String listenYn;				//������ȸû�� �޴� û�뿩��
	public String saveYn;				//������ȸû�� �޴� ���忩��
	
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
