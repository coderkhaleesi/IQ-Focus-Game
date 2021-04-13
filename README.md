# COMP1110 Assignment 2

## Commands to run the game

Run from C:

set PATH_TO_FX="openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\"

java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -jar IQ-Focus-Game\game.jar

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds with being resourceful and working collaboratively. You *should* be resourceful, you should collaborate within your team, and you should discuss the assignment and other aspects of the course with others taking the class. However, *you must never misrepresent the work of others as your own*. If you have taken ideas from elsewhere or used code sourced from elsewhere, you must say so with *utmost clarity*. At each stage of the assignment you will be asked to submit a statement of originality, either as a group or as individuals. This statement is the place for you to declare which ideas or code contained in your submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/) on academic honesty. If you have any questions, please ask me.

Carefully review the [statement of originality](originality.md) which you must complete.  Edit that statement and update it as you complete each state of the assignment, ensuring that when you complete each stage, a truthful statement is committed and pushed to your repo.

## Purpose

In this assignment you will *work as a group* to master a number of major themes of this course, including software design and implementation, using development tools such as Git and IntelliJ, and using JavaFX to build a user interface.  **Above all, this assignment will emphasize group work**; while you will receive an individual mark for your work based on your contributions to the assignment, **you can only succeed if all members contribute to your group's success**.


## Assignment Deliverables

The assignment is worth 25% of your total assessment, and it will be marked out of 25. So each mark in the assignment corresponds to a mark in your final assessment for the course. Note that for some stages of the assignment you will get a _group_ mark, and for others you will be _individually_ marked. The mark breakdown and the due dates are described on the [deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) page.

Your work will be marked via your tutor accessing git, so it is essential that you carefully follow instructions for setting up and maintaining your group repository. At each deadline you will be marked according to whatever is committed to your repository at the time of the deadline. You will be assessed on how effectively you use git as a development tool.

## Problem Description

The assignment involves implementing in Java, a board game called [IQ-focus](https://www.smartgames.eu/uk/one-player-games/iq-focus)
made by the games developer [SmartGames](https://www.smartgames.eu/uk).

<img src="assets/iqfocus.png" width="400">

#### Objective 

The game is a puzzle.  At the start, the player selects a
[challenge](#challenges) which defines the color of each of the nine
squares in the central board area.  The objective is to place all ten
colored playing pieces onto a board comprising 43 locations (indents).
The player must place the pieces such that they: a) fit together
correctly on the board, without overlaps or gaps, and b) satisfy the
challenge.

A completed game:

<img src="assets/completedgame.png" width="400">

To help you visualize the game, we have provided a
[paper](assets/papergame.pdf) version, which you can cut out.

#### Challenges

A game starts by choosing a challenge which specifies the color of the
nine central squares.  Here is the chosen challenge for the game above
(this happens to be challenge 1 that comes with the game):

<img src="assets/challenge.png" width="400">

Interestingly, although all challenges are specified only in terms of
the arrangement of colors in the nine central squares, some challenges
are much easier to solve than others.  If you attempt the harder
tasks, you may want to reflect on what makes some challenges so much
easier than others.  Note that as a general rule for puzzles, the more
constrained the player is, the fewer options they have, and
consequently the solution to the challenge is simpler.

The game comes with five difficulty levels: _starter_, _junior_, _expert_, _master_, and _wizard_, and offers 24 challenges at each level, for a total of 120 prescribed challenges.   These challenges are provided for you in the [Solutions class](https://gitlab.cecs.anu.edu.au/comp1110/comp1110-ass2/blob/master/src/comp1110/ass2/Solution.java#L28), and the different difficulty levels are tested in the [SolutionsTest](https://gitlab.cecs.anu.edu.au/comp1110/comp1110-ass2/blob/master/tests/comp1110/ass2/SolutionsTest.java#L38) test.

#### Solutions

Each challenge has just one solution.  When we refer to solutions, we
ignore piece rotations that take up the same space on the board.  Such
rotations are described as *symmetric*, which is defined in more
detail [below](#strict-symmetry).

The following sequence shows one possible progression of a solution to the game
above (note that the order in which the pieces are played is not important; this
is just one possible ordering).

<img src="assets/a2.png" width="200">
<img src="assets/a3.png" width="200">
<img src="assets/a4.png" width="200">
<img src="assets/a5.png" width="200">
<img src="assets/a6.png" width="200">
<img src="assets/a7.png" width="200">
<img src="assets/a8.png" width="200">
<img src="assets/a9.png" width="200">
<img src="assets/a10.png" width="200">
<img src="assets/a00.png" width="200">

#### Board

The game is played on a board comprised of 43 **locations** arranged
in a 9x5 grid.  In the real-world game, each location consists of a
square indent into which a piece may fit.  In our game, locations are
encoded as two digits, the first one identifying the column from `0`
to `8`, followed by another identifying the row from `0` to `4`.  For
example, in the game above, the first piece is put on position `30`
and the second is put on `32`.  Note that pieces are addressed as `XY`
where `X` identifies the column where the left-most square of the
piece is in, and `Y` identifies the row where the top square of the
piece is in.   Yellow dots in the diagram above indicate the
point of reference (i.e. the top-most row and left-most column
occupied by the piece).

#### Pieces

The game comprises **10 playing shapes**, each of which is made of
plastic and consists of three, four, or five connected squares (see
the photo above). The pieces fit neatly into the indents in the
plastic board formed by the 43 locations.

Each piece can be **rotated** at 90 degree increments, allowing for 4
different **orientations**.  The following illustration shows all 40
possible combinations of the 10 pieces and 4 orientations.   (Yellow
dots indicate the point of reference for the piece's location,
described below).

<img src="assets/all_pieces.png">

##### Strict Symmetry

Notice that piece `f` and piece `g` are symmetric, so rotating them
twice will not change the shape (for example `fxy0` is identical to
`fxy2`).  We describe that as *'strictly
[symmetric](https://en.wikipedia.org/wiki/Symmetry)'*.  We ignore the
redundant rotations with higher numberings (e.g. `fxy2`, `fxy3`,
`gxy2` and `gxy3` are ignored).

#### Legal Piece Placements

For a piece placement to be valid, the following must be true:

* All squares comprising each piece must be placed on valid board
  locations (**no part of a piece may be off the board**).
* All squares comprising each piece must be placed on vacant board
  locations (**pieces may not overlap**).

#### Encoding Game State and Challenge

Game states and challenges are encoded as strings.  Your game will
need to be able to initialize itself using these strings and some of
your tasks relate directly to these strings.

##### Challenge Strings

A challenge string consists of a sequence of exactly nine characters,
each describing the color of one square in the central 3x3 board area.
There are four colors: `White`, `Red`, `Blue` and `Green`, which are
encoded as `'W'`, `'R'`, `'B'` and `'G'` respectively.

For the sample challenge below, the challenge string is `"RRRBWBBRB"`,
which is achieved by tracing the central board area with this order:

<img src="assets/challenge_encoding.png">

##### Placement Strings

A placement string consists of between one and ten (inclusive) **piece
placements** (pieces `a` to `j`).  The placement string may not
include any piece twice.  A completed game must include ten piece
placements.  Each piece placement is described using four characters.
For example, the game described above is characterized (when complete)
by the string `"a000b013c113d302e323f400g420h522i613j701"`.  Note that
the placement string is ordered (piece `a` first, and piece `j` last),
which is a requirement for valid placement strings.

##### Piece Placement Strings

A piece placement string consists of four characters describing the
location and orientation of one particular piece on the board:

* The first character identifies **which of the ten shapes** is being
  placed (`a` to `j`).
* The second character identifies **which column** the left of the
  piece is in (columns are labelled `0` to `9`).
* The third character identifies **which row** the top of the piece is
  in (rows are labelled `0` to `4`).
* The fourth character identifies **which orientation** the piece is
  in (`0` to `3` for four rotations as illustrated above).

The image above shows the first and fourth characters for each of the
pieces in each of their orientations (40 in total). For example, at
top left, 'a0' describes piece 'a' at orientation '0'.  Below it, 'b0'
describes piece 'b' at orientation '0'.  At the bottom right 'j3'
describes piece 'j' at orientation '3'.  And so on.  A piece placement
string starts and ends with these two characters and has two more in
between which describe where the piece is placed.

## Legal and Ethical Issues

First, as with any work you do, you must abide by the principles of
[honesty and integrity](http://academichonesty.anu.edu.au). You are
expected to demonstrate honesty and integrity in everything you do.

In addition to those ground rules, you are to follow the rules one
would normally be subject to in a commercial setting. In particular,
you may make use of the works of others under two fundamental
conditions: a) your use of their work must be clearly acknowledged,
and b) your use of their work must be legal (for example, consistent
with any copyright and licensing that applies to the given
material). *Please understand that violation of these rules is a very
serious offence.* However, as long as you abide by these rules, you
are explicitly invited to conduct research and make use of a variety
of sources. You are also given an explicit means with which to declare
your use of other sources (via originality statements you must
complete). It is important to realize that you will be assessed on the
basis of your original contributions to the project. While you won't
be penalized for correctly attributed use of others' ideas, the work
of others will not be considered as part of your
contribution. Therefore, these rules allow you to copy another
student's work entirely if: a) they gave you permission to do so, and
b) you acknowledged that you had done so. Notice, however, that if you
were to do this you would have no original contribution and so would
receive no marks for the assignment (but you would not have broken any
rules either).

## Evaluation Criteria

It is essential that you refer to the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
to check that you understand each of the deadlines and what is
required.  Your assignment will be marked via tests run through git's
continuous integration (CI) framework, so all submittable materials
will need to be in git and in the *correct* locations, as prescribed
by the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/).


**The mark breakdown is described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.**

### Part One

In the first part of the assignment you will:
* Implement parts of the text interface to the game (Tasks #2, and #3).
* Implement a simple viewer that allows you to visualize game states (Task #4).

The criteria for the [completion of part one](https://gitlab.cecs.anu.edu.au/comp1110/comp1110/wikis/deliverables#d2c-assignment-2-stage-c-2-marks-group) is as follows:

<a name="p"></a>
**Pass**
* Tasks #2 and #3

<a name="cr"></a>
**Credit**
* Task #4 *(in addition to all tasks required for Pass)*

<a name="d"></a>
**Distinction**
* Task #5 *(in addition to all tasks required for Credit)*

### Part Two

Create a fully working game, using JavaFX to implement a playable
graphical version of the game in a 933x700 window.

Notice that aside from the window size, the details of exactly how the
game looks etc, are **intentionally** left up to you.  The diagrams
above are for illustration purposes only, although you are welcome to
use all of the resources provided in this repo, including the bitmap
images for each of the eight shapes.

The only **firm** requirements are that:

* you use Java and JavaFX,
* the game respects the specification of the game given here,
* the game be easy to play,
* it runs in a 933x700 window, and
* that it is executable on a standard lab machine from a jar file called `game.jar`,

Your game must successfully run from `game.jar` from within another
user's (i.e.  your tutor's) account on a standard lab machine (in
other words, your game must not depend on features not self-contained
within that jar file and the Java 11 runtime).

<a name="2p"></a>
**Pass**
* Correctly implements all of the <b>Part One</b> criteria.
* Appropriate use of git (as demonstrated by the history of your repo).
* Completion of Task #6
* Executable on a standard lab computer from a runnable jar file,
  game.jar, which resides in the root level of your group repo.

<a name="2c"></a>
**Credit**
* _All of the Pass-level criteria, plus the following..._
* Task #7

<a name="2d"></a>
**Distinction**
* _All of the Credit-level criteria, plus the following..._
* Tasks #8 and #9

<a name="2hd"></a>
**High Distinction**
* _All of the Distinction-level criteria, plus the following..._
* Tasks #10 and #11
