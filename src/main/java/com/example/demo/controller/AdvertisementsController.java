package com.example.demo.controller;

import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.*;
import com.example.demo.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private AdvertisementResponse getAdvertisementById(@PathVariable long id) {
        return advertisementService.getAdvertisementsById(id) ;
    }

    @DeleteMapping("/{id}")
    private void deleteAdvertisement(@PathVariable("id") int id) {
        advertisementService.delete(id);
    }

    @PostMapping()
    private CreateAdvertisementsResponse saveAdvertisement(@RequestBody CreateAdvertisementsRequest createAdvertisementsRequest) {
        return advertisementService.createAdvertisement(createAdvertisementsRequest);
    }

    @PutMapping("/{id}")
    public AdvertisementResponse updateAdvertisement(@PathVariable long id, @RequestBody SavingAdvertisementsRequest savingAdvertisementsRequest) {
        return advertisementService.updateAdvertisements(id, savingAdvertisementsRequest);
    }
}
