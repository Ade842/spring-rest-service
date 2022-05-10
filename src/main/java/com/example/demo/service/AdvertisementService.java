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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdvertisementService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
  @Autowired
  private AdvertisementsRepository advertisementRepository;
  @Autowired
  private UserRepository userRepository;


  public Advertisements createAdvertisementsFromAdvertisementRequest(
      final CreateAdvertisementsRequest createAdvertisementsRequest, final User user) {
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

  @SuppressWarnings("checkstyle:Indentation")
  public GetAllAdvertisementsResponse fromListAdvertisementsToListAdvertisementsResponse(
      final List<Advertisements> listAdvertisements) {
    List<AdvertisementResponse> listAdvertisementResponse = new ArrayList<>();
    for (Advertisements listAdvertisement : listAdvertisements) {
      if (!listAdvertisement.getDeleted()) {
      listAdvertisementResponse.add(fromAdvertisementsToAdvertisementResponse(listAdvertisement, listAdvertisement.getUser()));
      }
    }
    return new GetAllAdvertisementsResponse(listAdvertisementResponse);
  }

  public GetAllAdvertisementsResponse getAllAdvertisements() {
    return fromListAdvertisementsToListAdvertisementsResponse(advertisementRepository.findAll());
  }

  public AdvertisementResponse getAdvertisementsById(final long id) {
    Advertisements advertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement with id:" + id + " could not be found"));
    User user = userRepository.getById(advertisement.getUser().getId());

    boolean deletedAdvertisement = advertisement.getDeleted();

    try {


      if (!deletedAdvertisement) {
        return fromAdvertisementsToAdvertisementResponse(advertisement, advertisement.getUser());
      } else {
        throw new ResourceNotFoundException("Advertisement with id:" + id + " is deleted");
      }
    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
    }

  }

  public CreateAdvertisementsResponse createAdvertisement(final CreateAdvertisementsRequest createAdvertisementsRequest) {
    try {
      User user = userRepository.getById(createAdvertisementsRequest.getUserId());
      Advertisements adv = createAdvertisementsFromAdvertisementRequest(createAdvertisementsRequest, user);
      Advertisements advertisement = advertisementRepository.save(adv);
      return fromAdvertisementsToCreateResponse(advertisement.getId());
    } catch (Exception e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new ResourceNotFoundException("Advertisement could not be saved");
    }
  }

  public void delete(final long id) {
    try {
      Advertisements advertisement = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement with id:" + id + " is deleted"));
      boolean deletedAdvertisement = advertisement.getDeleted();

      if (!deletedAdvertisement) {
        advertisement.setDeleted(true);
        advertisementRepository.save(advertisement);
      } else {
        throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
      }
    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
    }
  }

  public AdvertisementResponse updateAdvertisements(final long id, final SavingAdvertisementsRequest advertisementsDetails) {
    Advertisements updateAdvertisements = advertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement with id:" + id + " could not be found"));

    try {
      if (!updateAdvertisements.getDeleted()) {
        updateAdvertisements.setTitle(advertisementsDetails.getTitle());
        updateAdvertisements.setDescription(advertisementsDetails.getDescription());
        advertisementRepository.save(updateAdvertisements);
        return fromAdvertisementsToAdvertisementResponse(updateAdvertisements, userRepository.getById(id));
      } else {
        throw new ResourceNotFoundException("Advertisement with id:" + id + " is deleted");
      }
    } catch (Exception e) {
      throw new ResourceNotFoundException("Advertisement with id:" + id + " could not be found");
    }
  }
}
