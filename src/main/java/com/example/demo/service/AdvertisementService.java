package com.example.demo.service;

import com.example.demo.data.entity.Advertisements;
import com.example.demo.data.entity.User;
import com.example.demo.data.repository.AdvertisementsRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.AdvertisementResponse;
import com.example.demo.response.CreateAdvertisementsResponse;
import com.example.demo.response.GetAllAdvertisementsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdvertisementService {

  @Autowired
  private AdvertisementsRepository advertisementRepository;
  @Autowired
  private UserRepository userRepository;


  public Advertisements createAdvertisementsFromAdvertisementRequest(final CreateAdvertisementsRequest createAdvertisementsRequest, final User user) {
    Advertisements advertisement = new Advertisements();
    advertisement.setTitle(createAdvertisementsRequest.getTitle());
    advertisement.setDescription(createAdvertisementsRequest.getDescription());
    advertisement.setUser(user);
    return advertisement;
  }

  public AdvertisementResponse fromAdvertisementsToAdvertisementResponse(final Advertisements advertisements, final User user) {
    AdvertisementResponse advertisementResponse = new AdvertisementResponse();
    advertisementResponse.setId(advertisements.getId());
    advertisementResponse.setTitle(advertisements.getTitle());
    advertisementResponse.setDescription(advertisements.getDescription());
    advertisementResponse.setUserId(advertisements.getUser().getId());
    return advertisementResponse;
  }

  public CreateAdvertisementsResponse fromAdvertisementsToCreateResponse(final long advertisementId) {
    CreateAdvertisementsResponse advertisementsResponse = new CreateAdvertisementsResponse();
    advertisementsResponse.setId(advertisementId);
    return advertisementsResponse;
  }

  public GetAllAdvertisementsResponse fromListAdvertisementsToListAdvertisementsResponse(
      final List<Advertisements> listAdvertisements) {
    List<AdvertisementResponse> listAdvertisementResponse = new ArrayList<>();
    for (Advertisements listAdvertisement : listAdvertisements) {
      listAdvertisementResponse.add(fromAdvertisementsToAdvertisementResponse(listAdvertisement, listAdvertisement.getUser()));
    }
    return new GetAllAdvertisementsResponse(listAdvertisementResponse);
  }

  public GetAllAdvertisementsResponse getAllAdvertisements() {
    return fromListAdvertisementsToListAdvertisementsResponse(advertisementRepository.findAll());
  }

  public AdvertisementResponse getAdvertisementsById(final long id) {
    User user = userRepository.getById(id);
    return fromAdvertisementsToAdvertisementResponse(advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement not exist with id:" + id)), user);
  }

  public CreateAdvertisementsResponse createAdvertisement(final CreateAdvertisementsRequest createAdvertisementsRequest) {
    try {
      User user = userRepository.getById(createAdvertisementsRequest.getUserId());
      Advertisements adv = createAdvertisementsFromAdvertisementRequest(createAdvertisementsRequest, user);
      Advertisements advertisement = advertisementRepository.save(adv);
      return fromAdvertisementsToCreateResponse(advertisement.getId());
    } catch (Exception e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new ResourceNotFoundException("Advertisement not save");
    }
  }

  public void delete(final long id) {
    try {
      advertisementRepository.deleteById(id);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement not exist with id:" + id);
    }
  }

  public AdvertisementResponse updateAdvertisements(final long id, final  SavingAdvertisementsRequest advertisementsDetails) {
    Advertisements advertisements = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement not exist with id:" + id));
    advertisements.setTitle(advertisementsDetails.getTitle());
    advertisements.setDescription(advertisementsDetails.getDescription());
    advertisementRepository.save(advertisements);
    return fromAdvertisementsToAdvertisementResponse(advertisements, userRepository.getById(id));
  }
}
