# Wandering Joe

A 2D tile-based Java game where you guide Joe through a 19x19 grid, collecting keys to unlock doors, gathering coins to open hatches, and progressing through levels.

## Overview
Navigate Joe in a top-down world to collect all coins and unlock the hatch to advance. Use keys to open doors. The game field is 760x760 pixels, with a panel above showing the key count ("Počet kľúčov: [number]").

## Features
- Move with arrow keys in a 19x19 tile grid.
- Collect keys to unlock doors, coins to open hatches.
- Progress through 5 levels with a "You Won" screen.
- Key count display above the game field.
- Smooth 60 FPS rendering with collision detection.
## Screenshots
<img width="316" height="341" alt="image" src="https://github.com/user-attachments/assets/e66a667c-a868-4750-ab6d-0f4790c9b325" />

## Installation
1. Clone the repo:
git clone https://github.com/Robindhuil/Wandering-Joe.git
cd Wandering-Joe
2. Ensure JDK 8+ is installed.
3. Place resources in `src/main/resources/`:
- Player images: `player/`
- Object images: `objects/` (e.g., `key.png`, `door_closed.png`)
- Level files: `levels/` (e.g., `level0.txt`, `level0_obj.txt`)

## How to Play
- **Controls**: Arrow keys to move Joe.
- **Goals**:
- Collect keys to unlock closed doors (1 key per door).
- Gather all coins to open the hatch.
- Enter the hatch to advance levels.
- **UI**: Key count shown above the game field.

## Building & Running
- **IDE**: Import into IntelliJ/Eclipse, run `Main.java`.
- **Command Line**:
- javac -d bin -sourcepath src -cp src src/main/Main.java
java -cp bin main.Main

## Known Issues
- Ensure resource files are in `src/main/resources/` to avoid errors.
- Report bugs via GitHub Issues.
