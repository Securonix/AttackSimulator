/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author securonix
 */

public class BytesGenerator {
    private final List<Integer> range = new ArrayList<Integer>();

        BytesGenerator(int min, int max) {
            this.addRange(min, max);
        }

        final void addRange(int min, int max) {
            for (int i = min; i <= max; i++) {
                this.range.add(i);
            }
        }

        int getRandom() {
            return this.range.get(new Random().nextInt(this.range.size()));
        }
}