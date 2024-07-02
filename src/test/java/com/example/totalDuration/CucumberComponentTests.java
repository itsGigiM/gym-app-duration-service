package com.example.totalDuration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features/")
@AutoConfigureMockMvc
public class CucumberComponentTests {
}
