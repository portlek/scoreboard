/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.scoreboard;

import io.github.portlek.scoreboard.line.Line;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class BoardTest {

  @Test
  void test() throws InterruptedException {
    Board.newBuilder(User.class)
      .setScoreboardSender(new Sender())
      .addDynamicObserverList(() -> Set.of(new User("observer-1")))
      .build()
      .start();
    while (true) {
      Thread.sleep(5L);
    }
  }

  private static final class Sender implements ScoreboardSender<User> {

    @Override
    public void close() {
      System.out.println("closed");
    }

    @Override
    public void send(@NotNull final Board<User> board, @NotNull final Collection<User> observers,
                     @NotNull final List<Line<User>> lines) {
      System.out.println("send -> " + observers);
    }
  }

  @ToString
  @RequiredArgsConstructor
  private static final class User {

    @NotNull
    private final String name;
  }
}
