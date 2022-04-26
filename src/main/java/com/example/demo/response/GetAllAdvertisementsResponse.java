package com.example.demo.response;

import java.util.List;

public class GetAllAdvertisementsResponse {

  private List<AdvertisementResponse> allAdvertisements;

  public GetAllAdvertisementsResponse() {
  }

  public GetAllAdvertisementsResponse(final List<AdvertisementResponse> allAdvertisements) {
    this.allAdvertisements = allAdvertisements;
  }

  public List<AdvertisementResponse> getAllAdvertisements() {
    return allAdvertisements;
  }

  //public void setAllUsers(final List<AdvertisementResponse> allAdvertisements) {this.allAdvertisements = allAdvertisements;}
}
