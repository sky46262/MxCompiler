package com.company.optimization.info;

import java.util.HashSet;

public class InstInfo {
    public HashSet<Integer> LiveIn = new HashSet<>();
    public HashSet<Integer> LiveOut = new HashSet<>();
    public int defReg = 0;
    public HashSet<Integer> usedReg = new HashSet<>();
}
