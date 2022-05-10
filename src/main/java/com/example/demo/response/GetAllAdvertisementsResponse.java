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

  @Override
  public String toString() {
    String allAdvertisementsString = "";
    for (AdvertisementResponse allAdvertisement : allAdvertisements) {
      allAdvertisementsString += " " + allAdvertisement.toString();
    }
    return allAdvertisementsString;
  }
}
