package com.example.eadca2_app;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GetClothesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void getClothesTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_parse), withText("Show Clothes"),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_view_result), withText("1, Jeans, 45.0, Tommy Hilfiger, M, navy, 1\n\n2, Tracksuits, 30.0, nike, S, grey, 1\n\n3, Shoes, 80.0, Converse, 7, White, 1\n\n4, Jacket, 55.0, Nike, M, Navy, 2\n\n5, Tracksuit, 70.0, The North Face, L, Green, 2\n\n6, Shoes, 90.0, Jordans, 9, White, 2\n\n7, Jacket, 50.0, Superdry, L, Blue, 3\n\n"),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        textView.check(matches(withText("1, Jeans, 45.0, Tommy Hilfiger, M, navy, 1  2, Tracksuits, 30.0, nike, S, grey, 1  3, Shoes, 80.0, Converse, 7, White, 1  4, Jacket, 55.0, Nike, M, Navy, 2  5, Tracksuit, 70.0, The North Face, L, Green, 2  6, Shoes, 90.0, Jordans, 9, White, 2  7, Jacket, 50.0, Superdry, L, Blue, 3  ")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
