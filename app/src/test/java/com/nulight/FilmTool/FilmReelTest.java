package com.nulight.FilmTool;

import androidx.test.core.app.ApplicationProvider;

import com.nulight.FilmTool.film.FilmReel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FilmReelTest {
    FilmReel testFilmReel;

    @Before
    public void setUp() {
        this.testFilmReel = new FilmReel(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void testNonNull(){
        assert(this.testFilmReel != null);
    }

    @Test
    public void testBasicUserInput(){
        testFilmReel.setReelDiameter(10);
        assert(testFilmReel.getReelDiameter() == 10);
    }

    @Test
    public void testImperialAndMetric(){
        // Default is imperial
        testFilmReel.setCoreDiameter(50);
        testFilmReel.setReelDiameter(80);

        // Sets to 16mm
        testFilmReel.setFilmFormat(3);

        // Sets to 24fps
        testFilmReel.setFramesPerSecond(3);

        assert(testFilmReel.calculateReelLength() == 71.94);

        // Now switch and check
        testFilmReel.setUnit(Boolean.TRUE);

    }



}
