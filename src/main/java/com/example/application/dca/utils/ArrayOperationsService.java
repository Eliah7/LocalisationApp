package com.example.application.dca.utils;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayOperationsService<T>{

    public List<List<Integer>> permuteArray(Integer lengthOfArrayToPermute){
        List<List<Integer>> permutations = new ArrayList();

        // length of resulting array is 2**lengthOfArrayToPermute
        String lastAction = Integer.toBinaryString(lengthOfArrayToPermute);

        for (int i = 0; i < lengthOfArrayToPermute; i++) {

            List binaryArrayForI = Arrays.asList(Integer.toBinaryString(i).split(""));
            if(binaryArrayForI.size() < lastAction.length()){
                int remainingZeros = lastAction.length() - binaryArrayForI.size();
                for (int j = 0; j < remainingZeros; j++) {
                    binaryArrayForI.add(binaryArrayForI.size()-1, 0);
                }
            }
            System.out.println(binaryArrayForI);
            permutations.add(binaryArrayForI);
        }

        return permutations;
    }

    public List<Double> permutationForI(int maxLength, int action){
        List<Double> max = Arrays.asList(Integer.toBinaryString(maxLength).split(""))
                .stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        List<Double> permutation = Arrays.asList(Integer.toBinaryString(action).split(""))
                .stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        int remainingItems = maxLength - permutation.size();
        if (remainingItems != 0){
            for (int i = 0; i < remainingItems; i++) {
                permutation.add(0, 1.0);
            }
        }

        return permutation;
    }

    public Double dot(List<Double> arr1, List<Double>arr2) throws Exception {
        if(arr1.size() != arr2.size()){
            throw new Exception("Dot Product: Array length do not match");
        }
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < arr1.size(); i++) {
            result.add(arr1.get(i) * arr2.get(i));
        }

        return result.stream().reduce((val, next) -> {
            return val + next;
        }).get();
    }
}
