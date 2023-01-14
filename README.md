# Dcmchat - minecraft & discord chat integration

![repo-size-badge](https://img.shields.io/github/repo-size/Zielin0/Dcmchat?style=flat-square)
![license-badge](https://img.shields.io/github/license/Zielin0/Dcmchat?style=flat-square)
![stars-badge](https://img.shields.io/github/stars/Zielin0/Dcmchat?style=flat-square)
![activity-badge](https://img.shields.io/github/commit-activity/m/Zielin0/Dcmchat?style=flat-square)


## A Minecraft plugin that connects your discord server to the Minecraft server

## Table Of Contents

1. [General Info](#general-info)
2. [Features](#features)
3. [Commands](#commands)
4. [Downloading](#downloading)
5. [Installation](#installation)
6. [Configuring](#configuring)
7. [Building from source](#building-from-source)
8. [Warnings](#warnings)
9. [Dependencies and Credits](#dependencies-and-credits)
10. [Contributing](#contributing)
11. [License](#license)
12. [Others](#others)

## General Info
The plugin's purpose is to connect the discord text channel with Minecraft chat.

The project was done for the 1.19.2 Minecraft version.

The plugin will display messages from Minecraft on the discord channel and vice versa.

The project was done purely for learning and fun purposes. Do not take this plugin seriously.

## Features

- Displays join/leave message on the discord channel
- Displays player deaths
- Displays player advancements
- Displays player messages on discord
- Displays discord messages on Minecraft chat
- Commands
   - Status of discord server on minecraft chat 
   - Configure options of the plugin from minecraft chat

## Commands

| Command Name | Usage                                                   | Example                 |
|--------------|---------------------------------------------------------|-------------------------|
| Status       | /status                                                 | /status                 |
| Config       | /config [enable-tag \ enable-console \ channel] <value> | /config enable-tag true |

## Downloading

Download this plugin from [Releases](https://github.com/Zielin0/Dcmchat/releases).

## Installation

You need a 1.19.2 [papermc server](https://papermc.io/downloads).

When the server is installed place the freshly downloaded plugin in the `plugins/` folder.

Start a server to generate needed files (Don't worry about errors **for now**).

## Configuring

You need a discord bot for this.

1. Create one at [Discord Developer Portal](https://discord.com/developers/applications).

   Make sure to invite the bot to your server!

2. Copy the bot token to your clipboard.

3. Go to `plugins/Dcmchat/` and open the `config.yml` file with a text editor.

4. Replace `"server"` with your server name (the server with the bot).

5. You need to set the channel.

   There are two options:
   - Create a text channel with a "mc-chat" name.
   - Change the "mc-chat" to your channel name.

6. If you want the plugin to display discord messages in the console or show the discord user's tag
   choose an option and change "false" to "true"

7. Lastly add a line to `config.yml` saying:

    ```yaml
    BOT_TOKEN: YOUR_BOT_TOKEN
    ```

Replace the "YOUR_BOT_TOKEN" with the actual token.

## Building from source

1. Clone the repository.

    ```shell
    $ git clone https://github.com/Zielin0/Dcmchat.git
    ```

2. Open created folder with [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

3. Install all maven dependencies.

   - If you want the jar to be compiled directly to your plugins
     directory add this to `pom.xml` in `maven-jar-plugin` section:
   
   ```xml
   <configuration>
     <outputDirectory>C:\path\to\plugins</outputDirectory>
   </configuration>
   ```

4. Run maven scripts first `clean` then `package`.

5. Plugin should appear in the `target/` directory (only if you skipped `outputDirectory` configuration).

## Warnings

The plugin is not recommended for discord and Minecraft servers
more significant than 75 users/players.

## Dependencies and Credits

#### Dependencies:

- [paper-api 1.19.2](https://papermc.io/using-the-api)
- [Lombok](https://projectlombok.org/setup/maven)
- [JDA](https://github.com/DV8FromTheWorld/JDA#download)
- [emoji-java](https://github.com/vdurmont/emoji-java)

#### Credits:

- [urlregex.com](https://urlregex.com/) for URL regex pattern

## Contributing

Contributions are welcome just make sure the code works.

Also, please describe your changes / added features if you want to contribute.

## License

The project is under the [MIT License](./LICENSE).

## Others

If the plugin throws an unexpected error **PLEASE** report it!

You can report issues and errors at [Issues Page](https://github.com/Zielin0/Dcmchat/issues).
