package com.thesis.megahjaya.pagination;

import java.util.ArrayList;

public class prevNextPagination {
    public static final int TOTAL_MATERIAL = 100;
    public static final int LIST_MATERIAL_PER_PAGE = 25;
    public static final int MATERIAL_REMAINING = TOTAL_MATERIAL % LIST_MATERIAL_PER_PAGE;
    public static final int LAST_PAGE = TOTAL_MATERIAL / LIST_MATERIAL_PER_PAGE;

    public ArrayList<Integer> pagination(int currentPage){
        // if currentPage = 0  --->  0 * 25 + 1 = 1, material start at material num 1
        // if currentPage = 1  --->  1 * 25 + 1 = 26, material start at material num 25
        int setStartMaterialNumberForEachPage = currentPage * LIST_MATERIAL_PER_PAGE + 1;
//        int numOfMaterial = LIST_MATERIAL_PER_PAGE;

        ArrayList<Integer> page = new ArrayList<>();

        // If reaching the last page & theres still some material data left
        if(currentPage == LAST_PAGE && MATERIAL_REMAINING > 0){
            for(int x = setStartMaterialNumberForEachPage; x < setStartMaterialNumberForEachPage + MATERIAL_REMAINING; x++){
                page.add(x);
            }
        }
        else{
            for(int y = setStartMaterialNumberForEachPage; y < setStartMaterialNumberForEachPage + LIST_MATERIAL_PER_PAGE; y++){
                page.add(y);
            }
        }

        return page;
    }
}
