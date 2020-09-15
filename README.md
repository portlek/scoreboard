[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
[![rultor](https://www.rultor.com/b/yegor256/rultor)](https://www.rultor.com/p/portlek/scoreboard)

[![Build Status](https://travis-ci.com/portlek/scoreboard.svg?branch=master)](https://travis-ci.com/portlek/scoreboard)
![Maven Central](https://img.shields.io/maven-central/v/io.github.portlek/scoreboard-common?label=version)
## How to use
```xml
<!-- For Bukkit projects. -->
<dependency>
    <groupId>io.github.portlek</groupId>
    <artifactId>scoreboard-bukkit</artifactId>
    <version>${version}</version>
</dependency>
<!-- For Nukkit projects. -->
<dependency>
    <groupId>io.github.portlek</groupId>
    <artifactId>scoreboard-nukkit</artifactId>
    <version>${version}</version>
</dependency>
```
```groovy
// For Bukkit projects.
implementation("io.github.portlek:scoreboard-bukkit:${version}")
// For Nukkit projects.
implementation("io.github.portlek:scoreboard-nukkit:${version}")
```
## Example usage
```java
final class TestScoreboard {

    void sendScoreboard(@NotNull Plugin plugin, @NotNull final List<Player> players) {
        Board.create(plugin)
            // Adds the players that scoreboard shows up.
            .addPlayers(players)
            // Runs before the scoreboard sent for each player. The players list depend on the `sendType`
            // If returns false, player can get the scoreboard for the currency tick.
            .filter(player -> {
                return player.getName().equals("Test");
            })
            // Runs before the scoreboard sent for each player. The players list depend on the `sendType`
            // Removes the player from the list that contains all listed players.
            .removeIf(player -> {
                return player.getName().equals("ShouldRemove");
            })
            .beforeSend(player -> {
                player.sendMessage("This message sent before the scoreboard sent!");
            })
            .afterSend(player -> {
                player.sendMessage("This message sent after the scoreboard sent!");
            })
            // Send the player which added with `addPlayers` methods.
            .sendType(SendType.forEachPlayer(player -> {
                // You can use PlaceholderAPI plugin here.
                return Arrays.asList("line 1", "line 2", player.getName());
            }))
            // Sends all players on the server, not depend on the `addPlayers` method.
            .sendType(SendType.forEachOnlinePlayer(online -> {
                // You can use PlaceholderAPI plugin here.
                return Arrays.asList("line 1", "line 2", player.getName());
            }))
            // Send the player which added with `addPlayers` methods.
            .sendType(SendType.forEachPlayerLine(() -> {
                return Arrays.asList("line 1", "line 2", "line 3");
            }))
            // Sends all players on the server, not depend on the `addPlayers` method.
            .sendType(SendType.forEachOnlinePlayerLine(() -> {
                return Arrays.asList("line 1", "line 2", "line 3");
            }))
            // Sends the first scoreboard after this value. (20 = 1 second)
            .startDelay(0L)
            // Sends the scoreboards with this period. (20 = 1 second)
            .tick(10L)
            //.sendOnce() Disable the task and send the scoreboard for each player just for once.
            .start();
    }

}
```