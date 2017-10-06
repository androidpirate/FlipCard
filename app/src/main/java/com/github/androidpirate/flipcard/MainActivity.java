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

package com.github.androidpirate.flipcard;



import android.app.Fragment;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.github.androidpirate.flipcard.model.FlipCard;
import com.github.androidpirate.flipcard.utils.CardFactoryUtils;

import java.util.ArrayList;

public class MainActivity extends SingleFragmentActivity implements
    FrontCardFragment.OnFragmentInteractionListener,
    BackCardFragment.OnFragmentInteractionListener,
    CorrectCardFragment.OnFragmentInteractionListener,
    ScoreFragment.OnFragmentInteractionListener {

    private final ArrayList<FlipCard> mCards = CardFactoryUtils.getInstance().getCards();
    private FlipCard mFlipCard;
    private int mCardIndex = 0;
    private int mScore = 0;

    @Override
    protected Fragment createFragment() {
        mFlipCard = mCards.get(mCardIndex);
        return FrontCardFragment.newInstance(mFlipCard);
    }

    @Override
    public void flipToBack() {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out)
                .replace(R.id.fragment_container, BackCardFragment.newInstance(mFlipCard, false))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displayCorrectAnswerAnimation() {
        mScore++;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_right_in,
                                    R.animator.card_left_out)
                .replace(R.id.fragment_container, CorrectCardFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void moveToNextCard() {
        if(++mCardIndex < mCards.size()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCardIndex = mCardIndex % mCards.size();
                    mFlipCard = mCards.get(mCardIndex);
                    updateProgress();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.card_right_in,
                                    R.animator.card_left_out)
                            .replace(R.id.fragment_container, FrontCardFragment.newInstance(mFlipCard))
                            .addToBackStack(null)
                            .commit();
                }
            }, 1500);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.card_right_in,
                            R.animator.card_left_out)
                    .replace(R.id.fragment_container, ScoreFragment.newInstance(mScore))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void updateProgress() {
        float deckSize = mCards.size();
        float index = mCardIndex;
        float progress = 1;
        if(mCardIndex != 0) {
            progress = (index / deckSize) * 100;
        }
        mProgressBar.setProgress((int) progress);
    }

    @Override
    public void restart() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
