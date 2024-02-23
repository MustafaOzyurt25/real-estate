package com.realestate.service;

import com.realestate.entity.*;
import com.realestate.entity.enums.AdvertStatus;


import com.realestate.entity.enums.LogType;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.AdvertMapper;
import com.realestate.payload.mappers.CategoryPropertyValueMapper;
import com.realestate.payload.mappers.LogMapper;
import com.realestate.payload.mappers.LogUserMapper;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.request.AdvertUpdateRequest;
import com.realestate.payload.response.AdvertCategoriesResponse;
import com.realestate.payload.response.AdvertCityResponse;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final ImageService imageService;
    private final CountryService countryService;
    private final CityService cityService;
    private final DistrictService districtService;
    private final AdvertTypeService advertTypeService;
    private final PageableHelper pageableHelper;
    private final TourRequestsRepository tourRequestsRepository;
    private final UserRepository userRepository;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;
    private final CategoryPropertyValueMapper categoryPropertyValueMapper;
    private final LogMapper logMapper;
    private final LogsService logsService;
    private final LogUserRepository logUserRepository;
    private final LogUserMapper logUserMapper;
    private final EmailService emailService;


    public Advert save(AdvertRequest advertRequest, HttpServletRequest httpServletRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        if (!(user.getId() == null)) {
            Country country = countryService.getCountryById(advertRequest.getCountryId());

            City city = cityService.getCityById(advertRequest.getCityId());
            District district = districtService.getDistrictById(advertRequest.getDistrictId());
            AdvertType advertType = advertTypeService.getAdvertTypeById(advertRequest.getAdvertTypeId());

            String slug = advertRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "") + "-" + user.getId();

            List<Image> images = imageService.saveAndGetImages(advertRequest.getImages());
            Category category = categoryPropertyKeyService.isCategoryExist(advertRequest.getCategoryId());
            Advert advert = advertMapper.mapToAdvertRequestToAdvert(advertRequest, images, country, city, district, advertType, slug, user, category);

            advert.setStatus(AdvertStatus.PENDING);
            advert.setBuiltIn(false);
            advert.setIsActive(true);
            advert.setViewCount(0);

            advertRepository.save(advert);
            List<CategoryPropertyValue> categoryPropertyValues = new ArrayList<>();
            for (int i = 0; i < advertRequest.getPropertyValues().size(); i++) {
                CategoryPropertyValue categoryPropertyValue = categoryPropertyValueRepository
                        .save(categoryPropertyValueMapper
                                .mapValuesToCategoryPropertyValue(advert, category.getCategoryPropertyKeys().get(i), advertRequest.getPropertyValues().get(i)));
                categoryPropertyValues.add(categoryPropertyValue);
            }
            advert.setCategoryPropertyValue(categoryPropertyValues);
            LogAdvert logAdvert = logMapper.mapLog(user, advert, LogType.CREATED_ADVERT);
            List<LogAdvert> logAdvertList = new ArrayList<>();
            logAdvertList.add(logAdvert);
            logsService.save(logAdvert);
            advert.setLogAdverts(logAdvertList);
            LogUser logUser = logUserMapper.mapLog(user, LogType.CREATED_ADVERT);
            user.getLogUser().add(logUser);
            logUserRepository.save(logUser);
            userRepository.save(user);
            advertRepository.save(advert);

            emailService.sendAdvertCreationEmail(userEmail, advert);

            return advert;

        } else {
            throw new ConflictException("You must log in to create an advert.");
        }
    }



    public void addImageToAdvert(Long advertId, List<Image> images) {
        Advert advert = advertRepository.findById(advertId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages
                        .ADVERT_NOT_FOUND_EXCEPTION, advertId)));
        images.addAll(advert.getImages());
        Advert updatedAdvert = advert.toBuilder()
                .images(images)
                .build();
        advertRepository.save(updatedAdvert);
    }

 /*   public ResponseMessage<Advert> getAdvertWithSlug(String slug) {
        Advert advert = getAdvertBySlug(slug);
        advert = advertView(advert.getId());

        return ResponseMessage.<Advert>builder()
                .object(advert)
                .message(SuccessMessages.ADVERT_FOUNDED)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    //view_count sayısını güncellemek için
    public Advert advertView(Long advertId) {
        //id kontrol
        Advert advert = getAdvertById(advertId);

        advert.setViewCount(advert.getViewCount() + 1);
        return advertRepository.save(advert);

    }*/

    public ResponseMessage<Advert> getAdvertWithSlug(String slug,HttpServletRequest httpServletRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmailEquals(userEmail);

        Advert advert = getAdvertBySlug(slug);

        advert = advertView(advert,user);

        return ResponseMessage.<Advert>builder()
                .object(advert)
                .message(SuccessMessages.ADVERT_FOUNDED)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    public Advert advertView(Advert advert,User user) {
        //id kontrol

        if(user==null){
            advert.setViewCount(advert.getViewCount() + 1);
            return advertRepository.save(advert);
        }
        if(!(advert.getUser().getId().equals(user.getId()))){
            advert.setViewCount(advert.getViewCount() + 1);
            return advertRepository.save(advert);
        }
        return advert;
    }



    public Advert getAdvertBySlug(String slug) {

        Advert advert = advertRepository.findBySlug(slug).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION_BY_SLUG, slug)));
        return advert;
    }

    public List<AdvertCityResponse> getAdvertAmountByCity() {

        return advertRepository.getAdvertAmountByCity().stream().collect(Collectors.toList());
    }

    public List<AdvertCategoriesResponse> getAdvertAmountByCategories() {
        return advertRepository.getAdvertAmountByCategories().stream().collect(Collectors.toList());
    }

    //====================================popular================================================



    //tour_request sayısını almak için
    public int tourRequestAmount(Long advertId) {
        return tourRequestsRepository.countByAdvertId(advertId);
    }

    public List<AdvertResponse> getPopularAdvertsByAmount(Integer defaultAmount) {

        Integer amount = (defaultAmount == null) ? 10 : defaultAmount;

        //tüm advertları almak için
        List<Advert> allAdvert = advertRepository.findAll().stream().toList();

        List<PopularAdvert> popularAdvert = new ArrayList<>();
        for (int i = 0; i < allAdvert.size(); i++) {
            int tvoa = allAdvert.get(i).getViewCount();
            int troa = tourRequestAmount(allAdvert.get(i).getId());
            int pp = ((troa * 3) + tvoa);
            popularAdvert.add(new PopularAdvert(allAdvert.get(i).getId(), pp));
        }

        //amount miktarı kadar popularAdvert ı büyükten küçüğe sıraladım topList e attım
        List<PopularAdvert> topListPopularAdvert = popularAdvert.stream()
                .sorted(Comparator.comparingInt(PopularAdvert::getPpValue).reversed())
                .limit(amount)
                .toList();

        // topListPopularAdvert ten sadece idlerin oldugu liste oluşturdum
        List<Advert> adverts = new ArrayList<>();
        for (PopularAdvert popularAdverts : topListPopularAdvert) {
            adverts.add(advertRepository.findById(popularAdverts.
                    getAdvertId()).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION)));
        }
        return adverts.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList());

    }
    //===================================================popular===================================================

    // =======================================A08================================================
    public ResponseMessage<AdvertResponse> getAuthenticatedCustomerAdvertById(Long advertId, HttpServletRequest httpServletRequest) {

        // advert customer a mı ait kontrol yapılmalı

        String userEmail = (String) httpServletRequest.getAttribute("email");
        //id kontrol
        Advert advert = getAdvertById(advertId);
        User user = userRepository.findByEmailEquals(userEmail);

        if (advert.getUser().equals(user)) {
            return ResponseMessage.<AdvertResponse>builder()
                    .object(advertMapper.mapAdvertToAdvertResponse(advert))
                    .message(SuccessMessages.ADVERT_FOUNDED)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            throw new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION);
        }

    }

    // UserService için yazıldı, id si ile gelen kullanıcınının advert'ü var mı diye kontrol edicelek
    public boolean controlAdvertByUserId(Long userId) {
        return advertRepository.existsByUserId(userId);
    }

    public List<Advert> getAdvertsByUserId(Long userId) {
        return advertRepository.findByUser_Id(userId);
    }

    //===========================ID kontrol============================================

    public ResponseEntity<Map<String, Object>> getSortedAdvertsByValues(String q, List<Long> categoryId, List<Long> advertTypeId, Double priceStart, Double priceEnd, Integer status, Long countryId, Long cityId, Long districtId, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort.toLowerCase(), type.toLowerCase());
        AdvertStatus aStatus = null;
        if (categoryId != null && categoryId.isEmpty()) {
            categoryId = null;
        }
        if (advertTypeId != null && advertTypeId.isEmpty()) {
            advertTypeId = null;
        }
        if (districtId != null) {
            cityId = null;
            countryId = null;
        }
        if (cityId != null) {
            countryId = null;
        }
        if (status != null) {
            aStatus = AdvertStatus.getAdvertStatusByNumber(status);
        }
        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }
        Page<AdvertResponse> adverts = advertRepository.getSortedAdvertsByValues(q, categoryId, advertTypeId, priceStart, priceEnd, aStatus, countryId, cityId, districtId, pageable)
                .map(advertMapper::mapAdvertToAdvertResponse);
        Map<String, Object> responseBody = new HashMap<>();
        if (adverts.isEmpty()) {
            responseBody.put("message", ErrorMessages.CRITERIA_ADVERT_NOT_FOUND);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        responseBody.put("Message", SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("Adverts", adverts);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public Advert getAdvertById(Long advertId) {
        return isAdvertExists(advertId);
    }

    private Advert isAdvertExists(Long advertId) {
        return advertRepository.findById(advertId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION, advertId)));
    }

    // A09
    public ResponseMessage<AdvertResponse> getAdvertBySlugAdminManager(Long id) {

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(advertRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION, id)))))
                .message(SuccessMessages.ADVERT_FOUNDED)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    //--------------- updateAuthenticatedCustomersAdvertById ---------------------------//

    public ResponseMessage<AdvertResponse> updateAuthenticatedCustomersAdvertById(Long advertId,
                                                                                  AdvertUpdateRequest advertUpdateRequest,
                                                                                  HttpServletRequest httpServletRequest) {
        String currentUserEmail = (String) httpServletRequest.getAttribute("email");

        User currentUser = userRepository.findByEmailEquals(currentUserEmail);

        // id kontrolu
        Advert existAdvert = getAdvertById(advertId);

        City city = cityService.getCityById(advertUpdateRequest.getCityId());
        Country country = countryService.getCountryById(advertUpdateRequest.getCountryId());
        District district = districtService.getDistrictById(advertUpdateRequest.getDistrictId());
        AdvertType advertType = advertTypeService.getAdvertTypeById(advertUpdateRequest.getAdvertTypeId());
        Category category = categoryPropertyKeyService.isCategoryExist(advertUpdateRequest.getCategoryId());

        Advert advert = advertMapper.mapAdvertRequestToUpdatedAdvert(advertUpdateRequest);

        advert.setBuiltIn(false);
        advert.setAdvertType(advertType);
        advert.setCity(city);
        advert.setDistrict(district);
        advert.setCategory(category);
        advert.setUpdateAt(LocalDateTime.now());
        advert.setCountry(country);

        // diger setlemeler.....
        advert.setCategoryPropertyValue(existAdvert.getCategoryPropertyValue());
        advert.setFavorites(existAdvert.getFavorites());
        advert.setCreateAt(existAdvert.getCreateAt());
        advert.setImages(existAdvert.getImages());
        advert.setSlug(existAdvert.getSlug());
        advert.setLogAdverts(existAdvert.getLogAdverts());
        advert.setId(existAdvert.getId());
        advert.setTourRequests(existAdvert.getTourRequests());
        advert.setUser(existAdvert.getUser());
        advert.setViewCount(existAdvert.getViewCount());

        // "builtIn" özelliği kontrolü
        if (existAdvert.getBuiltIn()) {
            throw new ConflictException(ErrorMessages.ADVERT_BUILT_IN_CAN_NOT_BE_UPDATED);
        }

        // Kullanıcının kendi reklamını güncelleme yetkisi kontrolü
        if (!existAdvert.getUser().getId().equals(currentUser.getId())) {
            throw new ConflictException(String.format(ErrorMessages.ADVERT_CAN_NOT_BE_UPDATED, advertId));
        }

        // Durumu "PENDING" olarak ayarla
        advert.setStatus(AdvertStatus.PENDING);
        LogAdvert logAdvert = logMapper.mapLog(currentUser, advert, LogType.UPDATED_ADVERT);
        logsService.save(logAdvert);
        advert.getLogAdverts().add(logAdvert);
        for (CategoryPropertyValue categoryPropertyValue : advert.getCategoryPropertyValue()) {
            categoryPropertyValueRepository.delete(categoryPropertyValue);
        }
        List<CategoryPropertyValue> categoryPropertyValues = new ArrayList<>();

        // advertUpdateRequest den gelen value'lar db ye kaydediliyor:
        for (int i = 0; i < advertUpdateRequest.getPropertyValues().size(); i++) {
            CategoryPropertyValue categoryPropertyValue = categoryPropertyValueRepository
                    .save(categoryPropertyValueMapper
                            .mapValuesToCategoryPropertyValue(advert, category.getCategoryPropertyKeys().get(i), advertUpdateRequest.getPropertyValues().get(i)));

            //Bu value'lar listeye de ekleniyor...
            categoryPropertyValues.add(categoryPropertyValue);
        }

        // advert'in ilgili fieldi advertUpdateRequest'den gelen value ile setleniyor...
        advert.setCategoryPropertyValue(categoryPropertyValues);
        LogUser logUser = logUserMapper.mapLog(currentUser, LogType.UPDATED_ADVERT);
        currentUser.getLogUser().add(logUser);
        logUserRepository.save(logUser);
        userRepository.save(currentUser);
        Advert savedAdvert = advertRepository.save(advert);

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(savedAdvert))
                .message(SuccessMessages.ADVERT_UPDATE)
                .httpStatus(HttpStatus.OK).build();
    }


    public ResponseMessage deleteAdvertById(Long id, HttpServletRequest httpServletRequest) {

        Optional<Advert> advert = advertRepository.findById(id);

        if (advert.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION, id));
        } else if (advert.get().getBuiltIn()) {
            throw new ConflictException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User loginUser = userRepository.findByEmailEquals(userEmail);
        LogUser logLoginUser;
        LogUser logOwnerUser;
        if (Objects.equals(advert.get().getUser().getId(), loginUser.getId())) {
            logLoginUser = logUserMapper.mapLog(loginUser, LogType.DELETED_ADVERT);
            loginUser.getLogUser().add(logLoginUser);
        } else {
            int deger = 0;
            for (Role role : loginUser.getRole()) {
                if (role.getRoleName().equals(RoleType.ADMIN)) {
                    deger++;
                }
            }
            User user = advertRepository.findByUserWithAdvertId(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
            logLoginUser = logUserMapper.mapLog(loginUser, LogType.DELETED_USERS_ADVERT);
            loginUser.getLogUser().add(logLoginUser);
            if (deger > 0) {
                logOwnerUser = logUserMapper.mapLog(user, LogType.DELETED_ADVERT_BY_ADMIN);
            } else {
                logOwnerUser = logUserMapper.mapLog(user, LogType.DELETED_ADVERT_BY_MANAGER);
            }
            user.getLogUser().add(logOwnerUser);
            logUserRepository.save(logOwnerUser);
            userRepository.save(user);
        }
        userRepository.save(loginUser);
        logUserRepository.save(logLoginUser);

        advertRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.ADVERT_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    // public Page<AdvertResponse> getAuthenticatedUserAdverts(int page, int size, String sort, String type, HttpServletRequest httpServletRequest) {

    //A05
    public Page<AdvertResponse> getAuthenticatedUserAdverts(int page, int size, String sort, String type, HttpServletRequest httpServletRequest) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        if (user == null) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }

        return advertRepository.findByUserEmail(user.getEmail(), pageable)
                .map(advertMapper::mapAdvertToAdvertResponse);
    }

    public void isSlugExists(String slug) {
        advertRepository.findBySlug(slug).orElseThrow(() ->
                new ConflictException(String.format(ErrorMessages.NOT_UNIQUE_SLUG)));

    }


    public ResponseMessage<AdvertResponse> updateAdminAdvertById(Long advertId, AdvertUpdateRequest updateRequest, HttpServletRequest httpServletRequest) {
        Advert existAdvert = getAdvertById(advertId);
        City city = cityService.getCityById(updateRequest.getCityId());
        Country country = countryService.getCountryById(updateRequest.getCountryId());
        District district = districtService.getDistrictById(updateRequest.getDistrictId());
        AdvertType advertType = advertTypeService.getAdvertTypeById(updateRequest.getAdvertTypeId());
        Category category = categoryPropertyKeyService.isCategoryExist(updateRequest.getCategoryId());
        List<CategoryPropertyKey> categoryPropertyKeys =
                categoryPropertyKeyService.getCategoryPropertyKeys(updateRequest.getCategoryId());
        Advert advert = advertMapper.mapAdvertRequestToUpdatedAdvert(updateRequest);

        Long userId = existAdvert.getUser().getId();
        String title = updateRequest.getTitle();
        String slug = title.toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "") + "-" + userId;

        String updatedSlug = updateRequest.getSlug() + "-" + userId;

        if (!slug.equals(updatedSlug)) {
            throw new ConflictException(ErrorMessages.TITLE_SLUG_IS_NOT_IN_THE_DESIRED_FORMAT);
        }


        //isSlugExists(slug);


        advert.setBuiltIn(false);
        advert.setAdvertType(advertType);
        advert.setCity(city);
        advert.setDistrict(district);
        advert.setCategory(category);
        advert.setUpdateAt(LocalDateTime.now());
        advert.setCountry(country);
        advert.setUpdateAt(LocalDateTime.now());

        advert.setCategoryPropertyValue(existAdvert.getCategoryPropertyValue());
        advert.setFavorites(existAdvert.getFavorites());
        advert.setCreateAt(existAdvert.getCreateAt());
        advert.setImages(existAdvert.getImages());
        advert.setSlug(slug);
        advert.setLogAdverts(existAdvert.getLogAdverts());
        advert.setId(existAdvert.getId());
        advert.setTourRequests(existAdvert.getTourRequests());
        advert.setUser(existAdvert.getUser());
        advert.setViewCount(existAdvert.getViewCount());
        advert.setStatus(AdvertStatus.getAdvertStatusByNumber(updateRequest.getStatusId()));


        if (existAdvert.getBuiltIn()) {
            throw new ConflictException(ErrorMessages.ADVERT_BUILT_IN_CAN_NOT_BE_UPDATED);
        }
        for (CategoryPropertyValue categoryPropertyValue : advert.getCategoryPropertyValue()) {
            categoryPropertyValueRepository.delete(categoryPropertyValue);
        }

        List<CategoryPropertyValue> categoryPropertyValues = new ArrayList<>();
        for (int i = 0; i < updateRequest.getPropertyValues().size(); i++) {
            CategoryPropertyValue categoryPropertyValue = categoryPropertyValueRepository
                    .save(categoryPropertyValueMapper
                            .mapValuesToCategoryPropertyValue(advert, category.getCategoryPropertyKeys().get(i), updateRequest.getPropertyValues().get(i)));
            categoryPropertyValues.add(categoryPropertyValue);
        }
        advert.setCategoryPropertyValue(categoryPropertyValues);


        String userEmail = (String) httpServletRequest.getAttribute("email");
        User loginUser = userRepository.findByEmailEquals(userEmail);
        LogUser logLoginUser;
        LogUser logOwnerUser;
        int deger = 0;
        User user = advertRepository.findByUserWithAdvertId(advertId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        if (advert.getStatus().equals(AdvertStatus.REJECTED)) {
            LogAdvert logAdvert1;
            LogUser logUser;
            if (Objects.equals(advert.getUser().getId(), loginUser.getId())) {
                logAdvert1 = logMapper.mapLog(user, advert, LogType.DECLINED_ADVERT);
                logUser = logUserMapper.mapLog(user, LogType.DECLINED_ADVERT);
                logsService.save(logAdvert1);
                logUserRepository.save(logUser);
            } else {
                LogUser logUserOwner;
                for (Role role : loginUser.getRole()) {
                    if (role.getRoleName().equals(RoleType.ADMIN)) {
                        deger++;
                    }
                }
                if (deger > 0) {
                    logUserOwner = logUserMapper.mapLog(user, LogType.DECLINED_ADVERT_BY_ADMIN);
                } else {
                    logUserOwner = logUserMapper.mapLog(user, LogType.DECLINED_ADVERT_BY_MANAGER);
                }
                logAdvert1 = logMapper.mapLog(user, advert, LogType.DECLINED_ADVERT);
                logUser = logUserMapper.mapLog(loginUser, LogType.DECLINED_USERS_ADVERT);

                logsService.save(logAdvert1);
                logUserRepository.save(logUser);
                logUserRepository.save(logUserOwner);
            }
        } else {
            if (Objects.equals(advert.getUser().getId(), loginUser.getId())) {
                logLoginUser = logUserMapper.mapLog(loginUser, LogType.UPDATED_ADVERT);
                loginUser.getLogUser().add(logLoginUser);
            } else {

                for (Role role : loginUser.getRole()) {
                    if (role.getRoleName().equals(RoleType.ADMIN)) {
                        deger++;
                    }
                }

                logLoginUser = logUserMapper.mapLog(loginUser, LogType.UPDATED_USERS_ADVERT);
                loginUser.getLogUser().add(logLoginUser);
                if (deger > 0) {
                    logOwnerUser = logUserMapper.mapLog(user, LogType.UPDATED_ADVERT_BY_ADMIN);
                } else {
                    logOwnerUser = logUserMapper.mapLog(user, LogType.UPDATED_ADVERT_BY_MANAGER);
                }
                user.getLogUser().add(logOwnerUser);
                logUserRepository.save(logOwnerUser);
                userRepository.save(user);
            }
            userRepository.save(loginUser);
            logUserRepository.save(logLoginUser);
            LogAdvert logAdvert = logMapper.mapLog(user, advert, LogType.UPDATED_ADVERT);
            logsService.save(logAdvert);
            advert.getLogAdverts().add(logAdvert);

        }

        Advert savedAdvert = advertRepository.save(advert);

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(savedAdvert))
                .message(SuccessMessages.ADVERT_UPDATE)
                .httpStatus(HttpStatus.OK).build();


    }


}
