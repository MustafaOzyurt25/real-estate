package com.realestate.service.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PageableHelper {

    //burada secim sansini front end dev a birakiyoruz. Eger client dan alcak olursak bu methodla alirdik ve kutucuklarda secim sansi verirdik
    public Pageable getPageableWithProperties(int page, int size, String sort, String type){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return pageable;
    }


    public Pageable getPageableWithProperties(int page, int size){//burada front end dev a secenek vermiyoruz id ile desc i kendimiz setliyoruz
        return PageRequest.of(page, size,Sort.by("id").descending());
    }

}
