package com.etrol.model;

public class BaseInfoAssayResult
{

  private String isImpurityDeduction;
  private String isWaterDeduction;
  private String itemCode;
  private String storeHouseCode;
  private String gradeCode;

  
  
  public String getIsImpurityDeduction() {
	return isImpurityDeduction;
}
public void setIsImpurityDeduction(String isImpurityDeduction) {
	this.isImpurityDeduction = isImpurityDeduction;
}
public String getIsWaterDeduction() {
	return isWaterDeduction;
}
public void setIsWaterDeduction(String isWaterDeduction) {
	this.isWaterDeduction = isWaterDeduction;
}
public String getItemCode() {
	return itemCode;
}
public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
}
public String getStoreHouseCode() {
	return storeHouseCode;
}
public void setStoreHouseCode(String storeHouseCode) {
	this.storeHouseCode = storeHouseCode;
}

public String getGradeCode() {
	return gradeCode;
}
public void setGradeCode(String gradeCode) {
	this.gradeCode = gradeCode;
}
@Override
public String toString() {
	return "BaseInfoAssayResult [isImpurityDeduction=" + isImpurityDeduction + ", isWaterDeduction=" + isWaterDeduction
			+ ", itemCode=" + itemCode + ", storeHouseCode=" + storeHouseCode + ", gradeCode=" + gradeCode + "]";
}



}