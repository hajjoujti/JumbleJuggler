/**
* @author h@jjoujti
* Copyright (C)2023. All rights reserved.
* This code is licensed under the MIT License.
*/
package io.github.jumblejuggler;

import io.github.jumblejuggler.exceptions.JumbleJugglerException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class for generating random shorts with various constraints.
 *
 * <p>This class provides methods for generating random shorts within specified ranges, with
 * specific signs, and with a given length. It also handles constraints such as minimum and maximum
 * values, positivity, and negativity.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Get a random short within a specified range
 * try {
 *     short randomShortInRange = ShortJuggler.generateRandomShortBetween(10, 50);
 * } catch (JumbleJugglerException e) {
 *     // Handle exception
 * }
 *
 * // Get a random positive short greater than a specified minimum value
 * try {
 *     short randomPositiveShort = ShortJuggler.generateRandomPositiveShortGreaterThan(5);
 * } catch (JumbleJugglerException e) {
 *     // Handle exception
 * }
 * }</pre>
 *
 * <p>The class throws {@link JumbleJugglerException} for exceptional cases, such as invalid
 * constraints or when a negative value is provided where only positive values are allowed.
 *
 * @see JumbleJugglerException
 */
public class ShortJuggler {
  private static final String BAD_NEGATIVE = "The value given is %s, but it must be positive.";
  private static final String BAD_POSITIVE = "The value given is %s, but it must be negative.";
  private static final String BAD_BOUND =
      "The minimum value given is %s, but it must be smaller than the maximum value given which is %s.";

  private ShortJuggler() {}

  /**
   * Generates a random short.
   *
   * @return A randomly generated short.
   */
  public static short generateRandomShort() {
    return (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  /**
   * Generates a random positive short.
   *
   * @return A randomly generated positive short.
   */
  public static short generateRandomPositiveShort() {
    return (short) ThreadLocalRandom.current().nextInt(Short.MAX_VALUE);
  }

  /**
   * Generates a random negative short.
   *
   * @return A randomly generated negative short.
   */
  public static short generateRandomNegativeShort() {
    return (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, 0);
  }

  /**
   * Generates a random short smaller than a specified maximum value.
   *
   * @param max The exclusive upper bound for the generated short.
   * @return A randomly generated short smaller than the specified maximum.
   */
  public static short generateRandomShortSmallerThan(short max) {
    return (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, max);
  }

  /**
   * Generates a random short greater than a specified minimum value.
   *
   * @param min The inclusive lower bound for the generated short.
   * @return A randomly generated short greater than the specified minimum.
   */
  public static short generateRandomShortGreaterThan(short min) {
    return (short) ThreadLocalRandom.current().nextInt(min, Short.MAX_VALUE);
  }

  /**
   * Generates a random positive short smaller than a specified maximum value.
   *
   * @param max The exclusive upper bound for the generated positive short.
   * @return A randomly generated positive short smaller than the specified maximum.
   * @throws JumbleJugglerException if the provided maximum value is negative.
   */
  public static short generateRandomPositiveShortSmallerThan(short max)
      throws JumbleJugglerException {
    if (max < 0) {
      throw new JumbleJugglerException(
          new IllegalArgumentException(String.format(BAD_NEGATIVE, max)), ShortJuggler.class);
    }
    return (short) ThreadLocalRandom.current().nextInt(max);
  }

  /**
   * Generates a random positive short greater than a specified minimum value.
   *
   * @param min The inclusive lower bound for the generated positive short.
   * @return A randomly generated positive short greater than the specified minimum.
   * @throws JumbleJugglerException if the provided minimum value is negative.
   */
  public static short generateRandomPositiveShortGreaterThan(short min)
      throws JumbleJugglerException {
    if (min < 0) {
      throw new JumbleJugglerException(
          new IllegalArgumentException(String.format(BAD_NEGATIVE, min)), ShortJuggler.class);
    }
    return generateRandomShortGreaterThan(min);
  }

  /**
   * Generates a random negative short smaller than a specified maximum value.
   *
   * @param max The exclusive upper bound for the generated negative short.
   * @return A randomly generated negative short smaller than the specified maximum.
   * @throws JumbleJugglerException if the provided maximum value is positive.
   */
  public static short generateRandomNegativeShortSmallerThan(short max)
      throws JumbleJugglerException {
    if (max > 0) {
      throw new JumbleJugglerException(
          new IllegalArgumentException(String.format(BAD_POSITIVE, max)), ShortJuggler.class);
    }
    return generateRandomShortSmallerThan(max);
  }

  /**
   * Generates a random negative short greater than a specified minimum value.
   *
   * @param min The inclusive lower bound for the generated negative short.
   * @return A randomly generated negative short greater than the specified minimum.
   * @throws JumbleJugglerException if the provided minimum value is positive.
   */
  public static short generateRandomNegativeShortGreaterThan(short min)
      throws JumbleJugglerException {
    if (min > 0) {
      throw new JumbleJugglerException(
          new IllegalArgumentException(String.format(BAD_POSITIVE, min)), ShortJuggler.class);
    }
    return (short) ThreadLocalRandom.current().nextInt(min, 0);
  }

  /**
   * Generates a random short between a specified minimum and maximum value.
   *
   * @param min The inclusive lower bound for the generated short.
   * @param max The exclusive upper bound for the generated short.
   * @return A randomly generated short within the specified range.
   * @throws JumbleJugglerException if the minimum value is greater than the maximum value.
   */
  public static short generateRandomShortBetween(short min, short max)
      throws JumbleJugglerException {
    if (min > max) {
      throw new JumbleJugglerException(
          new IllegalArgumentException(String.format(BAD_BOUND, min, max)), ShortJuggler.class);
    }
    return (short) ThreadLocalRandom.current().nextInt(min, max);
  }
}
