/**
* @author h@jjoujti
* Copyright (C)2024. All rights reserved.
* This code is licensed under the MIT License.
*/
package io.github.jumblejuggler;

import io.github.jumblejuggler.exceptions.JumbleJugglerException;
import java.time.LocalDate;
import java.time.Year;

/**
 * Utility class generating random {@link LocalDate} instances and manipulating dates based on
 * various criteria.
 *
 * <p>This class handles date generation, date manipulation, and age-related calculations for both
 * adults and minors. It ensures that the generated dates adhere to specified rules such as age of
 * majority and date constraints.
 *
 * <p>The class uses the {@link LongJuggler} utility for generating random long values within
 * specified ranges.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Get a random date
 * LocalDate randomDate = DateJuggler.generateRandomDate();
 *
 * // Get a random adult birthdate from the current date if the age of majority is 18
 * LocalDate adultBirthDate = DateJuggler.generateRandomAdultBirthDateWithAgeOfMajorityFromNow(18);
 * }</pre>
 *
 * <p>The class throws {@link JumbleJugglerException} for exceptional cases, such as invalid
 * constraints.
 *
 * @see LongJuggler
 * @see JumbleJugglerException
 */
public class DateJuggler {
  private static final long MIN_EPOCH_DAY = LocalDate.MIN.toEpochDay();
  private static final long MAX_EPOCH_DAY = LocalDate.MAX.toEpochDay();
  private static final LocalDate DAY_1_CE = LocalDate.of(1, 1, 1);
  private static final LocalDate DAY_1_BCE = LocalDate.of(-1, 12, 31);
  private static final int MAX_AGE = 120;

  private DateJuggler() {}

  /**
   * Generates a random LocalDate instance within the valid date range.
   *
   * @return A randomly generated LocalDate.
   */
  public static LocalDate generateRandomDate() {
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(MIN_EPOCH_DAY, MAX_EPOCH_DAY + 1));
  }

  /**
   * Generates a random LocalDate instance within the valid date range for dates before the Common
   * Era (BCE).
   *
   * @return A randomly generated BCE LocalDate.
   */
  public static LocalDate generateRandomBCEDate() {
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(MIN_EPOCH_DAY, DAY_1_BCE.toEpochDay() + 1));
  }

  /**
   * Generates a random LocalDate instance within the valid date range for dates after the Common
   * Era (CE).
   *
   * @return A randomly generated CE LocalDate.
   */
  public static LocalDate generateRandomCEDate() {
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(DAY_1_CE.toEpochDay(), MAX_EPOCH_DAY + 1L));
  }

  /**
   * Generates a random LocalDate before the specified maximum date.
   *
   * @param max The maximum date.
   * @return A randomly generated LocalDate before the specified date.
   * @throws JumbleJugglerException If the specified date is equal to the minimum date.
   */
  public static LocalDate generateRandomDateBefore(LocalDate max) throws JumbleJugglerException {
    if (max.isEqual(LocalDate.MIN)) {
      throw new JumbleJugglerException(
          String.format(
              "The given date is equals to the minimum date %s. It needs to be after it.",
              LocalDate.MIN),
          DateJuggler.class);
    }
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(MIN_EPOCH_DAY, max.toEpochDay()));
  }

  /**
   * Generates a random LocalDate after the specified minimum date.
   *
   * @param min The minimum date.
   * @return A randomly generated LocalDate after the specified date.
   * @throws JumbleJugglerException If the specified date is equal to the maximum date.
   */
  public static LocalDate generateRandomDateAfter(LocalDate min) throws JumbleJugglerException {
    if (min.isEqual(LocalDate.MAX)) {
      throw new JumbleJugglerException(
          String.format(
              "The given date is equals to the maximum date %s. It needs to be before it.",
              LocalDate.MAX),
          DateJuggler.class);
    }
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(min.toEpochDay() + 1L, MAX_EPOCH_DAY + 1L));
  }

  /**
   * Generates a random LocalDate before the current date.
   *
   * @return A randomly generated LocalDate before the current date.
   */
  public static LocalDate generateRandomDateBeforeNow() {
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(MIN_EPOCH_DAY, LocalDate.now().toEpochDay()));
  }

  /**
   * Generates a random LocalDate after the current date.
   *
   * @return A randomly generated LocalDate after the current date.
   */
  public static LocalDate generateRandomDateAfterNow() {
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(
            LocalDate.now().toEpochDay() + 1L, MAX_EPOCH_DAY + 1L));
  }

  /**
   * Generates a random LocalDate between the specified minimum and maximum dates (inclusive).
   *
   * @param min The minimum date.
   * @param max The maximum date.
   * @return A randomly generated LocalDate between the specified dates.
   * @throws JumbleJugglerException If the minimum date is after the maximum date.
   */
  public static LocalDate generateRandomDateBetween(LocalDate min, LocalDate max)
      throws JumbleJugglerException {
    if (min.isAfter(max)) {
      throw new JumbleJugglerException(
          "The minimum given date is after the maximum given date", DateJuggler.class);
    }

    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(min.toEpochDay(), max.toEpochDay()));
  }

  /**
   * Generates a random LocalDate within the specified century.
   *
   * @param century The century for which the date should be generated.
   * @return A randomly generated LocalDate within the specified century.
   * @throws JumbleJugglerException If the specified century is invalid.
   */
  public static LocalDate generateRandomDateInCentury(int century) throws JumbleJugglerException {
    if (century < Year.MIN_VALUE / 100 || century > Year.MAX_VALUE / 100) {
      throw new JumbleJugglerException(
          String.format(
              "The given century needs to be between %s and %s.",
              Year.MIN_VALUE / 100, Year.MAX_VALUE / 100),
          DateJuggler.class);
    }

    if (century == 0) {
      throw new JumbleJugglerException(
          "The given century cannot have value zero as the Gregorian calendar does not have a zero century.",
          DateJuggler.class);
    }
    int startYear = (century - 1) * 100 + 1;
    int endYear = century * 100;
    LocalDate startDate = LocalDate.of(startYear, 1, 1);
    LocalDate endDate = LocalDate.of(endYear, 12, 31);
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(startDate.toEpochDay(), endDate.toEpochDay() + 1L));
  }

  /**
   * Generates a random birthdate for an adult with a specified age of majority based on the
   * provided date.
   *
   * @param ageOfMajority The age at which an individual attains majority.
   * @param date The reference date from which the random birthdate is generated.
   * @return A random birthdate for an adult.
   * @throws JumbleJugglerException If the specified age of majority is invalid or the calculated
   *     birthdate falls outside valid date ranges.
   */
  public static LocalDate generateRandomAdultBirthDateWithAgeOfMajorityFrom(
      int ageOfMajority, LocalDate date) throws JumbleJugglerException {
    if (date.getYear() - ageOfMajority < Year.MIN_VALUE) {
      throw new JumbleJugglerException(
          String.format(
              "The given date cannot have a year where the age of majority year calculated is less than the minimum allowed year %s",
              Year.MIN_VALUE),
          DateJuggler.class);
    }

    if (ageOfMajority > MAX_AGE) {
      throw new JumbleJugglerException(
          String.format(
              "The given age of majority cannot be greater than the maximum age %s", MAX_AGE),
          DateJuggler.class);
    }

    if (date.getYear() - MAX_AGE < Year.MIN_VALUE) {
      return LocalDate.ofEpochDay(
          LongJuggler.generateRandomLongBetween(
              MIN_EPOCH_DAY, date.minusYears(ageOfMajority).toEpochDay() + 1L));
    }
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(
            date.minusYears(MAX_AGE).toEpochDay(),
            date.minusYears(ageOfMajority).toEpochDay() + 1L));
  }

  /**
   * Generates a random birthdate for a minor with a specified age of majority based on the provided
   * date.
   *
   * @param ageOfMajority The age at which an individual attains majority.
   * @param date The reference date from which the random birth date is generated.
   * @return A random birthdate for a minor.
   * @throws JumbleJugglerException If the specified age of majority is invalid or the calculated
   *     birth date falls outside valid date ranges.
   */
  public static LocalDate generateRandomMinorBirthDateWithAgeOfMajorityFrom(
      int ageOfMajority, LocalDate date) throws JumbleJugglerException {
    if (ageOfMajority > MAX_AGE) {
      throw new JumbleJugglerException(
          String.format(
              "The given age of majority cannot be greater than the maximum age %s", MAX_AGE),
          DateJuggler.class);
    }

    if (date.getYear() - ageOfMajority < Year.MIN_VALUE) {
      return LocalDate.ofEpochDay(
          LongJuggler.generateRandomLongBetween(MIN_EPOCH_DAY, date.toEpochDay() + 1L));
    }
    return LocalDate.ofEpochDay(
        LongJuggler.generateRandomLongBetween(
            date.minusYears(ageOfMajority).toEpochDay() + 1L, date.toEpochDay() + 1L));
  }

  /**
   * Generates a random birthdate for an adult with a specified age of majority based on the current
   * date.
   *
   * @param ageOfMajority The age at which an individual attains majority.
   * @return A random birthdate for an adult.
   * @throws JumbleJugglerException If the specified age of majority is invalid or the calculated
   *     birthdate falls outside valid date ranges.
   */
  public static LocalDate generateRandomAdultBirthDateWithAgeOfMajorityFromNow(int ageOfMajority)
      throws JumbleJugglerException {
    return generateRandomAdultBirthDateWithAgeOfMajorityFrom(ageOfMajority, LocalDate.now());
  }

  /**
   * Generates a random birthdate for a minor with a specified age of majority based on the current
   * date.
   *
   * @param ageOfMajority The age at which an individual attains majority.
   * @return A random birthdate for a minor.
   * @throws JumbleJugglerException If the specified age of majority is invalid, or the calculated
   *     birthdate falls outside valid date ranges.
   */
  public static LocalDate generateRandomMinorBirthDateWithAgeOfMajorityFromNoe(int ageOfMajority)
      throws JumbleJugglerException {
    return generateRandomMinorBirthDateWithAgeOfMajorityFrom(ageOfMajority, LocalDate.now());
  }
}
