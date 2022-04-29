package com.example.demo.controller;

import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.AdvertisementResponse;
import com.example.demo.response.CreateAdvertisementsResponse;
import com.example.demo.response.GetAllAdvertisementsResponse;
import com.example.demo.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<GetAllAdvertisementsResponse> getAllAdvertisements() {
    GetAllAdvertisementsResponse getAllAdvertisementsResponse = advertisementService.getAllAdvertisements();
    return ResponseEntity.ok(getAllAdvertisementsResponse);
  }

  @GetMapping("/{id}")
  private ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable final long id) {
    return ResponseEntity.ok(advertisementService.getAdvertisementsById(id));
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<HttpStatus> deleteAdvertisement(@PathVariable("id") final int id) {
    advertisementService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping()
  private ResponseEntity<CreateAdvertisementsResponse> saveAdvertisement(@RequestBody final CreateAdvertisementsRequest createAdvertisementsRequest) {
    CreateAdvertisementsResponse createAdvertisementsResponse =  advertisementService.createAdvertisement(createAdvertisementsRequest);
    return ResponseEntity.ok(createAdvertisementsResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AdvertisementResponse> updateAdvertisement(@PathVariable final long id,
      @RequestBody final SavingAdvertisementsRequest savingAdvertisementsRequest) {
    AdvertisementResponse advertisementResponse = advertisementService.updateAdvertisements(id, savingAdvertisementsRequest);
    return ResponseEntity.ok(advertisementResponse);
  }
}
