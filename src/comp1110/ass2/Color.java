package comp1110.ass2;

/*
 * This is the Color enum representing the various states of a place on the board.
 * FORBIDDEN represents that this is a null-space and no tile should be placed here.
 * EMPTY means there is no tile occupying this place.
 * R, B, G, W represents Red, Blue, Green and White respectively which show which color
 * there on that tile position. So for example a tile is placed and due to its placement
 * (0,0) has white, so the boardStates will be W at (0,0)
 *
 * Author: Tanya Dixit
 * */
public enum Color {
    FORBIDDEN,EMPTY,R,B,G,W;
}
