package com.garcia.felipe.rappimovies;

import android.support.test.runner.AndroidJUnit4;
import com.garcia.felipe.rappimovies.Helpers.Constants;
import com.garcia.felipe.rappimovies.Helpers.StringHelper;
import com.garcia.felipe.rappimovies.Models.MultimediaItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Test
    public void stringTest() {
        StringHelper stringHelper = new StringHelper();
        assertEquals(true, stringHelper.containString("true", "true"));
        assertEquals(false, stringHelper.containString("false", "true"));
    }

    @Test
    public void multimediaItemTest() {
        int id= 100;
        String title = "Batman";
        MultimediaItem multimediaItem = new MultimediaItem(id, title);
        assertEquals(id, multimediaItem.getId());
        assertEquals(title, multimediaItem.getTitle());
    }

    @Test
    public void multimediaPosterURLTest() {
        int id= 100;
        String title = "Batman";
        String postURL = "my_image.png";
        MultimediaItem multimediaItem = new MultimediaItem(id, title);
        multimediaItem.setPosterPath(postURL);
        assertEquals(Constants.POSTER_IMAGE_URL + postURL, multimediaItem.getPosterPath());
    }

    @Test
    public void multimediaBannerURLTest() {
        int id= 100;
        String title = "Batman";
        String bannerURL = "my_image.png";
        MultimediaItem multimediaItem = new MultimediaItem(id, title);
        multimediaItem.setPosterPath(bannerURL);
        assertEquals(Constants.BANNER_IMAGE_URL + bannerURL, multimediaItem.getBannerImage());
    }

}
