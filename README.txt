The exercise was created in Eclipse and also tested on NetBeans.

The folder mazeRunner has two java files, the Main and the MazeRunner.
The first one has interaction between the user and the machine, along with 
testing if the folder name is valid.
The second one reads the files from the folder, creates the maze array and then
checks if there is a path from the starting point (S) to the end (E). This is done 
by the recursive function "runTheMaze", which checks all the possible ways N,E,S,W
along with wrapping with other side of the array if there is a way (by using
the functions "isValidPos" and "getArrayPos").
The recursion ends and the maze either has or hasn't got a path. If it does,
the maze array is printed, including the path (which first has been cleared by
unfinished routes by the functions "fixMaze" and "isNeighbour").

The exercise can be run by any Java IDE.

 
