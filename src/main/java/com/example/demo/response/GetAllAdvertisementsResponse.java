package com.example.demo.response;

import java.util.List;

public class GetAllAdvertisementsResponse {

    public GetAllAdvertisementsResponse() {}

    private List<AdvertisementResponse> allAdvertisements;

    public GetAllAdvertisementsResponse(List<AdvertisementResponse> allAdvertisements) {
        this.allAdvertisements = allAdvertisements;
    }
    public List<AdvertisementResponse> getAllAdvertisements() {
        return allAdvertisements;
    }

    public void setAllUsers(List<AdvertisementResponse> allAdvertisements) {
        this.allAdvertisements = allAdvertisements;
    }
}
