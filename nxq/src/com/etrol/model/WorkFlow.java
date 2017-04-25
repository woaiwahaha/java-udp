package com.etrol.model;

public class WorkFlow
{
  private String id;
  private String carcode;
  private String assayresultid;
  private String conciergeDate;
  private String weightNet;

  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getCarcode() {
    return this.carcode;
  }
  public void setCarcode(String carcode) {
    this.carcode = carcode;
  }
  public String getAssayresultid() {
    return this.assayresultid;
  }
  public void setAssayresultid(String assayresultid) {
    this.assayresultid = assayresultid;
  }
public String getConciergeDate() {
	return conciergeDate;
}
public void setConciergeDate(String conciergeDate) {
	this.conciergeDate = conciergeDate;
}
public String getWeightNet() {
	return weightNet;
}
public void setWeightNet(String weightNet) {
	this.weightNet = weightNet;
}
@Override
public String toString() {
	return "WorkFlow [id=" + id + ", carcode=" + carcode + ", assayresultid=" + assayresultid + ", conciergeDate="
			+ conciergeDate + ", weightNet=" + weightNet + "]";
}

  
}