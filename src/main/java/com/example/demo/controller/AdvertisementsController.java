package com.example.demo.controller;

import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.AdvertisementResponse;
import com.example.demo.response.CreateAdvertisementsResponse;
import com.example.demo.response.GetAllAdvertisementsResponse;
import com.example.demo.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementsController.class);
  @Autowired
  private AdvertisementService advertisementService;

  @GetMapping()
  public ResponseEntity<GetAllAdvertisementsResponse> getAllAdvertisements() {
    GetAllAdvertisementsResponse getAllAdvertisementsResponse = advertisementService.getAllAdvertisements();
    LOGGER.info("Advertisements: {}", getAllAdvertisementsResponse);
    return ResponseEntity.ok(getAllAdvertisementsResponse);
  }

  @GetMapping("/{id}")
  private ResponseEntity<AdvertisementResponse> getAdvertisementById(@Valid @PathVariable final long id) {
    LOGGER.info("Advertisement with id: " + id + " " + advertisementService.getAdvertisementsById(id));
    return ResponseEntity.ok(advertisementService.getAdvertisementsById(id));
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<HttpStatus> deleteAdvertisement(@Valid @PathVariable("id") final int id) {
    advertisementService.delete(id);
    LOGGER.info("Deleted advertisement with id: " + id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping()
  private ResponseEntity<CreateAdvertisementsResponse> saveAdvertisement(
      @Valid @RequestBody final CreateAdvertisementsRequest createAdvertisementsRequest) {
    CreateAdvertisementsResponse createAdvertisementsResponse = advertisementService.createAdvertisement(createAdvertisementsRequest);
    LOGGER.info("Created new advertisement: {}", createAdvertisementsRequest);
    return ResponseEntity.ok(createAdvertisementsResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AdvertisementResponse> updateAdvertisement(@Valid @PathVariable final long id,
      @Valid @RequestBody final SavingAdvertisementsRequest savingAdvertisementsRequest) {
    AdvertisementResponse advertisementResponse = advertisementService.updateAdvertisements(id, savingAdvertisementsRequest);
    LOGGER.info("Updated advertisement with id: " + id);
    return ResponseEntity.ok(advertisementResponse);
  }
}
