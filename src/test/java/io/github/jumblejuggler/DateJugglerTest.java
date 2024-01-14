/**
* @author h@jjoujti
* Copyright (C)2024. All rights reserved.
* This code is licensed under the MIT License.
*/
package io.github.jumblejuggler;

import static org.junit.jupiter.api.Assertions.*;

import io.github.jumblejuggler.exceptions.JumbleJugglerException;
import java.time.LocalDate;
import java.time.Year;
import org.junit.jupiter.api.Test;

class DateJugglerTest {

  private final LocalDate BCE_DATE = LocalDate.of(-1234, 4, 1);
  private final LocalDate CE_DATE = LocalDate.of(1234, 4, 1);
  private static final LocalDate DAY_1_CE = LocalDate.of(1, 1, 1);
  private static final LocalDate DAY_1_BCE = LocalDate.of(-1, 12, 31);

  @Test
  void generateRandomDate() {
    LocalDate result = assertDoesNotThrow(DateJuggler::generateRandomDate);
    assertNotEquals(0, result.getYear());
  }

  @Test
  void generateRandomBCEDate() {
    LocalDate result = assertDoesNotThrow(DateJuggler::generateRandomBCEDate);
    assertTrue(result.isBefore(DAY_1_BCE) || result.isEqual(DAY_1_BCE));
  }

  @Test
  void generateRandomCEDate() {
    LocalDate result = assertDoesNotThrow(DateJuggler::generateRandomCEDate);
    assertTrue(result.isAfter(DAY_1_CE) || result.isEqual(DAY_1_CE));
  }

  @Test
  void generateRandomDateBefore() {
    LocalDate result =
        assertDoesNotThrow(() -> DateJuggler.generateRandomDateBefore(LocalDate.of(1243, 4, 1)));
    assertTrue(result.isBefore(LocalDate.of(1243, 4, 1)));
  }

  @Test
  void generateRandomDateBefore_WhenDateIsMinimumDate_ThrowsException() {
    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> DateJuggler.generateRandomDateBefore(LocalDate.MIN));
    assertEquals(
        exception.getMessage(),
        String.format(
            "The given date is equals to the minimum date %s. It needs to be after it.",
            LocalDate.MIN));
  }

  @Test
  void generateRandomDateAfter() {
    LocalDate result =
        assertDoesNotThrow(() -> DateJuggler.generateRandomDateAfter(LocalDate.of(1243, 4, 1)));
    assertTrue(result.isAfter(LocalDate.of(1243, 4, 1)));
  }

  @Test
  void generateRandomDateAfter_WhenDateIsMaximumDate_ThrowsException() {
    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class, () -> DateJuggler.generateRandomDateAfter(LocalDate.MAX));
    assertEquals(
        exception.getMessage(),
        String.format(
            "The given date is equals to the maximum date %s. It needs to be before it.",
            LocalDate.MAX));
  }

  @Test
  void generateRandomDateBeforeNow() {
    LocalDate result = assertDoesNotThrow(DateJuggler::generateRandomDateBeforeNow);
    assertTrue(result.isBefore(LocalDate.now()));
  }

  @Test
  void generateRandomDateAfterNow() {
    LocalDate result = assertDoesNotThrow(DateJuggler::generateRandomDateAfterNow);
    assertTrue(result.isAfter(LocalDate.now()));
  }

  @Test
  void generateRandomDateBetween() {
    LocalDate result = DateJuggler.generateRandomDateBetween(BCE_DATE, CE_DATE);
    assertTrue(result.isBefore(CE_DATE) && result.isAfter(BCE_DATE));
  }

  @Test
  void generateRandomDateBetween_WhenMinDateIsAfterMaxDate_ThrowsException() {
    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> DateJuggler.generateRandomDateBetween(CE_DATE, BCE_DATE));
    assertEquals(exception.getMessage(), "The minimum given date is after the maximum given date");
  }

  @Test
  void generateRandomDateInCentury() {
    assertAll(
        () -> {
          LocalDate result = DateJuggler.generateRandomDateInCentury(21);
          assertTrue(result.getYear() >= 2001 && result.getYear() <= 2100);
        },
        () -> {
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class,
                  () -> DateJuggler.generateRandomDateInCentury((Year.MIN_VALUE / 100) - 1));
          assertEquals(
              exception.getMessage(),
              String.format(
                  "The given century needs to be between %s and %s.",
                  Year.MIN_VALUE / 100, Year.MAX_VALUE / 100));
        },
        () -> {
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class,
                  () -> DateJuggler.generateRandomDateInCentury((Year.MAX_VALUE / 100) + 1));
          assertEquals(
              exception.getMessage(),
              String.format(
                  "The given century needs to be between %s and %s.",
                  Year.MIN_VALUE / 100, Year.MAX_VALUE / 100));
        },
        () -> {
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class, () -> DateJuggler.generateRandomDateInCentury(0));
          assertEquals(
              exception.getMessage(),
              "The given century cannot have value zero as the Gregorian calendar does not have a zero century.");
        });
  }

  @Test
  void generateRandomAdultBirthDateWithAgeOfMajorityFrom() {
    assertAll(
        () -> {
          LocalDate date = LocalDate.of(2000, 1, 1);
          LocalDate result =
              DateJuggler.generateRandomAdultBirthDateWithAgeOfMajorityFrom(18, date);
          assertTrue(result.isBefore(date.minusYears(18)) && result.isAfter(date.minusYears(120)));
        },
        () -> {
          LocalDate date = LocalDate.of(Year.MIN_VALUE + 10, 1, 1);
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class,
                  () -> DateJuggler.generateRandomAdultBirthDateWithAgeOfMajorityFrom(18, date));
          assertEquals(
              exception.getMessage(),
              "The given date cannot have a year where the age of majority year calculated is less than the minimum allowed year -999999999");
        },
        () -> {
          LocalDate date = LocalDate.of(2000, 1, 1);
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class,
                  () -> DateJuggler.generateRandomAdultBirthDateWithAgeOfMajorityFrom(121, date));
          assertEquals(
              exception.getMessage(),
              "The given age of majority cannot be greater than the maximum age 120");
        });
  }

  @Test
  void generateRandomMinorBirthDateWithAgeOfMajorityFrom() {
    assertAll(
        () -> {
          LocalDate date = LocalDate.of(2000, 1, 1);
          LocalDate result =
              DateJuggler.generateRandomMinorBirthDateWithAgeOfMajorityFrom(18, date);
          assertTrue(
              result.isAfter(date.minusYears(18))
                  && (result.isBefore(date) || result.isEqual(date)));
        },
        () -> {
          LocalDate date = LocalDate.of(2000, 1, 1);
          JumbleJugglerException exception =
              assertThrows(
                  JumbleJugglerException.class,
                  () -> DateJuggler.generateRandomMinorBirthDateWithAgeOfMajorityFrom(121, date));
          assertEquals(
              exception.getMessage(),
              "The given age of majority cannot be greater than the maximum age 120");
        });
  }

  @Test
  void generateRandomAdultBirthDateFromNow() {
    LocalDate now = LocalDate.now();
    LocalDate result = DateJuggler.generateRandomAdultBirthDateWithAgeOfMajorityFromNow(18);
    assertTrue(result.isBefore(now.minusYears(18)) && result.isAfter(now.minusYears(120)));
  }

  @Test
  void generateRandomMinorBirthDateFromNow() {
    LocalDate now = LocalDate.now();
    LocalDate result = DateJuggler.generateRandomMinorBirthDateWithAgeOfMajorityFromNoe(18);
    assertTrue(result.isAfter(now.minusYears(18)) && (result.isBefore(now) || result.isEqual(now)));
  }
}
