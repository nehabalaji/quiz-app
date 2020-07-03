package com.example.quizapp.ui;

import com.example.quizapp.ListActivity;
import com.example.quizapp.R;
import com.example.quizapp.addStateActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public IntentsTestRule<ListActivity> intentsTestRule = new IntentsTestRule<>(ListActivity.class);

    @Test
    public void testAddActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.floatingButton)).perform(
                click()
        );
        intended(hasComponent(addStateActivity.class.getName()));
    }
}
