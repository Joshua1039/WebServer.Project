package com.modernwave.web.server.model;

public class RecordSearchVo {
	
	public RecordSearchVo(){
	}

	public String startDate = "";				//������� ����
	public String endDate = "";					//������� ����
	public String startHour = "";				//���ð� ����
	public String startTime = "";				//���ð� ����
	public String startHourTime = "";
	public String endHour = "";					//���ð� ����
	public String endTime = "";					//���ð� ����
	public String endHourTime = "";
	public String typeCode = "";				//��ȭ����
	public String estimateBit = "";				//�򰡿���
	public String deptCode = "";				//�μ�
	public String apuserDef1 = "";			//���з�1
	public String apuserDef2 = "";			//���з�2
	public String apuserDef3 = "";			//���з�3
	public String recordStartTime = "";			//�������۽ð�
	public String recordEndTime = "";			//��������ð�
	public String agentCondition = "";		//���� �˻� ����
	public String agentValue = "";			//���� �˻���
	
	public String agentTelNo = "";			//������ȣ
	public String agentId = "";				//���� ID
	public String agentName = "";			//������
	
	public String custCondition = "";		//�� �˻� ����
	public String custValue = "";			//�� �˻� ��
	public String custAccount = "";			//�� ���¹�ȣ
	public String custName = "";			//����
	public String custSsn = "";				//���ֹι�ȣ
	public String custTelNo = "";			//����ȭ��ȣ
	
	
	public String listenBit = "";			//û�뿩��
	public String[] recTables;
	
	public String startRowIndex;			//����¡ ���� ��
	public String endRowIndex;			//����¡ ���� ��
	
	public String branchCode = "";			//�����ڵ�
	
	
//	public String listType = "";		//1: from ������ȸ, 2: from �� ����
//	public String userId = "";
	
	
	
	
	
//	public String TOTAL_COUNT = "";			//��ȸ�� ����� �� count
//	public String MAX_ROW_COUNT = "";		//���������� ������ �ִ� row ����
//	public String RET;					//���ν��� ȣ�� �� ��� �ڵ�
//	public String RET_MSG;				//���ν��� ȣ�� �� ��� �޼���
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartHourTime() {
		return startHourTime;
	}
	public void setStartHourTime(String startHourTime) {
		this.startHourTime = startHourTime;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndHourTime() {
		return endHourTime;
	}
	public void setEndHourTime(String endHourTime) {
		this.endHourTime = endHourTime;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getEstimateBit() {
		return estimateBit;
	}
	public void setEstimateBit(String estimateBit) {
		this.estimateBit = estimateBit;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getApuserDef1() {
		return apuserDef1;
	}
	public void setApuserDef1(String apuserDef1) {
		this.apuserDef1 = apuserDef1;
	}
	public String getApuserDef2() {
		return apuserDef2;
	}
	public void setApuserDef2(String apuserDef2) {
		this.apuserDef2 = apuserDef2;
	}
	public String getApuserDef3() {
		return apuserDef3;
	}
	public void setApuserDef3(String apuserDef3) {
		this.apuserDef3 = apuserDef3;
	}
	public String getRecordStartTime() {
		return recordStartTime;
	}
	public void setRecordStartTime(String recordStartTime) {
		this.recordStartTime = recordStartTime;
	}
	public String getRecordEndTime() {
		return recordEndTime;
	}
	public void setRecordEndTime(String recordEndTime) {
		this.recordEndTime = recordEndTime;
	}
	public String getAgentCondition() {
		return agentCondition;
	}
	public void setAgentCondition(String agentCondition) {
		this.agentCondition = agentCondition;
	}
	public String getAgentValue() {
		return agentValue;
	}
	public void setAgentValue(String agentValue) {
		this.agentValue = agentValue;
	}
	public String getAgentTelNo() {
		return agentTelNo;
	}
	public void setAgentTelNo(String agentTelNo) {
		this.agentTelNo = agentTelNo;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getCustCondition() {
		return custCondition;
	}
	public void setCustCondition(String custCondition) {
		this.custCondition = custCondition;
	}
	public String getCustValue() {
		return custValue;
	}
	public void setCustValue(String custValue) {
		this.custValue = custValue;
	}
	public String getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustSsn() {
		return custSsn;
	}
	public void setCustSsn(String custSsn) {
		this.custSsn = custSsn;
	}
	public String getCustTelNo() {
		return custTelNo;
	}
	public void setCustTelNo(String custTelNo) {
		this.custTelNo = custTelNo;
	}
	public String getListenBit() {
		return listenBit;
	}
	public void setListenBit(String listenBit) {
		this.listenBit = listenBit;
	}
	public String[] getRecTables() {
		return recTables;
	}
	public void setRecTables(String[] recTables) {
		this.recTables = recTables;
	}
	public String getStartRowIndex() {
		return startRowIndex;
	}
	public void setStartRowIndex(String startRowIndex) {
		this.startRowIndex = startRowIndex;
	}
	public String getEndRowIndex() {
		return endRowIndex;
	}
	public void setEndRowIndex(String endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
//	public String getListType() {
//		return listType;
//	}
//	public void setListType(String listType) {
//		this.listType = listType;
//	}
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public String getTOTAL_COUNT() {
//		return TOTAL_COUNT;
//	}
//	public void setTOTAL_COUNT(String tOTAL_COUNT) {
//		TOTAL_COUNT = tOTAL_COUNT;
//	}
//	public String getMAX_ROW_COUNT() {
//		return MAX_ROW_COUNT;
//	}
//	public void setMAX_ROW_COUNT(String mAX_ROW_COUNT) {
//		MAX_ROW_COUNT = mAX_ROW_COUNT;
//	}
//	public String getRET() {
//		return RET;
//	}
//	public void setRET(String rET) {
//		RET = rET;
//	}
//	public String getRET_MSG() {
//		return RET_MSG;
//	}
//	public void setRET_MSG(String rET_MSG) {
//		RET_MSG = rET_MSG;
//	}
//
//	
}

