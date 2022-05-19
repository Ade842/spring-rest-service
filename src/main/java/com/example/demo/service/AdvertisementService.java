package com.example.demo.service;

import com.example.demo.data.entity.Advertisements;
import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.User;
import com.example.demo.data.repository.AdvertisementsRepository;
import com.example.demo.data.repository.CategoryRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.request.CreateAdvertisementsRequest;
import com.example.demo.request.SavingAdvertisementsRequest;
import com.example.demo.response.AdvertisementResponse;
import com.example.demo.response.CreateAdvertisementsResponse;
import com.example.demo.response.GetAllAdvertisementsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdvertisementService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementService.class);
  @Autowired
  private AdvertisementsRepository advertisementRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  public Advertisements createAdvertisementsFromAdvertisementRequest(
      final CreateAdvertisementsRequest createAdvertisementsRequest, final User user, final Category category) {
    Advertisements advertisement = new Advertisements();
    advertisement.setTitle(createAdvertisementsRequest.getTitle());
    advertisement.setDescription(createAdvertisementsRequest.getDescription());
    advertisement.setUser(user);
    advertisement.setCategory(category);
    return advertisement;
  }

  public AdvertisementResponse fromAdvertisementsToAdvertisementResponse(final Advertisements advertisements, final User user) {
    AdvertisementResponse advertisementResponse = new AdvertisementResponse();
    advertisementResponse.setId(advertisements.getId());
    advertisementResponse.setTitle(advertisements.getTitle());
    advertisementResponse.setDescription(advertisements.getDescription());
    advertisementResponse.setUserId(advertisements.getUser().getId());
    advertisementResponse.setCategoryId(advertisements.getCategory().getId());
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
    return fromListAdvertisementsToListAdvertisementsResponse(advertisementRepository.getNonDeletedAdvertisements());
  }

  public AdvertisementResponse getAdvertisementsById(final long id) {
    try {
      Advertisements advertisement = advertisementRepository.getNonDeletedAdvertisementById(id);
      User user = userRepository.getById(advertisement.getUser().getId());
      return fromAdvertisementsToAdvertisementResponse(advertisement, advertisement.getUser());

    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
    }
  }

  public CreateAdvertisementsResponse createAdvertisement(final CreateAdvertisementsRequest createAdvertisementsRequest) {
    try {
      User user = userRepository.getNonDeletedUsersById(createAdvertisementsRequest.getUserId());
      if (user != null) {
        Category category = categoryRepository.getById(createAdvertisementsRequest.getCategoryId());
        Advertisements adv = createAdvertisementsFromAdvertisementRequest(createAdvertisementsRequest, user, category);
        Advertisements advertisement = advertisementRepository.save(adv);
        return fromAdvertisementsToCreateResponse(advertisement.getId());
      } else {
        throw new ApiRequestException("Advertisement could not be saved, user does not exists");
      }
    } catch (Exception e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new ApiRequestException("Advertisement could not be saved");
    }
  }

  public void delete(final long id) {
    try {
      Advertisements advertisement = advertisementRepository.getNonDeletedAdvertisementById(id);
      advertisement.setDeleted(true);
      advertisementRepository.save(advertisement);

    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id: " + id + " could not be found");
    }
  }

  public AdvertisementResponse updateAdvertisements(final long id, final SavingAdvertisementsRequest advertisementsDetails) {
    try {
      Advertisements updateAdvertisement = advertisementRepository.getNonDeletedAdvertisementById(id);
      updateAdvertisement.setTitle(advertisementsDetails.getTitle());
      updateAdvertisement.setDescription(advertisementsDetails.getDescription());
      advertisementRepository.save(updateAdvertisement);
      return fromAdvertisementsToAdvertisementResponse(updateAdvertisement, userRepository.getById(id));
    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
    }
  }
}
