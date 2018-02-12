/*
 * <!--
 *  Copyright (C) 2016 The Android Open Source Project
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 * -->
 */

package com.github.androidpirate.flipit.model;

import java.io.Serializable;

/**
 * Model class.
 */
public class FlipCard implements Serializable {
    private String frontSide;
    private String rearSide;
    private boolean isVisible;
    private boolean isFavorite;

    public FlipCard(String frontSide, String rearSide) {
        this.frontSide = frontSide;
        this.rearSide = rearSide;
        isVisible = true;
        isFavorite = false;
    }

    public String getFrontSide() {
        return frontSide;
    }

    public void setFrontSide(String frontSide) {
        this.frontSide = frontSide;
    }

    public String getRearSide() {
        return rearSide;
    }

    public void setRearSide(String rearSide) {
        this.rearSide = rearSide;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}