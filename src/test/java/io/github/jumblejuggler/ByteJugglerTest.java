/**
* @author h@jjoujti
* Copyright (C)2023. All rights reserved.
* This code is licensed under the MIT License.
*/
package io.github.jumblejuggler;

import static org.junit.jupiter.api.Assertions.*;

import io.github.jumblejuggler.exceptions.JumbleJugglerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ByteJugglerTest {

  @Test
  void testGenerateRandomPositiveByte() {
    byte result = assertDoesNotThrow(ByteJuggler::generateRandomPositiveByte);
    assertTrue(result >= 0);
  }

  @Test
  void testGenerateRandomNegativeByte() {
    byte result = assertDoesNotThrow(ByteJuggler::generateRandomNegativeByte);
    assertTrue(result < 0);
  }

  @Test
  void testGenerateRandomByteSmallerThan() {
    byte random = ByteJuggler.generateRandomByte();

    byte result =
        Assertions.assertDoesNotThrow(() -> ByteJuggler.generateRandomByteSmallerThan(random));
    assertTrue(result < random);
  }

  @Test
  void testGenerateRandomByteGreaterThan() {
    byte random = ByteJuggler.generateRandomByte();

    byte result =
        Assertions.assertDoesNotThrow(() -> ByteJuggler.generateRandomByteGreaterThan(random));
    assertTrue(result >= random);
  }

  @Test
  void testGenerateRandomPositiveByteGreaterThan() {
    byte random = ByteJuggler.generateRandomPositiveByte();

    byte result =
        Assertions.assertDoesNotThrow(
            () -> ByteJuggler.generateRandomPositiveByteGreaterThan(random));
    assertTrue(result >= 0 && result >= random);
  }

  @Test
  void testGenerateRandomPositiveByteGreaterThan_WhenMaxIsNegative_ThrowException() {
    byte random = ByteJuggler.generateRandomNegativeByte();

    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> ByteJuggler.generateRandomPositiveByteGreaterThan(random));
    assertEquals(
        "The value given is " + random + ", but it must be positive.", exception.getMessage());
  }

  @Test
  void testGenerateRandomPositiveByteSmallerThan() {
    byte random = ByteJuggler.generateRandomPositiveByte();

    byte result =
        Assertions.assertDoesNotThrow(
            () -> ByteJuggler.generateRandomPositiveByteSmallerThan(random));
    assertTrue(result >= 0 && result < random);
  }

  @Test
  void testGenerateRandomPositiveByteSmallerThan_WhenMaxIsNegative_ThrowException() {
    byte random = ByteJuggler.generateRandomNegativeByte();

    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> ByteJuggler.generateRandomPositiveByteSmallerThan(random));
    assertEquals(
        "The value given is " + random + ", but it must be positive.", exception.getMessage());
  }

  @Test
  void testGenerateRandomNegativeByteGreaterThan() {
    byte random = ByteJuggler.generateRandomNegativeByte();
    byte result =
        Assertions.assertDoesNotThrow(
            () -> ByteJuggler.generateRandomNegativeByteGreaterThan(random));
    assertTrue(result < 0 && result >= random);
  }

  @Test
  void testGenerateRandomNegativeByteGreaterThan_WhenMaxIsPositive_ThrowException() {
    byte random = ByteJuggler.generateRandomPositiveByte();
    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> ByteJuggler.generateRandomNegativeByteGreaterThan(random));
    assertEquals(
        "The value given is " + random + ", but it must be negative.", exception.getMessage());
  }

  @Test
  void testGenerateRandomNegativeByteSmallerThan() {
    byte random = ByteJuggler.generateRandomNegativeByte();

    byte result =
        Assertions.assertDoesNotThrow(
            () -> ByteJuggler.generateRandomNegativeByteSmallerThan(random));
    assertTrue(result < 0 && result < random);
  }

  @Test
  void testGenerateRandomNegativeByteSmallerThan_WhenMaxIsPositive_ThrowException() {
    byte random = ByteJuggler.generateRandomPositiveByte();

    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class,
            () -> ByteJuggler.generateRandomNegativeByteSmallerThan(random));
    assertEquals(
        "The value given is " + random + ", but it must be negative.", exception.getMessage());
  }

  @Test
  void testGenerateRandomByteBetween_WhenMinGreaterThanMax_ThrowException() {
    byte random = ByteJuggler.generateRandomByte();
    byte min = ByteJuggler.generateRandomByteSmallerThan(random);
    byte max = ByteJuggler.generateRandomByteGreaterThan(random);

    byte result =
        Assertions.assertDoesNotThrow(() -> ByteJuggler.generateRandomByteBetween(min, max));
    assertTrue(result >= min && result < max);
  }

  @Test
  void testGenerateRandomByteBetween() {
    byte random = ByteJuggler.generateRandomByte();
    byte min = ByteJuggler.generateRandomByteGreaterThan(random);
    byte max = ByteJuggler.generateRandomByteSmallerThan(random);

    JumbleJugglerException exception =
        assertThrows(
            JumbleJugglerException.class, () -> ByteJuggler.generateRandomByteBetween(min, max));
    assertEquals(
        "The minimum value given is "
            + min
            + ", but it must be smaller than "
            + "the maximum value given which is "
            + max
            + ".",
        exception.getMessage());
  }
}
