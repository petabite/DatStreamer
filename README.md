<p align="center">
    <img src="https://raw.githubusercontent.com/petabite/DatStreamer/master/src/imgs/icon.png" width="80">
</p>
<p align="center">
    <strong>DatStreamer</strong>
</p>
<p align="center">
    a DatPiff streaming client written in Java
</p>

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Changelog](#changelog)
- [Features](#features)
- [Keyboard Shortcuts](#keyboard-shortcuts)
- [Get DatStreamer!](#get-datstreamer)
- [Updating DatStreamer!](#updating-datstreamer)
- [Screenshots](#screenshots)
- [Troubleshooting](#troubleshooting)
- [Project BTS](#project-bts)
  - [.dat folder](#dat-folder)
  - [src tree](#src-tree)
- [Acknowledgements](#acknowledgements)

## Changelog

| Release |                                                                                                     Changes                                                                                                     |  Date   |
| :-----: | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----: |
|  v1.1   | <ul><li>browse tab</li><li>download cover art</li><li>new button tooltips</li><li>add keyboard shortcuts</li><li>ui improvements</li><li>performance optimizations</li><li>under the hood refactoring</li></ul> |         |
|  v1.0   |                                                                                                 First Release!                                                                                                  | 3/18/20 |

## Features

- **Browse** featured mixtapes on DatPiff
- **Stream** any mixtape on DatPiff
- **Download** full mixtapes and cover art
- **Queue** up mixtapes and tracks to jam out to
- **Search** for mixtapes
- **Organize** your favorite tracks into playlists
- **Favorite** the best mixtapes for later

## Keyboard Shortcuts

|  Key  |     Action     |
| :---: | :------------: |
| SPACE |   play/pause   |
|   B   | previous track |
|   N   |   next track   |
|   M   | shuffle queue  |

## Get DatStreamer!

1. go to the [releases](https://github.com/petabite/DatStreamer/releases) tab
2. download the latest DatStreamer .jar file to any location on your computer
3. make sure you have [Java](https://www.java.com/en/download/) installed
4. launch and enjoy!

On first startup, DatStreamer creates a .dat folder where all your playlists and downloaded mixtapes are stored. don't delete this folder!

## Updating DatStreamer!

1. go to the [releases](https://github.com/petabite/DatStreamer/releases) tab
2. download the latest DatStreamer .jar file and replace with the previous version's .jar file
3. launch and enjoy your new DatStreamer!
   - if DatStreamer doesn't launch:
     1. make a copy of your .dat folder and place it somewhere safe
     2. then delete the orignal .dat folder
     3. restart datstreamer and copy over your previously downloaded mixtapes
     4. you'll have to start over with your playlists and your liked mixtapes :( srry

## Screenshots

![browse view](https://github.com/petabite/DatStreamer/blob/master/docs/browse.png?raw=true)
**Browse**

![library view](https://github.com/petabite/DatStreamer/blob/master/docs/library.png?raw=true)
**Library**

![mixtape view](https://github.com/petabite/DatStreamer/blob/master/docs/mixtape.png?raw=true)
**Mixtapes**

![search view](https://github.com/petabite/DatStreamer/blob/master/docs/search.png?raw=true)
**Search**

## Troubleshooting

**DatStreamer doesn't launch after updating!**

1. make a copy of your .dat folder and place it somewhere safe
2. then delete the orignal .dat folder
3. restart datstreamer and copy over your previously downloaded mixtapes
4. you'll have to start over with your playlists and your liked mixtapes :( srry

## Project BTS

### .dat folder

```
├───mixtapes
│   │   liked.dat           [your liked mixtapes]
│   │
│   └─── all your downloaded mixtapes
│
└───playlists
    |   Liked Songs.play    [your liked songs playlist]
    │
    └─── your other playlists

```

### src tree

```
│   style.css                   [styling for the app]
│
├───DatStreamer
│       Browse.java             [browse view]
│       DatFiles.java           [retrieve and store datstreamer files]
│       DatStreamer.java        [main class]
│       Library.java            [library view]
│       Menu.java               [tabs/nav view]
│       Player.java             [player/controls view]
│       QueueView.java          [tracks queue view]
│       Search.java             [search view]
│
├───imgs                        [icon resources]
│       add.png     
│       add_to_playlist.png
│       add_to_queue.png
│       back.png
│       clear.png
│       download.png
│       explore.png
│       icon.png
│       library.png
│       liked.png
│       next.png
│       not_liked.png
│       pause.png
│       play_arrow.png
│       previous.png
│       search.png
│       shuffle.png
│       track.png
│       trash.png
│
├───META-INF
│       MANIFEST.MF
│
├───Mixtapes
│       Mixtape.java            [Mixtape class]
│       MixtapePreview.java     [mixtape preview view]
│       Mixtapes.java           [just an ArrayList for Mixtape]
│       MixtapeView.java        [full mixtape view]
│
├───Playlists
│       Playlist.java           [Playlist class]
│       PlaylistPreview.java    [playlist preview view]
│       PlaylistView.java       [full playlist view]
│
└───Tracks
        Track.java              [Track class]
        TrackPreview.java       [track preview view]
        Tracks.java             [just an ArrayList for Track]
        TrackView.java          [full track view]
        TrackViewSpacer.java    [spacer for track view buttons]
```

## Acknowledgements

- [DatPiff🔥](https://www.datpiff.com/)
- [Google Material Design Icons](https://material.io/resources/icons/?style=baseline)
- [jsoup](https://jsoup.org/)
