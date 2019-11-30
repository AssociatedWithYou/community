package com.jiane.utils;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PagingUtil {

    public List<Integer> getPageList(Integer myCurrentPage,Integer totalPages){
        List<Integer> pages = new ArrayList<>();
        if (totalPages<5){
            for (int i = 1 ; i <=totalPages; i++){
                pages.add(i);
            }
        }else{
            if(myCurrentPage<4){
                pages.add(1);
                pages.add(2);
                pages.add(3);
                pages.add(4);
                pages.add(5);
            } else if (myCurrentPage>=totalPages-2) {
                pages.add(totalPages-4);
                pages.add(totalPages-3);
                pages.add(totalPages-2);
                pages.add(totalPages-1);
                pages.add(totalPages);
            }else{
                pages.add(myCurrentPage-2);
                pages.add(myCurrentPage-1);
                pages.add(myCurrentPage);
                pages.add(myCurrentPage+1);
                pages.add(myCurrentPage+2);
            }
        }
        return pages;
    }
}
