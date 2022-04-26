package com.example.demo.controller;

import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.AdvertisementResponse;
import com.example.demo.response.CreateAdvertisementsResponse;
import com.example.demo.response.GetAllAdvertisementsResponse;
import com.example.demo.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementsController {
  @Autowired
  private AdvertisementService advertisementService;

  @GetMapping()
  public GetAllAdvertisementsResponse getAllAdvertisements() {
    return advertisementService.getAllAdvertisements();
  }

  @GetMapping("/{id}")
  private AdvertisementResponse getAdvertisementById(@PathVariable final long id) {
    return advertisementService.getAdvertisementsById(id);
  }

  @DeleteMapping("/{id}")
  private void deleteAdvertisement(@PathVariable("id") final int id) {
    advertisementService.delete(id);
  }

  @PostMapping()
  private CreateAdvertisementsResponse saveAdvertisement(@RequestBody final CreateAdvertisementsRequest createAdvertisementsRequest) {
    return advertisementService.createAdvertisement(createAdvertisementsRequest);
  }

  @PutMapping("/{id}")
  public AdvertisementResponse updateAdvertisement(@PathVariable final long id,
      @RequestBody final SavingAdvertisementsRequest savingAdvertisementsRequest) {
    return advertisementService.updateAdvertisements(id, savingAdvertisementsRequest);
  }
}
